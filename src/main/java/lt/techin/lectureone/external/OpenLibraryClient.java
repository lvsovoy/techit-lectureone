package lt.techin.lectureone.external;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.techin.lectureone.config.OpenLibraryClientConfig;
import lt.techin.lectureone.external.model.AuthorWorksResponse;
import lt.techin.lectureone.external.model.SearchResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static lt.techin.lectureone.util.RestUtil.initUriBuilder;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Profile("default")
@Component
@Slf4j
@RequiredArgsConstructor
public class OpenLibraryClient implements IOpenLibraryClient {

    private RestClient restClient = RestClient.create();

    private final OpenLibraryClientConfig config;

    @Cacheable("authors")
    public String getAuthorOlid(String author) {
        log.debug("[!] Getting Author OLID from OpenLibrary");
        SearchResponse searchResponse = restClient.get()
                .uri(initUriBuilder(config.getBase())
                        .path(config.getEndpoints().getSearchAuthors())
                        .queryParam("q", author)
                        .build())
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(SearchResponse.class);

        if (searchResponse != null && (!searchResponse.getDocs().isEmpty())) {
                return searchResponse.getDocs().getFirst().getKey();
        }

        return "";
    }

    public AuthorWorksResponse getWorks(String olid) {
        log.debug("[!] Getting Author works from OpenLibrary");

        return restClient.get()
                .uri(initUriBuilder(config.getBase())
                        .path(config.getEndpoints().getGetAuthorsWorks())
                        .build(olid))
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(AuthorWorksResponse.class);
    }

}
