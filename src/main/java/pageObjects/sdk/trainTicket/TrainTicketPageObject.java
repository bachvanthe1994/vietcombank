package pageObjects.sdk.trainTicket;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.sdk.airTicketBooking.AirTicketBookingUIs;
import vietcombankUI.sdk.hotelBooking.HotelBookingPageUIs;
import vietcombankUI.sdk.trainTicket.TrainTicketPageUIs;


public class TrainTicketPageObject extends AbstractPage {

    public TrainTicketPageObject(AndroidDriver<AndroidElement> mappingDriver) {
	driver = mappingDriver;
    }

    private AndroidDriver<AndroidElement> driver;

    public List<String> getListStatusTransfer(AndroidDriver<AndroidElement> driver, String dynamicIndex) {
	waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_STARUS, dynamicIndex);
	return getTextInListElements(driver, DynamicPageUIs.DYNAMIC_STARUS, dynamicIndex);
    }
    
    public boolean checkSuggestPoint(List<String> listSuggestPoint, String checkedValue) {
		for (String point : listSuggestPoint) {
			if (!point.toLowerCase().contains(checkedValue)) {
				return false;
			}
		}
		return true;
		
		
		
	}
    
    public boolean getSelectedAttributeOfDate(String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		AndroidElement element = driver.findElement(By.xpath(locator));
		return Boolean.parseBoolean(element.getAttribute("selected"));
}
    
	

		
    
    public String getCurentMonthAndYear() {
		LocalDate now = LocalDate.now();
		int month = now.getMonthValue();
		int year = now.getYear();
		return "THÁNG" + " " + month + " " + year;
	}
    
    public String getMonthAndYearFORMAT() {
    	LocalDate now = LocalDate.now();
		int month = now.getMonthValue();
		int year = now.getYear();
		return "T."  + month + " " + year;
	}
    
	
}

    
  

