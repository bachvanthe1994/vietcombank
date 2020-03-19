package vnpay.vietcombank.landLinePhoneCharge;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.HomePageObject;
import pageObjects.LandLinePhoneChargePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LandLinePhoneCharge_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class LandLinePhoneCharge extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private LandLinePhoneChargePageObject landLinePhoneCharge;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private String transactionNumber;
	long fee, money = 0;
	String password = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		
		password = pass;
	}

	private long surplus, availableBalance, actualAvailableBalance;
	@Test
	public void TC_01_ThanhToanCuocDienThoaiCoDinh_CoDinhKhongDay_ThanhToanMatKhauDangNhap() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		landLinePhoneCharge = PageFactoryManager.getLandLinePhoneChargePageObject(driver);

		log.info("TC_01_01_Click Chuyen tien tu thien");
		landLinePhoneCharge.scrollDownToText(driver, "Cước điện thoại cố định");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Cước điện thoại cố định");
		
		log.info("TC_01_02_Chon tai khoan nguon");
		landLinePhoneCharge.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_03_Chon loai cuoc thanh toan");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, "Cố định không dây Viettel");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, "Cố định không dây Viettel");
		
		log.info("TC_01_04_Nhap so dien thoai tra cuoc va bam Tiep tuc");
		landLinePhoneCharge.inputPhoneNumberLandLinePhoneCharge(LandLinePhoneCharge_Data.LIST_LANDLINE_PHONE_NOLINE);
		
		money = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTotalPrice"));
		log.info("TC_01_05_Kiem tra man hinh xac nhan thong tin");
		
		
		log.info("TC_01_06_Chon phuong thuc xac thuc");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		fee = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Mật khẩu đăng nhập"));

		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		log.info("TC_01_11_Click Tiep tuc");
		landLinePhoneCharge.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_11_Nhap mat khau");
		landLinePhoneCharge.inputToDynamicPopupPasswordInput(driver, password, "Tiếp tục");
		landLinePhoneCharge.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_12_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_01_12_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(landLinePhoneCharge.isDynamicMessageAndLabelTextDisplayed(driver, LandLinePhoneCharge_Data.SUCCESS_TRANSFER_MONEY));

		log.info("TC_01_12_2_Kiem tra ten nguoi thu huong");

		log.info("TC_01_12_3_Kiem tra tai khoan dich");

		log.info("TC_01_12_4_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(landLinePhoneCharge.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_01_12_5_Lay ma giao dich");
		transferTime = landLinePhoneCharge.getTransferTimeSuccess(driver, TransferMoneyQuick_Data.TransferQuick.SUCCESS_TRANSFER_MONEY);
		transactionNumber = landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_13_Click Thuc hien giao dich moi");
		landLinePhoneCharge.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_14_Kiem tra so du kha dung luc sau");
		landLinePhoneCharge.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalance = canculateAvailableBalances(surplus, money, fee);
		verifyEquals(actualAvailableBalance, availableBalance);
		
	}
	
	@Test(dependsOnMethods = { "TC_01_ThanhToanCuocDienThoaiCoDinh_CoDinhKhongDay" })
	public void TC_02_ThanhToanCuocDienThoaiCoDinh_CoDinhKhongDay_BaoCao() {
		log.info("TC_02_1: Click  nut Back");
		landLinePhoneCharge.clickToDynamicBackIcon(driver, "Cước điện thoại cố định");

		log.info("TC_02_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_3: Click Bao Cao giao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_02_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_02_5: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán cước điện thoại cố định");

		log.info("TC_02_6: Click Chon Tai Khoan");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_7: Chon tai Khoan chuyen");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_02_8: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_02_9: Kiem tra ngay tao giao dich hien thi");
		String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
		verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), convertTransferTimeToReportDateTime(transferTime));

		log.info("TC_02_10: Kiem tra noi dung hien thi");

		log.info("TC_02_11: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(money + "") + " VND"));

		log.info("TC_02_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_13: Kiem tra thoi gian tao giao dich hien thi");
		String reportTime2 = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		verifyEquals(reportTime2, reportTime1);

		log.info("TC_02_14: Kiem tra ma giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_02_15: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_02_16: Kiem tra so tai khoan ghi co");

		log.info("TC_02_17: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(money + "") + " VND"));

		log.info("TC_02_18: Kiem tra ten quy, to chuc tu thien");

		log.info("TC_02_19: Kiem tra phi giao dich hien thi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(fee + "") + " VND");

		log.info("TC_02_20: Kiem tra loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Thanh toán cước điện thoại cố định");

		log.info("TC_02_21: Kiem Tra noi dung giao dich");

		log.info("TC_02_22: Click  nut Back");
		landLinePhoneCharge.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_02_23: Click  nut Back");
		landLinePhoneCharge.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_02_24: Click  nut Home");
		landLinePhoneCharge.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
