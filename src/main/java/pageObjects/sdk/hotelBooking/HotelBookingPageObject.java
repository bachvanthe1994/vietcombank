package pageObjects.sdk.hotelBooking;

import java.text.DateFormatSymbols;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import commons.AbstractPage;
import commons.Constants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;
import model.HotelBookingInfo;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.sdk.hotelBooking.HotelBookingPageUIs;

public class HotelBookingPageObject extends AbstractPage {

	public HotelBookingPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public String getDayCheckIn() {
		boolean status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, "com.VCB:id/wdFromDay");
		List<String> listText = new ArrayList<String>();
		String day = "";
		if (status) {
			listText = getTextInListElements(driver, HotelBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, "com.VCB:id/wdFromDay");
			for (String text : listText) {
				day = day + text;
			}
			day = day.replace("Ngày đặt", "");
		}
		return day;

	}

	public String getDayCheckOut() {
		boolean status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, "com.VCB:id/wdToDay");
		List<String> listText = new ArrayList<String>();
		String day = "";
		if (status) {
			listText = getTextInListElements(driver, HotelBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, "com.VCB:id/wdToDay");
			for (String text : listText) {
				day = day + text;
			}
			day = day.replace("Ngày trả", "");
		}
		return day;

	}

	public String getPassengerAndRoom() {
		boolean status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, "com.VCB:id/llSelectPassengerAndRoom");
		List<String> listText = new ArrayList<String>();
		String passengerAndRoom = "";
		if (status) {
			listText = getTextInListElements(driver, HotelBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, "com.VCB:id/llSelectPassengerAndRoom");
			for (String text : listText) {
				passengerAndRoom = passengerAndRoom + text;
			}

		}
		return passengerAndRoom;

	}

	public String convertVietNameseStringToString(String vietnameseString) {
		String temp = Normalizer.normalize(vietnameseString, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}

	public boolean checkSuggestLocation(List<String> listSuggestLocations, String checkedValue) {
		for (String location : listSuggestLocations) {
			if (!convertVietNameseStringToString(location).toLowerCase().contains(checkedValue)) {
				return false;
			}
		}
		return true;

	}

	public boolean checkDateSelected(String dynamicValue) {
		waitForElementVisible(driver, HotelBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicValue);

		String locator = String.format(HotelBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicValue);

		AndroidElement element = (AndroidElement) driver.findElement(By.xpath(locator));

		return Boolean.parseBoolean(element.getAttribute("selected"));

	}

	public void handleSeekBarPrice(int endPoint1, int startPoint, int endPoint2) {
		WebElement seekBar = driver.findElement(By.id("com.VCB:id/rangeSeekbar"));

		int x = seekBar.getLocation().getX();
		int y = seekBar.getLocation().getY();
		float end = x + seekBar.getSize().getWidth();

		TouchAction action = new TouchAction(driver);

		int moveToMin = (int) (end * endPoint1 / 15000000);
		int startMax = (int) (end * startPoint / 15000000);
		int moveToMax = (int) (end * endPoint2 / 15000000);

		action.longPress(PointOption.point(x, y)).moveTo(PointOption.point(moveToMin, y)).release().perform();

		action.longPress(PointOption.point(startMax, y)).moveTo(PointOption.point(moveToMax, y)).release().perform();

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
			numberCheck++;

		}

		return true;
	}

	public void chooseStarRateHotel(int star) {
		List<String> numberList = getListTextViewByLinearLayoutID(driver, "com.VCB:id/llStars");
		clickToDynamicButtonLinkOrLinkText(driver, numberList.get(star - 1));
	}

	public boolean checkSelectedStarRate(int star) {
		List<String> starList = getSelectedInListElements(driver, HotelBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, "com.VCB:id/llStars");
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

	public void horizontalSwipeByPercentage(double startPercentage, double endPercentage, double anchorPercentage) {
		Dimension size = driver.manage().window().getSize();
		int anchor = (int) (size.height * anchorPercentage);
		int startPoint = (int) (size.width * startPercentage);
		int endPoint = (int) (size.width * endPercentage);

		new TouchAction(driver).longPress(PointOption.point(startPoint, anchor)).moveTo(PointOption.point(endPoint, anchor)).release().perform();
	}

	public void verticalSwipeByPercentage(double startPercentage, double endPercentage, double anchorPercentage) {
		Dimension size = driver.manage().window().getSize();
		int anchor = (int) (size.width * anchorPercentage);
		int startPoint = (int) (size.height * startPercentage);
		int endPoint = (int) (size.height * endPercentage);

		new TouchAction(driver).longPress(PointOption.point(anchor, startPoint)).moveTo(PointOption.point(anchor, endPoint)).release().perform();
	}

	public void swipeElementToElementByText(String textStart, String textEnd) {
		Dimension size = driver.manage().window().getSize();
		String locatorStart = String.format(HotelBookingPageUIs.TEXTVIEW_BY_TEXT, textStart);
		MobileElement elementStart = driver.findElement(By.xpath(locatorStart));

		String locatorEnd = String.format(HotelBookingPageUIs.TEXTVIEW_BY_TEXT, textEnd);
		MobileElement elementEnd = driver.findElement(By.xpath(locatorEnd));

		int xStart = elementStart.getLocation().getX();
		int yStart = elementStart.getLocation().getY();

		int xEnd = elementEnd.getLocation().getX();
		int yEnd = elementEnd.getLocation().getY();

		new TouchAction(driver).longPress(PointOption.point(xStart, yStart)).moveTo(PointOption.point(xEnd, yEnd)).release().perform();
	}

	public List<HotelBookingInfo> getListHotelBookingHistory() {
		List<HotelBookingInfo> listHotelBookingInfo = new ArrayList<HotelBookingInfo>();
		List<String> listPayCode = new ArrayList<String>();

		for (int i = 0; i <= 30; i++) {
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

	public HotelBookingInfo getHotelBookingHistoryByStatus(List<HotelBookingInfo> listHotel, String statusFilter) {
		return listHotel.stream().filter(p -> p.status.equals(statusFilter)).collect(Collectors.toList()).get(0);
	}

	public List<String> getListOfStatusHotelBooking(AppiumDriver<MobileElement> driver, String dynamicID) {
		waitForElementVisible(driver, HotelBookingPageUIs.TEXTVIEW_BY_LISTVIEW, dynamicID);
		return getTextInListElements(driver, HotelBookingPageUIs.TEXTVIEW_BY_LISTVIEW, dynamicID);

	}

	public List<HotelBookingInfo> getListHotelRecentView() {
		List<HotelBookingInfo> listHotelBookingInfo = new ArrayList<HotelBookingInfo>();
		List<String> listHotelName = new ArrayList<String>();

		for (int i = 0; i <= 30; i++) {
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

		for (int i = 0; i <= 1; i++) {
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
			waitForElementVisible(driver, HotelBookingPageUIs.TEXTVIEW_BY_TEXT, info.hotelName);
			navigateBack(driver);

		}
	}

	public int getNumberOfHotelInMap(String... dynamicValue) {
		String locator = String.format(HotelBookingPageUIs.VIEW_BY_CONTENT_DESC, (Object[]) dynamicValue);
		List<MobileElement> listElements = driver.findElements(By.xpath(locator));
		return listElements.size();
	}

	public boolean isDynamicTextViewDisplayed(String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, HotelBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicTextValue);
		if (status) {
			isDisplayed = isControlDisplayed(driver, HotelBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicTextValue);
		}
		return isDisplayed;

	}

	public boolean isDynamicTextViewDisplayedByID(String dynamicTextID) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, HotelBookingPageUIs.TEXTVIEW_BY_ID, dynamicTextID);
		if (status) {
			isDisplayed = isControlDisplayed(driver, HotelBookingPageUIs.TEXTVIEW_BY_ID, dynamicTextID);
		}
		return isDisplayed;

	}

	public boolean isDynamicInputBoxByTextDisPlayed(String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, HotelBookingPageUIs.INPUT_BOX_BY_TEXT, dynamicTextValue);
		if (status) {
			isDisplayed = isControlDisplayed(driver, HotelBookingPageUIs.INPUT_BOX_BY_TEXT, dynamicTextValue);
		}
		return isDisplayed;

	}

	public void clickToDynamicTextView(String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, HotelBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicTextValue);
		status = waitForElementVisible(driver, HotelBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicTextValue);
		if (status) {
			clickToElement(driver, HotelBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicTextValue);
		}

	}

	public void clickToDynamicTextViewByID(String dynamicTextID) {
		boolean status = false;
		scrollIDown(driver, HotelBookingPageUIs.TEXTVIEW_BY_ID, dynamicTextID);
		status = waitForElementVisible(driver, HotelBookingPageUIs.TEXTVIEW_BY_ID, dynamicTextID);
		if (status) {
			clickToElement(driver, HotelBookingPageUIs.TEXTVIEW_BY_ID, dynamicTextID);
		}

	}
	
	// Click vào ô dropdown, và ô date time , tham số truyền vào là resource id
	public void clickToDynamicDropdownAndDateTimePicker(String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		}

	}
	
	// Click vào menu tại bottom hoặc icon đóng k chứa text, tham số truyền vào là resource id
	public void clickToDynamicImageViewByID(String dynamicID) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		}
	}

	public List<String> getServicesOfHotelByID(String... dynamicID) {
		List<String> listService = new ArrayList<String>();
		String locator = String.format(HotelBookingPageUIs.TEXTVIEW_BY_LINEAR_LAYOUT_ID, (Object[]) dynamicID);
		List<MobileElement> listElements = driver.findElements(By.xpath(locator));
		for (MobileElement element : listElements) {
			listService.add(element.getText());
		}
		return listService;

	}

	public boolean checkSelectedService(String... dynamicValue) {
		String locator = String.format(HotelBookingPageUIs.TEXTVIEW_BY_TEXT, (Object[]) dynamicValue);
		MobileElement element = driver.findElement(By.xpath(locator));
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
			if ((Integer.parseInt(info.price.replaceAll("\\D+", "")) < Integer.parseInt(minPrice.replaceAll("\\D+", ""))) || (Integer.parseInt(info.price.replaceAll("\\D+", "")) > Integer.parseInt(maxPrice.replaceAll("\\D+", "")))) {
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
			List<MobileElement> elementsOne = driver.findElements(By.xpath(locator));
			overRideTimeOut(driver, Constants.LONG_TIME);
			if (elementsOne.size() > 0 && elementsOne.get(0).isDisplayed()) {
				break;
			} else {
				touch.longPress(PointOption.point(x, startY)).moveTo(PointOption.point(x, endY)).release().perform();

			}
		}
	}

	public void inputToDynamicInputBoxByID(String inputValue, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, HotelBookingPageUIs.INPUTBOX_BY_ID, dynamicID);
		if (status) {
			sendKeyToElement(driver, HotelBookingPageUIs.INPUTBOX_BY_ID, inputValue, dynamicID);
		}
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

	public void clickToDetailButtonByPayCode(String paycode) {
		scrollIDown(driver, HotelBookingPageUIs.DETAIL_BUTTON_BY_PAYCODE, paycode);
		waitForElementVisible(driver, HotelBookingPageUIs.DETAIL_BUTTON_BY_PAYCODE, paycode);
		clickToElement(driver, HotelBookingPageUIs.DETAIL_BUTTON_BY_PAYCODE, paycode);

	}

	public void waitForTextViewDisplay(String textView) {
		waitForElementVisible(driver, HotelBookingPageUIs.TEXTVIEW_BY_TEXT, textView);

	}

	public String getTextViewByID(String dynamicID) {
		waitForElementVisible(driver, HotelBookingPageUIs.TEXTVIEW_BY_ID, dynamicID);
		return getTextElement(driver, HotelBookingPageUIs.TEXTVIEW_BY_ID, dynamicID);

	}

	public String getTextInEditTextFieldByResourceID(String... dynamicID) {
		waitForElementVisible(driver, HotelBookingPageUIs.INPUTBOX_BY_ID, dynamicID);
		return getTextElement(driver, HotelBookingPageUIs.INPUTBOX_BY_ID, dynamicID);

	}

	public void clickToDynamicTextOrButtonLink(String dynamicValue) {
		boolean status = false;
		scrollIDown(driver, HotelBookingPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, dynamicValue);
		status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, dynamicValue);
		if (status) {
			clickToElement(driver, HotelBookingPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, dynamicValue);
		}
	}

	public void clickToDynamicButton(String dynamicValue) {
		boolean status = false;
		scrollIDown(driver, HotelBookingPageUIs.DYNAMIC_BUTTON, dynamicValue);
		status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_BUTTON, dynamicValue);
		if (status) {
			clickToElement(driver, HotelBookingPageUIs.DYNAMIC_BUTTON, dynamicValue);
		}
	}

	public boolean isDynamicMessageAndLabelTextDisplayed(String text) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, text);
		if (status) {
			isDisplayed = isControlDisplayed(driver, HotelBookingPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, text);
		}
		return isDisplayed;
	}

	public void clickToDynamicBackIcon(String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, HotelBookingPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		if (status) {
			clickToElement(driver, HotelBookingPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		}
	}

	public boolean isDynamicTextInInputBoxDisPlayed(String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		if (status) {
			isDisplayed = isControlDisplayed(driver, HotelBookingPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		}
		return isDisplayed;
	}

	public void clickToTextViewByLinearLayoutID(String... dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		if (status) {
			clickToElement(driver, HotelBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		}
	}

	public void inputIntoEditTextByID(String inputValue, String... dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, HotelBookingPageUIs.INPUTBOX_BY_ID, dynamicID);
		if (status) {
			sendKeyToElement(driver, HotelBookingPageUIs.INPUTBOX_BY_ID, inputValue, dynamicID);
		}
	}

	public void inputToDynamicPopupPasswordInput(String inputValue, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_PASSWORD_INPUT, dynamicTextValue);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_PASSWORD_INPUT, dynamicTextValue);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_PASSWORD_INPUT, inputValue, dynamicTextValue);
		}
	}
	
	public String getDynamicTextInTransactionDetail(String dynamicTextValue) {
		String text = null;
		scrollIDown(driver, HotelBookingPageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);
		boolean status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);
		if (status) {
			text = getTextElement(driver, HotelBookingPageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);
		}
		return text;
	}

	public List<String> getListOfSuggestedMoneyOrListText(String dynamicID) {
		List<String> listString = new ArrayList<String>();
		boolean status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		if (status) {
			listString = getTextInListElements(driver, HotelBookingPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		}
		return listString;
	}

	public String getTextInEditTextFieldByID(String... dynamicID) {
		String text = null;
		scrollIDown(driver, HotelBookingPageUIs.INPUTBOX_BY_ID, dynamicID);
		boolean status = waitForElementVisible(driver, HotelBookingPageUIs.INPUTBOX_BY_ID, dynamicID);
		if (status) {
			text = getTextElement(driver, HotelBookingPageUIs.INPUTBOX_BY_ID, dynamicID);
		}
		return text;

	}

	public String getTextTextViewByLinearLayoutID(String... dynamicID) {
		String text = null;
		scrollIDown(driver, HotelBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		boolean status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		if (status) {
			text = getTextElement(driver, HotelBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		}
		return text;

	}

	public void clickToDynamicBottomMenuOrCloseIcon(String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		if (status) {
			clickToElement(driver, HotelBookingPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		}
	}

	public String getTextInDynamicDropdownOrDateTimePicker(String dynamicID) {
		String text = null;
		scrollIDown(driver, HotelBookingPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		boolean status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		if (status) {
			text = getTextElement(driver, HotelBookingPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		}
		return text;
	}

	public String getTextInDynamicPopup(String dynamicResourceID) {
		String text = null;
		scrollIDown(driver, HotelBookingPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicResourceID);
		boolean status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicResourceID);
		if (status) {
			text = getTextElement(driver, HotelBookingPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicResourceID);
		}
		return text;

	}

	public void clickToDynamicInput(String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, HotelBookingPageUIs.INPUT_BOX_BY_TEXT, dynamicTextValue);
		if (status) {
			clickToElement(driver, HotelBookingPageUIs.INPUT_BOX_BY_TEXT, dynamicTextValue);
		}

	}

	public void inputToDynamicInputBox(String inputValue, String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, HotelBookingPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		if (status) {
			sendKeyToElement(driver, HotelBookingPageUIs.DYNAMIC_INPUT_BOX, inputValue, dynamicTextValue);
		}

	}

	public void clickToDynamicDropDown(String dymanicText) {
		boolean status = false;
		scrollIDown(driver, HotelBookingPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dymanicText);
		status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dymanicText);
		if (status) {
			clickToElement(driver, HotelBookingPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dymanicText);
		}

	}

	public boolean isDynamicButtonDisplayed(String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, HotelBookingPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		boolean status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status) {
			isDisplayed = isControlDisplayed(driver, HotelBookingPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
		return isDisplayed;

	}

	public void inputToDynamicOtpOrPIN(String inputValue, String dynamicTextValue) {
		boolean status = false;
		clearText(driver, HotelBookingPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
		status = waitForElementVisible(driver, HotelBookingPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
		if (status) {
			setValueToElement(driver, HotelBookingPageUIs.DYNAMIC_OTP_INPUT, inputValue, dynamicTextValue);
		}

	}

	/* SCROLL DOWN */
	public void scrollDownToText(String dynamicText) {
		scrollIDown(driver, HotelBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicText);

	}
	
	/* SCROLL UP */
	public void scrollUpToText(AppiumDriver<MobileElement> driver, String dynamicText) {
		scrollUp(driver, HotelBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicText);

	}
	
	public String getTransferTimeSuccess(String textSuccess) {
		String transferTime = "";
		transferTime = getDynamicTransferTimeAndMoney(driver, textSuccess, "4");

		if (transferTime.equals("") || transferTime == null) {
			Locale locale = new Locale("en", "UK");
			DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
			dateFormatSymbols.setWeekdays(new String[] { "Unused", "Chủ Nhật", "Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy", });

			String pattern = "HH:mm EEEEE dd/MM/yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, dateFormatSymbols);
			String date = simpleDateFormat.format(new Date());
			transferTime = date;

		}

		return transferTime;
	}
	
}
