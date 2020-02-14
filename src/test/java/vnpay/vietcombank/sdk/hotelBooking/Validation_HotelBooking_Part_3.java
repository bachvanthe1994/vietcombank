package vnpay.vietcombank.sdk.hotelBooking;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import model.HotelBookingInfo;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.sdk.hotelBooking.HotelBookingPageObject;
import vnpay.vietcombank.sdk.hotelBooking.data.HotelBooking_Data;

public class Validation_HotelBooking_Part_3 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private HotelBookingPageObject hotelBooking;
	

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })

	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);

		login.Global_login(phone, pass, opt);
		
	}

	@Test
	public void TC_01_LichSuDatPhong_KiemTraManHinhHienThi() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);

		log.info("TC_01_01_Click Dat phong khach san");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Đặt phòng khách sạn");
		
		log.info("TC_01_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton(driver, "Đồng ý");
		
		log.info("TC_01_03_Click vao Lich su & huy phong");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Lịch sử & hủy phòng");
		
		log.info("TC_01_04_Kiem tra man hinh Lich su va huy phong");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Danh sách đặt phòng"));

	}
	
	@Test
	public void TC_02_LichSuDatPhong_KiemTraCachHienThiDuLieu() {
		log.info("TC_02_01_Lay du lieu tai man hinh lich su dat phong");
		List<HotelBookingInfo> expectedlist = hotelBooking.sortListHotelBookingHistory();
		
		log.info("TC_02_02_Kiem tra cach hien thi du lieu man hinh lich su dat phong");
		verifyEquals(HotelBookingPageObject.actualList, expectedlist);
		
	}
	
	@Test
	public void TC_03_LichSuDatPhong_LocKetqua_KiemTraManHinhHienThi() {
		log.info("TC_03_01_Bam vao icon Loc");
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivFilter");
		
		log.info("TC_03_02_Kiem tra man hinh loc ket qua");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Lọc kết quả"));
		
		log.info("TC_03_03_Kiem tra o Ma giao dich");
		verifyTrue(hotelBooking.isDynamicTextInInputBoxDisPlayed(driver, "Mã giao dịch"));
		
		log.info("TC_03_04_Kiem tra o Ma nhan phong");
		verifyTrue(hotelBooking.isDynamicTextInInputBoxDisPlayed(driver, "Mã nhận phòng"));
		
		log.info("TC_03_05_Kiem tra o Ten khach san");
		verifyTrue(hotelBooking.isDynamicTextInInputBoxDisPlayed(driver, "Tên khách sạn"));
		
		log.info("TC_03_06_Kiem tra o Ngay nhan");
		verifyTrue(hotelBooking.isDynamicTextInInputBoxDisPlayed(driver, "Ngày nhận"));
		
		log.info("TC_03_07_Kiem tra o Ngay tra");
		verifyTrue(hotelBooking.isDynamicTextInInputBoxDisPlayed(driver, "Ngày trả"));
		
		log.info("TC_03_08_Kiem tra combo Trang thai");
		verifyTrue(hotelBooking.isDynamicTextInInputBoxDisPlayed(driver, "Tất cả"));
		
		log.info("TC_03_09_Kiem tra nut Tim kiem");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Tìm kiếm"));
		
	}
	
	@Test
	public void TC_04_LichSuDatPhong_LocKetqua_KiemTraComboTrangThai() {
		log.info("TC_04_01_Click chon combo Trang thai");
		hotelBooking.clickToDynamicInput(driver, "Tất cả");
		
		log.info("TC_04_02_Kiem tra combo Trang thai");
		List<String> actualList = hotelBooking.getListOfStatusHotelBooking(driver, "android:id/title");
		verifyEquals(actualList, HotelBooking_Data.STATUS_HOTEL_BOOKING_LIST);
		
		log.info("TC_04_03_Click chon Trang thai");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, HotelBookingPageObject.actualList.get(0).status);
		
	}
	
	@Test
	public void TC_05_LichSuDatPhong_LocKetqua_NhanNutTimKiem_KhongTimThayKetQua() {
		log.info("TC_05_01_Nhap Ma giao dich");
		hotelBooking.inputToDynamicInputBox(driver, "abc123", "Mã giao dịch");
		
		log.info("TC_05_02_Bam nut Tim kiem");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Tìm kiếm");
		
		log.info("TC_05_03_Kiem tra hien thi thong bao Khong co du lieu hien thi");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Không có dữ liệu hiển thị"));
		
	}
	
	@Test
	public void TC_06_LichSuDatPhong_LocKetqua_NhanNutTimKiem_TimThayKetQua() throws ParseException {
		log.info("TC_06_01_Quay lai man hinh loc du lieu");
		hotelBooking.clickToDynamicBackIcon(driver, "Danh sách đặt phòng");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Lịch sử & hủy phòng");
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivFilter");
		
		log.info("TC_06_02_Nhap Ma giao dich");
		hotelBooking.inputToDynamicInputBox(driver, HotelBookingPageObject.actualList.get(0).payCode, "Mã giao dịch");
		
		log.info("TC_06_03_Bam nut Tim kiem");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Tìm kiếm");
		
		log.info("TC_06_04_Kiem tra du lieu loc");
		log.info("TC_06_04_01_Kiem tra Ma giao dich");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail(driver, "Mã giao dịch"), HotelBookingPageObject.actualList.get(0).payCode);
		
		log.info("TC_06_04_02_Kiem tra ten khach san");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, HotelBookingPageObject.actualList.get(0).hotelName));
		
		log.info("TC_06_04_03_Kiem tra dia chi khach san");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, HotelBookingPageObject.actualList.get(0).hotelAddress));
		
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
	    String createdDate = formatter1.format(formatter2.parse(HotelBookingPageObject.actualList.get(0).createDate));
	    String checkinDate = formatter1.format(formatter2.parse(HotelBookingPageObject.actualList.get(0).checkinDate));
		
		log.info("TC_06_04_04_Kiem tra Ngay dat phong");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail(driver, "Ngày đặt phòng"), createdDate);
		
		log.info("TC_06_04_05_Kiem tra Ngay nhan phong");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail(driver, "Ngày nhận phòng"), checkinDate);
		
		log.info("TC_06_04_06_Kiem tra Tong tien");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail(driver, "Tổng tiền"), HotelBookingPageObject.actualList.get(0).price);
		
		log.info("TC_06_04_07_Kiem tra Trạng thái");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, HotelBookingPageObject.actualList.get(0).status));
		
	}
	
	@Test
	public void TC_07_LichSuDatPhong_ChiTietDatPhong_KiemTraManHinhHienThi() throws ParseException {
		log.info("TC_07_01_Bam nut Chi tiet");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Chi tiết");
		
		log.info("TC_07_02_Kiem tra man hinh chi tiet dat phong");
		hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Chi tiết đặt phòng");
		
		log.info("TC_07_02_01_Kiem tra Ma giao dich");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail(driver, "Mã giao dịch"), HotelBookingPageObject.actualList.get(0).payCode);
		
		log.info("TC_07_02_02_Kiem tra ten khach san");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, HotelBookingPageObject.actualList.get(0).hotelName));
		
		log.info("TC_07_02_03_Kiem tra dia chi khach san");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, HotelBookingPageObject.actualList.get(0).hotelAddress));
		
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
	    String createdDate = formatter1.format(formatter2.parse(HotelBookingPageObject.actualList.get(0).createDate));
	    String checkinDate = formatter1.format(formatter2.parse(HotelBookingPageObject.actualList.get(0).checkinDate));
		
		log.info("TC_07_02_04_Kiem tra Ngay dat phong");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail(driver, "Ngày đặt phòng"), createdDate);
		
		log.info("TC_07_02_05_Kiem tra Ngay nhan phong");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail(driver, "Ngày nhận phòng"), checkinDate);
		
		log.info("TC_07_02_06_Kiem tra Tong tien");
		verifyEquals(hotelBooking.getDynamicTextInTransactionDetail(driver, "Tổng tiền"), HotelBookingPageObject.actualList.get(0).price);
		
		log.info("TC_07_02_07_Kiem tra hien thi ngay tra phong");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Ngày trả phòng"));
		
		log.info("TC_07_02_07_Kiem tra hien thi ma nhan phong");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Mã nhận phòng"));
		
	}
	
	@Test
	public void TC_08_LichSuDatPhong_ChiTietDatPhong_KiemTraNutChucNang_TrangThaiGiaoDich_HetHanThanhToan() {
		log.info("TC_08_01_Quay ve man hinh Danh sach dat phong");
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivBack");
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivBack");
		hotelBooking.clickToDynamicTextView("Lịch sử & hủy phòng");
		
		log.info("TC_08_02_Chon 1 khach san Het han thanh toan");
		String paycode = hotelBooking.getHotelBookingHistoryByStatus(HotelBookingPageObject.actualList, "Hết hạn thanh toán").payCode;
		hotelBooking.clickToDetailButtonByPayCode(paycode);
		
		log.info("TC_08_03_Kiem tra hien thi nut thuc hien lai");
//		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Thực hiện lại")); --> Hiện tại ko có nút thực hiện lại
		
	}
	
