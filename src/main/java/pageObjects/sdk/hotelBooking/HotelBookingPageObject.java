package pageObjects.sdk.hotelBooking;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import commons.AbstractPage;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;
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
	
	public void handleSeekBarPrice(int endPoint1, int startPoint, int endPoint2) {
		WebElement seekBar = driver.findElement(By.id("com.VCB:id/rangeSeekbar"));
		
        int x = seekBar.getLocation().getX();
        int y = seekBar.getLocation().getY();
        float end = x + seekBar.getSize().getWidth();
        
        TouchAction action = new TouchAction(driver);
        
        int moveToMin = (int)(end * endPoint1 / 15000000);
        int startMax = (int)(end * startPoint / 15000000);
        int moveToMax = (int)(end *  endPoint2 / 15000000);
        
        action.longPress(PointOption.point(x , y))
        	  .moveTo(PointOption.point(moveToMin , y))
        	  .release().perform();
        
        action.longPress(PointOption.point(startMax , y))
  	  		  .moveTo(PointOption.point(moveToMax , y))
  	  		  .release().perform();
        
	}
	
	public boolean checkStarRate() {
		List<String> numberList = getListTextViewByLinearLayoutID(driver, "com.VCB:id/llStars");
		int numberOfStar = getListImageViewByLinearLayoutID(driver, "com.VCB:id/llStars").size();
		
		if (numberOfStar != 5) {
			return false;
		}
		
		int numberCheck = 1;
		for (String number : numberList) {
			if (Integer.parseInt(number) != numberCheck) {
				return false;
			}
			numberCheck ++;
			
		}
		
		return true;
	}
	
	public void chooseStarRateHotel(int star) {
		List<String> numberList = getListTextViewByLinearLayoutID(driver, "com.VCB:id/llStars");
		clickToDynamicButtonLinkOrLinkText(driver, numberList.get(star - 1));
	}
	
	public boolean checkSelectedStarRate(int star) {
		List<String> starList = getSelectedInListElements(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, "com.VCB:id/llStars");
		return Boolean.parseBoolean(starList.get(star - 1));
		
	}
	
	public String getTextRateAndPriceFilter() {
		List<String> listText = getListTextViewByRelativeLayoutID(driver, "com.VCB:id/llFilterContent");
		String result = "";
		for (String text : listText) {
			result = result + text;
		}
		return result;
		
	}
	
}
