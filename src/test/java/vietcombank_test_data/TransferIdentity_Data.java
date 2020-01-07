package vietcombank_test_data;

import java.util.Arrays;
import java.util.List;

public class TransferIdentity_Data {
    public class textCheckElement {
	public static final String PAGE_TRANSFER = "Chuyển tiền cho người nhận tại quầy";
	public static final String PAGE_CONFIRM = "Xác nhận thông tin";
	public static final String CONFIRM_PASSWORD = "com.VCB:id/pin";
	public static final String CONFIRM_TRANSFER_SUCCESS = "CHUYỂN KHOẢN THÀNH CÔNG";
	public static final String BENEFICIARY_NAME = "Tên người hưởng";
	public static final String DESTINATION_ACCOUNT = "Tài khoản đích";
	public static final String CONNTENT = "Nội dung";
	public static final String TRANSECTION_NUMBER = "Mã giao dịch";
	public static final String AMOUNT = "11000";
    }

    public static class textDataInputForm {
	public static final String USER_NAME = "Hoangkm";
	public static final String IDENTITY_NUMBER = "123456789";
	public static final String PASSPORT_NUMBER = "abc12345678";
	public static final String ACCOUNT_VND = "0011000000779";
	public static final String ACCOUNT_USD = "0011370000646";
	public static final String ACCOUNT_EUR = "0011140000647";
	public static final String MONEY_TRANSFER_VND = "100000";
	public static final String MONEY_TRANSFER_USD = "100";
	public static final String MONEY_TRANSFER_EUR = "100";
	public static final String CONTEN_TRANSFER = "ABC123";
	public static final String CURRENCY_USD = "USD";
	public static final String CURRENCY_VND = "VND";
	public static final String CURRENCY_EURO = "EUR";
	public static final String ISSUED = "Thành phố Hà Nội ";
	public static final String ISSUED_SPACE = "Thành phố Hà Nội";
	public static final String SPECIAL_CHARACTERS = " {@ _ - \\ . , & * # ! $ + : ; ? / | % ( ) = }";
	public static final String MAX_LENGTH_100 = "TC validation chuyen tien ngay TC validation chuyen tien ngayTC validation chuyen tien ngayTC valida";
	public static final String MAX_LENGTH_101 = "TC validation chuyen tien ngay TC validation chuyen tien ngayTC validation chuyen tien ngayTC validat";
	public static final String MAX_LENGTH_20_NUM = "12345678901234567890";
	public static final String MAX_LENGTH_21_NUM = "123456789012345678901";
	public static final String MAX_LENGTH_8_NUM = "12345678";
	public static final String MAX_LENGTH_9_NUM = "123456789";
	public static final String MAX_LENGTH_15_NUM = "123456789012345";
	public static final String MAX_LENGTH_16_NUM = "1234567890123456";
	public static final String CONFIRM_IDENTITY = "Số CMND không hợp lệ. Quý khách vui lòng kiểm tra lại";
	public static final String CONTENT = "Nội dung";
	public static final String CONTENT_UNIKEY = "chuyen tien cho nguoi nhan";
	public static final String MONEY_0 = "0100000";
	public static final String MIN_TRANSFER = "49999";
	public static final String MAX_TRANSFER = "1000000000";
	public static final String CONFIRM_MIN_TRANSFER = "Chuyển tiền không thành công. Số tiền giao dịch nhỏ hơn hạn mức 100,000 VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
	public static final String CONFIRM_MAX_TRANSFER = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000,000 VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
	public static final String INVALID_MONEY = "12345678901";
	public static final String MAX_LENGTH_13_MONEY = "11234567890.10";
	public static final List<String> SUGGESTED_VND_MONEY = Arrays.asList("1,000,000 VND", "10,000,000 VND", "100,000,000 VND");
	public static final List<String> SUGGESTED_EUR_MONEY = Arrays.asList("1,000 EUR ~ 27,006,000 VND", "10,000 EUR ~ 270,060,000 VND");
	public static final String MAX_LENGTH_140 = "In Vietnam, bonuses are agreed between employers and workers, but the government encourages rewards based on performance Tet, the Lunar New1";
	public static final String MAX_LENGTH_141 = "In Vietnam, bonuses are agreed between employers and workers, but the government encourages rewards based on performance Tet, the Lunar New Y";
    }

    public static class confirmMessage {
	public static final String MESSSAGE_USER = "Quý khách vui lòng nhập Tên người hưởng";
	public static final String MESSSAGE_IDENTITY = "Quý khách vui lòng chọn giấy tờ tùy thân";
	public static final String MESSSAGE_IDENTITY_NUMBER = "Quý khách vui lòng nhập số của giấy tờ tùy thân";
	public static final String MESSSAGE_DATE = "Quý khách vui lòng chọn ngày cấp";
	public static final String MESSSAGE_ISSUED = "Quý khách vui lòng nhập hoặc chọn nơi cấp";
	public static final String MESSSAGE_MONEY = "Quý khách vui lòng nhập số tiền";
	public static final String MESSSAGE_CONTENT = "Quý khách vui lòng nhập nội dung";
	public static final String MESSSAGE_ACCOUNT_CO_OWNER = "Tài khoản nguồn không hợp lệ do là tài khoản đồng chủ sở hữu.";
	public static final String MESSSAGE_ACCOUNT_MONEY = "Giao dịch không thành công do tài khoản của Quý khách không đủ số dư. Vui lòng kiểm tra lại.";
    }
}