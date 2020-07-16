package vnpay.vietcombank.sdk.airticket.data;

public class DomesticAirTicketBooking_Data {

	public static class Message {
		public static final String EMPTY_DEPARTURE_POINT = "Quý khách vui lòng chọn Điểm khởi hành";
		public static final String EMPTY_ARRIVE_POINT = "Quý khách vui lòng chọn Điểm đến.";
		public static final String EMPTY_TIME_FLIGHT = "Quý khách vui lòng chọn Thời gian bay.";
		public static final String OVER_NUMBER_OF_ALLOWED_PEOPLE = "Tổng số hành khách trong 1 chuyến bay không được quá 9 người. Quý khách vui lòng kiểm tra lại.";
		public static final String INVALID_NUMBER_OF_ALLOWED_PEOPLE = "Trường hợp đặt vé có số lượng người lớn và trẻ em lớn hơn 7, hệ thống chỉ trả về danh sách chuyến bay của hãng VietjetAir, Jetstar và Bamboo (nếu có). ĐT hỗ trợ: 1900555520.";
		public static final String INVALID_NUMBER_OF_BABIES = "Số lượng hành khách em bé phải nhỏ hơn hoặc bằng số lượng hành khách người lớn";
		public static final String INVALID_NUMBER_OF_CHILDREN = "1 hành khách người lớn chỉ được phép đi kèm với 1 hành khách trẻ em và 1 hành khách em bé.";
	}

	public static class validInput {
		public static final String ADULT_NAME = "NGUOI LON";
		public static final String ADULT_NAME_1 = "Nguyen van an";
		public static final String ADULT_EMAIL = "abcd@gmail.com";
		public static final String ADULT_PHONE = "0901234567";
		public static final String CONTENT = "DAT CHUYEN BAY DI TP HO CHI MINH";
		public static final String CHILD_NAME = "NGUYEN NGOC ANH";
		public static final String BABY_NAME = "NGUYEN NGOC LAN";
		public static final String HANOI_PLACE = "Hà Nội";
		public static final String HANOI_CODE = "HAN";

		public static final String DN_PLACE = "Đà Nẵng";
		public static final String DN_CODE = "DAD";

		public static final String HCM_PLACE = "TP Hồ Chí Minh";
		public static final String HCM_CODE = "SGN";
		public static final String INTERNATIONAL_PLACE = "Singapore";
		public static final String INTERNATIONAL_CODE = "SIN";

	}
	
	public static class Air_Text {
		
		public static final String AIR_TICKET_TEXT = "Đặt vé máy bay";
		public static final String DOMESTIC_AIR_TICKET_TEXT = "Đặt vé máy bay Nội địa";
		public static final String INTERNATIONAL_AIR_TICKET_TEXT = "Đặt vé máy bay Quốc tế";
		public static final String AIR_TICKET_PAY_TEXT = "Thanh toán vé máy bay";
		public static final String PAY_INFO_TEXT = "Thông tin thanh toán";
		public static final String ONE_WAY_TEXT = "Một chiều";
		public static final String TWO_WAYS_TEXT = "Khứ hồi";
		public static final String DEPARTURE_WAY_TEXT = "CHIỀU ĐI";
		public static final String ARRIVAL_WAY_TEXT = "CHIỀU VỀ";
		public static final String DEPARTURE_PLACE_TEXT = "Khởi hành";
		public static final String ARRIVAL_PLACE_TEXT = "Điểm đến";
		public static final String DEPARTURE_DATE_TEXT = "Ngày đi";
		public static final String CONTACT_INFO_TEXT = "THÔNG TIN LIÊN HỆ";
		public static final String FLIGHT_CONFIRM_TEXT = "Xác nhận chuyến bay";
		public static final String JOURNEY_TEXT = "HÀNH TRÌNH";
		public static final String TOTAL_MONEY_TEXT = "Tổng tiền";
		public static final String TOTAL_PAY_TEXT = "Tổng tiền thanh toán";
		public static final String FLIGHT_WAY_TEXT = "Chiều bay";
		public static final String FLIGHT_CODE_TEXT = "Số hiệu chuyến bay";
		public static final String PAY_ID_TEXT = "Mã thanh toán";
		public static final String FLIGHT_TIME_TEXT = "Thời gian bay";
		public static final String FROM_ACCOUNT_TEXT = "Từ tài khoản";
		public static final String MONEY_AMOUNT_TEXT = "Số tiền";
		public static final String FEE_VALUE_TEXT = "Phí giao dịch";
		public static final String TRANSACTION_ID_TEXT = "Mã giao dịch";
		public static final String SURPLUS_TEXT = "Tài khoản nguồn";
		
		public static final String HAN_PLACE = "Hà Nội";
		public static final String SGN_PLACE = "TP Hồ Chí Minh";
		public static final String SIN_PLACE = "Singapore";
		public static final String SYD_PLACE = "Sydney";
		
		public static final String SUGGEST_FLIGHT_CODE_BL = "BL";
		public static final String SUGGEST_FLIGHT_CODE_VN = "VN";
		
		public static final String SMS_OTP_TEXT = "SMS OTP";
		public static final String PASSWORD_TEXT = "Mật khẩu đăng nhập";
		public static final String SMART_OTP_TEXT = "VCB - Smart OTP";
		
		public static final String BUTTON_AGREE_TEXT = "Đồng ý";
		public static final String BUTTON_CONFIRM_TEXT = "Xác nhận";
		public static final String BUTTON_CONTINUE_TEXT = "Tiếp tục";
		public static final String BUTTON_PAY_TEXT = "Thanh toán";
		public static final String BUTTON_ORDER_TICKET_TEXT = "Đặt vé";
		public static final String BUTTON_PAY_LATER_TEXT = "Thanh toán sau";
		public static final String BUTTON_SEARCH_FLIGHT_TEXT = "Tìm chuyến bay";
		
		public static final String PAY_DOMESTIC_AIR_TICKET = "Thanh toán vé máy bay nội địa";
		public static final String PAY_INTERNATIONNAL_TICKET = "Thanh toán vé máy bay quốc tế";
	}
	
	public static class Limit_Data {
		
	}
		
	
}
