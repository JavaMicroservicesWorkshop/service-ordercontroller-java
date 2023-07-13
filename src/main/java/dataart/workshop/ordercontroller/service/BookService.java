package dataart.workshop.ordercontroller.service;

import dataart.workshop.ordercontroller.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class BookService {

    private static final Logger logger = Logger.getLogger(BookService.class.getName());
    private static final String BOOK_MANAGER_URL = "http://localhost:8082/api/v1/books/";

    private final RestTemplate restTemplate;

    public BookDto getBookDtoFromBookManager(Long bookId) {
        String resourceUrl = BOOK_MANAGER_URL + bookId;
        ResponseEntity<BookDto> response = restTemplate.getForEntity(resourceUrl, BookDto.class);
        BookDto bookDto = response.getBody();
        Objects.requireNonNull(bookDto).setBookId(bookId);

        logger.info("Got book for order from Book Manager service");

        return bookDto;
    }
}
