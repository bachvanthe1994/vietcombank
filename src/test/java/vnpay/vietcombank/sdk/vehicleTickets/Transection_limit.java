package vnpay.vietcombank.sdk.vehicleTickets;

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
import vehicalPageObject.VehicalPageObject;
import vietcombankUI.sdk.trainTicket.TrainTicketPageUIs;
import vietcombank_test_data.TransferIdentity_Data;
import vnpay.vietcombank.sdk.vehicleTicket.data.VehicalData;

public class Transection_limit extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private VehicalPageObject vehicalTicket;
	LocalDate now = LocalDate.now();
	String today = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now)) + " " + getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String tommorrowDate = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now) + 1) + " " + getForwardDate(1);
	String tomorrow_week = convertDayOfWeekVietNamese2(getCurrentDayOfWeek(now) + 2) + " " + getForwardDate(2);
	List<String> listActual;
	List<String> listExpect;

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
		login.scrollDownToText(driver, "© 2019 Vietcombank");
		login.scrollIDownOneTime(driver);

		vehicalTicket.clickToDynamicText("Đặt vé xe");

		vehicalTicket.clickToDynamicButton("Đồng ý");
	}

	@Test
	public void TC_01_NhoHonHanMucToiThieuTrenMotGiaoDich() {
		log.info("TC_01_Step_1: Chọn và nhập điểm đi");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_01_Step_2: Chọn và nhập điểm đến");
		vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_2, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_2);

		log.info("TC_01_Step_3: Chọn ngày mai");
		vehicalTicket.clickToDynamicTomorrowAndFilterTrip("com.VCB:id/lnNextday");

		log.info("TC_01_Step_4: click button tìm kiếm chuyến đi");
		vehicalTicket.clickToDynamicButton("Tìm kiếm chuyến đi");

		log.info("TC_01_Step_5: click chọn ghê");
		vehicalTicket.clickToDynamicText("Chọn ghế");

		log.info("TC_01_Step_6: Click chon cho trong");
		for (int i = 2; i < 4; i++) {
			listActual = vehicalTicket.chooseSeats(i, "(255,255,255)");
		}
		
		log.info("TC_01_Step_7: click dat cho");
		vehicalTicket.clickToDynamicText("Đặt chỗ");
		
		log.info("TC_01_Step_8: click dat cho");
		vehicalTicket.clickToDynamicText("Tiếp tục");
		
		log.info("TC_01_Step_9: click tu choi");
		vehicalTicket.clickToDynamicButton("Từ chối");
		
		log.info("TC_01_Step_10: nhap email");
		vehicalTicket.clickDynamicEditText("com.VCB:id/email");
		vehicalTicket.clickToDynamicTextOrButtonLink("nghiepvutest502@gmail.com");
		vehicalTicket.clickToDynamicTextByID("com.VCB:id/tvWarning");
		
		log.info("TC_01_Step_11: click dat cho");
		vehicalTicket.clickToDynamicButton("Tiếp tục");
		
		log.info("TC_01_Step_12: click dat cho");
		vehicalTicket.clickToDynamicButton("Thanh toán");
		
		log.info("TC_01_Step_13: click dat cho");
		vehicalTicket.clickToDynamicButton("Tiếp tục");
		
		log.info("TC_01_STEP_14: kiem tra thong bao khi nhap so tien nho hơn han muc nho nhat cua 1 lan giao dich");
		verifyEquals(vehicalTicket.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), VehicalData.NOTIFICATION.NOTI_MIN_TRANSECTION);
	}
	
	@Test
	public void TC_02_LonHonHanMucToiDaTrenMotGiaoDich() {
		log.info("TC_01_Step_1: Chọn và nhập điểm đi");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
		vehicalTicket.inputToDynamicInputBoxID(VehicalData.DATA_ORDER_TICKET.PLACE_1, "com.VCB:id/linPickUp");
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_1);

		log.info("TC_01_Step_2: Chọn và nhập điểm đến");
		vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_2, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
		vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_2);

		log.info("TC_01_Step_3: Chọn ngày mai");
		vehicalTicket.clickToDynamicTomorrowAndFilterTrip("com.VCB:id/lnNextday");

		log.info("TC_01_Step_4: click button tìm kiếm chuyến đi");
		vehicalTicket.clickToDynamicButton("Tìm kiếm chuyến đi");

		log.info("TC_01_Step_5: click chọn ghê");
		vehicalTicket.clickToDynamicText("Chọn ghế");

		log.info("TC_01_Step_6: Click chon cho trong");
		for (int i = 2; i < 4; i++) {
			listActual = vehicalTicket.chooseSeats(i, "(255,255,255)");
		}
		
		log.info("TC_01_Step_7: click dat cho");
		vehicalTicket.clickToDynamicText("Đặt chỗ");
		
		log.info("TC_01_Step_8: click dat cho");
		vehicalTicket.clickToDynamicText("Tiếp tục");
		
		log.info("TC_01_Step_9: click tu choi");
		vehicalTicket.clickToDynamicButton("Từ chối");
		
		log.info("TC_01_Step_10: nhap email");
		vehicalTicket.clickDynamicEditText("com.VCB:id/email");
		vehicalTicket.clickToDynamicTextOrButtonLink("nghiepvutest502@gmail.com");
		vehicalTicket.clickToDynamicTextByID("com.VCB:id/tvWarning");
		
		log.info("TC_01_Step_11: click dat cho");
		vehicalTicket.clickToDynamicButton("Tiếp tục");
		
		log.info("TC_01_Step_12: click dat cho");
		vehicalTicket.clickToDynamicButton("Thanh toán");
		
		log.info("TC_01_Step_13: click dat cho");
		vehicalTicket.clickToDynamicButton("Tiếp tục");
		
		log.info("TC_01_STEP_14: kiem tra thong bao khi nhap so tien nho hơn han muc nho nhat cua 1 lan giao dich");
		verifyEquals(vehicalTicket.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), VehicalData.NOTIFICATION.NOTI_MAX_TRANSECTION);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();

	}

}
