package vnpay.vietcombank.airticket.data;

public class DomesticAirTicketBooking_Data {

	public static class output {
		// 2 cot, 22 dong
		public static final String PLACE[][] = { { "HAN", "Hà Nội" }, { "SGN", "TP Hồ Chí Minh" }, { "DAD", "Đà Nẵng" }, { "BMV", "Buôn Ma Thuột" }, { "CAH", "Cà Mau" }, { "VCA", "Cần Thơ" }, { "VCL", "Chu Lai" }, { "VCS", "Côn Đảo" }, { "DLI", "Đà Lạt" }, { "DIN", "Điện Biên" }, { "VDH", "Đồng Hới" }, { "HPH", "Hải Phòng" }, { "HUI", "Huế" }, { "CXR", "Nha Trang" }, { "PQC", "Phú Quốc" },
				{ "PXU", "Pleiku" }, { "UIH", "Quy Nhơn" }, { "VKG", "Rạch Giá" }, { "THD", "Thanh Hóa" }, { "TBB", "Tuy Hòa" }, { "VDO", "Vân đồn" }, { "VII", "Vinh" } };

	}

	public static class Message {
		public static final String EMPTY_DEPARTURE_POINT = "Quý khách vui lòng chọn Điểm khởi hành";
		public static final String EMPTY_ARRIVE_POINT = "Quý khách vui lòng chọn Điểm đến.";
		public static final String EMPTY_TIME_FLIGHT = "Quý khách vui lòng chọn Thời gian bay.";
		public static final String OVER_NUMBER_OF_ALLOWED_PEOPLE = "Tổng số hành khách trong 1 chuyến bay không được quá 9 người. Quý khách vui lòng kiểm tra lại.";
		public static final String INVALID_NUMBER_OF_ALLOWED_PEOPLE = "Trường hợp đặt vé có số lượng người lớn và trẻ em lớn hơn 7, hệ thống chỉ trả về danh sách chuyến bay của hãng VietjetAir, Jetstar và Bamboo (nếu có). ĐT hỗ trợ: 1900555520.";
		public static final String INVALID_NUMBER_OF_BABIES = "Số lượng hành khách em bé phải nhỏ hơn hoặc bằng số lượng hành khách người lớn";
		public static final String INVALID_NUMBER_OF_CHILDREN = "1 hành khách người lớn chỉ được phép đi kèm với 2 hành khách trẻ em";
	}

}
