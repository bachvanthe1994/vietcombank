package vnpay.vietcombank.TransferIdentityCard;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePageObject;
import pageObjects.LogInPageObject;
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferIdentiryPageObject;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.TransferIdentity_Data;


public class TransferIdentity extends Base {
	AndroidDriver<AndroidElement> driver;
	private LogInPageObject login;
	private HomePageObject homePage;
	private TransferIdentiryPageObject trasferPage;
	private TransactionReportPageObject transReport;
	private String transferTime;
	private String transactionNumber;


	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName) throws IOException, InterruptedException {
		startServer();
		log.info("Before class: Mo app ");
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);

		login = PageFactoryManager.getLoginPageObject(driver);
		homePage = PageFactoryManager.getHomePageObject(driver);
		trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);

		log.info("Before class: Click Allow Button");
		login.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");

		log.info("TC_06_Step_0");
		login.inputToDynamicLogInTextBox(driver, LogIn_Data.Login_Account.PHONE, "Tiếp tục");

		log.info("TC_06_Step_0");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05_Step_0");
		login.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.NEW_PASSWORD, LogIn_Data.UI.PASSWORD_LABEL);

		log.info("TC_06_Step_0");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_06_Step_0");
		login.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");

		log.info("TC_06_Step_0");
		login.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_06_Step_0");
		login.clickToDynamicButton(driver, "TỪ CHỐI");

	}

	@Test
	public void TC_01_ChuyenTienQuaCMTNguoiChuyenTraPhiVNDXacNhanMatKhau() {
		log.info("TC_01: chon chuyển tiền nhận bằng CMT");
		homePage.scrollToText(driver, "Chuyển tiền nhận bằng CMT");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

		log.info("TC_01: chon tai khoan");
		trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		homePage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);


		log.info("TC_01: lay so tien truoc khi chuyen khoan");
		String overbalanceBefore = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		Long overbalanceBeforeInt = convertMoneyToLong(overbalanceBefore, "VND");


		log.info("TC_01: nhap ten nguoi thu huong");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người hưởng");

		log.info("TC_01: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

		log.info("TC_01: so CMT");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

		log.info("TC_01: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
		trasferPage.clickToDynamicButton(driver, "OK");

		log.info("TC_01: lay ra ngay dang ki CMT");

		trasferPage.scrollToText(driver, "Thông tin giao dịch");

		log.info("TC_01: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);

		log.info("TC_01: nguoi tra phi giao dich");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_01: nhap so tien chuyen di");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_01: noi dung");
		trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER, "Nội dung");

		log.info("TC_01: noi dung");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01: xac nhan thong tin");
		String confirm = trasferPage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
		verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));


		log.info("TC_01: kiem tra tai khoan nguon");
		verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

		log.info("TC_01: kiem tra ten nguoi thu huong");
		verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

		log.info("TC_01: kiem tra giay to tuy than");
		verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Giấy tờ tùy thân"), "Chứng minh nhân dân");

		log.info("TC_01: kiem tra so CMT");
		verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

		trasferPage.scrollToText(driver, "Chọn phương thức xác thực");
		log.info("TC_01: chon phuong thuc xac thuc");
		trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");


		log.info("TC_01: lay ra so tien chuyen di");
		String moneyTransfer = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền");
		Long moneyTransferInt = convertMoneyToLong(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
		String transferContent = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);

		log.info("tc_01: lay phi");
		String amount = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
		Long amountInt = convertMoneyToLong(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01: xac thuc giao dich");
		trasferPage.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
		String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
		verifyTrue(moneyTransfer.equals(transferMoney));

		log.info("TC_01: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
		transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

		log.info("TC_01: ten nguoi thu huong");
		String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
		verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

		log.info("TC_01: tai khoan giao dich");
		String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
		verifyTrue(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER.equals(destinationAccount));

		log.info("TC_01: ma giao dich");
		transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

		log.info("TC_01: noi dung giao dich");
		String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);
		verifyTrue(transferContent.equals(conten));

		log.info("TC_01: xac thuc thuc hien giao dich moi");
		String newDealConfirm = trasferPage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
		verifyTrue(newDealConfirm.equals(TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS));
		trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");


		log.info("TC_01: so tien kha dung con lai trong tai khoan");
		String overbalanceAfter = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		Long overbalanceAfterInt = convertMoneyToLong(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);

		log.info("TC_01: kiem tra so tien kha dung sau khi chuyen");
		long overbalanceAfterCacuLator = overbalanceBeforeInt - moneyTransferInt - amountInt;
		verifyEquals(overbalanceAfterInt, overbalanceAfterCacuLator);

	}

