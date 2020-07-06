package vnpay.vietcombank.sdk.shopping_online;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import commons.WebPageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.ServiceLimitInfo;
import model.SourceAccountModel;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.WebBackendSetupPageObject;
import pageObjects.shopping_online.ShoppingOnlinePageObject;
import vietcombank_test_data.HomePage_Data;
import vietcombank_test_data.HomePage_Data.Home_Text_Elements;
import vnpay.vietcombank.sdk.shopping_online.data.Shopping_Online_Data;

public class Limit_Shopping_Online extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private ShoppingOnlinePageObject shopping;
	private HomePageObject homePage;
	String transferTime;
	List<String> listActual;
	String codeBill;
	double soDuThuc = 0;
	String codeTransfer = "";
	int indexHang = 0;
	WebDriver driverWeb;
	private WebBackendSetupPageObject webBackend;
	long amountStart, feeView, amountView, amountAfter = 0;
	String moneyConfirm;
	SourceAccountModel sourceAccount = new SourceAccountModel();
	ServiceLimitInfo inputInfo = new ServiceLimitInfo("1000", "10000", "500000000", "5500000000");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp", "username", "passWeb" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt, String username, String passWeb) throws IOException, InterruptedException, GeneralSecurityException {
		log.info("Before class: Mo backend ");
		startServer();
		driverWeb = openMultiBrowser(Constants.BE_BROWSER_CHROME, Constants.BE_BROWSER_VERSION, Constants.BE_URL);
		webBackend = WebPageFactoryManager.getWebBackendSetupPageObject(driverWeb);
		webBackend.Login_Web_Backend(driverWeb, username, passWeb);

		webBackend.addMethod(driverWeb, "Thanh toán mua sắm online", inputInfo, Constants.BE_CODE_PACKAGE);
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, HomePage_Data.Home_Text_Elements.VIETCOMBANK_2020);
		homePage.scrollIDownOneTime(driver);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.TITLE_SHOPPING_ONLINE);

		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		shopping = PageFactoryManager.getShoppingOnlinePageObject(driver);
		shopping.sleep(driver, 5000);
	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_Shopping_Online_Hoa_Don_Nho_Hon_Min_Tran(String otp) throws InterruptedException {
		log.info("TC_01_STEP_2: Them vao gio hang");
		shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);

		List<String> listProduct = shopping.getTextInListElementsProduct("đ");

		for (int i = 3; i < listProduct.size(); i++) {
			shopping.clickToDynamicView(listProduct.get(i));
			indexHang = i;
			if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
				log.info("TC_01_STEP_5: click Back");
				shopping.clickToDynamicCart("1", "0");
				continue;
			} else {
				break;
			}
		}

		log.info("TC_01_STEP_4: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

		log.info("TC_01_STEP_4: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("1");

		log.info("TC_01_STEP_3: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
		log.info("TC_01_STEP_5: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		shopping.scrollDownToTextView(Shopping_Online_Data.SHIPPING_STANDARD);
		String feeShipping = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SHIPPING_STANDARD);
		double feeShippingD = 0;
		if (feeShipping.equals(Shopping_Online_Data.FREE_SHIPPING)) {
			feeShipping = feeShipping.replace(Shopping_Online_Data.FREE_SHIPPING, "0");
			feeShippingD = 0;

		} else {
			feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
			feeShippingD = Double.parseDouble(feeShipping);

		}
		double totalMoney = tottalMoneyCart + feeShippingD;

		log.info("TC_01_STEP_5: click thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.ARE_YOU_HAVE_ADDRESS)) {

			log.info("TC_01_STEP_4_1: click the moi");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_NEW);

			log.info("TC_01_STEP_4_1: Hien thi man hinh them moi dia chi");
			shopping.isDynamicTextInfoDisplayed(Shopping_Online_Data.ADD_NEW_ADDRESS);

			log.info("TC_01_STEP_4_2: nhap ten");
			shopping.inputToDynamicInfo(Shopping_Online_Data.USER_NAME, Shopping_Online_Data.NAME_RECIEPT);

			log.info("TC_01_STEP_4_2: nhap so dien thoai");
			shopping.inputToDynamicInfo(Shopping_Online_Data.PHONE_NUMBER, Shopping_Online_Data.RECIPT_PHONE_NUMBER);

			log.info("TC_01_STEP_4_3: chon tinh thanh pho");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.CAPITAL);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.HANOI_CITY);

			log.info("TC_01_STEP_4_3: chon tinh quan huyen");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.DISTRICT);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.BA_DINH_DISTRICT);

			log.info("TC_01_STEP_4_3: chon tinh xa phuong");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.WARD);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.WARD_CONG_VI);

			log.info("TC_01_STEP_4_3: dia chi cu the");
			shopping.inputToDynamicInfo(Shopping_Online_Data.ADDRESS_NUMBER, Shopping_Online_Data.ADDRESS_DETAIL);

			log.info("TC_01_STEP_4_3: chon hoan tat");
			shopping.clickToDynamicButton(Shopping_Online_Data.COMPLETE);

			log.info("TC_01_STEP_6: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);
		}

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.PRODUCT_OUT)) {
			shopping.clickToDynamicButton(Shopping_Online_Data.BACK_TO_CART);

			shopping.clickToDynamicCart("2", "0");
			shopping.clickToDynamicButton(Shopping_Online_Data.BUY_NOW);

			shopping.clickToDynamicCategories(Shopping_Online_Data.VIEW_ALL);

			for (int j = indexHang + 1; j < listProduct.size(); j++) {
				shopping.clickToDynamicView(listProduct.get(j));
				if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
					log.info("TC_01_STEP_5: click Back");
					shopping.clickToDynamicCart("1", "0");
					continue;
				} else {
					break;
				}
			}
			log.info("TC_01_STEP_4: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_01_STEP_4: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_01_STEP_3: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
			log.info("TC_01_STEP_5: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

			shopping.scrollDownToTextView(Shopping_Online_Data.SHIPPING_STANDARD);
			feeShipping = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SHIPPING_STANDARD);
			feeShippingD = 0;
			if (feeShipping.equals(Shopping_Online_Data.FREE_SHIPPING)) {
				feeShipping = feeShipping.replace(Shopping_Online_Data.FREE_SHIPPING, "0");
				feeShippingD = 0;

			} else {
				feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
				feeShippingD = Double.parseDouble(feeShipping);

			}
			totalMoney = tottalMoneyCart + feeShippingD;
			ServiceLimitInfo inputInfoMin = new ServiceLimitInfo("1000", (totalMoney + 20) + "", (totalMoney + 100) + "", "10000000");

			webBackend.getInfoServiceLimit(driverWeb, "Thanh toán vé tàu", inputInfoMin, Constants.BE_CODE_PACKAGE);

			log.info("TC_01_STEP_5: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

			log.info("TC_01_STEP_3: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_01_STEP_4: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_01_STEP_4: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_01_STEP_5: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		}

		log.info("TC_01_STEP_7: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		sourceAccount = shopping.chooseSourceAccount(driver, Double.parseDouble(Constants.THREE_BILLION_VND), Constants.VND_CURRENCY);

		log.info("TC_01_STEP_9: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.FEE_SHIPPING).split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_01_STEP_10: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.DISCOUNT).split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", "").replace("", ""));

		log.info("TC_01_STEP_11: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.PRICE_ORDER).split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoneyBill, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("TC_01_STEP_12: Chon thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		log.info("TC_01_STEP_12: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.CONFIRM_INFO);

		log.info("TC_01_STEP_17: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(shopping.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Giao dịch không thành công. Số tiền giao dịch nhỏ hơn hạn mức " + addCommasToLong((calulatorMoney + 20) + "") + " VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900 545413 để được trợ giúp.");

		shopping.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		webBackend.resetAssignServicesLimit_All(driverWeb, "Thanh toán vé tàu", Constants.BE_CODE_PACKAGE);

		shopping.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_02_Shopping_Online_Hoa_Don_Lon_Hon_Max_Tran() throws InterruptedException {

		log.info("TC_01_STEP_2: Them vao gio hang");
		shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);

		List<String> listProduct = shopping.getTextInListElementsProduct("đ");

		for (int i = 3; i < listProduct.size(); i++) {
			shopping.clickToDynamicView(listProduct.get(i));
			indexHang = i;
			if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
				log.info("TC_01_STEP_5: click Back");
				shopping.clickToDynamicCart("1", "0");
				continue;
			} else {
				break;
			}
		}

		log.info("TC_01_STEP_4: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

		log.info("TC_01_STEP_4: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("1");

		log.info("TC_01_STEP_3: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
		log.info("TC_01_STEP_5: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		shopping.scrollDownToTextView(Shopping_Online_Data.SHIPPING_STANDARD);
		String feeShipping = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SHIPPING_STANDARD);
		double feeShippingD = 0;
		if (feeShipping.equals(Shopping_Online_Data.FREE_SHIPPING)) {
			feeShipping = feeShipping.replace(Shopping_Online_Data.FREE_SHIPPING, "0");
			feeShippingD = 0;

		} else {
			feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
			feeShippingD = Double.parseDouble(feeShipping);

		}
		double totalMoney = tottalMoneyCart + feeShippingD;

		log.info("TC_01_STEP_5: click thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.ARE_YOU_HAVE_ADDRESS)) {

			log.info("TC_01_STEP_4_1: click the moi");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_NEW);

			log.info("TC_01_STEP_4_1: Hien thi man hinh them moi dia chi");
			shopping.isDynamicTextInfoDisplayed(Shopping_Online_Data.ADD_NEW_ADDRESS);

			log.info("TC_01_STEP_4_2: nhap ten");
			shopping.inputToDynamicInfo(Shopping_Online_Data.USER_NAME, Shopping_Online_Data.NAME_RECIEPT);

			log.info("TC_01_STEP_4_2: nhap so dien thoai");
			shopping.inputToDynamicInfo(Shopping_Online_Data.PHONE_NUMBER, Shopping_Online_Data.RECIPT_PHONE_NUMBER);

			log.info("TC_01_STEP_4_3: chon tinh thanh pho");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.CAPITAL);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.HANOI_CITY);

			log.info("TC_01_STEP_4_3: chon tinh quan huyen");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.DISTRICT);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.BA_DINH_DISTRICT);

			log.info("TC_01_STEP_4_3: chon tinh xa phuong");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.WARD);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.WARD_CONG_VI);

			log.info("TC_01_STEP_4_3: dia chi cu the");
			shopping.inputToDynamicInfo(Shopping_Online_Data.ADDRESS_NUMBER, Shopping_Online_Data.ADDRESS_DETAIL);

			log.info("TC_01_STEP_4_3: chon hoan tat");
			shopping.clickToDynamicButton(Shopping_Online_Data.COMPLETE);

			log.info("TC_01_STEP_6: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);
		}

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.PRODUCT_OUT)) {
			shopping.clickToDynamicButton(Shopping_Online_Data.BACK_TO_CART);

			shopping.clickToDynamicCart("2", "0");
			shopping.clickToDynamicButton(Shopping_Online_Data.BUY_NOW);

			shopping.clickToDynamicCategories(Shopping_Online_Data.VIEW_ALL);

			for (int j = indexHang + 1; j < listProduct.size(); j++) {
				shopping.clickToDynamicView(listProduct.get(j));
				if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
					log.info("TC_01_STEP_5: click Back");
					shopping.clickToDynamicCart("1", "0");
					continue;
				} else {
					break;
				}
			}
			log.info("TC_01_STEP_4: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_01_STEP_4: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_01_STEP_3: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
			log.info("TC_01_STEP_5: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

			shopping.scrollDownToTextView(Shopping_Online_Data.SHIPPING_STANDARD);
			feeShipping = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SHIPPING_STANDARD);
			feeShippingD = 0;
			if (feeShipping.equals(Shopping_Online_Data.FREE_SHIPPING)) {
				feeShipping = feeShipping.replace(Shopping_Online_Data.FREE_SHIPPING, "0");
				feeShippingD = 0;

			} else {
				feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
				feeShippingD = Double.parseDouble(feeShipping);

			}
			totalMoney = tottalMoneyCart + feeShippingD;

			ServiceLimitInfo inputInfoMax = new ServiceLimitInfo("1000", (totalMoney - 20) + "", (totalMoney - 10) + "", "10000000");
			webBackend.getInfoServiceLimit(driverWeb, "Thanh toán vé tàu", inputInfoMax, Constants.BE_CODE_PACKAGE);

			log.info("TC_01_STEP_5: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

			log.info("TC_01_STEP_3: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_01_STEP_4: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_01_STEP_4: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_01_STEP_5: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		}

		log.info("TC_01_STEP_7: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		sourceAccount = shopping.chooseSourceAccount(driver, Double.parseDouble(Constants.THREE_BILLION_VND), Constants.VND_CURRENCY);

		log.info("TC_01_STEP_9: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.FEE_SHIPPING).split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_01_STEP_10: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.DISCOUNT).split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", "").replace("", ""));

		log.info("TC_01_STEP_11: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.PRICE_ORDER).split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoneyBill, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("TC_01_STEP_12: Chon thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		log.info("TC_01_STEP_12: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.CONFIRM_INFO);

		log.info("TC_01_STEP_17: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(shopping.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Giao dịch không thành công. Số tiền giao dịch nhỏ hơn hạn mức " + addCommasToLong((calulatorMoney + 20) + "") + " VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900 545413 để được trợ giúp.");

		shopping.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		webBackend.resetAssignServicesLimit_All(driverWeb, "Thanh toán vé tàu", Constants.BE_CODE_PACKAGE);

		shopping.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_03_Shopping_Online_Hoa_Don_Lon_Hon_Max_Nhom_Giao_Dich() throws InterruptedException {

		log.info("TC_01_STEP_2: Them vao gio hang");
		shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);

		List<String> listProduct = shopping.getTextInListElementsProduct("đ");

		for (int i = 3; i < listProduct.size(); i++) {
			shopping.clickToDynamicView(listProduct.get(i));
			indexHang = i;
			if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
				log.info("TC_01_STEP_5: click Back");
				shopping.clickToDynamicCart("1", "0");
				continue;
			} else {
				break;
			}
		}

		log.info("TC_01_STEP_4: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

		log.info("TC_01_STEP_4: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("1");

		log.info("TC_01_STEP_3: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
		log.info("TC_01_STEP_5: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		shopping.scrollDownToTextView(Shopping_Online_Data.SHIPPING_STANDARD);
		String feeShipping = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SHIPPING_STANDARD);
		double feeShippingD = 0;
		if (feeShipping.equals(Shopping_Online_Data.FREE_SHIPPING)) {
			feeShipping = feeShipping.replace(Shopping_Online_Data.FREE_SHIPPING, "0");
			feeShippingD = 0;

		} else {
			feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
			feeShippingD = Double.parseDouble(feeShipping);

		}
		double totalMoney = tottalMoneyCart + feeShippingD;

		log.info("TC_01_STEP_5: click thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.ARE_YOU_HAVE_ADDRESS)) {

			log.info("TC_01_STEP_4_1: click the moi");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_NEW);

			log.info("TC_01_STEP_4_1: Hien thi man hinh them moi dia chi");
			shopping.isDynamicTextInfoDisplayed(Shopping_Online_Data.ADD_NEW_ADDRESS);

			log.info("TC_01_STEP_4_2: nhap ten");
			shopping.inputToDynamicInfo(Shopping_Online_Data.USER_NAME, Shopping_Online_Data.NAME_RECIEPT);

			log.info("TC_01_STEP_4_2: nhap so dien thoai");
			shopping.inputToDynamicInfo(Shopping_Online_Data.PHONE_NUMBER, Shopping_Online_Data.RECIPT_PHONE_NUMBER);

			log.info("TC_01_STEP_4_3: chon tinh thanh pho");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.CAPITAL);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.HANOI_CITY);

			log.info("TC_01_STEP_4_3: chon tinh quan huyen");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.DISTRICT);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.BA_DINH_DISTRICT);

			log.info("TC_01_STEP_4_3: chon tinh xa phuong");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.WARD);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.WARD_CONG_VI);

			log.info("TC_01_STEP_4_3: dia chi cu the");
			shopping.inputToDynamicInfo(Shopping_Online_Data.ADDRESS_NUMBER, Shopping_Online_Data.ADDRESS_DETAIL);

			log.info("TC_01_STEP_4_3: chon hoan tat");
			shopping.clickToDynamicButton(Shopping_Online_Data.COMPLETE);

			log.info("TC_01_STEP_6: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);
		}

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.PRODUCT_OUT)) {
			shopping.clickToDynamicButton(Shopping_Online_Data.BACK_TO_CART);

			shopping.clickToDynamicCart("2", "0");
			shopping.clickToDynamicButton(Shopping_Online_Data.BUY_NOW);

			shopping.clickToDynamicCategories(Shopping_Online_Data.VIEW_ALL);

			for (int j = indexHang + 1; j < listProduct.size(); j++) {
				shopping.clickToDynamicView(listProduct.get(j));
				if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
					log.info("TC_01_STEP_5: click Back");
					shopping.clickToDynamicCart("1", "0");
					continue;
				} else {
					break;
				}
			}
			log.info("TC_01_STEP_4: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_01_STEP_4: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_01_STEP_3: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
			log.info("TC_01_STEP_5: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

			shopping.scrollDownToTextView(Shopping_Online_Data.SHIPPING_STANDARD);
			feeShipping = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SHIPPING_STANDARD);
			feeShippingD = 0;
			if (feeShipping.equals(Shopping_Online_Data.FREE_SHIPPING)) {
				feeShipping = feeShipping.replace(Shopping_Online_Data.FREE_SHIPPING, "0");
				feeShippingD = 0;

			} else {
				feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
				feeShippingD = Double.parseDouble(feeShipping);

			}
			totalMoney = tottalMoneyCart + feeShippingD;
			webBackend.Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Thanh toán hóa đơn", (totalMoney - 10000) + "");

			log.info("TC_01_STEP_5: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

			log.info("TC_01_STEP_3: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_01_STEP_4: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_01_STEP_4: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_01_STEP_5: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		}

		log.info("TC_01_STEP_7: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		sourceAccount = shopping.chooseSourceAccount(driver, Double.parseDouble(Constants.THREE_BILLION_VND), Constants.VND_CURRENCY);

		log.info("TC_01_STEP_9: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.FEE_SHIPPING).split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_01_STEP_10: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.DISCOUNT).split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", "").replace("", ""));

		log.info("TC_01_STEP_11: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.PRICE_ORDER).split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoneyBill, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("TC_01_STEP_12: Chon thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		log.info("TC_01_STEP_12: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.CONFIRM_INFO);

		log.info("TC_01_STEP_17: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(shopping.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Giao dịch không thành công. Số tiền giao dịch nhỏ hơn hạn mức " + addCommasToLong((calulatorMoney + 20) + "") + " VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900 545413 để được trợ giúp.");

		shopping.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		webBackend.Reset_Setup_Assign_Services_Type_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Thanh toán hóa đơn");

		shopping.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

	}

	@Test
	public void TC_04_BaoCaoGiaoDichChonMuaNhieuSanPhamThanhToanOTPKhongChonKhuyenMai() throws InterruptedException {
		log.info("TC_01_STEP_2: Them vao gio hang");
		shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);

		List<String> listProduct = shopping.getTextInListElementsProduct("đ");

		for (int i = 3; i < listProduct.size(); i++) {
			shopping.clickToDynamicView(listProduct.get(i));
			indexHang = i;
			if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
				log.info("TC_01_STEP_5: click Back");
				shopping.clickToDynamicCart("1", "0");
				continue;
			} else {
				break;
			}
		}

		log.info("TC_01_STEP_4: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

		log.info("TC_01_STEP_4: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("1");

		log.info("TC_01_STEP_3: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
		log.info("TC_01_STEP_5: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		shopping.scrollDownToTextView(Shopping_Online_Data.SHIPPING_STANDARD);
		String feeShipping = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SHIPPING_STANDARD);
		double feeShippingD = 0;
		if (feeShipping.equals(Shopping_Online_Data.FREE_SHIPPING)) {
			feeShipping = feeShipping.replace(Shopping_Online_Data.FREE_SHIPPING, "0");
			feeShippingD = 0;

		} else {
			feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
			feeShippingD = Double.parseDouble(feeShipping);

		}
		double totalMoney = tottalMoneyCart + feeShippingD;

		log.info("TC_01_STEP_5: click thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.ARE_YOU_HAVE_ADDRESS)) {

			log.info("TC_01_STEP_4_1: click the moi");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_NEW);

			log.info("TC_01_STEP_4_1: Hien thi man hinh them moi dia chi");
			shopping.isDynamicTextInfoDisplayed(Shopping_Online_Data.ADD_NEW_ADDRESS);

			log.info("TC_01_STEP_4_2: nhap ten");
			shopping.inputToDynamicInfo(Shopping_Online_Data.USER_NAME, Shopping_Online_Data.NAME_RECIEPT);

			log.info("TC_01_STEP_4_2: nhap so dien thoai");
			shopping.inputToDynamicInfo(Shopping_Online_Data.PHONE_NUMBER, Shopping_Online_Data.RECIPT_PHONE_NUMBER);

			log.info("TC_01_STEP_4_3: chon tinh thanh pho");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.CAPITAL);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.HANOI_CITY);

			log.info("TC_01_STEP_4_3: chon tinh quan huyen");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.DISTRICT);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.BA_DINH_DISTRICT);

			log.info("TC_01_STEP_4_3: chon tinh xa phuong");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.WARD);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.WARD_CONG_VI);

			log.info("TC_01_STEP_4_3: dia chi cu the");
			shopping.inputToDynamicInfo(Shopping_Online_Data.ADDRESS_NUMBER, Shopping_Online_Data.ADDRESS_DETAIL);

			log.info("TC_01_STEP_4_3: chon hoan tat");
			shopping.clickToDynamicButton(Shopping_Online_Data.COMPLETE);

			log.info("TC_01_STEP_6: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);
		}

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.PRODUCT_OUT)) {
			shopping.clickToDynamicButton(Shopping_Online_Data.BACK_TO_CART);

			shopping.clickToDynamicCart("2", "0");
			shopping.clickToDynamicButton(Shopping_Online_Data.BUY_NOW);

			shopping.clickToDynamicCategories(Shopping_Online_Data.VIEW_ALL);

			for (int j = indexHang + 1; j < listProduct.size(); j++) {
				shopping.clickToDynamicView(listProduct.get(j));
				if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
					log.info("TC_01_STEP_5: click Back");
					shopping.clickToDynamicCart("1", "0");
					continue;
				} else {
					break;
				}
			}
			log.info("TC_01_STEP_4: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_01_STEP_4: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_01_STEP_3: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
			log.info("TC_01_STEP_5: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

			shopping.scrollDownToTextView(Shopping_Online_Data.SHIPPING_STANDARD);
			feeShipping = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SHIPPING_STANDARD);
			feeShippingD = 0;
			if (feeShipping.equals(Shopping_Online_Data.FREE_SHIPPING)) {
				feeShipping = feeShipping.replace(Shopping_Online_Data.FREE_SHIPPING, "0");
				feeShippingD = 0;

			} else {
				feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
				feeShippingD = Double.parseDouble(feeShipping);

			}
			totalMoney = tottalMoneyCart + feeShippingD;

			webBackend.Setup_Add_Method_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp", (totalMoney - 1000) + "");

			log.info("TC_01_STEP_5: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

			log.info("TC_01_STEP_3: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_01_STEP_4: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_01_STEP_4: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_01_STEP_5: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		}

		log.info("TC_01_STEP_7: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		sourceAccount = shopping.chooseSourceAccount(driver, Double.parseDouble(Constants.THREE_BILLION_VND), Constants.VND_CURRENCY);

		log.info("TC_01_STEP_9: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.FEE_SHIPPING).split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_01_STEP_10: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.DISCOUNT).split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", "").replace("", ""));

		log.info("TC_01_STEP_11: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.PRICE_ORDER).split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoneyBill, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("TC_01_STEP_12: Chon thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		log.info("TC_01_STEP_12: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.CONFIRM_INFO);

		log.info("TC_01_STEP_17: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("TC_01_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyEquals(shopping.getDynamicTextView(driver, "com.VCB:id/tvContent"), "Giao dịch không thành công. Số tiền giao dịch nhỏ hơn hạn mức " + addCommasToLong((calulatorMoney + 20) + "") + " VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900 545413 để được trợ giúp.");

		shopping.clickToDynamicContinue(driver, "com.VCB:id/btOK");

		webBackend.Reset_Package_Total_Limit(driverWeb, Constants.BE_CODE_PACKAGE, "Method Otp");

		shopping.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
