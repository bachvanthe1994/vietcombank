package vietcombank_test_data;

public class TransferMoneyQuick_Data {

	public static class TransferQuick {
		public static final String[] OPTION_TRANSFER = { "Chuyển tiền nhanh qua số tài khoản","Chuyển tiền nhanh qua số thẻ" };
		public static final String ACCOUNT_TO_INVALID = "0129837294abc";
		public static final String[] COST = { "Phí giao dịch người chuyển trả", "Phí giao dịch người nhận trả" };
		public static final String[] COST_SUB = { "Người chuyển trả", "Người nhận trả" };
		public static final String[] ACCURACY = { "Mật khẩu đăng nhập", "SMS OTP" };

		public static final String BANK_INVALID = "Ngân hàng Test";
		public static final String MONEY = "100000";
		public static final String MONEY_USD = "10";
		public static final String MONEY_EUR = "40";
		public static final String RECEIVER_NAME = "NGUYEN VAN NAPAS TEST ONLINE IBFT 247 VN";
		public static final String SUCCESS_TRANSFER_MONEY = "CHUYỂN KHOẢN THÀNH CÔNG";

		public static final String COST_AMOUNT_USD = "0.33";
		public static final String COST_AMOUNT_EUR = "0.02";
		public static final String COST_AMOUNT_VND = "550";
		public static final String PASSWORD = "Nhập mật khẩu";

		public static final String CARD_FROM = "0011000000863";
		public static final String SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT = "LẬP LỆNH THÀNH CÔNG";
		public static final String PAYMENT_BY_PASSWORD_FEE = "3,300";
		public static final String PAYMENT_BY_OTP_FEE = "5,500";
		public static final String EUR_PAYMENT_BY_PASSWORD_FEE = "0.12";
		public static final String EUR_PAYMENT_BY_OTP_FEE = "0.2";
		public static final String USD_PAYMENT_BY_PASSWORD_FEE = "0.11";
		public static final String USD_PAYMENT_BY_OTP_FEE = "0.18";
		
		public static final String TRANSFER_MONEY_LABEL = "Chuyển tiền nhanh 24/7";
		public static final String CONFIRM_LABEL = "Xác thực giao dịch";
		public static final String CONFIRM_INFO_LABEL = "Xác nhận thông tin";
		public static final String NOTE_LABEL = "Nội dung";
		public static final String AMOUNT_FEE_LABEL = "Số tiền phí";
		public static final String INFO_FROM_LABEL = "Thông tin người chuyển";
		public static final String INFO_TO_LABEL = "Thông tin người hưởng";
		public static final String MOUNT_LABEL = "Số tiền";
		public static final String ACCOUNT_FROM_LABEL = "Tài khoản nguồn";
		public static final String ACCOUNT_TO_LABEL = "Tài khoản đích/ VND";
		
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
		public static final String MONEY_LESS_MIN_LIMIT_VND = "4500";
		public static final String MONEY_EXCEED_MAX_LIMIT_VND = "5000001";
		public static final String MONEY_EXCEED_MAX_TRANSFER_ONE_DAY_VND = "10000001";
		public static final String MONEY_EXCEED_MAX_TRANSFER_TYPE_ONE_DAY_VND = "8000001";
		public static final String MONEY_EXCEED_MAX_TRANSFER_PACKAGE_ONE_DAY_VND = "6000001";
		public static final String MONEY_ALLOW_VND = "4900000";
		public static final String [] LIST_MONEY_SHOW_VND = {"50,000 VND","500,000 VND","5,000,000 VND"};
		public static final String [] LIST_MONEY_SHOW_USD = {"50,002.00 USD ~ 1,500,060,000 VND","500,020.00 USD ~ 15,000,600,000 VND"};
		public static final String [] LIST_MONEY_SHOW_EUR = {"50,002.00 EUR ~ 1,350,354,010 VND","500,020.00 EUR ~ 13,503,540,100 VND"};


		public static final String MONEY_DOUBLE_NUMBER_USD_EUR =".09";
		public static final String MONEY_NINE_NUMBER_USD_EUR = MONEY_NINE_NUMBER_VND + MONEY_DOUBLE_NUMBER_USD_EUR; 
		public static final String MONEY_NINE_NUMBER_INVALID_USD_EUR = MONEY_NINE_NUMBER_VND +".091";
		public static final String MONEY_TEN_NUMBER_USD_EUR = MONEY_TEN_NUMBER_VND + MONEY_DOUBLE_NUMBER_USD_EUR;
		public static final String MONEY_ELEVEN_NUMBER_USD_EUR = "12345678912";
		public static final String MONEY_TEXT_INVALID_USD_EUR = "abc@12345";
		public static final String MONEY_FOUR_NUMBER_USD_EUR = "5000.20";
		
