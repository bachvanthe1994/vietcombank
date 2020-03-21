package vnpay.vietcombank.notifyManagement;

import java.io.IOException;
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
import pageObjects.NotifyManagementPageObject;
import pageObjects.sdk.airTicketBooking.DynamicAirTicketBookingObjects;
import pageObjects.sdk.filmTicketBooking.FilmTicketBookingPageObject;
import pageObjects.sdk.hotelBooking.HotelBookingPageObject;
import vnpay.vietcombank.sdk.airticket.data.DomesticAirTicketBooking_Data;
import vnpay.vietcombank.sdk.hotelBooking.data.HotelBooking_Data;

public class Notify_Management_Flow extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private HomePageObject home;
	private NotifyManagementPageObject notifyManage;
	private FilmTicketBookingPageObject filmTicketBooking;
	private InboxPageObject inbox;
	private HotelBookingPageObject hotelBooking;
	private DynamicAirTicketBookingObjects airTicket;

	private String cinemaName, inboxContent, ticketCode, counter, hotelName, hotelAddress, transactionID, roomID;
	private long surplus, fee, money;
	private boolean status;
	FilmTicketInfo info = new FilmTicketInfo();

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone",
			"pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities,
			String appPackage, String appName, String phone, String pass, String opt)
			throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login("0976878903", "aaaa1111", opt);

	}

	@Test
	public void TC_01_TinOTTVeXemPhim_DaDangNhap() {

		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_01_Step_01: Chon tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_01_Step_02: Mo sub-menu Cai dat");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("TC_01_Step_03: An vao phan Quan ly thong bao");
		home.scrollDownToText(driver, "Tra cứu");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Quản lý thông báo");

		log.info("TC_01_Step_04: Hien thi man hinh Tinh toan lai suat");
		notifyManage = PageFactoryManager.getNotifyManagementPageObject(driver);
		verifyEquals(notifyManage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"),
				"Quản lý thông báo");

		log.info("TC_01_Step_05: Xac nhan hien thi nhan thong bao tu ngan hang la ON");
		verifyTrue(notifyManage.isDynamicImageByIdEnable(driver, "com.VCB:id/ic_switch"));

		log.info("TC_01_Step_06: Back ve man hinh Home");
		notifyManage.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		home = PageFactoryManager.getHomePageObject(driver);
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");
		status = home.isDynamicTextDetailByID(driver, "com.VCB:id/counter");
		if (status == true) {
			counter = home.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter");
		} else {
			counter = "0";
		}

		log.info("TC_01_01_Click Dat ve xem phim");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đặt vé xem phim");

		log.info("TC_01_02_Click nut Dong y");
		home.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_01_03_Click chon Tinh thanh");
		filmTicketBooking = PageFactoryManager.getFilmTicketBookingPageObject(driver);
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");

		log.info("TC_01_04_Tim kiem thanh pho");
		filmTicketBooking.inputIntoEditTextByID("Hồ Chí Minh", "com.VCB:id/edtSearch");

		log.info("TC_01_05_Click chon thanh pho");
		filmTicketBooking.clickToDynamicTextView("Hồ Chí Minh");

		log.info("TC_01_06_Click chon cum rap Mega GS");
		filmTicketBooking.clickToDynamicTextView("BHD Star Cineplex");

		log.info("TC_01_07_Click chon rap phim");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameCinema");
		cinemaName = listCinema.get(0);
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

		log.info("TC_01_11_Chon moi loai 1 ghe");
		filmTicketBooking.clickToChooseEachTypeASeate();

		List<SeatType> listSeatType = filmTicketBooking.getListSeatType();

		log.info("TC_01_12_Click chon cho ngoi");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

		log.info("TC_01_13_Chon cho ngoi nhu da dang ky");
		filmTicketBooking.chooseMaxSeats(listSeatType);

		info.cinemaName = filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle");
		info.price = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("TC_01_14_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("TC_01_16_Nhap thong tin nguoi nhan ve");
		log.info("TC_01_16_01_Nhap ten khach hang");
		filmTicketBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");

		log.info("TC_01_16_02_Nhap sdt");
		filmTicketBooking.inputToDynamicInputBoxByID("0974862669", "com.VCB:id/etPhoneNumber");

		log.info("TC_01_16_03_Nhap email");
		filmTicketBooking.scrollDownToText("Thanh toán");
		filmTicketBooking.inputToDynamicInputBoxByID("trungnd@vnpay.vn", "com.VCB:id/etEmail");

		log.info("TC_01_17_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("TC_01_18_Chon tai khoan nguon");
		filmTicketBooking.clickToDynamicDropDown("Tài khoản nguồn");
		filmTicketBooking.clickToDynamicButtonLinkOrLinkText(driver, "0019966611");
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(
				filmTicketBooking.getDynamicTextInTransactionDetail("Số dư khả dụng"));

		log.info("TC_01_20: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_22_Chon phuong thuc xac thuc");
		filmTicketBooking.clickToDynamicTextView("Mật khẩu đăng nhập");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(
				filmTicketBooking.getDynamicTextInTransactionDetail("Mật khẩu đăng nhập"));
		filmTicketBooking.clickToDynamicTextView("Mật khẩu đăng nhập");

		log.info("TC_01_23: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton("Tiếp tục");

		filmTicketBooking.inputToDynamicPopupPasswordInput("aaaa1111", "Tiếp tục");

		filmTicketBooking.clickToDynamicButton("Tiếp tục");
		ticketCode = filmTicketBooking.getDynamicTextInTransactionDetail("Mã vé");

		log.info("TC_01_26_Click Thuc hien giao dich moi");
		filmTicketBooking.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_02_1: Click  nut Back");
		filmTicketBooking.clickToDynamicBackIcon("Mua vé xem phim");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_2: Click vao More Icon");
		verifyEquals(home.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter"), getCounterPlus(counter, 1));
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_3");
		inbox = PageFactoryManager.getInboxPageObject(driver);

		log.info("TC_02_2: Click tab tat ca");
		inbox.clickToTextID(driver, "com.VCB:id/radioAll");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(info.filmName));
		verifyTrue(inboxContent.contains(info.time));
		verifyTrue(inboxContent.contains(info.cinemaName));
		verifyTrue(inboxContent.contains(ticketCode));

		log.info("TC_02_2: Click tab quang cao");
		inbox.clickToTextID(driver, "com.VCB:id/radioOther");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(info.filmName));
		verifyTrue(inboxContent.contains(info.time));
		verifyTrue(inboxContent.contains(info.cinemaName));
		verifyTrue(inboxContent.contains(ticketCode));
	}

	@Test
	public void TC_02_TinOTTVeXemPhim_ChuaDangNhap() {

		log.info("TC_01_Step_06: Back ve man hinh Home");
		home = PageFactoryManager.getHomePageObject(driver);
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");

		log.info("TC_01_01_Click Dat ve xem phim");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đặt vé xem phim");

		log.info("TC_01_02_Click nut Dong y");
		home.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_01_03_Click chon Tinh thanh");
		filmTicketBooking = PageFactoryManager.getFilmTicketBookingPageObject(driver);
		filmTicketBooking.clickToDynamicTextViewByID("com.VCB:id/tvLocationName");

		log.info("TC_01_04_Tim kiem thanh pho");
		filmTicketBooking.inputIntoEditTextByID("Hồ Chí Minh", "com.VCB:id/edtSearch");

		log.info("TC_01_05_Click chon thanh pho");
		filmTicketBooking.clickToDynamicTextView("Hồ Chí Minh");

		log.info("TC_01_06_Click chon cum rap Mega GS");
		filmTicketBooking.clickToDynamicTextView("BHD Star Cineplex");

		log.info("TC_01_07_Click chon rap phim");
		List<String> listCinema = filmTicketBooking.getListOfSuggestedMoneyOrListText("com.VCB:id/tvNameCinema");
		cinemaName = listCinema.get(0);
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

		log.info("TC_01_11_Chon moi loai 1 ghe");
		filmTicketBooking.clickToChooseEachTypeASeate();

		List<SeatType> listSeatType = filmTicketBooking.getListSeatType();

		log.info("TC_01_12_Click chon cho ngoi");
		filmTicketBooking.clickToTextViewByText("Chọn chỗ ngồi");

		log.info("TC_01_13_Chon cho ngoi nhu da dang ky");
		filmTicketBooking.chooseMaxSeats(listSeatType);

		info.cinemaName = filmTicketBooking.getTextViewByID("com.VCB:id/tvTitle");
		info.price = filmTicketBooking.getTextViewByID("com.VCB:id/tvPrince");

		log.info("TC_01_14_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("TC_01_16_Nhap thong tin nguoi nhan ve");
		log.info("TC_01_16_01_Nhap ten khach hang");
		filmTicketBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");

		log.info("TC_01_16_02_Nhap sdt");
		filmTicketBooking.inputToDynamicInputBoxByID("0974862669", "com.VCB:id/etPhoneNumber");

		log.info("TC_01_16_03_Nhap email");
		filmTicketBooking.scrollDownToText("Thanh toán");
		filmTicketBooking.inputToDynamicInputBoxByID("trungnd@vnpay.vn", "com.VCB:id/etEmail");

		log.info("TC_01_17_Click Thanh toan");
		filmTicketBooking.clickToTextViewByText("Thanh toán");

		log.info("TC_01_18_Chon tai khoan nguon");
		filmTicketBooking.clickToDynamicDropDown("Tài khoản nguồn");
		filmTicketBooking.clickToDynamicButtonLinkOrLinkText(driver, "0019966611");
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(
				filmTicketBooking.getDynamicTextInTransactionDetail("Số dư khả dụng"));

		log.info("TC_01_20: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_22_Chon phuong thuc xac thuc");
		filmTicketBooking.clickToDynamicTextView("Mật khẩu đăng nhập");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(
				filmTicketBooking.getDynamicTextInTransactionDetail("Mật khẩu đăng nhập"));
		filmTicketBooking.clickToDynamicTextView("Mật khẩu đăng nhập");

		log.info("TC_01_23: Click Tiep tuc");
		filmTicketBooking.clickToDynamicButton("Tiếp tục");

		filmTicketBooking.inputToDynamicPopupPasswordInput("aaaa1111", "Tiếp tục");

		filmTicketBooking.clickToDynamicButton("Tiếp tục");
		ticketCode = filmTicketBooking.getDynamicTextInTransactionDetail("Mã vé");

		log.info("TC_01_26_Click Thuc hien giao dich moi");
		filmTicketBooking.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_02_1: Click  nut home");
		filmTicketBooking.clickToDynamicBackIcon("Mua vé xem phim");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_2: Xac nhan hien thi icon notify");
		verifyEquals(home.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter"), "01");

		log.info("TC_01_Step_01: Chon tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_01_Step_03: An vao Thoat ung dung");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Thoát ứng dụng");

		log.info("TC_01_Step_03: Chon dong y");
		home.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("TC_02_2: Click vao Inbox");
		verifyEquals(login.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter"), "01");
		login.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivOTT");
		inbox = PageFactoryManager.getInboxPageObject(driver);

		log.info("TC_02_2: Click tab tat ca");
		inbox.clickToTextID(driver, "com.VCB:id/radioAll");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(info.filmName));
		verifyTrue(inboxContent.contains(info.time));
		verifyTrue(inboxContent.contains(info.cinemaName));
		verifyTrue(inboxContent.contains(ticketCode));

		log.info("TC_02_2: Click tab quang cao");
		inbox.clickToTextID(driver, "com.VCB:id/radioOther");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(info.filmName));
		verifyTrue(inboxContent.contains(info.time));
		verifyTrue(inboxContent.contains(info.cinemaName));
		verifyTrue(inboxContent.contains(ticketCode));

		log.info("TC_02_2: Click back ve Login");
		inbox.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("TC_02_2: Nhap mat khau va log in");
		login.inputIntoEditTextByID(driver, "aaaa1111", "com.VCB:id/edtInput");
		login.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/btnNext");

		log.info("TC_02_2: Hien thi man hinh Home");
		home = PageFactoryManager.getHomePageObject(driver);
	}

	@Test
	public void TC_03_TinOTTDatVeKhachSan_DaDangNhap() {

		log.info("TC_01_01_Click Dat phong khach san");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đặt phòng khách sạn");

		log.info("TC_01_02_Click nut Dong y");
		home.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_01_03_Tim kiem dia diem");
		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		hotelBooking.inputToDynamicInputBox(HotelBooking_Data.HOTEL_NAME_BOOKING_INPUT, "Tên khách sạn hoặc điểm đến");

		log.info("TC_01_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.HOTEL_NAME_BOOKING);

		log.info("TC_01_05_Click dat phong");
		hotelName = hotelBooking.getTextViewByID("com.VCB:id/tvHotelName");
		hotelAddress = hotelBooking.getTextViewByID("com.VCB:id/tvAddress");
		hotelBooking.waitForTextViewDisplay(HotelBooking_Data.HOTEL_NAME_BOOKING);
		hotelBooking.scrollIDownToText("ĐẶT PHÒNG");
		hotelBooking.clickToDynamicTextView("ĐẶT PHÒNG");

		money = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getTextViewByID("com.VCB:id/tvTotalPrice"));

		log.info("TC_01_06_Nhap ten khach hang");
		hotelBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");

		log.info("TC_01_07_Nhap so dien thoai khach hang");
		hotelBooking.inputToDynamicInputBoxByID("0976878903", "com.VCB:id/etCustomerPhone");

		log.info("TC_01_08_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID("minhducdo2603@gmail.com", "com.VCB:id/etCustomerEmail");

		log.info("TC_01_09_Click Thanh toan");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("THANH TOÁN");

		log.info("TC_01_10_Chon tai khoan nguon");
		hotelBooking.clickToDynamicDropDown("Tài khoản nguồn");
		hotelBooking.clickToDynamicTextOrButtonLink("0019966611");
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(
				hotelBooking.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_01_11_Kiem tra thong tin hoa don");
		transactionID = hotelBooking.getDynamicTextByLabel("Mã giao dịch");
		hotelBooking.scrollDownToButton(driver, "Tiếp tục");

		log.info("TC_01_12_Click Tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_13_Chon phuong thuc xac thuc");
		hotelBooking.clickToDynamicTextOrButtonLink("Mật khẩu đăng nhập");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(
				hotelBooking.getDynamicTextInTransactionDetail(driver, "Mật khẩu đăng nhập"));
		hotelBooking.clickToDynamicTextOrButtonLink("Mật khẩu đăng nhập");

		log.info("TC_01_14_Click tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_15_Nhap ma OTP chinh xac");
		hotelBooking.inputToDynamicPopupPasswordInput("aaaa1111", "Tiếp tục");

		log.info("TC_01_16_Click tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_25_Kiem tra nut Thuc hien giao dich moi");
		roomID = hotelBooking.getDynamicTextByLabel("Mã nhận phòng");
		verifyTrue(hotelBooking.isDynamicButtonDisplayed("Thực hiện giao dịch mới"));

		log.info("TC_01_26_Click Thuc hien giao dich moi");
		hotelBooking.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_02_1: Click  nut Back");
		hotelBooking.clickToDynamicBackIcon("ĐẶT PHÒNG\r\n" + "KHÁCH SẠN");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_2: Click vao More Icon");
		verifyEquals(home.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter"), "01");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_3");
		inbox = PageFactoryManager.getInboxPageObject(driver);

		log.info("TC_02_2: Click tab tat ca");
		inbox.clickToTextID(driver, "com.VCB:id/radioAll");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(hotelName));
		verifyTrue(inboxContent.contains(hotelAddress));
		verifyTrue(inboxContent.contains(roomID));

		log.info("TC_02_2: Click tab quang cao");
		inbox.clickToTextID(driver, "com.VCB:id/radioOther");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(hotelName));
		verifyTrue(inboxContent.contains(hotelAddress));
		verifyTrue(inboxContent.contains(roomID));
	}

	@Test
	public void TC_04_TinOTTDatVeKhachSan_ChuaDangNhap() {

		log.info("TC_01_Step_06: Back ve man hinh Home");
		home = PageFactoryManager.getHomePageObject(driver);
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_1");

		log.info("TC_01_01_Click Dat phong khach san");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Đặt phòng khách sạn");

		log.info("TC_01_02_Click nut Dong y");
		home.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_01_03_Tim kiem dia diem");
		hotelBooking = PageFactoryManager.getHotelBookingPageObject(driver);
		hotelBooking.clickToDynamicTextViewByID("com.VCB:id/tvPlaceName");
		hotelBooking.inputToDynamicInputBox(HotelBooking_Data.HOTEL_NAME_BOOKING_INPUT, "Tên khách sạn hoặc điểm đến");

		log.info("TC_01_04_Chon dia diem");
		hotelBooking.clickToDynamicTextView(HotelBooking_Data.HOTEL_NAME_BOOKING);

		log.info("TC_01_05_Click dat phong");
		hotelName = hotelBooking.getTextViewByID("com.VCB:id/tvHotelName");
		hotelAddress = hotelBooking.getTextViewByID("com.VCB:id/tvAddress");
		hotelBooking.waitForTextViewDisplay(HotelBooking_Data.HOTEL_NAME_BOOKING);
		hotelBooking.scrollIDownToText("ĐẶT PHÒNG");
		hotelBooking.clickToDynamicTextView("ĐẶT PHÒNG");

		money = convertAvailableBalanceCurrentcyOrFeeToLong(hotelBooking.getTextViewByID("com.VCB:id/tvTotalPrice"));

		log.info("TC_01_06_Nhap ten khach hang");
		hotelBooking.inputToDynamicInputBoxByID("Duc Do", "com.VCB:id/etCustomerName");

		log.info("TC_01_07_Nhap so dien thoai khach hang");
		hotelBooking.inputToDynamicInputBoxByID("0976878903", "com.VCB:id/etCustomerPhone");

		log.info("TC_01_08_Nhap email khach hang");
		hotelBooking.inputToDynamicInputBoxByID("minhducdo2603@gmail.com", "com.VCB:id/etCustomerEmail");

		log.info("TC_01_09_Click Thanh toan");
		hotelBooking.swipeElementToElementByText("Bạn có mã giảm giá?", "Đặt phòng");
		hotelBooking.clickToDynamicTextView("THANH TOÁN");

		log.info("TC_01_10_Chon tai khoan nguon");
		hotelBooking.clickToDynamicDropDown("Tài khoản nguồn");
		hotelBooking.clickToDynamicTextOrButtonLink("0019966611");
		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(
				hotelBooking.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_01_11_Kiem tra thong tin hoa don");
		transactionID = hotelBooking.getDynamicTextByLabel("Mã giao dịch");
		hotelBooking.scrollDownToButton(driver, "Tiếp tục");

		log.info("TC_01_12_Click Tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_13_Chon phuong thuc xac thuc");
		hotelBooking.clickToDynamicTextOrButtonLink("Mật khẩu đăng nhập");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(
				hotelBooking.getDynamicTextInTransactionDetail(driver, "Mật khẩu đăng nhập"));
		hotelBooking.clickToDynamicTextOrButtonLink("Mật khẩu đăng nhập");

		log.info("TC_01_14_Click tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_15_Nhap ma OTP chinh xac");
		hotelBooking.inputToDynamicPopupPasswordInput("aaaa1111", "Tiếp tục");

		log.info("TC_01_16_Click tiep tuc");
		hotelBooking.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_25_Kiem tra nut Thuc hien giao dich moi");
		roomID = hotelBooking.getDynamicTextByLabel("Mã nhận phòng");
		verifyTrue(hotelBooking.isDynamicButtonDisplayed("Thực hiện giao dịch mới"));

		log.info("TC_01_26_Click Thuc hien giao dich moi");
		hotelBooking.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_02_1: Click  nut Back");
		hotelBooking.clickToDynamicBackIcon("ĐẶT PHÒNG\r\n" + "KHÁCH SẠN");
		home = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_02_2: Xac nhan hien thi icon notify");
		verifyEquals(home.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter"), "01");

		log.info("TC_01_Step_01: Chon tab Menu");
		home.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/menu_5");

		log.info("TC_01_Step_03: An vao Thoat ung dung");
		home.clickToDynamicButtonLinkOrLinkText(driver, "Thoát ứng dụng");

		log.info("TC_01_Step_03: Chon dong y");
		home.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("TC_02_2: Click vao Inbox");
		verifyEquals(login.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/counter"), "01");
		login.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivOTT");
		inbox = PageFactoryManager.getInboxPageObject(driver);

		log.info("TC_02_2: Click tab tat ca");
		inbox.clickToTextID(driver, "com.VCB:id/radioAll");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(info.filmName));
		verifyTrue(inboxContent.contains(info.time));
		verifyTrue(inboxContent.contains(info.cinemaName));
		verifyTrue(inboxContent.contains(ticketCode));

		log.info("TC_02_2: Click tab quang cao");
		inbox.clickToTextID(driver, "com.VCB:id/radioOther");
		inboxContent = inbox.getTextInDynamicIndexAndID(driver, "0", "com.VCB:id/content");
		verifyTrue(inboxContent.contains(info.filmName));
		verifyTrue(inboxContent.contains(info.time));
		verifyTrue(inboxContent.contains(info.cinemaName));
		verifyTrue(inboxContent.contains(ticketCode));

		log.info("TC_02_2: Click back ve Login");
		inbox.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		login = PageFactoryManager.getLoginPageObject(driver);

		log.info("TC_02_2: Nhap mat khau va log in");
		login.inputIntoEditTextByID(driver, "aaaa1111", "com.VCB:id/edtInput");
		login.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/btnNext");

		log.info("TC_02_2: Hien thi man hinh Home");
		home = PageFactoryManager.getHomePageObject(driver);
	}

	@Parameters({ "otp" })
//	@Test
	public void TC_05_TinOTTVeMayBayThanhToanNgay_DadangNhap(String otp) {

		log.info("TC_02_2: Click vao phan Dat ve may bay");
		home.clickToDynamicIcon(driver, "Đặt vé máy bay");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);

		log.info(": Click thanh toan ve may bay ");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		airTicket.clickToDynamicTextOrButtonLink("Thanh toán vé máy bay");

		log.info("TC_05_Step_03: Nhap ma thanh toan");
		airTicket.inputToDynamicInputBoxById(DomesticAirTicketBooking_Data.validInput.AIR_TICKET_DATA[0],
				"com.VCB:id/edtBookCode");

		log.info("TC_03_Step 17: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_Step_22: Chọn tai khoan nguon");
		airTicket.clickToDynamicTextByID("com.VCB:id/number_account");
		airTicket.clickToDynamicTextOrButtonLink(DomesticAirTicketBooking_Data.validInput.ACCOUNT2);

		log.info("TC_03_Step 17: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_03_Step_27: Chon phuong thuc xac thuc SMS OTP");
		airTicket.clickToDynamicTextByID("com.VCB:id/tvptxt");
		airTicket.clickToDynamicTextOrButtonLink("SMS OTP");

		log.info("TC_03_Step_28: Click Tiep Tuc");
		airTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_24: Nhap Ma OTP roi click Tiep tuc");
		airTicket.inputToDynamicOtp(driver, otp, "Tiếp tục");
		airTicket.clickToDynamicButton("Tiếp tục");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		service.stop();
	}

}
