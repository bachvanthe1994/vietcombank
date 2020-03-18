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
import vietcombank_test_data.Lock_Card_Data;

public class LockCard_Validate extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private LockCardPageObject lockCard;
	
	private String debitCard_01;

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
		login.Global_login("0918679292", "aaaaa11111", opt);

	}

	@Test
	public void TC_01_KhoaThe_ManHinhKhoaThe() {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_02: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_03: Xac nhan hien thi title 'Khoa the' ");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Lock_Card_Data.VALIDATE.LOCK_CARD_TILTE);
		
		log.info("TC_01_Step_04: Xac nhan hien thi Icon Back ");
		verifyTrue(lockCard.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivTitleLeft"));
		
		log.info("TC_01_Step_05: Xac nhan hien thi Combobox Loai The ");
		verifyEquals(lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout0"),Lock_Card_Data.VALIDATE.DEBIT_CARD_TEXT);
		
		log.info("TC_01_Step_06: Xac nhan hien thi Combobox so the ");
		verifyTrue(lockCard.isDynamicTextViewByLinearLayoutIdDisplayed(driver, "com.VCB:id/layout1"));
		
		log.info("TC_01_Step_07: Xac nhan hien thi anh thuong hieu the ");
		verifyTrue(lockCard.isDynamicImageViewByLinearLayoutIdDisplayed(driver, "com.VCB:id/layout1"));
		
		log.info("TC_01_Step_08: Xac nhan hien thi buton 'Tiep tuc'");
		verifyTrue(lockCard.isDynamicButtonByIdEnable(driver, "com.VCB:id/btContinue"));
	}

	@Test
	public void TC_02_KhoaThe_KiemTraAnIconBack() {
		
		log.info("TC_02_Step_01: An Icon Back");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_02_Step_02: Xac nhan hien thi man hinh chinh");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "Dịch vụ thẻ"));
	}

	@Test
	public void TC_03_KiemTraComboboxLoaiThe_KiemTraHienThiMacDinh() {

		log.info("TC_03_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_03_Step_02: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);
		
		log.info("TC_03_Step_03: Xac nhan hien thi Combobox Loai The mac dinh");
		verifyEquals(lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout0"),Lock_Card_Data.VALIDATE.DEBIT_CARD_TEXT);
	}

	@Test
	public void TC_04_KiemTraComboboxLoaiThe_KiemTraDanhSachLoaiThe() {

		log.info("TC_04_Step_01: Click vao combobox Loai the");
		lockCard.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layout0");
		
		log.info("TC_04_Step_02: Xac nhan chi hien thi 2 loai the");
		verifyEquals(lockCard.getCountNumberOfDynamicListElements(driver, "com.VCB:id/tvContent"), 2);
		verifyTrue(lockCard.isTextDisplayedInListTextElements(driver, Lock_Card_Data.VALIDATE.CREDIT_CARD_TEXT, "com.VCB:id/tvContent"));
		verifyTrue(lockCard.isTextDisplayedInListTextElements(driver, Lock_Card_Data.VALIDATE.DEBIT_CARD_TEXT, "com.VCB:id/tvContent"));
	}

	@Test
	public void TC_05_KiemTraComboboxLoaiThe_KiemTraChonLoaiThe() {
		
		log.info("TC_05_Step_01: Chon the Tin dung");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Lock_Card_Data.VALIDATE.CREDIT_CARD_TEXT);
		
		log.info("TC_05_Step_02: Xac nhan hien thi dung the da chon o combobox");
		verifyEquals(lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout0"),Lock_Card_Data.VALIDATE.CREDIT_CARD_TEXT);
		
		log.info("TC_05_Step_03: Chon the Ghi no");
		lockCard.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layout0");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Lock_Card_Data.VALIDATE.DEBIT_CARD_TEXT);
		
		log.info("TC_05_Step_04: Xac nhan hien thi dung the da chon o combobox");
		verifyEquals(lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout0"),Lock_Card_Data.VALIDATE.DEBIT_CARD_TEXT);
	}

	@Test
	public void TC_06_KiemTraComboboxSoThe_KiemTraHienThiMacDinh() {
		
		log.info("TC_06_Step_01: Lay So The hien thi trong combobox");
		debitCard_01 = lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout1");
		
		log.info("TC_06_Step_02: Click vao combobox So the");
		lockCard.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layout1");
		
		log.info("TC_06_Step_03: Xac nhan the hien thi mac dinh la the dau tien trong danh sach");
		verifyEquals(lockCard.getFirstOptionInDynamicListElements(driver, "com.VCB:id/tvContent"), debitCard_01);

	}
	
	@Test 
	public void TC_07_KiemTraComboboxSoThe_KiemTraChonSoThe() {
		
		log.info("TC_07_Step_01: Chon So the");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Lock_Card_Data.DEBIT_CARD01);
		
		log.info("TC_07_Step_02: Xac nhan the hien thi la the vua chon");
		verifyEquals(lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout1"), Lock_Card_Data.DEBIT_CARD01);
		
		log.info("TC_07_Step_03: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");
		
		log.info("TC_07_Step_04: Chon So the");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Lock_Card_Data.DEBIT_CARD03);
		
		log.info("TC_07_Step_05: Xac nhan the hien thi la the vua chon");
		verifyEquals(lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout1"), Lock_Card_Data.DEBIT_CARD03);
	}
	
	@Test
	public void TC_08_KiemTraButtonTiepTuc() {
		
		log.info("TC_08_Step_01: Click vao nut 'Tiep tuc'");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_08_Step_02: Xac nhan hien thi man hinh xac nhan thong tin");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");
	}
	
	@Test
	public void TC_09_ManHinhXacNhanThongTin_KiemTraHienThiMacDinh() {
		
		log.info("TC_09_Step_01: Xac nhan hien thi man hinh xac nhan thong tin");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");
		
		log.info("TC_09_Step_02: Xac nhan hien thi Icon Back ");
		verifyTrue(lockCard.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivTitleLeft"));

		log.info("TC_09_Step_03: Xac nhan hien thi thong bao kiem tra thong tin");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), Lock_Card_Data.VALIDATE.LOCK_VERIFY_TITLE_HEAD_MESSAGE);
		
		log.info("TC_09_Step_04: Xac nhan hien thi loai the");
		verifyEquals(lockCard.getDynamicTextByLabel(driver, "Loại thẻ"), Lock_Card_Data.VALIDATE.DEBIT_CARD_TEXT);
		
		log.info("TC_09_Step_05: Xac nhan hien thi so the");
		verifyEquals(lockCard.getDynamicTextByLabel(driver, "Số thẻ"), Lock_Card_Data.DEBIT_CARD03);
		
		log.info("TC_09_Step_06: Xac nhan hien thi thuong hieu the");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvKey1"), "Thương hiệu thẻ");
		
		log.info("TC_09_Step_07: Xac nhan hien thi Yeu cau");
		verifyEquals(lockCard.getDynamicTextByLabel(driver, "Yêu cầu"), "Khóa thẻ");
		
		log.info("TC_09_Step_08: Xac nhan hien thi buton 'Tiep tuc'");
		verifyTrue(lockCard.isDynamicButtonByIdEnable(driver, "com.VCB:id/btContinue"));
	}
	
	@Test
	public void TC_10_ManHinhXacNhanThongTin_KiemTraAnButtonTiepTuc() {
		
		log.info("TC_10_Step_01: Click vao nut 'Tiep tuc'");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_10_Step_02: Xac nhan hien thi man hinh thanh cong");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "KHÓA THẺ THÀNH CÔNG");
	}
	@Test
	public void TC_11_ManHinhKetQua_KiemTraManHinhHienThi() {
		
		log.info("TC_11_Step_01: Xac nhan hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver,  "com.VCB:id/tvTitle"), "KHÓA THẺ THÀNH CÔNG");

		log.info("TC_11_Step_02: Xac nhan hien thi Icon Home");
		verifyTrue(lockCard.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivHome"));

		log.info("TC_11_Step_03: Xac nhan hien thi icon Thanh cong");
		verifyTrue(lockCard.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));
		
		log.info("TC_11_Step_04: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(lockCard.isDynamicTextDetailByID(driver, "com.VCB:id/tvTime"));
		
		log.info("TC_11_Step_05: Xac nhan hien thi loai the");
		verifyEquals(lockCard.getDynamicTextByLabel(driver, "Loại thẻ"), Lock_Card_Data.VALIDATE.DEBIT_CARD_TEXT);
		
		log.info("TC_11_Step_06: Xac nhan hien thi so the");
		verifyEquals(lockCard.getDynamicTextByLabel(driver, "Số thẻ"), Lock_Card_Data.DEBIT_CARD03);
		
		log.info("TC_11_Step_07: Xac nhan hien thi ma giao dich");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "Mã giao dịch"));
		
		log.info("TC_11_Step_08: Hien thi Icon Chia se");
		verifyTrue(lockCard.isDynamicTextDetailByID(driver, "com.VCB:id/tvShare"));

		log.info("TC_11_Step_09: Hien thi Icon Luu anh");
		verifyTrue(lockCard.isDynamicTextDetailByID(driver, "com.VCB:id/tvSavePhoto"));

		log.info("TC_11_Step_10: Hien thi Button quay ve man hinh the");
		verifyTrue(lockCard.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btContinue"));
		
		log.info("TC_11_Step_11: An nut Home");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivHome");

		log.info("TC_11_Step_12: Xac nhan qua ve man hinh chinh");
		verifyTrue(lockCard.isDynamicImageHomeDisplay(driver, "com.VCB:id/menu_1"));
	}
	
	@Parameters ({"otp"})
	@Test
	public void TC_12_ManHinhKetQua_KiemTraNhanQuayVeManHinhMoKhoaThe(String otp){
		
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_12_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_12_Step_02: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);
		
		log.info("TC_12_Step_03: Click vao combobox Loai the");
		lockCard.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layout0");
		
		log.info("TC_12_Step_04: Chon the Tin dung");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Lock_Card_Data.VALIDATE.CREDIT_CARD_TEXT);
		
		log.info("TC_12_Step_05: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");
		
		log.info("TC_12_Step_06: Chon So the");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Lock_Card_Data.CREDIT_CARD_03);
		
		log.info("TC_12_Step_07: Click vao nut 'Tiep tuc'");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_12_Step_08: Click vao nut 'Tiep tuc'");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_12_Step_09: Xac nhan hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver,  "com.VCB:id/tvTitle"), "KHÓA THẺ THÀNH CÔNG");
		
		log.info("TC_12_Step_10: An Button Quay ve man hinh dich vu the");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_12_Step_11: Xac nhan qua ve man hinh chinh");
		verifyTrue(lockCard.isDynamicImageHomeDisplay(driver, "com.VCB:id/menu_1"));

	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
