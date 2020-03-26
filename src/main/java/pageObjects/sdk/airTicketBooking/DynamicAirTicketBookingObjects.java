package pageObjects.sdk.airTicketBooking;

import java.time.LocalDate;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.sdk.airTicketBooking.AirTicketBookingUIs;

public class DynamicAirTicketBookingObjects extends AbstractPage {

	public DynamicAirTicketBookingObjects(AppiumDriver<MobileElement> mappingDriver) {
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

	private AppiumDriver<MobileElement> driver;

//dien vao o text box phan thong tin lien he, tham so text va id
	public void inputToDynamicInputBoxByLabel(String inputValue, String... dynamicTextAndID) {
		boolean status = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_BY_LABEL, dynamicTextAndID);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_BY_LABEL, dynamicTextAndID);
		if (status == true) {
			clearText(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_BY_LABEL, dynamicTextAndID);
			sendKeyToElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_BY_LABEL, inputValue, dynamicTextAndID);
		}
	}

	// dien vao o text box , tham so id
	public void inputToDynamicInputBoxById(String inputValue, String... dynamicTextAndID) {
		boolean status = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_BY_ID, dynamicTextAndID);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_BY_ID, dynamicTextAndID);
		if (status == true) {
			clearText(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_BY_ID, dynamicTextAndID);
			sendKeyToElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_BY_ID, inputValue, dynamicTextAndID);
		}
	}

//dien vao o text box phan thong tin hanh khach bay , 3 tham so ID, index, id
	public void inputToDynamicInputBoxByLabelAndIndex(String inputValue, String... dynamicTextAndID) {
		boolean status = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_AIR_TICKET_INFO_OF_CUSTOMER, dynamicTextAndID);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_AIR_TICKET_INFO_OF_CUSTOMER, dynamicTextAndID);
		if (status == true) {
			clearText(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_AIR_TICKET_INFO_OF_CUSTOMER, dynamicTextAndID);
			sendKeyToElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_AIR_TICKET_INFO_OF_CUSTOMER, inputValue, dynamicTextAndID);
		}
	}

//Chon  date o calendar k co scroll
	public void clickToDynamicDateInCalendar(String text) {
		boolean status = false;
		status = waitForElementVisible(driver, AirTicketBookingUIs.DATE_IN_CALENDAR, text);
		if (status == true) {
			clickToElement(driver, AirTicketBookingUIs.DATE_IN_CALENDAR, text);
		}
	}

//click vao check box  phan thong tin lien he, tham so truyen vao la id va text
	public void clickToDynamicCheckBoxByLabel(String... dynamicTextAndID) {
		boolean status = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_LABEL, dynamicTextAndID);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_LABEL, dynamicTextAndID);
		if (status == true) {
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_LABEL, dynamicTextAndID);
		}
	}

	// click vao check box phan dieu khoan, tham so truyen vao la id
	public void clickToDynamicCheckBoxByID(String... dynamicID) {
		boolean status = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_ID, dynamicID);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_ID, dynamicID);
		if (status == true) {
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_ID, dynamicID);
		}
	}

//click vao text, hoac button tren phan thong tin lien he,phan gioi tinh, tham so la id va text
	public void checkToDynamicTextOrDropDownByLabel(String... dynamicTextAndID) {
		boolean status = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_OR_DROPDOWN_BY_LABEL, dynamicTextAndID);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_OR_DROPDOWN_BY_LABEL, dynamicTextAndID);
		if (status == true) {
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_OR_DROPDOWN_BY_LABEL, dynamicTextAndID);
		}
	}

//Click vao text hoac dropdown voi 4 tham so la id, indexm id va id
	public void clickToDynamicTextOrDropDownByLabelAndIndex(String... dynamicIDIndexIdAndId) {
		boolean status = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_TEXT_VIEW_AIR_TICKET_INFO_OF_CUSTOMER, dynamicIDIndexIdAndId);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_VIEW_AIR_TICKET_INFO_OF_CUSTOMER, dynamicIDIndexIdAndId);
		if (status == true) {
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_VIEW_AIR_TICKET_INFO_OF_CUSTOMER, dynamicIDIndexIdAndId);
		}
	}

