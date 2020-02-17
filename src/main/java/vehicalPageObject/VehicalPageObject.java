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

    public void Vehical_login() {

	clickToDynamicText("Đặt vé xe");

	clickToDynamicButton(driver, "Đồng ý");
    }

    // input vào ô input với tham số truyền vào là inputbox
    public void inputToDynamicInputBox(String inputValue, String dynamicTextValue) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
	if (status == true) {
	    clearText(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
	    sendKeyToElement(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, inputValue, dynamicTextValue);
	}
    }

    public void inputToDynamicInputBoxIDandIndex(String inputValue, String dynamicID) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicID);
	if (status == true) {
	    clearText(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicID);
	    sendKeyToElement(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, inputValue, dynamicID);
	}
    }

    public void clickToDynamicTextBox(String dynamicTextValue) {
	scrollIDown(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicTextValue);
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicTextValue);
	if (status == true) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicTextValue);
	}
    }

    // Click vào button, text có class là textview, tham số truyền vào là text
    public void clickToDynamicButtonLinkOrLinkText(String dynamicTextValue) {
	scrollIDown(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
	if (status == true) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
	}

    }

    // Click vào button, text có class là textview, tham số truyền vào là text
    public void clickToDynamicText(String dynamicTextValue) {
	scrollIDown(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	if (status == true) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	}

    }

    // Click vao 1 button sử dụng tham số là text
    public void clickToDynamicButton(String dynamicTextValue) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
	if (status == true) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
	}

    }

    // Click vao 1 button ngay mai
    public void clickToDynamicTomorrow(String dynamicTextValue) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicTextValue);
	if (status == true) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicTextValue);
	}

    }

    public boolean isDynamicMessageAndLabelTextDisplayed(String dynamicTextValue) {
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	if (status = true) {
	    isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	}
	return isDisplayed;

    }

    public boolean isDynamicIconBackDisplayed(String dynamicTextValue) {
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_BACK, dynamicTextValue);
	if (status = true) {
	    isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_ICON_BACK, dynamicTextValue);
	}
	return isDisplayed;

    }

    public boolean isDynamicIconChangePlaceDisplayed(String dynamicId) {
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE, dynamicId);
	if (status = true) {
	    isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE, dynamicId);
	}
	return isDisplayed;

    }

    public String getDynamicDayStart(String dynamicID) {
	boolean status = false;
	String text = null;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
	if (status = true) {
	    text = getTextElement(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);

	}
	return text;

    }

    public String getDynamicTextCalenda(String dynamicID) {
	boolean status = false;
	String text = null;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_CALENDA, dynamicID);
	if (status = true) {
	    text = getTextElement(driver, CommonPageUIs.DYNAMIC_CALENDA, dynamicID);

	}
	return text;

    }

    // check button có hiển thị hay không, tham số truyền vào là text của button
    public boolean isDynamicButtonDisplayed(String dynamicTextValue) {
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
	if (status = true) {
	    isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
	}
	return isDisplayed;

    }

    // Click vao 1 button back tham số truyền vào là text
    public void clickToDynamicButtonBack(String dynamicTextValue) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_BACK, dynamicTextValue);
	if (status == true) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_ICON_BACK, dynamicTextValue);
	}

    }

    // Click chọn ngày
    public void clickToDynamicButtonChoiseDate(String dynamicTextValue) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicTextValue);
	if (status == true) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicTextValue);
	}

    }

    // Click icon đổi điểm đi điểm đến
    public void clickToDynamicIconChangePlacek(String dynamicID) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE, dynamicID);
	if (status == true) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE, dynamicID);
	}

    }

//    get text ở edit text
    public String getDynamicEditText(String dynamicID) {
	boolean status = false;
	String text = null;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_EDIT_TEXT, dynamicID);
	if (status = true) {
	    text = getTextElement(driver, CommonPageUIs.DYNAMIC_EDIT_TEXT, dynamicID);

	}
	return text;
    }

    // Click close chọn điểm đi
    public void clickToDynamicButtonIconBack(String dynamicID) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE, dynamicID);
	if (status == true) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE, dynamicID);
	}

    }

    public String getDynamicConfirmNullData(String dynamicID) {
	boolean status = false;
	String text = null;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_NULL_DATA, dynamicID);
	if (status = true) {
	    text = getTextElement(driver, CommonPageUIs.DYNAMIC_NULL_DATA, dynamicID);

	}
	return text;

    }

    public boolean isDynamicForcus(String dynamicTextValue) {
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicTextValue);
	if (status = true) {
	    isDisplayed = isControlForcus(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicTextValue);
	}
	return isDisplayed;

    }
}
