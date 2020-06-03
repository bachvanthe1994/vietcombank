package vietcombank_test_data;

public class TransferMoneyStatus_Data {

	public static class Output {
		public static final String NOTE = "Lưu ý: Tra cứu trạng thái lệnh giao dịch chuyển tiền định kỳ hoặc chuyển tiền ngày tương lai";
		public static final String WAITING_STATUS = "Chờ xử lý";
		public static final String CANCEL_STATUS = "Đã hủy";
	}
	
	public static class Input {
		
		public static final String[] OPTION_TRANSFER = { "Chuyển tiền ngày giá trị hiện tại", "Chuyển tiền định kỳ", "Chuyển tiền ngày tương lai" };
	}
	
	public static class Text {
		
		public static final String SEARCH = "Tìm kiếm";
		public static final String CREAT_DATE = "Ngày lập lệnh";
		public static final String NAME_RECEIPTER = "Tên người nhận";
		public static final String AMOUNT = "Số tiền";
		public static final String START_DATE_TEXT = "Ngày bắt đầu";
		public static final String END_DATE_TEXT = "Ngày kết thúc";
		public static final String DETAIL_TRANSFER = "Chi tiết lệnh chuyển tiền";
		public static final String ACCEPT = "Đồng ý";
		public static final String FREQUENCY_TEXT = "Tần suất";
		public static final String TRANSFER_PER_TIMES_TEXT = "/ lần";
		public static final String SUCCESS = "Thành công";
		public static final String UNSUCCESS_MESSAGE = "Không thành công";
		public static final String TRANSFER_MONEY_STATUS_TEXT = "Trạng thái chuyển tiền";
		public static final String CLOSE = "Đóng";
	}

}
