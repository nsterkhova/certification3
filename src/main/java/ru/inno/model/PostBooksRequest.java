package ru.inno.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostBooksRequest {
    private String userId;
    private Book[] collectionOfIsbns;
}
