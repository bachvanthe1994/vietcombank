package vietcombank_test_data;

import java.util.Arrays;
import java.util.List;

public class TransferMoneyInVCB_Data {

	public static class InputDataInVCB {
		public static final String[] OPTION_TRANSFER = { "Chuyển tiền ngay", "Chuyển tiền ngay", "Chuyển tiền ngày tương lai" };
		public static final String[] PAYMENT_OPTIONS = { "Mật khẩu đăng nhập", "SMS OTP" };
		public static final String ACCOUNT1 = "0011000000847";
		public static final String ACCOUNT2 = "0011000000779";
		public static final String ACCOUNT3 = "0010000000322";
		public static final String DIFFERENT_OWNER_ACCOUNT = "0011000000845";
		public static final String DIFFERENT_OWNER_NAME = "PHAN THI THU THUY";
		public static final String USD_ACCOUNT = "0011370000646";
		public static final String USD_ACCOUNT_2 = "0011370080228";
		public static final String EUR_ACCOUNT = "0011140000647";
		public static final String EUR_ACCOUN_2 = "0011140313982";
		public static final String RECEIVER_NAME = "NGO TRI NAM";
		public static final String[] COST = { "Người chuyển trả", "Người nhận trả" };
		public static final String AMOUNT_OF_EUR_OR_USD_TRANSFER = "2";
		public static final List<String> SUGGESTED_EUR_MONEY = Arrays.asList("20 EUR ~ 540,120 VND", "200 EUR ~ 5,401,200 VND");
		public static final String VND_MONEY = "20000";
		public static final List<String> SUGGESTED_VND_MONEY = Arrays.asList("200,000 VND", "2,000,000 VND", "20,000,000 VND");

		public static final String NOTE = "Test";
		public static final String TRANSFER_OTP_FEE_TO_OTHER_ACCOUNT_OWNER = "2,200";
		public static final String TRANSFER_PASSWORD_FEE_TO_OTHER_ACCOUNT_OWNER = "2,200";
		public static final String PAYMENT_BY_PASSWORD_FEE = "3,300";
		public static final String PAYMENT_BY_OTP_FEE = "5,500";
		public static final String EUR_PAYMENT_BY_PASSWORD_FEE = "0.12";
		public static final String EUR_PAYMENT_BY_OTP_FEE = "0.2";
		public static final String USD_PAYMENT_BY_PASSWORD_FEE = "0.11";
		public static final String USD_PAYMENT_BY_OTP_FEE = "0.18";
		public static final String TRANSFER_TYPE = "Chuyển tiền trong Vietcombank";
		public static final String USD_EXCHANGE_RATE = "1 USD ~ 30,000 VND";
		public static final String EUR_EXCHANGE_RATE = "1 EUR ~ 27,006 VND";

	}

