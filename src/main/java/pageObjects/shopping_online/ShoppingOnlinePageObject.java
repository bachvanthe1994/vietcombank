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

		String locator = String.format(ShoppingOnlinePageUIs.DYNAMIC_VIEW_LIST, textOrder);
		boolean status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_VIEW_LIST, textOrder);
		if (status) {
			List<MobileElement> elements = driver.findElements(By.xpath(locator));
			for (MobileElement element : elements) {
				// File imageFile = ((TakesScreenshot)
				// element).getScreenshotAs(OutputType.FILE);
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

	// Input vào ô nhập otp , tham số truyền vào là text của button tiếp tục
	public void inputToDynamicOtp(String inputValue, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, dynamicTextValue);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_OTP_INPUT, inputValue, dynamicTextValue);
		}
	}

	public void inputToDynamicInfo(String inputValue, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_EDIT_TEXT_VIEW_BY_TEXT, dynamicTextValue);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_EDIT_TEXT_VIEW_BY_TEXT, dynamicTextValue);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_EDIT_TEXT_VIEW_BY_TEXT, inputValue, dynamicTextValue);
		}
	}

	// input vào ô input với tham số truyền vào là inputbox
	public void inputToDynamicInputBox(String inputValue, String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		status = waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
		if (status == true) {
			clearText(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, dynamicTextValue);
			sendKeyToElement(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, inputValue, dynamicTextValue);
		}
	}

	// Input vào thông tin người mua hàng
	public void inputToDynamicUser(String inputValue, String... dynamicTextValueIndex) {
		boolean status = false;
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_CUSTOMER, dynamicTextValueIndex);
		if (status == true) {
			clearText(driver, ShoppingOnlinePageUIs.DYNAMIC_CUSTOMER, dynamicTextValueIndex);
			sendKeyToElement(driver, ShoppingOnlinePageUIs.DYNAMIC_CUSTOMER, inputValue, dynamicTextValueIndex);
		}
	}

	// Click vao 1 button sử dụng tham số là text
	public void clickToDynamicButton(String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
		sleep(driver, 3000);
		if (driver.getPageSource().contains("com.VCB:id/progressLoadingVntalk")) {
			waitForElementInvisible(driver, "//android.widget.ImageView[@resource-id='com.VCB:id/progressLoadingVntalk']");
		}
		if (driver.getPageSource().contains("Xin lỗi") | driver.getPageSource().contains("NOT FOUND") | driver.getPageSource().contains("Lỗi trong kết nối tới server") | driver.getPageSource().contains("Không tìm thấy")) {
			clickToElement(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON, "Đóng");
			clickToElement(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
	}
	
	

	// Click vao 1 button sử dụng tham số là text
	public void clickToDynamicCategories(String dynamicText) {
		boolean status = false;
		scrollIDown(driver, ShoppingOnlinePageUIs.PRODUCT_BY_CONTAIN_TEXT, dynamicText);

		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.PRODUCT_BY_CONTAIN_TEXT, dynamicText);
		if (status == true) {
			clickToElement(driver, ShoppingOnlinePageUIs.PRODUCT_BY_CONTAIN_TEXT, dynamicText);
		}
		if (driver.getPageSource().contains("com.VCB:id/progressLoadingVntalk")) {
			waitForElementInvisible(driver, "//android.widget.ImageView[@resource-id='com.VCB:id/progressLoadingVntalk']");
		}
		if (driver.getPageSource().contains("Xin lỗi") | driver.getPageSource().contains("NOT FOUND") | driver.getPageSource().contains("Lỗi trong kết nối tới server") | driver.getPageSource().contains("Không tìm thấy")) {
			clickToElement(driver, ShoppingOnlinePageUIs.PRODUCT_BY_CONTAIN_TEXT, "Đóng");
			clickToElement(driver, ShoppingOnlinePageUIs.PRODUCT_BY_CONTAIN_TEXT, dynamicText);
		}
	}
	
	// Click vao 1 button sử dụng tham số là text

	public void clickToDynamicTextContains(String dynamicText) {
		boolean status = false;
		scrollIDown(driver, ShoppingOnlinePageUIs.PRODUCT_VIEW_BY_CONTAIN_TEXT, dynamicText);

		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.PRODUCT_VIEW_BY_CONTAIN_TEXT, dynamicText);
		if (status == true) {
			clickToElement(driver, ShoppingOnlinePageUIs.PRODUCT_VIEW_BY_CONTAIN_TEXT, dynamicText);
		}
		if (driver.getPageSource().contains("com.VCB:id/progressLoadingVntalk")) {
			waitForElementInvisible(driver, "//android.widget.ImageView[@resource-id='com.VCB:id/progressLoadingVntalk']");
		}
		if (driver.getPageSource().contains("Xin lỗi") | driver.getPageSource().contains("NOT FOUND") | driver.getPageSource().contains("Lỗi trong kết nối tới server") | driver.getPageSource().contains("Không tìm thấy")) {
			clickToElement(driver, ShoppingOnlinePageUIs.PRODUCT_VIEW_BY_CONTAIN_TEXT, "Đóng");
			clickToElement(driver, ShoppingOnlinePageUIs.PRODUCT_VIEW_BY_CONTAIN_TEXT, dynamicText);
		}
	}
	
	public List<String> getTextInListElementsProduct( String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		List<MobileElement> listElements = driver.findElements(By.xpath(locator));
		List<String> listProduct = new ArrayList<String>();
		for (MobileElement element : listElements) {
			listProduct.add(element.getText());
		}
		return listProduct;
	}

	// Kiểm tra text trong nội dung link thông báo
	public boolean isDynamicTextInfoDisplayed(String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, ShoppingOnlinePageUIs.DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Kiểm tra text không hiển thị trên màn hình, tham số truyền vào là text
	public boolean isDynamicMessageAndLabelTextUndisplayed(String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementInvisible(driver, ShoppingOnlinePageUIs.DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlUnDisplayed(driver, ShoppingOnlinePageUIs.DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Click vào ngày trong back
	public void clickToDynamicCart(String... dynamicIndex) {
		boolean status = false;
		scrollIDown(driver, ShoppingOnlinePageUIs.DYNAMIC_CART, dynamicIndex);
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_CART, dynamicIndex);
		if (status == true) {
			clickToElement(driver, ShoppingOnlinePageUIs.DYNAMIC_CART, dynamicIndex);
		}

	}

	// Click vào ngày trong date time picker , tham số truyền vào là text
	public void clickToDynamicDateInDateTimePicker(String dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT, dynamicText);
		if (status == true) {
			clickToElement(driver, ShoppingOnlinePageUIs.DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT, dynamicText);
		}

	}

	// Click vào thông tin người dùng , tham số truyền vào là text
	public void clickToDynamicCustomer(String dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_INFOMATION_CUSTOMER, dynamicText);
		if (status == true) {
			clickToElement(driver, ShoppingOnlinePageUIs.DYNAMIC_INFOMATION_CUSTOMER, dynamicText);
		}

	}

	// Click vào thông tin người dùng , tham số truyền vào là text
	public void clickToDynamicListProvince(String dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_CHECKED, dynamicText);
		if (status == true) {
			clickToElement(driver, ShoppingOnlinePageUIs.DYNAMIC_CHECKED, dynamicText);
		}

	}

