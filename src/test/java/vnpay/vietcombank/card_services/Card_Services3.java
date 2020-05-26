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

public class Card_Services3 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private LockCardPageObject lockCard;
	String numberCard = "";
	private String otpNumber;

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
		login.Global_login1("0974862666", "aaaa1111", opt);
		otpNumber = opt;
		home = PageFactoryManager.getHomePageObject(driver);

	}

	@Test
	public void TC_01_DangKySuDungTheTrenInternetVCB() {

		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_02: An vao tab 'Dang ky su dung the tren internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_03: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_01_Step_04: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicTextContains(driver, "4032");
		numberCard = lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/llContent");

		log.info("TC_01_Step_05: An chon checkbox Xac Nhan Dieu Khoan");
		lockCard.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_01_Step_06: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_07: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Đăng ký sử dụng thẻ trên Internet");

		log.info("TC_01_Step_08: Mo DropdownList 'Phuong Thuc Xac Thuc'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_01_Step_09: Chọn Phuong Thuc 'SMS OTP'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_01_Step_10: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_11: Nhap 'SMS OTP'");
		lockCard.inputToDynamicOtp(driver, otpNumber, "Tiếp tục");

		log.info("TC_01_Step_12: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_13: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "ĐĂNG KÝ SỬ DỤNG THẺ TRÊN INTERNET THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);

		log.info("TC_01_Step_14: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_15: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_16: An vao tab 'Dang ky su dung the tren internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_17: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_01_Step_18: Xac nhan the vua dang ky Internet bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_01_Step_19: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_01_Step_20: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_21: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_22: An vao tab 'Huy dang ky su dung the tren Internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hủy đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_23: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_01_Step_24: Xac nhan the vua dang ky Internet hien thi trong danh sach");
		lockCard.scrollDownToText(driver, numberCard);
		verifyTrue(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));
	}

	@Test
	public void TC_02_HuyDangKySuDungTheTrenInternetVCB() {

		log.info("TC_02_Step_01: An chon the bat ki trong Dropdown");
		
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, numberCard);;

		log.info("TC_02_Step_02: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_03: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Hủy đăng ký sử dụng thẻ trên Internet");

		log.info("TC_02_Step_04: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_04: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "HỦY ĐĂNG KÝ SỬ DỤNG THẺ TRÊN INTERNET THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);

		log.info("TC_02_Step_05: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_06: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_02_Step_07: An vao tab 'Dang ky su dung the tren internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_02_Step_08: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_02_Step_09: Xac nhan the vua huy dang ky Internet hien thi trong danh sach");
		lockCard.scrollDownToText(driver, numberCard);
		verifyTrue(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_02_Step_10: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_02_Step_11: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_12: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_02_Step_13: An vao tab 'Huy dang ky su dung the tren Internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hủy đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_02_Step_14: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_02_Step_15: Xac nhan the vua huy dang ky Internet bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_02_Step_16: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_02_Step_17: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

	}

	@Test
	public void TC_03_DangKySuDungTheTrenInternetJCB() {

		log.info("TC_03_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_03_Step_02: An vao tab 'Dang ky su dung the tren internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_03_Step_03: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_03_Step_04: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicTextContains(driver, "3567");
		numberCard = lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/llContent");

		log.info("TC_03_Step_05: An chon checkbox Xac Nhan Dieu Khoan");
		lockCard.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_03_Step_06: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_07: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Đăng ký sử dụng thẻ trên Internet");

		log.info("TC_03_Step_08: Mo DropdownList 'Phuong Thuc Xac Thuc'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_03_Step_09: Chọn Phuong Thuc 'SMS OTP'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_03_Step_10: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_11: Nhap 'SMS OTP'");
		lockCard.inputToDynamicOtp(driver, otpNumber, "Tiếp tục");

		log.info("TC_03_Step_12: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_13: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "ĐĂNG KÝ SỬ DỤNG THẺ TRÊN INTERNET THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);

		log.info("TC_03_Step_14: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_03_Step_15: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_03_Step_16: An vao tab 'Dang ky su dung the tren internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_03_Step_17: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_03_Step_18: Xac nhan the vua dang ky Internet bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_03_Step_19: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_03_Step_20: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_03_Step_21: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_03_Step_22: An vao tab 'Huy dang ky su dung the tren Internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hủy đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_03_Step_23: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_03_Step_24: Xac nhan the vua dang ky Internet hien thi trong danh sach");
		lockCard.scrollDownToText(driver, numberCard);
		verifyTrue(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));
	}

	@Test
	public void TC_04_HuyDangKySuDungTheTrenInternet() {

		log.info("TC_04_Step_01: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, numberCard);;

		log.info("TC_04_Step_02: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_03: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Hủy đăng ký sử dụng thẻ trên Internet");

		log.info("TC_04_Step_04: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_04: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "HỦY ĐĂNG KÝ SỬ DỤNG THẺ TRÊN INTERNET THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);

		log.info("TC_04_Step_05: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_04_Step_06: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_04_Step_07: An vao tab 'Dang ky su dung the tren internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_04_Step_08: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_04_Step_09: Xac nhan the vua huy dang ky Internet hien thi trong danh sach");
		lockCard.scrollDownToText(driver, numberCard);
		verifyTrue(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_04_Step_10: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_04_Step_11: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_04_Step_12: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_04_Step_13: An vao tab 'Huy dang ky su dung the tren Internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hủy đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_04_Step_14: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_04_Step_15: Xac nhan the vua huy dang ky Internet bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_04_Step_16: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_04_Step_17: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

	}

	@Test
	public void TC_05_DangKySuDungTheTrenInternetMasterCard() {

		log.info("TC_05_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_05_Step_02: An vao tab 'Dang ky su dung the tren internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_05_Step_03: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_05_Step_04: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicTextContains(driver, "5462");
		numberCard = lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/llContent");

		log.info("TC_05_Step_05: An chon checkbox Xac Nhan Dieu Khoan");
		lockCard.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_05_Step_06: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_07: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Đăng ký sử dụng thẻ trên Internet");

		log.info("TC_05_Step_08: Mo DropdownList 'Phuong Thuc Xac Thuc'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_05_Step_09: Chọn Phuong Thuc 'SMS OTP'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_05_Step_10: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_11: Nhap 'SMS OTP'");
		lockCard.inputToDynamicOtp(driver,otpNumber, "Tiếp tục");

		log.info("TC_05_Step_12: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_13: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "ĐĂNG KÝ SỬ DỤNG THẺ TRÊN INTERNET THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);

		log.info("TC_05_Step_14: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_05_Step_15: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_05_Step_16: An vao tab 'Dang ky su dung the tren internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_05_Step_17: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_05_Step_18: Xac nhan the vua dang ky Internet bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_05_Step_19: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_05_Step_20: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_05_Step_21: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_05_Step_22: An vao tab 'Huy dang ky su dung the tren Internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hủy đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_05_Step_23: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_05_Step_24: Xac nhan the vua dang ky Internet hien thi trong danh sach");
		lockCard.scrollDownToText(driver, numberCard);
		verifyTrue(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));
	}

	@Test
	public void TC_06_HuyDangKySuDungTheTrenInternetMasterCard() {

		log.info("TC_06_Step_01: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, numberCard);;

		log.info("TC_06_Step_02: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_06_Step_03: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Hủy đăng ký sử dụng thẻ trên Internet");

		log.info("TC_06_Step_04: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_06_Step_04: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "HỦY ĐĂNG KÝ SỬ DỤNG THẺ TRÊN INTERNET THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);

		log.info("TC_06_Step_05: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_06_Step_06: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_06_Step_07: An vao tab 'Dang ky su dung the tren internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_06_Step_08: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_06_Step_09: Xac nhan the vua huy dang ky Internet hien thi trong danh sach");
		lockCard.scrollDownToText(driver, numberCard);
		verifyTrue(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_06_Step_10: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_06_Step_11: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_06_Step_12: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_06_Step_13: An vao tab 'Huy dang ky su dung the tren Internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hủy đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_06_Step_14: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_06_Step_15: Xac nhan the vua huy dang ky Internet bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_06_Step_16: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_06_Step_17: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

	}

	@Test
	public void TC_07_DangKySuDungTheTrenInternetAmex() {

		log.info("TC_07_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_07_Step_02: An vao tab 'Dang ky su dung the tren internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_07_Step_03: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_07_Step_04: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicTextContains(driver, "3791");
		numberCard = lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/llContent");

		log.info("TC_07_Step_05: An chon checkbox Xac Nhan Dieu Khoan");
		lockCard.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_07_Step_06: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_Step_07: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Đăng ký sử dụng thẻ trên Internet");

		log.info("TC_07_Step_08: Mo DropdownList 'Phuong Thuc Xac Thuc'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_07_Step_09: Chọn Phuong Thuc 'SMS OTP'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_07_Step_10: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_Step_11: Nhap 'SMS OTP'");
		lockCard.inputToDynamicOtp(driver, otpNumber, "Tiếp tục");

		log.info("TC_07_Step_12: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_Step_13: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "ĐĂNG KÝ SỬ DỤNG THẺ TRÊN INTERNET THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);

		log.info("TC_07_Step_14: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_07_Step_15: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_07_Step_16: An vao tab 'Dang ky su dung the tren internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_07_Step_17: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_07_Step_18: Xac nhan the vua dang ky Internet bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_07_Step_19: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_07_Step_20: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_07_Step_21: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_07_Step_22: An vao tab 'Huy dang ky su dung the tren Internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hủy đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_07_Step_23: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_07_Step_24: Xac nhan the vua dang ky Internet hien thi trong danh sach");
		lockCard.scrollDownToText(driver, numberCard);
		verifyTrue(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));
	}

	@Test
	public void TC_08_HuyDangKySuDungTheTrenInternet() {

		log.info("TC_08_Step_01: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, numberCard);;

		log.info("TC_08_Step_02: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_08_Step_03: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Hủy đăng ký sử dụng thẻ trên Internet");

		log.info("TC_08_Step_04: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_08_Step_04: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "HỦY ĐĂNG KÝ SỬ DỤNG THẺ TRÊN INTERNET THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);

		log.info("TC_08_Step_05: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_08_Step_06: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_08_Step_07: An vao tab 'Dang ky su dung the tren internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_08_Step_08: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_08_Step_09: Xac nhan the vua huy dang ky Internet hien thi trong danh sach");
		lockCard.scrollDownToText(driver, numberCard);
		verifyTrue(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_08_Step_10: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_08_Step_11: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_08_Step_12: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_08_Step_13: An vao tab 'Huy dang ky su dung the tren Internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hủy đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_08_Step_14: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_08_Step_15: Xac nhan the vua huy dang ky Internet bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_08_Step_16: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_08_Step_17: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
