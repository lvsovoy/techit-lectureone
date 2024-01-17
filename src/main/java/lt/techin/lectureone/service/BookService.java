package lt.techin.lectureone.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lt.techin.lectureone.exception.AuthorNotFoundException;
import lt.techin.lectureone.external.IOpenLibraryClient;
import lt.techin.lectureone.external.model.AuthorWorksResponse;
import lt.techin.lectureone.model.mapper.BookMapper;
import lt.techin.lectureone.model.request.ReactionAction;
import lt.techin.lectureone.model.request.RecordReactionRequest;
import lt.techin.lectureone.model.response.BookResponse;
import lt.techin.lectureone.model.response.UserReactionResponse;
import lt.techin.lectureone.persistence.AuthorRepository;
import lt.techin.lectureone.persistence.ReactionRepository;
import lt.techin.lectureone.persistence.model.AuthorRecord;
import lt.techin.lectureone.persistence.model.ReactionRecord;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static lt.techin.lectureone.model.request.ReactionAction.DISLIKE;
import static lt.techin.lectureone.model.request.ReactionAction.LIKE;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final IOpenLibraryClient openLibraryClient;
    private final AuthorRepository authorRepository;
    private final ReactionRepository reactionRepository;
    private final AsyncAuthorRepositoryService asyncAuthorRepositoryService;

    @SneakyThrows
    public BookResponse getAuthorsWorks(String author, int count) throws IOException, InterruptedException {

//        String olid = openLibraryClient.getAuthorOlid(author);

        String olid = "";

        Optional<AuthorRecord> optionalAuthorRecord = authorRepository.findById(sanitizeAuthorKey(author));

        if (optionalAuthorRecord.isPresent()) {
            olid = optionalAuthorRecord.get().getOlid();
            log.info("Got olid for {} from DB", author);
        }

        if (olid.isBlank()) {
            olid = openLibraryClient.getAuthorOlid(author);
            log.info("Got olid for {} from OpenLibrary", author);
        }

        if (olid.isBlank()) {
            throw new AuthorNotFoundException("Could not find author: " + author);
        }


        CompletableFuture<Map<String, String>> async1Future = asyncAuthorRepositoryService.async1();
        CompletableFuture<Map<String, String>> async2Future = asyncAuthorRepositoryService.async2();
        CompletableFuture<Map<String, String>> async3Future = asyncAuthorRepositoryService.async3();



        log.debug("started execution");

        CompletableFuture.anyOf(
                async1Future, async2Future, async3Future
        ).get();

        log.debug("{}", async1Future.getNow(Map.of("absent", "absent")));
        log.debug("{}", async2Future.getNow(Map.of("absent", "absent")));
        log.debug("{}", async3Future.getNow(Map.of("absent", "absent")));
        log.debug("finished execution");

        AuthorWorksResponse authorWorksResponse = openLibraryClient.getWorks(olid);

        return BookMapper.map(authorWorksResponse, author);
    }


    public void recordReaction(RecordReactionRequest recordReactionRequest) {

        ReactionRecord reactionRecord = new ReactionRecord(
                recordReactionRequest.getUuid(),
                recordReactionRequest.getOlid(),
                recordReactionRequest.getAction()
        );

        reactionRepository.save(reactionRecord);
    }

    public UserReactionResponse getUserReaction(String uuid, ReactionAction action) {
        List<ReactionRecord> reactionRecordList = reactionRepository.findByUuid(uuid);
        log.debug("got records form db for user {} : {}", uuid, reactionRecordList);

        UserReactionResponse userReactionResponse = new UserReactionResponse();

        try {
            switch (action) {
                case LIKE -> userReactionResponse.setLike(getOlidByAction(reactionRecordList, LIKE));

                case DISLIKE -> userReactionResponse.setDislike(getOlidByAction(reactionRecordList, DISLIKE));
            }
        } catch (NullPointerException e) {
            userReactionResponse.setLike(getOlidByAction(reactionRecordList, LIKE));
            userReactionResponse.setDislike(getOlidByAction(reactionRecordList, DISLIKE));
        }

        return userReactionResponse;
    }

    @CacheEvict("authors")
    public void evictAuthorFromCache(String author) {
        log.debug("[x] evicted {} from cache", author);
    }

    //    @Scheduled(cron = "0 * * * * *") // every 0th second of a minute
    @Scheduled(fixedRate = 15000, initialDelay = 30000) //rate in millis
    @CacheEvict(value = "authors", allEntries = true)
    public void evictAllCache() {
        log.debug("[x] evicted whole authors cache");
    }

    protected static String sanitizeAuthorKey(String author) {
        return author
                .toUpperCase(Locale.ROOT)
                .strip()
                .replaceAll(" ", "_");
    }

    protected List<String> getOlidByAction(List<ReactionRecord> reactionRecordList, ReactionAction action) {
        return reactionRecordList.stream()
                .filter((reactionRecord -> reactionRecord.getReactionAction().equalsIgnoreCase(action.name())))
                .map(ReactionRecord::getOlid)
                .toList();
    }

}
