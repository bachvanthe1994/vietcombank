package pageObjects;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import vietcombankUI.TransferMoneyCharityPageUIs;

public class TransferMoneyCharityPageObject extends AbstractPage {
	public TransferMoneyCharityPageObject(AndroidDriver<AndroidElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AndroidDriver<AndroidElement> driver;

	public long canculateAvailableBalances(long surPlus, long money) {
		return surPlus - money;

	}

	public String getTextCurrencyCharity() {

		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TransferMoneyCharityPageUIs.TEXT_VIEW_CURRENCY_CHARITY);
		if (status == true) {
			text = getTextElement(driver, TransferMoneyCharityPageUIs.TEXT_VIEW_CURRENCY_CHARITY);

		}
		return text;

	}

	public List<String> getListOrganizationCharity() {
		boolean status = false;
		List<String> listOrganizationCharity = null;
		status = waitForElementVisible(driver, TransferMoneyCharityPageUIs.TEXT_VIEW_LIST_ORGANIZATION);
		if (status) {
			List<AndroidElement> listElement = driver.findElements(By.xpath(TransferMoneyCharityPageUIs.TEXT_VIEW_LIST_ORGANIZATION));
			listOrganizationCharity = new ArrayList<String>();

			for (AndroidElement element : listElement) {
				listOrganizationCharity.add(element.getText());
			}
		}
		return listOrganizationCharity;

	}

	public void inputOTPInvalidBy_N_Times(int time) {
		for (int i = 0; i < time; i++) {
			inputToDynamicOtpOrPIN(driver, "213456", "Tiếp tục");
			clickToDynamicButton(driver, "Tiếp tục");
			if (i < time - 1) {
				clickToDynamicButton(driver, "Đóng");
			}
		}
	}

	public void inputPasswordInvalidBy_N_Times(int time) {
		for (int i = 0; i < time; i++) {
			inputToDynamicPopupPasswordInput(driver, "12345678", "Tiếp tục");
			clickToDynamicButton(driver, "Tiếp tục");
			if (i < time - 1) {
				clickToDynamicButton(driver, "Đóng");
			}
		}
	}
	
	public String convertEUROToVNeseMoney(String money, String currentcy) {
		String result = "";
		try {
			result =  String.format("%,d", Math.round(Double.parseDouble(money) * Double.parseDouble(currentcy))) + " VND";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	public double convertAvailableBalanceCurrentcyToDouble(String money) {
		double result = 0;
		try {
			result = Double.parseDouble(money.replaceAll("[^\\.0123456789]",""));
		}catch (Exception e) {
			
		}
		return result;
	}
	
	public long convertAvailableBalanceCurrentcyToLong(String money) {
		long result = 0;
		try {
			result = Long.parseLong(money.replaceAll("[^\\.0123456789]",""));
		}catch (Exception e) {
			
		}
		return result;
	}
	
	public String convertEURO_USDToVNeseMoney(String money, String currentcy) {
		String result = "";
		try {
			result =  String.format("%,d", Math.round(Double.parseDouble(money) * Double.parseDouble(currentcy))) + " VND";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	public long canculateAvailableBalances(long surPlus, long money, long transactionFree) {
		return surPlus - money - transactionFree;
	}
	
	public double canculateAvailableBalancesCurrentcy(double surPlus, double money, double transactionFree) {
		return surPlus - money - transactionFree;
	}
	
	public String convertDateTimeIgnoreSecond(String stringDate) {
		String result = "";
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd HH:mm");
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
	    	result = stringDate.split(" ")[0] + stringDate.split(" ")[3];
	    }
	    catch (Exception e) {
			
		}
		return result;
		
	}
	
}
