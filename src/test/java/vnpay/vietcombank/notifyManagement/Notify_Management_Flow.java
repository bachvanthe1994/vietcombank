package vnpay.vietcombank.notifyManagement;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.testng.annotations.AfterClass;
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
import pageObjects.HomePageObject;
import pageObjects.InboxPageObject;
import pageObjects.LogInPageObject;
import pageObjects.sdk.airTicketBooking.DynamicAirTicketBookingObjects;
import pageObjects.sdk.filmTicketBooking.FilmTicketBookingPageObject;
import pageObjects.sdk.hotelBooking.HotelBookingPageObject;
import pageObjects.shopping_online.ShoppingOnlinePageObject;
import vehicalPageObject.VehicalPageObject;
import vehicalTicketBookingUI.CommonPageUIs;
import vietcombankUI.sdk.filmTicketBooking.FilmTicketBookingPageUIs;
import vietcombank_test_data.Account_Data.Valid_Account;
import vietcombank_test_data.Notify_Management_Data;
import vnpay.vietcombank.sdk.airticket.data.DomesticAirTicketBooking_Data;
import vnpay.vietcombank.sdk.hotelBooking.data.HotelBooking_Data;
import vnpay.vietcombank.sdk.vehicleTicket.data.VehicalData;

public class Notify_Management_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private FilmTicketBookingPageObject filmTicketBooking;
	private InboxPageObject inbox;
	private HotelBookingPageObject hotelBooking;
	private DynamicAirTicketBookingObjects airTicket;
	private ShoppingOnlinePageObject shopping;
	private VehicalPageObject vehicalTicket;

	private String tomorrowDay = getForWardDay(4);

	LocalDate now = LocalDate.now();
	String today = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String tomorow = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + Integer.valueOf(getCurrentDay()) + 1 + "/" + getCurrenMonth() + "/" + getCurrentYear();
	private String cinemaName, inboxContent, ticketCode, counter, hotelName, hotelAddress, roomID, ticketPrice, payID, maThanhToan, maVe;
	private boolean status;
	FilmTicketInfo info = new FilmTicketInfo();

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		home = PageFactoryManager.getHomePageObject(driver);

//		log.info("Before Class 01: Chon tab Menu");
//		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");
//
//		log.info("Before Class 02: Mo sub-menu Cai dat");
//		home.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");
//
//		log.info(" Before Class 03: An vao phan Quan ly thong bao");
//		home.scrollDownToText(driver, "Tra cứu");
//		home.clickToDynamicButtonLinkOrLinkText(driver, "Quản lý thông báo");
//
//		log.info("Before Class 05: Xac nhan hien thi nhan thong bao tu ngan hang la ON");
//		notifyManage = PageFactoryManager.getNotifyManagementPageObject(driver);
//		status = notifyManage.isDynamicImageByIdEnable(driver, "com.VCB:id/ic_switch");
//		if (status = false) {
//			throw new SkipException("");
//		}
//		log.info("TC_01_Step_01: Back ve man hinh Home");
//		notifyManage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
//
//		home = PageFactoryManager.getHomePageObject(driver);
//		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

