package pageObjects.sdk.trainTicket;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.DynamicPageUIs;


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
			if (!convertVietNameseStringToString(point).toLowerCase().contains(checkedValue.toLowerCase())) {
				return false;
			}
		}
		return true;
    }
    
		public String convertVietNameseStringToString(String vietnameseString) {
			String temp = Normalizer.normalize(vietnameseString, Normalizer.Form.NFD);
			Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
			return pattern.matcher(temp).replaceAll("");
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

    
  

