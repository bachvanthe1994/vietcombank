package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.TransferStatusPageUIs;

public class TransferMoneyStatusPageObject extends AbstractPage {

    public TransferMoneyStatusPageObject(AndroidDriver<AndroidElement> mappingDriver) {
	driver = mappingDriver;
    }

    private AndroidDriver<AndroidElement> driver;

    public String getDynamicTextTitle(AndroidDriver<AndroidElement> driver, String dynamicID) {
	waitForElementVisible(driver, TransferStatusPageUIs.DYNAMIC_TITLE, dynamicID);
	return getTextElement(driver, TransferStatusPageUIs.DYNAMIC_TITLE, dynamicID);
    }

    public void clickToDynamictTextBox(AndroidDriver<AndroidElement> driver, String dynamicID) {
	waitForElementVisible(driver, TransferStatusPageUIs.DYNAMIC_TRANSFER, dynamicID);
	clickToElement(driver, TransferStatusPageUIs.DYNAMIC_TRANSFER, dynamicID);
    }
}
