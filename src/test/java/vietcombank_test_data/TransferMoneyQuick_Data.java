package vietcombank_test_data;

public class TransferMoneyQuick_Data {
	
	public static class Tittle_Quick {

		public static final String AVAIBLE_BALANCES = "Số dư khả dụng";
		public static final String TYPE_SELECT_CARD_NUMBER = "Nhập/ chọn số thẻ";
		public static final String TYPE_SELECT_ACCOUNT = "Nhập/ chọn tài khoản thụ hưởng VND";
		public static final String NAME_RECIVED = "Tên người hưởng";
		public static final String NAME_RECIVED_TEXT = "Tên người thụ hưởng";
		public static final String ACCOUNT_RECIVED_TEXT = "Tài khoản thụ hưởng";
		public static final String BANK_RECIVED = "Ngân hàng thụ hưởng";
		public static final String CONTINUE_BUTTON = "Tiếp tục";
		public static final String CODE_TRANSFER = "Mã giao dịch";
		public static final String CONTENT = "Nội dung";
		public static final String CAR_NUMBER = "Số thẻ";
		public static final String NEW_TRANSFER = "Thực hiện giao dịch mới";
		public static final String CARD_TRIP = "Số thẻ chuyển đi";
		public static final String TRANSACTION_INFO = "Thông tin giao dịch";
		public static final String RATE_REFER = "Tỷ giá quy đổi tham khảo";
		public static final String UNSATISFACTORY = "không";

	}
	
	

	public static class TransferQuick {
		public static final String[] OPTION_TRANSFER = { "Chuyển tiền nhanh 24/7 qua tài khoản", "Chuyển tiền nhanh 24/7 qua thẻ" };
		public static final String ACCOUNT_TO_INVALID = "01298372945";
		public static final String[] COST = { "Phí giao dịch người chuyển trả", "Phí giao dịch người nhận trả" };
		public static final String[] COST_SUB = { "Người chuyển trả", "Người nhận trả" };
		public static final String[] ACCURACY = { "Mật khẩu đăng nhập", "SMS OTP" ,"VCB - Smart OTP"};

		public static final String BANK_INVALID = "Ngân hàng Test";
		public static final String MONEY = "100000";
		public static final String MONEY_USD = "10";
		public static final String MONEY_EUR = "10";
		public static final String RECEIVER_NAME = "NGUYEN VAN NAPAS TEST ONLINE IBFT 247 VN";
		public static final String SUCCESS_TRANSFER_MONEY = "GIAO DỊCH ĐƯỢC XỬ LÝ THÀNH CÔNG TẠI VIETCOMBANK";
		public static final String SUCCESS_TRANSFER_MONEY1 = "CHUYỂN KHOẢN THÀNH CÔNG";

		public static final String COST_AMOUNT_USD_EUR_OTP = "0.33";
		public static final String COST_AMOUNT_USD_EUR_MK = "0.02";
		public static final String PASSWORD = "Nhập mật khẩu";

		public static final String CARD_FROM = "0011000000863";
		public static final String SUCCESS_TRANSFER_MONEY_IN_VCB_RECURRENT = "LẬP LỆNH THÀNH CÔNG";
		public static final String PAYMENT_BY_PASSWORD_FEE = "3,300";
		public static final String PAYMENT_BY_OTP_FEE = "5,500";
		public static final String EUR_PAYMENT_BY_PASSWORD_FEE = "0.12";
		public static final String EUR_PAYMENT_BY_OTP_FEE = "0.2";
		public static final String USD_PAYMENT_BY_PASSWORD_FEE = "0.11";
		public static final String USD_PAYMENT_BY_OTP_FEE = "0.18";
		public static final String USD_MAX_TRANSECTION = "21000";
		public static final String USD_MAX_TRANSECTION_GROUP = "10000";


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
		public static final String MONEY_FOUR_NUMBER_VND = "50000";
		public static final String MONEY_LESS_MIN_LIMIT_VND = "6000";
		public static final String MONEY_EXCEED_MAX_LIMIT_VND = "1500000000";
		public static final String MONEY_EXCEED_MAX_TRANSFER_ONE_DAY_VND = "100000000";
		public static final String MONEY_EXCEED_MAX_TRANSFER_TYPE_ONE_DAY_VND = "8000001";
		public static final String MONEY_EXCEED_MAX_TRANSFER_PACKAGE_ONE_DAY_VND = "6000001";
		public static final String MONEY_ALLOW_VND = "4900000";
		public static final String[] LIST_MONEY_SHOW_VND = { "50,000 VND", "500,000 VND", "5,000,000 VND" };
		public static final String[] LIST_MONEY_SHOW_USD = { "50,002 USD ~ 1,500,060,000 VND", "500,020.00 USD ~ 15,000,600,000 VND" };
		public static final String[] LIST_MONEY_SHOW_EUR = { "50,002 EUR ~ 1,199,936,000 VND", "500,020 EUR ~ 11,999,360,000 VND" };

		public static final String MONEY_DOUBLE_NUMBER_USD_EUR = ".09";
		public static final String MONEY_NINE_NUMBER_USD_EUR = MONEY_NINE_NUMBER_VND + MONEY_DOUBLE_NUMBER_USD_EUR;
		public static final String MONEY_NINE_NUMBER_INVALID_USD_EUR = MONEY_NINE_NUMBER_VND + ".091";
		public static final String MONEY_TEN_NUMBER_USD_EUR = MONEY_TEN_NUMBER_VND + MONEY_DOUBLE_NUMBER_USD_EUR;
		public static final String MONEY_ELEVEN_NUMBER_USD_EUR = "12345678912";
		public static final String MONEY_TEXT_INVALID_USD_EUR = "abc@12345";
		public static final String MONEY_FOUR_NUMBER_USD_EUR = "5000.20";

