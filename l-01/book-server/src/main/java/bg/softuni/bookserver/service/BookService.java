package bg.softuni.bookserver.service;

import bg.softuni.bookserver.model.dto.BookDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookService {

    List<BookDTO> getAllBooks();

    Optional<BookDTO> finBookById(Long id);

    void deleteBookById(Long id);

    Long createBook(BookDTO bookDTO);

    Optional<BookDTO> updateBookById(Long bookId, BookDTO updateBookDto);

    Optional<BookDTO> partialUpdateBook(Long bookId, Map<String, Object> updates);
}
