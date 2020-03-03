package vehicalPageObject;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vehicalTicketBookingUI.CommonPageUIs;

public class VehicalPageObject extends AbstractPage {
	public VehicalPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public void Vehical_login() {

		clickToDynamicText("Đặt vé xe");

		clickToDynamicButton(driver, "Đồng ý");
	}

	// input vào ô input với tham số truyền vào là inputbox
	public void inputToDynamicInputBox(String inputValue, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
		if (status) {
			clearText(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
			sendKeyToElement(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, inputValue, dynamicTextValue);
		}
	}

	public void inputToDynamicInputBoxID(String inputValue, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicID);
		if (status) {
			clearText(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicID);
			sendKeyToElement(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, inputValue, dynamicID);
		}
	}

	public void clickToDynamicTextBox(String dynamicTextValue) {
		scrollIDown(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicTextValue);
		boolean status = false;
		status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicTextValue);
		if (status) {
			clickToElement(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicTextValue);
		}
	}

	// Click vào button, text có class là textview, tham số truyền vào là text
	public void clickToDynamicInputBox(String dynamicTextValue) {
		scrollIDown(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
		boolean status = false;
		status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
		if (status) {
			clickToElement(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
		}

	}

	// Click vào button, text có class là textview, tham số truyền vào là text
	public void clickToDynamicText(String dynamicTextValue) {
		scrollIDown(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
		boolean status = false;
		status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
		if (status) {
			clickToElement(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
		}

	}

	// Click vao 1 button sử dụng tham số là text
	public void clickToDynamicButton(String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status) {
			clickToElement(driver, CommonPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}

	}

	// Click vao 1 button ngay mai
	public void clickToDynamicTomorrow(String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicTextValue);
		if (status) {
			clickToElement(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicTextValue);
		}

	}

	public boolean isDynamicMessageAndLabelTextDisplayed(String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
		if (status) {
			isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
		}
		return isDisplayed;

	}

	public boolean isDynamicIconBackDisplayed(String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_BACK, dynamicTextValue);
		if (status) {
			isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_ICON_BACK, dynamicTextValue);
		}
		return isDisplayed;

	}

	public boolean isDynamicIconChangePlaceDisplayed(String dynamicId) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE, dynamicId);
		if (status) {
			isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE, dynamicId);
		}
		return isDisplayed;

	}

	public String getDynamicDayStart(String dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		if (status) {
			text = getTextElement(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);

		}
		return text;

	}

	public String getDynamicTextCalenda(String dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_CALENDA, dynamicID);
		if (status) {
			text = getTextElement(driver, CommonPageUIs.DYNAMIC_CALENDA, dynamicID);

		}
		return text;

	}

	// check button có hiển thị hay không, tham số truyền vào là text của button
	public boolean isDynamicButtonDisplayed(String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status) {
			isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
		return isDisplayed;

	}

	// Click vao 1 button back tham số truyền vào là text
	public void clickToDynamicButtonBack(String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_BACK, dynamicTextValue);
		if (status) {
			clickToElement(driver, CommonPageUIs.DYNAMIC_ICON_BACK, dynamicTextValue);
		}

	}

	// Click chọn ngày
	public void clickToDynamicButtonChoiseDate(String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicTextValue);
		if (status) {
			clickToElement(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicTextValue);
		}

	}

	// Click icon đổi điểm đi điểm đến
	public void clickToDynamicIconChangePlace(String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE, dynamicID);
		if (status) {
			clickToElement(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE, dynamicID);
		}

	}

//    get text ở edit text
	public String getDynamicEditText(String dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_EDIT_TEXT, dynamicID);
		if (status) {
			text = getTextElement(driver, CommonPageUIs.DYNAMIC_EDIT_TEXT, dynamicID);

		}
		return text;
	}

	public String getDynamicSuggestTrip(String dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_SUGGEST_TRIP, dynamicID);
		if (status) {
			text = getTextElement(driver, CommonPageUIs.DYNAMIC_SUGGEST_TRIP, dynamicID);

		}
		return text;
	}

	// Click close chọn điểm đi
	public void clickToDynamicButtonIconBack(String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE, dynamicID);
		if (status) {
			clickToElement(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE, dynamicID);
		}

	}

	public void clickToDynamicSelectedDate(String... dynamicTextAndText) {
		boolean status = false;
		status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextAndText);
		if (status) {
			clickToElement(driver, CommonPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextAndText);
		}

	}

	public String getDynamicConfirmNullData(String dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_NULL_DATA, dynamicID);
		if (status) {
			text = getTextElement(driver, CommonPageUIs.DYNAMIC_NULL_DATA, dynamicID);

		}
		return text;

	}

	public boolean isDynamicForcus(String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicTextValue);
		if (status) {
			isDisplayed = isControlForcus(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicTextValue);
		}
		return isDisplayed;
	}

	public boolean isDynamicForcusText(String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
		if (status) {
			isDisplayed = isControlForcus(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
		}
		return isDisplayed;
	}

	public boolean isDynamicDisplayed(String dynamicTextValue) {
		scrollIDown(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
		if (status) {
			isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
		}
		return isDisplayed;

	}

	public boolean isDynamicUnDisplayed(String dynamicTextValue) {
		scrollIDown(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
		boolean isDisplayed = true;
		boolean status = waitForElementInvisible(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlUnDisplayed(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
		}
		return isDisplayed;

	}

}
