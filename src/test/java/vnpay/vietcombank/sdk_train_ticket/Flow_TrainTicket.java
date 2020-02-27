package vnpay.vietcombank.sdk_train_ticket;

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
import pageObjects.sdk.trainTicket.TrainTicketPageObject;
import vnpay.vietcombank.sdk_train_ticket_data.TrainTicket_Data;

public class Flow_TrainTicket extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TrainTicketPageObject trainTicket;
	private String currentDay = getCurrentDay();

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		trainTicket = PageFactoryManager.getTrainTicketPageObject(driver);
	}

	@Test
	public void TC_01_KiemTraTimKiemThanhCongDatVeTau() {
		log.info("TC_01_Step_Click dat ve tau");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Đặt vé tàu");

		log.info("TC_01_Step_Click close message");
		trainTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_01_Check title dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("ĐẶT VÉ TÀU"));

		log.info("TC_01_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd("ĐẶT VÉ TÀU", "com.VCB:id/tvTextPickUp");

		log.info("TC_01_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/linPickUp");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Nhap text ga den");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END, "com.VCB:id/linArival");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_08_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate("com.VCB:id/tv_ngay_di");

		String EndDay = getForWardDay(3);

		log.info("TC_08_Chon ngay di la ngay hien tai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(0), currentDay);

		log.info("TC_08_Chon ngay ve la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(3), EndDay);

		log.info("TC_12_Click button tiep tuc");
		trainTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_17_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Hành khách");

		log.info("TC_21_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivIncrase");

		log.info("TC_21_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivIncrase");

		log.info("TC_21_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_17_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Loại chỗ");

		log.info("TC_17_Click radio chon tat ca");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Chọn tất cả");

		log.info("TC_21_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_12_Click button tiep tuc");
		trainTicket.clickToDynamicButton("TIẾP TỤC");

		log.info("TC_10_Verify hien thi man hinh danh sach chieu di khi tim kiem ve tau co ket qua");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tvTitle1"), "Danh sách chiều đi");
	}

	@Test
	public void TC_02_KiemTraThongTinHanhTrinhDanhSachChonChuyenDi() {
		LocalDate now = LocalDate.now();
		LocalDate date3 = now.plusDays(3);

		log.info("TC_12_Lay gia tri ten tau duoc tra ve ket qua tim kiem");
		trainTicket.getDynamicList("com.VCB:id/tv_ten_tau");

		log.info("TC_12_Verify hien thi man hinh danh sach chieu di khi tim kiem ve tau co ket qua");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tvTitle1"), "Danh sách chiều đi");

		log.info("TC_12_Hien thi button back");
		verifyTrue(trainTicket.isDynamicRadioDisplayed("com.VCB:id/ivTitleLeft"));

		log.info("TC_12_Hien thi button home");
		verifyTrue(trainTicket.isDynamicRadioDisplayed("com.VCB:id/ivTitleRight"));

		log.info("TC_12_Verify text gio chay");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_gio_khoi_hanh"), "Giờ chạy");

		log.info("TC_12_Verify text thoi gian chay");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_thoi_gian_chay"), "Thời gian chạy");

		log.info("TC_12_Verify button sua");
		verifyTrue(trainTicket.isDynamicButtonEditDisplay("com.VCB:id/btnEdit"));

		String weekPickup = convertDayOfWeekVietNamese(getCurrentDayOfWeek(now));
		String weekArrival = convertDayOfWeekVietNamese(getCurrentDayOfWeek(date3));
		String expectDay = weekPickup + " " + getForwardDate(0) + " - " + weekArrival + " " + getForwardDate(3);

		log.info("TC_12_verify thoi gian khu hoi");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_date_title"), expectDay);

		log.info("TC_12_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_12_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_12_Check hien thi ten tau");
		verifyTrue(trainTicket.isDynamicNameTrainDisplay("com.VCB:id/tv_ten_tau", "0"));

		log.info("TC_12_Check hien thi thoi gian bat dau chay");
		verifyTrue(trainTicket.isDynamicDateTimeDisplay("com.VCB:id/tv_thoi_di"));

		String timeStart = trainTicket.getDynamicDateTime("com.VCB:id/tv_thoi_di") + " - " + getForwardDate(0);

		log.info("TC_12_Check hien thi thoi gian den");
		String timeEnd = trainTicket.getDynamicDateTime("com.VCB:id/tv_thoi_gian_den") + trainTicket.getDynamicDateTime("com.VCB:id/tv_ngay_den");

		String expectTime = trainTicket.getDuration(timeStart, timeEnd);

		log.info("TC_12_Verify tong thoi gian di chuyen");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_tong_thoi_gian"), expectTime);
	}
	
	@Test
	public void TC_03_KiemTraThaoTacSuaChieuDiThanhCong() {
		String startDay = getForWardDay(2);
		String EndDay = getForWardDay(5);
		
		LocalDate now = LocalDate.now();
		LocalDate date2 = now.plusDays(2);
		LocalDate date5 = now.plusDays(5);
		log.info("TC_07_Click button sua");
		trainTicket.clickToDynamicButton("Sửa");

		log.info("TC_09_Click ga khoi hanh");
		trainTicket.clickToDynamicSelectDate("com.VCB:id/tvTextPickUp");
		
		log.info("TC_01_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/linPickUp");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Nhap text ga den");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_CHANGE, "com.VCB:id/linArival");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_CHANGE);
		
		log.info("TC_08_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate("com.VCB:id/tv_ngay_di");

		log.info("TC_08_Chon ngay di la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(2), startDay);

		log.info("TC_08_Chon ngay ve la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(5), EndDay);
		
		log.info("TC_12_Click button tiep tuc");
		trainTicket.clickToDynamicButton("Tiếp tục");
		
		log.info("TC_07_Click button ap dung");
		trainTicket.clickToDynamicButton("ÁP DỤNG");
		
		String weekPickup = convertDayOfWeekVietNamese(getCurrentDayOfWeek(date2));
		String weekArrival = convertDayOfWeekVietNamese(getCurrentDayOfWeek(date5));
		String expectDay = weekPickup + " " + getForwardDate(2) + " - " + weekArrival + " " + getForwardDate(5);

		log.info("TC_12_verify thoi gian khu hoi");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_date_title"), expectDay);

		log.info("TC_12_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_12_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to"), TrainTicket_Data.inputText.POINT_EDIT_CHANGE);
	}
	
	@Test
	public void TC_04_KiemTraThaoTacSuaChieuVeThanhCong() {
		
		
		LocalDate now = LocalDate.now();
		LocalDate date3 = now.plusDays(3);
		
		

		log.info("TC_12_Lay gia tri ten tau duoc tra ve ket qua tim kiem");
		trainTicket.getDynamicList("com.VCB:id/tv_ten_tau");

		log.info("TC_12_Verify hien thi man hinh danh sach chieu di khi tim kiem ve tau co ket qua");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tvTitle1"), "Danh sách chiều đi");

		log.info("TC_12_Hien thi button back");
		verifyTrue(trainTicket.isDynamicRadioDisplayed("com.VCB:id/ivTitleLeft"));

		log.info("TC_12_Hien thi button home");
		verifyTrue(trainTicket.isDynamicRadioDisplayed("com.VCB:id/ivTitleRight"));

		log.info("TC_12_Verify text gio chay");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_gio_khoi_hanh"), "Giờ chạy");

		log.info("TC_12_Verify text thoi gian chay");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_thoi_gian_chay"), "Thời gian chạy");

		log.info("TC_12_Verify button sua");
		verifyTrue(trainTicket.isDynamicButtonEditDisplay("com.VCB:id/btnEdit"));

		String weekPickup = convertDayOfWeekVietNamese(getCurrentDayOfWeek(now));
		String weekArrival = convertDayOfWeekVietNamese(getCurrentDayOfWeek(date3));
		String expectDay = weekPickup + " " + getForwardDate(0) + " - " + weekArrival + " " + getForwardDate(3);

		log.info("TC_12_verify thoi gian khu hoi");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_date_title"), expectDay);

		log.info("TC_12_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_12_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_12_Check hien thi ten tau");
		verifyTrue(trainTicket.isDynamicNameTrainDisplay("com.VCB:id/tv_ten_tau", "0"));

		log.info("TC_12_Check hien thi thoi gian bat dau chay");
		verifyTrue(trainTicket.isDynamicDateTimeDisplay("com.VCB:id/tv_thoi_di"));

		String timeStart = trainTicket.getDynamicDateTime("com.VCB:id/tv_thoi_di") + " - " + getForwardDate(0);

		log.info("TC_12_Check hien thi thoi gian den");
		String timeEnd = trainTicket.getDynamicDateTime("com.VCB:id/tv_thoi_gian_den") + trainTicket.getDynamicDateTime("com.VCB:id/tv_ngay_den");

		String expectTime = trainTicket.getDuration(timeStart, timeEnd);

		log.info("TC_12_Verify tong thoi gian di chuyen");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_tong_thoi_gian"), expectTime);
}
}