		public static final String NOTE = "Phan Thi Hiep chuyen";
		public static final String NOI_DUNG = "Thanh toan ve tau";
		public static final String NOTE_1_CHAR_VIETNAM = "a";
		public static final String NOTE_2_CHAR = "ab";
		public static final String NOTE_140_CHAR = "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
		public static final String NOTE_140_CHAR_VIETNAM = "Giao dịch không thành công do tài khoản hưởng không hợp lệ. Quý khách vui lòng kiểm tra lại. Chi tiết xem tại http://www.vietcombank.com.vn ";
		public static final String NOTE_141_CHAR_INVALID = "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901";
		public static final String NOTE_VIETNAM_KEY_INVALID = "Xin chào";
		public static final String NOTE_SPECAL_CHAR = "@ _ - \\ . , & * # ! $ + : ; ? / | % ( ) =";
		public static final String NOTE_NUMBER_CHAR = "12345";

	}

	public static class MessageTransferMoney {
		public static final String ACCOUNT_NOT_EXIST = "Giao dịch không thành công do tài khoản hưởng không hợp lệ. Quý khách vui lòng kiểm tra lại. Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
		public static final String ACCOUNT_BLANK = "Quý khách vui lòng nhập tài khoản nhận";
		public static final String BANK_BLANK = "Quý khách vui lòng chọn ngân hàng hưởng";
		public static final String AMOUNT_BLANK = "Quý khách vui lòng nhập số tiền chuyển khoản";
		public static final String NOTE_BLANK = "Quý khách vui lòng nhập nội dung giao dịch";
		public static final String MESSAGE_LESS_MIN_LIMIT_VND = "Chuyển tiền không thành công. Số tiền giao dịch nhỏ hơn hạn mức 5,000 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";
		public static final String MESSAGE_EXCEED_MAX_LIMIT_VND = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 1,000,000,000 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";
		public static final String MESSAGE_EXCEED_MAX_TRANSFER_TYPE_ONE_DAY_VND = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000,000 VND/1 ngày của nhóm dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";
		public static final String MESSAGE_EXCEED_MAX_TRANSFER_PACKAGE = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000,000 VND/1 ngày của gói dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";
		public static final String MESSAGE_EXCEED_MAX_TRANSFER_PACKAGE_ONE_DAY_VND = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 9,000,000 VND/1 ngày của gói dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
		public static final String MESSAGE_EXCEED_MAX_TRANSFER_ONE_DAY_VND = "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000,000 VND/nhóm dich vụ chuyển tiền, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 1900545413 của Vietcombank để được trợ giúp.";
		public static final String AMOUNT_NOT_ENOUGH = "Giao dịch không thành công do tài khoản của Quý khách không đủ số dư. Quý khách vui lòng kiểm tra lại.";
		public static final String SAME_ACCOUNT_NAME = "Tài khoản nguồn không hợp lệ do là tài khoản đồng chủ sở hữu.";
		public static final String ACCOUNT_TO_INVALID_MESSAGE = "Giao dịch không thành công do tài khoản hưởng không hợp lệ. Quý khách vui lòng kiểm tra lại. Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
		public static final String OTP_BLANK_MESSAGE = "Quý khách vui lòng nhập mã OTP";
		public static final String OTP_NOT_ENOUGH_MESSAGE = "OTP phải đủ 6 ký tự, Quý khách vui lòng kiểm tra lại";
		public static final String OTP_NOT_EXIST_MESSAGE = "OTP không chính xác, Quý khách vui lòng kiểm tra lại.";
		public static final String OTP_OVER_4_TIMES_MESSAGE = "Nhập sai OTP quá 4 lần. Quý khách vui lòng thực hiện giao dịch mới.";
		public static final String OTP_OVER_TIMES_MESSAGE = "OTP đã hết hiệu lực, Quý khách vui lòng khởi tạo mã xác thực mới.";

		public static final String PASS_BLANK_MESSAGE = "Quý khách vui lòng nhập mật khẩu đăng nhập ứng dụng";
		public static final String PASS_NOT_ENOUGH_MESSAGE = "OTP phải đủ 6 ký tự, Quý khách vui lòng kiểm tra lại";
		public static final String PASS_NOT_EXIST_MESSAGE = "Mật khẩu không chính xác. Quý khách lưu ý, dịch vụ VCB-Mobile B@nking sẽ bị tạm khóa và dịch vụ VCBPAY sẽ bị hủy nếu Quý khách nhập sai mật khẩu quá 5 lần.";
		public static final String PASS_OVER_3_TIMES_MESSAGE = "Nhập sai OTP quá 3 lần. Quý khách vui lòng thực hiện giao dịch khác.";
		public static final String PASS_OVER_TIMES_MESSAGE = "OTP đã hết hiệu lực, Quý khách vui lòng khởi tạo mã xác thực mới.";

		public static final String SAVE_SUCCESS_MESSAGE = "Ảnh đã lưu trong thư viện.";
		public static final String SAVE_CONTACT_SUCCESS_MESSAGE = "Quý khách đã lưu danh bạ thụ hưởng thành công";
		
		public static final String MESSAGE_LESS_MIN_LIMIT_USD = "Chuyển tiền không thành công. Số tiền giao dịch nhỏ hơn hạn mức 5,000 VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
		public static final String MESSAGE_LESS_MAX_LIMIT_USD = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 400,000,000 VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		public static final String MESSAGE_MAX_LIMIT_USD_GROUP = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 200,000,000 VND/1 ngày của nhóm dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		public static final String MESSAGE_MAX_LIMIT_USD_PACKAGE = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000,000 VND/1 ngày của gói dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
	}

}
