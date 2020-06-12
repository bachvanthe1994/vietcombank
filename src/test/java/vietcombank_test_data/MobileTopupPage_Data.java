package vietcombank_test_data;


public class MobileTopupPage_Data {

	public static class UIs {
		public static final String MOBILE_TOPUP_TITLE = "Nạp tiền điện thoại";
		public static final String MOBILE_TOPUP_CONFIRM_HEADER_TITLE = "Xác nhận thông tin";
		public static final String MOBILE_TOPUP_CONFIRM_TRANSACTION_TITLE = "Xác thực giao dịch";
		public static final String MOBILE_TOPUP_CONFIRM_PASSWORD_TRANSACTION_MESSAGE = "Vui lòng nhập mật khẩu đăng nhập ứng dụng của Quý khách để xác thực giao dịch";
		public static final String MOBILE_TOPUP_CONFIRM_SMS_OTP_TRANSACTION_MESSAGE = "Quý khách vui lòng nhập mã OTP đã được gửi về số điện thoại";
		public static final String MOBILE_TOPUP_CONFIRM_TITLE = "Quý khách vui lòng kiểm tra thông tin giao dịch đã khởi tạo";
		public static final String MOBILE_TOPUP_GUIDE = "Hỗ trợ tất cả các mạng viễn thông (Viettel, Vinaphone, Mobifone, G-Mobile, Vietnamobile)";
		public static final String MOBILE_TOPUP_DES_ACCOUNT = "Tài khoản nguồn";
		public static final String MOBILE_TOPUP_BALANCE_LABEL = "Số dư khả dụng";
		public static final String MOBILE_TOPUP_TRANSACTION_INFO = "Thông tin giao dịch";
		public static final String MOBILE_TOPUP_TRANSACTION_GUIDE = "* Để trống nếu nạp cho chính mình";
		public static final String MOBILE_TOPUP_BANK_ACCOUNT_01 = "0121000667333";
		public static final String[] LIST_UNIT_VALUE = { "30,000", "50,000", "100,000", "200,000", "300,000", "500,000" };
		public static final String DEFAULT_UNIT_VALUE = "100,000";
		public static final String PHONE_NUMBER_INVALID_MESSAGE = "Số điện thoại không hợp lệ, Quý khách vui lòng kiểm tra lại.";
		public static final String ACCOUNT_MONEY_NOT_ENOUGH = "Giao dịch không thành công do tài khoản nguồn của Quý khách không đủ số dư.";
		public static final String PASSWORD_METHOD = "Mật khẩu đăng nhập";
		public static final String PASSWORD_METHOD_EMPTY_MESSAGE = "Quý khách vui lòng nhập mật khẩu đăng nhập ứng dụng";
		public static final String SMS_OTP_METHOD_EMPTY_MESSAGE = "Quý khách vui lòng nhập mã OTP";
		public static final String SMS_OTP_METHOD_LESS_THAN_SIX_CHARACTER_MESSAGE = "OTP phải đủ 6 ký tự, Quý khách vui lòng kiểm tra lại";
		public static final String SMS_OTP_METHOD_ERROR_MESSAGE = "OTP không chính xác, Quý khách vui lòng kiểm tra lại.";
		public static final String PASSWORD_METHOD_ERROR_MESSAGE = "Mật khẩu không chính xác. Quý khách lưu ý, dịch vụ VCB-Mobile B@nking sẽ bị tạm khóa và dịch vụ VCBPAY sẽ bị hủy nếu Quý khách nhập sai mật khẩu quá 3 lần.";
		public static final String MOBILE_TOPUP_TRANSACTION_SUCCEED_TITLE = "GIAO DỊCH THÀNH CÔNG";
	}

	public static class Values_Limit {
		public static final String LOWER_LIMIT_A_TRAN = "49999";
		public static final String HIGHER_LIMIT_A_TRAN = "100001";

	}

	public static class MESSEGE_ERROR_Limit {
		public static final String MESSEGE_LOWER_LIMIT_A_TRAN = "Giao dịch không thành công. Số tiền giao dịch nhỏ hơn hạn mức 49,999 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";
		public static final String MESSEGE_HIGHER_LIMIT_A_TRAN = "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";

		public static final String MESSEGE_HIGHER_LIMIT_A_DAY = "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức 400,000 VND/1 ngày, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";
		public static final String MESSEGE_HIGHER_LIMIT_GROUP = "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức 200,000 VND/1 ngày của nhóm dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";
		public static final String MESSEGE_HIGHER_LIMIT_PACKAGE = "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức 200,000 VND/1 ngày của gói dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";

	}

	public static class Text {
		public static final String SOURCE_ACCOUNT = "Tài khoản nguồn";
		public static final String AMOUNT_FEE = "Số tiền phí";
		public static final String PASSWORD = "Mật khẩu đăng nhập";
		public static final String PRICE_CARD = "Mệnh giá thẻ";
		public static final String CODE_TRANSFER = "Mã giao dịch";
		public static final String SMS_OTP = "SMS OTP";
		public static final String SMART_OTP = "VCB - Smart OTP";
		public static final String COUTINUE = "Tiếp tục";
		public static final String SUCCESS_TRANSFER = "GIAO DỊCH THÀNH CÔNG";
		public static final String PHONE_TOPUP = "Số điện thoại được nạp";

	}

}
