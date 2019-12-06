package vietcombank_test_data;

public class TransferMoneyInVCB_Data {
	
	public static class InputData {
		public static final String[] OPTION_TRANSFER = { "Chuyển tiền ngay","Chuyển tiền ngay","Chuyển tiền ngày tương lai"};
		public static final String[] PAYMENT_OPTIONS = { "Mật khẩu đăng nhập","SMS OTP"};
		public static final String ACCOUNT1 = "0011000000847";
		public static final String ACCOUNT2 = "0011000000779";
		public static final String RECEIVER_NAME = "NGO TRI NAM";
		public static final String[] COST = { "Người chuyển trả","Người nhận trả"};
		public static final String MONEY = "20000";
		public static final String CONVERT_MONEY = "20,000";
		public static final String NOTE = "Test";
		public static final String 	PAYMENT_BY_PASSWORD_FEE = "3,300";
		public static final String 	PAYMENT_BY_OTP_FEE = "5,500";
		public static final String 	TRANSFER_TYPE = "Chuyển tiền trong Vietcombank";
		
	}
	public static class Output {
		public static final String TRANSFER_SUCESS_MESSAGE = "CHUYỂN KHOẢN THÀNH CÔNG";
	}
}
