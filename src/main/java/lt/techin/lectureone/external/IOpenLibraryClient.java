package lt.techin.lectureone.external;

import lt.techin.lectureone.external.model.AuthorWorksResponse;

public interface IOpenLibraryClient {

    public String getAuthorOlid(String author);

    public AuthorWorksResponse getWorks(String olid);

}
