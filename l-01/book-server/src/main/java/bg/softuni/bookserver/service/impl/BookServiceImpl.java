package bg.softuni.bookserver.service.impl;

import bg.softuni.bookserver.model.dto.BookDTO;
import bg.softuni.bookserver.model.entity.AuthorEntity;
import bg.softuni.bookserver.model.entity.BookEntity;
import bg.softuni.bookserver.repo.AuthorRepository;
import bg.softuni.bookserver.repo.BookRepository;
import bg.softuni.bookserver.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private final ObjectMapper om;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           ObjectMapper om) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.om = om;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookEntity -> om.convertValue(bookEntity, BookDTO.class))
                .toList();
    }

    @Override
    public Optional<BookDTO> finBookById(Long id) {
        return bookRepository
                .findById(id)
                .map(bookEntity -> om.convertValue(bookEntity, BookDTO.class));
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Long createBook(BookDTO bookDTO) {

        Optional<AuthorEntity> author = authorRepository
                .findByName(bookDTO.author().name());

        BookEntity newBook = new BookEntity()
                .setTitle(bookDTO.title())
                .setIsbn(bookDTO.isbn())
                .setAuthor(author.orElseGet(() -> authorRepository
                        .save(new AuthorEntity()
                                .setName(bookDTO.author().name()))));

        newBook = bookRepository.save(newBook);

        return newBook.getId();
    }

    @Override
    @Transactional
    public Optional<BookDTO> updateBookById(Long bookId, BookDTO updateBookDto) {
        Optional<BookEntity> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isEmpty()) {
            return Optional.empty();
        }

        BookEntity bookEntity = bookOptional.get();

        String authorName = updateBookDto.author().name();
        Optional<AuthorEntity> authorOpt = authorRepository.findByName(authorName);

        bookEntity.setIsbn(updateBookDto.isbn())
                .setTitle(updateBookDto.title())
                .setAuthor(authorOpt.orElseGet(() -> authorRepository
                        .save(new AuthorEntity()
                                .setName(updateBookDto.author().name()))));

        BookDTO updatedDto = om.convertValue(bookEntity, BookDTO.class);

        return Optional.of(updatedDto);
    }

    @Override
    @Transactional
    public Optional<BookDTO> partialUpdateBook(Long bookId, Map<String, Object> updates) {
        Optional<BookEntity> bookOpt = bookRepository.findById(bookId);

        if (bookOpt.isEmpty()) {
            return Optional.empty();
        }

        BookEntity bookEntity = bookOpt.get();

        updates.forEach((key, value) -> {
            switch (key) {
                case "title":
                    bookEntity.setTitle((String) value);
                    break;
                case "isbn":
                    bookEntity.setIsbn((String) value);
                    break;
                case "author":
                    String authorName;

                    if (value instanceof Map<?, ?> authorMap) {
                        if (authorMap.containsKey("name")) {
                            authorName = (String) authorMap.get("name");

                        } else {
                            authorName = null;
                        }
                    } else if (value instanceof String) {
                        authorName = (String) value;
                    } else {
                        authorName = null;
                    }

                    Optional<AuthorEntity> authorOpt = authorRepository.findByName(authorName);
                    bookEntity.setAuthor(authorOpt.orElseGet(() -> authorRepository
                            .save(new AuthorEntity().setName(authorName))));

                    break;
            }
        });

        BookDTO bookDTO = om.convertValue(bookEntity, BookDTO.class);

        return Optional.of(bookDTO);
    }
}
