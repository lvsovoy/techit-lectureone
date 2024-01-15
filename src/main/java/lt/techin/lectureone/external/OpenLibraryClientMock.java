package lt.techin.lectureone.external;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lt.techin.lectureone.config.OpenLibraryClientConfig;
import lt.techin.lectureone.external.model.AuthorWorksResponse;
import lt.techin.lectureone.external.model.SearchResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static lt.techin.lectureone.util.RestUtil.initUriBuilder;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Profile("test")
@Component
@RequiredArgsConstructor
public class OpenLibraryClientMock implements IOpenLibraryClient{

    private final OpenLibraryClientConfig config;

    public String getAuthorOlid(String author) {
        log.debug("[!] Getting Author OLID from MOCK");

        return null;
    }

    public AuthorWorksResponse getWorks(String olid) {
        return null;
    }

}
