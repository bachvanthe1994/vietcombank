package vnpay.vietcombank.sdk.vehicleTickets;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.LogInPageObject;
import vehicalPageObject.VehicalPageObject;
import vnpay.vietcombank.sdk.vehicleTicketData.VehicalData;

public class VehicalTicket_1 extends Base {
    AndroidDriver<AndroidElement> driver;
    private LogInPageObject login;
    private VehicalPageObject vehicalTicket;

    @Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
    @BeforeClass
    public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt)
	    throws IOException, InterruptedException {
	startServer();
	log.info("Before class: Mo app ");
	driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
	login = PageFactoryManager.getLoginPageObject(driver);
	vehicalTicket = PageFactoryManager.getVehicalPageObject(driver);
	login.Global_login(phone, pass, opt);

	vehicalTicket.Vehical_login();
    }

    @Test
    public void TC_01_KiemTraLuongDatVeThanhCong() throws InterruptedException {
	log.info("TC_01_Step_1: Chọn và nhập điểm đi");
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_1, VehicalData.DATA_ORDER_TICKET.PLACE_3);

	log.info("TC_01_Step_1: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.ARRIVAL);
	vehicalTicket.inputToDynamicInputBox(VehicalData.DATA_ORDER_TICKET.PLACE_3, VehicalData.DATA_ORDER_TICKET.ARRIVAL);
	vehicalTicket.clickToDynamicText(VehicalData.DATA_ORDER_TICKET.PLACE_3);
    }

}
