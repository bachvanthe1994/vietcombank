package vietcombank_test_data;

import java.util.Arrays;
import java.util.List;

public class Electric_Bills_Data {

	public class VALIDATE {

		public static final String ELECTIC_BILL_TITLE = "Hóa đơn tiền điện";
		public static final String VERIFY_INFO_TITLE_HEAD = "Quý khách vui lòng kiểm tra thông tin giao dịch đã khởi tạo";
		public static final String ELECTRIC_BILL_MESSAGE = "Giao dịch không thành công do hóa đơn không còn nợ cước. Quý khách vui lòng kiểm tra lại.";

	}

	public static class DATA {

		public static final String SOURCE_ACCOUNT = "0011002929257";
		public static final String EVN_HCM = "EVN Hồ Chí Minh";
		public static final String EVN_HN = "EVN Hà Nội";
		public static final String EVN_MIEN_BAC = "EVN Miền Bắc";
		public static final String EVN_MIEN_TRUNG = "EVN Miền Trung";
		public static final String EVN_MIEN_NAM = "EVN Miền Nam";
		public static final List<String> LIST_CUSTOMER_ID = Arrays.asList("0988781905", "0989735001", "0980080904",
				"0860080906", "0980080905", "0860080907", "0980080906", "0860080908", "0980080999", "0860080905");
	}

}