//	@Test
	public void TC_01_TinOTTVeXemPhim_DaDangNhap() {

		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
		status = home.isDynamicTextDetailByID(driver, "com.VCB:id/counter");
		if (status == true) {
			counter = home.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter");
		} else {
			counter = "0";
		}

		log.info("TC_01_Step_02: Click Dat ve xem phim");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đặt vé xem phim");

		log.info("TC_01_Step_03: Click nut Dong y");
		home.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_01_Step_04: Click chon Tinh thanh");
		filmTicketBooking = PageFactoryManager.getFilmTicketBookingPageObject(driver);
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");

		log.info("TC_01_Step_05_Tim kiem thanh pho");
		filmTicketBooking.inputIntoEditTextByID("Hồ Chí Minh", "com.VCB:id/edtSearch");

		log.info("TC_01_Step_06_Click chon thanh pho");
		filmTicketBooking.clickToDynamicTextView("Hồ Chí Minh");

		log.info("TC_01_Step_07_Click chon cum rap Mega GS");
		filmTicketBooking.clickToDynamicTextView("BHD Star Cineplex");

		log.info("TC_01_Step_08_Click chon rap phim");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameCinema");
		cinemaName = listCinema.get(0);
		filmTicketBooking.clickToDynamicTextView(cinemaName);

		log.info("TC_01_Step_09_Click xem chi tiet phim");
		FilmInfo filmInfo = filmTicketBooking.getInfoOfTheFirstFilm();
		filmTicketBooking.clickToDynamicTextView(filmInfo.filmName);
		info.filmName = filmInfo.filmName;
		info.filmDuration = filmTicketBooking.canculateDurationOfFilm(filmInfo.filmDuration);

		log.info("TC_01_Step_10_Nhan nut Dat ve");
		filmTicketBooking.clickToTextViewByText("Đặt vé");

		log.info("TC_01_Step_11_Nhan chon gio chieu");
		info.time = filmTicketBooking.getDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		log.info("TC_01_Step_12_Chon moi loai 1 ghe");
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlus");
		List<SeatType> listSeatType = filmTicketBooking.getListSeatType();

		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");
		String type = filmTicketBooking.getTypeOfSeat(listSeatType);

		String colorOfSeat = filmTicketBooking.getColorOfElement(FilmTicketBookingPageUIs.VIEW_BY_TEXT, type);
		filmTicketBooking.chooseSeats(1, colorOfSeat);
		info.cinemaName = filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle");
		info.price = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("TC_01_Step_15_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("TC_01_Step_16_Nhap thong tin nguoi nhan ve");
		log.info("TC_01_Step_16_01_Nhap ten khach hang");
		filmTicketBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");

		log.info("TC_01_Step_16_02_Nhap sdt");
		filmTicketBooking.inputToDynamicInputBoxByID("0974862668", "com.VCB:id/etPhoneNumber");

		log.info("TC_01_Step_16_03_Nhap email");
		filmTicketBooking.scrollDownToText("Thanh toán");
		filmTicketBooking.inputToDynamicInputBoxByID("vnpay.automation.team@gmail.com", "com.VCB:id/etEmail");

		log.info("TC_01_Step_17_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("TC_01_Step_18_Chon tai khoan nguon");
		filmTicketBooking.clickToDynamicDropDown("Tài khoản nguồn");
		filmTicketBooking.clickToDynamicButtonLinkOrLinkText(driver, Notify_Management_Data.ACCOUNT.ACCOUNT_01);

		log.info("TC_01_Step_20: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_22_Chon phuong thuc xac thuc");
		filmTicketBooking.clickToDynamicTextView("Mật khẩu đăng nhập");
		filmTicketBooking.clickToDynamicTextView("Mật khẩu đăng nhập");

		log.info("TC_01_Step_23: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton("Tiếp tục");

		filmTicketBooking.inputToDynamicPopupPasswordInput("aaaa1111", "Tiếp tục");

		filmTicketBooking.clickToDynamicButton("Tiếp tục");
		ticketCode = filmTicketBooking.getDynamicTextInTransactionDetail("Mã vé");

		log.info("TC_01_Step_26_Click Thuc hien giao dich moi");
		filmTicketBooking.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_01_Step_27: Click  nut Back");
		filmTicketBooking.clickToDynamicBackIcon("Mua vé xem phim");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_28: Click vao More Icon");
		verifyEquals(home.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter"), getCounterPlus(counter, 1));
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_3");
		inbox = PageFactoryManager.getInboxPageObject(driver);

		log.info("TC_01_Step_29: Click tab tat ca");
		inbox.clickToTextID(driver, "com.VCB:id/radioAll");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(info.filmName));
		verifyTrue(inboxContent.contains(info.time));
		verifyTrue(inboxContent.contains(info.cinemaName));
		verifyTrue(inboxContent.contains(ticketCode));

		log.info("TC_01_30: Click tab quang cao");
		inbox.clickToTextID(driver, "com.VCB:id/radioOther");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(info.filmName));
		verifyTrue(inboxContent.contains(info.time));
		verifyTrue(inboxContent.contains(info.cinemaName));
		verifyTrue(inboxContent.contains(ticketCode));
	}

//	@Test
	public void TC_02_TinOTTVeXemPhim_ChuaDangNhap() {

		log.info("TC_02_Step_01: Back ve man hinh Home");
		home = PageFactoryManager.getHomePageObject(driver);
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");

		log.info("TC_02_Step_02_Click Dat ve xem phim");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đặt vé xem phim");

		log.info("TC_02_Step_03_Click nut Dong y");
		home.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_02_Step_04_Click chon Tinh thanh");
		filmTicketBooking = PageFactoryManager.getFilmTicketBookingPageObject(driver);
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");

		log.info("TC_02_Step_05_Tim kiem thanh pho");
		filmTicketBooking.inputIntoEditTextByID("Hồ Chí Minh", "com.VCB:id/edtSearch");

		log.info("TC_02_Step_06_Click chon thanh pho");
		filmTicketBooking.clickToDynamicTextView("Hồ Chí Minh");

		log.info("TC_02_Step_07_Click chon cum rap Mega GS");
		filmTicketBooking.clickToDynamicTextView("BHD Star Cineplex");

		log.info("TC_02_Step_08_Click chon rap phim");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameCinema");
		cinemaName = listCinema.get(0);
		filmTicketBooking.clickToDynamicTextView(cinemaName);

		log.info("TC_02_Step_09_Click xem chi tiet phim");
		FilmInfo filmInfo = filmTicketBooking.getInfoOfTheFirstFilm();
		filmTicketBooking.clickToDynamicTextView(filmInfo.filmName);
		info.filmName = filmInfo.filmName;
		info.filmDuration = filmTicketBooking.canculateDurationOfFilm(filmInfo.filmDuration);

		log.info("TC_02_Step_10_Nhan nut Dat ve");
		filmTicketBooking.clickToTextViewByText("Đặt vé");

		log.info("TC_02_Step_11_Nhan chon gio chieu");
		info.time = filmTicketBooking.getDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");
		filmTicketBooking.clickToDynamicTextViewByViewGroupID("com.VCB:id/tagShowtimes2D", "0");

		log.info("TC_02_Step_11_1:Chon moi loai 1 ghe");
		filmTicketBooking.clickToChooseEachTypeASeate();

		List<SeatType> listSeatType = filmTicketBooking.getListSeatType();

		log.info("TC_02_Step_12_Click chon cho ngoi");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

		log.info("TC_02_Step_13_Chon cho ngoi nhu da dang ky");
		filmTicketBooking.chooseMaxSeats(listSeatType);

		info.cinemaName = filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle");
		info.price = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("TC_02_Step_14_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("TC_02_Step_16_Nhap thong tin nguoi nhan ve");
		log.info("TC_01_Step_16_01_Nhap ten khach hang");
		filmTicketBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");

		log.info("TC_02_Step_16_02_Nhap sdt");
		filmTicketBooking.inputToDynamicInputBoxByID("0974862668", "com.VCB:id/etPhoneNumber");

		log.info("TC_02_Step_16_03_Nhap email");
		filmTicketBooking.scrollDownToText("Thanh toán");
		filmTicketBooking.inputToDynamicInputBoxByID("vnpay.automation.team@gmail.com", "com.VCB:id/etEmail");

		log.info("TC_02_Step_17_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("TC_02_Step_18_Chon tai khoan nguon");
		filmTicketBooking.clickToDynamicDropDown("Tài khoản nguồn");
		filmTicketBooking.clickToDynamicButtonLinkOrLinkText(driver, Notify_Management_Data.ACCOUNT.ACCOUNT_01);

		log.info("TC_02_Step_19: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_02_Step_20_Chon phuong thuc xac thuc");
		filmTicketBooking.clickToDynamicTextView("Mật khẩu đăng nhập");
		filmTicketBooking.clickToDynamicTextView("Mật khẩu đăng nhập");

		log.info("TC_02_Step_21: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton("Tiếp tục");

		filmTicketBooking.inputToDynamicPopupPasswordInput("aaaa1111", "Tiếp tục");

		filmTicketBooking.clickToDynamicButton("Tiếp tục");
		ticketCode = filmTicketBooking.getDynamicTextInTransactionDetail("Mã vé");

		log.info("TC_02_Step_22_Click Thuc hien giao dich moi");
		filmTicketBooking.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_02_Step_23: Click  nut home");
		filmTicketBooking.clickToDynamicBackIcon("Mua vé xem phim");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_Step_24: Xac nhan hien thi icon notify");
		verifyEquals(home.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter"), "01");

		log.info("TC_02_Step_25: Chon tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step_26: An vao Thoat ung dung");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Thoát ứng dụng");

		log.info("TC_02_Step_27: Chon dong y");
		home.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("TC_02_Step_28: Click vao Inbox");
		verifyEquals(login.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter"), "01");
		login.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivOTT");
		inbox = PageFactoryManager.getInboxPageObject(driver);

		log.info("TC_02_Step_29: Click tab tat ca");
		inbox.clickToTextID(driver, "com.VCB:id/radioAll");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(info.filmName));
		verifyTrue(inboxContent.contains(info.time));
		verifyTrue(inboxContent.contains(info.cinemaName));
		verifyTrue(inboxContent.contains(ticketCode));

		log.info("TC_02_Step_30: Click tab quang cao");
		inbox.clickToTextID(driver, "com.VCB:id/radioOther");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(info.filmName));
		verifyTrue(inboxContent.contains(info.time));
		verifyTrue(inboxContent.contains(info.cinemaName));
		verifyTrue(inboxContent.contains(ticketCode));

		log.info("TC_02_Step_31: Click back ve Login");
		inbox.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("TC_02_Step_32: Nhap mat khau va log in");
		login.inputIntoEditTextByID(driver, "aaaa1111", "com.VCB:id/edtInput");
		login.clickToDynamicAcceptButton(driver, "com.VCB:id/btnNext");

		log.info("TC_02_Step_33: Hien thi man hinh Home");
		home = PageFactoryManager.getHomePageObject(driver);
	}

//	@Test
	public void TC_03_TinOTTDatVeKhachSan_DaDangNhap() {

		log.info("TC_03_Step_01_Click Dat phong khach san");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đặt phòng khách sạn");

		log.info("TC_03_Step_02_Click nut Dong y");
		home.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_03_Step_03_Tim kiem dia diem");
		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		hotelBooking.inputToDynamicInputBox(HotelBooking_Data.HOTEL_NAME_BOOKING_INPUT, "Tên khách sạn hoặc điểm đến");

		log.info("TC_03_Step_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.HOTEL_NAME_BOOKING);

		log.info("TC_03_Step_05_Click dat phong");
		hotelName = hotelBooking.getTextViewByID("com.VCB:id/tvHotelName");
		hotelAddress = hotelBooking.getTextViewByID("com.VCB:id/tvAddress");
		hotelBooking.waitForTextViewDisplay(HotelBooking_Data.HOTEL_NAME_BOOKING);
		hotelBooking.scrollIDownToText("ĐẶT PHÒNG");
		hotelBooking.clickToDynamicTextView("ĐẶT PHÒNG");

		log.info("TC_03_Step_06_Nhap ten khach hang");
		hotelBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");

		log.info("TC_03_Step_07_Nhap so dien thoai khach hang");
		hotelBooking.inputToDynamicInputBoxByID("0976878903", "com.VCB:id/etCustomerPhone");

		log.info("TC_03_Step_08_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID("vnpay.automation.team@gmail.com", "com.VCB:id/etCustomerEmail");

		log.info("TC_03_Step_09_Click Thanh toan");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("THANH TOÁN");

		log.info("TC_03_Step_10_Chon tai khoan nguon");
		hotelBooking.clickToDynamicDropDown("Tài khoản nguồn");
		hotelBooking.clickToDynamicTextOrButtonLink(Notify_Management_Data.ACCOUNT.ACCOUNT_02);

		log.info("TC_03_Step_11_Kiem tra thong tin hoa don");
		hotelBooking.scrollDownToButton(driver, "Tiếp tục");

		log.info("TC_03_Step_12_Click Tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_Step_13_Chon phuong thuc xac thuc");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvptxt");
		hotelBooking.clickToDynamicTextOrButtonLink("SMS OTP");

		log.info("TC_03_Step_14_Click tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_Step_15_Nhap ma OTP chinh xac");
		hotelBooking.inputToDynamicOtpOrPIN("123456", "Tiếp tục");

		log.info("TC_03_Step_16_Click tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_Step_17_Kiem tra nut Thuc hien giao dich moi");
		roomID = hotelBooking.getDynamicTextByLabel("Mã nhận phòng");
		verifyTrue(hotelBooking.isDynamicButtonDisplayed("Thực hiện giao dịch mới"));

		log.info("TC_03_Step_18_Click Thuc hien giao dich moi");
		hotelBooking.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_03_Step_19: Click  nut Back");
		hotelBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_03_Step_20: Click vao More Icon");
		verifyEquals(home.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter"), "01");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_3");
		inbox = PageFactoryManager.getInboxPageObject(driver);

		log.info("TC_03_Step_21: Click tab tat ca");
		inbox.clickToTextID(driver, "com.VCB:id/radioAll");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(hotelName));
		verifyTrue(inboxContent.contains(hotelAddress));
		verifyTrue(inboxContent.contains(roomID));

		log.info("TC_03_Step_22: Click tab quang cao");
		inbox.clickToTextID(driver, "com.VCB:id/radioOther");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(hotelName));
		verifyTrue(inboxContent.contains(hotelAddress));
		verifyTrue(inboxContent.contains(roomID));
	}

//	@Test
	public void TC_04_TinOTTDatVeKhachSan_ChuaDangNhap() {

		log.info("TC_04_Step_01: Back ve man hinh Home");
		home = PageFactoryManager.getHomePageObject(driver);
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");

		log.info("TC_04_Step_01_1: Click Dat phong khach san");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đặt phòng khách sạn");

		log.info("TC_04_Step_02_Click nut Dong y");
		home.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_04_Step_03_Tim kiem dia diem");
		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		hotelBooking.inputToDynamicInputBox(HotelBooking_Data.HOTEL_NAME_BOOKING_INPUT, "Tên khách sạn hoặc điểm đến");

		log.info("TC_04_Step_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.HOTEL_NAME_BOOKING);

		log.info("TC_04_Step_05_Click dat phong");
		hotelName = hotelBooking.getTextViewByID("com.VCB:id/tvHotelName");
		hotelAddress = hotelBooking.getTextViewByID("com.VCB:id/tvAddress");
		hotelBooking.waitForTextViewDisplay(HotelBooking_Data.HOTEL_NAME_BOOKING);
		hotelBooking.scrollIDownToText("ĐẶT PHÒNG");
		hotelBooking.clickToDynamicTextView("ĐẶT PHÒNG");

		log.info("TC_04_Step_06_Nhap ten khach hang");
		hotelBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");

		log.info("TC_04_Step_07_Nhap so dien thoai khach hang");
		hotelBooking.inputToDynamicInputBoxByID("0976878903", "com.VCB:id/etCustomerPhone");

		log.info("TC_04_Step_08_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID("vnpay.automation.team@gmail.com", "com.VCB:id/etCustomerEmail");

		log.info("TC_04_Step_09_Click Thanh toan");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("THANH TOÁN");

		log.info("TC_04_Step_10_Chon tai khoan nguon");
		hotelBooking.clickToDynamicDropDown("Tài khoản nguồn");
		hotelBooking.clickToDynamicTextOrButtonLink(Notify_Management_Data.ACCOUNT.ACCOUNT_02);

		log.info("TC_04_Step_11_Kiem tra thong tin hoa don");
		hotelBooking.scrollDownToButton(driver, "Tiếp tục");

		log.info("TC_04_Step_12_Click Tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_04_Step_13_Chon phuong thuc xac thuc");
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvptxt");
		hotelBooking.clickToDynamicTextOrButtonLink("SMS OTP");

		log.info("TC_04_Step_14_Click tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_04_Step_15_Nhap ma OTP chinh xac");
		hotelBooking.inputToDynamicOtpOrPIN("123456", "Tiếp tục");

		log.info("TC_04_Step_16_Click tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_04_Step_17_Kiem tra nut Thuc hien giao dich moi");
		roomID = hotelBooking.getDynamicTextByLabel("Mã nhận phòng");
		verifyTrue(hotelBooking.isDynamicButtonDisplayed("Thực hiện giao dịch mới"));

		log.info("TC_04_Step_18_Click Thuc hien giao dich moi");
		hotelBooking.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_04_Step_19: Click  nut Back");
		hotelBooking.clickToDynamicImageViewByID("com.VCB:id/ivBack");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_04_Step_20: Xac nhan hien thi icon notify");
		verifyEquals(home.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter"), "01");

		log.info("TC_04_Step_21: Chon tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_04_Step_22: An vao Thoat ung dung");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Thoát ứng dụng");

		log.info("TC_04_Step_23: Chon dong y");
		home.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("TC_04_Step_24: Click vao Inbox");
		verifyEquals(login.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter"), "01");
		login.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivOTT");
		inbox = PageFactoryManager.getInboxPageObject(driver);

		log.info("TC_04_Step_25: Click tab tat ca");
		inbox.clickToTextID(driver, "com.VCB:id/radioAll");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(hotelName));
		verifyTrue(inboxContent.contains(hotelAddress));
		verifyTrue(inboxContent.contains(roomID));

		log.info("TC_04_Step_26: Click tab quang cao");
		inbox.clickToTextID(driver, "com.VCB:id/radioOther");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(hotelName));
		verifyTrue(inboxContent.contains(hotelAddress));
		verifyTrue(inboxContent.contains(roomID));

		log.info("TC_04_Step_27: Click back ve Login");
		inbox.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("TC_04_Step_28: Nhap mat khau va log in");
		login.inputIntoEditTextByID(driver, "aaaa1111", "com.VCB:id/edtInput");
		login.clickToDynamicAcceptButton(driver, "com.VCB:id/btnNext");

		log.info("TC_04_Step_29: Hien thi man hinh Home");
		home = PageFactoryManager.getHomePageObject(driver);
	}

//	@Parameters({ "otp" })
//	@Test
	public void TC_05_TinOTTVeMayBay_DadangNhap(String otp) {

		log.info("TC_05_Step_01: Click vao phan Dat ve may bay");
		home.clickToDynamicIcon(driver, "Đặt vé máy bay");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);

		log.info("TC_05_Step_02: Click dat ve may bay noi dia ");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("TC_05_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_05_Step_04: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink("Một chiều");

		log.info("TC_05_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");

		log.info("TC_05_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		airTicket.clickToDynamicTextOrButtonLink("Đà Nẵng");

		log.info("TC_05_Step_07: Chon ngay di");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(1), tomorrowDay);
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_05_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_05_Step_09: Chon chuyen bay va dat ve");
		airTicket.clickToDynamicFlight(0, "VJ");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");
		airTicket.clickToDynamicTextByID("com.VCB:id/btn_book");

		log.info("TC_05_Step_10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_05_Step_11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_05_Step 12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_05_Step 13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_05_Step 14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content");

		log.info("TC_05_Step 15: Dien ten nguoi lon 1 ");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten");

		log.info("TC_05_Step_16: Chon gioi tinh nu");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");

		log.info("TC_05_Step_17: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_05_Step_18: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Xác nhận chuyến bay");

		log.info("TC_05_Step_19: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền", "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_05_Step_20: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_05_Step_21: Click Thanh toan");
		airTicket.clickToDynamicButton("Thanh toán");

		log.info("TC_05_Step_22: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		airTicket.clickToDynamicTextOrButtonLink(Notify_Management_Data.ACCOUNT.ACCOUNT_03);

		log.info("TC_05_Step_23: Click Tiep tuc");
		payID = airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền thanh toán", "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_05_Step 24: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_05_Step_25: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Số tiền", "com.VCB:id/tvAmount"), ticketPrice);

		log.info("TC_05_Step_26: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink("SMS OTP");

		log.info("TC_05_Step_27: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_05_Step_28: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicOtp(driver, otp, "Tiếp tục");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_05_Step_29: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent"), payID);

		log.info("TC_05_Step_30: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_05_Step_31: Click vao More Icon");
		verifyEquals(home.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter"), "01");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_3");
		inbox = PageFactoryManager.getInboxPageObject(driver);

		log.info("TC_05_Step_32: Click tab tat ca");
		inbox.clickToTextID(driver, "com.VCB:id/radioAll");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains("Mã thanh toán:" + payID));
		verifyTrue(inboxContent.contains("Số tiền:" + ticketPrice.replace(" VND", "")));

		log.info("TC_05_Step_33: Click tab quang cao");
		inbox.clickToTextID(driver, "com.VCB:id/radioOther");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains("Mã thanh toán:" + payID));
		verifyTrue(inboxContent.contains("Số tiền:" + ticketPrice.replace(" VND", "")));
	}

//	@Parameters({ "otp" })
//	@Test
	public void TC_06_TinOTTVeMayBay_ChuadangNhap(String otp) {

		log.info("TC_06_Step_01: Back ve man hinh Home");
		home = PageFactoryManager.getHomePageObject(driver);
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");

		log.info("TC_06_Step_02: Click vao phan Dat ve may bay");
		home.clickToDynamicIcon(driver, "Đặt vé máy bay");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);

		log.info("TC_06_Step_03: Click dat ve may bay noi dia ");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("TC_06_Step_04: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_06_Step_05: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink("Một chiều");

		log.info("TC_06_Step_06: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");

		log.info("TC_06_Step_07: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		airTicket.clickToDynamicTextOrButtonLink("Đà Nẵng");

		log.info("TC_06_Step_07: Chon ngay di");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(1), tomorrowDay);
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_06_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_06_Step_09: Chon chuyen bay va dat ve");
		airTicket.clickToDynamicFlight(0, "VJ");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");
		airTicket.clickToDynamicTextByID("com.VCB:id/btn_book");

		log.info("TC_06_Step_10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_06_Step_11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_06_Step_12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_06_Step_13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_06_Step_14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content");

		log.info("TC_06_Step_15: Dien ten nguoi lon 1 ");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten");

		log.info("TC_06_Step_16: Chon gioi tinh nu");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");

		log.info("TC_06_Step_17: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_06_Step 18: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Xác nhận chuyến bay");

		log.info("TC_06_Step 19: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền", "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_06_Step_20: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_06_Step_21: Click Thanh toan");
		airTicket.clickToDynamicButton("Thanh toán");

		log.info("TC_06_Step_22: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		airTicket.clickToDynamicTextOrButtonLink(Notify_Management_Data.ACCOUNT.ACCOUNT_03);

		log.info("TC_06_Step_23: Click Tiep tuc");
		payID = airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền thanh toán", "com.VCB:id/tvContent"), ticketPrice);

		log.info("TC_06_Step 24: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_06_Step_25: Xac nhan giao dich");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvBooking_number"), payID);
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Số tiền", "com.VCB:id/tvAmount"), ticketPrice);

		log.info("TC_06_Step_26: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink("SMS OTP");

		log.info("TC_06_Step_27: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_06_Step_28: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicOtp(driver, otp, "Tiếp tục");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_06_Step_29: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent"), payID);

		log.info("TC_06_Step_30: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_06_Step_31: Xac nhan hien thi icon notify");
		verifyEquals(home.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter"), "01");

		log.info("TC_06_Step_32: Chon tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_06_Step_33: An vao Thoat ung dung");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Thoát ứng dụng");

		log.info("TC_06_Step_33: Chon dong y");
		home.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("TC_06_Step_34: Click vao Inbox");
		verifyEquals(login.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter"), "01");
		login.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivOTT");
		inbox = PageFactoryManager.getInboxPageObject(driver);

		log.info("TC_06_Step_35: Click tab tat ca");
		inbox.clickToTextID(driver, "com.VCB:id/radioAll");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains("Mã thanh toán:" + payID));
		verifyTrue(inboxContent.contains("Số tiền:" + ticketPrice.replace(" VND", "")));

		log.info("TC_06_Step_36: Click tab quang cao");
		inbox.clickToTextID(driver, "com.VCB:id/radioOther");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains("Mã thanh toán:" + payID));
		verifyTrue(inboxContent.contains("Số tiền:" + ticketPrice.replace(" VND", "")));

		log.info("TC_06_Step_37: Click back ve Login");
		inbox.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("TC_06_Step_38: Nhap mat khau va log in");
		login.inputIntoEditTextByID(driver, "aaaa1111", "com.VCB:id/edtInput");
		login.clickToDynamicAcceptButton(driver, "com.VCB:id/btnNext");

		log.info("TC_06_Step_39: Hien thi man hinh Home");
		home = PageFactoryManager.getHomePageObject(driver);
	}

//	@Parameters({ "otp" })
//	@Test
	public void TC_07_TinOTTVeMayBayThanhToanNgay_DadangNhap(String otp) {

		log.info("TC_07_Step_01: Click vao phan Dat ve may bay");
		home.clickToDynamicIcon(driver, "Đặt vé máy bay");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);

		log.info("TC_07_Step_02: Click dat ve may bay noi dia ");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("TC_07_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_07_Step_04: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink("Một chiều");

		log.info("TC_07_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");

		log.info("TC_07_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		airTicket.clickToDynamicTextOrButtonLink("TP Hồ Chí Minh");

		log.info("TC_07_Step_07: Chon ngay di");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(1), tomorrowDay);
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_07_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_07_Step_09: Chon chuyen bay va dat ve");
		airTicket.clickToDynamicFlight(0, "BL");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");
		airTicket.clickToDynamicTextByID("com.VCB:id/btn_book");

		log.info("TC_07_Step_10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_07_Step_11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_07_Step_12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_07_Step_13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_07_Step_14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content");

		log.info("TC_07_Step_15: Dien ten nguoi lon 1 ");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten");

		log.info("TC_07_Step_16: Chon gioi tinh nu");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");

		log.info("TC_07_Step_17: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_07_Step_18: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Xác nhận chuyến bay");

		log.info("TC_07_Step_19: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền", "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_07_Step_20: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_07_Step_21: Click Thanh toan sau");
		airTicket.clickToDynamicButton("Thanh toán sau");

		log.info("TC_07_Step_22: Hien thi man hinh Ket qua");
		payID = airTicket.getDynamicTextByID("com.VCB:id/tvBookCode");

		log.info("TC_07_Step_23: Ve man hinh ve may bay");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleRight");

		log.info("TC_07_Step_24: Chon Thanh toan ve may bay");
		airTicket.clickToDynamicTextOrButtonLink("Thanh toán vé máy bay");

		log.info("TC_07_Step_25: Nhap ma thanh toan");
		airTicket.inputToDynamicInputBoxById(payID, "com.VCB:id/edtBookCode");

		log.info("TC_07_Step_26: An tiep tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_07_Step_27: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		airTicket.clickToDynamicTextOrButtonLink(Notify_Management_Data.ACCOUNT.ACCOUNT_04);

		log.info("TC_07_Step_28: An tiep tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_07_Step_29: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink("SMS OTP");

		log.info("TC_07_Step_30: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_07_Step_31: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicOtp(driver, otp, "Tiếp tục");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_07_Step_32: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent"), payID);

		log.info("TC_07_Step_33: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		home = PageFactoryManager.getHomePageObject(driver);
		home.sleep(driver, 10000);

		log.info("TC_07_Step_34: Xac nhan hien thi icon notify");
		verifyEquals(home.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter"), "02");

		log.info("TC_07_Step_35: Chon tab inbox");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_3");

		log.info("TC_07_Step_36: Click tab tat ca");
		inbox = PageFactoryManager.getInboxPageObject(driver);
		inbox.clickToTextID(driver, "com.VCB:id/radioAll");
		home.sleep(driver, 5000);

		log.info("TC_07_Step_37: Click tab quang cao");
		inbox.clickToTextID(driver, "com.VCB:id/radioOther");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains("Mã thanh toán:" + payID));
		verifyTrue(inboxContent.contains("Số tiền:" + ticketPrice.replace(" VND", "")));

		log.info("TC_07_Step_38: Click tab tat ca");
		inbox.clickToTextID(driver, "com.VCB:id/radioAll");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains("Mã thanh toán:" + payID));
		verifyTrue(inboxContent.contains("Số tiền:" + ticketPrice.replace(" VND", "")));
	}

//	@Parameters({ "otp" })
//	@Test
	public void TC_08_TinOTTVeMayBayThanhToanNgay_ChuadangNhap(String otp) {

		log.info("TC_08_Step_01_1: Back ve man hinh Home");
		home = PageFactoryManager.getHomePageObject(driver);
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");

		log.info("TC_08_Step_01_2: Click vao phan Dat ve may bay");
		home.clickToDynamicIcon(driver, "Đặt vé máy bay");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);

		log.info("TC_08_Step_02: Click dat ve may bay noi dia ");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("TC_08_Step_03: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_08_Step_04: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink("Một chiều");

		log.info("TC_08_Step_05: Chon diem khoi hanh");
		airTicket.clickToDynamicTextOrButtonLink("Khởi hành");
		airTicket.clickToDynamicTextOrButtonLink("Hà Nội");

		log.info("TC_08_Step_06: Chon diem den");
		airTicket.clickToDynamicTextOrButtonLink("Điểm đến");
		airTicket.clickToDynamicTextOrButtonLink("TP Hồ Chí Minh");

		log.info("TC_08_Step_07: Chon ngay di");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(1), tomorrowDay);
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_08_Step_08: Tim chuyen bay");
		airTicket.clickToDynamicButton("Tìm chuyến bay");

		log.info("TC_08_Step_09: Chon chuyen bay va dat ve");
		airTicket.clickToDynamicFlight(0, "BL");
		ticketPrice = airTicket.getDynamicTextByID("com.VCB:id/tv_amount_flight_selected");
		airTicket.clickToDynamicTextByID("com.VCB:id/btn_book");

		log.info("TC_08_Step_10: Dien Ten Nguoi lon ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_hoten");

		log.info("TC_08_Step_11: Chon gioi tinh nu");
		airTicket.checkToDynamicTextOrDropDownByLabel("THÔNG TIN LIÊN HỆ", "com.VCB:id/tv_NuContact");

		log.info("TC_08_Step_12: Dien Email  ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_EMAIL, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_email");

		log.info("TC_08_Step_13: Dien Phone ");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.ADULT_PHONE, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_phonenumber");

		log.info("TC_08_Step_14: Dien Noi dung");
		airTicket.inputToDynamicInputBoxByLabel(DomesticAirTicketBooking_Data.validInput.CONTENT, "THÔNG TIN LIÊN HỆ", "com.VCB:id/edt_content");

		log.info("TC_08_Step_15: Dien ten nguoi lon 1 ");
		airTicket.inputToDynamicInputBoxByLabelAndIndex(DomesticAirTicketBooking_Data.validInput.ADULT_NAME, "com.VCB:id/recy_info_book", "0", "com.VCB:id/edt_hoten");

		log.info("TC_08_Step_16: Chon gioi tinh nu");
		airTicket.clickToDynamicTextOrDropDownByLabelAndIndex("com.VCB:id/recy_info_book", "0", "com.VCB:id/ll_NuNoiDia", "com.VCB:id/tv_NuNoiDia");

		log.info("TC_08_Step_17: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_08_Step_18: Kiem tra man hinh xac nhan chuyen bay hien thi");
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvTitle1"), "Xác nhận chuyến bay");

		log.info("TC_08_Step_19: Kiem tra tong so tien hien thi dung");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Tổng tiền", "com.VCB:id/tvText2"), ticketPrice);

		log.info("TC_08_Step_20: Click checkbox dong y dieu khoan dat ve");
		airTicket.clickToDynamicCheckBoxByID("com.VCB:id/checkBoxCondition");

		log.info("TC_08_Step_21: Click Thanh toan sau");
		airTicket.clickToDynamicButton("Thanh toán sau");

		log.info("TC_08_Step_22: Hien thi man hinh Ket qua");
		payID = airTicket.getDynamicTextByID("com.VCB:id/tvBookCode");

		log.info("TC_08_Step_23: Ve man hinh ve may bay");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleRight");

		log.info("TC_08_Step_24: Chon Thanh toan ve may bay");
		airTicket.clickToDynamicTextOrButtonLink("Thanh toán vé máy bay");

		log.info("TC_08_Step_25: Nhap ma thanh toan");
		airTicket.inputToDynamicInputBoxById(payID, "com.VCB:id/edtBookCode");

		log.info("TC_08_Step_26: An tiep tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_08_Step_27: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		airTicket.clickToDynamicTextOrButtonLink(Notify_Management_Data.ACCOUNT.ACCOUNT_04);

		log.info("TC_08_Step_28: An tiep tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_08_Step_29: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink("SMS OTP");

		log.info("TC_08_Step_30: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_08_Step_31: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicOtp(driver, otp, "Tiếp tục");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_08_Step_32: Xac nhan hien thi man hinh thanh toan thanh cong");
		verifyEquals(airTicket.getAirTicketPriceInfo1Way("Mã thanh toán", "com.VCB:id/tvContent"), payID);

		log.info("TC_08_Step_33: Clich nut Home");
		airTicket.clickToDynamicIcon("com.VCB:id/ivHome");
		home = PageFactoryManager.getHomePageObject(driver);
		home.sleep(driver, 10000);

		log.info("TC_08_Step_34: Xac nhan hien thi icon notify");
		counter = home.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter");

		log.info("TC_08_Step_35: Chon tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_08_Step_36: An vao Thoat ung dung");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Thoát ứng dụng");

		log.info("TC_08_Step_37: Chon dong y");
		home.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		login = PageFactoryManager.getLoginPageObject(driver);
		login.sleep(driver, 5000);

		log.info("TC_08_Step_38: Click vao Inbox");
		verifyEquals(login.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter"), counter);
		login.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivOTT");
		inbox = PageFactoryManager.getInboxPageObject(driver);

		log.info("TC_08_Step_39: Click tab tat ca");
		inbox.clickToTextID(driver, "com.VCB:id/radioAll");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains("Mã thanh toán:" + payID));
		verifyTrue(inboxContent.contains("Số tiền:" + ticketPrice.replace(" VND", "")));

		log.info("TC_08_Step_40: Click tab quang cao");
		inbox.clickToTextID(driver, "com.VCB:id/radioOther");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains("Mã thanh toán:" + payID));
		verifyTrue(inboxContent.contains("Số tiền:" + ticketPrice.replace(" VND", "")));

		log.info("TC_08_Step_41: Click back ve Login");
		inbox.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("TC_08_Step_42: Nhap mat khau va log in");
		login.inputIntoEditTextByID(driver, "aaaa1111", "com.VCB:id/edtInput");
		login.clickToDynamicAcceptButton(driver, "com.VCB:id/btnNext");

		log.info("TC_08_Step_43: Hien thi man hinh Home");
		home = PageFactoryManager.getHomePageObject(driver);
	}

	@Parameters({ "otp" })
	@Test
	public void TC_09_TinOTT_VNShop_DaDangNhap(String otp) {

		home.scrollDownToText(driver, "© 2019 Vietcombank");
		home.scrollIDownOneTime(driver);
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mua sắm trực tuyến");
		shopping = PageFactoryManager.getShoppingOnlinePageObject(driver);
		shopping.sleep(driver, 5000);

		log.info("TC_09_Step_02: Them vao gio hang");
		shopping.clickToDynamicCategories("Xem tất cả");
		shopping.clickToDynamicCategories("đ");

		log.info("TC_09_Step_03: click icon gio hang");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");
		shopping.clickToDynamicCart("1", "0");

		log.info("TC_09_Step_04: click dat hang");
		shopping.clickToDynamicDateInDateTimePicker("1");
		shopping.clickToDynamicButton("Đặt hàng");

		log.info("TC_09_Step_05: click thanh toán");
		shopping.clickToDynamicButton("Thanh toán");

		if (shopping.isTextDisplayedInPageSource(driver, "Thông báo") == true) {

			log.info("TC_09_Step_04_1: click the moi");
			shopping.clickToDynamicButton("Thêm mới");

			log.info("TC_09_Step_04_2: nhap ten");
			shopping.inputToDynamicInfo("Ngyen Van A", "Họ tên người nhận");

			log.info("TC_09_Step_04_2: nhap so dien thoai");
			shopping.inputToDynamicInfo("0904797866", "Số điện thoại người nhận");

			log.info("TC_09_Step_04_3: chon tinh thanh pho");
			shopping.clickToDynamicCustomer("Tỉnh/Thành phố");
			shopping.clickToDynamicListProvince("Thành phố Hà Nội");

			log.info("TC_09_Step_04_3: chon tinh quan huyen");
			shopping.clickToDynamicCustomer("Quận/Huyện");
			shopping.clickToDynamicListProvince("Quận Ba Đình");

			log.info("TC_09_Step_04_3: chon tinh xa phuong");
			shopping.clickToDynamicCustomer("Quận/Huyện");
			shopping.clickToDynamicListProvince("Phường Biên Giang");

			log.info("TC_09_Step_04_3: dia chi cu the");
			shopping.inputToDynamicInfo("22 abc", "Địa chỉ cụ thể (Số nhà, tên đường...)");

			log.info("TC_09_Step_04_3: chon hoan tat");
			shopping.clickToDynamicButton("Hoàn tất");
			shopping.clickToDynamicButton("Thanh toán");
		}

		log.info("TC_09_Step_06: click thanh toan");
		shopping.clickToDynamicDateInDateTimePicker("Thanh toán ngay");
		shopping.clickToDynamicButton("Đồng ý");

		log.info("TC_09_Step_07: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		shopping.clickToDynamicButtonLinkOrLinkText(Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

		log.info("TC_09_Step_08: lay ra ma don hang");
		String PayIDString = shopping.getDynamicTextInTransactionDetail("Mã đơn hàng");

		log.info("TC_09_Step_09: tong tien");
		String totalMoneyBill = shopping.getDynamicTextInTransactionDetail("Tổng tiền:");

		log.info("TC_09_Step_10: Chon thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("TC_09_Step_11: Kiem tra tai khoan nguon");
		String account = shopping.getMoneyByAccount("Tài khoản nguồn");
		verifyEquals(account, Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

		log.info("TC_09_Step_12: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText("SMS OTP");

		log.info("TC_09_Step_13: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_09_Step_14: dien otp");
		shopping.inputToDynamicOtp(otp, "Tiếp tục");

		log.info("TC_09_Step_15: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_09_Step_16: Chon ve man hinh home");
		shopping.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivHome");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_09_Step_17: Click vao Inbox Icon");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_3");
		inbox = PageFactoryManager.getInboxPageObject(driver);

		log.info("TC_09_Step_18: Click tab tat ca");
		inbox.clickToTextID(driver, "com.VCB:id/radioAll");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(PayIDString));
		verifyTrue(inboxContent.contains(totalMoneyBill.replace("D", "Đ")));

		log.info("TC_09_Step_19: Click tab quang cao");
		inbox.clickToTextID(driver, "com.VCB:id/radioOther");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(PayIDString));
		verifyTrue(inboxContent.contains(totalMoneyBill.replace("D", "Đ")));

		log.info("TC_09_Step_20: Back ve man hinh Home");
		home = PageFactoryManager.getHomePageObject(driver);
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_10_TinOTT_VNShop_ChuaDangNhap(String otp) {

		home.scrollDownToText(driver, "© 2019 Vietcombank");
		home.scrollIDownOneTime(driver);
		home.clickToDynamicButtonLinkOrLinkText(driver, "Mua sắm trực tuyến");
		shopping = PageFactoryManager.getShoppingOnlinePageObject(driver);
		shopping.sleep(driver, 5000);

		log.info("TC_10_Step_02: Them vao gio hang");
		shopping.clickToDynamicCategories("Xem tất cả");
		shopping.clickToDynamicCategories("đ");

		log.info("TC_10_Step_03: click icon gio hang");
		shopping.clickToDynamicButton("Thêm vào giỏ hàng");
		shopping.clickToDynamicCart("1", "0");

		log.info("TC_10_Step_04: click dat hang");
		shopping.clickToDynamicDateInDateTimePicker("1");
		shopping.clickToDynamicButton("Đặt hàng");

		log.info("TC_10_Step_05: click thanh toán");
		shopping.clickToDynamicButton("Thanh toán");

		if (shopping.isTextDisplayedInPageSource(driver, "Thông báo") == true) {

			log.info("TC_10_Step_04_1: click the moi");
			shopping.clickToDynamicButton("Thêm mới");

			log.info("TC_10_Step_04_2: nhap ten");
			shopping.inputToDynamicInfo("Ngyen Van A", "Họ tên người nhận");

			log.info("TC_10_Step_04_2: nhap so dien thoai");
			shopping.inputToDynamicInfo("0904797866", "Số điện thoại người nhận");

			log.info("TC_10_Step_04_3: chon tinh thanh pho");
			shopping.clickToDynamicCustomer("Tỉnh/Thành phố");
			shopping.clickToDynamicListProvince("Thành phố Hà Nội");

			log.info("TC_10_Step_04_3: chon tinh quan huyen");
			shopping.clickToDynamicCustomer("Quận/Huyện");
			shopping.clickToDynamicListProvince("Quận Ba Đình");

			log.info("TC_10_Step_04_3: chon tinh xa phuong");
			shopping.clickToDynamicCustomer("Quận/Huyện");
			shopping.clickToDynamicListProvince("Phường Biên Giang");

			log.info("TC_10_Step_04_3: dia chi cu the");
			shopping.inputToDynamicInfo("22 abc", "Địa chỉ cụ thể (Số nhà, tên đường...)");

			log.info("TC_10_Step_04_3: chon hoan tat");
			shopping.clickToDynamicButton("Hoàn tất");
			shopping.clickToDynamicButton("Thanh toán");
		}

		log.info("TC_10_Step_06: click thanh toan");
		shopping.clickToDynamicDateInDateTimePicker("Thanh toán ngay");
		shopping.clickToDynamicButton("Đồng ý");

		log.info("TC_10_Step_07: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		shopping.clickToDynamicButtonLinkOrLinkText(Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

		log.info("TC_10_Step_08: lay ra ma don hang");
		String PayIDString = shopping.getDynamicTextInTransactionDetail("Mã đơn hàng");

		log.info("TC_10_Step_09: tong tien");
		String totalMoneyBill = shopping.getDynamicTextInTransactionDetail("Tổng tiền:");

		log.info("TC_10_Step_10: Chon thanh toan");
		shopping.clickToDynamicButton("Thanh toán");

		log.info("TC_10_Step_11: Kiem tra tai khoan nguon");
		String account = shopping.getMoneyByAccount("Tài khoản nguồn");
		verifyEquals(account, Valid_Account.DIFFERENT_OWNER_ACCOUNT_2);

		log.info("TC_10_Step_12: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText("SMS OTP");

		log.info("TC_10_Step_13: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_10_Step_14: dien otp");
		shopping.inputToDynamicOtp(otp, "Tiếp tục");

		log.info("TC_10_Step_15: Chon tiep tuc");
		shopping.clickToDynamicButton("Tiếp tục");

		log.info("TC_10_Step_16: Chon ve man hinh home");
		shopping.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivHome");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_10_Step_17: Chon tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_10_Step_18: An vao Thoat ung dung");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Thoát ứng dụng");

		log.info("TC_10_Step_19: Chon dong y");
		home.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		login = PageFactoryManager.getLoginPageObject(driver);
		login.sleep(driver, 5000);

		log.info("TC_10_Step_20: Click vao Inbox");
		login.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivOTT");
		inbox = PageFactoryManager.getInboxPageObject(driver);

		log.info("TC_10_Step_21: Click tab tat ca");
		inbox.clickToTextID(driver, "com.VCB:id/radioAll");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(PayIDString));
		verifyTrue(inboxContent.contains(totalMoneyBill.replace("D", "Đ")));

		log.info("TC_10_Step_22: Click tab quang cao");
		inbox.clickToTextID(driver, "com.VCB:id/radioOther");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(PayIDString));
		verifyTrue(inboxContent.contains(totalMoneyBill.replace("D", "Đ")));

		log.info("TC_10_Step_23: Click back ve Login");
		inbox.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("TC_10_Step_24: Nhap mat khau va log in");
		login.inputIntoEditTextByID(driver, "aaaa1111", "com.VCB:id/edtInput");
		login.clickToDynamicAcceptButton(driver, "com.VCB:id/btnNext");

		log.info("TC_10_Step_25: Hien thi man hinh Home");
		home = PageFactoryManager.getHomePageObject(driver);
	}

//	@Test
	public void TC_11_TinOTT_VeXe_DaDangNhap() {

		home.scrollDownToText(driver, "© 2019 Vietcombank");

		vehicalTicket = PageFactoryManager.getVehicalPageObject(driver);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);
		vehicalTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_11_Step_01: Chon va nhap diem di");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_1, VehicalData.DATA_ORDER_TICKET.DESTINATION);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_11_Step_02: Chon va nhap diem den");
		vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_3, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_3);

		log.info("TC_11_Step_03: Chon ngay muon di");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TOMORROW);

		log.info("TC_11_Step_04: Tim kiem chuyen di");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP);

		log.info("TC_11_Step_05: Chon ghe: ");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TITLE_CHOISE_CHAIR);
		String colorSeat = "(255,255,255)";
		vehicalTicket.chooseSeats(1, colorSeat);

		log.info("TC_11_Step_06 : Dat chuyen di: ");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.BOOK_SEAT);

		log.info("TC_11_Step_07: Chon ben diem di: ");
		vehicalTicket.waitForElementVisible(CommonPageUIs.DYNAMIC_POINT_ARRVAL, "com.VCB:id/tvAddress");
		vehicalTicket.clickToElement(CommonPageUIs.DYNAMIC_POINT_ARRVAL, "com.VCB:id/tvAddress");

		log.info("TC_11_Step_08: Chon ben diem den: ");
		vehicalTicket.waitForElementVisible(CommonPageUIs.DYNAMIC_POINT_ARRVAL, "com.VCB:id/tvAddress");
		vehicalTicket.clickToElement(CommonPageUIs.DYNAMIC_POINT_ARRVAL, "com.VCB:id/tvAddress");

		log.info("TC_11_Step_09: Click chon tiep tuc ");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_11_Step_10: Click chọn Cho phep");
		vehicalTicket.clickToDynamicAcceptButton("com.android.packageinstaller:id/permission_allow_button");

		log.info("TC_11_Step_11: Input email");
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.EMAIL, VehicalData.DATA_ORDER_TICKET.INPUT_INFO);

		log.info("TC_11_Step_14: Click button tiep tuc");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_11_Step_14: Click btutton Thanh toan");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.THANHTOAN);

		log.info("TC_11_Step_15: Chon thong tin tai khoan nguon");
		vehicalTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		vehicalTicket.clickToDynamicTextOrButtonLink(Notify_Management_Data.ACCOUNT.ACCOUNT_05);

		log.info("TC_11_Step_16: Click button tiep tuc");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_11_Step_17: Lay ma thanh toan-----");
		maThanhToan = vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_TT);

		log.info("TC_11_Step_18: Click chon phuong thuc xac thuc");
		vehicalTicket.clickToDynamicTomorrow("com.VCB:id/llptxt");

		log.info("TC_11_Step_19: Verify hien thi man hinh cac phuong thuc xac minh");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.TITILE_SELECT));

		log.info("TC_11_Step_20: Chon hinh thuc xac thuc là mat khau");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PASS);

		log.info("TC_11_Step_21: Click button Tiep tuc");
		vehicalTicket.waitForElementVisible(CommonPageUIs.DYNAMIC_BUTTON, VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_11_Step_22: Nhap mat khau cua tai khoan");
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PASSWORD_LOGIN, VehicalData.DATA_ORDER_TICKET.INPUT_PASSWORD);

		log.info("TC_11_Step_23: Click btn Tiep tuc");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_11_Step_24: Chon ve man hinh home");
		vehicalTicket.clickToDynamicBottomMenuOrIcon("com.VCB:id/ivHome");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_11_Step_25: Click vao Inbox Icon");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_3");
		inbox = PageFactoryManager.getInboxPageObject(driver);

		log.info("TC_11_Step_26: Click tab tat ca");
		inbox.clickToTextID(driver, "com.VCB:id/radioAll");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(maVe));
		verifyTrue(inboxContent.contains(maThanhToan));

		log.info("TC_11_Step_27: Click tab quang cao");
		inbox.clickToTextID(driver, "com.VCB:id/radioOther");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(maVe));
		verifyTrue(inboxContent.contains(maThanhToan));

		log.info("TC_11_Step_28: Back ve man hinh Home");
		home = PageFactoryManager.getHomePageObject(driver);
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
	}

