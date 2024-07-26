package ru.netology.page;
import com.codeborne.selenide.SelenideElement;
import ru.netology.dto.VerificationCode;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement notification = $("data-test-id=error-notification");

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
    }

    public void verifyErrorNotification(VerificationCode verificationCode) {
        notification.shouldHave(exactText("Ошибка! Неверно указан код! Попробуйте еще раз.")).shouldBe(visible);
    }
}