//	@Test
	public void TC_09_LichSuDatPhong_ChiTietDatPhong_KiemTraNutChucNang_TrangThaiGiaoDich_ChoThanhToan() {
		log.info("TC_09_01_Quay ve man hinh Danh sach dat phong");
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivBack");
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivBack");
		hotelBooking.clickToDynamicTextView("Lịch sử & hủy phòng");
		
		log.info("TC_09_02_Chon 1 khach san Cho thanh toan");
		String paycode = hotelBooking.getHotelBookingHistoryByStatus(HotelBookingPageObject.actualList, "Chờ thanh toán").payCode;
		hotelBooking.clickToDetailButtonByPayCode(paycode);
		
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Thanh toán"));
		
	}
	
//	@Test
	public void TC_10_LichSuDatPhong_ChiTietDatPhong_KiemTraNutChucNang_TrangThaiGiaoDich_DatPhongThanhCong() {
		log.info("TC_10_01_Quay ve man hinh Danh sach dat phong");
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivBack");
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivBack");
		hotelBooking.clickToDynamicTextView("Lịch sử & hủy phòng");
		
		log.info("TC_10_02_Chon 1 khach san Het han thanh toan");
		String paycode = hotelBooking.getHotelBookingHistoryByStatus(HotelBookingPageObject.actualList, "Đặt phòng thành công").payCode;
		hotelBooking.clickToDetailButtonByPayCode(paycode);
		
	}
	
//	@Test
	public void TC_11_LichSuDatPhong_ChiTietDatPhong_KiemTraNutChucNang_TrangThaiGiaoDich_HuyPhong() {
		log.info("TC_11_01_Quay ve man hinh Danh sach dat phong");
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivBack");
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivBack");
		hotelBooking.clickToDynamicTextView("Lịch sử & hủy phòng");
		
		log.info("TC_11_02_Chon 1 khach san Het han thanh toan");
		String paycode = hotelBooking.getHotelBookingHistoryByStatus(HotelBookingPageObject.actualList, "Hủy phòng").payCode;
		hotelBooking.clickToDetailButtonByPayCode(paycode);
		
		
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
