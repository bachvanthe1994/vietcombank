package vnpay.vietcombank.settingVCB_Smart_OTP;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.ElectricBillPageObject;
import pageObjects.InternetADSLPageObject;
import pageObjects.LandLinePhoneChargePageObject;
import pageObjects.LogInPageObject;
import pageObjects.MobileTopupPageObject;
import pageObjects.PayBillTelevisionPageObject;
import pageObjects.PostpaidMobileBillPageObject;
import pageObjects.SettingVCBSmartOTPPageObject;
import pageObjects.VCBCreditCardPaymentObject;
import pageObjects.WaterBillPageObject;
import vietcombankUI.DynamicPageUIs;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.Electric_Bills_Data;
import vietcombank_test_data.Internet_ADSL_Data;
import vietcombank_test_data.LandLinePhoneCharge_Data;
import vietcombank_test_data.LogIn_Data;
import vietcombank_test_data.PayBillTelevison_Data;
import vietcombank_test_data.Postpaid_Mobile_Bill_Data;
import vietcombank_test_data.SettingVCBSmartOTP_Data;
import vietcombank_test_data.Water_Bills_Data;


public class Flow_SettingVCB_Smart_OTP_PaymentBilling_toUpperCase_Part6 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private SettingVCBSmartOTPPageObject smartOTP;
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
	private String nameCustomer;
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
		smartOTP = PageFactoryManager.getSettingVCBSmartOTPPageObject(driver);
		ADSL = PageFactoryManager.getInternetADSLPageObject(driver);
		waterBill = PageFactoryManager.getWaterBillPageObject(driver);
		billTelevision = PageFactoryManager.getPayBillTelevisionPageObject(driver);
		electricBill = PageFactoryManager.getElectricBillPageObject(driver);
		landLinePhoneCharge = PageFactoryManager.getLandLinePhoneChargePageObject(driver);
		postpaidMobile = PageFactoryManager.getPostpaidMobileBillPageObject(driver);
		mobileTopup = PageFactoryManager.getMobileTopupPageObject(driver);
		vcbACreditCardPayment = PageFactoryManager.getVCBCreditCardPaymentPageObject(driver);
	}
	
	public void TC_01_CaiDatPhuongThucXacThucOTP() {
		log.info("TC_01_Step: Click menu header");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("Before class: Lay ten user");
		nameCustomer = smartOTP.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvFullname");

		log.info("TC_01_Step: Click cai dat");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("TC_01_Step: Click cai dat Smart OTP");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt VCB-Smart OTP");

		log.info("TC_01_Step: Click cai dat cho tai khoan");
		smartOTP.clickToDynamicTextFollowText(driver, "Chưa kích hoạt");

		log.info("TC_01_Step: Click toi dong y");
		smartOTP.clickToTextID(driver, "com.VCB:id/rule");

		log.info("TC_01_Step_click button dong y");
		smartOTP.clickToDynamicButton(driver, "Đồng ý");

		log.info("TC_01_Step_Nhap mat khau");
		smartOTP.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.Smart_OTP, "Nhập mật khẩu");

		log.info("TC_01_Step_Nhap lai mat khau");
		smartOTP.inputToDynamicInputBox(driver, LogIn_Data.Login_Account.Smart_OTP, "Nhập lại mật khẩu");

		log.info("TC_01_Step_click button tiep tuc");
		smartOTP.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Nhap ma xac thuc");
		smartOTP.inputToDynamicSmartOtp(driver, "666888", "com.VCB:id/otp");

		log.info("TC_01_Step_click button tiep tuc");
		smartOTP.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_01_Step_Verify cai dat thanh cong");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		verifyEquals(smartOTP.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/state_vnpay"), "Đã kích hoạt");

		log.info("TC_01_Step_click button quay lai cai dat");
		smartOTP.clickToDynamicImageViewID(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_01_Click button home");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_1");
	}

	// Thanh Toan ADSL
	//Lỗi app không hiển thị PTXT smart OTP
	@Test
	@Parameters({ "pass" })
	public void TC_02_ThanhToanCuocViettelXacThucSmartOTP(String pass) throws InterruptedException {
		log.info("TC_02_Step_Click cuoc ADSL");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "Cước Internet ADSL");

		log.info("TC_02_Step_Select tai khoan nguon");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_02_Step_Get so du kha dung");
		amountStart = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextByLabel(driver, "Số dư khả dụng"));

		log.info("TC_02_Step_Thong tin giao dich chon Viettel");
		ADSL.clickToTextID(driver, "com.VCB:id/content");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Internet_ADSL_Data.Valid_Account.VIETTEL_ADSL);

		log.info("TC_02_Input ma khach hang");
		ADSL.inputCustomerCodeToUpperCase(Internet_ADSL_Data.Valid_Account.CODEVIETTEL);

		log.info("TC_02_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_02_Kiem tra tai khoan nguon");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_02_Kiem tra dich vu");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước Internet ADSL");

		log.info("TC_02_Kiem tra nha cung cap");

		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), Internet_ADSL_Data.Valid_Account.VIETTEL_ADSL);


		log.info("TC_02_Kiem tra ma khach hang");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Mã khách hàng"), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_02_Get so tien thanh toan");
		amount = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số tiền thanh toán"));

		log.info("TC_02_Chon phuong thuc xac thuc");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_02_verify so tien phi");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số tiền phí"));

		log.info("TC_02_Click Tiep tuc");
		ADSL.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_02_Step_Nhap ma xac thuc");
		ADSL.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_02_Step_Tiep tuc");
		ADSL.clickToDynamicContinue(driver, "com.VCB:id/submit");
		ADSL.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_02_Verify message thanh cong");
		verifyEquals(ADSL.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_02_Verify so tien thanh toan");
		amountView = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"));
		verifyEquals(amountView, amount);

		log.info("TC_02_get thoi gian giao dich thanh cong");
		transferTime = ADSL.getDynamicTransferTimeAndMoney(driver, "GIAO DỊCH THÀNH CÔNG", "4").split(" ")[3];

		log.info("TC_02_Kiem tra dich vu");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước Internet ADSL");

		log.info("TC_02_Kiem tra nha cung cap");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), Internet_ADSL_Data.Valid_Account.VIETTEL_ADSL);

		log.info("TC_02_Kiem tra ma khach hang");
		verifyEquals(ADSL.getDynamicTextInTransactionDetail(driver, "Mã khách hàng"), InternetADSLPageObject.codeADSL.toUpperCase());

		log.info("TC_02_Step_:Lay ma giao dich");
		transactionNumber = ADSL.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_02_Step_: Chon thuc hien giao dich");
		ADSL.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_02_Step_: Chon tai khoan chuyen");
		ADSL.clickToDynamicDropDown(driver, "Tài khoản nguồn");

		log.info("TC_02_Step_: Chon tai khoan chuyen");
		ADSL.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

		log.info("TC_02_Step_:Check so du kha dung sau khi chuyen tien");
		amountAfter = convertAvailableBalanceCurrentcyOrFeeToLong(ADSL.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_02_Step_:Check so du kha dung sau khi chuyen tien");
		verifyEquals(amountStart - amount - fee, amountAfter);
	}

	//Thanh toan tien nước
	@Parameters("otp")
	@Test
	public void TC_03_ThanhToanTienNuoc_SmartOTP(String otp) throws InterruptedException {
		log.info("TC_03_Step_01: Hoa don tien dien");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, "Thanh toán tiền nước");
		waterBill = PageFactoryManager.getWaterBillPageObject(driver);

		log.info("TC_03_Step_02: Chon tai khoan nguon");
		waterBill.clickToTextID(driver, "com.VCB:id/number_account");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		sourceAccountMoney = waterBill.getDynamicTextByLabel(driver, "Số dư khả dụng");

		log.info("TC_03_Step_03: Chon nha cung cap");
		waterBill.clickToTextID(driver, "com.VCB:id/wrap_tv");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, Water_Bills_Data.DATA.WATER_DAWACO);

		log.info("TC_03_Step_04: Nhap ma khach hang va an tiep tuc");
		customerID = waterBill.inputCustomerIdToUpperCase(Water_Bills_Data.DATA.LIST_CUSTOMER_ID);

		log.info("TC_03_Step_05: Hien thi man hinh xac nhan thong tin");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_03_Step_06: Hien thi tai khoan nguon");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_03_Step_07: Hien thi ten dich vu");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, "Dịch vụ"), Water_Bills_Data.DATA.WATER_BILL_TEXT);

		log.info("TC_03_Step_08: Hien thi Nha cung cap");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, "Nhà cung cấp"), Water_Bills_Data.DATA.WATER_DAWACO);

		log.info("TC_03_Step_09: Hien thi ma khach hang");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, "Mã khách hàng"), customerID);

		log.info("TC_03_Step_10: Hien thi So tien thanh toan");
		moneyBill = waterBill.getDynamicTextByLabel(driver, "Số tiền thanh toán");

		log.info("TC_03_Step_11: Chon phuong thuc xac thuc");
		waterBill.scrollDownToText(driver, "Chọn phương thức xác thực");
		waterBill.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_03_Step_12: Kiem tra so tien phi");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(waterBill.getDynamicTextInTransactionDetail(driver, "Số tiền phí"));

		log.info("TC_03_Step_13: An nut Tiep Tuc");
		verifyEquals(waterBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Tiếp tục");
		waterBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_Nhap ma xac thuc");
		waterBill.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_03_Step_Tiep tuc");
		waterBill.clickToDynamicContinue(driver, "com.VCB:id/submit");
		waterBill.clickToDynamicContinue(driver, "com.VCB:id/btContinue");
		log.info("TC_03_Step_16: Hien thi man hinh giao dich thanh cong");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_03_Step_17: Xac nhan hien thi dung so tien thanh toan");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), moneyBill);

		log.info("TC_03_Step_18: Xac nhan hien thi thoi gian giao dich");
		transactionDate = waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		verifyTrue(waterBill.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_03_Step_19: Hien thi dung ten dich vu");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, "Dịch vụ"), Water_Bills_Data.DATA.WATER_BILL_TEXT);

		log.info("TC_03_Step_20: Hien thi dung Nha cung cap");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, "Nhà cung cấp"), Water_Bills_Data.DATA.WATER_DAWACO);

		log.info("TC_03_Step_21: Hien thi dung ma khach hang");
		verifyEquals(waterBill.getDynamicTextByLabel(driver, "Mã khách hàng"), moneyBill);

		log.info("TC_03_Step_22: Hien thi ma giao dich");
		transactionID = waterBill.getDynamicTextByLabel(driver, "Mã giao dịch");

		log.info("TC_03_Step_23: An tiep button 'Thuc hien giao dich moi'");
		verifyEquals(waterBill.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Thực hiện giao dịch mới");
		waterBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_03_Step_24: Hien thi man hinh Hoa don tien dien");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Water_Bills_Data.DATA.WATER_BILL_TEXT);

		log.info("TC_03_Step_25: Chon tai khoan nguon");
		waterBill.clickToTextID(driver, "com.VCB:id/number_account");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_03_Step_26: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(moneyBill) - transferFee) + "";
		verifyEquals(waterBill.getDynamicTextByLabel(driver, "Số dư khả dụng"), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_03_Step_27: Chon nha cung cap");
		waterBill.clickToTextID(driver, "com.VCB:id/wrap_tv");
		waterBill.clickToDynamicButtonLinkOrLinkText(driver, Water_Bills_Data.DATA.WATER_DAWACO);

		log.info("TC_03_Step_28: Nhap ma khach hang");
		waterBill.inputToDynamicEditviewByLinearlayoutId(driver, customerID, "com.VCB:id/llCode");

		log.info("TC_03_Step_29: An nut Tiep Tuc");
		verifyEquals(waterBill.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), "Tiếp tục");
		waterBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_03_Step_30: Hien thi thong bao Ma khach hang khong con no truoc");
		verifyEquals(waterBill.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Electric_Bills_Data.VALIDATE.ELECTRIC_BILL_MESSAGE);

		log.info("TC_03_Step_31: Click nut Dong tat pop-up");
		waterBill.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_03_Step_32: Click nut Back ve man hinh chinh");
		waterBill.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
		
		log.info("TC_03_Step_32: Click nut Back ve man hinh chinh");
		waterBill.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

	}
	
	//Thanh toan truyền hình cab
	@Test
	@Parameters({ "pass" })
	public void TC_04_PhuongThucThanhToan_SmartOTP(String pass) throws InterruptedException {
		log.info("TC_04_STEP_0: chọn cước truyền hình cap");
		billTelevision.clickToDynamicButtonLinkOrLinkText(driver, "Cước truyền hình cáp");

		log.info("TC_04_STEP_0: lấy ra số dư");
		String getToltalMoney = billTelevision.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/available_balance");
		String[] toltal_money = getToltalMoney.split(" ");
		toltalMoney = Double.parseDouble(toltal_money[0].replace(",", ""));

		log.info("TC_04_STEP_1: kiem tra hien thị mac dinh");
		List<String> data = PayBillTelevison_Data.inputData.CODE_CUSTOMER;
		for (int i = 0; i < data.size(); i++) {
			log.info("TC_04_STEP_2: điền mã khách hàng");
			billTelevision.inputIntoEditTextByID(driver, data.get(i).toUpperCase(), "com.VCB:id/code");

			log.info("TC_04_STEP_3: chọn tiếp tục");
			billTelevision.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

			String locator = String.format(DynamicPageUIs.DYNAMIC_TEXT_BY_ID, "com.VCB:id/tvTitleBar");
			if (driver.findElements(By.xpath(locator)).size() > 0) {
				break;
			} else {
				log.info("TC_04_STEP_3: chọn đóng");
				billTelevision.clickToDynamicButton(driver, "Đóng");
			}
		}

		log.info("TC_04_STEP_4: chọn phương thức xác thực");
		billTelevision.clickToTextID(driver, "com.VCB:id/tvptxt");
		billTelevision.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_04_STEP_5: lấy ra số tiền thanh toán ");
		getPayments = billTelevision.getMoneyByAccount(driver, "Số tiền thanh toán");
		String[] paymentsSplit = getPayments.split(" ");
		payments = Double.parseDouble(paymentsSplit[0].replace(",", ""));

		log.info("TC_04_STEP_6: lấy ra tên dịch vụ");
		getService = billTelevision.getMoneyByAccount(driver, "Dịch vụ");

		log.info("TC_04_STEP_7: lấy nhà cung cấp");
		supplier = billTelevision.getMoneyByAccount(driver, "Nhà cung cấp");

		log.info("TC_04_STEP_8: lấy mã khách hàng");
		userCode = billTelevision.getMoneyByAccount(driver, "Mã khách hàng");

		log.info("TC_04_STEP_9: lấy ra số số phí");
		getFee = billTelevision.getMoneyByAccount(driver, "Số tiền phí");
		String[] feeSplit = getFee.split(" ");
		feeCab = Double.parseDouble(feeSplit[0].replace(",", ""));

		log.info("TC_04_STEP_10: lấy tài khoản nguồn");
		account = billTelevision.getMoneyByAccount(driver, "Tài khoản nguồn");

		log.info("TC_04_STEP_11: chọn tiếp tục");
		billTelevision.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_04_Step_Nhap ma xac thuc");
		billTelevision.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_04_Step_Tiep tuc");
		billTelevision.clickToDynamicContinue(driver, "com.VCB:id/submit");
		billTelevision.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_04_STEP_15: lấy mã hóa đơn");
		dealCode = billTelevision.getMoneyByAccount(driver, "Mã giao dịch");

		log.info("TC_04_STEP_14: lấy thời gian giao dịch");
		getTimeTransfer = billTelevision.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");

		log.info("TC_04_STEP_15: chọn thực hiện giao dịch mới");
		billTelevision.clickToDynamicButton(driver, "Thực hiện giao dịch mới");
		
		log.info("TC_04_17: kiểm tra số dư");
		billTelevision.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		String surplus = billTelevision.getDynamicTextInTransactionDetail(driver, Account_Data.Valid_Account.ACCOUNT2);
		String[] surplusSplit = surplus.split(" ");
		double surplusInt = Double.parseDouble(surplusSplit[0].replace(",", ""));
		double canculateAvailable = toltalMoney - payments - fee;
		verifyEquals(surplusInt, canculateAvailable);

		log.info("TC_04_STEP_16: chọn back");
		billTelevision.clickToDynamicImageViewByID(driver, "com.VCB:id/ivTitleLeft");

	}

	//Thanh toán cước di động trả sau
	//@Test Check lai không có phương thức smart OTP
	@Parameters({ "otp" })
	public void TC_06_CuocDiDongTraSau_Viettel_OTP(String otp) throws InterruptedException {
		log.info("TC_06_Step_01: Click Cuoc di dong tra sau");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, "Cước di động trả sau");
		postpaidMobile = PageFactoryManager.getPostpaidMobileBillPageObject(driver);

		log.info("TC_06_Step_02: Hien thi man hinh Cuoc di dong tra sau");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Postpaid_Mobile_Bill_Data.Title.POSTPAID_MOBILE_TITLE);

		log.info("TC_06_Step_03: Chon tai khoan nguon");
		postpaidMobile.clickToDynamicDropdownByHeader(driver, "Tài khoản nguồn", "1");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);
		sourceAccountMoney = postpaidMobile.getDynamicTextByLabel(driver, "Số dư khả dụng");

		log.info("TC_06_Step_04: Chon nha cung cao Viettel");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_06_Step_05: Nhap so dien thoai va an tiep tuc");
		mobilePhone = postpaidMobile.inputPhoneNumberPostPaidMobiletoUpperCase(Postpaid_Mobile_Bill_Data.DATA.LIST_VIETTEL_MOBILE);

		log.info("TC_06_Step_06: Hien thi man hinh xac nhan thong tin");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), "Xác nhận thông tin");

		log.info("TC_06_Step_07: Hien thi thông tin xac nhan");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleHead"), Postpaid_Mobile_Bill_Data.Title.VERIFY_INFO_TITLE_HEAD);

		log.info("TC_06_Step_08: Hien thi tai khoan nguon");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_06_Step_09: Hien thi ten dich vu");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Dịch vụ"), Postpaid_Mobile_Bill_Data.Title.POSTPAID_MOBILE_TITLE);

		log.info("TC_06_Step_10: Hien thi Nha cung cap");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Nhà cung cấp"), Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_06_Step_11: Hien thi So dien thoai");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Số điện thoại"), mobilePhone);

		log.info("TC_06_Step_12: Hien thi So tien thanh toan");
		mobileBill = postpaidMobile.getDynamicTextByLabel(driver, "Số tiền thanh toán");

		log.info("TC_06_Step_13: Chon phuong thuc xac thuc");
		postpaidMobile.scrollDownToText(driver, "Chọn phương thức xác thực");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/llptxt");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_06_Step_14: Kiem tra so tien phi");
		transferFee = convertAvailableBalanceCurrentcyOrFeeToLong(postpaidMobile.getDynamicTextInTransactionDetail(driver, "Số tiền phí"));

		log.info("TC_06_Step_15: An nut Tiep Tuc");
		verifyEquals(postpaidMobile.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Tiếp tục");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_06_Step_Nhap ma xac thuc");
		postpaidMobile.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_06_Step_Tiep tuc");
		postpaidMobile.clickToDynamicContinue(driver, "com.VCB:id/submit");
		postpaidMobile.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_06_Step_18: Hien thi man hinh giao dich thanh cong");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitle"), "GIAO DỊCH THÀNH CÔNG");

		log.info("TC_06_Step_19: Xac nhan hien thi dung so tien thanh toan");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvAmount"), mobileBill);

		log.info("TC_06_Step_20: Xac nhan hien thi thoi gian giao dich");
		transactionDate = postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTime");
		verifyTrue(postpaidMobile.isTextDisplayedInListTextElements(driver, getForWardDay(0), "com.VCB:id/tvTime"));

		log.info("TC_06_Step_21: Hien thi dung ten dich vu");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Dịch vụ"), Postpaid_Mobile_Bill_Data.Title.POSTPAID_MOBILE_TITLE);

		log.info("TC_06_Step_22: Hien thi dung Nha cung cap");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Nhà cung cấp"), Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_06_Step_23: Hien thi dung So dien thoai");
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Số điện thoại"), mobilePhone);

		log.info("TC_06_Step_24: Hien thi ma giao dich");
		transactionID = postpaidMobile.getDynamicTextByLabel(driver, "Mã giao dịch");

		log.info("TC_06_Step_26: Hien thi Icon Chia se");
		verifyTrue(postpaidMobile.isDynamicTextDetailByID(driver, "com.VCB:id/tvShare"));

		log.info("TC_06_Step_27: Hien thi Icon Luu anh");
		verifyTrue(postpaidMobile.isDynamicTextDetailByID(driver, "com.VCB:id/tvSavePhoto"));

		log.info("TC_06_Step_29: An tiep button 'Thuc hien giao dich moi'");
		verifyEquals(postpaidMobile.getDynamicTextButtonById(driver, "com.VCB:id/btContinue"), "Thực hiện giao dịch mới");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btContinue");

		log.info("TC_06_Step_30: Hien thi man hinh Cuoc di dong tra sau");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvTitleBar"), Postpaid_Mobile_Bill_Data.Title.POSTPAID_MOBILE_TITLE);

		log.info("TC_06_Step_31: Chon tai khoan nguon");
		postpaidMobile.clickToDynamicDropdownByHeader(driver, "Tài khoản nguồn", "1");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_06_Step_31: Xac nhan so du TK nguon da bi tru thanh cong");
		sourceAccountMoney = (convertAvailableBalanceCurrentcyOrFeeToLong(sourceAccountMoney) - convertAvailableBalanceCurrentcyOrFeeToLong(mobileBill) - transferFee) + "";
		verifyEquals(postpaidMobile.getDynamicTextByLabel(driver, "Số dư khả dụng"), addCommasToLong(sourceAccountMoney) + " VND");

		log.info("TC_06_Step_32: Chon nha cung cap Viettel");
		postpaidMobile.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		postpaidMobile.clickToDynamicButtonLinkOrLinkText(driver, Postpaid_Mobile_Bill_Data.DATA.VIETTEL_SUPPLIER);

		log.info("TC_06_Step_33: Nhap so dien thoai");
		postpaidMobile.inputToDynamicEditviewByLinearlayoutId(driver, mobilePhone, "com.VCB:id/llCode");

		log.info("TC_06_Step_34: An nut Tiep Tuc");
		verifyEquals(postpaidMobile.getDynamicTextButtonById(driver, "com.VCB:id/btn_submit"), "Tiếp tục");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btn_submit");

		log.info("TC_06_Step_35: Hien thi thong bao So dien thoai khong con no truoc");
		verifyEquals(postpaidMobile.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), Postpaid_Mobile_Bill_Data.Title.POSTPAID_MOBILE_MESSAGE);

		log.info("TC_06_Step_36: Click nut Dong tat pop-up");
		postpaidMobile.clickToDynamicAcceptButton(driver, "com.VCB:id/btOK");

		log.info("TC_06_Step_37: Click nut Back ve man hinh chinh");
		postpaidMobile.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_06_Step_38: Click nut Back ve man hinh chinh");
		postpaidMobile.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

	}
	
	//Thanh toán cước điện thoại cố định
	//@Test - Check lại ko thấy PTXT smart OTP
	public void TC_07_ThanhToanCuocDienThoaiCoDinh_CoDinhCoDay_ThanhToanMatKhauDangNhap() throws InterruptedException {
		log.info("TC_07_01_Click Cuoc dien thoai co dinh");
		landLinePhoneCharge.scrollDownToText(driver, "Cước truyền hình cáp");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, "Cước điện thoại cố định");

		log.info("TC_07_02_Chon tai khoan nguon");
		landLinePhoneCharge.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		surplus = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));

		log.info("TC_07_03_Chon loai cuoc thanh toan");
		landLinePhoneCharge.clickToTextViewByLinearLayoutID(driver, "com.VCB:id/wrap_tv");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, "Cố định có dây Viettel");

		log.info("TC_07_04_Nhap so dien thoai tra cuoc va bam Tiep tuc");
		landLinePhoneCharge.inputPhoneNumberLandLinePhoneCharge(LandLinePhoneCharge_Data.LIST_LANDLINE_PHONE_LINE);

		money1 = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Số tiền thanh toán"));
		log.info("TC_07_05_Kiem tra man hinh xac nhan thong tin");
		log.info("TC_07_05_1_Kiem tra tai khoan nguon");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Account_Data.Valid_Account.ACCOUNT2);

		log.info("TC_07_05_2_Kiem tra dich vu");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước điện thoại cố định");

		log.info("TC_07_05_3_Kiem tra nha cung cap");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), "Cố định có dây Viettel");

		log.info("TC_07_05_4_Kiem tra so dien thoai");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Số điện thoại"), LandLinePhoneChargePageObject.phoneNumber);

		log.info("TC_07_06_Chon phuong thuc xac thuc");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, "VCB - Smart OTP");

		log.info("TC_07_07_01_Kiem tra so tien phi");
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Số tiền phí"));

		log.info("TC_07_08_Click Tiep tuc");
		landLinePhoneCharge.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_07_Step_Nhap ma xac thuc");
		landLinePhoneCharge.inputToDynamicSmartOTP(driver, LogIn_Data.Login_Account.Smart_OTP, "com.VCB:id/otp");

		log.info("TC_07_Step_Tiep tuc");
		landLinePhoneCharge.clickToDynamicContinue(driver, "com.VCB:id/submit");
		landLinePhoneCharge.clickToDynamicContinue(driver, "com.VCB:id/btContinue");

		log.info("TC_07_10_Kiem tra man hinh Chuyen khoan thanh cong");
		log.info("TC_07_10_1_Kiem tra Chuyen khoan thanh cong");
		verifyTrue(landLinePhoneCharge.isDynamicMessageAndLabelTextDisplayed(driver, LandLinePhoneCharge_Data.SUCCESS_TRANSFER_MONEY));

		log.info("TC_07_10_2_Kiem tra dich vu");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Dịch vụ"), "Cước điện thoại cố định");

		log.info("TC_07_10_3_Kiem tra nha cung cap");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Nhà cung cấp"), "Cố định có dây Viettel");

		log.info("TC_07_10_3_So dien thoai");
		verifyEquals(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Số điện thoại"), LandLinePhoneChargePageObject.phoneNumber);

		log.info("TC_07_10_4_Kiem tra nut Thuc hien giao dich moi");
		verifyTrue(landLinePhoneCharge.isDynamicButtonDisplayed(driver, "Thực hiện giao dịch mới"));

		log.info("TC_07_10_5_Lay ma giao dich");
		transferTime = landLinePhoneCharge.getTransferTimeSuccess(driver, LandLinePhoneCharge_Data.SUCCESS_TRANSFER_MONEY);
		transactionNumber = landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Mã giao dịch");

		log.info("TC_07_11_Click Thuc hien giao dich moi");
		landLinePhoneCharge.clickToDynamicButton(driver, "Thực hiện giao dịch mới");

		log.info("TC_07_11_Kiem tra so du kha dung luc sau");
		landLinePhoneCharge.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		landLinePhoneCharge.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT2);

		actualAvailableBalance = convertAvailableBalanceCurrentcyOrFeeToLong(landLinePhoneCharge.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng"));
		availableBalance = canculateAvailableBalances(surplus, money1, fee);
		verifyEquals(actualAvailableBalance, availableBalance);

	}
	@Test
	public void TC_08_HuyKichHoatVCBSmartOPT() {
		log.info("TC_08_Step_01: Click menu header");
		smartOTP.clickToDynamicImageViewByID(driver, "com.VCB:id/menu_5");

		log.info("TC_08_Step_02: Click thanh Cai dat VCB-Smart OTP");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt");

		log.info("TC_08_Step_03: Click Cai dat VCB Smart OTP");
		smartOTP.waitForElementVisible(driver, DynamicPageUIs.DYNAMIC_BUTTON_LINK_LABEL_TEXT, "Cài đặt VCB-Smart OTP");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Cài đặt VCB-Smart OTP");

		log.info("TC_08_Step_04: Click btn Huy Cai dat");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Hủy");

		log.info("TC_08_Step_05: Verify hien thi popup xac nhan huy cai dat OTP");
		smartOTP.isDynamicMessageAndLabelTextDisplayed(driver, SettingVCBSmartOTP_Data.MESSEGE_CONFIRM_CANCEL);

		log.info("TC_08_Step_06: Verify hien thi popup xac nhan huy cai dat OTP");
		smartOTP.clickToDynamicButtonLinkOrLinkText(driver, "Có");
	}
	
	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
