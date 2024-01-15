package lt.techin.lectureone.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("app.external.open-library-client")
public class OpenLibraryClientConfig {

    private String base;
    private Endpoints endpoints;

    @Getter
    @Setter
    public static class Endpoints {

        private String searchAuthors;
        private String getAuthorsWorks;
    }
}
