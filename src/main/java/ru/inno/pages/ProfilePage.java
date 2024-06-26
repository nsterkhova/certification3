package ru.inno.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.qameta.allure.Allure.step;

public class ProfilePage {
    private static final SelenideElement pageSizeSelect = $(By.cssSelector("span.select-wrap.-pageSizeOptions"));
    private static final SelenideElement deleteAllBooksButton = $(byText("Delete All Books"));
    private static final SelenideElement logOutButton = $(byText("Log out"));

    //private static final String emptyTableContent = $(By.cssSelector(".rt-noData")).getText();

    //private static final ElementsCollection Books = $$(By.cssSelector("div.rt-td img"));

    public ProfilePage() {
    }

    public static void waitForVisible(SelenideElement element) {
        element.shouldBe(visible);
    }

    public String getEmptyTableText() {
        return $(By.cssSelector(".rt-noData")).getText();
    }

    public void setMaxPageSize() {
        step("Устанавливаем отображение количество книг в таблице = 10", () -> {
            pageSizeSelect.scrollIntoView(true);
            pageSizeSelect.click();
            $(byText("10 rows")).click();
        });
    }

    public ElementsCollection getBooks() {
        waitForVisible($(By.cssSelector("div.rt-td img")));
        return $$(By.cssSelector("div.rt-td img"));
    }

    public void deleteAllBooks() {
        step("Нажимаем кнопку [Delete All Books] и подтверждаем выполнение удаления всех книг", () -> {
            deleteAllBooksButton.scrollIntoView(true);
            deleteAllBooksButton.click();
            SelenideElement okButton = $(By.cssSelector("#closeSmallModal-ok"));
            waitForVisible(okButton);
            okButton.click();
        });
    }

    public void logOut() {
        logOutButton.click();
    }
}
