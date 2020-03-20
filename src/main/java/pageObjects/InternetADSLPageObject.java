package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.SetupContactPageUIs;



public class InternetADSLPageObject extends AbstractPage {


	public InternetADSLPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AppiumDriver<MobileElement> driver;
	
public String inputCustomerCode (String codeCustomer) {
	for (int i=0; i<=30; i++){
		codeCustomer = i +"";
		inputIntoEditTextByID(driver, codeCustomer, "com.VCB:id/code");
	}
	return codeCustomer;
	}
}
