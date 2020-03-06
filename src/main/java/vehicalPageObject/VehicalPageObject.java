package vehicalPageObject;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vehicalTicketBookingUI.CommonPageUIs;

public class VehicalPageObject extends AbstractPage {
    public VehicalPageObject(AppiumDriver<MobileElement> mappingDriver) {
	driver = mappingDriver;
    }

    private AppiumDriver<MobileElement> driver;

    public void Vehical_login() {

	clickToDynamicText("Đặt vé xe");

	clickToDynamicButton(driver, "Đồng ý");
    }

    // input vào ô textbox
    public void inputToDynamicInputBox(String inputValue, String dynamicTextValue) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
	if (status) {
	    clearText(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
	    sendKeyToElement(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, inputValue, dynamicTextValue);
	}
    }

    // input vào textbox màn hình chỉnh sủa, nhập điểm đi điểm đến, tham số truyền
    // vào là
    // resource id của class android.widget.LinearLayout
    public void inputToDynamicInputBoxID(String inputValue, String dynamicID) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicID);
	if (status) {
	    clearText(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicID);
	    sendKeyToElement(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, inputValue, dynamicID);
	}
    }

    // click textbox trong màn hình chỉnh sủa, nhập điểm đi điểm đến
    // resource id của class android.widget.LinearLayout
    public void clickToDynamicTextBox(String dynamicID) {
	scrollIDown(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicID);
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicID);
	if (status) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_FROMT_INPUT_BY_CLOSE, dynamicID);
	}
    }

    // Click vào button, text có class là textview, tham số truyền vào là text
    public void clickToDynamicInputBox(String dynamicTextValue) {
	scrollIDown(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
	if (status) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_INPUT_TEXT, dynamicTextValue);
	}

    }

    // Click vào button, text có class là textview, tham số truyền vào là text
    public void clickToDynamicText(String dynamicTextValue) {
	scrollIDown(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	if (status) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	}

    }

    // Click vào button, text có class là textview, tham số truyền vào là text
    public void clickToDynamicTextScollUP(String dynamicTextValue) {
	scrollUp(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	if (status) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	}

    }

    // Chọn ghế ngồi trong màn hình chi tiết chuyến xe
    public void clickBookingChair(String... dynamicIndex) {
	scrollIDown(driver, CommonPageUIs.DYNAMIC_BOOKING_CHAIR, dynamicIndex);
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_BOOKING_CHAIR, dynamicIndex);
	if (status) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_BOOKING_CHAIR, dynamicIndex);
	}

    }

    // Click vao 1 button sử dụng tham số là text
    public void clickToDynamicButton(String dynamicTextValue) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
	if (status) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
	}
    }

    // Click vao 1 button ngay mai
    public void clickToDynamicTomorrow(String dynamicTextValue) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicTextValue);
	if (status) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicTextValue);
	}

    }

    public boolean isDynamicMessageAndLabelTextDisplayed(String dynamicTextValue) {
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	if (status) {
	    isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	}
	return isDisplayed;
    }

//    click chọn áp dụng trong màn hình nhập, chỉnh sửa điểm đi điểm đến
    public void clickToDynamicButtonForID(String dynamicID) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_BUTTON_ID, dynamicID);
	if (status) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_BUTTON_ID, dynamicID);
	}
    }

    public boolean isDynamicIconBackDisplayed(String dynamicTextValue) {
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_BACK, dynamicTextValue);
	if (status) {
	    isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_ICON_BACK, dynamicTextValue);
	}
	return isDisplayed;
    }

    public boolean isDynamicIconChangePlaceDisplayed(String dynamicId) {
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE_AND_BACK, dynamicId);
	if (status) {
	    isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE_AND_BACK, dynamicId);
	}
	return isDisplayed;
    }

//    click chọn tìm kiếm chuyến đi
    public void clickToDynamicFilterTrips(String dynamicIndex) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_FILTER_TRIPS, dynamicIndex);
	if (status) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_FILTER_TRIPS, dynamicIndex);
	}
    }

    // Click icon đổi điểm đi điểm đến
    public void clickToDynamicIconChangePlaceAndBack(String dynamicID) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE_AND_BACK, dynamicID);
	if (status) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE_AND_BACK, dynamicID);
	}

    }

    // Click vao 1 button back tham số truyền vào là text
    public void clickToDynamicButtonBack(String dynamicTextValue) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_BACK, dynamicTextValue);
	if (status) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_ICON_BACK, dynamicTextValue);
	}
    }

    // Click chọn ngày
    public void clickToDynamicButtonChoiseDate(String dynamicTextValue) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicTextValue);
	if (status) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicTextValue);
	}
    }

    // Click icon đổi điểm đi điểm đến
    public void clickToDynamicIconChangePlace(String dynamicID) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE_AND_BACK, dynamicID);
	if (status) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE_AND_BACK, dynamicID);
	}
    }

