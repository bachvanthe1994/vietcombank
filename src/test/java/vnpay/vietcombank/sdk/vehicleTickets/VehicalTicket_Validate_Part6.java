package vnpay.vietcombank.sdk.vehicleTickets;

import java.io.IOException;
import java.time.LocalDate;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.LogInPageObject;
import vehicalPageObject.VehicalPageObject;
import vnpay.vietcombank.sdk.vehicleTicket.data.VehicalData;

public class VehicalTicket_Validate_Part6 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private VehicalPageObject vehicalTicket;
	LocalDate now = LocalDate.now();
	String today = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String tommorrowDate = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now) + 1) + " " + getForwardDate(1);
	String tomorrow_week = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now) + 2) + " " + getForwardDate(2);

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
		vehicalTicket = PageFactoryManager.getVehicalPageObject(driver);
		login.Global_login(phone, pass, opt);
		login.scrollDownToText(driver, "Đặt vé xe");
		login.scrollIDownOneTime(driver);

		vehicalTicket.clickToDynamicText("Đặt vé xe");

		vehicalTicket.clickToDynamicButton("Đồng ý");
	}

	@Test
	public void TC_79_KiemTraNhanChonNgayQuaKhu() {
		log.info("TC_79_Step_1: Chọn và nhập điểm đi");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_79_Step_2: Chọn và nhập điểm đến");
		vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_2, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_2);

		log.info("TC_89_Step_4: Chọn ngày mai");
		vehicalTicket.clickToDynamicTomorrowAndFilterTrip("com.VCB:id/lnNextday");

		log.info("TC_79_Step_3: click button tìm kiếm chuyến đi");
		vehicalTicket.clickToDynamicButton("Tìm kiếm chuyến đi");

		log.info("TC_79_Step_4: chọn sửa");
		vehicalTicket.clickToDynamicButtonForID("com.VCB:id/btnEdit");

		log.info("TC_79_Step_5: Chọn ngày");
		vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

		log.info("TC_79_Step_6: Chon ngay qua khu");
		vehicalTicket.clickToDynamicSelectedDate(getCurentMonthAndYearMinusDays(1), getBackWardDay(1));

		log.info("TC_79_Step_7: Click Quay lai");
		vehicalTicket.clickToDynamicButtonIconBack("com.VCB:id/ivTitleLeft");

		log.info("TC_79_Step_8: kiểm tra ngay qua khu khong duoc chon");
		String today = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
		verifyEquals(vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth"), today);
	}

	@Test
	public void TC_80_KiemTraChonNgayHienTai() {
		log.info("TC_80_Step_1: Chọn ngày");
		vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

		log.info("TC_80_Step_2: Chọn ngày hiện tai");
		vehicalTicket.clickToDynamicTextScollUP(getCurrentDay().replace("0", ""));

		log.info("TC_80_Step_3: kiểm tra ngày thay đổi đi");
		String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
		verifyEquals(DayStart, today);

	}

	@Test
	public void TC_81_KiemTraChonNgayMai() {
		log.info("TC_81_Step_1: Chọn ngày");
		vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

		log.info("TC_81_Step_2: Chọn ngày hiện tai");
		vehicalTicket.clickToDynamicSelectedDate(getCurentMonthAndYearPlusDays1(1), getForWardDay(1));

		log.info("TC_81_Step_3: kiểm tra ngày thay đổi đi");
		String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
		verifyEquals(DayStart, tommorrowDate);

	}

	@Test
	public void TC_82_KiemTraChonNgayLonHonNgayMai() {
		log.info("TC_82_Step_1: Chọn ngày");
		vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

		log.info("TC_82_Step_2: chọn ngày lớn hon ngày mai");
		vehicalTicket.clickToDynamicSelectedDate(getCurentMonthAndYearPlusDays1(2), getForWardDay(2));

		log.info("TC_82_Step_3: kiểm tra ngày thay đổi đi");
		String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
		verifyEquals(DayStart, tomorrow_week);

		log.info("TC_82_Step_4: kiểm tra không focus vào ngày button nào");
		verifyFailure(vehicalTicket.isDynamicForcusAndPriceDisplay("com.VCB:id/lnToday"));
		verifyFailure(vehicalTicket.isDynamicForcusAndPriceDisplay("com.VCB:id/lnNextday"));
	}

	@Test
	public void TC_83_KiemTraThayDoiThoiGianDi() {
		log.info("TC_83_Step_1: Chọn ngày");
		vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

		log.info("TC_83_Step_2: Chọn ngày mai");
		vehicalTicket.clickToDynamicSelectedDate(getCurentMonthAndYearPlusDays1(1), getForWardDay(1));

		log.info("TC_83_Step_3: Tim kiếm chuyến đi");
		vehicalTicket.clickToDynamicButton("ÁP DỤNG");

		log.info("TC_83_Step_4: chọn sửa");
		vehicalTicket.clickToDynamicButtonForID("com.VCB:id/btnEdit");

		log.info("TC_83_Step_5: chon lại ngày đi");
		vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

		log.info("TC_83_Step_6: chọn ngày lớn hon ngày mai");
		vehicalTicket.clickToDynamicSelectedDate(getCurentMonthAndYearPlusDays1(2), getForWardDay(2));

		log.info("TC_83_Step_7: kiểm tra ngày thay đổi đi");
		String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
		verifyEquals(DayStart, tomorrow_week);
	}

	@Test
	public void TC_84_KiemTraButtonApDung() {
		log.info("TC_84_Step_1: lấy thời gian đi");
		String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
		String[] date = DayStart.split("\\ ");

		log.info("TC_84_Step_2: lấy điểm đi và điểm đến");
		String fromt = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextPickUp");
		String to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextArrival");

		log.info("TC_84_Step_3: Tim kiếm chuyến đi");
		vehicalTicket.clickToDynamicButton("ÁP DỤNG");

		log.info("TC_84_Step_4: kiểm tra điểm đi điểm đến");
		String fromtEdit = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextPickUp");
		String toEdit = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextArrival");
		verifyEquals(fromtEdit, fromt);
		verifyEquals(toEdit, to);

		log.info("TC_84_Step_5: kiểm tra thời gian đi");
		String getDateEdit = vehicalTicket.getDynamicDayStart("com.VCB:id/tvStartTime");
		String[] dateEdit = getDateEdit.split(",");
		verifyEquals(date[2], dateEdit[1]);
	}

	@Test
	public void TC_85_KiemTraClickBack() {
		log.info("TC_85_Step_1: chọn sửa");
		vehicalTicket.clickToDynamicButtonForID("com.VCB:id/btnEdit");

		log.info("TC_85_Step_2: click chọn icon");
		vehicalTicket.clickToDynamicIconChangePlaceAndBack("com.VCB:id/ivTitleLeft");

		log.info("TC_85_Step_3: kiểm tra hiển thị màn hình sau khi click back");
		String title = vehicalTicket.getDynamicDayStart("com.VCB:id/tv_title");
		verifyEquals(title, VehicalData.NOTIFICATION.LIST_TRIP);
	}

	@Test
	public void TC_86_KiemTraThayDoiDiemDi() {
		log.info("TC_86_Step_1: chọn sửa");
		vehicalTicket.clickToDynamicButtonForID("com.VCB:id/btnEdit");

		log.info("TC_86_Step_2: sửa điểm đi");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_3, "com.VCB:id/linPickUp");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_3);

		log.info("TC_63_Step_2: Chọn và nhập điểm đến");
		vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_2, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_2);

		log.info("TC_86_Step_3: Tim kiếm chuyến đi");
		vehicalTicket.clickToDynamicButton("ÁP DỤNG");

		log.info("TC_06_Step_4: chọn đồng ý");
		vehicalTicket.clickToDynamicButton(driver, "ĐỒNG Ý");

		log.info("TC_86_Step_5: kiểm tra hiển thị điểm đi mới");
		String fromtEdit = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextPickUp");
		verifyEquals(fromtEdit, VehicalData.DATA_ORDER_TICKET.PLACE_3);
	}

	@Test
	public void TC_87_KiemTraThayDoiDiemDen() {
		log.info("TC_87_Step_1: nhập điểm đến");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_2);
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.EDIT_DESTINATION, "com.VCB:id/linArival");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.EDIT_DESTINATION);

		log.info("TC_87_Step_2: Tim kiếm chuyến đi");
		vehicalTicket.clickToDynamicButton("ÁP DỤNG");

		log.info("TC_87_Step_3: chọn đồng ý");
		vehicalTicket.clickToDynamicButton(driver, "ĐỒNG Ý");

		log.info("TC_87_Step_4: kiểm tra hiển thị điểm đến mới");
		String totEdit = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextArrival");
		verifyEquals(totEdit, VehicalData.DATA_ORDER_TICKET.EDIT_DESTINATION);
	}

	@Test
	public void TC_88_KiemTraThayDoiDiemDiDiemDen() {
		log.info("TC_88_Step_1: nhập điểm đi");
		vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvTextPickUp");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_88_Step_2: Chọn và nhập điểm đến");
		vehicalTicket.clickToDynamicTextBox("com.VCB:id/linArival");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_2, "com.VCB:id/linArival");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_2);

		log.info("TC_88_Step_3: Tim kiếm chuyến đi");
		vehicalTicket.clickToDynamicButton("ÁP DỤNG");

		log.info("TC_88_Step_5: kiểm tra hiển thị điểm đến mới");
		String totEdit = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextArrival");
		verifyEquals(totEdit, VehicalData.DATA_ORDER_TICKET.PLACE_2);

		log.info("TC_88_Step_5: kiểm tra hiển thị điểm đi mới");
		String fromttEdit = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextPickUp");
		verifyEquals(fromttEdit, VehicalData.DATA_ORDER_TICKET.PLACE_1);
	}

	@Test
	public void TC_89_KiemTraThayDoiDiemDiDiemDenVaNgayDi() {
		log.info("TC_89_Step_1: chọn sửa");
		vehicalTicket.clickToDynamicButtonForID("com.VCB:id/btnEdit");

		log.info("TC_89_Step_2: nhập điểm đi");
		vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvTextPickUp");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.EDIT_DESTINATION, "com.VCB:id/linPickUp");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.EDIT_DESTINATION);

		log.info("TC_89_Step_3: Chọn và nhập điểm đến");
		vehicalTicket.clickToDynamicTextBox("com.VCB:id/linArival");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linArival");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_89_Step_4: Chọn ngày mai");
		vehicalTicket.clickToDynamicTomorrowAndFilterTrip("com.VCB:id/lnNextday");

		String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
		String[] date = DayStart.split("\\ ");

		log.info("TC_89_Step_5: Tim kiếm chuyến đi");
		vehicalTicket.clickToDynamicButton("ÁP DỤNG");

		log.info("TC_89_Step_6: kiểm tra điểm đi điểm đến");
		String fromtEdit = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextPickUp");
		String toEdit = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextArrival");
		verifyEquals(fromtEdit, VehicalData.DATA_ORDER_TICKET.EDIT_DESTINATION);
		verifyEquals(toEdit, VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_89_Step_7: kiểm tra thời gian đi");
		String getDateEdit = vehicalTicket.getDynamicDayStart("com.VCB:id/tvStartTime");
		String[] dateEdit = getDateEdit.split(",");
		verifyEquals(date[2], dateEdit[1]);

	}

	@Test
	public void TC_90_KiemTraManHinhChonGhe() {
		log.info("TC_90_Step_1: click chọn ghê");
		vehicalTicket.clickToDynamicText("Chọn ghế");

		log.info("TC_90_Step_2: kiểm tra hiển thị title màn hình");
		String title = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTitle1");
		verifyEquals(title, VehicalData.DATA_ORDER_TICKET.TITLE_CHOISE_CHAIR);

		log.info("TC_90_Step_3: kiểm tra hiển thị button quay lại");
		verifyTrue(vehicalTicket.isDynamicIconChangePlaceAndBackAndFinndDisplayed("com.VCB:id/ivTitleLeft"));

		log.info("TC_90_Step_4: kiểm tra thông tin hành trình");
		verifyTrue(vehicalTicket.isDynamicDisplayedManufacturerAndRateAndTimeRunAndRouter("com.VCB:id/tvTextPickUp"));
		verifyTrue(vehicalTicket.isDynamicDisplayedManufacturerAndRateAndTimeRunAndRouter("com.VCB:id/tvTextArrival"));

		log.info("TC_90_Step_5: kiểm tra hiển thị thông tin ngày đi");
		verifyTrue(vehicalTicket.isDynamicDisplayedManufacturerAndRateAndTimeRunAndRouter("com.VCB:id/tvStartTime"));

		log.info("TC_90_Step_6: kiểm tra hiển thị button sửa");
		verifyTrue(vehicalTicket.isDynamicEditTripDisplayed("com.VCB:id/btnEdit"));

		log.info("TC_90_Step_7: kiểm tra hiển thị hãng xe");
		verifyTrue(vehicalTicket.isDynamicDisplayedManufacturerAndRateAndTimeRunAndRouter("com.VCB:id/tv_hang_xe"));

		log.info("TC_90_Step_8: kiểm tra hiển thị text ghế trống");
		String chairEmpty = vehicalTicket.getDynamicSuggestTrip("0");
		verifyEquals(chairEmpty, VehicalData.DATA_ORDER_TICKET.CHAIR_EMPTY);

		log.info("TC_90_Step_9: kiểm tra hiển thị text ghế đã bán");
		String chairSold = vehicalTicket.getDynamicSuggestTrip("1");
		verifyEquals(chairSold, VehicalData.DATA_ORDER_TICKET.CHAIR_SOLD);

		log.info("TC_90_Step_10: kiểm tra hiển thị text ghế đang chọn");
		String chairSelected = vehicalTicket.getDynamicSuggestTrip("2");
		verifyEquals(chairSelected, VehicalData.DATA_ORDER_TICKET.CHAIR_SELECTED);

		log.info("TC_90_Step_11: kiểm tra hiển thị giá tiền");
		verifyTrue(vehicalTicket.isDynamicDisplayedManufacturerAndRateAndTimeRunAndRouter("com.VCB:id/tv_hang_xe"));

		log.info("TC_90_Step_12: kiểm tra hiển thị sơ đồ ghế ngồi");
		verifyTrue(vehicalTicket.isDynamicChairMap("com.VCB:id/lnSeat"));

		log.info("TC_90_Step_13: kiểm tra hiển thị thông báo số ghế");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();

	}

}
