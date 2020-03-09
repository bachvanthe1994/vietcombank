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
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Transfer_Money_Immedidately_Validation_Part3 extends Base {
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

	}

	@Test
	public void TC_01_KiemTraChuyenKhoanTuTaiKhoanUSDSangUSDVaDongPopup() {

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_01_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.USD_ACCOUNT);

		log.info("TC_01_Step_04: Nhap Tai Khoan Nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.USD_ACCOUNT_2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_01_Step_05: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_01_Step_06: Nhap So Tien Chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_01_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_08: Kiem tra yeu cau nhap noi dung");

		verifyEquals(transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.INVALID_RECEIVE_ACCOUNT);

		log.info("TC_01_Step_09: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_01_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_02_KiemTraChuyenKhoanTuTaiKhoanEURSangEURVaDongPopup() {

		log.info("TC_02_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_02_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_02_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.EUR_ACCOUNT);

		log.info("TC_02_Step_04: Nhap Tai Khoan Nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.EUR_ACCOUN_2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_02_Step_05: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_02_Step_06: Nhap So Tien Chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_02_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_08: Kiem tra yeu cau nhap noi dung");

		verifyEquals(transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.INVALID_RECEIVE_ACCOUNT_EUR);

		log.info("TC_02_Step_09: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_02_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_03_KiemTraLoiKhongDuSoDuVaDongPopup() {

		log.info("TC_03_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_03_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_03_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT3);

		log.info("TC_01_Step_07: Lay so du tai khoan chuyen");
		transferInVCB.sleep(driver, 2000);
		String beforeBalanceOfAccount1 = transferInVCB.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

		log.info("TC_03_Step_04: Nhap Tai Khoan Nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_03_Step_05: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_03_Step_06: Nhap So Tien Chuyen");
		transferInVCB.inputToDynamicInputBox(driver, beforeBalanceAmountOfAccount1 + 1000 + "", "Số tiền");

		log.info("TC_03_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_08: Kiem tra yeu cau nhap noi dung");
		verifyEquals(transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.NOT_ENOUGH_MONEY);

		log.info("TC_03_Step_09: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_03_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_04_KiemTraSoTienGiaoDichNhoHonHanMucToiThieuVaDongPopup() {

		log.info("TC_04_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_04_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_04_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_04_Step_04: Nhap Tai Khoan Nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_04_Step_05: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_04_Step_06: Nhap So Tien Chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InvalidInputData.INVALID_MIN_TRANSFER_AMOUNT, "Số tiền");

		log.info("TC_04_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_08: Kiem tra yeu cau nhap noi dung");
		verifyEquals(transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.ERROR_MESSAGE_WITH_MIN_LIMIT_TRANSFER_MONEY);

		log.info("TC_04_Step_09: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_04_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_05_KiemTraSoTienGiaoDichLonHonHanMucToiDaVaDongPopup() {

		log.info("TC_05_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_05_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_05_Step_03: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_05_Step_04: Nhap Tai Khoan Nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_05_Step_05: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_05_Step_06: Nhap So Tien Chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InvalidInputData.INVALID_MAX_TRANSFER_AMOUNT, "Số tiền");

		log.info("TC_05_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_08: Kiem tra yeu cau nhap noi dung");
		verifyEquals(transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.ERROR_MESSAGE_WITH_MAX_LIMIT_TRANSFER_MONEY);

		log.info("TC_05_Step_09: Click Đong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		log.info("TC_05_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
	}

	@Test
	public void TC_06_KiemTraButtonBackTaiManHinhXacThucGiaoDich() {
		log.info("TC_06_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_06_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_06_Step_06: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_06_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_06_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_06_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_06_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_06_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Xác nhận thông tin");

		log.info("TC_06_Step_10: Click Quay Lai");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền trong Vietcombank"));

		log.info("TC_06_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@Test
	public void TC_07_KiemTraButtonDongTaiPopupChonPhuongThucXacThuc() {
		log.info("TC_07_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_07_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_07_Step_06: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_07_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_07_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_07_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_07_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_Step_12: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_07_Step_13: Chon Dong");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_07_Step_14: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Xác nhận thông tin");

		log.info("TC_07_Step_15: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_07_Step_16: Click Quay Lai");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền tới ngân hàng khác"));
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền nhanh 24/7"));

	}

	@Parameters({ "phone" })
	@Test
	public void TC_08_KiemTraManHinhXacThucGiaoDichBangOTP(String phone) {
		log.info("TC_08_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_08_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_08_Step_03: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_08_Step_04: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_08_Step_05: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInVCB.VND_MONEY, "Số tiền");

		log.info("TC_08_Step_06: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_08_Step_07: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_08_Step_08: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_08_Step_09: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_08_Step_10: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_08_Step_11: Kiem tra xac thuc giao dich hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Xác thực giao dịch"));

		log.info("TC_08_Step_12: Kiem tra inbox message hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Quý khách vui lòng nhập mã OTP đã được gửi về số điện thoại " + phone.substring(0, 4) + "***" + phone.substring(7, 10)));

	}

	@Test
	public void TC_09_KiemTraBoTrongXacThucOTP() {

		log.info("TC_09_Step_01: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_09_Step_02: Kiem tra popup message hien thi");
		verifyEquals(transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.ERROR_MESSAGE_FOR_EMPTY_OTP);

		log.info("TC_09_Step_03: Chon Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

	}

	@Test
	public void TC_10_KiemXacThucOTPNhoHon6KyTu_SaiLan1() {

		log.info("TC_10_Step_02: Dien OTP nho hon 6 ky tu");
		transferInVCB.inputToDynamicOtp(driver, TransferMoneyInVCB_Data.InvalidInputData.OTP_WITH_LESS_THAN_6_CHARACTERS, "Tiếp tục");

		log.info("TC_10_Step_03: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_10_Step_04: Kiem tra popup message hien thi");
		verifyEquals(transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.ERROR_MESSAGE_FOR_OTP_LESS_THAN_6);

		log.info("TC_10_Step_05: Chon Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_11_KiemXacThucOTPLonHon6KyTu() {

		log.info("TC_11_Step_02: Dien OTP lon hon 6 ky tu");
		transferInVCB.inputToDynamicOtp(driver, TransferMoneyInVCB_Data.InvalidInputData.INVALID_MAX_TRANSFER_AMOUNT, "Tiếp tục");

		log.info("TC_11_Step_04: Kiem tra popup message hien thi");
		verifyEquals(transferInVCB.getTextInDynamicOtp(driver, "Tiếp tục"), TransferMoneyInVCB_Data.InvalidInputData.INVALID_MAX_TRANSFER_AMOUNT.subSequence(0, 6));
	}

	@Test
	public void TC_12_KiemXacNhapSaiOTP_Sailan2() {

		log.info("TC_12_Step_02: Nhap sai OTP");
		transferInVCB.inputToDynamicOtp(driver, TransferMoneyInVCB_Data.InvalidInputData.WRONG_OTP, "Tiếp tục");

		log.info("TC_12_Step_03: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_12_Step_04: Kiem tra popup message hien thi");
		verifyEquals(transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.ERROR_MESSAGE_FOR_WRONG_OTP);

		log.info("TC_12_Step_05: Chon Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

	}

	@Test
	public void TC_13_KiemXacNhapSaiOTPNhoHonNLan() {
		for (int i = 0; i < 2; i++) {
			log.info("TC_13_Step_01: Dien OTP lon hon 6 ky tu");
			transferInVCB.inputToDynamicOtp(driver, TransferMoneyInVCB_Data.InvalidInputData.WRONG_OTP, "Tiếp tục");

			log.info("TC_13_Step_02: Click Tiep tuc");
			transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

			log.info("TC_13_Step_03: Kiem tra popup message hien thi");
			verifyEquals(transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.ERROR_MESSAGE_FOR_WRONG_OTP);

			log.info("TC_13_Step_04: Chon Dong");
			transferInVCB.clickToDynamicButton(driver, "Đóng");
		}

		log.info("TC_13_Step_05: Dien OTP lon hon 6 ky tu");
		transferInVCB.inputToDynamicOtp(driver, TransferMoneyInVCB_Data.InvalidInputData.WRONG_OTP, "Tiếp tục");

		log.info("TC_13_Step_06: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13_Step_07: Kiem tra popup message hien thi");
		verifyEquals(transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.ERROR_MESSAGE_FOR_WRONG_OTP_N_TIMES);

		log.info("TC_13_Step_08: Chon Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

		transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền trong Vietcombank");

		log.info("TC_13_Step_10: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@Test
	public void TC_14_KiemTraManHinhNhapMatKhau() {
		log.info("TC_14_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_14_Step_02:Click tai khoan nguon");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_14_Step_06: Chon tai khoan chuyen");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_14_Step_08: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, "Nhập/chọn tài khoản nhận VND");

		log.info("TC_14_Step_09: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, "20000", "Số tiền");

		log.info("TC_14_Step_10: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_14_Step_11: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_14_Step_20: Chon Phuong thuc nhap");
		transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("TC_14_Step_21: Chon SMS OTP");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_14_Step_22: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_14_Step_11: Kiem tra xac thuc giao dich hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Xác thực giao dịch"));

		log.info("TC_14_Step_12: Kiem tra inbox message hien thi");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, "Vui lòng nhập mật khẩu đăng nhập ứng dụng của Quý khách để xác nhận giao dịch"));

	}

	@Test
	public void TC_15_KiemTraBoTrongMatKhau() {
		log.info("TC_15_Step_01: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_15_Step_02: Kiem tra popup message hien thi");
		verifyEquals(transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.ERROR_MESSAGE_FOR_EMPTY_PASSWORD);

		log.info("TC_15_Step_03: Chon Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

	}

	@Test
	public void TC_16_KiemTraNhapMatKhauSai() {

		log.info("TC_16_Step_23: Nhap Mat Khau sai");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, "wrongpass", "Tiếp tục");

		log.info("TC_16_Step_01: Click Tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_16_Step_02: Kiem tra popup message hien thi");
		verifyEquals(transferInVCB.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferMoneyInVCB_Data.Output.ERROR_MESSAGE_FOR_WRONG_PASSWORD);

		log.info("TC_16_Step_03: Chon Dong");
		transferInVCB.clickToDynamicButton(driver, "Đóng");

	}

	@Test
	public void TC_17_KiemTraNhapMatKhauDaiHon20KyTu() {

		log.info("TC_17_Step_23: Nhap Mat Khau dai hon 20 ky tu");
		transferInVCB.inputToDynamicPopupPasswordInput(driver, TransferMoneyInVCB_Data.InvalidInputData.MORE_THAN_21_CHARACTERS, "Tiếp tục");

		log.info("TC_17_Step_02: Kiem tra do dai mat khau");
		verifyEquals(transferInVCB.getTextInDynamicPasswordInput(driver, "com.VCB:id/pin").length(), 20);

		log.info("TC_17_Step_05: Chon Quay lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Quay lại");

		log.info("TC_17_Step_14: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Xác nhận thông tin");

		log.info("TC_17_Step_15: Click Quay Lai");
		transferInVCB.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");

	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
