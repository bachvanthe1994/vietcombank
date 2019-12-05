package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.TransferIdentityPageUIs;

public class TransferIdentiryPageObject extends AbstractPage{

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
	
	public void clickToDynamicAccept(AndroidDriver<AndroidElement> driver, String dynamicIDValue) {
		waitForElementVisible(driver, TransferIdentityPageUIs.CLICK_TEXTVIEW, dynamicIDValue);
		clickToElement(driver, TransferIdentityPageUIs.CLICK_TEXTVIEW, dynamicIDValue);

	}
	
	public void clickToDynamicAcceptText(AndroidDriver<AndroidElement> driver, String dynamicIDValue) {
		waitForElementVisible(driver, TransferIdentityPageUIs.CLICK_TEXT, dynamicIDValue);
		clickToElement(driver, TransferIdentityPageUIs.CLICK_TEXT, dynamicIDValue);

	}
}
