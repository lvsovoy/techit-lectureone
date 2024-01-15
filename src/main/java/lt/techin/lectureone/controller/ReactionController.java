package lt.techin.lectureone.controller;

import io.swagger.v3.oas.annotations.OpenAPI31;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.techin.lectureone.model.request.ReactionAction;
import lt.techin.lectureone.model.request.RecordReactionRequest;
import lt.techin.lectureone.model.response.ErrorResponse;
import lt.techin.lectureone.model.response.UserReactionResponse;
import lt.techin.lectureone.service.BookService;
import org.hibernate.validator.constraints.UUID;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Slf4j
@Validated
@RestController
@RequestMapping("/reaction")
@RequiredArgsConstructor
public class ReactionController {

    private final BookService bookService;

    @PostMapping
    public void recordReaction(
            @RequestBody @Valid RecordReactionRequest recordReactionRequest
    ) {
        log.debug("Received POST /react with body {}", recordReactionRequest);
        bookService.recordReaction(recordReactionRequest);
    }

    @GetMapping
    public UserReactionResponse getUserReactions(
            @RequestParam @UUID String uuid,
            @RequestParam(required = false) @Pattern(regexp = "like|dislike/i") String action
    ) {
        return bookService.getUserReaction(uuid, Enum.valueOf(ReactionAction.class, action.toUpperCase(Locale.ROOT).trim()));
    }

}
