package vnpay.vietcombank.transfer_money_charity;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import model.TransferCharity;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyCharityPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyCharity_Data;

public class Validation_Continue_Button_Charity extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyCharityPageObject transferMoneyCharity;

	TransferCharity info = new TransferCharity("0011000000779", "Test order", "1000000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })

	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

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
	public void TC_01_KiemTraBoTrongTruongQuyToChucTuThien() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);

		log.info("TC_01_1_Click Chuyen tien tu thien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		log.info("TC_01_2_Chon tai khoan nguon");

		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		
		log.info("TC_01_3_Nhap so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.money, "Số tiền ủng hộ");

		log.info("TC_01_4_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.name, "Tên người ủng hộ");

		log.info("TC_01_5_Nhap dia chi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.address, "Địa chỉ người ủng hộ");

		log.info("TC_01_6_Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBox(driver, info.status, "Hoàn cảnh người ủng hộ");

		log.info("TC_01_7_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_8_Kiem tra pop up hien thi thong bao");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.ORGANIZATION_INPUT_EMPTY_MESSAGE));
		
		log.info("TC_01_9_Kiem tra pop up hien thi nut Dong");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng"));
		
		log.info("TC_01_10_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_01_11_Chon Quy, to chuc tu thien");
		transferMoneyCharity.clickToDynamicInput(driver, "Quỹ/ Tổ chức từ thiện");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.organization);
		
	}
	
	@Test
	public void TC_02_KiemTraBoTrongSoTienUngHo() {
		log.info("TC_02_1_Xoa so tien ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "", "Thông tin giao dịch", "1");

		log.info("TC_02_2_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_02_3_Kiem tra pop up hien thi thong bao");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.MONEY_INPUT_EMPTY_MESSAGE));
		
		log.info("TC_02_4_Kiem tra pop up hien thi nut Dong");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng"));
		
		log.info("TC_02_5_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_02_6_Nhap so tien");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, info.money, "Thông tin giao dịch", "1");
	}
	
	@Test
	public void TC_03_KiemTraBoTrongTenNguoiUngHo() {
		log.info("TC_03_1_Xoa Ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "", "Thông tin giao dịch", "2");

		log.info("TC_03_2_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_03_3_Kiem tra pop up hien thi thong bao");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.NAME_INPUT_EMPTY_MESSAGE));
		
		log.info("TC_03_4_Kiem tra pop up hien thi nut Dong");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng"));
		
		log.info("TC_03_5_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_03_6_Nhap ten nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, info.name, "Thông tin giao dịch", "2");
	}
	
	@Test
	public void TC_04_KiemTraBoTrongHoanCanhNguoiUngHo() {
		log.info("TC_04_1_Xoa Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "", "Thông tin giao dịch", "4");

		log.info("TC_04_2_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_04_3_Kiem tra pop up hien thi thong bao");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.STATUS_INPUT_EMPTY_MESSAGE));
		
		log.info("TC_04_4_Kiem tra pop up hien thi nut Dong");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng"));
		
		log.info("TC_04_5_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
		
		log.info("TC_04_6_Nhap Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, info.status, "Thông tin giao dịch", "4");
	}
	
	@Test
	public void TC_05_KiemTraChonTaiKhoanNguonLaTaiKhoanDongChuSoHuu() {
		
	}
	
	@Test
	public void TC_06_KiemTraChonTaiKhoanNguonLaTaiKhoanKhongDuocPhepTrichNo() {
		
	}

	@Test
	public void TC_07_KiemTraLoiKhongDuSoDu() {
		long surplus, moneyCharity;
		log.info("TC_07_1_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);
		surplus = Long.parseLong(transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", ""));
		moneyCharity = surplus + 1;
		
		log.info("TC_07_2_Nhap so tien");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, String.valueOf(moneyCharity), "Thông tin giao dịch", "1");
		
		log.info("TC_07_3_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_07_4_Kiem tra pop up hien thi thong bao");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.MONEY_INPUT_OVER_MESSAGE));
		
		log.info("TC_07_5_Kiem tra pop up hien thi nut Dong");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng"));
		
		log.info("TC_07_6_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
	}
	
	@Test
	public void TC_08_KiemTraSoTienGiaoDichNhoHonHanMucToiThieu1LanGiaoDich() {
		log.info("TC_08_1_Nhap so tien");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "1000", "Thông tin giao dịch", "1");
		
		log.info("TC_08_2_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_08_3_Kiem tra pop up hien thi thong bao");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.MONEY_INPUT_NOT_ENOUGH_PER_A_TRANSACTION_MESSAGE));
		
		log.info("TC_08_4_Kiem tra pop up hien thi nut Dong");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng"));
		
		log.info("TC_08_5_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
	}
	
	@Test
	public void TC_09_KiemTraSoTienGiaoDichVuotQuaHanMucToiThieu1LanGiaoDich() {
		log.info("TC_09_1_Nhap so tien");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, "50000001", "Thông tin giao dịch", "1");
		
		log.info("TC_09_2_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_09_3_Kiem tra pop up hien thi thong bao");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyCharity_Data.MONEY_INPUT_OVER_PER_A_TRANSACTION_MESSAGE));
		
		log.info("TC_09_4_Kiem tra pop up hien thi nut Dong");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng"));
		
		log.info("TC_09_5_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}