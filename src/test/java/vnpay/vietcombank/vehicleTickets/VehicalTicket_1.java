package vnpay.vietcombank.vehicleTickets;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.LogInPageObject;
import vehicalPageObject.VehicalPageObject;
import vnpay.vietcombank.vehicleTicketData.VehicalData;

public class VehicalTicket_1 extends Base {
    AndroidDriver<AndroidElement> driver;
    private LogInPageObject login;
    private VehicalPageObject vehicalTicket;

    @Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
    @BeforeClass
    public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
	startServer();
	log.info("Before class: Mo app ");
	driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

	login = PageFactoryManager.getLoginPageObject(driver);
	vehicalTicket = PageFactoryManager.getVehicalPageObject(driver);

	log.info("Before class: Click Allow Button");
	login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

	log.info("TC_00_Step_1: chon tiep tuc");
	login.inputToDynamicLogInTextBox(driver, VehicalData.Login.PHONE, "Tiếp tục");

	log.info("TC_00_Step_2: chon tiep tuc");
	login.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_00_Step_3: chon tiep tuc");
	login.inputToDynamicInputBox(driver, VehicalData.Login.NEW_PASSWORD, VehicalData.Login.PASSWORD_LABEL);

	log.info("TC_00_Step_4: chon tiep tuc");
	login.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_00_Step_5: chon tiep tuc");
	login.inputToDynamicOtpOrPIN(driver, VehicalData.Login.OTP, "Tiếp tục");

	log.info("TC_00_Step_6: chon tiep tuc");
	login.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_00_Step_7: chon tu choi");
	login.clickToDynamicButton(driver, "TỪ CHỐI");

	log.info("TC_00_Step_8: chon đặt vé xe");
	login.clickToDynamicButtonLinkOrLinkText(driver, "Đặt vé xe");

	log.info("TC_00_Step_9: chon đồng ý");
	login.clickToDynamicButton(driver, "Đồng ý");
    }

    @Test
    public void TC_01_KiemTraLuongDatVeThanhCong() throws InterruptedException {
	log.info("TC_01_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data.FROMT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data.DEPARTURE, VehicalData.Data.FROMT_INPUT);

	log.info("TC_01_Step_1: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicButtonLinkOrLinkText(driver, VehicalData.Data.TO_INPUT);
	vehicalTicket.inputToDynamicInputBox(driver, VehicalData.Data.DESTINATION, VehicalData.Data.TO_INPUT);
	TimeUnit.SECONDS.sleep(2);
	vehicalTicket.clickToDynamicText(driver, VehicalData.Data.DESTINATION);
    }

}
