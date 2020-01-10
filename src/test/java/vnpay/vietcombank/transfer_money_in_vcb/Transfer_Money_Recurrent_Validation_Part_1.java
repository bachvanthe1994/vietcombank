package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

public class Transfer_Money_Recurrent_Validation_Part_1 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TransferMoneyInVcbPageObject transferRecurrent;
	private HomePageObject homePage;

	TransferInVCBRecurrent info = new TransferInVCBRecurrent(Account_Data.Valid_Account.DEFAULT_ACCOUNT2, Account_Data.Valid_Account.ACCOUNT3, "1", "Ngày", "", "", "500000", "Người chuyển trả", "test", "SMS OTP");
	TransferInVCBRecurrent info1 = new TransferInVCBRecurrent(Account_Data.Valid_Account.EUR_ACCOUNT, Account_Data.Valid_Account.ACCOUNT3, "2", "Ngày", "", "", "10", "Người nhận trả", "test", "SMS OTP");
	
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
	}

	String defaultAccount;
	@Test
	public void TC_01_ChuyenTienDinhKy_KiemTraHienThiManHinh() {
		defaultAccount = homePage.getDynamicTextDetailByID(driver, "com.VCB:id/tvDefaultAcc");
		
		log.info("TC_01_01_Click Chuyen tien trong ngan hang");
		homePage.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_01_02_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		
		log.info("TC_01_03_Kiem tra title 'Chuyen tien trong Vietcombank' ");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền trong Vietcombank"));
		
		log.info("TC_01_04_Kiem tra icon Back' ");
		
		
		log.info("TC_01_05_Kiem tra combobox Hinh thuc chuyen tien");
		
		
		log.info("TC_01_06_Kiem tra label Thong tin nguoi chuyen ");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Thông tin người chuyển"));
		
		
		log.info("TC_01_07_Kiem tra combo tai khoan nguon");
		
		
		log.info("TC_01_08_Kiem tra so du kha dung tuong ung tai khoan nguon duoc chon");
		
		
		log.info("TC_01_09_Kiem tra label Thong tin nguoi huong");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Thông tin người hưởng"));
		
		log.info("TC_01_10_Kiem tra tan suat");
		
		
		log.info("TC_01_11_Kiem tra combo chu ky");
		
		
		log.info("TC_01_12_Kiem tra thoi gian bat dau, thoi gian ket thuc");
		
		
		log.info("TC_01_13_Kiem tra so lan giao dich");
		
		
		log.info("TC_01_14_Kiem tra Thong tin giao dich");
		
		
		log.info("TC_01_15_Kiem tra link Han muc");
		
		transferRecurrent.scrollToText(driver, "Thông tin giao dịch");
		
		log.info("TC_01_16_Kiem tra textbox so tien");
		verifyTrue(transferRecurrent.isDynamicTextInInputBoxDisPlayed(driver, "Số tiền"));
		
		log.info("TC_01_17_Kiem tra combo Phi giao dich");
		
		
		log.info("TC_01_18_Kiem tra textbox Noi dung");
		
		
		log.info("TC_01_19_Kiem tra button Tiep tuc");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Tiếp tục"));
	}
	
	@Test
	public void TC_02_ChuyenTienDinhKy_KiemTraHoatDongNutBack() {
		log.info("TC_02_01: Click  nut Back");
		transferRecurrent.clickToDynamicBackIcon(driver, "Chuyển tiền trong Vietcombank");
		
		log.info("TC_02_02: Kiem tra hien thi man hinh Home");
	}

	@Test
	public void TC_03_ChuyenTienDinhKy_ComboHinhThucChuyenTien_KiemTraHienThiMacDinh() {
		log.info("TC_03_01_Click Chuyen tien trong ngan hang");
		homePage.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_03_03_Kiem tra Hien thi mac dinh");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền ngay"));
	}
	
	@Test
	public void TC_04_ChuyenTienDinhKy_ComboHinhThucChuyenTien_KiemTraDanhSachHinhThuc() {
		log.info("TC_04_01_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");

		log.info("TC_04_02_Lay list Hinh thuc chuyen tien");
		List<String> actualList = transferRecurrent.getListOfSuggestedMoney(driver, "com.VCB:id/tvContent");
		
		
		List<String> expectList = Arrays.asList("Chuyển tiền ngay", "Chuyển tiền định kỳ", "Chuyển tiền ngày tương lai");

		log.info("TC_04_03_Kiem tra list Hinh thuc chuyen tien");
		verifyEquals(actualList, expectList);
	}
	
	@Test
	public void TC_05_ChuyenTienDinhKy_ComboHinhThucChuyenTien_KiemTraChonHinhThucChuyenTienKhac() {
		log.info("TC_05_01_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		
		log.info("TC_05_02_Kiem tra Hinh thuc chuyen tien khac duoc chon");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền định kỳ"));
	}
	
	@Test
	public void TC_06_ChuyenTienDinhKy_ComboTaiKhoanNguon_KiemTraHienThiMacDinh() {
		log.info("TC_06_01_Kiem tra hien thi tai khoan mac dinh");
		String actualDefaultAccount =  transferRecurrent.getTextDynamicDefaultSourceAccount(driver, "Tài khoản nguồn");
		verifyEquals(actualDefaultAccount, defaultAccount);
	}
	
	@Test
	public void TC_07_ChuyenTienDinhKy_ComboTaiKhoanNguon_ChonMotTaiKhoanVNDKhac() {
		log.info("TC_07_01_Chon mot tai khoan VND khac");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		
		log.info("TC_07_02_Lay so du tuong ung voi tai khoan nguon");
		String expectAvailableBalance = transferRecurrent.getMoneyByAccount(driver, info.sourceAccount);
		
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		
		log.info("TC_07_03_So tai khoan chon duoc fill vao textbox");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, info.sourceAccount));
		
		log.info("TC_07_04_Hien thi so du kha dung, loai tien tuong ung voi tai khoan da chon");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Số dư khả dụng"));
		String availableBalance =  transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		verifyEquals(availableBalance, expectAvailableBalance);
		
		log.info("TC_07_05_So du hien thi dung dinh dang");
		verifyTrue(transferRecurrent.checkFormatMoney(availableBalance, TransferInVCBRecurrent.Currency.VND));
		
	}
	
	@Test
	public void TC_08_ChuyenTienDinhKy_ComboTaiKhoanNguon_ChonMotTaiKhoanNgoaiTeKhac() {
		log.info("TC_08_01_Chon mot tai khoan Ngoai te khac");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		
		log.info("TC_08_02_Lay so du tuong ung voi tai khoan nguon");
		String expectAvailableBalance = transferRecurrent.getMoneyByAccount(driver, info1.sourceAccount);
		
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
		
		log.info("TC_08_03_So tai khoan chon duoc fill vao textbox");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, info1.sourceAccount));
		
		log.info("TC_08_04_Hien thi so du kha dung, loai tien tuong ung voi tai khoan da chon");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Số dư khả dụng"));
		String availableBalance =  transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		verifyEquals(availableBalance, expectAvailableBalance);
		
		log.info("TC_08_05_So du hien thi dung dinh dang");
		verifyTrue(transferRecurrent.checkFormatMoney(availableBalance, TransferInVCBRecurrent.Currency.CURRENCY));
		
	}
	
	@Test
	public void TC_09_ChuyenTienDinhKy_TaiKhoanDich_KiemTraHienThiMacDinh() {
		log.info("TC_09_01_Kiem tra hien thi mac dinh");
		String actualDestinationAccount = transferRecurrent.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "1");
		verifyEquals(actualDestinationAccount, "Nhập/chọn tài khoản nhận VND");
		
	}
	
	@Test
	public void TC_10_ChuyenTienDinhKy_TaiKhoanDich_KiemTraMaxLength() {
		log.info("TC_10_01_Nhap gia tri tai khoan dich");
		transferRecurrent.inputToDynamicInputBox(driver, "12345678999999", "Nhập/chọn tài khoản nhận VND");
		
		log.info("TC_10_01_Kiem tra do dai tai khoan dich");
		String actualDestinationAccount = transferRecurrent.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "1");
		verifyEquals(actualDestinationAccount.length(), 13);
	}
	
	@Test
	public void TC_11_ChuyenTienDinhKy_TaiKhoanDich_KiemTraTaiKhoanNhoHon10KyTu() {
		log.info("TC_11_01_Nhap gia tri tai khoan dich");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, "123456789", "Thông tin người hưởng", "1");
		
		log.info("TC_11_02_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		
		log.info("TC_11_03_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info.frequencyNumber);

		log.info("TC_11_04_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info.money, "Số tiền");

		log.info("TC_11_05_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.fee);

		log.info("TC_11_06_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBox(driver, info.note, "Nội dung");

		log.info("TC_11_07_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_11_08_Kiem tra thong bao tai khoan dich khong du 10 ky tu vui long nhap lai");
		
	}
	
//	@Test
	public void TC_12_ChuyenTienDinhKy_TaiKhoanDich_KiemTraKyTuNhap() {
		log.info("TC_12_01_Nhap gia tri tai khoan dich");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, "123456789", "Thông tin người hưởng", "1");
	
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
