package vnpay.vietcombank.transfer_money_quick_247;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Validation_QuickMoneyTransfer247_4 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private TransferMoneyObject transferMoney;
	private String amountStartString;
	private String amountExpectString;
	List<String> listExpect;
	List<String> listActual;
	private String amountExpect;

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

		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);

		log.info("TC_01_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");
	}

	@Test
	public void TC_48_KiemTraLabelSoTienEUR() {
		log.info("TC_48_Chon tai khoan nguon EUR");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[2]);

		log.info("TC_48_Kiem tra label so tien");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Số tiền"));

		log.info("TC_48_Kiem tra label ty gia quy doi tham khao");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Tỷ giá quy đổi tham khảo"));

		log.info("TC_48_Kiem tra label ty gia quy doi tham khao");
		verifyTrue(transferMoney.isDynamicSuggestedMoneyDisplayed(driver, "com.VCB:id/tvTiGia"));
	}

	@Test
	public void TC_49_KiemTraLabelLoaiTienEUR() {
		log.info("TC_49_Kiem tra label loai tien");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "EUR"));
	}

	@Test
	public void TC_50_NhapNhoHon10KyTuVaKiemTraDauChamEUR() {
		log.info("TC_50_Input so tien co 9 ky tu phan nguyrm va 2 chu so phan thap phan");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

		log.info("TC_50_text mac dinh 'So tien' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Số tiền"));

		log.info("TC_50_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_VND;
		amountExpectString = String.format("%,d", Long.parseLong(amountExpect.toString()));
		String amountExpectDoubleString = amountExpectString + TransferMoneyQuick_Data.TransferQuick.MONEY_DOUBLE_NUMBER_USD_EUR;

		log.info("TC_50_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpectDoubleString);

	}

	@Test
	public void TC_51_Nhap10KyTuVaKiemTraDauChamEUR() {
		log.info("TC_51_Input so tien co 10 ky tu");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_TEN_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

		log.info("TC_51_text mac dinh 'So tien' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Số tiền"));

		log.info("TC_51_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_TEN_NUMBER_VND;
		amountExpectString = String.format("%,d", Long.parseLong(amountExpect.toString()));

		String amountExpectDoubleString = amountExpectString + TransferMoneyQuick_Data.TransferQuick.MONEY_DOUBLE_NUMBER_USD_EUR;

		log.info("TC_51_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpectDoubleString);
	}

	@Test
	public void TC_52_KiemTraDinhDangHienThiEUR() {
		log.info("TC_52_Invalid so tien co 9 chu so phan nguyen va 3 chu so phan thap phan");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_INVALID_USD_EUR, "Thông tin giao dịch", "1");

		log.info("Khong sendkey duoc gia tri, mac dinh de trong va hien thi text so tien");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Số tiền"));

		log.info("TC_52_Input so tien co 9 chu so phan nguyen va 2 chu so phan thap phan");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

		log.info("TC_52_Lay gia tri nhap vao");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		log.info("TC_52_verify ket qua hien thi khi focus ra ngoai so tien duoc giu nguyen");
		String moneyExpect = addCommasToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_USD_EUR);
		verifyEquals(amountStartString, moneyExpect);
	}

	@Test
	// --- Lỗi app hiển thị dấu , ngăn cách hàng nghìn đang bị sai
	public void TC_53_KiemTraDinhDangHienThiGoiYEUR() {
		log.info("TC_53_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

		log.info("TC_53_Lay danh sach goi y");
		listActual = transferMoney.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvAmount");

		log.info("TC_53_danh sach gia tri so tien goi y");
		listExpect = Arrays.asList(TransferMoneyQuick_Data.TransferQuick.LIST_MONEY_SHOW_EUR);

		log.info("TC_53_Kiem tra so tien goi y");
		verifyTrue(transferMoney.checkListContain(listActual, listExpect));
	}

	@Test
	public void TC_54_KiemTraKhongHienThiGoiYNhanhKhiFocusRaNgoaiEUR() {
		log.info("TC_54_Click text thong tin giao dich focus ra ngoai");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_54_Kiem tra gia tri goi y khong hien thi tren man hinh");
		verifyTrue(transferMoney.isDynamicSuggestedMoneyUndisplayed(driver, "com.VCB:id/tvAmount"));

		log.info("TC_54_verify ket qua hien thi khi focus ra ngoai so tien duoc giu nguyen");
		String moneyExpect = addCommasToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_USD_EUR);
		verifyEquals(transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1"), moneyExpect);
	}

	@Test
	// ----Loi app không hiển thị popup gợi ý
	public void TC_55_KiemTraHienThiGoiYNhanhTiepTucFocusVaoSoTienEUR() {
		log.info("TC_55_Click vao so tien vua nhap tren");
		transferMoney.clickToDynamicInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		log.info("TC_55_Lay danh sach goi y");
		listActual = transferMoney.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvAmount");

		log.info("TC_55_danh sach gia tri so tien goi y");
		listExpect = Arrays.asList(TransferMoneyQuick_Data.TransferQuick.LIST_MONEY_SHOW_EUR);

		log.info("TC_55_Kiem tra so tien goi y");
		verifyTrue(transferMoney.checkListContain(listActual, listExpect));
	}

	@Test
	// --- Loi app vẫn hiển thị popup gợi ý x10, x100
	public void TC_56_ChonMotGoiYVaDongPopupEUR() {
		log.info("TC_56_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

		log.info("TC_56_Click vao so tien goi y");
		transferMoney.clickToDynamicSuggestedMoney(driver, 1, "com.VCB:id/tvAmount");

		log.info("TC_56_Verify khong co danh sach goi y");
		verifyFailure(transferMoney.isDynamicSuggestedMoneyDisplayed(driver, "com.VCB:id/tvAmount"));
	}

	@Test
	public void TC_57_BoTrongTruongSoTien() {
		log.info("TC_57_Clear text truong nhap so tien");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/ivClearInput");

		log.info("TC_57_Nhap so tai khoan huong");
		transferMoney.inputToDynamicInputBoxByHeader(driver, Account_Data.Valid_Account.ACCOUNT_TO, "Thông tin người hưởng", "1");

		log.info("TC_57_chon ngan hang huong");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.BANK[0]);

		log.info("TC_57_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_57_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_057_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_057_Step_verify message khi tai khoan nguoi nhan trong ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.AMOUNT_BLANK));

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_58_NhapSoTienHopLe() {
		log.info("TC_58_Chon tai khoan nguon VND");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[3]);

		log.info("TC_58_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Thông tin giao dịch", "1");

		log.info("TC_58_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_58_verify man xac nhan thong tin khi nhap so tien hop le");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Xác nhận thông tin"));

		log.info("TC_58_Step_Kiem tra button back");
		transferMoney.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	public void TC_59_NhapSoTienNhoHonHanMucToiThieu() {
		log.info("TC_59_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_LESS_MIN_LIMIT_VND, "Thông tin giao dịch", "1");

		log.info("TC_59_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_59_Step_verify message khi so tien chuyen nho hon han muc toi thieu ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.MESSAGE_LESS_MIN_LIMIT_VND));

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_60_NhapSoTienLonHonHanMucToiDa() {
		log.info("TC_60_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_EXCEED_MAX_LIMIT_VND, "Thông tin giao dịch", "1");

		log.info("TC_60_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_60_Step_verify message khi so tien chuyen lon hon han muc toi da ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.MESSAGE_EXCEED_MAX_LIMIT_VND));

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_61_NhapSoTienLonHonHanMucToiDaTrongMotNgay() {
		log.info("TC_61_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_EXCEED_MAX_TRANSFER_ONE_DAY_VND, "Thông tin giao dịch", "1");

		log.info("TC_61_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_61_Step_verify message khi so tien chuyen lon hon han muc toi da ");
		verifyEquals(transferMoney.getTextDynamicInSelectBox(driver, "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 5,000,000 VND/1 ngày của nhóm dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp."), TransferMoneyQuick_Data.MessageTransferMoney.MESSAGE_EXCEED_MAX_TRANSFER_ONE_DAY_VND);

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_62_NhapSoTienLonHonHanMucToiDaNhomTrongMotNgay() {
		log.info("TC_62_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_EXCEED_MAX_TRANSFER_TYPE_ONE_DAY_VND, "Thông tin giao dịch", "1");

		log.info("TC_62_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_62_Step_verify message khi so tien chuyen lon hon han muc toi da ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.MESSAGE_EXCEED_MAX_TRANSFER_TYPE_ONE_DAY_VND));

		log.info("TC_62_Step_verify message khi so tien chuyen lon hon han muc toi da ");
		verifyEquals(transferMoney.getTextDynamicInSelectBox(driver, "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 8,000,000 VND/1 ngày của nhóm dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp"), TransferMoneyQuick_Data.MessageTransferMoney.MESSAGE_EXCEED_MAX_TRANSFER_TYPE_ONE_DAY_VND);

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_63_NhapSoTienLonHonHanMucToiDaGoiTrongMotNgay() {
		log.info("TC_63_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_EXCEED_MAX_TRANSFER_PACKAGE_ONE_DAY_VND, "Thông tin giao dịch", "1");

		log.info("TC_63_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_63_Step_verify message khi so tien chuyen lon hon han muc toi da ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.MESSAGE_EXCEED_MAX_TRANSFER_PACKAGE_ONE_DAY_VND));

		log.info("TC_62_Step_verify message khi so tien chuyen lon hon han muc toi da ");
		verifyEquals(transferMoney.getTextDynamicInSelectBox(driver, "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 9,000,000 VND/1 ngày của gói dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp."), TransferMoneyQuick_Data.MessageTransferMoney.MESSAGE_EXCEED_MAX_TRANSFER_PACKAGE_ONE_DAY_VND);

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

}
