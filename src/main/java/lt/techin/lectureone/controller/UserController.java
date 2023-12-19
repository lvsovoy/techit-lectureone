package lt.techin.lectureone.controller;

import lombok.AllArgsConstructor;
import lt.techin.lectureone.model.request.User;
import lt.techin.lectureone.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController()
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private String something;

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
}
