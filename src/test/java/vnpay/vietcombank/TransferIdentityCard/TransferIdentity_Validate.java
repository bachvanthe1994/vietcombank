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

public class TransferIdentity_Validate extends Base {
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
    public void TC_01_TenNguoiThuHuong() {
	log.info("TC_01_STEP_0: chon chuyển tiền nhận bằng CMT");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_01_STEP_1: kiem tra hien thị mac dinh");
	String beneficiary = trasferPage.getTextDynamicTextInInputBox(driver, "Tên người hưởng");
	verifyEquals(beneficiary, "Tên người hưởng");

	log.info("TC_01_Step_2 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

//    @Test
    public void TC_02_NhapTenNguoiThuHuong() {
	log.info("TC_02_STEP_0: chon chuyển tiền nhận bằng CMT");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_02_STEP_1: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_02_STEP_2: lay ten nguoi thu huong vua nhap");
	String beneficiaryInput = trasferPage.getTextDynamicTextInInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_02_STEP_3: kiem tra hien thi ten vua nhap");
	verifyEquals(beneficiaryInput, TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_02_Step_4 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_03_KiemTraLoaiKiTuNhap() {
	log.info("TC_03_STEP_0: chon chuyển tiền nhận bằng CMT");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_03_STEP_1: nhap ten nguoi thu huong gom ki tu so");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER, "Tên người hưởng");

	log.info("TC_03_STEP_2: lay ten nguoi thu huong vua nhap");
	String beneficiaryInputNumber = trasferPage.getTextDynamicTextInInputBox(driver, TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER);

	log.info("TC_03_STEP_3: kiem tra hien thi ten vua nhap");
	verifyEquals(beneficiaryInputNumber, TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER);

	log.info("TC_03_STEP_4: nhap ten nguoi thu huong gom ki tu dac biet");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, TransferIdentity_Data.textDataInputForm.SPECIAL_CHARACTERS, TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER);

	log.info("TC_03_STEP_5: lay ten nguoi thu huong vua nhap");
	String specialCharacters = trasferPage.getTextDynamicTextInInputBox(driver, "Tên người hưởng");

	log.info("TC_03_STEP_6: kiem tra hien thi ten vua nhap");
	verifyEquals(specialCharacters, "Tên người hưởng");

	log.info("TC_03_STEP_7: nhap ten nguoi thu huong gom ki tu co dau");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM, "Tên người hưởng");

	log.info("TC_03_STEP_8: lay ten nguoi thu huong vua nhap");
	String CharactersVN = trasferPage.getTextDynamicTextInInputBox(driver, "Tên người hưởng");

	log.info("TC_03_STEP_9: kiem tra hien thi ten vua nhap");
	verifyEquals(CharactersVN, "Xac nhan thong tin");

	log.info("TC_03_STEP_10: kiem tra max length = 100");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_100, "Xac nhan thong tin");

	log.info("TC_03_STEP_11: lay ten nguoi thu huong vua nhap");
	String maxLength100 = trasferPage.getTextDynamicTextInInputBox(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_100);

	log.info("TC_03_STEP_12: kiem tra hien thi ten vua nhap");
	verifyEquals(maxLength100, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_100);

	log.info("TC_03_STEP_13: kiem tra max length = 101");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_101, "Xac nhan thong tin");

	log.info("TC_03_STEP_14: lay ten nguoi thu huong vua nhap");
	String maxLength101 = trasferPage.getTextDynamicTextInInputBox(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_100);

	log.info("TC_03_STEP_15: kiem tra hien thi ten vua nhap");
	verifyEquals(maxLength101, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_100);

	log.info("TC_03_Step_16 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");

    }

