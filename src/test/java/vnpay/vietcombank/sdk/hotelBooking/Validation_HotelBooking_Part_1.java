package vnpay.vietcombank.sdk.hotelBooking;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.sdk.hotelBooking.HotelBookingPageObject;
import vnpay.vietcombank.sdk.hotelBooking.data.HotelBooking_Data;

public class Validation_HotelBooking_Part_1 extends Base {
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
	public void TC_01_DatPhongKhachSan_KiemTraDanhSachChucNangDatPhongKhachSan() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);

		log.info("TC_01_01_Click Dat phong khach san");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Đặt phòng khách sạn");

		log.info("TC_01_02_Click nut Dong ý");
		hotelBooking.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_01_03_Kiem tra danh sach chuc nang dat phong khach san");
		log.info("TC_01_03_01_Kiem tra icon Backs");
//		verifyTrue(hotelBooking.isDynamicImageButtonDisplayed(driver, "com.VCB:id/ivBack"));

		log.info("TC_01_03_02_Kiem tra text Dat phong khach san");
//		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "ĐẶT PHÒNG\r\n" + "KHÁCH SẠN"));

		log.info("TC_01_03_02_Kiem tra Lich su va huy phong");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Lịch sử & hủy phòng"));
	}

	@Test
	public void TC_02_DatPhongKhachSan_KiemTraVaoChucNangDatPhongKhachSan() {
		LocalDate now = LocalDate.now();
		LocalDate date = now.plusDays(2);
		log.info("TC_02_01_Kiem tra Dia diem");

		log.info("TC_02_02_Kiem tra Ngay dat");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Ngày đặt"));
		String actualCheckInDay = hotelBooking.getDayCheckIn();
		String expectCheckInDay = getCurrentDay() + convertMonthVietNamese(getCurrenMonth()) + convertDayOfWeekVietNamese(getCurrentDayOfWeek(now));
		verifyEquals(actualCheckInDay, expectCheckInDay);

		log.info("TC_02_03_Kiem tra Ngay tra");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Ngày trả"));
		String actualCheckOutDay = hotelBooking.getDayCheckOut();
		String expectCheckOutDay = getForwardDate(2).split("/")[0] + convertMonthVietNamese(getForwardDate(2).split("/")[1]) + convertDayOfWeekVietNamese(getCurrentDayOfWeek(date));
		verifyEquals(actualCheckOutDay, expectCheckOutDay);

		log.info("TC_02_04_Kiem tra Khach va Phong");
		String actualPassengerAndRook = hotelBooking.getPassengerAndRoom();
		String expectPassengerAndRook = "2KHÁCH1PHÒNG";
		verifyEquals(actualPassengerAndRook, expectPassengerAndRook);

		log.info("TC_02_05_Kiem tra text Loc theo gia va hang sao");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Lọc theo giá & hạng sao"));

		log.info("TC_02_06_Kiem tra nut Tim kiem");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Tìm kiếm"));

		log.info("TC_02_07_Kiem tra Xem gan day");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Xem gần đây"));

	}

	@Test
	public void TC_03_DatPhongKhachSan_KiemTraToiManHinhLichSuVaHuyPhong() {
		log.info("TC_03_01_Click Lich su va huy phong");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Lịch sử & hủy phòng");

		log.info("TC_03_02_Kiem tra toi man Danh sach dat phong");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Danh sách đặt phòng"));

		log.info("TC_03_03_Click nut Back");
		hotelBooking.clickToDynamicBackIcon(driver, "Danh sách đặt phòng");

	}

	@Test
	public void TC_04_DatPhongKhachSan_KiemTraManHinhHienThiChucNangDatPhongKhachSan() {
		LocalDate now = LocalDate.now();
		LocalDate date = now.plusDays(2);
		log.info("TC_04_01_Kiem tra Dia diem");

		log.info("TC_04_02_Kiem tra Ngay dat");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Ngày đặt"));
		String actualCheckInDay = hotelBooking.getDayCheckIn();
		String expectCheckInDay = getCurrentDay() + convertMonthVietNamese(getCurrenMonth()) + convertDayOfWeekVietNamese(getCurrentDayOfWeek(now));
		verifyEquals(actualCheckInDay, expectCheckInDay);

		log.info("TC_04_03_Kiem tra Ngay tra");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Ngày trả"));
		String actualCheckOutDay = hotelBooking.getDayCheckOut();
		String expectCheckOutDay = getForwardDate(2).split("/")[0] + convertMonthVietNamese(getForwardDate(2).split("/")[1]) + convertDayOfWeekVietNamese(getCurrentDayOfWeek(date));
		verifyEquals(actualCheckOutDay, expectCheckOutDay);

		log.info("TC_04_04_Kiem tra Khach va Phong");
		String actualPassengerAndRook = hotelBooking.getPassengerAndRoom();
		String expectPassengerAndRook = "2KHÁCH1PHÒNG";
		verifyEquals(actualPassengerAndRook, expectPassengerAndRook);

		log.info("TC_04_05_Kiem tra text Loc theo gia va hang sao");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Lọc theo giá & hạng sao"));

		log.info("TC_04_06_Kiem tra nut Tim kiem");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Tìm kiếm"));

		log.info("TC_04_07_Kiem tra Xem gan day");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Xem gần đây"));

	}

	@Test
	public void TC_05_DatPhongKhachSan_KiemTraNhanVaoTextTimKiemDiaDiemHoacKhachSan() {
		log.info("TC_05_01_Nhan chon Tim kiem dia diem hoac khach san");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");

		log.info("TC_05_02_Kiem tra O Ten khach san hoac diem den");
		verifyTrue(hotelBooking.isDynamicTextInInputBoxDisPlayed(driver, "Tên khách sạn hoặc điểm đến"));

		log.info("TC_05_03_Kiem tra nut Huy");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayedByID("com.VCB:id/tvCancel"));

		log.info("TC_05_04_Kiem tra text Vi tri hien tai");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Vị trí hiện tại"));

		log.info("TC_05_05_Kiem tra danh sach diem den noi bat");
		List<String> listActualLocations = new ArrayList<String>();
		List<String> listExpectLocations = Arrays.asList(HotelBooking_Data.FAMOUS_LOCATION);
		listActualLocations = hotelBooking.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvTitle");
		verifyEquals(listActualLocations, listExpectLocations);

	}

	@Test
	public void TC_06_DatPhongKhachSan_KiemTraNhanNutHuy() {
		log.info("TC_06_01_Nhan nut Huy");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvCancel");

		log.info("TC_06_02_Kiem tra tro lai man hinh Dat Phong Khach San");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("Lịch sử & hủy phòng"));

		log.info("TC_06_03_Nhan chon Tim kiem dia diem hoac khach san");
		hotelBooking.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llPlace");

	}

	@Test
	public void TC_07_DatPhongKhachSan_KiemTraNhapKyTuVaoTextBoxSearch_HienThiDanhSachGoiY() {
		String actualSuggestLocation;
		log.info("TC_07_01_Nhap ky tu vao o Search");
		hotelBooking.inputIntoEditTextByID(driver, "Ga Hà Nội", "com.VCB:id/etSearch");

		log.info("TC_07_02_Kiem tra ket qua goi y");
		actualSuggestLocation = hotelBooking.getDynamicTextInTransactionDetail(driver, "Ga Hà Nội");
		verifyEquals(actualSuggestLocation, "ĐỊA ĐIỂM");

		log.info("TC_07_03_Nhap ky tu vao o Search");
		hotelBooking.inputIntoEditTextByID(driver, "Thái Hà", "com.VCB:id/etSearch");

		log.info("TC_07_04_Kiem tra ket qua goi y");
		actualSuggestLocation = hotelBooking.getDynamicTextInTransactionDetail(driver, "Khách sạn Thái Hà");
		verifyEquals(actualSuggestLocation, "KHÁCH SẠN");

	}

	@Test
	public void TC_08_DatPhongKhachSan_KiemTraNhapKyTuVaoTextBoxSearch_Nhap2KyTu() {
		log.info("TC_08_01_Nhap ky tu vao o Search");
		hotelBooking.inputIntoEditTextByID(driver, "ha", "com.VCB:id/etSearch");

		List<String> listSuggestLocations = hotelBooking.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvTitle");
		log.info("TC_08_02_Kiem tra hien thi ket qua goi y");
		verifyTrue(hotelBooking.checkSuggestLocation(listSuggestLocations, "ha"));

	}

	@Test
	public void TC_09_DatPhongKhachSan_KiemTraNhapKyTuVaoTextBoxSearch_ChoPhepNhap200KyTu_KyTuChu_KyTuDacBiet_KyTuTiengViet() {
		log.info("TC_09_01_Nhap ky tu vao o Search");
		hotelBooking.inputIntoEditTextByID(driver, HotelBooking_Data.GREATER_200_SPECIAL_VIETNAMESE_CHARACTERS, "com.VCB:id/etSearch");

		log.info("TC_09_02_Kiem tra ky tu nhap");
		String inputText = hotelBooking.getTextInEditTextFieldByID(driver, "com.VCB:id/etSearch");

		log.info("TC_09_03_Kiem tra hien thi 200 Ky tu");
		verifyEquals(inputText.length(), 200);

		log.info("TC_09_04_Kiem tra cho phep nhap ky tu dac biet, tieng Viet");
		verifyEquals(inputText, HotelBooking_Data.GREATER_200_SPECIAL_VIETNAMESE_CHARACTERS.substring(0, 200));

	}

	@Test
	public void TC_10_DatPhongKhachSan_KiemTraNhanVaoButtonViTriHienTai() {
		log.info("TC_10_01_Xoa ky tu o Search");
		hotelBooking.inputIntoEditTextByID(driver, "", "com.VCB:id/etSearch");

		log.info("TC_10_02_Click vao Vi tri hien tai");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Vị trí hiện tại");

		log.info("TC_10_03_Kiem tra ung dung lap Vi tri hien tai hien thi vao o Dia diem");
		verifyEquals(hotelBooking.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/llPlace"), HotelBooking_Data.CURRENT_LOCATION);

		log.info("TC_10_04_Nhan chon Tim kiem dia diem hoac khach san");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		
	}

	@Test
	public void TC_11_DatPhongKhachSan_KiemTraKhiChonDiaDiemXong() {
		log.info("TC_11_01_Nhap ky tu vao o Search");
		hotelBooking.inputIntoEditTextByID(driver, "Ga Hà Nội", "com.VCB:id/etSearch");

		List<String> listSuggestLocations = hotelBooking.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvTitle");

		log.info("TC_11_02_Click chon Dia diem");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, listSuggestLocations.get(0));

		log.info("TC_11_03_Kiem tra dia diem duoc chon thanh cong");
		verifyEquals(hotelBooking.getTextTextViewByLinearLayoutID(driver, "com.VCB:id/llPlace"), "Ga Hà Nội");

	}

	@Test
	public void TC_12_DatPhongKhachSan_KiemTraChonKhachSan() {
		log.info("TC_12_01_Click Dia diem");
		hotelBooking.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llPlace");

		log.info("TC_12_02_Nhap ky tu vao o Search");
		hotelBooking.inputIntoEditTextByID(driver, "CLASSY HOLIDAY HOTEL & SPA", "com.VCB:id/etSearch");

		List<String> listSuggestLocations = hotelBooking.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvTitle");

		log.info("TC_12_03_Click chon Dia diem");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, listSuggestLocations.get(0));

		log.info("TC_12_04_Kiem tra den trang thong tin khach san");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed("CLASSY HOLIDAY HOTEL & SPA"));
		
		log.info("TC_12_05_Quay lai man hinh hien thi khach san duoc tim kiem");
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivBack");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvCancel");
		
	}

	@Test
	public void TC_13_DatPhongKhachSan_KiemTraGiaTriMacDinhNgayDat() {
		LocalDate now = LocalDate.now();
		log.info("TC_13_01_Kiem tra gia tri mac dinh ngay dat");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Ngày đặt"));
		String actualCheckInDay = hotelBooking.getDayCheckIn();
		String expectCheckInDay = getCurrentDay() + convertMonthVietNamese(getCurrenMonth()) + convertDayOfWeekVietNamese(getCurrentDayOfWeek(now));
		verifyEquals(actualCheckInDay, expectCheckInDay);

	}

	@Test
	public void TC_14_DatPhongKhachSan_KiemTraGiaTriMacDinhNgayTra() {
		LocalDate now = LocalDate.now();
		LocalDate date = now.plusDays(2);
		log.info("TC_14_01_Kiem tra gia tri mac dinh ngay tra");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Ngày trả"));
		String actualCheckOutDay = hotelBooking.getDayCheckOut();
		String expectCheckOutDay = getForwardDate(2).split("/")[0] + convertMonthVietNamese(getForwardDate(2).split("/")[1]) + convertDayOfWeekVietNamese(getCurrentDayOfWeek(date));
		verifyEquals(actualCheckOutDay, expectCheckOutDay);

	}

	@Test
	public void TC_15_DatPhongKhachSan_KiemTraManHinhChonNgay() {
		log.info("TC_15_01_Click mo man hinh chon ngay");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Ngày đặt");

		log.info("TC_15_02_Nhap ky tu vao o Search");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Chọn ngày đặt phòng"));

	}

	@Test
	public void TC_16_DatPhongKhachSan_KiemTraHienThiLich() {
		log.info("TC_16_01_Click mo man hinh chon ngay");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Ngày đặt");

		log.info("TC_16_02_Kiem tra ngay duoc chon - Ngay dat");
		verifyTrue(hotelBooking.checkDateSelected(getCurrentDay()));

		log.info("TC_16_03_Kiem tra ngay duoc chon - Ngay tra");
		verifyTrue(hotelBooking.checkDateSelected(getForwardDate(2).split("/")[0]));

	}

	@Test
	public void TC_17_DatPhongKhachSan_KiemTraChonNgayQuaKhu() {
		log.info("TC_17_01_Click chon ngay qua khu");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, getBackWardDay(1).split("/")[0]);

		String toastMessage = hotelBooking.getToastMessage(driver);

		log.info("TC_17_02_Kiem tra message thong bao");
		verifyTrue(toastMessage.contains("Thời gian phải nằm trong khoảng"));

		log.info("TC_17_03_Kiem tra van o man hinh chon ngay dat phong");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Chọn ngày đặt phòng"));

	}

	@Test
	public void TC_18_DatPhongKhachSan_KiemTraChonNgayDatVaNgayTra() {
		String nowDay = getForwardDate(0);
		String expectCheckInDay = getForwardDate(1);
		String expectCheckOutDay = getForwardDate(3);

		log.info("TC_18_01_Click chon ngay tra va ngay dat");
		hotelBooking.clickToDateHotelBooking(nowDay, expectCheckInDay, expectCheckOutDay);
		
		log.info("TC_18_02_Kiem tra ngay duoc chon - Ngay tra, Ngay dat");
		verifyTrue(hotelBooking.checkDateSelected(expectCheckOutDay.split("/")[0].replace("0", "")));
		
		String actualCheckInDay = hotelBooking.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDay");
		String actualCheckOutDay = hotelBooking.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDay");

		log.info("TC_18_03_Kiem tra ngay dat duoc chon chinh xac");
		verifyEquals(actualCheckInDay, expectCheckInDay);

		log.info("TC_18_04_Kiem tra ngay tra duoc chon chinh xac");
		verifyEquals(expectCheckOutDay, actualCheckOutDay);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
