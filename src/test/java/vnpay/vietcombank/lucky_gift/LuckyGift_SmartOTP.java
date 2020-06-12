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
import vietcombank_test_data.LuckyGift_Data;
import vietcombank_test_data.LuckyGift_Data.TitleLuckyGift;
import vietcombank_test_data.TransactionReport_Data.ReportTitle;

public class LuckyGift_SmartOTP extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private LuckyGiftPageObject luckyGift;
	private HomePageObject homePage;
	private TransactionReportPageObject transReport;
	private String source_account;
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
	String passSmartOTP  = "111222";
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
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		smartOTP.setupSmartOTP(passSmartOTP, getDataInCell(6));
	}
	
	@Test
	public void TC_01_NGuoiNhanTrongVCBBangSDTXacThucBangOTP() throws GeneralSecurityException, IOException {
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

		log.info("TC_01_Step_4: chọn hình thức nhận");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThucNhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.TITLE_PHONE_NUMBER);

		log.info("TC_01_Step_5: nhập số điện thoại");
		luckyGift.inputToDynamicInputBox(driver, getDataInCell(5), TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

		log.info("TC_01_Step_6: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_01_Step_7: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, TitleLuckyGift.TITLE_WISHES);
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.hideKeyBoard(driver);

		log.info("TC_01_Step_8: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_9: lấy ra tài khoản nguồn");
		source_account = luckyGift.getDynamicTextInTransactionDetail(driver, TitleLuckyGift.SOURCE_ACCOUNT);

		log.info("TC_01_Step_10: lấy ra lời chúc");
		wishes = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLoichuc");

		log.info("TC_01_Step_11: lấy ra ten nguoi nhan");
		String userName = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		getName = userName.split("/");

		log.info("TC_01_Step_12: lấy ra số tiền chuyển");
		money = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSumAmount");

		log.info("TC_01_Step_13: lấy ra phí chuyển");
		String moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		long sumFeeInt = convertMoneyToLong(moneyFee, "VND") ;
		long surplusTotal = surplus - Long.parseLong(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_01_Step_14: chọn phương thức xác thực");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvptxt");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.OTP);

		log.info("TC_01_Step_15: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_16: điền otp");
		luckyGift.inputToDynamicSmartOtp(driver, passSmartOTP, "2");

		log.info("TC_01_Step_17: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_01_Step_18: kiem tra loi chuc");
		String getWish = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/content");
		verifyEquals(wishes, getWish);

		log.info("TC_01_Step_10: lấy ra tên người thụ hưởng");
		beneficiary_account = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/info_receiver");
		verifyEquals(beneficiary_account, getName[1]);

		log.info("TC_01_Step_16: kiem tra so tien chuyen di");
		String getMoney = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/amount");
		verifyEquals(money, getMoney);

		log.info("TC_01_Step_16: kiem tra so tai khoan nguoi nhan");
		String getAccount = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAcc");
		verifyEquals(getName[0], getAccount);

		log.info("TC_01_Step_17: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.NEW_TRANSFER);

		log.info("TC_01_Step_18: chọn tai khoản");
		luckyGift.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_01_Step_19: kiểm tra số dư");
		String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, account);
		String[] moneyTransferSplit = moneyTransfer.split(" ");
		String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
		verifyEquals(moneyTransferReplace, surplusString);

		log.info("TC_01_Step_21: chọn đóng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.CLOSE);

		log.info("TC_01_Step_21: Lấy thời gian thực hiện giao dịch");
		date = new Date();

		log.info("TC_01_Step_22 : Click home");
		luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");

	}

	@Test
	public void TC_02_BaoCaoTienQuaTangMayManTrongVCBBangSDTVaXacThucBangOTP() {
		log.info("TC_02_Step_02: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_02_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.ALL_TYPE_TRANSACTION);

		log.info("TC_02_Step_05: Chon quà tặng may mắn");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, ReportTitle.TITLE);

		log.info("TC_02_Step_06: click chọn tài khoản");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");
		sourceAccount = luckyGift.chooseSourceAccount(driver, Constants.MONEY_CHECK_VND, "VND");
		account = sourceAccount.account;

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
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), source_account);

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

	@Test
	public void TC_03_NGuoiNhanTrongVCBSoTaiKhoanXacThucBangOTP() {
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

		log.info("TC_03_Step_5: nhập số tài khoản");
		luckyGift.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

		log.info("TC_03_Step_6: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_03_Step_7: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, TitleLuckyGift.TITLE_WISHES);
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.hideKeyBoard(driver);

		log.info("TC_03_Step_8: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_03_Step_9: lấy ra tài khoản nguồn");
		source_account = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAccSource");

		log.info("TC_03_Step_10: lấy ra tài khoản thụ hưởng");
		String user = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		String[] userSplit = user.split("/");
		beneficiary_account = userSplit[1];

		log.info("TC_03_Step_11: lấy ra lời chúc");
		wishes = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLoichuc");

		log.info("TC_01_Step_12: lấy ra ten nguoi nhan");
		String userName = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		getName = userName.split("/");

		log.info("TC_03_Step_13: lấy ra số tiền chuyển");
		money = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSumAmount");

		log.info("TC_03_Step_14: lấy ra phí chuyển");
		String moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		long sumFeeInt = convertMoneyToLong(moneyFee, "VND") ;
		long surplusTotal = surplus - Long.parseLong(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_03_Step_15: chọn phương thức xác thực OTP");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvptxt");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.OTP);

		log.info("TC_03_Step_16: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_03_Step_17: điền OTP");
		luckyGift.inputToDynamicSmartOtp(driver, passSmartOTP, "2");

		log.info("TC_03_Step_18: Click tiep tuc popup");
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

		log.info("TC_03_Step_23: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.NEW_TRANSFER);

		log.info("TC_03_Step_24: chọn tai khoản");
		luckyGift.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_03_Step_25: kiểm tra số dư");
		String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, account);
		String[] moneyTransferSplit = moneyTransfer.split(" ");
		String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
		verifyEquals(moneyTransferReplace, surplusString);

		log.info("TC_03_Step_26: chọn đóng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.CLOSE);

		log.info("TC_03_Step_27: Lấy thời gian thực hiện giao dịch");
		date = new Date();

		log.info("TC_03_Step_28 : Click home");
		luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");
	}

	@Test
	public void TC_04_KiemTraChiTietGiaoDichChuyenTienQuaTangMayManTrongVCBVaXacThucBangOTP() {

		log.info("TC_04_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
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

		log.info("TC_04_Step_07: chọn đóng");
		transReport.clickToTextID(driver, "com.VCB:id/cancel_button");

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
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_04_Step_16: Kiem tra tài khoản thụ hưởng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.BENEFICIARY_NAME), beneficiary_account);

		log.info("TC_02_Step_17: Kiem tra tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleLuckyGift.ACCOUNT_CREDITED), getName[0]);

		log.info("TC_02_Step_18: Kiem tra so tien phi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, TitleLuckyGift.TRANSACTION_FEE), moneyFee);

		log.info("TC_04_Step_19: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, TitleLuckyGift.TRANSACTION_DETAIL);

		log.info("TC_04_Step_20: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_04_Step_21: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Test
	public void TC_05_NGuoiNhanNgoaiVCBSoTaiKhoanXacThucBangOTP() throws GeneralSecurityException, IOException {
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
		luckyGift.inputToDynamicInputBox(driver, getDataInCell(4), TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

		log.info("TC_05_Step_6: ngần hàng thụ hưởng");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvBank");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.BANK_DBA);

		log.info("TC_05_Step_7: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_05_Step_8: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, TitleLuckyGift.TITLE_WISHES);
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.hideKeyBoard(driver);

		log.info("TC_05_Step_9: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_05_Step_10: lấy ra tài khoản nguồn");
		source_account = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAccSource");

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
		String moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		long sumFeeInt = convertMoneyToLong(moneyFee, "VND") ;
		long surplusTotal = surplus - Long.parseLong(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_05_Step_16: chọn phương thức xác thực OTP");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvptxt");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.OTP);

		log.info("TC_05_Step_17: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, TitleLuckyGift.NEXT);

		log.info("TC_05_Step_18: điền OTP");
		luckyGift.inputToDynamicOtp(driver, passSmartOTP, TitleLuckyGift.NEXT);

		log.info("TC_05_Step_19: Click tiep tuc popup");
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

		log.info("TC_05_Step_24: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.NEW_TRANSFER);

		log.info("TC_05_Step_25: chọn tai khoản");
		luckyGift.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_05_Step_26: kiểm tra số dư");
		String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, account);
		String[] moneyTransferSplit = moneyTransfer.split(" ");
		String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
		verifyEquals(moneyTransferReplace, surplusString);

		log.info("TC_05_Step_27: chọn đóng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, TitleLuckyGift.CLOSE);

		log.info("TC_05_Step_28: Lấy thời gian thực hiện giao dịch");
		date = new Date();

		log.info("TC_05_Step_29 : Click home");
		luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");
	}

	@Test
	public void TC_06_KiemTraChiTietGiaoDichChuyenTienQuaTangMayManNgoaiVCBVaXacThucBangOTP() {

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

		log.info("TC_06_Step_07: chọn đóng");
		transReport.clickToTextID(driver, "com.VCB:id/cancel_button");

		log.info("TC_06_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, TitleLuckyGift.FIND);

		log.info("TC_06_Step_9: Kiem tra ngày giao dịch");
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
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CARD), source_account);

		log.info("TC_06_Step_15: Kiem tra tài khoản thụ hưởng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.BENEFICIARY_NAME), beneficiary_account);

		log.info("TC_02_Step_16: Kiem tra tai khoan ghi co");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.ACCOUNT_CREDITED), getName[0]);

		log.info("TC_02_Step_17: Kiem tra so tien phi");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, ReportTitle.TRANSACTION_FEE), moneyFee);

		log.info("TC_06_Step_18: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_DETAIL);

		log.info("TC_06_Step_19: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, ReportTitle.TRANSACTION_REPORT);

		log.info("TC_06_Step_21: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}