//Bo check 1 check box, tham so truyen vao la text va id
	public void uncheckToDynamicCheckBoxByLabel(String... dynamicTextAndID) {
		boolean status = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_LABEL, dynamicTextAndID);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_LABEL, dynamicTextAndID);
		if (status == true && isControlSelected(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_LABEL, dynamicTextAndID) == true) {
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_LABEL, dynamicTextAndID);
		}
	}

//Click vao 1 button hoac link text su dung text
	public void clickToDynamicTextOrButtonLink(String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, dynamicTextValue);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, dynamicTextValue);
		}
	}

//Click vao mot button co tham so la text
	public void clickToDynamicButton(String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_BUTTON, dynamicTextValue);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
		sleep(driver, 3000);
		if (driver.getPageSource().contains("com.VCB:id/progressLoadingVntalk")) {
			waitForElementInvisible(driver, "//android.widget.ImageView[@resource-id='com.VCB:id/progressLoadingVntalk']");
		}
		if (driver.getPageSource().contains("Xin lỗi") | driver.getPageSource().contains("Thông báo") | driver.getPageSource().contains("Lỗi trong kết nối tới server") | driver.getPageSource().contains("Dịch vụ không thực hiện được trong lúc này")) {
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_BUTTON, "Đồng ý");
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
	}

//Click vao icon băng id
	public void clickToDynamicIcon(String id) {
		boolean status = false;
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_ICON_BY_ID, id);
		if (status == true) {
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_ICON_BY_ID, id);
		}
	}

//Click vao icon băng id va label
	public void clickToDynamicIconByLabel(String... textandId) {
		boolean status = false;
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_ICON_BY_LABEL, textandId);
		if (status == true) {
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_ICON_BY_LABEL, textandId);
		}
	}

//Click nut tang giam ơ man hinh dat ve may bay, tham số là text và icon cần click
	public void clickToDynamicPlusAndMinusIcon(String... textAndIcon) {
		boolean status = false;
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_DEFAULT_INCREASE_OR_DECREASE_ICON, textAndIcon);
		if (status == true) {
			clickToOneOfElement(driver, 0, AirTicketBookingUIs.DYNAMIC_DEFAULT_INCREASE_OR_DECREASE_ICON, textAndIcon);
		}
	}

//Click chọn chuyến bay 2 chiều sử dụng  id va text code của chuyến bay
	public void clickToDynamicFirstFlightShiftByFlightCode(String... resourceIDAndtext) {
		boolean status = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_BY_FLIGHT_CODE, resourceIDAndtext);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_BY_FLIGHT_CODE, resourceIDAndtext);
		if (status == true) {
			clickToOneOfElement(driver, 0, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_BY_FLIGHT_CODE, resourceIDAndtext);
		}
	}

//Click chọn ngày ở lịch, tham số là tháng năm và ngày cần click
	public void clickToDynamicDay(String... dynamicValue) {
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_DATE_SELECTED, dynamicValue);
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_DATE_SELECTED, dynamicValue);
		if (status == true) {
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_DATE_SELECTED, dynamicValue);
		}
	}

