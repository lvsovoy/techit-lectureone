package lt.techin.lectureone.controller;

import lt.techin.lectureone.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private BookService bookServiceMock;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @Test
    void getWorksByAuthorHappyPath() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/book")
                        .param("author", "Terry Pratchett")
                        .param("count", "15")
                        .param("something", "1")
        ).andExpectAll(
                status().isOk()
        );

        verify(bookServiceMock, times(1)).getAuthorsWorks(stringCaptor.capture(), eq(15));

        assertEquals("Terry Pratchett", stringCaptor.getValue());
    }

    @ParameterizedTest
    @MethodSource("missingAuthorArgumentProvider")
    void getWorksByAuthorWithMissingAuthor(String author) throws Exception {
       Exception resolvedException =  mockMvc.perform(
                MockMvcRequestBuilders.get("/book")
                        .param("author", author)
                        .param("count", "15")
                        .param("something", "1")
        ).andExpectAll(
                status().is4xxClientError()
        ).andReturn()
                .getResolvedException();

       assertEquals("getWorksByAuthor.author: must not be blank", resolvedException.getMessage());

    }

    @ParameterizedTest
    @MethodSource("invalidCountArgumentProvider")
    void getWorksByAuthorWithInvalidCount(String count, int status, String expectedMessagePart) throws Exception {
        Exception resolvedException =  mockMvc.perform(
                        MockMvcRequestBuilders.get("/book")
                                .param("author", "Terry Pratchett")
                                .param("count", count)
                                .param("something", "1")
                ).andExpectAll(
                        status().is(status)
                ).andReturn()
                .getResolvedException();

        assertTrue(resolvedException.getMessage().contains(expectedMessagePart));

    }

    private static Stream<Arguments> missingAuthorArgumentProvider() {
        return Stream.of(
          Arguments.of("   "),
          Arguments.of("")
        );
    }

    private static Stream<Arguments> invalidCountArgumentProvider() {
        return Stream.of(
                Arguments.of("-1", 400, "count: must be greater than or equal to 1"),
                Arguments.of("16", 400, "count: must be less than or equal to 15"),
                Arguments.of("sdfs", 500, "Failed to convert value of type 'java.lang.String' to required type 'int'")
        );
    }





}