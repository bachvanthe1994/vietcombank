package pageObjects;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.TransferIdentityPageUIs;
import vietcombankUI.sdk.trainTicket.TrainTicketPageUIs;

public class TransferIdentiryPageObject extends AbstractPage {
    public TransferIdentiryPageObject(AppiumDriver<MobileElement> mappingDriver) {
	driver = mappingDriver;
    }

    private AppiumDriver<MobileElement> driver;

    public String getDynamicAmountLabelConvertVNDToLong(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
	String moneyString = getDynamicTextByLabel(driver, dynamicTextValue);
	moneyString = moneyString.replaceAll("Phí: ", "");
	moneyString = moneyString.replaceAll(",", "");
	moneyString = moneyString.replaceAll("VND/giao dịch", "");
	return moneyString;
    }

    public String getTextInDynamicIdentifition(AppiumDriver<MobileElement> driver, String... dynamicIndex1Index2) {
	boolean status = false;
	String text = null;
	status = waitForElementVisible(driver, TransferIdentityPageUIs.DYNAMIC_IDENTIFITION, dynamicIndex1Index2);
	if (status == true) {
	    text = getTextElement(driver, TransferIdentityPageUIs.DYNAMIC_IDENTIFITION, dynamicIndex1Index2);

	}
	return text;
    }

    public String getDynamicPopupPassword(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
	boolean status = false;
	String text = null;
	status = waitForElementVisible(driver, TransferIdentityPageUIs.DYNAMIC_PASSWORD, dynamicTextValue);
	if (status == true) {
	    text = getTextElement(driver, TransferIdentityPageUIs.DYNAMIC_PASSWORD, dynamicTextValue);

	}
	return text;
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

    public String getTextTitleTransferSuccess(String... dynamicIdId) {
	boolean status = false;
	String text = null;
	status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TITLE_TRANSFERSUCCESS, dynamicIdId);
	if (status == true) {
	    text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TITLE_TRANSFERSUCCESS, dynamicIdId);

	}
	return text;
    }

    // Dien text vao input box
    // input vào ô input với tham số truyền vào là inputbox
    public void inputToDynamicInputBox(String inputValue, String dynamicTextValue) {
	boolean status = false;
	scrollUp(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
	status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
	if (status == true) {
	    clearText(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
	    sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, inputValue, dynamicTextValue);
	}

    }

    public void clickToTextID(String dynamicID) {
	boolean status = false;
	scrollIDown(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
	status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
	if (status == true) {
	    clickToElement(driver, DynamicPageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
	}

    }

//    kiểm tra gợi ý số tiền gửi đơn vị ngoại tệ
    public boolean isDynamicSuggestMoneyIdDisplayed(String dynamicID) {
	boolean isDisplayed = false;
	scrollIDown(driver, DynamicPageUIs.DYNAMIC_ESPECIALLY, dynamicID);
	boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_ESPECIALLY, dynamicID);
	if (status == true) {
	    isDisplayed = isControlDisplayed(driver, DynamicPageUIs.DYNAMIC_ESPECIALLY, dynamicID);
	}
	return isDisplayed;
    }

//    kiểm tra không hiển thị list money suggest
    public boolean isDynamicSuggestedMoneyUndisplayed(String dynamicID) {
	boolean isDisplayed = false;
	boolean status = waitForElementInvisible(driver, DynamicPageUIs.DYNAMIC_ESPECIALLY, dynamicID);
	if (status == true) {
	    isDisplayed = isControlUnDisplayed(driver, DynamicPageUIs.DYNAMIC_ESPECIALLY, dynamicID);
	}
	return isDisplayed;
    }

    // Click vào button, text có class là textview, tham số truyền vào là text
    public void clickToDynamicButtonLinkOrLinkText(String dynamicTextValue) {
	boolean status = false;
	scrollUp(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
	status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
	if (status == true) {
	    clickToElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

	}
    }

    public void clickToDynamicHomeIcon(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
	boolean status = false;
	scrollIDown(driver, TransferIdentityPageUIs.DYNAMIC_HOME_ICON, dynamicTextValue);
	status = waitForElementVisible(driver, TransferIdentityPageUIs.DYNAMIC_HOME_ICON, dynamicTextValue);
	if (status) {
	    clickToElement(driver, TransferIdentityPageUIs.DYNAMIC_HOME_ICON, dynamicTextValue);
	}
    }

}
