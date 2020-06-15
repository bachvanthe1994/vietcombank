package vnpay.vietcombank.sdk.shopping_online;

import java.io.IOException;
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
import pageObjects.shopping_online.ShoppingOnlinePageObject;
import vnpay.vietcombank.sdk.shopping_online.data.Shopping_Online_Data;

public class Shopping_Online_Flow2 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private ShoppingOnlinePageObject shopping;
	private HomePageObject homePage;
	String transferTime;
	String transactionNumber;
	List<String> listActual;
	double soDuThuc = 0;
	String maGiaodich = "";
	int indexHang = 0;

	long amount, amountStart, feeView, amountView, amountAfter = 0;
	SourceAccountModel sourceAccount = new SourceAccountModel();

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
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
		homePage.scrollDownToText(driver, "© 2019 Vietcombank");
		homePage.scrollIDownOneTime(driver);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Mua sắm trực tuyến - VNPAY Shopping");
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		shopping = PageFactoryManager.getShoppingOnlinePageObject(driver);
		shopping.sleep(driver, 5000);

	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_ChonMuaMotSanPhamThanhToanOTPKhongChonKhuyenMai_NhapMAGG(String otp) {
		log.info("TC_01_STEP_2: Them vao gio hang");

		shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);
		List<String> listProduct = shopping.getTextInListElementsProduct("đ");
		for (int i = 2; i < listProduct.size(); i++) {
			shopping.clickToDynamicTextContains(listProduct.get(i));
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

		log.info("TC_01_STEP: Chon ma giam gia");
		shopping.clickToDynamicCategories(Shopping_Online_Data.CHOICE_VOUCHER);

		log.info("TC_01_STEP: Click ap dung");
		shopping.TabtoElementByPoint(832, 1332);

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

		while (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.PRODUCT_OUT)) {
			shopping.clickToDynamicButton(Shopping_Online_Data.BACK_TO_CART);

			shopping.TabtoElementByPoint(1002, 924);
			shopping.clickToDynamicButton(Shopping_Online_Data.BUY_NOW);

			shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);

			for (int j = indexHang + 1; j < listProduct.size(); j++) {
				shopping.clickToDynamicTextContains(listProduct.get(j));
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

			log.info("TC_01_STEP: Chon ma giam gia");
			shopping.clickToDynamicCategories(Shopping_Online_Data.CHOICE_VOUCHER);

			log.info("TC_01_STEP: Click ap dung");
			shopping.TabtoElementByPoint(832, 1332);

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

			log.info("TC_01_STEP_4: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_01_STEP_4: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_01_STEP: Click ap dung");
			shopping.TabtoElementByPoint(832, 1332);

			log.info("TC_01_STEP_3: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_01_STEP_5: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		}

		log.info("TC_01_STEP_6: click thanh toan ngay");
		shopping.clickToDynamicButton(Shopping_Online_Data.ACCEPT);

		log.info("TC_01_STEP_7: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		sourceAccount = shopping.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_01_STEP_7: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AVAIABLE_BALANCE).replace("VND", "").replace(",", ""));

		log.info("TC_01_STEP_7: lay thong tin ma don hang");
		String codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("TC_01_STEP_9: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.FEE_SHIPPING).split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_01_STEP_9: verify fee Ship");
		verifyEquals(feeShippingD, fee);

	
		log.info("TC_01_STEP_11: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.TOTAL).split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoney, (long) fee, 0);
		verifyEquals(calulatorMoney, totalMoneyBill);

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
		verifyEquals(moneyConfirm + " VND", totalMoneyBill + " VND");

		log.info("TC_01_STEP_16: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText(Shopping_Online_Data.SMS_OTP);

		log.info("TC_01_STEP_17: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("TC_01_STEP_18: dien otp");
		shopping.inputToDynamicOtp(otp, Shopping_Online_Data.CONTINUE);

		log.info("TC_01_STEP_19: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

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
	public void TC_02_ChonMuaNhieuSanPhamThanhToanOTPNhap_Ma_Khuyen_Mai(String otp) {

		log.info("TC_02_STEP: Them vao gio hang");

		shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);
		List<String> listProduct = shopping.getTextInListElementsProduct("đ");
		for (int i = 2; i < listProduct.size(); i++) {
			shopping.clickToDynamicTextContains(listProduct.get(i));
			indexHang = i;
			if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
				log.info("TC_01_STEP_5: click Back");
				shopping.clickToDynamicCart("1", "0");
				continue;
			} else {
				break;
			}
		}

		log.info("TC_02_STEP: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

		log.info("TC_02_STEP: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("3");

		log.info("TC_02_STEP: Chon ma giam gia");
		shopping.clickToDynamicCategories(Shopping_Online_Data.CHOICE_VOUCHER);

		log.info("TC_02_STEP: Click ap dung");
		shopping.TabtoElementByPoint(832, 1332);

		log.info("TC_02_STEP: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

		log.info("TC_02_STEP: click dat hang");
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

		log.info("TC_02_STEP: click thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.ARE_YOU_HAVE_ADDRESS)) {

			log.info("TC_02_STEP: click the moi");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_NEW);

			log.info("TC_02_STEP: Hien thi man hinh them moi dia chi");
			shopping.isDynamicTextInfoDisplayed(Shopping_Online_Data.ADD_NEW_ADDRESS);

			log.info("TC_02_STEP: nhap ten");
			shopping.inputToDynamicInfo(Shopping_Online_Data.USER_NAME, Shopping_Online_Data.NAME_RECIEPT);

			log.info("TC_02_STEP: nhap so dien thoai");
			shopping.inputToDynamicInfo(Shopping_Online_Data.PHONE_NUMBER, Shopping_Online_Data.RECIPT_PHONE_NUMBER);

			log.info("TC_02_STEP: chon tinh thanh pho");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.CAPITAL);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.HANOI_CITY);

			log.info("TC_02_STEP: chon tinh quan huyen");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.DISTRICT);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.BA_DINH_DISTRICT);

			log.info("TC_02_STEP: chon tinh xa phuong");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.WARD);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.WARD_CONG_VI);

			log.info("TC_02_STEP: dia chi cu the");
			shopping.inputToDynamicInfo(Shopping_Online_Data.ADDRESS_NUMBER, Shopping_Online_Data.ADDRESS_DETAIL);

			log.info("TC_02_STEP: chon hoan tat");
			shopping.clickToDynamicButton(Shopping_Online_Data.COMPLETE);

			log.info("TC_02_STEP: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);
		}

		while (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.PRODUCT_OUT)) {
			shopping.clickToDynamicButton(Shopping_Online_Data.BACK_TO_CART);

			shopping.TabtoElementByPoint(1002, 924);
			shopping.clickToDynamicButton(Shopping_Online_Data.BUY_NOW);

			shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);

			for (int j = indexHang + 1; j < listProduct.size(); j++) {
				shopping.clickToDynamicTextContains(listProduct.get(j));
				if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
					log.info("TC_02_STEP: click Back");
					shopping.clickToDynamicCart("1", "0");
					continue;
				} else {
					break;
				}
			}
			log.info("TC_02_STEP: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_02_STEP: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("3");

			log.info("TC_02_STEP: Chon ma giam gia");
			shopping.clickToDynamicCategories(Shopping_Online_Data.CHOICE_VOUCHER);

			log.info("TC_02_STEP: Click ap dung");
			shopping.TabtoElementByPoint(832, 1332);

			log.info("TC_02_STEP: lay tong tien can thanh toan");
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

			log.info("TC_02_STEP: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

			log.info("TC_02_STEP: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_02_STEP: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("3");

			log.info("TC_02_STEP: Click ap dung");
			shopping.TabtoElementByPoint(832, 1332);

			log.info("TC_02_STEP: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_02_STEP: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		}

		log.info("TC_02_STEP: click thanh toan ngay");
		shopping.clickToDynamicButton(Shopping_Online_Data.ACCEPT);

		log.info("TC_02_STEP: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		sourceAccount = shopping.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_02_STEP: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AVAIABLE_BALANCE).replace("VND", "").replace(",", ""));

		log.info("TC_02_STEP: lay thong tin ma don hang");
		String codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("TC_02_STEP: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.FEE_SHIPPING).split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_02_STEP: verify fee Ship");
		verifyEquals(feeShippingD, fee);

		log.info("TC_02_STEP: giam gia");

		// tong tien can thanh toan
		log.info("TC_02_STEP: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.TOTAL).split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoney, (long) fee, 0);
		verifyEquals(calulatorMoney, totalMoneyBill);

		log.info("TC_02_STEP: Chon thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		log.info("TC_02_STEP: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.CONFIRM_INFO);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);
		verifyTrue(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AMOUNT_PRICE).contains(totalMoneyBillString[1]));

		log.info("TC_02_STEP: Kiem tra tai khoan nguon");
		String account = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SOURCE_ACCOUNT);
		verifyEquals(account, sourceAccount.account);

		log.info("TC_02_STEP: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount(Shopping_Online_Data.AMOUNT_PRICE).replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", totalMoneyBill + " VND");

		log.info("TC_02_STEP: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText(Shopping_Online_Data.SMS_OTP);

		log.info("TC_02_STEP: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("TC_02_STEP: dien otp");
		shopping.inputToDynamicOtp(otp, Shopping_Online_Data.CONTINUE);

		log.info("TC_02_STEP: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.SUCCESS_TRANSFER);

		soDuThuc = soDuTK - moneyConfirm;

		log.info("TC_02_STEP: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SUPPLIER), Shopping_Online_Data.VNSHOP);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);

		maGiaodich = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_TRANSFER);

		log.info("TC_02_STEP: thuc hien giao dich moi");
		shopping.clickToDynamicButton(Shopping_Online_Data.NEW_TRANSFER);

	}

	@Parameters({ "pass" })
	@Test
	public void TC_03_ChonMuaMotSanPhamThanhToa_Nhap_MaKhuyenMai(String pass) {

		log.info("TC_03_STEP_: Them vao gio hang");

		shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);
		List<String> listProduct = shopping.getTextInListElementsProduct("đ");
		for (int i = 2; i < listProduct.size(); i++) {
			shopping.clickToDynamicTextContains(listProduct.get(i));
			indexHang = i;
			if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
				log.info("TC_03_STEP_: click Back");
				shopping.clickToDynamicCart("1", "0");
				continue;
			} else {
				break;
			}
		}

		log.info("TC_03_STEP_: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

		log.info("TC_03_STEP_: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("1");

		log.info("TC_03_STEP_: Chon ma giam gia");
		shopping.clickToDynamicCategories(Shopping_Online_Data.CHOICE_VOUCHER);

		log.info("TC_03_STEP_: Click ap dung");
		shopping.TabtoElementByPoint(832, 1332);

		log.info("TC_03_STEP_: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

		log.info("TC_03_STEP_: click dat hang");
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

		log.info("TC_03_STEP_: click thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.ARE_YOU_HAVE_ADDRESS)) {

			log.info("TC_03_STEP_: click the moi");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_NEW);

			log.info("TC_03_STEP_: Hien thi man hinh them moi dia chi");
			shopping.isDynamicTextInfoDisplayed(Shopping_Online_Data.ADD_NEW_ADDRESS);

			log.info("TC_03_STEP_: nhap ten");
			shopping.inputToDynamicInfo(Shopping_Online_Data.USER_NAME, Shopping_Online_Data.NAME_RECIEPT);

			log.info("TC_03_STEP_: nhap so dien thoai");
			shopping.inputToDynamicInfo(Shopping_Online_Data.PHONE_NUMBER, Shopping_Online_Data.RECIPT_PHONE_NUMBER);

			log.info("TC_03_STEP_: chon tinh thanh pho");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.CAPITAL);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.HANOI_CITY);

			log.info("TC_03_STEP_: chon tinh quan huyen");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.DISTRICT);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.BA_DINH_DISTRICT);

			log.info("TC_03_STEP_: chon tinh xa phuong");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.WARD);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.WARD_CONG_VI);

			log.info("TC_03_STEP_: dia chi cu the");
			shopping.inputToDynamicInfo(Shopping_Online_Data.ADDRESS_NUMBER, Shopping_Online_Data.ADDRESS_DETAIL);

			log.info("TC_03_STEP_: chon hoan tat");
			shopping.clickToDynamicButton(Shopping_Online_Data.COMPLETE);

			log.info("TC_03_STEP_: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);
		}

		while (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.PRODUCT_OUT)) {
			shopping.clickToDynamicButton(Shopping_Online_Data.BACK_TO_CART);

			shopping.TabtoElementByPoint(1002, 924);
			shopping.clickToDynamicButton(Shopping_Online_Data.BUY_NOW);

			shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);

			for (int j = indexHang + 1; j < listProduct.size(); j++) {
				shopping.clickToDynamicTextContains(listProduct.get(j));
				if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
					log.info("TC_03_STEP_: click Back");
					shopping.clickToDynamicCart("1", "0");
					continue;
				} else {
					break;
				}
			}
			log.info("TC_03_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_03_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_01_STEP: Chon ma giam gia");
			shopping.clickToDynamicCategories(Shopping_Online_Data.CHOICE_VOUCHER);

			log.info("TC_03_STEP_: Click ap dung");
			shopping.TabtoElementByPoint(832, 1332);

			log.info("TC_03_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_03_STEP_: click dat hang");
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

			log.info("TC_03_STEP_: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

			log.info("TC_03_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_03_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_03_STEP_: Click ap dung");
			shopping.TabtoElementByPoint(832, 1332);

			log.info("TC_03_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_03_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		}

		log.info("TC_03_STEP_: click thanh toan ngay");
		shopping.clickToDynamicButton(Shopping_Online_Data.ACCEPT);

		log.info("TC_03_STEP_: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		sourceAccount = shopping.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_03_STEP_: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AVAIABLE_BALANCE).replace("VND", "").replace(",", ""));

		log.info("TC_03_STEP_: lay thong tin ma don hang");
		String codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("TC_03_STEP_: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.FEE_SHIPPING).split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_03_STEP_: verify fee Ship");
		verifyEquals(feeShippingD, fee);

		log.info("TC_03_STEP_: giam gia");

		log.info("TC_03_STEP_: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.TOTAL).split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoney, (long) fee, 0);
		verifyEquals(calulatorMoney, totalMoneyBill);

		log.info("TC_03_STEP_: Chon thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		log.info("TC_03_STEP_: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.CONFIRM_INFO);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);
		verifyTrue(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AMOUNT_PRICE).contains(totalMoneyBillString[1]));

		log.info("TC_03_STEP_: Kiem tra tai khoan nguon");
		String account = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SOURCE_ACCOUNT);
		verifyEquals(account, sourceAccount.account);

		log.info("TC_03_STEP_: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount(Shopping_Online_Data.AMOUNT_PRICE).replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", totalMoneyBill + " VND");

		log.info("TC_03_STEP_: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText(Shopping_Online_Data.PASSWORD_LOGIN);

		log.info("TC_03_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("TC_03_STEP_: dien otp");
		shopping.inputToDynamicPopupPasswordInput(pass, Shopping_Online_Data.CONTINUE);

		log.info("TC_03_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.SUCCESS_TRANSFER);

		soDuThuc = soDuTK - moneyConfirm;

		log.info("TC_03_STEP_: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SUPPLIER), Shopping_Online_Data.VNSHOP);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);

		maGiaodich = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_TRANSFER);

		log.info("TC_03_STEP_: thuc hien giao dich moi");
		shopping.clickToDynamicButton(Shopping_Online_Data.NEW_TRANSFER);

	}

	@Parameters({ "pass" })
	@Test

	public void TC_04_ChonMuaNhieuSanPhamThanhToa_Nhap_MaKhuyenMai_Pass(String pass) {
		log.info("Testcase_04: Them vao gio hang");
		shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);
		List<String> listProduct = shopping.getTextInListElementsProduct("đ");
		for (int i = 2; i < listProduct.size(); i++) {
			shopping.clickToDynamicTextContains(listProduct.get(i));
			indexHang = i;
			if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
				log.info("Testcase_03: click Back");
				shopping.clickToDynamicCart("1", "0");
				continue;
			} else {
				break;
			}
		}

		log.info("Testcase_04: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

		log.info("Testcase_04: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("3");

		log.info("Testcase_04: Chon ma giam gia");
		shopping.clickToDynamicTextContains(Shopping_Online_Data.CHOICE_VOUCHER);

		log.info("Testcase_04: Click ap dung");
		shopping.TabtoElementByPoint(832, 1332);

		log.info("Testcase_04: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

		log.info("Testcase_04: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		shopping.scrollDownToViewText(Shopping_Online_Data.SHIPPING_STANDARD);
		String feeShipping = shopping.getDynamicTextFeeShipping(Shopping_Online_Data.SHIPPING_STANDARD);
		double feeShippingD = 0;
		if (feeShipping.equals(Shopping_Online_Data.FREE_SHIPPING)) {
			feeShipping = feeShipping.replace(Shopping_Online_Data.FREE_SHIPPING, "0");
			feeShippingD = 0;

		} else {
			feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
			feeShippingD = Double.parseDouble(feeShipping);

		}
		double totalMoney = tottalMoneyCart + feeShippingD;

		log.info("Testcase_04: click thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.ARE_YOU_HAVE_ADDRESS)) {

			log.info("Testcase_04: click the moi");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_NEW);

			log.info("Testcase_04: Hien thi man hinh them moi dia chi");
			shopping.isDynamicTextInfoDisplayed(Shopping_Online_Data.ADD_NEW_ADDRESS);

			log.info("Testcase_04: nhap ten");
			shopping.inputToDynamicInfo(Shopping_Online_Data.USER_NAME, Shopping_Online_Data.NAME_RECIEPT);

			log.info("Testcase_04: nhap so dien thoai");
			shopping.inputToDynamicInfo(Shopping_Online_Data.PHONE_NUMBER, Shopping_Online_Data.RECIPT_PHONE_NUMBER);

			log.info("Testcase_04: chon tinh thanh pho");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.CAPITAL);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.HANOI_CITY);

			log.info("Testcase_04: chon tinh quan huyen");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.DISTRICT);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.BA_DINH_DISTRICT);

			log.info("Testcase_04: chon tinh xa phuong");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.WARD);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.WARD_CONG_VI);

			log.info("Testcase_04: dia chi cu the");
			shopping.inputToDynamicInfo(Shopping_Online_Data.ADDRESS_NUMBER, Shopping_Online_Data.ADDRESS_DETAIL);

			log.info("Testcase_04: chon hoan tat");
			shopping.clickToDynamicButton(Shopping_Online_Data.COMPLETE);

			log.info("Testcase_04: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);
		}

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.PRODUCT_OUT)) {
			shopping.clickToDynamicButton(Shopping_Online_Data.BACK_TO_CART);

			shopping.clickToDynamicCart("2", "0");
			shopping.clickToDynamicButton(Shopping_Online_Data.BUY_NOW);

			shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);

			for (int j = indexHang + 1; j < listProduct.size(); j++) {
				shopping.clickToDynamicTextContains(listProduct.get(j));
				if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
					log.info("Testcase_04: click Back");
					shopping.clickToDynamicCart("1", "0");
					continue;
				} else {
					break;
				}
			}
			log.info("Testcase_04: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("Testcase_04: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("3");

			log.info("Testcase_04: Chon ma giam gia");
			shopping.clickToDynamicTextContains(Shopping_Online_Data.CHOICE_VOUCHER);

			log.info("Testcase_04: Click ap dung");
			shopping.TabtoElementByPoint(842, 939);

			log.info("Testcase_04: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("Testcase_04: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

			shopping.scrollDownToViewText(Shopping_Online_Data.SHIPPING_STANDARD);
			feeShipping = shopping.getDynamicTextFeeShipping(Shopping_Online_Data.SHIPPING_STANDARD);
			feeShippingD = 0;
			if (feeShipping.equals(Shopping_Online_Data.FREE_SHIPPING)) {
				feeShipping = feeShipping.replace(Shopping_Online_Data.FREE_SHIPPING, "0");
				feeShippingD = 0;

			} else {
				feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
				feeShippingD = Double.parseDouble(feeShipping);

			}
			totalMoney = tottalMoneyCart + feeShippingD;

			log.info("Testcase_04: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

			log.info("Testcase_04: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("Testcase_04: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_01_STEP: Chon ma giam gia");
			shopping.clickToDynamicTextContains(Shopping_Online_Data.CHOICE_VOUCHER);

			log.info("TC_01_STEP: Click ap dung");
			shopping.TabtoElementByPoint(842, 1202);

			log.info("Testcase_04: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("Testcase_04: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		}

		log.info("Testcase_04: click thanh toan ngay");
		shopping.clickToDynamicButton(Shopping_Online_Data.ACCEPT);

		log.info("Testcase_04: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		sourceAccount = shopping.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("Testcase_04: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AVAIABLE_BALANCE).replace("VND", "").replace(",", ""));

		log.info("Testcase_04: lay thong tin ma don hang");
		String codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("Testcase_04: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.FEE_SHIPPING).split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("Testcase_04: verify fee Ship");
		verifyEquals(feeShippingD, fee);

		// tong tien can thanh toan
		log.info("Testcase_04: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.TOTAL).split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoney, (long) fee, 0);
		verifyEquals(calulatorMoney, totalMoneyBill);

		log.info("Testcase_04: Chon thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		log.info("Testcase_04: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.CONFIRM_INFO);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);
		verifyTrue(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AMOUNT_PRICE).contains(totalMoneyBillString[1]));

		log.info("Testcase_04: Kiem tra tai khoan nguon");
		String account = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SOURCE_ACCOUNT);
		verifyEquals(account, sourceAccount.account);

		log.info("Testcase_04: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount(Shopping_Online_Data.AMOUNT_PRICE).replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", totalMoneyBill + " VND");

		log.info("Testcase_04: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText(Shopping_Online_Data.PASSWORD_LOGIN);

		log.info("Testcase_04: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("Testcase_04: dien pass");
		shopping.inputToDynamicPopupPasswordInput(pass, Shopping_Online_Data.CONTINUE);

		log.info("Testcase_04: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.SUCCESS_TRANSFER);

		soDuThuc = soDuTK - moneyConfirm;

		log.info("Testcase_04: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SUPPLIER), Shopping_Online_Data.VNSHOP);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);

		maGiaodich = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_TRANSFER);

		log.info("Testcase_04: thuc hien giao dich moi");
		shopping.clickToDynamicButton(Shopping_Online_Data.NEW_TRANSFER);
	}

	@Parameters({ "otp" })
	@Test
	public void TC_05_ChonMuaMotSanPhamCoKhuyenMaiThanhToanOTP_NhapMaKhuyenMai(String otp) {
		log.info("TC_05_STEP_: Them vao gio hang");
		shopping.scrollIDownOneTime(driver);
		shopping.scrollDownToTextView("%");
		List<String> listProduct = shopping.getTextInListElementsProduct("%");

		for (int i = 0; i < listProduct.size(); i++) {
			shopping.clickToDynamicCategories(listProduct.get(i));
			indexHang = i;
			if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
				log.info("TC_05_STEP_: click Back");
				shopping.clickToDynamicCart("1", "0");
				continue;
			} else {
				break;
			}
		}

		log.info("TC_05_STEP_: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

		log.info("TC_05_STEP_: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("1");

		log.info("TC_05_STEP_: Chon ma giam gia");
		shopping.clickToDynamicCategories(Shopping_Online_Data.CHOICE_VOUCHER);

		log.info("TC_05_STEP_: Click ap dung");
		shopping.TabtoElementByPoint(832, 1332);

		log.info("TC_05_STEP_: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

		log.info("TC_05_STEP_: click dat hang");
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

		log.info("TC_05_STEP_: click thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.ARE_YOU_HAVE_ADDRESS)) {

			log.info("TC_05_STEP_: click the moi");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_NEW);

			log.info("TC_05_STEP_: Hien thi man hinh them moi dia chi");
			shopping.isDynamicTextInfoDisplayed(Shopping_Online_Data.ADD_NEW_ADDRESS);

			log.info("TC_05_STEP_: nhap ten");
			shopping.inputToDynamicInfo(Shopping_Online_Data.USER_NAME, Shopping_Online_Data.NAME_RECIEPT);

			log.info("TC_05_STEP_: nhap so dien thoai");
			shopping.inputToDynamicInfo(Shopping_Online_Data.PHONE_NUMBER, Shopping_Online_Data.RECIPT_PHONE_NUMBER);

			log.info("TC_05_STEP_: chon tinh thanh pho");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.CAPITAL);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.HANOI_CITY);

			log.info("TC_05_STEP_: chon tinh quan huyen");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.DISTRICT);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.BA_DINH_DISTRICT);

			log.info("TC_05_STEP_: chon tinh xa phuong");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.WARD);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.WARD_CONG_VI);

			log.info("TC_05_STEP_: dia chi cu the");
			shopping.inputToDynamicInfo(Shopping_Online_Data.ADDRESS_NUMBER, Shopping_Online_Data.ADDRESS_DETAIL);

			log.info("TC_05_STEP_: chon hoan tat");
			shopping.clickToDynamicButton(Shopping_Online_Data.COMPLETE);

			log.info("TC_05_STEP_: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);
		}

		while (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.PRODUCT_OUT)) {
			shopping.clickToDynamicButton(Shopping_Online_Data.BACK_TO_CART);

			shopping.TabtoElementByPoint(1002, 924);
			shopping.clickToDynamicButton(Shopping_Online_Data.BUY_NOW);

			shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);

			for (int j = indexHang + 1; j < listProduct.size(); j++) {
				shopping.clickToDynamicTextContains(listProduct.get(j));
				if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
					log.info("TC_05_STEP_: click Back");
					shopping.clickToDynamicCart("1", "0");
					continue;
				} else {
					break;
				}
			}
			log.info("TC_05_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_05_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_05_STEP_: Chon ma giam gia");
			shopping.clickToDynamicCategories(Shopping_Online_Data.CHOICE_VOUCHER);

			log.info("TC_05_STEP_: Click ap dung");
			shopping.TabtoElementByPoint(832, 1332);

			log.info("TC_05_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_05_STEP_: click dat hang");
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

			log.info("TC_05_STEP_: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

			log.info("TC_05_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_05_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_05_STEP_: Click ap dung");
			shopping.TabtoElementByPoint(842, 1202);

			log.info("TC_05_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_05_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		}

		log.info("TC_05_STEP_: click thanh toan ngay");
		shopping.clickToDynamicButton(Shopping_Online_Data.ACCEPT);

		log.info("TC_05_STEP_: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		sourceAccount = shopping.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_05_STEP_: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AVAIABLE_BALANCE).replace("VND", "").replace(",", ""));

		log.info("TC_05_STEP_: lay thong tin ma don hang");
		String codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("TC_05_STEP_: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.FEE_SHIPPING).split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_05_STEP_: verify fee Ship");
		verifyEquals(feeShippingD, fee);

		// tong tien can thanh toan
		log.info("TC_05_STEP_: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.TOTAL).split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoney, (long) fee, 0);
		verifyEquals(calulatorMoney, totalMoneyBill);

		log.info("TC_05_STEP_: Chon thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		log.info("TC_05_STEP_: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.CONFIRM_INFO);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);
		verifyTrue(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AMOUNT_PRICE).contains(totalMoneyBillString[1]));

		log.info("TC_05_STEP_: Kiem tra tai khoan nguon");
		String account = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SOURCE_ACCOUNT);
		verifyEquals(account, sourceAccount.account);

		log.info("TC_05_STEP_: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount(Shopping_Online_Data.AMOUNT_PRICE).replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", totalMoneyBill + " VND");

		log.info("TC_05_STEP_: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText(Shopping_Online_Data.SMS_OTP);

		log.info("TC_05_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("TC_05_STEP_: dien otp");
		shopping.inputToDynamicOtp(otp, Shopping_Online_Data.CONTINUE);

		log.info("TC_05_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.SUCCESS_TRANSFER);

		soDuThuc = soDuTK - moneyConfirm;

		log.info("TC_05_STEP_: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SUPPLIER), Shopping_Online_Data.VNSHOP);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);

		maGiaodich = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_TRANSFER);

		log.info("TC_05_STEP_: thuc hien giao dich moi");
		shopping.clickToDynamicButton(Shopping_Online_Data.NEW_TRANSFER);
	}

//	@Parameters({ "otp" })
//	@Test
	public void TC_06_ChonMuaMotSanPhamCoKhuyenMaiThanhToanOTP_NhapMaKhuyenMai_GiaTriLonHonMatHang(String otp) {
		log.info("TC_06_STEP_: Them vao gio hang");
		shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);
		shopping.scrollIDownOneTime(driver);

		shopping.clickToDynamicView(Shopping_Online_Data.SAUSAGE);

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
			log.info("TC_06_STEP_: click Back");
			shopping.clickToDynamicCart("1", "0");

		}

		log.info("TC_06_STEP_: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

		log.info("TC_06_STEP_: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("1");

		log.info("TC_06_STEP_: Chon ma giam gia");
		shopping.clickToDynamicTextContains(Shopping_Online_Data.CHOICE_VOUCHER);

		log.info("TC_06_STEP_: Click ap dung");
		shopping.TabtoElementByPoint(554, 817);

		log.info("TC_06_STEP_: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

		log.info("TC_06_STEP_: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		shopping.scrollDownToViewText(Shopping_Online_Data.SHIPPING_STANDARD);
		String feeShipping = shopping.getDynamicTextFeeShipping(Shopping_Online_Data.SHIPPING_STANDARD);
		double feeShippingD = 0;
		if (feeShipping.equals(Shopping_Online_Data.FREE_SHIPPING)) {
			feeShipping = feeShipping.replace(Shopping_Online_Data.FREE_SHIPPING, "0");
			feeShippingD = 0;

		} else {
			feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
			feeShippingD = Double.parseDouble(feeShipping);

		}
		double totalMoney = tottalMoneyCart + feeShippingD;

		log.info("TC_06_STEP_: click thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.ARE_YOU_HAVE_ADDRESS)) {

			log.info("TC_06_STEP_: click the moi");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_NEW);

			log.info("TC_06_STEP_: Hien thi man hinh them moi dia chi");
			shopping.isDynamicTextInfoDisplayed(Shopping_Online_Data.ADD_NEW_ADDRESS);

			log.info("TC_06_STEP_: nhap ten");
			shopping.inputToDynamicInfo(Shopping_Online_Data.USER_NAME, Shopping_Online_Data.NAME_RECIEPT);

			log.info("TC_06_STEP_: nhap so dien thoai");
			shopping.inputToDynamicInfo(Shopping_Online_Data.PHONE_NUMBER, Shopping_Online_Data.RECIPT_PHONE_NUMBER);

			log.info("TC_06_STEP_: chon tinh thanh pho");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.CAPITAL);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.HANOI_CITY);

			log.info("TC_06_STEP_: chon tinh quan huyen");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.DISTRICT);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.BA_DINH_DISTRICT);

			log.info("TC_06_STEP_: chon tinh xa phuong");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.WARD);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.WARD_CONG_VI);

			log.info("TC_06_STEP_: dia chi cu the");
			shopping.inputToDynamicInfo(Shopping_Online_Data.ADDRESS_NUMBER, Shopping_Online_Data.ADDRESS_DETAIL);

			log.info("TC_06_STEP_: chon hoan tat");
			shopping.clickToDynamicButton(Shopping_Online_Data.COMPLETE);

			log.info("TC_06_STEP_: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);
		}

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.PRODUCT_OUT)) {
			shopping.clickToDynamicButton(Shopping_Online_Data.BACK_TO_CART);

			shopping.clickToDynamicCart("2", "0");
			shopping.clickToDynamicButton(Shopping_Online_Data.BUY_NOW);

			shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);

			shopping.clickToDynamicView(Shopping_Online_Data.SAUSAGE);
			if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
				log.info("TC_01_STEP_5: click Back");
				shopping.clickToDynamicCart("1", "0");

			}

			log.info("TC_06_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_06_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_06_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
			log.info("TC_06_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

			shopping.scrollDownToViewText(Shopping_Online_Data.SHIPPING_STANDARD);
			feeShipping = shopping.getDynamicTextFeeShipping(Shopping_Online_Data.SHIPPING_STANDARD);
			feeShippingD = 0;
			if (feeShipping.equals(Shopping_Online_Data.FREE_SHIPPING)) {
				feeShipping = feeShipping.replace(Shopping_Online_Data.FREE_SHIPPING, "0");
				feeShippingD = 0;

				feeShipping = feeShipping.replace("₫", "").replace("+", "").replace(".", "");
				feeShippingD = Double.parseDouble(feeShipping);

			}
			totalMoney = tottalMoneyCart + feeShippingD;

			log.info("TC_06_STEP_: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

			log.info("TC_06_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_06_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_06_STEP_: Chon ma giam gia");
			shopping.clickToDynamicTextContains(Shopping_Online_Data.CHOICE_VOUCHER);

			log.info("TC_06_STEP_: Click ap dung");
			shopping.TabtoElementByPoint(554, 817);

			log.info("TC_06_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_06_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		}

		log.info("TC_06_STEP_: click thanh toan ngay");
		shopping.clickToDynamicButton(Shopping_Online_Data.ACCEPT);

		log.info("TC_06_STEP_: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		sourceAccount = shopping.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, Constants.VND_CURRENCY);

		log.info("TC_06_STEP_: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AVAIABLE_BALANCE).replace("VND", "").replace(",", ""));

		log.info("TC_06_STEP_: lay thong tin ma don hang");
		String codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("TC_06_STEP_: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.FEE_SHIPPING).split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_06_STEP_: verify fee Ship");
		verifyEquals(feeShippingD, fee);

		// tong tien can thanh toan
		log.info("TC_06_STEP_: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.TOTAL).split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoney, (long) fee, 0);
		verifyEquals(calulatorMoney, totalMoneyBill);

		log.info("TC_06_STEP_: Chon thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		log.info("TC_06_STEP_: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.CONFIRM_INFO);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);
		verifyTrue(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AMOUNT_PRICE).contains(totalMoneyBillString[1]));

		log.info("TC_06_STEP_: Kiem tra tai khoan nguon");
		String account = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SOURCE_ACCOUNT);
		verifyEquals(account, sourceAccount.account);

		log.info("TC_06_STEP_: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount(Shopping_Online_Data.AMOUNT_PRICE).replace(",", "")).split(" ");
		double moneyConfirm = Double.parseDouble(money[0]);
		verifyEquals(moneyConfirm + " VND", calulatorMoney + " VND");

		log.info("TC_06_STEP_: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText(Shopping_Online_Data.SMS_OTP);

		log.info("TC_06_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("TC_06_STEP_: dien pass");
		shopping.inputToDynamicOtp(otp, Shopping_Online_Data.CONTINUE);

		log.info("TC_06_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.SUCCESS_TRANSFER);

		soDuThuc = soDuTK - moneyConfirm;

		log.info("TC_06_STEP_: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SUPPLIER), Shopping_Online_Data.VNSHOP);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);

		maGiaodich = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_TRANSFER);

		log.info("TC_06_STEP_: thuc hien giao dich moi");
		shopping.clickToDynamicButton(Shopping_Online_Data.NEW_TRANSFER);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}
