package vnpay.vietcombank.sdk_train_ticket;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.LogInPageObject;
import pageObjects.sdk.trainTicket.TrainTicketPageObject;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.sdk.trainTicket.TrainTicketPageUIs;
import vnpay.vietcombank.sdk_train_ticket_data.TrainTicket_Data;

public class Validation_TrainTicket_3 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TrainTicketPageObject trainTicket;
	List<String> listExpect;
	List<String> listActual;
	private String currentDay = getCurrentDay();
	private String currentMonth = getCurrenMonth();
	private String currentYear = getCurrentYear();

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
	}

	@Test
	public void TC_01_DoiGaKhoiHanhVaGaDen() {
		trainTicket = PageFactoryManager.getTrainTicketPageObject(driver);

		log.info("TC_01_Step_Click dat ve tau");
		trainTicket.clickToDynamicButtonLinkOrLinkText("Đặt vé tàu");

		log.info("TC_01_Step_Click close message");
		trainTicket.clickToDynamicButton("Đồng ý");

		log.info("TC_01_Check title dat ve tau");
		verifyTrue(trainTicket.isDynamicMessageAndLabelTextDisplayed("ĐẶT VÉ TÀU"));

		log.info("TC_01_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd("ĐẶT VÉ TÀU", "com.VCB:id/tvTextPickUp");

		log.info("TC_01_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_START_END_VALID, "com.VCB:id/linPickUp");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_01_Nhap text ga den");
		trainTicket.inputToDynamicTextPoint(TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "com.VCB:id/linArival");

		log.info("TC_01_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Check text ga khoi hanh man hinh Dat ve tau truoc khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart("com.VCB:id/tvTextPickUp"), TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_01_Check text ga den man hinh dat ve tau truoc khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart("com.VCB:id/tvTextArrival"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Click button change");
		trainTicket.clickToDynamicIconChange(TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Check text ga khoi hanh man hinh Dat ve tau sau khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart("com.VCB:id/tvTextPickUp"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Check text ga den man hinh dat ve tau sau khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart("com.VCB:id/tvTextArrival"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}
}