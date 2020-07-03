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

public class VehicalTicket_Validate_Part5 extends Base {
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
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		vehicalTicket = PageFactoryManager.getVehicalPageObject(driver);
		login.Global_login(phone, pass, opt);
		login.scrollDownToText(driver, "© 2019 Vietcombank");
		login.scrollIDownOneTime(driver);

		vehicalTicket.clickToDynamicText("Đặt vé xe");

		vehicalTicket.clickToDynamicButton("Đồng ý");
	}

	@Test
	public void TC_63_KiemTraSoKiTuDuocPhepNhapVaoDiemDen() {
		log.info("TC_63_Step_1: Chọn và nhập điểm đi");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.CHOOSE_FROM_PLACE);
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_63_Step_2: Chọn và nhập điểm đến");
		vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.EDIT_DESTINATION, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.EDIT_DESTINATION);

		log.info("TC_63_Step_3: click button tìm kiếm chuyến đi");
		vehicalTicket.clickToDynamicButton("Tìm kiếm chuyến đi");

		log.info("TC_63_Step_5: chọn sửa");
		vehicalTicket.clickToDynamicButtonForID("com.VCB:id/btnEdit");

		log.info("TC_63_Step_6: điền 201 ki tự vào chọn điểm đến");
		vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvTextArrival");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.LENGTH_201, "com.VCB:id/linArival");

		log.info("TC_63_Step_7: kiểm tra max lenght 200");
		String lenght_201 = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextArrival");
		verifyEquals(lenght_201, VehicalData.DATA_ORDER_TICKET.LENGTH_200);
	}

	@Test
	public void TC_64_KiemTraLoaiKiTuDuocPhepNhapVaoTextBoxDiemDen() {
		log.info("TC_17_Step_1: nhập kí tự hợp lệ");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.DATA_INPUT_VN, "com.VCB:id/linArival");

		log.info("TC_17_Step_2: kiểm tra hiển thị các kí tự vừa nhập");
		String data_invalid = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextArrival");
		verifyEquals(data_invalid, VehicalData.DATA_ORDER_TICKET.DATA_INPUT_VN);

		log.info("TC_17_Step_3: nhập kí tự không hợp lệ");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.DATA_INPUT_SPECCIAL, "com.VCB:id/linArival");

		log.info("TC_17_Step_4: kiểm tra hiển thị các kí tự vừa nhập");
		String data_valid = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextArrival");
		verifyEquals(data_valid, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
	}

	@Test
	public void TC_65_KiemTraNhapDiemDenHopLe() {
		log.info("TC_65_Step_1: nhập kí tự hợp lệ");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_3, "com.VCB:id/linArival");

		log.info("TC_65_Step_2: kiểm tra hiển thị các kí tự vừa nhập");
		String to = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextArrival");
		verifyEquals(to, VehicalData.DATA_ORDER_TICKET.PLACE_3);
	}

	@Test
	public void TC_66_KiemTraDiemDenTuongDoi() {
		log.info("TC_66_Step_2: nhập kí tự hợp lệ");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.CONTENT_DESTINATION, "com.VCB:id/linArival");

		log.info("TC_66_Step_3: lấy vị trí được sugget để so sánh");
		String sugget_fromt = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTen");
		verifyTrue(sugget_fromt.contains(VehicalData.DATA_ORDER_TICKET.PLACE_3));
	}

	@Test
	public void TC_67_KiemTraGiaTriNhapVaoDiemDen() {
		log.info("TC_67_Step_2: Chọn và nhập điểm đến");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_3, "com.VCB:id/linArival");

		log.info("TC_67_Step_3: Kiểm tra tìm kiếm tương đối");
		String sugget_to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTen");
		verifyTrue(sugget_to.contains(VehicalData.DATA_ORDER_TICKET.CONTENT_DESTINATION));
	}

	@Test
	public void TC_68_KiemTraSuaDiemDen() {
		log.info("TC_21_Step_2: Chọn và nhập điểm đến");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_3, "com.VCB:id/linArival");

		log.info("TC_21_Step_3: chỉnh sửa lại điểm đến");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.CONTENT_DESTINATION, "com.VCB:id/linArival");

		log.info("TC_21_Step_4: kiểm tra tìm kiếm tương đối");
		String sugget_to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTen");
		verifyTrue(sugget_to.contains(VehicalData.DATA_ORDER_TICKET.PLACE_3));
	}

	@Test
	public void TC_70_TimKiemDiemDenKhongTonTai() {
		log.info("TC_70_Step_1: nhập điểm đến không tồn tại");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.DESTINATION_INVALID, "com.VCB:id/linArival");

		log.info("TC_70_Step_2: kiểm tra thông báo");
		String nullData = vehicalTicket.getDynamicConfirmNullData("com.VCB:id/rlNullData");
		verifyEquals(nullData, VehicalData.NOTIFICATION.NOTI_NULL_DATA);

	}

	@Test
	public void TC_71_KiemTraChonDiemDenTruocKhiChonDiemDi() {
		log.info("TC_71_Step_1: xóa địa điểm đã nhập");
		vehicalTicket.inputToDynamicInputBoxID("", "com.VCB:id/linArival");
		vehicalTicket.clickToDynamicTextBox("com.VCB:id/linPickUp");
		vehicalTicket.inputToDynamicInputBoxID("", "com.VCB:id/linPickUp");

		log.info("TC_71_Step_2: Chọn và nhập điểm đi");
		vehicalTicket.clickToDynamicTextBox("com.VCB:id/linArival");

		log.info("TC_71_Step_3: kiểm tra thông báo");
		String noti = vehicalTicket.getDynamicDayStart("android:id/message");
		verifyEquals(noti, VehicalData.NOTIFICATION.NOTI_CHOISE_DESTINATION_BEFORE);
		vehicalTicket.clickToDynamicButton("OK");
	}

	@Test
	public void TC_72_KiemTraChonDiemDenGiongDiemDi() {
		log.info("TC_72_Step_1: Chọn và nhập điểm đi");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_72_Step_2: Chọn và nhập điểm đi");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linArival");

		log.info("TC_72_Step_3: kiểm tra hiển thị");
		String sugget_to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTen");
		vehicalTicket.clickToDynamicText(sugget_to);
	}

	@Test
	public void TC_73_KiemTraChonLaiDiemDiSauKhiChonDiemDen() {
		log.info("TC_73_Step_1: Chọn và nhập điểm đi");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_73_Step_2: Chọn và nhập điểm đến");
		vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_3, VehicalData.DATA_ORDER_TICKET.ARRIVAL);

		log.info("TC_73_Step_3: chỉnh sửa lại điểm đi");
		vehicalTicket.clickToDynamicTextBox("com.VCB:id/linPickUp");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_2, "com.VCB:id/linPickUp");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_2);

		log.info("TC_73_Step_4: kiểm tra hiển thị");
		String fromt = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextArrival");
		verifyEquals(fromt, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
	}

	@Test
	public void TC_74_KiemTraNhanDoiDiemDiDiemDen() {
		log.info("TC_74_Step_1: Chọn và nhập điểm đến");
		vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_3, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_3);

		log.info("TC_74_Step_2: click icon");
		vehicalTicket.clickToDynamicIconChangePlaceAndBack("com.VCB:id/ivround");

		log.info("TC_74_Step_3: kiểm tra hiển thị sau khi đổi vị trí");
		String fromt = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextPickUp");
		verifyEquals(fromt, VehicalData.DATA_ORDER_TICKET.PLACE_3);
		String to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextArrival");
		verifyEquals(to, VehicalData.DATA_ORDER_TICKET.PLACE_2);
	}

	@Test
	public void TC_75_KiemTraThongTinKhoiHanh() {
		log.info("TC_75_Step_1: lấy ngày đi");
		String date = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
		verifyEquals(date, today);

		log.info("TC_75_Step_2: kiểm tra button được focus");
		verifyTrue(vehicalTicket.isDynamicForcusAndPriceDisplay("com.VCB:id/lnToday"));
	}

	@Test
	public void TC_76_KiemTraChonNgayMai() {
		log.info("TC_76_Step_1: Chọn ngày mai");
		vehicalTicket.clickToDynamicTomorrowAndFilterTrip("com.VCB:id/lnNextday");

		log.info("TC_76_Step_2: kiểm tra ngày thay đổi đi");
		String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
		verifyEquals(DayStart, tommorrowDate);

		log.info("TC_76_Step_3: kiểm tra button được focus");
		verifyTrue(vehicalTicket.isDynamicForcusAndPriceDisplay("com.VCB:id/lnNextday"));
	}

	@Test
	public void TC_77_KiemTraChonNgayHomNay() {
		log.info("TC_77_Step_1: Chọn ngày hôm nay");
		vehicalTicket.clickToDynamicTomorrowAndFilterTrip("com.VCB:id/lnToday");

		log.info("TC_77_Step_2: kiểm tra ngày thay đổi đi");
		String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
		verifyEquals(DayStart, today);

		log.info("TC_77_Step_3: kiểm tra button được focus");
		verifyTrue(vehicalTicket.isDynamicForcusAndPriceDisplay("com.VCB:id/lnToday"));
	}

	@Test
	public void TC_78_KiemTraNhanChonThoiGian() {
		log.info("TC_78_Step_1: Chọn ngày");
		vehicalTicket.clickToDynamicButtonChoiseDate("com.VCB:id/tvMonth");

		log.info("TC_78_Step_2: kiểm tra ngày được focus là ngày hiện tại");
		verifyTrue(vehicalTicket.isDynamicForcusText(getCurrentDay()));

		log.info("TC_78_Step_3: kiểm tra ngày được focus là ngày hiện tại");
		vehicalTicket.clickToDynamicButtonBack("Chọn ngày đi");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();

	}

}
