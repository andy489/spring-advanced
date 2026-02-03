package com.rest.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record BookDto(Long id, String title, String isbn, @JsonAlias(value = "author") AuthorDTO authorDTO) {
}