		public static final String NOTE = "Test";
		public static final String NOTE_1_CHAR_VIETNAM = "a";
		public static final String NOTE_2_CHAR = "ab";
		public static final String NOTE_140_CHAR = "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
		public static final String NOTE_140_CHAR_VIETNAM = "Giao dịch không thành công do tài khoản hưởng không hợp lệ. Quý khách vui lòng kiểm tra lại. Chi tiết xem tại http://www.vietcombank.com.vn ";
		public static final String NOTE_141_CHAR_INVALID = "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901";
		public static final String NOTE_VIETNAM_KEY_INVALID = "Xin chào";
		public static final String NOTE_SPECAL_CHAR = "@ _ - \\ . , & * # ! $ + : ; ? / | % ( ) =";
		public static final String NOTE_NUMBER_CHAR = "12345";
		public static final String EXCHANGE_EUR = "24274.03";
		public static final String EXCHANGE_USD = "23140.00";
		
	}

	public static class MessageTransferMoney {
		public static final String ACCOUNT_NOT_EXIST = "Giao dịch không thành công do tài khoản hưởng không hợp lệ. Quý khách vui lòng kiểm tra lại. Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
		public static final String ACCOUNT_BLANK = "Quý khách vui lòng nhập tài khoản nhận";
		public static final String BANK_BLANK = "Quý khách vui lòng chọn ngân hàng hưởng";
		public static final String AMOUNT_BLANK = "Quý khách vui lòng nhập số tiền chuyển khoản";
		public static final String NOTE_BLANK = "Quý khách vui lòng nhập nội dung giao dịch";
		public static final String MESSAGE_LESS_MIN_LIMIT_VND = "Chuyển tiền không thành công. Số tiền giao dịch nhỏ hơn hạn mức 5,000 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";
		public static final String MESSAGE_EXCEED_MAX_LIMIT_VND = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 5,000,000 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";
		public static final String MESSAGE_EXCEED_MAX_TRANSFER_TYPE_ONE_DAY_VND = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 8,000,000 VND/1 ngày của nhóm dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";
		public static final String MESSAGE_EXCEED_MAX_TRANSFER_PACKAGE_ONE_DAY_VND = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 9,000,000 VND/1 ngày của gói dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
		public static final String MESSAGE_EXCEED_MAX_TRANSFER_ONE_DAY_VND = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 5,000,000 VND/1 ngày, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
		public static final String AMOUNT_NOT_ENOUGH = "Giao dịch không thành công do tài khoản của Quý khách không đủ số dư. Quý khách vui lòng kiểm tra lại.";
		public static final String SAME_ACCOUNT_NAME = "Tài khoản nguồn không hợp lệ do là tài khoản đồng chủ sở hữu.";
		public static final String ACCOUNT_TO_INVALID_MESSAGE = "Giao dịch không thành công do tài khoản hưởng không hợp lệ. Quý khách vui lòng kiểm tra lại. Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
		public static final String OTP_BLANK_MESSAGE = "Quý khách vui lòng nhập mã OTP";
		public static final String OTP_NOT_ENOUGH_MESSAGE = "OTP phải đủ 6 ký tự, Quý khách vui lòng kiểm tra lại";
		public static final String OTP_NOT_EXIST_MESSAGE = "OTP không chính xác, Quý khách vui lòng kiểm tra lại.";
		public static final String OTP_OVER_3_TIMES_MESSAGE = "Nhập sai OTP quá 3 lần. Quý khách vui lòng thực hiện giao dịch khác.";
		public static final String OTP_OVER_TIMES_MESSAGE = "OTP đã hết hiệu lực, Quý khách vui lòng khởi tạo mã xác thực mới.";
		
		public static final String PASS_BLANK_MESSAGE = "Quý khách vui lòng nhập mật khẩu đăng nhập ứng dụng";
		public static final String PASS_NOT_ENOUGH_MESSAGE = "OTP phải đủ 6 ký tự, Quý khách vui lòng kiểm tra lại";
		public static final String PASS_NOT_EXIST_MESSAGE = "Mật khẩu không chính xác. Quý khách lưu ý, dịch vụ VCB-Mobile B@nking sẽ bị tạm khóa và dịch vụ VCBPAY sẽ bị hủy nếu Quý khách nhập sai mật khẩu quá 5 lần.";
		public static final String PASS_OVER_3_TIMES_MESSAGE = "Nhập sai OTP quá 3 lần. Quý khách vui lòng thực hiện giao dịch khác.";
		public static final String PASS_OVER_TIMES_MESSAGE = "OTP đã hết hiệu lực, Quý khách vui lòng khởi tạo mã xác thực mới.";
		
		public static final String SAVE_SUCCESS_MESSAGE = "Lưu vào thư viện ảnh thành công";
	}


}


