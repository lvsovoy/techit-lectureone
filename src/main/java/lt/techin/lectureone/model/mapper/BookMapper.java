package lt.techin.lectureone.model.mapper;

import lombok.experimental.UtilityClass;
import lt.techin.lectureone.external.model.AuthorWorksResponse;
import lt.techin.lectureone.model.response.Book;
import lt.techin.lectureone.model.response.BookResponse;

import java.util.stream.Collectors;

@UtilityClass
public class BookMapper {

    public static BookResponse map(AuthorWorksResponse authorWorksResponse, String author) {
        return BookResponse.builder()
                .author(author)
                .books(authorWorksResponse.getEntries().stream()
                        .map((BookMapper::mapBook))
                        .collect(Collectors.toList()))
                .build();
    }

    public static Book mapBook(AuthorWorksResponse.WorkEntry workEntry) {

        Book.BookBuilder builder = Book.builder()
                .openLibraryKey(workEntry.getKey())
                .title(workEntry.getTitle())
                .description(workEntry.getDescription())
                .tags(workEntry.getSubjects());

        if (workEntry.getCreated() != null) {
            builder.publishDate(workEntry.getCreated().getValue());
        }

        return builder.build();
    }
}
