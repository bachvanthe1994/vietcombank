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
import pageObjects.TransactionReportPageObject;
import pageObjects.shopping_online.ShoppingOnlinePageObject;
import vietcombank_test_data.HomePage_Data;
import vietcombank_test_data.HomePage_Data.Home_Text_Elements;
import vietcombank_test_data.TransactionReport_Data;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;
import vnpay.vietcombank.sdk.shopping_online.data.Shopping_Online_Data;

public class Shopping_Online_Flow1 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private ShoppingOnlinePageObject shopping;
	private HomePageObject homePage;
	private TransactionReportPageObject transReport;
	String transferTime;
	List<String> listActual;
	String codeBill;
	double soDuThuc = 0;
	String codeTransfer = "";
	int indexHang = 0;

	long amount, amountStart, feeView, amountView, amountAfter = 0;
	String moneyConfirm;
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
		homePage.scrollDownToText(driver, HomePage_Data.Home_Text_Elements.VIETCOMBANK_2020);
		homePage.scrollIDownOneTime(driver);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.TITLE_SHOPPING_ONLINE);
		
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		shopping = PageFactoryManager.getShoppingOnlinePageObject(driver);
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		shopping.sleep(driver, 5000);
	}

	@Parameters({ "otp" })
	@Test
	public void TC_01_ChonMuaMotSanPhamThanhToanOTPKhongChonKhuyenMai(String otp) {
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

		log.info("TC_02_STEP_7: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AVAIABLE_BALANCE).replace("VND", "").replace(",", ""));

		log.info("TC_02_STEP_7: lay thong tin ma don hang");
		codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

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
		moneyConfirm = money[0];
		verifyEquals(moneyConfirm + " VND", (long) calulatorMoney + " VND");

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

		soDuThuc = soDuTK - Double.parseDouble(moneyConfirm);

		log.info("TC_01_STEP_19: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SUPPLIER), Shopping_Online_Data.VNSHOP);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);

		codeTransfer = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_TRANSFER);

		transferTime = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTime").split(" ")[3];

		log.info("TC_01_STEP_20: thuc hien giao dich moi");
		shopping.clickToDynamicButton(Shopping_Online_Data.NEW_TRANSFER);

		log.info("TC_01_STEP_21: Click btn back");
		shopping.clickToDynamicCart("1", "0");

	}

	@Test
	public void TC_02_BaoCaoGiaoDichChonMuaMotSanPhamThanhToanOTPKhongChonKhuyenMai() {

		log.info("TC_02_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		log.info("TC_02_3: Click Bao Cao giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_3: Click Chon loai bao cao");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_02_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.VNSHOP_PAYMENT);

		log.info("TC_02: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_02: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(6);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_02: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_02: Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_02_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_02: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney").replace(",", ""), ("- " + convertAvailableBalanceCurrentcyOrFeeToLong(moneyConfirm) + " VND"));

		log.info("TC_02: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_02: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_02: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), codeTransfer);

		log.info("TC_02: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_02: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CODE_ORDER), codeBill);

		log.info("TC_02: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), ReportTitle.VNSHOP_PAYMENT);

		log.info("TC_02: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(ReportTitle.VNSHOP));

		log.info("TC_02: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TTC_02: Chon button back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_Click button home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Parameters({ "otp" })
	@Test
	public void TC_03_ChonMuaNhieuSanPhamThanhToanOTPKhongChonKhuyenMai(String otp) {

		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, HomePage_Data.Home_Text_Elements.VIETCOMBANK_2020);
		homePage.scrollIDownOneTime(driver);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.TITLE_SHOPPING_ONLINE);
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		shopping = PageFactoryManager.getShoppingOnlinePageObject(driver);

		log.info("TC_03_STEP_: Them vao gio hang");
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

		log.info("TC_03_STEP_: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

		log.info("TC_03_STEP_: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("3");

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

			log.info("TC__03_STEP_: chon tinh thanh pho");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.CAPITAL);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.HANOI_CITY);

			log.info("TC_03_STEP_: chon tinh quan huyen");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.DISTRICT);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.BA_DINH_DISTRICT);

			log.info("TC__03_STEP_: chon tinh xa phuong");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.WARD);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.WARD_CONG_VI);

			log.info("TC_03_STEP_: dia chi cu the");
			shopping.inputToDynamicInfo(Shopping_Online_Data.ADDRESS_NUMBER, Shopping_Online_Data.ADDRESS_DETAIL);

			log.info("TC_03_STEP_: chon hoan tat");
			shopping.clickToDynamicButton(Shopping_Online_Data.COMPLETE);

			log.info("TC_03_STEP_: click thanh toan");
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
			log.info("TC__03_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_03_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("3");

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

			log.info("TC_03_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_03_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_03_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("3");

			log.info("TC_03_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		}



		log.info("TC_03_STEP_: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		sourceAccount = shopping.chooseSourceAccount(driver, Double.parseDouble(Constants.THREE_BILLION_VND), Constants.VND_CURRENCY);

		log.info("TC_03_STEP_: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AVAIABLE_BALANCE).replace("VND", "").replace(",", ""));

		log.info("TC_03_STEP_: lay thong tin ma don hang");
		codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("TC_03_STEP_: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.FEE_SHIPPING).split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_03_STEP_: verify fee Ship");
		verifyEquals(feeShippingD, fee);

		log.info("TC_03_STEP_: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.DISCOUNT).split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", "").replace("", ""));

		log.info("TC_03_STEP_: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.PRICE_ORDER).split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoneyBill, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

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
		 moneyConfirm = money[0];
		verifyEquals(moneyConfirm + " VND", (long)calulatorMoney + " VND");

		log.info("TC_03_STEP_: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText(Shopping_Online_Data.SMS_OTP);

		log.info("TC_03_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("TC_03_STEP_: dien otp");
		shopping.inputToDynamicOtp(otp, Shopping_Online_Data.CONTINUE);

		log.info("TC_03_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.SUCCESS_TRANSFER);

		soDuThuc = soDuTK - Double.parseDouble(moneyConfirm);

		log.info("TC_03_STEP_: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SUPPLIER), Shopping_Online_Data.VNSHOP);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);

		codeTransfer = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_TRANSFER);
		
		transferTime = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTime").split(" ")[3];


		log.info("TC_03_STEP_: thuc hien giao dich moi");
		shopping.clickToDynamicButton(Shopping_Online_Data.NEW_TRANSFER);

		log.info("TC_03_STEP_21: Click btn back");
		shopping.clickToDynamicCart("1", "0");

	}

	@Test
	public void TC_04_BaoCaoGiaoDichChonMuaNhieuSanPhamThanhToanOTPKhongChonKhuyenMai() {

		log.info("TC_04_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		log.info("TC_04_3: Click Bao Cao giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_3: Click Chon loai bao cao");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_04_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.VNSHOP_PAYMENT);

		log.info("TC_04: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_04: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(6);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_04: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_04: Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_04_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC__04: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney").replace(",", ""), ("- " + convertAvailableBalanceCurrentcyOrFeeToLong(moneyConfirm) + " VND"));

		log.info("TC_04: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_04: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_04: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), codeTransfer);

		log.info("TC_04: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_04: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CODE_ORDER), codeBill);

		log.info("TC_04: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), ReportTitle.VNSHOP_PAYMENT);

		log.info("TC_02: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(ReportTitle.VNSHOP));

		log.info("TC_04: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TTC_04: Chon button back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_Click button home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Parameters({ "pass" })
	@Test
	public void TC_05_ChonMuaMotSanPhamThanhToanMKKhongChonKhuyenMai(String pass) {

		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, HomePage_Data.Home_Text_Elements.VIETCOMBANK_2020);
		homePage.scrollIDownOneTime(driver);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.TITLE_SHOPPING_ONLINE);
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		shopping = PageFactoryManager.getShoppingOnlinePageObject(driver);

		log.info("TC_05_STEP_: Them vao gio hang");
		shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);

		List<String> listProduct = shopping.getTextInListElementsProduct("đ");

		for (int i = 3; i < listProduct.size(); i++) {
			shopping.clickToDynamicView(listProduct.get(i));
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

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.PRODUCT_OUT)) {
			shopping.clickToDynamicButton(Shopping_Online_Data.BACK_TO_CART);

			shopping.clickToDynamicCart("2", "0");
			shopping.clickToDynamicButton(Shopping_Online_Data.BUY_NOW);

			shopping.clickToDynamicCategories(Shopping_Online_Data.VIEW_ALL);

			for (int j = indexHang + 1; j < listProduct.size(); j++) {
				shopping.clickToDynamicView(listProduct.get(j));
				if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
					log.info("TC_05_STEP_5: click Back");
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

			log.info("TC_05_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_05_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_05_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_05_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		}


		log.info("TC_05_STEP_: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		sourceAccount = shopping.chooseSourceAccount(driver, Double.parseDouble(Constants.THREE_BILLION_VND), Constants.VND_CURRENCY);

		log.info("TC_05_STEP_: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AVAIABLE_BALANCE).replace("VND", "").replace(",", ""));

		log.info("TC_05_STEP_: lay thong tin ma don hang");
		codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("TC_05_STEP_: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.FEE_SHIPPING).split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_05_STEP_: verify fee Ship");
		verifyEquals(feeShippingD, fee);

		log.info("TC_05_STEP_: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.DISCOUNT).split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", "").replace("", ""));

		log.info("TC_05_STEP_: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.PRICE_ORDER).split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoneyBill, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

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
		moneyConfirm = money[0];
		verifyEquals(moneyConfirm + " VND", (long)calulatorMoney + " VND");

		log.info("TC_05_STEP_: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText(Shopping_Online_Data.PASSWORD_LOGIN);

		log.info("TC_05_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("TC_05_STEP_: dien otp");
		shopping.inputToDynamicPopupPasswordInput(pass, Shopping_Online_Data.CONTINUE);

		log.info("TC_05_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.SUCCESS_TRANSFER);

		soDuThuc = soDuTK - Double.parseDouble(moneyConfirm);

		log.info("TC_05_STEP_: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SUPPLIER), Shopping_Online_Data.VNSHOP);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);

		codeTransfer = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_TRANSFER);
		
		transferTime = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTime").split(" ")[3];


		log.info("TC_05_STEP_: thuc hien giao dich moi");
		shopping.clickToDynamicButton(Shopping_Online_Data.NEW_TRANSFER);
		shopping.clickToDynamicCart("1", "0");

	}

	@Test
	public void TC_06_BaoCaoGiaoDichChonMuaMotSanPhamThanhToanMKKhongChonKhuyenMai() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_06_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		log.info("TC_06_3: Click Bao Cao giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_6_3: Click Chon loai bao cao");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_06_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.VNSHOP_PAYMENT);

		log.info("TC_06: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_06: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_06: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(6);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_06: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_06 Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_06_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC__06: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney").replace(",", ""), ("- " + convertAvailableBalanceCurrentcyOrFeeToLong(moneyConfirm) + " VND"));

		log.info("TC_06: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_06: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_06: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), codeTransfer);

		log.info("TC_06: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_06: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CODE_ORDER), codeBill);

		log.info("TC_06: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), ReportTitle.VNSHOP_PAYMENT);

		log.info("TC_06: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(ReportTitle.VNSHOP));

		log.info("TC_06: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TTC_06: Chon button back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_06_Click button home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Parameters({ "pass" })
	@Test
	public void TC_07_ChonMuaNhieuSanPhamThanhToanMKKhongChonKhuyenMai(String pass) {
		
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, HomePage_Data.Home_Text_Elements.VIETCOMBANK_2020);
		homePage.scrollIDownOneTime(driver);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.TITLE_SHOPPING_ONLINE);
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		shopping = PageFactoryManager.getShoppingOnlinePageObject(driver);
		
		log.info("TC_07_STEP_: Them vao gio hang");
		shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);

		List<String> listProduct = shopping.getTextInListElementsProduct("đ");

		for (int i = 3; i < listProduct.size(); i++) {
			shopping.clickToDynamicView(listProduct.get(i));
			indexHang = i;
			if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
				log.info("TC_07_STEP_: click Back");
				shopping.clickToDynamicCart("1", "0");
				continue;
			} else {
				break;
			}
		}

		log.info("TC_07_STEP_: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

		log.info("TC_07_STEP_: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("3");

		log.info("TC_07_STEP_: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
		log.info("TC_07_STEP_: click dat hang");
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

		log.info("TC_07_STEP_: click thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.ARE_YOU_HAVE_ADDRESS)) {

			log.info("TC_07_STEP_: click the moi");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_NEW);

			log.info("TC_07_STEP_: Hien thi man hinh them moi dia chi");
			shopping.isDynamicTextInfoDisplayed(Shopping_Online_Data.ADD_NEW_ADDRESS);

			log.info("TC_07_STEP_: nhap ten");
			shopping.inputToDynamicInfo(Shopping_Online_Data.USER_NAME, Shopping_Online_Data.NAME_RECIEPT);

			log.info("TC_07_STEP_: nhap so dien thoai");
			shopping.inputToDynamicInfo(Shopping_Online_Data.PHONE_NUMBER, Shopping_Online_Data.RECIPT_PHONE_NUMBER);

			log.info("TC_07_STEP_: chon tinh thanh pho");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.CAPITAL);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.HANOI_CITY);

			log.info("TC_07_STEP_: chon tinh quan huyen");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.DISTRICT);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.BA_DINH_DISTRICT);

			log.info("TC_07_STEP_: chon tinh xa phuong");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.WARD);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.WARD_CONG_VI);

			log.info("TC_07_STEP_: dia chi cu the");
			shopping.inputToDynamicInfo(Shopping_Online_Data.ADDRESS_NUMBER, Shopping_Online_Data.ADDRESS_DETAIL);

			log.info("TC_07_STEP_: chon hoan tat");
			shopping.clickToDynamicButton(Shopping_Online_Data.COMPLETE);

			log.info("TC_07_STEP_: click thanh toan");
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
					log.info("TC__07_STEP_: click Back");
					shopping.clickToDynamicCart("1", "0");
					continue;
				} else {
					break;
				}
			}
			log.info("TC_07_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_07_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("3");

			log.info("TC_07_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
			log.info("TC_07_STEP_: click dat hang");
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

			log.info("TC_07_STEP_: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

			log.info("TC_07_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_07_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_07_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("3");

			log.info("TC_07_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		}


		log.info("TC_07_STEP_: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		sourceAccount = shopping.chooseSourceAccount(driver, Double.parseDouble(Constants.THREE_BILLION_VND), Constants.VND_CURRENCY);

		log.info("TC_07_STEP_: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AVAIABLE_BALANCE).replace("VND", "").replace(",", ""));

		log.info("TC_07_STEP_: lay thong tin ma don hang");
		codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("TC_07_STEP_: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.FEE_SHIPPING).split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_07_STEP_: verify fee Ship");
		verifyEquals(feeShippingD, fee);

		log.info("TC_07_STEP_: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.DISCOUNT).split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", "").replace("", ""));

		log.info("TC_07_STEP_: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.PRICE_ORDER).split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoneyBill, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("TC_07__STEP_: Chon thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		log.info("TC_07_STEP_: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.CONFIRM_INFO);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);
		verifyTrue(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AMOUNT_PRICE).contains(totalMoneyBillString[1]));

		log.info("TC_07__STEP_: Kiem tra tai khoan nguon");
		String account = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SOURCE_ACCOUNT);
		verifyEquals(account, sourceAccount.account);

		log.info("TC_07_STEP_: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount(Shopping_Online_Data.AMOUNT_PRICE).replace(",", "")).split(" ");
		moneyConfirm = money[0];
		verifyEquals(moneyConfirm + " VND", (long)calulatorMoney + " VND");

		log.info("TC_07_STEP_: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText(Shopping_Online_Data.PASSWORD_LOGIN);

		log.info("TC_07_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("TC_07__STEP_: dien otp");
		shopping.inputToDynamicPopupPasswordInput(pass, Shopping_Online_Data.CONTINUE);

		log.info("TC_07_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.SUCCESS_TRANSFER);

		soDuThuc = soDuTK - Double.parseDouble(moneyConfirm);

		log.info("TC_07_STEP_: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SUPPLIER), Shopping_Online_Data.VNSHOP);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);

		codeTransfer = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_TRANSFER);
		
		transferTime = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTime").split(" ")[3];


		log.info("TC_07_STEP_: thuc hien giao dich moi");
		shopping.clickToDynamicButton(Shopping_Online_Data.NEW_TRANSFER);
		
		log.info("TC_01_STEP_21: Click btn back");
		shopping.clickToDynamicCart("1", "0");


	}
	
	@Test
	public void TC_08_BaoCaoGiaoDichChonMuaNhieuSanPhamThanhToanMKKhongChonKhuyenMai() {
		
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_08_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		log.info("TC_08_3: Click Bao Cao giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_6_3: Click Chon loai bao cao");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_08_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.VNSHOP_PAYMENT);

		log.info("TC_08: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_08: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_08: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(6);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_08: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_08 Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_08_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC__08: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney").replace(",", ""), ("- " + convertAvailableBalanceCurrentcyOrFeeToLong(moneyConfirm) + " VND"));

		log.info("TC_08: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_08: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_08: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), codeTransfer);

		log.info("TC_08: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_08: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CODE_ORDER), codeBill);

		log.info("TC_08: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), ReportTitle.VNSHOP_PAYMENT);

		log.info("TC_08: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(ReportTitle.VNSHOP));

		log.info("TC_08: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TTC_08: Chon button back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_08_Click button home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Parameters({ "otp" })
	@Test
	public void TC_09_ChonMuaMotSanPhamCoKhuyenMaiThanhToanOTP(String otp) {
		
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, HomePage_Data.Home_Text_Elements.VIETCOMBANK_2020);
		homePage.scrollIDownOneTime(driver);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.TITLE_SHOPPING_ONLINE);
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		shopping = PageFactoryManager.getShoppingOnlinePageObject(driver);

		
		log.info("TC_09_STEP_: Them vao gio hang");
		shopping.scrollIDownOneTime(driver);
		shopping.scrollDownToConatainText("%");
		List<String> listProduct = shopping.getTextInListElementsProduct("%");

		for (int i = 0; i < listProduct.size(); i++) {
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

		log.info("TC_09_STEP_: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

		log.info("TC_09_STEP_: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("1");

		log.info("TC_09_STEP_: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
		log.info("TC_09_STEP_: click dat hang");
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

		log.info("TC_09_STEP_: click thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.ARE_YOU_HAVE_ADDRESS)) {

			log.info("TC_09_STEP_: click the moi");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_NEW);

			log.info("TC_09_STEP_: Hien thi man hinh them moi dia chi");
			shopping.isDynamicTextInfoDisplayed(Shopping_Online_Data.ADD_NEW_ADDRESS);

			log.info("TC_09_STEP_: nhap ten");
			shopping.inputToDynamicInfo(Shopping_Online_Data.USER_NAME, Shopping_Online_Data.NAME_RECIEPT);

			log.info("TC_09_STEP_: nhap so dien thoai");
			shopping.inputToDynamicInfo(Shopping_Online_Data.PHONE_NUMBER, Shopping_Online_Data.RECIPT_PHONE_NUMBER);

			log.info("TC_09_STEP_: chon tinh thanh pho");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.CAPITAL);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.HANOI_CITY);

			log.info("TC_09_STEP_: chon tinh quan huyen");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.DISTRICT);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.BA_DINH_DISTRICT);

			log.info("TC_09_STEP_: chon tinh xa phuong");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.WARD);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.WARD_CONG_VI);

			log.info("TC_09_STEP_: dia chi cu the");
			shopping.inputToDynamicInfo(Shopping_Online_Data.ADDRESS_NUMBER, Shopping_Online_Data.ADDRESS_DETAIL);

			log.info("TC_09_STEP_: chon hoan tat");
			shopping.clickToDynamicButton(Shopping_Online_Data.COMPLETE);

			log.info("TC_09_STEP_: click thanh toan");
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
					log.info("TC_09_STEP_: click Back");
					shopping.clickToDynamicCart("1", "0");
					continue;
				} else {
					break;
				}
			}
			log.info("TC_09_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_09_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_09_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
			log.info("TC_09_STEP_: click dat hang");
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

			log.info("TC_09_STEP_: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

			log.info("TC_09_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_09_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_09_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("1");

			log.info("TC_09_STEP_5: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		}



		log.info("TC_09_STEP_: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		sourceAccount = shopping.chooseSourceAccount(driver, Double.parseDouble(Constants.THREE_BILLION_VND), Constants.VND_CURRENCY);

		log.info("TC_09_STEP_: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AVAIABLE_BALANCE).replace("VND", "").replace(",", ""));

		log.info("TC_09_STEP_: lay thong tin ma don hang");
		codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("TC_09_STEP_: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.FEE_SHIPPING).split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_09_STEP_: verify fee Ship");
		verifyEquals(feeShippingD, fee);

		log.info("TC_09_STEP_: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.DISCOUNT).split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", "").replace("", ""));

		log.info("TC_09_STEP_: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.PRICE_ORDER).split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoneyBill, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("TC_09_STEP_: Chon thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		log.info("TC_09_STEP_: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.CONFIRM_INFO);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);
		verifyTrue(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AMOUNT_PRICE).contains(totalMoneyBillString[1]));

		log.info("TC_09_STEP_: Kiem tra tai khoan nguon");
		String account = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SOURCE_ACCOUNT);
		verifyEquals(account, sourceAccount.account);

		log.info("TC_09_STEP_: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount(Shopping_Online_Data.AMOUNT_PRICE).replace(",", "")).split(" ");
		moneyConfirm = money[0];
		verifyEquals(moneyConfirm + " VND", (long)calulatorMoney + " VND");

		log.info("TC_09_STEP_: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText(Shopping_Online_Data.SMS_OTP);

		log.info("TC_09_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("TC_09_STEP_: dien otp");
		shopping.inputToDynamicOtp(otp, Shopping_Online_Data.CONTINUE);

		log.info("TC_09_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.SUCCESS_TRANSFER);

		soDuThuc = soDuTK - Double.parseDouble(moneyConfirm);

		log.info("TC_09_STEP_19: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SUPPLIER), Shopping_Online_Data.VNSHOP);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);

		codeTransfer = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_TRANSFER);
		
		transferTime = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTime").split(" ")[3];


		log.info("TC_09_STEP_: thuc hien giao dich moi");
		shopping.clickToDynamicButton(Shopping_Online_Data.NEW_TRANSFER);
		
		log.info("TC_01_STEP_21: Click btn back");
		shopping.clickToDynamicCart("1", "0");


	}

	@Test
	public void TC_10_BaoCaoGiaoDichChonMuaMotSanPhamCoKhuyenMaiThanhToanOTP() {
		homePage = PageFactoryManager.getHomePageObject(driver);

		log.info("TC_10_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		log.info("TC_10_3: Click Bao Cao giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_10_3: Click Chon loai bao cao");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_10_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.VNSHOP_PAYMENT);

		log.info("TC_10_: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_10_: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_10_: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(6);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_10_: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_10_ Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_10_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10_: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC__10_: Check so tien chuyen");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney").replace(",", ""), ("- " + convertAvailableBalanceCurrentcyOrFeeToLong(moneyConfirm) + " VND"));

		log.info("TC_10_: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_10_: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_10_: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), codeTransfer);

		log.info("TC_10_: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_10_: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CODE_ORDER), codeBill);

		log.info("TC_10_: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), ReportTitle.VNSHOP_PAYMENT);

		log.info("TC_10_: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(ReportTitle.VNSHOP));

		log.info("TC_10_: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TTC_10_: Chon button back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_10_Click button home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}
	
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