//	lấy ra tổng số tiền cần thanh toán ở màn hình xác nhận thanh toán
	public String getDynamicTextView(String... dynamicTextIndex) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_TOTAL_MONEY, dynamicTextIndex);
		if (status == true) {
			text = getTextElement(driver, ShoppingOnlinePageUIs.DYNAMIC_TOTAL_MONEY, dynamicTextIndex);
		}
		return text;
	}

//	lấy ra giá trị text, giá trị truyền vào là text
	public String getDynamicTextInPopUp(String... dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		}
		return text;
	}

//	get text giá trị truyền vào là id
	public String getDynamicTextDetailByIDOrPopup(String dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		if (status == true) {
			text = getTextElement(driver, ShoppingOnlinePageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);

		}
		return text;
	}
	
	public void scrollDownToConatainText( String dynamicText) {
		scrollIDown(driver, ShoppingOnlinePageUIs.PRODUCT_VIEW_BY_CONTAIN_TEXT, dynamicText);

	}
	
	
//	get text giá trị truyền vào là text
	public String getDynamicTextPricesByText(String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_TOTAL_MONEY_BY_TEXT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, ShoppingOnlinePageUIs.DYNAMIC_TOTAL_MONEY_BY_TEXT, dynamicTextValue);

		}
		return text;
	}
