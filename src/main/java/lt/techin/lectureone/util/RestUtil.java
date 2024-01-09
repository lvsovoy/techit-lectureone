package lt.techin.lectureone.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestUtil {

    public static UriBuilder initUriBuilder(String baseUri) {
        return new DefaultUriBuilderFactory().uriString(baseUri);
    }
}
