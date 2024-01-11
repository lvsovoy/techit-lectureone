package lt.techin.lectureone.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@Builder
public class Book {

    private String openLibraryKey;
    private String title;
    private String publishDate;
    private String description;
    private List<String> tags;
}
