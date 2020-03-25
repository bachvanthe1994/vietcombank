package vnpay.vietcombank.sdk_train_ticket;

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
import pageObjects.sdk.trainTicket.TrainTicketPageObject;
import vnpay.vietcombank.sdk_train_ticket_data.TrainTicket_Data;

public class Validation_TrainTicket_3 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private TrainTicketPageObject trainTicket;

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
		login.Global_login(phone, pass, opt);
		trainTicket = PageFactoryManager.getTrainTicketPageObject(driver);

		log.info("TC_00_Step_Click dat ve tau");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Đặt vé tàu");

		log.info("TC_00_Step_Click close message");
		trainTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_00_Check title dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("ĐẶT VÉ TÀU"));
	}

	@Test
	public void TC_01_KiemTraQuyDinhSoLuongKhachToiDa() {
		log.info("TC_01_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Hành khách");

		log.info("TC_01_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivIncrase");

		log.info("TC_01_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivIncrase");

		log.info("TC_01_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivIncrase");

		log.info("TC_01_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Người lớn", "com.VCB:id/ivIncrase");

		log.info("TC_01_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Người cao tuổi", "com.VCB:id/ivIncrase");

		log.info("TC_01_Verify so luong Sinh vien = 2");
		verifyEquals(trainTicket.getTextCustomerNumber("Sinh viên", "com.VCB:id/tvQuantity"), "2");

		log.info("TC_01_Verify so luong tre em = 1");
		verifyEquals(trainTicket.getTextCustomerNumber("Trẻ em", "com.VCB:id/tvQuantity"), "1");

		log.info("TC_01_Verify so luong nguoi lon = 1");
		verifyEquals(trainTicket.getTextCustomerNumber("Người lớn", "com.VCB:id/tvQuantity"), "1");

		log.info("TC_01_Da add du 4 hanh khach, hanh khach thu 5 la Nguoi cao tuoi se khong duoc add vao danh sach, verify so luong nguoi cao tuoi =0");
		verifyEquals(trainTicket.getTextCustomerNumber("Người cao tuổi", "com.VCB:id/tvQuantity"), "0");
	}

	@Test
	public void TC_02_KiemTraIconDongManHinhHanhKhach() {
		log.info("TC_02_Click so luon nguoi giam ve = 0 ");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Người lớn", "com.VCB:id/ivDecrase");

		log.info("TC_02_Click dong man hinh hanh khach");
		trainTicket.clickDynamicRadioSelectType("Số lượng hành khách");

		log.info("TC_02_Check title dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("ĐẶT VÉ TÀU"));
	}

	@Test
	public void TC_03_KiemTraChonSoLuongKhachHangThanhCong() {
		log.info("TC_03_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Hành khách");

		log.info("TC_03_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Người cao tuổi", "com.VCB:id/ivIncrase");

		log.info("TC_03_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivIncrase");

		log.info("TC_03_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivIncrase");

		log.info("TC_03_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Người lớn", "com.VCB:id/ivIncrase");

		String adult = trainTicket.getTextCustomerNumber("Người lớn", "com.VCB:id/tvQuantity") + " Người lớn, ";

		String child = trainTicket.getTextCustomerNumber("Trẻ em", "com.VCB:id/tvQuantity") + " Trẻ em, ";

		String student = trainTicket.getTextCustomerNumber("Sinh viên", "com.VCB:id/tvQuantity") + " Sinh viên, ";

		String old = trainTicket.getTextCustomerNumber("Người cao tuổi", "com.VCB:id/tvQuantity") + " Người cao tuổi";

		log.info("TC_03_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_03_Verify so luong hanh khach da chon man hinh dat ve tau");
		verifyEquals(trainTicket.getTextMessageInvalid("com.VCB:id/tv_hanh_khach"), adult + child + student + old);
	}

	@Test
	public void TC_04_KiemTraHienThiMacDinhComboboxGheNgoi() {
		log.info("TC_04_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Loại chỗ");

		log.info("TC_04_Verify Mac dinh ghe phu khong duoc check");
		verifyTrue(trainTicket.isDynamicIconUncheck("Ghế phụ", "com.VCB:id/ivCheck"));

		log.info("TC_04_Verify Mac dinh giường nằm khoang 6 cho khong duoc check");
		verifyTrue(trainTicket.isDynamicIconUncheck("Giường nằm (khoang 6 chỗ)", "com.VCB:id/ivCheck"));

		log.info("TC_04_Verify Mac dinh giường nằm khoang 4 khong duoc check");
		verifyTrue(trainTicket.isDynamicIconUncheck("Giường nằm (khoang 4 chỗ)", "com.VCB:id/ivCheck"));

		log.info("TC_04_Verify Mac dinh ngoi cung khong duoc check");
		verifyTrue(trainTicket.isDynamicIconUncheck("Ngồi cứng", "com.VCB:id/ivCheck"));

		log.info("TC_04_Verify Mac dinh ngoi mem khong duoc check");
		verifyTrue(trainTicket.isDynamicIconUncheck("Ngồi mềm", "com.VCB:id/ivCheck"));

		log.info("TC_04_Click dong man hinh hanh khach");
		trainTicket.clickDynamicRadioSelectType("Chọn loại chỗ");
	}

	@Test
	public void TC_05_BoTrongKhongChonGheNgoi() {
		log.info("TC_05_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd("ĐẶT VÉ TÀU", "com.VCB:id/tvTextPickUp");

		log.info("TC_05_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/linPickUp");

		log.info("TC_05_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_05_Nhap text ga den");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END, "com.VCB:id/linArival");

		log.info("TC_05_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_05_Click button tiep tuc");
		trainTicket.clickToDynamicButton("TIẾP TỤC");

		log.info("TC_05_Verify message");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tvTitle"), TrainTicket_Data.message.LOCATION_BLANK);
	}

	@Test
	public void TC_06_FocusVaoComboboxGheNgoi() {
		log.info("TC_06_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Loại chỗ");

		log.info("TC_06_Hien thi icon x");
		verifyTrue(trainTicket.isDynamicImageSuccess("Chọn loại chỗ"));

		log.info("TC_06_Hien thi button xong");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Xong"));

		log.info("TC_06_Hien thi Checkbox");
		verifyTrue(trainTicket.isDynamicRadioDisplayed("com.VCB:id/ivCheckAll"));

		log.info("TC_06_Hien thi Chon tat ca");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Chọn tất cả"));

		log.info("TC_06_Hien thi ghe phu");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Ghế phụ"));

		log.info("TC_06_Hien thi giuong nam khoang 6 cho");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Giường nằm (khoang 6 chỗ)"));

		log.info("TC_06_Hien thi giuong nam khoang 4 cho");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Giường nằm (khoang 4 chỗ)"));

		log.info("TC_06_Hien thi ngoi cung");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Ngồi cứng"));

		log.info("TC_06_Hien thi ngoi mem");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Ngồi mềm"));
	}

	@Test
	public void TC_07_KiemTraNhanIconDong() {
		log.info("TC_07_Click dong man hinh chon loai cho");
		trainTicket.clickDynamicRadioSelectType("Chọn loại chỗ");

		log.info("TC_07_Check title dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("ĐẶT VÉ TÀU"));
	}

	@Test
	public void TC_08_KiemTraChon1LoaiGheThanhCong() {
		log.info("TC_08_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Loại chỗ");

		log.info("TC_08_Click chon ghe la ngoi cung");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Ngồi cứng");

		log.info("TC_08_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_08_Verify ghe ngoi cung duoc chon hien thi trong phan loai cho");
		verifyEquals(trainTicket.getTextMessageInvalid("com.VCB:id/tv_ghe_ngoi"), "Ngồi cứng");
	}

	@Test
	public void TC_09_KiemTraChonLonHon1LoaiGhe() {
		log.info("TC_09_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Loại chỗ");

		log.info("TC_09_Click chon ghe la ngoi mềm");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Ngồi mềm");

		log.info("TC_09_Click chon ghe la ghe phu");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Ghế phụ");

		log.info("TC_09_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_09_Verify ghe ngoi cung duoc chon hien thi trong phan loai cho");
		verifyEquals(trainTicket.getTextMessageInvalid("com.VCB:id/tv_ghe_ngoi"), "Ghế phụ, Ngồi cứng, Ngồi mềm");
	}

	@Test
	public void TC_10_KiemTraThaoTacTimKiemThanhCong() {
		log.info("TC_10_Click button tiep tuc");
		trainTicket.clickToDynamicButton("TIẾP TỤC");

		log.info("TC_10_Verify hien thi man hinh danh sach chieu di khi tim kiem ve tau co ket qua");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tvTitle1"), "Danh sách chiều đi");
	}

	@Test
	public void TC_11_KiemTraThaoTacTimKiemKhongThanhCong() {
		log.info("TC_11_Click button quay lai man hinh dat ve tau");
		trainTicket.clickToDynamicBackIcon("Danh sách chiều đi");

		log.info("TC_11_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Loại chỗ");

		log.info("TC_11_Bo chon ghe la ngoi cung");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Ngồi cứng");

		log.info("TC_11_Bo chon ghe la ngoi mềm");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Ngồi mềm");

		log.info("TC_11_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_11_Click button tiep tuc");
		trainTicket.clickToDynamicButton("TIẾP TỤC");

		log.info("TC_11_Verify message khong tim kiem duoc ket qua");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tvTitle"), "Không có chuyến đi phù hợp. Quý khách vui lòng chọn chuyến đi khác.");

		log.info("TC_11_Click button tiep tuc");
		trainTicket.clickToDynamicButton("ĐỒNG Ý");
	}

	@Test
	public void TC_12_KiemTraManHinhDanhSachChuyenDi() {
		LocalDate now = LocalDate.now();
		LocalDate date3 = now.plusDays(3);

		log.info("TC_12_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Loại chỗ");

		log.info("TC_12_Click radio chon tat ca");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Chọn tất cả");

		log.info("TC_12_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_12_Click button tiep tuc");
		trainTicket.clickToDynamicButton("TIẾP TỤC");

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
		verifyTrue(trainTicket.isDynamicNameTrainDisplay( "0","com.VCB:id/tv_ten_tau"));

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
	public void TC_13_KiemTraIconBack() {
		log.info("TC_13_Click button quay lai man hinh dat ve tau");
		trainTicket.clickToDynamicBackIcon("Danh sách chiều đi");

		log.info("TC_13_Check title dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("ĐẶT VÉ TÀU"));
	}

	@Test
	public void TC_14_KiemTraThongTinHanhTrinh() {
		LocalDate now = LocalDate.now();
		LocalDate date3 = now.plusDays(3);

		log.info("TC_14_Click button tiep tuc");
		trainTicket.clickToDynamicButton("TIẾP TỤC");

		log.info("TC_14_Verify text gio chay");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_gio_khoi_hanh"), "Giờ chạy");

		log.info("TC_14_Verify text thoi gian chay");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_thoi_gian_chay"), "Thời gian chạy");

		String weekPickup = convertDayOfWeekVietNamese(getCurrentDayOfWeek(now));
		String weekArrival = convertDayOfWeekVietNamese(getCurrentDayOfWeek(date3));
		String expectDay = weekPickup + " " + getForwardDate(0) + " - " + weekArrival + " " + getForwardDate(3);

		log.info("TC_14_verify thoi gian khu hoi");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_date_title"), expectDay);

		log.info("TC_14_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_14_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}
}
