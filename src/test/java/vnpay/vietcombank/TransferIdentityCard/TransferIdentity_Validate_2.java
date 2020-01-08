package vnpay.vietcombank.TransferIdentityCard;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferIdentiryPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferIdentity_Data;

public class TransferIdentity_Validate_2 extends Base {
    AndroidDriver<AndroidElement> driver;
    private LogInPageObject login;
    private HomePageObject homePage;
    private TransferIdentiryPageObject trasferPage;
    private TransactionReportPageObject transReport;
    private String transferTime;
    private String transactionNumber;
    String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();

    @Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
    @BeforeClass
    public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
	startServer();
	log.info("Before class: Mo app ");
	driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

	login = PageFactoryManager.getLoginPageObject(driver);
	homePage = PageFactoryManager.getHomePageObject(driver);
	trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
	transReport = PageFactoryManager.getTransactionReportPageObject(driver);

	log.info("Before class: Click Allow Button");
	login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

	log.info("TC_00_Step_1: chon tiep tuc");
	login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

	log.info("TC_00_Step_2: chon tiep tuc");
	login.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_00_Step_3: chon tiep tuc");
	login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

	log.info("TC_00_Step_4: chon tiep tuc");
	login.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_00_Step_5: chon tiep tuc");
	login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

	log.info("TC_00_Step_6: chon tiep tuc");
	login.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_00_Step_7: chon tu choi");
	login.clickToDynamicButton(driver, "TỪ CHỐI");

    }

//    @Test
    public void TC_33_KiemTraHienThiNoiDungSauKhiNhapKiTuDacBiet() {
	log.info("TC_33_Step_01: Click Chuyen tien trong VCB");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_33_STEP_02: nhap noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.SPECIAL_CHARACTERS, "Nội dung");

	log.info("TC_33_STEP_03: kiem tra hien thi noi dung vua nhap");
	verifyTrue(trasferPage.isDynamicTextInInputBoxDisPlayed(driver, ""));

	log.info("TC_33_Step_04: Click quay lai");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

//    @Test
    public void TC_34_KiemTraHienThiNoiDungNhapMaxLength140() {
	log.info("TC_34_Step_01: Click Chuyen tien trong VCB");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_34_STEP_02: nhap noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_140, "Nội dung");

	log.info("TC_34_STEP_03: kiem tra hien thi noi dung vua nhap");
	verifyTrue(trasferPage.isDynamicTextInInputBoxDisPlayed(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_140));

	log.info("TC_34_Step_04: Click quay lai");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

//    @Test
    public void TC_35_KiemTraHienThiNoiDungNhapMaxLength141() {
	log.info("TC_35_Step_01: Click Chuyen tien trong VCB");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_35_STEP_02: nhap noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_141, "Nội dung");

	log.info("TC_35_STEP_03: kiem tra hien thi noi dung vua nhap");
	verifyTrue(trasferPage.isDynamicTextInInputBoxDisPlayed(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_140));

	log.info("TC_35_Step_04: Click quay lai");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

