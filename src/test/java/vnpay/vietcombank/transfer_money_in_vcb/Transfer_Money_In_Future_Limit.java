package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import commons.WebPageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.ServiceLimitInfo;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import pageObjects.WebBackendSetupPageObject;
import pageObjects.WebLogInPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data.InputText_MoneyRecurrent;

public class Transfer_Money_In_Future_Limit extends Base {
	AppiumDriver<MobileElement> driver;
	WebDriver driver1;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyInVcbPageObject transferInVCB;
	private WebBackendSetupPageObject setupBE;

	String[] exchangeRateUSD;
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String tommorrowDate = getForwardDate(1);
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "1000000", "10000000");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo backend ");
		driver1 = openMultiBrowser("chrome", "83.0.4103.14", "http://10.22.7.91:2021/HistorySMS/Index?f=5&c=107");
		setupBE = WebPageFactoryManager.getWebBackendSetupPageObject(driver);
		setupBE.Login_Web_Backend(driver1,username, passWeb);
		setupBE.setup_Assign_Services_Limit(driver1,InputText_MoneyRecurrent.BE_TRANSFER_RECURRENT_TEXT,inputInfo);
		
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		log.info("Before class_Step_10: Scroll den trang thai lenh chuyen tien");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, "Trạng thái lệnh chuyển tiền");
		transferInVCB = PageFactoryManager.getTransferMoneyInVcbPageObject(driver);

	}

	// Set up BE han muc nho nhat 10.000vnd
	// Muc cao nhat 100.000.000vnd
	// Han Muc cao nhan trong ngay là 400.000.000vnd
	@Test
	public void TC_01_ChuyenTienTuongLaiThapHonHanMucToiThieu() {

		log.info("TC_01_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_01_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");

		log.info("TC_01_Step_03: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_01_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_05: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_06: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT1, "Nhập/ chọn tài khoản nhận VND");

		log.info("TC_01_Step_07: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_01_Step_08: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.LOWER_TRANSFER_AMOUNT, "Số tiền");

		log.info("TC_01_Step_09: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_01_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_11: Verify hien thi man hinh thong bao loi");
		transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_lOWER_MIN_LIMIT);

		log.info("TC_01_Step_12: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@Test
	public void TC_02_ChuyenTienTuongLaiCaoHonHanMucToiDa() {

		log.info("TC_02_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_02_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");

		log.info("TC_02_Step_03: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_02_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_02_Step_05: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_02_Step_06: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, "Nhập/ chọn tài khoản nhận VND");

		log.info("TC_02_Step_07: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_02_Step_08: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.HIGHER_TRANSFER_AMOUNT, "Số tiền");

		log.info("TC_02_Step_09: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_02_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_11: Verify hien thi man hinh thong bao loi");
		transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_MAX_LIMIT);

		log.info("TC_02_Step_12: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Test
	public void TC_03_ChuyenTienTuongLaiVuotQuaHanMucTrongNgay() {

		log.info("TC_03_Step_01 : Click  nut Back");
		transferInVCB.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_03_Step_02: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");
		int round = Integer.parseInt(TransferMoneyInVCB_Data.InputDataInFutureForOTP.TOTAL_LIMIT_AMOUNT) / Integer.parseInt(TransferMoneyInVCB_Data.InputDataInFutureForOTP.MAX_TRANFER_AMOUNT);
		for (int i = 0; i < round; i++) {
			log.info("TC_03_Step_03: Chon chuyen tien ngay gia tri hien tai");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");

			log.info("TC_03_Step_04: Chon chuyen tien ngay gia tri hien tai");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

			log.info("TC_03_Step_05:Click tai khoan nguon");
			transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

			log.info("TC_03_Step_06: Chon tai khoan dich");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

			log.info("TC_03_Step_07: Nhap tai khoan nhan");
			transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, "Nhập/ chọn tài khoản nhận VND");

			log.info("TC_03_Step_08: Kiem tra Ngay hieu luc mac dinh");
			verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

			log.info("TC_03_Step_09: Nhap so tien chuyen");
			transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.MAX_TRANFER_AMOUNT, "Số tiền");

			log.info("TC_03_Step_10: Nhap noi dung");
			transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

			log.info("TC_03_Step_11: Click tiep tuc");
			transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

			log.info("TC_03_Step_12: Click tiep tuc");
			transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.INFO_VALIDATION);

			log.info("TC_03_Step_13: Chon Phuong thuc nhap");
			transferInVCB.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

			log.info("TC_03_Step_14: Chon SMS OTP");
			transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

			log.info("TC_03_Step_15: Click Tiep tuc");
			transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

			log.info("TC_03_Step_16: Nhap OTP");
			transferInVCB.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

			log.info("TC_03_Step_17: Click tiep tuc");
			transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

			log.info("TC_03_Step_18: Kiem  tra giao dich thanh cong");
			verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.TRANSFER_SUCESS_MESSAGE));

			log.info("TC_03_Step_19: Click thuc hien giao dich moi");
			transferInVCB.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		}

		log.info("TC_03_Step_20: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");

		log.info("TC_03_Step_21: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_03_Step_22:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_03_Step_23: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_03_Step_24: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, "Nhập/ chọn tài khoản nhận VND");

		log.info("TC_03_Step_25: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_03_Step_26: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.MAX_TRANFER_AMOUNT, "Số tiền");

		log.info("TC_03_Step_27: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_03_Step_28: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_29: Verify hien thi man hinh thong bao loi");
		transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_LIMIT_DAY);

		log.info("TC_03_Step_30: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	// Truoc khi chay case nay can set Up Nhom dich vu vs han muc la 99.999.999 VND
	public void TC_04_ChuyenTienTuongLaiVuotQuaNhomDichVu() {

		log.info("TC_04_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_04_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");

		log.info("TC_04_Step_03: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_04_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_04_Step_05: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_04_Step_06: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, "Nhập/ chọn tài khoản nhận VND");

		log.info("TC_04_Step_07: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_04_Step_08: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.MAX_TRANFER_AMOUNT, "Số tiền");

		log.info("TC_04_Step_09: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_04_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_11: Verify hien thi man hinh thong bao loi");
		transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_LIMIT_GROUP_SERVICES);

		log.info("TC_04_Step_12: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	// Truoc khi chay case nay can set Up Goi dich vu vs han muc la 99.999.999 VND
	public void TC_05_ChuyenTienTuongLaiVuotQuaGoiDichVu() {

		log.info("TC_05_Step_01: Click Chuyen tien trong VCB");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền trong VCB");

		log.info("TC_05_Step_02: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày giá trị hiện tại");

		log.info("TC_05_Step_03: Chon chuyen tien ngay gia tri hien tai");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngày tương lai");

		log.info("TC_05_Step_04:Click tai khoan nguon");
		transferInVCB.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_05_Step_05: Chon tai khoan dich");
		transferInVCB.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_05_Step_06: Nhap tai khoan nhan");
		transferInVCB.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT2, "Nhập/ chọn tài khoản nhận VND");

		log.info("TC_05_Step_07: Kiem tra Ngay hieu luc mac dinh");
		verifyTrue(transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, tommorrowDate));

		log.info("TC_05_Step_08: Nhap so tien chuyen");
		transferInVCB.inputToDynamicInputBox(driver, TransferMoneyInVCB_Data.InputDataInFutureForOTP.MAX_TRANFER_AMOUNT, "Số tiền");

		log.info("TC_05_Step_09: Nhap noi dung");
		transferInVCB.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_05_Step_10: Click tiep tuc");
		transferInVCB.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_11: Verify hien thi man hinh thong bao loi");
		transferInVCB.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.MESSEGE_ERROR_HIGHER_LIMIT_PACKAGE_SERVICES);

		log.info("TC_05_Step_12: Click btn Dong");
		transferInVCB.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
