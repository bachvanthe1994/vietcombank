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

public class VehicalTicket_3_Validate extends Base {
    AndroidDriver<AndroidElement> driver;
    private LogInPageObject login;
    private VehicalPageObject vehicalTicket;
    LocalDate now = LocalDate.now();
    String today = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
    String tommorrowDate = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now) + 1) + " " + getForwardDate(1);

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

	vehicalTicket.Vehical_login(driver);
    }

    @Test
    public void TC_17_KiemTraLoaiKiTuDuocPhepNhap() {
	log.info("TC_17_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE, VehicalData.Data_ORDER_TICKET.FROMT_INPUT);

	log.info("TC_17_Step_2: nhập kí tự hợp lệ");
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DATA_INPUT_INVALID, VehicalData.Data_ORDER_TICKET.TO_INPUT);

	log.info("TC_17_Step_3: kiểm tra hiển thị các kí tự vừa nhập");
	String data_invalid = vehicalTicket.getDynamicEditText(driver, "com.VCB:id/edtTextArrival");
	verifyEquals(data_invalid, VehicalData.Data_ORDER_TICKET.DATA_INPUT_INVALID);

	log.info("TC_17_Step_4: chọn đóng");
	vehicalTicket.clickToDynamicButtonIconBack(driver, "com.VCB:id/ivClose");

	log.info("TC_17_Step_5: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE, VehicalData.Data_ORDER_TICKET.FROMT_INPUT);

	log.info("TC_17_Step_6: nhập kí tự không hợp lệ");
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DATA_INPUT_VALID, VehicalData.Data_ORDER_TICKET.TO_INPUT);

	log.info("TC_17_Step_7: kiểm tra hiển thị các kí tự vừa nhập");
	String data_valid = vehicalTicket.getDynamicEditText(driver, "com.VCB:id/edtTextArrival");
	verifyEquals(data_valid, VehicalData.Data_ORDER_TICKET.TO_INPUT);

	log.info("TC_17_Step_8: chọn đóng");
	vehicalTicket.clickToDynamicButtonIconBack(driver, "com.VCB:id/ivClose");
    }

    @Test
    public void TC_18_KiemTraNhapDiemDenHopLe() {
	log.info("TC_13_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE, VehicalData.Data_ORDER_TICKET.FROMT_INPUT);

	log.info("TC_08_Step_1: nhập kí tự hợp lệ");
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DESTINATION, VehicalData.Data_ORDER_TICKET.TO_INPUT);

	log.info("TC_08_Step_2: kiểm tra hiển thị các kí tự vừa nhập");
	String to = vehicalTicket.getDynamicEditText(driver, "com.VCB:id/edtTextArrival");
	verifyEquals(to, VehicalData.Data_ORDER_TICKET.DESTINATION);
    }

    @Test
    public void TC_19_KiemTraDiemDenTuongDoi() {
	log.info("TC_19_Step_1: nhập và chọn điểm đi");
	vehicalTicket.inputToDynamicInputBoxIDandIndex(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE, "com.VCB:id/linPickUp");

	log.info("TC_19_Step_2: nhập kí tự hợp lệ");
	vehicalTicket.inputToDynamicInputBoxIDandIndex(driver, VehicalData.Data_ORDER_TICKET.CONTENT_DESTINATION, "com.VCB:id/linPickUp");

	log.info("TC_19_Step_3: lấy vị trí được sugget để so sánh");
	String sugget_fromt = vehicalTicket.getDynamicDayStart(driver, "com.VCB:id/tvTen");
	verifyTrue(sugget_fromt.contains(VehicalData.Data_ORDER_TICKET.DESTINATION));

	log.info("TC_19_Step_4: chọn đóng");
	vehicalTicket.clickToDynamicButtonIconBack(driver, "com.VCB:id/ivClose");
    }

    @Test
    public void TC_20_KiemTraGiaTriNhapVaoDiemDen() {
	log.info("TC_20_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE, VehicalData.Data_ORDER_TICKET.FROMT_INPUT);

	log.info("TC_20_Step_2: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicButtonLinkOrLinkText(driver, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DESTINATION, VehicalData.Data_ORDER_TICKET.TO_INPUT);

	log.info("TC_20_Step_3: Kiểm tra tìm kiếm tương đối");
	String sugget_to = vehicalTicket.getDynamicDayStart(driver, "com.VCB:id/tvTen");
	verifyTrue(sugget_to.contains(VehicalData.Data_ORDER_TICKET.CONTENT_DESTINATION));
    }

    @Test
    public void TC_21_KiemTraSuaDiemDen() {
	log.info("TC_21_Step_2: Chọn và nhập điểm đến");
	vehicalTicket.inputToDynamicInputBoxIDandIndex(driver, VehicalData.Data_ORDER_TICKET.DESTINATION, "com.VCB:id/linArival");

	log.info("TC_21_Step_3: chỉnh sửa lại điểm đến");
	vehicalTicket.inputToDynamicInputBoxIDandIndex(driver, VehicalData.Data_ORDER_TICKET.CONTENT_DESTINATION, "com.VCB:id/linArival");

	log.info("TC_21_Step_4: kiểm tra tìm kiếm tương đối");
	String sugget_to = vehicalTicket.getDynamicDayStart(driver, "com.VCB:id/tvTen");
	verifyTrue(sugget_to.contains(VehicalData.Data_ORDER_TICKET.DESTINATION));
    }

    @Test
    public void TC_22_TimKiemDiemDenKhongTonTai() {
	log.info("TC_22_Step_2: nhập điểm đến không tồn tại");
	vehicalTicket.inputToDynamicInputBoxIDandIndex(driver, VehicalData.Data_ORDER_TICKET.DESTINATION_INVALID, "com.VCB:id/linArival");

	log.info("TC_22_Step_3: kiểm tra thông báo");
	String nullData = vehicalTicket.getDynamicConfirmNullData(driver, "com.VCB:id/rlNullData");
	verifyEquals(nullData, VehicalData.NOTIFICATION.NOTI_NULL_DATA);

	log.info("TC_08_Step_6: chọn đóng");
	vehicalTicket.clickToDynamicButtonIconBack(driver, "com.VCB:id/ivClose");
    }

    @Test
    public void TC_23_KiemTraChonDiemDenTruocKhiChonDiemDi() {
	log.info("TC_13_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.TO);

	log.info("TC_13_Step_1: kiểm tra thông báo");
	String noti = vehicalTicket.getDynamicDayStart(driver, "android:id/message");
	verifyEquals(noti, VehicalData.NOTIFICATION.NOTI_CHOISE_DESTINATION_BEFORE);
	vehicalTicket.clickToDynamicButton(driver, "OK");
    }

    @Test
    public void TC_24_KiemTraChonDiemDenGiongDiemDi() {
	log.info("TC_13_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE, VehicalData.Data_ORDER_TICKET.FROMT_INPUT);
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE);

	log.info("TC_13_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.inputToDynamicInputBoxIDandIndex(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE, "com.VCB:id/linArival");

	log.info("TC_13_Step_1: kiểm tra hiển thị");
	String sugget_to = vehicalTicket.getDynamicDayStart(driver, "com.VCB:id/tvTen");
	verifyEquals(sugget_to, VehicalData.Data_ORDER_TICKET.DEPARTURE);
    }

    @Test
    public void TC_25_KiemTraChonLaiDiemDiSauKhiChonDiemDen() {
	log.info("TC_13_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.inputToDynamicInputBoxIDandIndex(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE, "com.VCB:id/linPickUp");
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE);

	log.info("TC_13_Step_1: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicButtonLinkOrLinkText(driver, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DESTINATION, VehicalData.Data_ORDER_TICKET.TO_INPUT);

	log.info("TC_13_Step_1: chỉnh sửa lại điểm đi");
	vehicalTicket.clickToDynamicTextBox(driver, "com.VCB:id/linPickUp");
	vehicalTicket.inputToDynamicInputBoxIDandIndex(driver, VehicalData.Data_ORDER_TICKET.EDIT_DEPARTURE, "com.VCB:id/linPickUp");
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.EDIT_DEPARTURE);

	log.info("TC_13_Step_1: kiểm tra hiển thị");
	String fromt = vehicalTicket.getDynamicEditText(driver, "com.VCB:id/edtTextArrival");
	verifyEquals(fromt, VehicalData.Data_ORDER_TICKET.TO_INPUT);
    }

    @Test
    public void TC_26_KiemTraNhanDoiDiemDiDiemDen() {
	log.info("TC_13_Step_1: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicButtonLinkOrLinkText(driver, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DESTINATION, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.DESTINATION);

	log.info("TC_13_Step_1: click icon");
	vehicalTicket.clickToDynamicIconChangePlacek(driver, "com.VCB:id/ivround");

	log.info("TC_13_Step_1: kiểm tra hiển thị sau khi đổi vị trí");
	String fromt = vehicalTicket.getDynamicDayStart(driver, "com.VCB:id/tvTextPickUp");
	verifyEquals(fromt, VehicalData.Data_ORDER_TICKET.DESTINATION);
	String to = vehicalTicket.getDynamicDayStart(driver, "com.VCB:id/tvTextArrival");
	verifyEquals(to, VehicalData.Data_ORDER_TICKET.DEPARTURE);
    }

    @Test
    public void TC_27_KiemTraThongTinKhoHanh() {
	log.info("TC_13_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE, VehicalData.Data_ORDER_TICKET.FROMT_INPUT);
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE);

	log.info("TC_13_Step_1: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicButtonLinkOrLinkText(driver, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DESTINATION, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.DESTINATION);

	log.info("TC_13_Step_1: lấy ngày đi");
	String date = vehicalTicket.getDynamicDayStart(driver, "com.VCB:id/tvMonth");
	verifyEquals(date, today);

	log.info("TC_13_Step_1: kiểm tra button được focus");
	verifyTrue(vehicalTicket.isControlForcus(driver, "//android.widget.LinearLayout[@resource-id='com.VCB:id/lnToday']"));
    }

//    @Test
    public void TC_28_KiemTraChonNgayMai() {
	log.info("TC_13_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE, VehicalData.Data_ORDER_TICKET.FROMT_INPUT);
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE);

	log.info("TC_13_Step_1: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicButtonLinkOrLinkText(driver, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DESTINATION, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.DESTINATION);

	log.info("TC_13_Step_1: Chọn ngày mai");
	vehicalTicket.clickToDynamicTomorrow(driver, "com.VCB:id/lnNextday");

	log.info("TC_13_Step_1: kiểm tra ngày thay đổi đi");
	String DayStart = vehicalTicket.getDynamicDayStart(driver, "com.VCB:id/tvMonth");
	verifyEquals(DayStart, tommorrowDate);

	log.info("TC_13_Step_1: kiểm tra button được focus");
	verifyTrue(vehicalTicket.isControlForcus(driver, "//android.widget.LinearLayout[@resource-id='com.VCB:id/lnNextday']"));
    }

//    @Test
    public void TC_29_KiemTraChonNgayHomNay() {
	log.info("TC_13_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE, VehicalData.Data_ORDER_TICKET.FROMT_INPUT);
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE);

	log.info("TC_13_Step_1: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicButtonLinkOrLinkText(driver, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DESTINATION, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.DESTINATION);

	log.info("TC_13_Step_1: Chọn ngày hôm nay");
	vehicalTicket.clickToDynamicTomorrow(driver, "com.VCB:id/lnToday");

	log.info("TC_13_Step_1: kiểm tra ngày thay đổi đi");
	String DayStart = vehicalTicket.getDynamicDayStart(driver, "com.VCB:id/tvMonth");
	verifyEquals(DayStart, today);

	log.info("TC_13_Step_1: kiểm tra button được focus");
	verifyTrue(vehicalTicket.isControlForcus(driver, "//android.widget.LinearLayout[@resource-id='com.VCB:id/lnToday']"));
    }

//    @Test
    public void TC_30_KiemTraNhanChonThoiGian() {
	log.info("TC_13_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE, VehicalData.Data_ORDER_TICKET.FROMT_INPUT);
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE);

	log.info("TC_13_Step_1: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicButtonLinkOrLinkText(driver, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DESTINATION, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.DESTINATION);

	log.info("TC_13_Step_1: Chọn ngày");
	vehicalTicket.clickToDynamicButtonChoiseDate(driver, "com.VCB:id/tvMonth");

	log.info("TC_13_Step_1: kiểm tra ngày được focus là ngày hiện tại");
	verifyTrue(vehicalTicket.isControlForcus(driver, "//android.widget.TextView[@text='" + getCurrentDay() + "']"));
    }

//    @Test
    public void TC_31_KiemTraNhanChonNgayQuaKhu() {
	log.info("TC_13_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE, VehicalData.Data_ORDER_TICKET.FROMT_INPUT);
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.DEPARTURE);

	log.info("TC_13_Step_1: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicButtonLinkOrLinkText(driver, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data_ORDER_TICKET.DESTINATION, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data_ORDER_TICKET.DESTINATION);

	log.info("TC_13_Step_1: Chọn ngày");
	vehicalTicket.clickToDynamicButtonChoiseDate(driver, "com.VCB:id/tvMonth");

	log.info("TC_13_Step_1: kiểm tra ngày được focus là ngày hiện tại");
	String string_date = getCurrentDay();
	int yesterday = Integer.parseInt(string_date) - 1;
	verifyFailure(vehicalTicket.isControlForcus(driver, "//android.widget.TextView[@text='" + yesterday + "']"));
	
    }

}
