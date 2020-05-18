package pageObjects.sdk.trainTicket;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import commons.AbstractPage;
import commons.Constants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import vietcombankUI.DynamicPageUIs;
import vietcombankUI.sdk.trainTicket.TrainTicketPageUIs;

public class TrainTicketPageObject extends AbstractPage {

	public TrainTicketPageObject(AppiumDriver<MobileElement> mappingDriver) {
		driver = mappingDriver;
	}

	private AppiumDriver<MobileElement> driver;

	public List<String> getListStatusTransfer(AppiumDriver<MobileElement> driver, String dynamicIndex) {
		waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_STARUS, dynamicIndex);
		return getTextInListElements(driver, TrainTicketPageUIs.DYNAMIC_STARUS, dynamicIndex);
	}

	public void navigateBack(AppiumDriver<MobileElement> driver) {
		driver.navigate().back();
	}

	// Kiểm tra text nhập vào danh sách tiếng việt, không phân biệt hoa thường
	public boolean checkSuggestPoint(List<String> listSuggestPoint, String checkedValue) {
		for (String point : listSuggestPoint) {
			if (!convertVietNameseStringToString(point).toLowerCase().contains(checkedValue.toLowerCase())) {
				return false;
			}
		}
		return true;
	}

	// Sắp xếp thời gian giảm dần định dạng 6'30
	public boolean orderSortIncrase(List<String> listOrderSort) {
		boolean result = true;
		int count = listOrderSort.size();
		for (int i = 0; i <= count; i++) {
			if (canculateConvertTimehh(listOrderSort.get(i)) < canculateConvertTimehh(listOrderSort.get(i + 1))) {
				return result;
			}
		}
		return false;
	}

	// Sắp xếp thời gian giảm dần định dạng 6'30
	public boolean orderSortDecrase(List<String> listOrderSort) {
		boolean result = true;
		int count = listOrderSort.size();
		for (int i = 0; i <= count; i--) {
			if (canculateConvertTimehh(listOrderSort.get(i + 1)) < canculateConvertTimehh(listOrderSort.get(i))) {
				return result;
			}
		}
		return false;
	}

	// Sắp xếp thời gian tăng dần định dạng 06:30
	public boolean orderSortIncraseFormat(List<String> listOrderSort) {
		boolean result = true;
		int count = listOrderSort.size();
		for (int i = 0; i <= count; i++) {
			if (canculateConvertTime(listOrderSort.get(i)) < canculateConvertTime(listOrderSort.get(i + 1))) {
				return result;
			}
		}
		return false;
	}

	// Sắp xếp thời gian giảm dần định dạng 06:30
	public boolean orderSortDecraseFormat(List<String> listOrderSort) {
		boolean result = true;
		int count = listOrderSort.size();
		for (int i = 0; i <= count; i--) {
			if (canculateConvertTime(listOrderSort.get(i + 1)) < canculateConvertTime(listOrderSort.get(i))) {

				return result;
			}
		}
		return false;
	}

	// Chuyển định dạng giờ phút về dạng số theo đơn vị phút VD: 6'h30
	public int canculateConvertTimehh(String duration) {
		int result = 0;
		String hour = "";
		String minute = "";
		hour = duration.split("h")[0];
		minute = duration.split("h")[1].replace("'", "");
		result = Integer.parseInt(hour) * 60 + Integer.parseInt(minute);
		return result;
	}

	// Chuyển định dạng giờ phút về dạng số theo đơn vị phút VD: 06:30
	public int canculateConvertTime(String duration) {
		int result = 0;
		String hour = "";
		String minute = "";
		hour = duration.split(":")[0];
		minute = duration.split(":")[1];
		result = Integer.parseInt(hour) * 60 + Integer.parseInt(minute);
		return result;
	}

	public String getDuration(String dateStart, String dateStop) {
		String result = "";
		SimpleDateFormat format = new SimpleDateFormat("HH:mm - dd/MM/yyyy");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			long diff = d2.getTime() - d1.getTime();
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;

			result = diffHours + "h" + diffMinutes + "'";
		} catch (Exception e) {

		}
		return result;

	}

	public String getColorOfElement(String locator, String... dynamicValue) {
		String colorOfElement = "";
		locator = String.format(locator, (Object[]) dynamicValue);
		boolean status = waitForElementVisible(driver, locator);
		if (status) {
			MobileElement element = driver.findElement(By.xpath(locator));

			File imageFile = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);
			try {
				BufferedImage bufferedImage = ImageIO.read(imageFile);
				imageFile.delete();

				int height = bufferedImage.getHeight();
				int width = bufferedImage.getWidth();
				int x = width / 2;
				int y = height / 4;
				int RGBA = bufferedImage.getRGB(x, y);
				int red = (RGBA >> 16) & 255;
				int green = (RGBA >> 8) & 255;
				int blue = RGBA & 255;
				colorOfElement = "(" + red + "," + green + "," + blue + ")";

			} catch (Exception e) {

			}

		}
		return colorOfElement;

	}

	public List<String> chooseSeats(int numberOfSeats, String colorOfSeat) {
		List<String> listSeat = new ArrayList<>();

		String locator = String.format(TrainTicketPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID_NAF_TRUE, "com.VCB:id/layoutRoot");
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID_NAF_TRUE, "com.VCB:id/layoutRoot");
		if (status) {
			List<MobileElement> elements = driver.findElements(By.xpath(locator));
			for (MobileElement element : elements) {
				File imageFile = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);
				try {
					BufferedImage bufferedImage = ImageIO.read(imageFile);
					imageFile.delete();

					int height = bufferedImage.getHeight();
					int width = bufferedImage.getWidth();
					int x = width / 2;
					int y = height / 4;
					int RGBA = bufferedImage.getRGB(x, y);
					int red = (RGBA >> 16) & 255;
					int green = (RGBA >> 8) & 255;
					int blue = RGBA & 255;
					String colorOfElement = "(" + red + "," + green + "," + blue + ")";

					if (colorOfSeat.equals(colorOfElement)) {
						element.click();

						clickToDynamicSelectDate("com.VCB:id/tvChoose");
						String seat = getTextInDynamicPopup("com.VCB:id/tvGheToa");
						listSeat.add(seat);
						clickToElement(TrainTicketPageUIs.DYNAMIC_DATE, "com.VCB:id/btnDone");
						numberOfSeats--;
					}

					if (numberOfSeats <= 0) {
						break;
					}

				} catch (Exception e) {

				}
			}

		}
		return listSeat;

	}

	// Chuyển tiếng việt có dấu
	public String convertVietNameseStringToString(String vietnameseString) {
		String temp = Normalizer.normalize(vietnameseString, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}

	// Kiểm tra ngày hiện tại được check
	public boolean getSelectedAttributeOfDate(String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		MobileElement element = driver.findElement(By.xpath(locator));
		return Boolean.parseBoolean(element.getAttribute("selected"));
	}

	// Lấy tháng năm hiện tại định dạng THÁNG 2 2020
	public String getCurentMonthAndYear() {
		LocalDate now = LocalDate.now();
		int month = now.getMonthValue();
		int year = now.getYear();
		return "THÁNG" + " " + month + " " + year;
	}

	// Trả về tháng năm hiện tại định dạng T.2 2020
	public String getMonthAndYearFORMAT() {
		LocalDate now = LocalDate.now();
		int month = now.getMonthValue();
		int year = now.getYear();
		return "T." + month + " " + year;
	}

	// Lấy ngày đi là ngày đã chọn
	public String getDayPickUp() {
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECT, "com.VCB:id/lnNgayDi");
		List<String> listText = new ArrayList<String>();
		String day = "";
		if (status) {
			listText = getTextInListElements(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECT, "com.VCB:id/lnNgayDi");
			for (String text : listText) {
				day = day + text;
			}
			day = day.replace("Ngày đi", "");
		}
		return day;

	}

	// Lấy ngày về là ngày đã chọn
	public String getDayArrival() {
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECT, "com.VCB:id/lnNgayVe");
		List<String> listText = new ArrayList<String>();
		String day = "";
		if (status) {
			listText = getTextInListElements(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECT, "com.VCB:id/lnNgayVe");
			for (String text : listText) {
				day = day + text;
			}
			day = day.replace("Ngày về", "");
		}
		return day;
	}

	// Lấy tháng và năm cho ngày tương lai, định dạng THÁNG 2 2020
	public String getMonthAndYearPlusDay(long days) {
		LocalDate now = LocalDate.now();
		LocalDate date = now.plusDays(days);
		int month = date.getMonthValue();
		int year = date.getYear();
		return "THÁNG" + " " + month + " " + year;
	}

	// Lấy tháng và năm cho ngày quá khứ, định dạng THÁNG 2 2020
	public String getMonthAndYearMinusDay(long days) {
		LocalDate now = LocalDate.now();
		LocalDate date = now.minusDays(days);
		int month = date.getMonthValue();
		int year = date.getYear();
		return "THÁNG" + " " + month + " " + year;
	}

	// Lấy tháng và năm cho ngày tương lai, định dạng T.2 2020
	public String getMonthAndYearPlusFORMAT(long days) {
		LocalDate now = LocalDate.now();
		LocalDate date = now.plusDays(days);
		int month = date.getMonthValue();
		int year = date.getYear();
		return "T." + month + " " + year;
	}

	// Click vào button, text có class là textview, tham số truyền vào là text
	public void clickToDynamicButtonLinkOrLinkText(String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, TrainTicketPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		}
	}

	public void clickToElement(String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
	}

	// Click vao 1 button sử dụng tham số là text
	public void clickToDynamicButton(String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, TrainTicketPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
	}

	// Click vao 1 button sử dụng contain tìm kiếm 1 phần text button
	public void clickToDynamicButtonContains(String dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, TrainTicketPageUIs.DYNAMIC_BUTTON_CONTAIN, dynamicTextValue);
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_BUTTON_CONTAIN, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_BUTTON_CONTAIN, dynamicTextValue);
		}
	}

	// Click link
	public void clickToDynamicLink(String... dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
		}
	}

	// Click nút back bằng label cạnh nó
	public void clickToDynamicBackIcon(String dynamicTextValue) {
		boolean status = false;
		// scrollIDown(driver, TrainTicketPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		}
	}

	// Click lựa chọn điểm đi và điểm đến
	public void clickDynamicPointStartAndEnd(String... dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicID);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicID);
		}
	}

	// Click button cancel
	public void clickDynamicCancelIcon(String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_CANCEL_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_CANCEL_ICON, dynamicTextValue);
		}
	}

	// Click icon change
	public void clickToDynamicIconChange(String dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicText);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicText);
		}
	}

	// Click select date
	public void clickToDynamicSelectDate(String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		}
	}

	// Click Chọn chuyến
	public void clickDynamicSelectTrain(String... dynamicTextID) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_NAME_TRAIN, dynamicTextID);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_TEXT_NAME_TRAIN, dynamicTextID);
		}
	}

	// Click Chọn toa
	public void clickDynamicSelectLocation(String... dynamicTextID) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_LOCATION, dynamicTextID);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_TEXT_LOCATION, dynamicTextID);
		}
	}

	// Click chọn ngày trong lịch calendar
	public void clickDynamicDateStartAndEnd(String... dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		}
	}

	// Click radio button
	public void clickDynamicRadioSelectType(String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_SUCCESS_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_SUCCESS_ICON, dynamicTextValue);
		}
	}

	// Click tăng giảm số lượng
	public void clickDynamicButtonNumber(String... dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_ICON_CHANGE_NUMBER, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_ICON_CHANGE_NUMBER, dynamicTextValue);
		}
	}

	public void clickDynamicTextInput(String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicID);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicID);
		}
	}

	// Click button follow resource ID
	public void clickDynamicButtonResourceID(String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicID);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicID);
		}
	}

	// Click image follow resource ID
	public void clickDynamicImageResourceID(String dynamicID) {
		boolean status = false;
		scrollIDown(driver, TrainTicketPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		}
	}

	public void clickDynamicEditResourceID(String... dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_EDIT, dynamicID);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_TEXT_EDIT, dynamicID);
		}
	}

	// So sánh giá trị trong list combobox, không cần sắp xếp theo thứ tự
	public boolean checkListContain(List<String> actualList, List<String> expectList) {
		return expectList.containsAll(actualList);
	}

	// Lay thu trong tuan
	public String getDynamicTitleWeek(String... dynamicText) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_WEEK, dynamicText);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_WEEK, dynamicText);
		}
		return text;
	}

	// Lay ten tau hien thi
	public String getDynamicTextViewIndex(String... dynamicIndex) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_NAME_TRAIN, dynamicIndex);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TEXT_NAME_TRAIN, dynamicIndex);
		}
		return text;
	}

	// Lấy message thông báo
	public String getDynamicMessageInvalid(String... dynamicText) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicText);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicText);
		}
		return text;
	}

	public String getDynamicTextOld(String... dynamicText) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_NUMBER_CUSTOMER, dynamicText);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_NUMBER_CUSTOMER, dynamicText);
		}
		return text;
	}

	
	public String getDynamicTextContains(String... dynamicText) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_BUTTON_CONTAIN, dynamicText);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_BUTTON_CONTAIN, dynamicText);
		}
		return text;
	}
	// Hiển thị time ngay book
	public String getDynamicDateTime(String dynamicID) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		}
		return text;
	}

	public List<String> getDynamicList(String dynamicID) {
		waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		return getTextInListElements(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
	}

	public String getDynamicTitleSelectDate(String dynamicText) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_DATE, dynamicText);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_DATE, dynamicText);
		}
		return text;
	}

	// Lấy text ngày đặt vé
	public String getTextInDynamicDateTicket(String... dynamicTextValue) {
		String text = null;

		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		scrollIDown(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		}
		return text;
	}

	// Lấy text tìm hiếm điểm khởi hành và điểm đến
	public String getDynamicPointStartAndEnd(String... dynamicID) {

		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicID);

		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicID);

		}
		return text;
	}

	// Lấy giá trị tìm kiếm trong danh sách
	public String getDynamicInputPoint(String dynamicID) {

		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicID);

		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicID);
		}
		return text;
	}

	public String getTextInDynamicNote(String dynamicIndex) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_INDEX, dynamicIndex);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TEXT_INDEX, dynamicIndex);
		}
		return text;
	}

	public String getTextInDynamicMessageSearch(String dynamicID) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_MESSAGE_SEARCH, dynamicID);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TEXT_MESSAGE_SEARCH, dynamicID);
		}
		return text;
	}

	public String getDynamicTextPointStart(String dynamicID) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_VIEW_TEXT_START, dynamicID);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_VIEW_TEXT_START, dynamicID);
		}
		return text;
	}

	public String getTextTextViewByLinearLayoutID(String... dynamicID) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID, dynamicID);
		}
		return text;
	}

	public String getDynamicTextInPopUp(String... dynamicTextValue) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_IMAGE, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TEXT_IMAGE, dynamicTextValue);
		}
		return text;
	}

	public String getTextInDynamicPopup(String dynamicResourceID) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_IN_POPUP, dynamicResourceID);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TEXT_IN_POPUP, dynamicResourceID);
		}
		return text;
	}

	public String getTextTotal(String... dynamicResourceID) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_TOTAL, dynamicResourceID);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TEXT_TOTAL, dynamicResourceID);
		}
		return text;
	}

	public String getTextMaxLength(String dynamicResourceID) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicResourceID);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicResourceID);
		}
		return text;
	}

	// lấy số lượng khách hàng
	public String getTextCustomerNumber(String... dynamicResourceID) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_CHANGE_NUMBER, dynamicResourceID);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TEXT_CHANGE_NUMBER, dynamicResourceID);
		}
		return text;
	}

	public List<String> getListOfSuggestedMoneyOrListText(String dynamicID) {
		waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
		return getTextInListElements(driver, TrainTicketPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicID);
	}

	public String getTextMessageInvalid(String dynamicResourceID) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicResourceID);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY, dynamicResourceID);
		}
		return text;
	}

	// Lấy text theo button ID
	public String getTextMessageFollowButton(String dynamicResourceID) {
		String text = null;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_FOLLOW_BUTTON, dynamicResourceID);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TEXT_FOLLOW_BUTTON, dynamicResourceID);
		}
		return text;
	}

	// Kiểm tra text có hiển thị hay không, tham số truyền vào là text
	public boolean isDynamicMessageAndLabelTextDisplayed(String dynamicTextValue) {
		boolean isDisplayed = false;
		// scrollIDown(driver, TrainTicketPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT,
		// dynamicTextValue);
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Hiển thị button sửa
	public boolean isDynamicButtonEditDisplay(String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, TrainTicketPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicTextValue);
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_ACCEPT_BUTTON_OR_BUTTON, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Kiểm tra hiển thị image, check chuyển khoản thành công
	public boolean isDynamicImageSuccess(String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_SUCCESS_ICON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_SUCCESS_ICON, dynamicTextValue);
		}
		return isDisplayed;
	}

	// check button có hiển thị hay không, tham số truyền vào là text của button
	public boolean isDynamicButtonDisplayed(String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, TrainTicketPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Hiển thị time chay
	public boolean isDynamicDateTimeDisplay(String dynamicID) {
		boolean isDisplayed = false;
		scrollIDown(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		}
		return isDisplayed;
	}

	// Hiển thị tên tàu
	public boolean isDynamicNameTrainDisplay(String... dynamicID) {
		boolean isDisplayed = false;
		scrollIDown(driver, TrainTicketPageUIs.DYNAMIC_TEXT_NAME_TRAIN, dynamicID);
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_NAME_TRAIN, dynamicID);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_TEXT_NAME_TRAIN, dynamicID);
		}
		return isDisplayed;
	}

	// hiển thị radio button
	public boolean isDynamicRadioDisplayed(String dynamicID) {
		boolean isDisplayed = false;
		scrollIDown(driver, TrainTicketPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_BOTTOM_MENU_CLOSE_ICON, dynamicID);
		}
		return isDisplayed;
	}

	public boolean isDynamicBackIconDisplayed(String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_BACK_ICON, dynamicTextValue);
		}
		return isDisplayed;
	}

	public boolean isDynamicHistoryIconDisplayed(String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Check hiển thị button chuyển đổi
	public boolean isDynamicChangeIconDisplayed(String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Check hiển thị icon combobox
	public boolean isDynamicComboboxDisplayed(String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_COMBOBOX, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_COMBOBOX, dynamicTextValue);
		}
		return isDisplayed;
	}

	public boolean isDynamicVerifyTextOnButton(String dynamicTextValue) {
		boolean isDisplayed = false;
		scrollIDown(driver, TrainTicketPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_BUTTON, dynamicTextValue);
		}
		return isDisplayed;
	}

	// hiển thị icon trường số lượng hành khách
	public boolean isDynamicIconChangeNumber(String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_ICON_CHANGE_NUMBER, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_ICON_CHANGE_NUMBER, dynamicTextValue);
		}
		return isDisplayed;
	}

	// hiển thị text trường số lượng hành khách
	public boolean isDynamicTextChangeNumber(String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_CHANGE_NUMBER, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_TEXT_CHANGE_NUMBER, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Lấy text trường số lượng hành khách
	public boolean isDynamicTextNumberCustomerDisplayed(String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Không hiển thị text trường số lượng hành khách
	public boolean isDynamicTextNumberCustomerUnDisplayed(String dynamicTextValue) {
		boolean isUndisplayed = false;
		boolean status = waitForElementInvisible(driver, TrainTicketPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
		if (status == true) {
			isUndisplayed = isControlUnDisplayed(driver, TrainTicketPageUIs.DYNAMIC_LABEL_AMOUNT, dynamicTextValue);
		}
		return isUndisplayed;
	}

	// Không hiển thị icon check
	public boolean isDynamicIconUncheck(String... dynamicTextValue) {
		boolean isUndisplayed = false;
		boolean status = waitForElementInvisible(driver, TrainTicketPageUIs.DYNAMIC_ICON_CHECK, dynamicTextValue);
		if (status == true) {
			isUndisplayed = isControlUnDisplayed(driver, TrainTicketPageUIs.DYNAMIC_ICON_CHECK, dynamicTextValue);
		}
		return isUndisplayed;
	}

	// Hiển thị icon check
	public boolean isDynamicIconCheck(String... dynamicTextValue) {
		boolean isdisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_ICON_CHECK, dynamicTextValue);
		if (status == true) {
			isdisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_ICON_CHECK, dynamicTextValue);
		}
		return isdisplayed;
	}

	// Nhập địa điểm tìm kiếm
	public void inputToDynamicTextPoint(String inputValue, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicID);
		if (status == true) {
			clearText(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicID);
			sendKeyToElement(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, inputValue, dynamicID);
		}
	}

	// Nhập thông tin khách hàng, dựa vào linearlayout ID
	public void inputToDynamicText(String inputValue, String... dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_EDIT, dynamicID);
		if (status == true) {
			clearText(driver, TrainTicketPageUIs.DYNAMIC_TEXT_EDIT, dynamicID);
			sendKeyToElement(driver, TrainTicketPageUIs.DYNAMIC_TEXT_EDIT, inputValue, dynamicID);
		}
	}

	// Nhập thông tin khách hàng, dựa vào linearlayout ID
	public void inputToDynamicTextHeader(String inputValue, String... dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_HEADER, dynamicID);
		if (status == true) {
			clearText(driver, TrainTicketPageUIs.DYNAMIC_TEXT_HEADER, dynamicID);
			sendKeyToElement(driver, TrainTicketPageUIs.DYNAMIC_TEXT_HEADER, inputValue, dynamicID);
		}
	}

	// Click icon change
	public void clickToDynamicIconChange(AppiumDriver<MobileElement> driver, String dynamicText) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicText);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicText);
		}
	}

	// Click select date
	public void clickToDynamicSelectDate(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		}
	}

	// Click chọn ngày trong lịch calendar
	public void clickDynamicDateStartAndEnd(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		scrollIDown(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		}
	}

	public void clickToDynamicLink(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
		}
	}

	// Click button cancel
	public void clickDynamicCancelIcon(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_CANCEL_ICON, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_CANCEL_ICON, dynamicTextValue);
		}
	}

	public void clickDynamicPointStartAndEnd(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicTextValue);
		if (status == true) {
			clickToElement(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicTextValue);
		}
	}

