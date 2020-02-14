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

	public String getCurentMonthAndYearPlusDays(long days) {
		LocalDate now = LocalDate.now();
		LocalDate date = now.plusDays(days);
		int month = date.getMonthValue();
		int year = date.getYear();
		return "THÁNG" + " " + month + " " + year;
	}

	public String getCurentMonthAndYearMinusDays(long days) {
		LocalDate now = LocalDate.now();
		LocalDate date = now.minusDays(days);
		int month = date.getMonthValue();
		int year = date.getYear();
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

	public void clickToDynamicPlusAndMinusIcon(String... textAndIcon) {
		boolean status = false;
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_DEFAULT_INCREASE_OR_DECREASE_ICON, textAndIcon);
		if (status = true) {
			clickToOneOfElement(driver, 0, AirTicketBookingUIs.DYNAMIC_DEFAULT_INCREASE_OR_DECREASE_ICON, textAndIcon);
		}
	}

	public void clickToDynamicFirstFlightShiftByFlightCode(String... resourceIDAndtext) {
		boolean status = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_BY_FLIGHT_CODE, resourceIDAndtext);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_BY_FLIGHT_CODE, resourceIDAndtext);
		if (status = true) {
			clickToOneOfElement(driver, 0, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_BY_FLIGHT_CODE, resourceIDAndtext);
		}
	}

	public void clickToDynamicDay(String... dynamicValue) {
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_DATE_SELECTED, dynamicValue);
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_DATE_SELECTED, dynamicValue);
		if (status = true) {
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_DATE_SELECTED, dynamicValue);
		}
	}

	public void clickToDynamicFlight(int index, String... dynamicText) {
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.LIST_FLIGHT, dynamicText);
		if (status = true) {
			clickToOneOfElement(driver, index, AirTicketBookingUIs.LIST_FLIGHT, dynamicText);
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

	public String getAirTicketInfo(int index, String id) {
		String text = null;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		if (status = true) {
			text = getTextInFirstElement(driver, index, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		}
		return text;
	}

	public String getAirTicketPriceInfo1Way(String... textAndId) {
		String text = null;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_PRICE_INFO, textAndId);
		if (status = true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_PRICE_INFO, textAndId);
		}
		return text;
	}

	public String getAirTicketPriceInfo2Way(String... IDAndFlightcodeID) {
		String text = null;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_INFO_BY_FLIGHT_CODE, IDAndFlightcodeID);
		if (status = true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_INFO_BY_FLIGHT_CODE, IDAndFlightcodeID);
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

	public String getTextSelectedPeople(String dynamicText) {
		String text = null;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_SELECTED_PEOPLE, dynamicText);
		if (status = true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_SELECTED_PEOPLE, dynamicText);
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

	public String getTextInDynamicTextBoxByLabel(String... textAndID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_BY_LABEL, textAndID);
		if (status = true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_BY_LABEL, textAndID);
		}
		return text;
	}

	public String getTextInDynamicCheckboxByLabel(String... textAndID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_LABEL, textAndID);
		if (status = true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_LABEL, textAndID);
		}
		return text;
	}

	public String getTextInDynamicTextBoxAirTicketInfoOfCustomer(String... IdIndexID) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_AIR_TICKET_INFO_OF_CUSTOMER, IdIndexID);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_AIR_TICKET_INFO_OF_CUSTOMER, IdIndexID);
		if (status = true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_AIR_TICKET_INFO_OF_CUSTOMER, IdIndexID);
		}
		return text;
	}

	public String getTextInDynamicTextViewAirTicketInfoOfCustomer(String... IdIndexID) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_TEXT_VIEW_AIR_TICKET_INFO_OF_CUSTOMER, IdIndexID);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_VIEW_AIR_TICKET_INFO_OF_CUSTOMER, IdIndexID);
		if (status = true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_VIEW_AIR_TICKET_INFO_OF_CUSTOMER, IdIndexID);
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

	public boolean isDynamicTextByIdDisplayed(String id) {

		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		if (status = true) {
			isDisplayed = isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
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

	public boolean isDynamicButtonDisplayed(String text) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_BUTTON, text);
		if (status = true) {
			isDisplayed = isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_BUTTON, text);
		}
		return isDisplayed;
	}

	public boolean isDynamicPlaceDisplayed(String... text) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_PLACE_TEXT, text);
		if (status = true) {
			isDisplayed = isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_PLACE_TEXT, text);
		}
		return isDisplayed;
	}

	public boolean isDynamicDropdownByLabelDisplayed(String... text) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_DEFAULT_INCREASE_OR_DECREASE_ICON, text);
		if (status = true) {
			isDisplayed = isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_DEFAULT_INCREASE_OR_DECREASE_ICON, text);
		}
		return isDisplayed;
	}

	public void scrollUpToTextView(String text) {
		scrollUp(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, text);
	}

}