//    get text ở edit text
    public String getDynamicEditText(String dynamicID) {
	boolean status = false;
	String text = null;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_EDIT_TEXT, dynamicID);
	if (status) {
	    text = getTextElement(driver, CommonPageUIs.DYNAMIC_EDIT_TEXT, dynamicID);

	}
	return text;
    }

    public String getDynamicSuggestTrip(String dynamicID) {
	boolean status = false;
	String text = null;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_SUGGEST_TRIP, dynamicID);
	if (status) {
	    text = getTextElement(driver, CommonPageUIs.DYNAMIC_SUGGEST_TRIP, dynamicID);

	}
	return text;
    }

    public String getDynamicDayStart(String dynamicID) {
	boolean status = false;
	String text = null;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
	if (status) {
	    text = getTextElement(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);

	}
	return text;

    }

    // Click close chọn điểm đi
    public void clickToDynamicButtonIconBack(String dynamicID) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE_AND_BACK, dynamicID);
	if (status) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE_AND_BACK, dynamicID);
	}

    }

    public void clickToDynamicSelectedDate(String... dynamicTextAndText) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextAndText);
	if (status) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextAndText);
	}
    }

    // Click vao 1 button ngay mai
    public void clickToDynamicTomorrowAndFilterTrip(String dynamicId) {
	boolean status = false;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicId);
	if (status) {
	    clickToElement(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicId);
	}

    }

    public String getDynamicConfirmNullData(String dynamicID) {
	boolean status = false;
	String text = null;
	status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_NULL_DATA, dynamicID);
	if (status) {
	    text = getTextElement(driver, CommonPageUIs.DYNAMIC_NULL_DATA, dynamicID);

	}
	return text;
    }

    public boolean isDynamicForcus(String dynamicTextValue) {
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicTextValue);
	if (status) {
	    isDisplayed = isControlForcus(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicTextValue);
	}
	return isDisplayed;
    }

    public boolean isDynamicForcusText(String dynamicTextValue) {
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	if (status) {
	    isDisplayed = isControlForcus(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	}
	return isDisplayed;
    }

    public boolean isDynamicDisplayed(String dynamicTextValue) {
	scrollIDown(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	if (status) {
	    isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	}
	return isDisplayed;
    }

    public boolean isDynamicUnDisplayed(String dynamicTextValue) {
	scrollIDown(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	boolean isDisplayed = true;
	boolean status = waitForElementInvisible(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	if (status == true) {
	    isDisplayed = isControlUnDisplayed(driver, CommonPageUIs.DYNAMIC_TEXT, dynamicTextValue);
	}
	return isDisplayed;

    }

//    kiểm tra hiển thị tên hãng xe
    public boolean isDynamicDisplayedManufacturerAndRateAndTimeRunAndRouter(String dynamicID) {
	scrollIDown(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
	if (status) {
	    isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
	}
	return isDisplayed;

    }

//    kiểm tra hiển thị icon đổi chỗ điểm đi cho điểm đến và ngược lại
    public boolean isDynamicIconChangePlaceAndBackAndFinndDisplayed(String dynamicId) {
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE_AND_BACK, dynamicId);
	if (status) {
	    isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_ICON_CHANGE_PLACE_AND_BACK, dynamicId);
	}
	return isDisplayed;

    }

//kiểm tra hiển thị thòi gian đi
    public boolean isDynamicTimeStartDisplayed(String dynamicId) {
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicId);
	if (status) {
	    isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_TEXT_BY_ID, dynamicId);
	}
	return isDisplayed;

    }

//    kiểm tra hiển thị giá và hãng xe
    public boolean isDynamicFilterTripDisplayed(String dynamicId) {
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_FILTER_TRIPS, dynamicId);
	if (status) {
	    isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_FILTER_TRIPS, dynamicId);
	}
	return isDisplayed;

    }

//    kiểm tra hiển thị link text chỉnh sửa chuyến đi
    public boolean isDynamicEditTripDisplayed(String dynamicId) {
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_BUTTON_ID, dynamicId);
	if (status) {
	    isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_BUTTON_ID, dynamicId);
	}
	return isDisplayed;

    }

    // check button có hiển thị hay không, tham số truyền vào là text của button
    public boolean isDynamicButtonDisplayed(String dynamicTextValue) {
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
	if (status) {
	    isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
	}
	return isDisplayed;

    }

    // kiểm tra text ghi chú trạng thái ghế
    public boolean isDynamicChairStatus(String dynamicId) {
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_SUGGEST_TRIP, dynamicId);
	if (status) {
	    isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_SUGGEST_TRIP, dynamicId);
	}
	return isDisplayed;

    }

    // kiểm tra sơ đồ ghế ngồi
    public boolean isDynamicChairMap(String dynamicId) {
	boolean isDisplayed = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicId);
	if (status) {
	    isDisplayed = isControlDisplayed(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicId);
	}
	return isDisplayed;

    }

//  kiểm tra button có được forcus
    public boolean isDynamicForcusAndPriceDisplay(String dynamicID) {
	boolean isForcus = false;
	boolean status = waitForElementVisible(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicID);
	if (status) {
	    isForcus = isControlForcus(driver, CommonPageUIs.DYNAMIC_TITlE, dynamicID);
	}
	return isForcus;
    }

}
