package lt.techin.lectureone.external;


import jakarta.ws.rs.core.UriBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class OpenLibraryClient {

    private HttpClient httpClient = HttpClient.newHttpClient();
    private String baseURI = "https://openlibrary.org/";
    private String searchEndpoint = "search.json";

    public Object lookupBookByName(String bookName) throws IOException, InterruptedException { //TODO use in service + create an endpoint

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(UriBuilder.fromPath(baseURI+searchEndpoint)
                                .queryParam("q", bookName)
                                .build())
                .build();

        return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()); //TODO map body to the model we created
    }

}
