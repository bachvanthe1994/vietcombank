package vnpay.vietcombank.lucky_gift;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import pageObjects.LuckyGiftPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.HomePage_Data;
import vietcombank_test_data.LuckyGift_Data;
import vietcombank_test_data.LuckyGift_Data.TitleLuckyGift;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;

public class LuckyGift extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private LuckyGiftPageObject luckyGift;
	private HomePageObject homePage;
	private TransactionReportPageObject transReport;
	private String beneficiary_account;
	private String money;
	private String wishes;
	private Date date;
	private String surplusString;
	private String[] getName;
	private String moneyFee;
	private SettingVCBSmartOTPPageObject smartOTP;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	String account;
	String passSmartOTP = "111222";
	SourceAccountModel sourceAccount = new SourceAccountModel();

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
    @BeforeClass
    public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt)
	    throws IOException, InterruptedException, GeneralSecurityException {
		startServer();
		log.info("Before class: Mo app ");
		if (deviceType.contains("android")) {
			driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		} else if (deviceType.contains("ios")) {
			driver = openIOSApp(deviceName, udid, url);
		}
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		luckyGift = PageFactoryManager.getLuckyGiftPageObject(driver);
		homePage = PageFactoryManager.getHomePageObject(driver);
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
//		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
//		smartOTP.setupSmartOTP(passSmartOTP, getDataInCell(6));
	}
	
	@Parameters({ "pass" })
