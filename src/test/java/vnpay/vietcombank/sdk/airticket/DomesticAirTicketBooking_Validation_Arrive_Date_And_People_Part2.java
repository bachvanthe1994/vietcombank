package vnpay.vietcombank.sdk.airticket;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.sdk.airTicketBooking.DynamicAirTicketBookingObjects;

public class DomesticAirTicketBooking_Validation_Arrive_Date_And_People_Part2 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private DynamicAirTicketBookingObjects airTicket;
	private String yesterday = getBackWardDay(1);
	private String twoDaysAgo = getBackWardDay(2);

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		log.info("Before class: Dang nhap ");
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		log.info("Before class: Click Dat ve may bay ");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicIcon(driver, "Đặt vé máy bay");

		log.info("Before class: Click dat ve may bay noi dia ");
		airTicket = PageFactoryManager.getDynamicAirTicketBooking(driver);
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("Before class: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("Before class: Chon mot chieu ");
		airTicket.clickToDynamicTextOrButtonLink("Một chiều");

	}

	@Test
	public void TC_01_KiemTraGiaoDienManHinhNgayVe2Chieu() {

		log.info("TC_1_Step 01: Click Khu Hoi");
		airTicket.clickToDynamicTextOrButtonLink("Khứ hồi");

		log.info("TC_1_Step 02: Click Ngay Đi");
		airTicket.clickToDynamicTextOrButtonLink("Ngày về");

		log.info("TC_1_Step 03: Kiem tra giao dien cua chon ngay ve khu hoi");
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleLeft"));
		verifyTrue(airTicket.isDynamicIconDisplayed("com.VCB:id/ivTitleRight"));
		verifyTrue(airTicket.isDynamicTextDisplayed("Chọn ngày bay"));
		verifyTrue(airTicket.isDynamicTextDisplayed("Ngày đi"));
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvNgayDi"), "Chọn ngày");
		verifyTrue(airTicket.isDynamicTextDisplayed("Ngày về"));
		verifyEquals(airTicket.getDynamicTextByID("com.VCB:id/tvNgayVe"), "Chọn ngày");

	}

	@Test
	public void TC_02_KiemTraNgayDiNhoHonNgayHienTai() {
		log.info("TC_02_Step 01: Chon  2 ngay truoc");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearMinusDays(2), twoDaysAgo);

		log.info("TC_02_Step 02: Chon ngay hom qua");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearMinusDays(1), yesterday);

		log.info("TC_02_Step 03: Click xac nhan");
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_02_Step 04: Click quay lai");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

		log.info("TC_02_Step 05: Kiem tra text chon ngay hien thi");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Ngày đi", "com.VCB:id/tvValue"), "Chọn ngày");

		log.info("TC_02_Step 06: Kiem tra text chon ngay hien thi");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Ngày về", "com.VCB:id/tvValue"), "Chọn ngày");

	}

	@Test
	public void TC_03_KiemTraNgayDiNhoHonNgayHienTai11Thang() {
		log.info("TC_03_Step 01: Lay ngay cuoi cung cua thang");
		Calendar calendar = Calendar.getInstance();
		int lastDate = calendar.getActualMaximum(Calendar.DATE);
		calendar.set(Calendar.DATE, lastDate);
		int lastDay = calendar.get(Calendar.DAY_OF_MONTH);

		log.info("TC_03_Step 02: Lay Thang cach thang hien tai 11 thang");
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, 11);
		String next11Month = "THÁNG " + (date.get(Calendar.MONTH) + 1) + " " + date.get(Calendar.YEAR);
		String nextDay = getForWardDay(1);
		String next2Days = getForWardDay(2);

		log.info("TC_03_Step 03: Lay ngay hien tai dang focus");
		LocalDate now = LocalDate.now();
		String curentFocusDay = now.getDayOfMonth() + "";

		if (!curentFocusDay.equals(lastDay + "")) {
			log.info("TC_03_Step 01: Click Ngay Đi");
			airTicket.clickToDynamicTextOrButtonLink("Ngày về");
			log.info("TC_03_Step 02: Chon  1 ngay truoc ngay hien tai 11 thang");
			airTicket.clickToDynamicDay(next11Month, nextDay);

			log.info("TC_03_Step 02: Chon 2 ngay truoc ngay hien tai 11 thang");
			airTicket.clickToDynamicDay(next11Month, next2Days);

			log.info("TC_03_Step 04: Click xac nhan");
			airTicket.clickToDynamicButton("Xác nhận");

			log.info("TC_03_Step 05: Click quay lai");
			airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

			log.info("TC_03_Step 06: Kiem tra text chon ngay hien thi");
			verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Ngày đi", "com.VCB:id/tvValue"), "Chọn ngày");

			log.info("TC_03_Step 07: Kiem tra text chon ngay hien thi");
			verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Ngày về", "com.VCB:id/tvValue"), "Chọn ngày");
		}
	}

	@Test
	public void TC_04_KiemTracChonNgayHopLeVoiVe2Chieu() {

		log.info("TC_03_Step 01: Click Ngay Đi");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");

		log.info("TC_04_Step 03: Click Chon Ngay Di va Ngay Ve");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(1), getForWardDay(1));
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(2), getForWardDay(2));

		log.info("TC_04_Step 04: Click xac nhan");
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_04_Step 05: Kiem tra ngay di ngay ve da duoc chon");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Ngày đi", "com.VCB:id/tvValue"), getForwardDate(1));
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Ngày về", "com.VCB:id/tvValue"), getForwardDate(2));

	}

	@Test
	public void TC_05_KiemTraChonNgayDiVaNgayVeTrungNhau() {

		log.info("TC_05_Step 01: Click Ngay Đi");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");

		log.info("TC_05_Step 03: Click Chon Ngay Di va Ngay Ve");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(1), getForWardDay(1));
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(1), getForWardDay(1));

		log.info("TC_05_Step 04: Click xac nhan");
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_05_Step 05: Kiem tra ngay di ngay ve da duoc chon");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Ngày đi", "com.VCB:id/tvValue"), getForwardDate(1));
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Ngày về", "com.VCB:id/tvValue"), getForwardDate(1));

		log.info("TC_05_Step 06: Click quay lai");
		airTicket.clickToDynamicIcon("com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_06_KiemTraNgayDiKhiChonTuKhuHoiSang1Chieu() {

		log.info("TC_06_Step 01: Click Dat ve may bay noi dia");
		airTicket.clickToDynamicTextOrButtonLink("Đặt vé máy bay Nội địa");

		log.info("TC_06_Step 02: Click Dong y ");
		airTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_06_Step 03: Click Khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink("Khứ hồi");

		log.info("TC_06_Step 04: Click Ngay Đi");
		airTicket.clickToDynamicTextOrButtonLink("Ngày đi");

		log.info("TC_06_Step 05: Click Chon Ngay Di va Ngay Ve");
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(1), getForWardDay(1));
		airTicket.clickToDynamicDay(airTicket.getCurentMonthAndYearPlusDays(2), getForWardDay(1));

		log.info("TC_06_Step 06: Click xac nhan");
		airTicket.clickToDynamicButton("Xác nhận");

		log.info("TC_06_Step 07: Click Mot chieu");
		airTicket.clickToDynamicTextOrButtonLink("Một chiều");

		log.info("TC_06_Step 09: Kiem tra ngay di ngay ve da duoc chon");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Ngày đi", "com.VCB:id/tvValue"), getForwardDate(1));

	}

	@Test
	public void TC_07_KiemTraNgayDiKhiChonTuKhuHoiSang1ChieuQuayLaiKhuHoi() {

		log.info("TC_07_Step 01: Click Khu hoi ");
		airTicket.clickToDynamicTextOrButtonLink("Khứ hồi");

		log.info("TC_07_Step 02: Kiem tra ngay di da duoc chon");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Ngày đi", "com.VCB:id/tvValue"), getForwardDate(1));

		log.info("TC_07_Step 03: Kiem tra ngay ve bi reset");
		verifyEquals(airTicket.getTextInDynamicDropDownByLabel("Ngày về", "com.VCB:id/tvValue"), "Chọn ngày");

	}

	@Test
	public void TC_08_KiemTraThayDoiSoLuongNguoiLon() {

		log.info("TC_08_Step 01: Kiem tra so luong nguoi nho nhat ");
		for (int i = 0; i < 2; i++) {
			airTicket.clickToDynamicPlusAndMinusIcon("Người lớn (12 tuổi trở lên)", "-");
			verifyEquals(airTicket.getTextSelectedPeople("Người lớn (12 tuổi trở lên)"), "1");

		}
		log.info("TC_08_Step 02: Kiem tra so luong nguoi  lon lon nhat ");
		for (int i = 1; i < 9; i++) {
			airTicket.clickToDynamicPlusAndMinusIcon("Người lớn (12 tuổi trở lên)", "+");
			verifyEquals(airTicket.getTextSelectedPeople("Người lớn (12 tuổi trở lên)"), i + 1 + "");

		}
		log.info("TC_08_Step 03: Kiem tra so luong nguoi  lon lon nhat ");
		airTicket.clickToDynamicPlusAndMinusIcon("Người lớn (12 tuổi trở lên)", "+");
		verifyEquals(airTicket.getTextSelectedPeople("Người lớn (12 tuổi trở lên)"), "9");

		log.info("TC_08_Step 04: Kiem tra so luong nguoi giam dan");
		airTicket.clickToDynamicPlusAndMinusIcon("Người lớn (12 tuổi trở lên)", "-");
		verifyEquals(airTicket.getTextSelectedPeople("Người lớn (12 tuổi trở lên)"), "8");
	}

	@Test
	public void TC_09_KiemTraThayDoiSoLuongTreEm() {
		log.info("TC_09_Step 01: Kiem tra so luong tre em nho nhat ");
		for (int i = 0; i < 2; i++) {
			airTicket.clickToDynamicPlusAndMinusIcon("Trẻ em (2 đến dưới 12 tuổi)", "-");
			verifyEquals(airTicket.getTextSelectedPeople("Trẻ em (2 đến dưới 12 tuổi)"), "0");

		}

		log.info("TC_09_Step 02: Kiem tra so luong tre em lon nhat ");
		for (int i = 0; i < 6; i++) {
			airTicket.clickToDynamicPlusAndMinusIcon("Trẻ em (2 đến dưới 12 tuổi)", "+");
			verifyEquals(airTicket.getTextSelectedPeople("Trẻ em (2 đến dưới 12 tuổi)"), i + 1 + "");

		}
		log.info("TC_09_Step 03: Kiem tra so luong tre em lon nhat ");
		airTicket.clickToDynamicPlusAndMinusIcon("Trẻ em (2 đến dưới 12 tuổi)", "+");
		verifyEquals(airTicket.getTextSelectedPeople("Trẻ em (2 đến dưới 12 tuổi)"), "6");

		log.info("TC_09_Step 04: Kiem tra so luong tre em giam dan");
		airTicket.clickToDynamicPlusAndMinusIcon("Trẻ em (2 đến dưới 12 tuổi)", "-");
		verifyEquals(airTicket.getTextSelectedPeople("Trẻ em (2 đến dưới 12 tuổi)"), "5");
	}

	@Test
	public void TC_10_KiemTraThayDoiSoLuongEmBe() {
		log.info("TC_10_Step 01: Kiem tra so luong  em  be nho nhat ");
		for (int i = 0; i < 2; i++) {
			airTicket.clickToDynamicPlusAndMinusIcon("Em bé (Dưới 2 tuổi)", "-");
			verifyEquals(airTicket.getTextSelectedPeople("Em bé (Dưới 2 tuổi)"), "0");

		}
		log.info("TC_10_Step 02: Kiem tra so luong  em be lon nhat ");
		for (int i = 0; i < 9; i++) {
			airTicket.clickToDynamicPlusAndMinusIcon("Em bé (Dưới 2 tuổi)", "+");
			verifyEquals(airTicket.getTextSelectedPeople("Em bé (Dưới 2 tuổi)"), i + 1 + "");

		}
		log.info("TC_10_Step 04: Kiem tra so luong tre em lon nhat ");
		airTicket.clickToDynamicPlusAndMinusIcon("Em bé (Dưới 2 tuổi)", "+");
		verifyEquals(airTicket.getTextSelectedPeople("Em bé (Dưới 2 tuổi)"), "9");

		log.info("TC_10_Step 05: Kiem tra so luong em be giam dan");
		airTicket.clickToDynamicPlusAndMinusIcon("Em bé (Dưới 2 tuổi)", "-");
		verifyEquals(airTicket.getTextSelectedPeople("Em bé (Dưới 2 tuổi)"), "8");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
