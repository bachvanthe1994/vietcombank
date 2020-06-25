package vnpay.vietcombank.web_backend_setup;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import commons.WebAbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.WebBackendSetupPageObject;

public class Setup_Assign_Services_Type_Limit extends Base {

	AppiumDriver<MobileElement> driver;
	private WebDriver driver1;
	


	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
		public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
			WebAbstractPage webBackend = new WebAbstractPage();
			driver1 = openMultiBrowser("chrome", "83.0.4103.14", "http://10.22.7.91:2021/HistorySMS/Index?f=5&c=107");
		
			webBackend.inputIntoInputByID(driver1, "hieppt", "login-username");
			webBackend.inputIntoInputByID(driver1, "123456a@", "login-password");
			webBackend.clickToDynamicButtonByID(driver1, "btn-login");
			
			WebBackendSetupPageObject webBackend1 = new WebBackendSetupPageObject(driver1);
			 webBackend1 = PageFactoryManager.getWebBackendSetupPageObject(driver1);
		}

		@Test
		public void TC_01_SetupLimitBackend() {
			
		

		}

	}

