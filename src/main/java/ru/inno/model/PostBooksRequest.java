package ru.inno.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class PostBooksRequest {
    private String userId;
    private Book[] collectionOfIsbns;
}