//    @Test
    public void TC_36_KiemTraNutTiepTuc() {
	log.info("TC_36_Step_01: Click Chuyen tien trong VCB");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_36_Step_02: bỏ trống tên người thụ hưởng");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_36_STEP_03: kiểm tra hiển thị thông báo khi không nhập tên người thụ hưởng");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_USER));

	log.info("TC_36_Step_04: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_36_STEP_05: nhap noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_36_Step_06: bỏ trống giấy tờ tùy thân");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_36_Step_07: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_36_Step_08: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_36_Step_09: bỏ trống số giấy tờ tùy thân");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_36_STEP_10: kiểm tra hiển thị thông báo khi không nhập số giấy tờ tùy thân");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_IDENTITY_NUMBER));

	log.info("TC_36_Step_11: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_36_STEP_12: nhap số giấy tờ tùy thân");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_36_Step_13: bỏ trống ngày cấp");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_36_STEP_14: kiểm tra hiển thị thông báo khi không nhập ngày cấp");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_DATE));

	log.info("TC_36_Step_15: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_36_Step_16: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_36_Step_17: bỏ trống nơi cấp");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_36_STEP_18: kiểm tra hiển thị thông báo khi không nhập nới cấp");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_ISSUED));

	log.info("TC_36_Step_19: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_36_Step_20: noi cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_36_Step_21: bỏ trống số tiền");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_36_STEP_22: kiểm tra hiển thị thông báo khi không nhập số tiền");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_MONEY));

	log.info("TC_36_Step_23: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_36_Step_24: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");

	log.info("TC_36_Step_25: chọn tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_36_STEP_26: kiểm tra hiển thị thông báo khi không nhập nội dung");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_CONTENT));

	log.info("TC_36_Step_27: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_36_Step_28: nhap nội dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTENT, "Nội dung");

	log.info("TC_36_Step_28: kiểm tra khi tài đồng chủ sở hữu");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "0011000000659");

	log.info("TC_36_Step_29: bỏ trống nội dung");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_36_STEP_30: kiểm tra hiển thị thông báo khi chọn tài khoản đồng chủ sở hữu");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_ACCOUNT_CO_OWNER));

	log.info("TC_36_Step_27: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_36_Step_31: chọn tài khoản nguồn kiểm tra không đủ số dư");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "0011000000659");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "0011000001433");

	log.info("TC_36_Step_32: nhập số tiền chuyển đi");
	trasferPage.inputToDynamicInputBoxIdentityValidate(driver, "6000000", "100,000");

	log.info("TC_36_Step_33: chọn tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_36_STEP_30: kiểm tra hiển thị thông báo khi chọn tài khoản đồng chủ sở hữu");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_ACCOUNT_CO_OWNER));

	log.info("TC_36_Step_31: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_36_Step_04: Click quay lai");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

//    @Test
    public void TC_37_ManHinhXacNhanGiaoDich() {
	log.info("TC_37_Step_1: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_37_Step_2: chon tai khoan");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

	homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

	log.info("TC_37_Step_3: lay so tien truoc khi chuyen khoan");
	String overbalanceBefore = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
	Long overbalanceBeforeLong = convertMoneyToLong(overbalanceBefore, "VND");

	log.info("TC_37_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_37_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

	log.info("TC_37_Step_6: so HC");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER, "Số");

	log.info("TC_37_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_37_Step_8: noi cap");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.ISSUED, "Nơi cấp");

	log.info("TC_37_Step_9: nguoi tra phi giao dich");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

	log.info("TC_37_Step_10: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

	log.info("TC_37_Step_11: noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER, "Nội dung");

	log.info("TC_37_Step_12: tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_37_Step_13: xac nhan thong tin");
	String confirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
	verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

	log.info("TC_37_Step_14: kiem tra tai khoan nguon");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

	log.info("TC_37_Step_15: kiem tra ten nguoi thu huong");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_37_Step_16: kiem tra giay to tuy than");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Giấy tờ tùy thân"), "Hộ chiếu");

	log.info("TC_37_Step_17: kiem tra so HC");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER);

	log.info("TC_37_Step_18: kiem tra ngay cap");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Ngày cấp"), today);

	log.info("TC_37_Step_19: kiem tra noi cap");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_37_Step_20: lay ra so tien chuyen di");
	String moneyTransfer = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền");
	Long moneyTransferLong = convertMoneyToLong(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
	String moneyTransferLongToString = Long.toString(moneyTransferLong);

	log.info("TC_37_Step_21: kiem tra so tien");
	verifyEquals(moneyTransferLongToString, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND);

	log.info("TC_37_Step_22: lay phi");
	String amount = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
	Long amountLong = convertMoneyToLong(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
	String amountLongToString = Long.toString(amountLong);

	log.info("TC_37_Step_23: kiem tra noi dung");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER);

	trasferPage.scrollToText(driver, "Tiếp tục");
	log.info("TC_37_Step_24: chon phuong thuc xac thuc");
	trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

	log.info("TC_37_Step_25: lay phi giao dich theo phuong thuc xac thuc");
	String moneyAuthen = trasferPage.getDynamicAmountLabelConvertVNDToLong(driver, "Mật khẩu đăng nhập");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

	log.info("TC_37_Step_26: kiem tra phi giao dich");
	verifyEquals(moneyAuthen, amountLongToString);

	log.info("TC_37_Step_27: kiem tra so tien phi");
	verifyEquals(amountLongToString, TransferIdentity_Data.textCheckElement.AMOUNT);

	log.info("TC_37_Step_28 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Xác nhận thông tin");

    }

