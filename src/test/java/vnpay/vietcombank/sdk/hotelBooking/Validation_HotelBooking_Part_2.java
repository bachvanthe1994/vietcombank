package vnpay.vietcombank.sdk.hotelBooking;

import java.io.IOException;
import java.util.ArrayList;
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

public class Validation_HotelBooking_Part_2 extends Base {
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
	public void TC_01_DatPhongKhachSan_KiemTraBamVaoLocTheoGiaVaHangSao() {
		homePage = PageFactoryManager.getHomePageObject(driver);
		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);

		log.info("TC_01_01_Click Dat phong khach san");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Đặt phòng khách sạn");
		
		log.info("TC_01_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton(driver, "Đồng ý");
		
		log.info("TC_01_03_Click vao Loc theo gia va hang sao");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Lọc theo giá & hạng sao");
		
		log.info("TC_01_04_Kiem tra man hinh Loc theo gia va hang sao");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Xếp hạng sao"));
		verifyTrue(hotelBooking.checkStarRate());
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Giá phòng"));
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Xác nhận"));
		
	}
	
	@Test
	public void TC_02_DatPhongKhachSan_KiemTraXepHangSao() {
		log.info("TC_02_01_Click chon hang sao");
		hotelBooking.chooseStarRateHotel(3);

		log.info("TC_02_02_Kiem tra hang sao duoc chon");
		verifyTrue(hotelBooking.checkSelectedStarRate(3));
		
		log.info("TC_02_03_Click chon lai hang sao");
		hotelBooking.chooseStarRateHotel(3);
		
		log.info("TC_02_04_Kiem tra bo chon hang sao");
		verifyFailure(hotelBooking.checkSelectedStarRate(3));
		
	}
	
	String actualMinPrice;
	String actualMaxPrice;
	@Test
	public void TC_03_DatPhongKhachSan_KiemTraKeoChonMucGia() {
		log.info("TC_03_01_Click chon hang sao");
		hotelBooking.handleSeekBarPrice(6000000, 9999000, 12000000);
		
		log.info("TC_03_02_Kiem tra muc gia min duoc chon");
		actualMinPrice = hotelBooking.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFilterPriceMin");
		verifyTrue(Integer.parseInt(actualMinPrice.replaceAll("\\D+","")) > 0);
		
		log.info("TC_03_03_Kiem tra muc gia max duoc chon");
		actualMaxPrice = hotelBooking.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFilterPriceMax");
		verifyTrue(Integer.parseInt(actualMaxPrice.replaceAll("\\D+","")) > 9999000);
		
	}
	
	@Test
	public void TC_04_DatPhongKhachSan_KiemTraNhanXacNhan() {
		log.info("TC_04_01_Click chon hang sao");
		hotelBooking.chooseStarRateHotel(3);
		
		log.info("TC_04_02_Click Xac nhan");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Xác nhận");
		
		log.info("TC_04_03_Kiem tra thong tin da chon");
		String actualStarAndPriceInfomation =  hotelBooking.getTextRateAndPriceFilter();
		
		log.info("TC_04_03_01_Kiem tra gia Min da chon");
		verifyTrue(actualStarAndPriceInfomation.contains(actualMinPrice.split(" ")[0]));
		
		log.info("TC_04_03_02_Kiem tra gia Max da chon");
		verifyTrue(actualStarAndPriceInfomation.contains(actualMaxPrice.split(" ")[0]));
		
		log.info("TC_04_03_03_Kiem tra so Sao da chon");
		verifyTrue(actualStarAndPriceInfomation.contains("3"));
		
	}
	
	@Test
	public void TC_05_DatPhongKhachSan_KiemTraNutXemGanDay_ChuaXemGanDay() {
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Xem gần đây");
		
		verifyEquals(hotelBooking.getTextInDynamicPopup(driver, "com.VCB:id/tvContent"), "Gần đây bạn chưa xem phòng nào");
		
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Đồng ý");
		
	}
	
	List<HotelBookingInfo> listHotelRecentView = new ArrayList<HotelBookingInfo>();
	@Test
	public void TC_06_DatPhongKhachSan_KiemTraNutXemGanDay_DaXemGanDay() {
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Tìm kiếm địa điểm hoặc khách sạn");
		
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Hà Nội");
		
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Tìm kiếm");
		
		List<HotelBookingInfo> listHotelSearch = hotelBooking.getListHotelSearched();
		
		hotelBooking.viewHotelDetail();
		
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivBack");
		
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Xem gần đây");
		
		listHotelRecentView  = hotelBooking.getListHotelRecentView();
		
		verifyEquals(listHotelRecentView, listHotelSearch); 
		
	}
	
