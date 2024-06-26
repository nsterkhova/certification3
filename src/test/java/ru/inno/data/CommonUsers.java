package ru.inno.data;

import ru.inno.model.User;

public class CommonUsers {
    /**
     * Пользователь с пустой корзиной
     */
    public static final User DEFAULT_USER = new User("Test01", "P@ssw0rd", "9f1eecb2-a656-4c29-aef4-9a2eaf7fc5d6");

    /**
     * Пользователь с шестью добавленными книгами
     */
    public static final User USER_WITH_SIX_BOOKS = new User("newUser", "P@ssw0rd", "aa00f37c-a4a5-4590-9105-59c418d02705");

    /**
     * Пользователь для теста на проверку добавления и удаления двух книг
     */
    public static final User USER_WITH_TWO_BOOKS = new User("twoBooksUser", "P@ssw0rd", "40787a86-b194-4115-9c68-4d871b57a372");
}
