package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;



public class LocationQRCodePageObject extends AbstractPage {


	public LocationQRCodePageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}
	private AppiumDriver<MobileElement> driver;
	
	public List<String> clickChooseLocator(int numberLocator) {
		List<String> listLocator = new ArrayList<>();

		String locator = String.format(DynamicPageUIs.DYNAMIC_VIEWGROUP_TEXT,"Nổi bật" );
		boolean status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_VIEWGROUP_TEXT,"Nổi bật");
		if (status) {
			List<MobileElement> elements = driver.findElements(By.xpath(locator));
			for (MobileElement element : elements) {
				try {
					
						element.click();

					
						numberLocator--;
					

					if (numberLocator <= 0) {
						break;
					}

				} catch (Exception e) {

				}
			
			}
		}
		return listLocator;

		}
	
	public List<String> getListOfSuggestedMoneyOrListText1(AppiumDriver<MobileElement> driver, String dynamictext) {
		boolean status = false;
		List<String> text = null;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_VIEWGROUP_TEXT, dynamictext);
		if (status == true) {
			text = getTextInListElements(driver, DynamicPageUIs.DYNAMIC_VIEWGROUP_TEXT, dynamictext);
		}
		return text;

	}
	
	public int getNumberLike() {
	int numberLikeStart = Integer.parseInt(getToDynamicTextOther(driver, "Đơn vị chấp nhận thanh toán", "4"));

	clickToDynamicTextLike(driver, "Đơn vị chấp nhận thanh toán", "4");
	int numberLikeEnd = Integer.parseInt(getToDynamicTextOther(driver, "Đơn vị chấp nhận thanh toán", "4"));
	if (numberLikeStart < numberLikeEnd)
	{
		numberLikeEnd = numberLikeStart + 1;
	}
	
	else if (numberLikeStart > numberLikeEnd)
	{
		numberLikeEnd = numberLikeStart - 1;
	}
	return numberLikeEnd;
}

	public String getToDynamicTextOther (AppiumDriver<MobileElement> driver, String... dynamicTextAndIndex) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_TEXT_FOLLOWING_VIEWGROUP, dynamicTextAndIndex);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_FOLLOWING_VIEWGROUP, dynamicTextAndIndex);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXT_FOLLOWING_VIEWGROUP, dynamicTextAndIndex);

		}
		return text;

	}
	public void clickToDynamicTextLike(AppiumDriver<MobileElement> driver, String... dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_FOLLOWING_VIEWGROUP, dynamicText);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_TEXT_FOLLOWING_VIEWGROUP, dynamicText);
		}

	}
	public String getDynamicTextPrecedingText (AppiumDriver<MobileElement> driver, String... dynamicTextAndIndex) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_TEXT_PRECEDING_TEXT, dynamicTextAndIndex);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_PRECEDING_TEXT, dynamicTextAndIndex);
		if (status == true) {
			text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXT_PRECEDING_TEXT, dynamicTextAndIndex);

		}
		return text;

	}
	public void clickToTextImageView(AppiumDriver<MobileElement> driver, String dynamicText) {
		boolean status = false;
		scrollUp(driver, DynamicPageUIs.DYNAMIC_IMAGE_TEXTVEW, dynamicText);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_IMAGE_TEXTVEW, dynamicText);
		if (status == true) {
			clickToElement(driver, DynamicPageUIs.DYNAMIC_IMAGE_TEXTVEW, dynamicText);
		}
		
	}
		public void clickToDynamicBack(AppiumDriver<MobileElement> driver, String ... dynamicIndex ) {
			boolean status = false;
			scrollIDown(driver, DynamicPageUIs.DYNAMIC_BACK, dynamicIndex);
			status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BACK, dynamicIndex);
			if (status == true) {
				clickToElement(driver, DynamicPageUIs.DYNAMIC_BACK, dynamicIndex);
			}
		}
		public String getDynamicTextFollowingText (AppiumDriver<MobileElement> driver, String... dynamicTextAndIndex) {
			boolean status = false;
			String text = null;
			scrollIDown(driver, DynamicPageUIs.DYNAMIC_TEXT_FOLLOWING_TEXT, dynamicTextAndIndex);
			status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_TEXT_FOLLOWING_TEXT, dynamicTextAndIndex);
			if (status == true) {
				text = getTextElement(driver, DynamicPageUIs.DYNAMIC_TEXT_FOLLOWING_TEXT, dynamicTextAndIndex);

			}
			return text;

		}
		public static final String DYNAMIC_TEXT_FOLLOWING_TEXT = "//android.widget.TextView[@text = '%s']//following-sibling::android.widget.TextView[@index ='%s']";

		public void clickToDynamicTextContains(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
				boolean status = false;
				scrollIDown(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_CONTAINS, dynamicTextValue);
				status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_CONTAINS, dynamicTextValue);
				if (status == true) {
					clickToElement(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT_CONTAINS, dynamicTextValue);

				}
			}
		public String getDynamicTextViewBack(AppiumDriver<MobileElement> driver, String... dynamicTextAndIndex) {
			boolean status = false;
			String text = null;
			scrollIDown(driver, DynamicPageUIs.DYNAMIC_BACK_TEXT, dynamicTextAndIndex);
			status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BACK_TEXT, dynamicTextAndIndex);
			if (status == true) {
				text = getTextElement(driver, DynamicPageUIs.DYNAMIC_BACK_TEXT, dynamicTextAndIndex);

			}
			return text;

		}
		public String getDynamicTextBack(AppiumDriver<MobileElement> driver, String... dynamicTextAndIndex) {
			boolean status = false;
			String text = null;
			scrollIDown(driver, DynamicPageUIs.DYNAMIC_BACK, dynamicTextAndIndex);
			status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BACK, dynamicTextAndIndex);
			if (status == true) {
				text = getTextElement(driver, DynamicPageUIs.DYNAMIC_BACK, dynamicTextAndIndex);

			}
			return text;

		}
		public void clickToTextEditText(AppiumDriver<MobileElement> driver, String dynamicText) {
			boolean status = false;
			scrollUp(driver, DynamicPageUIs.DYNAMIC_IMAGE_EDITTEXT, dynamicText);
			status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_IMAGE_EDITTEXT, dynamicText);
			if (status == true) {
				clickToElement(driver, DynamicPageUIs.DYNAMIC_IMAGE_EDITTEXT, dynamicText);
			}

		}
	}
	
	
