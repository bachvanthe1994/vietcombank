package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.TransferIdentityPageUIs;

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

    public String getTextInDynamicIdentifition(AndroidDriver<AndroidElement> driver, String... dynamicIndex1Index2) {
	waitForElementVisible(driver, TransferIdentityPageUIs.DYNAMIC_IDENTIFITION, dynamicIndex1Index2);
	return getTextElement(driver, TransferIdentityPageUIs.DYNAMIC_IDENTIFITION, dynamicIndex1Index2);

    }

    public String getTextDynamicPopupTransfers(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
	return getTextElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

    }

    // input vào ô input với tham số truyền vào là inputbox
    public void inputToDynamicInputBoxIdentityValidate(AndroidDriver<AndroidElement> driver, String inputValue, String dynamicTextValue) {
	scrollToText(driver, dynamicTextValue);
	waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
	sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, inputValue, dynamicTextValue);

    }

}
