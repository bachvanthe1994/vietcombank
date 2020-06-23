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
import pageObjects.TransactionReportPageObject;
import pageObjects.shopping_online.ShoppingOnlinePageObject;
import vietcombank_test_data.HomePage_Data;
import vietcombank_test_data.HomePage_Data.Home_Text_Elements;
import vietcombank_test_data.Notify_Management_Data.Notify_Text;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransactionReport_Data;
import vietcombank_test_data.TransferMoneyInVCB_Data.TittleData;
import vnpay.vietcombank.sdk.shopping_online.data.Shopping_Online_Data;

public class Shopping_Online_Flow3_Smart_OTP extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private ShoppingOnlinePageObject shopping;
	private HomePageObject homePage;
	private SettingVCBSmartOTPPageObject smartOTP;
	private TransactionReportPageObject transReport;

	String transferTime;
	String transactionNumber;
	List<String> listActual;
	double soDuThuc = 0;
	String codeBill,codeTransfer, otpSmart, newOTP = "";
	int indexHang = 0;
	String moneyConfirm;


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
	@Test(invocationCount = 2)
	public void TC_00_ChonMuaMotSanPhamThanhToanOTP(String otp) {
		log.info("TC_00_: Them vao gio hang");
		shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);

		List<String> listProduct = shopping.getTextInListElementsProduct("đ");

		for (int i = 3; i < listProduct.size(); i++) {
			shopping.clickToDynamicView(listProduct.get(i));
			indexHang = i;
			if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
				log.info("TC_00_: click Back");
				shopping.clickToDynamicCart("1", "0");
				continue;
			} else {
				break;
			}
		}

		log.info("TC_00_: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

		log.info("TC_00_: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("1");

		log.info("TC_00_: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		shopping.scrollDownToTextView(Shopping_Online_Data.SHIPPING_STANDARD);
		
		log.info("TC_00_: click thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);



		log.info("TC_00_: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		sourceAccount = shopping.chooseSourceAccount(driver, Double.parseDouble(Constants.THREE_BILLION_VND), Constants.VND_CURRENCY);

	

		log.info("TC_00_: Chon thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		log.info("TC_00_: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.CONFIRM_INFO);

		log.info("TC_00_: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText(Shopping_Online_Data.SMS_OTP);

		log.info("TC_00_: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("TC_00_: dien otp");
		shopping.inputToDynamicOtp(otp, Shopping_Online_Data.CONTINUE);

		log.info("TC_00_: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.SUCCESS_TRANSFER);


		log.info("TC_00_: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SUPPLIER), Shopping_Online_Data.VNSHOP);

		codeTransfer = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_TRANSFER);

		transferTime = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTime").split(" ")[3];

		log.info("TC_00_: thuc hien giao dich moi");
		shopping.clickToDynamicButton(Shopping_Online_Data.NEW_TRANSFER);
		
	}

	@Test
	public void TC_01_ChonMuaMotSanPhamThanhToanOTPKhongChonKhuyenMai() {
		
		
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
		verifyEquals(moneyConfirm + " VND", (long)calulatorMoney + " VND");

		log.info("TC_01_STEP_16: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText(Shopping_Online_Data.SMART_OTP);

		log.info("TC_01_STEP_17: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("TC_01_Step_23: Nhap OTP");
		shopping.inputToDynamicSmartOTP(newOTP, "com.VCB:id/otp");

		log.info("TC_01_Step_24: Click tiep tuc");
		shopping.clickToDynamicButton(TittleData.CONTINUE_BTN);
		shopping.clickToDynamicButton(TittleData.CONTINUE_BTN);

		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.SUCCESS_TRANSFER);

		soDuThuc = soDuTK - Double.parseDouble(moneyConfirm);

		log.info("TC_01_STEP_19: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SUPPLIER), Shopping_Online_Data.VNSHOP);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);

		codeTransfer = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_TRANSFER);
		transferTime = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTime").split(" ")[3];


		log.info("TC_01_STEP_20: thuc hien giao dich moi");
		shopping.clickToDynamicButton(Shopping_Online_Data.NEW_TRANSFER);
		shopping.clickToDynamicCart("1", "0");

	}
	
	@Test
	public void TC_02_BaoCaoGiaoDichChonMuaNhieuSanPhamThanhToanMatKhauKhongChonKhuyenMai_Smart_OTP() {

		log.info("TC_02_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		log.info("TC_02_3: Click Bao Cao giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_3: Click Chon loai bao cao");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_02_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.VNSHOP_PAYMENT);

		log.info("TC_02_: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02_: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_02_: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(6);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_02_: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_02_: Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_02_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		
		log.info("TC_02_: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_02_: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_02_: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), codeTransfer);

		log.info("TC_02_: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);


		log.info("TC_02_: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), ReportTitle.VNSHOP_PAYMENT);

		log.info("TC_02: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(ReportTitle.VNSHOP));

		log.info("TC_02_: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_02_: Chon button back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_Click button home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Test
	public void TC_03_KiemTra_HienThiThongBao_DaDangNhap() {
		
		log.info("TC_03_Step_01: Click vao Inbox");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_3");
		
		log.info("TC_03_Step_02: Click vao tab Tat ca");
		homePage.clickToTextID(driver, "com.VCB:id/radioAll");
		
		log.info("TC_03_Step_03: Lay du lieu hien thi");
		String inboxContent = homePage.getDynamicTextByContentID(driver, "com.VCB:id/recycleview","com.VCB:id/content",codeBill);
		
		log.info("TC_03_Step_04: So sanh thong tin thanh toan");
		verifyTrue(inboxContent.contains(codeBill));
		verifyTrue(inboxContent.contains(addCommasToLong(moneyConfirm)));
		
		log.info("TC_03_Step_05: Click vao tab Khac");
		homePage.clickToTextID(driver, "com.VCB:id/radioOther");
		
		log.info("TC_03_Step_06: Lay du lieu hien thi");
		inboxContent = homePage.getDynamicTextByContentID(driver, "com.VCB:id/recycleview","com.VCB:id/content",codeBill);
		
		log.info("TC_03_Step_07: So sanh thong tin thanh toan");
		verifyTrue(inboxContent.contains(codeBill));
		verifyTrue(inboxContent.contains(addCommasToLong(moneyConfirm)));
		
		log.info("TC_03_Step_08: Mo tab Home");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}
	
	@Test
	public void TC_04_ChonMuaNhieuSanPhamThanhToanOTPKhongChonKhuyenMai() {
		
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.scrollDownToText(driver, HomePage_Data.Home_Text_Elements.VIETCOMBANK_2020);
		homePage.scrollIDownOneTime(driver);
		homePage.clickToDynamicButtonLinkOrLinkText(driver, Home_Text_Elements.TITLE_SHOPPING_ONLINE);
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		shopping = PageFactoryManager.getShoppingOnlinePageObject(driver);

		log.info("TC_04_STEP_: Them vao gio hang");
		shopping.clickToDynamicTextContains(Shopping_Online_Data.VIEW_ALL);

		List<String> listProduct = shopping.getTextInListElementsProduct("đ");

		for (int i = 3; i < listProduct.size(); i++) {
			shopping.clickToDynamicView(listProduct.get(i));
			indexHang = i;
			if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.OUT_OF_BOOK)) {
				log.info("TC_04_STEP_5: click Back");
				shopping.clickToDynamicCart("1", "0");
				continue;
			} else {
				break;
			}
		}

		log.info("TC_04_STEP_: click dat hang");
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
		shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

		log.info("TC_04_STEP_: click Vao gio hang");
		shopping.clickToDynamicDateInDateTimePicker("3");

		log.info("TC_04_STEP_: lay tong tien can thanh toan");
		String tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
		double tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
		log.info("TC_04_STEP_: click dat hang");
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

		log.info("TC__03_STEP_: click thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		if (shopping.isTextDisplayedInPageSource(Shopping_Online_Data.ARE_YOU_HAVE_ADDRESS)) {

			log.info("TC_04_STEP_: click the moi");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_NEW);

			log.info("TC__03_STEP_: Hien thi man hinh them moi dia chi");
			shopping.isDynamicTextInfoDisplayed(Shopping_Online_Data.ADD_NEW_ADDRESS);

			log.info("TC_04_STEP_: nhap ten");
			shopping.inputToDynamicInfo(Shopping_Online_Data.USER_NAME, Shopping_Online_Data.NAME_RECIEPT);

			log.info("TC_04_STEP_: nhap so dien thoai");
			shopping.inputToDynamicInfo(Shopping_Online_Data.PHONE_NUMBER, Shopping_Online_Data.RECIPT_PHONE_NUMBER);

			log.info("TC_02_STEP_: chon tinh thanh pho");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.CAPITAL);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.HANOI_CITY);

			log.info("TC_04_STEP_: chon tinh quan huyen");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.DISTRICT);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.BA_DINH_DISTRICT);

			log.info("TC_04_STEP_: chon tinh xa phuong");
			shopping.clickToDynamicCustomer(Shopping_Online_Data.WARD);
			shopping.clickToDynamicListProvince(Shopping_Online_Data.WARD_CONG_VI);

			log.info("TC_04_STEP_: dia chi cu the");
			shopping.inputToDynamicInfo(Shopping_Online_Data.ADDRESS_NUMBER, Shopping_Online_Data.ADDRESS_DETAIL);

			log.info("TC_04_STEP_: chon hoan tat");
			shopping.clickToDynamicButton(Shopping_Online_Data.COMPLETE);

			log.info("TC_04_STEP_: click thanh toan");
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
					log.info("TC_04_STEP_: click Back");
					shopping.clickToDynamicCart("1", "0");
					continue;
				} else {
					break;
				}
			}
			log.info("TC_04_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_04_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("3");

			log.info("TC_04_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));
			log.info("TC_04_STEP_: click dat hang");
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

			log.info("TC_04_STEP_: click thanh toan");
			shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

			log.info("TC_04_STEP_: lay tong tien can thanh toan");
			tottalMoneyCartString = shopping.getDynamicTextPricesByText(Shopping_Online_Data.PRODUCT).replace("₫", "");
			tottalMoneyCart = Double.parseDouble(tottalMoneyCartString.replace(".", ""));

			log.info("TC_04_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);
			shopping.clickToDynamicButton(Shopping_Online_Data.ADD_TO_CART);

			log.info("TC_04_STEP_: click Vao gio hang");
			shopping.clickToDynamicDateInDateTimePicker("3");

			log.info("TC_04_STEP_: click dat hang");
			shopping.clickToDynamicButton(Shopping_Online_Data.BOOKING);

		}


		log.info("TC_04_STEP_: click chon tai khoan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvContent");
		sourceAccount = shopping.chooseSourceAccount(driver, Double.parseDouble(Constants.THREE_BILLION_VND), Constants.VND_CURRENCY);

		log.info("TC_04_STEP_: Lay so du tai khoan ");
		double soDuTK = Double.parseDouble(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AVAIABLE_BALANCE).replace("VND", "").replace(",", ""));

		log.info("TC_04_STEP_: lay thong tin ma don hang");
		String codeBill = shopping.getDynamicTextDetailByIDOrPopup("com.VCB:id/LblMadonhangDescription");

		log.info("TC_04_STEP_: lay ra phi giao hang");
		String[] getfeeString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.FEE_SHIPPING).split(" ");
		double fee = Double.parseDouble(getfeeString[0].replace(",", ""));

		log.info("TC_04_STEP_: verify fee Ship");
		verifyEquals(feeShippingD, fee);

		log.info("TC_04_STEP_: giam gia");
		String[] getSaleString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.DISCOUNT).split(" ");
		double sale = Double.parseDouble(getSaleString[0].replace(",", "").replace("", ""));

		log.info("TC_04_STEP_: tong tien");
		String[] totalMoneyBillString = shopping.getDynamicTextInTransactionDetail(Shopping_Online_Data.PRICE_ORDER).split(" ");
		double totalMoneyBill = Double.parseDouble(totalMoneyBillString[0].replace(",", ""));
		double calulatorMoney = canculateAvailableBalances((long) totalMoneyBill, (long) fee, (long) sale);
		verifyEquals(calulatorMoney, totalMoney);

		log.info("TC_04_STEP_: Chon thanh toan");
		shopping.clickToDynamicButton(Shopping_Online_Data.PAYMENT);

		log.info("TC_04_STEP_: Xac minh hien thi man hinh xac nhan thong tin");
		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.CONFIRM_INFO);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);
		verifyTrue(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.AMOUNT_PRICE).contains(totalMoneyBillString[1]));

		log.info("TC_04_STEP_: Kiem tra tai khoan nguon");
		String account = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SOURCE_ACCOUNT);
		verifyEquals(account, sourceAccount.account);

		log.info("TC_04_STEP_: Kiem tra so tien thanh toan");
		String[] money = (shopping.getMoneyByAccount(Shopping_Online_Data.AMOUNT_PRICE).replace(",", "")).split(" ");
		moneyConfirm = money[0];
		verifyEquals(moneyConfirm + " VND",(long) calulatorMoney + " VND");

		log.info("TC_04_STEP_: Chon phuong thuc thanh toan");
		shopping.clickToDynamicDropdownAndDateTimePicker("com.VCB:id/tvptxt");
		shopping.clickToDynamicButtonLinkOrLinkText(Shopping_Online_Data.SMART_OTP);

		log.info("TC_04_STEP_: Chon tiep tuc");
		shopping.clickToDynamicButton(Shopping_Online_Data.CONTINUE);

		log.info("TC_04_Step_23: Nhap OTP");
		shopping.inputToDynamicSmartOTP(newOTP, "com.VCB:id/otp");

		log.info("TC_04_Step_24: Click tiep tuc");
		shopping.clickToDynamicButton(TittleData.CONTINUE_BTN);
		shopping.clickToDynamicButton(TittleData.CONTINUE_BTN);


		shopping.isDynamicMessageAndLabelTextDisplayed(Shopping_Online_Data.SUCCESS_TRANSFER);

		soDuThuc = soDuTK - Double.parseDouble(moneyConfirm);

		log.info("TC_04_STEP_: Xac nhan thong tin ");

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.SUPPLIER), Shopping_Online_Data.VNSHOP);

		verifyEquals(shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_ORDER), codeBill);

		codeTransfer = shopping.getDynamicTextTableByTextView(Shopping_Online_Data.CODE_TRANSFER);
		transferTime = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvTime").split(" ")[3];


		log.info("TC_04_STEP_: thuc hien giao dich moi");
		shopping.clickToDynamicButton(Shopping_Online_Data.NEW_TRANSFER);
		shopping.clickToDynamicCart("1", "0");

	}
	
	@Test
	public void TC_05_BaoCaoGiaoDichChonMuaNhieuSanPhamThanhToanMatKhauKhongChonKhuyenMai() {

		log.info("TC_05_2: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		log.info("TC_05_3: Click Bao Cao giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.TRANSACTION_REPORT);

		log.info("TC_05_3: Click Chon loai bao cao");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectTransType");

		log.info("TC_05_4: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransactionReport_Data.ReportTitle.VNSHOP_PAYMENT);

		log.info("TC_05_: Chon so tai khoan");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_05_: Chon so tai khoan tra cuu");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, sourceAccount.account);

		log.info("TC_05_: verify thoi tim kiem tu ngay");
		String dateStartActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvFromDate");
		String dateStartExpect = getBackwardDate(6);
		verifyEquals(dateStartActual, dateStartExpect);

		log.info("TC_05_: verify thoi tim kiem tu ngay");
		String dateEndActual = transReport.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvToDate");
		String dateEndtExpect = getForwardDate(0);
		verifyEquals(dateEndActual, dateEndtExpect);

		log.info("TC_05_: Tim kiem");
		transReport.clickToDynamicButton(driver, ReportTitle.SEARCH_BUTTON);

		log.info("TC_05_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_05_: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport), transferTime);

		log.info("TC_05_: Click chi tiet giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_05_: Lay ngay tao giao dich hien thi");
		String transferTimeInReport1 = transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent");

		log.info("TC_05_: Kiem tra ngay tao giao dich hien thi");
		verifyEquals(convertDateTimeIgnoreHHmmss(transferTimeInReport1), transferTime);

		log.info("TC_05_: Check so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_NUMBER), codeTransfer);

		log.info("TC_05_: Check tao khoan ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), sourceAccount.account);

		log.info("TC_05_: Check loai giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TYPE_TRANSFER), ReportTitle.VNSHOP_PAYMENT);

		log.info("TC_05_: Check noi dung giao dich");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.CONTENT_TRANSFER).contains(ReportTitle.VNSHOP));

		log.info("TC_05_: Chick chi tiet giao dich");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.DETAIL_TRANSFER);

		log.info("TC_05_: Chon button back");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_05_Click button home");
		transReport.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Parameters ({"pass"})
	@Test
	public void TC_06_KiemTra_HienThiThongBao_ChuaDangNhap(String password) {
		
		log.info("TC_06_Step_01: Click vao Menu");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");
		
		log.info("TC_06_Step_02: Click vao Thoat Ung Dung");
		homePage.scrollUpToText(driver, Notify_Text.LOG_OUT_TEXT);
		homePage.clickToDynamicButtonLinkOrLinkTextNotScroll(driver, Notify_Text.LOG_OUT_TEXT);
		
		log.info("TC_06_Step_03: Click vao Dong y");
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");
		
		log.info("TC_06_Step_04: Click vao Inbox");
		homePage.clickToDynamicImageView(driver, "com.VCB:id/ivOTT");
		
		log.info("TC_06_Step_05: Click vao tab Tat ca");
		homePage.clickToTextID(driver, "com.VCB:id/radioAll");
		
		log.info("TC_06_Step_06: Lay du lieu hien thi");
		String inboxContent = homePage.getDynamicTextByContentID(driver, "com.VCB:id/recycleview","com.VCB:id/content",codeBill);
		
		log.info("TC_06_Step_07: So sanh thong tin thanh toan");
		verifyTrue(inboxContent.contains(codeBill));
		verifyTrue(inboxContent.contains(addCommasToLong(moneyConfirm)));
		
		log.info("TC_06_Step_08: Click vao tab Khac");
		homePage.clickToTextID(driver, "com.VCB:id/radioOther");
		
		log.info("TC_06_Step_09: Lay du lieu hien thi");
		inboxContent = homePage.getDynamicTextByContentID(driver, "com.VCB:id/recycleview","com.VCB:id/content",codeBill);

		log.info("TC_06_Step_10: So sanh thong tin thanh toan");
		verifyTrue(inboxContent.contains(codeBill));
		verifyTrue(inboxContent.contains(addCommasToLong(moneyConfirm)));
		
		log.info("TC_06_Step_11: Back ve man Log In");
		homePage.clickToDynamicImageView(driver, "com.VCB:id/back");
		
		log.info("TC_06_Step_12: Nhap mat khau va dang nhap");
		homePage.inputIntoEditTextByID(driver, password, "com.VCB:id/edtInput");
		homePage.clickToDynamicAcceptButton(driver, "com.VCB:id/btnNext");
		
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}
