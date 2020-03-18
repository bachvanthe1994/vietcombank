package vnpay.vietcombank.TransferIdentityCard;

import java.io.IOException;

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
import pageObjects.TransactionReportPageObject;
import pageObjects.TransferIdentiryPageObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.Account_Data.Invalid_Account;
import vietcombank_test_data.Account_Data.Valid_Account;
import vietcombank_test_data.TransferIdentity_Data;

public class TransferIdentity_Validate_2 extends Base {
    AppiumDriver<MobileElement> driver;
    private LogInPageObject login;
    private HomePageObject homePage;
    private TransferIdentiryPageObject trasferPage;
    private TransactionReportPageObject transReport;
    private String transferTime;
    private String transactionNumber;
    String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();

    @Parameters({ "deviceType", "deviceName", "deviceUDID", "hubURL", "appActivities", "appPackage", "appName", "phone", "pass", "otp" })
    @BeforeClass
    public void beforeClass(String deviceType, String deviceName, String udid, String url, String appActivities, String appPackage, String appName, String phone, String pass, String opt)
	    throws IOException, InterruptedException {
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
	trasferPage = PageFactoryManager.getTransferIdentiryPageObject(driver);

    }

    @Test
    public void TC_33_KiemTraHienThiNoiDungSauKhiNhapKiTuDacBiet() {
	log.info("TC_33_Step_01: Click Chuyen tien trong VCB");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_33_STEP_02: nhap noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.SPECIAL_CHARACTERS, "3");

	log.info("TC_33_STEP_03: kiem tra hien thi noi dung vua nhap");
	verifyEquals(trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3"), TransferIdentity_Data.textDataInputForm.SPECIAL_CHARACTERS);

	log.info("TC_33_Step_04: Click quay lai");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_34_KiemTraHienThiNoiDungNhapMaxLength140() {
	log.info("TC_34_Step_01: Click Chuyen tien trong VCB");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_34_STEP_02: nhap noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_140, "3");

	log.info("TC_34_STEP_03: kiem tra hien thi noi dung vua nhap");
	verifyEquals(trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3"), TransferIdentity_Data.textDataInputForm.MAX_LENGTH_140);

	log.info("TC_34_Step_04: Click quay lai");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_35_KiemTraHienThiNoiDungNhapMaxLength141() {
	log.info("TC_35_Step_01: Click Chuyen tien trong VCB");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_35_STEP_02: nhap noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.MAX_LENGTH_141, "3");

	log.info("TC_35_STEP_03: kiem tra hien thi noi dung vua nhap");
	verifyEquals(trasferPage.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3"), TransferIdentity_Data.textDataInputForm.MAX_LENGTH_140);

	log.info("TC_35_Step_04: Click quay lai");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_36_BoTrongTenThuHuong() {
	log.info("TC_36_Step_01: Click Chuyen tien trong VCB");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_36_Step_02: bỏ trống tên người thụ hưởng");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_36_STEP_03: kiểm tra hiển thị thông báo khi không nhập tên người thụ hưởng");
	verifyEquals(trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferIdentity_Data.confirmMessage.MESSSAGE_USER);

	log.info("TC_36_Step_04: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");
    }

    @Test
    public void TC_37_BoTrongGiayToTuyThan() {
	log.info("TC_37_STEP_01: nhap người thụ hưởng");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");

	log.info("TC_37_Step_02: bỏ trống giấy tờ tùy thân");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_37_STEP_03: kiểm tra hiển thị thông báo khi không nhập tên người thụ hưởng");
	verifyEquals(trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferIdentity_Data.confirmMessage.MESSSAGE_IDENTITY);

	log.info("TC_37_Step_04: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");
    }

    @Test
    public void TC_38_BoTrongSo() {
	log.info("TC_38_Step_01: chon giay to tuy than");
	trasferPage.clickToDynamicDropdownByHeader(driver, "Thông tin người hưởng", "3");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Chứng minh nhân dân");

	log.info("TC_38_Step_02: bỏ trống số giấy tờ tùy thân");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_38_STEP_13: kiểm tra hiển thị thông báo khi không nhập số giấy tờ tùy thân");
	verifyEquals(trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferIdentity_Data.confirmMessage.MESSSAGE_IDENTITY_NUMBER);

	log.info("TC_38_Step_04: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

    }

    @Test
    public void TC_39_BoTrongNgayCap() {
	log.info("TC_39_STEP_1: nhap số giấy tờ tùy thân");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.IDENTITY_NUMBER, "Số");

	log.info("TC_39_Step_2: bỏ trống ngày cấp");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_39_STEP_3: kiểm tra hiển thị thông báo khi không nhập ngày cấp");
	verifyEquals(trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferIdentity_Data.confirmMessage.MESSSAGE_DATE);

	log.info("TC_39_Step_4: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

    }

    @Test
    public void TC_40_BoTrongNoiCap() {
	log.info("TC_40_Step_1: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_40_Step_2: bỏ trống nơi cấp");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_40_STEP_2: kiểm tra hiển thị thông báo khi không nhập nới cấp");
	verifyEquals(trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferIdentity_Data.confirmMessage.MESSSAGE_ISSUED);

	log.info("TC_40_Step_3: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_40_Step_4: noi cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Nơi cấp");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, TransferIdentity_Data.textDataInputForm.ISSUED);
    }

    @Test
    public void TC_41_BoTrongSoTienVaChonTiepTuc() {
	log.info("TC_41_Step_1: bỏ trống số tiền");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_41_STEP_2: kiểm tra hiển thị thông báo khi không nhập số tiền");
	verifyEquals(trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferIdentity_Data.confirmMessage.MESSSAGE_MONEY);

	log.info("TC_41_Step_3: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");
    }

    @Test
    public void TC_42_BoTrongNoiDung() {
	log.info("TC_42_Step_1: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");

	log.info("TC_42_Step_2: chọn tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_42_STEP_3: kiểm tra hiển thị thông báo khi không nhập nội dung");
	verifyEquals(trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferIdentity_Data.confirmMessage.MESSSAGE_CONTENT);

	log.info("TC_42_Step_4: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_42_Step_5: nhap nội dung");
	trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT, "3");
    }

    @Test
    public void TC_43_TaiKhoanDongChuSoHuu() {
	log.info("TC_43_Step_28: kiểm tra khi tài đồng chủ sở hữu");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Invalid_Account.SAME_OWNER_ACCOUNT_3);

	log.info("TC_43_Step_29: chon tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_43_STEP_30: kiểm tra hiển thị thông báo khi chọn tài khoản đồng chủ sở hữu");
	verifyEquals(trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferIdentity_Data.confirmMessage.MESSSAGE_ACCOUNT_CO_OWNER);

	log.info("TC_43_Step_27: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");
    }

