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

public class Validation_QuickMoneyTransfer247_5 extends Base {
	AppiumDriver<MobileElement> driver;
	private LogInPageObject login;
	private TransferMoneyObject transferMoney;
	List<String> listExpect;
	List<String> listActual;
	private String Note;
	private String costTranferString;
	private long fee = 0;

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
	public void TC_64_KiemTraLabelLinkHanMuc() {
		log.info("TC_64_Check text han muc");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Hạn mức"));

		log.info("TC_64_Click link han muc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Hạn mức");

		log.info("TC_64_Check mot phan noi dung han muc");
		transferMoney.isDynamicTextInfoDisplayed(driver, "Hạn mức giao dịch áp dụng cho các chức năng chuyển tiền:");

		log.info("TC_64_Quay lai man hinh chuyen tien nhanh 24/7");
		transferMoney.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");
	}

	@Test
	// Lỗi app, hiển thị text null chuyển tiền
	public void TC_65_KiemTraHienThiTextNoiDung() {
		log.info("TC_65_Kiem tra label noi dung");
		verifyEquals(transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3"), "Nội dung");
	}

	@Test
	public void TC_66_KiemTraGiaTriChoPhepNoiDung() {
		log.info("TC_66_Nhap 2 ky tu cho phep");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_2_CHAR, "Thông tin giao dịch", "3");

		log.info("TC_66_text mac dinh 'noi dung' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Nội dung"));

		log.info("TC_66_Lay noi dung tren man hinh");
		Note = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");

		log.info("TC_66_Verify text noi dung");
		verifyEquals(Note, TransferMoneyQuick_Data.TransferQuick.NOTE_2_CHAR);

