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
	}

}
