package vnpay.vietcombank.transfer_money_in_vcb;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.Keys;
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
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Transfer_Money_Recurrent_Validation_Part_2 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TransferMoneyInVcbPageObject transferRecurrent;
	private HomePageObject homePage;

	TransferInVCBRecurrent info = new TransferInVCBRecurrent("0010000000322", "0010000000318", "1", "Ngày", "", "", "500000", "Người chuyển trả", "test", "SMS OTP");
	TransferInVCBRecurrent info1 = new TransferInVCBRecurrent("0011140000647", "0010000000318", "2", "Ngày", "", "", "10", "Người nhận trả", "test", "SMS OTP");
	
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

	@Test
	public void TC_01_ChuyenTienDinhKy_KiemTraNhomThongTinTanSuat() {
		log.info("TC_01_01_Click Chuyen tien trong ngan hang");
		homePage.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");
			
		log.info("TC_01_02_Kiem tra chua xuat hien nhom thong tin Tan suat");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextUndisplayed(driver, "Tần suất"));
		
		log.info("TC_01_03_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
	
		transferRecurrent.scrollToText(driver, "Tiếp tục");
		
		log.info("TC_01_05_Kiem tra xuat hien nhom thong Tan suat");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Tần suất"));
		
		log.info("TC_01_06_Kiem tra hien thi tan suat");
		
		
		log.info("TC_01_07_Kiem tra hien thi don vi");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Ngày"));
		
		String startDate = getForwardDate(1);
		String endDate = getForwardDate(2);
		log.info("TC_01_08_Kiem tra hien thi ngay bat dau");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, startDate));
		
		log.info("TC_01_09_Kiem tra hien thi ngay ket thuc");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, endDate));
		
		String actualString = transferRecurrent.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvName") + transferRecurrent.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvValue");
		log.info("TC_01_10_Kiem tra hien thi so lan giao dich");
		verifyTrue(actualString.contains(TransferMoneyInVCB_Data.InputDataInVCB.NUMBER_TRANSACTION));
		
	}
	
	@Test
	public void TC_02_ChuyenTienDinhKy_KiemTraDanhSachChuKy() {
		log.info("TC_02_01_Mo danh sach chu ky");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		
		List<String> actualList = transferRecurrent.getListOfSuggestedMoney(driver, "com.VCB:id/tvContent");

		log.info("TC_02_02_danh sach gia tri chu ky");
		List<String> expectList = Arrays.asList("Ngày", "Tuần", "Tháng");

		log.info("TC_02_03_Kiem tra danh sach chi ky");
		verifyEquals(actualList, expectList);

		log.info("TC_02_01_Chon chu ky Ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
	}
	
	@Test
	public void TC_03_ChuyenTienDinhKy_KiemTraKyTuNhapOSoLuong() { 
		log.info("TC_03_01_Nhap gia tri vao o So luong");
		List<Keys> listKey = Arrays.asList(Keys.NUMPAD2, Keys.NUMPAD2);
		transferRecurrent.pressKeyCodeIntoAmountInput(driver, listKey);

		String actualAmount = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_03_2_Kiem tra gia tri trong o So luong");
		verifyEquals(actualAmount, "22");

	}
	
	@Test
	public void TC_04_ChuyenTienDinhKy_KiemTraNhapKyTu0() {
		log.info("TC_04_01_Nhap gia tri vao o So luong");
		List<Keys> listKey = Arrays.asList(Keys.NUMPAD0);
		transferRecurrent.pressKeyCodeIntoAmountInput(driver, listKey);

		String actualAmount = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_04_2_Kiem tra gia tri trong o So luong");
		verifyEquals(actualAmount, "");

	}
	
	@Test
	public void TC_05_ChuyenTienDinhKy_KiemTraNhapSoLuongKhiDonviLaNgay_NhapNhoHon31() {
		log.info("TC_05_01_Nhap gia tri vao o So luong");
		transferRecurrent.inputFrequencyNumber("30");

		String actualAmount = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_05_02_Kiem tra gia tri trong o So luong");
		verifyEquals(actualAmount, "30");

		log.info("TC_05_03_Nhap gia tri vao o So luong");
		transferRecurrent.inputFrequencyNumber("31");

		actualAmount = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_05_04_Kiem tra gia tri trong o So luong");
		verifyEquals(actualAmount, "31");
	}
	
	@Test
	public void TC_06_ChuyenTienDinhKy_KiemTraNhapSoLuongKhiDonviLaNgay_NhapLonHon31() {
		log.info("TC_06_01_Nhap gia tri vao o So luong");
		List<Keys> listKey = Arrays.asList(Keys.NUMPAD3, Keys.NUMPAD2);
		transferRecurrent.pressKeyCodeIntoAmountInput(driver, listKey);

		String actualAmount = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_06_02_Kiem tra gia tri trong o So tien ung ho");
		verifyEquals(actualAmount, "3");

	}
	
	@Test
	public void TC_07_ChuyenTienDinhKy_KiemTraNhapSoLuongKhiDonviLaTuan_NhapNhoHon53() {
		log.info("TC_07_01_Chon chu ky Tuan");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Tuần");
		
		log.info("TC_07_02_Nhap gia tri vao o So luong");
		transferRecurrent.inputFrequencyNumber("52");

		String actualAmount = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_07_03_Kiem tra gia tri trong o So tien ung ho");
		verifyEquals(actualAmount, "52");

		log.info("TC_07_04_Nhap gia tri vao o So luong");
		transferRecurrent.inputFrequencyNumber("53");

		actualAmount = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_07_5_Kiem tra gia tri trong o So tien ung ho");
		verifyEquals(actualAmount, "53");

	}
	
	@Test
	public void TC_08_ChuyenTienDinhKy_KiemTraNhapSoLuongKhiDonviLaTuan_NhaponHon53() {
		log.info("TC_08_01_Nhap gia tri vao o So luong");
		List<Keys> listKey = Arrays.asList(Keys.NUMPAD5, Keys.NUMPAD4);
		transferRecurrent.pressKeyCodeIntoAmountInput(driver, listKey);

		String actualAmountMoney = transferRecurrent.getTextInDynamicPasswordInput(driver, "com.VCB:id/edtContent");
		log.info("TC_08_2_Kiem tra gia tri trong o So tien ung ho");
		verifyEquals(actualAmountMoney, "5");

	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