//	@Test
	public void TC_02_KiemTraChiTietGiaoDichChuyenTienQuaCMTNguoiChuyenTraPhiVNDXacNhanMatKhauTrongBaoCaoGiaoDich() {
		log.info("TC_02_Step_: quay lại home page");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");

		log.info("TC_02:Tìm tới menu page");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");

		log.info("TC_02:chon bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_02:click texbox tat ca cac loai giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_02:chon chuyen tien cho nguoi nhan tai quay");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền cho người nhận tại quầy");

		log.info("TC_02:chon ngay thuc hien giao dich");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_02:chon tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

		log.info("TC_02:Thuc hien tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");


		log.info("TC_02: chon ngay thuc hien giao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_02: kiem tra ngay giao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_02: kiem tra noi dung giao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER));

		log.info("TC_02: kiem tra so tien chuyen di");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND) + " " + TransferIdentity_Data.textDataInputForm.CURRENCY_VND));

		log.info("TC_02: click chon giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_02: kiem tra thoi gian giao dich trong man hinh chi tiet");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_02: kiem tra thoi gian giao dich trong man hinh chi tiet");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_02: so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_02: tai khoan the ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

		log.info("TC_02_Step_: ten nguoi thu huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

		log.info("TC_02: giay to tuy than");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Giấy tờ tùy thân"), "Chứng minh nhân dân");

		log.info("TC_02_Step_: so CMT");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

		System.out.println(transReport.getDynamicTextInTransactionDetail(driver, "Ngày cấp"));
		System.out.println(transferTime.split(" ")[0]);
		log.info("TC_02: ngay cap");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Ngày cấp").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_02: noi cap");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);
		log.info("TC_02: noi cap");
		trasferPage.navigateBack(driver);
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
	}

//	@Test
	public void TC_03_ChuyenTienQuaCMTNguoiChuyenNhanPhiEURXacNhanMatKhau() {
		log.info("TC_03: chon chuyển tiền nhận bằng CMT");
		homePage.scrollToText(driver, "Chuyển tiền nhận bằng CMT");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");
		String text = homePage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.PAGE_TRANSFER).trim();
		verifyTrue(text.equals(TransferIdentity_Data.textCheckElement.PAGE_TRANSFER));

		log.info("TC_03: chon tai khoan");
		trasferPage.clickToDynamicAccount(driver, "Tài khoản nguồn");
		trasferPage.scrollToText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_EUR);
		trasferPage.clickToDynamicAcceptText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_EUR);


		log.info("TC_03: lay so tien truoc khi chuyen khoan");
		String overbalanceBefore = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double overbalanceBeforeInt = convertMoneyToDouble(overbalanceBefore, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);

		log.info("TC_03: nhap ten nguoi thu huong");
		trasferPage.inputBeneficiary(TransferIdentity_Data.textDataInputForm.USER_NAME);

		log.info("TC_03: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

		log.info("TC_03: so CMT");
		trasferPage.inputIdentityNumber(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

		log.info("TC_03: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
		trasferPage.clickToDynamicButton(driver, "OK");

		trasferPage.scrollToText(driver, "Thông tin giao dịch");

		log.info("TC_03: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thành phố Hà Nội ");

		log.info("TC_03: nguoi tra phi giao dich");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_03: nhap so tien chuyen di");
		trasferPage.inputMoney(TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR);
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_03: noi dung");
		trasferPage.inputContent(TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER);

		log.info("TC_03: noi dung");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03: xac nhan thong tin");
		String confirm = trasferPage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
		verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

		trasferPage.scrollToText(driver, "Chọn phương thức xác thực");
		log.info("TC_03: chon phuong thuc xac thuc");
		trasferPage.clickToDynamicAcceptText(driver, "Mật khẩu đăng nhập");
		trasferPage.clickToDynamicAcceptText(driver, "Mật khẩu đăng nhập");


		log.info("TC_03: lay ra so tien chuyen di");
		String moneyTransfer = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền(EUR)");
		double moneyTransferInt = convertMoneyToDouble(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);
		String transferContent = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);

		log.info("TC_03: lay phi");
		String amount = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
		double amountInt = convertMoneyToDouble(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03: xac thuc giao dich");
		trasferPage.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_03: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
		String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
		verifyTrue(moneyTransfer.equals(transferMoney));

		log.info("TC_03: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
		transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

		log.info("TC_03: ten nguoi thu huong");
		String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
		verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

		log.info("TC_03: tai khoan giao dich");
		String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
		verifyTrue(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER.equals(destinationAccount));

		log.info("TC_03: ma giao dich");
		transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

		log.info("TC_03: noi dung giao dich");
		String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);
		verifyTrue(transferContent.equals(conten));

		log.info("TC_03: xac thuc thuc hien giao dich moi");
		String newDealConfirm = trasferPage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
		verifyTrue(newDealConfirm.equals(TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS));
		trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");


		log.info("TC_03: so tien kha dung con lai trong tai khoan");
		String overbalanceAfter = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double overbalanceAfterInt = convertMoneyToDouble(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);

		log.info("TC_03: kiem tra so tien kha dung sau khi chuyen");
		double overbalanceAfterCacuLator = overbalanceBeforeInt - moneyTransferInt - amountInt;
		verifyEquals(overbalanceAfterInt, overbalanceAfterCacuLator);
	}

//	@Test
	public void TC_04_KiemTraChiTietGiaoDichChuyenTienQuaCMTNguoiChuyenTraPhiEURXacNhanMatKhauTrongBaoCaoGiaoDich() {
		log.info("TC_04: quay lại home page");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");

		log.info("TC_04:Tìm tới menu page");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");

		log.info("TC_04:chon bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_04:click texbox tat ca cac loai giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_04:chon chuyen tien cho nguoi nhan tai quay");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền cho người nhận tại quầy");

		log.info("TC_04:chon ngay thuc hien giao dich");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_04:chon tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_EUR);

		log.info("TC_04:Thuc hien tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");


		log.info("TC_04: chon ngay thuc hien giao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_04: kiem tra ngay giao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_04: kiem tra noi dung giao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER));

		log.info("TC_04: kiem tra so tien chuyen di");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR) + " " + TransferIdentity_Data.textDataInputForm.CURRENCY_EURO));

		log.info("TC_04: click chon giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_04: kiem tra thoi gian giao dich trong man hinh chi tiet");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_04: kiem tra thoi gian giao dich trong man hinh chi tiet");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_04: so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_04: tai khoan the ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), TransferIdentity_Data.textDataInputForm.ACCOUNT_EUR);

		log.info("TC_04: ten nguoi thu huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

		log.info("TC_04: giay to tuy than");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Giấy tờ tùy thân"), "Chứng minh nhân dân");

		log.info("TC_04: so CMT");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

		log.info("TC_04: ngay cap");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Ngày cấp").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_04: noi cap");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

		log.info("TC_04: noi cap");
		trasferPage.navigateBack(driver);
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
	}