	@Test
	public void TC_07_DatPhongKhachSan_KiemTraNutXemGanDay_XemChiTietKhachSan() {
		hotelBooking.navigateBack(driver);
		
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivBack");
		
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Đặt phòng khách sạn");
		
		hotelBooking.clickToDynamicButton(driver, "Đồng ý");
		
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Xem gần đây");
		
		String hotelName = listHotelRecentView.get(0).hotelName;
		
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, hotelName);
		
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed(driver, hotelName));
		
	}
	
	@Test
	public void TC_08_DatPhongKhachSan_TimKiemKhachSan_TruongHopKhongTimThayKhachSanNao() throws InterruptedException {
		log.info("TC_08_01_Quay lai man hinh home");
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivBack");
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivBack");
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivBack");
		
		log.info("TC_08_02_Click Dat phong khach san");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Đặt phòng khách sạn");
		
		log.info("TC_08_03_Click nut Dong y");
		hotelBooking.clickToDynamicButton(driver, "Đồng ý");
		
		log.info("TC_08_04_Click vao Loc theo gia va hang sao");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Lọc theo giá & hạng sao");
		
		log.info("TC_08_05_Click chon hang sao");
		hotelBooking.handleSeekBarPrice(0, 9999000, 0);
		
		log.info("TC_08_06_Click Xac nhan");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Xác nhận");
		
		log.info("TC_08_07_Click Tim kiem dia diem");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Tìm kiếm địa điểm hoặc khách sạn");
		
		log.info("TC_08_08_Click chon Dia diem");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Hà Nội");
		
		log.info("TC_08_09_Click Tim kiem");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Tìm kiếm");
		
		log.info("TC_08_10_Kiem tra thong bao");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Không có khách sạn nào được tìm thấy"));
		
	}
	
	@Test
	public void TC_09_DatPhongKhachSan_TimKiemKhachSan_TruongHopCoKetQuaTimKiem() {
		log.info("TC_09_01_Quay lai man hinh home");
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivBack");
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivBack");
		
		log.info("TC_09_02_Click Dat phong khach san");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Đặt phòng khách sạn");
		
		log.info("TC_09_03_Click nut Dong y");
		hotelBooking.clickToDynamicButton(driver, "Đồng ý");
		
		log.info("TC_09_04_Click Tim kiem dia diem");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Tìm kiếm địa điểm hoặc khách sạn");
		
		log.info("TC_09_05_Click chon Dia diem");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Hà Nội");
		
		log.info("TC_09_06_Click Tim kiem");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Tìm kiếm");
		
		log.info("TC_09_07_Kiem tra hien thi nut Loc");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed(driver, "Lọc"));
		
		log.info("TC_09_08_Kiem tra hien thi nut Ban do");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed(driver, "Bản đồ"));
		
		log.info("TC_09_09_Kiem tra hien thi danh sach tim kiem");
		verifyTrue(hotelBooking.getListHotelSearched().size() > 0);
		
	}
	
	@Test
	public void TC_10_DatPhongKhachSan_TimKiemKhachSan_KiemTraBamNutBanDo() {
		log.info("TC_10_01_Bam nut Ban do");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Bản đồ");
		
		log.info("TC_10_02_Kiem tra khach san danh dau tren Ban do");
		int numberHotelInMap = hotelBooking.getNumberOfHotelInMap("Bản đồ trên Google");
		verifyEquals(17, numberHotelInMap);
		
	}
	
	@Test
	public void TC_11_DatPhongKhachSan_TimKiemKhachSan_NhanChon1KhachSan() {
		log.info("TC_11_01_Bam nut Danh sach");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Danh sách");
		
		log.info("TC_11_02_Chon 1 khach san");
		List<HotelBookingInfo> listHotelSearch = hotelBooking.getListHotelSearched();
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, listHotelSearch.get(0).hotelName);
		
		log.info("TC_11_03_Kiem tra thong tin chi tiet khach san");
		log.info("TC_11_03_01_Kiem tra ten khach san");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed(driver, listHotelSearch.get(0).hotelName));
		
		log.info("TC_11_03_02_Kiem tra dia chi khach san");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, listHotelSearch.get(0).hotelAddress));
		
		log.info("TC_11_03_03_Kiem tra so tien");
