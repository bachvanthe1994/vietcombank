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

public class VehicalTicket_Validate_Part1 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private VehicalPageObject vehicalTicket;
	LocalDate now = LocalDate.now();
	String today = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();

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
	public void TC_02_KiemTraManHinhTimKiemChuyenDi() {
		log.info("TC_02_Step_1: kiểm tra hiển thị title màn hình");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(driver, VehicalData.DATA_ORDER_TICKET.ORDER_TICKET));

		log.info("TC_02_Step_2: kiểm tra hiển thị icon back");
		verifyTrue(vehicalTicket.isDynamicIconBackDisplayed(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET));

		log.info("TC_02_Step_3: kiểm tra hiển thị lịch sử đặt vé");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(driver, VehicalData.DATA_ORDER_TICKET.HISTORY_TICKET));

		log.info("TC_02_Step_4: kiểm tra hiển thị chọn điểm đi");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(driver, VehicalData.DATA_ORDER_TICKET.FROMT));

		log.info("TC_02_Step_5: kiểm tra hiển thị chọn điểm đến");
		vehicalTicket.isDynamicMessageAndLabelTextDisplayed(driver, VehicalData.DATA_ORDER_TICKET.TO);

		log.info("TC_02_Step_6: kiểm tra hiển thị icon thay đổi điểm đi điểm đến");
		vehicalTicket.isDynamicIconChangePlaceAndBackAndFinndDisplayed("com.VCB:id/ivround");

		log.info("TC_02_Step_7: kiểm tra hiển thị thơi gian khởi hành");
		String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
		verifyEquals(DayStart, today);

		log.info("TC_02_Step_8: kiểm tra hiển thị ngày hôm nay");
		String today = vehicalTicket.getDynamicDayStart("com.VCB:id/tvHomnay");
		verifyEquals(today, VehicalData.DATA_ORDER_TICKET.TODAY);

		log.info("TC_02_Step_9: kiểm tra hiển thị ngày mai");
		String tomorrow = vehicalTicket.getDynamicDayStart("com.VCB:id/tvNgayMai");
		verifyEquals(tomorrow, VehicalData.DATA_ORDER_TICKET.TOMORROW);

		log.info("TC_02_Step_10: kiểm tra hiển thị button tìm kiếm chuyến đi");
		verifyTrue(vehicalTicket.isDynamicButtonDisplayed(driver, VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP));
	}

	@Test
	public void TC_03_KiemTraManHinhTimKiemChuyenDiKhachHangSuDungLanSau() {
		log.info("TC_03_Step_1: kiểm tra hiển thị text gợi ý chuyến đi");
		vehicalTicket.isDynamicMessageAndLabelTextDisplayed(driver, VehicalData.DATA_ORDER_TICKET.SUGGEST_TRIP);

		log.info("TC_03_Step_2: kiểm tra hiển thị gợi ý chuyến đi");
		String suggest_from = vehicalTicket.getDynamicDayStart("com.VCB:id/tvFrom");

		log.info("TC_03_Step_3: kiểm tra điểm đi trong gợi ý");
		String from = suggest_from.substring(12);
		verifyEquals(from, VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_03_Step_4: kiểm tra điểm tới trong gợi ý");
		String to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTo");
		verifyEquals(to, VehicalData.DATA_ORDER_TICKET.PLACE_3);
	}

	@Test
	public void TC_04_KiemTraClickBack() {
		log.info("TC_04_Step_1: click chọn icon");
		vehicalTicket.clickToDynamicButtonBack("Đặt vé xe");

		log.info("TC_04_Step_2: kiểm tra hiển thị thoát màn hình chính");
		vehicalTicket.isDynamicMessageAndLabelTextDisplayed(driver, VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);

		log.info("TC_04_Step_3: click chọn đặt vé xe");
		vehicalTicket.clickToDynamicText("Đặt vé xe");

		vehicalTicket.clickToDynamicButton("Đồng ý");
	}

	@Test
	public void TC_05_KiemTraTextBoxDiemDi() {
		log.info("TC_05_Step_1: kiểm tra textbox");
		String choise_from = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextPickUp");
		verifyEquals(choise_from, VehicalData.DATA_ORDER_TICKET.FROMT);
	}

	@Test
	public void TC_06_KiemTraDeTrongDiemDi() {
		log.info("TC_06_Step_1: click chọn tìm kiếm chuyến đi");
		vehicalTicket.clickToDynamicButton(driver, VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP);

		log.info("TC_06_Step_2: kiểm tra thông báo chọn điểm đi");
		String noti_fromt = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTitle");
		verifyEquals(noti_fromt, VehicalData.NOTIFICATION.NOTI_CHOISE_FROMT);

		log.info("TC_06_Step_3: chọn đồng ý");
		vehicalTicket.clickToDynamicButton(driver, "ĐỒNG Ý");
	}

	@Test
	public void TC_07_KiemTraSoKiTuDuocNhapVaoDiemDi() {
		log.info("TC_07_Step_1: điền 201 ki tự vào chọn điểm đi");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.LENGTH_201, VehicalData.DATA_ORDER_TICKET.DESTINATION);

		log.info("TC_07_Step_2: kiểm tra max lenght 200");
		String lenght_201 = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextPickUp");
		verifyEquals(lenght_201, VehicalData.DATA_ORDER_TICKET.LENGTH_200);

		log.info("TC_07_Step_3: chọn đóng");
		vehicalTicket.clickToDynamicButtonIconBack("com.VCB:id/ivClose");
	}

	@Test
	public void TC_08_KiemTraLoaiKiTuDuocNhap() {
		log.info("TC_08_Step_1: nhập kí tự hợp lệ");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.DATA_INPUT_VN, "com.VCB:id/linPickUp");

		log.info("TC_08_Step_2: kiểm tra hiển thị các kí tự vừa nhập");
		String data_invalid = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextPickUp");
		verifyEquals(data_invalid, VehicalData.DATA_ORDER_TICKET.DATA_INPUT_VN);

		log.info("TC_08_Step_3: chọn đóng");
		vehicalTicket.clickToDynamicButtonIconBack("com.VCB:id/ivClose");

		log.info("TC_08_Step_4: nhập kí tự không hợp lệ");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.DATA_INPUT_SPECCIAL, "com.VCB:id/linPickUp");

		log.info("TC_08_Step_5: kiểm tra hiển thị các kí tự vừa nhập");
		String data_valid = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextPickUp");
		verifyEquals(data_valid, VehicalData.DATA_ORDER_TICKET.DESTINATION);

		log.info("TC_08_Step_6: chọn đóng");
		vehicalTicket.clickToDynamicButtonIconBack("com.VCB:id/ivClose");
	}

	@Test
	public void TC_09_NhapDiemDiHopLe() {
		log.info("TC_09_Step_1: nhập và chọn điểm đi");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_09_Step_2: kiểm tra hiển thị trên textbox");
		String fromt = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextPickUp");
		verifyEquals(fromt, VehicalData.DATA_ORDER_TICKET.PLACE_1);
	}

	@Test
	public void TC_10_KiemTraTimKiemTuongDoi() {
		log.info("TC_10_Step_1: nhập và chọn điểm đi");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.CONTENT_DEPARTURE, "com.VCB:id/linPickUp");

		log.info("TC_10_Step_2: lấy vị trí được sugget để so sánh");
		String sugget_fromt = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTen");
		verifyTrue(sugget_fromt.contains(VehicalData.DATA_ORDER_TICKET.CONTENT_DEPARTURE));
	}

	@Test
	public void TC_11_KiemTraSuaDiemDi() {
		log.info("TC_11_Step_1: nhập và chọn điểm đi");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");

		log.info("TC_11_Step_2: chỉnh sửa lại điểm đi");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_3, "com.VCB:id/linPickUp");

		log.info("TC_11_Step_3: kiểm tra tìm kiếm tương đối");
		String sugget_to = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTen");
		verifyTrue(sugget_to.contains(VehicalData.DATA_ORDER_TICKET.PLACE_3));
	}

	@Test
	public void TC_12_KiemTraGiaTriNhapVaoDiemDi() {
		log.info("TC_12_Step_1: nhập và chọn điểm đi");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");

		log.info("TC_12_Step_2: kiểm tra tìm kiếm tương đối");
		String sugget_fromt = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTen");
		verifyTrue(sugget_fromt.contains(VehicalData.DATA_ORDER_TICKET.PLACE_1));
	}

	@Test
	public void TC_13_KiemTraDiemDiKhongTonTai() {
		log.info("TC_13_Step_1: Chọn và nhập điểm đi");
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.DESTINATION_INVALID, "com.VCB:id/linPickUp");

		log.info("TC_13_Step_3: kiểm tra thông báo");
		String nullData = vehicalTicket.getDynamicConfirmNullData("com.VCB:id/rlNullData");
		verifyEquals(nullData, VehicalData.NOTIFICATION.NOTI_NULL_DATA);

		log.info("TC_07_Step_3: chọn đóng");
		vehicalTicket.clickToDynamicButtonIconBack("com.VCB:id/ivClose");
	}

	@Test
	public void TC_14_KiemTraTextBoxDiemDen() {
		log.info("TC_14_Step_1: get text");
		String choise_from = vehicalTicket.getDynamicDayStart("com.VCB:id/tvTextArrival");

		log.info("TC_14_Step_2: kiểm tra hiển thị");
		verifyEquals(choise_from, VehicalData.DATA_ORDER_TICKET.TO);
	}

	@Test
	public void TC_15_KiemTraSoKiTuDuocPhepNhapVaoDiemDen() {
		log.info("TC_15_Step_2: điền 201 ki tự vào chọn điểm đến");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TO);
		vehicalTicket.inputToDynamicInputBox(driver, VehicalData.DATA_ORDER_TICKET.LENGTH_201, VehicalData.DATA_ORDER_TICKET.ARRIVAL);

		log.info("TC_15_Step_2: kiểm tra max lenght 200");
		String lenght_201 = vehicalTicket.getDynamicEditText("com.VCB:id/edtTextArrival");
		verifyEquals(lenght_201, VehicalData.DATA_ORDER_TICKET.LENGTH_200);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();

	}

}
