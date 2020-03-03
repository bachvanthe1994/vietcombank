package vnpay.vietcombank.transfer_money_charity;

import java.io.IOException;

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
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Validation_Continue_Button_Charity extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferMoneyCharityPageObject transferMoneyCharity;

	TransferCharity info = new TransferCharity(Account_Data.Valid_Account.ACCOUNT2, TransferMoneyCharity_Data.ORGANIZATION, "1000000", "Do Minh Duc", "So 18 ngo 3 Thai Ha", "Ho ngheo", "Mật khẩu đăng nhập");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
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
		verifyEquals(transferMoneyCharity.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyCharity_Data.ORGANIZATION_INPUT_EMPTY_MESSAGE);

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
		verifyEquals(transferMoneyCharity.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyCharity_Data.MONEY_INPUT_EMPTY_MESSAGE);

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
		verifyEquals(transferMoneyCharity.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyCharity_Data.NAME_INPUT_EMPTY_MESSAGE);

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
		verifyEquals(transferMoneyCharity.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyCharity_Data.STATUS_INPUT_EMPTY_MESSAGE);

		log.info("TC_04_4_Kiem tra pop up hien thi nut Dong");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng"));

		log.info("TC_04_5_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");

		log.info("TC_04_6_Nhap Hoan canh nguoi ung ho");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, info.status, "Thông tin giao dịch", "4");
	}

	@Test
	public void TC_05_KiemTraChonTaiKhoanNguonLaTaiKhoanDongChuSoHuu() {
		log.info("TC_05_Chon tai khoan nguon VND");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Invalid_Account.SAME_OWNER_ACCOUNT_1);

		log.info("TC_05_Input so tien chuyen");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, info.money, "Thông tin giao dịch", "1");

		log.info("TC_05_Step_Nhap so tai khoan chuyen dong chu so huu voi tai khoan nguon");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, Account_Data.Invalid_Account.SAME_OWNER_ACCOUNT_2, "Thông tin người hưởng", "1");

		log.info("TC_05_Step_Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_verify message khi tai khoan dong chu so huu ");
		verifyEquals(transferMoneyCharity.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyQuick_Data.MessageTransferMoney.SAME_ACCOUNT_NAME);

		log.info("Close popup");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
	}

//	@Test
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
		verifyEquals(transferMoneyCharity.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyCharity_Data.MONEY_INPUT_OVER_MESSAGE);

		log.info("TC_07_5_Kiem tra pop up hien thi nut Dong");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng"));

		log.info("TC_07_6_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_08_KiemTraSoTienGiaoDichNhoHonHanMucToiThieu1LanGiaoDich() {
		log.info("TC_08_1_Nhap so tien");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, TransferMoneyCharity_Data.MONEY_NOT_ENOUGH_FOR_TRANSACTION, "Thông tin giao dịch", "1");

		log.info("TC_08_2_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_08_3_Kiem tra pop up hien thi thong bao");
		verifyEquals(transferMoneyCharity.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyCharity_Data.MONEY_INPUT_NOT_ENOUGH_PER_A_TRANSACTION_MESSAGE);

		log.info("TC_08_4_Kiem tra pop up hien thi nut Dong");
		verifyTrue(transferMoneyCharity.isDynamicButtonDisplayed(driver, "Đóng"));

		log.info("TC_08_5_Click nut Dong");
		transferMoneyCharity.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_09_KiemTraSoTienGiaoDichVuotQuaHanMucToiDa1LanGiaoDich() {
		log.info("TC_09_1_Nhap so tien");
		transferMoneyCharity.inputToDynamicInputBoxByHeader(driver, TransferMoneyCharity_Data.MONEY_OVER_TRANSACTION_PER_DAY, "Thông tin giao dịch", "1");

		log.info("TC_09_2_Click Tiep tuc");
		transferMoneyCharity.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_09_3_Kiem tra pop up hien thi thong bao");
		verifyEquals(transferMoneyCharity.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), TransferMoneyCharity_Data.MONEY_INPUT_OVER_PER_A_TRANSACTION_MESSAGE);

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
