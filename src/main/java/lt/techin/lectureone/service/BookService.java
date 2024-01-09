package lt.techin.lectureone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.techin.lectureone.external.OpenLibraryClient;
import lt.techin.lectureone.external.model.AuthorWorksResponse;
import lt.techin.lectureone.model.mapper.BookMapper;
import lt.techin.lectureone.model.response.BookResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final OpenLibraryClient openLibraryClient;

    public BookResponse getAuthorsWorks(String author, int count) throws IOException, InterruptedException {

        String olid = openLibraryClient.getAuthorOlid(author);
        AuthorWorksResponse authorWorksResponse = openLibraryClient.getWorks(olid);

        return BookMapper.map(authorWorksResponse, author);
    }
}
