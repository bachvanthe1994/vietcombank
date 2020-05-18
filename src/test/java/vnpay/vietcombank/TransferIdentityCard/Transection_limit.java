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
import pageObjects.TransferIdentiryPageObject;
import vietcombank_test_data.TransferIdentity_Data;
import vietcombank_test_data.Account_Data.Valid_Account;

public class Transection_limit extends Base {
    AppiumDriver<MobileElement> driver;
    private LogInPageObject login;
    private HomePageObject homePage;
    private TransferIdentiryPageObject trasferPage;

    @Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
    @BeforeClass
    public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt)
	    throws IOException, InterruptedException {
	startServer();
	log.info("Before class: Mo app ");
	if (deviceType.contains("android")) {
	    driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
	} else if (deviceType.contains("ios")) {
	    driver = openIOSApp(deviceName, udid, url);
	}
	login = PageFactoryManager.getLoginPageObject(driver);
	login.Global_login1(phone, pass, opt);

	homePage = PageFactoryManager.getHomePageObject(driver);
	trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);

    }

    @Test
    public void TC_01_SoTienGiaoDichNhoHonHanMucToiThieuTren1GiaoDich() {
    	log.info("TC_01_STEP_0: chon chuyển tiền nhận bằng CMT");
    	homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");

    	log.info("TC_01_Step_1: nhap ten nguoi thu huong");
    	trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");

    	log.info("TC_01_Step_2: chon giay to tuy than");
    	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
    	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

    	log.info("TC_01_Step_3: so CMT");
    	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

    	log.info("TC_01_Step_4: ngay cap");
    	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
    	trasferPage.clickToDynamicButton(driver, "OK");

    	log.info("TC_01_Step_5: noi cap");
    	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
    	trasferPage.clickToDynamicTextIndex(driver, "0", TransferIdentity_Data.textDataInputForm.ISSUED);

    	log.info("TC_01_STEP_6: nhap so tien bat dau la khong");
    	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MIN_TRANSFER, "Số tiền");

    	log.info("TC_01_Step_7: noi dung");
    	trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");

    	log.info("TC_01_STEP_8: chon tiep tuc");
    	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

    	log.info("TC_01_STEP_9: kiem tra thong bao khi nhap so tien nho hơn han muc nho nhat cua 1 lan giao dich");
    	verifyEquals(trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferIdentity_Data.textDataInputForm.CONFIRM_MIN_TRANSFER);

    	log.info("TC_01_STEP_10: chon tiep tuc");
    	trasferPage.clickToDynamicButton(driver, "Đóng");

    	log.info("TC_01_Step_11: Click  nut Back");
    	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }
    
    @Test
    public void TC_02_SoTienGiaoDiVuotQuaHanMucToiDaTren1GiaoDich() {
		log.info("TC_02_STEP_1: chon chuyển tiền nhận bằng CMT");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");
	
		log.info("TC_02_Step_2: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");
	
		log.info("TC_02_Step_3: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");
	
		log.info("TC_02_Step_4: so CMT");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");
	
		log.info("TC_02_Step_5: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
		trasferPage.clickToDynamicButton(driver, "OK");
	
		log.info("TC_02_Step_6: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);
	
		log.info("TC_02_STEP_7: nhap so tien bat dau la khong");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MAX_TRANSFER, "Số tiền");
	
		log.info("TC_02_Step_8: noi dung");
		trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");
	
		log.info("TC_02_STEP_9: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");
	
		log.info("TC_02_STEP_10: kiem tra thong bao khi nhap so tien nho hơn han muc nho nhat cua 1 lan giao dich");
		verifyEquals(trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferIdentity_Data.textDataInputForm.CONFIRM_MAX_TRANSFER);
	
		log.info("TC_02_STEP_11: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Đóng");
	
		log.info("TC_02_Step_12: Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }
    
    @Test
    public void TC_03_SoTienGiaoVuotQuaHanMucToiDaTrenNgayCuaDichVu() {
		log.info("TC_03_STEP_1: chon chuyển tiền nhận bằng CMT");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");
	
		log.info("TC_03_Step_2: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");
	
		log.info("TC_03_Step_3: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");
	
		log.info("TC_03_Step_4: so CMT");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");
	
		log.info("TC_03_Step_5: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
		trasferPage.clickToDynamicButton(driver, "OK");
	
		log.info("TC_03_Step_6: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);
	
		log.info("TC_03_STEP_7: nhap so tien bat dau la khong");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MAX_TRANSFER_DAY, "Số tiền");
	
		log.info("TC_03_Step_8: noi dung");
		trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");
	
		log.info("TC_03_STEP_9: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");
	
		log.info("TC_03_STEP_10: kiem tra thong bao khi nhap so tien nho hơn han muc nho nhat cua 1 lan giao dich");
		verifyEquals(trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferIdentity_Data.textDataInputForm.CONFIRM_MAX_TRANSFER);
	
		log.info("TC_03_STEP_11: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Đóng");
	
		log.info("TC_03_Step_12: Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }
    
    @Test
    public void TC_04_SoTienGiaoVuotQuaHanMucToiDaTrenNgayCuaNhomDichVu() {
		log.info("TC_04_STEP_1: chon chuyển tiền nhận bằng CMT");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");
	
		log.info("TC_04_Step_2: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");
	
		log.info("TC_04_Step_3: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");
	
		log.info("TC_04_Step_4: so CMT");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");
	
		log.info("TC_04_Step_5: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
		trasferPage.clickToDynamicButton(driver, "OK");
	
		log.info("TC_04_Step_6: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);
	
		log.info("TC_04_STEP_7: nhap so tien bat dau la khong");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MAX_TRANSFER_DAY_SERVICE_PACK, "Số tiền");
	
		log.info("TC_04_Step_8: noi dung");
		trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");
	
		log.info("TC_04_STEP_9: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");
	
		log.info("TC_04_STEP_10: kiem tra thong bao khi nhap so tien nho hơn han muc nho nhat cua 1 lan giao dich");
		verifyEquals(trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferIdentity_Data.textDataInputForm.CONFIRM_MAX_SERVICE_GROUP);
	
		log.info("TC_04_STEP_11: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Đóng");
	
		log.info("TC_04_Step_12: Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }
    
    @Test
    public void TC_05_SoTienGiaoVuotQuaHanMucToiDaTrenNgayCuaGoiDichVu() {
    	log.info("TC_05_STEP_1: chon chuyển tiền nhận bằng CMT");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");
	
		log.info("TC_05_Step_2: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");
	
		log.info("TC_05_Step_3: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");
	
		log.info("TC_05_Step_4: so CMT");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");
	
		log.info("TC_05_Step_5: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
		trasferPage.clickToDynamicButton(driver, "OK");
	
		log.info("TC_05_Step_6: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);
	
		log.info("TC_05_STEP_7: nhap so tien bat dau la khong");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MAX_TRANSFER_DAY_SERVICE_PACK, "Số tiền");;
	
		log.info("TC_05_Step_8: noi dung");
		trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");
	
		log.info("TC_05_STEP_9: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");
	
		log.info("TC_05_STEP_10: kiem tra thong bao khi nhap so tien nho hơn han muc nho nhat cua 1 lan giao dich");
		verifyEquals(trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferIdentity_Data.textDataInputForm.CONFIRM_MAX_TRANSFER);
	
		log.info("TC_05_STEP_11: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Đóng");
	
		log.info("TC_05_Step_12: Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }
    
    @Test
    public void TC_06_LoiKhongDuSoDu() {
    	log.info("TC_06_STEP_0: chon chuyển tiền nhận bằng CMT");
		homePage.clickToDynamicIcon(driver, "Chuyển tiền nhận bằng tiền mặt");
		
		log.info("TC_06_STEP_1: chon tai khoan nguon");
		trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.DEFAULT_ACCOUNT3);
		
		log.info("TC_01_STEP_3: lấy ra số dư");
		trasferPage.scrollUpToText(driver, "Tài khoản nguồn");
		String getToltalMoney = trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvInfoBottomRight");
		String[] toltal_money = getToltalMoney.split(" ");
		int toltalMoney = Integer.parseInt(toltal_money[0].replace(",", ""));
	
		log.info("TC_06_Step_2: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");
	
		log.info("TC_06_Step_3: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");
	
		log.info("TC_06_Step_4: so CMT");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");
	
		log.info("TC_06_Step_5: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
		trasferPage.clickToDynamicButton(driver, "OK");
	
		log.info("TC_06_Step_6: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);
	
		log.info("TC_06_STEP_7: nhap so tien bat dau la khong");
		trasferPage.inputToDynamicInputBox(driver, ""+toltalMoney + 100, "Số tiền");;
	
		log.info("TC_06_Step_8: noi dung");
		trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");
	
		log.info("TC_06_STEP_9: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");
	
		log.info("TC_06_STEP_10: kiem tra thong bao khi nhap so tien nho hơn han muc nho nhat cua 1 lan giao dich");
		verifyEquals(trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferIdentity_Data.textDataInputForm.CONFIRM_INSUFFICIENT_BALANCE);
	
		log.info("TC_06_STEP_11: chon tiep tuc");
		trasferPage.clickToDynamicButton(driver, "Đóng");
	
		log.info("TC_06_Step_12: Click  nut Back");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
	closeApp();
	service.stop();
    }

}
