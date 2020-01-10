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
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyCharity_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Transfer_Money_Recurrent_Validation_Part_4 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TransferMoneyInVcbPageObject transferRecurrent;
	private HomePageObject homePage;

	TransferInVCBRecurrent info = new TransferInVCBRecurrent(Account_Data.Valid_Account.DEFAULT_ACCOUNT2, Account_Data.Valid_Account.ACCOUNT3, "1", "Ngày", "", "", "500000", "Người chuyển trả", "test", "SMS OTP");
	TransferInVCBRecurrent info1 = new TransferInVCBRecurrent(Account_Data.Valid_Account.EUR_ACCOUNT, Account_Data.Valid_Account.ACCOUNT3, "2", "Ngày", "", "", "10", "Người nhận trả", "test", "SMS OTP");
	TransferInVCBRecurrent info2 = new TransferInVCBRecurrent(Account_Data.Valid_Account.USD_ACCOUNT, Account_Data.Valid_Account.ACCOUNT3, "2", "Ngày", "", "", "10", "Người nhận trả", "test", "SMS OTP");
	TransferInVCBRecurrent info3 = new TransferInVCBRecurrent(Account_Data.Valid_Account.EUR_ACCOUN_2, Account_Data.Valid_Account.ACCOUNT3, "2", "Ngày", "", "", "10", "Người nhận trả", "test", "SMS OTP");
	TransferInVCBRecurrent info4 = new TransferInVCBRecurrent(Account_Data.Valid_Account.USD_ACCOUNT_2, Account_Data.Valid_Account.ACCOUNT3, "2", "Ngày", "", "", "10", "Người nhận trả", "test", "SMS OTP");
	
	
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
		
		log.info("TC_11_03_Nhap tai khoan dich");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info.destinationAccount, "Thông tin người hưởng", "1");
	}
	
	@Test
	public void TC_12_ChuyenTienDinhKy_NutTiepTuc_NhapSoTaiKhoanDichNhoHon13KyTu() {
		log.info("TC_12_01_Nhap tai khoan dich nho hon 13 ky tu");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InvalidInputData.INVALID_ACCOUNT_LESS_THAN_13_CHARACTERS, "Thông tin người hưởng", "1");
		
		log.info("TC_12_02_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_12_03_Kiem tra popup thong bao");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.INVALID_DESTINATION_ACCOUNT_MESSAGE));
		
		log.info("TC_12_04_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_12_05_Kiem tra man hinh khoi tao chuyen tien trong VCB");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền trong Vietcombank"));
		
		log.info("TC_12_06_Nhap tai khoan dich");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info.destinationAccount, "Thông tin người hưởng", "1");
	}
	
	@Test
	public void TC_13_ChuyenTienDinhKy_NutTiepTuc_NhapSoTaiKhoanDichKhongTonTaiTrenHeThong() {
		log.info("TC_13_01_Nhap tai khoan dich khong ton tai tren he thong");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InvalidInputData.INVALID_ACCOUNT_NOT_EXISTED_IN_SYSTEM, "Thông tin người hưởng", "1");
		
		log.info("TC_13_02_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_13_03_Kiem tra popup thong bao");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.INVALID_DESTINATION_ACCOUNT_MESSAGE));
		
		log.info("TC_13_04_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_13_05_Kiem tra man hinh khoi tao chuyen tien trong VCB");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền trong Vietcombank"));
		
		log.info("TC_13_06_Nhap tai khoan dich");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info.destinationAccount, "Thông tin người hưởng", "1");
	}
	
	@Test
	public void TC_14_ChuyenTienDinhKy_NutTiepTuc_NhapSoTaiKhoanDichKhongThuocVCB() {
		log.info("TC_14_01_Nhap tai khoan dich khong thuoc VCB");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InvalidInputData.INVALID_ACCOUNT_NOT_EXISTED_IN_VCB, "Thông tin người hưởng", "1");
		
		log.info("TC_14_02_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_14_03_Kiem tra popup thong bao");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.INVALID_DESTINATION_ACCOUNT_MESSAGE));
		
		log.info("TC_14_04_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_14_05_Kiem tra man hinh khoi tao chuyen tien trong VCB");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, "Chuyển tiền trong Vietcombank"));
		
		log.info("TC_14_06_Nhap tai khoan dich");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info.destinationAccount, "Thông tin người hưởng", "1");
	}
	
	@Test
	public void TC_15_ChuyenTienDinhKy_NutTiepTuc_ChuyenKhoanVNDSangUSD() {
		log.info("TC_15_01_Nhap tai khoan nguon VND");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		
		log.info("TC_15_02_Nhap tai khoan dich USD");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info2.sourceAccount, "Thông tin người hưởng", "1");
		
		log.info("TC_15_03_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_15_04_Kiem tra popup thong bao");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.INVALID_DESTINATION_ACCOUNT_MESSAGE));
		
		log.info("TC_15_05_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
		
	}
	
	@Test
	public void TC_16_ChuyenTienDinhKy_NutTiepTuc_ChuyenKhoanVNDSangEURO() {
		log.info("TC_16_01_Nhap tai khoan nguon VND");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		
		log.info("TC_16_02_Nhap tai khoan dich EUR");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info1.sourceAccount, "Thông tin người hưởng", "1");
		
		log.info("TC_16_03_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_16_04_Kiem tra popup thong bao");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.INVALID_DESTINATION_ACCOUNT_MESSAGE));
		
		log.info("TC_16_05_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
		
	}
	
	@Test
	public void TC_17_ChuyenTienDinhKy_NutTiepTuc_ChuyenKhoanUSDSangEURO() {
		log.info("TC_17_01_Nhap tai khoan nguon USD");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);
		
		log.info("TC_17_02_Nhap tai khoan dich EUR");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info1.sourceAccount, "Thông tin người hưởng", "1");
		
		log.info("TC_17_03_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_17_04_Kiem tra popup thong bao");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.INVALID_DESTINATION_ACCOUNT_MESSAGE));
		
		log.info("TC_17_05_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
		
	}
	
	@Test
	public void TC_18_ChuyenTienDinhKy_NutTiepTuc_ChuyenKhoanEURSangUSD() {
		log.info("TC_18_01_Nhap tai khoan nguon USD");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
		
		log.info("TC_18_02_Nhap tai khoan dich EUR");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info2.sourceAccount, "Thông tin người hưởng", "1");
		
		log.info("TC_18_03_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_18_04_Kiem tra popup thong bao");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.INVALID_DESTINATION_ACCOUNT_MESSAGE));
		
		log.info("TC_18_05_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
		
	}
	