//	@Test
	public void TC_05_ChuyenTienQuaCMTNguoiNhanTraPhiUSDXacNhanMatKhau() {
		log.info("TC_05: chon chuyển tiền nhận bằng CMT");
		homePage.scrollToText(driver, "Chuyển tiền nhận bằng CMT");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");
		String text = homePage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.PAGE_TRANSFER).trim();
		verifyTrue(text.equals(TransferIdentity_Data.textCheckElement.PAGE_TRANSFER));

		log.info("TC_05: chon tai khoan");
		trasferPage.clickToDynamicAccount(driver, "Tài khoản nguồn");
		trasferPage.scrollToText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_USD);
		trasferPage.clickToDynamicAcceptText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_USD);

		log.info("TC_05: lay so tien truoc khi chuyen khoan");
		String overbalanceBefore = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double overbalanceBeforeInt = convertMoneyToDouble(overbalanceBefore, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);

		log.info("TC_05: nhap ten nguoi thu huong");
		trasferPage.inputBeneficiary(TransferIdentity_Data.textDataInputForm.USER_NAME);

		log.info("TC_05: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

		log.info("TC_05: so CMT");
		trasferPage.inputIdentityNumber(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

		log.info("TC_05: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
		trasferPage.clickToDynamicButton(driver, "OK");

		trasferPage.scrollToText(driver, "Thông tin giao dịch");

		log.info("TC_05: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thành phố Hà Nội ");

		log.info("TC_03: nguoi tra phi giao dich");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_03: nguoi tra phi giao dich");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_05: nhap so tien chuyen di");
		trasferPage.inputMoney(TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_USD);
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_05: noi dung");
		trasferPage.inputContent(TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER);

		log.info("TC_05: noi dung");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05: xac nhan thong tin");
		String confirm = trasferPage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
		verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

		trasferPage.scrollToText(driver, "Chọn phương thức xác thực");
		log.info("TC_05: chon phuong thuc xac thuc");
		trasferPage.clickToDynamicAcceptText(driver, "Mật khẩu đăng nhập");
		trasferPage.clickToDynamicAcceptText(driver, "Mật khẩu đăng nhập");


		log.info("TC_05: lay ra so tien chuyen di");
		String moneyTransfer = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền(USD)");
		double moneyTransferInt = convertMoneyToDouble(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);
		String transferContent = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);

		log.info("TC_05: lay phi");
		String amount = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
		double amountInt = convertMoneyToDouble(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05: xac thuc giao dich");
		trasferPage.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_05: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
		String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
		verifyTrue(moneyTransfer.equals(transferMoney));

		log.info("TC_05: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
		transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

		log.info("TC_05: ten nguoi thu huong");
		String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
		verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

		log.info("TC_05: tai khoan giao dich");
		String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
		verifyTrue(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER.equals(destinationAccount));

		log.info("TC_05: ma giao dich");
		transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

		log.info("TC_05: noi dung giao dich");
		String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);
		verifyTrue(transferContent.equals(conten));

		log.info("TC_05: xac thuc thuc hien giao dich moi");
		String newDealConfirm = trasferPage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
		verifyTrue(newDealConfirm.equals(TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS));
		trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_05: so tien kha dung con lai trong tai khoan");
		String overbalanceAfter = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double overbalanceAfterInt = convertMoneyToDouble(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);

		log.info("TC_05: kiem tra so tien kha dung sau khi chuyen");
		double overbalanceAfterCacuLator = overbalanceBeforeInt - moneyTransferInt - amountInt;
		verifyEquals(overbalanceAfterInt, overbalanceAfterCacuLator);
	}

//	@Test
	public void TC_06_KiemTraChiTietGiaoDichChuyenTienQuaCMTNguoiChuyenTraPhiUSDXacNhanMatKhauTrongBaoCaoGiaoDich() {
		log.info("TC_06: quay lại home page");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");

		log.info("TC_06:Tìm tới menu page");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");

		log.info("TC_06:chon bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_06:click texbox tat ca cac loai giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_06:chon chuyen tien cho nguoi nhan tai quay");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền cho người nhận tại quầy");

		log.info("TC_06:chon ngay thuc hien giao dich");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_06:chon tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_USD);

		log.info("TC_06:Thuc hien tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");


		log.info("TC_06: chon ngay thuc hien giao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_06: kiem tra ngay giao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_06: kiem tra noi dung giao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER));

		log.info("TC_06: kiem tra so tien chuyen di");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR) + " " + TransferIdentity_Data.textDataInputForm.CURRENCY_USD));

		log.info("TC_06: click chon giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_06: kiem tra thoi gian giao dich trong man hinh chi tiet");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_06: kiem tra thoi gian giao dich trong man hinh chi tiet");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_06: so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_06: tai khoan the ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), TransferIdentity_Data.textDataInputForm.ACCOUNT_USD);

		log.info("TC_06: ten nguoi thu huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

		log.info("TC_06: giay to tuy than");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Giấy tờ tùy thân"), "Chứng minh nhân dân");

		log.info("TC_06: so CMT");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

		System.out.println(transReport.getDynamicTextInTransactionDetail(driver, "Ngày cấp"));
		System.out.println(transferTime.split(" ")[0]);

		log.info("TC_06: ngay cap");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Ngày cấp").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_06: noi cap");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

		log.info("TC_06: noi cap");
		trasferPage.navigateBack(driver);
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
	}

