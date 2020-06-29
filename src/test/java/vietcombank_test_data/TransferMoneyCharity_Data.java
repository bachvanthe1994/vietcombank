package vietcombank_test_data;

import java.util.Arrays;
import java.util.List;

public class TransferMoneyCharity_Data {
	public static final String ORGANIZATION_INPUT_EMPTY_MESSAGE = "Quý khách vui lòng chọn Quỹ/Tổ chức từ thiện.";
	public static final String MONEY_INPUT_EMPTY_MESSAGE = "Quý khách vui lòng nhập số tiền ủng hộ";
	public static final String NAME_INPUT_EMPTY_MESSAGE = "Quý khách vui lòng nhập tên người ủng hộ";
	public static final String STATUS_INPUT_EMPTY_MESSAGE = "Quý khách vui lòng nhập hoàn cảnh ủng hộ";
	
	public static final String LOWER_THAN_MIN_MESSAGE = "Chuyển tiền không thành công. Số tiền giao dịch nhỏ hơn hạn mức ";
	public static final String HIGHER_THAN_MAX_MESSAGE = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức ";
	public static final String DETAIL_A_TRANSACTION_MESSAGE = " VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
	public static final String DETAIL_A_DAY_MESSAGE = " VND/1 ngày, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
	
	
	public static final String MONEY_INPUT_OVER_MESSAGE = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 300,000 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
	public static final String MONEY_INPUT_NOT_ENOUGH_PER_A_TRANSACTION_MESSAGE = "Chuyển tiền không thành công. Số tiền giao dịch nhỏ hơn hạn mức 100,000 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";
	public static final String MONEY_INPUT_OVER_PER_A_TRANSACTION_MESSAGE = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000,000 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
	public static final List<String> LIST_ORGANIZATION_CHARITY = Arrays.asList("Tấm lòng nhân ái - TK 10 số", "Dự án nhà chống lũ", "Tấm lòng nhân ái - TK 13 số", "Core moi Quy nhan ai");
	public static final String STRING_OVER_50_CHARACTERS = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAASSSSSSSSSSSSSSSSSSSSSS";
	public static final String STRING_HAS_SPECIAL_CHARACTERS = "Test!@#$:";
	public static final String MONEY_OVER_TRANSACTION_PER_DAY = "100000001";
	public static final String MONEY_NOT_ENOUGH_FOR_TRANSACTION = "1";
	public static final String TRANSACTION_VALIDATION = "Xác thực giao dịch";
	public static final String OTP_NOTIFICATION_SENDED = "Quý khách vui lòng nhập OTP đã được gửi về số điện thoại " + LogIn_Data.Login_Account.PHONE_HIDDEN;
	public static final String PASSWORD_NOTIFICATION = "Vui lòng nhập mật khẩu đăng nhập ứng dụng của Quý khách để xác thực giao dịch";
	public static final String OTP_EMPTY = "Quý khách vui lòng nhập mã OTP";
	public static final String PASSWORD_EMPTY = "Quý khách vui lòng nhập mật khẩu đăng nhập ứng dụng";
	public static final String OTP_LESS_THAN_6_CHARACTER = "OTP phải đủ 6 ký tự, Quý khách vui lòng kiểm tra lại";
	public static final String PASSWORD_LESS_THAN_8_CHARACTER = "Mật khẩu không chính xác. Quý khách lưu ý, dịch vụ VCB-Mobile B@nking sẽ bị tạm khóa và dịch vụ VCBPAY sẽ bị hủy nếu Quý khách nhập sai mật khẩu quá " + LogIn_Data.Login_Account.PASSWORD_INVALID_TIMES + " lần.";
	public static final String OTP_INVALID = "OTP không chính xác, Quý khách vui lòng kiểm tra lại.";
	public static final String PASSWORD_INVALID = "Mật khẩu không chính xác. Quý khách lưu ý, dịch vụ VCB-Mobile B@nking sẽ bị tạm khóa và dịch vụ VCBPAY sẽ bị hủy nếu Quý khách nhập sai mật khẩu quá " + LogIn_Data.Login_Account.PASSWORD_INVALID_TIMES + " lần.";
	public static final String OTP_INVALID_N_TIMES = "Nhập sai OTP quá " + LogIn_Data.Login_Account.OTP_INVALID_TIMES + " lần. Quý khách vui lòng thực hiện giao dịch khác.";
	public static final String PASSWORD_INVALID_N_TIMES = "";
	public static final String OTP_EXPIRE = "Mã xác thực đã hết hiệu lực. Quý khách vui lòng khởi tạo mã xác thực mới.";
	public static final String SUCCESS_TRANSFER_MONEY = "CHUYỂN KHOẢN THÀNH CÔNG";
	public static final String CONFIRM_MAX_TRANSECTION_LIMIT_DAY = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 500,000 VND/1 ngày, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.”";
	public static final String CONFIRM_MAX_TRANSECTION_LIMIT_DAY_GROUP = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 350,000,000 VND/1 ngày của nhóm dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
	public static final String CONFIRM_MAX_TRANSECTION_LIMIT_DAY_PACKAGE = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 300,000,000 VND/1 ngày của gói dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
	public static final String STATUS_TRANSFER_MONEY = "Trạng thái chuyển tiền";
	public static final String TRANSFER_CHARITY = "Chuyển tiền từ thiện";
	public static final String AVAILABLE_BALANCE = "Số dư khả dụng";
	public static final String ORGANIRATION_CHARITY = "Quỹ/ Tổ chức từ thiện";
	public static final String MONEY_CHARITY = "Số tiền ủng hộ";
	public static final String NAME_CHARITY = "Tên người ủng hộ";
	public static final String ADDRESS_CHARITY = "Địa chỉ người ủng hộ";
	public static final String STATUS_CHARITY = "Hoàn cảnh ủng hộ";
	public static final String CONTINUE_BUTTON = "Tiếp tục";
	public static final String SOURCE_ACCOUNT = "Tài khoản nguồn";
	public static final String DESTINATION_ACCOUNT = "Tài khoản đích";
	public static final String DESTINATION_NAME = "Tên Quỹ/ Tổ chức từ thiện";
	public static final String ACCURACY_METHOD = "Chọn phương thức xác thực";
	public static final String RECIEVED_NAME = "Tên người thụ hưởng";
	public static final String RECIEVED_ACCOUNT = "Tài khoản thụ hưởng";
	public static final String TRANSACTION_CODE = "Mã giao dịch";
	public static final String NEW_TRANSACTION_PERFORM = "Thực hiện giao dịch mới";
	public static final String ORGANIZATION = "Tấm lòng nhân ái - TK 10 số";
	public static final String BE_TRANSFER_CHARITY_TEXT = "Chuyển tiền từ thiện (chuyển khoản nội bộ)";

}
