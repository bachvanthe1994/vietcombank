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

public class Transfer_Money_Immedidately_Validation_Part3 extends Base {
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
	public void TC_32_KiemTraChuyenKhoanTuTaiKhoanUSDSangUSDVaDongPopup() {

		log.info("TC_32_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_32_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_32_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.USD_ACCOUNT);

		log.info("TC_32_Step_04: Nhap Tai Khoan Nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.USD_ACCOUNT_2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_32_Step_05: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_32_Step_06: Nhap So Tien Chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_32_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_32_Step_08: Kiem tra yeu cau nhap noi dung");

		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.INVALID_RECEIVE_ACCOUNT);

		log.info("TC_32_Step_09: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_32_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_33_KiemTraChuyenKhoanTuTaiKhoanEURSangEURVaDongPopup() {

		log.info("TC_33_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_33_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_33_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_33_Step_04: Nhap Tai Khoan Nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.EUR_ACCOUN_2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_33_Step_05: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_33_Step_06: Nhap So Tien Chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_33_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_33_Step_08: Kiem tra yeu cau nhap noi dung");

		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.INVALID_RECEIVE_ACCOUNT);

		log.info("TC_33_Step_09: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_33_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_34_KiemTraLoiKhongDuSoDuVaDongPopup() {

		log.info("TC_34_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_34_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_34_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_01_Step_07: Lay so du tai khoan chuyen");
		transferInVCB.sleep(driver, 2000);
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_34_Step_04: Nhap Tai Khoan Nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_34_Step_05: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_34_Step_06: Nhap So Tien Chuyen");
		transferInVCB.inputToDynamicInputBox(driver, beforeBalanceAmountOfAccount1 + 1000 + "", "Số tiền");

		log.info("TC_34_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_34_Step_08: Kiem tra yeu cau nhap noi dung");
		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.NOT_ENOUGH_MONEY);

		log.info("TC_34_Step_09: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_34_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_35_KiemTraSoTienGiaoDichNhoHonHanMucToiThieuVaDongPopup() {

		log.info("TC_35_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_35_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_35_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_35_Step_04: Nhap Tai Khoan Nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_35_Step_05: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_35_Step_06: Nhap So Tien Chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InvalidInputData.INVALID_MIN_TRANSFER_AMOUNT, "Số tiền");

		log.info("TC_35_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_35_Step_08: Kiem tra yeu cau nhap noi dung");
		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.ERROR_MESSAGE_WITH_MIN_LIMIT_TRANSFER_MONEY);

		log.info("TC_35_Step_09: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_35_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_36_KiemTraSoTienGiaoDichNhoHonHanMucToiThieuVaDongPopup() {

		log.info("TC_36_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_36_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_36_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT3);

		log.info("TC_36_Step_04: Nhap Tai Khoan Nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_36_Step_05: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_36_Step_06: Nhap So Tien Chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InvalidInputData.INVALID_MAX_TRANSFER_AMOUNT, "Số tiền");

		log.info("TC_36_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_36_Step_08: Kiem tra yeu cau nhap noi dung");
		verifyEquals(transferInVCB.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.ERROR_MESSAGE_WITH_MAX_LIMIT_TRANSFER_MONEY);

		log.info("TC_36_Step_09: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_36_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_37_KiemTraButtonBackTaiManHinhXacThucGiaoDich() {
		log.info("TC_37_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_37_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_37_Step_06: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_37_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_37_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_37_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_37_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_37_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Xác nhận thông tin");

		log.info("TC_37_Step_10: Click Quay Lai");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền trong Vietcombank"));

		log.info("TC_37_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@Test
	public void TC_38_KiemTraButtonDongTaiPopupChonPhuongThucXacThuc() {
		log.info("TC_38_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_38_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_38_Step_06: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_38_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_38_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_38_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_38_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_38_Step_12: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_38_Step_13: Chon Dong");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_38_Step_14: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Xác nhận thông tin");

		log.info("TC_38_Step_15: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_38_Step_16: Click Quay Lai");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền tới ngân hàng khác"));
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền nhanh 24/7"));

	}

	@Test
	public void TC_39_KiemTraManHinhXacThucGiaoDichBangOTP() {
		log.info("TC_39_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_39_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_39_Step_03: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_39_Step_04: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_39_Step_05: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_39_Step_06: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_39_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_39_Step_08: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_39_Step_09: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_39_Step_10: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_39_Step_11: Kiem tra xac thuc giao dich hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Xác thực giao dịch"));

		log.info("TC_39_Step_12: Kiem tra inbox message hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Quý khách vui lòng nhập OTP đã được gửi về số điện thoại " + LogIn_Data.Login_Account.PHONE.substring(0, 4) + "***" + LogIn_Data.Login_Account.PHONE.substring(7, 10)));

	}

	@Test
	public void TC_40_KiemTraBoTrongXacThucOTP() {

		log.info("TC_40_Step_01: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_40_Step_02: Kiem tra popup message hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.ERROR_MESSAGE_FOR_EMPTY_OTP));

		log.info("TC_40_Step_03: Chon Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

	}

	@Test
	public void TC_41_KiemXacThucOTPNhoHon6KyTu() {

		log.info("TC_41_Step_02: Dien OTP nho hon 6 ky tu");
		transferInVCB.inputToDynamicOtpOrPIN(driver, TransferMoneyInVCB_Data.InvalidInputData.OTP_WITH_LESS_THAN_6_CHARACTERS, "Tiếp tục");

		log.info("TC_41_Step_03: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_41_Step_04: Kiem tra popup message hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.ERROR_MESSAGE_FOR_OTP_LESS_THAN_6));

		log.info("TC_41_Step_05: Chon Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_42_KiemXacThucOTPLonHon6KyTu() {

		log.info("TC_42_Step_02: Dien OTP lon hon 6 ky tu");
		transferInVCB.inputToDynamicOtpOrPIN(driver, TransferMoneyInVCB_Data.InvalidInputData.INVALID_MAX_TRANSFER_AMOUNT, "Tiếp tục");

		log.info("TC_42_Step_04: Kiem tra popup message hien thi");
		verifyEquals(transferInVCB.getTextInDynamicOtp(driver, "Tiếp tục"), TransferMoneyInVCB_Data.InvalidInputData.INVALID_MAX_TRANSFER_AMOUNT.subSequence(0, 6));
	}

	@Test
	public void TC_43_KiemXacNhapSaiOTP() {

		log.info("TC_43_Step_02: Dien OTP lon hon 6 ky tu");
		transferInVCB.inputToDynamicOtpOrPIN(driver, TransferMoneyInVCB_Data.InvalidInputData.WRONG_OTP, "Tiếp tục");

		log.info("TC_43_Step_03: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_43_Step_04: Kiem tra popup message hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.ERROR_MESSAGE_FOR_WRONG_OTP));

		log.info("TC_43_Step_05: Chon Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_43_Step_05: Chon Quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Quay lại");

		log.info("TC_43_Step_14: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Xác nhận thông tin");

		log.info("TC_43_Step_15: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_44_KiemTraManHinhNhapMatKhau() {
		log.info("TC_44_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_44_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_44_Step_06: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_44_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_44_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, "20000", "Số tiền");

		log.info("TC_44_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Nội dung");

		log.info("TC_44_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_44_Step_20: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_44_Step_21: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_44_Step_22: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_44_Step_11: Kiem tra xac thuc giao dich hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Xác thực giao dịch"));

		log.info("TC_44_Step_12: Kiem tra inbox message hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Vui lòng nhập mật khẩu đăng nhập ứng dụng của Quý khách để xác nhận giao dịch"));

	}

	@Test
	public void TC_45_KiemTraBoTrongMatKhau() {
		log.info("TC_45_Step_01: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_45_Step_02: Kiem tra popup message hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.ERROR_MESSAGE_FOR_EMPTY_PASSWORD));

		log.info("TC_45_Step_03: Chon Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

	}

	@Test
	public void TC_46_KiemTraNhapMatKhauSai() {

		log.info("TC_46_Step_23: Nhap Mat Khau sai");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, "wrongpass", "Tiếp tục");

		log.info("TC_46_Step_01: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_46_Step_02: Kiem tra popup message hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.ERROR_MESSAGE_FOR_WRONG_PASSWORD));

		log.info("TC_46_Step_03: Chon Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

	}

	@Test
	public void TC_47_KiemTraNhapMatKhauDaiHon20KyTu() {

		log.info("TC_47_Step_23: Nhap Mat Khau dai hon 20 ky tu");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, TransferMoneyInVCB_Data.InvalidInputData.MORE_THAN_21_CHARACTERS, "Tiếp tục");

		log.info("TC_47_Step_02: Kiem tra do dai mat khau");
		verifyEquals(transferInVCB.getTextInDynamicPasswordInput(driver, "com.VCB:id/pin").length(), 20);

		log.info("TC_47_Step_05: Chon Quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Quay lại");

		log.info("TC_47_Step_14: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Xác nhận thông tin");

		log.info("TC_47_Step_15: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
