package pageObjects.sdk.trainTicket;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.sdk.airTicketBooking.AirTicketBookingUIs;
import vietcombankUI.sdk.hotelBooking.HotelBookingPageUIs;
import vietcombankUI.sdk.trainTicket.TrainTicketPageUIs;

public class TrainTicketPageObject extends AbstractPage {

	public TrainTicketPageObject(AndroidDriver<AndroidElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AndroidDriver<AndroidElement> driver;

	public List<String> getListStatusTransfer(AndroidDriver<AndroidElement> driver, String dynamicIndex) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_STARUS, dynamicIndex);
		return getTextInListElements(driver, DynamicPageUIs.DYNAMIC_STARUS, dynamicIndex);
	}

	public boolean checkSuggestPoint(List<String> listSuggestPoint, String checkedValue) {
		for (String point : listSuggestPoint) {
			if (!convertVietNameseStringToString(point).toLowerCase().contains(checkedValue.toLowerCase())) {
				return false;
			}
		}
		return true;
	}

	public String convertVietNameseStringToString(String vietnameseString) {
		String temp = Normalizer.normalize(vietnameseString, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}

	public boolean getSelectedAttributeOfDate(String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		AndroidElement element = driver.findElement(By.xpath(locator));
		return Boolean.parseBoolean(element.getAttribute("selected"));
	}

	public String getCurentMonthAndYear() {
		LocalDate now = LocalDate.now();
		int month = now.getMonthValue();
		int year = now.getYear();
		return "THÁNG" + " " + month + " " + year;
	}

	public String getMonthAndYearFORMAT() {
		LocalDate now = LocalDate.now();
		int month = now.getMonthValue();
		int year = now.getYear();
		return "T." + month + " " + year;
	}
	
	public String getDayPickUp() {
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECT, "com.VCB:id/lnNgayDi");
		List<String> listText = new ArrayList<String>();
		String day = "";
		if (status) {
			listText = getTextInListElements(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECT, "com.VCB:id/lnNgayDi");
			for (String text : listText) {
				day = day + text;
			}
			day = day.replace("Ngày đi", "");
		}
		return day;
		
	}
	
	public String getDayArrival() {
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECT, "com.VCB:id/lnNgayVe");
		List<String> listText = new ArrayList<String>();
		String day = "";
		if (status) {
			listText = getTextInListElements(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECT, "com.VCB:id/lnNgayVe");
			for (String text : listText) {
				day = day + text;
			}
			day = day.replace("Ngày về", "");
		}
		return day;
	}
	// Click vào button, text có class là textview, tham số truyền vào là text
	public void clickToDynamicButtonLinkOrLinkText(String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status = true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		}
	}

	// Click vao 1 button sử dụng tham số là text
	public void clickToDynamicButton(String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status = true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
	}

	public void clickToDynamicLink(String... dynamicTextValue) {
		boolean status = false;
		waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
		if (status = true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
		}
	}

	// Click nút back bằng label cạnh nó
	public void clickToDynamicBackIcon(String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		if (status = true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		}
	}

	public void clickDynamicPointStartAndEnd(String... dynamicID) {
		boolean status = false;
		waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicID);
		if (status = true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicID);
		}
	}

	// Click button cancel
	public void clickDynamicCancelIcon(String dynamicTextValue) {
		boolean status = false;
		waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_CANCEL_ICON, dynamicTextValue);
		if (status = true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_CANCEL_ICON, dynamicTextValue);
		}
	}

	// Click icon change
	public void clickToDynamicIconChange(String dynamicText) {
		boolean status = false;
		waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicText);
		if (status = true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicText);
		}
	}

	// Click select date
	public void clickToDynamicSelectDate(String dynamicID) {
		boolean status = false;
		waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		if (status = true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		}
	}

	// Click chọn ngày trong lịch calendar
	public void clickDynamicDateStartAndEnd(String... dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		if (status = true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		}
	}

	// Click radio button
	public void clickDynamicRadioSelectType(String dynamicTextValue) {
		boolean status = false;
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_SUCCESS_ICON, dynamicTextValue);
		if (status = true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_SUCCESS_ICON, dynamicTextValue);
		}
	}

	// Lay thu trong tuan
	public String getDynamicTitleWeek(String... dynamicText) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_WEEK, dynamicText);
		if (status = true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_WEEK, dynamicText);
		}
		return text;
	}

	// Lấy message thông báo
	public String getDynamicMessageInvalid(String... dynamicText) {
		String text = null;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicText);
		if (status = true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicText);
		}
		return text;
	}

	public String getDynamicTextOld( String... dynamicText) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_NUMBER_CUSTOMER, dynamicText);
		if (status = true) {
			text =  getTextElement(driver, TrainTicketPageUIs.DYNAMIC_NUMBER_CUSTOMER, dynamicText);
		}
		return text;
	}

	// Hiển thị time ngay book
	public String getDynamicDateTime( String dynamicID) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		if (status = true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
	}
	return text;
}

	public String getDynamicTitleSelectDate(String dynamicText) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_DATE, dynamicText);
		if (status = true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_DATE, dynamicText);
	}
	return text;
}

	// Lấy text ngày đặt vé
	public String getTextInDynamicDateTicket( String... dynamicTextValue) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		if (status = true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
	}
	return text;
}

	// Lấy text tìm hiếm điểm khởi hành và điểm đến
	public String getDynamicPointStartAndEnd( String... dynamicID) {

		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicID);

		if (status = true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicID);

	}
	return text;
}

	// Lấy giá trị tìm kiếm trong danh sách
	public String getDynamicInputPoint( String dynamicID) {

		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicID);

		if (status = true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicID);
	}
	return text;
}
	
	public String getTextInDynamicNote(String dynamicIndex) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_INDEX, dynamicIndex);
		if (status = true) {
			text =  getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TEXT_INDEX, dynamicIndex);
		}
		return text;
	}
	
	public String getDynamicTextPointStart( String dynamicID) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_VIEW_TEXT_START, dynamicID);
		if (status = true) {
			text =  getTextElement(driver, TrainTicketPageUIs.DYNAMIC_VIEW_TEXT_START, dynamicID);
		}
		return text;
	}
	
	public String getTextTextViewByLinearLayoutID( String... dynamicID) {
		String text = null;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		if (status = true) {
			text =  getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		}
		return text;
	}
	
	public String getDynamicTextInPopUp( String... dynamicTextValue) {
		String text = null;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_IMAGE, dynamicTextValue);
		if (status = true) {
			text =  getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXT_IMAGE, dynamicTextValue);
		}
		return text;
	}
	
	public String getTextInDynamicPopup( String dynamicResourceID) {
		String text = null;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_IN_POPUP, dynamicResourceID);
		if (status = true) {
			text =  getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXT_IN_POPUP, dynamicResourceID);
		}
		return text;
	}
	
	
	public List<String> getListOfSuggestedMoneyOrListText( String dynamicID) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		return getTextInListElements(driver, DynamicPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
	}

		
	//Kiểm tra text có hiển thị hay không, tham số truyền vào là text 
		public boolean isDynamicMessageAndLabelTextDisplayed( String dynamicTextValue) {
			boolean isDisplayed = false;
			scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
			boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
			if (status = true) {
				isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);	
			}
		return isDisplayed;
	}
		
		// Kiểm tra hiển thị image, check chuyển khoản thành công
		public boolean isDynamicImageSuccess( String dynamicTextValue) {
			boolean isDisplayed = false;
			boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_SUCCESS_ICON, dynamicTextValue);
			if (status = true) {
				isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_SUCCESS_ICON, dynamicTextValue);
			}
			return isDisplayed;
		}
		
		// check button có hiển thị hay không, tham số truyền vào là text của button
		public boolean isDynamicButtonDisplayed( String dynamicTextValue) {
			boolean isDisplayed = false;
			scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
			boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
			if (status = true) {
				isDisplayed =  isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
			}
			return isDisplayed;
		}
		
		public boolean isDynamicBackIconDisplayed( String dynamicTextValue) {
			boolean isDisplayed = false;
			boolean status =  waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
			if (status = true) {
				isDisplayed =  isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
			}
			return isDisplayed;
		}

		public boolean isDynamicHistoryIconDisplayed( String dynamicTextValue) {
			boolean isDisplayed = false;
			boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
			if (status = true) {
				isDisplayed =  isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
			}
			return isDisplayed;
		}

		// Check hiển thị button chuyển đổi
		public boolean isDynamicChangeIconDisplayed( String dynamicTextValue) {
			boolean isDisplayed = false;
			boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicTextValue);
			if (status = true) {
				isDisplayed =  isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicTextValue);
			}
			return isDisplayed;
		}

		// Check hiển thị icon combobox
		public boolean isDynamicComboboxDisplayed( String dynamicTextValue) {
			boolean isDisplayed = false;
			boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_COMBOBOX, dynamicTextValue);
			if (status = true) {
				isDisplayed =  isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_COMBOBOX, dynamicTextValue);
			}
			return isDisplayed;
		}
		
		public boolean isDynamicVerifyTextOnButton( String dynamicTextValue) {
			boolean isDisplayed = false;
			scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
			boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
			if (status = true) {
				isDisplayed =  isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
			}
			return isDisplayed;
		}
		
		// hiển thị icon trường số lượng hành khách
		public boolean isDynamicIconChangeNumber( String... dynamicTextValue) {
			boolean isDisplayed = false;
			boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_ICON_CHANGE_NUMBER, dynamicTextValue);
			if (status = true) {
				isDisplayed =  isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_ICON_CHANGE_NUMBER, dynamicTextValue);
			}
			return isDisplayed;
		}

		// hiển thị text trường số lượng hành khách
		public boolean isDynamicTextChangeNumber( String... dynamicTextValue) {
			boolean isDisplayed = false;
			boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_CHANGE_NUMBER, dynamicTextValue);
			if (status = true) {
				isDisplayed =  isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_TEXT_CHANGE_NUMBER, dynamicTextValue);
			}
			return isDisplayed;
		}

		// Lấy text trường số lượng hành khách
		public boolean isDynamicTextNumberCustomerDisplayed( String dynamicTextValue) {
			boolean isDisplayed = false;
			boolean status =waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
			if (status = true) {
				isDisplayed =  isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
			}
			return isDisplayed;
		}

		// Không hiển thị text trường số lượng hành khách
		public boolean isDynamicTextNumberCustomerUnDisplayed( String dynamicTextValue) {
			boolean isDisplayed = false;
			boolean status =waitForElementInvisible(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
			if (status = true) {
				isDisplayed =  isControlUnDisplayed(driver, DynamicPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
			}
			return isDisplayed;
		}

		// Nhập địa điểm tìm kiếm
		public void inputToDynamicTextPoint( String inputValue, String dynamicID) {
			clearText(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicID);
			boolean status = false;
			 waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicID);
			if (status = true) {
				sendKeyToElement(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, inputValue, dynamicID);
			}
			
	}
}
		
