package vnpay.vietcombank.card_services;

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
import pageObjects.LockCardPageObject;
import pageObjects.LogInPageObject;
import vietcombank_test_data.Card_Services_Data;

public class Register_Cancel_Internet_Card_Validate extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private LockCardPageObject lockCard;
	
	private String card01;;

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
		login.Global_login("0974862668", pass, opt);

	}

	@Test
	public void TC_01_HuyDangKySuDungTheTrenInternet_KiemTraManHinhHienThi() {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_02: An vao tab 'Huy dang ky su dung the tren Internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hủy đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_03: Xac nhan hien thi title 'Huy dang ky su dung the tren Internet' ");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Card_Services_Data.VALIDATE.REGISTER_CANCEL_INTERNET_CARD_TITLE);
		
		log.info("TC_01_Step_04: Xac nhan hien thi Icon Back ");
		verifyTrue(lockCard.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivTitleLeft"));
		
		log.info("TC_01_Step_05: Xac nhan hien thi label 'Thong tin the'");
		verifyEquals(lockCard.getTextDynamicFollowLayout(driver, "com.VCB:id/llTitle"),"Thông tin thẻ");
		
		log.info("TC_01_Step_06: Xac nhan hien thi Combobox thong tin the");
		verifyTrue(lockCard.isDynamicTextViewByLinearLayoutIdDisplayed(driver, "com.VCB:id/llContent"));
		
		log.info("TC_01_Step_07: Xac nhan hien thi anh thuong hieu the ");
		verifyTrue(lockCard.isDynamicImageViewByLinearLayoutIdDisplayed(driver, "com.VCB:id/llContent"));
		
		log.info("TC_01_Step_08: Xac nhan hien thi buton 'Tiep tuc'");
		verifyEquals(lockCard.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Tiếp tục");
		verifyTrue(lockCard.isDynamicButtonByIdEnable(driver, "com.VCB:id/btContinue"));
		
	}

	@Test
	public void TC_02_HuyDangKySuDungTheTrenInternet_KiemTraAnIconBack() {
		
		log.info("TC_02_Step_01: An Icon Back");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_02_Step_02: Xac nhan hien thi man hinh chinh");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "Dịch vụ thẻ"));
	}

	@Test
	public void TC_03_KiemTraComboboxSoThe_KiemTraHienThiMacDinh() {

		log.info("TC_03_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_03_Step_02: An vao tab 'Huy dang ky su dung the tren Internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hủy đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);
		
		log.info("TC_03_Step_03: Lay ma the hien thi tren combobox");
		card01 = lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/llContent");
		
		log.info("TC_03_Step_04: Click vao combobox");
		lockCard.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llContent");
		
		log.info("TC_03_Step_05: Xac nhan the mac dinh la the hien thi dau tien trong danh sach");
		verifyEquals(lockCard.getFirstOptionInDynamicListElements(driver, "com.VCB:id/tvContent"), card01);
	}

	@Test
	public void TC_04_KiemTraComboboxSoThe_KiemTraDanhSachLoaiThe() {

		log.info("TC_04_Step_01: Chon So the");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Data.CREDIT_CARD04);
		
		log.info("TC_04_Step_02: Xac nhan the hien thi la the vua chon");
		verifyEquals(lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/llContent"), Card_Services_Data.CREDIT_CARD04);
		
		log.info("TC_04_Step_03: Click vao combobox");
		lockCard.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llContent");
		
		log.info("TC_04_Step_04: Chon So the");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Data.CREDIT_CARD03);
		
		log.info("TC_04_Step_05: Xac nhan the hien thi la the vua chon");
		verifyEquals(lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/llContent"), Card_Services_Data.CREDIT_CARD03);
	}

	@Test
	public void TC_05_KiemTraButtonTiepTuc() {
		
		log.info("TC_05_Step_01: Click vao nut 'Tiep tuc'");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_05_Step_02: Xac nhan hien thi man hinh xac nhan thong tin");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");
	}
	
	@Test 
	public void TC_06_ManHinhXacNhanThongTin_KiemTraHienThiMacDinh() {
		
		log.info("TC_06_Step_01: Xac nhan hien thi man hinh xac nhan thong tin");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");
		
		log.info("TC_06_Step_02: Xac nhan hien thi Icon Back ");
		verifyTrue(lockCard.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivTitleLeft"));
		
		log.info("TC_06_Step_03: Xac nhan hien thi thong bao kiem tra thong tin");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), Card_Services_Data.VALIDATE.REGISTER_CANCEL_INTERNET_VERIFY_TITLE_HEAD_MESSAGE);
		
		log.info("TC_06_Step_04: Xac nhan hien thi so the");
		verifyEquals(lockCard.getDynamicTextByLabel(driver, "Số thẻ"), Card_Services_Data.CREDIT_CARD03);
		
		log.info("TC_06_Step_05: Xac nhan hien thi thuong hieu the");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvKey1"), "Thương hiệu thẻ");
		
		log.info("TC_06_Step_06: Xac nhan hien thi Yeu cau");
		verifyEquals(lockCard.getDynamicTextByLabel(driver, "Yêu cầu"), "Hủy đăng ký sử dụng thẻ trên Internet");
		
		log.info("TC_06_Step_07: Xac nhan hien thi buton 'Tiep tuc'");
		verifyEquals(lockCard.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Tiếp tục");
		verifyTrue(lockCard.isDynamicButtonByIdEnable(driver, "com.VCB:id/btContinue"));
	}
	
	@Test
	public void TC_07_ManHinhXacNhanThongTin_KiemTraAnButtonTiepTuc() {
		
		log.info("TC_07_Step_01: Click vao nut 'Tiep tuc'");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_07_Step_06: Xac nhan hien thi man hinh ket qua giao dich ");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver,  "com.VCB:id/tvTitle"), "HỦY ĐĂNG KÝ SỬ DỤNG THẺ TRÊN INTERNET THÀNH CÔNG");
	}
	
	@Test
	public void TC_08_ManHinhKetQua_KiemTraManHinhHienThi() {
		
		log.info("TC_08_Step_01: Xac nhan hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver,  "com.VCB:id/tvTitle"), "HỦY ĐĂNG KÝ SỬ DỤNG THẺ TRÊN INTERNET THÀNH CÔNG");

		log.info("TC_08_Step_02: Xac nhan hien thi Icon Home");
		verifyTrue(lockCard.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivHome"));

		log.info("TC_08_Step_03: Xac nhan hien thi icon Thanh cong");
		verifyTrue(lockCard.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));
		
		log.info("TC_08_Step_04: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(lockCard.isDynamicTextDetailByID(driver, "com.VCB:id/tvTime"));
		
		log.info("TC_08_Step_06: Xac nhan hien thi so the");
		verifyEquals(lockCard.getDynamicTextByLabel(driver, "Số thẻ"), Card_Services_Data.CREDIT_CARD03);
		
		log.info("TC_08_Step_07: Xac nhan hien thi ma giao dich");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "Mã giao dịch"));
		
		log.info("TC_08_Step_08: Hien thi Icon Chia se");
		verifyTrue(lockCard.isDynamicTextDetailByID(driver, "com.VCB:id/tvShare"));

		log.info("TC_08_Step_09: Hien thi Icon Luu anh");
		verifyTrue(lockCard.isDynamicTextDetailByID(driver, "com.VCB:id/tvSavePhoto"));

		log.info("TC_08_Step_10: Hien thi Button quay ve man hinh the");
		verifyEquals(lockCard.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Quay về màn hình dịch vụ thẻ");
		verifyTrue(lockCard.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btContinue"));
		
		log.info("TC_08_Step_11: An nut Home");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivHome");

		log.info("TC_08_Step_12: Xac nhan qua ve man hinh chinh");
		verifyTrue(lockCard.isDynamicImageHomeDisplay(driver, "com.VCB:id/menu_1"));
	}
	
	@Test
	public void TC_09_ManHinhKetQua_KiemTraNhanQuayVeManHinhDichVuThe() {
		
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_09_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_09_Step_02: An vao tab 'Huy dang ky su dung the tren Internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hủy đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);
		
		log.info("TC_09_Step_03: Click vao combobox");
		lockCard.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llContent");
		
		log.info("TC_09_Step_04: Chon So the");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Data.CREDIT_CARD04);
		
		log.info("TC_09_Step_05: Click vao nut 'Tiep tuc'");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_09_Step_06: Click vao nut 'Tiep tuc'");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_09_Step_07: Xac nhan hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver,  "com.VCB:id/tvTitle"), "HỦY ĐĂNG KÝ SỬ DỤNG THẺ TRÊN INTERNET THÀNH CÔNG");
		
		log.info("TC_09_Step_08: An Button Thuc hien giao dich moi");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_09_Step_09: Xac nhan qua ve man hinh chinh");
		verifyTrue(lockCard.isDynamicImageHomeDisplay(driver, "com.VCB:id/menu_1"));
	}
	

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