//	@Test
	public void TC_01_NGuoiNhanTrongVCBBangSDTXacThucBangMatKhau(String pass) throws GeneralSecurityException, IOException {
		log.info("TC_01_Step_1: Chọn quà tặng may mắn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_01_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, TitleLuckyGift.SOURCE_ACCOUNT);
		sourceAccount = luckyGift.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		String moneySurplus = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] moneySurplusSplit = moneySurplus.split(" ");
		String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
		long surplus = Long.parseLong(moneySurplusReplace);

		log.info("TC_01_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_01_Step_4: Click tiep tuc popup");
//		luckyGift.waitUntilPopUpDisplay(TitleLuckyGift.CONFIRM);
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_5: chọn hình thức nhận");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThucNhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.TITLE_PHONE_NUMBER);

		log.info("TC_01_Step_6: nhập số điện thoại");
		luckyGift.inputToDynamicInputBox(driver, getDataInCell(5), TitleLuckyGift.TITLE_PHONE_NUMBER);

		log.info("TC_01_Step_7: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_01_Step_8: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, TitleLuckyGift.TITLE_WISHES);
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivReceiver");
		
		log.info("TC_01_Step_9: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_11: lấy ra lời chúc");
		wishes = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLoichuc");

		log.info("TC_01_Step_12: lấy ra ten nguoi nhan");
		String userName = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		getName = userName.split("/");

		log.info("TC_01_Step_13: lấy ra số tiền chuyển");
		money = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSumAmount");

		log.info("TC_01_Step_14: lấy ra phí chuyển");
		moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		long sumFeeInt = convertMoneyToLong(moneyFee, "VND");
		long surplusTotal = surplus - Long.parseLong(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_01_Step_15: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_16: điền mât khẩu");
		luckyGift.inputToDynamicInputBox(driver, pass, TitleLuckyGift.INPUT_PASSWORD);

		log.info("TC_01_Step_17: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_18: kiem tra loi chuc");
		String getWish = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/content");
		verifyEquals(wishes, getWish);

		log.info("TC_01_Step_19: lấy ra tên người thụ hưởng");
		beneficiary_account = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/info_receiver");
		verifyEquals(beneficiary_account, getName[1]);

		log.info("TC_01_Step_20: kiem tra so tien chuyen di");
		String getMoney = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/amount");
		verifyEquals(money, getMoney);

		log.info("TC_01_Step_21: kiem tra so tai khoan nguoi nhan");
		String getAccount = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAcc");
		verifyEquals(getName[0], getAccount);

		log.info("TC_01_Step_22: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.NEW_TRANSFER);

		log.info("TC_01_Step_23: chọn tai khoản");
		luckyGift.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_01_Step_24: kiểm tra số dư");
		String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, account);
		String[] moneyTransferSplit = moneyTransfer.split(" ");
		String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
		verifyEquals(moneyTransferReplace, surplusString);

		log.info("TC_07_Step_25: chọn đóng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.CLOSE);

		log.info("TC_01_Step_26: Lấy thời gian thực hiện giao dịch");
		date = new Date();

		log.info("TC_01_Step_27 : Click home");
		luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");

	}

//	@Test
	public void TC_02_BaoCaoTienQuaTangMayManTrongVCBBangSDTVaXacThucBangMK() {
		log.info("TC_02_Step_02: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step_03: Click Bao Cao Dao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_02_Step_05: Chon quà tặng may mắn");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TITLE);

		log.info("TC_02_Step_06: click chọn tài khoản");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_02_Step_07: chọn tìm kiếm");
		transReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_02_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, TitleLuckyGift.FIND);

		log.info("TC_02_Step_9: Kiem tra ngày giao dịch");
		String dateSuccess = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvDate");
		String[] dateSplit = dateSuccess.split(" ");
		verifyEquals(dateSplit[0], formatter.format(date));

		log.info("TC_02_Step_10: Kiem tra noi dung hien thi");
		String content = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent");
		verifyEquals(content, wishes.trim());

		log.info("TC_02_Step_11: Kiem tra số tiền chuyển đi");
		String getMoney = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney");
		String[] moneySplit = getMoney.split(" ");
		verifyEquals(moneySplit[1] + " " + moneySplit[2], money);

		log.info("TC_02_Step_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02_Step_13: Kiem tra ngày giao dịch");
		String getDate = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSFER);
		String[] dateDeal = getDate.split(" ");
		verifyEquals(dateDeal[0], formatter.format(date));

		log.info("TC_02_Step_14: Kiem tra tài khoản nguồn");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_02_Step_15: Kiem tra tên người thụ hưởng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.BENEFICIARY_NAME), beneficiary_account);

		log.info("TC_02_Step_16: Kiem tra tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleLuckyGift.ACCOUNT_CREDITED), getName[0]);

		log.info("TC_02_Step_17: Kiem tra so tien phi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleLuckyGift.TRANSACTION_FEE), moneyFee);

		log.info("TC_02_Step_18: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, TitleLuckyGift.TRANSACTION_DETAIL);

		log.info("TC_02_Step_19: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_Step_20: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "pass" })
//	@Test
	public void TC_03_NGuoiNhanTrongVCBBangSTKXacThucBangMatKhau(String pass) {
		log.info("TC_03_Step_1: Chọn quà tặng may mắn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_03_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, TitleLuckyGift.SOURCE_ACCOUNT);
		sourceAccount = luckyGift.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		String moneySurplus = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] moneySurplusSplit = moneySurplus.split(" ");
		String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
		long surplus = Long.parseLong(moneySurplusReplace);

		log.info("TC_03_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");
//		luckyGift.waitUntilPopUpDisplay(TitleLuckyGift.CONFIRM);
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_03_Step_4: nhập số tài khoản");
		luckyGift.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DIFFERENT_OWNER_ACCOUNT_2, TitleLuckyGift.NUMBER_ACCOUNT);

		log.info("TC_03_Step_5: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_03_Step_6: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, TitleLuckyGift.TITLE_WISHES);
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.hideKeyBoard(driver);

		log.info("TC_03_Step_7: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_03_Step_9: lấy ra lời chúc");
		wishes = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLoichuc");

		log.info("TC_01_Step_10: lấy ra ten nguoi nhan");
		String userName = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		getName = userName.split("/");

		log.info("TC_03_Step_11: lấy ra số tiền chuyển");
		money = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSumAmount");

		log.info("TC_03_Step_12: lấy ra phí chuyển");
		moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		long sumFeeInt = convertMoneyToLong(moneyFee, "VND");
		long surplusTotal = surplus - Long.parseLong(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_03_Step_13: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_03_Step_14: điền mât khẩu");
		luckyGift.inputToDynamicInputBox(driver, pass, TitleLuckyGift.INPUT_PASSWORD);

		log.info("TC_03_Step_15: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_16: kiem tra loi chuc");
		String getWish = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/content");
		verifyEquals(wishes, getWish);

		log.info("TC_03_Step_17: lấy ra tài khoản thụ hưởng");
		beneficiary_account = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAcc");
		verifyEquals(beneficiary_account, getName[1]);

		log.info("TC_01_Step_18: kiem tra so tien chuyen di");
		String getMoney = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/amount");
		verifyEquals(money, getMoney);

		log.info("TC_01_Step_19: kiem tra so tai khoan nguoi nhan");
		String getAccount = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAcc");
		verifyEquals(getName[0], getAccount);

		log.info("TC_03_Step_20: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.NEW_TRANSFER);

		log.info("TC_03_Step_21: chọn tai khoản");
		luckyGift.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_03_Step_22: kiểm tra số dư");
		String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, account);
		String[] moneyTransferSplit = moneyTransfer.split(" ");
		String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
		verifyEquals(moneyTransferReplace, surplusString);

		log.info("TC_07_Step_23: chọn đóng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.CLOSE);

		log.info("TC_03_Step_24: Lấy thời gian thực hiện giao dịch");
		date = new Date();

		log.info("TC_03_Step_25 : Click home");
		luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");

	}

//	@Test
	public void TC_04_BaoCaoTienQuaTangMayManTrongVCBBangSTKVaXacThucBangMK() {
		log.info("TC_04_Step_02: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_04_Step_05: Chon quà tặng may mắn");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TITLE);

		log.info("TC_04_Step_06: click chọn tài khoản");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_04_Step_07: chọn tìm kiếm");
		transReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_04_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, TitleLuckyGift.FIND);

		log.info("TC_04_Step_9: Kiem tra ngày giao dịch");
		String dateSuccess = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvDate");
		String[] dateSplit = dateSuccess.split(" ");
		verifyEquals(dateSplit[0], formatter.format(date));

		log.info("TC_04_Step_10: Kiem tra noi dung hien thi");
		String content = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent");
		verifyEquals(content, wishes.trim());

		log.info("TC_04_Step_11: Kiem tra số tiền chuyển đi");
		String getMoney = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney");
		String[] moneySplit = getMoney.split(" ");
		verifyEquals(moneySplit[1] + " " + moneySplit[2], money);

		log.info("TC_04_Step_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04_Step_13: Kiem tra ngày giao dịch");
		String getDate = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSFER);
		String[] dateDeal = getDate.split(" ");
		verifyEquals(dateDeal[0], formatter.format(date));

		log.info("TC_04_Step_14: Kiem tra tài khoản nguồn");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_04_Step_15: Kiem tra tài khoản thụ hưởng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleLuckyGift.ACCOUNT_CREDITED), getName[0]);

		log.info("TC_02_Step_16: Kiem tra so tien phi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleLuckyGift.TRANSACTION_FEE), moneyFee);

		log.info("TC_04_Step_17: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, TitleLuckyGift.TRANSACTION_DETAIL);

		log.info("TC_04_Step_18: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_Step_19: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_05_NGuoiNhanNgoaiVCBSoTaiKhoanXacThucBangMK(String pass) throws GeneralSecurityException, IOException {
		log.info("TC_05_Step_1: Chọn quà tặng may mắn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_05_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, TitleLuckyGift.SOURCE_ACCOUNT);
		sourceAccount = luckyGift.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		String moneySurplus = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] moneySurplusSplit = moneySurplus.split(" ");
		String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
		long surplus = Long.parseLong(moneySurplusReplace);

		log.info("TC_05_Step_3: chọn hình thức gửi quà");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThuc");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.USER_OUTVCB);

		log.info("TC_05_Step_4: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_05_Step_5: nhập số tài khoản");
		luckyGift.inputToDynamicInputBox(driver, getDataInCell(4), TitleLuckyGift.NUMBER_ACCOUNT);

		log.info("TC_05_Step_6: ngần hàng thụ hưởng");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvBank");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK_DBA);

		log.info("TC_05_Step_7: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_05_Step_8: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, TitleLuckyGift.TITLE_WISHES);
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivReceiver");

		log.info("TC_05_Step_9: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_05_Step_11: lấy ra tài khoản thụ hưởng");
		String user = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		String[] userSplit = user.split("/");
		beneficiary_account = userSplit[0];

		log.info("TC_05_Step_12: lấy ra lời chúc");
		wishes = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLoichuc");

		log.info("TC_01_Step_13: lấy ra ten nguoi nhan");
		String userName = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		getName = userName.split("/");

		log.info("TC_05_Step_14: lấy ra số tiền chuyển");
		money = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSumAmount");

		log.info("TC_05_Step_15: lấy ra phí chuyển");
		moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		long sumFeeInt = convertMoneyToLong(moneyFee, "VND") ;
		long surplusTotal = surplus - Long.parseLong(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_05_Step_16: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_17: điền mât khẩu");
		luckyGift.inputToDynamicInputBox(driver, pass, TitleLuckyGift.INPUT_PASSWORD);

		log.info("TC_05_Step_18: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_05_Step_19: kiem tra loi chuc");
		String getWish = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/content");
		verifyEquals(wishes, getWish);

		log.info("TC_05_Step_20: lấy ra tên người thụ hưởng");
		beneficiary_account = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/info_receiver");
		verifyEquals(beneficiary_account, getName[1]);

		log.info("TC_05_Step_21: kiem tra so tien chuyen di");
		String getMoney = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/amount");
		verifyEquals(money, getMoney);

		log.info("TC_05_Step_22: kiem tra so tai khoan nguoi nhan");
		String getAccount = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAcc");
		verifyEquals(getName[0], getAccount);

		log.info("TC_05_Step_23: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.NEW_TRANSFER);

		log.info("TC_05_Step_24: chọn tai khoản");
		luckyGift.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_05_Step_25: kiểm tra số dư");
		String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, account);
		String[] moneyTransferSplit = moneyTransfer.split(" ");
		String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
		verifyEquals(moneyTransferReplace, surplusString);

		log.info("TC_05_Step_26: chọn đóng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.CLOSE);

		log.info("TC_05_Step_27: Lấy thời gian thực hiện giao dịch");
		date = new Date();
	}

	@Test
	public void TC_06_BaoCaoGiaoDichChuyenTienQuaTangMayManNgoaiVCBVaXacThucBangMK() throws GeneralSecurityException, IOException {
		log.info("TC_06_Step_01 : Click home");
		luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");

		log.info("TC_06_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_06_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_06_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_06_Step_05: Chon quà tặng may mắn");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TITLE);

		log.info("TC_06_Step_06: click chọn tài khoản");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, account);

		log.info("TC_06_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, TitleLuckyGift.FIND);

		log.info("TC_06_Step_09: Kiem tra ngày giao dịch");
		String dateSuccess = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvDate");
		String[] dateSplit = dateSuccess.split(" ");
		verifyEquals(dateSplit[0], formatter.format(date));

		log.info("TC_06_Step_10: Kiem tra noi dung hien thi");
		String content = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent");
		verifyEquals(content, wishes.trim());

		log.info("TC_06_Step_11: Kiem tra số tiền chuyển đi");
		String getMoney = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney");
		String[] moneySplit = getMoney.split(" ");
		verifyEquals(moneySplit[1] + " " + moneySplit[2], money);

		log.info("TC_06_Step_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06_Step_13: Kiem tra ngày giao dịch");
		String getDate = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSFER);
		String[] dateDeal = getDate.split(" ");
		verifyEquals(dateDeal[0], formatter.format(date));

		log.info("TC_06_Step_14: Kiem tra tài khoản nguồn");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_06_Step_15: Kiem tra tài khoản thụ hưởng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleLuckyGift.ACCOUNT_CREDITED), getDataInCell(4));

		log.info("TC_06_Step_16: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, TitleLuckyGift.TRANSACTION_DETAIL);

		log.info("TC_06_Step_17: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_Step_19: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Parameters({ "otp" })
	@Test
	public void TC_07_NGuoiNhanTrongVCBBangSDTXacThucBangOTP(String otp) throws GeneralSecurityException, IOException {
		log.info("TC_07_Step_1: Chọn quà tặng may mắn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_07_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, TitleLuckyGift.SOURCE_ACCOUNT);
		sourceAccount = luckyGift.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		String moneySurplus = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] moneySurplusSplit = moneySurplus.split(" ");
		String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
		long surplus = Long.parseLong(moneySurplusReplace);

		log.info("TC_07_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_07_Step_4: chọn hình thức nhận");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThucNhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.TITLE_PHONE_NUMBER);

		log.info("TC_07_Step_5: nhập số điện thoại");
		luckyGift.inputToDynamicInputBox(driver, getDataInCell(5), TitleLuckyGift.TITLE_PHONE_NUMBER);

		log.info("TC_07_Step_6: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_07_Step_7: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, TitleLuckyGift.TITLE_WISHES);
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.hideKeyBoard(driver);

		log.info("TC_07_Step_8: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_07_Step_10: lấy ra lời chúc");
		wishes = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLoichuc");

		log.info("TC_01_Step_11: lấy ra ten nguoi nhan");
		String userName = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		getName = userName.split("/");

		log.info("TC_07_Step_12: lấy ra số tiền chuyển");
		money = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSumAmount");

		log.info("TC_07_Step_13: lấy ra phí chuyển");
		moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		long sumFeeInt = convertMoneyToLong(moneyFee, "VND") ;
		long surplusTotal = surplus - Long.parseLong(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_07_Step_14: chọn phương thức xác thực");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvptxt");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.OTP);

		log.info("TC_07_Step_15: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_07_Step_16: điền otp");
		luckyGift.inputToDynamicOtp(driver, otp, TitleLuckyGift.NEXT);

		log.info("TC_07_Step_17: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_18: kiem tra loi chuc");
		String getWish = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/content");
		verifyEquals(wishes, getWish);

		log.info("TC_07_Step_10: lấy ra tên người thụ hưởng");
		beneficiary_account = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/info_receiver");
		verifyEquals(beneficiary_account, getName[1]);

		log.info("TC_01_Step_16: kiem tra so tien chuyen di");
		String getMoney = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/amount");
		verifyEquals(money, getMoney);

		log.info("TC_01_Step_16: kiem tra so tai khoan nguoi nhan");
		String getAccount = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAcc");
		verifyEquals(getName[0], getAccount);

		log.info("TC_07_Step_17: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.NEW_TRANSFER);

		log.info("TC_07_Step_18: chọn tai khoản");
		luckyGift.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_07_Step_19: kiểm tra số dư");
		String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, account);
		String[] moneyTransferSplit = moneyTransfer.split(" ");
		String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
		verifyEquals(moneyTransferReplace, surplusString);

		log.info("TC_07_Step_21: chọn đóng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.CLOSE);

		log.info("TC_07_Step_21: Lấy thời gian thực hiện giao dịch");
		date = new Date();

		log.info("TC_07_Step_22 : Click home");
		luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");

	}

	@Test
	public void TC_08_BaoCaoTienQuaTangMayManTrongVCBBangSDTVaXacThucBangOTP() {
		log.info("TC_08_Step_02: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_08_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_08_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_08_Step_05: Chon quà tặng may mắn");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TITLE);

		log.info("TC_08_Step_06: click chọn tài khoản");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");
		sourceAccount = luckyGift.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		log.info("TC_08_Step_07: chọn tìm kiếm");
		transReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_08_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, TitleLuckyGift.FIND);

		log.info("TC_08_Step_9: Kiem tra ngày giao dịch");
		String dateSuccess = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvDate");
		String[] dateSplit = dateSuccess.split(" ");
		verifyEquals(dateSplit[0], formatter.format(date));

		log.info("TC_08_Step_10: Kiem tra noi dung hien thi");
		String content = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent");
		verifyEquals(content, wishes.trim());

		log.info("TC_08_Step_11: Kiem tra số tiền chuyển đi");
		String getMoney = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney");
		String[] moneySplit = getMoney.split(" ");
		verifyEquals(moneySplit[1] + " " + moneySplit[2], money);

		log.info("TC_08_Step_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08_Step_13: Kiem tra ngày giao dịch");
		String getDate = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSFER);
		String[] dateDeal = getDate.split(" ");
		verifyEquals(dateDeal[0], formatter.format(date));

		log.info("TC_08_Step_14: Kiem tra tài khoản nguồn");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_08_Step_15: Kiem tra tên người thụ hưởng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.BENEFICIARY_NAME), beneficiary_account);

		log.info("TC_02_Step_16: Kiem tra tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleLuckyGift.ACCOUNT_CREDITED), getName[0]);

		log.info("TC_02_Step_17: Kiem tra so tien phi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleLuckyGift.TRANSACTION_FEE), moneyFee);

		log.info("TC_08_Step_18: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, TitleLuckyGift.TRANSACTION_DETAIL);

		log.info("TC_08_Step_19: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_08_Step_20: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_09_NGuoiNhanTrongVCBSoTaiKhoanXacThucBangOTP(String otp) {
		log.info("TC_09_Step_1: Chọn quà tặng may mắn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_09_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, TitleLuckyGift.SOURCE_ACCOUNT);
		sourceAccount = luckyGift.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		String moneySurplus = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] moneySurplusSplit = moneySurplus.split(" ");
		String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
		long surplus = Long.parseLong(moneySurplusReplace);

		log.info("TC_09_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_09_Step_5: nhập số tài khoản");
		luckyGift.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, TitleLuckyGift.NUMBER_ACCOUNT);

		log.info("TC_09_Step_6: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_09_Step_7: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, TitleLuckyGift.TITLE_WISHES);
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.hideKeyBoard(driver);

		log.info("TC_09_Step_8: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_09_Step_10: lấy ra tài khoản thụ hưởng");
		String user = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		String[] userSplit = user.split("/");
		beneficiary_account = userSplit[1];

		log.info("TC_09_Step_11: lấy ra lời chúc");
		wishes = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLoichuc");

		log.info("TC_01_Step_12: lấy ra ten nguoi nhan");
		String userName = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		getName = userName.split("/");

		log.info("TC_09_Step_13: lấy ra số tiền chuyển");
		money = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSumAmount");

		log.info("TC_09_Step_14: lấy ra phí chuyển");
		moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		long sumFeeInt = convertMoneyToLong(moneyFee, "VND") ;
		long surplusTotal = surplus - Long.parseLong(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_09_Step_15: chọn phương thức xác thực OTP");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvptxt");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.OTP);

		log.info("TC_09_Step_16: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_09_Step_17: điền OTP");
		luckyGift.inputToDynamicOtp(driver, otp, TitleLuckyGift.NEXT);

		log.info("TC_09_Step_18: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_19: kiem tra loi chuc");
		String getWish = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/content");
		verifyEquals(wishes, getWish);

		log.info("TC_01_Step_20: lấy ra tên người thụ hưởng");
		beneficiary_account = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/info_receiver");
		verifyEquals(beneficiary_account, getName[1]);

		log.info("TC_01_Step_21: kiem tra so tien chuyen di");
		String getMoney = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/amount");
		verifyEquals(money, getMoney);

		log.info("TC_01_Step_22: kiem tra so tai khoan nguoi nhan");
		String getAccount = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAcc");
		verifyEquals(getName[0], getAccount);

		log.info("TC_09_Step_23: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.NEW_TRANSFER);

		log.info("TC_09_Step_24: chọn tai khoản");
		luckyGift.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_09_Step_25: kiểm tra số dư");
		String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, account);
		String[] moneyTransferSplit = moneyTransfer.split(" ");
		String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
		verifyEquals(moneyTransferReplace, surplusString);

		log.info("TC_09_Step_26: chọn đóng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.CLOSE);

		log.info("TC_09_Step_27: Lấy thời gian thực hiện giao dịch");
		date = new Date();

		log.info("TC_09_Step_28 : Click home");
		luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");
	}

	@Test
	public void TC_10_KiemTraChiTietGiaoDichChuyenTienQuaTangMayManTrongVCBVaXacThucBangOTP() {

		log.info("TC_10_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_10_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_10_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_10_Step_05: Chon quà tặng may mắn");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TITLE);

		log.info("TC_10_Step_06: click chọn tài khoản");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_10_Step_07: chọn đóng");
		transReport.clickToTextID(driver, "com.VCB:id/cancel_button");

		log.info("TC_10_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, TitleLuckyGift.FIND);

		log.info("TC_10_Step_9: Kiem tra ngày giao dịch");
		String dateSuccess = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvDate");
		String[] dateSplit = dateSuccess.split(" ");
		verifyEquals(dateSplit[0], formatter.format(date));

		log.info("TC_10_Step_10: Kiem tra noi dung hien thi");
		String content = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent");
		verifyEquals(content, wishes.trim());

		log.info("TC_10_Step_11: Kiem tra số tiền chuyển đi");
		String getMoney = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney");
		String[] moneySplit = getMoney.split(" ");
		verifyEquals(moneySplit[1] + " " + moneySplit[2], money);

		log.info("TC_10_Step_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10_Step_13: Kiem tra ngày giao dịch");
		String getDate = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSFER);
		String[] dateDeal = getDate.split(" ");
		verifyEquals(dateDeal[0], formatter.format(date));

		log.info("TC_10_Step_14: Kiem tra tài khoản nguồn");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_10_Step_16: Kiem tra tài khoản thụ hưởng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.BENEFICIARY_NAME), beneficiary_account);

		log.info("TC_02_Step_17: Kiem tra tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleLuckyGift.ACCOUNT_CREDITED), getName[0]);

		log.info("TC_02_Step_18: Kiem tra so tien phi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleLuckyGift.TRANSACTION_FEE), moneyFee);

		log.info("TC_10_Step_19: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, TitleLuckyGift.TRANSACTION_DETAIL);

		log.info("TC_10_Step_20: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_10_Step_21: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Parameters({ "otp" })
	@Test
	public void TC_11_NGuoiNhanNgoaiVCBSoTaiKhoanXacThucBangOTP(String otp) throws GeneralSecurityException, IOException {
		log.info("TC_11_Step_1: Chọn quà tặng may mắn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_11_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, TitleLuckyGift.SOURCE_ACCOUNT);
		sourceAccount = luckyGift.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		String moneySurplus = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] moneySurplusSplit = moneySurplus.split(" ");
		String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
		long surplus = Long.parseLong(moneySurplusReplace);

		log.info("TC_11_Step_3: chọn hình thức gửi quà");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThuc");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.USER_OUTVCB);

		log.info("TC_11_Step_4: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_11_Step_5: nhập số tài khoản");
		luckyGift.inputToDynamicInputBox(driver, getDataInCell(4), TitleLuckyGift.NUMBER_ACCOUNT);

		log.info("TC_11_Step_6: ngần hàng thụ hưởng");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvBank");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK_DBA);

		log.info("TC_11_Step_7: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_11_Step_8: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, TitleLuckyGift.TITLE_WISHES);
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.hideKeyBoard(driver);

		log.info("TC_11_Step_9: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_11_Step_11: lấy ra tài khoản thụ hưởng");
		String user = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		String[] userSplit = user.split("/");
		beneficiary_account = userSplit[0];

		log.info("TC_11_Step_12: lấy ra lời chúc");
		wishes = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLoichuc");

		log.info("TC_01_Step_13: lấy ra ten nguoi nhan");
		String userName = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		getName = userName.split("/");

		log.info("TC_11_Step_14: lấy ra số tiền chuyển");
		money = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSumAmount");

		log.info("TC_11_Step_15: lấy ra phí chuyển");
		moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		long sumFeeInt = convertMoneyToLong(moneyFee, "VND") ;
		long surplusTotal = surplus - Long.parseLong(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_11_Step_16: chọn phương thức xác thực OTP");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvptxt");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.OTP);

		log.info("TC_11_Step_17: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_11_Step_18: điền OTP");
		luckyGift.inputToDynamicOtp(driver, otp, TitleLuckyGift.NEXT);

		log.info("TC_11_Step_19: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_20: kiem tra loi chuc");
		String getWish = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/content");
		verifyEquals(wishes, getWish);

		log.info("TC_01_Step_21: lấy ra tên người thụ hưởng");
		beneficiary_account = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/info_receiver");
		verifyEquals(beneficiary_account, getName[1]);

		log.info("TC_01_Step_22: kiem tra so tien chuyen di");
		String getMoney = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/amount");
		verifyEquals(money, getMoney);

		log.info("TC_01_Step_23: kiem tra so tai khoan nguoi nhan");
		String getAccount = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAcc");
		verifyEquals(getName[0], getAccount);

		log.info("TC_11_Step_24: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.NEW_TRANSFER);

		log.info("TC_11_Step_25: chọn tai khoản");
		luckyGift.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_11_Step_26: kiểm tra số dư");
		String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, account);
		String[] moneyTransferSplit = moneyTransfer.split(" ");
		String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
		verifyEquals(moneyTransferReplace, surplusString);

		log.info("TC_11_Step_27: chọn đóng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.CLOSE);

		log.info("TC_11_Step_28: Lấy thời gian thực hiện giao dịch");
		date = new Date();

		log.info("TC_11_Step_29 : Click home");
		luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");
	}

	@Test
	public void TC_12_KiemTraChiTietGiaoDichChuyenTienQuaTangMayManNgoaiVCBVaXacThucBangOTP() {

		log.info("TC_12_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_12_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_12_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_12_Step_05: Chon quà tặng may mắn");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TITLE);

		log.info("TC_12_Step_06: click chọn tài khoản");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_12_Step_07: chọn đóng");
		transReport.clickToTextID(driver, "com.VCB:id/cancel_button");

		log.info("TC_12_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, TitleLuckyGift.FIND);

		log.info("TC_12_Step_9: Kiem tra ngày giao dịch");
		String dateSuccess = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvDate");
		String[] dateSplit = dateSuccess.split(" ");
		verifyEquals(dateSplit[0], formatter.format(date));

		log.info("TC_12_Step_10: Kiem tra noi dung hien thi");
		String content = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent");
		verifyEquals(content, wishes.trim());

		log.info("TC_12_Step_11: Kiem tra số tiền chuyển đi");
		String getMoney = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney");
		String[] moneySplit = getMoney.split(" ");
		verifyEquals(moneySplit[1] + " " + moneySplit[2], money);

		log.info("TC_12_Step_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_12_Step_13: Kiem tra ngày giao dịch");
		String getDate = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSFER);
		String[] dateDeal = getDate.split(" ");
		verifyEquals(dateDeal[0], formatter.format(date));

		log.info("TC_12_Step_14: Kiem tra tài khoản nguồn");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_12_Step_15: Kiem tra tài khoản thụ hưởng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.BENEFICIARY_NAME), beneficiary_account);

		log.info("TC_02_Step_16: Kiem tra tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CREDITED), getName[0]);

		log.info("TC_02_Step_17: Kiem tra so tien phi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_FEE), moneyFee);

		log.info("TC_12_Step_18: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_12_Step_19: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_12_Step_21: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	public void TC_13_NGuoiNhanTrongVCBBangSDTXacThucBangSmartOTP() throws GeneralSecurityException, IOException {
		log.info("TC_13_Step_1: Chọn quà tặng may mắn");
		homePage.scrollDownToText(driver, HomePage_Data.Home_Text_Elements.TRANS_STATUS);
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_13_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, TitleLuckyGift.SOURCE_ACCOUNT);
		sourceAccount = luckyGift.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		String moneySurplus = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] moneySurplusSplit = moneySurplus.split(" ");
		String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
		long surplus = Long.parseLong(moneySurplusReplace);

		log.info("TC_13_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_13_Step: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_13_Step_4: chọn hình thức nhận");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThucNhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.TITLE_PHONE_NUMBER);

		log.info("TC_13_Step_5: nhập số điện thoại");
		luckyGift.inputToDynamicInputBox(driver, getDataInCell(5), TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

		log.info("TC_13_Step_6: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_13_Step_7: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, TitleLuckyGift.TITLE_WISHES);
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.hideKeyBoard(driver);

		log.info("TC_13_Step_8: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_13_Step_10: lấy ra lời chúc");
		wishes = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLoichuc");

		log.info("TC_13_Step_11: lấy ra ten nguoi nhan");
		String userName = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		getName = userName.split("/");

		log.info("TC_13_Step_12: lấy ra số tiền chuyển");
		money = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSumAmount");

		log.info("TC_13_Step_13: lấy ra phí chuyển");
		String moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		long sumFeeInt = convertMoneyToLong(moneyFee, "VND");
		long surplusTotal = surplus - Long.parseLong(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_13_Step_14: chọn phương thức xác thực");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvptxt");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.SMART_OTP);

		log.info("TC_13_Step_15: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_13_Step_16: điền otp");
		luckyGift.inputToDynamicSmartOTP(driver, passSmartOTP, "com.VCB:id/otp");
		luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/submit");

		log.info("TC_13_Step_17: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_13_Step_18: kiem tra loi chuc");
		String getWish = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/content");
		verifyEquals(wishes, getWish);

		log.info("TC_13_Step_10: lấy ra tên người thụ hưởng");
		beneficiary_account = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/info_receiver");
		verifyEquals(beneficiary_account, getName[1]);

		log.info("TC_13_Step_16: kiem tra so tien chuyen di");
		String getMoney = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/amount");
		verifyEquals(money, getMoney);

		log.info("TC_13_Step_16: kiem tra so tai khoan nguoi nhan");
		String getAccount = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAcc");
		verifyEquals(getName[0], getAccount);

		log.info("TC_13_Step_17: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.NEW_TRANSFER);

		log.info("TC_13_Step_18: chọn tai khoản");
		luckyGift.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_13_Step_19: kiểm tra số dư");
		String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, account);
		String[] moneyTransferSplit = moneyTransfer.split(" ");
		String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
		verifyEquals(moneyTransferReplace, surplusString);

		log.info("TC_13_Step_21: chọn đóng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.CLOSE);

		log.info("TC_13_Step_21: Lấy thời gian thực hiện giao dịch");
		date = new Date();

		log.info("TC_13_Step_22 : Click home");
		luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");

	}

	@Test
	public void TC_14_BaoCaoTienQuaTangMayManTrongVCBBangSDTVaXacThucBangSmartOTP() {
		log.info("TC_14_Step_02: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_14_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_14_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_14_Step_05: Chon quà tặng may mắn");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TITLE);

		log.info("TC_14_Step_06: click chọn tài khoản");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");
		sourceAccount = luckyGift.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		log.info("TC_14_Step_07: chọn tìm kiếm");
		transReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_14_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, TitleLuckyGift.FIND);

		log.info("TC_14_Step_9: Kiem tra ngày giao dịch");
		String dateSuccess = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvDate");
		String[] dateSplit = dateSuccess.split(" ");
		verifyEquals(dateSplit[0], formatter.format(date));

		log.info("TC_14_Step_10: Kiem tra noi dung hien thi");
		String content = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent");
		verifyEquals(content, wishes.trim());

		log.info("TC_14_Step_11: Kiem tra số tiền chuyển đi");
		String getMoney = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney");
		String[] moneySplit = getMoney.split(" ");
		verifyEquals(moneySplit[1] + " " + moneySplit[2], money);

		log.info("TC_14_Step_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_14_Step_13: Kiem tra ngày giao dịch");
		String getDate = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSFER);
		String[] dateDeal = getDate.split(" ");
		verifyEquals(dateDeal[0], formatter.format(date));

		log.info("TC_14_Step_14: Kiem tra tài khoản nguồn");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_14_Step_15: Kiem tra tên người thụ hưởng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.BENEFICIARY_NAME), beneficiary_account);

		log.info("TC_14_Step_16: Kiem tra tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleLuckyGift.ACCOUNT_CREDITED), getName[0]);

		log.info("TC_14_Step_17: Kiem tra so tien phi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleLuckyGift.TRANSACTION_FEE), moneyFee);

		log.info("TC_14_Step_18: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, TitleLuckyGift.TRANSACTION_DETAIL);

		log.info("TC_14_Step_19: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_14_Step_20: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Test
	public void TC_15_NGuoiNhanTrongVCBSoTaiKhoanXacThucBangSmartOTP() {
		log.info("TC_15_Step_1: Chọn quà tặng may mắn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_15_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, TitleLuckyGift.SOURCE_ACCOUNT);
		sourceAccount = luckyGift.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		String moneySurplus = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] moneySurplusSplit = moneySurplus.split(" ");
		String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
		long surplus = Long.parseLong(moneySurplusReplace);

		log.info("TC_15_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_15_Step_5: nhập số tài khoản");
		luckyGift.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

		log.info("TC_15_Step_6: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_15_Step_7: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, TitleLuckyGift.TITLE_WISHES);
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.hideKeyBoard(driver);

		log.info("TC_15_Step_8: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_15_Step_10: lấy ra tài khoản thụ hưởng");
		String user = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		String[] userSplit = user.split("/");
		beneficiary_account = userSplit[1];

		log.info("TC_15_Step_11: lấy ra lời chúc");
		wishes = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLoichuc");

		log.info("TC_01_Step_12: lấy ra ten nguoi nhan");
		String userName = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		getName = userName.split("/");

		log.info("TC_15_Step_13: lấy ra số tiền chuyển");
		money = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSumAmount");

		log.info("TC_15_Step_14: lấy ra phí chuyển");
		String moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		long sumFeeInt = convertMoneyToLong(moneyFee, "VND");
		long surplusTotal = surplus - Long.parseLong(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_15_Step_15: chọn phương thức xác thực OTP");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvptxt");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.SMART_OTP);

		log.info("TC_15_Step_16: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_15_Step_17: điền OTP");
		luckyGift.inputToDynamicSmartOTP(driver, passSmartOTP, "com.VCB:id/otp");
		luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/submit");

		log.info("TC_15_Step_18: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_19: kiem tra loi chuc");
		String getWish = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/content");
		verifyEquals(wishes, getWish);

		log.info("TC_01_Step_20: lấy ra tên người thụ hưởng");
		beneficiary_account = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/info_receiver");
		verifyEquals(beneficiary_account, getName[1]);

		log.info("TC_01_Step_21: kiem tra so tien chuyen di");
		String getMoney = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/amount");
		verifyEquals(money, getMoney);

		log.info("TC_01_Step_22: kiem tra so tai khoan nguoi nhan");
		String getAccount = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAcc");
		verifyEquals(getName[0], getAccount);

		log.info("TC_15_Step_23: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.NEW_TRANSFER);

		log.info("TC_15_Step_24: chọn tai khoản");
		luckyGift.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_15_Step_25: kiểm tra số dư");
		String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, account);
		String[] moneyTransferSplit = moneyTransfer.split(" ");
		String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
		verifyEquals(moneyTransferReplace, surplusString);

		log.info("TC_15_Step_26: chọn đóng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.CLOSE);

		log.info("TC_15_Step_27: Lấy thời gian thực hiện giao dịch");
		date = new Date();

		log.info("TC_15_Step_28 : Click home");
		luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");
	}

	@Test
	public void TC_16_KiemTraChiTietGiaoDichChuyenTienQuaTangMayManTrongVCBVaXacThucBangSmartOTP() {

		log.info("TC_16_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_16_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_16_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_16_Step_05: Chon quà tặng may mắn");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TITLE);

		log.info("TC_16_Step_06: click chọn tài khoản");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_16_Step_07: chọn đóng");
		transReport.clickToTextID(driver, "com.VCB:id/cancel_button");

		log.info("TC_16_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, TitleLuckyGift.FIND);

		log.info("TC_16_Step_9: Kiem tra ngày giao dịch");
		String dateSuccess = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvDate");
		String[] dateSplit = dateSuccess.split(" ");
		verifyEquals(dateSplit[0], formatter.format(date));

		log.info("TC_16_Step_10: Kiem tra noi dung hien thi");
		String content = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent");
		verifyEquals(content, wishes.trim());

		log.info("TC_16_Step_11: Kiem tra số tiền chuyển đi");
		String getMoney = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney");
		String[] moneySplit = getMoney.split(" ");
		verifyEquals(moneySplit[1] + " " + moneySplit[2], money);

		log.info("TC_16_Step_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_16_Step_13: Kiem tra ngày giao dịch");
		String getDate = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSFER);
		String[] dateDeal = getDate.split(" ");
		verifyEquals(dateDeal[0], formatter.format(date));

		log.info("TC_16_Step_14: Kiem tra tài khoản nguồn");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_16_Step_16: Kiem tra tài khoản thụ hưởng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.BENEFICIARY_NAME), beneficiary_account);

		log.info("TC_02_Step_17: Kiem tra tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleLuckyGift.ACCOUNT_CREDITED), getName[0]);

		log.info("TC_02_Step_18: Kiem tra so tien phi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleLuckyGift.TRANSACTION_FEE), moneyFee);

		log.info("TC_16_Step_19: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, TitleLuckyGift.TRANSACTION_DETAIL);

		log.info("TC_16_Step_20: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_16_Step_21: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Test
	public void TC_17_NGuoiNhanNgoaiVCBSoTaiKhoanXacThucBangOTP() throws GeneralSecurityException, IOException {
		log.info("TC_17_Step_1: Chọn quà tặng may mắn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_17_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, TitleLuckyGift.SOURCE_ACCOUNT);
		sourceAccount = luckyGift.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

		String moneySurplus = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] moneySurplusSplit = moneySurplus.split(" ");
		String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
		long surplus = Long.parseLong(moneySurplusReplace);

		log.info("TC_17_Step_3: chọn hình thức gửi quà");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThuc");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.USER_OUTVCB);

		log.info("TC_17_Step_4: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_17_Step_5: nhập số tài khoản");
		luckyGift.inputToDynamicInputBox(driver, getDataInCell(4), TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

		log.info("TC_17_Step_6: ngần hàng thụ hưởng");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvBank");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK_DBA);

		log.info("TC_17_Step_7: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_17_Step_8: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, TitleLuckyGift.TITLE_WISHES);
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.hideKeyBoard(driver);

		log.info("TC_17_Step_9: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_17_Step_11: lấy ra tài khoản thụ hưởng");
		String user = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		String[] userSplit = user.split("/");
		beneficiary_account = userSplit[0];

		log.info("TC_17_Step_12: lấy ra lời chúc");
		wishes = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLoichuc");

		log.info("TC_01_Step_13: lấy ra ten nguoi nhan");
		String userName = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		getName = userName.split("/");

		log.info("TC_17_Step_14: lấy ra số tiền chuyển");
		money = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSumAmount");

		log.info("TC_17_Step_15: lấy ra phí chuyển");
		String moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		long sumFeeInt = convertMoneyToLong(moneyFee, "VND");
		long surplusTotal = surplus - Long.parseLong(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_17_Step_16: chọn phương thức xác thực OTP");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvptxt");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.SMART_OTP);

		log.info("TC_17_Step_17: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_17_Step_18: điền OTP");
		luckyGift.inputToDynamicSmartOTP(driver, passSmartOTP, "com.VCB:id/otp");
		luckyGift.clickToDynamicAcceptButton(driver, "com.VCB:id/submit");

		log.info("TC_17_Step_19: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_20: kiem tra loi chuc");
		String getWish = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/content");
		verifyEquals(wishes, getWish);

		log.info("TC_01_Step_21: lấy ra tên người thụ hưởng");
		beneficiary_account = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/info_receiver");
		verifyEquals(beneficiary_account, getName[1]);

		log.info("TC_01_Step_22: kiem tra so tien chuyen di");
		String getMoney = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/amount");
		verifyEquals(money, getMoney);

		log.info("TC_01_Step_23: kiem tra so tai khoan nguoi nhan");
		String getAccount = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAcc");
		verifyEquals(getName[0], getAccount);

		log.info("TC_17_Step_24: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.NEW_TRANSFER);

		log.info("TC_17_Step_25: chọn tai khoản");
		luckyGift.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_17_Step_26: kiểm tra số dư");
		String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, account);
		String[] moneyTransferSplit = moneyTransfer.split(" ");
		String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
		verifyEquals(moneyTransferReplace, surplusString);

		log.info("TC_17_Step_27: chọn đóng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.CLOSE);

		log.info("TC_17_Step_28: Lấy thời gian thực hiện giao dịch");
		date = new Date();

		log.info("TC_17_Step_29 : Click home");
		luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");
	}

	@Test
	public void TC_18_KiemTraChiTietGiaoDichChuyenTienQuaTangMayManNgoaiVCBVaXacThucBangSmartOTP() {

		log.info("TC_18_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_18_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_18_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_18_Step_05: Chon quà tặng may mắn");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TITLE);

		log.info("TC_18_Step_06: click chọn tài khoản");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_18_Step_07: chọn đóng");
		transReport.clickToTextID(driver, "com.VCB:id/cancel_button");

		log.info("TC_18_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, TitleLuckyGift.FIND);

		log.info("TC_18_Step_9: Kiem tra ngày giao dịch");
		String dateSuccess = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvDate");
		String[] dateSplit = dateSuccess.split(" ");
		verifyEquals(dateSplit[0], formatter.format(date));

		log.info("TC_18_Step_10: Kiem tra noi dung hien thi");
		String content = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent");
		verifyEquals(content, wishes.trim());

		log.info("TC_18_Step_11: Kiem tra số tiền chuyển đi");
		String getMoney = transReport.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvMoney");
		String[] moneySplit = getMoney.split(" ");
		verifyEquals(moneySplit[1] + " " + moneySplit[2], money);

		log.info("TC_18_Step_12: Click vao giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_18_Step_13: Kiem tra ngày giao dịch");
		String getDate = transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TIME_TRANSFER);
		String[] dateDeal = getDate.split(" ");
		verifyEquals(dateDeal[0], formatter.format(date));

		log.info("TC_18_Step_14: Kiem tra tài khoản nguồn");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), account);

		log.info("TC_18_Step_15: Kiem tra tài khoản thụ hưởng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.BENEFICIARY_NAME), beneficiary_account);

		log.info("TC_02_Step_16: Kiem tra tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CREDITED), getName[0]);

		log.info("TC_02_Step_17: Kiem tra so tien phi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_FEE), moneyFee);

		log.info("TC_18_Step_18: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_18_Step_19: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_18_Step_21: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//		closeApp();
//		service.stop();
	}

}
