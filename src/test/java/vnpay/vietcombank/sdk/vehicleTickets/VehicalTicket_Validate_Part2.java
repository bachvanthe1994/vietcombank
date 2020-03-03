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

public class VehicalTicket_Validate_Part2 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private VehicalPageObject vehicalTicket;
	LocalDate now = LocalDate.now();
	String today = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String tommorrowDate = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now) + 1) + " " + getForwardDate(1);

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		vehicalTicket = PageFactoryManager.getVehicalPageObject(driver);
		login.Global_login(phone, pass, opt);
		vehicalTicket.Vehical_login();
	}

	@Test
	public void TC_17_KiemTraLoaiKiTuDuocPhepNhapVaoTextBoxDiemDen() {
		log.info("TC_17_Step_1: Chọn và nhập điểm đi");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");

		log.info("TC_17_Step_2: nhập kí tự hợp lệ");
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.DATA_INPUT_VN, VehicalData.DATA_ORDER_TICKET.ARRIVAL);

		log.info("TC_17_Step_3: kiểm tra hiển thị các kí tự vừa nhập");
		String data_invalid = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextArrival");
		verifyEquals(data_invalid, VehicalData.DATA_ORDER_TICKET.DATA_INPUT_VN);

		log.info("TC_17_Step_4: chọn đóng");
		vehicalTicket.clickToDynamicButtonIconBack("com.VCB:id/ivClose");

		log.info("TC_17_Step_5: Chọn và nhập điểm đi");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");

		log.info("TC_17_Step_6: nhập kí tự không hợp lệ");
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.DATA_INPUT_SPECCIAL, VehicalData.DATA_ORDER_TICKET.ARRIVAL);

		log.info("TC_17_Step_7: kiểm tra hiển thị các kí tự vừa nhập");
		String data_valid = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextArrival");
		verifyEquals(data_valid, VehicalData.DATA_ORDER_TICKET.ARRIVAL);

		log.info("TC_17_Step_8: chọn đóng");
		vehicalTicket.clickToDynamicButtonIconBack("com.VCB:id/ivClose");
	}

	@Test
	public void TC_18_KiemTraNhapDiemDenHopLe() {
		log.info("TC_18_Step_1: Chọn và nhập điểm đi");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");

		log.info("TC_18_Step_2: nhập kí tự hợp lệ");
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_3, VehicalData.DATA_ORDER_TICKET.ARRIVAL);

		log.info("TC_18_Step_3: kiểm tra hiển thị các kí tự vừa nhập");
		String to = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextArrival");
		verifyEquals(to, VehicalData.DATA_ORDER_TICKET.PLACE_3);
	}

	@Test
	public void TC_19_KiemTraDiemDenTuongDoi() {
		log.info("TC_19_Step_1: nhập và chọn điểm đi");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");

		log.info("TC_19_Step_2: nhập kí tự hợp lệ");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.CONTENT_DESTINATION, "com.VCB:id/linPickUp");

		log.info("TC_19_Step_3: lấy vị trí được sugget để so sánh");
		String sugget_fromt = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTen");
		verifyTrue(sugget_fromt.contains(VehicalData.DATA_ORDER_TICKET.PLACE_3));

		log.info("TC_19_Step_4: chọn đóng");
		vehicalTicket.clickToDynamicButtonIconBack("com.VCB:id/ivClose");
	}

	@Test
	public void TC_20_KiemTraGiaTriNhapVaoDiemDen() {
		log.info("TC_20_Step_1: Chọn và nhập điểm đi");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");

		log.info("TC_20_Step_2: Chọn và nhập điểm đến");
		vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_3, "com.VCB:id/linArival");

		log.info("TC_20_Step_3: Kiểm tra tìm kiếm tương đối");
		String sugget_to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTen");
		verifyTrue(sugget_to.contains(VehicalData.DATA_ORDER_TICKET.CONTENT_DESTINATION));
	}

	@Test
	public void TC_21_KiemTraSuaDiemDen() {
		log.info("TC_21_Step_2: Chọn và nhập điểm đến");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_3, "com.VCB:id/linArival");

		log.info("TC_21_Step_3: chỉnh sửa lại điểm đến");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.CONTENT_DESTINATION, "com.VCB:id/linArival");

		log.info("TC_21_Step_4: kiểm tra tìm kiếm tương đối");
		String sugget_to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTen");
		verifyTrue(sugget_to.contains(VehicalData.DATA_ORDER_TICKET.PLACE_3));
	}

	@Test
	public void TC_22_TimKiemDiemDenKhongTonTai() {
		log.info("TC_22_Step_1: nhập điểm đến không tồn tại");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.DESTINATION_INVALID, "com.VCB:id/linArival");

		log.info("TC_22_Step_2: kiểm tra thông báo");
		String nullData = vehicalTicket.getDynamicConfirmNullData("com.VCB:id/rlNullData");
		verifyEquals(nullData, VehicalData.NOTIFICATION.NOTI_NULL_DATA);

		log.info("TC_22_Step_3: chọn đóng");
		vehicalTicket.clickToDynamicButtonIconBack("com.VCB:id/ivClose");
	}

	@Test
	public void TC_23_KiemTraChonDiemDenTruocKhiChonDiemDi() {
		log.info("TC_23_Step_1: Chọn và nhập điểm đi");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TO);

		log.info("TC_23_Step_2: kiểm tra thông báo");
		String noti = vehicalTicket.getDynamicDayStart("android:id/message");
		verifyEquals(noti, VehicalData.NOTIFICATION.NOTI_CHOISE_DESTINATION_BEFORE);
		vehicalTicket.clickToDynamicButton("OK");
	}

	@Test
	public void TC_24_KiemTraChonDiemDenGiongDiemDi() {
		log.info("TC_24_Step_1: Chọn và nhập điểm đi");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_24_Step_2: Chọn và nhập điểm đi");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linArival");

		log.info("TC_24_Step_3: kiểm tra hiển thị");
		String sugget_to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTen");
		vehicalTicket.clickToDynamicText(sugget_to);
	}

	@Test
	public void TC_25_KiemTraChonLaiDiemDiSauKhiChonDiemDen() {
		log.info("TC_25_Step_1: Chọn và nhập điểm đi");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_25_Step_2: Chọn và nhập điểm đến");
		vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_3, VehicalData.DATA_ORDER_TICKET.ARRIVAL);

		log.info("TC_25_Step_3: chỉnh sửa lại điểm đi");
		vehicalTicket.clickToDynamicTextBox("com.VCB:id/linPickUp");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_2, "com.VCB:id/linPickUp");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_2);

		log.info("TC_25_Step_4: kiểm tra hiển thị");
		String fromt = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextArrival");
		verifyEquals(fromt, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
	}

	@Test
	public void TC_26_KiemTraNhanDoiDiemDiDiemDen() {
		log.info("TC_26_Step_1: Chọn và nhập điểm đến");
		vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_3, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_3);

		log.info("TC_26_Step_2: click icon");
		vehicalTicket.clickToDynamicIconChangePlaceAndBack("com.VCB:id/ivround");

		log.info("TC_23_Step_3: kiểm tra hiển thị sau khi đổi vị trí");
		String fromt = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextPickUp");
		verifyEquals(fromt, VehicalData.DATA_ORDER_TICKET.PLACE_3);
		String to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextArrival");
		verifyEquals(to, VehicalData.DATA_ORDER_TICKET.PLACE_2);
	}

	@Test
	public void TC_27_KiemTraThongTinKhoiHanh() {
		log.info("TC_27_Step_1: lấy ngày đi");
		String date = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
		verifyEquals(date, today);

		log.info("TC_27_Step_2: kiểm tra button được focus");
		verifyTrue(vehicalTicket.isDynamicForcusAndPriceDisplay("com.VCB:id/lnToday"));
	}

	@Test
	public void TC_28_KiemTraChonNgayMai() {
		log.info("TC_23_Step_1: Chọn ngày mai");
		vehicalTicket.clickToDynamicTomorrowAndFilterTrip("com.VCB:id/lnNextday");

		log.info("TC_23_Step_2: kiểm tra ngày thay đổi đi");
		String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
		verifyEquals(DayStart, tommorrowDate);

		log.info("TC_23_Step_3: kiểm tra button được focus");
		verifyTrue(vehicalTicket.isDynamicForcusAndPriceDisplay("com.VCB:id/lnNextday"));
	}

	@Test
	public void TC_29_KiemTraChonNgayHomNay() {
		log.info("TC_29_Step_1: Chọn ngày hôm nay");
		vehicalTicket.clickToDynamicTomorrowAndFilterTrip("com.VCB:id/lnToday");

		log.info("TC_29_Step_2: kiểm tra ngày thay đổi đi");
		String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
		verifyEquals(DayStart, today);

		log.info("TC_29_Step_3: kiểm tra button được focus");
		verifyTrue(vehicalTicket.isDynamicForcusAndPriceDisplay("com.VCB:id/lnToday"));
	}

	@Test
	public void TC_30_KiemTraNhanChonThoiGian() {
		log.info("TC_30_Step_1: Chọn ngày");
		vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

		log.info("TC_30_Step_2: kiểm tra ngày được focus là ngày hiện tại");
		verifyTrue(vehicalTicket.isDynamicForcusText(getCurrentDay()));

		log.info("TC_30_Step_3: kiểm tra ngày được focus là ngày hiện tại");
		vehicalTicket.clickToDynamicButtonBack("Chọn ngày đi");
	}

	@Test
	public void TC_31_KiemTraNhanChonNgayQuaKhu() {
		log.info("TC_31_Step_1: Chọn ngày");
		vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

		log.info("TC_31_Step_2: Chon ngay qua khu");
		vehicalTicket.clickToDynamicSelectedDate(getCurentMonthAndYearMinusDays(1), getBackWardDay(1));

		log.info("TC_31_Step_2: Click Quay lai");
		vehicalTicket.clickToDynamicButtonIconBack("com.VCB:id/ivTitleLeft");

		log.info("TC_31_Step_3: kiểm tra ngay qua khu khong duoc chon");
		String today = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
		verifyEquals(vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth"), today);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}
}