//Click vao chuyen bay 1 chiều sử dụng theo mã code của chuyến bay và thứ thự của chuyến bay 
	public void clickToDynamicFlight(int index, String... dynamicText) {
		scrollIDown(driver, AirTicketBookingUIs.LIST_FLIGHT, dynamicText);
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.LIST_FLIGHT, dynamicText);
		if (status == true) {
			clickToOneOfElement(driver, index, AirTicketBookingUIs.LIST_FLIGHT, dynamicText);
		}
	}

	// Chọn các gói hành lý
	public void clickToDynamicPackage(int index, String id) {
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		if (status == true) {
			clickToOneOfElement(driver, index, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		}
	}

	// Chọn text bang id
	public void clickToDynamicTextByID(String id) {
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		if (status == true) {
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		}
	}

	// Chon chieu di / chieu ve bang index linearlayout
	public void clickToDynamicFlight2WayCode(String... idAndIndex) {
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_DEPARTURE_RETURN_FLIGHT_CODE_2_WAY, idAndIndex);
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_DEPARTURE_RETURN_FLIGHT_CODE_2_WAY, idAndIndex);
		if (status == true) {
			clickToElement(driver, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_DEPARTURE_RETURN_FLIGHT_CODE_2_WAY, idAndIndex);
		}
	}

	public void waitForAirPlainDisapear(String dynamicText) {
		waitForElementInvisible(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, dynamicText);
	}

//lấy text ở ô text box sử dụng id
	public String getTextInDynamicTextBox(String id) {
		String text = null;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_BY_ID, id);
		if (status == true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_BY_ID, id);
		}
		return text;
	}

//lấy text ở 1 trong số các dropwdown value bằng id
	public String getTextInOneOfDropDownValue(int index, String id) {
		String text = null;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		if (status == true) {
			text = getTextInOneOFElement(driver, index, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		}
		return text;
	}

//Lấy text của thông tin vé bằng id
	public String getAirTicketInfo(int index, String id) {
		String text = null;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		if (status == true) {
			text = getTextInOneOFElement(driver, index, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		}
		return text;
	}

	// Lấy text của thông tin chuyen bay bằng id
	public String getDynamicFlightData(String... dynamicTextIndexId) {
		String text = null;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_DATA, dynamicTextIndexId);
		if (status == true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_DATA, dynamicTextIndexId);
		}
		return text;
	}

	// Lấy text của thông tin chuyen bay bằng id
	public String getDynamicDepartureArrivalData(String... dynamicID) {
		String text = null;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_DEPARTURE_ARRIVAL_DATA, dynamicID);
		if (status == true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_DEPARTURE_ARRIVAL_DATA, dynamicID);
		}
		return text;
	}

//lấy thông tin vé may bay 1 chiều sử dụng text và id
	public String getAirTicketPriceInfo1Way(String... textAndId) {
		String text = null;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_PRICE_INFO, textAndId);
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_PRICE_INFO, textAndId);
		if (status == true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_PRICE_INFO, textAndId);
		}
		return text;
	}

//Lấy thông tin vé máy bay 2 chiều sử dụng ID, 1 phần của flight code và ID
	public String getAirTicketInfoByFlightCode2Way(String... IDAndFlightcodeID) {
		String text = null;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_INFO_BY_FLIGHT_CODE_2_WAY, IDAndFlightcodeID);
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_INFO_BY_FLIGHT_CODE_2_WAY, IDAndFlightcodeID);
		if (status == true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_INFO_BY_FLIGHT_CODE_2_WAY, IDAndFlightcodeID);
		}
		return text;
	}

	public String getAirTicketInfoByFlightCode1Way(String... IDAndFlightcodeID) {
		String text = null;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_INFO_BY_FLIGHT_CODE_1_WAY, IDAndFlightcodeID);
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_INFO_BY_FLIGHT_CODE_1_WAY, IDAndFlightcodeID);
		if (status == true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_FLIGHT_SHIFT_INFO_BY_FLIGHT_CODE_1_WAY, IDAndFlightcodeID);
		}
		return text;
	}

//Get text ở pop-up sử dung id
	public String getTextInDynamicPopUpAndTitle(String id) {
		String text = null;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		if (status == true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		}
		return text;
	}

//Lấy text của số người đã được chọn ở màn hình đặt vé máy bay, tham số là text
	public String getTextSelectedPeople(String dynamicText) {
		String text = null;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_SELECTED_PEOPLE, dynamicText);
		if (status == true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_SELECTED_PEOPLE, dynamicText);
		}
		return text;
	}

