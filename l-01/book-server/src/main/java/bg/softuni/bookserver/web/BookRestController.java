package bg.softuni.bookserver.web;

import bg.softuni.bookserver.model.dto.BookDTO;
import bg.softuni.bookserver.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
//@CrossOrigin(origins = {"http://localhost:8082/", "http://localhost:63342/"})
@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {

        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable(name = "id") Long bookId) {
        Optional<BookDTO> bookDTOOptional = bookService.finBookById(bookId);

        return bookDTOOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDTO> deleteBookById(@PathVariable(name = "id") Long bookId) {
        bookService.deleteBookById(bookId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO,
                                              BindingResult bindingResult,
                                              UriComponentsBuilder uriComponentsBuilder) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Long newBookId = bookService.createBook(bookDTO);
        return ResponseEntity.created(uriComponentsBuilder.path("/api/books/{id}").build(newBookId)).build();
    }

    @Operation(summary = "Update book by ID", description = "Updates an existing book with the given ID")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Book successfully updated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))
                    }),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Book not found or no changes made"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data or validation errors"
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBookById(
            @PathVariable("id") Long bookId,
            @Valid @RequestBody BookDTO updateBookDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<BookDTO> updatedBook = bookService.updateBookById(bookId, updateBookDto);

        return updatedBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookDTO> partialUpdateBook(@PathVariable("id") Long bookId,
                                                     @RequestBody Map<String, Object> updates) {

        Optional<BookDTO> updatedBook = bookService.partialUpdateBook(bookId, updates);

        return updatedBook
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
