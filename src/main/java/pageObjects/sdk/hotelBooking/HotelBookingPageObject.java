package pageObjects.sdk.hotelBooking;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.DynamicPageUIs;

public class HotelBookingPageObject extends AbstractPage{

	public HotelBookingPageObject(AndroidDriver<AndroidElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AndroidDriver<AndroidElement> driver;
	
	public String getDayCheckIn() {
		List<String> listText = getTextInListElements(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, "com.VCB:id/wdFromDay");
		String day = "";
		for (String text : listText) {
			day = day + text;
		}
		return day;
	}
	
	public String getDayCheckOut() {
		List<String> listText = getTextInListElements(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, "com.VCB:id/wdToDay");
		String day = "";
		for (String text : listText) {
			day = day + " " + text;
		}
		return day;
	}
	
	public String getPassengerAndRoom() {
		List<String> listText = getTextInListElements(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, "com.VCB:id/llSelectPassengerAndRoom");
		String passengerAndRoom = "";
		for (String text : listText) {
			passengerAndRoom = passengerAndRoom + text;
		}
		return passengerAndRoom;
	}
	
	public boolean checkSuggestLocation(List<String> listSuggestLocations, String checkedValue) {
		for (String location : listSuggestLocations) {
			if (!location.contains(checkedValue)) {
				return false;
			}
		}
		return true;
		
	}
	
	public boolean checkDateSelected(String dynamicValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicValue);
		
		String locator = String.format(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicValue);
		
		AndroidElement element = driver.findElement(By.xpath(locator));
		
		return Boolean.parseBoolean(element.getAttribute("selected"));
		
	}
	
}
