package ru.netology.page;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.dto.AuthInfo;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement button = $("[data-test-id=action-login]");
    private final SelenideElement notification = $("[data-test-id=error-notification]");

    public VerificationPage validLogin(AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        button.click();
        return new VerificationPage();
    }

    public void invalidLogin(AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        button.click();
        cleanUpField(loginField);
        cleanUpField(passwordField);
    }

    public void verifyErrorNotification(AuthInfo info) {
        notification.shouldHave(exactText("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    private void cleanUpField(SelenideElement field) {
        field.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        field.sendKeys(Keys.BACK_SPACE);
    }
}
