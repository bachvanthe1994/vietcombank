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
import vnpay.vietcombank.sdk.vehicleTicket.data.VehicalData;

public class VehicalFlowTicket extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private VehicalPageObject vehicalTicket;
	String amountFee = "- ";
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
		login.scrollDownToText(driver, "Đặt vé xe");
		login.scrollIDownOneTime(driver);

		vehicalTicket.clickToDynamicText("Đặt vé xe");

		vehicalTicket.clickToDynamicButton("Đồng ý");
	}

	@Test
	public void TC_01_DatVeXeThanhCong() {
		log.info("TC_01_Step_1: kiểm tra hiển thị title màn hình");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET));

		log.info("TC_01_Step_4: kiểm tra hiển thị chọn điểm đi");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.FROMT));

		log.info("TC_01_Step_5: kiểm tra hiển thị chọn điểm đến");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.TO));

		log.info("TC_01_Step_7: kiểm tra hiển thị thơi gian khởi hành");
		String DayStart = vehicalTicket.getDynamicDayStart("com.VCB:id/tvMonth");
		verifyEquals(DayStart, today);

		log.info("TC_01_Step_8: kiểm tra hiển thị ngày hôm nay");
		String today = vehicalTicket.getDynamicDayStart("com.VCB:id/tvHomnay");
		verifyEquals(today, VehicalData.DATA_ORDER_TICKET.TODAY);

		log.info("TC_01_Step_9: kiểm tra hiển thị ngày mai");
		String tomorrow = vehicalTicket.getDynamicDayStart("com.VCB:id/tvNgayMai");
		verifyEquals(tomorrow, VehicalData.DATA_ORDER_TICKET.TOMORROW);

		log.info("TC_01_Step_10: kiểm tra hiển thị button tìm kiếm chuyến đi");
		verifyTrue(vehicalTicket.isDynamicButtonDisplayed(VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP));

		log.info("TC_01_Step_11: Chọn và nhập điểm đi");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_1, "Điểm đi");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_01_Step_12: Chọn và nhập điểm đến");
		vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_3, "Điểm đến");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_3);

		log.info("TC_01_Step_13: Chọn ngày muốn đi");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.TOMORROW);
		log.info("TC_01_Step_14: Chọn Tìm kiếm chuyến đi");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_FIND_TRIP);

		log.info("TC_01_Step_15: Chọn ghế");
		vehicalTicket.clickToDynamicText("Chọn ghế");
		String colorSeat = "(255,255,255)";
		System.out.println(colorSeat);
		vehicalTicket.chooseSeats(1, colorSeat);

		log.info("TC_01_Step_16 đặt chuyế đi ");
		vehicalTicket.clickToDynamicText("Đặt chỗ");

		log.info("TC_01_Step_17 Chọn bến điểm đi ");
		vehicalTicket.clickToElement( CommonPageUIs.DYNAMIC_POINT_ARRVAL);

		log.info("TC_01_Step_18 Chọn bến điểm đến ");
		vehicalTicket.clickToElement( CommonPageUIs.DYNAMIC_POINT_ARRVAL);

		log.info("TC_01_Step_19 Click chọn Tiếp tục");
		vehicalTicket.clickToDynamicText("Tiếp tục");

		log.info("TC_01_Step_19 Click chọn Cho Phép");
		vehicalTicket.clickToDynamicAcceptButton("com.android.packageinstaller:id/permission_allow_button");

		log.info("TC_01_Step_20 Input email");
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.EMAIL, "Nhập thông tin");

		log.info("TC_01_Step_21 Click btn Tiếp tục");
		vehicalTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_22 Click btn Thanh toán");
		vehicalTicket.clickToDynamicButton("Thanh toán");

		log.info("TC_01_Step_23 Click btn Tiếp tục");
		vehicalTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_24 Click btn Tiếp tục");
		vehicalTicket.clickToDynamicButton("Tiếp tục");

		log.info("TC_01_Step_25 Xác minh màn hình xác nhận thông tin");
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PASSWORD_LOGIN, "Nhập mật khẩu");

		log.info("TC_01_Step_23 Click btn Tiếp tục");
		vehicalTicket.clickToDynamicButton("Tiếp tục");
		log.info("TC_01_Step_24 Xác Minh giao dịch thành công");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed("THANH TOÁN THÀNH CÔNG"));
	}

	@Test
	public void TC_02_KiemTraDichSuGiaoDich() {
		log.info("TC_02_Step_01_Lấy mã thanh toán");
		String maThanhToan = vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, "Mã thanh toán");
		log.info("TC_02_Step_02_Lấy Giao dịch");

		log.info("TC_02_Step_03_Lấy số tiền giao dịch");
		String amount = vehicalTicket.getTextDynamicAmount(CommonPageUIs.DYNAMIC_AMOUNT, "com.VCB:id/tvAmount");
		amountFee += amount;
		String replaceAmount = amount.replace("VND", "đ");
		System.out.println(replaceAmount);

		log.info("TC_02_Step_03_Click thực hiện giao dịch mới");
		vehicalTicket.clickToDynamicButton("Thực hiện giao dịch mới");

		log.info("TC_02_Step_05_Hiển thị màn hình đặt vé xe và click lịch sử giao dịch");
		verifyTrue(vehicalTicket.isDynamicMessageAndLabelTextDisplayed("Đặt vé xe"));
		vehicalTicket.clickToDynamicText("Lịch sử đặt vé");

		log.info("TC_02_Step_04_Verify mã thanh toán");
		verifyEquals(vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, "Mã thanh toán"), maThanhToan);

		log.info("TC_02_Step_04_Verify số tiền");
		verifyEquals(vehicalTicket.getTextDynamicFollowTextTable(CommonPageUIs.DYNAMIC_VALUE, "Tổng tiền"), replaceAmount);
	}

	@Test
	public void TC_03_truyVanTaiKhoan() {
		log.info("TC_03_Step_01_Click btn back lan 1");
		vehicalTicket.clickToDynamicButtonBackByID("com.VCB:id/ivTitleLeft");
		log.info("TC_03_Step_02_Click btn back");
		vehicalTicket.clickToDynamicButtonBackByID("com.VCB:id/ivTitleLeft");
		log.info("TC_03_Step_03_Chuyển sang màn hình Đặt vé ");
		vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.ORDER_TICKET);
		log.info("TC_03_Step_04_Click btn menu");
		vehicalTicket.clickToDynamicBottomMenuOrIcon("com.VCB:id/menu_5");
		log.info("TC_03_Step_05_Click chọn phần Báo cáo giao dịch");
		vehicalTicket.clickToDynamicText("Báo cáo giao dịch");
		log.info("TC_03_Step_06_Kiểm tra hiển thị màn hình báo cáo giao dịch ");
		vehicalTicket.isDynamicMessageAndLabelTextDisplayed(VehicalData.DATA_ORDER_TICKET.TITLE_REPORT);

		log.info("TC_03_Step_17: Chọn Tìm kiếm chuyến đi");
		vehicalTicket.clickToDynamicButton(VehicalData.DATA_ORDER_TICKET.BUTTON_SEARCH);
		log.info("TC_03_Step_18: Kiểm tra thông tin giao dịch số tiền đã mua mục TOÀN BỘ ");
		String fee = vehicalTicket.getTextDynamicAmount(CommonPageUIs.DYNAMIC_AMOUNT, "com.VCB:id/tvMoney");
		System.out.println(fee);
		System.out.println(amountFee);
		verifyEquals(fee, amountFee);

	}

	@Test
	public void TC_04_truyVanTaiKhoan() {

		log.info("TC_04_Step_01: Click chọn mục Tiền ra ");
		vehicalTicket.clickToDynamicText("Tiền ra");
		log.info("TC_04_Step_02: Kiểm tra thông tin giao dịch số tiền đã mua mục Tiền ra ");
		String fee = vehicalTicket.getTextDynamicAmount(CommonPageUIs.DYNAMIC_AMOUNT, "com.VCB:id/tvMoney");
		System.out.println(fee);
		System.out.println(amountFee);
		verifyEquals(fee, amountFee);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();

	}

}
