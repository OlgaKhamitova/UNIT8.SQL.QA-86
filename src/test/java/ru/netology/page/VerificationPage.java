package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.dto.VerificationCode;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
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
        notification.shouldBe(visible, Duration.ofSeconds(10));
        notificationContent.shouldHave(exactText("Ошибка! Неверно указан код! Попробуйте ещё раз."));
    }
}
