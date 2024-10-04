package com.amoura.bookapi.dto;


import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class BookInput {
    private String title;
    private String author;
    private int publicationYear;

}
