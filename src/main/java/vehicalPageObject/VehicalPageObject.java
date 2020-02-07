package vehicalPageObject;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vehicalTicketBookingUI.CommonPageUIs;

public class VehicalPageObject extends AbstractPage {
    public VehicalPageObject(AndroidDriver<AndroidElement> mappingDriver) {
	driver = mappingDriver;
    }

    private AndroidDriver<AndroidElement> driver;

    // input vào ô input với tham số truyền vào là inputbox
    public void inputToDynamicInputBox(AndroidDriver<AndroidElement> driver, String inputValue, String dynamicTextValue) {
	clearText(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
	sendKeyToElement(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, inputValue, dynamicTextValue);

    }

    // Click vào button, text có class là textview, tham số truyền vào là text
    public void clickToDynamicButtonLinkOrLinkText(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	scrollIDown(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
	clickToElement(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);

    }

    // Click vào button, text có class là textview, tham số truyền vào là text
    public void clickToDynamicText(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	scrollIDown(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	clickToElement(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);

    }

}
