package vnpay.vietcombank.register_online;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.RegisterOnlinePageObject;
import vietcombank_test_data.Register_Online_data;

public class Flow_RegisterOnline_DoNotLoginUser extends Base {
	AppiumDriver<MobileElement> driver;
	private RegisterOnlinePageObject registerOnline;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		registerOnline = PageFactoryManager.getRegisterOnlinePageObject(driver);

		registerOnline.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		registerOnline.clickToTextID(driver, "com.VCB:id/tvSkip");
	}

	@Test
	public void TC_01_GiaoDichChuyenTienTrongVietComBankThanhCong() {
		log.info("TC_01_Step: Click dang ky truc tuyen");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký trực tuyến");

		log.info("TC_01_Step: Click giao dich tien mat/chuyen tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Giao dịch tiền mặt/chuyển tiền");

		log.info("TC_01_Step: Click chuyen tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền");

		log.info("TC_01_Step: Nhap thong tin khach hang");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.ACC_NAME, "com.VCB:id/edtFullName");

		log.info("TC_01_Step: Click chon giay to tuy than");
		registerOnline.clickToTextID(driver, "com.VCB:id/tvContentConfirm");

		log.info("TC_01_Step: Click chon CMT");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_01_Step: Input so giay to");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/edtIDPp");

		log.info("TC_01_Step: Click ngay cap");
		registerOnline.clickToTextID(driver, "com.VCB:id/tvContentDate");

		log.info("TC_01_Step: Click nam");
		registerOnline.clickToTextID(driver, "android:id/date_picker_header_year");

		log.info("TC_01_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_01_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_1_Step: Click noi cap");
		registerOnline.clickToTextID(driver, "com.VCB:id/edtIssuePlace");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.LOCATION[0], "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LOCATION[0]);

		log.info("TC_01_Step: Nhap email");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/edtEmail");

		log.info("TC_01_Step: Nhap so tai khoan nhan");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[0], "com.VCB:id/layoutSoTk3");

		log.info("TC_01_Step: Nhap ten chu tai khoan");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.ACC_NAME, "com.VCB:id/layoutTenChuTk2");

		log.info("TC_01_Step: Nhap so tai khoan phan ghi co");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1], "com.VCB:id/layoutSoTk1");

		log.info("TC_01_Step: Nhap ten tai khoan phan ghi co");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.CUSTOMER_NAME_ACCEPT, "com.VCB:id/layoutTenChuTk");

		log.info("TC_01_Step: Nhap so tien chuyen");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutSoTien");

		log.info("TC_01_Step: Nhap noi dung");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NOTE, "com.VCB:id/layoutNoiDungNop");

		log.info("TC_01_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_01_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "0", "1"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_01_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_01_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_01_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_01_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_02_GiaoDichChuyenTienNgoaiVietComBankThanhCong() {
		log.info("TC_02_Step: Click chuyen tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền");

		log.info("TC_02_Step: Nhap thong tin khach hang");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.ACC_NAME, "com.VCB:id/edtFullName");

		log.info("TC_02_Step: Click chon giay to tuy than");
		registerOnline.clickToTextID(driver, "com.VCB:id/tvContentConfirm");

		log.info("TC_02_Step: Click chon CMT");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_02_Step: Input so ho chieu");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/edtIDPp");

		log.info("TC_02_Step: Click ngay cap");
		registerOnline.clickToTextID(driver, "com.VCB:id/tvContentDate");

		log.info("TC_02_Step: Click nam");
		registerOnline.clickToTextID(driver, "android:id/date_picker_header_year");

		log.info("TC_02_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_02_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_02_Step: Click noi cap");
		registerOnline.clickToTextID(driver, "com.VCB:id/edtIssuePlace");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.LOCATION[0], "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LOCATION[0]);

		log.info("TC_02_Step: Nhap email");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/edtEmail");

		log.info("TC_02_Step: Nhap so tai khoan nhan");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[0], "com.VCB:id/layoutSoTk3");

		log.info("TC_02_Step: Nhap ten chu tai khoan");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.ACC_NAME, "com.VCB:id/layoutTenChuTk2");

		log.info("TC_02_Step: Click tab ngoai he thong VCB");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Ngoài hệ thống Vietcombank");

		log.info("TC_02_Step: Nhap so tai khoan phan ghi co");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1], "com.VCB:id/layoutSoTk1");

		log.info("TC_02_Step: Nhap ten tai khoan phan ghi co");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.CUSTOMER_NAME_ACCEPT, "com.VCB:id/layoutTenChuTk");

		log.info("TC_02_Step: Chon ngan hang");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutBank");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.BANK_NAME);

		log.info("TC_02_Step: Nhap so tien chuyen");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutSoTien");

		log.info("TC_02_Step: Nhap noi dung");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NOTE, "com.VCB:id/layoutNoiDungNop");

		log.info("TC_02_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_02_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "0", "1"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_02_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_02_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_02_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_02_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}

	@Test
	public void TC_03_GiaoDichNopTienThanhCong() {
		log.info("TC_03_Step: Click nop tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Nộp tiền");

		log.info("TC_03_Step: Nhap thong tin khach hang");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.ACC_NAME, "com.VCB:id/edtFullName");

		log.info("TC_03_Step: Click chon giay to tuy than");
		registerOnline.clickToTextID(driver, "com.VCB:id/tvContentConfirm");

		log.info("TC_03_Step: Click chon CMT");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_03_Step: Input so giay to");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/edtIDPp");

		log.info("TC_03_Step: Click ngay cap");
		registerOnline.clickToTextID(driver, "com.VCB:id/tvContentDate");

		log.info("TC_03_Step: Click nam");
		registerOnline.clickToTextID(driver, "android:id/date_picker_header_year");

		log.info("TC_03_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_03_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_03_Step: Click noi cap");
		registerOnline.clickToTextID(driver, "com.VCB:id/edtIssuePlace");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.LOCATION[0], "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LOCATION[0]);

		log.info("TC_03_Step: Nhap email");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/edtEmail");

		log.info("TC_03_Step: Nhap so tai khoan phan ghi co");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[0], "com.VCB:id/layoutSoTk1");

		log.info("TC_03_Step: Nhap ten tai khoan phan ghi co");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.CUSTOMER_NAME_ACCEPT, "com.VCB:id/layoutTenChuTk");

		log.info("TC_03_Step: Chon ngan hang");
		registerOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutBank");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.BANK_NAME);

		log.info("TC_03_Step: Nhap so tien chuyen");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutSoTien");

		log.info("TC_03_Step: Nhap nguoi nop tien");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.ACC_NAME, "com.VCB:id/layoutNguoiNopTien");

		log.info("TC_03_Step: Nhap noi dung chuyen");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NOTE, "com.VCB:id/layoutNoiDungNop");

		log.info("TC_03_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_03_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "0", "1"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_03_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_03_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_03_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_03_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");

	}

	@Test
	public void TC_04_GiaoDichRutTienUSDThanhCong() {
		log.info("TC_04_Step: Click nop tien");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "Rút tiền");

		log.info("TC_04_Step: Nhap thong tin khach hang");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.ACC_NAME, "com.VCB:id/edtFullName");

		log.info("TC_04_Step: Click chon giay to tuy than");
		registerOnline.clickToTextID(driver, "com.VCB:id/tvContentConfirm");

		log.info("TC_04_Step: Click chon CMT");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[0]);

		log.info("TC_04_Step: Input so giay to");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/edtIDPp");

		log.info("TC_04_Step: Click ngay cap");
		registerOnline.clickToTextID(driver, "com.VCB:id/tvContentDate");

		log.info("TC_04_Step: Click nam");
		registerOnline.clickToTextID(driver, "android:id/date_picker_header_year");

		log.info("TC_04_Step: Chon nam cap");
		registerOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(5));

		log.info("TC_04_Step: Click OK");
		registerOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_04_Step: Click noi cap");
		registerOnline.clickToTextID(driver, "com.VCB:id/edtIssuePlace");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.LOCATION[0], "com.VCB:id/edtSearch");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LOCATION[0]);

		log.info("TC_04_Step: Nhap email");
		registerOnline.inputIntoEditTextByID(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/edtEmail");

		log.info("TC_04_Step: Nhap so tai khoan phan ghi nợ");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.ACCOUNT_TAKE[1], "com.VCB:id/layoutSoTk1");

		log.info("TC_04_Step: Nhap ten tai khoan phan ghi nợ");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.CUSTOMER_NAME_ACCEPT, "com.VCB:id/layoutTenChuTk");

		log.info("TC_04_Step: Chon loai tien la USD");
		registerOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/ivContent3");
		registerOnline.clickToDynamicButtonLinkOrLinkText(driver, "USD");

		log.info("TC_04_Step: Nhap so tien chuyen");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT_USD_EUR, "com.VCB:id/layoutSoTien");

		log.info("TC_04_Step: Nhap noi dung chuyen");
		registerOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NOTE, "com.VCB:id/layoutNoiDungNop");

		log.info("TC_04_Step: Click button xac nhan");
		registerOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_04_Step: verify man hinh dang ky thanh cong");
		verifyEquals(registerOnline.getTextDynamicFollowImageIndex(driver, "0", "1"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_04_Step: verify text dieu kien");
		verifyEquals(registerOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_04_Step: verify image chia se");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_04_Step: verify image luu anh");
		verifyTrue(registerOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_04_Step: Click button thuc hien giao dich khác");
		registerOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

}
