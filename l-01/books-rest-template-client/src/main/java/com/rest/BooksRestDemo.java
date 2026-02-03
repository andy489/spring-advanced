package com.rest;

import com.rest.model.dto.BookDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BooksRestDemo implements CommandLineRunner {

    private static final String API_URL = "http://localhost:8080/api/books";


    private static final Logger LOGGER = LoggerFactory.getLogger(BooksRestDemo.API_URL);

    private final RestTemplate restTemplate;

    @Autowired
    public BooksRestDemo(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void run(String... args) {

        // String rawResponse = restTemplate.getForObject(API_URL, String.class);
        // LOGGER.info("Raw API response: {}", rawResponse);

        ResponseEntity<BookDto[]> allBooksResponse = restTemplate.getForEntity(API_URL, BookDto[].class);

        if (allBooksResponse.hasBody()) {
            BookDto[] books = allBooksResponse.getBody();
            if (books != null) {
                for (BookDto book : books) {
                    LOGGER.info("Retrieved a book: {}", book);
                }
            } else {
                LOGGER.info("No books retrieved");
            }
        }
    }
}
