package pageObjects.sdk.hotelBooking;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import commons.AbstractPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;
import model.HotelBookingInfo;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.sdk.hotelBooking.HotelBookingPageUIs;

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
	
	public void verticalSwipeByPercentage (double startPercentage, double endPercentage, double anchorPercentage) {
        Dimension size = driver.manage().window().getSize();
        int anchor = (int) (size.width * anchorPercentage);
        int startPoint = (int) (size.height * startPercentage);
        int endPoint = (int) (size.height * endPercentage);
 
        new TouchAction(driver)
                .press(PointOption.point(anchor, startPoint))
                .moveTo(PointOption.point(anchor, endPoint))
                .release().perform();
    }
	
	public void swipeElementToElementByText (String textStart, String textEnd) {
		Dimension size = driver.manage().window().getSize();
		String locatorStart = String.format(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, textStart);
    	MobileElement elementStart = driver.findElement(By.xpath(locatorStart));
    	
    	String locatorEnd = String.format(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, textEnd);
    	MobileElement elementEnd = driver.findElement(By.xpath(locatorEnd));
    	
		int xStart = elementStart.getLocation().getX();
        int yStart = elementStart.getLocation().getY();
        
        int xEnd = elementEnd.getLocation().getX();
        int yEnd = elementEnd.getLocation().getY();
        
        new TouchAction(driver)
                .press(PointOption.point(xStart, yStart))
                .moveTo(PointOption.point(xEnd, yEnd))
                .release().perform();
    }
	
	public List<HotelBookingInfo> getListHotelBookingHistory(){
		List<HotelBookingInfo> listHotelBookingInfo = new ArrayList<HotelBookingInfo>();
		List<String> listPayCode = new ArrayList<String>();
		
		for (int i = 0; i <= 30 ; i ++) {
			String payCode = getTextInListElements(driver, HotelBookingPageUIs.TEXTVIEW_PAYCODE_BY_ID).get(0);
			
			if (listPayCode.contains(payCode)) {
				break;
			}
			
			if (!listPayCode.contains(payCode)) {
				listPayCode.add(payCode);
				String locator = String.format(HotelBookingPageUIs.LINEARLAYOUT_HOTEL_BY_PAYCODE, payCode);
		    	String hotelName = driver.findElement(By.xpath(locator)).findElement(By.id("com.VCB:id/tvHotelName")).getText();
		    	String hotelAddress = driver.findElement(By.xpath(locator)).findElement(By.id("com.VCB:id/tvHotelAddress")).getText();
		    	String createdDate = driver.findElement(By.xpath(locator)).findElement(By.id("com.VCB:id/tvCreatedDate")).getText();
		    	String checkinDate = driver.findElement(By.xpath(locator)).findElement(By.id("com.VCB:id/tvCheckinDate")).getText();
		    	String price = driver.findElement(By.xpath(locator)).findElement(By.id("com.VCB:id/tvPrice")).getText();
		    	String status = driver.findElement(By.xpath(locator)).findElement(By.id("com.VCB:id/tvStatus")).getText();
		    	HotelBookingInfo info = new HotelBookingInfo(payCode, hotelName, hotelAddress, createdDate, checkinDate, price, status);
		    	listHotelBookingInfo.add(info);
		    	
			}
			swipeElementToElementByText("Trạng thái", "Danh sách đặt phòng");
			
		}
		
		return listHotelBookingInfo;
		
	}
	
}
