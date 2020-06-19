package pageObjects;

import java.util.List;

import org.openqa.selenium.By;

import commons.AbstractPage;
import commons.Constants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.OrderQRCode_Type1_Info;
import model.OrderQRCode_Type2_Info;
import model.SourceAccountModel;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.QRCodePageUIs;

public class QRCodePageObject extends AbstractPage {
	public QRCodePageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public void waitForTextViewDisplay(String textView) {
		waitForElementVisible(driver, QRCodePageUIs.DYNAMIC_IMAGE_BY_TEXT, textView);

	}
	
	public String clickToImageByIndex(int index) {
		boolean status = false;
		String text = "";
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LINEAR_LAYOUT_BY_RECYCLER);
		String locator = String.format(DynamicPageUIs.DYNAMIC_LINEAR_LAYOUT_BY_RECYCLER);
		List<MobileElement> elements = driver.findElements(By.xpath(locator));
		status = elements.size() > 0;
		if (status == true) {
			MobileElement element = elements.get(index);
			element.click();
		}

		return text;
	}
	
	public void clickToDynamicSpinner(String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, QRCodePageUIs.DYNAMIC_SPINNER, dynamicTextValue);
		status = waitForElementVisible(driver, QRCodePageUIs.DYNAMIC_SPINNER, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, QRCodePageUIs.DYNAMIC_SPINNER, dynamicTextValue);
		}

	}
	
	public void clickToDynamicCheckedTextView(String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, QRCodePageUIs.DYNAMIC_CHECKED_TEXT_VIEW, dynamicTextValue);
		status = waitForElementVisible(driver, QRCodePageUIs.DYNAMIC_CHECKED_TEXT_VIEW, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, QRCodePageUIs.DYNAMIC_CHECKED_TEXT_VIEW, dynamicTextValue);
		}

	}

	public void clickToDynamicViewByText(String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, QRCodePageUIs.DYNAMIC_VIEW_BY_TEXT, dynamicTextValue);
		status = waitForElementVisible(driver, QRCodePageUIs.DYNAMIC_VIEW_BY_TEXT, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, QRCodePageUIs.DYNAMIC_VIEW_BY_TEXT, dynamicTextValue);
		}

	}
	
	public int getNumberOfImageInLibrary() {
		int count = 0;
		waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_LINEAR_LAYOUT_BY_RECYCLER);
		String locator = String.format(DynamicPageUIs.DYNAMIC_LINEAR_LAYOUT_BY_RECYCLER);
		List<MobileElement> elements = driver.findElements(By.xpath(locator));
		count = elements.size();

		return count;
	}

	public void clickToCombobox(String dymanicText) {
		boolean status = false;
		String locator = String.format(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dymanicText);
		status = waitForElementVisible(driver, locator);
		if (status == true) {
			overRideTimeOut(driver, 2);
			String locator_1 = String.format(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Đóng");
			clickToElement(driver, locator);
			List<MobileElement> element = driver.findElements(By.xpath(locator_1));
			if (element.size() == 0) {
				clickToElement(driver, locator);
			}
			overRideTimeOut(driver, Constants.LONG_TIME);
		}

	}
	
	public void inputContactInfomation() {
		overRideTimeOut(driver, 10);
		String locator = String.format(DynamicPageUIs.DYNAMIC_BUTTON, "Thêm mới");
		List<MobileElement> elementsOne = driver.findElements(By.xpath(locator));

		if (elementsOne.size() > 0) {
			clickToDynamicButton(driver, "Tiếp tục");
		}

		else {
			inputToDynamicInputBox(driver, "Duc Do", "Họ tên người nhận");
			inputToDynamicInputBox(driver, "0363056625", "Số điện thoại");
			inputToDynamicInputBox(driver, "Test", "Địa chỉ");
			clickToCombobox("Tỉnh/ Thành phố");
			clickToDynamicButtonLinkOrLinkText(driver, "Hà Nội");
			clickToCombobox("Quận/ Huyện");
			clickToDynamicButtonLinkOrLinkText(driver, "Quận Đống Đa");
			inputToDynamicInputBox(driver, "minhducdo2603@gmail.com", "Email");
			clickToDynamicButton(driver, "Xác nhận");
		}

		overRideTimeOut(driver, Constants.LONG_TIME);
	}
	
	public void inputToDynamicInputBoxByHeader( String inputValue, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, QRCodePageUIs.DYNAMIC_INPUT_BOX_BY_HEADER_SAFARI, dynamicTextValue);
		if (status == true) {
			clearText(driver, QRCodePageUIs.DYNAMIC_INPUT_BOX_BY_HEADER_SAFARI, dynamicTextValue);
			sendKeyToElement(driver, QRCodePageUIs.DYNAMIC_INPUT_BOX_BY_HEADER_SAFARI, inputValue, dynamicTextValue);
		}
	}

	public void chooseQRCode(int number) {
		boolean check = false;
		for (int i = 1; i <= number; i++) {
			clickToImageByIndex(number - i);
			overRideTimeOut(driver, 10);
			String locator = String.format(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Thông tin thanh toán");
			List<MobileElement> elementsOne = driver.findElements(By.xpath(locator));

			if (elementsOne.size() > 0) {
				check = true;
				break;
			}

			else {
				clickToDynamicButton(driver, "Đóng");
				continue;
			}

		}
		overRideTimeOut(driver, Constants.LONG_TIME);
		if (!check) {
			throw new RuntimeException("Khong Ma QR code de thanh toan");
		}
	}

	public OrderQRCode_Type1_Info chooseQRCodeType1(int number) {
		OrderQRCode_Type1_Info qrCode = new OrderQRCode_Type1_Info();
		SourceAccountModel sourceAccount = new SourceAccountModel();
		boolean check = false;
		for (int i = 1; i <= number; i++) {
			clickToImageByIndex(number - i);
			qrCode.destinationPlace = getDynamicTextInTransactionDetail(driver, "Thanh toán cho");
			qrCode.namePlace = getDynamicTextInTransactionDetail(driver, "Điểm bán");
			qrCode.codePlace = getDynamicTextInTransactionDetail(driver, "Mã điểm bán");

			clickToDynamicDropDown(driver, "Tài khoản nguồn");
			sourceAccount = chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
			qrCode.account = sourceAccount.account;
			qrCode.surplus = getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");

			inputToDynamicInputBox(driver, "100000", "Số tiền");
			inputToDynamicInputBox(driver, "Test", "Ghi chú");
			clickToDynamicButton(driver, "Thanh toán");

			overRideTimeOut(driver, 10);
			String locator = String.format(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Xác nhận thông tin");
			List<MobileElement> elementsOne = driver.findElements(By.xpath(locator));

			if (elementsOne.size() > 0) {
				check = true;
				break;
			}

			else {
				clickToDynamicButton(driver, "Đóng");
				clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
				clickToDynamicButtonLinkOrLinkText(driver, "Thư viện ảnh");
				clickToDynamicImageButtonByContentDesc(driver, "Hiển thị gốc");
				clickToDynamicTextContains(driver, "ải xuống");
				clickToDynamicTextContains(driver, "Type 1");
				continue;
			}

		}
		overRideTimeOut(driver, Constants.LONG_TIME);
		if (!check) {
			throw new RuntimeException("Khong Ma QR code de thanh toan");
		}
		return qrCode;

	}

	public OrderQRCode_Type2_Info chooseQRCodeType2(int number) {
		OrderQRCode_Type2_Info qrCode = new OrderQRCode_Type2_Info();
		SourceAccountModel sourceAccount = new SourceAccountModel();
		boolean check = false;
		for (int i = 1; i <= number; i++) {
			clickToImageByIndex(number - i);
			qrCode.provider = getDynamicTextInTransactionDetail(driver, "Nhà cung cấp");
			qrCode.service = getDynamicTextInTransactionDetail(driver, "Dịch vụ");
			qrCode.codeCustomer = getDynamicTextInTransactionDetail(driver, "Mã khách hàng");

			clickToDynamicDropDown(driver, "Tài khoản nguồn");
			sourceAccount = chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
			qrCode.account = sourceAccount.account;
			qrCode.surplus = getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");

			clickToDynamicButton(driver, "Thanh toán");

			overRideTimeOut(driver, 10);
			String locator = String.format(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Xác nhận thông tin");
			List<MobileElement> elementsOne = driver.findElements(By.xpath(locator));

			if (elementsOne.size() > 0) {
				check = true;
				break;
			}

			else {
				clickToDynamicButton(driver, "Đóng");
				clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
				clickToDynamicButtonLinkOrLinkText(driver, "Thư viện ảnh");
				clickToDynamicImageButtonByContentDesc(driver, "Hiển thị gốc");
				clickToDynamicTextContains(driver, "tải xuống");
				clickToDynamicButtonLinkOrLinkText(driver, "Type 2");
				continue;
			}

		}
		overRideTimeOut(driver, Constants.LONG_TIME);
		if (!check) {
			throw new RuntimeException("Khong Ma QR code de thanh toan");
		}
		return qrCode;

	}

	public OrderQRCode_Type1_Info chooseQRCodeType3(int number) {
		OrderQRCode_Type1_Info qrCode = new OrderQRCode_Type1_Info();
		SourceAccountModel sourceAccount = new SourceAccountModel();
		boolean check = false;
		for (int i = 1; i <= number; i++) {
			clickToImageByIndex(number - i);
			qrCode.destinationPlace = getDynamicTextInTransactionDetail(driver, "Thanh toán cho");
			qrCode.namePlace = getDynamicTextInTransactionDetail(driver, "Điểm bán");

			clickToDynamicDropDown(driver, "Tài khoản nguồn");
			sourceAccount = chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);
			qrCode.account = sourceAccount.account;
			qrCode.surplus = getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");

			scrollIDown(driver, DynamicPageUIs.DYNAMIC_INPUT_BOX, "Ghi chú");
			inputToDynamicInputBox(driver, "Test", "Ghi chú");
			clickToDynamicButton(driver, "Thanh toán");

			overRideTimeOut(driver, 10);
			String locator = String.format(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Thông tin liên hệ");
			List<MobileElement> elementsOne = driver.findElements(By.xpath(locator));

			if (elementsOne.size() > 0) {
				check = true;
				break;
			}

			else {
				clickToDynamicButton(driver, "Đóng");
				clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
				clickToDynamicButtonLinkOrLinkText(driver, "Thư viện ảnh");
				clickToDynamicImageButtonByContentDesc(driver, "Hiển thị gốc");
				clickToDynamicTextContains(driver, "tải xuống");
				clickToDynamicButtonLinkOrLinkText(driver, "Type 3");
				continue;
			}

		}
		overRideTimeOut(driver, Constants.LONG_TIME);
		if (!check) {
			throw new RuntimeException("Khong Ma QR code de thanh toan");
		}
		return qrCode;

	}

}