// Nhập địa điểm tìm kiếm
	public void inputToDynamicTextPoint(AppiumDriver<MobileElement> driver, String inputValue, String dynamicIndexValue) {
		boolean status = false;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicIndexValue);
		if (status == true) {
			clearText(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicIndexValue);
			sendKeyToElement(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, inputValue, dynamicIndexValue);
		}
	}

// Lấy text ngày đặt vé
	public String getTextInDynamicDateTicket(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_DATE_SELECTED, dynamicTextValue);
		}
		return text;
	}

	// Lấy text tìm hiếm điểm khởi hành và điểm đến
	public String getDynamicPointStartAndEnd(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicTextValue);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_START_AND_END_TEXT, dynamicTextValue);
		}
		return text;

	}

	// Lấy text tìm hiếm điểm khởi hành và điểm đến
	public String getDynamicTextEdit(String... dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_EDIT, dynamicID);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TEXT_EDIT, dynamicID);
		}
		return text;

	}

	// Lấy giá trị tìm kiếm trong danh sách
	public String getDynamicInputPoint(AppiumDriver<MobileElement> driver, String dynamicIndexValue) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicIndexValue);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_INPUT_POINT, dynamicIndexValue);
		}
		return text;

	}

	public String getDynamicTextPointStart(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_VIEW_TEXT_START, dynamicID);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_VIEW_TEXT_START, dynamicID);
		}
		return text;
	}