//		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, listHotelSearch.get(0).price)); ---> App đang sai
		
	}
	
	@Test
	public void TC_12_DatPhongKhachSan_TimKiemKhachSan_KiemTraChucNangLoc() {
		log.info("TC_12_01_Quay lai man hinh hien thi khach san duoc tim kiem");
		hotelBooking.clickToDynamicBottomMenuOrCloseIcon(driver, "com.VCB:id/ivBack");
		
		log.info("TC_12_02_Click nut Loc");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Lọc");
		
		log.info("TC_12_03_Kiem tra man hinh Loc");
		log.info("TC_12_03_1_Kiem tra chuc nang Sap xep");
		hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Sắp xếp");
		
		log.info("TC_12_03_2_Kiem tra chuc nang Xep hang sao");
		hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Xếp hạng sao");
		
		log.info("TC_12_03_3_Kiem tra chuc nang Gia phong");
		hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Giá phòng");
		
		log.info("TC_12_03_4_Kiem tra chuc nang Tien nghi");
		hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Tiện nghi");
		
		log.info("TC_12_03_5_Kiem tra chuc nang chon Quan huyện");
		hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Quận huyện");
		
		log.info("TC_12_03_6_Kiem tra chuc nang Khach san gan");
		hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Khách sạn gần");
		
		log.info("TC_12_03_7_Kiem tra nut DAT LAI");
		hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Đặt lại");
		
		log.info("TC_12_03_8_Kiem tra nut XAC NHAN");
		hotelBooking.isDynamicMessageAndLabelTextDisplayed(driver, "Xác nhận");
		
	}
	
	@Test
	public void TC_13_DatPhongKhachSan_TimKiemKhachSan_KiemTraChucNangLoc_KiemTraComboSapXep() {
		log.info("TC_13_01_Quay lai man hinh hien thi khach san duoc tim kiem");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Xác nhận");
		
		log.info("TC_13_02_Click nut Loc");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Lọc");
		
		log.info("TC_13_03_Click mo combo Sap xep");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, "Giá từ thấp đến cao");
		
		log.info("TC_13_04_Lay list tieu chi Sap xep");
		List<String> actualList = hotelBooking.getListOfSuggestedMoneyOrListText(driver, "android:id/title");
		
		log.info("TC_13_05_Kiem tra danh sach tieu chi Sap xep");
		verifyEquals(actualList, HotelBooking_Data.CRITERIA_ORDER_LIST);
		
		log.info("TC_13_06_Chon 1 tieu chi sap xep");
		hotelBooking.clickToDynamicButtonLinkOrLinkText(driver, HotelBooking_Data.CRITERIA_ORDER_LIST.get(1));
		
		log.info("TC_13_07_Kiem tra tieu chi duoc chon");
		verifyTrue(hotelBooking.isDynamicTextViewDisplayed(driver, HotelBooking_Data.CRITERIA_ORDER_LIST.get(1)));
		
	}
	
	@Test
	public void TC_14_DatPhongKhachSan_TimKiemKhachSan_KiemTraChucNangLoc_KiemTraXepHangSao() {
		log.info("TC_14_01_Click chon hang sao");
		hotelBooking.chooseStarRateHotel(1);

		log.info("TC_14_02_Kiem tra hang sao duoc chon");
		verifyTrue(hotelBooking.checkSelectedStarRate(1));
		
		log.info("TC_14_03_Click chon hang sao");
		hotelBooking.chooseStarRateHotel(2);
		
		log.info("TC_14_04_Kiem tra bo chon hang sao");
		verifyTrue(hotelBooking.checkSelectedStarRate(2));
		
		log.info("TC_14_05_Click chon hang sao");
		hotelBooking.chooseStarRateHotel(3);
		
		log.info("TC_14_06_Kiem tra bo chon hang sao");
		verifyTrue(hotelBooking.checkSelectedStarRate(3));
		
		log.info("TC_14_07_Click chon hang sao");
		hotelBooking.chooseStarRateHotel(4);
		
		log.info("TC_14_08_Kiem tra bo chon hang sao");
		verifyTrue(hotelBooking.checkSelectedStarRate(4));
		
		log.info("TC_14_09_Click chon hang sao");
		hotelBooking.chooseStarRateHotel(5);
		
		log.info("TC_14_10_Kiem tra bo chon hang sao");
		verifyTrue(hotelBooking.checkSelectedStarRate(5));
		
	}
	
	@Test
	public void TC_15_DatPhongKhachSan_TimKiemKhachSan_KiemTraChucNangLoc_KiemTraChonGiaPhong() {
		log.info("TC_15_01_Click chon hang sao");
		hotelBooking.handleSeekBarPrice(6000000, 9999000, 12000000);
		
		log.info("TC_15_02_Kiem tra muc gia min duoc chon");
		actualMinPrice = hotelBooking.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFilterPriceMin");
		verifyTrue(Integer.parseInt(actualMinPrice.replaceAll("\\D+","")) > 0);
		
		log.info("TC_15_03_Kiem tra muc gia max duoc chon");
		actualMaxPrice = hotelBooking.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFilterPriceMax");
		verifyTrue(Integer.parseInt(actualMaxPrice.replaceAll("\\D+","")) > 9999000);
		
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
		service.stop();
	}

}
