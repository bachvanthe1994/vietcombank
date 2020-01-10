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
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.Account_Data.Invalid_Account;
import vietcombank_test_data.Account_Data.Valid_Account;
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

    @Test
    public void TC_33_KiemTraHienThiNoiDungSauKhiNhapKiTuDacBiet() {
	log.info("TC_33_Step_01: Click Chuyen tien trong VCB");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_33_STEP_02: nhap noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.SPECIAL_CHARACTERS, "Nội dung");

	log.info("TC_33_STEP_03: kiem tra hien thi noi dung vua nhap");
	verifyTrue(trasferPage.isDynamicTextInInputBoxDisPlayed(driver, "Nội dung"));

	log.info("TC_33_Step_04: Click quay lai");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
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

    @Test
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
    public void TC_36_BoTrongTenThuHuong() {
	log.info("TC_36_Step_01: Click Chuyen tien trong VCB");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_36_Step_02: bỏ trống tên người thụ hưởng");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_36_STEP_03: kiểm tra hiển thị thông báo khi không nhập tên người thụ hưởng");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_USER));

	log.info("TC_36_Step_04: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");
    }

    @Test
    public void TC_37_BoTrongGiayToTuyThan() {
	log.info("TC_37_STEP_01: nhap noi dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_37_Step_02: bỏ trống giấy tờ tùy thân");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_37_Step_03: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");
    }

    @Test
    public void TC_38_BoTrongSo() {
	log.info("TC_38_Step_01: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_38_Step_02: bỏ trống số giấy tờ tùy thân");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_38_STEP_13: kiểm tra hiển thị thông báo khi không nhập số giấy tờ tùy thân");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_IDENTITY_NUMBER));

	log.info("TC_38_Step_04: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_38_STEP_05: nhap số giấy tờ tùy thân");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");
    }

    @Test
    public void TC_39_BoTrongNgayCap() {
	log.info("TC_39_Step_1: bỏ trống ngày cấp");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_39_STEP_2: kiểm tra hiển thị thông báo khi không nhập ngày cấp");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_DATE));

	log.info("TC_39_Step_3: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_39_Step_4: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");
    }

    @Test
    public void TC_40_BoTrongNoiCap() {
	log.info("TC_40_Step_1: bỏ trống nơi cấp");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_40_STEP_2: kiểm tra hiển thị thông báo khi không nhập nới cấp");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_ISSUED));

	log.info("TC_40_Step_3: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_40_Step_4: noi cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);
    }

    @Test
    public void TC_41_BoTrongSoTienVaChonTiepTuc() {
	log.info("TC_41_Step_1: bỏ trống số tiền");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_41_STEP_2: kiểm tra hiển thị thông báo khi không nhập số tiền");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_MONEY));
    }

    @Test
    public void TC_42_BoTrongSoTienChonDong() {
	log.info("TC_42_Step_1: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");
    }

    @Test
    public void TC_43_BoTrongNoiDung() {
	log.info("TC_43_Step_1: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");

	log.info("TC_43_Step_2: chọn tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_43_STEP_3: kiểm tra hiển thị thông báo khi không nhập nội dung");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_CONTENT));

	log.info("TC_43_Step_4: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_43_Step_5: nhap nội dung");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTENT, "Nội dung");
    }

    @Test
    public void TC_44_TaiKhoanDongChuSoHuu() {
	log.info("TC_44_Step_28: kiểm tra khi tài đồng chủ sở hữu");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Invalid_Account.SAME_OWNER_ACCOUNT_3);

	log.info("TC_44_Step_29: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_44_STEP_30: kiểm tra hiển thị thông báo khi chọn tài khoản đồng chủ sở hữu");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_ACCOUNT_CO_OWNER));

	log.info("TC_44_Step_27: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");
    }

    @Test
    public void TC_45_TaiKhoanDongSoDu() {
	log.info("TC_45_Step_31: chọn tài khoản nguồn kiểm tra không đủ số dư");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Invalid_Account.SAME_OWNER_ACCOUNT_3);
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.USD_ACCOUNT);

	log.info("TC_45_Step_32: nhập số tiền chuyển đi");
	trasferPage.inputToDynamicInputBoxIdentityValidate(driver, "6000000", "1,000,000");

	log.info("TC_45_Step_33: chọn tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_45_STEP_30: kiểm tra hiển thị thông báo khi chọn tài không đủ số dư");
	verifyTrue(trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, TransferIdentity_Data.confirmMessage.MESSSAGE_ACCOUNT_MONEY));

	log.info("TC_45_Step_31: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
//	closeApp();
//	service.stop();
    }

}
