package vnpay.vietcombank.online_topup;

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
import pageObjects.OnlineTopupPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.Online_Topup_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data;

public class Limit_Online_Topup extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private OnlineTopupPageObject onlineTopup;


	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(Online_Topup_Data.MOMO.MOMO_DATA_01, "qqqq1111", opt);
		login.scrollDownToText(driver, "Tiết kiệm");
		home = PageFactoryManager.getHomePageObject(driver);
		home.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện tử");
		onlineTopup = PageFactoryManager.getOnlineTopupPageObject(driver);

	}
	

	@Test
	public void TC_01_NapTienDienTuVaoVETCThapHonHanMucToiThieu() {

		log.info("---------------------------TC_01_Step_01: An mo dropdownlist Ten Dich Vu---------------------------");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.MOMO.MOMO_SERVICE);

		log.info("---------------------------TC_01_Step_03: Chon 'Nap tien tai khoan VETC---------------------------");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.VETC.VETC_SERVICE);

		log.info("---------------------------TC_01_Step_04: An mo dropdownlist Nha cung cap---------------------------");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "VETC");

		log.info("---------------------------TC_01_Step_05: Chon 'VETC'---------------------------");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "VETC");

		log.info("---------------------------TC_01_Step_06: Nhap Bien so xe/Ma khach hang---------------------------");
		onlineTopup.inputToDynamicInputBox(driver, Online_Topup_Data.VETC.VETC_DATA_01, "Biển số xe/Mã khách hàng");

		log.info("---------------------------TC_01_Step_07: Nhap so tien---------------------------");
		onlineTopup.scrollDownToButton(driver, "Tiếp tục");
		onlineTopup.inputToDynamicInputBox(driver, Online_Topup_Data.AMOUNT_VETC.AMOUNT_LOWER_MIN_LIMIT_A_TRAN, "Số tiền");

		log.info("---------------------------TC_01_Step_08: An vao check box---------------------------");
		onlineTopup.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("---------------------------TC_01_Step_09: CLick tiep tuc-------------------------------");
		onlineTopup.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("---------------------------TC_01_Step_10: An nut 'Tiep tuc'---------------------------");
		onlineTopup.isDynamicMessageAndLabelTextDisplayed(driver,Online_Topup_Data.MESSEGE_VETC.MESSEGE_ERROR_LOWER_MIN_LIMIT_A_TRAN );
		log.info("---------------------------TC_01_Step_11: Click btn Dong---------------------------");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		
	}
	
	public void TC_02_NapTienDienTuVaoVETCVuotQuaHanMucToiDa() {
		log.info("---------------------------TC_02_Step_01: Nhap so tien-------------------------------");
		onlineTopup.inputToDynamicInputBoxByHeader(driver, Online_Topup_Data.AMOUNT_VETC.AMOUNT_OVER_MAX_LIMIT_A_TRAN, "Thông tin giao dịch", "6");
		
		log.info("---------------------------TC_02_Step_02: CLick tiep tuc-------------------------------");
		onlineTopup.clickToDynamicButton(driver, "Tiếp tục");

		log.info("---------------------------TC_02_Step_03: Verify man hinh hien thi loi-------------------------------");
		onlineTopup.isDynamicMessageAndLabelTextDisplayed(driver,Online_Topup_Data.MESSEGE_VETC.MESSEGE_ERROR_OVER_MAX_LIMIT_A_TRAN );
		
		log.info("---------------------------TC_02_Step_04: Click dong y-------------------------------");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}
	
	@Test
	public void TC_03_NapTienDienTuVaoVETCVuotQuaHanMucToiDaTrongNgay() {
		
		log.info("---------------------------TC_03_Step_01: Nhap so tien-------------------------------");
		onlineTopup.inputToDynamicInputBoxByHeader(driver, Online_Topup_Data.AMOUNT_VETC.AMOUNT_MAX_LIMIT_A_TRAN, "Thông tin giao dịch", "6");
		
		log.info("---------------------------TC_03_Step_02: CLick tiep tuc-------------------------------");
		onlineTopup.clickToDynamicButton(driver, "Tiếp tục");

		log.info("---------------------------TC_03_Step_03: Verify hint thi man hinh xac nhan thong tin giao dich---------------------");
		onlineTopup.isDynamicMessageAndLabelTextDisplayed(driver,Online_Topup_Data.MESSEGE_VETC.TITTLE_CONFIRM_INFO );
		onlineTopup.scrollIDownOneTime(driver);
		
		
		log.info("---------------------------TC_03_Step_04: Chon phuong thuc giao dich---------------------");
		onlineTopup.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

		log.info("---------------------------TC_03_Step_05: Chon hinh thuc OTP---------------------");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("---------------------------TC_03_Step_06: Click tiep tuc--------------------");
		onlineTopup.clickToDynamicButton(driver, "Tiếp tục");

		log.info("---------------------------TC_03_Step_07: Nhap OTP--------------------");
		onlineTopup.inputToDynamicOtp(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("---------------------------TC_03_Step_08:Tiep tuc--------------------");
		onlineTopup.clickToDynamicButton(driver, "Tiếp tục");
		
		
		log.info("---------------TC_03_Step_9: Kiem  tra giao dich thanh cong----------------");
		verifyTrue(onlineTopup.isDynamicMessageAndLabelTextDisplayed(driver, Online_Topup_Data.MESSEGE_VETC.TRANSFER_SUCESS_MESSAGE));

		log.info("---------------------------TC_03_Step_10: Click thuc hien giao dich moi--------------------");
		onlineTopup.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		
		log.info("---------------TC_03_Step_11:Click dropdownlist Ten Dich Vu----------------");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.MOMO.MOMO_SERVICE);

		
		log.info("---------------TC_03_Step_12:Chon 'Nap tien tai khoan VETC'----------------");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.VETC.VETC_SERVICE);

		log.info("---------------TC_03_Step_13:An mo dropdownlist Nha cung cap----------------");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "VETC");

		log.info("---------------TC_03_Step_14:Chon VETC ----------------");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "VETC");

		log.info("---------------TC_03_Step_15:Nhap Bien so xe/Ma khach han ----------------");
		onlineTopup.inputToDynamicInputBox(driver, Online_Topup_Data.VETC.VETC_DATA_01, "Biển số xe/Mã khách hàng");

		log.info("---------------TC_03_Step_16:Nhap so tien ----------------");
		onlineTopup.scrollDownToButton(driver, "Tiếp tục");
		onlineTopup.inputToDynamicInputBoxByHeader(driver, Online_Topup_Data.AMOUNT_VETC.AMOUNT_OVER_MAX_LIMIT_A_TRAN, "Thông tin giao dịch", "6");

		log.info("---------------------------TC_05_Step_17: Click checkbox dieu khoan-------------------------------");
		onlineTopup.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("---------------------------TC_05_Step_18: CLick tiep tuc-------------------------------");
		onlineTopup.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("---------------------------TC_05_Step_19: Verify hien thi thong bao loi qua muc giao dich trong ngay-------------------------------");
		onlineTopup.isDynamicMessageAndLabelTextDisplayed(driver,Online_Topup_Data.MESSEGE_VETC.MESSEGE_ERROR_OVER_MAX_LIMIT_A_DAY );
		
		log.info("---------------------------TC_05_Step_20:Click dong y-------------------------------");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}
	
	
	//truoc khi chay setup nhom giao dich max 100 000
	//Case đang fail do cai nhom dich vụ không bắt hạn mức
	public void TC_04_NapTienDienTuVaoVETCCaoHonHanMucToiDaNhomDichVu() {

		log.info("---------------------------TC_04_Step_01: An mo dropdownlist Ten Dich Vu---------------------------");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.MOMO.MOMO_SERVICE);

		log.info("---------------------------TC_04_Step_02: Chon 'Nap tien tai khoan VETC'---------------------------");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.VETC.VETC_SERVICE);

		log.info("---------------------------TC_04_Step_03: An mo dropdownlist Nha cung cap---------------------------");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "VETC");

		log.info("---------------------------TC_04_Step_04: Chon 'VETC'---------------------------");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "VETC");

		log.info("---------------------------TC_04_Step_05: Nhap Bien so xe/Ma khach hang---------------------------");
		onlineTopup.inputToDynamicInputBox(driver, Online_Topup_Data.VETC.VETC_DATA_01, "Biển số xe/Mã khách hàng");

		log.info("---------------------------TC_04_Step_06: Nhap so tien---------------------------");
		onlineTopup.scrollDownToButton(driver, "Tiếp tục");
		onlineTopup.inputToDynamicInputBox(driver, Online_Topup_Data.AMOUNT_VETC.AMOUNT_OVER_MAX_LIMIT_A_TRAN, "Số tiền");

		log.info("---------------------------TC_04_Step_07: An vao check box---------------------------");
		onlineTopup.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("---------------------------TC_04_Step_08: CLick tiep tuc-------------------------------");
		onlineTopup.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("---------------------------TC_04_Step_09: An nut 'Tiep tuc'---------------------------");
		onlineTopup.isDynamicMessageAndLabelTextDisplayed(driver,Online_Topup_Data.MESSEGE_VETC.MESSEGE_ERROR_OVER_MAX_LIMIT_A_DAY );
		log.info("TC_01_Step_12: Click btn Dong");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		
	}
	
	//truoc khi chay setup nhom giao dich max 100 000
	//Case đang fail do cai nhom dich vụ không bắt hạn mức
	public void TC_05_NapTienDienTuVaoVETCCaoHonHanMucToiDaGoiDichVu() {
		
		log.info("---------------------------TC_05_Step_02: An mo dropdownlist Ten Dich Vu---------------------------");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.MOMO.MOMO_SERVICE);
		
		log.info("---------------------------TC_05_Step_03: Chon 'Nap tien tai khoan VETC'---------------------------");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, Online_Topup_Data.VETC.VETC_SERVICE);
		
		log.info("---------------------------TC_05_Step_04: An mo dropdownlist Nha cung cap---------------------------");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "VETC");
		
		log.info("---------------------------TC_05_Step_05: Chon 'VETC'---------------------------");
		onlineTopup.clickToDynamicButtonLinkOrLinkText(driver, "VETC");
		
		log.info("---------------------------TC_05_Step_06: Nhap Bien so xe/Ma khach hang---------------------------");
		onlineTopup.inputToDynamicInputBox(driver, Online_Topup_Data.VETC.VETC_DATA_01, "Biển số xe/Mã khách hàng");
		
		log.info("---------------------------TC_05_Step_07: Nhap so tien---------------------------");
		onlineTopup.scrollDownToButton(driver, "Tiếp tục");
		onlineTopup.inputToDynamicInputBox(driver, Online_Topup_Data.AMOUNT_VETC.AMOUNT_OVER_MAX_LIMIT_A_TRAN, "Số tiền");
		
		log.info("---------------------------TC_05_Step_08: An vao check box---------------------------");
		onlineTopup.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");
		
		log.info("---------------------------TC_05_Step_09: CLick tiep tuc-------------------------------");
		onlineTopup.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("---------------------------TC_05_Step_09: An nut 'Tiep tuc'---------------------------");
		onlineTopup.isDynamicMessageAndLabelTextDisplayed(driver,Online_Topup_Data.MESSEGE_VETC.MESSEGE_ERROR_OVER_MAX_LIMIT_A_DAY);
		log.info("TC_01_Step_12: Click btn Dong");
		onlineTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		
	}
	
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