//	@Test
	public void TC_07_ChuyenTienQuaCMTNguoiChuyenTraPhiVNDRXacNhanOTP() {
		log.info("TC_07: chon chuyển tiền nhận bằng CMT");
		homePage.scrollToText(driver, "Chuyển tiền nhận bằng CMT");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");
		String text = homePage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.PAGE_TRANSFER).trim();
		verifyTrue(text.equals(TransferIdentity_Data.textCheckElement.PAGE_TRANSFER));

		log.info("TC_07: chon tai khoan");
		trasferPage.clickToDynamicAccount(driver, "Tài khoản nguồn");
		trasferPage.scrollToText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);
		trasferPage.clickToDynamicAcceptText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

		log.info("TC_07: lay so tien truoc khi chuyen khoan");
		String overbalanceBefore = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		Long overbalanceBeforeInt = convertMoneyToLong(overbalanceBefore, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);

		log.info("TC_07: nhap ten nguoi thu huong");
		trasferPage.inputBeneficiary(TransferIdentity_Data.textDataInputForm.USER_NAME);

		log.info("TC_07: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

		log.info("TC_07: so CMT");
		trasferPage.inputIdentityNumber(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

		log.info("TC_07: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
		trasferPage.clickToDynamicButton(driver, "OK");

		trasferPage.scrollToText(driver, "Thông tin giao dịch");

		log.info("TC_07: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thành phố Hà Nội ");

		log.info("TC_07: nguoi tra phi giao dich");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_07: nhap so tien chuyen di");
		trasferPage.inputMoney(TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND);
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_07: noi dung");
		trasferPage.inputContent(TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER);

		log.info("TC_07: noi dung");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07: xac nhan thong tin");
		String textConfirm = trasferPage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
		verifyTrue(textConfirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

		trasferPage.scrollToText(driver, "Chọn phương thức xác thực");
		log.info("TC_07: chon phuong thuc xac thuc");
		trasferPage.clickToDynamicAcceptText(driver, "Mật khẩu đăng nhập");
		trasferPage.clickToDynamicAcceptText(driver, "SMS OTP");

		log.info("TC_07: lay ra so tien chuyen di");
		String moneyTransfer = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền");
		Long moneyTransferInt = convertMoneyToLong(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
		String transferContent = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);

		log.info("TC_07: lay phi");
		String amount = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
		Long amountInt = convertMoneyToLong(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07: xac thuc giao dich");
		trasferPage.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
		String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
		verifyTrue(moneyTransfer.equals(transferMoney));

		log.info("TC_07: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
		transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

		log.info("TC_07: ten nguoi thu huong");
		String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
		verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

		log.info("TC_07: tai khoan giao dich");
		String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
		verifyTrue(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER.equals(destinationAccount));

		log.info("TC_07: ma giao dich");
		transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

		log.info("TC_07: noi dung giao dich");
		String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);
		verifyTrue(transferContent.equals(conten));

		log.info("TC_07: xac thuc thuc hien giao dich moi");
		String newDealConfirm = trasferPage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
		verifyTrue(newDealConfirm.equals(TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS));
		trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");


		log.info("TC_01: so tien kha dung con lai trong tai khoan");
		String overbalanceAfter = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		Long overbalanceAfterInt = convertMoneyToLong(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);

		log.info("TC_07: kiem tra so tien kha dung sau khi chuyen");
		long overbalanceAfterCacuLator = overbalanceBeforeInt - moneyTransferInt - amountInt;
		verifyEquals(overbalanceAfterInt, overbalanceAfterCacuLator);

	}

//	@Test
	public void TC_08_KiemTraChiTietGiaoDichChuyenTienQuaCMTNguoiChuyenTraPhiVNDRXacNhanOTPTrongBaoCaoGiaoDich() {
		log.info("TC_08: quay lại home page");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");

		log.info("TC_08:Tìm tới menu page");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");

		log.info("TC_08:chon bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_08:click texbox tat ca cac loai giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_08:chon chuyen tien cho nguoi nhan tai quay");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền cho người nhận tại quầy");

		log.info("TC_08:chon ngay thuc hien giao dich");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_08:chon tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

		log.info("TC_08:Thuc hien tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");


		log.info("TC_08: chon ngay thuc hien giao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_08: kiem tra ngay giao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_08: kiem tra noi dung giao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER));

		log.info("TC_08: kiem tra so tien chuyen di");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToLong(TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND) + " " + TransferIdentity_Data.textDataInputForm.CURRENCY_VND));

		log.info("TC_08: click chon giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_08: kiem tra thoi gian giao dich trong man hinh chi tiet");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_08: kiem tra thoi gian giao dich trong man hinh chi tiet");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_08: so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_08: tai khoan the ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);

		log.info("TC_08: ten nguoi thu huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

		log.info("TC_08: giay to tuy than");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Giấy tờ tùy thân"), "Chứng minh nhân dân");

		log.info("TC_08: so CMT");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

		log.info("TC_08: ngay cap");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Ngày cấp").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_08: noi cap");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

		log.info("TC_08: noi cap");
		trasferPage.navigateBack(driver);
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
	}

//	@Test
	public void TC_09_ChuyenTienQuaCMTNguoiChuyenTraPhiUSDXacNhanOTP() {
		log.info("TC_09: chon chuyển tiền nhận bằng CMT");
		homePage.scrollToText(driver, "Chuyển tiền nhận bằng CMT");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");
		String text = homePage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.PAGE_TRANSFER).trim();
		verifyTrue(text.equals(TransferIdentity_Data.textCheckElement.PAGE_TRANSFER));

		log.info("TC_09: chon tai khoan");
		trasferPage.clickToDynamicAccount(driver, "Tài khoản nguồn");
		trasferPage.scrollToText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_USD);
		trasferPage.clickToDynamicAcceptText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_USD);


		log.info("TC_09: lay so tien truoc khi chuyen khoan");
		String overbalanceBefore = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double overbalanceBeforeInt = convertMoneyToDouble(overbalanceBefore, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);

		log.info("TC_09: nhap ten nguoi thu huong");
		trasferPage.inputBeneficiary(TransferIdentity_Data.textDataInputForm.USER_NAME);

		log.info("TC_09: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

		log.info("TC_09: so CMT");
		trasferPage.inputIdentityNumber(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

		log.info("TC_09: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
		trasferPage.clickToDynamicButton(driver, "OK");

		trasferPage.scrollToText(driver, "Thông tin giao dịch");

		log.info("TC_09: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thành phố Hà Nội ");

		log.info("TC_09: nguoi tra phi giao dich");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_09: nhap so tien chuyen di");
		trasferPage.inputMoney(TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_USD);
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_09: noi dung");
		trasferPage.inputContent(TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER);

		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_09: xac nhan thong tin");
		String confirm = trasferPage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
		verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

		trasferPage.scrollToText(driver, "Chọn phương thức xác thực");

		log.info("TC_09: chon phuong thuc xac thuc");
		trasferPage.clickToDynamicAcceptText(driver, "Mật khẩu đăng nhập");
		trasferPage.clickToDynamicAcceptText(driver, "SMS OTP");


		log.info("TC_09: lay ra so tien chuyen di");
		String moneyTransfer = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền(USD)");
		double moneyTransferInt = convertMoneyToDouble(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);
		String transferContent = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);

		log.info("TC_09: lay phi");
		String amount = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
		double amountInt = convertMoneyToDouble(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_09: xac thuc giao dich");
		trasferPage.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_09: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
		String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
		verifyTrue(moneyTransfer.equals(transferMoney));

		log.info("TC_09: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
		transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

		log.info("TC_09: ten nguoi thu huong");
		String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
		verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

		log.info("TC_09: tai khoan giao dich");
		String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
		verifyTrue(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER.equals(destinationAccount));

		log.info("TC_09: ma giao dich");
		transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

		log.info("TC_09: noi dung giao dich");
		String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);
		verifyTrue(transferContent.equals(conten));

		log.info("TC_09: xac thuc thuc hien giao dich moi");
		String newDealConfirm = trasferPage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
		verifyTrue(newDealConfirm.equals(TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS));
		trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_09: so tien kha dung con lai trong tai khoan");
		String overbalanceAfter = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double overbalanceAfterInt = convertMoneyToDouble(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);

		log.info("TC_09: kiem tra so tien kha dung sau khi chuyen");
		double overbalanceAfterCacuLator = overbalanceBeforeInt - moneyTransferInt - amountInt;
		verifyEquals(overbalanceAfterInt, overbalanceAfterCacuLator);
	}

//	@Test
	public void TC_10_KiemTraChiTietGiaoDichChuyenTienQuaCMTNguoiChuyenTraPhiUSDXacNhanOTPTrongBaoCaoGiaoDich() {
		log.info("TC_10: quay lại home page");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");

		log.info("TC_10:Tìm tới menu page");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");

		log.info("TC_10:chon bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_10:click texbox tat ca cac loai giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_10:chon chuyen tien cho nguoi nhan tai quay");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền cho người nhận tại quầy");

		log.info("TC_10:chon ngay thuc hien giao dich");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_10:chon tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_USD);

		log.info("TC_10:Thuc hien tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");


		log.info("TC_10: chon ngay thuc hien giao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_10: kiem tra ngay giao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_10: kiem tra noi dung giao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER));

		log.info("TC_10: kiem tra so tien chuyen di");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_USD) + " " + TransferIdentity_Data.textDataInputForm.CURRENCY_USD));

		log.info("TC_10: click chon giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_10: kiem tra thoi gian giao dich trong man hinh chi tiet");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_10: kiem tra thoi gian giao dich trong man hinh chi tiet");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_10: so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_10: tai khoan the ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), TransferIdentity_Data.textDataInputForm.ACCOUNT_USD);

		log.info("TC_10: ten nguoi thu huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

		log.info("TC_10: giay to tuy than");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Giấy tờ tùy thân"), "Chứng minh nhân dân");

		log.info("TC_10: so CMT");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

		log.info("TC_10: ngay cap");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Ngày cấp").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_09: noi cap");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

		log.info("TC_09: nguoi tra phi giao dich");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_02_Step_: noi cap");
		trasferPage.navigateBack(driver);
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
	}

