package vnpay.vietcombank.lucky_gift;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.LogInPageObject;
import pageObjects.LuckyGiftPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.LuckyGift_Data;
import vietcombank_test_data.TransferMoney_Data;

public class LuckyGift extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private LuckyGiftPageObject luckyGift;
	private long amountStart;
	private long amountTranfer;
	private String amountStartString;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })

	@BeforeMethod
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
			String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("Before class: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		log.info("Before class");
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		log.info("Before class");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		log.info("Before class");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class");
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("Before class");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("Before class");
		login.clickToDynamicButton(driver, "CHO PHÉP");

		luckyGift = PageFactoryManager.getLuckyGiftPageObject(driver);
	}

	@Test
	public void TC_01_HuyChuyenTienDinhKy() {
		log.info("TC_01_1_Click trang thai lenh chuyen tien");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Quà tặng may mắn");

		log.info("TC_01_Step_Select tai khoan nguon");
		luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCOUNT_FORM);

		log.info("TC_01_Step_Get so du kha dung");
		amountStartString = luckyGift.getDynamicAmountLabel(driver, "Số dư khả dụng").replaceAll("\\D+", "");

		log.info("TC_01_Step_Doi kieu du lieu");
		amountStart = Long.parseLong(amountStartString);

		log.info("TC_01_Step_Select ngan hang");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK_OPTION[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK_OPTION[0]);

		log.info("TC_01_Step_Verify so nguoi nhan, neu trong ngan hang VCB, so list danh sach se hien thi toi da 10");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, "Nguời nhận (0/10)"));

		log.info("TC_01_Step_Click add danh sach nguoi nhan");
		luckyGift.clickToDynamicBottomMenu(driver, "com.VCB:id/ivAdd");

		log.info("TC_01_Step_Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Hinh thuc nhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TYPE_ACCEPT_OPTION[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TYPE_ACCEPT_OPTION[1]);

		log.info("TC_01_Step_Verify so dien thoai nhan 1");
		verifyTrue(luckyGift.isDynamicMessageAndLabelTextDisplayed(driver, "Số điện thoại nhận 1"));

		log.info("TC_01_Step_So dien thoai/ tai khoan nhan");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MOBI_ACCEPT, "Số điện thoại/tài khoản nhận");

		log.info("TC_01_Step_Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, "Số tiền");

		log.info("TC_01_Step_Chon loi chuc");
		luckyGift.clickToDynamicBottomMenu(driver, "com.VCB:id/more_content");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION[0]);
		
		log.info("TC_01_Step_Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Lay gia tri so tien chuyen");
		String amountTranferString = luckyGift.getDynamicAmountLabel(driver, "TỔNG TIỀN").replaceAll("\\D+", "");

		log.info("TC_01_Step_Doi kieu du lieu");
		amountTranfer = Long.parseLong(amountTranferString);
		
		
		log.info("TC_01_Step_Click tiep tuc");
		login.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_Verify hinh thuc giao dich");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Hình thức giao dịch"),"Quà tặng may mắn");
		
		log.info("TC_01_Verify tai khoan nguon");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"),LuckyGift_Data.LuckyGift.ACCOUNT_FORM);
		
		log.info("TC_01_Verify thong tin nguoi nhan");
		verifyTrue(luckyGift.getTextDynamicPopup(driver,LuckyGift_Data.LuckyGift.MOBI_ACCEPT +"/ "+LuckyGift_Data.LuckyGift.NAME_ACCEPT));
		
		log.info("TC_01_Verify So tien chuyen");
		verifyEquals(amountTranferString, LuckyGift_Data.LuckyGift.MONEY);
		
		log.info("TC_01_Verify phi giao dich");
		String amountCostString = luckyGift.getDynamicAmountCostLabel(driver, "Tổng số tiền phí").replaceAll("\\D+", "");
		
		log.info("TC_01_Step_: Verify loi chuc");
		verifyEquals(luckyGift.getDynamicTextInTransactionDetail(driver, "Lời chúc"),LuckyGift_Data.LuckyGift.WISHES_OPTION[0]);
		
		log.info("TC_01_Step_Chon phuong thuc xac thuc");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCURACY[0]);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.ACCURACY[1]);

		log.info("TC_01_Step_Tiep tuc");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		luckyGift.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		
		log.info("TC_01_Step_Tiep tuc");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

	}

	private void verifyTrue(String textDynamicPopup) {
		// TODO Auto-generated method stub
		
	}

	@AfterMethod(alwaysRun = true)
	public void afterClass() {
//		closeApp();
	//	service.stop();
	}

}
