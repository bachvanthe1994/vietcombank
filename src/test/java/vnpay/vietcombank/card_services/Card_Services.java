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

public class Card_Services extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private LockCardPageObject lockCard;
	private String numberCard = "";
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
	public void TC_01_KhoaTheGhiNoVCB() {
		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.scrollDownToText(driver, "Mua sắm");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_02: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_03: Mo DropdownList 'Loai The'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ ghi nợ");

		log.info("TC_01_Step_04: Chon 'The ghi no'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ ghi nợ");

		log.info("TC_01_Step_05: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_01_Step_06: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicTextContains(driver, "970436");
		numberCard = lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout1");

		log.info("TC_01_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_08: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Loại thẻ"), "Thẻ ghi nợ");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Khóa thẻ");

		log.info("TC_01_Step_09: An button 'Tiep tuc'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "KHÓA THẺ THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);

		log.info("TC_01_Step_10: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_11: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_12: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_13: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

//		log.info("TC_01_Step_14: Xac nhan the vua bi khoa bi xoa khoi danh sach");
//		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_01_Step_15: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_01_Step_16: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_17: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_18: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_19: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_01_Step_20: Xac nhan the vua bi khoa hien thi trong danh sach");
		verifyTrue(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));
	}

	@Test
	public void TC_02_MoKhoaTheGhiNoQuaOTP() {

		log.info("TC_02_Step_01: An chon the bat ki trong Dropdown");
		lockCard.scrollDownToTextContain(driver, "970436");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, numberCard);

		log.info("TC_02_Step_02: An chon checkbox Xac Nhan Dieu Khoan");
		lockCard.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_02_Step_03: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_04: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Loại thẻ"), "Thẻ ghi nợ");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Mở khóa thẻ");

		log.info("TC_02_Step_05: Mo DropdownList 'Phuong Thuc Xac Thuc'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_02_Step_06: Chọn Phuong Thuc 'SMS OTP'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_02_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_08: Nhap Ma OTP");
		lockCard.inputToDynamicOtp(driver, otpNumber, "Tiếp tục");

		log.info("TC_02_Step_09: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_10: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "MỞ KHÓA THẺ THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Loại thẻ"), "Thẻ ghi nợ");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);

		log.info("TC_02_Step_11: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_12: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_02_Step_13: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_02_Step_14: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_02_Step_15: Xac nhan the vua mo khoa hien thị trong danh sach");
		verifyTrue(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_2_Step_16: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_02_Step_17: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_18: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_02_Step_19: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_02_Step_20: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_02_Step_21: Xac nhan the vua mo khoa bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_02_Step_22: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_02_Step_23: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

	}

	@Test
	public void TC_03_KhoaTheGhiNoVisa() {
		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.scrollDownToText(driver, "Mua sắm");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_02: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_03: Mo DropdownList 'Loai The'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ ghi nợ");

		log.info("TC_01_Step_04: Chon 'The ghi no'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ ghi nợ");

		log.info("TC_01_Step_05: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_01_Step_06: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicTextContains(driver, "4283");
		numberCard = lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout1");

		log.info("TC_01_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_08: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Loại thẻ"), "Thẻ ghi nợ");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Khóa thẻ");

		log.info("TC_01_Step_09: An button 'Tiep tuc'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "KHÓA THẺ THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);

		log.info("TC_01_Step_10: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_11: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_12: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_13: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

//		log.info("TC_01_Step_14: Xac nhan the vua bi khoa bi xoa khoi danh sach");
//		verifyTrue(lockCard.isDynamicMessageAndLabelTextUndisplayed(driver, numberCard));

		log.info("TC_01_Step_15: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_01_Step_16: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_17: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_18: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_19: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_01_Step_20: Xac nhan the vua bi khoa hien thi trong danh sach");
		verifyTrue(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));
	}

	@Test
	public void TC_04_MoKhoaTheGhiNoQuaOTPTheGiNoVisa() {

		log.info("TC_02_Step_01: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, numberCard);

		log.info("TC_02_Step_02: An chon checkbox Xac Nhan Dieu Khoan");
		lockCard.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_02_Step_03: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_04: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Loại thẻ"), "Thẻ ghi nợ");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Mở khóa thẻ");

		log.info("TC_02_Step_05: Mo DropdownList 'Phuong Thuc Xac Thuc'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_02_Step_06: Chọn Phuong Thuc 'SMS OTP'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_02_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_08: Nhap Ma OTP");
		lockCard.inputToDynamicOtp(driver, otpNumber, "Tiếp tục");

		log.info("TC_02_Step_09: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_10: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "MỞ KHÓA THẺ THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Loại thẻ"), "Thẻ ghi nợ");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);

		log.info("TC_02_Step_11: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_12: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_02_Step_13: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_02_Step_14: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_02_Step_15: Xac nhan the vua mo khoa hien thị trong danh sach");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, numberCard));

		log.info("TC_2_Step_16: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_02_Step_17: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_18: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_02_Step_19: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_02_Step_20: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_02_Step_21: Xac nhan the vua mo khoa bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_02_Step_22: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_02_Step_23: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

	}

	@Test
	public void TC_05_KhoaTheUnionPay() {
		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.scrollDownToText(driver, "Mua sắm");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_02: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_03: Mo DropdownList 'Loai The'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ ghi nợ");

		log.info("TC_01_Step_04: Chon 'The ghi no'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ ghi nợ");

		log.info("TC_01_Step_05: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_01_Step_06: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicTextContains(driver, "6212");
		numberCard = lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout1");

		log.info("TC_01_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_08: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Loại thẻ"), "Thẻ ghi nợ");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Khóa thẻ");

		log.info("TC_01_Step_09: An button 'Tiep tuc'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "KHÓA THẺ THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);

		log.info("TC_01_Step_10: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_11: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_12: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_13: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

//		log.info("TC_01_Step_14: Xac nhan the vua bi khoa bi xoa khoi danh sach");
//		verifyTrue(lockCard.isDynamicMessageAndLabelTextUndisplayed(driver, numberCard));

		log.info("TC_01_Step_15: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_01_Step_16: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_17: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_18: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_19: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_01_Step_20: Xac nhan the vua bi khoa hien thi trong danh sach");
		verifyTrue(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));
	}

	@Test
	public void TC_06_MoKhoaTheGhiNoQuaOTPTheUnionPay() {

		log.info("TC_02_Step_01: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, numberCard);

		log.info("TC_02_Step_02: An chon checkbox Xac Nhan Dieu Khoan");
		lockCard.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_02_Step_03: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_04: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Loại thẻ"), "Thẻ ghi nợ");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Mở khóa thẻ");

		log.info("TC_02_Step_05: Mo DropdownList 'Phuong Thuc Xac Thuc'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_02_Step_06: Chọn Phuong Thuc 'SMS OTP'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_02_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_08: Nhap Ma OTP");
		lockCard.inputToDynamicOtp(driver, otpNumber, "Tiếp tục");

		log.info("TC_02_Step_09: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_10: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "MỞ KHÓA THẺ THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Loại thẻ"), "Thẻ ghi nợ");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);

		log.info("TC_02_Step_11: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_12: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_02_Step_13: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_02_Step_14: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_02_Step_15: Xac nhan the vua mo khoa hien thị trong danh sach");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, numberCard));

		log.info("TC_2_Step_16: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_02_Step_17: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_18: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_02_Step_19: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_02_Step_20: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_02_Step_21: Xac nhan the vua mo khoa bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_02_Step_22: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_02_Step_23: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

	}

	@Test
	public void TC_07_KhoaTheAmax() {
		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.scrollDownToText(driver, "Mua sắm");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_02: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_03: Mo DropdownList 'Loai The'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ ghi nợ");

		log.info("TC_01_Step_04: Chon 'The ghi no'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ ghi nợ");

		log.info("TC_01_Step_05: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_01_Step_06: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicTextContains(driver, "97");
		numberCard = lockCard.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/layout1");

		log.info("TC_01_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_08: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Loại thẻ"), "Thẻ ghi nợ");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Khóa thẻ");

		log.info("TC_01_Step_09: An button 'Tiep tuc'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "KHÓA THẺ THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);

		log.info("TC_01_Step_10: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_11: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_12: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_13: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

//		log.info("TC_01_Step_14: Xac nhan the vua bi khoa bi xoa khoi danh sach");
//		verifyTrue(lockCard.isDynamicMessageAndLabelTextUndisplayed(driver, numberCard));

		log.info("TC_01_Step_15: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_01_Step_16: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_17: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_18: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_19: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_01_Step_20: Xac nhan the vua bi khoa hien thi trong danh sach");
		verifyTrue(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));
	}

	@Test
	public void TC_08_MoKhoaTheGhiNoQuaOTPTheAmax() {

		log.info("TC_02_Step_01: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, numberCard);

		log.info("TC_02_Step_02: An chon checkbox Xac Nhan Dieu Khoan");
		lockCard.clickDynamicCheckBox(driver, "com.VCB:id/checkBox");

		log.info("TC_02_Step_03: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_04: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Loại thẻ"), "Thẻ ghi nợ");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Mở khóa thẻ");

		log.info("TC_02_Step_05: Mo DropdownList 'Phuong Thuc Xac Thuc'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_02_Step_06: Chọn Phuong Thuc 'SMS OTP'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_02_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_08: Nhap Ma OTP");
		lockCard.inputToDynamicOtp(driver, otpNumber, "Tiếp tục");

		log.info("TC_02_Step_09: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_10: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "MỞ KHÓA THẺ THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Loại thẻ"), "Thẻ ghi nợ");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), numberCard);

		log.info("TC_02_Step_11: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_12: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_02_Step_13: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_02_Step_14: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_02_Step_15: Xac nhan the vua mo khoa hien thị trong danh sach");
		verifyTrue(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_2_Step_16: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_02_Step_17: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_18: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_02_Step_19: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_02_Step_20: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_02_Step_21: Xac nhan the vua mo khoa bi xoa khoi danh sach");
		verifyFailure(lockCard.isTextDisplayedInListTextElements(driver, numberCard, "com.VCB:id/tvContent"));

		log.info("TC_02_Step_22: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_02_Step_23: Click ve 'Trang chu'");
		lockCard.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
