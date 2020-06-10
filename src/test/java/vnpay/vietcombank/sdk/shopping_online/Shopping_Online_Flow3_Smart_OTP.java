package vnpay.vietcombank.sdk.shopping_online;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.Constants;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.SourceAccountModel;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.shopping_online.ShoppingOnlinePageObject;
import vietcombank_test_data.HomePage_Data;
import vietcombank_test_data.HomePage_Data.Home_Text_Elements;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;
import vnpay.vietcombank.sdk.shopping_online.data.Shopping_Online_Data;

public class Shopping_Online_Flow3_Smart_OTP extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private ShoppingOnlinePageObject shopping;
	private HomePageObject homePage;
	private SettingVCBSmartOTPPageObject smartOTP;

	String transferTime;
	String transactionNumber;
	List<String> listActual;
	double soDuThuc = 0;
	String maGiaodich, otpSmart, newOTP = "";
	int indexHang = 0;

	long amount, amountStart, feeView, amountView, amountAfter = 0;

	SourceAccountModel sourceAccount = new SourceAccountModel();

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException, GeneralSecurityException {
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
		otpSmart = getDataInCell(6);
		newOTP = "111222";
		smartOTP.setupSmartOTP(LogIn_Data.Login_Account.Smart_OTP, otpSmart);
	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_ChonMuaMotSanPhamThanhToanOTPKhongChonKhuyenMai(String otp) {
		log.info("TC_01_STEP_2: Them vao gio hang");
		shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);

		List<String> listProduct = shopping.getTextInListElementsProduct("đ");

		for (int i = 4; i < listProduct.size(); i++) {
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

		log.info("TC_01_STEP_6: click thanh toan ngay");
		shopping.clickToDynamicButton(Shopping_Online_Data.ACCEPT);

		log.info("TC_01_STEP_7: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		sourceAccount = shopping.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_02_STEP_7: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AVAIABLE_BALANCE).replace("VND", "").replace(",", ""));

		log.info("TC_02_STEP_7: lay thong tin ma don hang");
		String codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("TC_01_STEP_9: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.FEE_SHIPPING).split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_01_STEP_9: verify fee Ship");
		verifyEquals(feeShippingD, fee);

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

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);
		verifyTrue(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AMOUNT_PRICE).contains(totalMoneyBillString[1]));

		log.info("TC_01_STEP_13: Kiem tra tai khoan nguon");
		String account = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SOURCE_ACCOUNT);
		verifyEquals(account, sourceAccount.account);

		log.info("TC_01_STEP_14: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount(Shopping_Online_Data.AMOUNT_PRICE).replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", calulatorMoney + " VND");

		log.info("TC_01_STEP_16: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText(Shopping_Online_Data.SMS_OTP);

		log.info("TC_01_STEP_17: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("TC_01_Step_23: Nhap OTP");
		shopping.inputToDynamicSmartOTP(newOTP, "com.VCB:id/otp");

		log.info("TC_01_Step_24: Click tiep tuc");
		shopping.clickToDynamicButton(TittleData.CONTINUE_BTN);
		shopping.clickToDynamicButton(TittleData.CONTINUE_BTN);

		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.SUCCESS_TRANSFER);

		soDuThuc = soDuTK - moneyConfirm;

		log.info("TC_01_STEP_19: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SUPPLIER), Shopping_Online_Data.VNSHOP);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);

		maGiaodich = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_TRANSFER);

		log.info("TC_01_STEP_20: thuc hien giao dich moi");
		shopping.clickToDynamicButton(Shopping_Online_Data.NEW_TRANSFER);

	}

	@Parameters({ "otp" })
	@Test
	public void TC_02_ChonMuaNhieuSanPhamThanhToanOTPKhongChonKhuyenMai(String otp) {

		log.info("TC_02_STEP_: Them vao gio hang");
		shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);

		List<String> listProduct = shopping.getTextInListElementsProduct("đ");

		for (int i = 4; i < listProduct.size(); i++) {
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

		log.info("TC_02_STEP_: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

		log.info("TC_02_STEP_: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("3");

		log.info("TC_02_STEP_: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
		log.info("TC_02_STEP_: click dat hang");
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

		log.info("TC_02_STEP_: click thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.ARE_YOU_HAVE_ADDRESS)) {

			log.info("TC_02_STEP_: click the moi");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_NEW);

			log.info("TC_02_STEP_: Hien thi man hinh them moi dia chi");
			shopping.isDynamicTextInfoDisplayed(Shopping_Online_Data.ADD_NEW_ADDRESS);

			log.info("TC_02_STEP_: nhap ten");
			shopping.inputToDynamicInfo(Shopping_Online_Data.USER_NAME, Shopping_Online_Data.NAME_RECIEPT);

			log.info("TC_02_STEP_: nhap so dien thoai");
			shopping.inputToDynamicInfo(Shopping_Online_Data.PHONE_NUMBER, Shopping_Online_Data.RECIPT_PHONE_NUMBER);

			log.info("TC_02_STEP_: chon tinh thanh pho");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.CAPITAL);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.HANOI_CITY);

			log.info("TC_02_STEP_: chon tinh quan huyen");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.DISTRICT);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.BA_DINH_DISTRICT);

			log.info("TC_02_STEP_: chon tinh xa phuong");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.WARD);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.WARD_CONG_VI);

			log.info("TC_02_STEP_: dia chi cu the");
			shopping.inputToDynamicInfo(Shopping_Online_Data.ADDRESS_NUMBER, Shopping_Online_Data.ADDRESS_DETAIL);

			log.info("TC_02_STEP_: chon hoan tat");
			shopping.clickToDynamicButton(Shopping_Online_Data.COMPLETE);

			log.info("TC_02_STEP_: click thanh toan");
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
					log.info("TC_02_STEP_: click Back");
					shopping.clickToDynamicCart("1", "0");
					continue;
				} else {
					break;
				}
			}
			log.info("TC_02_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_02_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("3");

			log.info("TC_02_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
			log.info("TC_02_STEP_: click dat hang");
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

			log.info("TC_02_STEP_: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

			log.info("TC_02_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_02_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_02_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("3");

			log.info("TC_02_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		}

		log.info("TC_02_STEP_: click thanh toan ngay");
		shopping.clickToDynamicButton(Shopping_Online_Data.ACCEPT);

		log.info("TC_02_STEP_: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		sourceAccount = shopping.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_02_STEP_: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AVAIABLE_BALANCE).replace("VND", "").replace(",", ""));

		log.info("TC_02_STEP_: lay thong tin ma don hang");
		String codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("TC_02_STEP_: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.FEE_SHIPPING).split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_02_STEP_: verify fee Ship");
		verifyEquals(feeShippingD, fee);

		log.info("TC_02_STEP_: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.DISCOUNT).split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", "").replace("", ""));

		log.info("TC_02_STEP_: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.PRICE_ORDER).split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoneyBill, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("TC_02_STEP_: Chon thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		log.info("TC_02_STEP_: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.CONFIRM_INFO);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);
		verifyTrue(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AMOUNT_PRICE).contains(totalMoneyBillString[1]));

		log.info("TC_02_STEP_: Kiem tra tai khoan nguon");
		String account = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SOURCE_ACCOUNT);
		verifyEquals(account, sourceAccount.account);

		log.info("TC_02_STEP_: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount(Shopping_Online_Data.AMOUNT_PRICE).replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", calulatorMoney + " VND");

		log.info("TC_02_STEP_: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText(Shopping_Online_Data.SMS_OTP);

		log.info("TC_02_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("TC_01_Step_23: Nhap OTP");
		shopping.inputToDynamicSmartOTP(newOTP, "com.VCB:id/otp");

		log.info("TC_01_Step_24: Click tiep tuc");
		shopping.clickToDynamicButton(TittleData.CONTINUE_BTN);
		shopping.clickToDynamicButton(TittleData.CONTINUE_BTN);


		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.SUCCESS_TRANSFER);

		soDuThuc = soDuTK - moneyConfirm;

		log.info("TC_02_STEP_: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SUPPLIER), Shopping_Online_Data.VNSHOP);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);

		maGiaodich = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_TRANSFER);

		log.info("TC_02_STEP_: thuc hien giao dich moi");
		shopping.clickToDynamicButton(Shopping_Online_Data.NEW_TRANSFER);

	}

	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}
