package vnpay.vietcombank.settingVCB_Smart_OTP;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.ConfirmMethodObject;
import pageObjects.ElectricBillPageObject;
import pageObjects.InternetADSLPageObject;
import pageObjects.LandLinePhoneChargePageObject;
import pageObjects.LogInPageObject;
import pageObjects.MobileTopupPageObject;
import pageObjects.PayBillTelevisionPageObject;
import pageObjects.PostpaidMobileBillPageObject;
import pageObjects.VCBCreditCardPaymentObject;
import pageObjects.WaterBillPageObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.Creadit_Card_Payment_Data;
import vietcombank_test_data.Electric_Bills_Data;
import vietcombank_test_data.Internet_ADSL_Data;
import vietcombank_test_data.LandLinePhoneCharge_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.MobileTopupPage_Data.UIs;
import vietcombank_test_data.PayBillTelevison_Data;
import vietcombank_test_data.Postpaid_Mobile_Bill_Data;
import vietcombank_test_data.SettingVCBSmartOTP_Data;
import vietcombank_test_data.Water_Bills_Data;


public class Flow_SettingVCB_Smart_OTP_Part2 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private ConfirmMethodObject smartOTP;
	private WaterBillPageObject waterBill;
	long amount, fee, amountStart, feeView, amountView, amountAfter,money1 = 0;
	private String sourceAccountMoney, customerID, moneyBill, transactionDate, electricBills, mobilePhone, mobileBill, accountMoneyBefore, accountFee, transactionID;
	private PayBillTelevisionPageObject billTelevision;
	private ElectricBillPageObject electricBill;
	private LandLinePhoneChargePageObject landLinePhoneCharge;
	private PostpaidMobileBillPageObject postpaidMobile;
	private MobileTopupPageObject mobileTopup;
	String transferTime, pass;
	String transactionNumber;
	private long surplus, availableBalance, actualAvailableBalance;
	
	private VCBCreditCardPaymentObject vcbACreditCardPayment;
	public String soTaiKhoan,soDuKhaDung,soThe, soTaiKhoanThe, tinhTrangThe, tongSoDaTTTrongKySaoKe, soTienToiThieuPhaiTT, soTienSaoKeConTT, soTienDuNoConPhaiTT, soTienThanhToan, maGiaoDich;
	

	
	String tommorrowDate = getForwardDate(1);
	String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
	String expectAvailableBalance, moneyTransfer, account, user, content, code,source_account,beneficiary_account,money,wishes,surplusString, moneyFee,getPayments,getFee,getService,supplier,userCode,dealCode,getTimeTransfer;

	long transferFee = 0;
	double transferFeeCurrentcy = 0;
	String password = "";
	private double toltalMoney,payments,feeCab;
	private InternetADSLPageObject ADSL;

	private long amountTranfer, costTranfer;

	@Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
	@BeforeClass
	public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt) throws IOException, InterruptedException {
		startServer();
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);
		smartOTP = PageFactoryManager.getConfirmMethodObject(driver);
		ADSL = PageFactoryManager.getInternetADSLPageObject(driver);
		waterBill = PageFactoryManager.getWaterBillPageObject(driver);
		billTelevision = PageFactoryManager.getPayBillTelevisionPageObject(driver);
		electricBill = PageFactoryManager.getElectricBillPageObject(driver);
		landLinePhoneCharge = PageFactoryManager.getLandLinePhoneChargePageObject(driver);
		postpaidMobile = PageFactoryManager.getPostpaidMobileBillPageObject(driver);
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);
		vcbACreditCardPayment = PageFactoryManager.getVCBCreditCardPaymentPageObject(driver);
	}
	
	@Test
	public void TC_01_CaiDatPhuongThucXacThucOTP() {
		log.info("TC_02_Step: Click menu header");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_02_Step: Click cai dat");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("TC_02_Step: Click cai dat Smart OTP");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt VCB-Smart OTP");

		log.info("TC_02_Step: Click cai dat cho tai khoan");
		smartOTP.clickToDynamicTextFollowText(driver, "Chưa kích hoạt");

		log.info("TC_02_Step: Click toi dong y");
		smartOTP.clickToTextID(driver, "com.VCB:id/rule");

		log.info("TC_02_Step_click button dong y");
		smartOTP.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_02_Step_Nhap mat khau");
		smartOTP.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.Smart_OTP, "Nhập mật khẩu");

		log.info("TC_02_Step_Nhap lai mat khau");
		smartOTP.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.Smart_OTP, "Nhập lại mật khẩu");

		log.info("TC_02_Step_click button tiep tuc");
		smartOTP.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Nhap ma xac thuc");
		smartOTP.inputToDynamicSmartOtp(driver, "666888", "com.VCB:id/otp");

		log.info("TC_02_Step_click button tiep tuc");
		smartOTP.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Verify cai dat thanh cong");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		verifyEquals(smartOTP.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/state_vnpay"), "Đã kích hoạt");

		log.info("TC_02_Step_click button quay lai cai dat");
		smartOTP.clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_02_Click button home");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	// Thanh Toan ADSL
	//@Test
	@Parameters({ "pass" })
	public void TC_02_ThanhToanCuocViettelXacThucSmartOTP(String pass) throws InterruptedException {
		log.info("TC_01_Step_Click cuoc ADSL");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "Cước Internet ADSL");

		log.info("TC_01_Step_Select tai khoan nguon");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_01_Step_Get so du kha dung");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextByLabel(driver, "Số dư khả dụng"));

		log.info("TC_01_Step_Thong tin giao dich chon Viettel");
		ADSL.clickToTextID(driver, "com.VCB:id/content");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.NETWORK[0]);

		log.info("TC_01_Input ma khach hang");
		ADSL.inputCustomerCode(Internet_ADSL_Data.Valid_Account.CODEVIETTEL);

		log.info("TC_01_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_Kiem tra tai khoan nguon");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_01_Kiem tra dich vu");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước Internet ADSL");

		log.info("TC_01_Kiem tra nha cung cap");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), Internet_ADSL_Data.Valid_Account.NETWORK[0]);

		log.info("TC_01_Kiem tra ma khach hang");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Mã khách hàng"), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_01_Get so tien thanh toan");
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số tiền thanh toán"));

		log.info("TC_01_Chon phuong thuc xac thuc");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "VCB - Smart OTP"));
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_01_verify so tien phi");
		feeView = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số tiền phí"));
		verifyEquals(feeView, fee);

		log.info("TC_01_Click Tiep tuc");
		ADSL.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		ADSL.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		ADSL.clickToDynamicContinue(driver, "com.VCB:id/submit");
		ADSL.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Verify message thanh cong");
		verifyEquals(ADSL.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_01_Verify so tien thanh toan");
		amountView = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"));
		verifyEquals(amountView, amount);

		log.info("TC_01_get thoi gian giao dich thanh cong");
		transferTime = ADSL.getDynamicTransferTimeAndMoney(driver, "GIAO DỊCH THÀNH CÔNG", "4").split(" ")[3];

		log.info("TC_01_Kiem tra dich vu");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước Internet ADSL");

		log.info("TC_01_Kiem tra nha cung cap");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), Internet_ADSL_Data.Valid_Account.NETWORK[0]);

		log.info("TC_01_Kiem tra ma khach hang");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Mã khách hàng"), InternetADSLPageObject.codeADSL.toUpperCase().toUpperCase());

		log.info("TC_01_Step_:Lay ma giao dich");
		transactionNumber = ADSL.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_Step_: Chon thuc hien giao dich");
		ADSL.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_Step_: Chon tai khoan chuyen");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_01_Step_: Chon tai khoan chuyen");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		amountAfter = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_01_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amount - fee, amountAfter);
	}

	//Thanh toan tien nước
	@Parameters("otp")
	//@Test
	public void TC_03_ThanhToanTienNuoc_SmartOTP(String otp) throws InterruptedException {
		log.info("TC_01_Step_01: Hoa don tien dien");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán tiền nước");
		waterBill = PageFactoryManager.getWaterBillPageObject(driver);

		log.info("TC_01_Step_02: Chon tai khoan nguon");
		waterBill.clickToTextID(driver, "com.VCB:id/number_account");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		sourceAccountMoney = waterBill.getDynamicTextByLabel(driver, "Số dư khả dụng");

		log.info("TC_01_Step_03: Chon nha cung cap");
		waterBill.clickToTextID(driver, "com.VCB:id/wrap_tv");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, Water_Bills_Data.DATA.WATER_DAWACO);

		log.info("TC_01_Step_04: Nhap ma khach hang va an tiep tuc");
		customerID = waterBill.inputCustomerId(Water_Bills_Data.DATA.LIST_CUSTOMER_ID);

		log.info("TC_01_Step_05: Hien thi man hinh xac nhan thong tin");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_01_Step_06: Hien thi tai khoan nguon");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_07: Hien thi ten dich vu");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, "Dịch vụ"), Water_Bills_Data.DATA.WATER_BILL_TEXT);

		log.info("TC_01_Step_08: Hien thi Nha cung cap");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, "Nhà cung cấp"), Water_Bills_Data.DATA.WATER_DAWACO);

		log.info("TC_01_Step_09: Hien thi ma khach hang");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, "Mã khách hàng"), customerID);

		log.info("TC_01_Step_10: Hien thi So tien thanh toan");
		moneyBill = waterBill.getDynamicTextByLabel(driver, "Số tiền thanh toán");

		log.info("TC_01_Step_11: Chon phuong thuc xac thuc");
		waterBill.scrollDownToText(driver, "Chọn phương thức xác thực");
		waterBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(waterBill.getDynamicTextInTransactionDetail(driver, "VCB - Smart OTP"));
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_01_Step_12: Kiem tra so tien phi");
		verifyEquals(waterBill.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_01_Step_13: An nut Tiep Tuc");
		verifyEquals(waterBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Tiếp tục");
		waterBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_Nhap ma xac thuc");
		waterBill.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		waterBill.clickToDynamicContinue(driver, "com.VCB:id/submit");
		waterBill.clickToDynamicContinue(driver, "com.VCB:id/btContinue");
		log.info("TC_01_Step_16: Hien thi man hinh giao dich thanh cong");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_01_Step_17: Xac nhan hien thi dung so tien thanh toan");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), moneyBill);

		log.info("TC_01_Step_18: Xac nhan hien thi thoi gian giao dich");
		transactionDate = waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		verifyTrue(waterBill.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_01_Step_19: Hien thi dung ten dich vu");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, "Dịch vụ"), Water_Bills_Data.DATA.WATER_BILL_TEXT);

		log.info("TC_01_Step_20: Hien thi dung Nha cung cap");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, "Nhà cung cấp"), Water_Bills_Data.DATA.WATER_DAWACO);

		log.info("TC_01_Step_21: Hien thi dung ma khach hang");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, "Mã khách hàng"), moneyBill);

		log.info("TC_01_Step_22: Hien thi ma giao dich");
		transactionID = waterBill.getDynamicTextByLabel(driver, "Mã giao dịch");

		log.info("TC_01_Step_23: An tiep button 'Thuc hien giao dich moi'");
		verifyEquals(waterBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Thực hiện giao dịch mới");
		waterBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_24: Hien thi man hinh Hoa don tien dien");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Water_Bills_Data.DATA.WATER_BILL_TEXT);

		log.info("TC_07_Step_25: Chon tai khoan nguon");
		waterBill.clickToTextID(driver, "com.VCB:id/number_account");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_26: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(moneyBill) - transferFee) + "";
		verifyEquals(waterBill.getDynamicTextByLabel(driver, "Số dư khả dụng"), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_07_Step_27: Chon nha cung cap");
		waterBill.clickToTextID(driver, "com.VCB:id/wrap_tv");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, Water_Bills_Data.DATA.WATER_DAWACO);

		log.info("TC_01_Step_28: Nhap ma khach hang");
		waterBill.inputToDynamicEditviewByLinearlayoutId(driver, customerID, "com.VCB:id/llCode");

		log.info("TC_01_Step_29: An nut Tiep Tuc");
		verifyEquals(waterBill.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), "Tiếp tục");
		waterBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_30: Hien thi thong bao Ma khach hang khong con no truoc");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Electric_Bills_Data.VALIDATE.ELECTRIC_BILL_MESSAGE);

		log.info("TC_01_Step_31: Click nut Dong tat pop-up");
		waterBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_01_Step_32: Click nut Back ve man hinh chinh");
		waterBill.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_01_Step_33: Click nut Back ve man hinh chinh");
		waterBill.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}
	
	//Thanh toan truyền hình cab
	@Test
	@Parameters({ "pass" })
	public void TC_04_PhuongThucThanhToan_SmartOTP(String pass) throws InterruptedException {
		log.info("TC_01_STEP_0: chọn cước truyền hình cap");
		billTelevision.clickToDynamicButtonLinkOrLinkText(driver, "Cước truyền hình cáp");

		log.info("TC_01_STEP_0: lấy ra số dư");
		String getToltalMoney = billTelevision.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] toltal_money = getToltalMoney.split(" ");
		toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

		log.info("TC_01_STEP_1: kiem tra hien thị mac dinh");
		List<String> data = PayBillTelevison_Data.inputData.CODE_CUSTOMER;
		for (int i = 0; i < data.size(); i++) {
			log.info("TC_01_STEP_2: điền mã khách hàng");
			billTelevision.inputIntoEditTextByID(driver, data.get(i), "com.VCB:id/code");

			log.info("TC_01_STEP_3: chọn tiếp tục");
			billTelevision.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

			String locator = String.format(DynamicPageUIs.DYNAMIC_TEXT_BY_ID, "com.VCB:id/tvTitleBar");
			if (driver.findElements(By.xpath(locator)).size() > 0) {
				break;
			} else {
				log.info("TC_01_STEP_3: chọn đóng");
				billTelevision.clickToDynamicButton(driver, "Đóng");
			}

		}

		log.info("TC_01_STEP_4: chọn phương thức xác thực");
		billTelevision.clickToTextID(driver, "com.VCB:id/tvptxt");
		billTelevision.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_01_STEP_5: lấy ra số tiền thanh toán ");
		getPayments = billTelevision.getMoneyByAccount(driver, "Số tiền thanh toán");
		String[] paymentsSplit = getPayments.split(" ");
		payments = Double.parseDouble(paymentsSplit[0].replace(",", ""));

		log.info("TC_01_STEP_6: lấy ra tên dịch vụ");
		getService = billTelevision.getMoneyByAccount(driver, "Dịch vụ");

		log.info("TC_01_STEP_7: lấy nhà cung cấp");
		supplier = billTelevision.getMoneyByAccount(driver, "Nhà cung cấp");

		log.info("TC_01_STEP_8: lấy mã khách hàng");
		userCode = billTelevision.getMoneyByAccount(driver, "Mã khách hàng");

		log.info("TC_01_STEP_9: lấy ra số số phí");
		getFee = billTelevision.getMoneyByAccount(driver, "Số tiền phí");
		String[] feeSplit = getFee.split(" ");
		feeCab = Double.parseDouble(feeSplit[0].replace(",", ""));

		log.info("TC_01_STEP_10: lấy tài khoản nguồn");
		account = billTelevision.getMoneyByAccount(driver, "Tài khoản nguồn");

		log.info("TC_01_STEP_11: chọn tiếp tục");
		billTelevision.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		billTelevision.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		billTelevision.clickToDynamicContinue(driver, "com.VCB:id/submit");
		billTelevision.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_03_STEP_15: lấy mã hóa đơn");
		dealCode = billTelevision.getMoneyByAccount(driver, "Mã giao dịch");

		log.info("TC_01_STEP_14: lấy thời gian giao dịch");
		getTimeTransfer = billTelevision.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");

		log.info("TC_01_STEP_15: chọn thực hiện giao dịch mới");
		billTelevision.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		
		log.info("TC_02_17: kiểm tra số dư");
		billTelevision.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		String surplus = billTelevision.getDynamicTextInTransactionDetail(driver, Account_Data.Valid_Account.ACCOUNT2);
		String[] surplusSplit = surplus.split(" ");
		double surplusInt = Double.parseDouble(surplusSplit[0].replace(",", ""));
		double canculateAvailable = toltalMoney - payments - fee;
		verifyEquals(surplusInt, canculateAvailable);

		log.info("TC_01_STEP_16: chọn back");
		billTelevision.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

	}
	
	//Thanh toán tiền điện
	//@Test --Không có mã
	@Parameters({ "otp" })
	public void TC_05_ThanhToanTienDien_SmartOTP(String otp) throws InterruptedException {
		log.info("TC_01_Step_01: Hoa don tien dien");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, "Hoá đơn tiền điện");

		log.info("TC_01_Step_02: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		sourceAccountMoney = electricBill.getDynamicTextByLabel(driver, "Số dư khả dụng");

		log.info("TC_01_Step_03: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToDynamicImageView(driver, "com.VCB:id/icon");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_01_Step_04: Nhap ma khach hang va an tiep tuc");
		customerID = electricBill.inputCustomerId(Electric_Bills_Data.DATA.LIST_CUSTOMER_ID);

		log.info("TC_01_Step_05: Hien thi man hinh xac nhan thong tin");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_01_Step_06: Hien thi tai khoan nguon");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_07: Hien thi ten dich vu");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Dịch vụ"), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_01_Step_08: Hien thi Nha cung cap");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Nhà cung cấp"), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_01_Step_09: Hien thi ma khach hang");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Mã khách hàng"), customerID);

		log.info("TC_01_Step_10: Hien thi So tien thanh toan");
		electricBills = electricBill.getDynamicTextByLabel(driver, "Số tiền thanh toán");

		log.info("TC_01_Step_11: Chon phuong thuc xac thuc");
		electricBill.scrollDownToText(driver, "Chọn phương thức xác thực");
		electricBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(electricBill.getDynamicTextInTransactionDetail(driver, "VCB - Smart OTP"));
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_01_Step_12: Kiem tra so tien phi");
		verifyEquals(electricBill.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_01_Step_13: An nut Tiep Tuc");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Tiếp tục");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_Nhap ma xac thuc");
		electricBill.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		electricBill.clickToDynamicContinue(driver, "com.VCB:id/submit");
		electricBill.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_16: Hien thi man hinh giao dich thanh cong");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_01_Step_17: Xac nhan hien thi dung so tien thanh toan");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), electricBills);

		log.info("TC_01_Step_18: Xac nhan hien thi thoi gian giao dich");
		transactionDate = electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		verifyTrue(electricBill.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_01_Step_19: Hien thi dung ten dich vu");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Dịch vụ"), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_01_Step_20: Hien thi dung Nha cung cap");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Nhà cung cấp"), Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_01_Step_21: Hien thi dung ma khach hang");
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Mã khách hàng"), electricBills);

		log.info("TC_01_Step_22: Hien thi ma giao dich");
		transactionID = electricBill.getDynamicTextByLabel(driver, "Mã giao dịch");

		log.info("TC_01_Step_23: An tiep button 'Thuc hien giao dich moi'");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Thực hiện giao dịch mới");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_24: Hien thi man hinh Hoa don tien dien");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Electric_Bills_Data.VALIDATE.ELECTIC_BILL_TITLE);

		log.info("TC_07_Step_25: Chon tai khoan nguon");
		electricBill.clickToTextID(driver, "com.VCB:id/number_account");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_26: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(electricBills) - transferFee) + "";
		verifyEquals(electricBill.getDynamicTextByLabel(driver, "Số dư khả dụng"), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_07_Step_27: Chon Nha cung cap EVN Mien Trung");
		electricBill.clickToTextID(driver, "com.VCB:id/wrap_tv");
		electricBill.clickToDynamicButtonLinkOrLinkText(driver, Electric_Bills_Data.DATA.EVN_MIEN_TRUNG);

		log.info("TC_01_Step_28: Nhap ma khach hang");
		electricBill.inputToDynamicEditviewByLinearlayoutId(driver, customerID, "com.VCB:id/llCode");

		log.info("TC_01_Step_29: An nut Tiep Tuc");
		verifyEquals(electricBill.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), "Tiếp tục");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_30: Hien thi thong bao Ma khach hang khong con no truoc");
		verifyEquals(electricBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Electric_Bills_Data.VALIDATE.ELECTRIC_BILL_MESSAGE);

		log.info("TC_01_Step_31: Click nut Dong tat pop-up");
		electricBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_01_Step_32: Click nut Back ve man hinh chinh");
		electricBill.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_01_Step_33: Click nut Back ve man hinh chinh");
		electricBill.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	//Thanh toán cước di động trả sau
	//@Test Check lai không có phương thức smart OTP
	@Parameters({ "otp" })
	public void TC_06_CuocDiDongTraSau_Viettel_OTP(String otp) throws InterruptedException {
		log.info("TC_01_Step_01: Click Cuoc di dong tra sau");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, "Cước di động trả sau");
		postpaidMobile = PageFactoryManager.getPostpaidMobileBillPageObject(driver);

		log.info("TC_01_Step_02: Hien thi man hinh Cuoc di dong tra sau");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Postpaid_Mobile_Bill_Data.VALIDATE.POSTPAID_MOBILE_TITLE);

		log.info("TC_01_Step_03: Chon tai khoan nguon");
		postpaidMobile.clickToDynamicDropdownByHeader(driver, "Tài khoản nguồn", "1");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		sourceAccountMoney = postpaidMobile.getDynamicTextByLabel(driver, "Số dư khả dụng");

		log.info("TC_01_Step_04: Chon nha cung cao Viettel");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_01_Step_05: Nhap so dien thoai va an tiep tuc");
		mobilePhone = postpaidMobile.inputPhoneNumberPostPaidMobile(Postpaid_Mobile_Bill_Data.DATA.LIST_VIETTEL_MOBILE);

		log.info("TC_01_Step_06: Hien thi man hinh xac nhan thong tin");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_01_Step_07: Hien thi thông tin xac nhan");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), Postpaid_Mobile_Bill_Data.VALIDATE.VERIFY_INFO_TITLE_HEAD);

		log.info("TC_01_Step_08: Hien thi tai khoan nguon");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_09: Hien thi ten dich vu");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Dịch vụ"), Postpaid_Mobile_Bill_Data.VALIDATE.POSTPAID_MOBILE_TITLE);

		log.info("TC_01_Step_10: Hien thi Nha cung cap");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Nhà cung cấp"), Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_01_Step_11: Hien thi So dien thoai");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Số điện thoại"), mobilePhone);

		log.info("TC_01_Step_12: Hien thi So tien thanh toan");
		mobileBill = postpaidMobile.getDynamicTextByLabel(driver, "Số tiền thanh toán");

		log.info("TC_01_Step_13: Chon phuong thuc xac thuc");
		postpaidMobile.scrollDownToText(driver, "Chọn phương thức xác thực");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(postpaidMobile.getDynamicTextInTransactionDetail(driver, "VCB - Smart OTP"));
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_01_Step_14: Kiem tra so tien phi");
		verifyEquals(postpaidMobile.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(transferFee + "") + " VND");

		log.info("TC_01_Step_15: An nut Tiep Tuc");
		verifyEquals(postpaidMobile.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Tiếp tục");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_Nhap ma xac thuc");
		postpaidMobile.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		postpaidMobile.clickToDynamicContinue(driver, "com.VCB:id/submit");
		postpaidMobile.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_18: Hien thi man hinh giao dich thanh cong");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_01_Step_19: Xac nhan hien thi dung so tien thanh toan");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), mobileBill);

		log.info("TC_01_Step_20: Xac nhan hien thi thoi gian giao dich");
		transactionDate = postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		verifyTrue(postpaidMobile.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_01_Step_21: Hien thi dung ten dich vu");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Dịch vụ"), Postpaid_Mobile_Bill_Data.VALIDATE.POSTPAID_MOBILE_TITLE);

		log.info("TC_01_Step_22: Hien thi dung Nha cung cap");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Nhà cung cấp"), Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_01_Step_23: Hien thi dung So dien thoai");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Số điện thoại"), mobilePhone);

		log.info("TC_01_Step_24: Hien thi ma giao dich");
		transactionID = postpaidMobile.getDynamicTextByLabel(driver, "Mã giao dịch");

		log.info("TC_01_Step_26: Hien thi Icon Chia se");
		verifyTrue(postpaidMobile.isDynamicTextDetailByID(driver, "com.VCB:id/tvShare"));

		log.info("TC_01_Step_27: Hien thi Icon Luu anh");
		verifyTrue(postpaidMobile.isDynamicTextDetailByID(driver, "com.VCB:id/tvSavePhoto"));

		log.info("TC_01_Step_29: An tiep button 'Thuc hien giao dich moi'");
		verifyEquals(postpaidMobile.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Thực hiện giao dịch mới");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_30: Hien thi man hinh Cuoc di dong tra sau");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Postpaid_Mobile_Bill_Data.VALIDATE.POSTPAID_MOBILE_TITLE);

		log.info("TC_01_Step_31: Chon tai khoan nguon");
		postpaidMobile.clickToDynamicDropdownByHeader(driver, "Tài khoản nguồn", "1");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_31: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(mobileBill) - transferFee) + "";
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Số dư khả dụng"), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_01_Step_32: Chon nha cung cap Viettel");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_01_Step_33: Nhap so dien thoai");
		postpaidMobile.inputToDynamicEditviewByLinearlayoutId(driver, mobilePhone, "com.VCB:id/llCode");

		log.info("TC_01_Step_34: An nut Tiep Tuc");
		verifyEquals(postpaidMobile.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), "Tiếp tục");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_35: Hien thi thong bao So dien thoai khong con no truoc");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Postpaid_Mobile_Bill_Data.VALIDATE.POSTPAID_MOBILE_MESSAGE);

		log.info("TC_01_Step_36: Click nut Dong tat pop-up");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_01_Step_37: Click nut Back ve man hinh chinh");
		postpaidMobile.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_01_Step_38: Click nut Back ve man hinh chinh");
		postpaidMobile.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

	}
	
	//Thanh toán cước điện thoại cố định
	//@Test - Check lại ko thấy PTXT smart OTP
	public void TC_07_ThanhToanCuocDienThoaiCoDinh_CoDinhCoDay_ThanhToanMatKhauDangNhap() throws InterruptedException {
		log.info("TC_01_01_Click Cuoc dien thoai co dinh");
		landLinePhoneCharge.scrollDownToText(driver, "Cước truyền hình cáp");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, "Cước điện thoại cố định");

		log.info("TC_01_02_Chon tai khoan nguon");
		landLinePhoneCharge.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_01_03_Chon loai cuoc thanh toan");
		landLinePhoneCharge.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, "Cố định có dây Viettel");

		log.info("TC_01_04_Nhap so dien thoai tra cuoc va bam Tiep tuc");
		landLinePhoneCharge.inputPhoneNumberLandLinePhoneCharge(LandLinePhoneCharge_Data.LIST_LANDLINE_PHONE_LINE);

		money1 = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Số tiền thanh toán"));
		log.info("TC_01_05_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_01_05_1_Kiem tra tai khoan nguon");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_05_2_Kiem tra dich vu");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước điện thoại cố định");

		log.info("TC_01_05_3_Kiem tra nha cung cap");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), "Cố định có dây Viettel");

		log.info("TC_01_05_4_Kiem tra so dien thoai");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Số điện thoại"), LandLinePhoneChargePageObject.phoneNumber);

		log.info("TC_01_06_Chon phuong thuc xac thuc");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "VCB - Smart OTP"));

		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_01_07_01_Kiem tra so tien phi");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Số tiền phí"), addCommasToLong(fee + "") + " VND");

		log.info("TC_01_08_Click Tiep tuc");
		landLinePhoneCharge.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		landLinePhoneCharge.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		landLinePhoneCharge.clickToDynamicContinue(driver, "com.VCB:id/submit");
		landLinePhoneCharge.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_10_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_01_10_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(landLinePhoneCharge.isDynamicMessageAndLabelTextDisplayed(driver, LandLinePhoneCharge_Data.SUCCESS_TRANSFER_MONEY));

		log.info("TC_01_10_2_Kiem tra dich vu");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước điện thoại cố định");

		log.info("TC_01_10_3_Kiem tra nha cung cap");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), "Cố định có dây Viettel");

		log.info("TC_01_10_3_So dien thoai");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Số điện thoại"), LandLinePhoneChargePageObject.phoneNumber);

		log.info("TC_01_10_4_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(landLinePhoneCharge.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_01_10_5_Lay ma giao dich");
		transferTime = landLinePhoneCharge.getTransferTimeSuccess(driver, LandLinePhoneCharge_Data.SUCCESS_TRANSFER_MONEY);
		transactionNumber = landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_01_11_Click Thuc hien giao dich moi");
		landLinePhoneCharge.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_01_11_Kiem tra so du kha dung luc sau");
		landLinePhoneCharge.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalance = canculateAvailableBalances(surplus, money1, fee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}

	//Nạp thẻ điện thoại   ---OK
	//@Test
	@Parameters({ "pass","phone" })
	public void TC_08_NapTheDienThoai_GiaTriMin_QuaMK(String pass, String phone) throws InterruptedException {
		log.info("TC_01_Step_01: Keo xuong va click vao phan 'Nap tien dien thoai'");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, "Nạp tiền điện thoại");
	
		log.info("TC_01_Step_02: Click vào DrodownList 'Tai khoan nguon' ");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.android.packageinstaller:id/permission_allow_button");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");

		log.info("TC_01_Step_03: Chon tai khoan nguon");
		accountMoneyBefore = mobileTopup.getDynamicTextByLabel(driver, Account_Data.Valid_Account.ACCOUNT2);
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_01_Step_04: Click vao menh gia 30,000");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, UIs.LIST_UNIT_VALUE[0]);

		log.info("TC_01_Step_05: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_01_Step_06: Chon phuong thuc xac thuc SMS OTP");
		accountFee = mobileTopup.getDynamicTextByLabel(driver, "Số tiền phí");
		mobileTopup.clickToTextID(driver, "com.VCB:id/tvptxt");
		mobileTopup.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_01_Step_07: An nut 'Tiep tuc'");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_Nhap ma xac thuc");
		mobileTopup.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		mobileTopup.clickToDynamicContinue(driver, "com.VCB:id/submit");
		mobileTopup.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_10: Lay ma giao dich roi an nut tiep tuc");
		transactionID = mobileTopup.getDynamicTextByLabel(driver, "Mã giao dịch");
		mobileTopup.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_11: Xac nhan so tien o tai khoan nguon bi tru dung");
		mobileTopup.clickToTextID(driver, "com.VCB:id/number_account");
		verifyEquals(mobileTopup.getDynamicTextByLabel(driver, Account_Data.Valid_Account.ACCOUNT2), mobileTopup.getStringNumberAfterCaculate(accountMoneyBefore, UIs.LIST_UNIT_VALUE[0], accountFee) + " VND");
		mobileTopup.clickToTextID(driver, "com.VCB:id/cancel_button");

		log.info("TC_01_Step_12: Click back ve man hinh chinh");
		mobileTopup.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}
	
	//Thanh toán thẻ tín dụng
	//@Test 
	//Lỗi app thanh toán bằng PTXT smart OTP, báo kết nối gián đoạn
	public void TC_09_ThanhToanTheTinDung() throws InterruptedException {
		vcbACreditCardPayment.scrollDownToText(driver, vietcombank_test_data.HomePage_Data.Home_Text_Elements.CREDIT_CARD_PAYMENT);
		vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, vietcombank_test_data.HomePage_Data.Home_Text_Elements.CREDIT_CARD_PAYMENT);
		log.info("TC_01_Step_01: Lay thong tin tai so tai khoan");
		soTaiKhoan = vcbACreditCardPayment.getDynamicTextInDropDownByLable(driver, Creadit_Card_Payment_Data.Tittle.TEXT_STK);

		log.info("TC_01_Step_02: Lay thong tin so du tai khoan");
		soDuKhaDung = vcbACreditCardPayment.getDynamicTextByLabel(driver, Creadit_Card_Payment_Data.Tittle.TEXT_SDKD);

		log.info("TC_01_Step_03: Chon so the");
		vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");

		log.info("TC_01_Step_04: verify hien thi man hinh chon so the");
		vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, Creadit_Card_Payment_Data.Tittle.SELECT_CARD);

		log.info("TC_01_Step_05: get list tai khoan tin dung");
		List<String> listAccount = vcbACreditCardPayment.getListAccount();

		vcbACreditCardPayment.scrollUpToText(driver, listAccount.get(0));

		for (String account : listAccount) {
			vcbACreditCardPayment.scrollDownToText(driver, account);
			log.info("TC_01_Step_06: Chon tai khoan the tin dung:");
			vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, account);
			if (vcbACreditCardPayment.isTextDisplayedInPageSource("Quý khách không còn dư nợ")) {
				log.info("TC_01_Step_07: Click btn Dong y:");
				vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

				log.info("TC_01_Step_08: Click chon so tai khoan the :");
				vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");

				continue;
			} else {
				vcbACreditCardPayment.scrollDownToText(driver, Creadit_Card_Payment_Data.Tittle.TEXT_STK);
				log.info("TC_01_Step_08: Lay thong tin so The");
				soThe = vcbACreditCardPayment.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent1");

				log.info("TC_01_Step_09: Lay thong tin so tai khoan the");
				soTaiKhoanThe = vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.creaditCardPaymentUI.SO_TK_THE);

				log.info("TC_01_Step_10: Lay thong tin tinh trang the");
				tinhTrangThe = vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.creaditCardPaymentUI.TINH_TRANG_THE);

				log.info("TC_01_Step_11: Lay thong tin so tien thanh toan trong ky sao ke: ");
				tongSoDaTTTrongKySaoKe = vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.creaditCardPaymentUI.TONG_SO_TT_SAO_KE);

				log.info("TC_01_Step_12: So tien toi thieu phai thanh toan ");
				soTienToiThieuPhaiTT = vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.creaditCardPaymentUI.SO_TIEN_TOI_THIEU_TT);

				log.info("TC_01_Step_13: So tien thanh toan ");
				vcbACreditCardPayment.scrollDownToText(driver, "Số tiền thanh toán");
				soTienSaoKeConTT = vcbACreditCardPayment.getDynamicTextByLabel(driver,creaditCardPaymentUI.creaditCardPaymentUI.SO_TIEN_SK_TT);

				log.info("TC_01_Step_14: So tien du no phai thanh toan ");
				soTienDuNoConPhaiTT = vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.creaditCardPaymentUI.SO_TIEN_DU_NO_TT);

				log.info("TC_01_Step_15: So tien thanh toan ");
				soTienThanhToan = vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.creaditCardPaymentUI.SO_TIEN_TT);

				if (soTienThanhToan.equals("0 VND")) {
					vcbACreditCardPayment.scrollUpToText(driver, creaditCardPaymentUI.creaditCardPaymentUI.TT_GIAO_DICH);

					log.info("TC_01_Step_16: Chon so the ");
					vcbACreditCardPayment.clickToDynamicLinerLayoutID(driver, "com.VCB:id/llContent1");
					continue;
				} else {
					break;
				}
			}

		}

		log.info("TC_01_Step_17: Click btn tiep tuc");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");
		
		log.info("TC_01_06_Chon phuong thuc xac thuc");
		vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

		vcbACreditCardPayment.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_01_Step_18: Verify hien thi man hinh Xac nhan thong tin ");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, creaditCardPaymentUI.creaditCardPaymentUI.XAC_NHAN_TT));

		log.info("TC_01_Step_19: Xac minh tai khoan nguon: ");
		verifyEquals(soTaiKhoan, vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.creaditCardPaymentUI.TK_NGUON));

		log.info("TC_01_Step_20: Verify so the ");
		verifyEquals(soThe, vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.creaditCardPaymentUI.SO_THE));
		log.info("TC_01_Step_21: Verify so tien thanh toan ");

		verifyEquals(soTienDuNoConPhaiTT, vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.creaditCardPaymentUI.SO_TIEN_TT));

		log.info("TC_01_Step_22: Click btn Tiep tuc ");
		vcbACreditCardPayment.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_23: Xac minh man hinh  Xac thuc giao dich ");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, creaditCardPaymentUI.creaditCardPaymentUI.XAC_THUC_GD));

		log.info("TC_01_Step_Nhap ma xac thuc");
		vcbACreditCardPayment.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_01_Step_Tiep tuc");
		vcbACreditCardPayment.clickToDynamicContinue(driver, "com.VCB:id/submit");
		vcbACreditCardPayment.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_01_Step_26: Xac minh man hinh thong bao giao dich thanh cong");
		verifyTrue(vcbACreditCardPayment.isDynamicMessageAndLabelTextDisplayed(driver, creaditCardPaymentUI.creaditCardPaymentUI.GD_THANH_CONG));

		log.info("TC_01_Step_27: Xac minh thong tin so the ");
		verifyEquals(soThe, vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.creaditCardPaymentUI.SO_THE));

		log.info("TC_01_Step_05: Lay thong tin ma giao dich ");
		maGiaoDich = vcbACreditCardPayment.getDynamicTextByLabel(driver, creaditCardPaymentUI.creaditCardPaymentUI.MA_GIAO_DICH);
	}



	

	public void TC_111_HuyKichHoatVCBSmartOPT() {

		log.info("------------------------TC_01_Step_01: Click menu header-------------------------------");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("-----------------------TC_01_Step_02: Click thanh Cai dat VCB-Smart OTP------------------------");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("---------------------------TC_01_Step_03: Click Cai dat VCB Smart OTP---------------------");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt VCB-Smart OTP");

		log.info("-------------------------------TC_01_Step_04: Verify man hinh cai dat VCB Smart OTP------------------------");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, "Cài đặt VCB-Smart OTP");

		log.info("---------------------------------TC_02_Step_01: Click btn Huy Cai dat-------------------------");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Hủy");

		log.info("----------------------------TC_02_Step_02: Verify hien thi popup xac nhan huy cai dat OTP----------------");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, SettingVCBSmartOTP_Data.MESSEGE_CONFIRM_CANCEL);

		log.info("---------------------------TC_02_Step_03: Verify hien thi popup xac nhan huy cai dat OTP----------------");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Có");

//		log.info("-------------------------------TC_02_Step_04: Verify xac nhan huy Smart OTP thanh cong----------------");
//		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, SettingVCBSmartOTP_Data.MESSEGE_CANCEL_SMART_OTP);

		log.info("--------------------------TC_02_Step_05: verify Trang thai dã kich hoat Smart OTP--------------------");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, "Chưa kích hoạt");
	}

}
