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

public class Register_Internet_Card_Validate extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private LockCardPageObject lockCard;

	private String card01;

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
	public void TC_01_DangKySuDungTheTrenInternet_KiemTraManHinhHienThi() {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_02: An vao tab 'Dang ky su dung the tren Internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_03: Xac nhan hien thi title 'Dang ky su dung the tren Internet' ");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Card_Services_Data.VALIDATE.REGISTER_INTERNET_CARD_TITLE);

		log.info("TC_01_Step_04: Xac nhan hien thi Icon Back ");
		verifyTrue(lockCard.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivTitleLeft"));

		log.info("TC_01_Step_05: Xac nhan hien thi label 'Thong tin the'");
		verifyEquals(lockCard.getTextDynamicFollowLayout(driver, "com.VCB:id/llTitle"), "Thông tin thẻ");

		log.info("TC_01_Step_06: Xac nhan hien thi Combobox thong tin the");
		verifyTrue(lockCard.isDynamicTextViewByLinearLayoutIdDisplayed(driver, "com.VCB:id/llContent"));

		log.info("TC_01_Step_07: Xac nhan hien thi anh thuong hieu the ");
		verifyTrue(lockCard.isDynamicImageViewByLinearLayoutIdDisplayed(driver, "com.VCB:id/llContent"));

		log.info("TC_01_Step_08: Xac nhan hien thi check box dieu khoan");
		verifyTrue(lockCard.isDynamicCheckboxByCheckboxIdDisplayed(driver, "com.VCB:id/checkBox"));

		log.info("TC_01_Step_09: Xac nhan hien thi buton 'Tiep tuc'");
		verifyEquals(lockCard.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Tiếp tục");
		verifyTrue(lockCard.isDynamicButtonByIdEnable(driver, "com.VCB:id/btContinue"));

	}

	@Test
	public void TC_02_DangKySuDungTheTrenInternet_KiemTraAnIconBack() {

		log.info("TC_02_Step_01: An Icon Back");
		lockCard.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step_02: Xac nhan hien thi man hinh chinh");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "Dịch vụ thẻ"));
	}

	@Test
	public void TC_03_KiemTraComboboxSoThe_KiemTraHienThiMacDinh() {

		log.info("TC_03_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_03_Step_02: An vao tab 'Dang ky su dung the tren Internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký sử dụng thẻ trên Internet");
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
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Data.CREDIT_CARD02);

		log.info("TC_04_Step_02: Xac nhan the hien thi la the vua chon");
		verifyEquals(lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/llContent"), Card_Services_Data.CREDIT_CARD02);

		log.info("TC_04_Step_03: Click vao combobox");
		lockCard.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llContent");

		log.info("TC_04_Step_04: Chon So the");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Data.CREDIT_CARD03);

		log.info("TC_04_Step_05: Xac nhan the hien thi la the vua chon");
		verifyEquals(lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/llContent"), Card_Services_Data.CREDIT_CARD03);
	}

	@Test
	public void TC_05_KiemTraCheckBoxDieuKhoan() {

		log.info("TC_05_Step_01: Xac nhan checkbox Dieu Khoan dang la uncheck");
		verifyFailure(lockCard.isDynamicCheckBoxByIdChecked(driver, "com.VCB:id/checkBox"));

		log.info("TC_05_Step_02: Click vao checkbox");
		lockCard.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_05_Step_03: Xac nhan checkbox Dieu Khoan dang la checked");
		verifyTrue(lockCard.isDynamicCheckBoxByIdChecked(driver, "com.VCB:id/checkBox"));

		log.info("TC_05_Step_04: Click vao checkbox");
		lockCard.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_05_Step_05: Xac nhan checkbox Dieu Khoan dang la uncheck");
		verifyFailure(lockCard.isDynamicCheckBoxByIdChecked(driver, "com.VCB:id/checkBox"));
	}

	@Test
	public void TC_06_KiemTraButtonTiepTuc() {

		log.info("TC_06_Step_01: Click vao nut 'Tiep tuc'");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_06_Step_02: Xac nhan hien thi pop-up thong bao");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Card_Services_Data.VALIDATE.CHECKBOX_UNCHECK_MESSAGE);
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_06_Step_03: Click vao checkbox");
		lockCard.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_06_Step_04: Click vao nut 'Tiep tuc'");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_06_Step_05: Xac nhan hien thi man hinh xac nhan thong tin");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");
	}

	@Test
	public void TC_07_ManHinhXacNhanThongTin_KiemTraHienThiMacDinh() {

		log.info("TC_07_Step_01: Xac nhan hien thi man hinh xac nhan thong tin");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_07_Step_02: Xac nhan hien thi Icon Back ");
		verifyTrue(lockCard.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivTitleLeft"));

		log.info("TC_07_Step_03: Xac nhan hien thi thong bao kiem tra thong tin");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), Card_Services_Data.VALIDATE.REGISTER_INTERNET_VERIFY_TITLE_HEAD_MESSAGE);

		log.info("TC_07_Step_04: Xac nhan hien thi so the");
		verifyEquals(lockCard.getDynamicTextByLabel(driver, "Số thẻ"), Card_Services_Data.CREDIT_CARD03);

		log.info("TC_07_Step_05: Xac nhan hien thi thuong hieu the");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvKey1"), "Thương hiệu thẻ");

		log.info("TC_07_Step_06: Xac nhan hien thi Yeu cau");
		verifyEquals(lockCard.getDynamicTextByLabel(driver, "Yêu cầu"), "Đăng ký sử dụng thẻ trên Internet");

		log.info("TC_07_Step_07: Xac nhan hien thi label Phuong thuc xac nhan");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "Chọn phương thức xác thực");

		log.info("TC_07_Step_08: Xac nhan hien thi Combobox Phuong thuc xac nhan");
		verifyTrue(lockCard.isDynamicTextDetailByID(driver, "com.VCB:id/tvptxt"));

		log.info("TC_07_Step_09: Xac nhan hien thi buton 'Tiep tuc'");
		verifyEquals(lockCard.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Tiếp tục");
		verifyTrue(lockCard.isDynamicButtonByIdEnable(driver, "com.VCB:id/btContinue"));
	}

	@Test
	public void TC_08_ManHinhXacNhanThongTin_KiemTraAnButtonTiepTuc() {

		log.info("TC_08_Step_01: Click vao nut 'Tiep tuc'");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_08_Step_02: Xac nhan hien thi pop-up Xac thuc");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/lblTitle"), "Xác thực");
	}

	@Parameters({ "phone" })
	@Test
	public void TC_09_XacThucBangSMSOTP_KiemTraManHinhHienThiMacDinh(String phone) {

		log.info("TC_09_Step_01: Xac nhan hien thi pop-up Xac thuc");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/lblTitle"), "Xác thực");

		log.info("TC_09_Step_02: Xac nhan hien thi noi dung pop-up");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/lblMessage"), Card_Services_Data.VALIDATE.OTP_METHOD_DESCRIPTION + " " + phone.substring(0, 3) + "*****" + phone.substring(8, 10));

		log.info("TC_09_Step_03: Xac nhan hien thi phan nhap ma OTP");
		verifyTrue(lockCard.isDynamicTextDetailByID(driver, "com.VCB:id/otp"));

		log.info("TC_09_Step_04: Xac nhan hien thi buton 'Tiep tuc'");
		verifyEquals(lockCard.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Tiếp tục");
		verifyTrue(lockCard.isDynamicButtonByIdEnable(driver, "com.VCB:id/btContinue"));
	}

	@Test
	public void TC_10_XacThucBangSMSOTP_KiemTraTextNhapSMSOTP() {

		log.info("TC_10_Step_01: Xac nhan hien thi o text nhap OTP trong");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/otp"), "");

		log.info("TC_10_Step_02: Nhap lon hon 6 ki tu");
		lockCard.inputToDynamicOtp(driver, "2345671", "Tiếp tục");

		log.info("TC_10_Step_03: Xac nhan chi nhap duoc 6 ki tu");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/otp"), "234567");

		log.info("TC_10_Step_04: De trong o nhap ma OTP roi click nut 'Tiep tuc'");
		lockCard.inputToDynamicOtp(driver, "", "Tiếp tục");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_10_Step_05: Xac nhan hien thi thong bao bat nhap ma OTP");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Card_Services_Data.VALIDATE.OTP_METHOD_EMPTY_MESSAGE);
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_11_XacThucBangSMSOTP_KiemTraNhapSMSOTP(String otp) {

		log.info("TC_11_Step_01: Nhap nho hon 6 ki tu roi click nut 'Tiep tuc'");
		lockCard.inputToDynamicOtp(driver, "23456", "Tiếp tục");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_11_Step_02: Xac nhan hien thi thong bao bat nhap du 6 ki tu ");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Card_Services_Data.VALIDATE.OTP_METHOD_LESS_THAN_6_CHARACTER_MESSAGE);
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_11_Step_03: Nhap sai ma OTP roi click nut 'Tiep tuc'");
		lockCard.inputToDynamicOtp(driver, "234567", "Tiếp tục");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_11_Step_04: Xac nhan hien thi thong bao nhap sai ma OTP ");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Card_Services_Data.VALIDATE.OTP_METHOD_ERROR_MESSAGE);
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_11_Step_05: Nhap dung ma OTP roi click nut 'Tiep tuc'");
		lockCard.inputToDynamicOtp(driver, otp, "Tiếp tục");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_11_Step_06: Xac nhan hien thi man hinh ket qua giao dich ");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "ĐĂNG KÝ SỬ DỤNG THẺ TRÊN INTERNET THÀNH CÔNG");
	}

	@Test
	public void TC_12_ManHinhKetQua_KiemTraManHinhHienThi() {

		log.info("TC_12_Step_01: Xac nhan hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "ĐĂNG KÝ SỬ DỤNG THẺ TRÊN INTERNET THÀNH CÔNG");

		log.info("TC_12_Step_02: Xac nhan hien thi Icon Home");
		verifyTrue(lockCard.isDynamicImageHomeDisplay(driver, "com.VCB:id/ivHome"));

		log.info("TC_12_Step_03: Xac nhan hien thi icon Thanh cong");
		verifyTrue(lockCard.isDynamicImageByFollowingImageIdDisplayed(driver, "com.VCB:id/ivHome"));

		log.info("TC_12_Step_04: Xac nhan hien thi thoi gian giao dich");
		verifyTrue(lockCard.isDynamicTextDetailByID(driver, "com.VCB:id/tvTime"));

		log.info("TC_12_Step_06: Xac nhan hien thi so the");
		verifyEquals(lockCard.getDynamicTextByLabel(driver, "Số thẻ"), Card_Services_Data.CREDIT_CARD03);

		log.info("TC_12_Step_07: Xac nhan hien thi ma giao dich");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "Mã giao dịch"));

		log.info("TC_12_Step_08: Hien thi Icon Chia se");
		verifyTrue(lockCard.isDynamicTextDetailByID(driver, "com.VCB:id/tvShare"));

		log.info("TC_12_Step_09: Hien thi Icon Luu anh");
		verifyTrue(lockCard.isDynamicTextDetailByID(driver, "com.VCB:id/tvSavePhoto"));

		log.info("TC_12_Step_10: Hien thi Button quay ve man hinh the");
		verifyEquals(lockCard.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Quay về màn hình dịch vụ thẻ");
		verifyTrue(lockCard.isDynamicButtonByIdDisplayed(driver, "com.VCB:id/btContinue"));

		log.info("TC_12_Step_11: An nut Home");
		lockCard.clickToDynamicImageViewByID(driver, "com.VCB:id/ivHome");

		log.info("TC_12_Step_12: Xac nhan qua ve man hinh chinh");
		verifyTrue(lockCard.isDynamicImageHomeDisplay(driver, "com.VCB:id/menu_1"));
	}

	@Parameters({ "otp" })
	@Test
	public void TC_13_ManHinhKetQua_KiemTraNhanQuayVeManHinhDichVuThe(String otp) {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_13_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_13_Step_02: An vao tab 'Dang ky su dung the tren Internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_13_Step_03: Click vao combobox");
		lockCard.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llContent");

		log.info("TC_13_Step_04: Chon So the");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Card_Services_Data.CREDIT_CARD02);

		log.info("TC_13_Step_07: Click vao checkbox");
		lockCard.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_13_Step_08: Click vao nut 'Tiep tuc'");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_13_Step_09: Click vao nut 'Tiep tuc'");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_13_Step_10: Nhap dung ma OTP roi click nut 'Tiep tuc'");
		lockCard.inputToDynamicOtp(driver, otp, "Tiếp tục");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_13_Step_11: Xac nhan hien thi man hinh thong bao giao dich thanh cong");
		verifyEquals(lockCard.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "ĐĂNG KÝ SỬ DỤNG THẺ TRÊN INTERNET THÀNH CÔNG");

		log.info("TC_13_Step_12: An Button Thuc hien giao dich moi");
		lockCard.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_13_Step_13: Xac nhan qua ve man hinh chinh");
		verifyTrue(lockCard.isDynamicImageHomeDisplay(driver, "com.VCB:id/menu_1"));
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
