package vnpay.vietcombank.investigation_online;

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

import pageObjects.InvestigationOnlinePageObject;

import vietcombank_test_data.Register_Online_data;

public class Flow_Investigation_Online extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;

	private HomePageObject homePageObject;
	private InvestigationOnlinePageObject investigationOnline;

	String phoneNumber = "";
	String nameCustomer = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		phoneNumber = phone;

		homePageObject=PageFactoryManager.getHomePageObject(driver);
		homePageObject.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");
		
	}

	@Test
	public void TC_01_TraSoatLapYeuCauTraSoat() {
		homePageObject.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");
		homePageObject.clickToDynamicButtonLinkOrLinkText(driver, "Tra soát trực tuyến");
		investigationOnline = PageFactoryManager.getInvestigationOnline(driver);
		investigationOnline.isDynamicMessageAndLabelTextDisplayed(driver, "Loại yêu cầu tra soát");
        investigationOnline.clickToDynamicButtonLinkOrLinkText(driver, "Lập yêu cầu tra soát");

		

	}

	@Test
	public void TC_02_TraSoatTraCuuYeuCauTraSoat() {

	
	}

	@Test
	public void TC_03_GuiTietKiem_TaiKhoanTienGuiCoKyHanTienMat() {

		log.info("TC_03_Step: Click gui tiet kiem");
		investigationOnline.clickToDynamicButtonLinkOrLinkText(driver, "Gửi/rút tiết kiệm");
		investigationOnline.clickToDynamicButtonLinkOrLinkText(driver, "Gửi tiết kiệm");

		log.info("TC_03_Step: Nhap dia chi lien he");
		investigationOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.ADDRESS, "com.VCB:id/layoutDiaChiLH");

		log.info("TC_03_Step: Click giay to tuy than");
		investigationOnline.clickToDynamicDropDown(driver, "Giấy tờ tùy thân");
		investigationOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.TYPE_IDENTIFICATION[1]);

		log.info("TC_03_Step: Input so ho chieu");
		investigationOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.NO_IDENTIFICATION, "com.VCB:id/layoutCMTND");

		log.info("TC_03_Step: Click ngay cap");
		investigationOnline.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/layoutNgayCap");

		log.info("TC_03_Step: Click nam");
		investigationOnline.clickToTextID(driver, "android:id/date_picker_header_year");

		log.info("TC_03_Step: Chon nam cap");
		investigationOnline.clickToTextListview(driver, "android:id/date_picker_year_picker", getBackWardYear(1));

		log.info("TC_03_Step: Click OK");
		investigationOnline.clickToDynamicButton(driver, "OK");

		log.info("TC_03_Step: Input noi cap");
		investigationOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.LOCATION[1], "com.VCB:id/layoutNoiCap2");

		log.info("TC_03_Step: Nhap email");
		investigationOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.EMAIL, "com.VCB:id/layoutEmail");

		log.info("TC_03_Step: Chon hinh thuc gui tien co ky han");
		investigationOnline.scrollDownToText(driver, "Tiền mặt");
		investigationOnline.clickToDynamicLinerLayoutID(driver, "com.VCB:id/layoutHinhThucGuiTien");
		investigationOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_SEND_MONEY[0]);

		log.info("TC_03_Step: Chon ky han thoi gian");
		investigationOnline.clickToDynamicButtonLinkOrLinkText(driver, "Kỳ hạn");
		investigationOnline.clickToDynamicButtonLinkOrLinkText(driver, Register_Online_data.Valid_Account.LIST_PERIOD[1]);

		log.info("TC_03_Step: Nhap so tien gui");
		investigationOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutAmount1");

		log.info("TC_03_Step: Chon ky han thoi gian");
		investigationOnline.clickToDynamicImageViewByID(driver, "com.VCB:id/ivContent3");
		investigationOnline.clickToDynamicButtonLinkOrLinkText(driver, "VND");

		log.info("TC_03_Step: Tien mat mac dinh chon, verify text 'Tien mat' hien thi");
		verifyEquals(investigationOnline.getTextDynamicFollowLayout(driver, "com.VCB:id/llTienMat"), "Tiền mặt");

		log.info("TC_03_Step: Nhap so tien nop");
		investigationOnline.scrollDownToButton(driver, "Xác nhận");
		investigationOnline.inputToDynamicEditviewByLinearlayoutId(driver, Register_Online_data.Valid_Account.AMOUNT, "com.VCB:id/layoutAmount2");

		log.info("TC_03_Step: Click button xac nhan");
		investigationOnline.clickToDynamicButton(driver, "Xác nhận");

		log.info("TC_03_Step: verify man hinh dang ky thanh cong");
		verifyEquals(investigationOnline.getDynamicTextInPopUp(driver, "ĐĂNG KÝ THÀNH CÔNG"), "ĐĂNG KÝ THÀNH CÔNG");

		log.info("TC_03_Step: verify text dieu kien");
		verifyEquals(investigationOnline.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvNote"), Register_Online_data.Message.MESSAGE_SUCCESS);

		log.info("TC_03_Step: verify image chia se");
		verifyTrue(investigationOnline.isDynamicImageTextDisplayed(driver, "Chia sẻ"));

		log.info("TC_03_Step: verify image luu anh");
		verifyTrue(investigationOnline.isDynamicImageTextDisplayed(driver, "Lưu ảnh"));

		log.info("TC_03_Step: Click button thuc hien giao dich khác");
		investigationOnline.clickToDynamicButton(driver, "Thực hiện đăng ký khác");
	}

	@Test
	public void TC_04_GuiTietKiem_TaiKhoanTienGuiKhongKyHan() {

		
	}

	@Test
	public void TC_05_GuiTietKiem_TaiKhoanTienGuiCoKyHanChuyenKhoan() {

		
	}

	@Test
	public void TC_06_GuiTietKiem_TheTietKiemCoKyHanChuyenKhoanVaTienMatNhanGoc() {

		

	}

	@Test
	public void TC_07_MoTheTietKiem_TheTietKiemKhongKyHanTienMatNhanGoc() {

	
	}

	@Test
	public void TC_08_MoTheTietKiem_TaiKhoanTienGuiCoKyHanTienMatNhanGoc() {

		
	}

	@Test
	public void TC_09_RutSoTietKiem_RutLaiPhuongThucTienMat() {

	}

	@Test
	public void TC_10_RutSoTietKiem_RutGocPhuongThucChuyenKhoanVaTienMat() {

	}

	@Test
	public void TC_11_MoTaiKhoanThanhToan_ManHinhThongTinKhachHang() {

		
	}

	@Test
	public void TC_12_MoTaiKhoanThanhToan_ManHinhDangKyDichVuDienTu() {
		
	}

	@Test
	public void TC_13_MoTaiKhoanThanhToan_ManHinhConnect24_ChonMotThePhu() {
		
	}


		


	@Test
	public void TC_01_GiaoDichNopTienThanhCong() {

		investigationOnline = PageFactoryManager.getInvestigationOnline(driver);
		investigationOnline.isDynamicMessageAndLabelTextDisplayed(driver, "Loại yêu cầu tra soát");
		
		log.info("TC_01_Step: Click giao dich tien mat/chuyen tien");
		investigationOnline.clickToDynamicButtonLinkOrLinkText(driver, "Lập yêu cầu tra soát");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
