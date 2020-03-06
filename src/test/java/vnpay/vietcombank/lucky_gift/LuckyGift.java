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
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
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
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

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
	String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
	int surplus = Integer.parseInt(moneySurplusReplace);

	log.info("TC_01_Step_3: Thêm người nhận");
	luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

	log.info("TC_01_Step_4: chọn tiếp tục");
	luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	log.info("TC_01_Step_5: nhập số tài khoản");
	luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.ACCOUNT_ACCEPT_IN_VCB, TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

	log.info("TC_01_Step_6: Nhap so tien chuyen");
	luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

	log.info("TC_01_Step_7: Chon loi chuc");
	luckyGift.clickToDynamicWishes(driver, "Nhập/chọn lời chúc");
	luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
	luckyGift.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/content_counter");

	log.info("TC_01_Step_8: Click tiep tuc popup");
	luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_01_Step_9: lấy ra tài khoản nguồn");
	source_account = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvAccSource");

	log.info("TC_01_Step_10: lấy ra tài khoản thụ hưởng");
	String user = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvReceiver");
	String[] userSplit = user.split("/");
	beneficiary_account = userSplit[0];

	log.info("TC_01_Step_11: lấy ra lời chúc");
	wishes = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvLoichuc");

	log.info("TC_01_Step_12: lấy ra số tiền chuyển");
	money = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvSumAmount");

	log.info("TC_01_Step_13: lấy ra phí chuyển");
	String moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
	String[] sumFee = moneyFee.split(" ");
	int sumFeeInt = Integer.parseInt(sumFee[0]);
	int surplusTotal = surplus - Integer.parseInt(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
	surplusString = String.valueOf(surplusTotal);

	log.info("TC_01_Step_14: Click tiep tuc popup");
	luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_01_Step_15: điền mât khẩu");
	luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.PASS, "com.VCB:id/pin");

	log.info("TC_01_Step_16: Click tiep tuc popup");
	luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_01_Step_17: Click giao dịch mới");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Giao dịch mới");

	log.info("TC_01_Step_18: chọn tai khoản");
	luckyGift.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/number_account");

	log.info("TC_01_Step_19: kiểm tra số dư");
	String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, LuckyGift_Data.LuckyGift.ACCOUNT_FORM);
	String[] moneyTransferSplit = moneyTransfer.split(" ");
	String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
	verifyEquals(moneyTransferReplace, surplusString);

	log.info("TC_07_Step_21: chọn đóng");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

	log.info("TC_01_Step_21: Lấy thời gian thực hiện giao dịch");
	date = new Date();

	log.info("TC_01_Step_22 : Click home");
	luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");

    }

    @Test
    public void TC_02_KiemTraChiTietGiaoDichChuyenTienQuaTangMayManTrongVCBVaXacThucBangMK() {
	log.info("TC_02_Step_02: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_02_Step_03: Click Bao Cao Dao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TRANSFER_REPORT);

	log.info("TC_02_Step_04: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TITLE_TRANSFER);

	log.info("TC_02_Step_05: Chon quà tặng may mắn");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

	log.info("TC_02_Step_06: click chọn tài khoản");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_02_Step_07: chọn đóng");
	transReport.clickToTextID(driver, "com.VCB:id/cancel_button");

	log.info("TC_02_Step_08: CLick Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_02_Step_9: Kiem tra ngày giao dịch");
	String dateSuccess = transReport.getDynamicTextDetailByID(driver, "com.VCB:id/tvDate");
	String[] dateSplit = dateSuccess.split(" ");
	verifyEquals(dateSplit[0], formatter.format(date));

	log.info("TC_02_Step_10: Kiem tra noi dung hien thi");
	String content = transReport.getDynamicTextDetailByID(driver, "com.VCB:id/tvContent");
	verifyEquals(content, wishes.trim());

	log.info("TC_02_Step_11: Kiem tra số tiền chuyển đi");
	String getMoney = transReport.getDynamicTextDetailByID(driver, "com.VCB:id/tvMoney");
	String[] moneySplit = getMoney.split(" ");
	verifyEquals(moneySplit[1] + " " + moneySplit[2], money);

	log.info("TC_02_Step_12: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_02_Step_13: Kiem tra ngày giao dịch");
	String getDate = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
	String[] dateDeal = getDate.split(" ");
	verifyEquals(dateDeal[0], formatter.format(date));

	log.info("TC_02_Step_14: Kiem tra tài khoản nguồn");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), source_account);

	log.info("TC_02_Step_15: Kiem tra tài khoản thụ hưởng");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), beneficiary_account);

	log.info("TC_02_Step_16: Click quay lai");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_02_Step_17: Click quay lai");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_02_Step_18: Click quay lai");
	transReport.navigateBack(driver);

	log.info("TC_02_Step_20: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");
    }

    @Test
    public void TC_03_NGuoiNhanTrongVCBSoTaiKhoanXacThucBangOTP() {
	log.info("TC_03_Step_1: Chọn quà tặng may mắn");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

	log.info("TC_03_Step_2: chọn tài khoản nguồn");
	luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCOUNT_FORM);

	String moneySurplus = luckyGift.getTextInDynamicPopup(driver, "com.VCB:id/available_balance");
	String[] moneySurplusSplit = moneySurplus.split(" ");
	String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
	int surplus = Integer.parseInt(moneySurplusReplace);

	log.info("TC_03_Step_3: Thêm người nhận");
	luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

	log.info("TC_03_Step_4: chọn tiếp tục");
	luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

	log.info("TC_03_Step_5: nhập số tài khoản");
	luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.ACCOUNT_ACCEPT_IN_VCB, TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

	log.info("TC_03_Step_6: Nhap so tien chuyen");
	luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

	log.info("TC_03_Step_7: Chon loi chuc");
	luckyGift.clickToDynamicWishes(driver, "Nhập/chọn lời chúc");
	luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
	luckyGift.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/content_counter");

	log.info("TC_03_Step_8: Click tiep tuc popup");
	luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_03_Step_9: lấy ra tài khoản nguồn");
	source_account = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvAccSource");

	log.info("TC_03_Step_10: lấy ra tài khoản thụ hưởng");
	String user = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvReceiver");
	String[] userSplit = user.split("/");
	beneficiary_account = userSplit[0];

	log.info("TC_03_Step_11: lấy ra lời chúc");
	wishes = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvLoichuc");

	log.info("TC_03_Step_12: lấy ra số tiền chuyển");
	money = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvSumAmount");

	log.info("TC_03_Step_13: lấy ra phí chuyển");
	String moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
	String[] sumFee = moneyFee.split(" ");
	int sumFeeInt = Integer.parseInt(sumFee[0]);
	int surplusTotal = surplus - Integer.parseInt(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
	surplusString = String.valueOf(surplusTotal);

	log.info("TC_03_Step_14: chọn phương thức xác thực OTP");
	luckyGift.clickToTextID("com.VCB:id/tvptxt");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

	log.info("TC_03_Step_15: Click tiep tuc popup");
	luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_03_Step_16: điền OTP");
	luckyGift.inputToDynamicOtp(driver, LuckyGift_Data.LuckyGift.OTP, "Tiếp tục");

	log.info("TC_03_Step_17: Click tiep tuc popup");
	luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_03_Step_18: Click giao dịch mới");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Giao dịch mới");

	log.info("TC_03_Step_19: chọn tai khoản");
	luckyGift.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/number_account");

	log.info("TC_03_Step_20: kiểm tra số dư");
	String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, LuckyGift_Data.LuckyGift.ACCOUNT_FORM);
	String[] moneyTransferSplit = moneyTransfer.split(" ");
	String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
	verifyEquals(moneyTransferReplace, surplusString);

	log.info("TC_03_Step_21: chọn đóng");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

	log.info("TC_03_Step_22: Lấy thời gian thực hiện giao dịch");
	date = new Date();
    }

    @Test
    public void TC_04_KiemTraChiTietGiaoDichChuyenTienQuaTangMayManTrongVCBVaXacThucBangOTP() {
	log.info("TC_04_Step_01 : Click home");
	luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");

	log.info("TC_04_Step_02: Click vao More Icon");
	homePage = PageFactoryManager.getHomePageObject(driver);
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_04_Step_03: Click Bao Cao Dao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TRANSFER_REPORT);

	log.info("TC_04_Step_04: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TITLE_TRANSFER);

	log.info("TC_04_Step_05: Chon quà tặng may mắn");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

	log.info("TC_04_Step_06: click chọn tài khoản");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_04_Step_07: chọn đóng");
	transReport.clickToTextID(driver, "com.VCB:id/cancel_button");

	log.info("TC_04_Step_08: CLick Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_04_Step_9: Kiem tra ngày giao dịch");
	String dateSuccess = transReport.getDynamicTextDetailByID(driver, "com.VCB:id/tvDate");
	String[] dateSplit = dateSuccess.split(" ");
	verifyEquals(dateSplit[0], formatter.format(date));

	log.info("TC_04_Step_10: Kiem tra noi dung hien thi");
	String content = transReport.getDynamicTextDetailByID(driver, "com.VCB:id/tvContent");
	verifyEquals(content, wishes.trim());

	log.info("TC_04_Step_11: Kiem tra số tiền chuyển đi");
	String getMoney = transReport.getDynamicTextDetailByID(driver, "com.VCB:id/tvMoney");
	String[] moneySplit = getMoney.split(" ");
	verifyEquals(moneySplit[1] + " " + moneySplit[2], money);

	log.info("TC_04_Step_12: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_04_Step_13: Kiem tra ngày giao dịch");
	String getDate = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
	String[] dateDeal = getDate.split(" ");
	verifyEquals(dateDeal[0], formatter.format(date));

	log.info("TC_04_Step_14: Kiem tra tài khoản nguồn");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), source_account);

	log.info("TC_04_Step_15: Kiem tra tài khoản thụ hưởng");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), beneficiary_account);

	log.info("TC_04_Step_16: Click quay lai");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_04_Step_17: Click quay lai");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_04_Step_18: Click quay lai");
	transReport.navigateBack(driver);

	log.info("TC_02_Step_19: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

    }

    @Test
    public void TC_05_NGuoiNhanNgoaiVCBSoTaiKhoanXacThucBangOTP() {
	log.info("TC_05_Step_1: Chọn quà tặng may mắn");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

	log.info("TC_05_Step_2: chọn tài khoản nguồn");
	luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCOUNT_FORM);

	String moneySurplus = luckyGift.getTextInDynamicPopup(driver, "com.VCB:id/available_balance");
	String[] moneySurplusSplit = moneySurplus.split(" ");
	String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
	int surplus = Integer.parseInt(moneySurplusReplace);

	log.info("TC_05_Step_3: chọn hình thức gửi quà");
	luckyGift.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvHinhThuc");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận khác VCB");

	log.info("TC_05_Step_3: Thêm người nhận");
	luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

	log.info("TC_05_Step_5: nhập số tài khoản");
	luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.ACCOUNT_ACCEPT_OUT_VCB, TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

	log.info("TC_05_Step_5: ngần hàng thụ hưởng");
	luckyGift.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvBank");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng DAB");

	log.info("TC_05_Step_6: Nhap so tien chuyen");
	luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

	log.info("TC_05_Step_7: Chon loi chuc");
	luckyGift.clickToDynamicWishes(driver, "Nhập/chọn lời chúc");
	luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
	luckyGift.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/content_counter");

	log.info("TC_05_Step_8: Click tiep tuc popup");
	luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_05_Step_9: lấy ra tài khoản nguồn");
	source_account = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvAccSource");

	log.info("TC_05_Step_10: lấy ra tài khoản thụ hưởng");
	String user = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvReceiver");
	String[] userSplit = user.split("/");
	beneficiary_account = userSplit[0];

	log.info("TC_05_Step_11: lấy ra lời chúc");
	wishes = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvLoichuc");

	log.info("TC_05_Step_12: lấy ra số tiền chuyển");
	money = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvSumAmount");

	log.info("TC_05_Step_13: lấy ra phí chuyển");
	String moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
	String[] sumFee = moneyFee.split(" ");
	int sumFeeInt = Integer.parseInt(sumFee[0].replace(",", ""));
	int surplusTotal = surplus - Integer.parseInt(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
	surplusString = String.valueOf(surplusTotal);

	log.info("TC_05_Step_14: chọn phương thức xác thực OTP");
	luckyGift.clickToTextID("com.VCB:id/tvptxt");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

	log.info("TC_05_Step_15: Click tiep tuc popup");
	luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_05_Step_16: điền OTP");
	luckyGift.inputToDynamicOtp(driver, LuckyGift_Data.LuckyGift.OTP, "Tiếp tục");

	log.info("TC_05_Step_17: Click tiep tuc popup");
	luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_05_Step_18: Click giao dịch mới");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Giao dịch mới");

	log.info("TC_05_Step_19: chọn tai khoản");
	luckyGift.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/number_account");

	log.info("TC_05_Step_20: kiểm tra số dư");
	String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, LuckyGift_Data.LuckyGift.ACCOUNT_FORM);
	String[] moneyTransferSplit = moneyTransfer.split(" ");
	String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
	verifyEquals(moneyTransferReplace, surplusString);

	log.info("TC_05_Step_21: chọn đóng");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

	log.info("TC_05_Step_22: Lấy thời gian thực hiện giao dịch");
	date = new Date();
    }

    @Test
    public void TC_06_KiemTraChiTietGiaoDichChuyenTienQuaTangMayManNgoaiVCBVaXacThucBangOTP() {
	log.info("TC_06_Step_01 : Click home");
	luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");

	log.info("TC_06_Step_02: Click vao More Icon");
	homePage = PageFactoryManager.getHomePageObject(driver);
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_06_Step_03: Click Bao Cao Dao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TRANSFER_REPORT);

	log.info("TC_06_Step_04: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TITLE_TRANSFER);

	log.info("TC_06_Step_05: Chon quà tặng may mắn");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

	log.info("TC_06_Step_06: click chọn tài khoản");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_06_Step_07: chọn đóng");
	transReport.clickToTextID(driver, "com.VCB:id/cancel_button");

	log.info("TC_06_Step_08: CLick Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_06_Step_9: Kiem tra ngày giao dịch");
	String dateSuccess = transReport.getDynamicTextDetailByID(driver, "com.VCB:id/tvDate");
	String[] dateSplit = dateSuccess.split(" ");
	verifyEquals(dateSplit[0], formatter.format(date));

	log.info("TC_06_Step_10: Kiem tra noi dung hien thi");
	String content = transReport.getDynamicTextDetailByID(driver, "com.VCB:id/tvContent");
	verifyEquals(content, wishes.trim());

	log.info("TC_06_Step_11: Kiem tra số tiền chuyển đi");
	String getMoney = transReport.getDynamicTextDetailByID(driver, "com.VCB:id/tvMoney");
	String[] moneySplit = getMoney.split(" ");
	verifyEquals(moneySplit[1] + " " + moneySplit[2], money);

	log.info("TC_06_Step_12: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_06_Step_13: Kiem tra ngày giao dịch");
	String getDate = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
	String[] dateDeal = getDate.split(" ");
	verifyEquals(dateDeal[0], formatter.format(date));

	log.info("TC_06_Step_14: Kiem tra tài khoản nguồn");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), source_account);

	log.info("TC_06_Step_15: Kiem tra tài khoản thụ hưởng");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), beneficiary_account);

	log.info("TC_06_Step_16: Click quay lai");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_06_Step_17: Click quay lai");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_06_Step_18: Click quay lai");
	transReport.navigateBack(driver);

	log.info("TC_02_Step_19: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

    }

    @Test
    public void TC_07_NGuoiNhanNgoaiVCBSoTaiKhoanXacThucBangMK() {
	log.info("TC_07_Step_1: Chọn quà tặng may mắn");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

	log.info("TC_07_Step_2: chọn tài khoản nguồn");
	luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCOUNT_FORM);

	String moneySurplus = luckyGift.getTextInDynamicPopup(driver, "com.VCB:id/available_balance");
	String[] moneySurplusSplit = moneySurplus.split(" ");
	String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
	int surplus = Integer.parseInt(moneySurplusReplace);

	log.info("TC_07_Step_3: chọn hình thức gửi quà");
	luckyGift.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvHinhThuc");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận khác VCB");

	log.info("TC_07_Step_3: Thêm người nhận");
	luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

	log.info("TC_07_Step_5: nhập số tài khoản");
	luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.ACCOUNT_ACCEPT_OUT_VCB, TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

	log.info("TC_07_Step_5: ngần hàng thụ hưởng");
	luckyGift.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvBank");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng DAB");

	log.info("TC_07_Step_6: Nhap so tien chuyen");
	luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

	log.info("TC_07_Step_7: Chon loi chuc");
	luckyGift.clickToDynamicWishes(driver, "Nhập/chọn lời chúc");
	luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
	luckyGift.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/content_counter");

	log.info("TC_07_Step_8: Click tiep tuc popup");
	luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_07_Step_9: lấy ra tài khoản nguồn");
	source_account = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvAccSource");

	log.info("TC_07_Step_10: lấy ra tài khoản thụ hưởng");
	String user = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvReceiver");
	String[] userSplit = user.split("/");
	beneficiary_account = userSplit[0];

	log.info("TC_07_Step_11: lấy ra lời chúc");
	wishes = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvLoichuc");

	log.info("TC_07_Step_12: lấy ra số tiền chuyển");
	money = luckyGift.getDynamicTextDetailByID(driver, "com.VCB:id/tvSumAmount");

	log.info("TC_07_Step_13: lấy ra phí chuyển");
	String moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
	String[] sumFee = moneyFee.split(" ");
	int sumFeeInt = Integer.parseInt(sumFee[0].replace(",", ""));
	int surplusTotal = surplus - Integer.parseInt(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
	surplusString = String.valueOf(surplusTotal);

	log.info("TC_07_Step_15: Click tiep tuc popup");
	luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_01_Step_15: điền mât khẩu");
	luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.PASS, "com.VCB:id/pin");

	log.info("TC_07_Step_17: Click tiep tuc popup");
	luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_07_Step_18: Click giao dịch mới");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Giao dịch mới");

	log.info("TC_07_Step_19: chọn tai khoản");
	luckyGift.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/number_account");

	log.info("TC_07_Step_20: kiểm tra số dư");
	String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, LuckyGift_Data.LuckyGift.ACCOUNT_FORM);
	String[] moneyTransferSplit = moneyTransfer.split(" ");
	String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
	verifyEquals(moneyTransferReplace, surplusString);

	log.info("TC_07_Step_21: chọn đóng");
	luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

	log.info("TC_07_Step_22: Lấy thời gian thực hiện giao dịch");
	date = new Date();
    }

    @Test
    public void TC_08_KiemTraChiTietGiaoDichChuyenTienQuaTangMayManNgoaiVCBVaXacThucBangMK() {
	log.info("TC_08_Step_01 : Click home");
	luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");

	log.info("TC_08_Step_02: Click vao More Icon");
	homePage = PageFactoryManager.getHomePageObject(driver);
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_08_Step_03: Click Bao Cao Dao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TRANSFER_REPORT);

	log.info("TC_08_Step_04: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TITLE_TRANSFER);

	log.info("TC_08_Step_05: Chon quà tặng may mắn");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

	log.info("TC_08_Step_06: click chọn tài khoản");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_08_Step_07: chọn đóng");
	transReport.clickToTextID(driver, "com.VCB:id/cancel_button");

	log.info("TC_08_Step_08: CLick Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_08_Step_09: Kiem tra ngày giao dịch");
	String dateSuccess = transReport.getDynamicTextDetailByID(driver, "com.VCB:id/tvDate");
	String[] dateSplit = dateSuccess.split(" ");
	verifyEquals(dateSplit[0], formatter.format(date));

	log.info("TC_08_Step_10: Kiem tra noi dung hien thi");
	String content = transReport.getDynamicTextDetailByID(driver, "com.VCB:id/tvContent");
	verifyEquals(content, wishes.trim());

	log.info("TC_08_Step_11: Kiem tra số tiền chuyển đi");
	String getMoney = transReport.getDynamicTextDetailByID(driver, "com.VCB:id/tvMoney");
	String[] moneySplit = getMoney.split(" ");
	verifyEquals(moneySplit[1] + " " + moneySplit[2], money);

	log.info("TC_08_Step_12: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_08_Step_13: Kiem tra ngày giao dịch");
	String getDate = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
	String[] dateDeal = getDate.split(" ");
	verifyEquals(dateDeal[0], formatter.format(date));

	log.info("TC_08_Step_14: Kiem tra tài khoản nguồn");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), source_account);

	log.info("TC_08_Step_15: Kiem tra tài khoản thụ hưởng");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), beneficiary_account);

	log.info("TC_08_Step_16: Click quay lai");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_08_Step_17: Click quay lai");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_08_Step_18: Click quay lai");
	transReport.navigateBack(driver);

	log.info("TC_02_Step_19: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

    }

    @AfterMethod(alwaysRun = true)
    public void afterClass() {
	closeApp();
	service.stop();
    }

}
