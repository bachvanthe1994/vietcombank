package vnpay.vietcombank.vehicleTickets;

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
import vnpay.vietcombank.vehicleTicketData.VehicalData;

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
	vehicalTicket.clickToDynamicText(VehicalData.Data_ORDER_TICKET.FROMT);
	vehicalTicket.inputToDynamicInputBox(VehicalData.Data_ORDER_TICKET.DEPARTURE, VehicalData.Data_ORDER_TICKET.FROMT_INPUT);

	log.info("TC_01_Step_1: Chọn và nhập điểm đến");
	vehicalTicket.clickToDynamicButtonLinkOrLinkText(VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.inputToDynamicInputBox(VehicalData.Data_ORDER_TICKET.DESTINATION, VehicalData.Data_ORDER_TICKET.TO_INPUT);
	vehicalTicket.clickToDynamicText(VehicalData.Data_ORDER_TICKET.DESTINATION);

    }

}
