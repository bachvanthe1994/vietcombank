package pageObjects;

import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;



public class PayBillTelevisionPageObject extends AbstractPage {


	public PayBillTelevisionPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AppiumDriver<MobileElement> driver;
	

	public void inputCustomerId(List<String> listViettel) {
		for (int i = 0; i < listViettel.size(); i++) {
			inputIntoEditTextByID(driver, listViettel.get(i), "com.VCB:id/code");
			clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

			String locator = String.format(DynamicPageUIs.DYNAMIC_TEXT_BY_ID, "com.VCB:id/tvTitleBar");
			if (driver.findElements(By.xpath(locator)).size() > 0) {
				break;
			} else {
				clickToDynamicButton(driver, "Đóng");
			}

		}
	}
}
