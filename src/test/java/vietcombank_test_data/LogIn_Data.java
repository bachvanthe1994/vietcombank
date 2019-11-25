package vietcombank_test_data;

public class LogIn_Data {
	public class Login_Account {
		public static final String LOCKED_PHONE = "0973441417";
		public static final String LOCKED_PASS = "0904797863";
		public static final String PHONE = "0904797863";
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

	}

	public class UI {
		public static final String PHONE_NUMBER = "Số điện thoại";
		public static final String PASSWORD_LABEL = "Mật khẩu đăng nhập";
		public static final String REGISTER = "Đăng ký VCB-Mobile B@nking";
		public static final String ONLINE_REGISTER = "Đăng ký trực tuyến";
		public static final String FORGOT_PASSWORD = "Quên mật khẩu?";

	}
}