//lấy text ở dropdown bằng text và id
	public String getTextInDynamicDropDownByLabel(String... textAndID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_OR_DROPDOWN_BY_LABEL, textAndID);
		if (status == true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_OR_DROPDOWN_BY_LABEL, textAndID);
		}
		return text;
	}

//lấy text trong text box sử dung text và id phần thông tin liên hệ
	public String getTextInDynamicTextBoxByLabel(String... textAndID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_BY_LABEL, textAndID);
		if (status == true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_BY_LABEL, textAndID);
		}
		return text;
	}

//Lấy thông tin từ checkbox phần thông tin liên hệ
	public String getTextInDynamicCheckboxByLabel(String... textAndID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_LABEL, textAndID);
		if (status == true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_LABEL, textAndID);
		}
		return text;
	}

//Lấy thông tin phần khách hàng bay ở ô text box sử dụng id index và id
	public String getTextInDynamicTextBoxAirTicketInfoOfCustomer(String... IdIndexID) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_AIR_TICKET_INFO_OF_CUSTOMER, IdIndexID);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_AIR_TICKET_INFO_OF_CUSTOMER, IdIndexID);
		if (status == true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BOX_AIR_TICKET_INFO_OF_CUSTOMER, IdIndexID);
		}
		return text;
	}

	public String getTextAmountOfMoneyInPayment(String... TextAndtext) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_PAYEMENT_INFO_BY_CUSTOMER_NAME, TextAndtext);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_PAYEMENT_INFO_BY_CUSTOMER_NAME, TextAndtext);
		if (status == true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_PAYEMENT_INFO_BY_CUSTOMER_NAME, TextAndtext);
		}
		return text;
	}

//Lấy thoonh tin phần khách hàng bay sử dụng dung id, index, id và id
	public String getTextInDynamicTextViewAirTicketInfoOfCustomer(String... IdIndexIDAndID) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_TEXT_VIEW_AIR_TICKET_INFO_OF_CUSTOMER, IdIndexIDAndID);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_VIEW_AIR_TICKET_INFO_OF_CUSTOMER, IdIndexIDAndID);
		if (status == true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_VIEW_AIR_TICKET_INFO_OF_CUSTOMER, IdIndexIDAndID);
		}
		return text;
	}

//Lấy thông tin title ng đặt ở phần thông tin khách hàng bay sử dụng id, index và id
	public String getTextInDynamicHeaderViewAirTicketInfoOfCustomer(String... IdIndexID) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_HEADER_IN_TICKET_INFO, IdIndexID);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_HEADER_IN_TICKET_INFO, IdIndexID);
		if (status == true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_HEADER_IN_TICKET_INFO, IdIndexID);
		}
		return text;
	}

//Lấy text bằng id
	public String getDynamicTextByID(String id) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		if (status == true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);

		}
		return text;
	}

	// Lấy thong tin theo index linearlayout
	public String getDynamicConfirmInfoByIndex(String... dynamicIdText) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_CONFIRM_INFO, dynamicIdText);
		status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_CONFIRM_INFO, dynamicIdText);
		if (status == true) {
			text = getTextElement(driver, AirTicketBookingUIs.DYNAMIC_CONFIRM_INFO, dynamicIdText);

		}
		return text;
	}

//Kiểm tra text có hiển thị không
	public boolean isDynamicTextDisplayed(String text) {
		boolean isDisplayed = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, text);
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, text);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP, text);
		}
		return isDisplayed;
	}

//Kiểm tra text theo id hiển thị
	public boolean isDynamicTextByIdDisplayed(String id) {

		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_TEXT_BY_ID, id);
		}
		return isDisplayed;
	}

//Kiểm tra icon có hiển thị không
	public boolean isDynamicIconDisplayed(String id) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_ICON_BY_ID, id);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_ICON_BY_ID, id);
		}
		return isDisplayed;
	}

