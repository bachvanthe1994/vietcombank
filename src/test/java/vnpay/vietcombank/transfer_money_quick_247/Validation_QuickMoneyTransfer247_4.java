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
import vietcombank_test_data.TransferMoneyQuick_Data.TransferQuick;

public class Validation_QuickMoneyTransfer247_4 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private SetupContactPageObject setupContact;
	private TransferMoneyObject transferMoney;
	private String amountStartString;
	List<String> listActualAmountMoney;
	List<String> listExpectAmountMoney;
	private String amountExpect;
	private String Note;

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
	}

	//@Test
	public void TC_48_KiemTraLabelSoTienEUR() {
		log.info("TC_06_Chon tai khoan nguon VND");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.LIST_ACCOUNT_FROM[2]);

		log.info("TC_01_Kiem tra label so tien");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Số tiền"));

		log.info("TC_01_Kiem tra label ty gia quy doi tham khao");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Tỷ giá quy đổi tham khảo"));

		log.info("TC_01_Kiem tra label ty gia quy doi tham khao");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "1 EUR ~ 27,006 VND"));
	}

	//@Test
	public void TC_49_KiemTraLabelLoaiTienEUR() {
		log.info("TC_01_Kiem tra label loai tien");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "EUR"));
	}

	//@Test
	public void TC_50_NhapNhoHon10KyTuVaKiemTraDauChamEUR() {
		log.info("TC_02_Input so tien co 9 ky tu phan nguyrm va 2 chu so phan thap phan");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

		log.info("TC_02_text mac dinh 'So tien' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Số tiền"));

		log.info("TC_02_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_USD_EUR;
		amountExpect = amountExpect.substring(0, 3) + "," + amountExpect.substring(3, 6) + "," + amountExpect.substring(6, amountExpect.length());

		log.info("TC_02_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpect);

	}

	//@Test
	public void TC_51_Nhap10KyTuVaKiemTraDauChamEUR() {
		log.info("TC_02_Input so tien co 9 ky tu");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_TEN_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

		log.info("TC_02_text mac dinh 'So tien' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Số tiền"));

		log.info("TC_02_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_TEN_NUMBER_USD_EUR;
		amountExpect = amountExpect.substring(0, 1) + "," + amountExpect.substring(1, 4) + "," + amountExpect.substring(4, 7) + "," + amountExpect.substring(7, amountExpect.length());
		log.info("TC_02_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpect);
	}

//@Test
	public void TC_52_KiemTraDinhDangHienThiEUR() {
		log.info("TC_02_Invalid so tien co 9 chu so phan nguyen va 3 chu so phan thap phan");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_INVALID_USD_EUR, "Thông tin giao dịch", "1");

		log.info("Khong sendkey duoc gia tri, mac dinh de trong va hien thi text so tien");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Số tiền"));

		log.info("TC_02_Input so tien co 9 chu so phan nguyen va 2 chu so phan thap phan");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

		log.info("TC_02_Lay gia tri nhap vao");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_USD_EUR;
		amountExpect = amountExpect.substring(0, 3) + "," + amountExpect.substring(3, 6) + "," + amountExpect.substring(6, amountExpect.length());

		log.info("TC_02_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpect);
	}

	// @Test --- Lỗi app hiển thị dấu , ngăn cách hàng nghìn đang bị sai
	public void TC_53_KiemTraDinhDangHienThiGoiYEUR() {

		listActualAmountMoney = new ArrayList<String>();
		listExpectAmountMoney = new ArrayList<String>();

		log.info("TC_15_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

		listActualAmountMoney = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvAmount");

		log.info("TC_15_List goi y hien thi tren man hinh");
		listExpectAmountMoney.add("50,002.00 EUR ~ 1,350,354,010 VND");
		listExpectAmountMoney.add("500,020.00 EUR ~ 13,503,540,100 VND");

		log.info("TC_15_Kiem tra so tien goi y");
		verifyEquals(listActualAmountMoney, listExpectAmountMoney);
	}

//@Test--- Run theo case 53
	public void TC_54_KiemTraKhongHienThiGoiYNhanhKhiFocusRaNgoaiEUR() {
		log.info("TC_07_Click text thong tin giao dich focus ra ngoai");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_07_Kiem tra gia tri goi y khong hien thi tren man hinh");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "50,002.20 USD ~ 1,500,060,000 VND"));
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "500,020.00 USD ~ 15,000,600,000 VND"));
	}

	// @Test ----Loi app không hiển thị popup gợi ý
	public void TC_55_KiemTraHienThiGoiYNhanhTiepTucFocusVaoSoTienEUR() {
		log.info("TC_07_Click vao so tien vua nhap tren");
		transferMoney.clickToDynamicInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		log.info("TC_06_Lay danh sach goi y");
		listActualAmountMoney = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvAmount");

		log.info("TC_06_Danh sach goi y hien thi tren man hinh");
		listExpectAmountMoney.add("50,002.00 EUR ~ 1,350,354,010 VND");
		listExpectAmountMoney.add("500,020.00 EUR ~ 13,503,540,100 VND");

		log.info("TC_06_Kiem tra so tien goi y");
		verifyEquals(listActualAmountMoney, listExpectAmountMoney);
	}

	// @Test ---> Loi app vẫn hiển thị popup gợi ý x10, x100
	public void TC_56_ChonMotGoiYVaDongPopupEUR() {
		log.info("TC_06_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

		log.info("TC_09_Click vao so tien goi y");
		transferMoney.clickToDynamicSuggestedMoney(driver, 1, "com.VCB:id/tvAmount");

		log.info("TC_09_Lay danh sach goi y");
		listActualAmountMoney = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvAmount");

		log.info("TC_09_Kiem tra gia tri goi y khong hien thi tren man hinh");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "500,020.00 USD ~ 15,000,600,000 VND"));
	}
