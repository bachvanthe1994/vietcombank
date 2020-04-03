package vnpay.vietcombank.transfer_money_charity;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.TransferCharity;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyCharityPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyCharity_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Validation_OTP_Charity extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyCharityPageObject transferMoneyCharity;
	String phoneNumber = "";

	TransferCharity info = new TransferCharity(Account_Data.Valid_Account.ACCOUNT2, TransferMoneyCharity_Data.ORGANIZATION, "100000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "SMS OTP");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		phoneNumber = phone;

		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);

		log.info("TC_00_1_Click Chuyen tien tu thien");
		homePage.scrollIDownOneTime(driver);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		log.info("TC_00_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_00_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Quỹ/ Tổ chức từ thiện");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.organization);

		log.info("TC_00_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.money, "Số tiền ủng hộ");

		log.info("TC_00_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, "Tên người ủng hộ");

		log.info("TC_00_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, "Địa chỉ người ủng hộ");

		log.info("TC_00_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, "Hoàn cảnh ủng hộ");

		log.info("TC_00_8_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_00_9_Chon phuong thuc xac thuc");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);

		log.info("TC_05_10_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
	}

	@Test
	public void TC_01_OTP_KiemTraManHinhHienThi() {
		log.info("TC_01_01_Kiem tra title Xac thuc giao dich");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.TRANSACTION_VALIDATION));

		log.info("TC_01_02_Kiem tra text Ma OTP da duoc gui den SDT ...");
		verifyEquals(transferMoneyCharity.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/lblMessage"), "Quý khách vui lòng nhập mã OTP đã được gửi về số điện thoại " + phoneNumber.substring(0, 3) + "*****" + phoneNumber.substring(8, 10));

		log.info("TC_01_03_Kiem tra button Tiep tuc");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Tiếp tục"));

	}

	@Test
	public void TC_02_OTP_NutTiepTuc_BoTrongOTP() {
		log.info("TC_02_01_Click nut Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_02_Kiem tra message thong bao loi");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.OTP_EMPTY));

		log.info("TC_02_03_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_03_OTP_NutTiepTuc_NhapOTPNhoHon6KyTu() {
		log.info("TC_03_01_Nhap ma OTP nho hon 6 Ky tu");
		transferMoneyCharity.inputToDynamicOtp(driver, "123", "Tiếp tục");

		log.info("TC_03_02_Click nut Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_03_Kiem tra message thong bao loi");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.OTP_LESS_THAN_6_CHARACTER));

		log.info("TC_03_04_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_04_OTP_NutTiepTuc_NhapOTPLonHon6KyTu() {
		log.info("TC_04_01_Nhap ma OTP lon hon 6 Ky tu");
		transferMoneyCharity.inputToDynamicOtp(driver, "1234567", "Tiếp tục");

		log.info("TC_04_02_Kiem tra OTP");
		String otp = transferMoneyCharity.getTextInDynamicOtp(driver, "Tiếp tục");
		verifyEquals(otp, "123456");

	}

	@Test
	public void TC_05_OTP_NutTiepTuc_NhapOTPKhongChinhXac() {
		log.info("TC_05_01_Nhap ma OTP khong chinh xac");
		transferMoneyCharity.inputToDynamicOtp(driver, "213456", "Tiếp tục");

		log.info("TC_05_02_Click nut Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_03_Kiem tra message thong bao loi");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.OTP_INVALID));

		log.info("TC_05_04_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");

		log.info("TC_05_05_Click nut Quay lai");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");

		log.info("TC_05_06_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

	}

	@Test
	public void TC_06_OTP_NutTiepTuc_NhapOTPKhongChinhXac_NhoHon_n_Lan() {
		log.info("TC_06_01_Nhap ma OTP khong chinh xac");
		transferMoneyCharity.inputOTPInvalidBy_N_Times(driver, LogIn_Data.Login_Account.OTP_INVALID_TIMES - 1);

		log.info("TC_06_02_Kiem tra message thong bao loi");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.OTP_INVALID));

		log.info("TC_06_03_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");

		log.info("TC_06_04_Click nut Quay lai");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");

		log.info("TC_06_04_Click nut Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, "Xác nhận thông tin");

	}

	@Test
	public void TC_08_OTP_NutTiepTuc_OTPHopLe() {
		log.info("TC_08_1_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_08_2_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicDropdownByHeader(driver, "Thông tin người hưởng", "1");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.organization);

		log.info("TC_08_3_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, info.money, "Thông tin giao dịch", "1");

		log.info("TC_08_4_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, info.name, "Thông tin giao dịch", "2");

		log.info("TC_08_5_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, info.address, "Thông tin giao dịch", "3");

		log.info("TC_08_6_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, info.status, "Thông tin giao dịch", "4");

		log.info("TC_08_7_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_08_8_Chon phuong thuc xac thuc");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);

		log.info("TC_08_9_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_08_10_Nhap ma OTP chinh xac");
		login.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_08_11_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_08_12_Kiem tra man hinh Chuyen khoan thanh cong");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
