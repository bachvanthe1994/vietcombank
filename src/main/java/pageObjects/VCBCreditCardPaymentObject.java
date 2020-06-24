package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import commons.AbstractPage;
import commons.Constants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import model.SourceAccountModel;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.creaditCardPaymentUI;
import vietcombankUI.sdk.filmTicketBooking.FilmTicketBookingPageUIs;



public class VCBCreditCardPaymentObject extends AbstractPage {


	public VCBCreditCardPaymentObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AppiumDriver<MobileElement> driver;
	
	public void clickToTextViewByLinearLayoutIndex( String... dynamicIndex) {
		boolean status = false;
		status = waitForElementVisible(driver, creaditCardPaymentUI.DYNAMIC_LINEAERLAYOUT_BY_TEXT, dynamicIndex);
		if (status == true) {
			clickToElement(driver, creaditCardPaymentUI.DYNAMIC_LINEAERLAYOUT_BY_TEXT, dynamicIndex);
		}
	}
	
	public boolean isDynamicMessageAndLabelTextDisplayed( String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		}
		return isDisplayed;

	}
	public String getDynamicTextView( String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible( driver,creaditCardPaymentUI.DYNAMIC_TEXT_VIEW, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, creaditCardPaymentUI.DYNAMIC_TEXT_VIEW, dynamicTextValue);
		}
		return text;
	}
	
	public void swipeElementToElement(String locatorStart, String locatorEnd, String dynamicValueStart, String dynamicValueEnd) {
		Dimension size = driver.manage().window().getSize();
		locatorStart = String.format(locatorStart, dynamicValueStart);
		MobileElement elementStart = driver.findElement(By.xpath(locatorStart));

		locatorEnd = String.format(locatorEnd, dynamicValueEnd);
		MobileElement elementEnd = driver.findElement(By.xpath(locatorEnd));

		int xStart = elementStart.getLocation().getX();
		int yStart = elementStart.getLocation().getY();

		int xEnd = elementEnd.getLocation().getX();
		int yEnd = elementEnd.getLocation().getY();

		new TouchAction(driver).longPress(PointOption.point(xStart, yStart)).moveTo(PointOption.point(xEnd, yEnd)).release().perform();
	}
	
	public List<String> getListAccount() {
		List<String> listCity = new ArrayList<String>();
		List<String> temList = new ArrayList<String>();
		boolean check = true;

		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, "com.VCB:id/tvContent");
		if (status) {
			int count = 0;
			while (check && count <= 6 ) {
				temList = getTextInListElements(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, "com.VCB:id/tvContent");
				for (String text : temList) {
					if (listCity.contains(text)) {
						continue;
						
					} else {
						listCity.add(text);
						
					}

				}
				swipeElementToElement(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, temList.get(temList.size() - 1), "Chọn số thẻ");
				count ++;
				
			}
		}

		return listCity;

	}
	
	// Kiem tra text co trong PageSource hay khong
		public boolean isTextDisplayedInPageSource( String dynamicText) {
			sleep(driver, 5000);
			return driver.getPageSource().contains(dynamicText);
		}
		
		public boolean isTextDisplayed (String... dynamicText) {
			return isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicText);
			
		}
	
}