// @Test 
	public void TC_57_BoTrongTruongSoTien() {
		log.info("TC_06_Clear text truong nhap so tien");
		transferMoney.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivClearInput");
		
		log.info("TC_06_Nhap so tai khoan huong");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO, "Thông tin người hưởng", "1");

		log.info("TC_06_chon ngan hang huong");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.BANK[0]);


		log.info("TC_05_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_05_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBox(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Nội dung");

		log.info("TC_05_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_verify message khi tai khoan nguoi nhan trong ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.AMOUNT_BLANK));

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}
 
 @Test 
	public void TC_58_KiemTraLabelLinkHanMuc() {
		log.info("TC_58_Check text han muc");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Hạn mức"));
		
		log.info("TC_58_Click link han muc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Hạn mức");
		
		log.info("TC_58_Check mot phan noi dung han muc");
		transferMoney.isDynamicTextInfoDisplayed(driver, "Hạn mức giao dịch áp dụng cho các chức năng chuyển tiền:");
		
		log.info("TC_58_Quay lai man hinh chuyen tien nhanh 24/7");
		transferMoney.clickToDynamicBottomMenu(driver, "com.VCB:id/ivTitleLeft");
 }
 
 @Test 
	public void TC_59_KiemTraHienThiTextNoiDung() {
		log.info("TC_059_Kiem tra label noi dung");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Nội dung"));
 }
 
 @Test 
	public void TC_60_KiemTraGiaTriChoPhepNoiDung() {
		log.info("TC_059_Nhap 2 ky tu cho phep");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_2_CHAR, "Thông tin giao dịch","3");
		
		log.info("TC_59_text mac dinh 'noi dung' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Nội dung"));
		
		log.info("TC_59_Lay noi dung tren man hinh");
		Note = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch","3");
		
		log.info("TC_59_Verify text noi dung");
		verifyEquals(Note, TransferMoneyQuick_Data.TransferQuick.NOTE_2_CHAR);
		
		log.info("TC_059_Nhap 140 ky tu cho phep");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_140_CHAR, "Thông tin giao dịch","3");
		
		log.info("TC_59_Lay noi dung tren man hinh");
		Note = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch","3");
		
		log.info("TC_59_Verify text noi dung");
		verifyEquals(Note, TransferMoneyQuick_Data.TransferQuick.NOTE_140_CHAR);
		
		log.info("TC_059_Nhap tieng viet co dau");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_VIETNAM_KEY_INVALID, "Thông tin giao dịch","3");
		
		log.info("TC_59_Lay noi dung tren man hinh");
		Note = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch","3");
		
		log.info("TC_59_Verify text noi dung");
		verifyEquals(Note, transferMoney.removeUnicode(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_VIETNAM_KEY_INVALID));
		
	 
 }
 
 @Test 
	public void TC_61_KiemTraGiaTriChoPhepNoiDung() {
		log.info("TC_059_Nhap 2 ky tu cho phep");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_2_CHAR, "Thông tin giao dịch","3");
 }
}
