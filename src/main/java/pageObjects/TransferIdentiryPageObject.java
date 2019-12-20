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

    public void inputBeneficiary(String inputValue) {
	waitForElementVisible(driver, TransferIdentityPageUIs.BENEFICIARY_FIELD);
	clearText(driver, TransferIdentityPageUIs.BENEFICIARY_FIELD);
	sendKeyToElement(driver, TransferIdentityPageUIs.BENEFICIARY_FIELD, inputValue);
    }

    public void inputIdentityNumber(String inputValue) {
	waitForElementVisible(driver, TransferIdentityPageUIs.INDENTITY_NUBER);
	clearText(driver, TransferIdentityPageUIs.INDENTITY_NUBER);
	sendKeyToElement(driver, TransferIdentityPageUIs.INDENTITY_NUBER, inputValue);
    }

    public void inputMoney(String inputValue) {
	waitForElementVisible(driver, TransferIdentityPageUIs.NUMBER);
	clearText(driver, TransferIdentityPageUIs.NUMBER);
	sendKeyToElement(driver, TransferIdentityPageUIs.NUMBER, inputValue);
    }

    public void inputContent(String inputValue) {
	waitForElementVisible(driver, TransferIdentityPageUIs.CONTENT);
	clearText(driver, TransferIdentityPageUIs.CONTENT);
	sendKeyToElement(driver, TransferIdentityPageUIs.CONTENT, inputValue);
    }

    public void clickToDynamicAcceptText(AndroidDriver<AndroidElement> driver, String dynamicIDValue) {
	waitForElementVisible(driver, TransferIdentityPageUIs.CLICK_TEXT, dynamicIDValue);
	clickToElement(driver, TransferIdentityPageUIs.CLICK_TEXT, dynamicIDValue);

    }

    public void clickToDynamicAccount(AndroidDriver<AndroidElement> driver, String dynamicAccount) {
	waitForElementVisible(driver, TransferIdentityPageUIs.CLICK_ACCOUNT, dynamicAccount);
	clickToElement(driver, TransferIdentityPageUIs.CLICK_ACCOUNT, dynamicAccount);

    }

    public void inputIssuedBy(String inputValue) {
	waitForElementVisible(driver, TransferIdentityPageUIs.ISSUED_BY);
	clearText(driver, TransferIdentityPageUIs.ISSUED_BY);
	sendKeyToElement(driver, TransferIdentityPageUIs.ISSUED_BY, inputValue);
    }

    public String getDynamicAmountLabelConvertVNDToLong(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	String moneyString = getDynamicAmountLabel(driver, dynamicTextValue);
	moneyString = moneyString.replaceAll("Phí: ", "");
	moneyString = moneyString.replaceAll(",", "");
	moneyString = moneyString.replaceAll("VND/giao dịch", "");
	return moneyString;

    }

    public String getDynamicAmountLabelConverEURToLong(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	String moneyString = getDynamicAmountLabel(driver, dynamicTextValue);
	moneyString = moneyString.replaceAll("Phí: ", "");
	moneyString = moneyString.replaceAll(",", "");
	moneyString = moneyString.replaceAll("EUR/giao dịch", "");
	return moneyString;

    }

    public void inputToDynamicInputBoxUsedValidate(AndroidDriver<AndroidElement> driver, String inputValue, String dynamicTextValue) {
	scrollToText(driver, dynamicTextValue);
	waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
	sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, inputValue, dynamicTextValue);

    }
}
