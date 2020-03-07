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
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferIdentity_Data;

public class Transfer_Identity_And_Passport extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferIdentiryPageObject trasferPage;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private String transactionNumber;
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();

    @Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
    @BeforeClass
    public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt)
	    throws IOException, InterruptedException {
	startServer();
	log.info("Before class: Mo app ");
	driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
	login = PageFactoryManager.getLoginPageObject(driver);
	login.Global_login(phone, pass, opt);

    }

    @Test
    public void TC_01_ChuyenTienQuaCMTNguoiChuyenTraPhiVNDXacNhanMatKhau() {
	log.info("TC_01_Step_1: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_01_Step_2: chon tai khoan");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

	homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

	log.info("TC_01_Step_3: lay so tien truoc khi chuyen khoan");
	String overbalanceBefore = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	Long overbalanceBeforeLong = convertMoneyToLong(overbalanceBefore, "VND");

	log.info("TC_01_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

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
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_01_Step_9: nguoi tra phi giao dich");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

	log.info("TC_01_Step_10: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

	log.info("TC_01_Step_11: noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "Nội dung");

	log.info("TC_01_Step_12: noi dung");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_01_Step_13: xac nhan thong tin");
	String confirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
	verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

	log.info("TC_01_Step_14: kiem tra tai khoan nguon");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tài khoản nguồn"), TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

	log.info("TC_01_Step_15: kiem tra ten nguoi thu huong");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tên người hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_01_Step_16: kiem tra giay to tuy than");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Giấy tờ tùy thân"), "Chứng minh nhân dân");

	log.info("TC_01_Step_17: kiem tra so CMT");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

	log.info("TC_01_Step_18: kiem tra ngay cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Ngày cấp"), today);

	log.info("TC_01_Step_19: kiem tra noi cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_01_Step_20: lay ra so tien chuyen di");
	String moneyTransfer = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền");
	Long moneyTransferLong = convertMoneyToLong(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
	String moneyTransferLongToString = Long.toString(moneyTransferLong);

	log.info("TC_01_Step_21: kiem tra so tien");
	verifyEquals(moneyTransferLongToString, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND);

	log.info("TC_01_Step_22: lay phi");
	String amount = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền phí");
	Long amountLong = convertMoneyToLong(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
	String amountLongToString = Long.toString(amountLong);

	log.info("TC_01_Step_23: kiem tra noi dung");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nội dung"), TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER);

	trasferPage.scrollDownToButton(driver, "Tiếp tục");

	log.info("TC_01_Step_24: chon phuong thuc xac thuc");
	trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

	log.info("TC_01_Step_25: lay phi giao dich theo phuong thuc xac thuc");
	String moneyAuthen = trasferPage.getDynamicAmountLabelConvertVNDToLong(driver, "Mật khẩu đăng nhập");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

	log.info("TC_01_Step_26: kiem tra phi giao dich");
	verifyEquals(moneyAuthen, amountLongToString);

	log.info("TC_01_Step_27: kiem tra so tien phi");
	verifyEquals(amountLongToString, TransferIdentity_Data.textCheckElement.AMOUNT);

	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_01_Step_28: xac thuc giao dich");
	trasferPage.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_01_Step_29: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
	String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	verifyTrue(moneyTransfer.equals(transferMoney));

	log.info("TC_01_Step_30: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
	transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

	log.info("TC_01_Step_31: ten nguoi thu huong");
	String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
	verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

	log.info("TC_01_Step_32: tai khoan giao dich");
	String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
	verifyTrue(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER.equals(destinationAccount));

	log.info("TC_01_Step_33: ma giao dich");
	transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_01_Step_34: noi dung giao dich");
	String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);
	verifyEquals(conten, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER);

	log.info("TC_01_Step_35: xac thuc thuc hien giao dich moi");
	String newDealConfirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
	verifyTrue(newDealConfirm.equals(TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS));
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_01_Step_36: so tien kha dung con lai trong tai khoan");
	String overbalanceAfter = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	Long overbalanceAfterLong = convertMoneyToLong(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);

	log.info("TC_01_Step_37: kiem tra so tien kha dung sau khi chuyen");
	long overbalanceAfterCacuLator = overbalanceBeforeLong - moneyTransferLong - amountLong;
	verifyEquals(overbalanceAfterLong, overbalanceAfterCacuLator);

	log.info("TC_01_Step_38 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_02_ChuyenTienQuaCMTNguoiNhanTraPhiEURXacNhanMatKhau() {
	log.info("TC_02_Step_1: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_02_Step_2: chon tai khoan");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

	homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_EUR);

	log.info("TC_02_Step_3: lay so tien truoc khi chuyen khoan");
	String overbalanceBefore = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceBeforeLong = convertMoneyToDouble(overbalanceBefore, "EUR");

	log.info("TC_02_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_02_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_02_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_02_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_02_Step_8: noi cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_02_Step_9: nguoi tra phi giao dich");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

	log.info("TC_02_Step_10: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR, "Số tiền");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

	log.info("TC_02_Step_11: noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "Nội dung");

	log.info("TC_02_Step_12: noi dung");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_02_Step_13: xac nhan thong tin");
	String confirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
	verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

	log.info("TC_02_Step_14: kiem tra tai khoan nguon");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tài khoản nguồn"), TransferIdentity_Data.textDataInputForm.ACCOUNT_EUR);

	log.info("TC_02_Step_15: kiem tra ten nguoi thu huong");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tên người hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_02_Step_16: kiem tra giay to tuy than");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Giấy tờ tùy thân"), "Chứng minh nhân dân");

	log.info("TC_02_Step_17: kiem tra so CMT");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

	log.info("TC_02_Step_18: kiem tra ngay cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Ngày cấp"), today);

	log.info("TC_02_Step_19: kiem tra noi cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_02_Step_20: lay ra so tien chuyen di");
	String moneyTransfer = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền(EUR)");
	double moneyTransferLong = convertMoneyToDouble(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);
	double moneyTransferLongToString = convertMoneyToDouble(moneyTransfer, "EUR");
	String moneyTransferLongToStringFormat = String.format("%.0f", moneyTransferLongToString);

	log.info("TC_02_Step_21: kiem tra so tien");
	verifyEquals(moneyTransferLongToStringFormat, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR);

	log.info("TC_02_Step_22: lay phi");
	String amount = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền phí");
	double amountLong = convertMoneyToDouble(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);
	String amountLongToString = String.valueOf(amountLong);

	log.info("TC_02_Step_23: kiem tra noi dung");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nội dung"), TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER);

	trasferPage.scrollDownToButton(driver, "Tiếp tục");

	log.info("TC_02_Step_24: chon phuong thuc xac thuc");
	trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

	log.info("TC_02_Step_25: lay phi giao dich theo phuong thuc xac thuc");
	String moneyAuthen = trasferPage.getDynamicAmountLabelConvertVNDToLong(driver, "Mật khẩu đăng nhập");
	double moneyAuthenLong = convertMoneyToDouble(moneyAuthen, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);
	String moneyAuthenLongToString = String.valueOf(amountLong);

	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

	log.info("TC_02_Step_26: kiem tra phi giao dich");
	verifyEquals(moneyAuthenLongToString, amountLongToString);

	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_02_Step_28: xac thuc giao dich");
	trasferPage.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_02_Step_29: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
	String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	String transferMoneyLong = transferMoney.replaceAll("EUR", "");
	double transferMoneyLongString = convertMoneyToDouble(transferMoneyLong, "EUR");
	String transferMoneyLongToStringFormat = String.format("%.0f", transferMoneyLongString);

	log.info("TC_02_Step_29: kiem tra so tien chuyen di o man hinh xac thuc thanh cong");
	verifyEquals(transferMoneyLongToStringFormat, moneyTransferLongToStringFormat);

	log.info("TC_02_Step_30: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
	transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

	log.info("TC_02_Step_31: ten nguoi thu huong");
	String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
	verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

	log.info("TC_02_Step_32: tai khoan giao dich");
	String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
	verifyTrue(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER.equals(destinationAccount));

	log.info("TC_02_Step_33: ma giao dich");
	transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_02_Step_34: noi dung giao dich");
	String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);

	verifyEquals(TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, conten);

	log.info("TC_02_Step_35: xac thuc thuc hien giao dich moi");
	String newDealConfirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
	verifyEquals(newDealConfirm, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_02_Step_36: so tien kha dung con lai trong tai khoan");
	String overbalanceAfter = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceAfterLong = convertMoneyToDouble(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);

	log.info("TC_02_Step_37: kiem tra so tien kha dung sau khi chuyen");
	double overbalanceAfterCacuLator = overbalanceBeforeLong - moneyTransferLong - amountLong;
	verifyEquals(overbalanceAfterLong, overbalanceAfterCacuLator);

	log.info("TC_02_Step_38 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_03_ChuyenTienQuaCMTNguoiNhanTraPhiUSDXacNhanMatKhau() {
	log.info("TC_03_Step_1: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_03_Step_2: chon tai khoan");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

	homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_USD);

	log.info("TC_03_Step_3: lay so tien truoc khi chuyen khoan");
	String overbalanceBefore = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceBeforeLong = convertMoneyToDouble(overbalanceBefore, "USD");

	log.info("TC_03_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_03_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_03_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_03_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_03_Step_8: noi cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_03_Step_9: nguoi tra phi giao dich");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

	log.info("TC_03_Step_10: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_USD, "Số tiền");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

	log.info("TC_03_Step_11: noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "Nội dung");

	log.info("TC_03_Step_12: noi dung");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_03_Step_13: xac nhan thong tin");
	String confirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
	verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

	log.info("TC_03_Step_14: kiem tra tai khoan nguon");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tài khoản nguồn"), TransferIdentity_Data.textDataInputForm.ACCOUNT_USD);

	log.info("TC_03_Step_15: kiem tra ten nguoi thu huong");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tên người hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_03_Step_16: kiem tra giay to tuy than");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Giấy tờ tùy thân"), "Chứng minh nhân dân");

	log.info("TC_03_Step_17: kiem tra so CMT");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

	log.info("TC_03_Step_18: kiem tra ngay cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Ngày cấp"), today);

	log.info("TC_03_Step_19: kiem tra noi cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_03_Step_20: lay ra so tien chuyen di");
	String moneyTransfer = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền(USD)");
	double moneyTransferLong = convertMoneyToDouble(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);
	double moneyTransferLongToString = convertMoneyToDouble(moneyTransfer, "USD");
	String moneyTransferLongToStringFormat = String.format("%.0f", moneyTransferLongToString);

	log.info("TC_03_Step_21: kiem tra so tien");
	verifyEquals(moneyTransferLongToStringFormat, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_USD);

	log.info("TC_03_Step_22: lay phi");
	String amount = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền phí");
	double amountLong = convertMoneyToDouble(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);
	String amountLongToString = String.valueOf(amountLong);

	log.info("TC_03_Step_23: kiem tra noi dung");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nội dung"), TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER);


	trasferPage.scrollDownToButton(driver, "Tiếp tục");
	log.info("TC_03_Step_24: chon phuong thuc xac thuc");
	trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");


	log.info("TC_03_Step_25: lay phi giao dich theo phuong thuc xac thuc");
	String moneyAuthen = trasferPage.getDynamicAmountLabelConvertVNDToLong(driver, "Mật khẩu đăng nhập");
	double moneyAuthenLong = convertMoneyToDouble(moneyAuthen, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);
	String moneyAuthenLongToString = String.valueOf(amountLong);

	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

	log.info("TC_03_Step_26: kiem tra phi giao dich");
	verifyEquals(moneyAuthenLongToString, amountLongToString);

	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_03_Step_28: xac thuc giao dich");
	trasferPage.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_03_Step_29: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
	String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	String transferMoneyLong = transferMoney.replaceAll("USD", "");
	double transferMoneyLongString = convertMoneyToDouble(transferMoneyLong, "USD");
	String transferMoneyLongToStringFormat = String.format("%.0f", transferMoneyLongString);

	log.info("TC_03_Step_29: kiem tra so tien chuyen di o man hinh xac thuc thanh cong");
	verifyEquals(transferMoneyLongToStringFormat, moneyTransferLongToStringFormat);

	log.info("TC_03_Step_30: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
	transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

	log.info("TC_03_Step_31: ten nguoi thu huong");
	String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
	verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

	log.info("TC_03_Step_32: tai khoan giao dich");
	String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
	verifyTrue(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER.equals(destinationAccount));

	log.info("TC_03_Step_33: ma giao dich");
	transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_03_Step_34: noi dung giao dich");
	String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);

	verifyEquals(TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, conten);

	log.info("TC_03_Step_35: xac thuc thuc hien giao dich moi");
	String newDealConfirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
	verifyEquals(newDealConfirm, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_03_Step_36: so tien kha dung con lai trong tai khoan");
	String overbalanceAfter = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceAfterLong = convertMoneyToDouble(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);

	log.info("TC_03_Step_37: kiem tra so tien kha dung sau khi chuyen");
	double overbalanceAfterCacuLator = overbalanceBeforeLong - moneyTransferLong - amountLong;
	verifyEquals(overbalanceAfterLong, overbalanceAfterCacuLator);

	log.info("TC_03_Step_38 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_04_ChuyenTienQuaCMTNguoiChuyenTraPhiVNDRXacNhanOTP() {
	log.info("TC_04_Step_1: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_04_Step_2: chon tai khoan");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

	homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

	log.info("TC_04_Step_3: lay so tien truoc khi chuyen khoan");
	String overbalanceBefore = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceBeforeLong = convertMoneyToDouble(overbalanceBefore, "VND");

	log.info("TC_04_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_04_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_04_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_04_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_04_Step_8: noi cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_04_Step_9: nguoi tra phi giao dich");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

	log.info("TC_04_Step_10: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

	log.info("TC_04_Step_11: noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "Nội dung");

	log.info("TC_04_Step_12: noi dung");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_04_Step_13: xac nhan thong tin");
	String confirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
	verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

	log.info("TC_04_Step_14: kiem tra tai khoan nguon");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tài khoản nguồn"), TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

	log.info("TC_04_Step_15: kiem tra ten nguoi thu huong");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tên người hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_04_Step_16: kiem tra giay to tuy than");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Giấy tờ tùy thân"), "Chứng minh nhân dân");

	log.info("TC_04_Step_17: kiem tra so CMT");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

	log.info("TC_04_Step_18: kiem tra ngay cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Ngày cấp"), today);

	log.info("TC_04_Step_19: kiem tra noi cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_04_Step_20: lay ra so tien chuyen di");
	String moneyTransfer = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền");
	double moneyTransferLong = convertMoneyToDouble(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
	double moneyTransferLongToString = convertMoneyToDouble(moneyTransfer, "VND");
	String moneyTransferLongToStringFormat = String.format("%.0f", moneyTransferLongToString);

	log.info("TC_04_Step_21: kiem tra so tien");
	verifyEquals(moneyTransferLongToStringFormat, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND);

	log.info("TC_04_Step_22: lay phi");
	String amount = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền phí");
	double amountLong = convertMoneyToDouble(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
	String amountLongToString = String.valueOf(amountLong);

	log.info("TC_04_Step_23: kiem tra noi dung");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nội dung"), TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER);

	log.info("TC_04_Step_24: chon phuong thuc xac thuc");
	trasferPage.scrollDownToButton(driver, "Tiếp tục");
	trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

	log.info("TC_04_Step_25: lay phi giao dich theo phuong thuc xac thuc");
	String moneyAuthen = trasferPage.getDynamicAmountLabelConvertVNDToLong(driver, "SMS OTP");
	double moneyAuthenLong = convertMoneyToDouble(moneyAuthen, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
	String moneyAuthenLongToString = String.valueOf(amountLong);

	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

	log.info("TC_04_Step_26: kiem tra phi giao dich");
	verifyEquals(moneyAuthenLongToString, amountLongToString);

	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_04_Step_28: xac thuc giao dich");
	trasferPage.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_04_Step_29: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
	String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	String transferMoneyLong = transferMoney.replaceAll("VND", "");
	double transferMoneyLongString = convertMoneyToDouble(transferMoneyLong, "VND");
	String transferMoneyLongToStringFormat = String.format("%.0f", transferMoneyLongString);

	log.info("TC_04_Step_29: kiem tra so tien chuyen di o man hinh xac thuc thanh cong");
	verifyEquals(transferMoneyLongToStringFormat, moneyTransferLongToStringFormat);

	log.info("TC_04_Step_30: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
	transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

	log.info("TC_04_Step_31: ten nguoi thu huong");
	String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
	verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

	log.info("TC_04_Step_32: tai khoan giao dich");
	String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
	verifyTrue(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER.equals(destinationAccount));

	log.info("TC_04_Step_33: ma giao dich");
	transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_04_Step_34: noi dung giao dich");
	String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);

	verifyEquals(TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, conten);

	log.info("TC_04_Step_35: xac thuc thuc hien giao dich moi");
	String newDealConfirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
	verifyEquals(newDealConfirm, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_7_Step_36: so tien kha dung con lai trong tai khoan");
	String overbalanceAfter = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceAfterLong = convertMoneyToDouble(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);

	log.info("TC_04_Step_37: kiem tra so tien kha dung sau khi chuyen");
	double overbalanceAfterCacuLator = overbalanceBeforeLong - moneyTransferLong - amountLong;
	verifyEquals(overbalanceAfterLong, overbalanceAfterCacuLator);

	log.info("TC_04_Step_39 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");

    }

    @Test
    public void TC_05_ChuyenTienQuaCMTNguoiNhaTraPhiUSDXacNhanOTP() {
	log.info("TC_05_Step_1: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_05_Step_2: chon tai khoan");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

	homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_EUR);

	log.info("TC_05_Step_3: lay so tien truoc khi chuyen khoan");
	String overbalanceBefore = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceBeforeLong = convertMoneyToDouble(overbalanceBefore, "EUR");

	log.info("TC_05_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_05_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_05_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_05_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_05_Step_8: noi cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_05_Step_9: nguoi tra phi giao dich");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

	log.info("TC_05_Step_10: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR, "Số tiền");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

	log.info("TC_05_Step_11: noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "Nội dung");

	log.info("TC_05_Step_12: noi dung");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_05_Step_13: xac nhan thong tin");
	String confirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
	verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

	log.info("TC_05_Step_14: kiem tra tai khoan nguon");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tài khoản nguồn"), TransferIdentity_Data.textDataInputForm.ACCOUNT_EUR);

	log.info("TC_05_Step_15: kiem tra ten nguoi thu huong");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tên người hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_05_Step_16: kiem tra giay to tuy than");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Giấy tờ tùy thân"), "Chứng minh nhân dân");

	log.info("TC_05_Step_17: kiem tra so CMT");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

	log.info("TC_05_Step_18: kiem tra ngay cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Ngày cấp"), today);

	log.info("TC_05_Step_19: kiem tra noi cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_05_Step_20: lay ra so tien chuyen di");
	String moneyTransfer = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền(EUR)");
	double moneyTransferDouble = convertMoneyToDouble(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);
	double moneyTransferLongToString = convertMoneyToDouble(moneyTransfer, "EUR");
	String moneyTransferLongToStringFormat = String.format("%.0f", moneyTransferLongToString);

	log.info("TC_05_Step_21: kiem tra so tien");
	verifyEquals(moneyTransferLongToStringFormat, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR);

	log.info("TC_05_Step_22: lay phi");
	String amount = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền phí");
	double amountLong = convertMoneyToDouble(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);
	String amountLongToString = String.valueOf(amountLong);

	log.info("TC_05_Step_23: kiem tra noi dung");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nội dung"), TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER);

	trasferPage.scrollDownToButton(driver, "Tiếp tục");
	log.info("TC_05_Step_24: chon phuong thuc xac thuc");
	trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

	log.info("TC_05_Step_25: lay phi giao dich theo phuong thuc xac thuc");
	String moneyAuthen = trasferPage.getDynamicAmountLabelConvertVNDToLong(driver, "SMS OTP");
	double moneyAuthenLong = convertMoneyToDouble(moneyAuthen, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);
	String moneyAuthenLongToString = String.valueOf(amountLong);

	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

	log.info("TC_05_Step_26: kiem tra phi giao dich");
	verifyEquals(moneyAuthenLongToString, amountLongToString);

	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_05_Step_28: xac thuc giao dich");
	trasferPage.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_05_Step_29: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
	String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	String transferMoneyLong = transferMoney.replaceAll("EUR", "");
	double transferMoneyLongString = convertMoneyToDouble(transferMoneyLong, "EUR");
	String transferMoneyLongToStringFormat = String.format("%.0f", transferMoneyLongString);

	log.info("TC_05_Step_29: kiem tra so tien chuyen di o man hinh xac thuc thanh cong");
	verifyEquals(transferMoneyLongToStringFormat, moneyTransferLongToStringFormat);

	log.info("TC_05_Step_30: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
	transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

	log.info("TC_05_Step_31: ten nguoi thu huong");
	String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
	verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

	log.info("TC_05_Step_32: tai khoan giao dich");
	String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
	verifyTrue(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER.equals(destinationAccount));

	log.info("TC_05_Step_33: ma giao dich");
	transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_05_Step_34: noi dung giao dich");
	String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);

	verifyEquals(TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, conten);

	log.info("TC_05_Step_35: xac thuc thuc hien giao dich moi");
	String newDealConfirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
	verifyEquals(newDealConfirm, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_05_Step_36: so tien kha dung con lai trong tai khoan");
	String overbalanceAfter = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceAfterLong = convertMoneyToDouble(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);

//		log.info("TC_05_Step_37: kiem tra so tien kha dung sau khi chuyen");
//		double overbalanceAfterCacuLator = overbalanceBeforeLong - moneyTransferDouble - amountLong;
//		verifyEquals(overbalanceAfterLong, overbalanceAfterCacuLator);

	log.info("TC_05_Step_38 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_06_ChuyenTienQuaCMTNguoiChuyenTraPhiUSDXacNhanOTP() {
	log.info("TC_06_Step_1: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_06_Step_2: chon tai khoan");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

	homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_USD);

	log.info("TC_06_Step_3: lay so tien truoc khi chuyen khoan");
	String overbalanceBefore = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceBeforeLong = convertMoneyToDouble(overbalanceBefore, "USD");

	log.info("TC_06_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_06_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_06_Step_6: so CMT");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_06_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_06_Step_8: noi cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_06_Step_9: nguoi tra phi giao dich");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

	log.info("TC_06_Step_10: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_USD, "Số tiền");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

	log.info("TC_06_Step_11: noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "Nội dung");

	log.info("TC_06_Step_12: noi dung");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_06_Step_13: xac nhan thong tin");
	String confirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
	verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

	log.info("TC_06_Step_14: kiem tra tai khoan nguon");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tài khoản nguồn"), TransferIdentity_Data.textDataInputForm.ACCOUNT_USD);

	log.info("TC_06_Step_15: kiem tra ten nguoi thu huong");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tên người hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_06_Step_16: kiem tra giay to tuy than");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Giấy tờ tùy thân"), "Chứng minh nhân dân");

	log.info("TC_06_Step_17: kiem tra so CMT");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

	log.info("TC_06_Step_18: kiem tra ngay cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Ngày cấp"), today);

	log.info("TC_06_Step_19: kiem tra noi cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_06_Step_20: lay ra so tien chuyen di");
	String moneyTransfer = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền(USD)");
	double moneyTransferLong = convertMoneyToDouble(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);
	double moneyTransferLongToString = convertMoneyToDouble(moneyTransfer, "USD");
	String moneyTransferLongToStringFormat = String.format("%.0f", moneyTransferLongToString);

	log.info("TC_06_Step_21: kiem tra so tien");
	verifyEquals(moneyTransferLongToStringFormat, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_USD);

	log.info("TC_06_Step_22: lay phi");
	String amount = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền phí");
	double amountLong = convertMoneyToDouble(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);
	String amountLongToString = String.valueOf(amountLong);

	log.info("TC_06_Step_23: kiem tra noi dung");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nội dung"), TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER);

	trasferPage.scrollDownToButton(driver, "Tiếp tục");
	log.info("TC_06_Step_24: chon phuong thuc xac thuc");
	trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

	log.info("TC_06_Step_25: lay phi giao dich theo phuong thuc xac thuc");
	String moneyAuthen = trasferPage.getDynamicAmountLabelConvertVNDToLong(driver, "SMS OTP");
	double moneyAuthenLong = convertMoneyToDouble(moneyAuthen, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);
	String moneyAuthenLongToString = String.valueOf(amountLong);

	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

	log.info("TC_06_Step_26: kiem tra phi giao dich");
	verifyEquals(moneyAuthenLongToString, amountLongToString);

	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_06_Step_28: xac thuc giao dich");
	trasferPage.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_06_Step_29: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
	String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	String transferMoneyLong = transferMoney.replaceAll("USD", "");
	double transferMoneyLongString = convertMoneyToDouble(transferMoneyLong, "USD");
	String transferMoneyLongToStringFormat = String.format("%.0f", transferMoneyLongString);

	log.info("TC_06_Step_29: kiem tra so tien chuyen di o man hinh xac thuc thanh cong");
	verifyEquals(transferMoneyLongToStringFormat, moneyTransferLongToStringFormat);

	log.info("TC_06_Step_30: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
	transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

	log.info("TC_06_Step_31: ten nguoi thu huong");
	String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
	verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

	log.info("TC_06_Step_32: tai khoan giao dich");
	String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
	verifyTrue(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER.equals(destinationAccount));

	log.info("TC_06_Step_33: ma giao dich");
	transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_06_Step_34: noi dung giao dich");
	String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);

	verifyEquals(TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, conten);

	log.info("TC_06_Step_35: xac thuc thuc hien giao dich moi");
	String newDealConfirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
	verifyEquals(newDealConfirm, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_06_Step_36: so tien kha dung con lai trong tai khoan");
	String overbalanceAfter = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceAfterLong = convertMoneyToDouble(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);

//	log.info("TC_06_Step_37: kiem tra so tien kha dung sau khi chuyen");
//	double overbalanceAfterCacuLator = overbalanceBeforeLong - moneyTransferLong - amountLong;
//	verifyEquals(overbalanceAfterLong, overbalanceAfterCacuLator);

	log.info("TC_06_Step_38 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_07_ChuyenTienQuaHCNguoiChuyenTraPhiVNDXacNhanMatKhau() {
	log.info("TC_07_Step_1: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_07_Step_2: chon tai khoan");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

	homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

	log.info("TC_07_Step_3: lay so tien truoc khi chuyen khoan");
	String overbalanceBefore = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	Long overbalanceBeforeLong = convertMoneyToLong(overbalanceBefore, "VND");

	log.info("TC_07_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_07_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

	log.info("TC_07_Step_6: so HC");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER, "Số");

	log.info("TC_07_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_07_Step_8: noi cap");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.ISSUED, "Nơi cấp");

	log.info("TC_07_Step_9: nguoi tra phi giao dich");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

	log.info("TC_07_Step_10: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

	log.info("TC_07_Step_11: noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "Nội dung");

	log.info("TC_07_Step_12: noi dung");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_07_Step_13: xac nhan thong tin");
	String confirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
	verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

	log.info("TC_07_Step_14: kiem tra tai khoan nguon");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tài khoản nguồn"), TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

	log.info("TC_07_Step_15: kiem tra ten nguoi thu huong");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tên người hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_07_Step_16: kiem tra giay to tuy than");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Giấy tờ tùy thân"), "Hộ chiếu");

	log.info("TC_07_Step_17: kiem tra so HC");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER);

	log.info("TC_07_Step_18: kiem tra ngay cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Ngày cấp"), today);

	log.info("TC_07_Step_19: kiem tra noi cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_07_Step_20: lay ra so tien chuyen di");
	String moneyTransfer = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền");
	Long moneyTransferLong = convertMoneyToLong(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
	String moneyTransferLongToString = Long.toString(moneyTransferLong);

	log.info("TC_07_Step_21: kiem tra so tien");
	verifyEquals(moneyTransferLongToString, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND);

	log.info("TC_07_Step_22: lay phi");
	String amount = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền phí");
	Long amountLong = convertMoneyToLong(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
	String amountLongToString = Long.toString(amountLong);

	log.info("TC_07_Step_23: kiem tra noi dung");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nội dung"), TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER);

	trasferPage.scrollDownToButton(driver, "Tiếp tục");
	log.info("TC_07_Step_24: chon phuong thuc xac thuc");
	trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

	log.info("TC_07_Step_25: lay phi giao dich theo phuong thuc xac thuc");
	String moneyAuthen = trasferPage.getDynamicAmountLabelConvertVNDToLong(driver, "Mật khẩu đăng nhập");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

	log.info("TC_07_Step_26: kiem tra phi giao dich");
	verifyEquals(moneyAuthen, amountLongToString);

	log.info("TC_07_Step_27: kiem tra so tien phi");
	verifyEquals(amountLongToString, TransferIdentity_Data.textCheckElement.AMOUNT);

	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_07_Step_28: xac thuc giao dich");
	trasferPage.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_07_Step_29: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
	String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	verifyTrue(moneyTransfer.equals(transferMoney));

	log.info("TC_07_Step_30: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
	transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

	log.info("TC_07_Step_31: ten nguoi thu huong");
	String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
	verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

	log.info("TC_07_Step_32: tai khoan giao dich");
	String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
	verifyTrue(TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER.equals(destinationAccount));

	log.info("TC_07_Step_33: ma giao dich");
	transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_07_Step_34: noi dung giao dich");
	String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);
	verifyTrue(TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER.equals(conten));

	log.info("TC_07_Step_35: xac thuc thuc hien giao dich moi");
	String newDealConfirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
	verifyTrue(newDealConfirm.equals(TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS));
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_07_Step_36: so tien kha dung con lai trong tai khoan");
	String overbalanceAfter = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	Long overbalanceAfterLong = convertMoneyToLong(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);

//	log.info("TC_07_Step_37: kiem tra so tien kha dung sau khi chuyen");
//	long overbalanceAfterCacuLator = overbalanceBeforeLong - moneyTransferLong - amountLong;
//	verifyEquals(overbalanceAfterLong, overbalanceAfterCacuLator);

	log.info("TC_07_Step_38 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");

    }

    @Test
    public void TC_08_ChuyenTienQuaHCNguoiNhanTraPhiUSDXacNhanMatKhau() {
	log.info("TC_08_Step_1: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_08_Step_2: chon tai khoan");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

	homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_USD);

	log.info("TC_08_Step_3: lay so tien truoc khi chuyen khoan");
	String overbalanceBefore = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceBeforeLong = convertMoneyToDouble(overbalanceBefore, "USD");

	log.info("TC_08_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_08_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

	log.info("TC_08_Step_6: so HC");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER, "Số");

	log.info("TC_08_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_08_Step_8: noi cap");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.ISSUED, "Nơi cấp");

	log.info("TC_08_Step_9: nguoi tra phi giao dich");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

	log.info("TC_08_Step_10: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_USD, "Số tiền");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

	log.info("TC_08_Step_11: noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "Nội dung");

	log.info("TC_08_Step_12: noi dung");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_08_Step_13: xac nhan thong tin");
	String confirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
	verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

	log.info("TC_08_Step_14: kiem tra tai khoan nguon");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tài khoản nguồn"), TransferIdentity_Data.textDataInputForm.ACCOUNT_USD);

	log.info("TC_08_Step_15: kiem tra ten nguoi thu huong");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tên người hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_08_Step_16: kiem tra giay to tuy than");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Giấy tờ tùy thân"), "Hộ chiếu");

	log.info("TC_08_Step_17: kiem tra so HC");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER);

	log.info("TC_08_Step_18: kiem tra ngay cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Ngày cấp"), today);

	log.info("TC_08_Step_19: kiem tra noi cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_08_Step_20: lay ra so tien chuyen di");
	String moneyTransfer = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền(USD)");
	double moneyTransferLong = convertMoneyToDouble(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);
	double moneyTransferLongToString = convertMoneyToDouble(moneyTransfer, "USD");
	String moneyTransferLongToStringFormat = String.format("%.0f", moneyTransferLongToString);

	log.info("TC_08_Step_21: kiem tra so tien");
	verifyEquals(moneyTransferLongToStringFormat, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_USD);

	log.info("TC_08_Step_22: lay phi");
	String amount = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền phí");
	double amountLong = convertMoneyToDouble(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);
	String amountLongToString = String.valueOf(amountLong);

	log.info("TC_08_Step_23: kiem tra noi dung");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nội dung"), TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER);

	trasferPage.scrollDownToButton(driver, "Tiếp tục");
	log.info("TC_08_Step_24: chon phuong thuc xac thuc");
	trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

	log.info("TC_08_Step_25: lay phi giao dich theo phuong thuc xac thuc");
	String moneyAuthen = trasferPage.getDynamicAmountLabelConvertVNDToLong(driver, "Mật khẩu đăng nhập");
	double moneyAuthenLong = convertMoneyToDouble(moneyAuthen, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);
	String moneyAuthenLongToString = String.valueOf(amountLong);

	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

	log.info("TC_08_Step_26: kiem tra phi giao dich");
	verifyEquals(moneyAuthenLongToString, amountLongToString);

	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_08_Step_28: xac thuc giao dich");
	trasferPage.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_08_Step_29: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
	String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	String transferMoneyLong = transferMoney.replaceAll("USD", "");
	double transferMoneyLongString = convertMoneyToDouble(transferMoneyLong, "USD");
	String transferMoneyLongToStringFormat = String.format("%.0f", transferMoneyLongString);

	log.info("TC_08_Step_29: kiem tra so tien chuyen di o man hinh xac thuc thanh cong");
	verifyEquals(transferMoneyLongToStringFormat, moneyTransferLongToStringFormat);

	log.info("TC_08_Step_30: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
	transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

	log.info("TC_08_Step_31: ten nguoi thu huong");
	String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
	verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

	log.info("TC_08_Step_32: tai khoan giao dich");
	String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
	verifyTrue(TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER.equals(destinationAccount));

	log.info("TC_08_Step_33: ma giao dich");
	transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_08_Step_34: noi dung giao dich");
	String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);
	verifyEquals(TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, conten);

	log.info("TC_08_Step_35: xac thuc thuc hien giao dich moi");
	String newDealConfirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
	verifyEquals(newDealConfirm, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_08_Step_36: so tien kha dung con lai trong tai khoan");
	String overbalanceAfter = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceAfterLong = convertMoneyToDouble(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);

//	log.info("TC_08_Step_37: kiem tra so tien kha dung sau khi chuyen");
//	double overbalanceAfterCacuLator = overbalanceBeforeLong - moneyTransferLong - amountLong;
//	verifyEquals(overbalanceAfterLong, overbalanceAfterCacuLator);

	log.info("TC_08_Step_38 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");

    }

    @Test
    public void TC_09_ChuyenTienQuaHCNguoiNhanTraPhiEURXacNhanMatKhau() {
	log.info("TC_09_Step_1: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_09_Step_2: chon tai khoan");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

	homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_EUR);

	log.info("TC_09_Step_3: lay so tien truoc khi chuyen khoan");
	String overbalanceBefore = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceBeforeLong = convertMoneyToDouble(overbalanceBefore, "EUR");

	log.info("TC_09_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_09_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

	log.info("TC_09_Step_6: so HC");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER, "Số");

	log.info("TC_09_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_09_Step_8: noi cap");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.ISSUED, "Nơi cấp");

	log.info("TC_09_Step_9: nguoi tra phi giao dich");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

	log.info("TC_09_Step_10: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR, "Số tiền");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

	log.info("TC_09_Step_11: noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "Nội dung");

	log.info("TC_09_Step_12: noi dung");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_09_Step_13: xac nhan thong tin");
	String confirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
	verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

	log.info("TC_09_Step_14: kiem tra tai khoan nguon");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tài khoản nguồn"), TransferIdentity_Data.textDataInputForm.ACCOUNT_EUR);

	log.info("TC_09_Step_15: kiem tra ten nguoi thu huong");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tên người hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_09_Step_16: kiem tra giay to tuy than");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Giấy tờ tùy thân"), "Hộ chiếu");

	log.info("TC_09_Step_17: kiem tra so HC");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER);

	log.info("TC_09_Step_18: kiem tra ngay cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Ngày cấp"), today);

	log.info("TC_09_Step_19: kiem tra noi cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_09_Step_20: lay ra so tien chuyen di");
	String moneyTransfer = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền(EUR)");
	double moneyTransferLong = convertMoneyToDouble(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);
	double moneyTransferLongToString = convertMoneyToDouble(moneyTransfer, "EUR");
	String moneyTransferLongToStringFormat = String.format("%.0f", moneyTransferLongToString);

	log.info("TC_09_Step_21: kiem tra so tien");
	verifyEquals(moneyTransferLongToStringFormat, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR);

	log.info("TC_09_Step_22: lay phi");
	String amount = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền phí");
	double amountLong = convertMoneyToDouble(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);
	String amountLongToString = String.valueOf(amountLong);

	log.info("TC_09_Step_23: kiem tra noi dung");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nội dung"), TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER);

	trasferPage.scrollDownToButton(driver, "Tiếp tục");

	log.info("TC_09_Step_24: chon phuong thuc xac thuc");
	trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

	log.info("TC_09_Step_25: lay phi giao dich theo phuong thuc xac thuc");
	String moneyAuthen = trasferPage.getDynamicAmountLabelConvertVNDToLong(driver, "Mật khẩu đăng nhập");
	double moneyAuthenLong = convertMoneyToDouble(moneyAuthen, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);
	String moneyAuthenLongToString = String.valueOf(amountLong);

	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

	log.info("TC_09_Step_26: kiem tra phi giao dich");
	verifyEquals(moneyAuthenLongToString, amountLongToString);

	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_09_Step_28: xac thuc giao dich");
	trasferPage.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_09_Step_29: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
	String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	String transferMoneyLong = transferMoney.replaceAll("EUR", "");
	double transferMoneyLongString = convertMoneyToDouble(transferMoneyLong, "EUR");
	String transferMoneyLongToStringFormat = String.format("%.0f", transferMoneyLongString);

	log.info("TC_09_Step_29: kiem tra so tien chuyen di o man hinh xac thuc thanh cong");
	verifyEquals(transferMoneyLongToStringFormat, moneyTransferLongToStringFormat);

	log.info("TC_09_Step_30: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
	transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

	log.info("TC_09_Step_31: ten nguoi thu huong");
	String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
	verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

	log.info("TC_09_Step_32: tai khoan giao dich");
	String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
	verifyTrue(TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER.equals(destinationAccount));

	log.info("TC_09_Step_33: ma giao dich");
	transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_09_Step_34: noi dung giao dich");
	String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);

	verifyEquals(TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, conten);

	log.info("TC_09_Step_35: xac thuc thuc hien giao dich moi");
	String newDealConfirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
	verifyEquals(newDealConfirm, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_09_Step_36: so tien kha dung con lai trong tai khoan");
	String overbalanceAfter = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceAfterLong = convertMoneyToDouble(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);

//	log.info("TC_09_Step_37: kiem tra so tien kha dung sau khi chuyen");
//	double overbalanceAfterCacuLator = overbalanceBeforeLong - moneyTransferLong - amountLong;
//	verifyEquals(overbalanceAfterLong, overbalanceAfterCacuLator);

	log.info("TC_09_Step_38 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");

    }

    @Test
    public void TC_10_ChuyenTienQuaHCNguoiChuyenTraPhiVNDRXacNhanOTP() {
	log.info("TC_10_Step_1: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_10_Step_2: chon tai khoan");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

	homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

	log.info("TC_10_Step_3: lay so tien truoc khi chuyen khoan");
	String overbalanceBefore = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceBeforeLong = convertMoneyToDouble(overbalanceBefore, "VND");

	log.info("TC_10_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_10_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

	log.info("TC_10_Step_6: so HC");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER, "Số");

	log.info("TC_10_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_10_Step_8: noi cap");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.ISSUED, "Nơi cấp");

	log.info("TC_10_Step_9: nguoi tra phi giao dich");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

	log.info("TC_10_Step_10: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

	log.info("TC_10_Step_11: noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "Nội dung");

	log.info("TC_10_Step_12: noi dung");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_10_Step_13: xac nhan thong tin");
	String confirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
	verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

	log.info("TC_10_Step_14: kiem tra tai khoan nguon");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tài khoản nguồn"), TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

	log.info("TC_10_Step_15: kiem tra ten nguoi thu huong");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tên người hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_10_Step_16: kiem tra giay to tuy than");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Giấy tờ tùy thân"), "Hộ chiếu");

	log.info("TC_10_Step_17: kiem tra so HC");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER);

	log.info("TC_10_Step_18: kiem tra ngay cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Ngày cấp"), today);

	log.info("TC_10_Step_19: kiem tra noi cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_10_Step_20: lay ra so tien chuyen di");
	String moneyTransfer = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền");
	double moneyTransferLong = convertMoneyToDouble(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
	double moneyTransferLongToString = convertMoneyToDouble(moneyTransfer, "VND");
	String moneyTransferLongToStringFormat = String.format("%.0f", moneyTransferLongToString);

	log.info("TC_10_Step_21: kiem tra so tien");
	verifyEquals(moneyTransferLongToStringFormat, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND);

	log.info("TC_10_Step_22: lay phi");
	String amount = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền phí");
	double amountLong = convertMoneyToDouble(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
	String amountLongToString = String.valueOf(amountLong);

	log.info("TC_10_Step_23: kiem tra noi dung");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nội dung"), TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER);

	log.info("TC_10_Step_24: chon phuong thuc xac thuc");
	trasferPage.scrollDownToButton(driver, "Tiếp tục");
	trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

	log.info("TC_10_Step_25: lay phi giao dich theo phuong thuc xac thuc");
	String moneyAuthen = trasferPage.getDynamicAmountLabelConvertVNDToLong(driver, "SMS OTP");
	double moneyAuthenLong = convertMoneyToDouble(moneyAuthen, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
	String moneyAuthenLongToString = String.valueOf(amountLong);

	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

	log.info("TC_10_Step_26: kiem tra phi giao dich");
	verifyEquals(moneyAuthenLongToString, amountLongToString);

	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_10_Step_28: xac thuc giao dich");
	trasferPage.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_10_Step_29: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
	String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	String transferMoneyLong = transferMoney.replaceAll("VND", "");
	double transferMoneyLongString = convertMoneyToDouble(transferMoneyLong, "VND");
	String transferMoneyLongToStringFormat = String.format("%.0f", transferMoneyLongString);

	log.info("TC_10_Step_29: kiem tra so tien chuyen di o man hinh xac thuc thanh cong");
	verifyEquals(transferMoneyLongToStringFormat, moneyTransferLongToStringFormat);

	log.info("TC_10_Step_30: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
	transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

	log.info("TC_10_Step_31: ten nguoi thu huong");
	String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
	verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

	log.info("TC_10_Step_32: tai khoan giao dich");
	String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
	verifyTrue(TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER.equals(destinationAccount));

	log.info("TC_10_Step_33: ma giao dich");
	transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_10_Step_34: noi dung giao dich");
	String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);

	verifyEquals(TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, conten);

	log.info("TC_10_Step_35: xac thuc thuc hien giao dich moi");
	String newDealConfirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
	verifyEquals(newDealConfirm, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_7_Step_36: so tien kha dung con lai trong tai khoan");
	String overbalanceAfter = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceAfterLong = convertMoneyToDouble(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);

//	log.info("TC_10_Step_37: kiem tra so tien kha dung sau khi chuyen");
//	double overbalanceAfterCacuLator = overbalanceBeforeLong - moneyTransferLong - amountLong;
//	verifyEquals(overbalanceAfterLong, overbalanceAfterCacuLator);

	log.info("TC_10_Step_38 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");

    }

    @Test
    public void TC_11_ChuyenTienQuaHCTNguoiChuyenTraPhiUSDXacNhanOTP() {
	log.info("TC_11_Step_1: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_11_Step_2: chon tai khoan");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

	homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_USD);

	log.info("TC_11_Step_3: lay so tien truoc khi chuyen khoan");
	String overbalanceBefore = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceBeforeLong = convertMoneyToDouble(overbalanceBefore, "USD");

	log.info("TC_11_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_11_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

	log.info("TC_11_Step_6: so HC");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER, "Số");

	log.info("TC_11_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_11_Step_8: noi cap");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.ISSUED, "Nơi cấp");

	log.info("TC_11_Step_9: nguoi tra phi giao dich");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

	log.info("TC_11_Step_10: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_USD, "Số tiền");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

	log.info("TC_11_Step_11: noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "Nội dung");

	log.info("TC_11_Step_12: noi dung");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_11_Step_13: xac nhan thong tin");
	String confirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
	verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

	log.info("TC_11_Step_14: kiem tra tai khoan nguon");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tài khoản nguồn"), TransferIdentity_Data.textDataInputForm.ACCOUNT_USD);

	log.info("TC_11_Step_15: kiem tra ten nguoi thu huong");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tên người hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_11_Step_16: kiem tra giay to tuy than");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Giấy tờ tùy thân"), "Hộ chiếu");

	log.info("TC_11_Step_17: kiem tra so HC");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER);

	log.info("TC_11_Step_18: kiem tra ngay cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Ngày cấp"), today);

	log.info("TC_11_Step_19: kiem tra noi cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_11_Step_20: lay ra so tien chuyen di");
	String moneyTransfer = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền(USD)");
	double moneyTransferLong = convertMoneyToDouble(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);
	double moneyTransferLongToString = convertMoneyToDouble(moneyTransfer, "USD");
	String moneyTransferLongToStringFormat = String.format("%.0f", moneyTransferLongToString);

	log.info("TC_11_Step_21: kiem tra so tien");
	verifyEquals(moneyTransferLongToStringFormat, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_USD);

	log.info("TC_11_Step_22: lay phi");
	String amount = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền phí");
	double amountLong = convertMoneyToDouble(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);
	String amountLongToString = String.valueOf(amountLong);

	log.info("TC_11_Step_23: kiem tra noi dung");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nội dung"), TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER);

	trasferPage.scrollDownToButton(driver, "Tiếp tục");

	log.info("TC_11_Step_24: chon phuong thuc xac thuc");
	trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

	log.info("TC_11_Step_25: lay phi giao dich theo phuong thuc xac thuc");
	String moneyAuthen = trasferPage.getDynamicAmountLabelConvertVNDToLong(driver, "SMS OTP");
	double moneyAuthenLong = convertMoneyToDouble(moneyAuthen, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);
	String moneyAuthenLongToString = String.valueOf(amountLong);

	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

	log.info("TC_11_Step_26: kiem tra phi giao dich");
	verifyEquals(moneyAuthenLongToString, amountLongToString);

	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_11_Step_28: xac thuc giao dich");
	trasferPage.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_11_Step_29: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
	String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	String transferMoneyLong = transferMoney.replaceAll("USD", "");
	double transferMoneyLongString = convertMoneyToDouble(transferMoneyLong, "USD");
	String transferMoneyLongToStringFormat = String.format("%.0f", transferMoneyLongString);

	log.info("TC_11_Step_29: kiem tra so tien chuyen di o man hinh xac thuc thanh cong");
	verifyEquals(transferMoneyLongToStringFormat, moneyTransferLongToStringFormat);

	log.info("TC_11_Step_30: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
	transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

	log.info("TC_11_Step_31: ten nguoi thu huong");
	String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
	verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

	log.info("TC_11_Step_32: tai khoan giao dich");
	String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
	verifyTrue(TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER.equals(destinationAccount));

	log.info("TC_11_Step_33: ma giao dich");
	transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_11_Step_34: noi dung giao dich");
	String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);

	verifyEquals(TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, conten);

	log.info("TC_11_Step_35: xac thuc thuc hien giao dich moi");
	String newDealConfirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
	verifyEquals(newDealConfirm, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_11_Step_36: so tien kha dung con lai trong tai khoan");
	String overbalanceAfter = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceAfterLong = convertMoneyToDouble(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);

//	log.info("TC_11_Step_37: kiem tra so tien kha dung sau khi chuyen");
//	double overbalanceAfterCacuLator = overbalanceBeforeLong - moneyTransferLong - amountLong;
//	verifyEquals(overbalanceAfterLong, overbalanceAfterCacuLator);

	log.info("TC_11_Step_38 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_12_ChuyenTienQuaHCNguoiNhanTraPhiEURXacNhanOTP() {
	log.info("TC_12_Step_1: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_12_Step_2: chon tai khoan");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

	homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_EUR);

	log.info("TC_12_Step_3: lay so tien truoc khi chuyen khoan");
	String overbalanceBefore = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceBeforeLong = convertMoneyToDouble(overbalanceBefore, "EUR");

	log.info("TC_12_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_12_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

	log.info("TC_12_Step_6: so HC");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER, "Số");

	log.info("TC_12_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_12_Step_8: noi cap");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.ISSUED, "Nơi cấp");

	log.info("TC_12_Step_9: nguoi tra phi giao dich");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

	log.info("TC_12_Step_10: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR, "Số tiền");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

	log.info("TC_12_Step_11: noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "Nội dung");

	log.info("TC_12_Step_12: noi dung");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_12_Step_13: xac nhan thong tin");
	String confirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
	verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

	log.info("TC_12_Step_14: kiem tra tai khoan nguon");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tài khoản nguồn"), TransferIdentity_Data.textDataInputForm.ACCOUNT_EUR);

	log.info("TC_12_Step_15: kiem tra ten nguoi thu huong");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Tên người hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_12_Step_16: kiem tra giay to tuy than");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Giấy tờ tùy thân"), "Hộ chiếu");

	log.info("TC_12_Step_17: kiem tra so HC");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER);

	log.info("TC_12_Step_18: kiem tra ngay cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Ngày cấp"), today);

	log.info("TC_12_Step_19: kiem tra noi cap");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_12_Step_20: lay ra so tien chuyen di");
	String moneyTransfer = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền(EUR)");
	double moneyTransferLong = convertMoneyToDouble(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);
	double moneyTransferLongToString = convertMoneyToDouble(moneyTransfer, "EUR");
	String moneyTransferLongToStringFormat = String.format("%.0f", moneyTransferLongToString);

	log.info("TC_12_Step_21: kiem tra so tien");
	verifyEquals(moneyTransferLongToStringFormat, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR);

	log.info("TC_12_Step_22: lay phi");
	String amount = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số tiền phí");
	double amountLong = convertMoneyToDouble(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);
	String amountLongToString = String.valueOf(amountLong);

	log.info("TC_12_Step_23: kiem tra noi dung");
	verifyEquals(trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Nội dung"), TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER);

	trasferPage.scrollDownToButton(driver, "Tiếp tục");

	log.info("TC_12_Step_24: chon phuong thuc xac thuc");
	trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

	log.info("TC_12_Step_25: lay phi giao dich theo phuong thuc xac thuc");
	String moneyAuthen = trasferPage.getDynamicAmountLabelConvertVNDToLong(driver, "SMS OTP");
	double moneyAuthenLong = convertMoneyToDouble(moneyAuthen, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);
	String moneyAuthenLongToString = String.valueOf(amountLong);

	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

	log.info("TC_12_Step_26: kiem tra phi giao dich");
	verifyEquals(moneyAuthenLongToString, amountLongToString);

	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_12_Step_28: xac thuc giao dich");
	trasferPage.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_12_Step_29: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
	String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	String transferMoneyLong = transferMoney.replaceAll("EUR", "");
	double transferMoneyLongString = convertMoneyToDouble(transferMoneyLong, "EUR");
	String transferMoneyLongToStringFormat = String.format("%.0f", transferMoneyLongString);

	log.info("TC_12_Step_29: kiem tra so tien chuyen di o man hinh xac thuc thanh cong");
	verifyEquals(transferMoneyLongToStringFormat, moneyTransferLongToStringFormat);

	log.info("TC_12_Step_30: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
	transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

	log.info("TC_12_Step_31: ten nguoi thu huong");
	String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
	verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

	log.info("TC_12_Step_32: tai khoan giao dich");
	String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
	verifyTrue(TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER.equals(destinationAccount));

	log.info("TC_12_Step_33: ma giao dich");
	transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

	log.info("TC_12_Step_34: noi dung giao dich");
	String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);

	verifyEquals(TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, conten);

	log.info("TC_12_Step_35: xac thuc thuc hien giao dich moi");
	String newDealConfirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
	verifyEquals(newDealConfirm, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
	trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

	log.info("TC_12_Step_36: so tien kha dung con lai trong tai khoan");
	String overbalanceAfter = trasferPage.getDynamicTextInFilmTicketInfoDetail(driver, "Số dư khả dụng");
	double overbalanceAfterLong = convertMoneyToDouble(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);

//	log.info("TC_12_Step_37: kiem tra so tien kha dung sau khi chuyen");
//	double overbalanceAfterCacuLator = overbalanceBeforeLong - moneyTransferLong - amountLong;
//	verifyEquals(overbalanceAfterLong, overbalanceAfterCacuLator);

	log.info("TC_12_Step_38 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
	closeApp();
	service.stop();
    }

}
