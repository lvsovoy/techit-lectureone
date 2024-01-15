package lt.techin.lectureone;

import lt.techin.lectureone.config.OpenLibraryClientConfig;
import lt.techin.lectureone.controller.BookController;
import lt.techin.lectureone.external.OpenLibraryClient;
import lt.techin.lectureone.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@ActiveProfiles("test")
@SpringBootTest
class LectureoneApplicationTests {

	@Autowired
	BookService bookService;

	@Autowired
	BookController bookController;

	@Autowired
	OpenLibraryClient openLibraryClient;

	@Autowired
	OpenLibraryClientConfig openLibraryClientConfig;

	@Test
	void contextLoads() {
		assertNotNull(bookService);
		assertNotNull(bookController);
		assertNotNull(openLibraryClient);
	}

	@Test
	void testOLConfig() {
		assertTrue(openLibraryClientConfig.getBase().contains("test"));
	}

}
