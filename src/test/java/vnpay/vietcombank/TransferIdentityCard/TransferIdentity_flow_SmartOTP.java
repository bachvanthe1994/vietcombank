package vnpay.vietcombank.TransferIdentityCard;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.testng.annotations.AfterClass;
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
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferIdentiryPageObject;
import vietcombank_test_data.TransactionReport_Data;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;
import vietcombank_test_data.TransferIdentity_Data.textCheckElement;
import vietcombank_test_data.TransferIdentity_Data.textDataInputForm;


public class TransferIdentity_flow_SmartOTP extends Base {
    AppiumDriver<MobileElement> driver;
    private LogInPageObject login;
    private HomePageObject homePage;
    private TransferIdentiryPageObject trasferPage;
    private TransactionReportPageObject transReport;
    private SettingVCBSmartOTPPageObject smartOTP;
    private String content;
    private String code;
    private String moneyTransfer;
    private double money_transferred;
    private String[] transferDate;
    private double toltalMoney;
    private double fee;
    String account;
    String passSmartOTP  = "111222";
    SourceAccountModel sourceAccount = new SourceAccountModel();

    @Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
    @BeforeClass
    public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt)
	    throws IOException, InterruptedException, GeneralSecurityException {
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
	trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
	smartOTP.setupSmartOTP(passSmartOTP, getDataInCell(6));
    }

    @Test
    public void TC_01_ChuyenTienVNDChoNguoNhanTaiQuayBangCMTXacThucBangMKNguoiChuyenTraPhi() throws GeneralSecurityException, IOException {
	log.info("TC_01_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, textCheckElement.TRANSFER_MONEY);

	log.info("TC_01_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	sourceAccount = trasferPage.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
	account = sourceAccount.account;

	log.info("TC_01_STEP_3: lấy ra số dư");
	trasferPage.scrollUpToText(driver, textCheckElement.ACCOUNT);
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_01_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(textDataInputForm.USER_NAME, textCheckElement.BENEFICIARY_NAME);

	log.info("TC_01_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.IDENTITY);
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.IDENTITY_CARD);

	log.info("TC_01_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, textDataInputForm.IDENTITY_NUMBER, textCheckElement.NUMBER);

	log.info("TC_01_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.DATE);
	trasferPage.clickToDynamicButton(driver, textCheckElement.OK);

	log.info("TC_01_Step_8: noi cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.ISSUED);
	trasferPage.clickToDynamicTextIndex(driver, "0", textDataInputForm.ISSUED);

	log.info("TC_01_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, textDataInputForm.MONEY_TRANSFER_VND, textCheckElement.MONEY);
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_01_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_01_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_01_STEP_12: lấy nội dung giao dịch");
	content = trasferPage.getMoneyByAccount(driver, textCheckElement.CONNTENT);

	log.info("TC_01_STEP_21: lấy ra phí giao dịch");
	String getFee = transReport.getMoneyByAccount(driver, textCheckElement.TRANSACTION_FEE);
	String[] feeSplit = getFee.split(" ");
	fee = Integer.parseInt(feeSplit[0].replace(",", ""));
	
	log.info("TC_13_STEP_12: chon phương thức xác thực");
	trasferPage.clickToTextID("com.VCB:id/tvptxt");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.SMART_OTP);

	log.info("TC_01_STEP_13: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_01_STEP_14: điền mật khẩu");
	trasferPage.inputToDynamicSmartOTP(driver, passSmartOTP, "com.VCB:id/otp");

	log.info("TC_01_STEP_15: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);
	
	log.info("TC_01_STEP_15: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_01_STEP_16: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Integer.parseInt(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_01_STEP_17: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_01_STEP_20: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_01_STEP_23: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEW_TRANSFER);

	log.info("TC_02_STEP_22: kiểm tra số dư");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, account);
	String surplus = transReport.getMoneyByAccount(driver, textCheckElement.SURPLUS);
	String[] surplusSplit = surplus.split(" ");
	long surplusInt = Long.parseLong(surplusSplit[0].replace(",", ""));
	long canculateAvailable = canculateAvailableBalances((long) toltalMoney, (long) fee, (long) money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_01_STEP_24: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_02_BaoCaoChuyenTienVNDChoNguoNhanTaiQuayBangCMTXacThucBangMK() throws GeneralSecurityException, IOException {
	log.info("TC_02_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_02_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

	log.info("TC_02_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.ALL_TYPE_TRANSACTION);

	log.info("TC_02_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.CASH_TRANS);

	log.info("TC_02_5: Click Chon Tai Khoan");
	transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_02_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

	log.info("TC_02_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, TransactionReport_Data.ReportTitle.SEARCH_BUTTON);

	log.info("TC_02_8: Kiem tra ngay tao giao dich hien thi");
	String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
	verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), transferDate[3].replace("-", "/"));

	log.info("TC_02_9: Kiem tra noi dung hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), content);

	log.info("TC_02_10: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney"), ("- " + moneyTransfer));

	log.info("TC_02_11: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_02_12: Kiem tra mã giao dịch");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), code);

	log.info("TC_02_13: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), account);

	log.info("TC_02_14: Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY).contains(addCommasToLong(textDataInputForm.MONEY_TRANSFER_VND) + " VND"));

	log.info("TC_02_15: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

	log.info("TC_02_17: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

	log.info("TC_02_18: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

    @Parameters({ "pass" })
    @Test
    public void TC_03_ChuyenTienUSDChoNguoNhanTaiQuayBangCMTXacThucBangMKNguoiNhanTraPhi(String pass) throws GeneralSecurityException, IOException {
	log.info("TC_03_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, textCheckElement.TRANSFER_MONEY);

	log.info("TC_03_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	sourceAccount = trasferPage.chooseSourceAccount(driver, Constants.MONEY_CHECK_USD, "USD");
	account = sourceAccount.account;

	log.info("TC_03_Step_3: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(textDataInputForm.USER_NAME, textCheckElement.BENEFICIARY_NAME);

	log.info("TC_03_STEP_4: lấy ra số dư");
	trasferPage.scrollUpToText(driver, textCheckElement.ACCOUNT);
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_03_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.IDENTITY);
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.IDENTITY_CARD);

	log.info("TC_03_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, textDataInputForm.IDENTITY_NUMBER, textCheckElement.NUMBER);

	log.info("TC_03_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.DATE);
	trasferPage.clickToDynamicButton(driver, textCheckElement.OK);

	log.info("TC_03_Step_8: noi cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.ISSUED);
	trasferPage.clickToDynamicTextIndex(driver, "0", textDataInputForm.ISSUED);

	log.info("TC_03_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, textCheckElement.AMOUNT_USD, textCheckElement.MONEY);
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_03_STEP_9: chọn người trả phí");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent3");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.RECEIVER_PAYER);

	log.info("TC_03_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_03_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_03_STEP_20: lấy nội dung giao dịch");
	content = trasferPage.getMoneyByAccount(driver, textCheckElement.CONNTENT);

	log.info("TC_04_15: lấy ra phí giao dịch");
	String getFee = transReport.getDynamicTextInTransactionDetail(driver, textCheckElement.TRANSACTION_FEE);
	String[] feeSplit = getFee.split(" ");
	fee = Integer.parseInt(feeSplit[0].replace(",", ""));
	
	log.info("TC_13_STEP_12: chon phương thức xác thực");
	trasferPage.clickToTextID("com.VCB:id/tvptxt");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.SMART_OTP);

	log.info("TC_03_STEP_12: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);
	
	log.info("TC_01_STEP_14: điền mật khẩu");
	trasferPage.inputToDynamicSmartOTP(driver, passSmartOTP, "com.VCB:id/otp");

	log.info("TC_01_STEP_15: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);
	
	log.info("TC_01_STEP_15: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_03_STEP_15: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Double.parseDouble(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_03_STEP_16: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");
	
	log.info("TC_03_STEP_19: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_03_STEP_21: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEW_TRANSFER);

	log.info("TC_04_17: kiểm tra số dư");
	trasferPage.scrollUpToText(driver, textCheckElement.ACCOUNT);
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, account);
	String surplus = transReport.getMoneyByAccount(driver, textCheckElement.SURPLUS);
	String[] surplusSplit = surplus.split(" ");
	double surplusInt = Double.parseDouble(surplusSplit[0].replace(",", ""));
	double canculateAvailable = canculateAvailableBalancesCurrentcy(toltalMoney, fee, money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_03_STEP_22: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_04_BaoCaoChuyenTienUSDChoNguoNhanTaiQuayBangCMTXacThucBangMKNguoiNhanTraPhi() throws GeneralSecurityException, IOException {
	log.info("TC_04_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_04_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

	log.info("TC_04_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.ALL_TYPE_TRANSACTION);

	log.info("TC_04_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.CASH_TRANS);

	log.info("TC_04_5: Click Chon Tai Khoan");
	transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_04_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

	log.info("TC_04_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, TransactionReport_Data.ReportTitle.SEARCH_BUTTON);

	log.info("TC_04_8: Kiem tra ngay tao giao dich hien thi");
	String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
	verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), transferDate[3].replace("-", "/"));

	log.info("TC_04_9: Kiem tra noi dung hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), content);

	log.info("TC_04_10: Kiem tra so tien chuyen hien thi");
	String getMoney  = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney");
	String[] checkMoneyTransfe = getMoney.split(" ");
	verifyEquals("- " + checkMoneyTransfe[1] + ".00 USD", ("- " + moneyTransfer));

	log.info("TC_04_11: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_04_12: Kiem tra mã giao dịch");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), code);

	log.info("TC_04_13: Kiem tra so tai khoan trich no");
	trasferPage.scrollUpToText(driver, ReportTitle.ACCOUNT_CARD);
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), account);

	log.info("TC_04_14: Kiem tra so tien giao dich hien thi");
	String get_money_transf = transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY);
	String[] getMoneyTransfer = get_money_transf.split("\\.");
	verifyEquals(getMoneyTransfer[0], textCheckElement.AMOUNT_USD);

	log.info("TC_04_16: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

	log.info("TC_04_19: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

	log.info("TC_04_20: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

    @Test
    public void TC_05_ChuyenTienEURChoNguoNhanTaiQuayBangCMTXacThucBangOTPNguoiNhanTraPhi() throws GeneralSecurityException, IOException {
	log.info("TC_05_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, textCheckElement.TRANSFER_MONEY);

	log.info("TC_05_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	sourceAccount = trasferPage.chooseSourceAccount(driver, Constants.MONEY_CHECK_EUR, "EUR");
	account = sourceAccount.account;

	log.info("TC_05_Step_3: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(textDataInputForm.USER_NAME, textCheckElement.BENEFICIARY_NAME);

	log.info("TC_05_STEP_4: lấy ra số dư");
	trasferPage.scrollUpToText(driver, textCheckElement.ACCOUNT);
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_05_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.IDENTITY);
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.IDENTITY_CARD);

	log.info("TC_05_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, textDataInputForm.IDENTITY_NUMBER, textCheckElement.NUMBER);

	log.info("TC_05_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.DATE);
	trasferPage.clickToDynamicButton(driver, textCheckElement.OK);

	log.info("TC_05_Step_8: noi cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.ISSUED);
	trasferPage.clickToDynamicTextIndex(driver, "0", textDataInputForm.ISSUED);

	log.info("TC_05_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, textCheckElement.AMOUNT_USD, textCheckElement.MONEY);
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_05_STEP_9: chọn người trả phí");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent3");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.RECEIVER_PAYER);

	log.info("TC_05_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_05_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_05_STEP_20: lấy nội dung giao dịch");
	content = trasferPage.getMoneyByAccount(driver, textCheckElement.CONNTENT);

	log.info("TC_06_15: lấy ra phí giao dịch");
	String getFee = transReport.getDynamicTextInTransactionDetail(driver, textCheckElement.TRANSACTION_FEE);
	String[] feeSplit = getFee.split("\\ ");
	fee = Double.parseDouble(feeSplit[0].replace(",", ""));

	log.info("TC_05_STEP_12: chon phương thức xác thực");
	trasferPage.clickToTextID("com.VCB:id/tvptxt");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.SMART_OTP);

	log.info("TC_05_STEP_13: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_01_STEP_14: điền mật khẩu");
	trasferPage.inputToDynamicSmartOTP(driver, passSmartOTP, "com.VCB:id/otp");

	log.info("TC_01_STEP_15: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_05_STEP_14: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_05_STEP_15: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Double.parseDouble(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_05_STEP_16: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_05_STEP_19: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_05_STEP_21: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEW_TRANSFER);

	log.info("TC_06_17: kiểm tra số dư");
	trasferPage.scrollUpToText(driver, textCheckElement.ACCOUNT);
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, account);
	String surplus = transReport.getMoneyByAccount(driver, textCheckElement.SURPLUS);
	String[] surplusSplit = surplus.split(" ");
	double surplusInt = Double.parseDouble(surplusSplit[0].replace(",", ""));
	double canculateAvailable = canculateAvailableBalancesCurrentcy(toltalMoney, fee, money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_05_STEP_22: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_06_BaoCaoChuyenTienEURChoNguoNhanTaiQuayBangCMTXacThucBangOTPNguoiNhanTraPhi() throws GeneralSecurityException, IOException {
	log.info("TC_06_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_06_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

	log.info("TC_06_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.ALL_TYPE_TRANSACTION);

	log.info("TC_06_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.CASH_TRANS);

	log.info("TC_06_5: Click Chon Tai Khoan");
	transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_06_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

	log.info("TC_06_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, TransactionReport_Data.ReportTitle.SEARCH_BUTTON);

	log.info("TC_06_8: Kiem tra ngay tao giao dich hien thi");
	String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
	verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), transferDate[3].replace("-", "/"));

	log.info("TC_06_9: Kiem tra noi dung hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), content);

	log.info("TC_06_10: Kiem tra so tien chuyen hien thi");
	String[] moneyTransfer1 = moneyTransfer.split("\\.");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney") , ("- " + moneyTransfer1[0] + " EUR"));

	log.info("TC_06_11: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_06_12: Kiem tra mã giao dịch");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_NUMBER), code);

	log.info("TC_06_13: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), account);

	log.info("TC_06_14: Kiem tra so tien giao dich hien thi");
	String get_money_transf = transReport.getDynamicTextInTransactionDetail(driver, TransactionReport_Data.ReportTitle.TRANSACTION_MONEY);
	String[] getMoneyTransfer = get_money_transf.split("\\.");
	verifyEquals(getMoneyTransfer[0], textCheckElement.AMOUNT_USD);

	log.info("TC_06_16: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_DETAIL);

	log.info("TC_06_19: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

	log.info("TC_06_20: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

   
    @AfterClass(alwaysRun = true)
    public void afterClass() {
//	closeApp();
//	service.stop();
    }

}
