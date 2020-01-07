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

    @Test
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

//	log.info("TC_36_Step_04: Click quay lai");
//	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");.
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
//	closeApp();
//	service.stop();
    }

}
