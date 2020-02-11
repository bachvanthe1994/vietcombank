package pageObjects.sdk.airTicketBooking;

import java.time.LocalDate;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.sdk.airTicketBooking.AirTicketBookingUIs;

public class DynamicAirTicketBooking extends AbstractPage {

	public DynamicAirTicketBooking(AndroidDriver<AndroidElement> mappingDriver) {
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
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, dynamicValue);
		waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, dynamicValue);
		clickToElement(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, dynamicValue);
	}

	public void clickToDynamicButton(String dynamicValue) {
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_BUTTON, dynamicValue);
		waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_BUTTON, dynamicValue);
		clickToElement(driver, AirTicketBookingUIs.DYNAMIC_BUTTON, dynamicValue);
	}

	public void clickToDynamicIcon(String id) {
		waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_ICON_BY_ID, id);
		clickToElement(driver, AirTicketBookingUIs.DYNAMIC_ICON_BY_ID, id);
	}

	public void clickToDynamicDay(String... dynamicValue) {
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_DATE_SELECTED, dynamicValue);
		waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_DATE_SELECTED, dynamicValue);
		clickToElement(driver, AirTicketBookingUIs.DYNAMIC_DATE_SELECTED, dynamicValue);
	}

	public void clickToDynamicFlight(int flightSlot) {
		waitForElementVisible(driver, AirTicketBookingUIs.LIST_FLIGHT);
		clickToOneOfElement(driver, flightSlot, AirTicketBookingUIs.LIST_FLIGHT);
	}

	public void waitForAirPlainDisapear(String dynamicText) {
		waitForElementInvisible(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, dynamicText);
	}

	public String getTextInDynamicTextBox(String id) {
		waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_BY_ID, id);
		return getTextElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_BY_ID, id);
	}

	public String getTextInDynamicPopUp(String id) {
		waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		return getTextElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
	}

	public String getTextInDynamicDropDownByLabel(String... textAndID) {
		waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_OR_DROPDOWN_BY_LABEL, textAndID);
		return getTextElement(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_OR_DROPDOWN_BY_LABEL, textAndID);
	}

	public String getDynamicTextByID(String id) {
		waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		return getTextElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
	}

	public boolean isDynamicTextDisplayed(String text) {
		waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, text);
		return isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, text);
	}

	public boolean isDynamicIconDisplayed(String text) {
		waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_ICON_BY_ID, text);
		return isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_ICON_BY_ID, text);
	}

	public boolean isDynamicPlaceDisplayed(String... text) {
		waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_PLACE, text);
		return isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_PLACE, text);
	}

}