    @Test
    public void TC_44_TaiKhoanKhongDuSoDu() {
	log.info("TC_44_Step_1: chọn tài khoản nguồn kiểm tra không đủ số dư");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

	log.info("TC_44_Step_02: Chon tai khoan dich");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.ACCOUNT1);

	log.info("TC_44_Step_03: Lay so du tai khoan chuyen");
	trasferPage.sleep(driver, 2000);
	String beforeBalanceOfAccount1 = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
	long beforeBalanceAmountOfAccount1 = convertMoneyToLong(beforeBalanceOfAccount1, "VND");

	log.info("TC_38_Step_04: Nhap So Tien Chuyen");
	trasferPage.inputToDynamicInputBoxByHeader(driver, beforeBalanceAmountOfAccount1 + 1000 + "", "Thông tin giao dịch", "1");

	log.info("TC_44_Step_05: chọn tiếp tục");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_44_STEP_06: kiểm tra hiển thị thông báo khi chọn tài không đủ số dư");
	verifyEquals(trasferPage.getDynamicTextDetailByIDOrPopup(driver, "com.VCB:id/tvContent"), TransferIdentity_Data.confirmMessage.MESSSAGE_ACCOUNT_MONEY);

	log.info("TC_44_Step_07: chọn đóng");
	trasferPage.clickToDynamicButton(driver, "Đóng");

