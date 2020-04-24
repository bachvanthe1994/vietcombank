package vnpay.vietcombank.settingVCB_Smart_OTP;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.FilmInfo;
import model.FilmTicketInfo;
import model.SeatType;
import pageObjects.ConfirmMethodObject;
import pageObjects.LogInPageObject;
import pageObjects.sdk.airTicketBooking.DynamicAirTicketBookingObjects;
import pageObjects.sdk.filmTicketBooking.FilmTicketBookingPageObject;
import pageObjects.sdk.hotelBooking.HotelBookingPageObject;
import pageObjects.sdk.trainTicket.TrainTicketPageObject;
import pageObjects.shopping_online.ShoppingOnlinePageObject;
import vehicalPageObject.VehicalPageObject;
import vehicalTicketBookingUI.CommonPageUIs;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.sdk.filmTicketBooking.FilmTicketBookingPageUIs;
import vietcombankUI.sdk.trainTicket.TrainTicketPageUIs;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.SettingVCBSmartOTP_Data;
import vnpay.vietcombank.sdk.airticket.data.DomesticAirTicketBooking_Data;
import vnpay.vietcombank.sdk.filmTicketBooking.data.FilmTicketBooking_Data;
import vnpay.vietcombank.sdk.hotelBooking.data.HotelBooking_Data;
import vnpay.vietcombank.sdk.vehicleTicket.data.VehicalData;
import vnpay.vietcombank.sdk_train_ticket_data.TrainTicket_Data;

