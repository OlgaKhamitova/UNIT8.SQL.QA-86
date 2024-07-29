package ru.netology.page;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.dto.AuthInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement button = $("[data-test-id=action-login]");
    private final SelenideElement notification = $("[data-test-id=error-notification]");
    private final SelenideElement notificationContent = $("[data-test-id=error-notification] .notification__content");

    public VerificationPage validLogin(AuthInfo authInfo) {
        login(authInfo);
        return new VerificationPage();
    }

    public void invalidLogin(AuthInfo authInfo) {
        login(authInfo);
        verifyMustHaveWrongCredentialsNotification();
        cleanUpField(loginField);
        cleanUpField(passwordField);
    }

    public void blockedUserLogin(AuthInfo authInfo) {
        login(authInfo);
        verifyMustHaveUserBlockedNotification();
        cleanUpField(loginField);
        cleanUpField(passwordField);
    }

    public void verifyMustHaveWrongCredentialsNotification() {
        notification.shouldBe(visible, Duration.ofSeconds(10));
        if (!notificationContent.getText().equals("Ошибка! Неверно указан логин или пароль")) {
            throw new IllegalArgumentException("Отсутствует ошибка неверного логина и пароля");
        }
    }

    public void verifyMustHaveUserBlockedNotification() {
        notification.shouldBe(visible, Duration.ofSeconds(10));
        if (!notificationContent.getText().equals("Ошибка! Пользователь заблокирован")) {
            throw new IllegalArgumentException("Отсутстует ошибка блокировки пользователя");
        }
    }

    private void cleanUpField(SelenideElement field) {
        field.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        field.sendKeys(Keys.BACK_SPACE);
    }

    private void login(AuthInfo authInfo) {
        loginField.setValue(authInfo.getLogin());
        passwordField.setValue(authInfo.getPassword());
        button.click();
    }
}
