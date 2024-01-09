package lt.techin.lectureone.service;

import lt.techin.lectureone.external.OpenLibraryClient;
import lt.techin.lectureone.external.model.AuthorWorksResponse;
import lt.techin.lectureone.model.response.Book;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

class BookServiceTest {

    BookService bookService;

    @Mock
    OpenLibraryClient openLibraryClientMock;

    @Test
    void getAuthorsWorks() throws IOException, InterruptedException {
        Mockito.when(openLibraryClientMock.getAuthorOlid(anyString())).thenReturn("OLID");
        Mockito.when(openLibraryClientMock.getWorks(eq("OLID"))).thenReturn(generateAuthorWorksResponse());


//        BookResponse actual = bookService.getAuthorsWorks("testValue");

        Book actual = Book.builder()
                .title("Title")
                .description("Description")
                .publishDate("Publish Date")
                .build();

        assertEquals("Description", actual.getDescription());
        assertEquals("Publish Date", actual.getPublishDate());
        assertEquals("Title", actual.getTitle());

    }

    private AuthorWorksResponse generateAuthorWorksResponse() {

        return new AuthorWorksResponse() {{
            setEntries(
                    List.of(
                            new WorkEntry() {{
                                setDescription("Description 1");
                                setCreated(
                                        new CreatedDate() {{
                                            setValue("Publish Date 1");
                                        }}
                                );
                                setTitle("Title 1");
                            }},

                            new WorkEntry() {{
                                setDescription("Description 1");
                                setCreated(
                                        new CreatedDate() {{
                                            setValue("Publish Date 1");
                                        }}
                                );
                                setTitle("Title 1");
                            }}
                    ));
        }};
    }

}