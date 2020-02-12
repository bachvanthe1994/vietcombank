package pageObjects.sdk.hotelBooking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import commons.AbstractPage;
import commons.Constants;
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
	
	 public void horizontalSwipeByPercentage (double startPercentage, double endPercentage, double anchorPercentage) {
	        Dimension size = driver.manage().window().getSize();
	        int anchor = (int) (size.height * anchorPercentage);
	        int startPoint = (int) (size.width * startPercentage);
	        int endPoint = (int) (size.width * endPercentage);
	 
	        new TouchAction(driver)
	                .longPress(PointOption.point(startPoint, anchor))
	                .moveTo(PointOption.point(endPoint, anchor))
	                .release().perform();
	    }
	
	public void verticalSwipeByPercentage (double startPercentage, double endPercentage, double anchorPercentage) {
        Dimension size = driver.manage().window().getSize();
        int anchor = (int) (size.width * anchorPercentage);
        int startPoint = (int) (size.height * startPercentage);
        int endPoint = (int) (size.height * endPercentage);
 
        new TouchAction(driver)
                .longPress(PointOption.point(anchor, startPoint))
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
                .longPress(PointOption.point(xStart, yStart))
                .moveTo(PointOption.point(xEnd, yEnd))
                .release().perform();
    }
	
	public List<HotelBookingInfo> getListHotelBookingHistory() {
		List<HotelBookingInfo> listHotelBookingInfo = new ArrayList<HotelBookingInfo>();
		List<String> listPayCode = new ArrayList<String>();
		
		for (int i = 0; i <= 30 ; i ++) {
			String payCode = getTextInListElements(driver, HotelBookingPageUIs.TEXTVIEW_PAYCODE_BY_ID).get(0);
			
			if (listPayCode.contains(payCode)) {
				break;
			}
			
			if (!listPayCode.contains(payCode)) {
				try {
					listPayCode.add(payCode);
					String locator = String.format(HotelBookingPageUIs.LINEARLAYOUT_HOTEL_BY_PAYCODE, payCode);
				    String hotelName = driver.findElement(By.xpath(locator)).findElement(By.id("com.VCB:id/tvHotelName")).getText();
				    String hotelAddress = driver.findElement(By.xpath(locator)).findElement(By.id("com.VCB:id/tvHotelAddress")).getText();
				    
				    SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
				    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
			    	String createdDate = formatter2.format(formatter1.parse(driver.findElement(By.xpath(locator)).findElement(By.id("com.VCB:id/tvCreatedDate")).getText()));
			    	String checkinDate = formatter2.format(formatter1.parse(driver.findElement(By.xpath(locator)).findElement(By.id("com.VCB:id/tvCheckinDate")).getText()));
			    	
				    String price = driver.findElement(By.xpath(locator)).findElement(By.id("com.VCB:id/tvPrice")).getText();
				    String status = driver.findElement(By.xpath(locator)).findElement(By.id("com.VCB:id/tvStatus")).getText();
				    HotelBookingInfo info = new HotelBookingInfo(payCode, hotelName, hotelAddress, createdDate, checkinDate, price, status);
				    listHotelBookingInfo.add(info);
			    	
				} catch (Exception e) {
					
				}

			}
			swipeElementToElementByText("Trạng thái", "Danh sách đặt phòng");
			
		}
		
		return listHotelBookingInfo;
		
	}
	
	public static List<HotelBookingInfo> actualList = new ArrayList<HotelBookingInfo>();
	public List<HotelBookingInfo> sortListHotelBookingHistory() {
		List<HotelBookingInfo> listContainWaitingPay = new ArrayList<HotelBookingInfo>();
		List<HotelBookingInfo> listNotContainWaitingPay = new ArrayList<HotelBookingInfo>();
		List<HotelBookingInfo> actualListHotelBookingInfo = getListHotelBookingHistory();
		actualList = actualListHotelBookingInfo;
		List<HotelBookingInfo> expectListHotelBookingInfo = new ArrayList<HotelBookingInfo>();
		
		listContainWaitingPay = (List<HotelBookingInfo>) actualListHotelBookingInfo.stream().filter(p -> p.status.equals("Chờ thanh toán")).collect(Collectors.toList());
		listContainWaitingPay.sort((o1, o2) -> o2.createDate.compareTo(o1.createDate));
		
		listNotContainWaitingPay = (List<HotelBookingInfo>) actualListHotelBookingInfo.stream().filter(p -> !p.status.equals("Chờ thanh toán")).collect(Collectors.toList());
		listNotContainWaitingPay.sort((o1, o2) -> o2.createDate.compareTo(o1.createDate));
		
		for (HotelBookingInfo info : listNotContainWaitingPay) {
			listContainWaitingPay.add(info);
		}
		
		expectListHotelBookingInfo = listContainWaitingPay;
		
		return expectListHotelBookingInfo;
		
	}
	
	public List<String> getListOfStatusHotelBooking(AndroidDriver<AndroidElement> driver, String dynamicID) {
		waitForElementVisible(driver, HotelBookingPageUIs.TEXTVIEW_BY_LISTVIEW, dynamicID);
		return getTextInListElements(driver, HotelBookingPageUIs.TEXTVIEW_BY_LISTVIEW, dynamicID);
		
	}
	
	public List<HotelBookingInfo> getListHotelRecentView() {
		List<HotelBookingInfo> listHotelBookingInfo = new ArrayList<HotelBookingInfo>();
		List<String> listHotelName = new ArrayList<String>();
		
		for (int i = 0; i <= 30 ; i ++) {
			String hotelName = getTextInListElements(driver, HotelBookingPageUIs.TEXTVIEW_HOTEL_NAME_BY_ID).get(0);
			
			if (listHotelName.contains(hotelName)) {
				break;
			}
			
			if (!listHotelName.contains(hotelName)) {
				try {
					listHotelName.add(hotelName);
					String locator = String.format(HotelBookingPageUIs.LINEARLAYOUT_HOTEL_BY_HOTEL_NAME, hotelName);
				    String hotelAddress = driver.findElement(By.xpath(locator)).findElement(By.id("com.VCB:id/tvAddress")).getText();
				    String price = driver.findElement(By.xpath(locator)).findElement(By.id("com.VCB:id/tvFinalPriceOneNight")).getText();

				    HotelBookingInfo info = new HotelBookingInfo("", hotelName, hotelAddress, "", "", price, "");
				    listHotelBookingInfo.add(info);
			    	
				} catch (Exception e) {
					
				}

			}
			horizontalSwipeByPercentage(0.9, 0, 0.9);
			
		}
		
		return listHotelBookingInfo;
		
	}
	
	public List<HotelBookingInfo> getListHotelSearched() {
		List<HotelBookingInfo> listHotelBookingInfo = new ArrayList<HotelBookingInfo>();
		List<String> listHotelName = new ArrayList<String>();
		
		for (int i = 0; i <= 1 ; i ++) {
			String hotelName = getTextInListElements(driver, HotelBookingPageUIs.TEXTVIEW_HOTEL_NAME_BY_ID).get(i);
			
			if (!listHotelName.contains(hotelName)) {
				listHotelName.add(hotelName);
				String locator = String.format(HotelBookingPageUIs.LINEARLAYOUT_HOTEL_BY_HOTEL_NAME, hotelName);
				String hotelAddress = driver.findElement(By.xpath(locator)).findElement(By.id("com.VCB:id/tvAddress")).getText();
				String price = driver.findElement(By.xpath(locator)).findElement(By.id("com.VCB:id/tvFinalPriceOneNight")).getText();

				HotelBookingInfo info = new HotelBookingInfo("", hotelName, hotelAddress, "", "", price, "");
				listHotelBookingInfo.add(info);
			}
			
		}
		
		return listHotelBookingInfo;
		
	}
	
	public void viewHotelDetail() {
		List<HotelBookingInfo> listHotelBookingInfo = getListHotelSearched();
		
		for (HotelBookingInfo info : listHotelBookingInfo) {
			clickToDynamicButtonLinkOrLinkText(driver, info.hotelName);
			waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, info.hotelName);
			navigateBack(driver);
			
		}
	}
	
	public int getNumberOfHotelInMap(String... dynamicValue) {
		String locator = String.format(HotelBookingPageUIs.VIEW_BY_CONTENT_DESC, (Object[]) dynamicValue);
		List<AndroidElement> listElements = driver.findElements(By.xpath(locator));
		return listElements.size();
	}
	
	public boolean isDynamicTextViewDisplayed(String dynamicTextValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		return isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

	}
	
	public void clickToDynamicTextView(String dynamicTextValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		clickToElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

	}
	
	public List<String> getServicesOfHotelByID(String... dynamicID){
		List<String> listService = new ArrayList<String>();
		String locator = String.format(HotelBookingPageUIs.TEXTVIEW_BY_LINEAR_LAYOUT_ID, (Object[]) dynamicID);
		List<AndroidElement> listElements = driver.findElements(By.xpath(locator));
		for(AndroidElement element : listElements) {
			listService.add(element.getText());
		}
		return listService;
		
	} 
	
	public boolean checkSelectedService(String... dynamicValue) {
		String locator = String.format(HotelBookingPageUIs.TEXTVIEW_BY_TEXT, (Object[]) dynamicValue);
		AndroidElement element = driver.findElement(By.xpath(locator));
		return Boolean.parseBoolean(element.getAttribute("selected"));
		
	}
	
	public boolean checkFilerHotelByDictrict(List<HotelBookingInfo> listHotelInfo, String expectDictrict) {
		for (HotelBookingInfo info : listHotelInfo) {
			if (!info.hotelAddress.contains(expectDictrict)) {
				return false;
			}
		}
		return true;
		
	}
	
	public boolean checkFilerHotelByPrice(List<HotelBookingInfo> listHotelInfo, String maxPrice, String minPrice) {
		for (HotelBookingInfo info : listHotelInfo) {
			if (( Integer.parseInt(info.price.replaceAll("\\D+","")) < Integer.parseInt(minPrice.replaceAll("\\D+","")) ) ||  ( Integer.parseInt(info.price.replaceAll("\\D+","")) > Integer.parseInt(maxPrice.replaceAll("\\D+","")) )) {
				return false;
			}
		}
		return true;
		
	}
	
	public void scrollIDownToText(String... dynamicValue) {
		Dimension size = driver.manage().window().getSize();
		int x = size.getWidth() / 2;
		int startY = (int) (size.getHeight() * 0.80);
		int endY = (int) (size.getHeight() * 0.30);
		TouchAction touch = new TouchAction(driver);
		String locator = String.format(HotelBookingPageUIs.TEXTVIEW_BY_TEXT, (Object[]) dynamicValue);
		for (int i = 0; i < 20; i++) {
			locator = String.format(locator, (Object[]) dynamicValue);
			overRideTimeOut(driver, 2);
			List<AndroidElement> elementsOne = driver.findElements(By.xpath(locator));
			overRideTimeOut(driver, Constants.LONG_TIME);
			if (elementsOne.size() > 0 && elementsOne.get(0).isDisplayed()) {
				break;
			} else {
				touch.longPress(PointOption.point(x, startY)).moveTo(PointOption.point(x, endY)).release().perform();

			}
		}
	}
	
	public void inputToDynamicInputBoxByID(String inputValue, String dynamicID) {
		waitForElementVisible(driver, HotelBookingPageUIs.INPUTBOX_BY_ID, dynamicID);
		sendKeyToElement(driver, HotelBookingPageUIs.INPUTBOX_BY_ID, inputValue, dynamicID);

	}
	
	public void clickToDateHotelBooking(String now, String startDate, String endDate) {	
		clickToDynamicTextView("Ngày đặt");
		String nowDay = now.split("/")[0].replace("0", "");
		String startDay = startDate.split("/")[0].replace("0", "");
		String endDay = endDate.split("/")[0].replace("0", "");
		
		String nowMonth = now.split("/")[1].replace("0", "");
		String startMonth = startDate.split("/")[1].replace("0", "");
		String endMonth = endDate.split("/")[1].replace("0", "");
		
		String endYear = endDate.split("/")[2];
		
		boolean check = false;
		if (!startMonth.equals(nowMonth)) {
			swipeElementToElementByText("Tháng " + startMonth + " " + endYear, "Ngày đặt");
			check = true;
		}
		clickToDynamicTextView(startDay);
		clickToDynamicTextView("Ngày trả");
		if (!endMonth.equals(nowMonth) && check == false) {
			swipeElementToElementByText("Tháng " + startMonth + " " + endYear, "Ngày đặt");
		}
		clickToDynamicTextView(endDay);
		
	}
	
}
