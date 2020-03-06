package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Transfer_Money_Immedidately_Validation_Part4 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, "Trạng thái lệnh chuyển tiền");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
	}

	@Test
	public void TC_01_KiemTraManHinhXacThucGiaoDichBangOTP() {
		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_01_Step_02:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_03: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_01_Step_04: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_05: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_01_Step_06: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_01_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_08: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_01_Step_09: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_01_Step_10: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_11: Nhap OTP");
		transferInVCB.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_01_Step_12: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_13: Click tiep tuc");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_01_Step_14: Click tiep tuc");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_01_Step_15: Click tiep tuc");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Lưu thụ hưởng"));
	}

	@Test
	public void TC_02_KiemTraNhanChonButtonChiaSeVaHuy() {
		log.info("TC_02_Step_01: Click button chia se");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chia sẻ");

		log.info("TC_02_Step_02: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_deny_button");

		log.info("TC_01_Step_03: Kiem tra user khong duoc cap quyen");
		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), "Bạn không được cấp quyền để sử dụng tính năng này.");

		log.info("TC_01_Step_04: Click Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

	}

	@Test
	public void TC_03_KiemTraNhanChonDanhBaThuHuong() {
		log.info("TC_03_Step_01: Click button chia se");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Lưu thụ hưởng");

		log.info("TC_03_Step_02: Kiem tra user khong duoc cap quyen");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Lưu danh bạ"));

		log.info("TC_04_Step_03: Click quay lai ");
		transferInVCB.clickToDynamicBackIcon(driver, "Lưu danh bạ");

	}

	@Test
	public void TC_04_KiemTraIconLuuAnhVaHuy() {
		log.info("TC_04_Step_01: Click button chia se");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Lưu ảnh");

		log.info("TC_04_Step_02: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_deny_button");

		log.info("TC_04_Step_03: Kiem tra user khong duoc cap quyen");
		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), "Bạn không được cấp quyền để sử dụng tính năng này.");

		log.info("TC_04_Step_04: Click Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

	}

	@Test
	public void TC_05_KiemTraIconLuuAnhVaDongY() {
		log.info("TC_05_Step_01: Click button chia se");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Lưu ảnh");

		log.info("TC_05_Step_02: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		log.info("TC_05_Step_03: Kiem tra user khong duoc cap quyen");
		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), "Lưu vào thư viện ảnh thành công");

		log.info("TC_05_Step_04: Click Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

	}

	@Test
	public void TC_06_KiemTraIconLuuAnhLan2VaDongY() {
		log.info("TC_06_Step_01: Click button chia se");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Lưu ảnh");

		log.info("TC_06_Step_03: Kiem tra user khong duoc cap quyen");
		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), "Ảnh đã lưu trong thư viện.");

		log.info("TC_06_Step_04: Click Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

	}

	@Test
	public void TC_07_KiemTraIconHome() {

		log.info("TC_07_Step_01: Click Icon Home");
		transferInVCB.clickToDynamicBottomMenu(driver, "com.VCB:id/ivHome");

		log.info("TC_07_Step_15: Kiem tra user khong duoc cap quyen");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "TÀI KHOẢN THANH TOÁN"));

	}

	@Test
	public void TC_08_KiemTraHienThiGoiYNhanhKhiFocusVaoOSoTien() {

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		transferInVCB.clickToDynamicInput(driver, "Số tiền");
		verifyTrue(transferInVCB.isKeyBoardDisplayed(driver));

		transferInVCB.hideKeyBoard(driver);

	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