//	get text giá trị truyền vào là text
	public String getDynamicTextFeeShipping(String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_NUMBER_CUSTOMER_VIEW, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, ShoppingOnlinePageUIs.DYNAMIC_NUMBER_CUSTOMER_VIEW, dynamicTextValue);
			
		}
		return text;
	}
	public String getDynamicTextTableByTextView(String dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_NUMBER_CUSTOMER, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, ShoppingOnlinePageUIs.DYNAMIC_NUMBER_CUSTOMER, dynamicTextValue);
			
		}
		return text;
	}



//Get thông tin được tạo trong chi tiết giao dich , tham số truyền vào là text phía bên tay trái
	public String getDynamicTextInTransactionDetail(String dynamicTextValue) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, ShoppingOnlinePageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, ShoppingOnlinePageUIs.DYNAMIC_CONFIRM_INFO, dynamicTextValue);

		}
		return text;

	}

//	lấy ra tổng số tiền cần thanh toán ở màn hình thêm sản phẩm vào giỏ hàng
	public String getDynamicTextViewTotalMoney(String index) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_TOTAL_MONEY_1, index);
		if (status == true) {
			text = getTextElement(driver, ShoppingOnlinePageUIs.DYNAMIC_TOTAL_MONEY_1, index);
		}
		return text;
	}

	// Click vào ô dropdown, và ô date time , tham số truyền vào là resource id
	public void clickToDynamicDropdownAndDateTimePicker(String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		if (status == true) {
			clickToElement(driver, ShoppingOnlinePageUIs.DYNAMIC_TEXT_BY_ID, dynamicID);
		}

	}

	// Click vào button, text có class là textview, tham số truyền vào là text
	public void clickToDynamicButtonLinkOrLinkText(String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);

		}
	}

	// Click vào button, text có class là textview, tham số truyền vào là text
	public void clickToDynamicDisCount(String... dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, ShoppingOnlinePageUIs.DYNAMIC_DISCOUNT, dynamicTextValue);
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_DISCOUNT, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, ShoppingOnlinePageUIs.DYNAMIC_DISCOUNT, dynamicTextValue);

		}
	}

	public String getMoneyByAccount(String dynamicTextValue) {
		boolean status = false;
		String text = null;
		scrollIDown(driver, ShoppingOnlinePageUIs.DYNAMIC_LABEL_MONEY_BY_ACCOUNT, dynamicTextValue);
		status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_LABEL_MONEY_BY_ACCOUNT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, ShoppingOnlinePageUIs.DYNAMIC_LABEL_MONEY_BY_ACCOUNT, dynamicTextValue);

		}
		return text;
	}
	
	//Kiểm tra text có hiển thị hay không, tham số truyền vào là text 
		public boolean isDynamicMessageAndLabelTextDisplayed(String dynamicTextValue) {
			boolean isDisplayed = false;
			scrollIDown(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
			boolean status = waitForElementVisible(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
			if (status == true) {
				isDisplayed = isControlDisplayed(driver, ShoppingOnlinePageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
			}
			return isDisplayed;

		}
		

		// Kiem tra text co trong PageSource hay khong
		public boolean isTextDisplayedInPageSource( String dynamicText) {
			if (driver.getPageSource().contains("com.VCB:id/progressLoadingVntalk")) {
				waitForElementInvisible(driver, "//android.widget.ImageView[@resource-id='com.VCB:id/progressLoadingVntalk']");
			}
			return driver.getPageSource().contains(dynamicText);
		}
		/* SCROLL DOWN */
		public void scrollDownToViewText( String dynamicText) {
			scrollIDown(driver, ShoppingOnlinePageUIs.PRODUCT_VIEW_BY_CONTAIN_TEXT, dynamicText);

		}
		public void scrollDownToTextView( String dynamicText) {
			scrollIDown(driver, ShoppingOnlinePageUIs.PRODUCT_BY_CONTAIN_TEXT, dynamicText);
			
		}

	
}
