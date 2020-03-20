package pageObjects;

import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import commons.Constants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;

public class LandLinePhoneChargePageObject extends AbstractPage {
	public LandLinePhoneChargePageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public static String phoneNumber = "";
	public void inputPhoneNumberLandLinePhoneCharge(List<String> listPhone) {
		boolean check = false;
		for (String phone : listPhone) {
			inputIntoEditTextByID(driver, phone, "com.VCB:id/code");
			clickToDynamicButton(driver, "Tiếp tục");
			
			overRideTimeOut(driver, 5);
			String locator = String.format(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Xác nhận thông tin");
			List<MobileElement> elementsOne = driver.findElements(By.xpath(locator));
			
			if(elementsOne.size() > 0) {
				check = true;
				phoneNumber = phone;
				break;
			}
			else{
				clickToDynamicButton(driver, "Đóng");
				continue;
			}
			
		}
		overRideTimeOut(driver, Constants.LONG_TIME);
		if (!check) {
			throw new RuntimeException("Khong co hoa don nao de thanh toan");
		}
	}
	
}
