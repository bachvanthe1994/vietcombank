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
import model.TransferInVCBRecurrent;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyInVcbPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Transfer_Money_Recurrent_Validation_Part_4 extends Base {
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
	public void TC_01_ChuyenTienDinhKy_NoiDung_KiemTraHienThiMacDinh() {
		log.info("TC_01_01_Click Chuyen tien trong ngan hang");
		homePage.scrollToText(driver, "Chuyển tiền tới ngân hàng khác");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền trong VCB");

		log.info("TC_01_02_Chon phuong thuc chuyen tien");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền ngay");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền định kỳ");
		
		log.info("TC_01_03_Kiem tra gia tri mac dinh o Noi dung");
		String actualNote = transferRecurrent.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");
		verifyEquals(actualNote, "Nội dung");
		
	}
	
	@Test
	public void TC_02_ChuyenTienDinhKy_NoiDung_KiemTraKyTuNhap() {
		log.info("TC_02_01_Nhap gia tri kiem tra vao o Noi dung");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InvalidInputData.TEXT_NUMBER_AND_SPECIAL_CHARACTERS, "Thông tin giao dịch", "3");
		
		log.info("TC_02_02_Kiem tra gia tri o Noi dung");
		String actualNote = transferRecurrent.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");
		verifyEquals(actualNote, TransferMoneyInVCB_Data.InvalidInputData.TEXT_NUMBER_AND_SPECIAL_CHARACTERS);
	}
	
	@Test
	public void TC_03_ChuyenTienDinhKy_NoiDung_KiemTraMaxLength() {
		log.info("TC_03_01_Nhap gia tri kiem tra vao o Noi dung");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InvalidInputData.TEXT_WITH_MORE_THAN_140_CHARAC, "Thông tin giao dịch", "3");
		
		log.info("TC_03_02_Kiem tra do dai gia tri nhap o Noi dung");
		int actualNoteLength = transferRecurrent.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3").length();
		verifyEquals(actualNoteLength, 140);
	}
	
	@Test
	public void TC_04_ChuyenTienDinhKy_NutTiepTuc_BoTrongTaiKhoanDich() {
		log.info("TC_04_01_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		
		log.info("TC_04_02_Bo trong tai khoan dich");
		transferRecurrent.inputToDynamicInputBox(driver, "", "Nhập/chọn tài khoản nhận VND");

		log.info("TC_04_03_Chon tan suat");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Ngày");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.frequencyCategory);
		transferRecurrent.inputFrequencyNumber(info.frequencyNumber);

		log.info("TC_04_04_Nhap so tien");
		transferRecurrent.inputToDynamicInputBox(driver, info.money, "Số tiền");

		log.info("TC_04_05_Chon nguoi tra phi giao dich");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.fee);

		log.info("TC_04_06_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info.note, "Thông tin giao dịch", "3");

		log.info("TC_04_07_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_04_08_Kiem tra popup thong bao");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.EMPTY_RECEIVE_ACCOUNT_MESSAGE));
	
	}
	
	@Test
	public void TC_05_ChuyenTienDinhKy_NutTiepTuc_BoTrongTaiKhoanDich_KiemTraNutDong() {
		log.info("TC_05_01_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_05_02_Kiem tra man hinh khoi tao chuyen tien trong VCB");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền trong Vietcombank"));
		
		log.info("TC_05_03_Nhap tai khoan dich");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info.destinationAccount, "Thông tin người hưởng", "1");
	}
	
	@Test
	public void TC_06_ChuyenTienDinhKy_NutTiepTuc_BoTrongSoTien() {
		log.info("TC_06_01_Bo trong so tien");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, "", "Thông tin giao dịch", "1");
		
		log.info("TC_06_02_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_06_03_Kiem tra popup thong bao");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.EMPTY_MONEY_MESSAGE));
	}
	
	@Test
	public void TC_07_ChuyenTienDinhKy_NutTiepTuc_BoTrongSoTien_KiemTraNutDong() {
		log.info("TC_07_01_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_07_02_Kiem tra man hinh khoi tao chuyen tien trong VCB");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền trong Vietcombank"));
		
		log.info("TC_07_03_Nhap so tien");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info.money, "Thông tin giao dịch", "1");
	}
	
	@Test
	public void TC_08_ChuyenTienDinhKy_NutTiepTuc_BoTrongNoiDung() {
		log.info("TC_08_01_Bo trong noi dung");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, "", "Thông tin giao dịch", "3");
		
		log.info("TC_08_02_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_08_03_Kiem tra popup thong bao");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.EMPTY_TRANSFER_NOTE_MESSAGE));
	}
	
	@Test
	public void TC_09_ChuyenTienDinhKy_NutTiepTuc_BoTrongNoiDung_KiemTraNutDong() {
		log.info("TC_09_01_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_09_02_Kiem tra man hinh khoi tao chuyen tien trong VCB");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền trong Vietcombank"));
		
		log.info("TC_09_03_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info.note, "Thông tin giao dịch", "3");
	}
	
	@Test
	public void TC_10_ChuyenTienDinhKy_NutTiepTuc_TaiKhoanNguonVaDichTrungNhau() {
		log.info("TC_10_01_Nhap tai khoan dich trung voi tai khoan nguon");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info.sourceAccount, "Thông tin người hưởng", "1");
		
		log.info("TC_10_02_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_10_03_Kiem tra popup thong bao");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.DUPLICATED_ACCOUNT_MESSAGE));
	}
	
	@Test
	public void TC_11_ChuyenTienDinhKy_NutTiepTuc_TaiKhoanNguonVaDichTrungNhau_KiemTraNutDong() {
		log.info("TC_11_01_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_11_02_Kiem tra man hinh khoi tao chuyen tien trong VCB");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền trong Vietcombank"));
		
		log.info("TC_11_03_Nhap noi dung");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info.note, "Thông tin giao dịch", "3");
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