public class Flow_SettingVCB_Smart_OTP_SDK_Part3 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private ConfirmMethodObject smartOTP;
	private TrainTicketPageObject trainTicket;
	private DynamicAirTicketBookingObjects airTicket;
	private FilmTicketBookingPageObject filmTicketBooking;
	private HotelBookingPageObject hotelBooking;
	private VehicalPageObject vehicalTicket;
	private ShoppingOnlinePageObject shopping;
	private String currentDay = getCurrentDay();
	List<String> listActual, listExpect;
	String phoneNumber, nameLogin, paycode, password, totalPrice = "";
	private String fifthDay = getForWardDay(5);
	private String ticketPrice, payID, transactionID, fee, sourceDetail, source, destDetail, dest, time, flightCode, departureTime, arrivalTime, duration, hoTen, SDT, email, hoChieu;
	FilmTicketInfo info = new FilmTicketInfo();
	double soDuThuc = 0;
	private String transferTime, transactionNumber, ticketCode;
	private long surplus, availableBalance, actualAvailableBalance, money, fee1, amountTranfer, costTranfer;
	int indexHang = 0;

	public String amountFee = "- ";
	public String nameTyped, phoneTyped, emailTyped, diemDi, diemDen, hangXe, soGhe, soLuongVe, tongTien, taiKhoanNguon, maThanhToan, maGiaodich, maVe, tongTienThanhToan, soTienPhi = "";

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		smartOTP = PageFactoryManager.getConfirmMethodObject(driver);
		trainTicket = PageFactoryManager.getTrainTicketPageObject(driver);
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		filmTicketBooking = PageFactoryManager.getFilmTicketBookingPageObject(driver);
		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);
		vehicalTicket = PageFactoryManager.getVehicalPageObject(driver);
		password = pass;
		
		shopping = PageFactoryManager.getShoppingOnlinePageObject(driver);
		shopping.sleep(driver, 5000);

		LocalDate now = LocalDate.now();
		String today = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
		String tomorow = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + Integer.valueOf(getCurrentDay()) + 1 + "/" + getCurrenMonth() + "/" + getCurrentYear();
		log.info("TC_02_Step: Click menu header");
		Thread.sleep(30000);
		
	}

	@Test
	public void TC_01_CaiDatPhuongThucXacThucOTP() {
		log.info("TC_02_Step: Click menu header");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step: Click cai dat");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("TC_02_Step: Click cai dat Smart OTP");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt VCB-Smart OTP");

		log.info("TC_02_Step: Click cai dat cho tai khoan");
		smartOTP.clickToDynamicTextFollowText(driver, "Chưa kích hoạt");

		log.info("TC_02_Step: Click toi dong y");
		smartOTP.clickToTextID(driver, "com.VCB:id/rule");

		log.info("TC_02_Step_click button dong y");
		smartOTP.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_02_Step_Nhap mat khau");
		smartOTP.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.Smart_OTP, "Nhập mật khẩu");

		log.info("TC_02_Step_Nhap lai mat khau");
		smartOTP.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.Smart_OTP, "Nhập lại mật khẩu");

		log.info("TC_02_Step_click button tiep tuc");
		smartOTP.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Nhap ma xac thuc");
		smartOTP.inputToDynamicSmartOtp(driver, "666888", "com.VCB:id/otp");

		log.info("TC_02_Step_click button tiep tuc");
		smartOTP.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Verify cai dat thanh cong");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		verifyEquals(smartOTP.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/state_vnpay"), "Đã kích hoạt");

		log.info("TC_02_Step_click button quay lai cai dat");
		smartOTP.clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Click button home");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	// Thanh Toan vé tàu
	//@Test
	public void TC_02_DatVeMotChieuSoLuongNguoiNhoNhatVaXacThucBangSmartOTP() throws InterruptedException {
		String startDay = getForWardDay(2);
		String EndDay = getForWardDay(7);

		LocalDate now = LocalDate.now();
		LocalDate date2 = now.plusDays(2);
		LocalDate date7 = now.plusDays(7);
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

		log.info("TC_08_Chon ngay di la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(2), startDay);

		log.info("TC_08_Chon ngay ve la ngay tuong lai");
		trainTicket.clickDynamicDateStartAndEnd(trainTicket.getMonthAndYearPlusDay(7), EndDay);

		log.info("TC_12_Click button tiep tuc");
		trainTicket.clickToDynamicButton("Tiếp tục");

		String weekPickup = convertDayOfWeekVietNamese(getCurrentDayOfWeek(date2));
		String weekArrival = convertDayOfWeekVietNamese(getCurrentDayOfWeek(date7));
		String expectDay = weekPickup + " " + getForwardDate(2) + " - " + weekArrival + " " + getForwardDate(7);

		log.info("TC_12_verify thoi gian khu hoi");
		verifyEquals(trainTicket.getDynamicDateTime("com.VCB:id/tv_date_title"), expectDay);
		log.info("TC_12_Click button tiep tuc");
		trainTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_17_Click button hanh khach");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Hành khách");

		log.info("TC_21_Click so luon nguoi tang len 1 don vi");
		trainTicket.clickDynamicButtonNumber("Sinh viên", "com.VCB:id/ivIncrase");

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
		System.out.print(listExpect);

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
		verifyEquals(TrainTicket_Data.inputText.TELEPHONE_NO, SDT);

		log.info("TC_05_veriFy Email");
		email = trainTicket.getDynamicTextOld(driver, "Email");
		verifyEquals(TrainTicket_Data.inputText.email, email);

		log.info("TC_05_veriFy Thong tin ho chieu");
		hoChieu = trainTicket.getDynamicTextOld(driver, "CMND/CCCD/Hộ chiếu");

		verifyEquals(TrainTicket_Data.inputText.CARD_NO, hoChieu);
		trainTicket.scrollDownToText(driver, "Tổng tiền thanh toán");

		log.info("TC_05_Verify tong tien thanh toan");
		tongTienThanhToan = trainTicket.getDynamicTextOld(driver, "Tổng tiền thanh toán");

		log.info("TC_05_click button tiep tuc");
		trainTicket.clickToDynamicButtonContains("Tiếp tục");

		log.info("TC_05_Verify hien thi man hinh xac nhan thong tin");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("Xác nhận thông tin"));
		log.info("TC_05_Verify tai khoan nguon");
		verifyEquals(taiKhoanNguon, trainTicket.getDynamicTextOld("Tài khoản nguồn"));

		log.info("TC_05_Verify rong so tien thanh toan");
		verifyTrue(trainTicket.getDynamicTextOld("Số tiền").contains(tongTienThanhToan));

		log.info("TC_05_Verify Phi giao dich");
		soTienPhi = trainTicket.getDynamicTextOld("Số tiền phí");

		log.info("TC_05_Step_06: Chon phuong thuc xac thuc SMS OTP");
		trainTicket.clickToTextID(driver, "com.VCB:id/tvptxt");
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_05_Step_07: An nut 'Tiep tuc'");
		trainTicket.clickToDynamicButtonContains("Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		trainTicket.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		trainTicket.clickToDynamicContinue(driver, "com.VCB:id/submit");
		trainTicket.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_05_Step_09: An tiep button Hien thi thong bao thanh toan thanh cong");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("THANH TOÁN THÀNH CÔNG"));

		log.info("TC_05_Step_09: Click thong quay lai man hinh home");
		trainTicket.clickDynamicImageResourceID("com.VCB:id/ivHome");
	}

	// Thanh toán vé máy bay
	@Parameters({ "otp" })
	//@Test
	public void TC_03_DatVeMayBayNoiDiaMotChieuThanhCong_1Nguoi_ThanhToanSmartOTP(String otp) throws InterruptedException {
		airTicket.clickToDynamicIcon(driver, "Đặt vé máy bay");

		log.info("Before class: Click dat ve may bay noi dia ");
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("Before class: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("Before class: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink("Một chiều");

		log.info("TC_01_Step_01: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");

		log.info("TC_01_Step_02: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		airTicket.clickToDynamicTextOrButtonLink("TP Hồ Chí Minh");

		log.info("TC_01_Step_03: Chon ngay di");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(5), fifthDay);
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_01_Step_04: Tim chuyen bay");
		airTicket.clickToDynamicButton("Tìm chuyến bay");
		sourceDetail = airTicket.getDynamicTextByID("com.VCB:id/tv_sourceDetail");
		source = airTicket.getDynamicTextByID("com.VCB:id/tv_source");
		destDetail = airTicket.getDynamicTextByID("com.VCB:id/tv_DestDetail");
		dest = airTicket.getDynamicTextByID("com.VCB:id/tv_Dest");
		time = airTicket.getDynamicTextByID("com.VCB:id/tv_thu");

		log.info("TC_01_Step_05: Chon chuyen bay va dat ve");
		airTicket.clickToDynamicFlight2WayCode("com.VCB:id/recycler_view_one", "0");

		flightCode = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_flightNo");
		departureTime = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_time_deptTime");
		arrivalTime = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_time_deptTime_arrival");
		duration = airTicket.getDynamicFlightData("com.VCB:id/recycler_view_one", "0", "com.VCB:id/tv_timeduration");

		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");
		airTicket.clickToDynamicTextByID("com.VCB:id/btn_book");

		log.info("TC_01_Step 06: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_01_Step 07: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_01_Step 08: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_11_Step 09: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_11_Step 10: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content");

		log.info("TC_11_Step 11: Dien ten nguoi lon 1 ");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten");

		log.info("TC_01_Step 12: Chon gioi tinh nu");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");

		log.info("TC_01_Step 13: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step 14: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Xác nhận chuyến bay");

		log.info("TC_01_Step 14_1: Kiem tra dia diem khoi hanh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem1"), sourceDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem2"), source);

		log.info("TC_01_Step 14_2: Kiem tra dia diem den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem3"), destDetail);
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/DiaDiem", "com.VCB:id/tvTextttdiadiem4"), dest);

		log.info("TC_01_Step 14_3: Kiem tra thong tin hanh trinh");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft1"), "HÀNH TRÌNH");

		log.info("TC_01_Step 14_4: Kiem tra thong tin ma may bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvFlightId"), flightCode);

		log.info("TC_01_Step 14_5: Kiem tra thong tin ngay di");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft2"), time);

		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight1"), time);

		log.info("TC_01_Step 14_6: Kiem tra thong tin gio di ");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextLeft3"), source + " " + departureTime);

		log.info("TC_01_Step 14_7: Kiem tra thong tin gio den");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvTextRight2"), dest + " " + arrivalTime);

		log.info("TC_01_Step 14_8: Kiem trathông tin thoi gian bay");
		verifyEquals(airTicket.getDynamicDepartureArrivalData("com.VCB:id/ThongTinMayBayChieuDi", "com.VCB:id/tvDuration"), duration);

		log.info("TC_01_Step 15: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền", "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_01_Step_16: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_01_Step_17: Click Thanh toan");
		airTicket.clickToDynamicButton("Thanh toán");

		log.info("TC_01_Step_18_1: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		airTicket.clickToDynamicTextOrButtonLink(Account_Data.Valid_Account.ACCOUNT2);
		payID = airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent");

		log.info("TC_01_Step_18_2: Xac nhan chieu di ");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("4", "Chiều bay"), source + " → " + dest);

		log.info("TC_01_Step_18_3: Xac nhan so hieu chuyen bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("5", "Số hiệu chuyến bay").replace("-", ""), flightCode);

		log.info("TC_01_Step_18_4: Xac nhan thoi gian bay chieu di");
		verifyEquals(airTicket.getDynamicConfirmInfoByIndex("6", "Thời gian bay"), departureTime + " - " + arrivalTime);

		log.info("TC_01_Step_19: Click Tiep tuc");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền thanh toán", "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_01_Step 20: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_21: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Từ tài khoản", "com.VCB:id/tvTu_tai_khoan"), Account_Data.Valid_Account.ACCOUNT2);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Số tiền", "com.VCB:id/tvAmount"), ticketPrice);

		log.info("TC_01_Step_22: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink("VCB - Smart OTP");
		fee = airTicket.getAirTicketPriceInfo1Way("Phí giao dịch", "com.VCB:id/tvPhiGiaoDich");

		log.info("TC_01_Step_23: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		airTicket.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		airTicket.clickToDynamicContinue(driver, "com.VCB:id/submit");
		airTicket.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_25: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent"), payID);
		transactionID = airTicket.getAirTicketPriceInfo1Way("Mã giao dịch", "com.VCB:id/tvContent");

		log.info("TC_01_Step_26: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
	}

	// Vé xem phim
//	@Test
	public void TC_04_DatVeXemPhim_Rap_MegaGS_BHDCineplex_SmartOTP() throws InterruptedException {
		log.info("TC_01_01_Click Dat ve xem phim");
		filmTicketBooking.clickToDynamicTextOrButtonLink("Đặt vé xem phim");

		log.info("TC_01_02_Click nut Dong y");
		filmTicketBooking.clickToDynamicButton("Đồng ý");

		log.info("TC_01_03_Click chon Tinh thanh");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");

		log.info("TC_01_04_Tim kiem thanh pho");
		filmTicketBooking.inputIntoEditTextByID("Hồ Chí Minh", "com.VCB:id/edtSearch");

		log.info("TC_01_05_Click chon thanh pho");
		filmTicketBooking.clickToDynamicTextView("Hồ Chí Minh");

		log.info("TC_01_06_Click chon cum rap Mega GS");
		filmTicketBooking.clickToDynamicTextView("BHD Star Cineplex");

		log.info("TC_01_07_Click chon rap phim");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameCinema");
		String cinemaName = listCinema.get(0);
		filmTicketBooking.clickToDynamicTextView(cinemaName);

		log.info("TC_01_08_Click xem chi tiet phim");
		FilmInfo filmInfo = filmTicketBooking.getInfoOfTheFirstFilm();
		filmTicketBooking.clickToDynamicTextView(filmInfo.filmName);
		info.filmName = filmInfo.filmName;
		info.filmDuration = filmTicketBooking.canculateDurationOfFilm(filmInfo.filmDuration);

		log.info("TC_01_09_Nhan nut Dat ve");
		filmTicketBooking.clickToTextViewByText("Đặt vé");

		log.info("TC_01_10_Nhan chon gio chieu");
		info.time = filmTicketBooking.getDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		log.info("TC_01_11_Chon 1 ghe");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");

		List<SeatType> listSeatType = filmTicketBooking.getListSeatType();
		String type = filmTicketBooking.getTypeOfSeat(listSeatType);

		log.info("TC_01_12_Click chon cho ngoi");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

		log.info("TC_01_13_Chon cho ngoi nhu da dang ky");
		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, type);
		filmTicketBooking.chooseSeats(1, colorOfSeat);

		info.cinemaName = filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle");
		info.price = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("TC_01_14_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("TC_01_15_Kiem tra Thong tin Thanh toan ve xem phim");
		log.info("TC_01_15_01: Kiem tra ten phim");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail("Phim"), info.filmName);

		log.info("TC_01_15_02: Kiem tra suat chieu");
		verifyTrue(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail("Suất chiếu").contains(info.time));

		log.info("TC_01_15_03: Kiem tra thời lượng");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail("Thời lượng"), info.filmDuration);

		log.info("TC_01_15_04: Kiem tra ten rap");
		info.cinemaAddress = filmTicketBooking.getTextViewByID("com.VCB:id/tvCinemaAddress");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail("Rạp"), info.cinemaName);

		log.info("TC_01_15_05: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInFilmTicketInfoDetail("Số tiền"), info.price);

		log.info("TC_01_16_Nhap thong tin nguoi nhan ve");
		log.info("TC_01_16_01_Nhap ten khach hang");
		filmTicketBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");

		log.info("TC_01_16_02_Nhap sdt");
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.PHONE_BOOKING, "com.VCB:id/etPhoneNumber");

		log.info("TC_01_16_03_Nhap email");
		filmTicketBooking.scrollDownToText("Thanh toán");
		filmTicketBooking.inputToDynamicInputBoxByID(FilmTicketBooking_Data.EMAIL_BOOKING, "com.VCB:id/etEmail");

		log.info("TC_01_17_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("TC_01_18_Chon tai khoan nguon");
		filmTicketBooking.clickToDynamicDropDown("Tài khoản nguồn");
		filmTicketBooking.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail("Số dư khả dụng"));

		log.info("TC_01_19_Kiem tra man hinh Thong tin mua ve");
		log.info("TC_01_19_01: Kiem tra ten phim");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Phim"), info.filmName);

		log.info("TC_01_19_02: Kiem tra suat chieu");
		verifyTrue(filmTicketBooking.getDynamicTextInTransactionDetail("Suất chiếu").contains(info.time));

		log.info("TC_01_19_03: Kiem tra thời lượng");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Thời lượng"), info.filmDuration);

		log.info("TC_01_19_04: Kiem tra ten rap");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Rạp"), info.cinemaName);

		log.info("TC_01_19_05: Kiem tra dia chi rap");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Địa chỉ"), info.cinemaAddress);

		log.info("TC_01_19_06: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Số tiền"), info.price);

		log.info("TC_01_19_07: Kiem tra ten khach hang");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Tên khách hàng"), "Duc Do");

		log.info("TC_01_19_08: Kiem tra sdt");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Số điện thoại nhận vé"), FilmTicketBooking_Data.PHONE_BOOKING);

		log.info("TC_01_19_09: Kiem tra email");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Email nhận vé"), FilmTicketBooking_Data.EMAIL_BOOKING);

		log.info("TC_01_20: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_21: Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_21_01: Kiem tra tai khoan nguon");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_21_02: Kiem tra so tien");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Số tiền"), info.price);

		log.info("TC_01_22_Chon phuong thuc xac thuc");
		filmTicketBooking.clickToDynamicTextView("Mật khẩu đăng nhập");
		fee1 = convertAvailableBalanceCurrentcyOrFeeToLong(filmTicketBooking.getDynamicTextInTransactionDetail("VCB - Smart OTP"));
		filmTicketBooking.clickToDynamicTextView("VCB - Smart OTP");

		log.info("TC_01_22_01: Kiem tra so tien phi");
		verifyEquals(filmTicketBooking.getDynamicTextInTransactionDetail("Số tiền phí"), addCommasToLong(fee1 + "") + " VND");

		log.info("TC_01_23: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		filmTicketBooking.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		filmTicketBooking.clickToDynamicContinue(driver, "com.VCB:id/submit");
		filmTicketBooking.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_24: Kiem tra man hinh thanh toan thanh cong");
		verifyTrue(filmTicketBooking.isDynamicMessageAndLabelTextDisplayed("THANH TOÁN THÀNH CÔNG"));
		transferTime = filmTicketBooking.getTransferTimeSuccess("THANH TOÁN THÀNH CÔNG");
		transactionNumber = filmTicketBooking.getDynamicTextInTransactionDetail("Mã giao dịch");
		ticketCode = filmTicketBooking.getDynamicTextInTransactionDetail("Mã vé");

		log.info("TC_01_25_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(filmTicketBooking.isDynamicButtonDisplayed("Thực hiện giao dịch mới"));

		log.info("TC_01_26_Click Thuc hien giao dich moi");
		filmTicketBooking.clickToDynamicButton("Thực hiện giao dịch mới");
		
		log.info("TC_01_Step_26: Clich nut Home");
		filmTicketBooking.clickToDynamicImageView(driver,"com.VCB:id/ivBack");

	}

	// Vé khách sạn
	//@Test
	public void TC_05_DatPhongKhachSan_ThanhToanSmartOTP() throws InterruptedException {
		log.info("TC_03_01_Click Dat phong khach san");
		hotelBooking.clickToDynamicTextOrButtonLink("Đặt phòng khách sạn");

		log.info("TC_03_02_Click nut Dong y");
		hotelBooking.clickToDynamicButton("Đồng ý");

		log.info("TC_03_03_Tim kiem dia diem");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		hotelBooking.inputToDynamicInputBox(HotelBooking_Data.HOTEL_NAME_BOOKING_INPUT, "Tên khách sạn hoặc điểm đến");

		log.info("TC_03_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.HOTEL_NAME_BOOKING);

		log.info("TC_03_05_Click dat phong");
		hotelBooking.waitForTextViewDisplay(HotelBooking_Data.HOTEL_NAME_BOOKING);
		hotelBooking.scrollIDownToText("ĐẶT PHÒNG");
		hotelBooking.clickToDynamicTextView("ĐẶT PHÒNG");

		money = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getTextViewByID("com.VCB:id/tvTotalPrice"));

		log.info("TC_03_06_Nhap ten khach hang");
		hotelBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");

		log.info("TC_03_07_Nhap so dien thoai khach hang");
		hotelBooking.inputToDynamicInputBoxByID(HotelBooking_Data.PHONE_BOOKING, "com.VCB:id/etCustomerPhone");

		log.info("TC_03_08_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID(HotelBooking_Data.EMAIL_BOOKING, "com.VCB:id/etCustomerEmail");

		log.info("TC_03_09_Click Thanh toan");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("THANH TOÁN");

		log.info("TC_03_10_Chon tai khoan nguon");
		hotelBooking.clickToDynamicDropDown("Tài khoản nguồn");
		hotelBooking.clickToDynamicTextOrButtonLink(Account_Data.Valid_Account.ACCOUNT2);
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_03_11_Kiem tra thong tin hoa don");
		hotelBooking.scrollDownToButton(driver, "Tiếp tục");

		log.info("TC_03_12_Click Tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_13_Chon phuong thuc xac thuc");
		hotelBooking.clickToDynamicDropDown("Chọn phương thức xác thực");
		fee1 = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getDynamicTextInTransactionDetail(driver, "VCB - Smart OTP"));
		hotelBooking.clickToDynamicTextOrButtonLink("VCB - Smart OTP");

		log.info("TC_03_14_Click tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		hotelBooking.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		hotelBooking.clickToDynamicContinue(driver, "com.VCB:id/submit");
		hotelBooking.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_03_24: Kiem tra man hinh thanh toan thanh cong");
		verifyTrue(hotelBooking.isDynamicMessageAndLabelTextDisplayed("THANH TOÁN THÀNH CÔNG"));
		transferTime = hotelBooking.getTransferTimeSuccess("THANH TOÁN THÀNH CÔNG");
		transactionNumber = hotelBooking.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");
		ticketCode = hotelBooking.getDynamicTextInTransactionDetail(driver, "Mã nhận phòng");

		log.info("TC_03_25_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(hotelBooking.isDynamicButtonDisplayed("Thực hiện giao dịch mới"));

		log.info("TC_03_26_Click Thuc hien giao dich moi");
		hotelBooking.clickToDynamicButton("Thực hiện giao dịch mới");
		
		log.info("TC_01_Step_26: Clich nut Home");
		hotelBooking.clickToDynamicImageViewID(driver,"com.VCB:id/ivBack");
	}

// Vé xe
//	@Test
	public void TC_06_MuaVeXeBangSmartOTP() throws InterruptedException {
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);
		vehicalTicket.clickToDynamicButton("Đồng ý");

		log.info("==========TC_02_Step_02: Chon va nhap diem di");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_1, VehicalData.DATA_ORDER_TICKET.DESTINATION);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("========TC_02_Step_03: Chon va nhap diem den");
		vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_3, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_3);

		log.info("=====TC_02_Step_04: Chon ngay muon di");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TOMORROW);

		log.info("TC_02_Step_05: Tim kiem chuyen di");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP);

		log.info("TC_02_Step_06: Chon ghe: ");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TITLE_CHOISE_CHAIR);
	//	trainTicket.chooseSeats(1, "255,255,255");

		log.info("TC_02_Step_07 : Dat chuyen di: ");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.BOOK_SEAT);

		log.info("----TC_02_Step_08: Chon ben diem di: ");
		listActual = vehicalTicket.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/layout");
		vehicalTicket.clickToElement(driver, listActual.get(1));
	
		vehicalTicket.clickToElement(CommonPageUIs.DYNAMIC_POINT_ARRVAL, "com.VCB:id/tvAddress");

		log.info("----TC_02_Step_9 Chon ben diem den: ");
		vehicalTicket.clickToElement(CommonPageUIs.DYNAMIC_POINT_ARRVAL, "com.VCB:id/tvAddress");

		log.info("-TC_02_Step_10 Click chon tiep tuc ");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("-----TC_02_Step_11___Input email");
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.EMAIL, VehicalData.DATA_ORDER_TICKET.INPUT_INFO);

		log.info("TC_02_Step_12 Lay thong tin ca nhan");

		log.info("TC_02_Step_13 ho va ten");
		nameTyped = vehicalTicket.getDynamicEditText("com.VCB:id/full_name");

		log.info("TC_02_Step_14 So dien thoai ");
		phoneTyped = vehicalTicket.getDynamicEditText("com.VCB:id/autoCompletePhone");

		log.info("TC_02_Step_15Email");
		emailTyped = vehicalTicket.getDynamicEditText("com.VCB:id/email");

		log.info("TC_02_Step_16 thon tin diem xuat phat");
		diemDi = vehicalTicket.getDynamicTextView("com.VCB:id/tvFrom");

		log.info("TC_02_Step_17 Thong tin diem den");
		diemDen = vehicalTicket.getDynamicTextView("com.VCB:id/tvTo");

		log.info("TC_02_Step_18 Hang xe ");
		hangXe = vehicalTicket.getDynamicTextView("com.VCB:id/tv_hang_xe");

		log.info("TC_02_Step_19  So ghe");
		soGhe = vehicalTicket.getDynamicTextView("com.VCB:id/tvAllSeat");

		log.info("TC_02_Step_20 So luong ghe ngoi");
		soLuongVe = vehicalTicket.getDynamicTextView("com.VCB:id/tvTicketNumber");

		log.info("TC_02_Step_21 Tong so tien can thanh toan ");
		tongTien = vehicalTicket.getDynamicTextView("com.VCB:id/tvTotalAmount");

		log.info("TC_02_Step_22 Click button tiep tuc");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_02_Step_23Verify hien thi man hinh thong tin khach hang");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.INFO_CUSTOMER));

		log.info("-TC_02_Step_24 Verify ho va ten khach hang----");
		verifyEquals(nameTyped, vehicalTicket.getDynamicEditText("com.VCB:id/full_name"));

		log.info("-TC_02_Step_25 Verify So dien thoai ----");
		verifyEquals(phoneTyped, vehicalTicket.getDynamicEditText("com.VCB:id/autoCompletePhone"));

		log.info("-TC_02_Step_26 Verify Email----");
		verifyEquals(emailTyped, vehicalTicket.getDynamicEditText("com.VCB:id/email"));

		log.info("-TC_02_Step_27 Verify diem di----");
		verifyEquals(diemDi, vehicalTicket.getDynamicTextView("com.VCB:id/tvFrom"));

		log.info("-TC_02_Step_28 Verify diem den---");
		verifyEquals(diemDen, vehicalTicket.getDynamicTextView("com.VCB:id/tvTo"));

		log.info("-TC_02_Step_30 Verify hhang Xe---");
		verifyEquals(hangXe, vehicalTicket.getDynamicTextView("com.VCB:id/tv_hang_xe"));

		log.info("-TC_02_Step_31 Verify So ghe ngoi----");
		verifyEquals(soGhe, vehicalTicket.getDynamicTextView("com.VCB:id/tvAllSeat"));

		log.info("-TC_02_Step_32 Verify tong so ve---");
		verifyEquals(soLuongVe, vehicalTicket.getDynamicTextView("com.VCB:id/tvTicketNumber"));

		log.info("-----TC_02_Step_33 Verify tong tien ");
		verifyEquals(tongTien, vehicalTicket.getDynamicTextView("com.VCB:id/tvTotalAmount"));

		log.info("TC_02_Step_34 Click btutton Thanh toan");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.THANHTOAN);

		log.info("--TC_02_Step_35 Verify hien thi man hinh thong tin ve xe");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.INFO_TICKET));

		log.info("---TC_02_Step_36 Lay thong tin tai khoan nguon");
		vehicalTicket.scrollUpToText(VehicalData.DATA_ORDER_TICKET.AMOUNT_ROOT);
		taiKhoanNguon = vehicalTicket.getDynamicTextView("com.VCB:id/number_account");

		log.info("-TC_02_Step_37 Verify ho va ten khach hang----");
		verifyEquals(nameTyped, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.NAME));

		log.info("-TC_02_Step_38 Verify ho va ten khach hang----");
		verifyEquals(phoneTyped, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.PHONE_NUMBER));

		log.info("-TC_02_Step_39 Verify Email----");
		verifyEquals(emailTyped, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.EMAIL_ADDRESS));
		vehicalTicket.scrollIDownOneTime(driver);

		log.info("-TC_02_Step_40 Verify diem di ----");
		verifyEquals(diemDi, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.DESTINATION));

		log.info("-TC_02_Step_41 Verify diem den ----");
		verifyEquals(diemDen, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.ARRIVAL));

		log.info("-TC_02_Step_42 Verify hang xe----");
		verifyEquals(hangXe, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.BRAND));

		log.info("-TC_02_Step_43 Verify So ghe----");
		verifyEquals(soGhe, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.SEAT_NUMBER));

		log.info("-TC_02_Step_44 Verify So luong ve---");
		verifyEquals(soLuongVe, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.TOTAL_TICKET));

		log.info("----TC_02_Step_45 Verify tong tien");
		tongTien = tongTien.replace("đ", "VND");
		verifyEquals(tongTien, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.AMOUNT_TT));

		log.info("-----TC_02_Step_46 Click button tiep tuc");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_02_Step_47 Xac nhan hien thi man hinh xac nhan thong tin");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.TITILE_CONFIRM));

		log.info("-TC_02_Step_48 Verrify tai khoan nguon-----");
		verifyEquals(taiKhoanNguon, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.AMOUNT_ROOT));
		maThanhToan = vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_TT);

		log.info("TC_02_Step_49 Click chon phuong thuc xac thuc");
		vehicalTicket.clickToDynamicTomorrow("com.VCB:id/llptxt");

		log.info("TC_02_Step_50- Verify hien thi man hinh cac phuong thuc xac minh");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.TITILE_SELECT));

		log.info("TC_02_Step_51- Chon hinh thuc xac thuc là mat khau");
		vehicalTicket.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		vehicalTicket.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_02_Step_52 Click button Tiep tuc");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_01_Step_Nhap ma xac thuc");
		vehicalTicket.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		vehicalTicket.clickToDynamicContinue(driver, "com.VCB:id/submit");
		vehicalTicket.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("---TC_02_Step_55 Verify hien thi man hinh giao dich thanh cong");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.NOTIFICATION.NOTI_SUCCESS));

		log.info("---TC-O2_Step_56Verify thông tin ma thanh toan");
		verifyEquals(maThanhToan, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_TT));

		log.info("-TC-O2_Step_57 Lấy thông tin mã giao dịch");
		maGiaodich = vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_GD);

		log.info("TC-O2_Step_58 Verify số tiền thanh toán");
		tongTien = tongTien.replace("đ", "VND");
		verifyEquals(tongTien, vehicalTicket.getDynamicTextView("com.VCB:id/tvAmount"));
		
		vehicalTicket.clickToDynamicButton("Thực hiện giao dịch mới");
		
		log.info("TC_01_Step_26: Clich nut Home");
		vehicalTicket.clickToDynamicImageViewID(driver,"com.VCB:id/ivTitleLeft");

	}
	
	//Mua sắm trực tuyến
	@Parameters({ "otp" })
	@Test
	public void TC_07_ChonMuaMotSanPhamThanhToanSmartOTPKhongChonKhuyenMai(String otp) throws InterruptedException {
		log.info("---------------------------TC_01_STEP_2: Them vao gio hang--------------------------------------");
		shopping.clickToDynamicCategories("Xem tất cả");
		List<String> listProduct = shopping.getTextInListElementsProduct(ShoppingOnlinePageUIs.PRODUCT_VIEW_BY_CONTAIN_TEXT, "đ");
		for (int i = 0; i < listProduct.size(); i++) {
			shopping.clickToDynamicTextContains(listProduct.get(i));
			indexHang = i;
			if (shopping.isTextDisplayedInPageSource(driver, "Tạm hết hàng")) {
				log.info("---------------------------TC_01_STEP_5: click Back---------------------------");
				shopping.clickToDynamicCart("1", "0");
				continue;
			} else {
				break;
			}
		}

		log.info("---------------------------TC_01_STEP_4: click dat hang---------------------------");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");

		log.info("---------------------------TC_01_STEP_4: click Vao gio hang---------------------------");
		shopping.clickToDynamicDateInDateTimePicker("1");

		log.info("---------------------------TC_01_STEP_3: lay tong tien can thanh toan---------------------------");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText("sản phẩm").replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
		log.info("---------------------------TC_01_STEP_5: click dat hang---------------------------");
		shopping.clickToDynamicButton("Đặt hàng");

		shopping.scrollDownToText(driver, "Giao hàng tiêu chuẩn");
		String feeShipping = shopping.getDynamicTextFeeShipping("Giao hàng tiêu chuẩn");
		double feeShippingD = 0;
		if (feeShipping.equals("Miễn phí")) {
			feeShipping = feeShipping.replace("Miễn phí", "0");
			feeShippingD = 0;

		} else {
			feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
			feeShippingD = Double.parseDouble(feeShipping);

		}
		double totalMoney = tottalMoneyCart + feeShippingD;

		log.info("---------------------------TC_01_STEP_5: click thanh toan---------------------------");
		shopping.clickToDynamicButton("Thanh toán");

		if (shopping.isTextDisplayedInPageSource(driver, "Bạn chưa có địa chỉ nhận hàng.")) {

			log.info("---------------------------TC_01_STEP_4_1: click the moi---------------------------");
			shopping.clickToDynamicButton("Thêm mới");

			log.info("---------------------------TC_01_STEP_4_1: Hien thi man hinh them moi dia chi---------------------------");
			shopping.isDynamicTextInfoDisplayed("Thêm mới địa chỉ");

			log.info("---------------------------TC_01_STEP_4_2: nhap ten");
			shopping.inputToDynamicInfo("Ngyen Van A", "Họ tên người nhận");

			log.info("---------------------------TC_01_STEP_4_2: nhap so dien thoai---------------------------");
			shopping.inputToDynamicInfo("0904797866", "Số điện thoại người nhận");

			log.info("---------------------------TC_01_STEP_4_3: chon tinh thanh pho---------------------------");
			shopping.clickToDynamicCustomer("Tỉnh/Thành phố");
			shopping.clickToDynamicListProvince("Thành phố Hà Nội");

			log.info("---------------------------TC_01_STEP_4_3: chon tinh quan huyen---------------------------");
			shopping.clickToDynamicCustomer("Quận/Huyện");
			shopping.clickToDynamicListProvince("Quận Ba Đình");

			log.info("---------------------------TC_01_STEP_4_3: chon tinh xa phuong---------------------------");
			shopping.clickToDynamicCustomer("Phường/Xã");
			shopping.clickToDynamicListProvince("Phường Cống Vị");

			log.info("---------------------------TC_01_STEP_4_3: dia chi cu the---------------------------");
			shopping.inputToDynamicInfo("22 abc", "Địa chỉ cụ thể (Số nhà, tên đường...)");

			log.info("---------------------------TC_01_STEP_4_3: chon hoan tat---------------------------");
			shopping.clickToDynamicButton("Hoàn tất");

			log.info("---------------------------TC_01_STEP_6: click thanh toan---------------------------");
			shopping.clickToDynamicButton("Thanh toán");
		}

		if (shopping.isTextDisplayedInPageSource(driver, "Sản phẩm không còn")) {
			shopping.clickToDynamicButton("Quay về giỏ hàng");

			shopping.clickToDynamicCart("2", "0");
			shopping.clickToDynamicButton("Mua sắm ngay");

			shopping.clickToDynamicCategories("Xem tất cả");

			for (int j = indexHang + 1; j < listProduct.size(); j++) {
				shopping.clickToDynamicTextContains(listProduct.get(j));
				if (shopping.isTextDisplayedInPageSource(driver, "Tạm hết hàng")) {
					log.info("---------------------------TC_01_STEP_5: click Back---------------------------");
					shopping.clickToDynamicCart("1", "0");
					continue;
				} else {
					break;
				}
			}
			log.info("---------------------------TC_01_STEP_4: click dat hang---------------------------");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");

			log.info("---------------------------TC_01_STEP_4: click Vao gio hang---------------------------");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("---------------------------TC_01_STEP_3: lay tong tien can thanh toan---------------------------");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText("sản phẩm").replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
			log.info("---------------------------TC_01_STEP_5: click dat hang---------------------------");
			shopping.clickToDynamicButton("Đặt hàng");

			shopping.scrollDownToText(driver, "Giao hàng tiêu chuẩn");
			feeShipping = shopping.getDynamicTextFeeShipping("Giao hàng tiêu chuẩn");
			feeShippingD = 0;
			if (feeShipping.equals("Miễn phí")) {
				feeShipping = feeShipping.replace("Miễn phí", "0");
				feeShippingD = 0;

			} else {
				feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
				feeShippingD = Double.parseDouble(feeShipping);

			}
			totalMoney = tottalMoneyCart + feeShippingD;

			log.info("---------------------------TC_01_STEP_5: click thanh toan---------------------------");
			shopping.clickToDynamicButton("Thanh toán");

			log.info("---------------------------TC_01_STEP_3: lay tong tien can thanh toan---------------------------");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText("sản phẩm").replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("---------------------------TC_01_STEP_4: click dat hang---------------------------");
			shopping.clickToDynamicButton("Thêm vào giỏ hàng");

			log.info("---------------------------TC_01_STEP_4: click Vao gio hang---------------------------");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("---------------------------TC_01_STEP_5: click dat hang---------------------------");
			shopping.clickToDynamicButton("Đặt hàng");

		}

		log.info("---------------------------TC_01_STEP_6: click thanh toan ngay---------------------------");
		shopping.clickToDynamicDateInDateTimePicker("Thanh toán ngay");
		shopping.clickToDynamicButton("Đồng ý");

		log.info("---------------------------TC_01_STEP_7: click chon tai khoan---------------------------");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		shopping.clickToDynamicButtonLinkOrLinkText(Valid_Account.ACCOUNT2);

		log.info("---------------------------TC_02_STEP_7: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextFeeShipping("Số dư khả dụng").replace("VND", "").replace(",", ""));

		log.info("---------------------------TC_02_STEP_7: lay thong tin ma don hang");
		String codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("---------------------------TC_01_STEP_9: lay ra phi giao hang---------------------------");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail("Phí giao hàng:").split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("---------------------------TC_01_STEP_9: verify fee Ship---------------------------");
		verifyEquals(feeShippingD, fee);

		log.info("---------------------------TC_01_STEP_10: giam gia---------------------------");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail("Giảm giá").split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", ""));

		// tong tien can thanh toan
		log.info("---------------------------TC_01_STEP_11: tong tien---------------------------");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail("Tổng tiền:").split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoney, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoneyBill);

		log.info("---------------------------TC_01_STEP_12: Chon thanh toan---------------------------");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("---------------------------TC_01_STEP_12: Xac minh hien thi man hinh xac nhan thong tin---------------------------");
		shopping.isDynamicMessageAndLabelTextDisplayed(driver, "Xác nhận thông tin");

		verifyEquals(shopping.getDynamicTextFeeShipping("Mã đơn hàng"), codeBill);
		verifyTrue(shopping.getDynamicTextFeeShipping("Số tiền thanh toán").contains(totalMoneyBillString[1]));

		log.info("---------------------------TC_01_STEP_13: Kiem tra tai khoan nguon---------------------------");
		String account = shopping.getMoneyByAccount("Tài khoản nguồn");
		verifyEquals(account, Valid_Account.ACCOUNT2);

		log.info("---------------------------TC_01_STEP_14: Kiem tra so tien thanh toan---------------------------");
		String[] money = (shopping.getMoneyByAccount("Số tiền thanh toán").replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", calulatorMoney + " VND");

		log.info("---------------------------TC_01_STEP_16: Chon phuong thuc thanh toan---------------------------");
		log.info("TC_01_STEP_16: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText("VCB - Smart OTP");

		log.info("TC_01_STEP_17: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_STEP_18: dien otp");
		log.info("TC_01_Step_Nhap ma xac thuc");
		shopping.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		shopping.clickToDynamicContinue(driver, "com.VCB:id/submit");
		shopping.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		shopping.isDynamicMessageAndLabelTextDisplayed(driver, "GIAO DỊCH THÀNH CÔNG");

		soDuThuc = soDuTK - moneyConfirm;

		log.info("---------------------------TC_01_STEP_19: Xac nhan thong tin ---------------------------");

		verifyEquals(shopping.getDynamicTextFeeShipping("Nhà cung cấp"), "VNSHOP");

		verifyEquals(shopping.getDynamicTextFeeShipping("Mã đơn hàng"), codeBill);

		maGiaodich = shopping.getDynamicTextFeeShipping("Mã giao dịch");

		log.info("---------------------------TC_01_STEP_20: thuc hien giao dich moi---------------------------");
		shopping.clickToDynamicButton("Thực hiện giao dịch mới");

		
		log.info("TC_01_Step_26: Clich nut Home");
		shopping.clickToDynamicImageViewID(driver,"com.VCB:id/ivTitleLeft");

	}

	public void TC_111_HuyKichHoatVCBSmartOPT() {

		log.info("---TC_01_Step_01: Click menu header---");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("--TC_01_Step_02: Click thanh Cai dat VCB-Smart OTP---");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("TC_01_Step_03: Click Cai dat VCB Smart OTP");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt VCB-Smart OTP");

		log.info("---TC_01_Step_04: Verify man hinh cai dat VCB Smart OTP---");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, "Cài đặt VCB-Smart OTP");

		log.info("-TC_02_Step_01: Click btn Huy Cai dat----");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Hủy");

		log.info("TC_02_Step_02: Verify hien thi popup xac nhan huy cai dat OTP--");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, SettingVCBSmartOTP_Data.MESSEGE_CONFIRM_CANCEL);

		log.info("TC_02_Step_03: Verify hien thi popup xac nhan huy cai dat OTP--");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Có");

//		log.info("---TC_02_Step_04: Verify xac nhan huy Smart OTP thanh cong--");
//		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, SettingVCBSmartOTP_Data.MESSEGE_CANCEL_SMART_OTP);

		log.info("-----TC_02_Step_05: verify Trang thai dã kich hoat Smart OTP");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, "Chưa kích hoạt");
	}

}
