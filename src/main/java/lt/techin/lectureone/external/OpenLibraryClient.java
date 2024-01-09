package lt.techin.lectureone.external;


//import jakarta.ws.rs.core.UriBuilder;

import lt.techin.lectureone.external.model.AuthorWorksResponse;
import lt.techin.lectureone.external.model.SearchResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;

import static lt.techin.lectureone.util.RestUtil.initUriBuilder;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class OpenLibraryClient {

    private RestClient restClient = RestClient.create();

    private String baseURI = "https://openlibrary.org"; //TODO extract to properties
    private String searchEndpoint = "/search.json";
    private String searchAuthorsEndpoint = "/search/authors.json";
    private String getAuthorsWorks = "/authors/{olid}/works.json";

    public String getAuthorOlid(String author) {
        SearchResponse searchResponse = restClient.get()
                .uri(initUriBuilder(baseURI)
                        .path(searchAuthorsEndpoint)
                        .queryParam("q", author)
                        .build())
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(SearchResponse.class);

        return searchResponse.getDocs()
                .getFirst()// todo fix null safety
                .getKey();
    }

    public AuthorWorksResponse getWorks(String olid) throws IOException, InterruptedException {
        return restClient.get()
                .uri(initUriBuilder(baseURI)
                        .path(getAuthorsWorks)
                        .build(olid))
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(AuthorWorksResponse.class);
    }

}
