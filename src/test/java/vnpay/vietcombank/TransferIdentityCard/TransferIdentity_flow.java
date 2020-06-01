package vnpay.vietcombank.TransferIdentityCard;

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
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferIdentiryPageObject;
import vietcombank_test_data.Account_Data.Valid_Account;
import vietcombank_test_data.TransferIdentity_Data.textCheckElement;
import vietcombank_test_data.TransferIdentity_Data.textDataInputForm;

public class TransferIdentity_flow extends Base {
    AppiumDriver<MobileElement> driver;
    private LogInPageObject login;
    private HomePageObject homePage;
    private TransferIdentiryPageObject trasferPage;
    private TransactionReportPageObject transReport;
    private String account;
    private String user;
    private String content;
    private String code;
    private String moneyTransfer;
    private double money_transferred;
    private String[] transferDate;
    private double toltalMoney;
    private double fee;

    @Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
    @BeforeClass
    public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt)
	    throws IOException, InterruptedException {
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
    }

    @Parameters({ "pass" })
    @Test
    public void TC_01_ChuyenTienVNDChoNguoNhanTaiQuayBangCMTXacThucBangMKNguoiChuyenTraPhi(String pass) {
	log.info("TC_01_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

	log.info("TC_01_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

	log.info("TC_01_STEP_3: lấy ra số dư");
	trasferPage.scrollUpToText(driver, textCheckElement.ACCOUNT);
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_01_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(textDataInputForm.USER_NAME, textCheckElement.BENEFICIARY_NAME);

	log.info("TC_01_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "textCheckElement.IDENTITY");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_01_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, textDataInputForm.IDENTITY_NUMBER, textCheckElement.NUMBER);

	log.info("TC_01_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.DATE);
	trasferPage.clickToDynamicButton(driver, "OK");

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

	log.info("TC_01_STEP_13: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_01_STEP_14: điền mật khẩu");
	trasferPage.inputToDynamicInputBox(driver, pass, "Nhập mật khẩu");

	log.info("TC_01_STEP_15: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_01_STEP_16: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Integer.parseInt(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_01_STEP_17: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_01_STEP_18: lấy tên người hưởng");
	user = trasferPage.getMoneyByAccount(driver, textCheckElement.BENEFICIARY_NAME);

	log.info("TC_01_STEP_20: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_01_STEP_23: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_02_STEP_22: kiểm tra số dư");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);
	String surplus = transReport.getMoneyByAccount(driver, "Số dư khả dụng");
	String[] surplusSplit = surplus.split(" ");
	long surplusInt = Long.parseLong(surplusSplit[0].replace(",", ""));
	long canculateAvailable = canculateAvailableBalances((long) toltalMoney, (long) fee, (long) money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_01_STEP_24: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_02_BaoCaoChuyenTienVNDChoNguoNhanTaiQuayBangCMTXacThucBangMK() {
	log.info("TC_02_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_02_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

	log.info("TC_02_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

	log.info("TC_02_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.TEXT_REPORT);

	log.info("TC_02_5: Click Chon Tai Khoan");
	transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_02_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

	log.info("TC_02_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

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
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), code);

	log.info("TC_02_13: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

	log.info("TC_02_14: Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(textDataInputForm.MONEY_TRANSFER_VND) + " VND"));

	log.info("TC_02_15: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_02_17: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_02_18: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

    @Parameters({ "pass" })
    @Test
    public void TC_03_ChuyenTienUSDChoNguoNhanTaiQuayBangCMTXacThucBangMKNguoiNhanTraPhi(String pass) {
	log.info("TC_03_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

	log.info("TC_03_STEP_2: chon tài khoản");

	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.USD_ACCOUNT);

	log.info("TC_03_Step_3: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(textDataInputForm.USER_NAME, textCheckElement.BENEFICIARY_NAME);

	log.info("TC_03_STEP_4: lấy ra số dư");
	trasferPage.scrollUpToText(driver, textCheckElement.ACCOUNT);
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_03_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "textCheckElement.IDENTITY");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_03_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, textDataInputForm.IDENTITY_NUMBER, textCheckElement.NUMBER);

	log.info("TC_03_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.DATE);
	trasferPage.clickToDynamicButton(driver, "OK");

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

	log.info("TC_03_STEP_12: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_03_STEP_13: điền mật khẩu");
	trasferPage.inputToDynamicInputBox(driver, pass, "Nhập mật khẩu");

	log.info("TC_03_STEP_14: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_03_STEP_15: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Double.parseDouble(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_03_STEP_16: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_03_STEP_17: lấy tên người hưởng");
	user = trasferPage.getMoneyByAccount(driver, textCheckElement.BENEFICIARY_NAME);

	log.info("TC_03_STEP_19: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_03_STEP_21: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_04_17: kiểm tra số dư");
	trasferPage.scrollUpToText(driver, textCheckElement.ACCOUNT);
	String surplus = transReport.getMoneyByAccount(driver, "Số dư khả dụng");
	String[] surplusSplit = surplus.split(" ");
	long surplusInt = Long.parseLong(surplusSplit[0].replace(",", ""));
	double canculateAvailable = canculateAvailableBalancesCurrentcy(toltalMoney, fee, money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_03_STEP_22: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_04_BaoCaoChuyenTienUSDChoNguoNhanTaiQuayBangCMTXacThucBangMKNguoiNhanTraPhi() {
	log.info("TC_04_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_04_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

	log.info("TC_04_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

	log.info("TC_04_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.TEXT_REPORT);

	log.info("TC_04_5: Click Chon Tai Khoan");
	transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_04_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.USD_ACCOUNT);

	log.info("TC_04_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

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
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), code);

	log.info("TC_04_13: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Valid_Account.USD_ACCOUNT);

	log.info("TC_04_14: Kiem tra so tien giao dich hien thi");
	String get_money_transf = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch");
	String[] getMoneyTransfer = get_money_transf.split("\\.");
	verifyEquals(getMoneyTransfer[0], textCheckElement.AMOUNT_USD);

	log.info("TC_04_16: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_04_19: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_04_20: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

    @Parameters({ "otp" })
    @Test
    public void TC_05_ChuyenTienVNDChoNguoNhanTaiQuayBangCMTXacThucBangOTPNguoiChuyenTraPhi(String otp) {
	log.info("TC_05_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

	log.info("TC_05_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

	log.info("TC_05_Step_3: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(textDataInputForm.USER_NAME, textCheckElement.BENEFICIARY_NAME);

	log.info("TC_05_STEP_4: lấy ra số dư");
	trasferPage.scrollUpToText(driver, textCheckElement.ACCOUNT);
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_05_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "textCheckElement.IDENTITY");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_05_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, textDataInputForm.IDENTITY_NUMBER, textCheckElement.NUMBER);

	log.info("TC_05_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.DATE);
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_05_Step_8: noi cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.ISSUED);
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textDataInputForm.ISSUED);

	log.info("TC_05_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, textDataInputForm.MONEY_TRANSFER_VND, textCheckElement.MONEY);
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_05_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_05_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_05_STEP_12: chon phương thức xác thực");
	trasferPage.clickToTextID("com.VCB:id/tvptxt");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.OTP_SMS);

	log.info("TC_01_STEP_20: lấy nội dung giao dịch");
	content = trasferPage.getMoneyByAccount(driver, textCheckElement.CONNTENT);

	log.info("TC_06_15: lấy ra phí giao dịch");
	String getFee = transReport.getDynamicTextInTransactionDetail(driver, textCheckElement.TRANSACTION_FEE);
	String[] feeSplit = getFee.split(" ");
	fee = Integer.parseInt(feeSplit[0].replace(",", ""));

	log.info("TC_05_STEP_13: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_05_STEP_14: điền OTP");
	trasferPage.inputToDynamicOtp(driver, otp, textCheckElement.NEXT);

	log.info("TC_05_STEP_15: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_05_STEP_16: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Integer.parseInt(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_05_STEP_17: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_05_STEP_18: lấy tên người hưởng");
	user = trasferPage.getMoneyByAccount(driver, textCheckElement.BENEFICIARY_NAME);
	
	log.info("TC_03_STEP_19: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_05_STEP_22: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_06_17: kiểm tra số dư");
	trasferPage.scrollUpToText(driver, textCheckElement.ACCOUNT);
	String surplus = transReport.getMoneyByAccount(driver, "Số dư khả dụng");
	String[] surplusSplit = surplus.split(" ");
	double surplusInt = Double.parseDouble(surplusSplit[0].replace(",", ""));
	double canculateAvailable = canculateAvailableBalancesCurrentcy(toltalMoney, fee, money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_05_STEP_23: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_06_BaoCaoChuyenTienVNDChoNguoNhanTaiQuayBangCMTXacThucBangOTPNguoiChuyenTraPhi() {
	log.info("TC_06_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_06_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

	log.info("TC_06_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

	log.info("TC_06_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.TEXT_REPORT);

	log.info("TC_06_5: Click Chon Tai Khoan");
	transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_06_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

	log.info("TC_06_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_06_8: Kiem tra ngay tao giao dich hien thi");
	String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
	verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), transferDate[3].replace("-", "/"));

	log.info("TC_06_9: Kiem tra noi dung hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), content);

	log.info("TC_06_10: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney"), ("- " + moneyTransfer));

	log.info("TC_06_11: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_06_12: Kiem tra mã giao dịch");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), code);

	log.info("TC_06_13: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

	log.info("TC_06_14: Kiem tra so tien giao dich hien thi");
	String get_money_transf = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").replace(",", "");
	String[] getMoneyTransfer = get_money_transf.split(" ");
	verifyEquals(getMoneyTransfer[0], textDataInputForm.MONEY_TRANSFER_VND);

	log.info("TC_06_16: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_06_19: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_06_20: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

    @Parameters({ "otp" })
    @Test
    public void TC_07_ChuyenTienEURChoNguoNhanTaiQuayBangCMTXacThucBangOTPNguoiNhanTraPhi(String otp) {
	log.info("TC_07_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

	log.info("TC_07_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.EUR_ACCOUNT);

	log.info("TC_07_Step_3: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(textDataInputForm.USER_NAME, textCheckElement.BENEFICIARY_NAME);

	log.info("TC_07_STEP_4: lấy ra số dư");
	trasferPage.scrollUpToText(driver, textCheckElement.ACCOUNT);
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_07_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "textCheckElement.IDENTITY");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_07_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, textDataInputForm.IDENTITY_NUMBER, textCheckElement.NUMBER);

	log.info("TC_07_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.DATE);
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_07_Step_8: noi cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.ISSUED);
	trasferPage.clickToDynamicTextIndex(driver, "0", textDataInputForm.ISSUED);

	log.info("TC_07_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, textCheckElement.AMOUNT_USD, textCheckElement.MONEY);
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_07_STEP_9: chọn người trả phí");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent3");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.RECEIVER_PAYER);

	log.info("TC_07_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_07_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_07_STEP_20: lấy nội dung giao dịch");
	content = trasferPage.getMoneyByAccount(driver, textCheckElement.CONNTENT);

	log.info("TC_08_15: lấy ra phí giao dịch");
	String getFee = transReport.getDynamicTextInTransactionDetail(driver, textCheckElement.TRANSACTION_FEE);
	String[] feeSplit = getFee.split("\\ ");
	fee = Double.parseDouble(feeSplit[0].replace(",", ""));

	log.info("TC_07_STEP_12: chon phương thức xác thực");
	trasferPage.clickToTextID("com.VCB:id/tvptxt");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.OTP_SMS);

	log.info("TC_07_STEP_13: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_07_STEP_14: điền OTP");
	trasferPage.inputToDynamicOtp(driver, otp, textCheckElement.NEXT);

	log.info("TC_07_STEP_14: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_07_STEP_15: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Double.parseDouble(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_07_STEP_16: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_07_STEP_17: lấy tên người hưởng");
	user = trasferPage.getMoneyByAccount(driver, textCheckElement.BENEFICIARY_NAME);

	log.info("TC_07_STEP_19: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_07_STEP_21: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_08_17: kiểm tra số dư");
	trasferPage.scrollUpToText(driver, textCheckElement.ACCOUNT);
	String surplus = transReport.getMoneyByAccount(driver, "Số dư khả dụng");
	String[] surplusSplit = surplus.split(" ");
	double surplusInt = Double.parseDouble(surplusSplit[0].replace(",", ""));
	double canculateAvailable = canculateAvailableBalancesCurrentcy(toltalMoney, fee, money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_07_STEP_22: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_08_BaoCaoChuyenTienEURChoNguoNhanTaiQuayBangCMTXacThucBangOTPNguoiNhanTraPhi() {
	log.info("TC_08_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_08_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

	log.info("TC_08_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

	log.info("TC_08_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.TEXT_REPORT);

	log.info("TC_08_5: Click Chon Tai Khoan");
	transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_08_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.EUR_ACCOUNT);

	log.info("TC_08_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_08_8: Kiem tra ngay tao giao dich hien thi");
	String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
	verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), transferDate[3].replace("-", "/"));

	log.info("TC_08_9: Kiem tra noi dung hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), content);

	log.info("TC_08_10: Kiem tra so tien chuyen hien thi");
	String[] moneyTransfer1 = moneyTransfer.split("\\.");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney") , ("- " + moneyTransfer1[0] + " EUR"));

	log.info("TC_08_11: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_08_12: Kiem tra mã giao dịch");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), code);

	log.info("TC_08_13: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Valid_Account.EUR_ACCOUNT);

	log.info("TC_08_14: Kiem tra so tien giao dich hien thi");
	String get_money_transf = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch");
	String[] getMoneyTransfer = get_money_transf.split("\\.");
	verifyEquals(getMoneyTransfer[0], textCheckElement.AMOUNT_USD);

	log.info("TC_08_16: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_08_19: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_08_20: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

    @Parameters({ "pass" })
    @Test
    public void TC_09_ChuyenTienVNDChoNguoNhanTaiQuayBangHCXacThucBangMKNguoiChuyenTraPhi(String pass) {
	log.info("TC_09_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

	log.info("TC_09_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

	log.info("TC_09_Step_3: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(textDataInputForm.USER_NAME, textCheckElement.BENEFICIARY_NAME);

	log.info("TC_09_STEP_4: lấy ra số dư");
	trasferPage.scrollUpToText(driver, textCheckElement.ACCOUNT);
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	long toltalMoney = Long.parseLong(toltal_money[0].replace(",", ""));

	log.info("TC_09_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "textCheckElement.IDENTITY");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.PASSPORT);

	log.info("TC_09_Step_6: so hộ chiếu");
	trasferPage.inputToDynamicInputBox(driver, textDataInputForm.IDENTITY_NUMBER, textCheckElement.NUMBER);

	log.info("TC_09_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.DATE);
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_09_Step_8: noi cap");
	trasferPage.inputToDynamicInputBox(driver, textDataInputForm.ISSUED, textCheckElement.ISSUED);

	log.info("TC_09_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, textDataInputForm.MONEY_TRANSFER_VND, textCheckElement.MONEY);
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_09_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_09_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_09_STEP_20: lấy nội dung");
	content = trasferPage.getMoneyByAccount(driver, textCheckElement.CONNTENT);

	log.info("TC_10_15: lấy ra phí giao dịch");
	String getFee = transReport.getDynamicTextInTransactionDetail(driver, textCheckElement.TRANSACTION_FEE);
	String[] feeSplit = getFee.split(" ");
	long fee = Long.parseLong(feeSplit[0].replace(",", ""));

	log.info("TC_09_STEP_12: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_09_STEP_13: điền mật khẩu");
	trasferPage.inputToDynamicInputBox(driver, pass, "Nhập mật khẩu");

	log.info("TC_09_STEP_14: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_09_STEP_15: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	long money_transferred = Long.parseLong(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_09_STEP_16: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_09_STEP_17: lấy tên người hưởng");
	user = trasferPage.getMoneyByAccount(driver, textCheckElement.BENEFICIARY_NAME);

	log.info("TC_09_STEP_19: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_09_STEP_21: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_10_17: kiểm tra số dư");
	trasferPage.scrollUpToText(driver, textCheckElement.ACCOUNT);
	String surplus = transReport.getMoneyByAccount(driver, "Số dư khả dụng");
	String[] surplusSplit = surplus.split(" ");
	double surplusInt = Double.parseDouble(surplusSplit[0].replace(",", ""));
	double canculateAvailable = canculateAvailableBalances((long) toltalMoney, (long) fee, (long) money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_09_STEP_22: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_10_BaoCaoChuyenTienVNDChoNguoNhanTaiQuayBangHCXacThucBangMKNguoiChuyenTraPhi() {
	log.info("TC_10_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_10_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

	log.info("TC_10_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

	log.info("TC_10_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.TEXT_REPORT);

	log.info("TC_10_5: Click Chon Tai Khoan");
	transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_10_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

	log.info("TC_10_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_10_8: Kiem tra ngay tao giao dich hien thi");
	String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
	verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), transferDate[3].replace("-", "/"));

	log.info("TC_10_9: Kiem tra noi dung hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), content);

	log.info("TC_10_10: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney"), ("- " + moneyTransfer));

	log.info("TC_10_11: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_10_12: Kiem tra mã giao dịch");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), code);

	log.info("TC_10_13: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

	log.info("TC_10_14: Kiem tra so tien giao dich hien thi");
	String get_money_transf = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").replace(",", "");
	String[] getMoneyTransfer = get_money_transf.split(" ");
	verifyEquals(getMoneyTransfer[0], textDataInputForm.MONEY_TRANSFER_VND);

	log.info("TC_10_16: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_10_19: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_10_20: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

    @Parameters({ "pass" })
    @Test
    public void TC_11_ChuyenTienUSDChoNguoNhanTaiQuayBangHCXacThucBangMKNguoiNhanTraPhi(String pass) {
	log.info("TC_11_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

	log.info("TC_11_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.USD_ACCOUNT);

	log.info("TC_11_Step_3: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(textDataInputForm.USER_NAME, textCheckElement.BENEFICIARY_NAME);

	log.info("TC_11_STEP_4: lấy ra số dư");
	trasferPage.scrollUpToText(driver, textCheckElement.ACCOUNT);
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_11_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "textCheckElement.IDENTITY");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.PASSPORT);

	log.info("TC_11_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, textDataInputForm.IDENTITY_NUMBER, textCheckElement.NUMBER);

	log.info("TC_11_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.DATE);
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_11_Step_8: noi cap");
	trasferPage.inputToDynamicInputBox(driver, textDataInputForm.ISSUED, textCheckElement.ISSUED);

	log.info("TC_11_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, textCheckElement.AMOUNT_USD, textCheckElement.MONEY);
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_11_STEP_9: chọn người trả phí");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent3");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.RECEIVER_PAYER);

	log.info("TC_11_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_11_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_11_STEP_20: lấy nội dung giao dịch");
	content = trasferPage.getMoneyByAccount(driver, textCheckElement.CONNTENT);

	log.info("TC_12_15: lấy ra phí giao dịch");
	String getFee = transReport.getDynamicTextInTransactionDetail(driver, textCheckElement.TRANSACTION_FEE);
	String[] feeSplit = getFee.split(" ");
	String fee = (feeSplit[0].replace(",", ""));
	double feeUSD = convertVNeseMoneyToEUROOrUSD(fee, "USD");

	log.info("TC_11_STEP_12: chon phương thức xác thực");
	trasferPage.clickToTextID("com.VCB:id/tvptxt");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

	log.info("TC_11_STEP_13: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_11_STEP_14: điền pass");
	trasferPage.inputToDynamicInputBox(driver, pass, "Nhập mật khẩu");

	log.info("TC_11_STEP_14: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_11_STEP_15: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Double.parseDouble(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_11_STEP_16: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_11_STEP_17: lấy tên người hưởng");
	user = trasferPage.getMoneyByAccount(driver, textCheckElement.BENEFICIARY_NAME);

	log.info("TC_11_STEP_19: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_11_STEP_21: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_12_17: kiểm tra số dư");
	trasferPage.scrollUpToText(driver, textCheckElement.ACCOUNT);
	String surplus = transReport.getMoneyByAccount(driver, "Số dư khả dụng");
	String[] surplusSplit = surplus.split(" ");
	double surplusInt = Double.parseDouble(surplusSplit[0].replace(",", ""));
	double canculateAvailable = canculateAvailableBalances((long) toltalMoney, (long) feeUSD, (long) money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_11_STEP_22: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_12_BaoCaoChuyenTienUSDChoNguoNhanTaiQuayBangHCXacThucBangMKNguoiNhanTraPhi() {
	log.info("TC_12_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_12_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

	log.info("TC_12_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

	log.info("TC_12_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.TEXT_REPORT);

	log.info("TC_12_5: Click Chon Tai Khoan");
	transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_12_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.USD_ACCOUNT);

	log.info("TC_12_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_12_8: Kiem tra ngay tao giao dich hien thi");
	String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
	verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), transferDate[3].replace("-", "/"));

	log.info("TC_12_9: Kiem tra noi dung hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), content);

	log.info("TC_12_10: Kiem tra so tien chuyen hien thi");
	String getMoneyTrasfer = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney");
	String[] moneyTransfer1 = moneyTransfer.split("\\.");
	verifyEquals(getMoneyTrasfer, ("- " + moneyTransfer1[0] + " USD"));

	log.info("TC_12_11: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_12_12: Kiem tra mã giao dịch");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), code);

	log.info("TC_12_13: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Valid_Account.USD_ACCOUNT);

	log.info("TC_12_14: Kiem tra so tien giao dich hien thi");
	String get_money_transf = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch");
	String[] getMoneyTransfer = get_money_transf.split("\\.");
	verifyEquals(getMoneyTransfer[0], textCheckElement.AMOUNT_USD);

	log.info("TC_12_16: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_12_19: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_12_20: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

    @Parameters({ "otp" })
    @Test
    public void TC_13_ChuyenTienVNDChoNguoNhanTaiQuayBangHCXacThucBangOTPNguoiChuyenTraPhi(String otp) {
	log.info("TC_13_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

	log.info("TC_13_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT2);

	log.info("TC_13_Step_3: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(textDataInputForm.USER_NAME, textCheckElement.BENEFICIARY_NAME);

	log.info("TC_13_STEP_4: lấy ra số dư");
	trasferPage.scrollUpToText(driver, textCheckElement.ACCOUNT);
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_13_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "textCheckElement.IDENTITY");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.PASSPORT);

	log.info("TC_13_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, textDataInputForm.IDENTITY_NUMBER, textCheckElement.NUMBER);

	log.info("TC_13_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.DATE);
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_13_Step_8: noi cap");
	trasferPage.inputToDynamicInputBox(driver, textDataInputForm.ISSUED, textCheckElement.ISSUED);

	log.info("TC_13_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, textDataInputForm.MONEY_TRANSFER_VND, textCheckElement.MONEY);
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_13_STEP_9: chọn người trả phí");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent3");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

	log.info("TC_13_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_13_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_13_STEP_20: lấy nội dung giao dịch");
	content = trasferPage.getMoneyByAccount(driver, textCheckElement.CONNTENT);

	log.info("TC_14_15: lấy ra phí giao dịch");
	String getFee = transReport.getDynamicTextInTransactionDetail(driver, textCheckElement.TRANSACTION_FEE);
	String[] feeSplit = getFee.split(" ");
	fee = Integer.parseInt(feeSplit[0].replace(",", ""));

	log.info("TC_13_STEP_12: chon phương thức xác thực");
	trasferPage.clickToTextID("com.VCB:id/tvptxt");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.OTP_SMS);

	log.info("TC_13_STEP_13: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_13_STEP_14: điền pass");
	trasferPage.inputToDynamicOtp(driver, otp, textCheckElement.NEXT);

	log.info("TC_13_STEP_14: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, textCheckElement.NEXT);

	log.info("TC_13_STEP_15: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Double.parseDouble(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_13_STEP_16: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_13_STEP_17: lấy tên người hưởng");
	user = trasferPage.getMoneyByAccount(driver, textCheckElement.BENEFICIARY_NAME);

	log.info("TC_13_STEP_19: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_13_STEP_21: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_14_17: kiểm tra số dư");
	trasferPage.scrollUpToText(driver, textCheckElement.ACCOUNT);
	String surplus = transReport.getMoneyByAccount(driver, "Số dư khả dụng");
	String[] surplusSplit = surplus.split("\\ ");
	double surplusInt = Double.parseDouble(surplusSplit[0].replace(",", ""));
	double canculateAvailable = canculateAvailableBalances((long) toltalMoney, (long) fee, (long) money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_13_STEP_22: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_14_BaoCaoChuyenTienVNDChoNguoNhanTaiQuayBangHCXacThucBangOTPNguoiChuyenTraPhi() {
	log.info("TC_14_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_14_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

	log.info("TC_14_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

	log.info("TC_14_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, textCheckElement.TEXT_REPORT);

	log.info("TC_14_5: Click Chon Tai Khoan");
	transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_14_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT2);

	log.info("TC_14_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_14_8: Kiem tra ngay tao giao dich hien thi");
	String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
	verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), transferDate[3].replace("-", "/"));

	log.info("TC_14_9: Kiem tra noi dung hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), content);

	log.info("TC_14_10: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney"), ("- " + moneyTransfer));

	log.info("TC_14_11: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_14_12: Kiem tra mã giao dịch");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), code);

	log.info("TC_14_13: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Valid_Account.ACCOUNT2);

	log.info("TC_14_14: Kiem tra so tien giao dich hien thi");
	String get_money_transf = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").replace(",", "");
	String[] getMoneyTransfer = get_money_transf.split(" ");
	verifyEquals(getMoneyTransfer[0], textDataInputForm.MONEY_TRANSFER_VND);

	log.info("TC_14_16: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_14_19: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_14_20: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
	closeApp();
	service.stop();
    }

}
