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
import model.TransferInVCBRecurrent;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyCharity_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Transfer_Money_Recurrent_Validation_Part_5 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TransferMoneyInVcbPageObject transferRecurrent;
	private HomePageObject homePage;

	TransferInVCBRecurrent info = new TransferInVCBRecurrent(Account_Data.Valid_Account.ACCOUNT3, "0010000000318", "1", "Ngày", "", "", "500000", "Người chuyển trả", "test", "SMS OTP");
	TransferInVCBRecurrent info1 = new TransferInVCBRecurrent(Account_Data.Valid_Account.EUR_ACCOUNT, "0010000000318", "2", "Ngày", "", "", "10", "Người nhận trả", "test", "SMS OTP");
	TransferInVCBRecurrent info2 = new TransferInVCBRecurrent(Account_Data.Valid_Account.USD_ACCOUNT, "0010000000318", "2", "Ngày", "", "", "10", "Người nhận trả", "test", "SMS OTP");
	TransferInVCBRecurrent info3 = new TransferInVCBRecurrent(Account_Data.Valid_Account.EUR_ACCOUN_2, "0010000000318", "2", "Ngày", "", "", "10", "Người nhận trả", "test", "SMS OTP");
	TransferInVCBRecurrent info4 = new TransferInVCBRecurrent(Account_Data.Valid_Account.USD_ACCOUNT_2, "0010000000318", "2", "Ngày", "", "", "10", "Người nhận trả", "test", "SMS OTP");
	
	
	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);
		transferRecurrent = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		homePage = PageFactoryManager.getHomePageObject(driver);

		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		login.clickToDynamicButton(driver, "Tiếp tục");

		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		
		log.info("TC_00_01_Click Chuyen tien trong ngan hang");
		homePage.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_00_02_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");

		log.info("TC_00_03_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		
		log.info("TC_00_04_Nhap tai khoan dich");
		transferRecurrent.inputToDynamicInputBox(driver, info.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_00_05_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info.frequencyNumber);

		log.info("TC_00_06_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info.money, "Số tiền");

		log.info("TC_00_07_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.fee);

		log.info("TC_00_08_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(driver, info.note, "Nội dung");

		log.info("TC_00_09_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_00_10_Chon phuong thuc xac thuc");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);
		
		log.info("TC_00_11_Click Tiep tuc man hinh xac nhan thong tin");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
	}

	@Test
	public void TC_01_OTP_KiemTraManHinhHienThi() {
		log.info("TC_01_01_Kiem tra title Xac thuc giao dich");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSACTION_VALIDATION));
		
		log.info("TC_01_02_Kiem tra text Ma OTP da duoc gui den SDT ...");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.OTP_NOTIFICATION_SENDED));
		
		log.info("TC_01_03_Kiem tra button Tiep tuc");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Tiếp tục"));
		
	}

	@Test
	public void TC_02_OTP_NutTiepTuc_BoTrongOTP() {
		log.info("TC_02_01_Click nut Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_02_02_Kiem tra message thong bao loi");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.OTP_EMPTY));
		
		log.info("TC_02_03_Kiem tra hien thi nut Dong");
		transferRecurrent.isDynamicButtonDisplayed(driver, "Đóng");
		
		log.info("TC_02_04_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
	}
	
	@Test
	public void TC_03_OTP_NutTiepTuc_NhapOTPNhoHon6KyTu() {
		log.info("TC_03_01_Nhap ma OTP nho hon 6 Ky tu");
		transferRecurrent.inputToDynamicOtpOrPIN(driver, "123", "Tiếp tục");
		
		log.info("TC_03_02_Click nut Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_03_03_Kiem tra message thong bao loi");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.OTP_LESS_THAN_6_CHARACTER));
		
		log.info("TC_03_04_Kiem tra hien thi nut Dong");
		transferRecurrent.isDynamicButtonDisplayed(driver, "Đóng");
		
		log.info("TC_03_05_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
	}
	
