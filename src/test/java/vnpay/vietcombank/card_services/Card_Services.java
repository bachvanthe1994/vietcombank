package vnpay.vietcombank.card_services;

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
import pageObjects.LockCardPageObject;
import pageObjects.LogInPageObject;

public class Card_Services extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private LockCardPageObject lockCard;
	
	private String debitCard = "";
	private String creditCard = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login("0918679292","aaaa1111", opt);

	}

	@Test
	public void TC_01_KhoaTheGhiNo() {
		
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver,"Dịch vụ thẻ");
		
		log.info("TC_01_Step_02: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver,"Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_03: Mo DropdownList 'Loai The'");
		lockCard.openCardTypeDropdownList();
		
		log.info("TC_01_Step_04: Chon 'The ghi no'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver,"Thẻ ghi nợ");
		
		log.info("TC_01_Step_05: Mo DropdownList 'So The'");
		lockCard.openCardNumberDropdownList("Thông tin thẻ","2");
		
		log.info("TC_01_Step_06: An chon the dau tien trong Dropdown");
		debitCard = lockCard.getFirstCardNumberInDropdownList();
		lockCard.clickToDynamicButtonLinkOrLinkText(driver,debitCard);
		
		log.info("TC_01_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_01_Step_08: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver,"Thẻ ghi nợ"));
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver,debitCard));
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver,"Khóa thẻ"));
		
		log.info("TC_01_Step_09: An button 'Tiep tuc'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver,"KHÓA THẺ THÀNH CÔNG"));
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver,debitCard));
		
		log.info("TC_01_Step_10: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_01_Step_11: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver,"Dịch vụ thẻ");
		
		log.info("TC_01_Step_12: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver,"Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);
		
		log.info("TC_01_Step_13: Mo DropdownList 'So The'");
		lockCard.openCardNumberDropdownList("Thông tin thẻ","2");
		
		log.info("TC_01_Step_14: Xac nhan the bi khoa bi xoa khoi danh sach");
		verifyFailure(lockCard.isCardNumberDisplayedInDropdownList(debitCard));
		
		log.info("TC_01_Step_15: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver,"Đóng");
		
		log.info("TC_01_Step_16: Click ve 'Trang chu'");
		lockCard.clickBackToHomePage();
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_01_Step_17: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver,"Dịch vụ thẻ");
		
		log.info("TC_01_Step_18: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver,"Mở khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);
		
		log.info("TC_01_Step_19: Mo DropdownList 'So The'");
		lockCard.openCardNumberDropdownList("Thông tin thẻ","2");
		
		log.info("TC_01_Step_20: Xac nhan the bi khoa bi xoa khoi danh sach");
		verifyTrue(lockCard.isCardNumberDisplayedInDropdownList(debitCard));
		
		log.info("TC_01_Step_21: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver,"Đóng");
		
		log.info("TC_01_Step_22: Click ve 'Trang chu'");
		lockCard.clickBackToHomePage();
		home = PageFactoryManager.getHomePageObject(driver);
	}
	
	@Test
	public void TC_02_KhoaTheTinDung() {
		
		log.info("TC_02_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver,"Dịch vụ thẻ");
		
		log.info("TC_02_Step_02: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver,"Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_02_Step_03: Mo DropdownList 'Loai The'");
		lockCard.openCardTypeDropdownList();
		
		log.info("TC_02_Step_04: Chon 'The tin dung'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver,"Thẻ tín dụng");
		
		log.info("TC_02_Step_05: Mo DropdownList 'So The'");
		lockCard.openCardNumberDropdownList("Thông tin thẻ","2");
		
		log.info("TC_02_Step_06: An chon the dau tien trong Dropdown");
		creditCard = lockCard.getFirstCardNumberInDropdownList();
		lockCard.clickToDynamicButtonLinkOrLinkText(driver,creditCard);
		
		log.info("TC_02_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");
		
		log.info("TC_02_Step_08: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver,"Thẻ tín dụng"));
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver,creditCard));
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver,"Khóa thẻ"));
		
		log.info("TC_02_Step_09: An button 'Tiep tuc'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver,"KHÓA THẺ THÀNH CÔNG"));
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver,creditCard));
		
		log.info("TC_02_Step_10: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_02_Step_11: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver,"Dịch vụ thẻ");
		
		log.info("TC_02_Step_12: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver,"Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);
		
		log.info("TC_02_Step_13: Mo DropdownList 'Loai The'");
		lockCard.openCardTypeDropdownList();
		
		log.info("TC_02_Step_14: Chon 'The tin dung'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver,"Thẻ tín dụng");
		
		log.info("TC_02_Step_15: Mo DropdownList 'So The'");
		lockCard.openCardNumberDropdownList("Thông tin thẻ","2");
		
		log.info("TC_02_Step_16: Xac nhan the bi khoa bi xoa khoi danh sach");
		verifyFailure(lockCard.isCardNumberDisplayedInDropdownList(creditCard));
		
		log.info("TC_02_Step_17: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver,"Đóng");
		
		log.info("TC_02_Step_18: Click ve 'Trang chu'");
		lockCard.clickBackToHomePage();
		home = PageFactoryManager.getHomePageObject(driver);
		
		log.info("TC_02_Step_19: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver,"Dịch vụ thẻ");
		
		log.info("TC_02_Step_20: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver,"Mở khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);
		
		log.info("TC_02_Step_21: Mo DropdownList 'Loai The'");
		lockCard.openCardTypeDropdownList();
		
		log.info("TC_02_Step_22: Chon 'The tin dung'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver,"Thẻ tín dụng");
		
		log.info("TC_02_Step_23: Mo DropdownList 'So The'");
		lockCard.openCardNumberDropdownList("Thông tin thẻ","2");
		
		log.info("TC_02_Step_24: Xac nhan the bi khoa bi xoa khoi danh sach");
		verifyTrue(lockCard.isCardNumberDisplayedInDropdownList(creditCard));
		
		log.info("TC_02_Step_25: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver,"Đóng");
		
		log.info("TC_02_Step_26: Click ve 'Trang chu'");
		lockCard.clickBackToHomePage();
		home = PageFactoryManager.getHomePageObject(driver);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
