package ru.netology.data;

import com.github.javafaker.Faker;
import ru.netology.dto.AuthInfo;
import ru.netology.dto.VerificationCode;

import java.util.Locale;

public class DataHelper {
    private static final Faker FAKER = new Faker(new Locale("en"));

    public DataHelper() {
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static String generateRandomLogin() {
        return FAKER.name().username();
    }

    public static String generateRandomPassword() {
        return FAKER.internet().password();
    }

    public static VerificationCode getInvalidVerificationCode(VerificationCode verificationCode) {
        var validVerificationCode = verificationCode.getCode();
        var invalidVerificationCode = validVerificationCode.substring(0, validVerificationCode.length() - 1);
        return new VerificationCode(invalidVerificationCode);
    }

    public static AuthInfo getInvalidAuthInfo(AuthInfo authInfo) {
        var validPassword = authInfo.getPassword();
        var invalidPassword = validPassword + "abc";
        return new AuthInfo(authInfo.getLogin(), invalidPassword);
    }

    public static AuthInfo generateRandomUser() {
        return new AuthInfo(generateRandomLogin(), generateRandomPassword());
    }

    public static VerificationCode generateVerificationCode(){
        return new VerificationCode(FAKER.numerify("######"));
    }
}
