package vnpay.vietcombank.vehicleTickets;

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
import vnpay.vietcombank.vehicleTicketData.VehicalData;

public class VehicalTicket_4_Validate extends Base {
    AndroidDriver<AndroidElement> driver;
    private LogInPageObject login;
    private VehicalPageObject vehicalTicket;
    LocalDate now = LocalDate.now();
    String today = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
    String tommorrowDate = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now) + 1) + " " + getForwardDate(1);
    String tomorrow_week = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now) + 2) + " " + getForwardDate(2);

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
	vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.DEPARTURE, VehicalData.DATA_ORDER_TICKET.FROMT_INPUT);
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.DEPARTURE);

	log.info("TC_31_Step_2: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicButtonLinkOrLinkText(VehicalData.DATA_ORDER_TICKET.TO_INPUT);
	vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.DESTINATION, VehicalData.DATA_ORDER_TICKET.TO_INPUT);
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.DESTINATION);

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

	int tomorrow = Integer.parseInt(getCurrentDay()) + 1;
	String tomorrow_string = Integer.toString(tomorrow);

	log.info("TC_32_Step_2: Chọn ngày hiện tai");
	vehicalTicket.clickToDynamicText(tomorrow_string);

	log.info("TC_32_Step_3: kiểm tra ngày thay đổi đi");
	String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
	verifyEquals(DayStart, tommorrowDate);

    }

    @Test
    public void TC_33_KiemTraChonNgayLonHonNgayMai() {
	log.info("TC_33_Step_1: Chọn ngày");
	vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

	int tomorrow = Integer.parseInt(getCurrentDay()) + 2;
	String tomorrow_string = Integer.toString(tomorrow);

	log.info("TC_33_Step_2: chọn ngày lớn hon ngày mai");
	vehicalTicket.clickToDynamicText(tomorrow_string);

	log.info("TC_33_Step_3: kiểm tra ngày thay đổi đi");
	String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
	verifyEquals(DayStart, tomorrow_week);

	log.info("TC_33_Step_4: kiểm tra không focus vào ngày button nào");
	verifyFailure(vehicalTicket.isControlForcus(driver, "//android.widget.LinearLayout[@resource-id='com.VCB:id/lnToday']"));
	verifyFailure(vehicalTicket.isControlForcus(driver, "//android.widget.LinearLayout[@resource-id='com.VCB:id/lnNextday']"));
    }

    @Test
    public void TC_34_KiemTraThayDoiThoiGianDi() {
	log.info("TC_34_Step_1: Chọn ngày");
	vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

	int tomorrow = Integer.parseInt(getCurrentDay()) + 1;
	String tomorrow_string = Integer.toString(tomorrow);

	log.info("TC_34_Step_2: Chọn ngày mai");
	vehicalTicket.clickToDynamicText(tomorrow_string);

	log.info("TC_34_Step_3: Tim kiếm chuyến đi");
	vehicalTicket.clickToDynamicButton("Tìm kiếm chuyến đi");

	log.info("TC_34_Step_4: click back");
	vehicalTicket.clickToDynamicButtonBack("Danh sách chuyến xe");

	log.info("TC_34_Step_5: chon lại ngày đi");
	vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

	int date = Integer.parseInt(getCurrentDay()) + 2;
	String date_string = Integer.toString(date);

	log.info("TC_34_Step_6: chọn ngày lớn hon ngày mai");
	vehicalTicket.clickToDynamicText(date_string);

	log.info("TC_34_Step_7: kiểm tra ngày thay đổi đi");
	String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
	verifyEquals(DayStart, tomorrow_week);
    }

    @Test
    public void TC_35_KiemTraLichTaiLich() {
	log.info("TC_35_Step_1: Chọn ngày");
	vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

	log.info("TC_35_Step_2: lấy ra tháng/năm hiện tại");
	String today = "THÁNG" + getCurrenMonth2() + "/" + getCurrentYear();

	log.info("TC_35_Step_3: lấy ra tháng/năm của 3 tháng sau");
	String MonthAndYear = getCurentMonthAndYearPlusMonth(3);
	verifyTrue(vehicalTicket.isDynamicDisplayed(MonthAndYear));

	log.info("TC_35_Step_4: lấy ra tháng/năm của 4 tháng sau");
	String MonthAndYear4 = getCurentMonthAndYearPlusMonth(4);
	vehicalTicket.isDynamicUnDisplayed(MonthAndYear4);

	log.info("TC_35_Step_5: click back");
	vehicalTicket.clickToDynamicIconChangePlacek("com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_36_KiemTraButtonTimKiemChuyenDi() {
	log.info("TC_36_Step_1: click button tìm kiếm chuyến đi");
	vehicalTicket.clickToDynamicButton("Tìm kiếm chuyến đi");

	log.info("TC_36_Step_2: kiểm tra hiển thị");
	String text = vehicalTicket.getDynamicDayStart("com.VCB:id/tv_title");
	verifyEquals(text, VehicalData.NOTIFICATION.LIST_TRIP);

	log.info("TC_36_Step_3: click back");
	vehicalTicket.clickToDynamicIconChangePlacek("com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_37_KiemTraNhomThongTinChuyenDiGoiY() {
	log.info("TC_37_Step_1: kiểm tra hiển thị title chuyến đi gợi ý");
	String sugget_trip = vehicalTicket.getDynamicSuggestTrip("com.VCB:id/lnSuggest");
	verifyEquals(sugget_trip, VehicalData.DATA_ORDER_TICKET.SUGGEST_TRIP);

	log.info("TC_37_Step_2: kiể tra hiển thị thời gian khởi hành và điểm đi");
	String suggest_from = vehicalTicket.getDynamicDayStart("com.VCB:id/tvFrom");
	String from = suggest_from.substring(12);
	verifyEquals(from, VehicalData.DATA_ORDER_TICKET.DEPARTURE);

	log.info("TC_37_Step_3: kiểm tra điểm tới trong gợi ý");
	String to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTo");
	verifyEquals(from, VehicalData.DATA_ORDER_TICKET.DEPARTURE);

	log.info("TC_37_Step_4: click back");
	vehicalTicket.clickToDynamicIconChangePlacek("com.VCB:id/ivTitleLeft");
    }

    @Test
    public void TC_38_KiemTraLuuThongTinChuyenDiGoiY() {
	log.info("TC_38_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.DESTINATION, VehicalData.DATA_ORDER_TICKET.FROMT_INPUT);
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.DESTINATION);

	log.info("TC_38_Step_2: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicButtonLinkOrLinkText(VehicalData.DATA_ORDER_TICKET.TO_INPUT);
	vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.EDIT_DEPARTURE, VehicalData.DATA_ORDER_TICKET.TO_INPUT);
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.EDIT_DEPARTURE);

	log.info("TC_38_Step_3: click button tìm kiếm chuyến đi");
	vehicalTicket.clickToDynamicButton("Tìm kiếm chuyến đi");

	log.info("TC_38_Step_4: click back");
	vehicalTicket.clickToDynamicIconChangePlacek("com.VCB:id/ivTitleLeft");

	log.info("TC_38_Step_5: kiể tra hiển thị thời gian khởi hành và điểm đi");
	String suggest_from = vehicalTicket.getDynamicDayStart("com.VCB:id/tvFrom");
	String from = suggest_from.substring(12);
	verifyEquals(from, VehicalData.DATA_ORDER_TICKET.DESTINATION);

	log.info("TC_38_Step_6: kiểm tra điểm tới trong gợi ý");
	String to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTo");
	verifyEquals(from, VehicalData.DATA_ORDER_TICKET.EDIT_DEPARTURE);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
	closeApp();
	service.stop();
    }
}