//	@Test
	public void TC_11_ChuyenTienQuaCMTNguoiChuyenTraPhiEURXacNhanOTP() {
		log.info("TC_11: chon chuyển tiền nhận bằng CMT");
		homePage.scrollToText(driver, "Chuyển tiền nhận bằng CMT");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");
		String text = homePage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.PAGE_TRANSFER).trim();
		verifyTrue(text.equals(TransferIdentity_Data.textCheckElement.PAGE_TRANSFER));

		log.info("TC_11: chon tai khoan");
		trasferPage.clickToDynamicAccount(driver, "Tài khoản nguồn");
		trasferPage.scrollToText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_EUR);
		trasferPage.clickToDynamicAcceptText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_EUR);

		log.info("TC_11: lay so tien truoc khi chuyen khoan");
		String overbalanceBefore = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double overbalanceBeforeInt = convertMoneyToDouble(overbalanceBefore, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);

		log.info("TC_11: nhap ten nguoi thu huong");
		trasferPage.inputBeneficiary(TransferIdentity_Data.textDataInputForm.USER_NAME);

		log.info("TC_11: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

		log.info("TC_11: so CMT");
		trasferPage.inputIdentityNumber(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

		log.info("TC_11: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
		trasferPage.clickToDynamicButton(driver, "OK");

		trasferPage.scrollToText(driver, "Thông tin giao dịch");

		log.info("TC_11: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thành phố Hà Nội ");

		log.info("TC_11: nguoi tra phi giao dich");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_11: nhap so tien chuyen di");
		trasferPage.inputMoney(TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR);
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_11: noi dung");
		trasferPage.inputContent(TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER);

		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_11: xac nhan thong tin");
		String confirm = trasferPage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
		verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

		trasferPage.scrollToText(driver, "Chọn phương thức xác thực");

		log.info("TC_11: chon phuong thuc xac thuc");
		trasferPage.clickToDynamicAcceptText(driver, "Mật khẩu đăng nhập");
		trasferPage.clickToDynamicAcceptText(driver, "SMS OTP");


		log.info("TC_11: lay ra so tien chuyen di");
		String moneyTransfer = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền(EUR)");
		double moneyTransferInt = convertMoneyToDouble(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);
		String transferContent = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);

		log.info("TC_11: lay phi");
		String amount = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
		double amountInt = convertMoneyToDouble(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_EURO);
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_11: xac thuc giao dich");
		trasferPage.inputToDynamicOtpOrPIN(driver, LogIn_Data.Login_Account.OTP, "Tiếp tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_11: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
		String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
		verifyTrue(moneyTransfer.equals(transferMoney));

		log.info("TC_11: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
		transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

		log.info("TC_11: ten nguoi thu huong");
		String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
		verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

		log.info("TC_11: tai khoan giao dich");
		String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
		verifyTrue(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER.equals(destinationAccount));

		log.info("TC_11: ma giao dich");
		transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

		log.info("TC_11: noi dung giao dich");
		String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);
		verifyTrue(transferContent.equals(conten));

		log.info("TC_11: xac thuc thuc hien giao dich moi");
		String newDealConfirm = trasferPage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
		verifyTrue(newDealConfirm.equals(TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS));
		trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");


		log.info("TC_11: so tien kha dung con lai trong tai khoan");
		String overbalanceAfter = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		double overbalanceAfterInt = convertMoneyToDouble(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_USD);

		log.info("TC_11: kiem tra so tien kha dung sau khi chuyen");
		double overbalanceAfterCacuLator = overbalanceBeforeInt - moneyTransferInt - amountInt;
		verifyEquals(overbalanceAfterInt, overbalanceAfterCacuLator);
	}

//	@Test
	public void TC_12_KiemTraChiTietGiaoDichChuyenTienQuaCMTNguoiChuyenTraPhiEURXacNhanOTPTrongBaoCaoGiaoDich() {
		log.info("TC_09: quay lại home page");
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");

		log.info("TC_09:Tìm tới menu page");
		homePage = PageFactoryManager.getHomePageObject(driver);
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");

		log.info("TC_09:chon bao cao giao dich");
		transReport = PageFactoryManager.getTransactionReportPageObject(driver);
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Báo cáo giao dịch");

		log.info("TC_09:click texbox tat ca cac loai giao dich");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Tất cả các loại giao dịch");

		log.info("TC_09:chon chuyen tien cho nguoi nhan tai quay");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền cho người nhận tại quầy");

		log.info("TC_09:chon ngay thuc hien giao dich");
		transReport.clickToDynamicDropdownAndDateTimePicker(driver, "com.VCB:id/tvSelectAcc");

		log.info("TC_09:chon tai khoan");
		transReport.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_EUR);

		log.info("TC_09:Thuc hien tim kiem");
		transReport.clickToDynamicButton(driver, "Tìm kiếm");


		log.info("TC_09: chon ngay thuc hien giao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").contains(transferTime.split(" ")[0]));

		log.info("TC_09: kiem tra ngay giao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_09: kiem tra noi dung giao dich");
		verifyTrue(transReport.getTextInDynamicTransactionInReport(driver, "0", "com.VCB:id/tvContent").equals(TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER));

		log.info("TC_09: kiem tra so tien chuyen di");
		verifyEquals(transReport.getTextInDynamicTransactionInReport(driver, "1", "com.VCB:id/tvMoney"), ("- " + addCommasToDouble(TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_EUR) + " " + TransferIdentity_Data.textDataInputForm.CURRENCY_EURO));

		log.info("TC_09: click chon giao dich");
		transReport.clickToDynamicTransactionInReport(driver, "0", "com.VCB:id/tvDate");

		log.info("TC_09: kiem tra thoi gian giao dich trong man hinh chi tiet");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").contains(transferTime.split(" ")[0]));

		log.info("TC_09: kiem tra thoi gian giao dich trong man hinh chi tiet");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Thời gian giao dịch").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_09: so lenh giao dich");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số lệnh giao dịch"), transactionNumber);

		log.info("TC_09: tai khoan the ghi no");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tài khoản/thẻ trích nợ"), TransferIdentity_Data.textDataInputForm.ACCOUNT_EUR);

		log.info("TC_09: ten nguoi thu huong");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

		log.info("TC_09: giay to tuy than");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Giấy tờ tùy thân"), "Chứng minh nhân dân");

		log.info("TC_09: so CMT");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

		System.out.println(transReport.getDynamicTextInTransactionDetail(driver, "Ngày cấp"));
		System.out.println(transferTime.split(" ")[0]);

		log.info("TC_09: ngay cap");
		verifyTrue(transReport.getDynamicTextInTransactionDetail(driver, "Ngày cấp").split(" ")[0].equals(transferTime.split(" ")[3]));

		log.info("TC_09: noi cap");
		verifyEquals(transReport.getDynamicTextInTransactionDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

		log.info("TC_09: nguoi tra phi giao dich");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người nhận trả");

		log.info("TC_02_Step_: noi cap");
		trasferPage.navigateBack(driver);
		trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
		homePage.clickToDynamicBottomMenu(driver, "com.VCB:id/menu_5");
	}

