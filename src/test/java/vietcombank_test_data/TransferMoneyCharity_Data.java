package vietcombank_test_data;

import java.util.Arrays;
import java.util.List;

public class TransferMoneyCharity_Data {
	public static final String ORGANIZATION_INPUT_EMPTY_MESSAGE = "Quý khách vui lòng chọn Quỹ/Tổ chức từ thiện.";
	public static final String MONEY_INPUT_EMPTY_MESSAGE = "Quý khách vui lòng nhập số tiền ủng hộ";
	public static final String NAME_INPUT_EMPTY_MESSAGE = "Quý khách vui lòng nhập tên người ủng hộ";
	public static final String STATUS_INPUT_EMPTY_MESSAGE = "Quý khách vui lòng nhập hoàn cảnh ủng hộ";
	public static final String MONEY_INPUT_OVER_MESSAGE = "Giao dịch không thành công do tài khoản của Quý khách không đủ số dư. Vui lòng kiểm tra lại.";
	public static final String MONEY_INPUT_NOT_ENOUGH_PER_A_TRANSACTION_MESSAGE = "";
	public static final String MONEY_INPUT_OVER_PER_A_TRANSACTION_MESSAGE = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000,000 VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
	public static final List<String> LIST_ORGANIZATION_CHARITY = Arrays.asList("Tấm lòng nhân ái", "Dự án nhà chống lũ", "Lũ lụt Miền Trung", "Test order", "Core mới _ Quỹ nhân ái");
	public static final String STRING_OVER_50_CHARACTERS = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAASSSSSSSSSSSSSSSSSSSSSS";
	public static final String STRING_HAS_SPECIAL_CHARACTERS = "Test!@#$:";
	public static final String MONEY_OVER_TRANSACTION_PER_DAY = "100000001";
	public static final String MONEY_NOT_ENOUGH_FOR_TRANSACTION = "1";
	public static final String TRANSACTION_VALIDATION = "Xác thực giao dịch";
	public static final String OTP_NOTIFICATION_SENDED = "Quý khách vui lòng nhập OTP đã được gửi về số điện thoại " + LogIn_Data.Login_Account.PHONE_HIDDEN;
	public static final String PASSWORD_NOTIFICATION = "Vui lòng nhập mật khẩu đăng nhập ứng dụng của Quý khách để xác nhận giao dịch";
	public static final String OTP_EMPTY = "Quý khách vui lòng nhập mã OTP";
	public static final String PASSWORD_EMPTY = "Quý khách vui lòng nhập mật khẩu đăng nhập ứng dụng";
	public static final String OTP_LESS_THAN_6_CHARACTER = "OTP phải đủ 6 ký tự, Quý khách vui lòng kiểm tra lại";
	public static final String PASSWORD_LESS_THAN_8_CHARACTER = "Mật khẩu không chính xác. Quý khách lưu ý, dịch vụ VCB-Mobile B@nking sẽ bị tạm khóa và dịch vụ VCBPAY sẽ bị hủy nếu Quý khách nhập sai mật khẩu quá 300 lần.";
	public static final String OTP_INVALID = "OTP không chính xác, Quý khách vui lòng kiểm tra lại.";
	public static final String PASSWORD_INVALID = "Mật khẩu không chính xác. Quý khách lưu ý, dịch vụ VCB-Mobile B@nking sẽ bị tạm khóa và dịch vụ VCBPAY sẽ bị hủy nếu Quý khách nhập sai mật khẩu quá 300 lần.";
	public static final String OTP_INVALID_N_TIMES = "Nhập sai OTP quá " + LogIn_Data.Login_Account.OTP_INVALID_TIMES + " lần. Quý khách vui lòng thực hiện giao dịch khác.";
	public static final String PASSWORD_INVALID_N_TIMES = "";
	public static final String OTP_EXPIRE = "Mã xác thực đã hết hiệu lực. Quý khách vui lòng khởi tạo mã xác thực mới.";
	public static final String ORGANIZATION = "Test order";
	
}
