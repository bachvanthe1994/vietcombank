package vnpay.vietcombank.transfer_money_quick_247;

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
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SetupContactPageObject;
import pageObjects.TransferMoneyObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SetupContact_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Validation_QuickMoneyTransfer247_2 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private SetupContactPageObject setupContact;
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
	public void TC_19_VerifyDefaultDanhBaThuHuong() {
		log.info("TC_19_Step_Scoll den man hinh chuyen tien nhanh");
		transferMoney.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");

		log.info("TC_19_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_19_click danh ba nguoi thu huong");
		transferMoney.clickToDynamicBottomMenu(driver, "com.VCB:id/ivContent1");

		log.info("TC_19_verify tieu de danh ba nguoi huong");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Danh bạ người hưởng"));

		log.info("TC_19_verify button dong");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Đóng"));

		log.info("TC_19_verify tim kiem");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Tìm kiếm"));

		log.info("TC_19_Lay danh sach gia tri danh ba nguoi huong");
		listActual = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvName");

		log.info("TC_19_danh sach gia tri bank actual");
		listExpect = Arrays.asList(SetupContact_Data.UI.NAME_CARD);

		log.info("TC_19_Verify gia tri tim kiem");
		verifyEquals(listActual, listExpect);
	}

	@Test
	public void TC_20_TimKiemDanhBaTenThuHuong() {
		log.info("TC_20_Tim kiem theo so tai khoan thu huong");
		transferMoney.inputToDynamicInputBoxSearch(driver, SetupContact_Data.UI.NAME_CARD[0], "Danh bạ người hưởng");

		log.info("TC_20_Lay danh sach gia tri danh ba nguoi huong");
		listActual = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvName");

		log.info("TC_20_danh sach gia tri bank actual");
		listExpect = Arrays.asList(SetupContact_Data.UI.NAME_CARD[0]);

		log.info("TC_20_Verify gia tri tim kiem");
		verifyEquals(listActual, listExpect);
	}

	@Test
	public void TC_21_TimKiemDanhBaSoTaiKhoanThuHuong() {
		log.info("TC_21_Tim kiem theo so tai khoan thu huong");
		transferMoney.inputToDynamicInputBoxSearch(driver, SetupContact_Data.UI.ACCOUNT[0], "Danh bạ người hưởng");

		log.info("TC_21_Lay danh sach gia tri danh ba nguoi huong");
		listActual = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvNo");

		log.info("TC_21_danh sach gia tri bank actual");
		listExpect = Arrays.asList(SetupContact_Data.UI.ACCOUNT[0]);

		log.info("TC_21_Verify gia tri tim kiem");
		verifyEquals(listActual, listExpect);
	}

	@Test
	public void TC_22_TimKiemDanhBaThuHuongKhongTonTai() {
		log.info("TC_22_Tim kiem theo ten nguoi thu huong khong ton tai");
		transferMoney.inputToDynamicInputBoxSearch(driver, SetupContact_Data.UI.NAME_INVALID, "Danh bạ người hưởng");

		log.info("TC_22_verify so tai khoan trong contact");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, TransferMoneyQuick_Data.TransferQuick.NAME_INVALID));

	}

	@Test
	public void TC_23_TimKiemChinhXacMotDanhBaThuHuong() {
		log.info("TC_23_Tim kiem ten nguoi thu huong");
		transferMoney.inputToDynamicInputBoxSearch(driver, SetupContact_Data.UI.NAME_CARD[0], "Danh bạ người hưởng");

		log.info("TC_23_chon 1 ten nguoi thu huong");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, SetupContact_Data.UI.NAME_CARD[0]);

		log.info("TC_23_check tai khoan vua chon ngoai man hinh khoi tao");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, SetupContact_Data.UI.ACCOUNT[0]));

		log.info("TC_23_check ngan hang thu huong");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.BANK[0]));
	}

	@Test
	public void TC_24_NganHangHuongBoTrong() {
		log.info("TC_24_Step_button back");
		transferMoney.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_24_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_24_Step_Select Chuyen tien nhanh qua tai khoan");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.OPTION_TRANSFER[0]);

		log.info("TC_24_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO, "Thông tin người hưởng", "1");

		log.info("TC_24_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Số tiền");

		log.info("TC_24_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_24_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_24_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_24_Step_verify message khi khong chon ngan hang huong ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.BANK_BLANK));

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_25_ValidationManHinhTimKiemNganHang() {
		log.info("TC_25_click danh sach ngan hang huong");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");

		log.info("TC_25_verify title ngan hang huong");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Ngân hàng hưởng"));

		log.info("TC_25_verify tim kiem");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Tìm kiếm"));
	}

	@Test
	public void TC_26_ValidationTimKiemNganHang() {
		log.info("TC_26_Tim kiem ngan hang theo ten ngan hang, nhap ten ngan hang");
		transferMoney.inputToDynamicInputBoxSearchBank(driver, TransferMoneyQuick_Data.TransferQuick.BANK[2], "Ngân hàng hưởng");

		log.info("TC_26_Lay danh sach gia tri bank");
		listActual = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvContent");

		log.info("TC_26_danh sach gia tri bank actual");
		listExpect = Arrays.asList(TransferMoneyQuick_Data.TransferQuick.BANK[2]);

		log.info("TC_26_Verify gia tri tim kiem");
		verifyEquals(listActual, listExpect);
	}

	@Test
	public void TC_27_NganHangHuongKhongTonTai() {
		log.info("TC_27_Tim kiem ngan hang theo ten ngan hang, nhap ten ngan hang");
		transferMoney.inputToDynamicInputBoxSearchBank(driver, TransferMoneyQuick_Data.TransferQuick.BANK_INVALID, "Ngân hàng hưởng");

		log.info("TC_27_verify ket qua tim kiem");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, TransferMoneyQuick_Data.TransferQuick.BANK_INVALID));
	}

	@Test
	public void TC_28_ChonMotNganHangThuHuong() {
		log.info("TC_28_Tim kiem ngan hang theo ten ngan hang, nhap ten ngan hang");
		transferMoney.inputToDynamicInputBoxSearchBank(driver, TransferMoneyQuick_Data.TransferQuick.BANK[1], "Ngân hàng hưởng");

		log.info("TC_28_Click ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.BANK[1]);

		log.info("TC_28_verify ket qua tim kiem");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.BANK[1]));
	}

	@Test
	public void TC_29_ChonMotNganHangThuHuongKhac() {
		log.info("TC_29_Click ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.BANK[1]);

		log.info("TC_29_Tim kiem ngan hang theo ten ngan hang, nhap ten ngan hang");
		transferMoney.inputToDynamicInputBoxSearchBank(driver, TransferMoneyQuick_Data.TransferQuick.BANK[0], "Ngân hàng hưởng");

		log.info("TC_29_Click ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.BANK[0]);

		log.info("TC_29_verify ket qua tim kiem");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.BANK[0]));
	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
