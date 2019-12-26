package vietcombank_test_data;

public class TransferMoneyInVCB_Data {

	public static class InputDataInVCB {
		public static final String[] OPTION_TRANSFER = { "Chuyển tiền ngay", "Chuyển tiền ngay", "Chuyển tiền ngày tương lai" };
		public static final String[] PAYMENT_OPTIONS = { "Mật khẩu đăng nhập", "SMS OTP" };
		public static final String ACCOUNT1 = "0011000000847";
		public static final String ACCOUNT2 = "0011000000779";
		public static final String DIFFERENT_OWNER_ACCOUNT = "0011000000845";
		public static final String DIFFERENT_OWNER_NAME = "PHAN THI THU THUY";
		public static final String USD_ACCOUNT = "0011370000646";
		public static final String EUR_ACCOUNT = "0011140000647";
		public static final String RECEIVER_NAME = "NGO TRI NAM";
		public static final String[] COST = { "Người chuyển trả", "Người nhận trả" };
		public static final String AMOUNT_OF_EUR_OR_USD_TRANSFER = "2";
		public static final String MONEY = "20000";
		public static final String NOTE = "Test";
		public static final String TRANSFER_OTP_FEE_TO_OTHER_ACCOUNT_OWNER = "2,200";
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
		public static final String ERRROR_MESSAGE_FOR_ACCOUNT_LESS_THAN_10_CHARACTER = "Tài khoản nhận không đủ 10 ký tự. Quý khách vui lòng kiểm tra lại";
		public static final String TRANSACTION_LIMIT_TEXT = "Tài khoản nhận không đủ 10 ký tự. Quý khách vui lòng kiểm tra lại";
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
		public static final String INVALID_ACCOUNT_15_CHARACTERS = "445555555555555";
		public static final String INVALID_ACCOUNT_LESS_THAN_10_CHARACTERS = "001100000";
		public static final String INVALID_ACCOUNT_LESS_THAN_13_CHARACTERS = "00110000000";
		public static final String TEXT_AND_SPECIAL_CHARACTERS = "Abc!@#$%%";
		public static final String INVALID_MONEY = "12345678901";
		public static final String INVALID_DECIMAL_MONEY = "1234567890.1234";
	}
}