// Hiển thị time ngay book
	public String getDynamicDateTime(AppiumDriver<MobileElement> driver, String dynamicID) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_DATE, dynamicID);
		}
		return text;

	}

	public String getDynamicTitleSelectDate(AppiumDriver<MobileElement> driver, String dynamicText) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_DATE, dynamicText);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_DATE, dynamicText);
		}
		return text;

	}

//Lay thu trong tuan
	public String getDynamicTitleWeek(AppiumDriver<MobileElement> driver, String... dynamicText) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_WEEK, dynamicText);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TITLE_SELECT_WEEK, dynamicText);
		}
		return text;
	}

	public String getDynamicTextOld(AppiumDriver<MobileElement> driver, String... dynamicText) {

		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_NUMBER_CUSTOMER, dynamicText);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_NUMBER_CUSTOMER, dynamicText);
		}
		return text;
	}

	public boolean isDynamicHistoryIconDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_HISTORY_ICON, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Check hiển thị button chuyển đổi
	public boolean isDynamicChangeIconDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_CHANGE_ICON, dynamicTextValue);
		}
		return isDisplayed;
	}

	// Check hiển thị icon combobox
	public boolean isDynamicComboboxDisplayed(AppiumDriver<MobileElement> driver, String dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_COMBOBOX, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_COMBOBOX, dynamicTextValue);
		}
		return isDisplayed;
	}

	// hiển thị icon trường số lượng hành khách
	public boolean isDynamicIconChangeNumber(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_ICON_CHANGE_NUMBER, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_ICON_CHANGE_NUMBER, dynamicTextValue);
		}
		return isDisplayed;

	}

	// hiển thị text trường số lượng hành khách
	public boolean isDynamicTextChangeNumber(AppiumDriver<MobileElement> driver, String... dynamicTextValue) {
		boolean isDisplayed = false;
		boolean status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_CHANGE_NUMBER, dynamicTextValue);
		if (status == true) {
			isDisplayed = isControlDisplayed(driver, TrainTicketPageUIs.DYNAMIC_TEXT_CHANGE_NUMBER, dynamicTextValue);
		}
		return isDisplayed;

	}

	public String getTextInDynamicNote(AppiumDriver<MobileElement> driver, String dynamicIndex) {
		boolean status = false;
		String text = null;
		status = waitForElementVisible(driver, TrainTicketPageUIs.DYNAMIC_TEXT_INDEX, dynamicIndex);
		if (status == true) {
			text = getTextElement(driver, TrainTicketPageUIs.DYNAMIC_TEXT_INDEX, dynamicIndex);

		}
		return text;
	}
	
	public String getNumberRandom() {
        int rand = (int)(Math.random() * (999999-100000)+1) + 100000; 
		String number = Integer.toString(rand);
		return number;
	}
	
	/* SCROLL UP */
	public void scrollUpToText( String dynamicText) {
		scrollUp(DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, dynamicText);

	}
	public void scrollUp( String locator, String... dynamicValue) {
		Dimension size = driver.manage().window().getSize();
		int x = size.getWidth() / 2;
		int startY = (int) (size.getHeight() * 0.30);
		int endY = (int) (size.getHeight() * 0.80);
		TouchAction touch = new TouchAction(driver);
		List<MobileElement> elementsOne = null;
		try {
			locator = String.format(locator, (Object[]) dynamicValue);
		} catch (Exception e) {
		}
		for (int i = 0; i < 20; i++) {
			boolean checkElementDisplayed = false;
			overRideTimeOut(driver, 2);
			try {
				driver.getPageSource();
				elementsOne = driver.findElements(By.xpath(locator));
				checkElementDisplayed = elementsOne.get(0).isDisplayed();
			} catch (Exception e) {
				checkElementDisplayed = true;
			}

			overRideTimeOut(driver, Constants.LONG_TIME);
			if (elementsOne.size() > 0 && checkElementDisplayed) {
				break;
			} else {
				try {
					touch.longPress(PointOption.point(x, startY)).moveTo(PointOption.point(x, endY)).release().perform();
				} catch (Exception e) {
					System.out.print(e.getMessage());
				}
			}
		}
	}

}
