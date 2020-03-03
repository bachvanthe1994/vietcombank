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
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyCharity_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Validation_Password_Charity extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyCharityPageObject transferMoneyCharity;

	TransferCharity info = new TransferCharity(Account_Data.Valid_Account.DEFAULT_ACCOUNT3, TransferMoneyCharity_Data.ORGANIZATION, "1000000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

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
	public void TC_01_MatKhau_KiemTraManHinhHienThi() {
		log.info("TC_01_01_Kiem tra title Xac thuc giao dich");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.TRANSACTION_VALIDATION));

		log.info("TC_01_02_Kiem tra text Vui long nhap mat khau dang nhap ...");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.PASSWORD_NOTIFICATION));

		log.info("TC_01_03_Kiem tra button Tiep tuc");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Tiếp tục"));

	}

	@Test
	public void TC_02_MatKhau_NutTiepTuc_BoTrongMatKhau() {
		log.info("TC_02_01_Click nut Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_02_Kiem tra message thong bao loi");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.PASSWORD_EMPTY));

		log.info("TC_02_03_Kiem tra hien thi nut Dong");
		transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng");

		log.info("TC_02_04_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_03_MatKhau_NutTiepTuc_NhapMatKhauNhoHon8KyTu() {
		log.info("TC_03_01_Nhap Mat khau nho hon 8 Ky tu");
		transferMoneyCharity.inputToDynamicPopupPasswordInput(driver, "123", "Tiếp tục");

		log.info("TC_03_02_Click nut Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_03_Kiem tra message thong bao loi");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.PASSWORD_LESS_THAN_8_CHARACTER));

		log.info("TC_03_04_Kiem tra hien thi nut Dong");
		transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng");

		log.info("TC_03_05_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_04_MatKhau_NutTiepTuc_NhapMatKhauLonHon8KyTu() {
		log.info("TC_04_01_Nhap mat khau lon hon 8 ky tu");
		transferMoneyCharity.inputToDynamicPopupPasswordInput(driver, "123456789", "Tiếp tục");

		log.info("TC_47_Step_02: Kiem tra do dai mat khau");
		verifyEquals(transferMoneyCharity.getTextInDynamicPasswordInput(driver, "com.VCB:id/pin").length(), 8);

	}

	@Test
	public void TC_05_MatKhau_NutTiepTuc_NhapMatKhauKhongChinhXac() {
		log.info("TC_05_01_Nhap mat khau khong chinh xac");
		transferMoneyCharity.inputToDynamicPopupPasswordInput(driver, "12345678", "Tiếp tục");

		log.info("TC_05_02_Click nut Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_03_Kiem tra message thong bao loi");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.PASSWORD_INVALID));

		log.info("TC_05_04_Kiem tra hien thi nut Dong");
		transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng");

		log.info("TC_05_05_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");

		log.info("TC_05_06_Click nut Quay lai");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");

		log.info("TC_05_07_Click Back");
		transferMoneyCharity.clickToDynamicBackIcon(driver, "Xác nhận thông tin");

	}

//	@Test
	public void TC_06_MatKhau_NutTiepTuc_NhapMatKhauKhongChinhXac_5_Lan() {
		log.info("TC_06_01_Nhap ma OTP khong chinh xac");
		transferMoneyCharity.inputPasswordInvalidBy_N_Times(driver, LogIn_Data.Login_Account.PASSWORD_INVALID_TIMES);

		log.info("TC_06_02_Kiem tra message thong bao loi");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.PASSWORD_INVALID_N_TIMES));

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
	public void TC_07_MatKhau_NutTiepTuc_MatKhauHopLe() {
		log.info("TC_07_1_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_07_2_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicInput(driver, "Quỹ/ Tổ chức từ thiện");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.organization);

		log.info("TC_07_3_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, info.money, "Thông tin giao dịch", "1");

		log.info("TC_07_4_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, info.name, "Thông tin giao dịch", "2");

		log.info("TC_07_5_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, info.address, "Thông tin giao dịch", "3");

		log.info("TC_07_6_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, info.status, "Thông tin giao dịch", "4");

		log.info("TC_07_7_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_8_Chon phuong thuc xac thuc");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);

		log.info("TC_07_9_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_10_Nhap mat khau chinh xac");
		transferMoneyCharity.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_07_11_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_12_Kiem tra man hinh Chuyen khoan thanh cong");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY));

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
