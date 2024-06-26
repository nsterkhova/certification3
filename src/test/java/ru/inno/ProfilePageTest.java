package ru.inno;

import com.codeborne.selenide.Browsers;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.google.common.io.Files;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.Alert;
import ru.inno.data.CommonCollectionOfIsbns;
import ru.inno.data.CommonUsers;
import ru.inno.helpers.BooksHelper;
import ru.inno.model.User;
import ru.inno.pages.LoginPage;
import ru.inno.pages.ProfilePage;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.switchTo;
import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Epic("Профиль пользователя")
@Feature("Список книг")
@Owner("Natalia Sterkhova")
@DisplayName("Проверка отображения книг в профиле")
@ExtendWith({ScreenShooterExtension.class})
public class ProfilePageTest {
    private static final User defaultUser = CommonUsers.DEFAULT_USER;
    private static final User userWithSixBooks = CommonUsers.USER_WITH_SIX_BOOKS;
    private static final User userWithTwoBooks = CommonUsers.USER_WITH_TWO_BOOKS;

    @RegisterExtension
    static ScreenShooterExtension screenshotEmAll = new ScreenShooterExtension(true).to("target/screenshots");

    @BeforeAll
    public static void SetUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        Configuration.browser = Browsers.CHROME;
    }

    @Attachment(type = "image/png")
    public byte[] screenshot() throws IOException {
        File screenshot = Screenshots.getLastScreenshot();
        return screenshot == null ? null : Files.toByteArray(screenshot);
    }

    @AfterEach
    public void TearDown() throws IOException {
        screenshot();
        step("LogOut", () -> {
            ProfilePage profilePage = new ProfilePage();
            profilePage.logOut();
        });
    }

    @Test
    @Story("Отображение пустого списка")
    @DisplayName("Проверка дефолтного состояния профиля (список книг пуст)")
    @Description("По умолчанию таблица книг нового пользователя пустая")
    @Severity(SeverityLevel.MINOR)
    public void getEmptyTableTest() {
        new LoginPage();
        ProfilePage profilePage = LoginPage.login(defaultUser);
        checkEmptyTable(profilePage);
    }

    @Test
    @Story("Отображение добавленных книг")
    @DisplayName("Проверка отображения добавленных книг в профиле")
    @Description("Выбранные в магазине книги отображаются в профле")
    @Severity(SeverityLevel.CRITICAL)
    public void getSixBooksTest() {
        new LoginPage();
        ProfilePage profilePage = LoginPage.login(userWithSixBooks);
        profilePage.setMaxPageSize();
        checkTableSize(profilePage, 6);
    }

    @Test
    @Stories({@Story("Отображение добавленных книг"), @Story("Отображение пустого списка")})
    @DisplayName("Проверка очистки списка книг нажатием кнопки [Delete All Books]")
    @Description("После нажатия на кнопку [Delete All Books] таблица книг в профиле сбрасывается до дефолтного состояния (становится пустой)")
    @Severity(SeverityLevel.NORMAL)
    public void deleteAllBooksTest() {
        BooksHelper.addBooksToUser(userWithTwoBooks, CommonCollectionOfIsbns.TWO_BOOKS);
        new LoginPage();
        ProfilePage profilePage = LoginPage.login(userWithTwoBooks);
        checkTableSize(profilePage, 2);
        profilePage.deleteAllBooks();
        checkAllertText();
        checkEmptyTable(profilePage);
    }

    private void checkEmptyTable(ProfilePage profilePage) {
        step("Проверяем отображение пустой таблицы книг", () ->
                assertThat("Таблица книг в профиле - пустая", profilePage.getEmptyTableText(), is("No rows found")));
    }

    private void checkTableSize(ProfilePage profilePage, int size) {
        step("Получаем список книг в таблице и проверяем количество", () ->
                assertThat("Таблица книг содержит 2 книги", profilePage.getBooks().size(), is(size)));
    }

    private void checkAllertText() {
        step("Проверяем отображение уведомления об успешном удалении книг", () -> {
            Alert alert = switchTo().alert();
            assertThat("Отображается нотификация об успешном удалении книг", alert.getText(), is("All Books deleted."));
        });
    }
}
