package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.TransferIdentityPageUIs;

public class TransferIdentiryPageObject extends AbstractPage {
	public TransferIdentiryPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

    public String getDynamicAmountLabelConvertVNDToLong(AndroidDriver<AndroidElement> driver, String dynamicTextValue) {
	String moneyString = getDynamicAmountLabel(driver, dynamicTextValue);
	moneyString = moneyString.replaceAll("Phí: ", "");
	moneyString = moneyString.replaceAll(",", "");
	moneyString = moneyString.replaceAll("VND/giao dịch", "");
	return moneyString;
	}
	
	public String getTextInDynamicIdentifition(AppiumDriver<MobileElement> driver, String... dynamicIndex1Index2) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TransferIdentityPageUIs.DYNAMIC_IDENTIFITION, dynamicIndex1Index2);
    }

	public String getDynamicPopupPassword(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TransferIdentityPageUIs.DYNAMIC_PASSWORD, dynamicTextValue);
	}
	return text;
    }

	public void clickToDynamicHomeIcon(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, TransferIdentityPageUIs.DYNAMIC_HOME_ICON, dynamicTextValue);
		status = waitForElementVisible(driver, TransferIdentityPageUIs.DYNAMIC_HOME_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TransferIdentityPageUIs.DYNAMIC_HOME_ICON, dynamicTextValue);
    }

	public String getDynamicTextTitle(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TransferIdentityPageUIs.DYNAMIC_TITLE_CONFIRM_TRANSFER, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, TransferIdentityPageUIs.DYNAMIC_TITLE_CONFIRM_TRANSFER, dynamicTextValue);
	}
	return text;
    }

	public String getDynamicTextTitleMoneyUSD(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TransferIdentityPageUIs.DYNAMIC_MONEY_USD, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, TransferIdentityPageUIs.DYNAMIC_MONEY_USD, dynamicTextValue);
	}
	return text;
    }

	public String getDynamicAccountTitle(AppiumDriver<MobileElement> driver, String dynamicIdValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TransferIdentityPageUIs.DYNAMIC_ACCOUNT_CONFIRM, dynamicIdValue);
		if (status == true) {
			text = getTextElement(driver, TransferIdentityPageUIs.DYNAMIC_ACCOUNT_CONFIRM, dynamicIdValue);

	}
	return text;
    }

	public String getTextConfirmOtp(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TransferIdentityPageUIs.DYNAMIC_OTP_CONFIRM, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, TransferIdentityPageUIs.DYNAMIC_OTP_CONFIRM, dynamicTextValue);

	}
	return text;
    }

}
