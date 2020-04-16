package vnpay.vietcombank.lucky_gift;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.LuckyGiftPageObject;
import pageObjects.TransactionReportPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.LuckyGift_Data;
import vietcombank_test_data.LuckyGift_Data.TitleLuckyGift;

public class Limit_LuckyGift extends Base {
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
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		luckyGift = PageFactoryManager.getLuckyGiftPageObject(driver);
		homePage = PageFactoryManager.getHomePageObject(driver);
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);

	}

	@Parameters({ "pass" })
	@Test
	public void TC_01_ChuyenTienQuaThapHonHanMucToiThieu(String pass) {

		log.info("TC_01_Step_1: Chọn quà tặng may mắn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_01_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		String moneySurplus = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] moneySurplusSplit = moneySurplus.split(" ");
		String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
		int surplus = Integer.parseInt(moneySurplusReplace);

		log.info("TC_01_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_01_Step_4: Click tiep tuc popup");
		luckyGift.waitUntilPopUpDisplay("Hướng dẫn");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_5: chọn hình thức nhận");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThucNhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Số điện thoại");

		log.info("TC_01_Step_5: nhập số điện thoại");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MOBI_ACCEPT, TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

		log.info("TC_01_Step_6: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.Limit_Money_Gift.MIN_MONEY_A_TRANSACTION, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_01_Step_7: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, "Nhập/chọn lời chúc");
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.clickToTextID(driver, "com.VCB:id/content_counter");

		log.info("TC_01_Step_8: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_13: lấy ra phí chuyển");
		String moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		String[] sumFee = moneyFee.split(" ");
		int sumFeeInt = Integer.parseInt(sumFee[0].replace(",", ""));
		int surplusTotal = surplus - Integer.parseInt(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_01_Step_14: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_15: điền mât khẩu");
		luckyGift.inputToDynamicInputBox(driver, pass, "Nhập mật khẩu");

		log.info("TC_01_Step_16: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_10: lấy ra tên người thụ hưởng");
		beneficiary_account = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/info_receiver");

		log.info("TC_01_Step_17: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Giao dịch mới");

		log.info("TC_01_Step_22 : Click home");
		luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");

	}

	@Test
	public void TC_02_BaoCaoTienQuaTangMayManTrongVCBBangSDTVaXacThucBangMK() {
		log.info("TC_02_Step_02: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step_03: Click Bao Cao Dao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TRANSFER_REPORT);

		log.info("TC_02_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TITLE_TRANSFER);

		log.info("TC_02_Step_05: Chon quà tặng may mắn");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Quà tặng may mắn");

		log.info("TC_02_Step_06: click chọn tài khoản");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_02_Step_07: chọn tìm kiếm");
		transReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_02_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

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
		String getDate = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		String[] dateDeal = getDate.split(" ");
		verifyEquals(dateDeal[0], formatter.format(date));

		log.info("TC_02_Step_14: Kiem tra tài khoản nguồn");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), source_account);

		log.info("TC_02_Step_15: Kiem tra tên người thụ hưởng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), beneficiary_account);

		log.info("TC_02_Step_16: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_02_Step_17: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_02_Step_20: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_03_NGuoiNhanTrongVCBBangSTKXacThucBangMatKhau(String pass) {
		log.info("TC_03_Step_1: Chọn quà tặng may mắn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_03_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		String moneySurplus = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] moneySurplusSplit = moneySurplus.split(" ");
		String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
		int surplus = Integer.parseInt(moneySurplusReplace);

		log.info("TC_03_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_03_Step_5: nhập số tài khoản");
		luckyGift.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

		log.info("TC_03_Step_6: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_03_Step_7: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, "Nhập/chọn lời chúc");
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.clickToTextID(driver, "com.VCB:id/content_counter");

		log.info("TC_03_Step_8: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_9: lấy ra tài khoản nguồn");
		source_account = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAccSource");

		log.info("TC_03_Step_11: lấy ra lời chúc");
		wishes = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLoichuc");

		log.info("TC_03_Step_12: lấy ra số tiền chuyển");
		money = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSumAmount");

		log.info("TC_03_Step_13: lấy ra phí chuyển");
		String moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		String[] sumFee = moneyFee.split(" ");
		int sumFeeInt = Integer.parseInt(sumFee[0].replace(",", ""));
		int surplusTotal = surplus - Integer.parseInt(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_03_Step_14: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_15: điền mât khẩu");
		luckyGift.inputToDynamicInputBox(driver, pass, "Nhập mật khẩu");

		log.info("TC_03_Step_16: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03_Step_10: lấy ra tài khoản thụ hưởng");
		beneficiary_account = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAcc");

		log.info("TC_03_Step_17: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Giao dịch mới");

		log.info("TC_03_Step_18: chọn tai khoản");
		luckyGift.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_03_Step_19: kiểm tra số dư");
		String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, Account_Data.Valid_Account.ACCOUNT1);
		String[] moneyTransferSplit = moneyTransfer.split(" ");
		String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
		verifyEquals(moneyTransferReplace, surplusString);

		log.info("TC_07_Step_21: chọn đóng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_03_Step_21: Lấy thời gian thực hiện giao dịch");
		date = new Date();

		log.info("TC_03_Step_22 : Click home");
		luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");

	}

	@Test
	public void TC_04_BaoCaoTienQuaTangMayManTrongVCBBangSTKVaXacThucBangMK() {
		log.info("TC_04_Step_02: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_04_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TRANSFER_REPORT);

		log.info("TC_04_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TITLE_TRANSFER);

		log.info("TC_04_Step_05: Chon quà tặng may mắn");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Quà tặng may mắn");

		log.info("TC_04_Step_06: click chọn tài khoản");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_04_Step_07: chọn tìm kiếm");
		transReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_04_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

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
		String getDate = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		String[] dateDeal = getDate.split(" ");
		verifyEquals(dateDeal[0], formatter.format(date));

		log.info("TC_04_Step_14: Kiem tra tài khoản nguồn");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), source_account);

		log.info("TC_04_Step_15: Kiem tra tài khoản thụ hưởng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), beneficiary_account);

		log.info("TC_04_Step_16: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_04_Step_17: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_04_Step_20: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "pass" })
	@Test
	public void TC_05_NGuoiNhanNgoaiVCBSoTaiKhoanXacThucBangMK(String pass) {
		log.info("TC_05_Step_1: Chọn quà tặng may mắn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_05_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		String moneySurplus = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] moneySurplusSplit = moneySurplus.split(" ");
		String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
		int surplus = Integer.parseInt(moneySurplusReplace);

		log.info("TC_05_Step_3: chọn hình thức gửi quà");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThuc");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận khác VCB");

		log.info("TC_05_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_05_Step_5: nhập số tài khoản");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.ACCOUNT_ACCEPT_OUT_VCB, TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

		log.info("TC_05_Step_5: ngần hàng thụ hưởng");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvBank");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng DAB");

		log.info("TC_05_Step_6: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_05_Step_7: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, "Nhập/chọn lời chúc");
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.clickToTextID(driver, "com.VCB:id/content_counter");

		log.info("TC_05_Step_8: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_9: lấy ra tài khoản nguồn");
		source_account = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAccSource");

		log.info("TC_05_Step_10: lấy ra tài khoản thụ hưởng");
		String user = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		String[] userSplit = user.split("/");
		beneficiary_account = userSplit[0];

		log.info("TC_05_Step_11: lấy ra lời chúc");
		wishes = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLoichuc");

		log.info("TC_05_Step_12: lấy ra số tiền chuyển");
		money = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSumAmount");

		log.info("TC_05_Step_13: lấy ra phí chuyển");
		String moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		String[] sumFee = moneyFee.split(" ");
		int sumFeeInt = Integer.parseInt(sumFee[0].replace(",", ""));
		int surplusTotal = surplus - Integer.parseInt(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_05_Step_15: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_15: điền mât khẩu");
		luckyGift.inputToDynamicInputBox(driver, pass, "Nhập mật khẩu");

		log.info("TC_05_Step_17: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_18: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Giao dịch mới");

		log.info("TC_05_Step_19: chọn tai khoản");
		luckyGift.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_05_Step_20: kiểm tra số dư");
		String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, Account_Data.Valid_Account.ACCOUNT1);
		String[] moneyTransferSplit = moneyTransfer.split(" ");
		String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
		verifyEquals(moneyTransferReplace, surplusString);

		log.info("TC_05_Step_21: chọn đóng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_05_Step_22: Lấy thời gian thực hiện giao dịch");
		date = new Date();
	}

	@Test
	public void TC_06_BaoCaoGiaoDichChuyenTienQuaTangMayManNgoaiVCBVaXacThucBangMK() {
		log.info("TC_06_Step_01 : Click home");
		luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");

		log.info("TC_06_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_06_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TRANSFER_REPORT);

		log.info("TC_06_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TITLE_TRANSFER);

		log.info("TC_06_Step_05: Chon quà tặng may mắn");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Quà tặng may mắn");

		log.info("TC_06_Step_06: click chọn tài khoản");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_06_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

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
		String getDate = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		String[] dateDeal = getDate.split(" ");
		verifyEquals(dateDeal[0], formatter.format(date));

		log.info("TC_06_Step_14: Kiem tra tài khoản nguồn");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), source_account);

		log.info("TC_06_Step_15: Kiem tra tài khoản thụ hưởng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), LuckyGift_Data.LuckyGift.ACCOUNT_ACCEPT_OUT_VCB);

		log.info("TC_06_Step_16: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_06_Step_17: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_02_Step_19: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Parameters({ "otp" })
	@Test
	public void TC_07_NGuoiNhanTrongVCBBangSDTXacThucBangOTP(String otp) {
		log.info("TC_07_Step_1: Chọn quà tặng may mắn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_07_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		String moneySurplus = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] moneySurplusSplit = moneySurplus.split(" ");
		String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
		int surplus = Integer.parseInt(moneySurplusReplace);

		log.info("TC_07_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_07_Step_5: chọn hình thức nhận");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThucNhan");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Số điện thoại");

		log.info("TC_07_Step_5: nhập số điện thoại");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MOBI_ACCEPT, TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

		log.info("TC_07_Step_6: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_07_Step_7: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, "Nhập/chọn lời chúc");
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.clickToTextID(driver, "com.VCB:id/content_counter");

		log.info("TC_07_Step_8: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_Step_9: lấy ra tài khoản nguồn");
		source_account = luckyGift.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn");

		log.info("TC_07_Step_11: lấy ra lời chúc");
		wishes = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLoichuc");

		log.info("TC_07_Step_12: lấy ra số tiền chuyển");
		money = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSumAmount");

		log.info("TC_07_Step_13: lấy ra phí chuyển");
		String moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		String[] sumFee = moneyFee.split(" ");
		int sumFeeInt = Integer.parseInt(sumFee[0]);
		int surplusTotal = surplus - Integer.parseInt(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_07_Step_14: chọn phương thức xác thực");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvptxt");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_07_Step_14: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_Step_15: điền otp");
		luckyGift.inputToDynamicOtp(driver, otp, "Tiếp tục");

		log.info("TC_07_Step_16: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_Step_10: lấy ra tên người thụ hưởng");
		beneficiary_account = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/info_receiver");

		log.info("TC_07_Step_17: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Giao dịch mới");

		log.info("TC_07_Step_18: chọn tai khoản");
		luckyGift.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_07_Step_19: kiểm tra số dư");
		String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, Account_Data.Valid_Account.ACCOUNT1);
		String[] moneyTransferSplit = moneyTransfer.split(" ");
		String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
		verifyEquals(moneyTransferReplace, surplusString);

		log.info("TC_07_Step_21: chọn đóng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

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
		transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TRANSFER_REPORT);

		log.info("TC_08_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TITLE_TRANSFER);

		log.info("TC_08_Step_05: Chon quà tặng may mắn");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Quà tặng may mắn");

		log.info("TC_08_Step_06: click chọn tài khoản");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_08_Step_07: chọn tìm kiếm");
		transReport.clickToDynamicAcceptButton(driver, "com.VCB:id/btSearch");

		log.info("TC_08_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

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
		String getDate = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		String[] dateDeal = getDate.split(" ");
		verifyEquals(dateDeal[0], formatter.format(date));

		log.info("TC_08_Step_14: Kiem tra tài khoản nguồn");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), source_account);

		log.info("TC_08_Step_15: Kiem tra tên người thụ hưởng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), beneficiary_account);

		log.info("TC_08_Step_16: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_08_Step_17: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_08_Step_20: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	@Parameters({ "otp" })
	@Test
	public void TC_09_NGuoiNhanTrongVCBSoTaiKhoanXacThucBangOTP(String otp) {
		log.info("TC_09_Step_1: Chọn quà tặng may mắn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_09_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		String moneySurplus = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] moneySurplusSplit = moneySurplus.split(" ");
		String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
		int surplus = Integer.parseInt(moneySurplusReplace);

		log.info("TC_09_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_09_Step_5: nhập số tài khoản");
		luckyGift.inputToDynamicInputBox(driver, Account_Data.Valid_Account.DEFAULT_ACCOUNT3, TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

		log.info("TC_09_Step_6: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_09_Step_7: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, "Nhập/chọn lời chúc");
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.clickToTextID(driver, "com.VCB:id/content_counter");

		log.info("TC_09_Step_8: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_09_Step_9: lấy ra tài khoản nguồn");
		source_account = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAccSource");

		log.info("TC_09_Step_10: lấy ra tài khoản thụ hưởng");
		String user = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		String[] userSplit = user.split("/");
		beneficiary_account = userSplit[1];

		log.info("TC_09_Step_11: lấy ra lời chúc");
		wishes = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLoichuc");

		log.info("TC_09_Step_12: lấy ra số tiền chuyển");
		money = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSumAmount");

		log.info("TC_09_Step_13: lấy ra phí chuyển");
		String moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		String[] sumFee = moneyFee.split(" ");
		int sumFeeInt = Integer.parseInt(sumFee[0]);
		int surplusTotal = surplus - Integer.parseInt(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_09_Step_14: chọn phương thức xác thực OTP");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvptxt");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_09_Step_15: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_09_Step_16: điền OTP");
		luckyGift.inputToDynamicOtp(driver, otp, "Tiếp tục");

		log.info("TC_09_Step_17: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_09_Step_18: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Giao dịch mới");

		log.info("TC_09_Step_19: chọn tai khoản");
		luckyGift.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_09_Step_20: kiểm tra số dư");
		String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, Account_Data.Valid_Account.ACCOUNT1);
		String[] moneyTransferSplit = moneyTransfer.split(" ");
		String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
		verifyEquals(moneyTransferReplace, surplusString);

		log.info("TC_09_Step_21: chọn đóng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_09_Step_22: Lấy thời gian thực hiện giao dịch");
		date = new Date();

		log.info("TC_09_Step_23 : Click home");
		luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");
	}

	@Test
	public void TC_10_KiemTraChiTietGiaoDichChuyenTienQuaTangMayManTrongVCBVaXacThucBangOTP() {

		log.info("TC_10_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_10_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TRANSFER_REPORT);

		log.info("TC_10_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TITLE_TRANSFER);

		log.info("TC_10_Step_05: Chon quà tặng may mắn");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Quà tặng may mắn");

		log.info("TC_10_Step_06: click chọn tài khoản");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_10_Step_07: chọn đóng");
		transReport.clickToTextID(driver, "com.VCB:id/cancel_button");

		log.info("TC_10_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

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
		String getDate = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		String[] dateDeal = getDate.split(" ");
		verifyEquals(dateDeal[0], formatter.format(date));

		log.info("TC_10_Step_14: Kiem tra tài khoản nguồn");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_10_Step_15: Kiem tra tài khoản thụ hưởng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), beneficiary_account);

		log.info("TC_10_Step_16: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_10_Step_17: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_10_Step_19: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");

	}

	@Parameters({ "otp" })
	@Test
	public void TC_11_NGuoiNhanNgoaiVCBSoTaiKhoanXacThucBangOTP(String otp) {
		log.info("TC_11_Step_1: Chọn quà tặng may mắn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.TitleLuckyGift.TITLE);

		log.info("TC_11_Step_2: chọn tài khoản nguồn");
		luckyGift.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		String moneySurplus = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] moneySurplusSplit = moneySurplus.split(" ");
		String moneySurplusReplace = moneySurplusSplit[0].replace(",", "");
		int surplus = Integer.parseInt(moneySurplusReplace);

		log.info("TC_11_Step_3: chọn hình thức gửi quà");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvHinhThuc");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận khác VCB");

		log.info("TC_11_Step_3: Thêm người nhận");
		luckyGift.clickToDynamicImageViewByID(driver, "com.VCB:id/ivAdd");

		log.info("TC_11_Step_5: nhập số tài khoản");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.ACCOUNT_ACCEPT_OUT_VCB, TitleLuckyGift.TITLE_CHOISE_ACCOUNT);

		log.info("TC_11_Step_5: ngần hàng thụ hưởng");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvBank");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng DAB");

		log.info("TC_11_Step_6: Nhap so tien chuyen");
		luckyGift.inputToDynamicInputBox(driver, LuckyGift_Data.LuckyGift.MONEY, TitleLuckyGift.TITLE_AMOUNT_MONEY);

		log.info("TC_11_Step_7: Chon loi chuc");
		luckyGift.clickToDynamicWishes(driver, "Nhập/chọn lời chúc");
		luckyGift.inputIntoEditTextByID(driver, LuckyGift_Data.LuckyGift.WISHES_OPTION, "com.VCB:id/content");
		luckyGift.clickToTextID(driver, "com.VCB:id/content_counter");

		log.info("TC_11_Step_8: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_11_Step_9: lấy ra tài khoản nguồn");
		source_account = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAccSource");

		log.info("TC_11_Step_10: lấy ra tài khoản thụ hưởng");
		String user = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvReceiver");
		String[] userSplit = user.split("/");
		beneficiary_account = userSplit[0];

		log.info("TC_11_Step_11: lấy ra lời chúc");
		wishes = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvLoichuc");

		log.info("TC_11_Step_12: lấy ra số tiền chuyển");
		money = luckyGift.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvSumAmount");

		log.info("TC_11_Step_13: lấy ra phí chuyển");
		String moneyFee = luckyGift.getTextInDynamicDropdownOrDateTimePicker(driver, "com.VCB:id/tvSumfee");
		String[] sumFee = moneyFee.split(" ");
		int sumFeeInt = Integer.parseInt(sumFee[0].replace(",", ""));
		int surplusTotal = surplus - Integer.parseInt(LuckyGift_Data.LuckyGift.MONEY) - sumFeeInt;
		surplusString = String.valueOf(surplusTotal);

		log.info("TC_11_Step_14: chọn phương thức xác thực OTP");
		luckyGift.clickToTextID(driver, "com.VCB:id/tvptxt");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "SMS OTP");

		log.info("TC_11_Step_15: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_11_Step_16: điền OTP");
		luckyGift.inputToDynamicOtp(driver, otp, "Tiếp tục");

		log.info("TC_11_Step_17: Click tiep tuc popup");
		luckyGift.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_11_Step_18: Click giao dịch mới");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Giao dịch mới");

		log.info("TC_11_Step_19: chọn tai khoản");
		luckyGift.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_11_Step_20: kiểm tra số dư");
		String moneyTransfer = luckyGift.getDynamicTextInTransactionDetail(driver, Account_Data.Valid_Account.ACCOUNT1);
		String[] moneyTransferSplit = moneyTransfer.split(" ");
		String moneyTransferReplace = moneyTransferSplit[0].replace(",", "");
		verifyEquals(moneyTransferReplace, surplusString);

		log.info("TC_11_Step_21: chọn đóng");
		luckyGift.clickToDynamicButtonLinkOrLinkText(driver, "Đóng");

		log.info("TC_11_Step_22: Lấy thời gian thực hiện giao dịch");
		date = new Date();

		log.info("TC_11_Step_23 : Click home");
		luckyGift.clickToDynamicImageViewByID("com.VCB:id/left");
	}

	@Test
	public void TC_12_KiemTraChiTietGiaoDichChuyenTienQuaTangMayManNgoaiVCBVaXacThucBangOTP() {

		log.info("TC_12_Step_02: Click vao More Icon");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_12_Step_03: Click Bao Cao Dao Dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TRANSFER_REPORT);

		log.info("TC_12_Step_04: Click Tat Ca Cac Loai Giao Dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, LuckyGift_Data.LuckyGift.TITLE_TRANSFER);

		log.info("TC_12_Step_05: Chon quà tặng may mắn");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Quà tặng may mắn");

		log.info("TC_12_Step_06: click chọn tài khoản");
		transReport.clickToTextID(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_12_Step_07: chọn đóng");
		transReport.clickToTextID(driver, "com.VCB:id/cancel_button");

		log.info("TC_12_Step_08: CLick Tim Kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");

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
		String getDate = transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch");
		String[] dateDeal = getDate.split(" ");
		verifyEquals(dateDeal[0], formatter.format(date));

		log.info("TC_12_Step_14: Kiem tra tài khoản nguồn");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), source_account);

		log.info("TC_12_Step_15: Kiem tra tài khoản thụ hưởng");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản ghi có"), beneficiary_account);

		log.info("TC_12_Step_16: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, "Chi tiết giao dịch");

		log.info("TC_12_Step_17: Click quay lai");
		transReport.clickToDynamicBackIcon(driver, "Báo cáo giao dịch");

		log.info("TC_12_Step_18: Click quay lai");
		transReport.navigateBack(driver);

		log.info("TC_02_Step_19: Click vao More Icon");
		homePage.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeApp();
		service.stop();
	}

}
