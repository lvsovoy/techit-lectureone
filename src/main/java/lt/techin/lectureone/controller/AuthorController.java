package lt.techin.lectureone.controller;

import lombok.RequiredArgsConstructor;
import lt.techin.lectureone.persistence.AuthorRepository;
import lt.techin.lectureone.persistence.model.AuthorRecord;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository repository;

    @GetMapping
    public List<AuthorRecord> getAllRecords() {
        return repository.findAll();
    }

    @DeleteMapping
    public void deleteRecord(
            @RequestParam String id) {
        repository.deleteById(id);
    }

    @PostMapping
    public void editRecord(
            @RequestBody AuthorRecord authorRecord
    ) {
        repository.save(authorRecord);
    }
}
