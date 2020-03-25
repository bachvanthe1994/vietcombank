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
import vietcombank_test_data.TransferIdentity_Data;
import vietcombank_test_data.TransferIdentity_Data.textCheckElement;

public class TransferIdentity_flow2 extends Base {
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
	driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
	login = PageFactoryManager.getLoginPageObject(driver);
	login.Global_login(phone, pass, opt);

	homePage = PageFactoryManager.getHomePageObject(driver);
	trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
    }

    @Parameters({ "otp" })
//    @Test
    public void TC_15_ChuyenTienERUChoNguoNhanTaiQuayBangHCXacThucBangOTPNguoiNhanTraPhi(String otp) {
	log.info("TC_15_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

	log.info("TC_15_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.EUR_ACCOUNT);

	log.info("TC_15_Step_3: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");

	log.info("TC_15_STEP_4: lấy ra số dư");
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_15_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

	log.info("TC_15_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_15_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_15_Step_8: noi cap");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.ISSUED, "Nơi cấp");

	log.info("TC_15_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR, "Số tiền");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_15_STEP_9: chọn người trả phí");
	trasferPage.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvContent3");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

	log.info("TC_15_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_15_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_15_STEP_20: lấy nội dung giao dịch");
	content = trasferPage.getMoneyByAccount(driver, "Nội dung");

	log.info("TC_15_STEP_12: chon phương thức xác thực");
	trasferPage.clickToTextID("com.VCB:id/tvptxt");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

	log.info("TC_15_STEP_13: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_15_STEP_14: điền pass");
	trasferPage.inputToDynamicOtp(driver, otp, "Tiếp tục");

	log.info("TC_15_STEP_14: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_15_STEP_15: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Double.parseDouble(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_15_STEP_16: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_15_STEP_17: lấy tên người hưởng");
	user = trasferPage.getMoneyByAccount(driver, "Tên người thụ hưởng");

	log.info("TC_15_STEP_18: lấy số CMT");
	user = trasferPage.getMoneyByAccount(driver, "Tài khoản thụ hưởng");

	log.info("TC_15_STEP_19: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, "Mã giao dịch");

	log.info("TC_15_STEP_21: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_15_STEP_22: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

//    @Test
    public void TC_16_BaoCaoChuyenTienEURChoNguoNhanTaiQuayBangHCXacThucBangOTPNguoiNhanTraPhi() {
	log.info("TC_16_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_16_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

	log.info("TC_16_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

	log.info("TC_16_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền cho người nhận tại quầy");

	log.info("TC_16_5: Click Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_16_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.EUR_ACCOUNT);

	log.info("TC_16_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_16_8: Kiem tra ngay tao giao dich hien thi");
	String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
	verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), transferDate[3].replace("-", "/"));

	log.info("TC_16_9: Kiem tra noi dung hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), content);

	log.info("TC_16_10: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney"), ("- " + moneyTransfer));

	log.info("TC_16_11: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_16_12: Kiem tra mã giao dịch");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), code);

	log.info("TC_16_13: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Valid_Account.EUR_ACCOUNT);

	log.info("TC_16_14: Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(textCheckElement.AMOUNT) + " USD"));

	log.info("TC_16_15: lấy ra phí giao dịch");
	String getFee = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
	String[] feeSplit = getFee.split(" ");
	String fee = (feeSplit[0].replace(",", ""));
	double feeUSD = convertVNeseMoneyToEUROOrUSD(fee, "EUR");

	log.info("TC_16_16: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_16_17: kiểm tra số dư");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.EUR_ACCOUNT);
	String surplus = transReport.getDynamicTextInTransactionDetail(driver, Valid_Account.EUR_ACCOUNT);
	String[] surplusSplit = surplus.split(" ");
	double surplusInt = Double.parseDouble(surplusSplit[0].replace(",", ""));
	double canculateAvailable = canculateAvailableBalancesCurrentcy((long) toltalMoney, (long) feeUSD, (long) money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_16_18: Click Đóng");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/cancel_button");

	log.info("TC_16_19: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_16_20: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

    @Parameters({ "pass" })
//    @Test
    public void TC_17_ChuyenTienVNDChoNguoNhanTaiQuayBangCMTQDXacThucBangMKNguoiChuyenTraPhi(String pass) {
	log.info("TC_17_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

	log.info("TC_17_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT1);

	log.info("TC_17_Step_3: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");

	log.info("TC_17_STEP_4: lấy ra số dư");
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_17_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "CMT Quân đội");

	log.info("TC_17_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_17_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_17_Step_8: noi cap");
	trasferPage.inputToDynamicEditviewByLinearlayoutId(driver, TransferIdentity_Data.textDataInputForm.ISSUED, "com.VCB:id/LayoutNoiCap");

	log.info("TC_17_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_17_STEP_9: chọn người trả phí");
	trasferPage.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvContent3");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

	log.info("TC_17_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_17_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_17_STEP_20: lấy nội dung giao dịch");
	content = trasferPage.getMoneyByAccount(driver, "Nội dung");

	log.info("TC_17_STEP_12: chon phương thức xác thực");
	trasferPage.clickToTextID("com.VCB:id/tvptxt");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

	log.info("TC_17_STEP_13: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_17_STEP_14: điền pass");
	trasferPage.inputToDynamicInputBox(driver, pass, "Nhập mật khẩu");

	log.info("TC_17_STEP_14: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_17_STEP_15: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Double.parseDouble(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_17_STEP_16: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_17_STEP_17: lấy tên người hưởng");
	user = trasferPage.getMoneyByAccount(driver, "Tên người thụ hưởng");

	log.info("TC_17_STEP_18: lấy số CMT");
	user = trasferPage.getMoneyByAccount(driver, "Tài khoản thụ hưởng");

	log.info("TC_17_STEP_19: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, "Mã giao dịch");

	log.info("TC_17_STEP_21: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_17_STEP_22: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

//    @Test
    public void TC_18_BaoCaoChuyenTienVNDChoNguoNhanTaiQuayBangCMTQDXacThucBangMKNguoiNhanTraPhi() {
	log.info("TC_18_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_18_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

	log.info("TC_18_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

	log.info("TC_18_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền cho người nhận tại quầy");

	log.info("TC_18_5: Click Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_18_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT1);

	log.info("TC_18_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_18_8: Kiem tra ngay tao giao dich hien thi");
	String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
	verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), transferDate[3].replace("-", "/"));

	log.info("TC_18_9: Kiem tra noi dung hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), content);

	log.info("TC_18_10: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney"), ("- " + moneyTransfer));

	log.info("TC_18_11: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_18_12: Kiem tra mã giao dịch");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), code);

	log.info("TC_18_13: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Valid_Account.ACCOUNT1);

	log.info("TC_18_14: Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND) + " VND"));

	log.info("TC_18_15: lấy ra phí giao dịch");
	String getFee = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
	String[] feeSplit = getFee.split(" ");
	fee = Integer.parseInt(feeSplit[0].replace(",", ""));

	log.info("TC_18_16: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_18_17: kiểm tra số dư");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT1);
	String surplus = transReport.getDynamicTextInTransactionDetail(driver, Valid_Account.ACCOUNT1);
	String[] surplusSplit = surplus.split(" ");
	double surplusInt = Integer.parseInt(surplusSplit[0].replace(",", ""));
	double canculateAvailable = canculateAvailableBalances((long) toltalMoney, (long) fee, (long) money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_18_18: Click Đóng");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/cancel_button");

	log.info("TC_18_19: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_18_20: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

    @Parameters({ "pass" })
//    @Test
    public void TC_19_ChuyenTienUSDChoNguoNhanTaiQuayBangCMTQDXacThucBangMKNguoiNhanTraPhi(String pass) {
	log.info("TC_19_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

	log.info("TC_19_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.USD_ACCOUNT);

	log.info("TC_19_Step_3: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");

	log.info("TC_19_STEP_4: lấy ra số dư");
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_19_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "CMT Quân đội");

	log.info("TC_19_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_19_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_19_Step_8: noi cap");
	trasferPage.inputToDynamicEditviewByLinearlayoutId(driver, TransferIdentity_Data.textDataInputForm.ISSUED, "com.VCB:id/LayoutNoiCap");

	log.info("TC_19_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_USD, "Số tiền");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_19_STEP_9: chọn người trả phí");
	trasferPage.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvContent3");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

	log.info("TC_19_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_19_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_19_STEP_20: lấy nội dung giao dịch");
	content = trasferPage.getMoneyByAccount(driver, "Nội dung");

	log.info("TC_19_STEP_12: chon phương thức xác thực");
	trasferPage.clickToTextID("com.VCB:id/tvptxt");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

	log.info("TC_19_STEP_13: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_19_STEP_14: điền pass");
	trasferPage.inputToDynamicInputBox(driver, pass, "Nhập mật khẩu");

	log.info("TC_19_STEP_14: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_19_STEP_15: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Double.parseDouble(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_19_STEP_16: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_19_STEP_17: lấy tên người hưởng");
	user = trasferPage.getMoneyByAccount(driver, "Tên người thụ hưởng");

	log.info("TC_19_STEP_18: lấy số CMT");
	user = trasferPage.getMoneyByAccount(driver, "Tài khoản thụ hưởng");

	log.info("TC_19_STEP_19: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, "Mã giao dịch");

	log.info("TC_19_STEP_21: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_19_STEP_22: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

//    @Test
    public void TC_20_BaoCaoChuyenTienUSDChoNguoNhanTaiQuayBangCMTQDXacThucBangMKNguoiNhanTraPhi() {
	log.info("TC_20_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_20_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

	log.info("TC_20_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

	log.info("TC_20_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền cho người nhận tại quầy");

	log.info("TC_20_5: Click Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_20_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT1);

	log.info("TC_20_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_20_8: Kiem tra ngay tao giao dich hien thi");
	String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
	verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), transferDate[3].replace("-", "/"));

	log.info("TC_20_9: Kiem tra noi dung hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), content);

	log.info("TC_20_10: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney"), ("- " + moneyTransfer));

	log.info("TC_20_11: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_20_12: Kiem tra mã giao dịch");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), code);

	log.info("TC_20_13: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Valid_Account.ACCOUNT1);

	log.info("TC_20_14: Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND) + " VND"));

	log.info("TC_20_15: lấy ra phí giao dịch");
	String getFee = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
	String[] feeSplit = getFee.split(" ");
	fee = convertVNeseMoneyToEUROOrUSD(feeSplit[0].replace(",", "") + "", "USD");

	log.info("TC_20_16: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_20_17: kiểm tra số dư");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT1);
	String surplus = transReport.getDynamicTextInTransactionDetail(driver, Valid_Account.ACCOUNT1);
	String[] surplusSplit = surplus.split(" ");
	double surplusInt = Integer.parseInt(surplusSplit[0].replace(",", ""));
	double canculateAvailable = canculateAvailableBalances((long) toltalMoney, (long) fee, (long) money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_20_18: Click Đóng");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/cancel_button");

	log.info("TC_20_19: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_20_20: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

    @Parameters({ "otp" })
//    @Test
    public void TC_21_ChuyenTienVNDChoNguoNhanTaiQuayBangCMTQDXacThucBangOTPNguoiNhanTraPhi(String otp) {
	log.info("TC_21_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

	log.info("TC_21_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT1);

	log.info("TC_21_Step_3: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");

	log.info("TC_21_STEP_4: lấy ra số dư");
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_21_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "CMT Quân đội");

	log.info("TC_21_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_21_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_21_Step_8: noi cap");
	trasferPage.inputToDynamicEditviewByLinearlayoutId(driver, TransferIdentity_Data.textDataInputForm.ISSUED, "com.VCB:id/LayoutNoiCap");

	log.info("TC_21_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_21_STEP_9: chọn người trả phí");
	trasferPage.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvContent3");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

	log.info("TC_21_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_21_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_21_STEP_20: lấy nội dung giao dịch");
	content = trasferPage.getMoneyByAccount(driver, "Nội dung");

	log.info("TC_21_STEP_12: chon phương thức xác thực");
	trasferPage.clickToTextID("com.VCB:id/tvptxt");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

	log.info("TC_21_STEP_13: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_21_STEP_14: điền pass");
	trasferPage.inputToDynamicOtp(driver, otp, "Tiếp tục");

	log.info("TC_21_STEP_14: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_21_STEP_15: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Double.parseDouble(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_21_STEP_16: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_21_STEP_17: lấy tên người hưởng");
	user = trasferPage.getMoneyByAccount(driver, "Tên người thụ hưởng");

	log.info("TC_21_STEP_18: lấy số CMT");
	user = trasferPage.getMoneyByAccount(driver, "Tài khoản thụ hưởng");

	log.info("TC_21_STEP_19: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, "Mã giao dịch");

	log.info("TC_21_STEP_21: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_21_STEP_22: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

//    @Test
    public void TC_22_BaoCaoChuyenTienVNDChoNguoNhanTaiQuayBangCMTQDXacThucBangOTPNguoiNhanTraPhi() {
	log.info("TC_22_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_22_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

	log.info("TC_22_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

	log.info("TC_22_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền cho người nhận tại quầy");

	log.info("TC_22_5: Click Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_22_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT1);

	log.info("TC_22_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_22_8: Kiem tra ngay tao giao dich hien thi");
	String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
	verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), transferDate[3].replace("-", "/"));

	log.info("TC_22_9: Kiem tra noi dung hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), content);

	log.info("TC_22_10: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney"), ("- " + moneyTransfer));

	log.info("TC_22_11: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_22_12: Kiem tra mã giao dịch");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), code);

	log.info("TC_22_13: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Valid_Account.ACCOUNT1);

	log.info("TC_22_14: Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND) + " VND"));

	log.info("TC_22_15: lấy ra phí giao dịch");
	String getFee = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
	String[] feeSplit = getFee.split(" ");
	fee = Integer.parseInt(feeSplit[0].replace(",", ""));

	log.info("TC_22_16: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_22_17: kiểm tra số dư");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT1);
	String surplus = transReport.getDynamicTextInTransactionDetail(driver, Valid_Account.ACCOUNT1);
	String[] surplusSplit = surplus.split(" ");
	double surplusInt = Integer.parseInt(surplusSplit[0].replace(",", ""));
	double canculateAvailable = canculateAvailableBalances((long) toltalMoney, (long) fee, (long) money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_22_18: Click Đóng");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/cancel_button");

	log.info("TC_22_19: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_22_20: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

    @Parameters({ "otp" })
//    @Test
    public void TC_23_ChuyenTienEURChoNguoNhanTaiQuayBangCMTQDXacThucBangMKNguoiChuyenTraPhi(String otp) {
	log.info("TC_23_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

	log.info("TC_23_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.EUR_ACCOUNT);

	log.info("TC_23_Step_3: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");

	log.info("TC_23_STEP_4: lấy ra số dư");
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_23_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "CMT Quân đội");

	log.info("TC_23_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_23_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_23_Step_8: noi cap");
	trasferPage.inputToDynamicEditviewByLinearlayoutId(driver, TransferIdentity_Data.textDataInputForm.ISSUED, "com.VCB:id/LayoutNoiCap");

	log.info("TC_23_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR, "Số tiền");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_23_STEP_9: chọn người trả phí");
	trasferPage.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvContent3");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

	log.info("TC_23_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_23_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_23_STEP_20: lấy nội dung giao dịch");
	content = trasferPage.getMoneyByAccount(driver, "Nội dung");

	log.info("TC_23_STEP_12: chon phương thức xác thực");
	trasferPage.clickToTextID("com.VCB:id/tvptxt");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

	log.info("TC_23_STEP_13: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_23_STEP_14: điền pass");
	trasferPage.inputToDynamicOtp(driver, otp, "Tiếp tục");

	log.info("TC_23_STEP_14: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_23_STEP_15: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Double.parseDouble(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_23_STEP_16: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_23_STEP_17: lấy tên người hưởng");
	user = trasferPage.getMoneyByAccount(driver, "Tên người thụ hưởng");

	log.info("TC_23_STEP_18: lấy số CMT");
	user = trasferPage.getMoneyByAccount(driver, "Tài khoản thụ hưởng");

	log.info("TC_23_STEP_19: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, "Mã giao dịch");

	log.info("TC_23_STEP_21: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_23_STEP_22: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

//    @Test
    public void TC_24_BaoCaoChuyenTienEURChoNguoNhanTaiQuayBangCMTQDXacThucBangMKNguoiChuyenTraPhi() {
	log.info("TC_24_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_24_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

	log.info("TC_24_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

	log.info("TC_24_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền cho người nhận tại quầy");

	log.info("TC_24_5: Click Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_24_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.EUR_ACCOUNT);

	log.info("TC_24_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_24_8: Kiem tra ngay tao giao dich hien thi");
	String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
	verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), transferDate[3].replace("-", "/"));

	log.info("TC_24_9: Kiem tra noi dung hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), content);

	log.info("TC_24_10: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney"), ("- " + moneyTransfer));

	log.info("TC_24_11: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_24_12: Kiem tra mã giao dịch");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), code);

	log.info("TC_24_13: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Valid_Account.EUR_ACCOUNT);

	log.info("TC_24_14: Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR) + " EUR"));

	log.info("TC_24_15: lấy ra phí giao dịch");
	String getFee = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
	String[] feeSplit = getFee.split(" ");
	fee = convertVNeseMoneyToEUROOrUSD(feeSplit[0].replace(",", "") + "", "EUR");

	log.info("TC_24_16: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_24_17: kiểm tra số dư");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.EUR_ACCOUNT);
	String surplus = transReport.getDynamicTextInTransactionDetail(driver, Valid_Account.EUR_ACCOUNT);
	String[] surplusSplit = surplus.split(" ");
	double surplusInt = Double.parseDouble(surplusSplit[0].replace(",", ""));
	double canculateAvailable = canculateAvailableBalancesCurrentcy((long) toltalMoney, (long) fee, (long) money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_24_18: Click Đóng");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/cancel_button");

	log.info("TC_24_19: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_24_20: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

    @Parameters({ "pass" })
    @Test
    public void TC_25_ChuyenTienVNDChoNguoNhanTaiQuayBangCCCDXacThucBangMKNguoiChuyenTraPhi(String pass) {
	log.info("TC_25_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

	log.info("TC_25_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT1);

	log.info("TC_25_Step_3: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");

	log.info("TC_25_STEP_4: lấy ra số dư");
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_25_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ căn cước công dân");

	log.info("TC_25_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_25_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_25_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_25_STEP_9: chọn người trả phí");
	trasferPage.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvContent3");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

	log.info("TC_25_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_25_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_25_STEP_20: lấy nội dung giao dịch");
	content = trasferPage.getMoneyByAccount(driver, "Nội dung");

	log.info("TC_25_STEP_12: chon phương thức xác thực");
	trasferPage.clickToTextID("com.VCB:id/tvptxt");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

	log.info("TC_25_STEP_13: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_25_STEP_14: điền pass");
	trasferPage.inputToDynamicInputBox(driver, pass, "Nhập mật khẩu");

	log.info("TC_25_STEP_14: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_25_STEP_15: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Double.parseDouble(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_25_STEP_16: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_25_STEP_17: lấy tên người hưởng");
	user = trasferPage.getMoneyByAccount(driver, "Tên người thụ hưởng");

	log.info("TC_25_STEP_18: lấy số CMT");
	user = trasferPage.getMoneyByAccount(driver, "Tài khoản thụ hưởng");

	log.info("TC_25_STEP_19: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, "Mã giao dịch");

	log.info("TC_25_STEP_21: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_25_STEP_22: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_26_BaoCaoChuyenTienVNDChoNguoNhanTaiQuayBangCCCDXacThucBangMKNguoiChuyenTraPhi() {
	log.info("TC_26_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_26_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

	log.info("TC_26_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

	log.info("TC_26_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền cho người nhận tại quầy");

	log.info("TC_26_5: Click Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_26_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT1);

	log.info("TC_26_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_26_8: Kiem tra ngay tao giao dich hien thi");
	String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
	verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), transferDate[3].replace("-", "/"));

	log.info("TC_26_9: Kiem tra noi dung hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), content);

	log.info("TC_26_10: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney"), ("- " + moneyTransfer));

	log.info("TC_26_11: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_26_12: Kiem tra mã giao dịch");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), code);

	log.info("TC_26_13: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Valid_Account.ACCOUNT1);

	log.info("TC_26_14: Kiem tra so tien giao dich hien thi");
	String get_money_transf = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").replace(",", "");
	String[] getMoneyTransfer = get_money_transf.split(" ");
	verifyEquals(getMoneyTransfer[0], TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND);

	log.info("TC_26_15: lấy ra phí giao dịch");
	String getFee = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
	String[] feeSplit = getFee.split(" ");
	double fee = Integer.parseInt(feeSplit[0].replace(",", ""));

	log.info("TC_26_16: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_26_17: kiểm tra số dư");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT1);
	String surplus = transReport.getDynamicTextInTransactionDetail(driver, Valid_Account.ACCOUNT1);
	String[] surplusSplit = surplus.split(" ");
	double surplusInt = Double.parseDouble(surplusSplit[0].replace(",", ""));
	double canculateAvailable = canculateAvailableBalances((long) toltalMoney, (long) fee, (long) money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_26_18: Click Đóng");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/cancel_button");

	log.info("TC_26_19: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_26_20: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

    @Parameters({ "pass" })
    @Test
    public void TC_27_ChuyenTienUSDChoNguoNhanTaiQuayBangCCCDXacThucBangMKNguoiNhanTraPhi(String pass) {
	log.info("TC_27_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

	log.info("TC_27_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.USD_ACCOUNT);

	log.info("TC_27_Step_3: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");

	log.info("TC_27_STEP_4: lấy ra số dư");
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_27_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ căn cước công dân");

	log.info("TC_27_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_27_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_27_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_USD, "Số tiền");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_27_STEP_9: chọn người trả phí");
	trasferPage.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvContent3");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

	log.info("TC_27_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_27_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_27_STEP_20: lấy nội dung giao dịch");
	content = trasferPage.getMoneyByAccount(driver, "Nội dung");

	log.info("TC_27_STEP_12: chon phương thức xác thực");
	trasferPage.clickToTextID("com.VCB:id/tvptxt");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

	log.info("TC_27_STEP_13: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_27_STEP_14: điền pass");
	trasferPage.inputToDynamicInputBox(driver, pass, "Nhập mật khẩu");

	log.info("TC_27_STEP_14: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_27_STEP_15: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Double.parseDouble(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_27_STEP_16: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_27_STEP_17: lấy tên người hưởng");
	user = trasferPage.getMoneyByAccount(driver, "Tên người thụ hưởng");

	log.info("TC_27_STEP_18: lấy số CMT");
	user = trasferPage.getMoneyByAccount(driver, "Tài khoản thụ hưởng");

	log.info("TC_27_STEP_19: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, "Mã giao dịch");

	log.info("TC_27_STEP_21: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_27_STEP_22: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_28_BaoCaoChuyenTienUSDChoNguoNhanTaiQuayBangCCCDXacThucBangMKNguoiNhanTraPhi() {
	log.info("TC_28_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_28_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

	log.info("TC_28_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

	log.info("TC_28_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền cho người nhận tại quầy");

	log.info("TC_28_5: Click Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_28_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.USD_ACCOUNT);

	log.info("TC_28_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_28_8: Kiem tra ngay tao giao dich hien thi");
	String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
	verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), transferDate[3].replace("-", "/"));

	log.info("TC_28_9: Kiem tra noi dung hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), content);

	log.info("TC_28_10: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney"), ("- " + moneyTransfer));

	log.info("TC_28_11: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_28_12: Kiem tra mã giao dịch");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), code);

	log.info("TC_28_13: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Valid_Account.USD_ACCOUNT);

	log.info("TC_28_14: Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(textCheckElement.AMOUNT) + " USD"));

	log.info("TC_28_15: lấy ra phí giao dịch");
	String getFee = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
	String[] feeSplit = getFee.split(" ");
	double fee = Integer.parseInt(feeSplit[0].replace(",", ""));

	log.info("TC_28_16: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_28_17: kiểm tra số dư");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.USD_ACCOUNT);
	String surplus = transReport.getDynamicTextInTransactionDetail(driver, Valid_Account.USD_ACCOUNT);
	String[] surplusSplit = surplus.split(" ");
	double surplusInt = Double.parseDouble(surplusSplit[0].replace(",", ""));
	double canculateAvailable = canculateAvailableBalancesCurrentcy(toltalMoney, fee, money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_28_18: Click Đóng");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/cancel_button");

	log.info("TC_28_19: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_28_20: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

    @Parameters({ "otp" })
    @Test
    public void TC_29_ChuyenTienUSDChoNguoNhanTaiQuayBangCCCDXacThucBangOTPNguoiNhanTraPhi(String otp) {
	log.info("TC_29_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

	log.info("TC_29_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.EUR_ACCOUNT);

	log.info("TC_29_Step_3: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");

	log.info("TC_29_STEP_4: lấy ra số dư");
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_29_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ căn cước công dân");

	log.info("TC_29_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_29_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_29_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR, "Số tiền");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_29_STEP_9: chọn người trả phí");
	trasferPage.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvContent3");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

	log.info("TC_29_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_29_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_29_STEP_20: lấy nội dung giao dịch");
	content = trasferPage.getMoneyByAccount(driver, "Nội dung");

	log.info("TC_29_STEP_12: chon phương thức xác thực");
	trasferPage.clickToTextID("com.VCB:id/tvptxt");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

	log.info("TC_29_STEP_13: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_29_STEP_14: điền pass");
	trasferPage.inputToDynamicOtp(driver, otp, "Tiếp tục");

	log.info("TC_29_STEP_14: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_29_STEP_15: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Double.parseDouble(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_29_STEP_16: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_29_STEP_17: lấy tên người hưởng");
	user = trasferPage.getMoneyByAccount(driver, "Tên người thụ hưởng");

	log.info("TC_29_STEP_18: lấy số CMT");
	user = trasferPage.getMoneyByAccount(driver, "Tài khoản thụ hưởng");

	log.info("TC_29_STEP_19: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, "Mã giao dịch");

	log.info("TC_29_STEP_21: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_29_STEP_22: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_30_BaoCaoChuyenTienUSDChoNguoNhanTaiQuayBangCCCDXacThucBangOTPNguoiChuyenTraPhi() {
	log.info("TC_30_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_30_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

	log.info("TC_30_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

	log.info("TC_30_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền cho người nhận tại quầy");

	log.info("TC_30_5: Click Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_30_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.USD_ACCOUNT);

	log.info("TC_30_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_30_8: Kiem tra ngay tao giao dich hien thi");
	String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
	verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), transferDate[3].replace("-", "/"));

	log.info("TC_30_9: Kiem tra noi dung hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), content);

	log.info("TC_30_10: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney"), ("- " + moneyTransfer));

	log.info("TC_30_11: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_30_12: Kiem tra mã giao dịch");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), code);

	log.info("TC_30_13: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Valid_Account.USD_ACCOUNT);

	log.info("TC_30_14: Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(textCheckElement.AMOUNT) + " USD"));

	log.info("TC_30_15: lấy ra phí giao dịch");
	String getFee = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
	String[] feeSplit = getFee.split(" ");
	double fee = Integer.parseInt(feeSplit[0].replace(",", ""));

	log.info("TC_30_16: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_30_17: kiểm tra số dư");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.USD_ACCOUNT);
	String surplus = transReport.getDynamicTextInTransactionDetail(driver, Valid_Account.USD_ACCOUNT);
	String[] surplusSplit = surplus.split(" ");
	double surplusInt = Double.parseDouble(surplusSplit[0].replace(",", ""));
	double canculateAvailable = canculateAvailableBalancesCurrentcy(toltalMoney, fee, money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_30_18: Click Đóng");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/cancel_button");

	log.info("TC_30_19: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_30_20: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

    @Parameters({ "otp" })
    @Test
    public void TC_31_ChuyenTienVNDChoNguoNhanTaiQuayBangCCCDXacThucBangOTPNguoiChuyenTraPhi(String otp) {
	log.info("TC_31_STEP_1: chon Chuyển tiền nhận bằng tiền mặt");
	homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

	log.info("TC_31_STEP_2: chon tài khoản");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvContent");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT1);

	log.info("TC_31_Step_3: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");

	log.info("TC_31_STEP_4: lấy ra số dư");
	String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
	String[] toltal_money = getToltalMoney.split(" ");
	toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

	log.info("TC_31_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ căn cước công dân");

	log.info("TC_31_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_31_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_31_STEP_9: nhap so tien bat dau la khong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");
	trasferPage.clickToTextID(driver, "com.VCB:id/tvTitle");

	log.info("TC_31_STEP_9: chọn người trả phí");
	trasferPage.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvContent3");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

	log.info("TC_31_Step_10: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_31_STEP_11: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_31_STEP_20: lấy nội dung giao dịch");
	content = trasferPage.getMoneyByAccount(driver, "Nội dung");

	log.info("TC_31_STEP_12: chon phương thức xác thực");
	trasferPage.clickToTextID("com.VCB:id/tvptxt");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

	log.info("TC_31_STEP_13: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_31_STEP_14: điền pass");
	trasferPage.inputToDynamicOtp(driver, otp, "Tiếp tục");

	log.info("TC_31_STEP_14: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_31_STEP_15: lấy tra số tiền chuyển đi");
	moneyTransfer = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount");
	String[] getMoneyTransfer = moneyTransfer.split(" ");
	money_transferred = Double.parseDouble(getMoneyTransfer[0].replace(",", ""));

	log.info("TC_31_STEP_16: lấy ra time chuyển");
	String getDate = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
	transferDate = getDate.split(" ");

	log.info("TC_31_STEP_17: lấy tên người hưởng");
	user = trasferPage.getMoneyByAccount(driver, "Tên người thụ hưởng");

	log.info("TC_31_STEP_18: lấy số CMT");
	user = trasferPage.getMoneyByAccount(driver, "Tài khoản thụ hưởng");

	log.info("TC_31_STEP_19: lấy mã giao dịch");
	code = trasferPage.getMoneyByAccount(driver, "Mã giao dịch");

	log.info("TC_31_STEP_21: chọn thực hiện giao dịch mới");
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_31_STEP_22: chọn back");
	trasferPage.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_32_BaoCaoChuyenTienVNDChoNguoNhanTaiQuayBangCCCDXacThucBangOTPNguoiChuyenTraPhi() {
	log.info("TC_32_1: Click vao More Icon");
	homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	log.info("TC_32_2: Click Bao Cao giao Dich");
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

	log.info("TC_32_3: Click Tat Ca Cac Loai Giao Dich");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

	log.info("TC_32_4: Chon Chuyen Tien Trong VCB");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền cho người nhận tại quầy");

	log.info("TC_32_5: Click Chon Tai Khoan");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

	log.info("TC_32_6: Chon tai Khoan chuyen");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT1);

	log.info("TC_32_7: Click Tim Kiem");
	transReport.clickToDynamicButton(driver, "Tìm kiếm");

	log.info("TC_32_8: Kiem tra ngay tao giao dich hien thi");
	String reportTime1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");
	verifyEquals(convertDateTimeIgnoreHHmmss(reportTime1), transferDate[3].replace("-", "/"));

	log.info("TC_32_9: Kiem tra noi dung hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), content);

	log.info("TC_32_10: Kiem tra so tien chuyen hien thi");
	verifyEquals(transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney"), ("- " + moneyTransfer));

	log.info("TC_32_11: Click vao giao dich");
	transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

	log.info("TC_32_12: Kiem tra mã giao dịch");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), code);

	log.info("TC_32_13: Kiem tra so tai khoan trich no");
	verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Valid_Account.ACCOUNT1);

	log.info("TC_32_14: Kiem tra so tien giao dich hien thi");
	verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(addCommasToLong(textCheckElement.AMOUNT) + " VND"));

	log.info("TC_32_15: lấy ra phí giao dịch");
	String getFee = transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
	String[] feeSplit = getFee.split(" ");
	double fee = Integer.parseInt(feeSplit[0].replace(",", ""));

	log.info("TC_32_16: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	log.info("TC_32_17: kiểm tra số dư");
	transReport.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT1);
	String surplus = transReport.getDynamicTextInTransactionDetail(driver, Valid_Account.ACCOUNT1);
	String[] surplusSplit = surplus.split(" ");
	double surplusInt = Double.parseDouble(surplusSplit[0].replace(",", ""));
	double canculateAvailable = canculateAvailableBalances((long) toltalMoney, (long) fee, (long) money_transferred);
	verifyEquals(surplusInt, canculateAvailable);

	log.info("TC_32_18: Click Đóng");
	transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/cancel_button");

	log.info("TC_32_19: Click  nut Back");
	transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

	log.info("TC_32_20: Click  nut Home");
	transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
//	closeApp();
//	service.stop();
    }

}