	public static class Output {
		public static final String SUCESSFULL_CREATED_ORDER = "LẬP LỆNH THÀNH CÔNG";
		public static final String TRANSFER_SUCESS_MESSAGE = "CHUYỂN KHOẢN THÀNH CÔNG";
		public static final String CONFIRMATION_MESSAGE = "Quý khách vui lòng kiểm tra thông tin giao dịch đã khởi tạo";
		public static final String ERRROR_MESSAGE_FOR_ACCOUNT_LESS_THAN_13_CHARACTER = "Tài khoản hưởng không hợp lệ. Quý khách vui lòng kiểm tra lại.";
		public static final String INVALID_RECEIVE_ACCOUNT = "Tài khoản nhận không đúng hoặc không hợp lệ, Quý khách vui lòng kiểm tra lại";
		public static final String ERRROR_MESSAGE_FOR_ACCOUNT_LESS_THAN_10_CHARACTER = "Tài khoản nhận không đủ 10 ký tự. Quý khách vui lòng kiểm tra lại";
		public static final String TRANSACTION_LIMIT_TEXT = "Tài khoản nhận không đủ 10 ký tự. Quý khách vui lòng kiểm tra lại";
		public static final String EMPTY_RECEIVE_ACCOUNT_MESSAGE = "Quý khách vui lòng nhập tài khoản nhận";
		public static final String EMPTY_MONEY_MESSAGE = "Quý khách vui lòng nhập số tiền chuyển khoản";
		public static final String EMPTY_TRANSFER_NOTE_MESSAGE = "Quý khách vui lòng nhập nội dung giao dịch";
		public static final String DUPLICATED_ACCOUNT_MESSAGE = "Số tài khoản chuyển đến và số tài khoản chuyển đi không được giống nhau, Quý khách vui lòng kiểm tra lại";
		public static final String SAME_ACCOUNT_OWNER_MESSAGE = "Tài khoản nguồn không hợp lệ do là tài khoản đồng chủ sở hữu.";
		public static final String INEXISTED_ACCOUNT_OWNER_MESSAGE = "Tài khoản nhận không chính xác. Quý khách vui lòng kiểm tra lại";
		public static final String NOT_ENOUGH_MONEY = "Giao dịch không thành công do tài khoản của Quý khách không đủ số dư. Vui lòng kiểm tra lại.";
		public static final String ERROR_MESSAGE_WITH_MIN_LIMIT_TRANSFER_MONEY = "Chuyển tiền không thành công. Số tiền giao dịch nhỏ hơn hạn mức 100 VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		public static final String ERROR_MESSAGE_WITH_MAX_LIMIT_TRANSFER_MONEY = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 10,000,000 VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		public static final String ERROR_MESSAGE_FOR_EMPTY_OTP = "Quý khách vui lòng nhập mã OTP";
		public static final String ERROR_MESSAGE_FOR_EMPTY_PASSWORD = "Quý khách vui lòng nhập mật khẩu đăng nhập ứng dụng";
		public static final String ERROR_MESSAGE_FOR_OTP_LESS_THAN_6 = "OTP phải đủ 6 ký tự, Quý khách vui lòng kiểm tra lại";
		public static final String ERROR_MESSAGE_FOR_WRONG_OTP = "OTP không chính xác, Quý khách vui lòng kiểm tra lại.";
		public static final String ERROR_MESSAGE_FOR_WRONG_PASSWORD = "Mật khẩu không chính xác. Quý khách lưu ý, dịch vụ VCB-Mobile B@nking sẽ bị tạm khóa và dịch vụ VCBPAY sẽ bị hủy nếu Quý khách nhập sai mật khẩu quá 5 lần.";
	}

	public static class InputDataInFutureForOTP {
		public static final String OTP_FEE = "2,200";
		public static final String TRANSFER_AMOUNT = "5000";
		public static final String AMOUNT_OF_EUR_OR_USD_TRANSFER = "0.2";
		public static final String USD_SMS_OTP_FEE = "0.07";
		public static final String EUR_SMS_OTP_FEE = "0.08";

	}

	public static class InputDataInFutureForPassword {
		public static final String PASSWORD_FEE = "1,000";
		public static final String TRANSFER_AMOUNT = "20000";
		public static final String AMOUNT_OF_EUR_OR_USD_TRANSFER = "1";
		public static final String USD_PASSWORD_FEE = "0.03";
		public static final String EUR_PASSWORD_FEE = "0.04";

	}

	public static class InvalidInputData {
		public static final String SAME_OWNER_ACCOUNT_1 = "0011000000659";
		public static final String SAME_OWNER_ACCOUNT_2 = "0011000000793";
		public static final String INVALID_ACCOUNT_15_CHARACTERS = "445555555555555";
		public static final String INVALID_ACCOUNT_LESS_THAN_10_CHARACTERS = "001100000";
		public static final String INVALID_ACCOUNT_LESS_THAN_13_CHARACTERS = "00110000000";
		public static final String TEXT_AND_SPECIAL_CHARACTERS = "Abc!@#$%%";
		public static final String VIETNAMESE_LANGUAGE = "Chuyển khoản nội bộ";
		public static final String TEXT_WITH_MORE_THAN_140_CHARAC = "Chuyen khoan Chuyen khoan Chuyen khoan Chuyen khoan Chuyen khoan Chuyen khoan Chuyen khoan Chuyen khoan Chuyen khoan Chuyen khoan Chuyen khoan Chuyen khoan ";
		public static final String CHANGED_VIETNAMESE_LANGUAGE = "Chuyen khoan noi bo";
		public static final String TEXT_NUMBER_AND_SPECIAL_CHARACTERS = "Abc!@ _ - \\ . , & * # ! $ + : ; ? / | % ( ) =1234";
		public static final String INVALID_MONEY = "12345678901";
		public static final String INVALID_DECIMAL_MONEY = "1234567890.1234";
		public static final String INVALID_MIN_TRANSFER_AMOUNT = "1";
		public static final String INVALID_MAX_TRANSFER_AMOUNT = "9999999999";
		public static final String OTP_WITH_LESS_THAN_6_CHARACTERS = "12345";
		public static final String WRONG_OTP = "111111";
		public static final String MORE_THAN_21_CHARACTERS = "123456789012345678901234";

	}
}
