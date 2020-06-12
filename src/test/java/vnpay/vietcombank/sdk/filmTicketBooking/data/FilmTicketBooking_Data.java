package vnpay.vietcombank.sdk.filmTicketBooking.data;

import java.util.Arrays;
import java.util.List;

public class FilmTicketBooking_Data {
	public static final List<String> LIST_CITY = Arrays.asList("B. Ma Thuột", "Biên Hoà", "Bình Dương", "Bạc Liêu", "Bảo Lộc", "Bắc Giang", "Bắc Ninh", "Bến Tre", "Cà Mau", "Cần Thơ", "Gia Lai", "Huế", "Hà Nam", "Hà Nội", "Hà Tĩnh", "Hạ Long", "Hải Dương", "Hải Phòng", "Hậu Giang", "Hồ Chí Minh", "Kiên Giang", "Kon Tum", "Long An", "Long Xuyên", "Lạng Sơn", "Mỹ Tho", "Nam Định", "Nha Trang", "Ninh Bình", "Phan Thiết", "Phú Yên", "Quy Nhơn","Quảng Ngãi", "Quảng Trị", "Sóc Trăng", "Sơn La", "Thanh Hóa", "Thái Bình", "Thái Nguyên", "Trà Vinh", "Tuyên Quang", "Tây Ninh", "Vinh", "Việt Trì", "Vĩnh Long", "Vũng Tàu", "Yên Bái", "Đà Lạt", "Đà Nẵng", "Đồng Hới");
	public static final List<String> LIST_CINEMA_HANOI = Arrays.asList("TT Chiếu phim Quốc gia");
	public static final List<String> LIST_CINEMA_HCM = Arrays.asList("Cinestar", "Mega GS", "BHD Star Cineplex");
	public static final String MAX_10_SEATS_PER_1_MESSAGE = "Quý khách chỉ có thể chọn tối đa 10 vé 1 lần";
	public static final String PHONE_BOOKING = "0399764702";
	public static final String EMAIL_BOOKING = "vnpay.automation.team@gmail.com";
	
	// MESSEGE ERROR LIMIT
	
	public static final String MESSEGE_ERROR_LOWER_MIN_LIMIT_A_TRAN = "Giao dịch không thành công. Số tiền giao dịch nhỏ hơn hạn mức 40,000 VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900 545413 để được trợ giúp.";
	public static final String MESSEGE_ERROR_HIGHER_MAX_LIMIT_A_TRAN = "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức 250,000 VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900 545413 để được trợ giúp.";
	public static final String MESSEGE_ERROR_HIGHER_MAX_LIMIT_A_DAY = "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức 260,000 VND/1 ngày, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900 545413 để được trợ giúp.";
	public static final String MESSEGE_ERROR_HIGHER_MAX_LIMIT_GROUP = "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức 50,000 VND/1 ngày của nhóm dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900 545413 để được trợ giúp.";
	public static final String MESSEGE_ERROR_HIGHER_MAX_LIMIT_PACKAGE = "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức 50,000 VND/1 ngày của gói dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900 545413 để được trợ giúp.";
	
	// MESSEGE SUCCESS
	public static final String MESSEGE_SUCCESS = "THANH TOÁN THÀNH CÔNG";
	
	// MESSEGE ERROR LIMIT 1 ngày của gói dịch vụ
	public static final String AMOUNT_MAX_LIMIT_A_TRAN = "200000";
	public static final String AMOUNT_MIN_LIMIT_A_TRAN = "40000";
	public static final String AMOUNT_HIGHER_MAX_LIMIT_A_DAY = "240000";
	public static final String AMOUNT_HIGHER_MAX_LIMIT_A_GROUP = "40000";
	public static final String AMOUNT_HIGHER_MAX_LIMIT_A_PACKAGE  = "40000";

	//Title
	public static final String FILM_TITLE = "Đặt vé xem phim";
	public static final String AGREE = "Đồng ý";
	public static final String BOOKING_TICKET = "Đặt vé";
	public static final String PAY = "Thanh toán";
	public static final String ACCOUNT_FROM_LABEL = "Tài khoản nguồn";
	public static final String FILM = "Phim";
	public static final String TIME_SLOT = "Suất chiếu";
	public static final String TIME = "Thời lượng";
	public static final String ADDRESS = "Địa chỉ";
	public static final String MOVIE_THEATER = "Rạp";
	public static final String MONEY = "Số tiền";
	public static final String NAME_CUSTOMER = "Tên khách hàng";
	public static final String PHONE_TAKE_TICKET = "Số điện thoại nhận vé";
	public static final String EMAIL_TAKE_TICKET = "Email nhận vé";
	public static final String PASSWORD = "Mật khẩu đăng nhập";
	public static final String FEE = "Số tiền phí";
	public static final String NEXT = "Tiếp tục";
	public static final String CODE_TRANSFER = "Mã giao dịch";
	public static final String NEW_TRANSFER = "Thực hiện giao dịch mới";
	public static final String CODE_TICKET = "Mã vé";
	public static final String BUY_TICKET_FILM = "Mua vé xem phim";
	public static final String SMART_OTP = "VCB - Smart OTP";
	public static final String CITY = "Hồ Chí Minh";
	public static final String AVAILABLE_BALANCES = "Số dư khả dụng";
	public static final String CHOOSE_SEAT = "Chọn chỗ ngồi";
	public static final String BHD_MOVIE_THEATER = "BHD Star Cineplex";
}

