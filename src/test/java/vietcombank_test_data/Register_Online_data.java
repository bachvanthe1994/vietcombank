package vietcombank_test_data;

public class Register_Online_data {

	public static class Valid_Account {
		public static final String ACC_NAME = "PHAN THI HIEP";
		public static final String TYPE_IDENTIFICATION[] = { "So CMND", "Ho Chieu", "The CCCD" };
		public static final String LOCATION[] = { "THANH PHO HA NOI ", "Cuc QLHC ve TTXH", "THANH PHO HO CHI MINH" };
		public static final String NO_IDENTIFICATION = "0339783058";
		public static final String TYPE_RETURN_MONEY[] = { "Rút lãi", "Rút gốc" };
		public static final String EMAIL = "hieppt@vnpay.vn";
		public static final String ADDRESS = "123 Truc Bach Ha Noi";
		public static final String CARD_RANK[] = { "Chuẩn", "Vàng", "Đặc biệt" };
		public static final String FEE_PAYMENT[] = { "Trích nợ tự động từ TK", "Nộp tiền mặt" };
		public static final String TYPE_AMOUNT[] = { "VND", "EUR", "USD" };
		public static final String ACCOUNT_TAKE[] = { Account_Data.Valid_Account.EUR_ACCOUNT, Account_Data.Valid_Account.ACCOUNT2 };
		public static final String AMOUNT = "100000";
		public static final String AMOUNT_OPEN_CARD = "200000";
		public static final String AMOUNT_USD_EUR = "10";
		public static final String NOTE = "test";
		public static final String CUSTOMER_NAME_ACCEPT = "NGUYEN VAN A";
		public static final String BANK_NAME = "NHTMCP Cong thuong VN";
		public static final String LIST_SEND_MONEY[] = { "Tài khoản tiền gửi có kỳ hạn", "Thẻ tiết kiệm không kỳ hạn", "Thẻ tiết kiệm có kỳ hạn" };
		public static final String LIST_PERIOD[] = { "14 ngày", "1 tháng", "3 tháng", "6 tháng", "9 tháng", "12 tháng", "24 tháng" };
		public static final String INTEREST_RECEPT_TYPE[] = { "Nhập gốc", "Tiền mặt", "Chuyển khoản" };
	}

	public class Message {
		public static final String MESSAGE_SUCCESS = "Trong vòng 10 ngày kể từ ngày đăng ký, Quý khách vui lòng ra Quầy của Vietcombank để chúng tôi cung cấp dịch vụ.\n\n" + "Mọi thông tin chi tiết, Quý khách vui lòng liên hệ Hotline 24/7: 1900545413.\n\n" + "Cảm ơn Quý khách đã sử dụng dịch vụ của Vietcombank";

	}
}
