package lt.techin.lectureone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.techin.lectureone.exception.AuthorNotFoundException;
import lt.techin.lectureone.external.OpenLibraryClient;
import lt.techin.lectureone.external.model.AuthorWorksResponse;
import lt.techin.lectureone.model.mapper.BookMapper;
import lt.techin.lectureone.model.response.BookResponse;
import lt.techin.lectureone.persistence.AuthorRepository;
import lt.techin.lectureone.persistence.model.AuthorRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final OpenLibraryClient openLibraryClient;
    private final AuthorRepository authorRepository;

    public BookResponse getAuthorsWorks(String author, int count) throws IOException, InterruptedException {

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

        authorRepository.save(new AuthorRecord(sanitizeAuthorKey(author), olid));

        AuthorWorksResponse authorWorksResponse = openLibraryClient.getWorks(olid);

        return BookMapper.map(authorWorksResponse, author);
    }

    protected static String sanitizeAuthorKey(String author) {
        return author
                .toUpperCase(Locale.ROOT)
                .strip()
                .replaceAll(" ", "_");
    }


}