//    @Test
    public void TC_38_XacNhanGiaoDichBangSMSOTP() {
	log.info("TC_38_Step_1: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_38_Step_2: chon tai khoan");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

	homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

	log.info("TC_38_Step_3: lay so tien truoc khi chuyen khoan");
	String overbalanceBefore = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
	Long overbalanceBeforeLong = convertMoneyToLong(overbalanceBefore, "VND");

	log.info("TC_38_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_38_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

	log.info("TC_38_Step_6: so HC");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER, "Số");

	log.info("TC_38_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_38_Step_8: noi cap");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.ISSUED, "Nơi cấp");

	log.info("TC_38_Step_9: nguoi tra phi giao dich");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

	log.info("TC_38_Step_10: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

	log.info("TC_38_Step_11: noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER, "Nội dung");

	log.info("TC_38_Step_12: tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_38_Step_13: xac nhan thong tin");
	String confirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
	verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

	log.info("TC_38_Step_14: kiem tra tai khoan nguon");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

	log.info("TC_38_Step_15: kiem tra ten nguoi thu huong");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_38_Step_16: kiem tra giay to tuy than");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Giấy tờ tùy thân"), "Hộ chiếu");

	log.info("TC_38_Step_17: kiem tra so HC");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER);

	log.info("TC_38_Step_18: kiem tra ngay cap");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Ngày cấp"), today);

	log.info("TC_38_Step_19: kiem tra noi cap");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_38_Step_20: lay ra so tien chuyen di");
	String moneyTransfer = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền");
	Long moneyTransferLong = convertMoneyToLong(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
	String moneyTransferLongToString = Long.toString(moneyTransferLong);

	log.info("TC_38_Step_21: kiem tra so tien");
	verifyEquals(moneyTransferLongToString, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND);

	log.info("TC_38_Step_22: lay phi");
	String amount = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
	Long amountLong = convertMoneyToLong(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
	String amountLongToString = Long.toString(amountLong);

	log.info("TC_38_Step_23: kiem tra noi dung");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER);

	trasferPage.scrollToText(driver, "Tiếp tục");
	log.info("TC_38_Step_24: chon phuong thuc xac thuc");
	trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

	log.info("TC_38_Step_25: lay phi giao dich theo phuong thuc xac thuc");
	String moneyAuthen = trasferPage.getDynamicAmountLabelConvertVNDToLong(driver, "SMS OTP");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

	log.info("TC_38_Step_26: kiem tra phi giao dich");
	verifyEquals(moneyAuthen, amountLongToString);

	log.info("TC_38_Step_27: kiem tra so tien phi");
	verifyEquals(amountLongToString, TransferIdentity_Data.textCheckElement.AMOUNT);

	log.info("TC_38_Step_28 : chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_38_Step_29 : chon quay lại");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");

	log.info("TC_38_Step_30 : chon tiep tuc");
	verifyEquals(trasferPage.getDynamicTextHeader(driver), "Xác nhận thông tin");

	log.info("TC_38_Step_31 : chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_38_Step_32 : chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_38_Step_33 : kiểm tra bỏ trống OTP");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_OTP_NOT_FOUND));

	log.info("TC_36_Step_34: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_36_Step_35: điền thiếu otp");
	trasferPage.inputToDynamicOtpOrPIN(driver, TransferIdentity_Data.textCheckElement.OTP_DATA, "Tiếp tục");

	log.info("TC_38_Step_36 : chon tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_38_Step_37 : kiểm tra điền thiếu OTP");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_OTP_MISSING));

	log.info("TC_36_Step_38: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_36_Step_35: điền sai otp");
	trasferPage.inputToDynamicOtpOrPIN(driver, TransferIdentity_Data.textCheckElement.OTP_FALSE, "Tiếp tục");

	log.info("TC_38_Step_39 : chon tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_38_Step_40 : kiểm tra điền sai OTP");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_OTP_FALSE));

	log.info("TC_38_Step_41: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_38_Step_42: điền đúng otp");
	trasferPage.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

	log.info("TC_38_Step_43 : chon tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");
    }

    @Test
    public void TC_39_XacNhanGiaoDichBangMatKhau() {
	log.info("TC_38_Step_1: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_38_Step_2: chon tai khoan");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

	homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

	log.info("TC_38_Step_3: lay so tien truoc khi chuyen khoan");
	String overbalanceBefore = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
	Long overbalanceBeforeLong = convertMoneyToLong(overbalanceBefore, "VND");

	log.info("TC_38_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_38_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

	log.info("TC_38_Step_6: so HC");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER, "Số");

	log.info("TC_38_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_38_Step_8: noi cap");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.ISSUED, "Nơi cấp");

	log.info("TC_38_Step_9: nguoi tra phi giao dich");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

	log.info("TC_38_Step_10: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, "1000000", "Số tiền");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

	log.info("TC_38_Step_11: noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER, "Nội dung");

	log.info("TC_38_Step_12: tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_38_Step_13: xac nhan thong tin");
	String confirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
	verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

	log.info("TC_38_Step_14: kiem tra tai khoan nguon");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

	log.info("TC_38_Step_15: kiem tra ten nguoi thu huong");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_38_Step_16: kiem tra giay to tuy than");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Giấy tờ tùy thân"), "Hộ chiếu");

	log.info("TC_38_Step_17: kiem tra so HC");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER);

	log.info("TC_38_Step_18: kiem tra ngay cap");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Ngày cấp"), today);

	log.info("TC_38_Step_19: kiem tra noi cap");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_38_Step_20: lay ra so tien chuyen di");
	String moneyTransfer = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền");
	Long moneyTransferLong = convertMoneyToLong(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
	String moneyTransferLongToString = Long.toString(moneyTransferLong);

	log.info("TC_38_Step_21: kiem tra so tien");
	verifyEquals(moneyTransferLongToString, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND);

	log.info("TC_38_Step_22: lay phi");
	String amount = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
	Long amountLong = convertMoneyToLong(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
	String amountLongToString = Long.toString(amountLong);

	log.info("TC_38_Step_23: kiem tra noi dung");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER);

	trasferPage.scrollToText(driver, "Tiếp tục");
	log.info("TC_38_Step_24: chon phuong thuc xac thuc");
	trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

	log.info("TC_38_Step_25: lay phi giao dich theo phuong thuc xac thuc");
	String moneyAuthen = trasferPage.getDynamicAmountLabelConvertVNDToLong(driver, "Mật khẩu đăng nhập");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

	log.info("TC_38_Step_26: kiem tra phi giao dich");
	verifyEquals(moneyAuthen, amountLongToString);

	log.info("TC_38_Step_27: kiem tra so tien phi");
	verifyEquals(amountLongToString, TransferIdentity_Data.textCheckElement.AMOUNT);

	log.info("TC_38_Step_28 : chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_38_Step_29 : chon quay lại");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");

	log.info("TC_38_Step_30 : chon tiep tuc");
	verifyEquals(trasferPage.getDynamicTextHeader(driver), "Xác nhận thông tin");

	log.info("TC_38_Step_31 : chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_38_Step_32 : chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_38_Step_33 : kiểm tra bỏ trống OTP");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_PASSWORD_FOUND));

	log.info("TC_36_Step_34: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_36_Step_35: điền thiếu otp");
	trasferPage.inputToDynamicPopupPasswordInput(driver, TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER, "Tiếp tục");

	log.info("TC_38_Step_36 : chon tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_38_Step_37 : kiểm tra điền thiếu OTP");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_PASSWORD_FALSE));

	log.info("TC_36_Step_38: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_36_Step_39: kiem tra maxlength với 20 kí tự");
	trasferPage.inputToDynamicPopupPasswordInput(driver, TransferIdentity_Data.textCheckElement.PASS_MAXLENGTH_20, "Tiếp tục");

	log.info("TC_36_Step_40: kiem tra maxlength");
	String pass = trasferPage.getDynamicPopupPassword(driver, "Tiếp tục");
	int coutnPass = pass.length();
	verifyEquals(coutnPass, 20);

	log.info("TC_38_Step_41 : chon quay lại");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Quay lại");

	log.info("TC_38_Step_42 : chon tiep tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_36_Step_39: kiem tra maxlength với 21 kí tự");
	trasferPage.inputToDynamicPopupPasswordInput(driver, TransferIdentity_Data.textCheckElement.PASS_MAXLENGTH_21, "Tiếp tục");

	log.info("TC_36_Step_40: kiem tra maxlength");
	String pass21 = trasferPage.getDynamicPopupPassword(driver, "Tiếp tục");
	int coutnPass21 = pass21.length();
	verifyEquals(coutnPass, 20);

	log.info("TC_38_Step_42 : chon tiep tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
//	closeApp();
//	service.stop();
    }

}
