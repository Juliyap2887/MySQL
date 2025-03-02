package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private static final Faker FAKER = new Faker(new Locale("en"));

    private DataHelper() {
    }

    public static AuthInfo getAuthInfoWithTestDate() {

        return new AuthInfo("vasya", "qwerty123");
    }

    public static String generateRandomLogin() {

        return FAKER.name().username();
    }

    public static String generateRandomPassword() {

        return FAKER.internet().password();
    }

    public static AuthInfo generateRandomUser() {
        return new AuthInfo(generateRandomLogin(), generateRandomPassword());
    }

    public static VerificationCode generateVerificationCode() {
        return new VerificationCode(FAKER.numerify("#####"));
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Value
    public static class VerificationCode {
        String code;
    }
}
