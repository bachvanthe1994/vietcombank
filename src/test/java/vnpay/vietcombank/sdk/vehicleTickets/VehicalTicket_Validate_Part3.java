package vnpay.vietcombank.sdk.vehicleTickets;

import java.io.IOException;
import java.time.LocalDate;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.LogInPageObject;
import vehicalPageObject.VehicalPageObject;
import vnpay.vietcombank.sdk.vehicleTicketData.VehicalData;

public class VehicalTicket_Validate_Part3 extends Base {
    AndroidDriver<AndroidElement> driver;
    private LogInPageObject login;
    private VehicalPageObject vehicalTicket;
    LocalDate now = LocalDate.now();
    String today = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
    String tommorrowDate = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now) + 1) + " " + getForwardDate(1);
    String tomorrow_week = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now) + 2) + " " + getForwardDate(2);
    String dateStart = "";
    String[] output;
    String dateOutput = "";
    String[] dateOutputSuggest;
    String dateOutput2 = "";

    @Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
    @BeforeClass
    public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt)
	    throws IOException, InterruptedException {
	startServer();
	log.info("Before class: Mo app ");
	driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
	login = PageFactoryManager.getLoginPageObject(driver);
	vehicalTicket = PageFactoryManager.getVehicalPageObject(driver);
	login.Global_login(phone, pass, opt);

	vehicalTicket.Vehical_login();
    }

    @Test
    public void TC_31_KiemTraChonNgayHienTai() {
	log.info("TC_31_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

	log.info("TC_31_Step_2: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
	vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_3, "com.VCB:id/linArival");
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_3);

	log.info("TC_31_Step_3: Chọn ngày");
	vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

	log.info("TC_31_Step_4: Chọn ngày hiện tai");
	vehicalTicket.clickToDynamicText(getCurrentDay());

	log.info("TC_31_Step_5: kiểm tra ngày thay đổi đi");
	String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
	verifyEquals(DayStart, today);

    }

    @Test
    public void TC_32_KiemTraChonNgayMai() {
	log.info("TC_32_Step_1: Chọn ngày");
	vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

	log.info("TC_32_Step_2: Chọn ngày hiện tai");
	vehicalTicket.clickToDynamicSelectedDate(getCurentMonthAndYearPlusDays1(1), getForWardDay(1));

	log.info("TC_32_Step_3: kiểm tra ngày thay đổi đi");
	String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
	verifyEquals(DayStart, tommorrowDate);

    }

    @Test
    public void TC_33_KiemTraChonNgayLonHonNgayMai() {
	log.info("TC_33_Step_1: Chọn ngày");
	vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

	log.info("TC_33_Step_2: chọn ngày lớn hon ngày mai");
	vehicalTicket.clickToDynamicSelectedDate(getCurentMonthAndYearPlusDays1(2), getForWardDay(2));

	log.info("TC_33_Step_3: kiểm tra ngày thay đổi đi");
	String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
	verifyEquals(DayStart, tomorrow_week);

	log.info("TC_33_Step_4: kiểm tra không focus vào ngày button nào");
	verifyFailure(vehicalTicket.isDynamicForcusAndPriceDisplay("com.VCB:id/lnToday"));
	verifyFailure(vehicalTicket.isDynamicForcusAndPriceDisplay("com.VCB:id/lnNextday"));
    }

    @Test
    public void TC_34_KiemTraThayDoiThoiGianDi() {
	log.info("TC_34_Step_1: Chọn ngày");
	vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

	log.info("TC_34_Step_2: Chọn ngày mai");
	vehicalTicket.clickToDynamicSelectedDate(getCurentMonthAndYearPlusDays1(1), getForWardDay(1));

	log.info("TC_34_Step_3: Tim kiếm chuyến đi");
	vehicalTicket.clickToDynamicButton("Tìm kiếm chuyến đi");

	log.info("TC_34_Step_4: click back");
	vehicalTicket.clickToDynamicButtonBack("Danh sách chuyến xe");

	log.info("TC_34_Step_5: chon lại ngày đi");
	vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

	log.info("TC_34_Step_6: chọn ngày lớn hon ngày mai");
	vehicalTicket.clickToDynamicSelectedDate(getCurentMonthAndYearPlusDays1(2), getForWardDay(2));

	log.info("TC_34_Step_7: kiểm tra ngày thay đổi đi");
	String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
	verifyEquals(DayStart, tomorrow_week);
    }

    @Test
    public void TC_35_KiemTraLichTaiLich() {
	log.info("TC_35_Step_1: Chọn ngày");
	vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

	log.info("TC_35_Step_3: lấy ra tháng/năm của 3 tháng sau");
	String MonthAndYear = getCurentMonthAndYearPlusMonth(3);
	verifyTrue(vehicalTicket.isDynamicDisplayed(MonthAndYear));

	log.info("TC_35_Step_4: lấy ra tháng/năm của 4 tháng sau");
	String MonthAndYear4 = getCurentMonthAndYearPlusMonth(4);
	vehicalTicket.isDynamicUnDisplayed(MonthAndYear4);

	log.info("TC_35_Step_5: click back");
	vehicalTicket.clickToDynamicIconChangePlaceAndBack("com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_36_KiemTraButtonTimKiemChuyenDi() {
	log.info("TC_36_Step_1: click button tìm kiếm chuyến đi");
	vehicalTicket.clickToDynamicButton("Tìm kiếm chuyến đi");

	log.info("TC_36_Step_2: kiểm tra hiển thị");
	String text = vehicalTicket.getDynamicDayStart("com.VCB:id/tv_title");
	verifyEquals(text, VehicalData.NOTIFICATION.LIST_TRIP);

	log.info("TC_36_Step_3: click back");
	vehicalTicket.clickToDynamicIconChangePlaceAndBack("com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_37_KiemTraNhomThongTinChuyenDiGoiY() {
	log.info("TC_37_Step_1: kiểm tra hiển thị title chuyến đi gợi ý");
	String sugget_trip = vehicalTicket.getDynamicSuggestTrip("com.VCB:id/lnSuggest");
	verifyEquals(sugget_trip, VehicalData.DATA_ORDER_TICKET.SUGGEST_TRIP);

	log.info("TC_37_Step_2: kiể tra hiển thị thời gian khởi hành và điểm đi");
	String suggest_from = vehicalTicket.getDynamicDayStart("com.VCB:id/tvFrom");
	String from = suggest_from.substring(12);
	verifyEquals(from, VehicalData.DATA_ORDER_TICKET.PLACE_1);

	log.info("TC_37_Step_3: kiểm tra điểm tới trong gợi ý");
	String to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTo");
	verifyEquals(to, VehicalData.DATA_ORDER_TICKET.PLACE_3);
    }

    @Test
    public void TC_38_KiemTraLuuThongTinChuyenDiGoiY() {
	log.info("TC_38_Step_1: lấy thời gian đi");
	dateStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
	output = dateStart.split("\\ ");
	dateOutput = output[2];

	log.info("TC_38_Step_2: click button tìm kiếm chuyến đi");
	vehicalTicket.clickToDynamicButton("Tìm kiếm chuyến đi");

	log.info("TC_38_Step_3: click back");
	vehicalTicket.clickToDynamicIconChangePlaceAndBack("com.VCB:id/ivTitleLeft");

	log.info("TC_38_Step_4: kiể tra hiển thị thời gian khởi hành và điểm đi");
	String suggest_from = vehicalTicket.getDynamicDayStart("com.VCB:id/tvFrom");
	String from = suggest_from.substring(12);
	verifyEquals(from, VehicalData.DATA_ORDER_TICKET.PLACE_1);

	log.info("TC_38_Step_5: kiểm tra điểm tới trong gợi ý");
	String to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTo");
	verifyEquals(to, VehicalData.DATA_ORDER_TICKET.PLACE_3);
    }

    @Test
    public void TC_39_KiemTraThongTinTrongMotGoiY() {
	log.info("TC_39_Step_1: lấy thời gian chuyến đi gợi ý");
	String dateSuggest = vehicalTicket.getDynamicDayStart("com.VCB:id/tvFrom");
	dateOutputSuggest = dateSuggest.split("\\ ");
	String date1 = dateOutputSuggest[0].replace("(", "");
	String dateOutput1 = date1.replace(")", "");
	dateOutput2 = dateOutput1.replace("-", "/");

	log.info("TC_39_Step_2: kiểm tra thời gian trong chuyến đi gợi ý");
	verifyEquals(dateOutput, dateOutput2);

	log.info("TC_39_Step_3: kiểm tra điểm đi gợi ý");
	verifyEquals(dateOutputSuggest[1] + " " + dateOutputSuggest[2], VehicalData.DATA_ORDER_TICKET.PLACE_1);

	log.info("TC_39_Step_4: kiểm tra icon chỉ hướng đi");
	vehicalTicket.isDynamicIconChangePlaceAndBackAndFinndDisplayed("com.VCB:id/ivImage");

	log.info("TC_39_Step_5: kiểm tra điểm đến gợi ý");
	String ToSuggest = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTo");
	verifyEquals(ToSuggest, VehicalData.DATA_ORDER_TICKET.PLACE_3);
    }

    @Test
    public void TC_40_KiemTraTimKiemTheoChuyenDiGoiY() {
	log.info("TC_40_Step_1: chọn chuyến đi gợi ý");
	vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvFrom");

	log.info("TC_40_Step_2: kiểm tra điểm đi được tụ động fill");
	String DiemDi = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextPickUp");
	verifyEquals(DiemDi, dateOutputSuggest[1] + " " + dateOutputSuggest[2]);

	log.info("TC_40_Step_3: kiểm tra điểm đến được tụ động fill");
	String DiemDen = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextArrival");
	String ToSuggest = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTo");
	verifyEquals(DiemDen, ToSuggest);

	log.info("TC_40_Step_4: kiểm tra ngày khởi hành được tụ động fill");
	verifyEquals(dateOutput2, dateOutput);

	log.info("TC_40_Step_4: Thoát khỏi màn hình đặt vé xe");
	vehicalTicket.clickToDynamicButtonBack("Đặt vé xe");

    }

    @Test
    public void TC_41_KiemTraChonChuyenDiGoiYSauKhiNhapTieuChiTimKiem() {
	log.info("TC_41_Step_1: Thực hiện chọn đặt vé xe");
	vehicalTicket.Vehical_login();

	log.info("TC_41_Step_2: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

	log.info("TC_41_Step_3: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
	vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_3, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_3);

	log.info("TC_41_Step_4: Chọn chuyến đi gợi ý");
	vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvFrom");

	log.info("TC_41_Step_5: kiểm tra điểm đi được tụ động fill");
	String DiemDi = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextPickUp");
	verifyEquals(DiemDi, dateOutputSuggest[1] + " " + dateOutputSuggest[2]);

	log.info("TC_41_Step_6: kiểm tra điểm đến được tụ động fill");
	String DiemDen = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextArrival");
	String ToSuggest = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTo");
	verifyEquals(DiemDen, ToSuggest);

	log.info("TC_41_Step_7: Thoát khỏi màn hình đặt vé xe");
	vehicalTicket.clickToDynamicButtonBack("Đặt vé xe");
    }

    @Test
    public void TC_42_KiemTraKetQuaTraVeLaRong() {
	log.info("TC_42_Step_1: Thực hiện chọn đặt vé xe");
	vehicalTicket.Vehical_login();

	log.info("TC_42_Step_2: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_2, "com.VCB:id/linPickUp");
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_2);

	log.info("TC_42_Step_3: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
	vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.EDIT_DESTINATION, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.EDIT_DESTINATION);

	log.info("TC_42_Step_4: click button tìm kiếm chuyến đi");
	vehicalTicket.clickToDynamicButton("Tìm kiếm chuyến đi");

	log.info("TC_42_Step_6: kiểm tra thông báo");
	String confirm = vehicalTicket.getDynamicDayStart("android:id/message");
	verifyEquals(confirm, VehicalData.NOTIFICATION.NULL_DATA_FIND_TICKET);
    }

    @Test
    public void TC_43_KiemTraManHinhDanhSachChuyenDi() {
	log.info("TC_42_Step_7: chọn đồng ý");
	vehicalTicket.clickToDynamicButton("ĐỒNG Ý");

	log.info("TC_43_Step_1 Thoát khỏi màn hình đặt vé xe");
	vehicalTicket.clickToDynamicButtonBack("Đặt vé xe");

	log.info("TC_43_Step_2: Thực hiện chọn đặt vé xe");
	vehicalTicket.Vehical_login();

	log.info("TC_43_Step_3: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

	log.info("TC_43_Step_4: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
	vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_3, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_3);

	log.info("TC_42_Step_5: click button tìm kiếm chuyến đi");
	vehicalTicket.clickToDynamicButton("Tìm kiếm chuyến đi");

	log.info("TC_43_Step_6: kiểm tra hiển thị title");
	String title = vehicalTicket.getDynamicDayStart("com.VCB:id/tv_title");
	verifyEquals(title, VehicalData.NOTIFICATION.LIST_TRIP);

	log.info("TC_43_Step_7: kiểm tra hiển thị button back");
	vehicalTicket.isDynamicIconChangePlaceAndBackAndFinndDisplayed("com.VCB:id/ivTitleLeft");

	log.info("TC_43_Step_8: kiểm tra hiển thị button lọc");
	vehicalTicket.isDynamicIconChangePlaceAndBackAndFinndDisplayed("com.VCB:id/ivTitleRight");

	log.info("TC_43_Step_9: kiểm tra thời gian đi");
	vehicalTicket.isDynamicTimeStartDisplayed("com.VCB:id/tvTime2");

	log.info("TC_43_Step_10: kiểm tra hiển thị điểm đi");
	String fromt = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextPickUp");
	verifyEquals(fromt, VehicalData.DATA_ORDER_TICKET.PLACE_2);

	log.info("TC_43_Step_11: kiểm tra hiển thị điểm đến");
	String to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextArrival");
	verifyEquals(to, VehicalData.DATA_ORDER_TICKET.PLACE_3);

	log.info("TC_43_Step_12: kiểm tra hiển thị chỉnh sửa chuyến đi");
	vehicalTicket.isDynamicEditTripDisplayed("com.VCB:id/btnEdit");

	log.info("TC_43_Step_13: kiểm tra thời gian bắt đầu");
	vehicalTicket.isDynamicTimeStartDisplayed("com.VCB:id/tvTime");

	log.info("TC_43_Step_14: kiểm tra hiển thị giá vé");
	vehicalTicket.isDynamicForcusAndPriceDisplay("com.VCB:id/lnPriceTicket");

	log.info("TC_43_Step_15: kiểm tra hiển thị đánh giá");
	vehicalTicket.isDynamicForcusAndPriceDisplay("com.VCB:id/lnRate");

	log.info("TC_43_Step_16: kiểm tra hiển thị tên hãng xe");
	vehicalTicket.isDynamicTimeStartDisplayed("com.VCB:id/tv_hang_xe");

	log.info("TC_43_Step_17: kiểm tra hiển thị đánh giá hãng xe");
	vehicalTicket.isDynamicTimeStartDisplayed("com.VCB:id/tv_count_rate");

	log.info("TC_43_Step_18: kiểm tra hiển thị lộ trình");
	vehicalTicket.isDynamicTimeStartDisplayed("com.VCB:id/tvFrom");
	vehicalTicket.isDynamicTimeStartDisplayed("com.VCB:id/tvTo");

	log.info("TC_43_Step_19: kiểm tra hiển thị dịch vụ");
	vehicalTicket.isDynamicForcusAndPriceDisplay("com.VCB:id/lnService");

	log.info("TC_43_Step_20: kiểm tra hiển thị giá vé");
	vehicalTicket.isDynamicTimeStartDisplayed("com.VCB:id/tvPrice");
    }

    @Test
    public void TC_44_KiemTraTieuDe() {
	log.info("TC_44_Step_1: kiểm tra hiển thị title");
	String title = vehicalTicket.getDynamicDayStart("com.VCB:id/tv_title");
	verifyEquals(title, VehicalData.NOTIFICATION.LIST_TRIP);
    }

    @Test
    public void TC_45_KiemTraNhanBack() {
	log.info("TC_45_Step_1: click back");
	vehicalTicket.clickToDynamicIconChangePlaceAndBack("com.VCB:id/ivTitleLeft");

	log.info("TC_45_Step_2: kiểm tra màn hình sau khi click back");
	vehicalTicket.getDynamicDayStart("com.VCB:id/tvTitle1");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
//	closeApp();
//	service.stop();
    }
}
