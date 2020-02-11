package vnpay.vietcombank.SDK_train_ticket;

import java.io.IOException;
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
import vietcombank_test_data.TrainTicket_Data;

public class Validation_TrainTicket_2 extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private TrainTicketPageObject trainTicket;
	List<String> listExpect;
	List<String> listActual;

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
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, "Đặt vé tàu");

		log.info("TC_01_Step_Click close message");
		login.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_01_Check title dat ve tau");
		trainTicket.isDynamicMessageAndLabelTextDisplayed(driver, "ĐẶT VÉ TÀU");

		log.info("TC_06_Click ga khoi hanh");
		trainTicket.clickDynamicPointStartAndEnd(driver, "ĐẶT VÉ TÀU", TrainTicket_Data.textDefault.TITLE_START);

		log.info("TC_06_Nhap text ga khoi hanh");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_START_END_VALID, "0");

		log.info("TC_06_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_06_Nhap text ga den");
		trainTicket.inputToDynamicTextPoint(driver, TrainTicket_Data.inputText.POINT_EDIT_SEARCH, "2");

		log.info("TC_06_Chon gia tri trong danh sach");
		trainTicket.clickToDynamicButtonLinkOrLinkText(driver, TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Check text ga khoi hanh man hinh Dat ve tau truoc khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart(driver, "com.VCB:id/tvTextPickUp"), TrainTicket_Data.inputText.POINT_START_END_VALID);

		log.info("TC_01_Check text ga den man hinh dat ve tau truoc khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart(driver, "com.VCB:id/tvTextArrival"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Click button change");
		trainTicket.clickToDynamicIconChange(driver, TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Check text ga khoi hanh man hinh Dat ve tau sau khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart(driver, "com.VCB:id/tvTextPickUp"), TrainTicket_Data.inputText.POINT_EDIT_SEARCH);

		log.info("TC_01_Check text ga den man hinh dat ve tau sau khi doi ga");
		verifyEquals(trainTicket.getDynamicTextPointStart(driver, "com.VCB:id/tvTextArrival"), TrainTicket_Data.inputText.POINT_START_END_VALID);
	}

	@Test
	public void TC_01_CheckHienThiThoiGianKhoiHanh() {

	}
}