    @Test
    public void TC_04_KiemTraMacDinhGiayToTuyThan() {
	log.info("TC_04_STEP_0: chon chuyển tiền nhận bằng CMT");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_04_STEP_1: kiem tra hien thị mac dinh");
	String beneficiary = trasferPage.getTextDynamicPopup(driver, "Giấy tờ tùy thân");
	verifyEquals(beneficiary, "Giấy tờ tùy thân");

	log.info("TC_02_Step_2 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_05_ChonLoaiGiayToTuyThan() {
	log.info("TC_05_STEP_0: chon chuyển tiền nhận bằng CMT");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_05_STEP_5:click chon CMT");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");

	log.info("TC_05_STEP_2:kiem tra hien thi khi click chon CMT");
	String identify = trasferPage.getTextDynamicPopup(driver, "Chứng minh nhân dân");
	verifyEquals(identify, "Chứng minh nhân dân");

	log.info("TC_05_STEP_3:click chon HC");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

	log.info("TC_05_STEP_4:kiem tra hien thi khi click chon HC");
	String passPort = trasferPage.getTextDynamicPopup(driver, "Hộ chiếu");
	verifyEquals(passPort, "Hộ chiếu");

	log.info("TC_05_STEP_5:click chon CMTQD");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "CMT Quân đội");

	log.info("TC_05_STEP_6:kiem tra hien thi khi click chon CMTQD");
	String military = trasferPage.getTextDynamicPopup(driver, "CMT Quân đội");
	verifyEquals(military, "CMT Quân đội");