//	@Test
	public void TC_12_TinOTT_VeXe_ChuaDangNhap() {

		home.scrollDownToText(driver, "© 2019 Vietcombank");

		vehicalTicket = PageFactoryManager.getVehicalPageObject(driver);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);
		vehicalTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_12_Step_01: Chon va nhap diem di");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_1, VehicalData.DATA_ORDER_TICKET.DESTINATION);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_12_Step_02: Chon va nhap diem den");
		vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_3, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_3);

		log.info("TC_12_Step_03: Chon ngay muon di");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TOMORROW);

		log.info("TC_12_Step_04: Tim kiem chuyen di");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP);

		log.info("TC_12_Step_05: Chon ghe: ");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TITLE_CHOISE_CHAIR);
		String colorSeat = "(255,255,255)";
		vehicalTicket.chooseSeats(1, colorSeat);

		log.info("TC_12_Step_06 : Dat chuyen di: ");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.BOOK_SEAT);

		log.info("TC_12_Step_07: Chon ben diem di: ");
		vehicalTicket.waitForElementVisible(CommonPageUIs.DYNAMIC_POINT_ARRVAL, "com.VCB:id/tvAddress");
		vehicalTicket.clickToElement(CommonPageUIs.DYNAMIC_POINT_ARRVAL, "com.VCB:id/tvAddress");

		log.info("TC_12_Step_08: Chon ben diem den: ");
		vehicalTicket.waitForElementVisible(CommonPageUIs.DYNAMIC_POINT_ARRVAL, "com.VCB:id/tvAddress");
		vehicalTicket.clickToElement(CommonPageUIs.DYNAMIC_POINT_ARRVAL, "com.VCB:id/tvAddress");

		log.info("TC_12_Step_09: Click chon tiep tuc ");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_12_Step_10: Click chọn Cho phep");
		vehicalTicket.clickToDynamicAcceptButton("com.android.packageinstaller:id/permission_allow_button");

		log.info("TC_12_Step_11: Input email");
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.EMAIL, VehicalData.DATA_ORDER_TICKET.INPUT_INFO);

		log.info("TC_12_Step_14: Click button tiep tuc");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_12_Step_14: Click btutton Thanh toan");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.THANHTOAN);

		log.info("TC_12_Step_15: Chon thong tin tai khoan nguon");
		vehicalTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		vehicalTicket.clickToDynamicTextOrButtonLink(Notify_Management_Data.ACCOUNT.ACCOUNT_05);

		log.info("TC_12_Step_16: Click button tiep tuc");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_12_Step_17: Lay ma thanh toan-----");
		maThanhToan = vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_TT);

		log.info("TC_12_Step_18: Click chon phuong thuc xac thuc");
		vehicalTicket.clickToDynamicTomorrow("com.VCB:id/llptxt");

		log.info("TC_12_Step_19: Verify hien thi man hinh cac phuong thuc xac minh");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.TITILE_SELECT));

		log.info("TC_12_Step_20: Chon hinh thuc xac thuc là mat khau");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PASS);

		log.info("TC_12_Step_21: Click button Tiep tuc");
		vehicalTicket.waitForElementVisible(CommonPageUIs.DYNAMIC_BUTTON, VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_12_Step_22: Nhap mat khau cua tai khoan");
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PASSWORD_LOGIN, VehicalData.DATA_ORDER_TICKET.INPUT_PASSWORD);

		log.info("TC_12_Step_23: Click btn Tiep tuc");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_12_Step_24: Chon ve man hinh home");
		vehicalTicket.clickToDynamicBottomMenuOrIcon("com.VCB:id/ivHome");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_12_Step_25: Chon tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_12_Step_26: An vao Thoat ung dung");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Thoát ứng dụng");

		log.info("TC_12_Step_27: Chon dong y");
		home.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		login = PageFactoryManager.getLoginPageObject(driver);
		login.sleep(driver, 5000);

		log.info("TC_12_Step_28: Click vao Inbox");
		login.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivOTT");
		inbox = PageFactoryManager.getInboxPageObject(driver);

		log.info("TC_12_Step_29: Click tab tat ca");
		inbox.clickToTextID(driver, "com.VCB:id/radioAll");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(maVe));
		verifyTrue(inboxContent.contains(maThanhToan));

		log.info("TC_12_Step_30: Click tab quang cao");
		inbox.clickToTextID(driver, "com.VCB:id/radioOther");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(maVe));
		verifyTrue(inboxContent.contains(maThanhToan));

		log.info("TC_12_Step_31: Click back ve Login");
		inbox.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("TC_12_Step_32: Nhap mat khau va log in");
		login.inputIntoEditTextByID(driver, "aaaa1111", "com.VCB:id/edtInput");
		login.clickToDynamicAcceptButton(driver, "com.VCB:id/btnNext");

		log.info("TC_12_Step_33: Hien thi man hinh Home");
		home = PageFactoryManager.getHomePageObject(driver);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
