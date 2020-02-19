package pageObjects.sdk.filmTicketBooking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import commons.AbstractPage;
import commons.Constants;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;
import vietcombankUI.sdk.filmTicketBooking.FilmTicketBookingPageUIs;

public class FilmTicketBookingPageObject extends AbstractPage{

	public FilmTicketBookingPageObject(AndroidDriver<AndroidElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AndroidDriver<AndroidElement> driver;

	public boolean isDynamicTextViewDisplayed(String dynamicTextValue) {
		waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicTextValue);
		return isControlDisplayed(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicTextValue);

	}
	
	public boolean isDynamicTextViewDisplayedByID(String dynamicTextID) {
		waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, dynamicTextID);
		return isControlDisplayed(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, dynamicTextID);

	}
	
	public boolean isDynamicInputBoxByTextDisPlayed(String... dynamicTextValue) {
		waitForElementVisible(driver, FilmTicketBookingPageUIs.INPUT_BOX_BY_TEXT, dynamicTextValue);
		return isControlDisplayed(driver, FilmTicketBookingPageUIs.INPUT_BOX_BY_TEXT, dynamicTextValue);
	}
	
	public void clickToDynamicTextView(String dynamicTextValue) {
		waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicTextValue);
		clickToElement(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, dynamicTextValue);

	}
	
	public void clickToDynamicTextViewByID(String dynamicTextID) {
		waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, dynamicTextID);
		clickToElement(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, dynamicTextID);

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
		waitForElementVisible(driver, FilmTicketBookingPageUIs.INPUTBOX_BY_ID, dynamicID);
		sendKeyToElement(driver, FilmTicketBookingPageUIs.INPUTBOX_BY_ID, inputValue, dynamicID);

	}
	
	public void swipeElementToElementByText (String textStart, String textEnd) {
		Dimension size = driver.manage().window().getSize();
		String locatorStart = String.format(FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, textStart);
    	MobileElement elementStart = driver.findElement(By.xpath(locatorStart));
    	
    	String locatorEnd = String.format(FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, textEnd);
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
	
	public void swipeElementToElement (String locatorStart, String locatorEnd, String dynamicValueStart, String dynamicValueEnd) {
		Dimension size = driver.manage().window().getSize();
		locatorStart = String.format(locatorStart, dynamicValueStart);
    	MobileElement elementStart = driver.findElement(By.xpath(locatorStart));
    	
    	locatorEnd = String.format(locatorEnd, dynamicValueEnd);
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
	
	public void waitForTextViewDisplay (String textView) {
		waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, textView);
	
	}
	
	public String getTextViewByID (String dynamicID) {
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
	
	public void clickToDynamicBottomMenuOrCloseIcon(String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, FilmTicketBookingPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		if (status) {
			clickToElement(driver, FilmTicketBookingPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
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
		result =  isControlSelected(driver, FilmTicketBookingPageUIs.DYNAMIC_SWITCH_SETTING_BY_TEXT, dynamicTextValue);
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
	
	public String getLocationByCheckSwitchLocationInGlobalSetting(boolean check){
		String location = "";
		if (check) {
			location = "Hà Nội";
		}
		else {
			location = "Hồ Chí Minh";
		}
		return location;
		
	}
	
	public List<String> getListCity(){
		List<String> listCity = new ArrayList<String>();
		List<String> tempList1 = new ArrayList<String>();
		List<String> tempList2 = new ArrayList<String>();
		boolean check = true;
		
		boolean status = waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvTitle");
		if (status) {
			tempList1 = getTextInListElements(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvTitle");
			tempList2 = getTextInListElements(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvTitle");
			while (check) {
				for(String text : tempList1) {
					if (!listCity.contains(text) ) {
						listCity.add(text);
					}
					
				}
				swipeElementToElement(FilmTicketBookingPageUIs.TEXTVIEW_BY_TEXT, FilmTicketBookingPageUIs.INPUT_BOX_BY_TEXT, tempList1.get(tempList1.size() - 1), "Tìm kiếm");
				tempList1 = getTextInListElements(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvTitle");
				if (tempList1.equals(tempList2)) {
					break;
				}
				else {
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
		List<AndroidElement> elements = null;
		if (status) {
			elements = driver.findElements(By.xpath(locator));
		}
	
		for (int i = 0; i < countList; i++) {
			elements.get(i).click();
			result = waitForElementVisible(driver, FilmTicketBookingPageUIs.TEXTVIEW_BY_ID, "com.VCB:id/tvNameCinema");
		}
		return result;
		
	}
	
	public String getDuration() {
		String result = "";
		String dateStart = "19/02/2020 09:29";
		String dateStop = "01/15/2020 10:28";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);
			
			long diff = d2.getTime() - d1.getTime();
			result = String.valueOf(diff);
		}
		catch(Exception e){
			
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
		}
		catch(Exception e){
			
		}
		return result;
	}
}