//Kiểm tra button có hiển thị hay không
	public boolean isDynamicButtonDisplayed(String text) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_BUTTON, text);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_BUTTON, text);
		}
		return isDisplayed;
	}

//Kiểm  tra 
	public boolean isDynamicPlaceAndCustomerNameDisplayed(String... text) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_PLACE_TEXT, text);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_PLACE_TEXT, text);
		}
		return isDisplayed;
	}

//Kiểm tra check box có được check không 
	public boolean isDynamicCheckBoxByLabelChecked(String... textAndID) {
		boolean isChecked = false;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_LABEL, textAndID);
		if (status == true) {
			isChecked = isControlSelected(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_LABEL, textAndID);
		}
		return isChecked;
	}

//Kiểm tra check box  bằng text có được check khong
	public boolean isDynamicCheckBoxByLabelChecked(String text) {
		boolean isChecked = false;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_TEXT, text);
		if (status == true) {
			isChecked = isControlSelected(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_TEXT, text);
		}
		return isChecked;
	}

//Kiểm tra radioButton  bằng checkbox có được check khong
	public boolean isDynamicRadioButtonByLabelChecked(String... textAndText) {
		boolean isChecked = false;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_CHECK_RADIO_BUTTON_BY_CHECKBOX, textAndText);
		if (status == true) {
			isChecked = isControlSelected(driver, AirTicketBookingUIs.DYNAMIC_CHECK_RADIO_BUTTON_BY_CHECKBOX, textAndText);
		}
		return isChecked;
	}

//Kiểm tra text có hiển thị hay không bằng label của nó
	public boolean isDynamicTextByLabel(String... textAndText) {
		boolean isDisplayed = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_DEFAULT_INCREASE_OR_DECREASE_ICON, textAndText);

		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_DEFAULT_INCREASE_OR_DECREASE_ICON, textAndText);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_DEFAULT_INCREASE_OR_DECREASE_ICON, textAndText);
		}
		return isDisplayed;
	}

//Kiểm tra dropdown có hiene thị không
	public boolean isDynamicDropdownByLabelDisplayed(String... text) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_DEFAULT_INCREASE_OR_DECREASE_ICON, text);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_DEFAULT_INCREASE_OR_DECREASE_ICON, text);
		}
		return isDisplayed;
	}

//Kiểm tra thong tin thanh toan trong icon dropdown
	public boolean isDynamicPaymentInfoByName(String... textAndText) {
		boolean isDisplayed = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_PAYEMENT_INFO_BY_CUSTOMER_NAME, textAndText);

		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_PAYEMENT_INFO_BY_CUSTOMER_NAME, textAndText);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_PAYEMENT_INFO_BY_CUSTOMER_NAME, textAndText);
		}
		return isDisplayed;
	}

//Kiểm tra text tren checkbox hien thi
	public boolean isDynamicCheckBoxDisplayed(String text) {
		boolean isDisplayed = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_TEXT, text);

		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_TEXT, text);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_CHECK_BOX_BY_TEXT, text);
		}
		return isDisplayed;
	}

//Kiểm tra text tren checkbox hien thi
	public boolean isDynamicRadioButtonByLabelDisplayed(String... textAndtext) {
		boolean isDisplayed = false;
		scrollIDown(driver, AirTicketBookingUIs.DYNAMIC_CHECK_RADIO_BUTTON_BY_CHECKBOX, textAndtext);

		boolean status = waitForElementVisible(driver, AirTicketBookingUIs.DYNAMIC_CHECK_RADIO_BUTTON_BY_CHECKBOX, textAndtext);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, AirTicketBookingUIs.DYNAMIC_CHECK_RADIO_BUTTON_BY_CHECKBOX, textAndtext);
		}
		return isDisplayed;
	}

	public void scrollUpToTextView(String text) {
		scrollUp(driver, AirTicketBookingUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP);
	}

}