//	@Test
	public void TC_19_ChuyenTienDinhKy_NutTiepTuc_ChuyenKhoanUSDSangUSD() {
		log.info("TC_19_01_Nhap tai khoan nguon USD");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info2.sourceAccount);
		
		log.info("TC_19_02_Nhap tai khoan dich EUR");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info4.sourceAccount, "Thông tin người hưởng", "1");
		
		log.info("TC_19_03_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_19_04_Kiem tra popup thong bao");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.INVALID_DESTINATION_ACCOUNT_MESSAGE));
		
		log.info("TC_19_05_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
		
	}
	
//	@Test
	public void TC_20_ChuyenTienDinhKy_NutTiepTuc_ChuyenKhoanEURSangEUR() {
		log.info("TC_20_01_Nhap tai khoan nguon USD");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info1.sourceAccount);
		
		log.info("TC_20_02_Nhap tai khoan dich EUR");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, info3.sourceAccount, "Thông tin người hưởng", "1");
		
		log.info("TC_20_03_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_20_04_Kiem tra popup thong bao");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.INVALID_DESTINATION_ACCOUNT_MESSAGE));
		
		log.info("TC_20_05_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
		
	}
	
	@Test
	public void TC_21_ChuyenTienDinhKy_NutTiepTuc_LoiKhongDuSoDu() {
		long surplus, moneyCharity;
		log.info("TC_21_1_Chon tai khoan nguon");
		transferRecurrent.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferRecurrent.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		surplus = Long.parseLong(transferRecurrent.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", ""));
		moneyCharity = surplus + 1;
		
		log.info("TC_21_02_Nhap so tien");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, String.valueOf(moneyCharity), "Thông tin giao dịch", "1");
		
		log.info("TC_21_03_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_21_04_Kiem tra pop up hien thi thong bao");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.MONEY_INPUT_OVER_MESSAGE));
		
		log.info("TC_21_05_Kiem tra pop up hien thi nut Dong");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Đóng"));
		
		log.info("TC_21_06_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
		
	}
	
	@Test
	public void TC_22__ChuyenTienDinhKy_NutTiepTuc_KiemTraSoTienGiaoDichVuotQuaHanMucToiDa1LanGiaoDich() {
		log.info("TC_22_01_Nhap so tien");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.MONEY_OVER_PER_TRANSACTION, "Thông tin giao dịch", "1");
		
		log.info("TC_22_02_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_22_03_Kiem tra pop up hien thi thong bao");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.MONEY_INPUT_OVER_PER_A_TRANSACTION_MESSAGE));
		
		log.info("TC_22_04_Kiem tra pop up hien thi nut Dong");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Đóng"));
		
		log.info("TC_22_05_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
	}
	
	@Test
	public void TC_23__ChuyenTienDinhKy_NutTiepTuc_KiemTraSoTienGiaoDichVuotQuaHanMucToiDa1NgayCuaNhomGiaoDich() {
		log.info("TC_23_01_Nhap so tien");
		transferRecurrent.inputToDynamicInputBoxByHeader(driver, TransferMoneyInVCB_Data.InputDataInVCB.MONEY_OVER_PER_DAY_OF_GROUP, "Thông tin giao dịch", "1");
		
		log.info("TC_23_02_Click Tiep tuc");
		transferRecurrent.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_23_03_Kiem tra pop up hien thi thong bao");
		verifyTrue(transferRecurrent.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyInVCB_Data.Output.MONEY_INPUT_OVER_PER_A_DAY_OF_GROUP_MESSAGE));
		
		log.info("TC_23_04_Kiem tra pop up hien thi nut Dong");
		verifyTrue(transferRecurrent.isDynamicButtonDisplayed(driver, "Đóng"));
		
		log.info("TC_23_05_Click nut Dong");
		transferRecurrent.clickToDynamicButton(driver, "Đóng");
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
