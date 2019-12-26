package vnpay.vietcombank.TransferIdentityCard;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

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

	log.info("TC_06_Step_0");
	login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

	log.info("TC_06_Step_0");
	login.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_05_Step_0");
	login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

	log.info("TC_06_Step_0");
	login.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_06_Step_0");
	login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

	log.info("TC_06_Step_0");
	login.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_06_Step_0");
	login.clickToDynamicButton(driver, "TỪ CHỐI");

    }

//    @Test
    public void TC_02_TenNguoiThuHuong() {
	log.info("TC_02_STEP_0: chon chuyển tiền nhận bằng CMT");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_02_STEP_1: kiem tra hien thị mac dinh");
	String beneficiary = trasferPage.getTextDynamicTextInInputBox(driver, "Tên người hưởng");
	verifyEquals(beneficiary, "Tên người hưởng");

	log.info("TC_02_Step_3 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

//    @Test
    public void TC_03_NhapTenNguoiThuHuong() {
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

//  @Test
    public void TC_04_KiemTraLoaiKiTuNhap() {

    }

//    @Test
    public void TC_021_TenNguoiThuHuong() {
	log.info("TC_02_STEP_1: kiem tra hien thị mac dinh");
	String beneficiary = trasferPage.getTextDynamicTextInInputBox(driver, "Tên người hưởng");
	verifyEquals(beneficiary, "Tên người hưởng");

	log.info("TC_02_STEP_2: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_02_STEP_3: lay ten nguoi thu huong vua nhap");
	String beneficiaryInput = trasferPage.getTextDynamicTextInInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_02_STEP_4: kiem tra hien thi ten vua nhap");
	verifyEquals(beneficiaryInput, TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_02_STEP_5: nhap ten nguoi thu huong gom ki tu so");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, "hoangkm123", "Hoangkm");

	log.info("TC_02_STEP_6: lay ten nguoi thu huong vua nhap");
	String beneficiaryInputNumber = trasferPage.getTextDynamicTextInInputBox(driver, "Tên người hưởng");

	log.info("TC_02_STEP_7: kiem tra hien thi ten vua nhap");
	verifyEquals(beneficiaryInputNumber, "hoangkm123");

	log.info("TC_02_STEP_8: nhap ten nguoi thu huong bang ki tu dac biet");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, "!@#$%^&*()", "hoangkm123");

	log.info("TC_02_STEP_9: lay ten nguoi thu huong bang ki tu dac biet");
	String beneficiaryInputSpecial = trasferPage.getTextDynamicTextInInputBox(driver, "Tên người hưởng");

	log.info("TC_02_STEP_10: kiem tra hien thi ten nguoi thu huong bang ki tu dac biet");
	verifyEquals(beneficiaryInputSpecial, "Tên người hưởng");
    }

//    @Test
    public void TC_03_GiayToTuyThan() {
	log.info("TC_03_STEP_1: kiem tra hien thị mac dinh");
	String beneficiary = trasferPage.getTextDynamicPopup(driver, "Giấy tờ tùy thân");
	verifyEquals(beneficiary, "Giấy tờ tùy thân");

	log.info("TC_03_STEP_2:click chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");

	log.info("TC_03_STEP_3:click chon CMT");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_03_STEP_4:kiem tra hien thi khi click chon CMT");
	String identify = trasferPage.getTextDynamicPopup(driver, "Chứng minh nhân dân");
	verifyEquals(identify, "Chứng minh nhân dân");

	log.info("TC_03_STEP_5:click chon chung minh nhan dan");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_03_STEP_6:click chon ho chieu");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

	log.info("TC_03_STEP_7:kiem tra hien thi khi click chon ho chieu");
	String passport = trasferPage.getTextDynamicPopup(driver, "Hộ chiếu");
	verifyEquals(passport, "Hộ chiếu");

	log.info("TC_03_STEP_8:click chon ho chieu");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

	log.info("TC_03_STEP_9:click chon ho chieu");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "CMT Quân đội");

	log.info("TC_03_STEP_10:kiem tra hien thi khi click chon ho chieu");
	String identityArmy = trasferPage.getTextDynamicPopup(driver, "CMT Quân đội");
	verifyEquals(identityArmy, "CMT Quân đội");

	log.info("TC_03_STEP_11:click CMT quan doi");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "CMT Quân đội");

	log.info("TC_03_STEP_12:click chon the can cuoc cong dan");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ căn cước công dân");

	log.info("TC_03_STEP_13:kiem tra hien thi khi click chon the can cuoc cong dan");
	String identityCard = trasferPage.getTextDynamicPopup(driver, "Thẻ căn cước công dân");
	verifyEquals(identityCard, "Thẻ căn cước công dân");

	log.info("TC_03_STEP_15:click chon the can cuoc cong dan");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ căn cước công dân");

	log.info("TC_03_STEP_15:kiem tra vi tri thu nhat hien thi trong danh sach giay to ");
	verifyEquals(trasferPage.getTextInDynamicIdentifition(driver, "0", "com.VCB:id/tvContent"), "Chứng minh nhân dân");

	log.info("TC_03_STEP_15:kiem tra vi tri thu nhat hien thi trong danh sach giay to ");
	verifyEquals(trasferPage.getTextInDynamicIdentifition(driver, "1", "com.VCB:id/tvContent"), "Hộ chiếu");

	log.info("TC_03_STEP_15:kiem tra vi tri thu nhat hien thi trong danh sach giay to ");
	verifyEquals(trasferPage.getTextInDynamicIdentifition(driver, "2", "com.VCB:id/tvContent"), "CMT Quân đội");

	log.info("TC_03_STEP_15:kiem tra vi tri thu nhat hien thi trong danh sach giay to ");
	verifyEquals(trasferPage.getTextInDynamicIdentifition(driver, "3", "com.VCB:id/tvContent"), "Thẻ căn cước công dân");
    }

//    @Test
    public void TC_04_So() {
	log.info("TC_02_STEP_1: kiem tra hien thị mac dinh");
	String number = trasferPage.getTextDynamicTextInInputBox(driver, "Số");
	verifyEquals(number, "Số");

	log.info("TC_02_STEP_2: nhap so");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_02_STEP_3: lay so vua nhap");
	String numberInput = trasferPage.getTextDynamicTextInInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

	log.info("TC_02_STEP_4: kiem tra hien thi so vua nhap");
	verifyEquals(numberInput, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

	log.info("TC_02_STEP_2: nhap lon hon maxlength truong so");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, "123456789012345678901", TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

	log.info("TC_02_STEP_6: kiem tra nhap = maxlength = 20 ki tu so");
	String numberInputMaxlength = trasferPage.getTextDynamicTextInInputBox(driver, "12345678901234567890");
	verifyEquals(numberInputMaxlength, "12345678901234567890");

	log.info("TC_02_STEP_5: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

	log.info("TC_02_STEP_5: chon giay to tuy than: chung minh thu");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_02_STEP_6: kiem tra nhap nho hon 9 ki tu");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, "12345678", "Số");

	log.info("TC_02_STEP_6: click tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_02_STEP_7: kiem tra thong bao trong popup");
	String confirmMaxlength = trasferPage.getTextDynamicPopupTransfers(driver, "Số CMND không hợp lệ. Quý khách vui lòng kiểm tra lại");
	verifyEquals(confirmMaxlength, "Số CMND không hợp lệ. Quý khách vui lòng kiểm tra lại");

	log.info("TC_02_STEP_6: click dong popup");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_02_STEP_6: kiem tra nhap lon hon 15 ki tu");
	trasferPage.inputToDynamicInputBoxUsedValidate(driver, "1234567890123456", "12345678");

	log.info("TC_02_STEP_6: click tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_02_STEP_7: kiem tra thong bao trong popup");
	String confirmMaxlength15 = trasferPage.getTextDynamicPopupTransfers(driver, "Số CMND không hợp lệ. Quý khách vui lòng kiểm tra lại");
	verifyEquals(confirmMaxlength15, "Số CMND không hợp lệ. Quý khách vui lòng kiểm tra lại");

	log.info("TC_02_STEP_6: click dong popup");
	trasferPage.clickToDynamicButton(driver, "Đóng");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER, "Nội dung");

    }

//    @Test
    public void TC_05_NgayCap() {

    }

//    @Test
    public void TC_06_NoiCap() {
	log.info("TC_06_STEP_1: kiem tra hien thị mac dinh");
	String noicap = trasferPage.getTextDynamicTextInInputBox(driver, "Nơi cấp");
	verifyEquals(noicap, "Nơi cấp");

	log.info("TC_06_STEP_2: chon giay to tuy than: chung minh thu");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");
    }

//    @Test
    public void TC_09_Noidung() {
	log.info("TC_06_STEP_1: kiem tra hien thị mac dinh");
	trasferPage.scrollToText(driver, "Nội dung");
	String content = trasferPage.getTextDynamicTextInInputBox(driver, "Nội dung");
	verifyEquals(content, "Nội dung");

	log.info("TC_06_STEP_2: nhap noi dung");
	trasferPage.inputToDynamicInputBox(driver, "chuyen tien cho nguoi nhan", "Nội dung");

	log.info("TC_06_STEP_3: lay ra noi dung vua nhap");
	String contentInput = trasferPage.getTextDynamicTextInInputBox(driver, "chuyen tien cho nguoi nhan");
	verifyEquals(contentInput, "chuyen tien cho nguoi nhan");

	log.info("TC_06_STEP_4: nhap ki tu dac biet");
	trasferPage.inputToDynamicInputBox(driver, "abc", "chuyen tien cho nguoi nhan");

//	log.info("TC_06_STEP_5: kiem tra ki tu dac biet vua nhap");
//	String contentEspeciallyInput = trasferPage.getTextDynamicTextInInputBox(driver, "@ _ - \\\\ . , & * # ! $ + : ; ? / | % ( ) = ");
//	verifyEquals(contentEspeciallyInput, "@ _ - \\\\ . , & * # ! $ + : ; ? / | % ( ) = ");
//
//	log.info("TC_06_STEP_4: nhap ki tu co dau");
//	trasferPage.inputToDynamicInputBox(driver, "gửi tiền", "@ _ - \\\\ . , & * # ! $ + : ; ? / | % ( ) = ");
//
//	log.info("TC_06_STEP_5: kiem tra ki tu dac biet vua nhap");
//	String contentVNInput = trasferPage.getTextDynamicTextInInputBox(driver, "guwi tieen");
//	verifyEquals(contentVNInput, "guwi tieen");

    }

//    @Test
    public void TC_10_NhapNoiDung() {
	log.info("TC_10_STEP_0: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_10_STEP_1: nhap noi dung");
	trasferPage.inputToDynamicInputBox(driver, "chuyen tien cho nguoi nhan", "Nội dung");

	log.info("TC_10_STEP_2: lay ra noi dung vua nhap");
	String contentInput = trasferPage.getTextDynamicTextInInputBox(driver, "chuyen tien cho nguoi nhan");

	log.info("TC_10_STEP_3: kiem tra noi dung vua nhap");
	verifyEquals(contentInput, "chuyen tien cho nguoi nhan");

	log.info("TC_10_Step_4 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

//    @Test
    public void TC_11_kiemTraLoaiKiTuNhap() {
	log.info("TC_11_STEP_0: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_10_STEP_1: nhap ki tu dac biet");
	trasferPage.inputToDynamicInputBox(driver, " @ _ - \\ . , & * # ! $ + : ; ? / | % ( ) = ", "Nội dung");

	log.info("TC_10_STEP_2: lay ra noi dung vua nhap");
	String contentInput = trasferPage.getTextDynamicTextInInputBox(driver, " @ _ - \\ . , & * # ! $ + : ; ? / | % ( ) = ");

	log.info("TC_10_STEP_3: kiem tra noi dung vua nhap");
	verifyEquals(contentInput, " @ _ - \\ . , & * # ! $ + : ; ? / | % ( ) = ");

	log.info("TC_10_STEP_4: nhap ki tu co dau");
	trasferPage.inputToDynamicInputBox(driver, "chuyển tiền", " @ _ - \\ . , & * # ! $ + : ; ? / | % ( ) = ");

//	log.info("TC_10_STEP_5: lay ra noi dung vua nhap");
//	String contentInputVN = trasferPage.getTextDynamicTextInInputBox(driver, "chuyeen tieenf");
//
//	log.info("TC_10_STEP_6: kiem tra noi dung vua nhap");
//	verifyEquals(contentInput, "chuyeen tieenf");
    }

//  @Test
    public void TC_12_KiemTraGioHanNhap() {
	log.info("TC_11_STEP_0: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
//	closeApp();
//	service.stop();
    }

}
