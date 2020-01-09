package vietcombank_test_data;

public class LogIn_Data {
	public static class Login_Account {
		public static final String LOCKED_PHONE = "0335059863";
		public static final String LOCKED_PASS = "1e9978d3";

		public static final String PHONE = "0904797863";
		public static final String PHONE_INVALID = "0904797863";
		public static final String PHONE_HIDDEN = PHONE.substring(0, 4) + "***" + PHONE.substring(PHONE.length() - 3);
		public static final int OTP_INVALID_TIMES = 3;
		public static final int PASSWORD_INVALID_TIMES = 5;

		public static final String WRONG_PASSWORD = "18a9713e";
		public static final String NEW_PASSWORD = "Abc12345";
		public static final String OTP = "123456";
		public static final String INVALID_PASSWORD = "123456a  @ABC";
		public static final String NULL = "";
		public static final String PASSWORD_GREATER_THAN_20 = "1ABC1111@11111111111111111";
	}

	public class Message {
		public static final String WELCOME_MESSAGE = "Chào mừng Quý khách đến với ứng dụng" + "\n" + "VCB-Mobile B@nking của Vietcombank";
		public static final String CHANGE_PASSWORD_INSTRUCTION = "Quý khách vui lòng đổi mật khẩu mặc định được cung cấp khi đăng ký sử dụng dịch vụ";
		public static final String INPUT_PASSWORD_MESSAGE = "Quý khách vui lòng nhập mật khẩu đăng nhập để kích hoạt lại dịch vụ.";
		public static final String OTP_INSTRUCTION = "Quý khách vui lòng nhập OTP đã được gửi về số điện thoại";
		public static final String EMPTY_MESSAGE = "Mật khẩu không được bỏ trống. Quý khách vui lòng kiểm tra lại";
		public static final String WRONG_PASSWORD_MESSAGE = "Mật khẩu đăng nhập không chính xác. Quý khách lưu ý, chức năng kích hoạt dịch vụ trên thiết bị khác sẽ bị tạm khóa 24h nếu Quý khách nhập sai mật khẩu từ 5 lần trở lên";
		public static final String LOCKED_ACCOUNT_MESSAGE = "Số điện thoại sử dụng dịch vụ VCB-Mobile B@nking đã bị khóa. Quý khách vui lòng yêu cầu mở lại dịch vụ tại Quầy giao dịch của Vietcombank. ";
		public static final String PHONE_NUMBER_NOT_EMPTY = "Số điện thoại không được bỏ trống. Quý khách vui lòng kiểm tra lại";
		public static final String PHONE_NUMBER_INVALID = "Số điện thoại không hợp lệ. Quý khách vui lòng kiểm tra lại.";
		public static final String PHONE_NUMBER_VALID = "Tài khoản đã được kích hoạt trên thiết bị khác. Quý khách có đồng ý tiếp tục cài đặt ứng dụng Mobile B@nking trên thiết bị hiện tại không?";
		public static final String PHONE_NOT_REGISTER = "Số điện thoại chưa đăng ký hoặc đã ngừng dịch vụ. Quý khách vui lòng đăng ký dịch vụ tại quầy hoặc đăng ký bằng tài khoản VCB-iB@nking/VCB-Mobile Bankplus. Chi tiết liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
	}

	public class UI {
		public static final String PHONE_NUMBER = "Số điện thoại";
		public static final String PASSWORD_LABEL = "Mật khẩu đăng nhập";
		public static final String REGISTER = "Đăng ký VCB-Mobile B@nking";
		public static final String ONLINE_REGISTER = "Đăng ký trực tuyến";
		public static final String FORGOT_PASSWORD = "Quên mật khẩu?";
		public static final String CONTINUE_BUTTON = "Tiếp tục";
		public static final String FIND_ATM = "Tìm kiếm ATM/chi nhánh gần đây";
		public static final String CLOSE_BUTTON = "Đóng";
		public static final String CANCEL_BUTTON = "Hủy";
		public static final String AGREE_BUTTON = "Đồng ý";
		public static final String REGISTER_NOW_BUTTON = "Đăng ký ngay";
		public static final String CONTINUE_USE_SERVICE_BUTTON = "Tiếp tục sử dụng dịch vụ";

	}
}
