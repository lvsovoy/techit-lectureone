package lt.techin.lectureone.external;


//import jakarta.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lt.techin.lectureone.external.model.BookSearchResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class OpenLibraryClient {

    private HttpClient httpClient = HttpClient.newHttpClient();

    private ObjectMapper mapper = JsonMapper.builder()
            .configure(JsonGenerator.Feature.IGNORE_UNKNOWN,true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
            .build();
    private String baseURI = "openlibrary.org";
    private String searchEndpoint = "/search.json";

    public BookSearchResponse lookupBookByName(String bookName) throws IOException, InterruptedException { //TODO use in service + create an endpoint
        System.out.println("calling openlibrary api...");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new DefaultUriBuilderFactory().builder()
                        .scheme("https")
                        .host(baseURI)
                        .path(searchEndpoint)
                        .queryParam("q", bookName)
                        .build())
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .build();


        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        //TODO map body to the model we created

       BookSearchResponse response = mapper.readValue(httpResponse.body(), BookSearchResponse.class);

        return response;
    }

}
