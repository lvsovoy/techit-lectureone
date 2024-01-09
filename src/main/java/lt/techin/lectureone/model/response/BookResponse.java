package lt.techin.lectureone.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BookResponse {

    private String author;
    private List<Book> books;

}
