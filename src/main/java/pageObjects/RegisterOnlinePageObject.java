package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class RegisterOnlinePageObject extends AbstractPage {

	public RegisterOnlinePageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public void DangKyPhatHanhTheGhiNo(String TYPE_IDENTIFICATION, String NO_IDENTIFICATION,String Plusyear, String LOCATION, String EMAIL) {
		clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký phát hành thẻ ghi nợ");

		clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
	clickToDynamicButtonLinkOrLinkText(driver, TYPE_IDENTIFICATION);

		inputToDynamicInputText(driver, NO_IDENTIFICATION, "Giấy tờ tùy thân", "6");

		clickToTextViewDate(driver, "Giấy tờ tùy thân", "7");

		clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		clickToTextListview(driver, "android:id/date_picker_year_picker", Plusyear);

		clickToDynamicButton(driver, "OK");

		inputToDynamicInputText(driver, LOCATION, "Giấy tờ tùy thân", "8");

		inputToDynamicInputText(driver, EMAIL, "Giấy tờ tùy thân", "11");

		clickToDynamicButton(driver, "Xác nhận");
	}
}
