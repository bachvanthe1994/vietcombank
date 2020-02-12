package pageObjects.sdk.airTicketBooking;

import java.time.LocalDate;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.sdk.airTicketBooking.AirTicketBookingUIs;

public class DynamicAirTicketBookingObjects extends AbstractPage {

	public DynamicAirTicketBookingObjects(AndroidDriver<AndroidElement> mappingDriver) {
		driver = mappingDriver;
	}

	public String getCurentMonthAndYear() {
		LocalDate now = LocalDate.now();
		int month = now.getMonthValue();
		int year = now.getYear();
		return "THÁNG" + " " + month + " " + year;
	}

	private AndroidDriver<AndroidElement> driver;

	public void clickToDynamicTextOrButtonLink(String dynamicValue) {
		boolean status = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, dynamicValue);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, dynamicValue);
		if (status = true) {
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, dynamicValue);
		}
	}

	public void clickToDynamicButton(String dynamicValue) {
		boolean status = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_BUTTON, dynamicValue);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_BUTTON, dynamicValue);
		if (status = true) {
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_BUTTON, dynamicValue);
		}
	}

	public void clickToDynamicIcon(String id) {
		boolean status = false;
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_ICON_BY_ID, id);
		if (status = true) {
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_ICON_BY_ID, id);
		}
	}

	public void clickToDynamicDay(String... dynamicValue) {
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_DATE_SELECTED, dynamicValue);
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_DATE_SELECTED, dynamicValue);
		if (status = true) {
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_DATE_SELECTED, dynamicValue);
		}
	}

	public void clickToDynamicFlight(int flightSlot) {
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.LIST_FLIGHT);
		if (status = true) {
			clickToOneOfElement(driver, flightSlot, AirTicketBookingUIs.LIST_FLIGHT);
		}
	}

	public void waitForAirPlainDisapear(String dynamicText) {
		waitForElementInvisible(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, dynamicText);
	}

	public String getTextInDynamicTextBox(String id) {
		String text = null;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_BY_ID, id);
		if (status = true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_BY_ID, id);
		}
		return text;
	}

	public String getTextInDynamicPopUp(String id) {
		String text = null;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		if (status = true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		}
		return text;
	}

	public String getTextInDynamicDropDownByLabel(String... textAndID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_OR_DROPDOWN_BY_LABEL, textAndID);
		if (status = true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_OR_DROPDOWN_BY_LABEL, textAndID);
		}
		return text;
	}

	public String getDynamicTextByID(String id) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		if (status = true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);

		}
		return text;
	}

	public boolean isDynamicTextDisplayed(String text) {

		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, text);
		if (status = true) {
			isDisplayed = isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, text);
		}
		return isDisplayed;
	}

	public boolean isDynamicIconDisplayed(String text) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_ICON_BY_ID, text);
		if (status = true) {
			isDisplayed = isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_ICON_BY_ID, text);
		}
		return isDisplayed;
	}

	public boolean isDynamicPlaceDisplayed(String... text) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_PLACE, text);
		if (status = true) {
			isDisplayed = isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_PLACE, text);
		}
		return isDisplayed;
	}

}
