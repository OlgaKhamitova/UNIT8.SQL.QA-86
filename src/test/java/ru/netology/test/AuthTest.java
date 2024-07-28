package ru.netology.test;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlHelper;
import ru.netology.page.LoginPage;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {
    @BeforeEach
    void SetUp() {
        open("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        SqlHelper.cleanAuthCode();
    }

    @AfterAll
    static void tearDownAll() {
        SqlHelper.cleanDataBase();
    }

    @Test
    @DisplayName("Should successfully login to dashboard with exist login and password from sut test data")
    void testSuccessLogin(){
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = SqlHelper.getVerificationCode(authInfo.getLogin());
        verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("System must not accept wrong verification code")
    void shouldNotAcceptWrongVerificationCode() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = SqlHelper.getVerificationCode(authInfo.getLogin());
        var invalidVerificationCode = DataHelper.getInvalidVerificationCode(verificationCode);
        verificationPage.validVerify(invalidVerificationCode);
        $("[data-test-id=error-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=error-notification] .notification__title").shouldHave(Condition.exactText("Ошибка"));
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! Неверно указан код! Попробуйте ещё раз."));
    }

    @Test
    @DisplayName("User must blocked after 3 entering of wrong password")
    void shouldNotAcceptWrongPassword() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var invalidAuthInfo = DataHelper.getInvalidAuthInfo(authInfo);
        for (int i = 0; i < 3; i++) {
            loginPage.invalidLogin(invalidAuthInfo);
            $("[data-test-id=error-notification]").shouldBe(visible, Duration.ofSeconds(10));
            $("[data-test-id=error-notification] .notification__title").shouldHave(Condition.exactText("Ошибка"));
            $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
        }
        loginPage.invalidLogin(authInfo);
        $("[data-test-id=error-notification]").shouldBe(visible, Duration.ofSeconds(10));
        $("[data-test-id=error-notification] .notification__title").shouldHave(Condition.exactText("Ошибка"));
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"));
    }
}
