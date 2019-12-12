package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.TransferIdentityPageUIs;

public class TransferIdentiryPageObject extends AbstractPage{

	public TransferIdentiryPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	
	private AndroidDriver<AndroidElement> driver;
	
	public void inputBeneficiary(String inputValue) {
		waitForElementVisible(TransferIdentityPageUIs.BENEFICIARY_FIELD);
		clearText(TransferIdentityPageUIs.BENEFICIARY_FIELD);
		sendKeyToElement(TransferIdentityPageUIs.BENEFICIARY_FIELD, inputValue);
	}
	
	public void inputIdentityNumber(String inputValue) {
		waitForElementVisible(TransferIdentityPageUIs.INDENTITY_NUBER);
		clearText(TransferIdentityPageUIs.INDENTITY_NUBER);
		sendKeyToElement(TransferIdentityPageUIs.INDENTITY_NUBER, inputValue);
	}
	
	public void inputMoney(String inputValue) {
		waitForElementVisible(TransferIdentityPageUIs.NUMBER);
		clearText(TransferIdentityPageUIs.NUMBER);
		sendKeyToElement(TransferIdentityPageUIs.NUMBER, inputValue);
	}
	
	public void inputContent(String inputValue) {
		waitForElementVisible(TransferIdentityPageUIs.CONTENT);
		clearText(TransferIdentityPageUIs.CONTENT);
		sendKeyToElement(TransferIdentityPageUIs.CONTENT, inputValue);
	}
	
	public void clickToDynamicAcceptText(AndroidDriver<AndroidElement> driver, String dynamicIDValue) {
		waitForElementVisible(TransferIdentityPageUIs.CLICK_TEXT, dynamicIDValue);
		clickToElement(TransferIdentityPageUIs.CLICK_TEXT, dynamicIDValue);

	}
	
	public void clickToDynamicAccount(AndroidDriver<AndroidElement> driver, String dynamicAccount) {
		waitForElementVisible(TransferIdentityPageUIs.CLICK_ACCOUNT, dynamicAccount);
		clickToElement(TransferIdentityPageUIs.CLICK_ACCOUNT, dynamicAccount);

	}
}
