package ru.inno.pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import ru.inno.model.User;
import ru.inno.utils.PropertiesManager;
import ru.inno.utils.UrnName;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;

@Accessors(chain = true)
public class LoginPage {
    private static final String loginPageUrl = PropertiesManager.getUrl(UrnName.LOGIN);
    private static final SelenideElement userNameField = $(By.cssSelector("#userName"));
    private static final SelenideElement passwordField = $(By.cssSelector("#password"));
    private static final SelenideElement loginButton = $(By.cssSelector("#login"));

    public static void openLoginPage() {
        Configuration.pageLoadTimeout = 60000;
        step("Открываем страницу логина", () -> {
            open(loginPageUrl);
            getWebDriver().manage().window().fullscreen();
        });
    }

    public static ProfilePage login(User user) {
        step("Ввводим логин и пароль пользователя, нажимаем кнопку [Login]", () -> {
            userNameField.setValue(user.getLogin());
            passwordField.setValue(user.getPassword());
            loginButton.click();
        });
        return new ProfilePage();
    }
}
