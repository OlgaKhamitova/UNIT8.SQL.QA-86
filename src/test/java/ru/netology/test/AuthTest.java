package ru.netology.test;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlHelper;
import ru.netology.page.LoginPage;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

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
        var dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.verifyHeadingIsVisible();
    }

    @Test
    @DisplayName("System must not accept wrong verification code")
    void shouldNotAcceptWrongVerificationCode() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = SqlHelper.getVerificationCode(authInfo.getLogin());
        var invalidVerificationCode = DataHelper.getInvalidVerificationCode(verificationCode);
        verificationPage.invalidVerify(invalidVerificationCode);
    }

    @Test
    @DisplayName("User must blocked after 3 entering of wrong password")
    void shouldNotAcceptWrongPassword() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var invalidAuthInfo = DataHelper.getInvalidAuthInfo(authInfo);
        for (int i = 0; i < 3; i++) {
            loginPage.invalidLogin(invalidAuthInfo);
        }
        loginPage.blockedUserLogin(authInfo);
    }
}
