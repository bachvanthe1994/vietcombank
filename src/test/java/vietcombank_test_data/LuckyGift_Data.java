package vietcombank_test_data;

public class LuckyGift_Data {

	public static class LuckyGift {
		public static final String[] TYPE_ACCEPT_OPTION = { "Số tài khoản", "Số điện thoại" };
		public static final String[] BANK_OPTION = { "Người nhận tại VCB", "Người nhận khác VCB" };
		public static final String WISHES_OPTION = "Gửi lời chúc Ngày phụ nữ hạnh phúc tới người đã đánh cắp trái tim tôi!";
		public static final String MONEY = "12000";
		public static final String[] ACCURACY = { "Mật khẩu đăng nhập", "SMS OTP" };

		public static final String MIN_MONEY_A_TRANSACTION = "100";
		public static final String[] COST = { "Phí giao dịch người chuyển trả", "Phí giao dịch người nhận trả" };
		public static final String[] COST_SUB = { "Người chuyển trả", "Người nhận trả" };
		public static final String BANK_DBA = "Ngân hàng DAB";
		public static final String PASS = "aaaa1111";
		public static final String OTP = "123456";
		public static final String TRANSFER_REPORT = "Báo cáo giao dịch";
		public static final String TITLE_TRANSFER = "Tất cả các loại giao dịch";

	}

	public static class TitleLuckyGift {
		public static final String TITLE = "Quà tặng";
		public static final String TITLE_GIFT = "QUÀ TẶNG";
		public static final String TITLE_CHOISE_ACCOUNT = "Số điện thoại/tài khoản nhận";
		public static final String TITLE_AMOUNT_MONEY = "Số tiền";
		public static final String TITLE_PHONE_NUMBER = "Số điện thoại";
		public static final String TITLE_WISHES = "Nhập/chọn lời chúc";
		public static final String USER_OUTVCB = "Người nhận khác VCB";
		public static final String TIME_TRANSFER = "Thời gian giao dịch";
		public static final String ACCOUNT_TRANSFER = "Tài khoản/thẻ trích nợ";
		public static final String BENEFICIARY_NAME = "Tên người hưởng";
		public static final String ACCOUNT_CREDITED = "Tài khoản ghi có";
		public static final String TRANSACTION_FEE = "Số tiền phí";
		public static final String TRANSACTION_DETAIL = "Chi tiết giao dịch";
		public static final String CONFIRM = "Khi chọn gửi quà may mắn cho nhiều người, Quý khách có thể chọn thiết lập số tiền giống nhau cho toàn bộ danh sách người nhận hoặc tự nhập cho từng người nhận riêng biệt.";
		public static final String NEXT = "Tiếp tục";
		public static final String INPUT_PASSWORD = "Nhập mật khẩu";
		public static final String NEW_TRANSFER = "Giao dịch mới";
		public static final String CLOSE = "Đóng";
		public static final String FIND = "Tìm kiếm";
	}
	
	public static class Limit_Money_Gift {
		public static final String LOWER_MIN_MONEY_A_TRANSACTION = "999";
		public static final String HIGHER_MAX_MONEY_A_TRANSACTION = "100000001";
		public static final String MAX_MONEY_A_TRANSACTION = "100000000";
		public static final String MIN_MONEY_A_TRANSACTION = "1";
	}
	
	public static class Messege_Limit {
		public static final String MESSEGE_ERROR_LOWER_MIN_TRAN = "Giao dịch không thành công. Số tiền giao dịch nhỏ hơn hạn mức 1,000 VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		public static final String MESSEGE_ERROR_HIGHER_MAX_TRAN = "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000,000 VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		public static final String MESSEGE_ERROR_HIGHER_MAX_GROUP = "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000,000 VND/1 ngày của nhóm dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		public static final String MESSEGE_ERROR_HIGHER_MAX_PACKAGE = "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000,000 VND/1 ngày của gói dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		public static final String MESSEGE_ERROR_HIGHER_MAX_DAY= "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000,001 VND/1 ngày, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";


	}

}
