package vnpay.vietcombank.transfer_money_charity;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import model.TransferCharity;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyCharityPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyCharity_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Validation_OTP_Charity extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyCharityPageObject transferMoneyCharity;

	TransferCharity info = new TransferCharity(TransferMoneyInVCB_Data.InputDataInVCB.ACCOUNT3, "Test order", "1000000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })

	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);
		
		log.info("TC_00_1_Click Chuyen tien tu thien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		log.info("TC_00_2_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_00_3_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicInput(driver, "Quỹ/ Tổ chức từ thiện");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.organization);

		log.info("TC_00_4_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.money, "Số tiền ủng hộ");

		log.info("TC_00_5_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, "Tên người ủng hộ");

		log.info("TC_00_6_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, "Địa chỉ người ủng hộ");

		log.info("TC_00_7_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, "Hoàn cảnh người ủng hộ");

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
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.OTP_NOTIFICATION_SENDED));
		
		log.info("TC_01_03_Kiem tra button Tiep tuc");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Tiếp tục"));
		
	}

	@Test
	public void TC_02_OTP_NutTiepTuc_BoTrongOTP() {
		log.info("TC_02_01_Click nut Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_02_02_Kiem tra message thong bao loi");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.OTP_EMPTY));
		
		log.info("TC_02_03_Kiem tra hien thi nut Dong");
		transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng");
		
		log.info("TC_02_04_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
	}
	
	@Test
	public void TC_03_OTP_NutTiepTuc_NhapOTPNhoHon6KyTu() {
		log.info("TC_03_01_Nhap ma OTP nho hon 6 Ky tu");
		transferMoneyCharity.inputToDynamicOtpOrPIN(driver, "123", "Tiếp tục");
		
		log.info("TC_03_02_Click nut Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_03_03_Kiem tra message thong bao loi");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.OTP_LESS_THAN_6_CHARACTER));
		
		log.info("TC_03_04_Kiem tra hien thi nut Dong");
		transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng");
		
		log.info("TC_03_05_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
	}
	
//	@Test
	public void TC_04_OTP_NutTiepTuc_NhapOTPLonHon6KyTu() {
		log.info("TC_04_01_Nhap ma OTP lon hon 6 Ky tu");
		transferMoneyCharity.inputToDynamicOtpOrPIN(driver, "1234567", "Tiếp tục");
		
	}
	
	@Test
	public void TC_05_OTP_NutTiepTuc_NhapOTPKhongChinhXac() {
		log.info("TC_05_01_Nhap ma OTP khong chinh xac");
		transferMoneyCharity.inputToDynamicOtpOrPIN(driver, "213456", "Tiếp tục");
		
		log.info("TC_05_02_Click nut Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_03_Kiem tra message thong bao loi");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.OTP_INVALID));
		
		log.info("TC_05_04_Kiem tra hien thi nut Dong");
		transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng");
		
		log.info("TC_05_05_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_05_06_Click nut Quay lai");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");
		
		log.info("TC_05_07_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
	}
	
	@Test
	public void TC_06_OTP_NutTiepTuc_NhapOTPKhongChinhXac_NhoHon_n_Lan() {
		log.info("TC_06_01_Nhap ma OTP khong chinh xac");
		transferMoneyCharity.inputOTPInvalidBy_N_Times(LogIn_Data.Login_Account.OTP_INVALID_TIMES - 1);
		
		log.info("TC_06_02_Kiem tra message thong bao loi");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.OTP_INVALID));
		
		log.info("TC_06_03_Kiem tra hien thi nut Dong");
		transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng");
		
		log.info("TC_06_04_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_06_05_Click nut Quay lai");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");
		
		log.info("TC_06_06_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
	}
	
	@Test
	public void TC_07_OTP_NutTiepTuc_NhapOTPKhongChinhXac_n_Lan_LienTiep() {
		log.info("TC_07_01_Nhap ma OTP khong chinh xac");
		transferMoneyCharity.inputOTPInvalidBy_N_Times(LogIn_Data.Login_Account.OTP_INVALID_TIMES);
		
		log.info("TC_07_02_Kiem tra message thong bao loi");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.OTP_INVALID_N_TIMES));
		
		log.info("TC_07_03_Kiem tra hien thi nut Dong");
		transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng");
		
		log.info("TC_07_04_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
		
		transferMoneyCharity.scrollToText(driver, "Hoàn cảnh người ủng hộ");
		
		log.info("TC_07_05_Kiem tra quay ve man hinh tao, xoa het thong tin da nhap");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền từ thiện"));
		
		log.info("TC_07_05_1_Kiem tra thong tin nguoi huong");
		String actualOrganization = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "1");
		verifyEquals(actualOrganization, "Quỹ/ Tổ chức từ thiện");
		
		log.info("TC_07_05_2_Kiem tra thong tin giao dich");
		String actualMoney = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		verifyEquals(actualMoney, "Số tiền ủng hộ");
		
		String actualName = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "2");
		verifyEquals(actualName, "Tên người ủng hộ");
		
		String actualAddress = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");
		verifyEquals(actualAddress, "Địa chỉ người ủng hộ");
		
		String actualStatus = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "4");
		verifyEquals(actualStatus, "Hoàn cảnh người ủng hộ");
		
	}
	
	@Test
	public void TC_08_OTP_NutTiepTuc_OTPHetHieuLuc() throws InterruptedException {
		log.info("TC_08_1_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_08_2_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicInput(driver, "Quỹ/ Tổ chức từ thiện");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.organization);

		log.info("TC_08_3_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.money, "Số tiền ủng hộ");

		log.info("TC_08_4_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, "Tên người ủng hộ");

		log.info("TC_08_5_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, "Địa chỉ người ủng hộ");

		log.info("TC_08_6_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, "Hoàn cảnh người ủng hộ");

		log.info("TC_08_7_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_08_8_Chon phuong thuc xac thuc");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);

		log.info("TC_08_9_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_08_10_Nhap ma OTP chinh xac");
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		Thread.sleep(35000);
		
		log.info("TC_08_11_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_08_12_Kiem tra message thong bao loi");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.OTP_EXPIRE));
		
		log.info("TC_08_13_Kiem tra hien thi nut Dong");
		transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng");
		
		log.info("TC_08_14_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_08_15_Kiem tra hien thi man hinh xac nhan thong tin");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, "Xác thực thông tin"));
		
		log.info("TC_08_16_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
	}
	
	@Test
	public void TC_09_OTP_NutTiepTuc_OTPHopLe() {
		log.info("TC_09_01_Nhap ma OTP chinh xac");
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		log.info("TC_09_02_Kiem tra man hinh Chuyen khoan thanh cong");
		//verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
