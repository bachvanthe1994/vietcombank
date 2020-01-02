package vnpay.vietcombank.transfer_money_quick_247;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.Keys;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SetupContactPageObject;
import pageObjects.TransferMoneyObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SetupContact_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Validation_QuickMoneyTransfer247_3 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private SetupContactPageObject setupContact;
	private TransferMoneyObject transferMoney;
	private String amountStartString;
	List<String> listActualAmountMoney;
	List<String> listExpectAmountMoney;
	private String amountExpect;

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
	}

	// @Test
	public void TC_00_TaoDanhBaThuHuong() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_00_Step_: Click menu header");
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");

		log.info("TC_00Step_: Click cai dat");
		setupContact = PageFactoryManager.getSetupContactPageObject(driver);
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("TC_00_Step_: Click quan ly danh ba");
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, "Quản lý danh bạ");

		log.info("TC_00_Step_: Click Danh bạ người hưởng");
		setupContact.clickToDynamicButtonLinkOrLinkText(driver, "Danh bạ người hưởng");

		log.info("TC_00_Step_: delete danh sach co tu truoc");
		setupContact.deleteContactReceiver();

		log.info("TC_00_Step_: Add contact 1");
		setupContact.addContactReceiver(SetupContact_Data.UI.TYPE_TRANFER[3], TransferMoneyQuick_Data.TransferQuick.BANK[0], SetupContact_Data.UI.NAME_CARD[0], SetupContact_Data.UI.ACCOUNT[0]);

		log.info("TC_00_Step_: Add contact 2");
		setupContact.addContactReceiver(SetupContact_Data.UI.TYPE_TRANFER[3], TransferMoneyQuick_Data.TransferQuick.BANK[0], SetupContact_Data.UI.NAME_CARD[1], SetupContact_Data.UI.ACCOUNT[1]);

		log.info("TC_00_Step_: back lai man hinh danh ba");
		setupContact.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_00_Step_: back lai man hinh home");
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_01_KiemTraLabelSoTienVND() {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Kiem tra label so tien");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Số tiền"));
		
		log.info("TC_01_Kiem tra label loai tien");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "VND"));
	}

	// @Test
	public void TC_02_NhapNhoHon10KyTuVaKiemTraDauPhayVND() {
		log.info("TC_02_Input so tien co 9 ky tu");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER, "Thông tin giao dịch", "1");

		log.info("TC_02_text mac dinh 'So tien' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Số tiền"));

		log.info("TC_02_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER;
		amountExpect = amountExpect.substring(0, 3) + "," + amountExpect.substring(3, 6) + "," + amountExpect.substring(6, amountExpect.length());

		log.info("TC_02_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpect);

	}

	// @Test
	public void TC_03_Nhap10KyTuVaKiemTraDauPhayVND() {
		log.info("TC_02_Input so tien co 9 ky tu");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_TEN_NUMBER, "Thông tin giao dịch", "1");

		log.info("TC_02_text mac dinh 'So tien' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Số tiền"));

		log.info("TC_02_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_TEN_NUMBER;
		amountExpect = amountExpect.substring(0, 1) + "," + amountExpect.substring(1, 4) + "," + amountExpect.substring(4, 7) + "," + amountExpect.substring(7, amountExpect.length());
		log.info("TC_02_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpect);
	}

	//@Test
	public void TC_04_NhapLonHon10KyTuVaKiemTraDauPhayVND() {
		log.info("TC_02_Input so tien co 9 ky tu");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_ELEVEN_NUMBER, "Thông tin giao dịch", "1");

		log.info("TC_02_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		log.info("TC_02_Khong cho phep nhap hon 10 ky tu, truong so tien trong");
		verifyEquals(amountStartString, "Số tiền");

	}

	//@Test
	public void TC_05_NhapKyTuChuVaKiemTraDauPhayVND() {
		log.info("TC_02_Input so tien co 9 ky tu");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_TEXT_INVALID, "Thông tin giao dịch", "1");

		log.info("TC_02_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_TEXT_INVALID.replace("abc@", "");
		amountExpect = amountExpect.substring(0, 2) + "," + amountExpect.substring(2, amountExpect.length());
		log.info("TC_02_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpect);

	}

	//@Test
	public void TC_06_KiemTraHienThiGoiYNhanhVND() {
		listActualAmountMoney = new ArrayList<String>();
		listExpectAmountMoney = new ArrayList<String>();

		log.info("TC_06_Chon tai khoan nguon VND");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[0]);

		log.info("TC_06_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER, "Thông tin giao dịch", "1");

		log.info("TC_06_Lay danh sach goi y");
		listActualAmountMoney = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvAmount");

		log.info("TC_06_Danh sach goi y hien thi tren man hinh");
		listExpectAmountMoney.add("50,000 VND");
		listExpectAmountMoney.add("500,000 VND");
		listExpectAmountMoney.add("5,000,000 VND");

		log.info("TC_06_Kiem tra so tien goi y");
		verifyEquals(listActualAmountMoney, listExpectAmountMoney);
	}

	//@Test
	public void TC_07_KiemTraKhongHienThiGoiYNhanhKhiFocusRaNgoaiVND() {
		log.info("TC_07_Click text thong tin giao dich focus ra ngoai");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_07_Kiem tra gia tri goi y khong hien thi tren man hinh");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "50,000 VND"));
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "500,000 VND"));
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "5,000,000 VND"));

	}
	
	//@Test ----Loi app bao dev
	public void TC_08_KiemTraHienThiGoiYNhanhTiepTucFocusVaoSoTienVND() {
		log.info("TC_07_Click vao so tien vua nhap tren");
		transferMoney.clickToDynamicInputBoxByHeader(driver, "Thông tin giao dịch", "1");
		
		log.info("TC_06_Lay danh sach goi y");
		listActualAmountMoney = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvAmount");

		log.info("TC_06_Danh sach goi y hien thi tren man hinh");
		listExpectAmountMoney.add("50,000 VND");
		listExpectAmountMoney.add("500,000 VND");
		listExpectAmountMoney.add("5,000,000 VND");

		log.info("TC_06_Kiem tra so tien goi y");
		verifyEquals(listActualAmountMoney, listExpectAmountMoney);

	}
	
	//@Test ----Loi app bao dev
	public void TC_09_ChonMotGoiYVaDongPopupVND() {
		log.info("TC_09_Click vao so tien goi y");
		transferMoney.clickToDynamicSuggestedMoney(driver,1 ,"com.VCB:id/tvAmount");
		
		log.info("TC_09_Lay danh sach goi y");
		listActualAmountMoney = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvAmount");

		log.info("TC_09_Kiem tra gia tri goi y khong hien thi tren man hinh");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "5,000,000 VND"));


	}
	
	@Test
	public void TC_10_KiemTraLabelSoTienUSD() {
		log.info("TC_06_Chon tai khoan nguon VND");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[2]);

		log.info("TC_01_Kiem tra label so tien");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Số tiền"));

		log.info("TC_01_Kiem tra label ty gia quy doi tham khao");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Tỷ giá quy đổi tham khảo"));
	}
	
	@Test
	public void TC_11_KiemTraLabelLoaiTienUSD() {
		log.info("TC_01_Kiem tra label loai tien");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "USD"));
	}
	//@Test -------Loi khong ngan cach dau cham
	public void TC_12_NhapNhoHon10KyTuVaKiemTraDauChamUSD() {
		log.info("TC_02_Input so tien co 9 ky tu");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER, "Thông tin giao dịch", "1");

		log.info("TC_02_text mac dinh 'So tien' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Số tiền"));

		log.info("TC_02_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER;
		amountExpect = amountExpect.substring(0, 1) + "," + amountExpect.substring(1, 4) + "," +   amountExpect.substring(4, 7) + "." +amountExpect.substring(7, amountExpect.length());

		log.info("TC_02_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpect);

	}
	
	//@Test -------Loi khong ngan cach dau cham
	public void TC_13_Nhap10KyTuVaKiemTraDauChamUSD() {
		log.info("TC_02_Input so tien co 9 ky tu");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_TEN_NUMBER, "Thông tin giao dịch", "1");

		log.info("TC_02_text mac dinh 'So tien' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Số tiền"));

		log.info("TC_02_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_TEN_NUMBER;
		amountExpect = amountExpect.substring(0, 2) + "," + amountExpect.substring(2, 5) + "," + amountExpect.substring(5, 8) + "," + amountExpect.substring(8, amountExpect.length());
		log.info("TC_02_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpect);
	}
	
	//@Test -------Confirm lai
	public void TC_14_KiemTraDinhDangHienThiUSD() {
		log.info("TC_02_Input so tien co 9 ky tu");
	}
	
	@Test
	public void TC_15_KiemTraDinhDangHienThiUSD() {
		
		listActualAmountMoney = new ArrayList<String>();
		listExpectAmountMoney = new ArrayList<String>();
		
		log.info("TC_15_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER, "Thông tin giao dịch", "1");

		listActualAmountMoney = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvAmount");
		
		log.info("TC_15_List goi y hien thi tren man hinh");
		listExpectAmountMoney.add("50,000 USD ~ 1,500,000,000 VND");
		listExpectAmountMoney.add("500,000 USD ~ 15,000,000,000 VND");

		log.info("TC_15_Kiem tra so tien goi y");
		verifyEquals(listActualAmountMoney, listExpectAmountMoney);
	}
	}


