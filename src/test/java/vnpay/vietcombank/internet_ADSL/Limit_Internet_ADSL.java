package vnpay.vietcombank.internet_ADSL;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.HomePageObject;
import pageObjects.InternetADSLPageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.Internet_ADSL_Data;
import vietcombank_test_data.LandLinePhoneCharge_Data;
import vietcombank_test_data.LogIn_Data;

public class Limit_Internet_ADSL extends Base {
	AppiumDriver<MobileElement> driver;
    private LogInPageObject login;
	private InternetADSLPageObject ADSL;
	private HomePageObject homePage;
	

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt)
		throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		homePage = PageFactoryManager.getHomePageObject(driver);
		ADSL = PageFactoryManager.getInternetADSLPageObject(driver);
	}


	@Test
	//Set han muc toi thieu là 101,000 VND
	public void TC_01_ThanhToanCuocViettelNhoHonHanMucToiThieu() {
		log.info("------------------------------TC_01_Step_Click cuoc ADSL------------------------------");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Cước Internet ADSL");

		log.info("------------------------------TC_01_Step_Select tai khoan nguon------------------------------");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("------------------------------TC_01_Step_Thong tin giao dich chon Viettel------------------------------");
		ADSL.clickToTextViewByLinearLayoutID(driver,"com.VCB:id/wrap_tv");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.NETWORK[2]);

		log.info("------------------------------TC_01_Input ma khach hang------------------------------");
		ADSL.inputIntoEditTextByID(driver,Internet_ADSL_Data.Limit_Internet_ADSL_Data.CODE_CUSTOMER[0],"com.VCB:id/code");

		
		log.info("------------------------------TC_01_Click Tiep tuc------------------------------");
		ADSL.clickToDynamicButton(driver, "Tiếp tục");


		log.info("------------------------------TC_01_Hien thi man hinh thong bao lo han muc------------------------------");
		ADSL.isDynamicMessageAndLabelTextDisplayed(driver, Internet_ADSL_Data.Limit_Internet_ADSL_Data.MESSEGER_ERROR_LOWER_LIMIT_MIN_A_TRAN);
		
		log.info("------------------------------TC_01_Click btn dong------------------------------");
		ADSL.clickToDynamicAcceptButton(driver,"com.VCB:id/btOK");
		
	}

	@Test
	//Set han muc toi da là 300,000 VND
	public void TC_02_ThanhToanCuocViettelCaoHonHanMucToiDa() {
		log.info("------------------------------TC_02_Step_Click cuoc ADSL------------------------------");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Cước Internet ADSL");

		log.info("------------------------------TC_02_Step_Select tai khoan nguon------------------------------");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("------------------------------TC_02_Step_Thong tin giao dich chon Viettel------------------------------");
		ADSL.clickToTextViewByLinearLayoutID(driver,"com.VCB:id/wrap_tv");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.NETWORK[0]);
		
		log.info("------------------------------TC_02_Input ma khach hang------------------------------");
		ADSL.inputIntoEditTextByID(driver,Internet_ADSL_Data.Limit_Internet_ADSL_Data.CODE_CUSTOMER[17],"com.VCB:id/code");
		log.info("------------------------------TC_01_Step_Tiep tuc------------------------------");
		
		ADSL.clickToDynamicButton(driver, "Tiếp tục");

		log.info("------------------------------TC_02_Hien thi man hinh thong bao lo han muc------------------------------");
		ADSL.isDynamicMessageAndLabelTextDisplayed(driver, Internet_ADSL_Data.Limit_Internet_ADSL_Data.MESSEGER_ERROR_HIHGER_LIMIT_MAX_A_TRAN);
		
		log.info("------------------------------TC_02_Click btn dong------------------------------");
		ADSL.clickToDynamicAcceptButton(driver,"com.VCB:id/btOK");
		
	}

	
	@Test
	//hạn mức ngày là 350000k
	public void TC_03_ThanhToanCuocViettelCaoHonHanMucToiDaNgay() {
		log.info("------------------------------TC_03_Step_Click cuoc ADSL");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Cước Internet ADSL");

		log.info("------------------------------TC_03_Step_Select tai khoan nguon");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);


		log.info("------------------------------TC_03_Step_Thong tin giao dich chon Viettel");
		ADSL.clickToTextViewByLinearLayoutID(driver,"com.VCB:id/wrap_tv");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.NETWORK[3]);

		log.info("------------------------------TC_03_Input ma khach hang");
		ADSL.inputIntoEditTextByID(driver,Internet_ADSL_Data.Limit_Internet_ADSL_Data.CODE_CUSTOMER[19],"com.VCB:id/code");

		
		log.info("------------------------------TC_03_Click Tiep tuc");
		ADSL.clickToDynamicButton(driver, "Tiếp tục");

		log.info("------------------------------TC_03_Step_Nhap ma xac thuc");
		ADSL.inputToDynamicPopupPasswordInput(driver, "aaaa1111", "Tiếp tục");

		log.info("------------------------------TC_03_Step_Tiep tuc");
		ADSL.clickToDynamicButton(driver, "Tiếp tục");

		log.info("------------------------------TC_03_Verify message thanh cong");
		verifyEquals(ADSL.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("------------------------------TC_03_Step_: Chon thuc hien giao dich");
		ADSL.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("------------------------------TC_03_Step_: Chon tai khoan chuyen");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("------------------------------TC_03_Step_: Chon tai khoan chuyen");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);
		
		log.info("------------------------------TC_03_Step_Thong tin giao dich chon Viettel");
		ADSL.clickToTextViewByLinearLayoutID(driver,"com.VCB:id/wrap_tv");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.NETWORK[3]);

		log.info("------------------------------TC_03_Input ma khach hang");
		ADSL.inputIntoEditTextByID(driver,Internet_ADSL_Data.Limit_Internet_ADSL_Data.CODE_CUSTOMER[0],"com.VCB:id/code");
		
		ADSL.clickToDynamicButton(driver, "Tiếp tục");

		log.info("------------------------------TC_03_08_Hien thi man hinh thong bao lo han muc------------------------------");
		ADSL.isDynamicMessageAndLabelTextDisplayed(driver, Internet_ADSL_Data.Limit_Internet_ADSL_Data.MESSEGER_ERROR_HIHGER_LIMIT_MAX_A_DAY);
		
		log.info("------------------------------TC_03_08_ Click btn dong------------------------------");
		ADSL.clickToDynamicAcceptButton(driver,"com.VCB:id/btOK");
		
		

	}

	//Truoc khi chay set hạn mức của nhóm là 50,000VND
	public void TC_04_ThanhToanCuocViettelCaoHonHanMucToiDaNhom() {
		log.info("------------------------------TC_04_Step_Click cuoc ADSL------------------------------");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Cước Internet ADSL");

		log.info("------------------------------TC_04_Step_Select tai khoan nguon------------------------------");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("------------------------------TC_04_Step_Thong tin giao dich chon Viettel------------------------------");
		ADSL.clickToTextViewByLinearLayoutID(driver,"com.VCB:id/wrap_tv");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.NETWORK[2]);

		log.info("------------------------------TC_04_Input ma khach hang------------------------------");
		ADSL.inputIntoEditTextByID(driver,Internet_ADSL_Data.Limit_Internet_ADSL_Data.CODE_CUSTOMER[0],"com.VCB:id/code");

		
		log.info("------------------------------TC_04_Click Tiep tuc------------------------------");
		ADSL.clickToDynamicButton(driver, "Tiếp tục");


		log.info("------------------------------TC_04_Hien thi man hinh thong bao lo han muc------------------------------");
		ADSL.isDynamicMessageAndLabelTextDisplayed(driver, Internet_ADSL_Data.Limit_Internet_ADSL_Data.MESSEGER_ERROR_HIHGER_LIMIT_MAX_GROUP);
		
		log.info("------------------------------TC_04_ Click btn dong------------------------------");
		ADSL.clickToDynamicAcceptButton(driver,"com.VCB:id/btOK");
		
		
	}


	
	//Truoc khi chay set hạn mức của goi là 50,000VND
	public void TC_05_ThanhToanCuocViettelCaoHonHanMucToiDaGoi() {
		log.info("------------------------------TC_05_Step_Click cuoc ADSL------------------------------");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Cước Internet ADSL");

		log.info("------------------------------TC_05_Step_Select tai khoan nguon------------------------------");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("------------------------------TC_05_Step_Thong tin giao dich chon Viettel------------------------------");
		ADSL.clickToTextViewByLinearLayoutID(driver,"com.VCB:id/wrap_tv");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.NETWORK[2]);

		log.info("------------------------------TC_05_Input ma khach hang------------------------------");
		ADSL.inputIntoEditTextByID(driver,Internet_ADSL_Data.Limit_Internet_ADSL_Data.CODE_CUSTOMER[0],"com.VCB:id/code");

		
		log.info("------------------------------TC_05_Click Tiep tuc------------------------------");
		ADSL.clickToDynamicButton(driver, "Tiếp tục");


		log.info("------------------------------TC_05_Hien thi man hinh thong bao lo han muc------------------------------");
		ADSL.isDynamicMessageAndLabelTextDisplayed(driver, Internet_ADSL_Data.Limit_Internet_ADSL_Data.MESSEGER_ERROR_HIHGER_LIMIT_MAX_PACKAGE);
		
		log.info("------------------------------TC_05_ Click btn dong------------------------------");
		ADSL.clickToDynamicAcceptButton(driver,"com.VCB:id/btOK");
		
		
	}

	
}