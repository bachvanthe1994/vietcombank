package vnpay.vietcombank.transfer_money_quick_247;

import java.io.IOException;
import java.util.ArrayList;
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
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
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

	@Test
	public void TC_30_KiemTraLabelSoTienVND() {
		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_01_Kiem tra label so tien");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Số tiền"));

		log.info("TC_01_Kiem tra label loai tien");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "VND"));
	}

 @Test
	public void TC_31_NhapNhoHon10KyTuVaKiemTraDauPhayVND() {
		log.info("TC_02_Input so tien co 9 ky tu");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_VND, "Thông tin giao dịch", "1");

		log.info("TC_02_text mac dinh 'So tien' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Số tiền"));

		log.info("TC_02_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_VND;
		amountExpect = amountExpect.substring(0, 3) + "," + amountExpect.substring(3, 6) + "," + amountExpect.substring(6, amountExpect.length());

		log.info("TC_02_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpect);

	}

	@Test
	public void TC_32_Nhap10KyTuVaKiemTraDauPhayVND() {
		log.info("TC_02_Input so tien co 9 ky tu");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_TEN_NUMBER_VND, "Thông tin giao dịch", "1");

		log.info("TC_02_text mac dinh 'So tien' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Số tiền"));

		log.info("TC_02_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_TEN_NUMBER_VND;
		amountExpect = amountExpect.substring(0, 1) + "," + amountExpect.substring(1, 4) + "," + amountExpect.substring(4, 7) + "," + amountExpect.substring(7, amountExpect.length());
		log.info("TC_02_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpect);
	}

 @Test
	public void TC_33_NhapLonHon10KyTuVaKiemTraDauPhayVND() {
		log.info("TC_02_Input so tien co 9 ky tu");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_ELEVEN_NUMBER_VND, "Thông tin giao dịch", "1");

		log.info("TC_02_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		log.info("TC_02_Khong cho phep nhap hon 10 ky tu, truong so tien trong");
		verifyEquals(amountStartString, "Số tiền");

	}

 @Test
	public void TC_34_NhapKyTuChuVaKiemTraDauPhayVND() {
		log.info("TC_02_Input so tien co 9 ky tu");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_TEXT_INVALID_VND, "Thông tin giao dịch", "1");

		log.info("TC_02_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_TEXT_INVALID_VND.replaceAll("\\D+","");
		
		amountExpect = amountExpect.substring(0, 2) + "," + amountExpect.substring(2, amountExpect.length());
		log.info("TC_02_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpect);

	}

 @Test
	public void TC_35_KiemTraHienThiGoiYNhanhVND() {
		listActualAmountMoney = new ArrayList<String>();
		listExpectAmountMoney = new ArrayList<String>();

		log.info("TC_06_Chon tai khoan nguon VND");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_06_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_VND, "Thông tin giao dịch", "1");

		log.info("TC_06_Lay danh sach goi y");
		listActualAmountMoney = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvAmount");

		log.info("TC_06_Danh sach goi y hien thi tren man hinh");
		listExpectAmountMoney.add("50,000 VND");
		listExpectAmountMoney.add("500,000 VND");
		listExpectAmountMoney.add("5,000,000 VND");

		log.info("TC_06_Kiem tra so tien goi y");
		verifyEquals(listActualAmountMoney, listExpectAmountMoney);
	}

 @Test
	public void TC_36_KiemTraKhongHienThiGoiYNhanhKhiFocusRaNgoaiVND() {
		log.info("TC_07_Click text thong tin giao dich focus ra ngoai");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_07_Kiem tra gia tri goi y khong hien thi tren man hinh");
		verifyTrue(transferMoney.isDynamicSuggestedMoneyUndisplayed(driver, "com.VCB:id/tvAmount"));
	}

	// @Test ----Loi app không hiển thị popup gợi ý
	public void TC_37_KiemTraHienThiGoiYNhanhTiepTucFocusVaoSoTienVND() {
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

	// @Test ---> Loi app vẫn hiển thị popup gợi ý x10, x100
	public void TC_38_ChonMotGoiYVaDongPopupVND() {
		log.info("TC_06_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_VND, "Thông tin giao dịch", "1");

		log.info("TC_09_Click vao so tien goi y");
		transferMoney.clickToDynamicSuggestedMoney(driver, 1, "com.VCB:id/tvAmount");

		log.info("TC_09_Lay danh sach goi y");
		listActualAmountMoney = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvAmount");

		log.info("TC_09_Kiem tra gia tri goi y khong hien thi tren man hinh");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "5,000,000 VND"));

	}

	@Test
	public void TC_39_KiemTraLabelSoTienUSD() {
		log.info("TC_06_Clear text truong nhap so tien");
		transferMoney.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivClearInput");
		
		log.info("TC_06_Chon tai khoan nguon USD");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[1]);

		log.info("TC_01_Kiem tra label so tien");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Số tiền"));

		log.info("TC_01_Kiem tra label ty gia quy doi tham khao");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Tỷ giá quy đổi tham khảo"));
		
		log.info("TC_01_Kiem tra label ty gia quy doi tham khao");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "1 USD ~ 30,000 VND"));
	}

	@Test
	public void TC_40_KiemTraLabelLoaiTienUSD() {
		log.info("TC_01_Kiem tra label loai tien");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "USD"));
	}

 @Test
	public void TC_41_NhapNhoHon10KyTuVaKiemTraDauChamUSD() {
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

 @Test
	public void TC_42_Nhap10KyTuVaKiemTraDauChamUSD() {
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

	@Test
	public void TC_43_KiemTraDinhDangHienThiUSD() {
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
	public void TC_44_KiemTraDinhDangHienThiGoiYUSD() {

		listActualAmountMoney = new ArrayList<String>();
		listExpectAmountMoney = new ArrayList<String>();

		log.info("TC_15_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

		listActualAmountMoney = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvAmount");

		
		log.info("TC_15_List goi y hien thi tren man hinh");
		listExpectAmountMoney.add("50,002.00 USD ~ 1,500,060,000 VND");
		listExpectAmountMoney.add("500,020.00 USD ~ 15,000,600,000 VND");

		log.info("TC_15_Kiem tra so tien goi y");
		verifyEquals(listActualAmountMoney, listExpectAmountMoney);
	}

	@Test
	public void TC_45_KiemTraKhongHienThiGoiYNhanhKhiFocusRaNgoaiUSD() {
		log.info("TC_07_Click text thong tin giao dich focus ra ngoai");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_07_Kiem tra gia tri goi y khong hien thi tren man hinh");
		verifyTrue(transferMoney.isDynamicSuggestedMoneyUndisplayed(driver, "com.VCB:id/tvAmount"));
	}
	
	// @Test ----Loi app không hiển thị popup gợi ý
		public void TC_46_KiemTraHienThiGoiYNhanhTiepTucFocusVaoSoTienUSD() {
			log.info("TC_07_Click vao so tien vua nhap tren");
			transferMoney.clickToDynamicInputBoxByHeader(driver, "Thông tin giao dịch", "1");

			log.info("TC_06_Lay danh sach goi y");
			listActualAmountMoney = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvAmount");

			log.info("TC_06_Danh sach goi y hien thi tren man hinh");
			listExpectAmountMoney.add("50,002.00 USD ~ 1,500,060,000 VND");
			listExpectAmountMoney.add("500,020.00 USD ~ 15,000,600,000 VND");

			log.info("TC_06_Kiem tra so tien goi y");
			verifyEquals(listActualAmountMoney, listExpectAmountMoney);

		}
		
		// @Test ---> Loi app vẫn hiển thị popup gợi ý x10, x100
		public void TC_47_ChonMotGoiYVaDongPopupUSD() {
			log.info("TC_06_Nhap so tien");
			transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

			log.info("TC_09_Click vao so tien goi y");
			transferMoney.clickToDynamicSuggestedMoney(driver, 1, "com.VCB:id/tvAmount");

			log.info("TC_09_Lay danh sach goi y");
			listActualAmountMoney = transferMoney.getListOfSuggestedMoney(driver, "com.VCB:id/tvAmount");

			log.info("TC_09_Kiem tra gia tri goi y khong hien thi tren man hinh");
			verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "500,020.00 USD ~ 15,000,600,000 VND"));

		}
		
		@AfterClass(alwaysRun = true)

		public void afterClass() {
			closeApp();
			service.stop();
		}

}