	log.info("TC_44_Step_08 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");
    }

    @Test
    public void TC_45_ManHinhXacNhanGiaoDich() {
	log.info("TC_45_Step_1: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_45_Step_2: chon tai khoan");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");

	homePage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT2);

	log.info("TC_45_Step_3: lay so tien truoc khi chuyen khoan");
	String overbalanceBefore = trasferPage.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng");
	Long overbalanceBeforeLong = convertMoneyToLong(overbalanceBefore, "VND");

	log.info("TC_45_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");

	log.info("TC_45_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

	log.info("TC_45_Step_6: so HC");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER, "Số");

	log.info("TC_45_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_45_Step_8: noi cap");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.ISSUED, "Nơi cấp");

	log.info("TC_45_Step_9: nguoi tra phi giao dich");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

	log.info("TC_45_Step_10: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

	log.info("TC_45_Step_11: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_45_Step_12: tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	log.info("TC_45_Step_13: xac nhan thong tin");
	String confirm = trasferPage.getTextDynamicInSelectBox(driver, TransferIdentity_Data.textCheckElement.PAGE_CONFIRM).trim();
	verifyTrue(confirm.equals(TransferIdentity_Data.textCheckElement.PAGE_CONFIRM));

	log.info("TC_45_Step_14: kiem tra tai khoan nguon");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Tài khoản nguồn"), Valid_Account.ACCOUNT2);

	log.info("TC_45_Step_15: kiem tra ten nguoi thu huong");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Tên người thụ hưởng"), TransferIdentity_Data.textDataInputForm.USER_NAME);

	log.info("TC_45_Step_16: kiem tra giay to tuy than");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Giấy tờ tùy thân"), "Hộ chiếu");

	log.info("TC_45_Step_17: kiem tra so HC");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Số"), TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER);

	log.info("TC_45_Step_18: kiem tra ngay cap");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Ngày cấp"), today);

	log.info("TC_45_Step_19: kiem tra noi cap");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Nơi cấp"), TransferIdentity_Data.textDataInputForm.ISSUED);

	log.info("TC_45_Step_20: lay ra so tien chuyen di");
	String moneyTransfer = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền");
	Long moneyTransferLong = convertMoneyToLong(moneyTransfer, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
	String moneyTransferLongToString = Long.toString(moneyTransferLong);

	log.info("TC_45_Step_21: kiem tra so tien");
	verifyEquals(moneyTransferLongToString, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND);

	log.info("TC_45_Step_22: lay phi");
	String amount = trasferPage.getDynamicTextInTransactionDetail(driver, "Số tiền phí");
	Long amountLong = convertMoneyToLong(amount, TransferIdentity_Data.textDataInputForm.CURRENCY_VND);
	String amountLongToString = Long.toString(amountLong);

	log.info("TC_45_Step_23: kiem tra noi dung");
	verifyEquals(trasferPage.getDynamicTextInTransactionDetail(driver, "Nội dung"), TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER);

	trasferPage.scrollDownToButton(driver, "Tiếp tục");
	log.info("TC_45_Step_24: chon phuong thuc xac thuc");
	trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

	log.info("TC_45_Step_25: lay phi giao dich theo phuong thuc xac thuc");
	String moneyAuthen = trasferPage.getDynamicAmountLabelConvertVNDToLong(driver, "Mật khẩu đăng nhập");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

	log.info("TC_45_Step_26: kiem tra phi giao dich");
	verifyEquals(moneyAuthen, amountLongToString);

	log.info("TC_45_Step_27: kiem tra so tien phi");
	verifyEquals(amountLongToString, TransferIdentity_Data.textCheckElement.AMOUNT);
    }

    @Test
    public void TC_46_KiemTraNutBack() {
	log.info("TC_46_Step_28 : Click  nut Back");
	trasferPage.clickToDynamicBackIcon(driver, "Xác nhận thông tin");

	log.info("TC_46_Step_04: Click quay lai");
	trasferPage.clickToDynamicBackIcon(driver, "Chuyển tiền cho người nhận tại quầy");

    }

    @Test
    public void TC_47_KiemTraNutHome() {
	log.info("TC_47_Step_1: chon chuyển tiền nhận bằng CMT");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhận bằng CMT");

	log.info("TC_47_Step_2: chon tai khoan");
	trasferPage.clickToDynamicDropDown(driver, "Tài khoản nguồn");
	homePage.clickToDynamicButtonLinkOrLinkText(driver, Valid_Account.ACCOUNT2);

	log.info("TC_47_Step_4: nhap ten nguoi thu huong");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.USER_NAME, "Tên người thụ hưởng");

	log.info("TC_47_Step_5: chon giay to tuy than");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Giấy tờ tùy thân");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Hộ chiếu");

	log.info("TC_47_Step_6: so HC");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.PASSPORT_NUMBER, "Số");

	log.info("TC_47_Step_7: ngay cap");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Ngày cấp");
	trasferPage.clickToDynamicButton(driver, "OK");

	log.info("TC_47_Step_8: noi cap");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.ISSUED, "Nơi cấp");

	log.info("TC_47_Step_9: nguoi tra phi giao dich");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Phí giao dịch người chuyển trả");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Người chuyển trả");

	log.info("TC_47_Step_10: nhap so tien chuyen di");
	trasferPage.inputToDynamicInputBox(driver, TransferIdentity_Data.textDataInputForm.MONEY_TRANSFER_VND, "Số tiền");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

	log.info("TC_38_Step_11: noi dung");
	trasferPage.inputToDynamicInputBoxContent(driver, TransferIdentity_Data.textDataInputForm.CONTENT_TRANSFER, "3");

	log.info("TC_47_Step_12: tiep tuc");
	trasferPage.clickToDynamicButton(driver, "Tiếp tục");

	trasferPage.scrollDownToButton(driver, "Tiếp tục");
	log.info("TC_47_Step_24: chon phuong thuc xac thuc");
	trasferPage.clickToDynamicDropDown(driver, "Chọn phương thức xác thực");

	log.info("TC_47_Step_25: lay phi giao dich theo phuong thuc xac thuc");
	trasferPage.clickToDynamicButtonLinkOrLinkText(driver, "Mật khẩu đăng nhập");

	log.info("TC_47_Step_28: Click home Icon");
	trasferPage.clickToDynamicHomeIcon(driver, "Xác nhận thông tin");

    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
//	closeApp();
//	service.stop();
    }

}
