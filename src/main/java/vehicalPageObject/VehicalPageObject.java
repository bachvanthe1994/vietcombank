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

    public void Vehical_login(AndroidDriver<AndroidElement> driver) {

	clickToDynamicText(driver, "Đặt vé xe");

	clickToDynamicButton(driver, "Đồng ý");
    }

    // input vào ô input với tham số truyền vào là inputbox
    public void inputToDynamicInputBox(AndroidDriver<AndroidElement> driver, String inputValue, String dynamicTextValue) {
	clearText(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
	sendKeyToElement(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, inputValue, dynamicTextValue);

    }

    public void inputToDynamicInputBoxIDandIndex(AndroidDriver<AndroidElement> driver, String inputValue, String dynamicID) {
	clearText(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicID);
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicID);
	sendKeyToElement(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, inputValue, dynamicID);

    }

    public void clickToDynamicTextBox(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	scrollIDown(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicTextValue);
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicTextValue);
	clickToElement(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicTextValue);

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

    // Click vao 1 button sử dụng tham số là text
    public void clickToDynamicButton(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
	clickToElement(driver, CommonPageUIs.DYNAMIC_BUTTON, dynamicTextValue);

    }

    // Click vao 1 button ngay mai
    public void clickToDynamicTomorrow(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicTextValue);
	clickToElement(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicTextValue);

    }

    public boolean isDynamicMessageAndLabelTextDisplayed(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	return isControlDisplayed(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);

    }

    public boolean isDynamicIconBackDisplayed(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_BACK, dynamicTextValue);
	return isControlDisplayed(driver, CommonPageUIs.DYNAMIC_ICON_BACK, dynamicTextValue);

    }

    public boolean isDynamicIconChangePlaceDisplayed(AndroidDriver<AndroidElement> driver, String dynamicId) {
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE, dynamicId);
	return isControlDisplayed(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE, dynamicId);

    }

    public String getDynamicDayStart(AndroidDriver<AndroidElement> driver, String dynamicID) {
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
	return getTextElement(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
    }

    public String getDynamicTextCalenda(AndroidDriver<AndroidElement> driver, String dynamicID) {
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_CALENDA, dynamicID);
	return getTextElement(driver, CommonPageUIs.DYNAMIC_CALENDA, dynamicID);
    }

    // check button có hiển thị hay không, tham số truyền vào là text của button
    public boolean isDynamicButtonDisplayed(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
	return isControlDisplayed(driver, CommonPageUIs.DYNAMIC_BUTTON, dynamicTextValue);

    }

    // Click vao 1 button back tham số truyền vào là text
    public void clickToDynamicButtonBack(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_BACK, dynamicTextValue);
	clickToElement(driver, CommonPageUIs.DYNAMIC_ICON_BACK, dynamicTextValue);

    }

    // Click chọn ngày
    public void clickToDynamicButtonChoiseDate(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicTextValue);
	clickToElement(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicTextValue);

    }

    // Click icon đổi điểm đi điểm đến
    public void clickToDynamicIconChangePlacek(AndroidDriver<AndroidElement> driver, String dynamicID) {
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE, dynamicID);
	clickToElement(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE, dynamicID);
    }

//    get text ở edit text
    public String getDynamicEditText(AndroidDriver<AndroidElement> driver, String dynamicID) {
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_EDIT_TEXT, dynamicID);
	return getTextElement(driver, CommonPageUIs.DYNAMIC_EDIT_TEXT, dynamicID);
    }

    // Click close chọn điểm đi
    public void clickToDynamicButtonIconBack(AndroidDriver<AndroidElement> driver, String dynamicID) {
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE, dynamicID);
	clickToElement(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE, dynamicID);

    }

    public String getDynamicConfirmNullData(AndroidDriver<AndroidElement> driver, String dynamicID) {
	waitForElementVisible(driver, CommonPageUIs.DYNAMIC_NULL_DATA, dynamicID);
	return getTextElement(driver, CommonPageUIs.DYNAMIC_NULL_DATA, dynamicID);
    }
    
}