//	@Test
	public void TC_04_OTP_NutTiepTuc_NhapOTPLonHon6KyTu() {
		log.info("TC_04_01_Nhap ma OTP lon hon 6 Ky tu");
		transferRecurrent.inputToDynamicOtpOrPIN(driver, "1234567", "Tiếp tục");
		
	}
	
	@Test
	public void TC_05_OTP_NutTiepTuc_NhapOTPKhongChinhXac() {
		log.info("TC_05_01_Nhap ma OTP khong chinh xac");
		transferRecurrent.inputToDynamicOtpOrPIN(driver, "213456", "Tiếp tục");
		
		log.info("TC_05_02_Click nut Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_05_03_Kiem tra message thong bao loi");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.OTP_INVALID));
		
		log.info("TC_05_04_Kiem tra hien thi nut Dong");
		transferRecurrent.isDynamicButtonDisplayed(driver, "Đóng");
		
		log.info("TC_05_05_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_05_06_Click nut Quay lai");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");
		
		log.info("TC_05_07_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
	}
	
	@Test
	public void TC_06_OTP_NutTiepTuc_NhapOTPKhongChinhXac_NhoHon_n_Lan() {
		log.info("TC_06_01_Nhap ma OTP khong chinh xac");
		transferRecurrent.inputOTPInvalidBy_N_Times(driver, LogIn_Data.Login_Account.OTP_INVALID_TIMES - 1);
		
		log.info("TC_06_02_Kiem tra message thong bao loi");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.OTP_INVALID));
		
		log.info("TC_06_03_Kiem tra hien thi nut Dong");
		transferRecurrent.isDynamicButtonDisplayed(driver, "Đóng");
		
		log.info("TC_06_04_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_06_05_Click nut Quay lai");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");
		
		log.info("TC_06_06_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
	}
	
	@Test
	public void TC_07_OTP_NutTiepTuc_NhapOTPKhongChinhXac_n_Lan_LienTiep() {
		log.info("TC_07_01_Nhap ma OTP khong chinh xac");
		transferRecurrent.inputOTPInvalidBy_N_Times(driver, LogIn_Data.Login_Account.OTP_INVALID_TIMES);
		
		log.info("TC_07_02_Kiem tra message thong bao loi");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.OTP_INVALID_N_TIMES));
		
		log.info("TC_07_03_Kiem tra hien thi nut Dong");
		transferRecurrent.isDynamicButtonDisplayed(driver, "Đóng");
		
		log.info("TC_07_04_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
		
		transferRecurrent.scrollToText(driver, "Hoàn cảnh người ủng hộ");
		
		log.info("TC_07_05_Kiem tra quay ve man hinh tao, xoa het thong tin da nhap");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền từ thiện"));
		
		log.info("TC_07_05_1_Kiem tra thong tin nguoi huong");
		String actualOrganization = transferRecurrent.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "1");
		verifyEquals(actualOrganization, "Quỹ/ Tổ chức từ thiện");
		
		log.info("TC_07_05_2_Kiem tra thong tin giao dich");
		String actualMoney = transferRecurrent.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		verifyEquals(actualMoney, "Số tiền ủng hộ");
		
		String actualName = transferRecurrent.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "2");
		verifyEquals(actualName, "Tên người ủng hộ");
		
		String actualAddress = transferRecurrent.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");
		verifyEquals(actualAddress, "Địa chỉ người ủng hộ");
		
		String actualStatus = transferRecurrent.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "4");
		verifyEquals(actualStatus, "Hoàn cảnh người ủng hộ");
		
	}
	
	@Test
	public void TC_08_OTP_NutTiepTuc_OTPHetHieuLuc() throws InterruptedException {
		log.info("TC_08_01_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		
		log.info("TC_08_02_Nhap tai khoan dich");
		transferRecurrent.inputToDynamicInputBox(driver, info.destinationAccount, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_08_03_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info.frequencyNumber);

		log.info("TC_08_04_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info.money, "Số tiền");

		log.info("TC_08_05_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.fee);

		log.info("TC_08_06_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(driver, info.note, "Nội dung");

		log.info("TC_08_07_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_08_08_Chon phuong thuc xac thuc");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);
		
		log.info("TC_08_09_Click Tiep tuc man hinh xac nhan thong tin");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_08_10_Nhap ma OTP chinh xac");
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		Thread.sleep(35000);
		
		log.info("TC_08_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_08_12_Kiem tra message thong bao loi");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.OTP_EXPIRE));
		
		log.info("TC_08_13_Kiem tra hien thi nut Dong");
		transferRecurrent.isDynamicButtonDisplayed(driver, "Đóng");
		
		log.info("TC_08_14_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_08_15_Kiem tra hien thi man hinh xac nhan thong tin");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Xác thực thông tin"));
		
		log.info("TC_08_16_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
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
