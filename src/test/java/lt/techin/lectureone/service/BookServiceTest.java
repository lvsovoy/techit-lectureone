package lt.techin.lectureone.service;

import lombok.SneakyThrows;
import lt.techin.lectureone.external.OpenLibraryClient;
import lt.techin.lectureone.external.model.AuthorWorksResponse;
import lt.techin.lectureone.model.response.Book;
import lt.techin.lectureone.model.response.BookResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private OpenLibraryClient openLibraryClientMock;

    @Captor
    private ArgumentCaptor<String> captor;

    @SneakyThrows
    @Test
    void getAuthorsWorks() throws IOException, InterruptedException {
        Mockito.when(openLibraryClientMock.getAuthorOlid(anyString())).thenReturn("OLID");
        Mockito.when(openLibraryClientMock.getWorks("OLID")).thenReturn(generateAuthorWorksResponse());

        BookResponse actual = bookService.getAuthorsWorks("testValue",1);

        Mockito.verify(openLibraryClientMock, Mockito.times(1)).getAuthorOlid(anyString());
        Mockito.verify(openLibraryClientMock, Mockito.times(1)).getWorks(captor.capture());

        assertEquals("OLID", captor.getValue());

        assertEquals("testValue", actual.getAuthor());

        Book firstEntry = actual.getBooks().get(0);

        assertEquals("Description 1", firstEntry.getDescription());
        assertEquals("Publish Date 1", firstEntry.getPublishDate());
        assertEquals("Title 1", firstEntry.getTitle());
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
                                setDescription("Description 2");
                                setCreated(
                                        new CreatedDate() {{
                                            setValue("Publish Date 2");
                                        }}
                                );
                                setTitle("Title 2");
                            }}
                    ));
        }};
    }

}