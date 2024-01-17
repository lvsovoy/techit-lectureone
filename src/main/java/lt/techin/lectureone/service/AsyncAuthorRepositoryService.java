package lt.techin.lectureone.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lt.techin.lectureone.persistence.AuthorRepository;
import lt.techin.lectureone.persistence.model.AuthorRecord;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncAuthorRepositoryService {

    private final AuthorRepository authorRepository;

    @SneakyThrows
    @Async
    public CompletableFuture<Map<String,String>> async1() {
        Thread.sleep(7000);
        log.debug("returning future 1");
        return CompletableFuture.completedFuture(Map.of(
                "prop1","val1"
        ));
    }

    @SneakyThrows
    @Async
    public CompletableFuture<Map<String,String>> async2() {
        Thread.sleep(5000);
        log.debug("returning future 2");
        return CompletableFuture.completedFuture(Map.of(
                "prop2","val2"
        ));
    }

    @SneakyThrows
    @Async
    public CompletableFuture<Map<String,String>> async3() {
        Thread.sleep(10000);
        log.debug("returning future 3");
        return CompletableFuture.completedFuture(Map.of(
                "prop3","val3"
        ));
    }


}
