package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.TransferIdentityPageUIs;

public class TransferIdentiryPageObject extends AbstractPage {

	public TransferIdentiryPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public String getDynamicAmountLabelConvertVNDToLong(String dynamicTextValue) {
		String moneyString = getDynamicAmountLabel(driver, dynamicTextValue);
		moneyString = moneyString.replaceAll("Phí: ", "");
		moneyString = moneyString.replaceAll(",", "");
		moneyString = moneyString.replaceAll("VND/giao dịch", "");
		return moneyString;

	}

	public void inputToDynamicInputBoxUsedValidate(String inputValue, String dynamicTextValue) {
		scrollToText(driver, dynamicTextValue);
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, inputValue, dynamicTextValue);

	}

	public String getTextInDynamicIdentifition(String... dynamicIndex1Index2) {
		waitForElementVisible(driver, TransferIdentityPageUIs.DYNAMIC_IDENTIFITION, dynamicIndex1Index2);
		return getTextElement(driver, TransferIdentityPageUIs.DYNAMIC_IDENTIFITION, dynamicIndex1Index2);

	}

	public String getTextDynamicPopupTransfers(String dynamicTextValue) {
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		return getTextElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

	}

}
