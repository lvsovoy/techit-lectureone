package lt.techin.lectureone.controller;

import lombok.AllArgsConstructor;
import lt.techin.lectureone.external.OpenLibraryClient;
import lt.techin.lectureone.model.mapper.BookMapper;
import lt.techin.lectureone.model.request.User;
import lt.techin.lectureone.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController()
//@RequestMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private OpenLibraryClient openLibraryClient;

    @GetMapping
    public Object getUser() {
        return "👌";
    }

    @GetMapping("/different")
    public Object getDifferntThing() {
        return "👀";
    }

    @PostMapping
    public Object returnWhateverWeReceived(
            @RequestParam String input
    ) {
        return input;
    }

    @PostMapping("/body")
    public User tryPassBody(
            @RequestBody User body
    ) {
        return userService.capitalizeName(body);
    }

    @GetMapping("/bookName")
    public Object getBookByName(
            @RequestParam String bookName
    ) throws IOException, InterruptedException {
        return BookMapper.map(openLibraryClient.lookupBookByName(bookName));
    }
}