	log.info("TC_05_STEP_7:click chon CCCD");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "CMT Quân đội");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ căn cước công dân");

	log.info("TC_05_STEP_8:kiem tra hien thi khi click chon CCCD");
	String citizen = trasferPage.getTextDynamicPopup(driver, "Thẻ căn cước công dân");
	verifyEquals(citizen, "Thẻ căn cước công dân");

	log.info("TC_05_Step_9 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_06_KiemTraHienThiGiayToTuyThan() {
	log.info("TC_06_STEP_0: chon chuyển tiền nhận bằng CMT");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_06_STEP_1:click chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");

	log.info("TC_06_STEP_2:kiem tra vi tri thu nhat hien thi trong danh sach giay to ");
	verifyEquals(trasferPage.getTextInDynamicIdentifition(driver, "0", "com.VCB:id/tvContent"), "Chứng minh nhân dân");

	log.info("TC_06_STEP_3:kiem tra vi tri thu nhat hien thi trong danh sach giay to ");
	verifyEquals(trasferPage.getTextInDynamicIdentifition(driver, "1", "com.VCB:id/tvContent"), "Hộ chiếu");

	log.info("TC_06_STEP_4:kiem tra vi tri thu nhat hien thi trong danh sach giay to ");
	verifyEquals(trasferPage.getTextInDynamicIdentifition(driver, "2", "com.VCB:id/tvContent"), "CMT Quân đội");

	log.info("TC_06_STEP_5:kiem tra vi tri thu nhat hien thi trong danh sach giay to ");
	verifyEquals(trasferPage.getTextInDynamicIdentifition(driver, "3", "com.VCB:id/tvContent"), "Thẻ căn cước công dân");

	log.info("TC_06_STEP_6:click chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ căn cước công dân");

	log.info("TC_06_Step_7 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_07_KiemTraMacDinhSo() {
	log.info("TC_07_STEP_0: chon chuyển tiền nhận bằng CMT");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_07_STEP_1: kiem tra hien thị mac dinh");
	String number = trasferPage.getTextDynamicTextInInputBox(driver, "Số");
	verifyEquals(number, "Số");

	log.info("TC_07_Step_3 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_08_KiemTraNhapSo() {
	log.info("TC_08_STEP_0: chon chuyển tiền nhận bằng CMT");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_08_STEP_1: nhap so");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_08_STEP_2: lay so vua nhap");
	String numberInput = trasferPage.getTextDynamicTextInInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

	log.info("TC_08_STEP_3: kiem tra hien thi so vua nhap");
	verifyEquals(numberInput, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

	log.info("TC_08_Step_4 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_9_KiemTraMaxLengthSo() {
	log.info("TC_09_STEP_0: chon chuyển tiền nhận bằng CMT");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_09_STEP_1: nhap maxlength truong so");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_20_NUM, "Số");

	log.info("TC_09_STEP_2: kiem tra nhap = maxlength = 20 ki tu so");
	String numberInputMaxlength = trasferPage.getTextDynamicTextInInputBox(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_20_NUM);
	verifyEquals(numberInputMaxlength, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_20_NUM);

	log.info("TC_09_STEP_3: nhap lon hon maxlength truong so");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_21_NUM, "Số");

	log.info("TC_09_STEP_4: kiem tra nhap = maxlength = 20 ki tu so");
	String numberInput20 = trasferPage.getTextDynamicTextInInputBox(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_20_NUM);
	verifyEquals(numberInput20, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_20_NUM);

	log.info("TC_09_Step_5 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_10_KiemTraSoCMT() {
	log.info("TC_10_STEP_0: chon chuyển tiền nhận bằng CMT");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_10_STEP_1: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_10_STEP_2: chon giay to tuy than: chung minh thu");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_10_STEP_3: kiem tra nhap nho hon 9 ki tu");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_8_NUM, "Số");

	log.info("TC_10_STEP_4: click tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_10_STEP_5: kiem tra thong bao trong popup");
	String confirmMaxlength = trasferPage.getTextDynamicPopupTransfers(driver, TransferIdentity_Data.textDataInputForm.CONFIRM_IDENTITY);
	verifyEquals(confirmMaxlength, TransferIdentity_Data.textDataInputForm.CONFIRM_IDENTITY);

	log.info("TC_10_STEP_6: click dong popup");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_10_STEP_7: kiem tra nhap lon hon 15 ki tu");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_16_NUM, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_8_NUM);

	log.info("TC_10_STEP_8: click tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_10_STEP_9: kiem tra thong bao trong popup");
	String confirmMaxlength15 = trasferPage.getTextDynamicPopupTransfers(driver, TransferIdentity_Data.textDataInputForm.CONFIRM_IDENTITY);
	verifyEquals(confirmMaxlength15, TransferIdentity_Data.textDataInputForm.CONFIRM_IDENTITY);

	log.info("TC_10_STEP_10: click dong popup");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_10_STEP_11: kiem tra nhap 15 ki tu");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_15_NUM, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_16_NUM);

	log.info("TC_10_STEP_12: click tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_10_STEP_13: kiem tra thong bao trong popup");
	String confirm = trasferPage.getTextDynamicPopupTransfers(driver, "Quý khách vui lòng chọn ngày cấp");
	verifyEquals(confirm, "Quý khách vui lòng chọn ngày cấp");

	log.info("TC_10_STEP_14: click dong popup");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_10_STEP_15: kiem tra nhap 9 ki tu");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_9_NUM, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_15_NUM);

	log.info("TC_10_STEP_16: click tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_10_STEP_17: kiem tra thong bao trong popup");
	String confirm1 = trasferPage.getTextDynamicPopupTransfers(driver, "Quý khách vui lòng chọn ngày cấp");
	verifyEquals(confirm1, "Quý khách vui lòng chọn ngày cấp");

	log.info("TC_10_STEP_18: click dong popup");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_10_Step_19: Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_11_HienThiMacDinhNoiCap() {
	log.info("TC_11_STEP_0: chon chuyển tiền nhận bằng CMT");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_11_STEP_1: kiem tra hien thị mac dinh");
	trasferPage.isDynamicMessageAndLabelTextDisplayed(driver, "Nơi cấp");

	log.info("TC_12_Step_4 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_12_NoiCapCMT() {
	log.info("TC_12_STEP_0: chon chuyển tiền nhận bằng CMT");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_12_STEP_1:click chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");

	log.info("TC_12_STEP_2:click chon CMT");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_12_STEP_3:click chon noi cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");

	log.info("TC_12_STEP_4:click chon Ha Noi");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_12_STEP_5:kiem tra gia tri vua chon");
	trasferPage.isControlDisplayed(driver, TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_12_Step_6 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_13_NoiCapHoChieu() {
	log.info("TC_13_STEP_0: chon chuyển tiền nhận bằng CMT");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_13_STEP_1:click chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");

	log.info("TC_13_STEP_2:click chon HC");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

	log.info("TC_13_STEP_3:click chon Ha Noi");
	trasferPage.inputToDynamicInputBox(driver, "Thành phố Hà Nội", "Nơi cấp");

	log.info("TC_13_STEP_4:kiem tra gia tri vua chon");
	trasferPage.isControlDisplayed(driver, TransferIdentity_Data.textDataInputForm.ISSUED_SPACE);

	log.info("TC_13_Step_5 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
	closeApp();
	service.stop();
    }

}
