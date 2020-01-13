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

    public void inputToDynamicInputBoxIdentityValidate(AndroidDriver<AndroidElement> driver, String inputValue, String dynamicTextValue) {
	scrollToText(driver, dynamicTextValue);
	waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
	sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, inputValue, dynamicTextValue);

    }

    public String getDynamicTextHeader(AndroidDriver<AndroidElement> driver) {
	waitForElementVisible(driver, TransferIdentityPageUIs.DYNAMIC_TEXT_HEADER);
	return getTextElement(driver, TransferIdentityPageUIs.DYNAMIC_TEXT_HEADER);
    }

    public String getDynamicPopupPassword(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	waitForElementVisible(driver, TransferIdentityPageUIs.DYNAMIC_PASSWORD, dynamicTextValue);
	return getTextElement(driver, TransferIdentityPageUIs.DYNAMIC_PASSWORD, dynamicTextValue);
    }

    public void clickToDynamicHomeIcon(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	scrollToText(driver, dynamicTextValue);
	waitForElementVisible(driver, TransferIdentityPageUIs.DYNAMIC_HOME_ICON, dynamicTextValue);
	clickToElement(driver, TransferIdentityPageUIs.DYNAMIC_HOME_ICON, dynamicTextValue);

    }

    public String getDynamicTextTitle(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	waitForElementVisible(driver, TransferIdentityPageUIs.DYNAMIC_TITLE_CONFIRM_TRANSFER, dynamicTextValue);
	return getTextElement(driver, TransferIdentityPageUIs.DYNAMIC_TITLE_CONFIRM_TRANSFER, dynamicTextValue);
    }

    public String getDynamicTextTitleMoneyUSD(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	waitForElementVisible(driver, TransferIdentityPageUIs.DYNAMIC_MONEY_USD, dynamicTextValue);
	return getTextElement(driver, TransferIdentityPageUIs.DYNAMIC_MONEY_USD, dynamicTextValue);
    }

    public boolean isDynamicTextConfirmDisplayed(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	scrollToText(driver, dynamicTextValue);
	waitForElementVisible(driver, TransferIdentityPageUIs.DYNAMIC_TEXT_CONFIM, dynamicTextValue);
	return isControlDisplayed(driver, TransferIdentityPageUIs.DYNAMIC_TEXT_CONFIM, dynamicTextValue);

    }

}
