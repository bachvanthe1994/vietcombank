package vnpay.vietcombank.sdk.hotelBooking.data;

import java.util.Arrays;
import java.util.List;

import vietcombank_test_data.LogIn_Data;

public class HotelBooking_Data {
	public static final String FAMOUS_LOCATION[] = { "Hà Nội", "TP Hồ Chí Minh" };
	public static final String GREATER_200_SPECIAL_VIETNAMESE_CHARACTERS = "!@#$^&*()Đặt Phòng Khách Sạn !@#$^&*()Đặt Phòng Khách Sạn !@#$^&*()Đặt Phòng Khách Sạn !@#$^&*()Đặt Phòng Khách Sạn !@#$^&*()Đặt Phòng Khách Sạn !@#$^&*()Đặt Phòng Khách Sạn !@#$^&*()Đặt Phòng Khách Sạn !@#$^&*()Đặt Phòng Khách Sạn !@#$^&*()Đặt Phòng Khách Sạn !@#$^&*()Đặt Phòng Khách Sạn !@#$^&*()Đặt Phòng Khách Sạn !@#$^&*()Đặt Phòng Khách Sạn !@#$^&*()Đặt Phòng Khách Sạn !@#$^&*()Đặt Phòng Khách Sạn !@#$^&*()Đặt Phòng Khách Sạn";
	public static final String CURRENT_LOCATION = "Đống Đa, Hà Nội";
	public static final List<String> STATUS_HOTEL_BOOKING_LIST = Arrays.asList("Tất cả", "Chờ thanh toán", "Đặt phòng thành công", "Đặt phòng thất bại", "Đã huỷ", "Hết hạn thanh toán", "Chờ xử lý");
	public static final List<String> CRITERIA_ORDER_LIST = Arrays.asList("Giá từ thấp đến cao", "Giá từ cao đến thấp", "Gần trung tâm", "Sắp xếp theo điểm đánh giá", "Sắp xếp theo khoảng cách");
	public static final List<String> SERVICE_HOTEL_LIST = Arrays.asList("Máy điều hòa", "Miễn phí Wifi trong phòng", "Tiếng Anh", "Tiếng Việt", "Lễ tân 24 giờ", "Phòng không hút thuốc", "Thang máy", "Dịch vụ giữ hành lý", "Wifi ở khu vực công cộng", "Dịch vụ ủi (là)", "Giặt ủi", "An ninh 24 giờ", "Dịch vụ đặt vé", "Dịch vụ lau dọn", "Dịch vụ báo thức", "...");
	public static final List<String> DICTRICT_HOTEL_LIST = Arrays.asList("Quận Ba Đình", "Quận Hoàn Kiếm", "Quận Tây Hồ", "Quận Long Biên", "Quận Cầu Giấy", "Quận Đống Đa", "Quận Hai Bà Trưng", "Quận Hoàng Mai", "Quận Hà Đông", "Quận Nam Từ Liêm", "Thành phố Vĩnh Yên", "...");
	public static final List<String> PLACE_NEARLY_HOTEL_LIST = Arrays.asList("Phố cổ Hà Nội", "Xung quanh Hồ Tây", "Lăng Chủ Tịch Hồ Chí Minh", "Bến xe Mỹ Đình", "Bến xe Lương Yên", "Ga Hà Nội", "Ga Long Biên");
	public static final String EMPTY_NAME_MESSAGE = "Quý khách vui lòng nhập họ và tên";
	public static final String EMPTY_PHONE_MESSAGE = "Quý khách vui lòng nhập Số điện thoại";
	public static final String EMPTY_EMAIL_MESSAGE = "Quý khách vui lòng nhập Email";
	public static final String NOT_VALID_EMAIL_MESSAGE = "Email không đúng định dạng. Vui lòng kiểm tra lại";
	public static final String NOT_VALID_EMAIL_BILL_MESSAGE = "Email không chính xác, Quý khách vui lòng kiểm tra lại.";
	public static final String NOT_VALID_PHONE_MESSAGE = "Số điện thoại khách hàng không hợp lệ";
	public static final String TRANSACTION_VALIDATION = "Xác thực giao dịch";
	public static final String OTP_NOTIFICATION_SENDED = "Quý khách vui lòng nhập OTP đã được gửi về số điện thoại " + LogIn_Data.Login_Account.PHONE_HIDDEN;
	public static final String PASSWORD_NOTIFICATION = "Vui lòng nhập mật khẩu đăng nhập ứng dụng của Quý khách để xác nhận giao dịch";
	public static final String OTP_EMPTY = "Quý khách vui lòng nhập mã OTP";
	public static final String PASSWORD_EMPTY = "Quý khách vui lòng nhập mật khẩu đăng nhập ứng dụng";
	public static final String OTP_LESS_THAN_6_CHARACTER = "OTP phải đủ 6 ký tự, Quý khách vui lòng kiểm tra lại";
	public static final String PASSWORD_LESS_THAN_8_CHARACTER = "Mật khẩu không chính xác. Quý khách lưu ý, dịch vụ VCB-Mobile B@nking sẽ bị tạm khóa và dịch vụ VCBPAY sẽ bị hủy nếu Quý khách nhập sai mật khẩu quá " + LogIn_Data.Login_Account.PASSWORD_INVALID_TIMES + " lần.";
	public static final String OTP_INVALID = "OTP không chính xác, Quý khách vui lòng kiểm tra lại.";
	public static final String PASSWORD_INVALID = "Mật khẩu không chính xác. Quý khách lưu ý, dịch vụ VCB-Mobile B@nking sẽ bị tạm khóa và dịch vụ VCBPAY sẽ bị hủy nếu Quý khách nhập sai mật khẩu quá " + LogIn_Data.Login_Account.PASSWORD_INVALID_TIMES + " lần.";
	public static final String OTP_INVALID_N_TIMES = "Nhập sai OTP quá " + LogIn_Data.Login_Account.OTP_INVALID_TIMES + " lần. Quý khách vui lòng thực hiện giao dịch khác.";
	public static final String PASSWORD_INVALID_N_TIMES = "";
	public static final String OTP_EXPIRE = "Mã xác thực đã hết hiệu lực. Quý khách vui lòng khởi tạo mã xác thực mới.";
	public static final String SPECIAL_TEXT_NUMBER_OVER_100_CHARACTERS = "m!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc!@#$%&*()abcabc";
	public static final String NUMBER_OVER_15_CHARACTERS = "1234567890000000";
	public static final String EMPTY_HOTEL_NAME = "Quý khách vui lòng nhập Tên công ty.";
	public static final String EMPTY_TAX_CODE = "Quý khách vui lòng nhập Mã số thuế.";
	public static final String EMPTY_HOTEL_ADDRESS = "Quý khách vui lòng nhập Địa chỉ công ty.";
	public static final String EMPTY_EMAIL = "Quý khách vui lòng nhập Email nhận hóa đơn.";

