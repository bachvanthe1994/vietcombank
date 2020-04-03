package vnpay.vietcombank.transfer_money_charity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.Keys;
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
import vietcombank_test_data.TransferMoneyCharity_Data;

public class Validation_Amount_Charity extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyCharityPageObject transferMoneyCharity;

	TransferCharity info = new TransferCharity(Account_Data.Valid_Account.DEFAULT_ACCOUNT3, TransferMoneyCharity_Data.ORGANIZATION, "1000000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");
	TransferCharity info1 = new TransferCharity(Account_Data.Valid_Account.EUR_ACCOUNT, TransferMoneyCharity_Data.ORGANIZATION, "10", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");

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
	}

	@Test
	public void TC_01_KiemTraHienThiMacDinh() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);

		log.info("TC_01_1_Click Chuyen tien tu thien");
		homePage.scrollIDownOneTime(driver);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		String actualAmountMoney = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		log.info("TC_01_2_Kiem tra gia tri trong o So tien ung ho");
		verifyEquals(actualAmountMoney, "Số tiền ủng hộ");
	}

	@Test
	public void TC_02_KiemTraNhapSoTienBang0() {
		log.info("TC_02_1_Nhap so 0 vao o So tien ung ho");
		List<Keys> listKey = Arrays.asList(Keys.NUMPAD0, Keys.NUMPAD0, Keys.NUMPAD0, Keys.NUMPAD0);
		transferMoneyCharity.pressKeyCodeIntoDynamicInputBoxByHeader(driver, listKey, "Thông tin giao dịch", "1");

		String actualAmountMoney = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		log.info("TC_02_2_Kiem tra gia tri trong o So tien ung ho");
		verifyEquals(actualAmountMoney, "Số tiền ủng hộ");
	}

	@Test
	public void TC_03_KiemTraLoaiKyTuNhapSoTien() {
		String actualAmountMoney;
		log.info("TC_03_1_Chon tai khoan nguon VND");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_03_2_Nhap ki tu so, chu, ki tu dac biet, dau cham vao o So tien ung ho");

		log.info("TC_03_2_1_Nhap ki tu so");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "123", "Thông tin giao dịch", "1");

		actualAmountMoney = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		log.info("TC_03_2_2_Kiem tra gia tri trong o So tien ung ho");
		verifyEquals(actualAmountMoney, "123");

		log.info("TC_03_3_Chon tai khoan nguon Ngoai te");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

		log.info("TC_03_4_Nhap ki tu so, dau cham");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "5.5", "Thông tin giao dịch", "1");

		actualAmountMoney = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		log.info("TC_03_4_1_Kiem tra gia tri trong o So tien ung ho");
		verifyEquals(actualAmountMoney, "5.5");
	}

	@Test
	public void TC_04_KiemTraKiemTraGioiHanNhapSoTien() {
		String actualAmountMoney;
		log.info("TC_04_1_Chon tai khoan nguon VND");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_04_2_Nhap ki tu so, chu, ki tu dac biet, dau cham vao o So tien ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "1234567899", "Thông tin giao dịch", "1");

		actualAmountMoney = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		verifyEquals(actualAmountMoney.replaceAll("\\D+", "").length(), 10);

		log.info("TC_04_3_Nhap ki tu so, chu, ki tu dac biet, dau cham vao o So tien ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "1234567899112", "Thông tin giao dịch", "1");

		actualAmountMoney = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		verifyEquals(actualAmountMoney, "Số tiền ủng hộ");

		log.info("TC_04_4_Chon tai khoan nguon Ngoai te");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

		log.info("TC_04_5_Nhap ki tu so, chu, ki tu dac biet, dau cham vao o So tien ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "111111111.11", "Thông tin giao dịch", "1");

		actualAmountMoney = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		verifyEquals(actualAmountMoney.replaceAll("\\D+", "").length(), 11);

		log.info("TC_04_6_Nhap ki tu so, chu, ki tu dac biet, dau cham vao o So tien ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "1111111111111.11", "Thông tin giao dịch", "1");

		actualAmountMoney = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		verifyEquals(actualAmountMoney, "Số tiền ủng hộ");
	}

	@Test
	public void TC_05_KiemTraDinhDangSoTienNhap() {
		String actualAmountMoney;
		log.info("TC_05_1_Chon tai khoan nguon VND");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_05_2_Nhap ki tu so, chu, ki tu dac biet, dau cham vao o So tien ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "5000000", "Thông tin giao dịch", "1");

		actualAmountMoney = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		verifyTrue(actualAmountMoney.contains(","));
	}

	@Test
	public void TC_06_KiemTraGoiYSoTienVND() {
		List<String> listActualAmountMoney = new ArrayList<String>();
		List<String> listExpectAmountMoney = new ArrayList<String>();
		log.info("TC_06_1_Chon tai khoan nguon VND");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_06_2_Nhap so tien");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "5000", "Thông tin giao dịch", "1");

		listActualAmountMoney = transferMoneyCharity.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvAmount");

		listExpectAmountMoney.add("50,000 VND");
		listExpectAmountMoney.add("500,000 VND");
		listExpectAmountMoney.add("5,000,000 VND");

		log.info("TC_06_3_Kiem tra so tien goi y");
		verifyEquals(listActualAmountMoney, listExpectAmountMoney);
	}

	@Test
	public void TC_07_KiemTraGoiYSoTienNgoaiTe() {
		List<String> listActualAmountMoney = new ArrayList<String>();
		List<String> listExpectAmountMoney = new ArrayList<String>();
		log.info("TC_07_1_Chon tai khoan nguon Ngoai te");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

		log.info("TC_07_2_Nhap so tien");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "10", "Thông tin giao dịch", "1");

		listActualAmountMoney = transferMoneyCharity.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvAmount");

		listExpectAmountMoney.add("100 EUR ~ 239,977,600 VND");
		listExpectAmountMoney.add("1,000 EUR ~ 2,399,776,000 VND");

		log.info("TC_07_3_Kiem tra so tien goi y");
		verifyEquals(listActualAmountMoney, listExpectAmountMoney);
	}

	@Test
	public void TC_08_KiemTraHienThiGoiYNhanh_FocusRaNgoaiSoTien() {
		String actualAmountMoney;

		log.info("TC_08_1_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_08_2_Nhap so tien");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "50000", "Thông tin giao dịch", "1");

		log.info("TC_08_3_Focus ra ngoai o nhap So tien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_08_4_Kiem tra so tien trong o nhap So tien");
		actualAmountMoney = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		verifyEquals(actualAmountMoney, "50,000");

	}

	@Test
	public void TC_09_KiemTraHienThiGoiYNhanh_FocusLaiVaoOSoTien() {
		List<String> listActualAmountMoney = new ArrayList<String>();
		List<String> listExpectAmountMoney = new ArrayList<String>();

		log.info("TC_09_01_Focus lai vao o nhap So tien");
		transferMoneyCharity.clickToDynamicInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		transferMoneyCharity.scrollDownToText(driver, "50,000,000 VND");

		listActualAmountMoney = transferMoneyCharity.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvAmount");

		listExpectAmountMoney.add("500,000 VND");
		listExpectAmountMoney.add("5,000,000 VND");
		listExpectAmountMoney.add("50,000,000 VND");

		log.info("TC_09_02_Kiem tra so tien goi y");
		verifyEquals(listActualAmountMoney, listExpectAmountMoney);

	}

	@Test
	public void TC_10_KiemTraHienThiGoiYNhanh_ChonMotGiaTriGoiY() {
		String actualAmountMoney;
		log.info("TC_10_01_Nhap so tien");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "50000", "Thông tin giao dịch", "1");

		log.info("TC_10_02_Chon 1 gia tri trong danh sach goi y");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, "500,000 VND");

		log.info("TC_10_03_Kiem tra so tien trong o nhap So tien");
		actualAmountMoney = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		verifyEquals(actualAmountMoney, "500,000");
	}

	@Test
	public void TC_11_KiemTraLoaiTien() {
		String actualCurrency = "";
		log.info("TC_11_01_Chon tai khoan nguon Ngoai te");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);

		log.info("TC_11_02_Kiem tra loai tien");
		actualCurrency = transferMoneyCharity.getTextCurrencyCharity();
		verifyEquals(actualCurrency, "EUR");

		log.info("TC_11_03_Chon tai khoan nguon VND");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_11_04_Kiem tra loai tien");
		actualCurrency = transferMoneyCharity.getTextCurrencyCharity();
		verifyEquals(actualCurrency, "VND");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
