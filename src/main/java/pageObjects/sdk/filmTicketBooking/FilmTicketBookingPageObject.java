package pageObjects.sdk.filmTicketBooking;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import commons.AbstractPage;
import commons.Constants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import model.FilmInfo;
import model.FilmInfo.TypeShow;
import model.SeatType;
import model.SeatType.TypeButton;
import vietcombankUI.sdk.filmTicketBooking.FilmTicketBookingPageUIs;

public class FilmTicketBookingPageObject extends AbstractPage {

	public FilmTicketBookingPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public boolean isDynamicTextViewDisplayed(String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicTextValue);
		if (status) {
			isDisplayed = isControlDisplayed(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicTextValue);
		}
		return isDisplayed;

	}

	public boolean isDynamicTextViewDisplayedByID(String dynamicTextID) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, dynamicTextID);
		if (status) {
			isDisplayed = isControlDisplayed(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, dynamicTextID);
		}
		return isDisplayed;

	}

	public boolean isDynamicInputBoxByTextDisPlayed(String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.INPUT_BOX_BY_TEXT, dynamicTextValue);
		if (status) {
			isDisplayed = isControlDisplayed(driver, FilmTicketBookingPageUIs.INPUT_BOX_BY_TEXT, dynamicTextValue);
		}
		return isDisplayed;
	}

	public boolean isDynamicImageViewDisplayed(String dynamicID) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_IMAGE_VIEW_BY_ID, dynamicID);
		if (status) {
			isDisplayed = isControlDisplayed(driver, FilmTicketBookingPageUIs.DYNAMIC_IMAGE_VIEW_BY_ID, dynamicID);
		}
		return isDisplayed;

	}

	public boolean isDynamicTextViewSelected(String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicTextValue);
		if (status) {
			isDisplayed = isControlForcus(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicTextValue);
		}
		return isDisplayed;

	}

	public boolean isDynamicTextViewByViewGroupID(String... dynamicID) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_TEXT_VIEW_BY_VIEW_GROUP_ID, dynamicID);
		if (status) {
			isDisplayed = isControlDisplayed(driver, FilmTicketBookingPageUIs.DYNAMIC_TEXT_VIEW_BY_VIEW_GROUP_ID, dynamicID);
		}
		return isDisplayed;

	}

	public void clickToTextViewByText(String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicTextValue);
		if (status) {
			clickToElement(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicTextValue);
		}

	}

	public void clickToDynamicTextView(String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicTextValue);
		status = waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicTextValue);
		if (status) {
			clickToElement(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicTextValue);
		}

	}

	public void clickToDynamicTextViewByID(String dynamicTextID) {
		boolean status = false;
		scrollIDown(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, dynamicTextID);
		status = waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, dynamicTextID);
		if (status) {
			clickToElement(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, dynamicTextID);
		}
	}

	public void clickToDynamicTextViewByViewGroupID(String... dynamicValue) {
		boolean status = false;
		scrollIDown(driver, FilmTicketBookingPageUIs.DYNAMIC_TEXT_VIEW_BY_VIEW_GROUP_ID, dynamicValue);
		status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_TEXT_VIEW_BY_VIEW_GROUP_ID, dynamicValue);
		if (status) {
			clickToElement(driver, FilmTicketBookingPageUIs.DYNAMIC_TEXT_VIEW_BY_VIEW_GROUP_ID, dynamicValue);
		}

	}

	public void scrollIDownToText(String... dynamicValue) {
		Dimension size = driver.manage().window().getSize();
		int x = size.getWidth() / 2;
		int startY = (int) (size.getHeight() * 0.80);
		int endY = (int) (size.getHeight() * 0.30);
		TouchAction touch = new TouchAction(driver);
		String locator = String.format(FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, (Object[]) dynamicValue);
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
		status = waitForElementVisible(driver, FilmTicketBookingPageUIs.INPUTBOX_BY_ID, dynamicID);
		if (status) {
			sendKeyToElement(driver, FilmTicketBookingPageUIs.INPUTBOX_BY_ID, inputValue, dynamicID);
		}

	}

	public void swipeElementToElementByText(String textStart, String textEnd) {
		Dimension size = driver.manage().window().getSize();
		String locatorStart = String.format(FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, textStart);
		MobileElement elementStart = driver.findElement(By.xpath(locatorStart));

		String locatorEnd = String.format(FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, textEnd);
		MobileElement elementEnd = driver.findElement(By.xpath(locatorEnd));

		int xStart = elementStart.getLocation().getX();
		int yStart = elementStart.getLocation().getY();

		int xEnd = elementEnd.getLocation().getX();
		int yEnd = elementEnd.getLocation().getY();

		new TouchAction(driver).longPress(PointOption.point(xStart, yStart)).moveTo(PointOption.point(xEnd, yEnd)).release().perform();
	}

	public void swipeElementToElement(String locatorStart, String locatorEnd, String dynamicValueStart, String dynamicValueEnd) {
		Dimension size = driver.manage().window().getSize();
		locatorStart = String.format(locatorStart, dynamicValueStart);
		MobileElement elementStart = driver.findElement(By.xpath(locatorStart));

		locatorEnd = String.format(locatorEnd, dynamicValueEnd);
		MobileElement elementEnd = driver.findElement(By.xpath(locatorEnd));

		int xStart = elementStart.getLocation().getX();
		int yStart = elementStart.getLocation().getY();

		int xEnd = elementEnd.getLocation().getX();
		int yEnd = elementEnd.getLocation().getY();

		new TouchAction(driver).longPress(PointOption.point(xStart, yStart)).moveTo(PointOption.point(xEnd, yEnd)).release().perform();
	}

	public void horizontalSwipeByPercentage(double startPercentage, double endPercentage, double anchorPercentage) {
		Dimension size = driver.manage().window().getSize();
		int anchor = (int) (size.height * anchorPercentage);
		int startPoint = (int) (size.width * startPercentage);
		int endPoint = (int) (size.width * endPercentage);

		new TouchAction(driver).longPress(PointOption.point(startPoint, anchor)).moveTo(PointOption.point(endPoint, anchor)).release().perform();
	}

	public void swipeToTheEndDate(int numberOfSwipe) {
		while (numberOfSwipe > 0) {
			horizontalSwipeByPercentage(0.9, 0.1, 0.18);
			numberOfSwipe--;
		}

	}

	public void waitForTextViewDisplay(String textView) {
		waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, textView);

	}

	public String getTextViewByID(String dynamicID) {
		waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, dynamicID);
		return getTextElement(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, dynamicID);

	}

	public String getTextInEditTextFieldByResourceID(String... dynamicID) {
		waitForElementVisible(driver, FilmTicketBookingPageUIs.INPUTBOX_BY_ID, dynamicID);
		return getTextElement(driver, FilmTicketBookingPageUIs.INPUTBOX_BY_ID, dynamicID);

	}

	public void clickToDynamicTextOrButtonLink(String dynamicValue) {
		boolean status = false;
		scrollIDown(driver, FilmTicketBookingPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, dynamicValue);
		status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, dynamicValue);
		if (status) {
			clickToElement(driver, FilmTicketBookingPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, dynamicValue);
		}
	}

	public void clickToDynamicButton(String dynamicValue) {
		boolean status = false;
		scrollIDown(driver, FilmTicketBookingPageUIs.DYNAMIC_BUTTON, dynamicValue);
		status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_BUTTON, dynamicValue);
		if (status) {
			clickToElement(driver, FilmTicketBookingPageUIs.DYNAMIC_BUTTON, dynamicValue);
		}
	}

	public boolean isDynamicMessageAndLabelTextDisplayed(String text) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, text);
		if (status) {
			isDisplayed = isControlDisplayed(driver, FilmTicketBookingPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, text);
		}
		return isDisplayed;
	}

	public void clickToDynamicBackIcon(String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, FilmTicketBookingPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		if (status) {
			clickToElement(driver, FilmTicketBookingPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		}
	}

	public boolean isDynamicTextInInputBoxDisPlayed(String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		if (status) {
			isDisplayed = isControlDisplayed(driver, FilmTicketBookingPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		}
		return isDisplayed;
	}

	public void clickToTextViewByLinearLayoutID(String... dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		if (status) {
			clickToElement(driver, FilmTicketBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		}
	}

	public void inputIntoEditTextByID(String inputValue, String... dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, FilmTicketBookingPageUIs.INPUTBOX_BY_ID, dynamicID);
		if (status) {
			sendKeyToElement(driver, FilmTicketBookingPageUIs.INPUTBOX_BY_ID, inputValue, dynamicID);
		}
	}

	public String getDynamicTextInTransactionDetail(String dynamicTextValue) {
		String text = null;
		scrollIDown(driver, FilmTicketBookingPageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);
		if (status) {
			text = getTextElement(driver, FilmTicketBookingPageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);
		}
		return text;
	}
	
	public String getDynamicTextViewByViewGroupID(String... dynamicTextValue) {
		String text = null;
		scrollIDown(driver, FilmTicketBookingPageUIs.DYNAMIC_TEXT_VIEW_BY_VIEW_GROUP_ID, dynamicTextValue);
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_TEXT_VIEW_BY_VIEW_GROUP_ID, dynamicTextValue);
		if (status) {
			text = getTextElement(driver, FilmTicketBookingPageUIs.DYNAMIC_TEXT_VIEW_BY_VIEW_GROUP_ID, dynamicTextValue);
		}
		return text;
	}

	public List<String> getListOfSuggestedMoneyOrListText(String dynamicID) {
		List<String> listString = new ArrayList<String>();
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		if (status) {
			listString = getTextInListElements(driver, FilmTicketBookingPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		}
		return listString;
	}

	public String getTextInEditTextFieldByID(String... dynamicID) {
		String text = null;
		scrollIDown(driver, FilmTicketBookingPageUIs.INPUTBOX_BY_ID, dynamicID);
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.INPUTBOX_BY_ID, dynamicID);
		if (status) {
			text = getTextElement(driver, FilmTicketBookingPageUIs.INPUTBOX_BY_ID, dynamicID);
		}
		return text;

	}

	public String getTextTextViewByLinearLayoutID(String... dynamicID) {
		String text = null;
		scrollIDown(driver, FilmTicketBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		if (status) {
			text = getTextElement(driver, FilmTicketBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		}
		return text;

	}

	public void clickToDynamicImageViewByID(String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_IMAGE_VIEW_BY_ID, dynamicID);
		if (status) {
			clickToElement(driver, FilmTicketBookingPageUIs.DYNAMIC_IMAGE_VIEW_BY_ID, dynamicID);
		}
	}

	public String getTextInDynamicDropdownOrDateTimePicker(String dynamicID) {
		String text = null;
		scrollIDown(driver, FilmTicketBookingPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		if (status) {
			text = getTextElement(driver, FilmTicketBookingPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		}
		return text;
	}

	public String getTextInDynamicPopup(String dynamicResourceID) {
		String text = null;
		scrollIDown(driver, FilmTicketBookingPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicResourceID);
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicResourceID);
		if (status) {
			text = getTextElement(driver, FilmTicketBookingPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicResourceID);
		}
		return text;

	}

	public void clickToDynamicInput(String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, FilmTicketBookingPageUIs.INPUT_BOX_BY_TEXT, dynamicTextValue);
		if (status) {
			clickToElement(driver, FilmTicketBookingPageUIs.INPUT_BOX_BY_TEXT, dynamicTextValue);
		}

	}

	public void inputToDynamicInputBox(String inputValue, String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, FilmTicketBookingPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		if (status) {
			sendKeyToElement(driver, FilmTicketBookingPageUIs.DYNAMIC_INPUT_BOX, inputValue, dynamicTextValue);
		}

	}

	public void clickToDynamicDropDown(String dymanicText) {
		boolean status = false;
		scrollIDown(driver, FilmTicketBookingPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dymanicText);
		status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dymanicText);
		if (status) {
			clickToElement(driver, FilmTicketBookingPageUIs.DYNAMIC_DROPDOWN_BY_LABEL, dymanicText);
		}

	}

	public boolean isDynamicButtonDisplayed(String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, FilmTicketBookingPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status) {
			isDisplayed = isControlDisplayed(driver, FilmTicketBookingPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
		return isDisplayed;

	}

	public void inputToDynamicOtpOrPIN(String inputValue, String dynamicTextValue) {
		boolean status = false;
		clearText(driver, FilmTicketBookingPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
		status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
		if (status) {
			setValueToElement(driver, FilmTicketBookingPageUIs.DYNAMIC_OTP_INPUT, inputValue, dynamicTextValue);
		}

	}

	public boolean checkSwitchInGlobalSetting(String dynamicTextValue) {
		boolean result = false;
		scrollIDown(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, "Vị trí");
		result = isControlSelected(driver, FilmTicketBookingPageUIs.DYNAMIC_SWITCH_SETTING_BY_TEXT, dynamicTextValue);
		return result;

	}

	public void turnOnSwitchInGlobalSetting(String dynamicTextValue) {
		if (checkSwitchInGlobalSetting(dynamicTextValue) == false) {
			clickToElement(driver, FilmTicketBookingPageUIs.DYNAMIC_SWITCH_SETTING_BY_TEXT, dynamicTextValue);
		}
	}

	public void turnOffSwitchInGlobalSetting(String dynamicTextValue) {
		if (checkSwitchInGlobalSetting(dynamicTextValue)) {
			clickToElement(driver, FilmTicketBookingPageUIs.DYNAMIC_SWITCH_SETTING_BY_TEXT, dynamicTextValue);
		}
	}

	public String getLocationByCheckSwitchLocationInGlobalSetting(boolean check) {
		String location = "";
		if (check) {
			location = "Hà Nội";
		} else {
			location = "Hồ Chí Minh";
		}
		return location;

	}

	public List<String> getListCity() {
		List<String> listCity = new ArrayList<String>();
		List<String> tempList1 = new ArrayList<String>();
		List<String> tempList2 = new ArrayList<String>();
		boolean check = true;

		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvTitle");
		if (status) {
			tempList1 = getTextInListElements(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvTitle");
			tempList2 = getTextInListElements(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvTitle");
			while (check) {
				for (String text : tempList1) {
					if (!listCity.contains(text)) {
						listCity.add(text);
					}

				}
				swipeElementToElement(FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, FilmTicketBookingPageUIs.INPUT_BOX_BY_TEXT, tempList1.get(tempList1.size() - 1), "Tìm kiếm");
				tempList1 = getTextInListElements(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvTitle");
				if (tempList1.equals(tempList2)) {
					break;
				} else {
					tempList2 = tempList1;
				}
			}
		}

		return listCity;

	}

	public boolean checkIconAndCinemaName(List<String> listCinemaGroup) {
		boolean result = true;
		int countList = listCinemaGroup.size();
		String locator = String.format(FilmTicketBookingPageUIs.DYNAMIC_IMAGE_VIEW_BY_ID, "com.VCB:id/ivArrow");
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_IMAGE_VIEW_BY_ID, "com.VCB:id/ivArrow");
		List<MobileElement> elements = null;
		if (status) {
			elements = driver.findElements(By.xpath(locator));
		}

		for (int i = 0; i < countList; i++) {
			elements.get(i).click();
			result = waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvNameCinema");
		}
		return result;

	}

	public boolean checkIconAndCinemaNameAndInfo(List<String> listCinemaGroup) {
		boolean result = true;
		int countList = listCinemaGroup.size();
		String locator = String.format(FilmTicketBookingPageUIs.DYNAMIC_IMAGE_VIEW_BY_ID, "com.VCB:id/ivArrow");
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_IMAGE_VIEW_BY_ID, "com.VCB:id/ivArrow");
		List<MobileElement> elements = null;
		if (status) {
			elements = driver.findElements(By.xpath(locator));
		}

		for (int i = 0; i < countList; i++) {
			elements.get(i).click();
			result = waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvCinemaName") && waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvCinemaAddress") && (isDynamicTextViewDisplayed("2D") || isDynamicTextViewDisplayed("3D")) && isDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		}
		return result;

	}

	public String getDuration(String dateStart, String dateStop) {
		String result = "";
		SimpleDateFormat format = new SimpleDateFormat("HH:mm - dd/MM/yyyy");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			long diff = d2.getTime() - d1.getTime();
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;

			result = diffHours + "h" + diffMinutes + "'";
		} catch (Exception e) {

		}
		return result;
	}

	public FilmInfo getInfoOfTheFirstFilm() {
		String name = getTextViewByID("com.VCB:id/tvFilmName");
		String category = getTextViewByID("com.VCB:id/tvCategory");
		String duration = getTextViewByID("com.VCB:id/tvDuration");
		String rate = getTextViewByID("com.VCB:id/tvIMDb");
		FilmInfo info = new FilmInfo(name, category, duration, rate);
		return info;

	}

	public String canculateDurationOfFilm(String duration) {
		String result = "";
		String hour = "";
		String minute = "";
		if (duration.contains("giờ")) {
			hour = duration.split(" ")[0];
			minute = duration.split(" ")[2];
			result = Integer.parseInt(hour) * 60 + Integer.parseInt(minute) + " phút";
		} else {
			result = duration;
		}
		return result;

	}

	public List<String> getListFilmSchedule() {
		List<String> listSchedule = new ArrayList<String>();
		for (int i = 0; i <= 6; i = i + 2) {
			String locator = String.format(FilmTicketBookingPageUIs.DYNAMIC_TEXT_VIEW_LINEAR_LAYOUT_BY_LINEAER_LAYOUT_ID, "com.VCB:id/llDate", String.valueOf(i));
			boolean status = waitForElementVisible(driver, locator);
			if (status) {
				List<MobileElement> listElements = driver.findElements(By.xpath(locator));
				String textDate = "";
				for (MobileElement element : listElements) {
					textDate = textDate + element.getText();
				}
				listSchedule.add(textDate);
			}
		}
		return listSchedule;

	}

	public List<SeatType> getListSeatType() {
		List<SeatType> listSeatType = new ArrayList<SeatType>();
		List<String> listSeatName = getTextInListElements(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvTypeName");
		List<String> listSeatPrice = getTextInListElements(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvAmount");
		List<String> listSeatNumber = getTextInListElements(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvNumber");

		int count = listSeatName.size();
		for (int i = 0; i < count; i++) {
			SeatType seat = new SeatType(listSeatName.get(i), listSeatPrice.get(i), listSeatNumber.get(i));
			listSeatType.add(seat);
		}
		return listSeatType;

	}

	public boolean checkSeatTypeDefault(List<SeatType> listSeatType) {
		boolean result = true;
		for (SeatType seat : listSeatType) {
			if (!seat.number.equals("0")) {
				return false;
			}
		}

		return result;
	}

	public void clickToChangeNumberSeat(TypeButton type, int numberClick) {
		if (type == TypeButton.INCREASE) {
			String locator = String.format(FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvPlus");

			boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvPlus");
			List<MobileElement> elements = null;

			if (status) {
				elements = driver.findElements(By.xpath(locator));
			}

			int count = numberClick;
			for (MobileElement element : elements) {
				while (numberClick > 0) {
					element.click();
					numberClick--;
				}
				numberClick = count;
			}

		}

		else if (type == TypeButton.INCREASE) {
			String locator = String.format(FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvMinus");

			boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvMinus");
			List<MobileElement> elements = null;

			if (status) {
				elements = driver.findElements(By.xpath(locator));
			}

			for (MobileElement element : elements) {
				while (numberClick > 0) {
					element.click();
					numberClick--;
				}
			}

		}

	}

	public void clickToChooseEachTypeASeate() {
		String locator = String.format(FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvPlus");
		String locatorTypeOfSeats = String.format(FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvTypeName");
		
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvPlus");
		List<MobileElement> elementsPlus = null;
		List<MobileElement> elementsTypeSeats = null;

		if (status) {
			elementsPlus = driver.findElements(By.xpath(locator));
			elementsTypeSeats = driver.findElements(By.xpath(locatorTypeOfSeats));
		}

		int index = 0;
		for (MobileElement element : elementsPlus) {
			String typeSeats = elementsTypeSeats.get(index).getText().toLowerCase();
			if (typeSeats.contains("standard") || typeSeats.contains("deluxe") || typeSeats.contains("vip") || typeSeats.contains("couple")) {
				element.click();
			}
			index ++;	
		}

	}

	public void clickToChangeNumberSeatSum10Tickets() {
		String locator = String.format(FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvPlus");
		String locatorTypeOfSeats = String.format(FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvTypeName");
		
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvPlus");
		List<MobileElement> elements = null;
		List<MobileElement> elementsTypeSeats = null;
		
		if (status) {
			elements = driver.findElements(By.xpath(locator));
			elementsTypeSeats = driver.findElements(By.xpath(locatorTypeOfSeats));
			
			int countTypeSeat = elements.size();
			
			for (MobileElement elementsTypeSeat : elementsTypeSeats) {
				String typeSeat = elementsTypeSeat.getText().toLowerCase();
				if (!typeSeat.contains("standard") && !typeSeat.contains("deluxe") && !typeSeat.contains("vip") && !typeSeat.contains("couple")) {
					countTypeSeat--;
				}
			}
			
			int numberClickAll = 10 / countTypeSeat;
			int numberClickFirst = 10 % countTypeSeat;
			
			int index = 0;
			int count = numberClickAll;
			for (MobileElement elementsTypeSeat : elementsTypeSeats) {
				String typeSeat = elementsTypeSeat.getText().toLowerCase();
				if (typeSeat.contains("standard") || typeSeat.contains("deluxe") || typeSeat.contains("vip") || typeSeat.contains("couple")) {
					while (numberClickFirst > 0) {
						elements.get(index).click();
						numberClickFirst--;
					}
					while (numberClickAll > 0) {
						elements.get(index).click();
						numberClickAll--;
					}
					numberClickAll = count;
				}
			}
			
		}

	}
	
	public static String addCommasToLong(String number) {
		long amount = Long.parseLong(number);
		String m = String.format("%,d", amount);
		return m;
	}

	public String canculateAmountFilmBooking(List<SeatType> seats) {
		String result = "";
		int amount = 0;
		for (SeatType seat : seats) {
			amount += Integer.parseInt(seat.price.replaceAll("\\D+", "")) * Integer.parseInt(seat.number);
		}
		result = addCommasToLong(amount + "") + " VND";
		return result;
	}

	public boolean checkTypeShowFilmList(TypeShow typeShow) {
		boolean result = true;
		String locator = String.format(FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, "Đặt vé");

		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, "Đặt vé");
		List<MobileElement> elements = null;

		if (status) {
			elements = driver.findElements(By.xpath(locator));
		}

		int count = elements.size();

		if (typeShow == TypeShow.HORIZONTAL) {
			if (count == 1) {
				result = true;
			} else {
				result = false;
			}
		} else if (typeShow == TypeShow.VERTICAL) {
			if (count > 1) {
				result = true;
			} else {
				result = false;
			}
		}

		return result;
	}

	public String convertVietNameseStringToString(String vietnameseString) {
		String temp = Normalizer.normalize(vietnameseString, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}

	public boolean checkSeachFilm(List<String> listSuggestLocations, String checkedValue) {
		for (String location : listSuggestLocations) {
			if (!convertVietNameseStringToString(location).toLowerCase().contains(convertVietNameseStringToString(checkedValue).toLowerCase())) {
				return false;
			}
		}
		return true;

	}

	public boolean checkListContain(List<String> actualList, List<String> expectList) {
		return expectList.containsAll(actualList);

	}

	public int getRGBColor(Color c) {
		return c.getRGB();
	}

	public String getColorOfElement(String locator, String... dynamicValue) {
		String colorOfElement = "";
		locator = String.format(locator, (Object[]) dynamicValue);
		boolean status = waitForElementVisible(driver, locator);
		if (status) {
			MobileElement element = driver.findElement(By.xpath(locator));

			File imageFile = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);
			try {
				BufferedImage bufferedImage = ImageIO.read(imageFile);
				imageFile.delete();

				int height = bufferedImage.getHeight();
				int width = bufferedImage.getWidth();
				int x = width / 2;
				int y = height / 4;
				int RGBA = bufferedImage.getRGB(x, y);
				int red = (RGBA >> 16) & 255;
				int green = (RGBA >> 8) & 255;
				int blue = RGBA & 255;
				colorOfElement = "(" + red + "," + green + "," + blue + ")";

			} catch (Exception e) {

			}

		}
		return colorOfElement;

	}

	public boolean checkColorOfElement(String colorCheck, String locator, String... dynamicValue) {
		boolean colorOfElement = true;
		locator = String.format(locator, (Object[]) dynamicValue);
		boolean status = waitForElementVisible(driver, locator);
		if (status) {
			MobileElement element = driver.findElement(By.xpath(locator));

			File imageFile = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);
			try {
				BufferedImage bufferedImage = ImageIO.read(imageFile);
				imageFile.delete();

				int height = bufferedImage.getHeight();
				int width = bufferedImage.getWidth();
				int y = height / 4;

				for (int x = 0; x < width; x++) {
					int RGBA = bufferedImage.getRGB(x, y);
					int red = (RGBA >> 16) & 255;
					int green = (RGBA >> 8) & 255;
					int blue = RGBA & 255;
					if (colorCheck.equals("(" + red + "," + green + "," + blue + ")")) {
						return true;
					}
				}
				colorOfElement = false;

			} catch (Exception e) {

			}

		}
		return colorOfElement;

	}

	public boolean chooseSeatsAndCheckColorAfterChoose(int numberOfSeats, String colorOfSeat, String checkColor) {
		boolean result = true;
		String locator = String.format(FilmTicketBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID_NAF_TRUE, "com.VCB:id/llSeat");
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID_NAF_TRUE, "com.VCB:id/llSeat");
		if (status) {
			List<MobileElement> elements = driver.findElements(By.xpath(locator));
			for (MobileElement element : elements) {
				File imageFile = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);
				try {
					BufferedImage bufferedImage = ImageIO.read(imageFile);
					imageFile.delete();

					int height = bufferedImage.getHeight();
					int width = bufferedImage.getWidth();
					int x = width / 2;
					int y = height / 4;
					int RGBA = bufferedImage.getRGB(x, y);
					int red = (RGBA >> 16) & 255;
					int green = (RGBA >> 8) & 255;
					int blue = RGBA & 255;
					String colorOfElement = "(" + red + "," + green + "," + blue + ")";

					if (colorOfSeat.equals(colorOfElement)) {
						element.click();
						imageFile = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);
						bufferedImage = ImageIO.read(imageFile);
						imageFile.delete();
						height = bufferedImage.getHeight();
						width = bufferedImage.getWidth();
						x = width / 2;
						y = height / 4;
						RGBA = bufferedImage.getRGB(x, y);
						red = (RGBA >> 16) & 255;
						green = (RGBA >> 8) & 255;
						blue = RGBA & 255;
						colorOfElement = "(" + red + "," + green + "," + blue + ")";

						result = checkColor.equals(colorOfElement);

						if (!result) {
							return false;
						}

						numberOfSeats--;
					}

					if (numberOfSeats <= 0) {
						break;
					}

				} catch (Exception e) {

				}
			}

		}
		return result;

	}

	public void chooseSeats(int numberOfSeats, String colorOfSeat) {
		String locator = String.format(FilmTicketBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID_NAF_TRUE, "com.VCB:id/llSeat");
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID_NAF_TRUE, "com.VCB:id/llSeat");
		if (status) {
			List<MobileElement> elements = driver.findElements(By.xpath(locator));
			for (MobileElement element : elements) {
				File imageFile = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);
				try {
					BufferedImage bufferedImage = ImageIO.read(imageFile);
					imageFile.delete();

					int height = bufferedImage.getHeight();
					int width = bufferedImage.getWidth();
					int x = width / 2;
					int y = height / 4;
					int RGBA = bufferedImage.getRGB(x, y);
					int red = (RGBA >> 16) & 255;
					int green = (RGBA >> 8) & 255;
					int blue = RGBA & 255;
					String colorOfElement = "(" + red + "," + green + "," + blue + ")";

					if (colorOfSeat.equals(colorOfElement)) {
						element.click();
						numberOfSeats--;
					}

					if (numberOfSeats <= 0) {
						break;
					}

				} catch (Exception e) {

				}
			}

		}

	}
	
	public void chooseMaxSeats(List<SeatType> listSeatType) {
		for (SeatType seat : listSeatType) {
			String type = seat.name;
			String colorOfSeat = "";
			int numberOfSeats = Integer.parseInt(seat.number);
			
			if (numberOfSeats > 0) {
				
				if (type.contains("Standard") || type.contains("Thường")) {
					colorOfSeat = getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Standard");
				}

				else if (type.contains("VIP") || type.contains("Vip")) {
					colorOfSeat = getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Vip");
				}

				else if (type.contains("Couple")) {
					colorOfSeat = getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Couple");
				}

				else if (type.contains("Deluxe")) {
					colorOfSeat = getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Deluxe");
				}
				
				String locator = String.format(FilmTicketBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID_NAF_TRUE, "com.VCB:id/llSeat");
				boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID_NAF_TRUE, "com.VCB:id/llSeat");
				if (status) {
					List<MobileElement> elements = driver.findElements(By.xpath(locator));
					for (MobileElement element : elements) {
						File imageFile = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);
						try {
							BufferedImage bufferedImage = ImageIO.read(imageFile);
							imageFile.delete();

							int height = bufferedImage.getHeight();
							int width = bufferedImage.getWidth();
							int x = width / 2;
							int y = height / 4;
							int RGBA = bufferedImage.getRGB(x, y);
							int red = (RGBA >> 16) & 255;
							int green = (RGBA >> 8) & 255;
							int blue = RGBA & 255;
							String colorOfElement = "(" + red + "," + green + "," + blue + ")";

							if (colorOfSeat.equals(colorOfElement)) {
								element.click();
								numberOfSeats--;
							}

							if (numberOfSeats <= 0) {
								break;
							}

						} catch (Exception e) {

						}
					}

				}
				
			}

		}
	}

	public void cancelAllChoosenSeats() {
		String colorOfSeat = "";
		colorOfSeat = getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, "Đang chọn");
		String locator = String.format(FilmTicketBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID_NAF_TRUE, "com.VCB:id/llSeat");
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID_NAF_TRUE, "com.VCB:id/llSeat");
		if (status) {
			List<MobileElement> elements = driver.findElements(By.xpath(locator));
			for (MobileElement element : elements) {
				File imageFile = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);
				try {
					BufferedImage bufferedImage = ImageIO.read(imageFile);
					imageFile.delete();
	
					int height = bufferedImage.getHeight();
					int width = bufferedImage.getWidth();
					int x = width / 2;
					int y = height / 4;
					int RGBA = bufferedImage.getRGB(x, y);
					int red = (RGBA >> 16) & 255;
					int green = (RGBA >> 8) & 255;
					int blue = RGBA & 255;
					String colorOfElement = "(" + red + "," + green + "," + blue + ")";
	
					if (colorOfSeat.equals(colorOfElement)) {
						element.click();
					}
	
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}
	
	public void chooseSeatsByLineEmptyLastSeat() {
		String index = "0";
		boolean check = false;
		while (!check) {
			String locator = String.format(FilmTicketBookingPageUIs.TEXTVIEW_BY_LINEARLAYOUT_NAF_TRUE, index);
			overRideTimeOut(driver, 2);
			List<MobileElement> elements = driver.findElements(By.xpath(locator));
			
			int count = elements.size();
			if	(count > 2) {
				elements.get(count - 2).click();
				check = true;
			}
			else {
				index = String.valueOf(Integer.parseInt(index) + 1);
			}

		}
		overRideTimeOut(driver, 30);
	}

	public void chooseSeatsByLineEmptyBetweenSeat() {
		String index = "0";
		boolean check = false;
		while (!check) {
			String locator = String.format(FilmTicketBookingPageUIs.TEXTVIEW_BY_LINEARLAYOUT_NAF_TRUE, index);
			overRideTimeOut(driver, 2);
			List<MobileElement> elements = driver.findElements(By.xpath(locator));
			
			int count = elements.size();
			if	(count > 5) {
				elements.get(count - 3).click();
				elements.get(count - 5).click();
				check = true;
			}
			else {
				index = String.valueOf(Integer.parseInt(index) + 1);
			}

		}
		overRideTimeOut(driver, 30);
	}

	public int getNumberSeatsByColor(String colorOfSeat) {
		int numberOfSeats = 0;
		String locator = String.format(FilmTicketBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID_NAF_TRUE, "com.VCB:id/llSeat");
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID_NAF_TRUE, "com.VCB:id/llSeat");
		if (status) {
			List<MobileElement> elements = driver.findElements(By.xpath(locator));
			for (MobileElement element : elements) {
				File imageFile = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);
				try {
					BufferedImage bufferedImage = ImageIO.read(imageFile);
					imageFile.delete();

					int height = bufferedImage.getHeight();
					int width = bufferedImage.getWidth();
					int x = width / 2;
					int y = height / 2;
					int RGBA = bufferedImage.getRGB(x, y);
					int red = (RGBA >> 16) & 255;
					int green = (RGBA >> 8) & 255;
					int blue = RGBA & 255;
					String colorOfElement = "(" + red + "," + green + "," + blue + ")";

					if (colorOfSeat.equals(colorOfElement)) {
						numberOfSeats++;
					}

				} catch (Exception e) {

				}
			}

		}
		return numberOfSeats;

	}

}
