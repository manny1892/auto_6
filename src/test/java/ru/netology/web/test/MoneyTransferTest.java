package ru.netology.web.test;

import lombok.val;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV2;
import ru.netology.web.page.TransferPage;

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
        String cardNumber = String.valueOf(DataHelper.getSecondCardNumber());
        val dashboardPage = new DashboardPage();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        dashboardPage.transferButtonSecondToFirst();
        val transferPage = new TransferPage();
        transferPage.importTransferData(value, cardNumber);
        var firstCardBalance1 = dashboardPage.getFirstCardBalance();
        var secondCardBalance1 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(secondCardBalance - value, secondCardBalance1);
        Assertions.assertEquals(firstCardBalance + value, firstCardBalance1);

    }

    @Test
    void shouldTransferMoneyFirstToSecondCard() {
        int value = 100;
        String cardNumber = String.valueOf(DataHelper.getFirstCardNumber());
        val dashboardPage = new DashboardPage();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        dashboardPage.transferButtonFirstToSecond();
        val transferPage = new TransferPage();
        transferPage.importTransferData(value, cardNumber);
        var firstCardBalance1 = dashboardPage.getFirstCardBalance();
        var secondCardBalance1 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstCardBalance - value, firstCardBalance1);
        Assertions.assertEquals(secondCardBalance + value, secondCardBalance1);

    }

    @Test
    void doNotShouldTransferMoneyFirstToSecondCardAfterLimit() {
        int value = 100;
        String cardNumber = String.valueOf(DataHelper.getSecondCardNumber());
        val dashboardPage = new DashboardPage();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        dashboardPage.transferButtonSecondToFirst();
        val transferPage = new TransferPage();
        transferPage.importTransferData(value + secondCardBalance, cardNumber);
        transferPage.getNotification();
    }
}