	public static final String HOTEL_NAME_BOOKING = "Khách sạn Apus Nha Trang";
	public static final String HOTEL_NAME_BOOKING_INPUT = "Khách sạn Apus Nha Trang";
	public static final String NAME_HOTEL_OR_PLACE = "Tên khách sạn hoặc điểm đến";
	public static final String ARE_YOU_HAVE_VOUCHER = "Bạn có mã giảm giá?";
	public static final String PAYMENT = "THANH TOÁN";
	public static final String SOURCE_ACCOUNT = "Tài khoản nguồn";
	public static final String CONTINUE_TEXT = "Tiếp tục";
	public static final String SUCCESS_TRANSFER = "THANH TOÁN THÀNH CÔNG";
	public static final String CODE_TRANSFER = "Mã giao dịch";
	public static final String CODE_ROOM = "Mã nhận phòng";
	public static final String BOOKING_HOTEL = "Đặt phòng khách sạn";
	public static final String BOOKING = "ĐẶT PHÒNG";
	public static final String ACCEPT = "Đồng ý";
	public static final String CHOICE_METHOD_VERIFY = "Chọn phương thức xác thực";
	public static final String NEW_PERFORM_TRANSFER= "Thực hiện giao dịch mới";
	public static final String PASSWORD= "Mật khẩu đăng nhập";
	public static final String SMART_OTP= "VCB - Smart OTP";
	public static final String SMS_OTP= "SMS OTP";
	public static final String AVAILIBLE_BALANCES= "Số dư khả dụng";
	public static final String FEE_AMOUNT= "Số tiền phí";

	public static final String PHONE_BOOKING = "0399764702";
	public static final String EMAIL_BOOKING = "vnpay.automation.team@gmail.com";

	public static final String MESSAGE_MIN_LIMIT_PER_TRANS_1 = "Giao dịch không thành công. Số tiền giao dịch nhỏ hơn hạn mức "; 
	public static final String MESSAGE_MIN_LIMIT_PER_TRANS_2 = "/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
	public static final String MESSAGE_MAX_LIMIT_PER_TRANS_1 = "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức "; 
	public static final String MESSAGE_MAX_LIMIT_PER_TRANS_2 = "/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
	public static final String MESSAGE_MAX_LIMIT_PER_GROUP_TRANS_1 = "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức "; 
	public static final String MESSAGE_MAX_LIMIT_PER_GROUP_TRANS_2 = "/1 ngày của nhóm dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
	public static final String MESSAGE_MAX_LIMIT_PER_PACKAGE_TRANS_1 = "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức "; 
	public static final String MESSAGE_MAX_LIMIT_PER_PACKAGE_TRANS_2 = "/1 ngày của gói dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
	
}
