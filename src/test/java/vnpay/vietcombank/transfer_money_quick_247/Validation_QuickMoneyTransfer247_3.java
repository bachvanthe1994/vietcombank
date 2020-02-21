package vnpay.vietcombank.transfer_money_quick_247;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.Base;
import commons.PageFactoryManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.LogInPageObject;
import pageObjects.TransferMoneyObject;
import vietcombank_test_data.Account_Data;
import vietcombank_test_data.TransferMoneyQuick_Data;

public class Validation_QuickMoneyTransfer247_3 extends Base {
	AndroidDriver<AndroidElement> driver;
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
		driver = openAndroidApp(deviceType, deviceName, udid, url, appActivities, appPackage, appName);
		login = PageFactoryManager.getLoginPageObject(driver);
		login.Global_login(phone, pass, opt);

		transferMoney = PageFactoryManager.getTransferMoneyObject(driver);
	}

	@Test
	public void TC_30_KiemTraLabelSoTienVND() {
		log.info("TC_30_Step_Click Chuyen tien nhanh");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Chuyển tiền nhanh 24/7");

		log.info("TC_30_Kiem tra label so tien");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Số tiền"));

		log.info("TC_30_Kiem tra label loai tien");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "VND"));
	}

	@Test
	public void TC_31_NhapNhoHon10KyTuVaKiemTraDauPhayVND() {
		log.info("TC_31_Input so tien co 9 ky tu");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_VND, "Thông tin giao dịch", "1");

		log.info("TC_31_text mac dinh 'So tien' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Số tiền"));

		log.info("TC_31_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		log.info("TC_31_So tien Nhap");
		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_VND;
		amountExpectString = String.format("%,d", Long.parseLong(amountExpect.toString()));

		log.info("TC_31_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpectString);
	}

	@Test
	public void TC_32_Nhap10KyTuVaKiemTraDauPhayVND() {
		log.info("TC_32_Input so tien co 10 ky tu");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_TEN_NUMBER_VND, "Thông tin giao dịch", "1");

		log.info("TC_32_text mac dinh 'So tien' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Số tiền"));

		log.info("TC_32_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		log.info("TC_32_So tien Nhap");
		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_TEN_NUMBER_VND;
		amountExpectString = String.format("%,d", Long.parseLong(amountExpect.toString()));

		log.info("TC_32_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpectString);
	}

	@Test
	public void TC_33_NhapLonHon10KyTuVaKiemTraDauPhayVND() {
		log.info("TC_33_Input so tien lon hon 10 ky tu");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_ELEVEN_NUMBER_VND, "Thông tin giao dịch", "1");

		log.info("TC_33_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		log.info("TC_33_Khong cho phep nhap hon 10 ky tu, truong so tien trong");
		verifyEquals(amountStartString, "Số tiền");
	}

	@Test
	public void TC_34_NhapKyTuChuVaKiemTraDauPhayVND() {
		log.info("TC_34_Input ky tu chu");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_TEXT_INVALID_VND, "Thông tin giao dịch", "1");

		log.info("TC_34_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_TEXT_INVALID_VND.replaceAll("\\D+", "");
		amountExpectString = String.format("%,d", Long.parseLong(amountExpect.toString()));

		log.info("TC_34_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpectString);
	}

	@Test
	public void TC_35_KiemTraHienThiGoiYNhanhVND() {
		log.info("TC_35_Chon tai khoan nguon VND");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_35_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_VND, "Thông tin giao dịch", "1");

		log.info("TC_35_Lay danh sach goi y");
		listActual = transferMoney.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvAmount");

		log.info("TC_35_danh sach gia tri so tien goi y");
		listExpect = Arrays.asList(TransferMoneyQuick_Data.TransferQuick.LIST_MONEY_SHOW_VND);

		log.info("TC_35_Kiem tra so tien goi y");
		verifyTrue(transferMoney.checkListContain(listActual, listExpect));
	}

	@Test
	public void TC_36_KiemTraKhongHienThiGoiYNhanhKhiFocusRaNgoaiVND() {
		log.info("TC_36_Click text thong tin giao dich focus ra ngoai");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_36_Kiem tra gia tri goi y khong hien thi tren man hinh");
		verifyTrue(transferMoney.isDynamicSuggestedMoneyUndisplayed(driver, "com.VCB:id/tvAmount"));

		log.info("TC_36_verify ket qua hien thi khi focus ra ngoai so tien duoc giu nguyen");
		String moneyExpect = addCommasToLong(TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_VND);
		verifyEquals(transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1"), moneyExpect);

	}

	@Test
	// ----Loi app không hiển thị popup gợi ý
	public void TC_37_KiemTraHienThiGoiYNhanhTiepTucFocusVaoSoTienVND() {
		log.info("TC_37_Click vao so tien vua nhap tren");
		transferMoney.clickToDynamicInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		log.info("TC_37_Lay danh sach goi y");
		listActual = transferMoney.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvAmount");

		log.info("TC_37_danh sach gia tri so tien goi y");
		listExpect = Arrays.asList(TransferMoneyQuick_Data.TransferQuick.LIST_MONEY_SHOW_VND);

		log.info("TC_37_Kiem tra so tien goi y");
		verifyTrue(transferMoney.checkListContain(listActual, listExpect));
	}

	@Test
	// ---- Loi app vẫn hiển thị popup gợi ý x10, x100
	public void TC_38_ChonMotGoiYVaDongPopupVND() {
		log.info("TC_38_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_VND, "Thông tin giao dịch", "1");

		log.info("TC_38_Click vao so tien goi y");
		transferMoney.clickToDynamicSuggestedMoney(driver, 1, "com.VCB:id/tvAmount");

		verifyFailure(transferMoney.isDynamicSuggestedMoneyDisplayed(driver, "com.VCB:id/tvAmount"));

	}

	@Test
	public void TC_39_KiemTraLabelSoTienUSD() {
		log.info("TC_39_Clear text truong nhap so tien");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/ivClearInput");

		log.info("TC_39_Chon tai khoan nguon USD");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[1]);

		log.info("TC_39_Kiem tra label so tien");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Số tiền"));

		log.info("TC_39_Kiem tra label ty gia quy doi tham khao");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Tỷ giá quy đổi tham khảo"));

		log.info("TC_39_Kiem tra label ty gia quy doi tham khao");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "1 USD ~ 30,000 VND"));
	}

	@Test
	public void TC_40_KiemTraLabelLoaiTienUSD() {
		log.info("TC_40_Kiem tra label loai tien");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "USD"));
	}

	@Test
	public void TC_41_NhapNhoHon10KyTuVaKiemTraDauChamUSD() {
		log.info("TC_41_Input so tien co 9 ky tu phan nguyen va 2 chu so phan thap phan");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

		log.info("TC_41_text mac dinh 'So tien' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Số tiền"));

		log.info("TC_41_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_VND;
		amountExpectString = String.format("%,d", Long.parseLong(amountExpect.toString()));
		String amountExpectDoubleString = amountExpectString + TransferMoneyQuick_Data.TransferQuick.MONEY_DOUBLE_NUMBER_USD_EUR;
		log.info("TC_41_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpectDoubleString);

	}

	@Test
	public void TC_42_Nhap10KyTuVaKiemTraDauChamUSD() {
		log.info("TC_42_Input so tien co 10 ky tu");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_TEN_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

		log.info("TC_42_text mac dinh 'So tien' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Số tiền"));

		log.info("TC_42_Lay so tien sau khi nhap");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_TEN_NUMBER_VND;
		amountExpectString = String.format("%,d", Long.parseLong(amountExpect.toString()));

		String amountExpectDoubleString = amountExpectString + TransferMoneyQuick_Data.TransferQuick.MONEY_DOUBLE_NUMBER_USD_EUR;

		log.info("TC_41_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpectDoubleString);
	}

	@Test
	public void TC_43_KiemTraDinhDangHienThiUSD() {
		log.info("TC_43_Invalid so tien co 9 chu so phan nguyen va 3 chu so phan thap phan");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_INVALID_USD_EUR, "Thông tin giao dịch", "1");

		log.info("Khong sendkey duoc gia tri, mac dinh de trong va hien thi text so tien");
		verifyTrue(transferMoney.isDynamicTextInInputBoxDisPlayed(driver, "Số tiền"));

		log.info("TC_43_Input so tien co 9 chu so phan nguyen va 2 chu so phan thap phan");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

		log.info("TC_43_Lay gia tri nhap vao");
		amountStartString = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		amountExpect = TransferMoneyQuick_Data.TransferQuick.MONEY_NINE_NUMBER_VND;
		amountExpectString = String.format("%,d", Long.parseLong(amountExpect.toString()));
		String amountExpectDoubleString = amountExpectString + TransferMoneyQuick_Data.TransferQuick.MONEY_DOUBLE_NUMBER_USD_EUR;

		log.info("TC_43_Kiem tra dau phay ngan cach hang ngan");
		verifyEquals(amountStartString, amountExpectDoubleString);
	}

	@Test
	// --- Lỗi app hiển thị dấu , ngăn cách hàng nghìn đang bị sai
	public void TC_44_KiemTraDinhDangHienThiGoiYUSD() {
		log.info("TC_44_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

		log.info("TC_44_Lay danh sach goi y");
		listActual = transferMoney.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvAmount");

		log.info("TC_44_danh sach gia tri so tien goi y");
		listExpect = Arrays.asList(TransferMoneyQuick_Data.TransferQuick.LIST_MONEY_SHOW_USD);

		log.info("TC_44_Kiem tra so tien goi y");
		verifyTrue(transferMoney.checkListContain(listActual, listExpect));
	}

	@Test
	public void TC_45_KiemTraKhongHienThiGoiYNhanhKhiFocusRaNgoaiUSD() {
		log.info("TC_45_Click text thong tin giao dich focus ra ngoai");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Thông tin giao dịch");

		log.info("TC_45_Kiem tra gia tri goi y khong hien thi tren man hinh");
		verifyTrue(transferMoney.isDynamicSuggestedMoneyUndisplayed(driver, "com.VCB:id/tvAmount"));

		log.info("TC_45_verify ket qua hien thi khi focus ra ngoai so tien duoc giu nguyen");
		String moneyExpect = addCommasToDouble(TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_USD_EUR);
		verifyEquals(transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "1"), moneyExpect);
	}

	@Test
	// ----Loi app không hiển thị popup gợi ý
	public void TC_46_KiemTraHienThiGoiYNhanhTiepTucFocusVaoSoTienUSD() {
		log.info("TC_46_Click vao so tien vua nhap tren");
		transferMoney.clickToDynamicInputBoxByHeader(driver, "Thông tin giao dịch", "1");

		log.info("TC_46_Lay danh sach goi y");
		listActual = transferMoney.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvAmount");

		log.info("TC_46_danh sach gia tri so tien goi y");
		listExpect = Arrays.asList(TransferMoneyQuick_Data.TransferQuick.LIST_MONEY_SHOW_USD);

		log.info("TC_46_Kiem tra so tien goi y");
		verifyTrue(transferMoney.checkListContain(listActual, listExpect));

	}

	@Test
	// --- Loi app vẫn hiển thị popup gợi ý x10, x100
	public void TC_47_ChonMotGoiYVaDongPopupUSD() {
		log.info("TC_47_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

		log.info("TC_47_Click vao so tien goi y");
		transferMoney.clickToDynamicSuggestedMoney(driver, 1, "com.VCB:id/tvAmount");

		verifyFailure(transferMoney.isDynamicSuggestedMoneyDisplayed(driver, "com.VCB:id/tvAmount"));
	}

	@AfterClass(alwaysRun = true)

	public void afterClass() {
		closeApp();
		service.stop();
	}

}
