package vnpay.vietcombank.lucky_gift;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.LuckyGiftPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.LuckyGift_Data;
import vietcombank_test_data.LuckyGift_Data.TitleLuckyGift;

public class LuckyGift extends Base {
	AppiumDriver<MobileElement> driver;
    private LogInPageObject login;
    private LuckyGiftPageObject luckyGift;
    private HomePageObject homePage;
    private TransactionReportPageObject transReport;
    private long amountStart;
    private long amountTranfer;
    private String amountStartString;
    private String source_account;
    private String beneficiary_account;
    private String money;
    private String transferTime;
    private String wishes;
    private Date date;
    private String surplusString;

    @Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
    @BeforeClass
    public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt)
	    throws IOException, InterruptedException {
	startServer();
	log.info("Before class: Mo app ");
	driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
	login = PageFactoryManager.getLoginPageObject(driver);
	login.Global_login(phone, pass, opt);
	luckyGift = PageFactoryManager.getLuckyGiftPageObject(driver);
	homePage = PageFactoryManager.getHomePageObject(driver);

    }

    @Test
    public void TC_01_NGuoiNhanTrongVCBSoTaiKhoanXacThucBangMatKhau() {
	log.info("TC_01_Step_1: Chọn quà tặng may mắn");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

	log.info("TC_01_Step_2: chọn tài khoản nguồn");
	luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCOUNT_FORM);

	String moneySurplus = luckyGift.getTextInDynamicPopup(driver, "com.VCB:id/available_balance");
	String[] moneySurplusSplit = moneySurplus.split(" ");
	String moneySurplusReplace = moneySurplusSplit[0].replace(',', ' ');
	int surplus = Integer.parseInt(moneySurplusReplace);

	log.info("TC_01_Step5: Thêm người nhận");
	luckyGift.clickToDynamicImageViewByID("com.VCB:id/ivAdd");

	log.info("TC_01_Step5: chọn tiếp tục");
	luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	log.info("TC_01_Step_6: nhập số tài khoản");
	luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.ACCOUNT_ACCEPT_IN_VCB, TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

	log.info("TC_01_Step_7: Nhap so tien chuyen");
	luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);
	int surplusTotal = surplus - Integer.parseInt(LuckyGift_Data.LuckyGift.MONEY);
	surplusString = String.valueOf(surplusTotal);

	log.info("TC_01_Step_8: Chon loi chuc");
	luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/more_content");
	luckyGift.clickToTextID(driver, "com.VCB:id/title");

	log.info("TC_01_Step_9: Click tiep tuc popup");
	luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_01_Step_10: Click tiep tuc popup");
	luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_01_Step_11: lấy ra tài khoản nguồn");
	source_account = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvAccSource");

	log.info("TC_01_Step_12: lấy ra tài khoản thụ hưởng");
	String user = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvReceiver");
	String[] userSplit = user.split("/");
	beneficiary_account = userSplit[0];

	log.info("TC_01_Step_13: lấy ra lời chúc");
	wishes = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvLoichuc");

	log.info("TC_01_Step_14: lấy ra số tiền chuyển");
	money = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvSumAmount");

	log.info("TC_01_Step_15: Click tiep tuc popup");
	luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_01_Step_16: điền mât khẩu");
	luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.PASS, "com.VCB:id/pin");

	log.info("TC_01_Step_17: Click tiep tuc popup");
	luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_01_Step_17: Lấy thời gian thực hiện giao dịch");
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	date = new Date();
    }

    @Test
    public void TC_02_KiemTraChiTietGiaoDichChuyenTienNgayCoPhiGiaoDichNguoiChuyenTraVNDVaXacThucBangOTP() {
	log.info("TC_02_Step_01 : Click home");
	luckyGift.clickToDynamicBottomMenu(driver, "com.VCB:id/go_home");

	log.info("TC_02_Step_02: Click vao More Icon");
	homePage = PageFactoryManager.getHomePageObject(driver);
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_02_Step_03: Click Bao Cao Dao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TRANSFER_REPORT);

	log.info("TC_02_Step_04: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TITLE_TRANSFER);

	log.info("TC_02_Step_05: Chon quà tặng may mắn");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

	log.info("TC_02_Step_05: click chọn tài khoản");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_02_Step_05: kiểm tra số dư");
	String moneyTransfer = transReport.getDynamicTextInTransactionDetail(driver, LuckyGift_Data.LuckyGift.ACCOUNT_FORM);
	String[] moneyTransferSplit = moneyTransfer.split(" ");
	String moneyTransferReplace = moneyTransferSplit[0].replace(',', ' ');
	verifyEquals(moneyTransferReplace, surplusString);

	log.info("TC_02_Step_08: CLick Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_02_Step_11: Kiem tra ngày giao dịch");
	String dateSuccess = transReport.getDynamicTextDetailByID(driver, "com.VCB:id/tvDate");
	verifyEquals(dateSuccess, date);

	log.info("TC_02_Step_11: Kiem tra noi dung hien thi");
	String content = transReport.getDynamicTextDetailByID(driver, "com.VCB:id/tvContent");
	verifyEquals(content, wishes);

	log.info("TC_02_Step_12: Kiem tra số tiền chuyển đi");
	String getMoney = transReport.getDynamicTextDetailByID(driver, "com.VCB:id/tvMoney");
	String[] moneySplit = getMoney.split(" ");
	verifyEquals(moneySplit[1] + " " + moneySplit[2], money);

	log.info("TC_02_Step_13: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_02_Step_11: Kiem tra ngày giao dịch");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch"), date);

	log.info("TC_02_Step_11: Kiem tra tài khoản nguồn");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), source_account);

	log.info("TC_02_Step_11: Kiem tra tài khoản thụ hưởng");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), beneficiary_account);

	log.info("TC_02_Step_35: Click quay lai");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	transReport.clickToDynamicButtonLinkOrLinkText(driver, "com.VCB:id/tvSelectAcc");
//	log.info("TC_02_Step_36: Click quay lai");
//	transferInVCB.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");
//
//	log.info("TC_02_Step_37: Click quay lai");
//	transferInVCB.navigateBack(driver);

    }

    @AfterMethod(alwaysRun = true)
    public void afterClass() {
//		closeApp();
	// service.stop();
    }

}
