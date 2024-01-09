package lt.techin.lectureone.external.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthorWorksResponse {

    private List<WorkEntry> entries;

    @Getter
    @Setter
    public static class WorkEntry {
        private String title;
        private CreatedDate created;
        private String key;
        private String description;
        private List<String> subjects;
    }

    @Getter
    @Setter
    public static class CreatedDate {
        private String value;
    }
}
