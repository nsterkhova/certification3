package ru.inno.data;

import ru.inno.model.Book;

import java.lang.reflect.Array;

public class CommonCollectionOfIsbns {
    public static final Book[] SIX_BOOKS = new Book[]{
            new Book("9781491950296"),
            new Book("9781491904244"),
            new Book("9781449365035"),
            new Book("9781449331818"),
            new Book("9781449325862"),
            new Book("9781449337711")
    };

    public static final Book[] TWO_BOOKS = new Book[]{
            new Book("9781449325862"),
            new Book("9781449337711")
    };
}
