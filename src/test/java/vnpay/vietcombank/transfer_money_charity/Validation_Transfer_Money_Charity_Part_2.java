package vnpay.vietcombank.transfer_money_charity;

import java.io.IOException;
import java.util.List;

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
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.TransferMoneyCharity_Data;

public class Validation_Transfer_Money_Charity_Part_2 extends Base {
	AndroidDriver<AndroidElement> driver;
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
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
	}

	String defaultAccount;

	@Test
	public void TC_01_KiemTraTruyVanSoDuCuaTaiKhoanMacDinhLaVND() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		transferMoneyCharity = PageFactoryManager.getTransferMoneyCharityPageObject(driver);

		defaultAccount = homePage.getDynamicTextDetailByID(driver, "com.VCB:id/tvDefaultAcc");
		log.info("TC_01_01_Click Chuyen tien tu thien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		log.info("TC_01_02_Kiem tra hien thi so du mac dinh la VND");
		String availableBalance = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		verifyTrue(availableBalance.contains("VND"));

	}

	@Test
	public void TC_03_KiemTraHienThiTaiKhoanMacDinh() {
		log.info("TC_03_01_Kiem tra hien thi tai khoan mac dinh");
		String actualDefaultAccount = transferMoneyCharity.getTextDynamicDefaultSourceAccount(driver, "Tài khoản nguồn");
		verifyEquals(actualDefaultAccount, defaultAccount);
	}

	@Test
	public void TC_04_KiemTraHienThiThongTinTaiKhoanNguon() {
		log.info("TC_04_01_Mo danh sach tai khoan nguon");
		transferMoneyCharity.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_04_02_Lay thong tin tai khoan nguon");
		String expectAvailableBalance = transferMoneyCharity.getMoneyByAccount(driver, info.sourceAccount);

		log.info("TC_04_03_Chon tai khoan nguon");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.sourceAccount);

		log.info("TC_04_04_Kiem tra hien thi label So du kha dung");
		verifyTrue(transferMoneyCharity.isDynamicMessageAndLabelTextDisplayed(driver, "Số dư khả dụng"));

		log.info("TC_04_05_Kiem tra so tien hien thi dung voi tai khoan nguon duoc chon");
		String availableBalance = transferMoneyCharity.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		verifyEquals(availableBalance, expectAvailableBalance);
	}

	@Test
	public void TC_05_KiemTraHienThiMacDinhOThongTinNguoiHuong() {
		log.info("TC_05_01_Click Chuyen tien tu thien");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền từ thiện");

		log.info("TC_05_02_Kiem tra hien thi mac dinh");
		verifyTrue(transferMoneyCharity.isDynamicTextInInputBoxDisPlayed(driver, "Quỹ/ Tổ chức từ thiện"));

	}

	@Test
	public void TC_06_KiemTraDanhSachQuyToChucTuThien() {
		log.info("TC_06_01_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicInput(driver, "Quỹ/ Tổ chức từ thiện");

		List<String> actualListOrganizationCharity = transferMoneyCharity.getListOrganizationCharity();

		log.info("TC_06_02_Kiem tra danh sach Quy/ To chuc tu thien");
		verifyEquals(actualListOrganizationCharity, TransferMoneyCharity_Data.LIST_ORGANIZATION_CHARITY);
	}

	@Test
	public void TC_07_KiemTraChon1QuyToChucTuThien() {
		log.info("TC_07_01_Chon Quy/ To chuc tu thien");
		transferMoneyCharity.clickToDynamicButtonLinkOrLinkText(driver, info.organization);

		log.info("TC_07_02_Kiem tra Quy/ To chuc tu thien duoc hien thi");
		String actualOrganization = transferMoneyCharity.getDynamicTextInInputBoxByHeader(driver, "Thông tin người hưởng", "1");
		verifyEquals(actualOrganization, info.organization);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
