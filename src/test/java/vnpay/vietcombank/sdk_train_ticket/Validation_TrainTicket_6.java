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

public class Validation_TrainTicket_6 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private TrainTicketPageObject trainTicket;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		trainTicket = PageFactoryManager.getTrainTicketPageObject(driver);

		log.info("TC_00_Step_Click dat ve tau");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Đặt vé tàu");

		log.info("TC_00_Step_Click close message");
		trainTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_00_Check title dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("ĐẶT VÉ TÀU"));

		log.info("TC_00_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd("ĐẶT VÉ TÀU", "com.VCB:id/tvTextPickUp");

		log.info("TC_00_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/linPickUp");

		log.info("TC_00_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_00_Nhap text ga den");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END, "com.VCB:id/linArival");

		log.info("TC_00_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_00_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate("com.VCB:id/tv_ngay_di");

		String nextDay = getForWardDay(4);
		String EndDay = getForWardDay(7);

		log.info("TC_00_Chon ngay di la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(4), nextDay);

		log.info("TC_00_Chon ngay ve la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(7), EndDay);

		log.info("TC_00_Click button tiep tuc");
		trainTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_00_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Hành khách");

		log.info("TC_00_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivIncrase");

		log.info("TC_00_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivIncrase");

		log.info("TC_00_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_00_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Loại chỗ");

		log.info("TC_00_Click radio chon tat ca");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Chọn tất cả");

		log.info("TC_00_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_00_Click button tiep tuc");
		trainTicket.clickToDynamicButton("TIẾP TỤC");

		log.info("TC_00_Click button sua");
		trainTicket.clickToDynamicButton("Sửa");
	}

	@Test
	public void TC_01_KiemTraHienThiMacDinhComboboxHanhKhach() {
		log.info("TC_01_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Hành khách");

		String child = trainTicket.getTextCustomerNumber("Trẻ em", "com.VCB:id/tvQuantity") + " Trẻ em, ";

		String student = trainTicket.getTextCustomerNumber("Sinh viên", "com.VCB:id/tvQuantity") + " Sinh viên";

		log.info("TC_01_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_01_Verify so luong hanh khach da chon man hinh dat ve tau");
		verifyEquals(trainTicket.getTextMessageInvalid("com.VCB:id/tv_hanh_khach"), child + student);
	}

	@Test
	public void TC_02_FocusComboboxHanhKhach() {
		log.info("TC_02_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Hành khách");

		log.info("TC_02_Hien thi icon x");
		verifyTrue(trainTicket.isDynamicImageSuccess("Số lượng hành khách"));

		log.info("TC_02_Hien thi button xong");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Xong"));

		log.info("TC_02_Hien thi nguoi lon");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Người lớn"));

		log.info("TC_02_Hien thi button giam");
		verifyTrue(trainTicket.isDynamicIconChangeNumber("Người lớn", "com.VCB:id/ivDecrase"));

		log.info("TC_02_Hien thi so luong nguoi mac dinh la 0");
		verifyTrue(trainTicket.isDynamicTextChangeNumber("Người lớn", "com.VCB:id/tvQuantity"));

		log.info("TC_02_Hien thi button tang");
		verifyTrue(trainTicket.isDynamicIconChangeNumber("Người lớn", "com.VCB:id/ivIncrase"));

		log.info("TC_02_Hien thi tre em");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Trẻ em"));

		log.info("TC_02_Hien thi button giam");
		verifyTrue(trainTicket.isDynamicIconChangeNumber("Trẻ em", "com.VCB:id/ivDecrase"));

		log.info("TC_02_Hien thi so luong nguoi mac dinh la 0");
		verifyTrue(trainTicket.isDynamicTextChangeNumber("Trẻ em", "com.VCB:id/tvQuantity"));

		log.info("TC_02_Hien thi so luong tre");
		verifyEquals(trainTicket.getDynamicTextOld("Trẻ em"), "0-10 tuổi");

		log.info("TC_02_Hien thi button tang");
		verifyTrue(trainTicket.isDynamicIconChangeNumber("Trẻ em", "com.VCB:id/ivIncrase"));

		log.info("TC_02_Hien thi sinh vien");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Sinh viên"));

		log.info("TC_02_Hien thi button giam");
		verifyTrue(trainTicket.isDynamicIconChangeNumber("Sinh viên", "com.VCB:id/ivDecrase"));

		log.info("TC_02_Hien thi so luong nguoi mac dinh la 0");
		verifyTrue(trainTicket.isDynamicTextChangeNumber("Sinh viên", "com.VCB:id/tvQuantity"));

		log.info("TC_02_Hien thi button tang");
		verifyTrue(trainTicket.isDynamicIconChangeNumber("Sinh viên", "com.VCB:id/ivIncrase"));

		log.info("TC_02_Hien thi Người cao tuổi");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Người cao tuổi"));

		log.info("TC_02_Hien thi tu 60 tuoi");
		verifyEquals(trainTicket.getDynamicTextOld("Người cao tuổi"), "Từ 60 tuổi");

		log.info("TC_02_Hien thi button giam");
		verifyTrue(trainTicket.isDynamicIconChangeNumber("Người cao tuổi", "com.VCB:id/ivDecrase"));

		log.info("TC_02_Hien thi so luong nguoi mac dinh la 0");
		verifyTrue(trainTicket.isDynamicTextChangeNumber("Người cao tuổi", "com.VCB:id/tvQuantity"));

		log.info("TC_02_Hien thi button tang");
		verifyTrue(trainTicket.isDynamicIconChangeNumber("Người cao tuổi", "com.VCB:id/ivIncrase"));

		log.info("TC_02_Hien thi title note");
		verifyEquals(trainTicket.getTextInDynamicNote("4"), TrainTicket_Data.message.NOTE_NUMBER_CUSTOMER);
	}

	@Test
	public void TC_03_KiemTraThayDoiSoLuongNguoiLon() {
		log.info("TC_03_Click so luon nguoi lon giam ve = 0 ");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivDecrase");

		log.info("TC_03_Hien thi so luong nguoi mac dinh la 0");
		verifyTrue(trainTicket.isDynamicTextChangeNumber("Người lớn", "com.VCB:id/tvQuantity"));

		log.info("TC_03_Click so luon nguoi giam di 1, khong thuc hien duoc do so luong min = 0");
		trainTicket.clickDynamicButtonNumber("Người lớn", "com.VCB:id/ivDecrase");

		log.info("TC_03_Hien thi so luong nguoi la 0");
		verifyEquals(trainTicket.getTextCustomerNumber("Người lớn", "com.VCB:id/tvQuantity"), "0");

		log.info("TC_03_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Người lớn", "com.VCB:id/ivIncrase");

		log.info("TC_03_Hien thi so luong nguoi la 1");
		verifyEquals(trainTicket.getTextCustomerNumber("Người lớn", "com.VCB:id/tvQuantity"), "1");

		log.info("TC_03_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Người lớn", "com.VCB:id/ivIncrase");

		log.info("TC_03_Hien thi so luong nguoi la 2");
		verifyEquals(trainTicket.getTextCustomerNumber("Người lớn", "com.VCB:id/tvQuantity"), "2");

		log.info("TC_03_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Người lớn", "com.VCB:id/ivIncrase");

		log.info("TC_03_Hien thi so luong nguoi la 3");
		verifyEquals(trainTicket.getTextCustomerNumber("Người lớn", "com.VCB:id/tvQuantity"), "3");

		log.info("TC_03_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Người lớn", "com.VCB:id/ivIncrase");

		log.info("TC_03_Hien thi so luong nguoi la 4");
		verifyEquals(trainTicket.getTextCustomerNumber("Người lớn", "com.VCB:id/tvQuantity"), "4");

		log.info("TC_03_Click so luon nguoi tang len 1 don vi, so luon khong tang do max = 4");
		trainTicket.clickDynamicButtonNumber("Người lớn", "com.VCB:id/ivIncrase");

		log.info("TC_03_Hien thi so luong nguoi la 4");
		verifyEquals(trainTicket.getTextCustomerNumber("Người lớn", "com.VCB:id/tvQuantity"), "4");
	}

	@Test
	public void TC_04_KiemTraRangBuocLoaiKhachHang() {
		log.info("TC_04_Click so luon nguoi lon giam ve = 0 ");
		trainTicket.clickDynamicButtonNumber("Người lớn", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Người lớn", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Người lớn", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Người lớn", "com.VCB:id/ivDecrase");

		log.info("TC_04_Click chon so luong tre em tang len 1");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivIncrase");

		log.info("TC_04_Verify message dieu kien khong phu hop");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tvTitle"), TrainTicket_Data.message.ONLY_SELECT_NUMBER_CHILD);

		log.info("TC_04_Click button dong y");
		trainTicket.clickToDynamicButton("ĐỒNG Ý");
	}

	@Test
	public void TC_05_KiemTraThayDoiSoLuongTreEm() {
		log.info("TC_05_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Người lớn", "com.VCB:id/ivIncrase");

		log.info("TC_05_Hien thi so luong nguoi mac dinh la 0");
		verifyTrue(trainTicket.isDynamicTextChangeNumber("Trẻ em", "com.VCB:id/tvQuantity"));

		log.info("TC_05_Click so luon nguoi giam di 1, khong thuc hien duoc do so luong min = 0");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivDecrase");

		log.info("TC_05_Hien thi so luong nguoi la 0");
		verifyEquals(trainTicket.getTextCustomerNumber("Trẻ em", "com.VCB:id/tvQuantity"), "0");

		log.info("TC_05_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivIncrase");

		log.info("TC_05_Hien thi so luong nguoi la 1");
		verifyEquals(trainTicket.getTextCustomerNumber("Trẻ em", "com.VCB:id/tvQuantity"), "1");

		log.info("TC_05_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivIncrase");

		log.info("TC_05_Hien thi so luong nguoi la 2");
		verifyEquals(trainTicket.getTextCustomerNumber("Trẻ em", "com.VCB:id/tvQuantity"), "2");

		log.info("TC_05_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivIncrase");

		log.info("TC_05_Hien thi so luong nguoi la 3");
		verifyEquals(trainTicket.getTextCustomerNumber("Trẻ em", "com.VCB:id/tvQuantity"), "3");

		log.info("TC_05_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivIncrase");

		log.info("TC_05_Hien thi so luong nguoi la 3");
		verifyEquals(trainTicket.getTextCustomerNumber("Trẻ em", "com.VCB:id/tvQuantity"), "3");
	}

	@Test
	public void TC_06_KiemTraThayDoiSoLuongSinhVien() {
		log.info("TC_06_Click so luon nguoi giam ve = 0 ");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Người lớn", "com.VCB:id/ivDecrase");

		log.info("TC_06_Hien thi so luong nguoi mac dinh la 0");
		verifyTrue(trainTicket.isDynamicTextChangeNumber("Sinh viên", "com.VCB:id/tvQuantity"));

		log.info("TC_06_Click so luon nguoi giam di 1, khong thuc hien duoc do so luong min = 0");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivDecrase");

		log.info("TC_06_Hien thi so luong nguoi la 0");
		verifyEquals(trainTicket.getTextCustomerNumber("Sinh viên", "com.VCB:id/tvQuantity"), "0");

		log.info("TC_06_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivIncrase");

		log.info("TC_06_Hien thi so luong nguoi la 1");
		verifyEquals(trainTicket.getTextCustomerNumber("Sinh viên", "com.VCB:id/tvQuantity"), "1");

		log.info("TC_06_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivIncrase");

		log.info("TC_06_Hien thi so luong nguoi la 2");
		verifyEquals(trainTicket.getTextCustomerNumber("Sinh viên", "com.VCB:id/tvQuantity"), "2");

		log.info("TC_06_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivIncrase");

		log.info("TC_06_Hien thi so luong nguoi la 3");
		verifyEquals(trainTicket.getTextCustomerNumber("Sinh viên", "com.VCB:id/tvQuantity"), "3");

		log.info("TC_06_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivIncrase");

		log.info("TC_06_Hien thi so luong nguoi la 4");
		verifyEquals(trainTicket.getTextCustomerNumber("Sinh viên", "com.VCB:id/tvQuantity"), "4");

		log.info("TC_06_Click so luon nguoi tang len 1 don vi, so luon khong tang do max = 4");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivIncrase");

		log.info("TC_06_Hien thi so luong nguoi la 4");
		verifyEquals(trainTicket.getTextCustomerNumber("Sinh viên", "com.VCB:id/tvQuantity"), "4");
	}

	@Test
	public void TC_07_KiemTraThayDoiSoLuongNguoiCaoTuoi() {
		log.info("TC_07_Click so luon nguoi giam ve = 0 ");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivDecrase");

		log.info("TC_07_Hien thi so luong nguoi mac dinh la 0");
		verifyTrue(trainTicket.isDynamicTextChangeNumber("Người cao tuổi", "com.VCB:id/tvQuantity"));

		log.info("TC_07_Click so luon nguoi giam di 1, khong thuc hien duoc do so luong min = 0");
		trainTicket.clickDynamicButtonNumber("Người cao tuổi", "com.VCB:id/ivDecrase");

		log.info("TC_07_Hien thi so luong nguoi la 0");
		verifyEquals(trainTicket.getTextCustomerNumber("Người cao tuổi", "com.VCB:id/tvQuantity"), "0");

		log.info("TC_07_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Người cao tuổi", "com.VCB:id/ivIncrase");

		log.info("TC_07_Hien thi so luong nguoi la 1");
		verifyEquals(trainTicket.getTextCustomerNumber("Người cao tuổi", "com.VCB:id/tvQuantity"), "1");

		log.info("TC_07_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Người cao tuổi", "com.VCB:id/ivIncrase");

		log.info("TC_07_Hien thi so luong nguoi la 2");
		verifyEquals(trainTicket.getTextCustomerNumber("Người cao tuổi", "com.VCB:id/tvQuantity"), "2");

		log.info("TC_07_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Người cao tuổi", "com.VCB:id/ivIncrase");

		log.info("TC_07_Hien thi so luong nguoi la 3");
		verifyEquals(trainTicket.getTextCustomerNumber("Người cao tuổi", "com.VCB:id/tvQuantity"), "3");

		log.info("TC_07_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Người cao tuổi", "com.VCB:id/ivIncrase");

		log.info("TC_07_Hien thi so luong nguoi la 4");
		verifyEquals(trainTicket.getTextCustomerNumber("Người cao tuổi", "com.VCB:id/tvQuantity"), "4");

		log.info("TC_07_Click so luon nguoi tang len 1 don vi, so luon khong tang do max = 4");
		trainTicket.clickDynamicButtonNumber("Người cao tuổi", "com.VCB:id/ivIncrase");

		log.info("TC_07_Hien thi so luong nguoi la 4");
		verifyEquals(trainTicket.getTextCustomerNumber("Người cao tuổi", "com.VCB:id/tvQuantity"), "4");

		log.info("TC_07_Click dong man hinh hanh khach");
		trainTicket.clickDynamicRadioSelectType("Số lượng hành khách");
	}

	@Test
	public void TC_08_KiemTraQuyDinhSoLuongKhachToiDa() {
		log.info("TC_08_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Hành khách");

		log.info("TC_08_Click so luon nguoi giam ve = 0 ");
		trainTicket.clickDynamicButtonNumber("Người cao tuổi", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Người cao tuổi", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Người cao tuổi", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Người cao tuổi", "com.VCB:id/ivDecrase");

		log.info("TC_08_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivIncrase");

		log.info("TC_08_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivIncrase");

		log.info("TC_08_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivIncrase");

		log.info("TC_08_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Người lớn", "com.VCB:id/ivIncrase");

		log.info("TC_08_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Người cao tuổi", "com.VCB:id/ivIncrase");

		log.info("TC_08_Verify so luong Sinh vien = 2");
		verifyEquals(trainTicket.getTextCustomerNumber("Sinh viên", "com.VCB:id/tvQuantity"), "2");

		log.info("TC_08_Verify so luong tre em = 1");
		verifyEquals(trainTicket.getTextCustomerNumber("Trẻ em", "com.VCB:id/tvQuantity"), "1");

		log.info("TC_08_Verify so luong nguoi lon = 1");
		verifyEquals(trainTicket.getTextCustomerNumber("Người lớn", "com.VCB:id/tvQuantity"), "1");

		log.info("TC_08_Da add du 4 hanh khach, hanh khach thu 5 la Nguoi cao tuoi se khong duoc add vao danh sach, verify so luong nguoi cao tuoi =0");
		verifyEquals(trainTicket.getTextCustomerNumber("Người cao tuổi", "com.VCB:id/tvQuantity"), "0");
	}

	@Test
	public void TC_09_KiemTraIconDongManHinhHanhKhach() {
		log.info("TC_09_Click so luon nguoi giam ve = 0 ");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivDecrase");
		trainTicket.clickDynamicButtonNumber("Người lớn", "com.VCB:id/ivDecrase");

		log.info("TC_09_Click dong man hinh hanh khach");
		trainTicket.clickDynamicRadioSelectType("Số lượng hành khách");

		log.info("TC_09_Check title dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Sửa thông tin tìm kiếm"));
	}

	@Test
	public void TC_10_KiemTraChonSoLuongKhachHangThanhCong() {
		log.info("TC_10_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Hành khách");

		log.info("TC_010_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Người cao tuổi", "com.VCB:id/ivIncrase");

		log.info("TC_10_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivIncrase");

		log.info("TC_10_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivIncrase");

		log.info("TC_10_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Người lớn", "com.VCB:id/ivIncrase");

		String adult = trainTicket.getTextCustomerNumber("Người lớn", "com.VCB:id/tvQuantity") + " Người lớn, ";

		String child = trainTicket.getTextCustomerNumber("Trẻ em", "com.VCB:id/tvQuantity") + " Trẻ em, ";

		String student = trainTicket.getTextCustomerNumber("Sinh viên", "com.VCB:id/tvQuantity") + " Sinh viên, ";

		String old = trainTicket.getTextCustomerNumber("Người cao tuổi", "com.VCB:id/tvQuantity") + " Người cao tuổi";

		log.info("TC_10_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_10_Verify so luong hanh khach da chon man hinh dat ve tau");
		verifyEquals(trainTicket.getTextMessageInvalid("com.VCB:id/tv_hanh_khach"), adult + child + student + old);
	}

	@Test
	public void TC_11_KiemTraHienThiMacDinhComboboxGheNgoi() {
		log.info("TC_11_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Loại chỗ");

		log.info("TC_11_Verify Mac dinh ghe phu  duoc check");
		verifyTrue(trainTicket.isDynamicIconCheck("Ghế phụ", "com.VCB:id/ivCheck"));

		log.info("TC_11_Verify Mac dinh giường nằm khoang 6 cho duoc check");
		verifyTrue(trainTicket.isDynamicIconCheck("Giường nằm (khoang 6 chỗ)", "com.VCB:id/ivCheck"));

		log.info("TC_11_Verify Mac dinh giường nằm khoang 4 duoc check");
		verifyTrue(trainTicket.isDynamicIconCheck("Giường nằm (khoang 4 chỗ)", "com.VCB:id/ivCheck"));

		log.info("TC_11_Verify Mac dinh ngoi cung duoc check");
		verifyTrue(trainTicket.isDynamicIconCheck("Ngồi cứng", "com.VCB:id/ivCheck"));

		log.info("TC_11_Verify Mac dinh ngoi mem duoc check");
		verifyTrue(trainTicket.isDynamicIconCheck("Ngồi mềm", "com.VCB:id/ivCheck"));

	}

	@Test
	public void TC_12_FocusVaoComboboxGheNgoi() {
		log.info("TC_12_Hien thi icon x");
		verifyTrue(trainTicket.isDynamicImageSuccess("Chọn loại chỗ"));

		log.info("TC_12_Hien thi button xong");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Xong"));

		log.info("TC_12_Hien thi Checkbox");
		verifyTrue(trainTicket.isDynamicRadioDisplayed("com.VCB:id/ivCheckAll"));

		log.info("TC_12_Hien thi Chon tat ca");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Chọn tất cả"));

		log.info("TC_12_Hien thi ghe phu");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Ghế phụ"));

		log.info("TC_12_Hien thi giuong nam khoang 6 cho");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Giường nằm (khoang 6 chỗ)"));

		log.info("TC_12_Hien thi giuong nam khoang 4 cho");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Giường nằm (khoang 4 chỗ)"));

		log.info("TC_12_Hien thi ngoi cung");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Ngồi cứng"));

		log.info("TC_12_Hien thi ngoi mem");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Ngồi mềm"));
	}

	@Test
	public void TC_13_KiemTraNhanIconDong() {
		log.info("TC_13_Click dong man hinh chon loai cho");
		trainTicket.clickDynamicRadioSelectType("Chọn loại chỗ");

		log.info("TC_13_Check title dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Sửa thông tin tìm kiếm"));
	}

	@Test
	public void TC_14_KiemTraChon1LoaiGheThanhCong() {
		log.info("TC_14_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Loại chỗ");

		log.info("TC_14_Click radio bo chon tat ca");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Chọn tất cả");

		log.info("TC_14_Click chon ghe la ngoi cung");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Ngồi cứng");

		log.info("TC_14_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_14_Verify ghe ngoi cung duoc chon hien thi trong phan loai cho");
		verifyEquals(trainTicket.getTextMessageInvalid("com.VCB:id/tv_ghe_ngoi"), "Ngồi cứng");
	}

	@Test
	public void TC_15_KiemTraChonLonHon1LoaiGhe() {
		log.info("TC_15_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Loại chỗ");

		log.info("TC_15_Click chon ghe la ngoi mềm");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Ngồi mềm");

		log.info("TC_15_Click chon ghe la ghe phu");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Ghế phụ");

		log.info("TC_15_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_15_Verify ghe ngoi cung duoc chon hien thi trong phan loai cho");
		verifyEquals(trainTicket.getTextMessageInvalid("com.VCB:id/tv_ghe_ngoi"), "Ghế phụ, Ngồi cứng, Ngồi mềm");
	}

	@Test
	public void TC_16_KiemTraNhanBack() {
		log.info("TC_16_Click button back");
		trainTicket.clickToDynamicBackIcon("Sửa thông tin tìm kiếm");

		log.info("TC_16_Verify quay ve man hinh danh sach chieu di");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tvTitle1"), "Danh sách chiều đi");
	}

	@Test
	public void TC_17_KiemTraThaoTacSuaThanhCongTruongHopKhongCoDuLieu() {
		log.info("TC_17_Click button sua");
		trainTicket.clickToDynamicButton("Sửa");

		log.info("TC_17_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Loại chỗ");

		log.info("TC_17_Click radio bo chon tat ca");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Chọn tất cả");

		log.info("TC_17_Click chon ghe la ghe phu");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Ghế phụ");

		log.info("TC_17_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_17_Verify ghe ngoi cung duoc chon hien thi trong phan loai cho");
		verifyEquals(trainTicket.getTextMessageInvalid("com.VCB:id/tv_ghe_ngoi"), "Ghế phụ");

		log.info("TC_17_Click button tiep tục");
		trainTicket.clickToDynamicButton("ÁP DỤNG");

		log.info("TC_17_Verify message khong tim kiem duoc ket qua");
		verifyEquals(trainTicket.getTextMessageFollowButton("com.VCB:id/btnOtherTrip"), "Không có kết quả chuyến đi phù hợp. Quý khách vui lòng chọn chuyến đi khác.");
	}

	@Test
	public void TC_18_KiemTraManHinhDanhSachChuyenDiChieuVe() {
		LocalDate now = LocalDate.now();
		LocalDate date4 = now.plusDays(4);
		LocalDate date7 = now.plusDays(7);

		log.info("TC_18_Click button sua");
		trainTicket.clickToDynamicButton("Sửa");

		log.info("TC_18_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Loại chỗ");

		log.info("TC_18_Click radio chon tat ca");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Chọn tất cả");

		log.info("TC_18_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_18_Click button ap dung");
		trainTicket.clickToDynamicButton("ÁP DỤNG");

		log.info("TC_18_Click chon mot chuyen di");
		trainTicket.clickDynamicSelectTrain("com.VCB:id/tv_ten_tau", "0");

		log.info("TC_18_Click button tiep tuc chon chieu ve");
		trainTicket.clickToDynamicButton("TIẾP TỤC CHỌN CHIỀU VỀ");

		log.info("TC_18_Verify hien thi man hinh danh sach chieu ve khi tim kiem ve tau co ket qua");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tvTitle1"), "Danh sách chiều về");

		log.info("TC_18_Hien thi button back");
		verifyTrue(trainTicket.isDynamicRadioDisplayed("com.VCB:id/ivTitleLeft"));

		log.info("TC_18_Hien thi button home");
		verifyTrue(trainTicket.isDynamicRadioDisplayed("com.VCB:id/ivTitleRight"));

		log.info("TC_18_Verify text gio chay");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_gio_khoi_hanh"), "Giờ chạy");

		log.info("TC_18_Verify text thoi gian chay");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_thoi_gian_chay"), "Thời gian chạy");

		log.info("TC_18_Verify button sua");
		verifyTrue(trainTicket.isDynamicButtonEditDisplay("com.VCB:id/btnEdit"));

		String weekPickup = convertDayOfWeekVietNamese(getCurrentDayOfWeek(date4));
		String weekArrival = convertDayOfWeekVietNamese(getCurrentDayOfWeek(date7));
		String expectDay = weekPickup + " " + getForwardDate(4) + " - " + weekArrival + " " + getForwardDate(7);

		log.info("TC_18_verify thoi gian khu hoi");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_date_title"), expectDay);

		log.info("TC_18_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_18_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_18_Check hien thi ten tau");
		verifyTrue(trainTicket.isDynamicNameTrainDisplay("com.VCB:id/tv_ten_tau", "0"));

		log.info("TC_18_Check hien thi thoi gian bat dau chay");
		verifyTrue(trainTicket.isDynamicDateTimeDisplay("com.VCB:id/tv_thoi_di"));

		String timeStart = trainTicket.getDynamicDateTime("com.VCB:id/tv_thoi_di") + " - " + getForwardDate(4);

		log.info("TC_18_Check hien thi thoi gian den");
		String timeEnd = trainTicket.getDynamicDateTime("com.VCB:id/tv_thoi_gian_den") + trainTicket.getDynamicDateTime("com.VCB:id/tv_ngay_den");

		String expectTime = trainTicket.getDuration(timeStart, timeEnd);

		log.info("TC_18_Verify tong thoi gian di chuyen");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_tong_thoi_gian"), expectTime);
	}

	@Test
	public void TC_19_KiemTraNhanBack() {
		log.info("TC_19_Click button back");
		trainTicket.clickToDynamicBackIcon("Danh sách chiều về");

		log.info("TC_19_Verify quay ve man hinh danh sach chieu di");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tvTitle1"), "Danh sách chiều đi");
	}

	@Test
	public void TC_20_NhanIconHome() {
		log.info("TC_20_Click chon mot chuyen di");
		trainTicket.clickDynamicSelectTrain("com.VCB:id/tv_ten_tau", "0");

		log.info("TC_20_Click button tiep tuc chon chieu ve");
		trainTicket.clickToDynamicButton("TIẾP TỤC CHỌN CHIỀU VỀ");

		log.info("TC_20_Click button home");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivTitleRight");

		log.info("TC_20_Check title dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("ĐẶT VÉ TÀU"));
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}
}
