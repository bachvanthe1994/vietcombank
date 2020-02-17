package vnpay.vietcombank.vehicleTickets;

import java.io.IOException;
import java.time.LocalDate;

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

//    @Test
    public void TC_31_KiemTraChonNgayHienTai() {
	log.info("TC_13_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(VehicalData.Data_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBox(VehicalData.Data_ORDER_TICKET.DEPARTURE, VehicalData.Data_ORDER_TICKET.FROMT_INPUT);
	vehicalTicket.clickToDynamicText(VehicalData.Data_ORDER_TICKET.DEPARTURE);

	log.info("TC_13_Step_1: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicButtonLinkOrLinkText(VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.inputToDynamicInputBox(VehicalData.Data_ORDER_TICKET.DESTINATION, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.clickToDynamicText(VehicalData.Data_ORDER_TICKET.DESTINATION);

	log.info("TC_13_Step_1: Chọn ngày");
	vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

	log.info("TC_13_Step_1: Chọn ngày hiện tai");
	vehicalTicket.clickToDynamicText(driver, getCurrentDay());

	log.info("TC_13_Step_1: kiểm tra ngày thay đổi đi");
	String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
	verifyEquals(DayStart, today);

	log.info("TC_10_Step_3: chọn đóng");
	vehicalTicket.clickToDynamicButtonIconBack("com.VCB:id/ivClose");

    }

//    @Test
    public void TC_32_KiemTraChonNgayMai() {
	log.info("TC_13_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(VehicalData.Data_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBox(VehicalData.Data_ORDER_TICKET.DEPARTURE, VehicalData.Data_ORDER_TICKET.FROMT_INPUT);
	vehicalTicket.clickToDynamicText(VehicalData.Data_ORDER_TICKET.DEPARTURE);

	log.info("TC_13_Step_1: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicButtonLinkOrLinkText(VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.inputToDynamicInputBox(VehicalData.Data_ORDER_TICKET.DESTINATION, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.clickToDynamicText(VehicalData.Data_ORDER_TICKET.DESTINATION);

	log.info("TC_13_Step_1: Chọn ngày");
	vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

	int tomorrow = Integer.parseInt(getCurrentDay()) + 1;
	String tomorrow_string = Integer.toString(tomorrow);

	log.info("TC_13_Step_1: Chọn ngày hiện tai");
	vehicalTicket.clickToDynamicText(tomorrow_string);

	log.info("TC_13_Step_1: kiểm tra ngày thay đổi đi");
	String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
	verifyEquals(DayStart, tommorrowDate);

    }

//    @Test
    public void TC_33_KiemTraChonNgayLonHonNgayMai() {
	log.info("TC_13_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(VehicalData.Data_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBox(VehicalData.Data_ORDER_TICKET.DEPARTURE, VehicalData.Data_ORDER_TICKET.FROMT_INPUT);
	vehicalTicket.clickToDynamicText(VehicalData.Data_ORDER_TICKET.DEPARTURE);

	log.info("TC_13_Step_1: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicButtonLinkOrLinkText(VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.inputToDynamicInputBox(VehicalData.Data_ORDER_TICKET.DESTINATION, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.clickToDynamicText(VehicalData.Data_ORDER_TICKET.DESTINATION);

	log.info("TC_13_Step_1: Chọn ngày");
	vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

	int tomorrow = Integer.parseInt(getCurrentDay()) + 2;
	String tomorrow_string = Integer.toString(tomorrow);

	log.info("TC_13_Step_1: chọn ngày lớn hon ngày mai");
	vehicalTicket.clickToDynamicText(tomorrow_string);

	log.info("TC_13_Step_1: kiểm tra ngày thay đổi đi");
	String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
	verifyEquals(DayStart, tomorrow_week);

	log.info("TC_13_Step_1: kiểm tra không focus vào ngày button nào");
	verifyFailure(vehicalTicket.isControlForcus(driver, "//android.widget.LinearLayout[@resource-id='com.VCB:id/lnToday']"));
	verifyFailure(vehicalTicket.isControlForcus(driver, "//android.widget.LinearLayout[@resource-id='com.VCB:id/lnNextday']"));
    }

//    @Test
    public void TC_33_KiemTraThayDoiThoiGianDi() {
	vehicalTicket.clickToDynamicText(VehicalData.Data_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBox(VehicalData.Data_ORDER_TICKET.DEPARTURE, VehicalData.Data_ORDER_TICKET.FROMT_INPUT);
	vehicalTicket.clickToDynamicText(VehicalData.Data_ORDER_TICKET.DEPARTURE);

	log.info("TC_13_Step_1: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicButtonLinkOrLinkText(VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.inputToDynamicInputBox(VehicalData.Data_ORDER_TICKET.DESTINATION, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.clickToDynamicText(VehicalData.Data_ORDER_TICKET.DESTINATION);

	log.info("TC_13_Step_1: Chọn ngày");
	vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

	int tomorrow = Integer.parseInt(getCurrentDay()) + 1;
	String tomorrow_string = Integer.toString(tomorrow);

	log.info("TC_13_Step_1: Chọn ngày mai");
	vehicalTicket.clickToDynamicText(tomorrow_string);

	log.info("TC_13_Step_1: Tim kiếm chuyến đi");
	vehicalTicket.clickToDynamicButton("Tìm kiếm chuyến đi");

	log.info("TC_13_Step_1: click back");
	vehicalTicket.clickToDynamicButtonBack("Danh sách chuyến xe");

	log.info("TC_13_Step_1: chon lại ngày đi");
	vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

	int date = Integer.parseInt(getCurrentDay()) + 2;
	String date_string = Integer.toString(date);

	log.info("TC_13_Step_1: chọn ngày lớn hon ngày mai");
	vehicalTicket.clickToDynamicText(date_string);

	log.info("TC_13_Step_1: kiểm tra ngày thay đổi đi");
	String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
	verifyEquals(DayStart, tomorrow_week);
    }

    @Test
    public void TC_33_KiemTraLichTaiLich() {
	vehicalTicket.clickToDynamicText(VehicalData.Data_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBox(VehicalData.Data_ORDER_TICKET.DEPARTURE, VehicalData.Data_ORDER_TICKET.FROMT_INPUT);
	vehicalTicket.clickToDynamicText(VehicalData.Data_ORDER_TICKET.DEPARTURE);

	log.info("TC_13_Step_1: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicButtonLinkOrLinkText(VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.inputToDynamicInputBox(VehicalData.Data_ORDER_TICKET.DESTINATION, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.clickToDynamicText(VehicalData.Data_ORDER_TICKET.DESTINATION);

	log.info("TC_13_Step_1: Chọn ngày");
	vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

	log.info("TC_13_Step_1: lấy ra tháng/năm hiện tại");
	String today = "THÁNG" + getCurrenMonth() + "/" + getCurrentYear();
//	vehicalTicket.isControlUnDisplayed(driver, locator, dynamicValue);
    }

}
