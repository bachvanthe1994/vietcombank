package pageObjects;

import java.text.SimpleDateFormat;
import java.util.List;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.TransferMoneyInVCBPageUIs;

public class TransferMoneyInVcbPageObject extends AbstractPage {

	public TransferMoneyInVcbPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public void inputFrequencyNumber(String inputValue) {

		boolean status = false;
		status = waitForElementVisible(driver, TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT);
		if (status == true) {
			clearText(driver, TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT);
			sendKeyToElement(driver, TransferMoneyInVCBPageUIs.FREQUENCY_NUMBER_INPUT, inputValue);
		}
	}

	public void chooseDateNextYearInDatePicker(String dynamicIDValue, String year) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_VIEW_BY_ID, dynamicIDValue);
		if (status == true) {
			clickToDynamicButtonLinkOrLinkText(driver, String.valueOf(Integer.parseInt(year) - 1));
			clickToDynamicButtonLinkOrLinkText(driver, year);

		}

	}

	public boolean checkDateNextYearEnable(String dynamicIDValue, int index) {
		boolean status = false;
		boolean firstDate = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_VIEW_BY_ID, dynamicIDValue);
		if (status == true) {
			List<String> listDate = getEnableInListElements(driver, DynamicPageUIs.DYNAMIC_VIEW_BY_ID, dynamicIDValue);

			if (index < 0) {
				return false;
			}

			if (index >= listDate.size()) {
				return false;
			}

			firstDate = Boolean.parseBoolean(listDate.get(index));
		}
		return firstDate;

	}
	
	public long canculateAvailableBalances(long surPlus, long money, long transactionFree) {
		return surPlus - money - transactionFree;
	}

	public double canculateAvailableBalancesCurrentcy(double surPlus, double money, double transactionFree) {
		return surPlus - money - transactionFree;
	}

	public double convertAvailableBalanceCurrentcyToDouble(String money) {
		double result = 0;
		try {
			result = Double.parseDouble(money.replaceAll("[^\\.0123456789]", ""));
		} catch (Exception e) {

		}
		return result;
	}

	public long convertAvailableBalanceCurrentcyToLong(String money) {
		long result = 0;
		try {
			result = Long.parseLong(money.replaceAll("[^\\.0123456789]", ""));
		} catch (Exception e) {

		}
		return result;
	}

	public String convertEURO_USDToVNeseMoney(String money, String currentcy) {
		String result = "";
		try {
			result = String.format("%,d", Math.round(Double.parseDouble(money) * Double.parseDouble(currentcy))) + " VND";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	public String convertDateTimeIgnoreSecond(String stringDate) {
		String result = "";
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    try {
	    	result = formatter2.format(formatter1.parse(stringDate));
	    }
	    catch (Exception e) {
			
		}
		return result;
		
	}
	
	public String convertTransferTimeToReportDateTime(String stringDate) {
		String result = "";
	    try {
	    	result = stringDate.split(" ")[3] + " " + stringDate.split(" ")[0];
	    }
	    catch (Exception e) {
			
		}
		return result;
		
	}
	
}
