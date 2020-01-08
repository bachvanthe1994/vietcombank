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
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Transfer_Money_Immedidately_Validation_Part4 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;

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

	@Test
	public void TC_48_KiemTraManHinhXacThucGiaoDichBangOTP() {
		log.info("TC_48_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_48_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_48_Step_03: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_48_Step_04: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_48_Step_05: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_48_Step_06: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_48_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_48_Step_08: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_48_Step_09: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_48_Step_10: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_48_Step_11: Nhap OTP");
		transferInVCB.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_48_Step_12: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_48_Step_13: Click tiep tuc");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_48_Step_14: Click tiep tuc");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_48_Step_15: Click tiep tuc");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Lưu thụ hưởng"));
	}

	@Test
	public void TC_49_KiemTraNhanChonButtonChiaSeKhiHuy() {
		log.info("TC_49_Step_01: Click button chia se");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chia sẻ");

		log.info("TC_49_Step_02: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_deny_button");

		log.info("TC_48_Step_15: Kiem tra user khong duoc cap quyen");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Bạn không được cấp quyền để sử dụng tính năng này."));

		log.info("TC_48_Step_12: Click Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

	}

	@Test
	public void TC_50_KiemTraNhanChonDanhBaThuHuong() {
		log.info("TC_50_Step_01: Click button chia se");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Lưu thụ hưởng");

		log.info("TC_50_Step_02: Kiem tra user khong duoc cap quyen");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Lưu danh bạ"));

		log.info("TC_51_Step_03: Kiem tra user khong duoc cap quyen");
		transferInVCB.clickToDynamicBackIcon(driver, "Lưu danh bạ");

	}

	@Test
	public void TC_51_KiemTraIconHome() {

		log.info("TC_50_Step_01: Click Icon Home");
		transferInVCB.clickToDynamicBottomMenu(driver, "com.VCB:id/ivHome");

		log.info("TC_48_Step_10: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_48_Step_15: Kiem tra user khong duoc cap quyen");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "TÀI KHOẢN THANH TOÁN"));

	}

	@Test
	public void TC_52_KiemTraTaiKhoanMacDinh() {

		log.info("TC_52_Step_01: Click Chuyen tien trong VCB");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.scrollToText(driver, "Trạng thái lệnh chuyển tiền");
		transferInVCB.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_52_Step_02: Kiem tra tai khoan mac dinh");
		verifyEquals(transferInVCB.getDynamicTextInDropDownByLable(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.DEFAULT_ACCOUNT2);

	}

	@Test
	public void TC_53_KiemTraDinhDangSoDuKhiChonTaiKhoanVND() {

		log.info("TC_53_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_53_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_53_Step_04: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");

		log.info("TC_53_Step_04: Lay so du tai khoan dich");
		verifyTrue(beforeBalanceOfAccount2.contains(",") && beforeBalanceOfAccount2.contains("VND"));

	}

	@Test
	public void TC_54_KiemTraDinhDangSoDuKhiChonTaiKhoanUSD() {

		log.info("TC_54_Step_01:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_54_Step_02: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.USD_ACCOUNT);

		log.info("TC_54_Step_03: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");

		log.info("TC_54_Step_04: Lay so du tai khoan dich");
		verifyTrue(beforeBalanceOfAccount2.contains(",") && beforeBalanceOfAccount2.contains("USD"));

	}

	@Test
	public void TC_55_KiemTraDinhDangSoDuKhiChonTaiKhoanEUR() {

		log.info("TC_55_Step_01:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_55_Step_02: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_55_Step_03: Lay so du tai khoan dich");
		String beforeBalanceOfAccount2 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");

		log.info("TC_55_Step_04: Lay so du tai khoan dich");
		verifyTrue(beforeBalanceOfAccount2.contains(",") && beforeBalanceOfAccount2.contains("EUR"));

	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
