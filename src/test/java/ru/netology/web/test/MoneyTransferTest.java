package ru.netology.web.test;

import lombok.val;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV2;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {
    @BeforeEach
    void shouldLogin() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }


    @Test
    void shouldTransferMoneySecondToFirstCard() {
        int value = 100;
        val dashboardPage = new DashboardPage();
        var FirstCardInitialBalance = dashboardPage.getFirstCardBalance();
        var SecondCardInitialBalance = dashboardPage.getSecondCardBalance();
        val dashboardPage2 = dashboardPage.TransferMoneySecondToFirst(value);
        var FirstCardFinalBalance = dashboardPage2.getFirstCardBalance();
        var SecondCardFinalBalance = dashboardPage2.getSecondCardBalance();
        Assertions.assertEquals(SecondCardInitialBalance - value, SecondCardFinalBalance);
        Assertions.assertEquals(FirstCardInitialBalance + value, FirstCardFinalBalance);

    }

    @Test
    void shouldTransferMoneyFirstToSecondCard() {
        int value = 100;
        val dashboardPage = new DashboardPage();
        var FirstCardInitialBalance = dashboardPage.getFirstCardBalance();
        var SecondCardInitialBalance = dashboardPage.getSecondCardBalance();
        val dashboardPage2 = dashboardPage.TransferMoneyFirstToSecond(value);
        var FirstCardFinalBalance = dashboardPage2.getFirstCardBalance();
        var SecondCardFinalBalance = dashboardPage2.getSecondCardBalance();
        Assertions.assertEquals(FirstCardInitialBalance - value, FirstCardFinalBalance);
        Assertions.assertEquals(SecondCardInitialBalance + value, SecondCardFinalBalance);

    }

    @Test
    void DoNotShouldTransferMoneyFirstToSecondCardAfterLimit() {
        int value = 100;
        val dashboardPage = new DashboardPage();
        var SecondCardInitialBalance = dashboardPage.getSecondCardBalance();
        val dashboardPage2 = dashboardPage.TransferMoneySecondToFirst(value + SecondCardInitialBalance);
        dashboardPage2.getNotification();

    }

}