//	@Test
	public void TC_13_ChuyenTienQuaHCNguoiChuyenTraPhiVNDXacNhanMatKhau() {
		log.info("TC_13: chon chuyển tiền nhận bằng CMT");
		homePage.scrollToText(driver, "Chuyển tiền nhận bằng CMT");
		homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");
		String text = homePage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.PAGE_TRANSFER).trim();
		verifyTrue(text.equals(TransferIdentity_Data.textCheckElement.PAGE_TRANSFER));

		log.info("TC_13: chon tai khoan");
		trasferPage.clickToDynamicAccount(driver, "Tài khoản nguồn");
		trasferPage.scrollToText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);
		trasferPage.clickToDynamicAcceptText(driver, TransferIdentity_Data.textDataInputForm.ACCOUNT_VND);


		log.info("TC_13: lay so tien truoc khi chuyen khoan");
		String overbalanceBefore = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		Long overbalanceBeforeInt = convertMoneyToLong(overbalanceBefore, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);

		log.info("TC_13: nhap ten nguoi thu huong");
		trasferPage.inputBeneficiary(TransferIdentity_Data.textDataInputForm.USER_NAME);

		log.info("TC_13: chon giay to tuy than");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

		log.info("TC_13: so CMT");
		trasferPage.inputIdentityNumber(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER);

		log.info("TC_13: ngay cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
		trasferPage.clickToDynamicButton(driver, "OK");

		trasferPage.scrollToText(driver, "Thông tin giao dịch");

		log.info("TC_13: noi cap");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
		trasferPage.inputBeneficiary(TransferIdentity_Data.textDataInputForm.ISSUED);

		log.info("TC_13: nguoi tra phi giao dich");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

		log.info("TC_13: nhap so tien chuyen di");
		trasferPage.inputMoney(TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND);
		trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_13: noi dung");
		trasferPage.inputContent(TransferIdentity_Data.textDataInputForm.CONTEN_TRANSFER);

		log.info("TC_13: noi dung");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13: xac nhan thong tin");
		String confirm = trasferPage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
		verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

		trasferPage.scrollToText(driver, "Chọn phương thức xác thực");
		log.info("TC_13: chon phuong thuc xac thuc");
		trasferPage.clickToDynamicAcceptText(driver, "Mật khẩu đăng nhập");
		trasferPage.clickToDynamicAcceptText(driver, "Mật khẩu đăng nhập");

		log.info("TC_13: lay ra so tien chuyen di");
		String moneyTransfer = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền");
		Long moneyTransferInt = convertMoneyToLong(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
		String transferContent = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);

		log.info("TC_13: lay phi");
		String amount = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
		Long amountInt = convertMoneyToLong(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13: xac thuc giao dich");
		trasferPage.inputToDynamicPopupPasswordInput(driver, LogIn_Data.Login_Account.NEW_PASSWORD, "Tiếp tục");
		trasferPage.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_13: lay ra so tien chuyen di o man hinh xac thuc thanh cong");
		String transferMoney = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS);
		verifyTrue(moneyTransfer.equals(transferMoney));

		log.info("TC_13: lay ra thoi gian chuyen di o man hinh xac thuc thanh cong");
		transferTime = trasferPage.getDynamicTransferTimeAndMoney(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS, "4");

		log.info("TC_13: ten nguoi thu huong");
		String beneficiaryName = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.BENEFICIARY_NAME);
		verifyTrue(TransferIdentity_Data.textDataInputForm.USER_NAME.equals(beneficiaryName));

		log.info("TC_13: tai khoan giao dich");
		String destinationAccount = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.DESTINATION_ACCOUNT);
		verifyTrue(TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER.equals(destinationAccount));

		log.info("TC_13: ma giao dich");
		transactionNumber = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.TRANSECTION_NUMBER);

		log.info("TC_13: noi dung giao dich");
		String conten = trasferPage.getDynamicAmountLabel(driver, TransferIdentity_Data.textCheckElement.CONNTENT);
		verifyTrue(transferContent.equals(conten));

		log.info("TC_13: xac thuc thuc hien giao dich moi");
		String newDealConfirm = trasferPage.getTextDynamicPopup(driver, TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS).trim();
		verifyTrue(newDealConfirm.equals(TransferIdentity_Data.textCheckElement.CONFIRM_TRANSFER_SUCCESS));
		trasferPage.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_13: so tien kha dung con lai trong tai khoan");
		String overbalanceAfter = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
		Long overbalanceAfterInt = convertMoneyToLong(overbalanceAfter, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);

		log.info("TC_13: kiem tra so tien kha dung sau khi chuyen");
		long overbalanceAfterCacuLator = overbalanceBeforeInt - moneyTransferInt - amountInt;
		verifyEquals(overbalanceAfterInt, overbalanceAfterCacuLator);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
//	closeApp();
//	service.stop();
	}

}
