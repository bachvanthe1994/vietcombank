package vnpay.vietcombank.register_online;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.RegisterOnlinePageObject;
import vietcombank_test_data.Register_Online_data;
import vietcombank_test_data.Register_Online_data.Register_Onl_Data;
import vietcombank_test_data.Register_Online_data.Register_Text;

public class Flow_RegisterOnline_DoNotLoginUser extends Base {
	AppiumDriver<MobileElement> driver;
	private RegisterOnlinePageObject registerOnline;
	
	private String sourceAccount01,accountName01,sourceAccount02,accountName02;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		registerOnline = PageFactoryManager.getRegisterOnlinePageObject(driver);

		sourceAccount01 = getDataInCell(0).trim();
		accountName01 = getDataInCell(1).trim();
		sourceAccount02 = getDataInCell(7).trim();
		accountName02 = getDataInCell(3).trim();
		
		registerOnline.clickToTextID(driver, "com.VCB:id/tvSkip");
	}

	@Test
	public void TC_01_GiaoDichChuyenTienTrongVietComBankThanhCong() {
		
		log.info("TC_01_Step: Click dang ky truc tuyen");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.REGISTER_ONLINE_TEXT);

		log.info("TC_01_Step: Click giao dich tien mat/chuyen tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.TRANSACTION_IN_CASH_OR_TRANSFER_MONEY_TEXT);

		log.info("TC_01_Step: Click chuyen tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.TRANSFER_MONEY_TEXT);

		log.info("TC_01_Step: Nhap thong tin khach hang");
		registerOnline.inputIntoEditTextByID(driver, accountName01, "com.VCB:id/edtFullName");

		log.info("TC_01_Step: Click chon giay to tuy than");
		registerOnline.clickToTextID(driver, "com.VCB:id/tvContentConfirm");

		log.info("TC_01_Step: Click chon CMT");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[0]);

		log.info("TC_01_Step: Input so giay to");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, "com.VCB:id/edtIDPp");

		log.info("TC_01_Step: Click ngay cap");
		registerOnline.clickToTextID(driver, "com.VCB:id/tvContentDate");

		log.info("TC_01_Step: Click nam");
		registerOnline.clickToTextID(driver, "android:id/date_picker_header_year");

		log.info("TC_01_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_01_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_OK_TEXT);

		log.info("TC_1_Step: Click noi cap");
		registerOnline.clickToTextID(driver, "com.VCB:id/edtIssuePlace");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Register_Onl_Data.LOCATION[0], "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.LOCATION[0]);

		log.info("TC_01_Step: Nhap email");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Register_Onl_Data.EMAIL, "com.VCB:id/edtEmail");

		log.info("TC_01_Step: Nhap so tai khoan nhan");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, sourceAccount01, "com.VCB:id/layoutSoTk3");

		log.info("TC_01_Step: Nhap ten chu tai khoan");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, accountName01, "com.VCB:id/layoutTenChuTk2");

		log.info("TC_01_Step: Nhap so tai khoan phan ghi co");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, sourceAccount02, "com.VCB:id/layoutSoTk1");

		log.info("TC_01_Step: Nhap ten tai khoan phan ghi co");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, accountName02, "com.VCB:id/layoutTenChuTk");

		log.info("TC_01_Step: Nhap so tien chuyen");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.AMOUNT, "com.VCB:id/layoutSoTien");

		log.info("TC_01_Step: Nhap noi dung");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.NOTE, "com.VCB:id/layoutNoiDungNop");

		log.info("TC_01_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_01_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "0", "1"), Register_Text.REGISTER_SUCCESS_TEXT);

		log.info("TC_01_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_01_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SHARE_ICON_TEXT));

		log.info("TC_01_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SAVE_IMG_ICON_TEXT));

		log.info("TC_01_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_NEW_TRANSACTION_TEXT);
	}

	@Test
	public void TC_02_GiaoDichChuyenTienNgoaiVietComBankThanhCong() {
		
		log.info("TC_02_Step: Click chuyen tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.TRANSFER_MONEY_TEXT);

		log.info("TC_02_Step: Nhap thong tin khach hang");
		registerOnline.inputIntoEditTextByID(driver, accountName01, "com.VCB:id/edtFullName");

		log.info("TC_02_Step: Click chon giay to tuy than");
		registerOnline.clickToTextID(driver, "com.VCB:id/tvContentConfirm");

		log.info("TC_02_Step: Click chon CMT");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[0]);

		log.info("TC_02_Step: Input so ho chieu");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, "com.VCB:id/edtIDPp");

		log.info("TC_02_Step: Click ngay cap");
		registerOnline.clickToTextID(driver, "com.VCB:id/tvContentDate");

		log.info("TC_02_Step: Click nam");
		registerOnline.clickToTextID(driver, "android:id/date_picker_header_year");

		log.info("TC_02_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_02_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_OK_TEXT);

		log.info("TC_02_Step: Click noi cap");
		registerOnline.clickToTextID(driver, "com.VCB:id/edtIssuePlace");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Register_Onl_Data.LOCATION[0], "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.LOCATION[0]);

		log.info("TC_02_Step: Nhap email");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Register_Onl_Data.EMAIL, "com.VCB:id/edtEmail");

		log.info("TC_02_Step: Nhap so tai khoan nhan");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, sourceAccount01, "com.VCB:id/layoutSoTk3");

		log.info("TC_02_Step: Nhap ten chu tai khoan");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, accountName01, "com.VCB:id/layoutTenChuTk2");

		log.info("TC_02_Step: Click tab ngoai he thong VCB");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.OUTSIDE_VCB_TEXT);

		log.info("TC_02_Step: Nhap so tai khoan phan ghi co");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, sourceAccount02, "com.VCB:id/layoutSoTk1");

		log.info("TC_02_Step: Nhap ten tai khoan phan ghi co");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, accountName02, "com.VCB:id/layoutTenChuTk");

		log.info("TC_02_Step: Chon ngan hang");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutBank");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.BANK_NAME);

		log.info("TC_02_Step: Nhap so tien chuyen");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.AMOUNT, "com.VCB:id/layoutSoTien");

		log.info("TC_02_Step: Nhap noi dung");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.NOTE, "com.VCB:id/layoutNoiDungNop");

		log.info("TC_02_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_02_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "0", "1"), Register_Text.REGISTER_SUCCESS_TEXT);

		log.info("TC_02_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_02_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SHARE_ICON_TEXT));

		log.info("TC_02_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SAVE_IMG_ICON_TEXT));

		log.info("TC_02_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_NEW_TRANSACTION_TEXT);

	}

	@Test
	public void TC_03_GiaoDichNopTienThanhCong() {
		
		log.info("TC_03_Step: Click nop tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.PAYMENT_TEXT);

		log.info("TC_03_Step: Nhap thong tin khach hang");
		registerOnline.inputIntoEditTextByID(driver, accountName01, "com.VCB:id/edtFullName");

		log.info("TC_03_Step: Click chon giay to tuy than");
		registerOnline.clickToTextID(driver, "com.VCB:id/tvContentConfirm");

		log.info("TC_03_Step: Click chon CMT");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[0]);

		log.info("TC_03_Step: Input so giay to");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, "com.VCB:id/edtIDPp");

		log.info("TC_03_Step: Click ngay cap");
		registerOnline.clickToTextID(driver, "com.VCB:id/tvContentDate");

		log.info("TC_03_Step: Click nam");
		registerOnline.clickToTextID(driver, "android:id/date_picker_header_year");

		log.info("TC_03_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_03_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_OK_TEXT);

		log.info("TC_03_Step: Click noi cap");
		registerOnline.clickToTextID(driver, "com.VCB:id/edtIssuePlace");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Register_Onl_Data.LOCATION[0], "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.LOCATION[0]);

		log.info("TC_03_Step: Nhap email");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Register_Onl_Data.EMAIL, "com.VCB:id/edtEmail");

		log.info("TC_03_Step: Nhap so tai khoan phan ghi co");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, sourceAccount01, "com.VCB:id/layoutSoTk1");

		log.info("TC_03_Step: Nhap ten tai khoan phan ghi co");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, accountName02, "com.VCB:id/layoutTenChuTk");

		log.info("TC_03_Step: Chon ngan hang");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutBank");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.BANK_NAME);

		log.info("TC_03_Step: Nhap so tien chuyen");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.AMOUNT, "com.VCB:id/layoutSoTien");

		log.info("TC_03_Step: Nhap nguoi nop tien");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, accountName01, "com.VCB:id/layoutNguoiNopTien");

		log.info("TC_03_Step: Nhap noi dung chuyen");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.NOTE, "com.VCB:id/layoutNoiDungNop");

		log.info("TC_03_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_03_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "0", "1"), Register_Text.REGISTER_SUCCESS_TEXT);

		log.info("TC_03_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_03_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SHARE_ICON_TEXT));

		log.info("TC_03_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SAVE_IMG_ICON_TEXT));

		log.info("TC_03_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_NEW_TRANSACTION_TEXT);

	}

	@Test
	public void TC_04_GiaoDichRutTienUSDThanhCong() {
		
		log.info("TC_04_Step: Click rut tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Text.CASH_OUT_TEXT);

		log.info("TC_04_Step: Nhap thong tin khach hang");
		registerOnline.inputIntoEditTextByID(driver, accountName01, "com.VCB:id/edtFullName");

		log.info("TC_04_Step: Click chon giay to tuy than");
		registerOnline.clickToTextID(driver, "com.VCB:id/tvContentConfirm");

		log.info("TC_04_Step: Click chon CMT");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.TYPE_IDENTIFICATION[0]);

		log.info("TC_04_Step: Input so giay to");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Register_Onl_Data.NO_IDENTIFICATION, "com.VCB:id/edtIDPp");

		log.info("TC_04_Step: Click ngay cap");
		registerOnline.clickToTextID(driver, "com.VCB:id/tvContentDate");

		log.info("TC_04_Step: Click nam");
		registerOnline.clickToTextID(driver, "android:id/date_picker_header_year");

		log.info("TC_04_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_04_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_OK_TEXT);

		log.info("TC_04_Step: Click noi cap");
		registerOnline.clickToTextID(driver, "com.VCB:id/edtIssuePlace");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Register_Onl_Data.LOCATION[0], "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Register_Onl_Data.LOCATION[0]);

		log.info("TC_04_Step: Nhap email");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Register_Onl_Data.EMAIL, "com.VCB:id/edtEmail");

		log.info("TC_04_Step: Nhap so tai khoan phan ghi nợ");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, sourceAccount02, "com.VCB:id/layoutSoTk1");

		log.info("TC_04_Step: Nhap ten tai khoan phan ghi nợ");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, accountName02, "com.VCB:id/layoutTenChuTk");

		log.info("TC_04_Step: Chon loai tien la USD");
		registerOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/ivContent3");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Onl_Data.TYPE_AMOUNT[2]);

		log.info("TC_04_Step: Nhap so tien chuyen");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.AMOUNT_USD_EUR, "com.VCB:id/layoutSoTien");

		log.info("TC_04_Step: Nhap noi dung chuyen");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Register_Onl_Data.NOTE, "com.VCB:id/layoutNoiDungNop");

		log.info("TC_04_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_CONFIRM_TEXT);

		log.info("TC_04_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "0", "1"), Register_Text.REGISTER_SUCCESS_TEXT);

		log.info("TC_04_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_04_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SHARE_ICON_TEXT));

		log.info("TC_04_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, Register_Text.SAVE_IMG_ICON_TEXT));

		log.info("TC_04_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, Register_Text.BUTTON_NEW_TRANSACTION_TEXT);
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}
	
}
