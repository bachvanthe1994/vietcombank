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

public class Card_Services extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private LockCardPageObject lockCard;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login("0918679292", "aaaa1111", opt);

	}

	@Test
	public void TC_01_KhoaTheGhiNo() {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Dich vu the'");
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
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Lock_Card_Data.DEBIT_CARD01);

		log.info("TC_01_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_08: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Loại thẻ"), "Thẻ ghi nợ");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), Lock_Card_Data.DEBIT_CARD01);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Khóa thẻ");

		log.info("TC_01_Step_09: An button 'Tiep tuc'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "KHÓA THẺ THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), Lock_Card_Data.DEBIT_CARD01);

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

		log.info("TC_01_Step_14: Xac nhan the vua bi khoa bi xoa khoi danh sach");
		verifyFailure(lockCard.isCardNumberDisplayedInDropdownList(Lock_Card_Data.DEBIT_CARD01));

		log.info("TC_01_Step_15: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_01_Step_16: Click ve 'Trang chu'");
		lockCard.clickBackToHomePage();
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_17: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_01_Step_18: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_01_Step_19: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_01_Step_20: Xac nhan the vua bi khoa hien thi trong danh sach");
		verifyTrue(lockCard.isCardNumberDisplayedInDropdownList(Lock_Card_Data.DEBIT_CARD01));
	}

	@Test
	public void TC_02_MoKhoaTheGhiNoQuaOTP() {

		log.info("TC_02_Step_01: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Lock_Card_Data.DEBIT_CARD01);

		log.info("TC_02_Step_02: An chon checkbox Xac Nhan Dieu Khoan");
		lockCard.clickToConfirmCheckBox();

		log.info("TC_02_Step_03: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_04: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Loại thẻ"), "Thẻ ghi nợ");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), Lock_Card_Data.DEBIT_CARD01);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Mở khóa thẻ");

		log.info("TC_02_Step_05: Mo DropdownList 'Phuong Thuc Xac Thuc'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_02_Step_06: Chọn Phuong Thuc 'SMS OTP'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_02_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_08: Nhap Ma OTP");
		lockCard.inputToDynamicOtp(driver, "123456", "Tiếp tục");

		log.info("TC_02_Step_09: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_10: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "MỞ KHÓA THẺ THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Loại thẻ"), "Thẻ ghi nợ");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), Lock_Card_Data.DEBIT_CARD01);

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
		verifyTrue(lockCard.isCardNumberDisplayedInDropdownList(Lock_Card_Data.DEBIT_CARD01));

		log.info("TC_2_Step_16: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_02_Step_17: Click ve 'Trang chu'");
		lockCard.clickBackToHomePage();
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_18: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_02_Step_19: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_02_Step_20: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_02_Step_21: Xac nhan the vua mo khoa bi xoa khoi danh sach");
		verifyFailure(lockCard.isCardNumberDisplayedInDropdownList(Lock_Card_Data.DEBIT_CARD01));

		log.info("TC_02_Step_22: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_02_Step_23: Click ve 'Trang chu'");
		lockCard.clickBackToHomePage();
		home = PageFactoryManager.getHomePageObject(driver);

	}

	@Test
	public void TC_03_KhoaTheTinDung() {

		log.info("TC_03_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_03_Step_02: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_03_Step_03: Mo DropdownList 'Loai The'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ ghi nợ");

		log.info("TC_03_Step_04: Chon 'The tin dung'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ tín dụng");

		log.info("TC_03_Step_05: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_03_Step_06: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Lock_Card_Data.CREDIT_CARD01);

		log.info("TC_03_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_08: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Loại thẻ"), "Thẻ tín dụng");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), Lock_Card_Data.CREDIT_CARD01);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Khóa thẻ");

		log.info("TC_03_Step_09: An button 'Tiep tuc'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "KHÓA THẺ THÀNH CÔNG"));
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, Lock_Card_Data.CREDIT_CARD01));

		log.info("TC_03_Step_10: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_03_Step_11: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_03_Step_12: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_03_Step_13: Mo DropdownList 'Loai The'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ ghi nợ");

		log.info("TC_03_Step_14: Chon 'The tin dung'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ tín dụng");

		log.info("TC_03_Step_15: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_03_Step_16: Xac nhan the bi khoa bi xoa khoi danh sach");
		verifyFailure(lockCard.isCardNumberDisplayedInDropdownList(Lock_Card_Data.CREDIT_CARD01));

		log.info("TC_03_Step_17: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_03_Step_18: Click ve 'Trang chu'");
		lockCard.clickBackToHomePage();
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_03_Step_19: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_03_Step_20: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_03_Step_21: Mo DropdownList 'Loai The'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ ghi nợ");

		log.info("TC_03_Step_22: Chon 'The tin dung'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ tín dụng");

		log.info("TC_03_Step_23: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_03_Step_24: Xac nhan the bi khoa bi xoa khoi danh sach");
		verifyTrue(lockCard.isCardNumberDisplayedInDropdownList(Lock_Card_Data.CREDIT_CARD01));
	}

	@Test
	public void TC_04_MoKhoaTheTinDungQuaOTP() {

		log.info("TC_04_Step_01: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Lock_Card_Data.CREDIT_CARD01);

		log.info("TC_04_Step_02: An chon checkbox Xac Nhan Dieu Khoan");
		lockCard.clickToConfirmCheckBox();

		log.info("TC_04_Step_03: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_04: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Loại thẻ"), "Thẻ tín dụng");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), Lock_Card_Data.CREDIT_CARD01);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Mở khóa thẻ");

		log.info("TC_04_Step_05: Mo DropdownList 'Phuong Thuc Xac Thuc'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_04_Step_06: Chọn Phuong Thuc 'SMS OTP'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_04_Step_07: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_08: Nhap Ma OTP");
		lockCard.inputToDynamicOtp(driver, "123456", "Tiếp tục");

		log.info("TC_04_Step_09: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_10: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "MỞ KHÓA THẺ THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Loại thẻ"), "Thẻ tín dụng");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), Lock_Card_Data.CREDIT_CARD01);

		log.info("TC_04_Step_11: An button 'Quan ve man hinh dich vu the'");
		lockCard.clickToDynamicButton(driver, "Quay về màn hình dịch vụ thẻ");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_04_Step_12: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_04_Step_13: An vao tab 'Khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_04_Step_14: Mo DropdownList 'Loai The'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ ghi nợ");

		log.info("TC_04_Step_15: Chon 'The tin dung'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ tín dụng");

		log.info("TC_04_Step_16: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_04_Step_17: Xac nhan the vua mo khoa hien thị trong danh sach");
		verifyTrue(lockCard.isCardNumberDisplayedInDropdownList(Lock_Card_Data.CREDIT_CARD01));

		log.info("TC_04_Step_18: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_04_Step_19: Click ve 'Trang chu'");
		lockCard.clickBackToHomePage();
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_04_Step_20: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_04_Step_21: An vao tab 'Mo khoa the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mở khóa thẻ");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_04_Step_22: Mo DropdownList 'Loai The'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ ghi nợ");

		log.info("TC_04_Step_23: Chon 'The tin dung'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Thẻ tín dụng");

		log.info("TC_04_Step_24: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "2");

		log.info("TC_04_Step_25: Xac nhan the vua mo khoa bi xoa khoi danh sach");
		verifyFailure(lockCard.isCardNumberDisplayedInDropdownList(Lock_Card_Data.CREDIT_CARD01));

		log.info("TC_04_Step_26: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_04_Step_27: Click ve 'Trang chu'");
		lockCard.clickBackToHomePage();
		home = PageFactoryManager.getHomePageObject(driver);
	}

	@Test
	public void TC_05_DangKySuDungTheTrenInternet() {

		log.info("TC_05_Step_01: Keo xuong va click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_05_Step_02: An vao tab 'Dang ky su dung the tren internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_05_Step_03: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_05_Step_04: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Lock_Card_Data.CREDIT_CARD02);

		log.info("TC_05_Step_05: An chon checkbox Xac Nhan Dieu Khoan");
		lockCard.clickToConfirmCheckBox();

		log.info("TC_05_Step_06: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_07: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), Lock_Card_Data.CREDIT_CARD02);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Đăng ký sử dụng thẻ trên Internet");

		log.info("TC_05_Step_08: Mo DropdownList 'Phuong Thuc Xac Thuc'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_05_Step_09: Chọn Phuong Thuc 'SMS OTP'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_05_Step_10: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_11: Nhap 'SMS OTP'");
		lockCard.inputToDynamicOtp(driver, "123456", "Tiếp tục");

		log.info("TC_05_Step_12: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_13: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "ĐĂNG KÝ SỬ DỤNG THẺ TRÊN INTERNET THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), Lock_Card_Data.CREDIT_CARD02);

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
		verifyFailure(lockCard.isCardNumberDisplayedInDropdownList(Lock_Card_Data.CREDIT_CARD02));

		log.info("TC_05_Step_19: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_05_Step_20: Click ve 'Trang chu'");
		lockCard.clickBackToHomePage();
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_05_Step_21: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_05_Step_22: An vao tab 'Huy dang ky su dung the tren Internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hủy đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_05_Step_23: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_05_Step_24: Xac nhan the vua dang ky Internet hien thi trong danh sach");
		verifyTrue(lockCard.isCardNumberDisplayedInDropdownList(Lock_Card_Data.CREDIT_CARD02));
	}

	@Test
	public void TC_06_HuyDangKySuDungTheTrenInternet() {

		log.info("TC_06_Step_01: An chon the bat ki trong Dropdown");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, Lock_Card_Data.CREDIT_CARD02);

		log.info("TC_06_Step_02: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_06_Step_03: Hien thi phan 'Xac nhan thong tin'");
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), Lock_Card_Data.CREDIT_CARD02);
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Yêu cầu"), "Hủy đăng ký sử dụng thẻ trên Internet");

		log.info("TC_06_Step_04: An button 'Tiep tục'");
		lockCard.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_06_Step_04: Hien thi phan 'Xac nhan thong tin'");
		verifyTrue(lockCard.isDynamicMessageAndLabelTextDisplayed(driver, "HỦY ĐĂNG KÝ SỬ DỤNG THẺ TRÊN INTERNET THÀNH CÔNG"));
		verifyEquals(lockCard.getDynamicTextInTransactionDetail(driver, "Số thẻ"), Lock_Card_Data.CREDIT_CARD02);

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
		verifyTrue(lockCard.isCardNumberDisplayedInDropdownList(Lock_Card_Data.CREDIT_CARD02));

		log.info("TC_06_Step_10: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_06_Step_11: Click ve 'Trang chu'");
		lockCard.clickBackToHomePage();
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_06_Step_12: Click vao phan 'Dich vu the'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Dịch vụ thẻ");

		log.info("TC_06_Step_13: An vao tab 'Huy dang ky su dung the tren Internet'");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Hủy đăng ký sử dụng thẻ trên Internet");
		lockCard = PageFactoryManager.LockCardPageObject(driver);

		log.info("TC_06_Step_14: Mo DropdownList 'So The'");
		lockCard.clickToDynamicDropdownByHeader(driver, "Thông tin thẻ", "1");

		log.info("TC_06_Step_15: Xac nhan the vua huy dang ky Internet bi xoa khoi danh sach");
		verifyFailure(lockCard.isCardNumberDisplayedInDropdownList(Lock_Card_Data.CREDIT_CARD02));

		log.info("TC_06_Step_16: Click vào nút 'Dong'");
		lockCard.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_06_Step_17: Click ve 'Trang chu'");
		lockCard.clickBackToHomePage();
		home = PageFactoryManager.getHomePageObject(driver);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
