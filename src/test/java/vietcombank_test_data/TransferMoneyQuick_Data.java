package vietcombank_test_data;

public class TransferMoneyQuick_Data {

	public static class TransferQuick {
		public static final String[] OPTION_TRANSFER = { "Chuyển tiền nhanh qua số tài khoản","Chuyển tiền nhanh qua số thẻ" };
		public static final String ACCOUNT_TO = "0129837294";
		public static final String ACCOUNT_USD_FROM = "0011370000886";
		public static final String ACCOUNT_EUR_FROM = "0011140000553";
		public static final String LIST_ACCOUNT_FROM[] = { "0011000000779", "0011370000646","0011140000647", "0010000000323", "0011000000659", "0011000000847", "0011000001433", "0010000000319", "0010000000320", "0011000000635" };
		public static final String[] COST = { "Phí giao dịch người chuyển trả", "Phí giao dịch người nhận trả" };
		public static final String[] COST_SUB = { "Người chuyển trả", "Người nhận trả" };
		public static final String[] ACCURACY = { "Mật khẩu đăng nhập", "SMS OTP" };
		public static final String [] BANK = {"Ngân hàng DAB","Ngân hàng TM TNHH MTV Dầu Khí Toàn Cầu","Ngân hàng TMCP Kỹ thương Việt Nam"};
		public static final String BANK_INVALID = "Ngân hàng Test";
		public static final String MONEY = "100000";
		public static final String MONEY_USD = "10";
		public static final String MONEY_EUR = "40";
		public static final String NOTE = "Test";
		public static final String RECEIVER_NAME = "NGUYEN VAN NAPAS";
		public static final String SUCCESS_TRANSFER_MONEY = "CHUYỂN KHOẢN THÀNH CÔNG";

		public static final String COST_AMOUNT = "0";
		public static final String COST_AMOUNT_USD = "000";
		public static final String PASSWORD = "Nhập mật khẩu";
		public static final String MESSAGE_SUCCESS = "CHUYỂN KHOẢN THÀNH CÔNG";

		public static final String CARD_FROM = "0011000000863";
		public static final String CARD_TO = "0129837294";
		public static final String SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT = "LẬP LỆNH THÀNH CÔNG";
		public static final String PAYMENT_BY_PASSWORD_FEE = "3,300";
		public static final String PAYMENT_BY_OTP_FEE = "5,500";
		public static final String EUR_PAYMENT_BY_PASSWORD_FEE = "0.12";
		public static final String EUR_PAYMENT_BY_OTP_FEE = "0.2";
		public static final String USD_PAYMENT_BY_PASSWORD_FEE = "0.11";
		public static final String USD_PAYMENT_BY_OTP_FEE = "0.18";
		
		public static final String INFO_FROM_LABEL = "Thông tin người chuyển";
		public static final String INFO_TO_LABEL = "Thông tin người hưởng";
		public static final String INVALID_ACC_ACCEPT_OVER_MAX = "012983729401298372940129837294";
		public static final String INVALID_ACC_ACCEPT = "01298372940129";
		public static final String INVALID_ACC_BLANK = "";
		public static final String INVALID_ACC_ACCEPT_BLANK = "";
		public static final String INVALID_ACC_VIETNAM_KEY = "Nhận123456";
		public static final String NAME_INVALID = "testabc";
		
		public static final String MONEY_NINE_NUMBER_VND = "150450000";
		public static final String MONEY_TEN_NUMBER_VND = "1504500000";
		public static final String MONEY_ELEVEN_NUMBER_VND = "12345678912";
		public static final String MONEY_TEXT_INVALID_VND = "abc@12345";
		public static final String MONEY_FOUR_NUMBER_VND = "5000";
		
		public static final String MONEY_NINE_NUMBER_USD_EUR = "150450000.09";
		public static final String MONEY_NINE_NUMBER_INVALID_USD_EUR = "150450000.091";
		public static final String MONEY_TEN_NUMBER_USD_EUR = "1504500000.56";
		public static final String MONEY_ELEVEN_NUMBER_USD_EUR = "12345678912";
		public static final String MONEY_TEXT_INVALID_USD_EUR = "abc@12345";
		public static final String MONEY_FOUR_NUMBER_USD_EUR = "5000.20";

	}

	public static class MessageTransferMoney {
		public static final String ACCOUNT_NOT_EXIST = "Giao dịch không thành công do tài khoản hưởng không hợp lệ. Quý khách vui lòng kiểm tra lại. Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
		public static final String ACCOUNT_BLANK = "Quý khách vui lòng nhập tài khoản nhận";
		public static final String BANK_BLANK = "Quý khách vui lòng chọn ngân hàng hưởng";
		public static final String AMOUNT_BLANK = "Quý khách vui lòng nhập số tiền chuyển khoản";
	}


}
