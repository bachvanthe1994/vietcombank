package vnpay.vietcombank.vcb_pay_cable_television_bill;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.SourceAccountModel;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.PayBillTelevisionPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.Account_Data.Valid_Account;
import vietcombank_test_data.PayBillTelevison_Data.TitleData;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;

public class Television_flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private PayBillTelevisionPageObject billTelevision;
	private TransactionReportPageObject transReport;
	private String getPayments;
	private String getFee;
	private String account;
	private String getService;
	private String supplier;
	private String userCode;
	private String dealCode;
	private String getTimeTransfer;
	private double toltalMoney;
	private double payments;
	private double fee;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	List<String> listViettel = new ArrayList<String>();

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		homePage = PageFactoryManager.getHomePageObject(driver);
		billTelevision = PageFactoryManager.getPayBillTelevisionPageObject(driver);
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		listViettel = Arrays.asList(getDataInCell(29).split(";"));
	}

	@Parameters({ "pass" })
	@Test
	public void TC_01_PhuongThucThanhToanMK(String pass) {
		log.info("TC_01_STEP_0: chọn cước truyền hình cap");
		billTelevision.clickToDynamicButtonLinkOrLinkText(driver, TitleData.TITLE_TELEVISION);

		log.info("TC_01_STEP_1: lấy ra số dư");
		billTelevision.clickToTextID(driver, "com.VCB:id/number_account");
		sourceAccount = billTelevision.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		String getToltalMoney = billTelevision.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] toltal_money = getToltalMoney.split(" ");
		toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

		log.info("TC_01_STEP_2: kiem tra hien thị mac dinh");
		billTelevision.inputCustomerId(listViettel);

		log.info("TC_01_STEP_3: chọn phương thức xác thực");
		billTelevision.clickToTextID(driver, "com.VCB:id/tvptxt");
		billTelevision.clickToDynamicButtonLinkOrLinkText(driver, TitleData.PASSWORD);

		log.info("TC_01_STEP_4: lấy ra số tiền thanh toán ");
		getPayments = billTelevision.getMoneyByAccount(driver, TitleData.NUMBER_MONEY_PAY);
		String[] paymentsSplit = getPayments.split(" ");
		payments = Double.parseDouble(paymentsSplit[0].replace(",", ""));

		log.info("TC_01_STEP_5: lấy nhà cung cấp");
		supplier = billTelevision.getMoneyByAccount(driver, TitleData.SUPPLIER);

		log.info("TC_01_STEP_6: lấy mã khách hàng");
		userCode = billTelevision.getMoneyByAccount(driver, TitleData.CUSTOMER_CODE);

		log.info("TC_01_STEP_7: lấy ra số số phí");
		getFee = billTelevision.getMoneyByAccount(driver, TitleData.TRANSACTION_FEE);
		String[] feeSplit = getFee.split(" ");
		fee = Double.parseDouble(feeSplit[0].replace(",", ""));

		log.info("TC_01_STEP_8: chọn tiếp tục");
		billTelevision.clickToDynamicButton(driver, TitleData.NEXT);
		
		log.info("TC_01_STEP_9: điền mật khẩu");
		billTelevision.inputToDynamicInputBox(driver, pass, "Nhập mật khẩu");
		
		log.info("TC_01_STEP_10: chọn tiếp tục");
		billTelevision.clickToDynamicButton(driver, TitleData.NEXT);
		
		log.info("TC_01_STEP_11: lấy ra tên dịch vụ");
		getService = billTelevision.getMoneyByAccount(driver, TitleData.SERVICE);

		log.info("TC_03_STEP_12: lấy mã hóa đơn");
		dealCode = billTelevision.getMoneyByAccount(driver, TitleData.CODE_TRANSFER);

		log.info("TC_01_STEP_13: lấy thời gian giao dịch");
		getTimeTransfer = billTelevision.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");

		log.info("TC_01_STEP_14: chọn thực hiện giao dịch mới");
		billTelevision.clickToDynamicButton(driver, TitleData.NEW_TRANSFER);

		log.info("TC_01_STEP_15: chọn back");
		billTelevision.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_01_STEP_16: chọn back");
		billTelevision.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_02_BaoCaoThanhToanHoaDonXacThucBangMK() {
		log.info("TC_02_1: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_2: Click Bao Cao giao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_3: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_02_4: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.PAYMENT_BILLING);

		log.info("TC_02_5: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_02_6: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_02_7: Kiem tra ngay tao giao dich hien thi");
		String getReportTime = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").substring(0, 16);

		String[] timeTransfer = getTimeTransfer.split(" ");
		String dateTime = timeTransfer[3] + " " + timeTransfer[0];

		verifyEquals(getReportTime, dateTime);

		log.info("TC_02_8: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney"), ("- " + getPayments));

		log.info("TC_02_9: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_10: Kiem tra mã giao dịch");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), dealCode);

		log.info("TC_02_11: Kiem tra nhà cung cấp");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleData.SUPPLIER), supplier);

		log.info("TC_02_12: Kiem tra mã khách hàng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleData.CUSTOMER_CODE), userCode);

		log.info("TC_02_13: Kiem tra dịch vụ");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleData.SERVICE), getService);

		log.info("TC_02_14: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_02_15: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.AMOUNT_TRANSFER).contains(getPayments));

		log.info("TC_02_16: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_02_17: kiểm tra số dư");
		billTelevision.clickToTextID(driver, "com.VCB:id/tvContent");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);
		String surplus = transReport.getDynamicTextInTransactionDetail(driver, account);
		String[] surplusSplit = surplus.split(" ");
		double surplusInt = Double.parseDouble(surplusSplit[0].replace(",", ""));
		double canculateAvailable = toltalMoney - payments - fee;
		verifyEquals(surplusInt, canculateAvailable);

		log.info("TC_02_18: Click Đóng");
		transReport.clickToTextID(driver, "com.VCB:id/cancel_button");

		log.info("TC_02_19: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_20: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_03_PhuongThucThanhToanOTP(String otp) {
		log.info("TC_03_STEP_0: chọn cước truyền hình cap");
		billTelevision.clickToDynamicButtonLinkOrLinkText(driver, TitleData.TITLE_TELEVISION);

		log.info("TC_03_STEP_1: lấy ra số dư");
		billTelevision.clickToTextID(driver, "com.VCB:id/number_account");
		sourceAccount = billTelevision.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;
		String getToltalMoney = billTelevision.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] toltal_money = getToltalMoney.split(" ");
		toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

		log.info("TC_03_STEP_2: kiem tra hien thị mac dinh");
		billTelevision.inputCustomerId(listViettel);

		log.info("TC_03_STEP_3: chọn phương thức xác thực");
		billTelevision.clickToTextID(driver, "com.VCB:id/tvptxt");
		billTelevision.clickToDynamicButtonLinkOrLinkText(driver, TitleData.OTP);

		log.info("TC_03_STEP_4: lấy ra số tiền thanh toán ");
		getPayments = billTelevision.getMoneyByAccount(driver, TitleData.NUMBER_MONEY_PAY);
		String[] paymentsSplit = getPayments.split(" ");
		payments = Double.parseDouble(paymentsSplit[0].replace(",", ""));

		log.info("TC_03_STEP_5: lấy nhà cung cấp");
		supplier = billTelevision.getMoneyByAccount(driver, TitleData.SUPPLIER);

		log.info("TC_03_STEP_6: lấy mã khách hàng");
		userCode = billTelevision.getMoneyByAccount(driver, TitleData.CUSTOMER_CODE);

		log.info("TC_03_STEP_7: lấy ra số số phí");
		getFee = billTelevision.getMoneyByAccount(driver, TitleData.TRANSACTION_FEE);
		String[] feeSplit = getFee.split(" ");
		fee = Double.parseDouble(feeSplit[0].replace(",", ""));

		log.info("TC_03_STEP_8: chọn tiếp tục");
		billTelevision.clickToDynamicButton(driver, TitleData.NEXT);
		
		log.info("TC_03_STEP_9: điền otp");
		billTelevision.inputToDynamicOtp(driver, otp, TitleData.NEXT);

		log.info("TC_03_STEP_10: chọn tiếp tục");
		billTelevision.clickToDynamicButton(driver, TitleData.NEXT);
		
		log.info("TC_01_STEP_11: lấy ra tên dịch vụ");
		getService = billTelevision.getMoneyByAccount(driver, TitleData.SERVICE);

		log.info("TC_03_STEP_12: lấy mã hóa đơn");
		dealCode = billTelevision.getMoneyByAccount(driver, TitleData.CODE_TRANSFER);

		log.info("TC_03_STEP_13: lấy thời gian giao dịch");
		getTimeTransfer = billTelevision.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");

		log.info("TC_03_STEP_14: chọn thực hiện giao dịch mới");
		billTelevision.clickToDynamicButton(driver, TitleData.NEW_TRANSFER);

		log.info("TC_03_STEP_15: chọn back");
		billTelevision.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_03_STEP_16: chọn back");
		billTelevision.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_04_BaoCaoThanhToanHoaDonXacThucBangMK() {
		log.info("TC_04_1: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04_2: Click Bao Cao giao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_3: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_04_4: Chon Chuyen Tien Trong VCB");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.PAYMENT_BILLING);

		log.info("TC_04_5: Click Chon Tai Khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_04_6: Click Tim Kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_04_7: Kiem tra ngay tao giao dich hien thi");
		String getReportTime = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").substring(0, 16);

		String[] timeTransfer = getTimeTransfer.split(" ");
		String dateTime = timeTransfer[3] + " " + timeTransfer[0];

		verifyEquals(getReportTime, dateTime);

		log.info("TC_04_8: Kiem tra so tien chuyen hien thi");
		verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney"), ("- " + getPayments));

		log.info("TC_04_9: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_10: Kiem tra mã giao dịch");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), dealCode);

		log.info("TC_04_11: Kiem tra nhà cung cấp");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.SUPPLIER), supplier);

		log.info("TC_04_12: Kiem tra mã khách hàng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleData.CUSTOMER_CODE), userCode);

		log.info("TC_04_13: Kiem tra dịch vụ");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.SERVICE), getService);

		log.info("TC_04_14: Kiem tra so tai khoan trich no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_04_15: Kiem tra so tien giao dich hien thi");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.AMOUNT_TRANSFER).contains(getPayments));

		log.info("TC_04_16: Kiem tra mã khách hàng");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.AMOUNT_TRANSFER).contains(getPayments));

		log.info("TC_04_17: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_04_18: kiểm tra số dư");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);
		String surplus = transReport.getDynamicTextInTransactionDetail(driver, account);
		String[] surplusSplit = surplus.split(" ");
		double surplusInt = Double.parseDouble(surplusSplit[0].replace(",", ""));
		double canculateAvailable = toltalMoney - payments - fee;
		verifyEquals(surplusInt, canculateAvailable);

		log.info("TC_04_19: Click Đóng");
		transReport.clickToTextID(driver, "com.VCB:id/cancel_button");

		log.info("TC_04_20: Click  nut Back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_21: Click  nut Home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@AfterMethod(alwaysRun = true)
	public void afterClass() throws IOException {
		closeApp();
		service.stop();
	}

}
