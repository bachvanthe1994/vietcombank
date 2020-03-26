package pageObjects.shopping_online;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.shopping_online_UI.ShoppingOnlinePageUIs;

public class ShoppingOnlinePageObject extends AbstractPage {

	public ShoppingOnlinePageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public List<String> clickChooseOrrder(int numberOrder, String textOrder) {
			List<String> listOrder = new ArrayList<>();

			String locator = String.format(ShoppingOnlinePageUIs.DYNAMIC_VIEW_LIST,textOrder );
			boolean status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_VIEW_LIST,textOrder);
			if (status) {
				List<MobileElement> elements = driver.findElements(By.xpath(locator));
				for (MobileElement element : elements) {
					//File imageFile = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);
					try {
						
							element.click();

						
							numberOrder--;
						

						if (numberOrder <= 0) {
							break;
						}

					} catch (Exception e) {

					}
				
				}
			}
			return listOrder;

			}
	
	//Click vao 1 button sử dụng  tham số là text
	public void clickToDynamicButton(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
		sleep(driver, 3000);
		if (driver.getPageSource().contains("com.VCB:id/progressLoadingVntalk")) {
			waitForElementInvisible(driver,
					"//android.widget.ImageView[@resource-id='com.VCB:id/progressLoadingVntalk']");
		}
		if (driver.getPageSource().contains("Xin lỗi") | driver.getPageSource().contains("NOT FOUND")
				| driver.getPageSource().contains("Lỗi trong kết nối tới server")
				| driver.getPageSource().contains("Không tìm thấy")) {
			clickToElement(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON, "Đóng");
			clickToElement(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
	}
	
	// Kiểm tra text trong nội dung link thông báo
		public boolean isDynamicTextInfoDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
			boolean isDisplayed = false;
			boolean status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT,
					dynamicTextValue);
			if (status == true) {
				isDisplayed = isControlDisplayed(driver, ShoppingOnlinePageUIs.DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT,
						dynamicTextValue);
			}
			return isDisplayed;
		}
		
	// Click vào ngày trong giỏ hàng
	public void clickToDynamicCart(AppiumDriver<MobileElement> driver, String... dynamicIndex) {
		boolean status = false;
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_CART, dynamicIndex);
		if (status == true) {
			clickToElement(driver, ShoppingOnlinePageUIs.DYNAMIC_CART, dynamicIndex);
		}
	
	}
	
	// Click vào ngày trong date time picker , tham số truyền vào là text
	public void clickToDynamicDateInDateTimePicker(AppiumDriver<MobileElement> driver, String dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT, dynamicText);
		if (status == true) {
			clickToElement(driver, ShoppingOnlinePageUIs.DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT, dynamicText);
		}

	}
	
//	lấy ra tổng số tiền cần thanh toán ở màn hình xác nhận thanh toán
	public String getDynamicTextView(AppiumDriver<MobileElement> driver, String dynamicText) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_TOTAL_MONEY, dynamicText);
		if (status == true) {
			text = getTextElement(driver, ShoppingOnlinePageUIs.DYNAMIC_TOTAL_MONEY, dynamicText);
		}
		return text;
	}
	
//	lấy ra giá trị text, giá trị truyền vào là text
	public String getDynamicTextInPopUp(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		}
		return text;
	}
	
//	get text giá trị truyền vào là id
	public String getDynamicTextDetailByIDOrPopup(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		if (status == true) {
			text = getTextElement(driver, ShoppingOnlinePageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);

		}
		return text;
	}
	
//Get thông tin được tạo trong chi tiết giao dich , tham số truyền vào là text phía bên tay trái
	public String getDynamicTextInTransactionDetail(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, ShoppingOnlinePageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, ShoppingOnlinePageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);

		}
		return text;

	}
	
	// Click vào ô dropdown, và ô date time , tham số truyền vào là resource id
	public void clickToDynamicDropdownAndDateTimePicker(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		if (status == true) {
			clickToElement(driver, ShoppingOnlinePageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		}

	}
	
	//Click vào button, text có class là textview, tham số truyền vào là text
	public void clickToDynamicButtonLinkOrLinkText(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

		}
	}
	
	public String getMoneyByAccount(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, ShoppingOnlinePageUIs.DYNAMIC_LABEL_MONEY_BY_ACCOUNT, dynamicTextValue);
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_LABEL_MONEY_BY_ACCOUNT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, ShoppingOnlinePageUIs.DYNAMIC_LABEL_MONEY_BY_ACCOUNT, dynamicTextValue);

		}
		return text;
	}
}
