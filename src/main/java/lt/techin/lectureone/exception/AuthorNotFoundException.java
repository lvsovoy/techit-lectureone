package lt.techin.lectureone.exception;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
