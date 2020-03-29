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
import vehicalTicketBookingUI.CommonPageUIs;
import vietcombankUI.DynamicPageUIs;
import vnpay.vietcombank.sdk.vehicleTicket.data.VehicalData;

public class VehicalFlowTicket extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private VehicalPageObject vehicalTicket;
	public String amountFee = "- ";
	public String nameTyped = "";
	public String phoneTyped = "";
	public String emailTyped = "";
	public String diemDi = "";
	public String diemDen = "";
	public String hangXe = "";
	public String soGhe = "";
	public String soLuongVe = "";
	public String tongTien = "";
	public String taiKhoanNguon = "";
	public String maThanhToan = "";
	public String maGiaodich = "";
	public String maVe = "";

	LocalDate now = LocalDate.now();
	String today = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String tomorow = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + Integer.valueOf(getCurrentDay()) + 1 + "/" + getCurrenMonth() + "/" + getCurrentYear();

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
		login.scrollDownToText(driver, VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);
		login.scrollIDownOneTime(driver);

		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);
		vehicalTicket.clickToDynamicButton("Đồng ý");

	}

	@Test
	public void TC_01_DatVeXeThanhCong() {
		log.info("TC_01_Step_01: kiểm tra hiển thị thơi gian khởi hành");
		String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
		verifyEquals(DayStart, today);

		log.info("TC_01_Step_02: kiểm tra hiển thị ngày hôm nay");
		String today = vehicalTicket.getDynamicDayStart("com.VCB:id/tvHomnay");
		verifyEquals(today, VehicalData.DATA_ORDER_TICKET.TODAY);

		log.info("TC_01_Step_03: kiểm tra hiển thị ngày mai");
		String tomorrow = vehicalTicket.getDynamicDayStart("com.VCB:id/tvNgayMai");
		verifyEquals(tomorrow, VehicalData.DATA_ORDER_TICKET.TOMORROW);

		log.info("TC_01_Step_04: kiểm tra hiển thị button tìm kiếm chuyến đi");
		verifyTrue(vehicalTicket.isDynamicButtonDisplayed(VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP));

		log.info("TC_01_Step_05: Chọn và nhập điểm đi");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_1, VehicalData.DATA_ORDER_TICKET.DESTINATION);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_01_Step_06: Chọn và nhập điểm đến");
		vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_3, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_3);

		log.info("TC_01_Step_07: Chọn ngày muốn đi");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TOMORROW);

		log.info("TC_01_Step_8: Chọn Tìm kiếm chuyến đi");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP);

		log.info("TC_01_Step_09: Chọn ghế");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TITLE_CHOISE_CHAIR);
		String colorSeat = "(255,255,255)";
		System.out.println(colorSeat);
		vehicalTicket.chooseSeats(1, colorSeat);

		log.info("TC_01_Step_10 đặt chuyế đi ");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.BOOK_SEAT);

		log.info("TC_01_Step_11 Chọn bến điểm đi ");
		vehicalTicket.waitForElementVisible(CommonPageUIs.DYNAMIC_POINT_ARRVAL);
		vehicalTicket.clickToElement(CommonPageUIs.DYNAMIC_POINT_ARRVAL);

		log.info("TC_01_Step_12 Chọn bến điểm đến ");
		vehicalTicket.waitForElementVisible(CommonPageUIs.DYNAMIC_POINT_ARRVAL);
		vehicalTicket.clickToElement(CommonPageUIs.DYNAMIC_POINT_ARRVAL);

		log.info("TC_01_Step_13 Click chọn Tiếp tục");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_01_Step_14 Click chọn Cho Phép");
		vehicalTicket.clickToDynamicAcceptButton("com.android.packageinstaller:id/permission_allow_button");

		log.info("TC_01_Step_15 Input email");
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.EMAIL, "Nhập thông tin");

		log.info("------TC_01_Step_16 Lấy thông tin cá nhân-------------");
		
		log.info("------TC_01_Step_17 Lấy Họ và tên -------------");
		nameTyped = vehicalTicket.getDynamicEditText("com.VCB:id/full_name");

		log.info("------TC_01_Step_18 Số diện thoại -------------");
		phoneTyped = vehicalTicket.getDynamicEditText("com.VCB:id/autoCompletePhone");

		log.info("------TC_01_Step_19 Email -------------");
		emailTyped = vehicalTicket.getDynamicEditText("com.VCB:id/email");

		log.info("------TC_01_Step_20 Thông tin điểm xuất phát -------------");
		diemDi = vehicalTicket.getDynamicTextView("com.VCB:id/tvFrom");

		log.info("------TC_01_Step_21 Thông tin điểm đến -------------");
		diemDen = vehicalTicket.getDynamicTextView("com.VCB:id/tvTo");

		log.info("------TC_01_Step_22 hãng xe -------------");
		hangXe = vehicalTicket.getDynamicTextView("com.VCB:id/tv_hang_xe");

		log.info("------TC_01_Step_23 Số ghế -------------");
		soGhe = vehicalTicket.getDynamicTextView("com.VCB:id/tvAllSeat");

		log.info("------TC_01_Step_24 Số lượng ghế ngồi -------------");
		soLuongVe = vehicalTicket.getDynamicTextView("com.VCB:id/tvTicketNumber");

		log.info("------TC_01_Step_25 Số lượng ghế ngồi -------------");
		tongTien = vehicalTicket.getDynamicTextView("com.VCB:id/tvTotalAmount");

		log.info("TC_01_Step_27 Click btn Tiếp tục");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_01_Step_28 Verify hiển thị màn hình thông tin khách hàng");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.INFO_CUSTOMER));
		
		log.info("TC_01_Step_29 Lấy thông tin khách hàng hiển thị");
		verifyEquals(nameTyped, vehicalTicket.getDynamicEditText("com.VCB:id/full_name"));
		verifyEquals(phoneTyped, vehicalTicket.getDynamicEditText("com.VCB:id/autoCompletePhone"));
		verifyEquals(emailTyped, vehicalTicket.getDynamicEditText("com.VCB:id/email"));
		verifyEquals(diemDi, vehicalTicket.getDynamicTextView("com.VCB:id/tvFrom"));
		verifyEquals(diemDen, vehicalTicket.getDynamicTextView("com.VCB:id/tvTo"));
		verifyEquals(hangXe, vehicalTicket.getDynamicTextView("com.VCB:id/tv_hang_xe"));
		verifyEquals(soGhe, vehicalTicket.getDynamicTextView("com.VCB:id/tvAllSeat"));
		verifyEquals(soLuongVe, vehicalTicket.getDynamicTextView("com.VCB:id/tvTicketNumber"));

		log.info("TC_01_Step_30 Verify tong tien 1");
		verifyEquals(tongTien, vehicalTicket.getDynamicTextView("com.VCB:id/tvTotalAmount"));

		log.info("TC_01_Step_31 Click btn Thanh toán");
		vehicalTicket.clickToDynamicButton("Thanh toán");

		log.info("TC_01_Step_32 Verify hiển thị màn hình thông tin Vé Xe");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.INFO_TICKET));

		vehicalTicket.scrollUpToText(VehicalData.DATA_ORDER_TICKET.AMOUNT_ROOT);

		taiKhoanNguon = vehicalTicket.getDynamicTextView("com.VCB:id/number_account");
		verifyEquals(nameTyped, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, "Họ tên"));

		verifyEquals(phoneTyped, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, "Số điện thoại"));

		verifyEquals(emailTyped, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, "Email"));
		vehicalTicket.scrollIDownOneTime(driver);

		verifyEquals(diemDi, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.DESTINATION));
		verifyEquals(diemDen, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.ARRIVAL));
		verifyEquals(hangXe, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, "Hãng xe"));
		verifyEquals(soGhe, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, "Số ghế"));
		verifyEquals(soLuongVe, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, "Số lượng vé"));

		log.info("TC_01_Step_33 Verify tong tien 2");
		tongTien = tongTien.replace("đ", "VND");
		verifyEquals(tongTien, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.AMOUNT_TT));

		log.info("TC_01_Step_34 Click btn Tiếp tục");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_01_Step_35 Xác minh thông tin tài khoản nguồn");

		verifyEquals(taiKhoanNguon, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.AMOUNT_ROOT));
		maThanhToan = vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_TT);

		log.info("TC_01_Step_36 Click btn Tiếp tục");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_01_Step_37 Nhập mật khẩu của tài khoản");
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PASSWORD_LOGIN, "Nhập mật khẩu");

		log.info("TC_01_Step_38 Click btn Tiếp tục");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_TIEPTUC);

		log.info("TC_01_Step_39 Xác Minh giao dịch thành công");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.NOTIFICATION.NOTI_SUCCESS));

		log.info("TC-O1_Step_40 Verify thông tin mã thanh toán");
		verifyEquals(maThanhToan, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_TT));

		log.info("TC-O1_Step_41 Lấy thông tin mã giao dịch");
		maGiaodich = vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_GD);

		log.info("TC-O1_Step_42 Verify số tiền thanh toán");
		tongTien = tongTien.replace("đ", "VND");
		verifyEquals(tongTien, vehicalTicket.getDynamicTextView("com.VCB:id/tvAmount"));
	}

	@Test
	public void TC_02_KiemTraDichSuGiaoDich() {

		log.info("TC_02_Step_03_Click thực hiện giao dịch mới");
		vehicalTicket.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_02_Step_04_Hiển thị màn hình đặt vé xe và click lịch sử giao dịch");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET));

		log.info("TC_02_Step_05_Click lịch sử đặt vé");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.HISTORY_TICKET);

		log.info("TC_02_Step_06_Verify mã thanh toán");
		verifyEquals(vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_TT), maThanhToan);

		log.info("TC_02_Step_06_Lấy thông tin mã vé");
		maVe = vehicalTicket.getTextDynamicFollowTextTable(DynamicPageUIs.DYNAMIC_TEXT, VehicalData.DATA_ORDER_TICKET.CODE_TICKET);

		log.info("TC_02_Step_06_Verify hãng xe");
		verifyEquals(hangXe, vehicalTicket.getTextDynamicFollowTextTable(DynamicPageUIs.DYNAMIC_TEXT, "Hãng xe"));

		log.info("TC_02_Step_06_Verify htổng tiên");
		tongTien = tongTien.replace("VND", "đ");

		log.info("TC_02_Step_07_Verify số tiền");
		verifyEquals(tongTien, vehicalTicket.getTextDynamicFollowTextTable(DynamicPageUIs.DYNAMIC_TEXT, "Tổng tiền"));

	}

	@Test
	public void TC_03_truyVanTaiKhoan() {
		log.info("TC_03_Step_01_Click btn back lần 1");
		vehicalTicket.clickToDynamicButtonBackByID("com.VCB:id/ivTitleLeft");

		log.info("TC_03_Step_02_Click btn back lần 2");
		vehicalTicket.clickToDynamicButtonBackByID("com.VCB:id/ivTitleLeft");

		log.info("TC_03_Step_03_Chuyển sang màn hình Đặt vé ");
		vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);

		log.info("TC_03_Step_04_Click btn menu");
		vehicalTicket.clickToDynamicBottomMenuOrIcon("com.VCB:id/menu_5");

		log.info("TC_03_Step_05_Click chọn phần Báo cáo giao dịch");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TITLE_REPORT);

		log.info("TC_03_Step_06_Kiểm tra hiển thị màn hình báo cáo giao dịch ");
		vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.TITLE_REPORT);

		log.info("TC_03_Step_06_Kiểm tra hiển thị thông tin tài khoản nguồn ");
		verifyEquals(taiKhoanNguon, vehicalTicket.getDynamicTextView("com.VCB:id/tvSelectAcc"));

		log.info("TC_03_Step_17: Chọn Tìm kiếm chuyến đi");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_SEARCH);

		log.info("TC_03_Step_17: Verify dynamic bản ghi thanh toán");
		vehicalTicket.clickToDynamicTomorrow("com.VCB:id/llRoot");

		log.info("TC_03_Step_18: Kiểm tra hiển thị màn hình chi tiết giao dịch ");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.DETAIL_GD));

		log.info("TC_03_Step_19: Kiểm tra tìa khoản trích nợ ");
		verifyEquals(taiKhoanNguon, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.ACCOUNT));

		log.info("TC_03_Step_19: Kiểm tra mã thanh toán ");
		verifyEquals(maThanhToan, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_TT));

		log.info("TC_03_Step_19: Kiểm tra số lệnh giao dịch ");
		verifyEquals(maGiaodich, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.NUMBER_ORDER));

		tongTien = tongTien.replace("đ", "VND");

		log.info("TC_03_Step_19: Kiểm tra số tiền giao dịch ");

		String tienSo = vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.AMOUNT);
		int indexTien = tienSo.indexOf("(");
		String subTienSo = tienSo.substring(0, indexTien - 1);
		verifyEquals(tongTien, subTienSo);
	}

	@Test
	public void TC_04_truyVanTaiKhoan() {

		log.info("TC_04_Step_01_Click btn back ");
		vehicalTicket.clickToDynamicButtonBackByID("com.VCB:id/ivTitleLeft");
		
		log.info("TC_04_Step_02: Click chọn mục Tiền ra ");
		vehicalTicket.clickToDynamicText("Tiền ra");

		log.info("TC_04_Step_03_Kiểm tra hiển thị thông tin tài khoản nguồn ");
		verifyEquals(taiKhoanNguon, vehicalTicket.getDynamicTextView("com.VCB:id/tvSelectAcc"));

		log.info("TC_04_Step_04: Verify dynamic bản ghi thanh toán");
		vehicalTicket.clickToDynamicTomorrow("com.VCB:id/llRoot");

		log.info("TC_04_Step_05: Kiểm tra hiển thị màn hình chi tiết giao dịch ");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.DETAIL_GD));

		log.info("TC_04_Step_06: Kiểm tra tìa khoản trích nợ ");
		verifyEquals(taiKhoanNguon, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.ACCOUNT));

		log.info("TC_04_Step_07: Kiểm tra mã thanh toán ");
		verifyEquals(maThanhToan, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.CODE_TT));

		log.info("TC_04_Step_08: Kiểm tra số lệnh giao dịch ");
		verifyEquals(maGiaodich, vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.NUMBER_ORDER));

		tongTien = tongTien.replace("đ", "VND");

		log.info("TC_04_Step_09: Kiểm tra số tiền giao dịch ");
		String tienSo = vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, VehicalData.DATA_ORDER_TICKET.AMOUNT);
		int indexTien = tienSo.indexOf("(");
		String subTienSo = tienSo.substring(0, indexTien - 1);
		verifyEquals(tongTien, subTienSo);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();

	}

}
