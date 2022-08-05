package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
public class TransferPage {

    private SelenideElement transferSumField = $("[data-test-id=amount] input");
    private SelenideElement transferCardField = $("[data-test-id=from] input");
    private SelenideElement applyButton = $("[data-test-id=action-transfer]");
    private SelenideElement notification = $("[data-test-id=error-notification]");


    public TransferPage ImportTransferDataSecondToFirst(int value) {
        transferSumField.setValue(Integer.toString(value));
        transferCardField.setValue(String.valueOf(DataHelper.getSecondCardNumber()));
        applyButton.click();
        return new TransferPage();
    }

    public TransferPage ImportTransferDataFirstToSecond(int value) {
        transferSumField.setValue(Integer.toString(value));
        transferCardField.setValue(String.valueOf(DataHelper.getFirstCardNumber()));
        applyButton.click();
        return new TransferPage();
    }

    public void getNotification() {
        notification.shouldHave(exactText("Перевод не возможен. Баланс карты превышен"));
        notification.shouldBe(visible);
    }
}
