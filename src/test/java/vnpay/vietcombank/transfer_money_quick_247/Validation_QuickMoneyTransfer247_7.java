package vnpay.vietcombank.transfer_money_quick_247;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Validation_QuickMoneyTransfer247_7 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TransferMoneyObject transferMoney;
	List<String> listExpect;
	List<String> listActual;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("Before class: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		log.info("Before class");
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		log.info("Before class");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		log.info("Before class");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class");
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("Before class");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);

		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_01_Step_Select tai khoan nguon");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_01_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT_TO, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.BANK[0]);

		log.info("TC_01_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Thông tin giao dịch", "1");

		log.info("TC_01_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_01_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_01_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
	}

	@Test
	public void TC_99_VerifyManHinhXacThucGiaoDichBangMatKhau() {
		log.info("TC_01_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);

		log.info("TC_05_Step_click button tiep tục");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_90_Verify text xac thuc giao dich");
		verifyEquals(transferMoney.getTextDynamicInSelectBox(driver, TransferMoneyQuick_Data.TransferQuick.CONFIRM_LABEL), "Xác thực giao dịch");

		log.info("TC_90_Verify message xac thuc mat khau");
		verifyEquals(transferMoney.getDynamicAmountLabel(driver, "Xác thực giao dịch"), "Vui lòng nhập mật khẩu đăng nhập ứng dụng của Quý khách để xác thực giao dịch");

		log.info("TC_90_Verify hien thi button tiep tuc");
		verifyTrue(transferMoney.isDynamicButtonDisplayed(driver, "Tiếp tục"));
	}

	@Test
	public void TC_100_VerifyMessageKhiKhongNhapMatKhau() {
		log.info("TC_91_Click button tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_91_Step_verify message khi khong nhap mat khau ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.PASS_BLANK_MESSAGE));

		log.info("TC_91_Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}
	
	@Test
	public void TC_101_VerifyMessageKhiNhapMatKhauKhongTonTai() {
		log.info("TC_95_Nhap mat khau khong ton tai");
		transferMoney.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.OTP_NUMBER_INVALID, "Tiếp tục");

		log.info("TC_95_Click button tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_95_Step_verify message khi nhap mat khau khong dung");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.OTP_NOT_EXIST_MESSAGE));

		log.info("TC_95_Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_102_NhapMatKhauVuotQua20KyTu() {
		log.info("TC_95_Nhap mat khau khong ton tai");
		transferMoney.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.PASSWORD_GREATER_THAN_20, "Tiếp tục");

		log.info("TC_95_Click button tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

	}

}
