package bg.softuni.bookserver.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookDTO(Long id,
                      @NotBlank String title,
                      @NotBlank String isbn,
                      @NotNull AuthorDTO author) {
}
