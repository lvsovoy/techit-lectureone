package lt.techin.lectureone;

import lt.techin.lectureone.controller.BookController;
import lt.techin.lectureone.external.OpenLibraryClient;
import lt.techin.lectureone.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LectureoneApplicationTests {

	@Autowired
	BookService bookService;

	@Autowired
	BookController bookController;

	@Autowired
	OpenLibraryClient openLibraryClient;

	@Test
	void contextLoads() {
		assertNotNull(bookService);
		assertNotNull(bookController);
		assertNotNull(openLibraryClient);
	}

}
