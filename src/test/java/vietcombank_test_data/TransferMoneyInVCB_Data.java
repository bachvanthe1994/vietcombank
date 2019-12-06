package vietcombank_test_data;

public class TransferMoneyInVCB_Data {

	public static class InputData {
		public static final String[] OPTION_TRANSFER = { "Chuyển tiền ngay", "Chuyển tiền ngay", "Chuyển tiền ngày tương lai" };
		public static final String[] PAYMENT_OPTIONS = { "Mật khẩu đăng nhập", "SMS OTP" };
		public static final String ACCOUNT1 = "0011000000847";
		public static final String ACCOUNT2 = "0011000000779";
		public static final String USD_ACCOUNT = "0011370000646";
		public static final String EUR_ACCOUNT = "0011140000647";
		public static final String RECEIVER_NAME = "NGO TRI NAM";
		public static final String[] COST = { "Người chuyển trả", "Người nhận trả" };
		public static final String AMOUNT_OF_EUR_OR_USD_TRANSFER = "2";
		public static final String CONVERTED_AMOUNT_OF_EUR_OR_USD_TRANSFER = "2.00";
		public static final String MONEY = "20000";
		public static final String CONVERT_MONEY = "20,000";
		public static final String NOTE = "Test";
		public static final String PAYMENT_BY_PASSWORD_FEE = "3,300";
		public static final String PAYMENT_BY_OTP_FEE = "5,500";
		public static final String EUR_PAYMENT_BY_PASSWORD_FEE = "0.12";
		public static final String EUR_PAYMENT_BY_OTP_FEE = "0.2";
		public static final String USD_PAYMENT_BY_PASSWORD_FEE = "0.11";
		public static final String USD_PAYMENT_BY_OTP_FEE = "0.18";
		public static final String TRANSFER_TYPE = "Chuyển tiền trong Vietcombank";

	}

	public static class Output {
		public static final String TRANSFER_SUCESS_MESSAGE = "CHUYỂN KHOẢN THÀNH CÔNG";
		public static final String CONFIRMATION_MESSAGE = "Quý khách vui lòng kiểm tra thông tin giao dịch đã khởi tạo";
	}
}
