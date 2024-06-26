package ru.inno.helpers;

import com.alibaba.fastjson2.JSON;
import io.restassured.http.ContentType;
import ru.inno.model.Book;
import ru.inno.model.PostBooksRequest;
import ru.inno.model.User;
import ru.inno.utils.PropertiesManager;
import ru.inno.utils.UrnName;

import static io.restassured.RestAssured.given;

public class BooksHelper {
    public static void addBooksToUser(User user, Book[] collectionOfIsbns) {
        String requestBody = JSON.toJSONString (new PostBooksRequest(user.getUserId(), collectionOfIsbns));
        given()
                .log().all()
                .auth()
                .preemptive()
                .basic(user.getLogin(), user.getPassword())
                .contentType(ContentType.JSON)
                .body(requestBody)
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .post(PropertiesManager.getUrl(UrnName.API_BOOKS))
                .then()
                .statusCode(201);
    }
}
