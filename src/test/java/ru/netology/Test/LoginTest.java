package ru.netology.Test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.Page.LoginPage;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanAuthCodes;
import static ru.netology.data.SQLHelper.cleanDataBase;

public class LoginTest {
    LoginPage login;

    @BeforeEach
    void setUp() {
        login = open("http://localhost:9999", LoginPage.class);
    }

    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }

    @AfterAll
    static void tearDownAll() {
        cleanDataBase();
    }

    @Test
    void shouldAuth() {
        var authInfo = DataHelper.getAuthInfoWithTestDate();
        var verificationPage = login.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    void shouldGetErrorMessageIfRandomUserIsNotExistInBase() {
        var authInfo = DataHelper.generateRandomUser();
        login.validLogin(authInfo);
        login.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    void shouldGetErrorMessageWithExistUserAndRandomVerificationCode() {
        var authInfo = DataHelper.getAuthInfoWithTestDate();
        var verificationPage = login.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = DataHelper.generateVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotification("Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }
}
