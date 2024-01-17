package lt.techin.lectureone.controller;

import lombok.RequiredArgsConstructor;
import lt.techin.lectureone.config.SecretConfig;
import lt.techin.lectureone.exception.UnauthorizedException;
import lt.techin.lectureone.persistence.AuthorRepository;
import lt.techin.lectureone.persistence.model.AuthorRecord;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository repository;
    private final SecretConfig secretConfig;

    @GetMapping
    public List<AuthorRecord> getAllRecords() {
        return repository.findAll();
    }

    @DeleteMapping
    public void deleteRecord(
            @RequestHeader String secret, //BAse64 encoded string
            @RequestParam String id) {

        if (! new String(Base64.getDecoder().decode(secret)).equals(secretConfig.getAuthorSecret()))
        {
            throw new UnauthorizedException("secret header value does not match expected");
        }

        repository.deleteById(id);
    }

    @PostMapping
    public void editRecord(
            @RequestBody AuthorRecord authorRecord
    ) {
        repository.save(authorRecord);
    }
}
