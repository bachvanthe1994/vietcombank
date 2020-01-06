package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Transfer_Money_In_Future_Validation_Part1 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private String tomorrow = getForwardDate(1);

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class_Step_00: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("Before class_Step_01: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		log.info("Before class_Step_02: Dien so dien thoai ");
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		log.info("Before class_Step_03: Click Tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class_Step_03:  Dien mat khau");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		log.info("Before class_Step_05: Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class_Step_06: Nhap OTP");
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("Before class_Step_07: Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class_Step_01: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollToText(driver, "Trạng thái lệnh chuyển tiền");

	}

//	@Test
	public void TC_01_KiemTraMaxTaiKhoanDich() {
		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_01_Step_02: Nhap tai khoan nhan");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InvalidInputData.INVALID_ACCOUNT_15_CHARACTERS, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_03: Lay do dai tai khoan huong duoc nhap vao");
		int accountNumber = transferInVCB.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "1").length();

		log.info("TC_01_Step_04: Kiem tra so tai khoan huong bi cat bot con 13 ky tu");
		verifyEquals(accountNumber, 13);

		log.info("TC_01_Step_04: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

//	@Test
	public void TC_02_KiemTraTaiKhoanDichNho10KyTu() {
		log.info("TC_02_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_02_Step_02: Nhap tai khoan nhan");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InvalidInputData.INVALID_ACCOUNT_LESS_THAN_10_CHARACTERS, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_02_Step_03: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_02_Step_04: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_02_Step_05: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_06: Kiem tra message loi hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.ERRROR_MESSAGE_FOR_ACCOUNT_LESS_THAN_10_CHARACTER));

		log.info("TC_02_Step_07: Click Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_02_Step_08: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

//	@Test
	public void TC_03_KiemTraTaiKhoanDichNhoHon13KyTuLonHon10KyTu() {
		log.info("TC_03_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_03_Step_02: Nhap tai khoan nhan");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InvalidInputData.INVALID_ACCOUNT_LESS_THAN_13_CHARACTERS, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_03_Step_03: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_03_Step_04: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_03_Step_05: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.ERRROR_MESSAGE_FOR_ACCOUNT_LESS_THAN_13_CHARACTER));

		log.info("TC_03_Step_06: Click Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_02_Step_07: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

//	@Test
	public void TC_04_KiemTraKhiNhapKyTuDacBietVaoTaiKhoanDich() {
		log.info("TC_04_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_04_Step_02: Nhap tai khoan nhan");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InvalidInputData.TEXT_AND_SPECIAL_CHARACTERS, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_04_Step_03:Kiem tra tai khoan khong duoc dien");
		verifyEquals(transferInVCB.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "1"), "Nhập/chọn tài khoản nhận VND");

		log.info("TC_04_Step_04: Click quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_05_KiemTraMacDinhComboxNgayHieuLuc() {

		log.info("TC_05_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_05_Step_02: Nhap tai khoan nhan");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

		log.info("TC_05_Step_03: Chon Chuyen tien ngay tuong lai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_05_Step_04: Kiem tra ngay hieu luc la today");
		verifyEquals(transferInVCB.getDynamicTextInDropDownByHeader(driver, "Ngày hiệu lực", "1"), tomorrow);

	}

	@Test
	public void TC_06_KiemTraChonNgayHieuLucCachNgayHienTaiMotNam() {

		log.info("TC_06_Step_01: Click vao o chon ngay hieu luc");
		transferInVCB.clickToDynamicDropdownByHeader(driver, "Ngày hiệu lực", "1");

		log.info("TC_06_Step_02: Click chon nam");
		transferInVCB.clickToDynamicDropdownAndDateTimePicker(driver, "android:id/date_picker_header_year");

		log.info("TC_06_Step_03: Chon 2021");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Integer.parseInt(getCurrentYear()) + 1 + "");

		log.info("TC_06_Step_04: CLick Ok");
		transferInVCB.clickToDynamicButton(driver, "OK");

		log.info("TC_06_Step_05: Kiem tra date do k duoc chon, mac dinh van la ngay mai");
		verifyEquals(transferInVCB.getDynamicTextInDropDownByHeader(driver, "Ngày hiệu lực", "1"), tomorrow);

	}

	@Test
	public void TC_07_KiemTraChonNgayHieuLucTrongQuaKhu() {

		log.info("TC_07_Step_01: Click vao o chon ngay hieu luc");
		transferInVCB.clickToDynamicDropdownByHeader(driver, "Ngày hiệu lực", "1");

		log.info("TC_07_Step_02: Click chon ngay hom qua");
		transferInVCB.clickToDynamicDateInDateTimePicker(driver, getBackWardDay(1));

		log.info("TC_07_Step_04: CLick Ok");
		transferInVCB.clickToDynamicButton(driver, "OK");

		log.info("TC_07_Step_04: Kiem tra date do k duoc chon, mac dinh van la ngay mai");
		verifyEquals(transferInVCB.getDynamicTextInDropDownByHeader(driver, "Ngày hiệu lực", "1"), tomorrow);

	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