		log.info("TC_66_Nhap 140 ky tu cho phep");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_140_CHAR, "Thông tin giao dịch", "3");

		log.info("TC_66_Lay noi dung tren man hinh");
		Note = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");

		log.info("TC_66_Verify text noi dung");
		verifyEquals(Note, TransferMoneyQuick_Data.TransferQuick.NOTE_140_CHAR);

		log.info("TC_66_Nhap tieng viet co dau");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_VIETNAM_KEY_INVALID, "Thông tin giao dịch", "3");

		log.info("TC_66_Lay noi dung tren man hinh");
		Note = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");

		log.info("TC_66_Verify text noi dung");
		verifyEquals(Note, transferMoney.removeUnicode(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_VIETNAM_KEY_INVALID));
	}

	@Test
	public void TC_67_KiemTraNhap1KyTuVaoNoiDung() {
		log.info("TC_67_Nhap 1 ky tu cho phep");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_1_CHAR_VIETNAM, "Thông tin giao dịch", "3");

		log.info("TC_67_text mac dinh 'noi dung' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Nội dung"));

		log.info("TC_67_Lay noi dung tren man hinh");
		Note = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");

		log.info("TC_67_Verify text khong cho phep nhap chu tieng viet co dau");
		verifyEquals(Note, transferMoney.removeUnicode(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_1_CHAR_VIETNAM));
	}

	@Test
	public void TC_68_KiemTraNhap140KyTuVaoNoiDung() {
		log.info("TC_68_Nhap 1 ky tu cho phep");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_140_CHAR_VIETNAM, "Thông tin giao dịch", "3");

		log.info("TC_68_text mac dinh 'noi dung' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Nội dung"));

		log.info("TC_68_Lay noi dung tren man hinh");
		Note = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");

		log.info("TC_68_Verify text khong cho phep nhap chu tieng viet co dau");
		verifyEquals(Note, transferMoney.removeUnicode(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_140_CHAR_VIETNAM));
	}

	@Test
	public void TC_69_KiemTraNhap141KyTuVaoNoiDung() {
		log.info("TC_69_Nhap 1 ky tu cho phep");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_141_CHAR_INVALID, "Thông tin giao dịch", "3");

		log.info("TC_69_text mac dinh 'noi dung' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Nội dung"));

		log.info("TC_69_Lay do dai ky tu text nhap vao");
		int textNumber = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3").length();

		log.info("TC_69_Step_04: Kiem tra so tai khoan huong bi cat bot con 140 ky tu");
		verifyEquals(textNumber, 140);
	}

	@Test
	public void TC_70_KiemTraNhapKyTuDacBietVaSo() {
		log.info("TC_70_Nhap ky tu dac biet");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_SPECAL_CHAR, "Thông tin giao dịch", "3");

		log.info("TC_70_text mac dinh 'noi dung' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Nội dung"));

		log.info("TC_70_Lay noi dung tren man hinh");
		Note = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");

		log.info("TC_70_Verify text noi dung");
		verifyEquals(Note, TransferMoneyQuick_Data.TransferQuick.NOTE_SPECAL_CHAR);

		log.info("TC_70_Nhap ky tu so");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE_NUMBER_CHAR, "Thông tin giao dịch", "3");

		log.info("TC_70_text mac dinh 'noi dung' bi xoa");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextUndisplayed(driver, "Nội dung"));

		log.info("TC_70_Lay noi dung tren man hinh");
		Note = transferMoney.getDynamicTextInInputBoxByHeader(driver, "Thông tin giao dịch", "3");

		log.info("TC_70_Verify text noi dung");
		verifyEquals(Note, TransferMoneyQuick_Data.TransferQuick.NOTE_NUMBER_CHAR);
	}

	@Test
	public void TC_71_BoTrongTruongNoiDung() {
		log.info("TC_71_Clear text truong nhap so tien");
		transferMoney.clickToDynamicImageViewByID(driver, "com.VCB:id/ivClearInput2");

		log.info("TC_71_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_USD_EUR, "Thông tin giao dịch", "1");

		log.info("TC_71_Step_Nhap so tai khoan chuyen");
		transferMoney.inputToDynamicInputBox(driver, Account_Data.Valid_Account.ACCOUNT_TO, "Nhập/ chọn tài khoản thụ hưởng");

		log.info("TC_71_Step_Select ngan hang");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, "Ngân hàng hưởng");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.BANK[0]);

		log.info("TC_71_Step_Nhap so tien chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY, "Thông tin giao dịch", "1");

		log.info("TC_71_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_71_Step_verify message khi tai khoan nguoi nhan trong ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.NOTE_BLANK));

		log.info("TC_71_Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");

		log.info("TC_71_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch", "3");
	}

	@Test
	public void TC_72_CheckDefaultComboboxPhiGiaoDich() {
		log.info("TC_72_Kiem tra gia tri mac dinh hien thi la so tien phi nguoi chuyen tra");
		verifyEquals(transferMoney.getTextInDynamicFee(driver, "Thông tin giao dịch", "2"), "Phí giao dịch người chuyển trả");
	}

	@Test
	public void TC_73_ChonPhiGiaoDichKhacDefaultVaKiemTraGiaTriSauChon() {
		log.info("TC_73_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_73_Kiem tra gia tri mac dinh hien thi la so tien phi nguoi nhan tra");
		verifyEquals(transferMoney.getTextInDynamicFee(driver, "Thông tin giao dịch", "2"), "Phí giao dịch người nhận trả");

		log.info("TC_73_Verify tick chon nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[1]);
		verifyTrue(transferMoney.isDynamicImageSelect(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]));
	}

	@Test
	public void TC_74_ChonPhiGiaoDichNguoiChuyenVaKiemTraPhiChuyen() {
		log.info("TC_74_Step_Kiem tra gia tri trong combobox");
		listActual = transferMoney.getListOfSuggestedMoneyOrListText(driver, "com.VCB:id/tvContent");

		log.info("TC_74_danh sach phi giao dich actual");
		listExpect = Arrays.asList(TransferMoneyQuick_Data.TransferQuick.COST_SUB);

		log.info("TC_74_Verify gia tri tim kiem");
		verifyTrue(transferMoney.checkListContain(listActual, listExpect));

		log.info("TC_74_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]);

		log.info("TC_74_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_74_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_01_Step_Chon phuong thuc xac thuc");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[0]);
		fee = convertAvailableBalanceCurrentcyOrFeeToLong(transferMoney.getDynamicTextInTransactionDetail(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]));
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.ACCURACY[1]);

		log.info("TC_74_Step_Verify so tien phi");
		verifyEquals(costTranferString, fee + "");

		log.info("TC_74_Step_Verify phi giao dich nguoi chuyen tra");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[0]));
	}

	@Test
	public void TC_75_ChonPhiGiaoDichNguoiNhanVaKiemTraPhiChuyen() {
		log.info("TC_75_Quay lai man hinh chuyen tien nhanh 24/7");
		transferMoney.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_75_Step_Chon phi giao dich la nguoi nhan tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST[0]);

		log.info("TC_75_Step_Chon phi giao dich la nguoi chuyen tra");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]);

		log.info("TC_75_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_75_Step_Lay gia tri so tien phí chuyen");
		costTranferString = transferMoney.getDynamicTextByLabel(driver, "Số tiền phí").replaceAll("\\D+", "");

		log.info("TC_75_Step_Verify so tien phi");
		verifyEquals(costTranferString, fee + "");

		log.info("TC_75_Step_Verify phi giao dich nguoi chuyen tra");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.TransferQuick.COST_SUB[1]));
	}

	@Test
	public void TC_76_ChonTaiKhoanNguonKhongDuSoDuVND() {
		log.info("TC_76_Quay lai man hinh chuyen tien nhanh 24/7");
		transferMoney.clickToDynamicBottomMenuOrIcon(driver, "com.VCB:id/ivTitleLeft");

		log.info("TC_76_Lay so du tai khoan nguon");
		Long surplus = Long.parseLong(transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", ""));
		Long moneyCharity = surplus + 1;

		log.info("TC_07_2_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, String.valueOf(moneyCharity), "Thông tin giao dịch", "1");

		log.info("TC_76_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_76_Step_verify message khi so tien chuyen vuot qua so du trong tai khoan ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.AMOUNT_NOT_ENOUGH));

		log.info("TC_76_Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_77_ChonTaiKhoanNguonKhongDuSoDuEUR() {
		log.info("TC_77_Chon tai khoan nguon VND");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[2]);

		log.info("TC_76_Lay so du tai khoan nguon");
		Long surplus = Long.parseLong(transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", ""));
		Long moneyCharity = surplus + 1;

		log.info("TC_07_2_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, String.valueOf(moneyCharity), "Thông tin giao dịch", "1");

		log.info("TC_77_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_77_Step_verify message khi so tien chuyen vuot qua so du trong tai khoan ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.AMOUNT_NOT_ENOUGH));

		log.info("TC_77_Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_78_ChonTaiKhoanNguonKhongDuSoDuUSD() {
		log.info("TC_78_Chon tai khoan nguon VND");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[1]);

		log.info("TC_76_Lay so du tai khoan nguon");
		Long surplus = Long.parseLong(transferMoney.getDynamicTextInTransactionDetail(driver, "Số dư khả dụng").replaceAll("\\D+", ""));
		Long moneyCharity = surplus + 1;

		log.info("TC_07_2_Nhap so tien");
		transferMoney.inputToDynamicInputBoxByHeader(driver, String.valueOf(moneyCharity), "Thông tin giao dịch", "1");

		log.info("TC_78_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_78_Step_verify message khi so tien chuyen vuot qua so du trong tai khoan ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.AMOUNT_NOT_ENOUGH));

		log.info("TC_78_Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_79_ChonTaiKhoanDongChuSoHuu() {
		log.info("TC_79_Chon tai khoan nguon VND");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[4]);

		log.info("TC_79_Input so tien chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_VND, "Thông tin giao dịch", "1");

		log.info("TC_79_Step_Nhap so tai khoan chuyen dong chu so huu voi tai khoan nguon");
		transferMoney.inputToDynamicInputBoxByHeader(driver, Account_Data.Invalid_Account.SAME_OWNER_ACCOUNT_2, "Thông tin người hưởng", "1");

		log.info("TC_79_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_79_Step_verify message khi tai khoan dong chu so huu ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.SAME_ACCOUNT_NAME));

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_80_ChonTaiKhoanHuongKhongHopLe() {
		log.info("TC_80_Chon tai khoan nguon VND");
		transferMoney.clickToDynamicDropDown(driver, "Tài khoản nguồn");
		transferMoney.clickToDynamicButtonLinkOrLinkText(driver, Account_Data.Valid_Account.LIST_ACCOUNT_FROM[0]);

		log.info("TC_80_Input so tien chuyen");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.MONEY_FOUR_NUMBER_VND, "Thông tin giao dịch", "1");

		log.info("TC_80_Step_Nhap so tai khoan chuyen khong hop le");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.ACCOUNT_TO_INVALID, "Thông tin người hưởng", "1");

		log.info("TC_80_Step_Nhap noi dung");
		transferMoney.inputToDynamicInputBoxByHeader(driver, TransferMoneyQuick_Data.TransferQuick.NOTE, "Thông tin giao dịch", "3");

		log.info("TC_80_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_80_Step_verify message ngan hang huong khong hop le ");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, TransferMoneyQuick_Data.MessageTransferMoney.ACCOUNT_TO_INVALID_MESSAGE));

		log.info("Close popup");
		transferMoney.clickToDynamicButton(driver, "Đóng");
	}

	@Test
	public void TC_81_NhapHopLeThongTinChuyenTienNhanh() {
		log.info("TC_81_Step_Nhap so tai khoan chuyen dong chu so huu voi tai khoan nguon");
		transferMoney.inputToDynamicInputBoxByHeader(driver, Account_Data.Valid_Account.ACCOUNT_TO, "Thông tin người hưởng", "1");

		log.info("TC_81_Step_Tiep tuc");
		transferMoney.clickToDynamicButton(driver, "Tiếp tục");

		log.info("TC_81_verify man xac nhan thong tin khi nhap so tien hop le");
		verifyTrue(transferMoney.isDynamicMessageAndLabelTextDisplayed(driver, "Xác nhận thông tin"));
	}

}
