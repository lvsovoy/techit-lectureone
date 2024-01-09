package lt.techin.lectureone.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.techin.lectureone.model.response.BookResponse;
import lt.techin.lectureone.service.BookService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
@Slf4j
@Validated
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private static final String EMAIL_REGEX = "[^i*&2@]";

    @GetMapping
    public BookResponse getWorksByAuthor(
            @RequestParam @NotBlank String author,
            @RequestParam @Max(15) @Min(1) int count,
            @RequestParam @Pattern(regexp = EMAIL_REGEX) String something
    ) throws IOException, InterruptedException {
        log.debug("Called get author works endpoint with author: {}", author);

        return bookService.getAuthorsWorks(author, count);
    }
}
