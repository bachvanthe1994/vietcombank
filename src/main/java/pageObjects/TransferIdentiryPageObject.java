package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.TransferIdentityCardUI;

public class TransferIdentiryPageObject extends AbstractPage {

    public TransferIdentiryPageObject(AndroidDriver<AndroidElement> mappingDriver) {
	driver = mappingDriver;
    }

    private AndroidDriver<AndroidElement> driver;

    public String getDynamicAmountLabelConvertVNDToLong(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	String moneyString = getDynamicAmountLabel(driver, dynamicTextValue);
	moneyString = moneyString.replaceAll("Phí: ", "");
	moneyString = moneyString.replaceAll(",", "");
	moneyString = moneyString.replaceAll("VND/giao dịch", "");
	return moneyString;

    }

    public void inputToDynamicInputBoxUsedValidate(AndroidDriver<AndroidElement> driver, String inputValue, String dynamicTextValue) {
	scrollToText(driver, dynamicTextValue);
	waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
	sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, inputValue, dynamicTextValue);

    }

    public String getTextInDynamicIdentifition(AndroidDriver<AndroidElement> driver, String... dynamicIndex1Index2) {
	waitForElementVisible(driver, TransferIdentityCardUI.DYNAMIC_IDENTIFITION, dynamicIndex1Index2);
	return getTextElement(driver, TransferIdentityCardUI.DYNAMIC_IDENTIFITION, dynamicIndex1Index2);

    }

    public String getTextDynamicPopupTransfers(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
	return getTextElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

    }

}
