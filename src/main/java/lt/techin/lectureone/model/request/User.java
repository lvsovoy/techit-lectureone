package lt.techin.lectureone.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder(setterPrefix = "with")
@ToString
@Getter
public class User {

    private int id;

    @Setter
    private String name;

    private int age;

}
