package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombank_test_data.Register_Online_data;

public class RegisterOnlinePageObject extends AbstractPage {

	public RegisterOnlinePageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public void DangKyPhatHanhTheGhiNo(String TYPE_IDENTIFICATION, String NO_IDENTIFICATION,String Plusyear, String LOCATION,String ADDRESS,  String EMAIL) {
		clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký phát hành thẻ ghi nợ");

		clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgaySinh");
		clickToDynamicButton(driver, "OK");

		clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutLoaiXacNhan");
		clickToDynamicButtonLinkOrLinkText(driver, TYPE_IDENTIFICATION);

		inputToDynamicEditviewByLinearlayoutId(driver, NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		clickToTextViewCombobox(driver, "android:id/date_picker_header_year");

		clickToTextListview(driver, "android:id/date_picker_year_picker", Plusyear);

		clickToDynamicButton(driver, "OK");

		inputToDynamicEditviewByLinearlayoutId(driver, LOCATION, "com.VCB:id/layoutNoiCap2");
		inputToDynamicEditviewByLinearlayoutId(driver,ADDRESS,"com.VCB:id/layoutDiaChiHienTai");
	
		inputToDynamicEditviewByLinearlayoutId(driver, EMAIL,"com.VCB:id/layoutDiaChiEmail");


		clickToDynamicButton(driver, "Xác nhận");
	}
}
