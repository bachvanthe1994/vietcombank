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
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Transfer_Money_Recurrent_Validation_Part_6 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TransferMoneyInVcbPageObject transferRecurrent;
	private HomePageObject homePage;

	TransferInVCBRecurrent info = new TransferInVCBRecurrent(Account_Data.Valid_Account.DEFAULT_ACCOUNT2, Account_Data.Valid_Account.ACCOUNT3, "1", "Ngày", "", "", "500000", "Người chuyển trả", "test", "Mật khẩu đăng nhập");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		
		transferRecurrent = PageFactoryManager.getTransferMoneyInVcbPageObject(driver); 
		homePage = PageFactoryManager.getHomePageObject(driver);

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
	public void TC_01_MatKhau_KiemTraManHinhHienThi() {
		log.info("TC_01_01_Kiem tra title Xac thuc giao dich");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSACTION_VALIDATION));

		log.info("TC_01_02_Kiem tra text Vui long nhap mat khau dang nhap ...");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.PASSWORD_NOTIFICATION));

		log.info("TC_01_03_Kiem tra button Tiep tuc");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Tiếp tục"));

	}

	@Test
	public void TC_02_MatKhau_NutTiepTuc_BoTrongMatKhau() {
		log.info("TC_02_01_Click nut Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_02_Kiem tra message thong bao loi");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.PASSWORD_EMPTY));

		log.info("TC_02_03_Kiem tra hien thi nut Dong");
		transferRecurrent.isDynamicButtonDisplayed(driver, "Đóng");

		log.info("TC_02_04_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_03_MatKhau_NutTiepTuc_NhapMatKhauNhoHon8KyTu() {
		log.info("TC_03_01_Nhap Mat khau nho hon 8 Ky tu");
		transferRecurrent.inputToDynamicPopupPasswordInput(driver, "123", "Tiếp tục");

		log.info("TC_03_02_Click nut Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_03_Kiem tra message thong bao loi");
		verifyEquals(transferRecurrent.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.PASSWORD_LESS_THAN_8_CHARACTER);

		log.info("TC_03_04_Kiem tra hien thi nut Dong");
		transferRecurrent.isDynamicButtonDisplayed(driver, "Đóng");

		log.info("TC_03_05_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_04_MatKhau_NutTiepTuc_NhapMatKhauLonHon20KyTu() {
		log.info("TC_04_01: Nhap Mat Khau dai hon 20 ky tu");
		transferRecurrent.inputToDynamicPopupPasswordInput(driver, TransferMoneyInVCB_Data.InvalidInputData.MORE_THAN_21_CHARACTERS, "Tiếp tục");

		log.info("TC_04_02: Kiem tra do dai mat khau");
		verifyEquals(transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/pin").length(), 20);

	}

	@Test
	public void TC_05_MatKhau_NutTiepTuc_NhapMatKhauKhongChinhXac() {
		log.info("TC_05_01_Nhap mat khau khong chinh xac");
		transferRecurrent.inputToDynamicPopupPasswordInput(driver, "12345678", "Tiếp tục");

		log.info("TC_05_02_Click nut Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_03_Kiem tra message thong bao loi");
		verifyEquals(transferRecurrent.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.PASSWORD_INVALID);

		log.info("TC_05_04_Kiem tra hien thi nut Dong");
		transferRecurrent.isDynamicButtonDisplayed(driver, "Đóng");

		log.info("TC_05_05_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");

		log.info("TC_05_06_Click nut Quay lai");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");

		log.info("TC_05_07_Click Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Xác nhận thông tin");

	}

//	@Test
	public void TC_06_MatKhau_NutTiepTuc_NhapMatKhauKhongChinhXac_5_Lan() {
		log.info("TC_06_01_Nhap ma OTP khong chinh xac");
		transferRecurrent.inputPasswordInvalidBy_N_Times(driver, LogIn_Data.Login_Account.PASSWORD_INVALID_TIMES);

		log.info("TC_06_02_Kiem tra message thong bao loi");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.PASSWORD_INVALID_N_TIMES));

		log.info("TC_06_03_Kiem tra hien thi nut Dong");
		transferRecurrent.isDynamicButtonDisplayed(driver, "Đóng");

		log.info("TC_06_04_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");

		log.info("TC_06_05_Click nut Quay lai");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");

		log.info("TC_05_07_Click Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Xác nhận thông tin");
	}

	@Test
	public void TC_07_MatKhau_NutTiepTuc_MatKhauHopLe() {
		log.info("TC_07_01_Chon phuong thuc chuyen tien");

		log.info("TC_07_02_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_07_03_Nhap tai khoan dich");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info.destinationAccount, "Thông tin người hưởng", "1");

		log.info("TC_07_04_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info.frequencyNumber);

		log.info("TC_07_05_Nhap so tien");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info.money, "Thông tin giao dịch", "1");

		log.info("TC_07_06_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.fee);

		log.info("TC_07_07_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info.note, "Thông tin giao dịch", "3");

		log.info("TC_07_08_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_09_Chon phuong thuc xac thuc");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.authenticationMethod);

		log.info("TC_07_10_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_11_Nhap mat khau chinh xac");
		transferRecurrent.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");

		log.info("TC_07_11_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_13_Kiem tra man hinh Chuyen khoan thanh cong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT));

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
