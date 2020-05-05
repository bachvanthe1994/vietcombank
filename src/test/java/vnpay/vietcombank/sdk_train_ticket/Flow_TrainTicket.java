package vnpay.vietcombank.sdk_train_ticket;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.sdk.trainTicket.TrainTicketPageObject;
import vietcombankUI.sdk.trainTicket.TrainTicketPageUIs;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;
import vnpay.vietcombank.sdk_train_ticket_data.TrainTicket_Data;

public class Flow_TrainTicket extends Base {
	AppiumDriver<MobileElement> driver;
	private TransactionReportPageObject transReport;
	private LogInPageObject login;
	private TrainTicketPageObject trainTicket;

	List<String> listExpect;
	List<String> listActual;
	String phoneNumber = "";
	String taiKhoanNguon = "";
	String hoTen = "";
	String SDT = "";
	String email = "";
	String hoChieu = "";
	String tongTienThanhToan = "";
	String soTienPhi = "";

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
		phoneNumber = phone;
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
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/edtTextPickUp");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Nhap text ga den");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END, "com.VCB:id/edtTextArrival");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_08_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate("com.VCB:id/tv_ngay_di");

		String startDay = getForWardDay(3);
		String EndDay = getForWardDay(12);

		log.info("TC_01_Chon ngay di la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(3), startDay);

		log.info("TC_01_Chon ngay ve la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(12), EndDay);
		log.info("TC_01_Click button tiep tuc");
		trainTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_01_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Hành khách");

		log.info("TC_01_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivIncrase");

		log.info("TC_01_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivIncrase");

		log.info("TC_01_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_01_Click link loai cho");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Loại chỗ");

		log.info("TC_01_Click radio chon tat ca");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Chọn tất cả");

		log.info("TC_01_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_01_Click button tiep tuc");
		trainTicket.clickToDynamicButton("TIẾP TỤC");

		log.info("TC_01_Verify hien thi man hinh danh sach chieu di khi tim kiem ve tau co ket qua");
		verifyTrue(trainTicket.isDynamicDateTimeDisplay("com.VCB:id/tvTitle1"));
	}

	@Test
	public void TC_02_KiemTraThongTinHanhTrinhDanhSachChonChuyenDi() {
		LocalDate now = LocalDate.now();
		LocalDate date3 = now.plusDays(3);
		LocalDate date12 = now.plusDays(12);

		log.info("TC_02_Hien thi button back");
		verifyTrue(trainTicket.isDynamicRadioDisplayed("com.VCB:id/ivTitleLeft"));

		log.info("TC_02_Hien thi button home");
		verifyTrue(trainTicket.isDynamicRadioDisplayed("com.VCB:id/ivTitleRight"));

		log.info("TC_02_Verify text gio chay");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_gio_khoi_hanh"), "Giờ khởi hành");

		log.info("TC_02_Verify text thoi gian chay");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_thoi_gian_chay"), "Thời gian chạy");

		log.info("TC_02_Verify button sua");
		verifyTrue(trainTicket.isDynamicButtonEditDisplay("com.VCB:id/btnEdit"));

		String weekPickup = convertDayOfWeekVietNamese(getCurrentDayOfWeek(date3));
		String weekArrival = convertDayOfWeekVietNamese(getCurrentDayOfWeek(date12));
		String expectDay = weekPickup + " " + getForwardDate(3) + " - " + weekArrival + " " + getForwardDate(12);

		log.info("TC_02_verify thoi gian khu hoi");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_date_title"), expectDay);

		log.info("TC_02_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_02_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_02_Check hien thi ten tau");
		verifyTrue(trainTicket.isDynamicNameTrainDisplay("0", "com.VCB:id/tv_ten_tau"));

		log.info("TC_02_Check hien thi thoi gian bat dau chay");
		verifyTrue(trainTicket.isDynamicDateTimeDisplay("com.VCB:id/tv_thoi_di"));

		String timeStart = trainTicket.getDynamicDateTime("com.VCB:id/tv_thoi_di") + " - " + getForwardDate(0);

		log.info("TC_02_Check hien thi thoi gian den");
		String timeEnd = trainTicket.getDynamicDateTime("com.VCB:id/tv_thoi_gian_den") + trainTicket.getDynamicDateTime("com.VCB:id/tv_ngay_den");

		String expectTime = trainTicket.getDuration(timeStart, timeEnd);

		log.info("TC_02_Verify tong thoi gian di chuyen");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_tong_thoi_gian"), expectTime);
	}

	@Test
	public void TC_03_KiemTraThaoTacSuaChieuDiThanhCong() {
		String startDay = getForWardDay(2);
		String EndDay = getForWardDay(5);

		LocalDate now = LocalDate.now();
		LocalDate date2 = now.plusDays(2);
		LocalDate date5 = now.plusDays(5);
		log.info("TC_03_Click button sua");
		trainTicket.clickToDynamicButton("Sửa");

		log.info("TC_03_Click ga khoi hanh");
		trainTicket.clickToDynamicSelectDate("com.VCB:id/tvTextPickUp");

		log.info("TC_03_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/edtTextPickUp");

		log.info("TC_03_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_03_Nhap text ga den");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END, "com.VCB:id/edtTextArrival");

		log.info("TC_03_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_03_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate("com.VCB:id/tv_ngay_di");

		log.info("TC_03_Chon ngay di la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(2), startDay);

		log.info("TC_03_Chon ngay ve la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(5), EndDay);

		log.info("TC_03_Click button tiep tuc");
		trainTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_03_Click button ap dung");
		trainTicket.clickToDynamicButton("ÁP DỤNG");

		String weekPickup = convertDayOfWeekVietNamese(getCurrentDayOfWeek(date2));
		String weekArrival = convertDayOfWeekVietNamese(getCurrentDayOfWeek(date5));
		String expectDay = weekPickup + " " + getForwardDate(2) + " - " + weekArrival + " " + getForwardDate(5);

		log.info("TC_03_verify thoi gian khu hoi");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_date_title"), expectDay);

		log.info("TC_03_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_03_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

	}

	@Test
	public void TC_04_KiemTraThaoTacSuaChieuVeThanhCong() {
		String startDay = getForWardDay(2);
		String EndDay = getForWardDay(7);

		LocalDate now = LocalDate.now();
		LocalDate date2 = now.plusDays(2);
		LocalDate date7 = now.plusDays(7);

		log.info("TC_04_Click chon mot chuyen di");
		trainTicket.clickDynamicSelectTrain("0", "com.VCB:id/tv_ten_tau");

		log.info("TC_04_Click button tiep tuc chon chieu ve");
		trainTicket.clickToDynamicButton("TIẾP TỤC CHỌN CHIỀU VỀ");

		log.info("TC_04_Click button sua");
		trainTicket.clickToDynamicButton("Sửa");

		log.info("TC_04_Vao man hinh chon ngay");
		trainTicket.clickToDynamicSelectDate("com.VCB:id/tv_ngay_di");

		log.info("TC_04_Chon ngay di la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(2), startDay);

		log.info("TC_04_Chon ngay ve la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(7), EndDay);

		log.info("TC_04_Click button tiep tuc");
		trainTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_04_Click button ap dung");
		trainTicket.clickToDynamicButton("ÁP DỤNG");

		String weekPickup = convertDayOfWeekVietNamese(getCurrentDayOfWeek(date2));
		String weekArrival = convertDayOfWeekVietNamese(getCurrentDayOfWeek(date7));
		String expectDay = weekPickup + " " + getForwardDate(2) + " - " + weekArrival + " " + getForwardDate(7);

		log.info("TC_04_verify thoi gian khu hoi");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_date_title"), expectDay);

		log.info("TC_04_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_04_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

	}

	@Test
	public void TC_05_DatVe_KhuHoi_SoLuongNguoiNhoNhat_XacThucBang_SMSOTP() {
		log.info("TC_05_Click button sua");
		trainTicket.clickToDynamicButton("Sửa");

		log.info("TC_05_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Hành khách");

		log.info("TC_05_Click bo so luong tre em di cung");
		trainTicket.clickDynamicButtonNumber("Trẻ em", "com.VCB:id/ivDecrase");

		log.info("TC_05_Click button xong");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Xong");

		log.info("TC_05_Click button ap dung");
		trainTicket.clickToDynamicButton("ÁP DỤNG");

		log.info("TC_05_Click chon mot chuyen di");
		trainTicket.clickDynamicSelectTrain("0", "com.VCB:id/tv_ten_tau");

		log.info("TC_05_get ten tau di");
		String codeTrainStart = trainTicket.getDynamicTextViewIndex("0", "com.VCB:id/tv_ten_tau");

		log.info("TC_05_Click button tiep tuc chon chieu ve");
		trainTicket.clickToDynamicButton("TIẾP TỤC CHỌN CHIỀU VỀ");

		log.info("TC_05_Click chon mot chuyen di");
		trainTicket.clickDynamicSelectTrain("1", "com.VCB:id/tv_ten_tau");
		String codeTrainEnd = trainTicket.getDynamicTextViewIndex("0", "com.VCB:id/tv_ten_tau");

		log.info("TC_05_Click button tiep tuc");
		trainTicket.clickToDynamicButton("TIẾP TỤC");

		log.info("TC_05_verify chuyen sang man chon cho chieu di");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), "Chọn chỗ cho chiều đi");

		log.info("TC_05_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_05_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_05_verify tên tàu");
		verifyEquals(trainTicket.getDynamicTextViewIndex("0", "com.VCB:id/tv_ten_tau"), codeTrainStart);

		log.info("TC_05_Click chon toa");
		trainTicket.clickDynamicSelectLocation("0", "com.VCB:id/tvWagon");

		log.info("TC_05_get lay mau o cho trong");
		String colorOfSeat = trainTicket.getColorOfElement(TrainTicketPageUIs.IMAGE_BY_TEXT, "Chỗ trống", "Ghế phụ");

		log.info("TC_05_Click chon cho trong");
		trainTicket.scrollIDownOneTime(driver);
		listActual = trainTicket.chooseSeats(1, colorOfSeat);

		log.info("TC_05Verify so ghe da chon");
		listExpect = Arrays.asList(trainTicket.getTextInDynamicPopup("com.VCB:id/tvSeat"));
		verifyEquals(listActual, listExpect);

		log.info("TC_05_Click tiep tuc chon chieu ve");
		trainTicket.clickToDynamicButton("TIẾP TỤC CHỌN CHIỀU VỀ");

		log.info("TC_05_verify chuyen sang man chon cho chieu ve");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), "Chọn chỗ cho chiều về");

		log.info("TC_05_Verify lo trinh diem khoi hanh");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_from_return"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH_END);

		log.info("TC_05_Verify lo trinh diem den");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_to_return"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_05_verify tên tàu");
		verifyEquals(trainTicket.getDynamicTextViewIndex("0", "com.VCB:id/tv_ten_tau_return"), codeTrainEnd);

		log.info("TC_05_Click chon toa");
		trainTicket.clickDynamicSelectLocation("0", "com.VCB:id/tvWagon");

		log.info("TC_05_get lay mau o cho trong");
		String colorOfSeatEnd = trainTicket.getColorOfElement(TrainTicketPageUIs.IMAGE_BY_TEXT, "Chỗ trống", "Ghế phụ");

		log.info("TC_05_Click chon cho trong");
		trainTicket.scrollIDownOneTime(driver);
		listActual = trainTicket.chooseSeats(1, colorOfSeatEnd);

		log.info("TC_05_Verify so ghe da chon");
		listExpect = Arrays.asList(trainTicket.getTextInDynamicPopup("com.VCB:id/tvSeat"));
		verifyEquals(listActual, listExpect);

		log.info("TC_05_Click tiep tuc");
		trainTicket.clickToDynamicButton("TIẾP TỤC");

		log.info("TC_05_Verify man hinh");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), "Chỗ đang đặt");

		log.info("TC_05_Get so tien chieu di");
		String amountStart = trainTicket.getTextInDynamicPopup("com.VCB:id/tvAmount");
		long amountStartConvert = convertAvailableBalanceCurrentcyOrFeeToLong(amountStart);

		log.info("TC_05_Get so tien chieu ve");
		String amountEnd = trainTicket.getTextInDynamicPopup("com.VCB:id/tvAmountReturn");
		long amountEndConvert = convertAvailableBalanceCurrentcyOrFeeToLong(amountEnd);
		String amountTotal = addCommasToLong(amountEndConvert + amountStartConvert + "") + " VND";

		log.info("TC_05_Verify tong so tien thanh toan");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTotalAmount"), amountTotal);

		log.info("TC_05_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains("TIẾP TỤC");

		log.info("TC_05_Verify man hinh thong tin hanh khach");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), "Thông tin hành khách");

		log.info("TC_05_Nhap ho ten khách hang");
		trainTicket.inputToDynamicTextHeader(TrainTicket_Data.inputText.CUSTOMER_NAME, "com.VCB:id/ivRight", "com.VCB:id/lnHeader", "com.VCB:id/tvHoTen");

		log.info("TC_05_Nhap so CMT");
		trainTicket.inputToDynamicTextHeader(TrainTicket_Data.inputText.CARD_NO, "com.VCB:id/ivRight", "com.VCB:id/lnHeader", "com.VCB:id/tvCMND");

		log.info("TC_05_Verify ten user login");
		verifyEquals(trainTicket.getDynamicTextEdit("Thông tin liên hệ", "com.VCB:id/tvHoTen").toUpperCase(), "NGUYEN NGOC TOAN");

		log.info("TC_05_Nhap so CMT");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.CARD_NO, "Thông tin liên hệ", "com.VCB:id/tvCMND");

		log.info("TC_05_Nhap email");
		trainTicket.inputToDynamicText(TrainTicket_Data.inputText.email, "Thông tin liên hệ", "com.VCB:id/tvEmail");

		log.info("TC_05_Click radio khong xuat hoa don");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivNoXuatHoaDon");

		log.info("TC_05_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains("TIẾP TỤC");

		log.info("TC_05_click button dong y dong popup");
		trainTicket.clickToDynamicButtonContains("Đ");

		log.info("TC_05_Verify man hinh thong tin dat ve");
		verifyEquals(trainTicket.getTextInDynamicPopup("com.VCB:id/tvTitle1"), "Thông tin hành khách");

		log.info("TC_05_Get tong tien chieu di");
		trainTicket.getTextTotal("0", "com.VCB:id/tvTotalAmount");

		log.info("TC_05_Get tong tien chieu ve");
		trainTicket.getTextTotal("1", "com.VCB:id/tvTotalAmount");

		log.info("TC_05_click button Thanh toan");
		trainTicket.clickToDynamicButtonContains("THANH TOÁN");

		log.info("TC_05_click button Khong");
		trainTicket.clickToDynamicButton("Không");

		log.info("TC_05_verify hien thi Thong tin ve Tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Thông tin vé tàu"));

		log.info("TC_05_lay Tai khoan nguon");
		taiKhoanNguon = trainTicket.getDynamicDateTime("com.VCB:id/number_account");

		log.info("TC_05_lay ho va ten");
		hoTen = trainTicket.getDynamicTextOld(driver, "Họ tên");

		log.info("TC_05_veriFy ho ten");
		verifyEquals("NGUYEN NGOC TOAN", hoTen);

		log.info("TC_05_veriFy So Dien Thoai");
		SDT = trainTicket.getDynamicTextOld(driver, "Số điện thoại");
		verifyEquals(phoneNumber, SDT);

		log.info("TC_05_veriFy Email");
		email = trainTicket.getDynamicTextOld(driver, "Email");
		verifyEquals(TrainTicket_Data.inputText.email, email);

		log.info("TC_05_veriFy Thong tin ho chieu");
		hoChieu = trainTicket.getDynamicTextOld(driver, "CMND/CCCD/Hộ chiếu");

		verifyEquals(TrainTicket_Data.inputText.CARD_NO, hoChieu);
		trainTicket.scrollDownToText(driver, "Tổng tiền thanh toán");

		log.info("TC_05_Verify tong tien thanh toan-------");
		tongTienThanhToan = trainTicket.getDynamicTextOld(driver, "Tổng tiền thanh toán");

		log.info("TC_05_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains("Tiếp tục");

		log.info("TC_05_Verify hien thi man hinh xac nhan thong tin");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Xác nhận thông tin"));
		log.info("TC_05_Verify tai khoan nguon");
		verifyEquals(taiKhoanNguon, trainTicket.getDynamicTextOld("Tài khoản nguồn"));

		log.info("TC_05_Verify rong so tien thanh toan");
		verifyTrue(trainTicket.getDynamicTextOld("Số tiền").contains(tongTienThanhToan));

		log.info("TC_05_Step_06: Chon phuong thuc xac thuc SMS OTP");
		trainTicket.clickToTextID(driver, "com.VCB:id/tvptxt");
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_05_Verify Phi giao dich");
		soTienPhi = trainTicket.getDynamicTextOld("Số tiền phí");

		log.info("TC_05_Step_07: An nut 'Tiep tuc'");
		trainTicket.clickToDynamicButtonContains("Tiếp tục");

		log.info("TC_05_Step_08: Nhap du ki tu vao o nhap OTP");
		trainTicket.inputToDynamicOtp(driver, "123456", "Tiếp tục");

		log.info("TC_05_Step_09: An tiep button 'Tiep tuc'");
		trainTicket.clickToDynamicButtonContains("Tiếp tục");

		log.info("TC_05_Step_09: An tiep button Hien thi thong bao thanh toan thanh cong");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("THANH TOÁN THÀNH CÔNG"));//

		log.info("TC_05_Step_09: Click thong quay lai man hinh home");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivHome");

	}

	@Test
	public void TC_06_DatVe_KhuHoi_SoLuongNguoiNhoNhat_XacThucBang_SMSOTP() {
		log.info("TC_06: Click menu header");
		trainTicket.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_06: Click bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_06: chon loai bao cao giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_06:_: Chon option chuyen tien nhanh qua tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán vé tàu");

		log.info("TC_06:: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(7);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_06: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_06:: Tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

		log.info("TC_06:: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06:: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06:: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		verifyEquals(transferTimeInReport, transferTimeInReport1);
		log.info("TC_06:: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), taiKhoanNguon);

		log.info("TC_06:: Check tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), Account_Data.Valid_Account.ACCOUNT_TO);

		log.info("TC_06:So tien giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền giao dịch").contains(tongTienThanhToan));

		log.info("TC_06: Check phi giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), soTienPhi);

		log.info("TC_06:: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Loại giao dịch"), "Thanh toán vé tàu");

		log.info("TC_06:: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Nội dung giao dịch").contains(TransferMoneyQuick_Data.TransferQuick.NOTE));

		log.info("TC_06: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
