package vietcombank_test_data;

public class ValidationTransferMoneyQuick_Data {

	public static class TransferQuick {
		public static final String[] OPTION_TRANSFER = { "Chuyển tiền nhanh qua số tài khoản",
				"Chuyển tiền nhanh qua số thẻ" };
		public static final String ACCOUNT_FORM = "0011000000779";
		public static final String INFO_FORM_LABEL = "Thông tin người chuyển";
		public static final String INFO_TO_LABEL = "Thông tin người hưởng";
		public static final String INVALID_ACC_ACCEPT_OVER_MAX = "012983729401298372940129837294";
		public static final String INVALID_ACC_ACCEPT_BLANK = "";
		public static final String INVALID_ACC_VIETNAM_KEY = "Nhận123456";
		public static final String ACCOUNT_FORM_LIST[] = { "0011000000779", "0011370000646" };

		public static final String ACCOUNT_TO = "0129837294";
		public static final String ACCOUNT_USD_FORM = "0011370000886";
		public static final String ACCOUNT_EUR_FORM = "0011140000553";
		public static final String[] COST = { "Phí giao dịch người chuyển trả", "Phí giao dịch người nhận trả" };
		public static final String[] COST_SUB = { "Người chuyển trả", "Người nhận trả" };
		public static final String[] ACCURACY = { "Mật khẩu đăng nhập", "SMS OTP" };
		public static final String MONEY = "100000";
		public static final String MONEY_USD = "10";
		public static final String MONEY_EUR = "40";
		public static final String NOTE = "Test";
		public static final String RECEIVER_NAME = "NGUYEN VAN NAPAS";
		public static final String SUCCESS_TRANSFER_MONEY = "Chuyển khoản thành công";
		public static final String NAME_INVALID = "testabc";

	}

	public static class MessageTransferMoney {
		public static final String ACCOUNT_NOT_EXIST = "Giao dịch không thành công do tài khoản hưởng không hợp lệ. Quý khách vui lòng kiểm tra lại. Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
		public static final String ACCOUNT_BLANK = "Quý khách vui lòng nhập tài khoản nhận";
		public static final String BANK_BLANK = "Quý khách vui lòng chọn ngân hàng hưởng";
	}

}
