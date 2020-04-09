package vnpay.vietcombank.check_online;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.CheckOnlineObject;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferIdentiryPageObject;
import vietcombank_test_data.Account_Data.Valid_Account;
import vietcombank_test_data.TransferIdentity_Data;

public class checkOnline_flow extends Base {
    AppiumDriver<MobileElement> driver;
    private LogInPageObject login;
    private HomePageObject homePage;
    private TransferIdentiryPageObject trasferPage;
    private CheckOnlineObject checkOnline;
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
	driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
	login = PageFactoryManager.getLoginPageObject(driver);
	login.Global_login(phone, pass, opt);

	homePage = PageFactoryManager.getHomePageObject(driver);
	trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
	checkOnline = PageFactoryManager.getCheckOnlineObject(driver);
    }

    @Parameters({ "pass" })
    @Test
    public void TC_01_ChuyenTienVNDChoNguoNhanTaiQuayBangCMTXacThucBangMKNguoiChuyenTraPhi(String pass) {
	log.info("TC_01_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

	log.info("TC_01_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.DEFAULT_ACCOUNT3);

	log.info("TC_01_STEP_3: lấy ra số dư");
	trasferPage.scrollUpToText(driver, "Tài khoản nguồn");
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Integer.parseInt(toltal_money[0].replace(",", ""));

	log.info("TC_01_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");

	log.info("TC_01_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_01_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_01_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_01_Step_8: noi cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
	trasferPage.clickToDynamicTextIndex(driver, "0", TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_01_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_01_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_01_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_01_STEP_12: lấy nội dung giao dịch");
	content = trasferPage.getMoneyByAccount(driver, "Nội dung");

	log.info("TC_01_STEP_21: lấy ra phí giao dịch");
	String getFee = trasferPage.getMoneyByAccount(driver, "Số tiền phí");
	String[] feeSplit = getFee.split(" ");
	fee = Integer.parseInt(feeSplit[0].replace(",", ""));

	log.info("TC_01_STEP_13: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_01_STEP_14: điền mật khẩu");
	trasferPage.inputToDynamicInputBox(driver, pass, "Nhập mật khẩu");

	log.info("TC_01_STEP_15: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_01_STEP_16: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Integer.parseInt(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_01_STEP_17: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_01_STEP_20: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, "Mã giao dịch");

	log.info("TC_01_STEP_23: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_01_STEP_24: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

//    @Test
//    public void TC_02_BaoCaoChuyenTienVNDChoNguoNhanTaiQuayBangCMTXacThucBangMK() {
//	log.info("TC_02_1: Click vao More Icon");
//	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");
//
//	log.info("TC_02_2: Click Bao Cao giao Dich");
//	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
//	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");
//
//	log.info("TC_02_3: Click Tat Ca Cac Loai Giao Dich");
//	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");
//
//	log.info("TC_02_4: Chon Chuyen Tien Trong VCB");
//	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền cho người nhận tại quầy");
//
//	log.info("TC_02_5: Click Chon Tai Khoan");
//	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");
//
//	log.info("TC_02_6: Chon tai Khoan chuyen");
//	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);
//
//	log.info("TC_02_7: Click Tim Kiem");
//	transReport.clickToDynamicButton(driver, "Tìm kiếm");
//
//	log.info("TC_02_8: Kiem tra ngay tao giao dich hien thi");
//	String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
//	verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), transferDate[3].replace("-", "/"));
//
//	log.info("TC_02_9: Kiem tra noi dung hien thi");
//	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), content);
//
//	log.info("TC_02_10: Kiem tra so tien chuyen hien thi");
//	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney"), ("- " + moneyTransfer));
//
//	log.info("TC_02_11: Click vao giao dich");
//	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
//
//	log.info("TC_02_12: Kiem tra mã giao dịch");
//	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), code);
//
//	log.info("TC_02_13: Kiem tra so tai khoan trich no");
//	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);
//
//	log.info("TC_02_14: Kiem tra so tien giao dich hien thi");
//	verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND) + " VND"));
//
//	log.info("TC_02_15: Click  nut Back");
//	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");
//
//	log.info("TC_02_17: Click  nut Back");
//	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
//
//	log.info("TC_02_18: Click  nut Home");
//	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
//    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
//	closeApp();
//	service.stop();
    }

}
