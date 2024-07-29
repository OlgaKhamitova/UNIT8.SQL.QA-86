package ru.netology.page;
import com.codeborne.selenide.SelenideElement;
import ru.netology.dto.VerificationCode;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement notification = $("[data-test-id=error-notification]");
    private final SelenideElement notificationContent = $("[data-test-id=error-notification] .notification__content");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public DashboardPage validVerify(VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashboardPage();
    }

    public void invalidVerify(VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        verifyMustHaveWrongCodeNotification();
    }

    public void verifyMustHaveWrongCodeNotification() {
        if (!notificationContent.getText().equals("Ошибка! Неверно указан код! Попробуйте ещё раз.")) {
            throw new IllegalArgumentException("Отсутствует ошибка неверного код авторизации");
        }
    }
}
