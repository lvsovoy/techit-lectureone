package lt.techin.lectureone.service;

import lt.techin.lectureone.model.request.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    public User capitalizeName(User user) {

        System.out.println(user);
        return user;
    }


    public static void main(String[] args) {
        List<User> userList = List.of(
                User.builder()
                        .withId(1)
                        .build(),
                User.builder()
                        .withId(2)
                        .build(),
                User.builder()
                        .withId(3)
                        .build(),
                User.builder()
                        .withId(4)
                        .build());

        System.out.println(
                userList.stream()
                        .filter((currentlyProcessedUser) -> currentlyProcessedUser.getId() % 2 == 0)
                        .peek((currentlyProcessedUser) -> currentlyProcessedUser.setName("Name" + currentlyProcessedUser.getId()))
                        .collect(Collectors.toList())
        );

    }

}
