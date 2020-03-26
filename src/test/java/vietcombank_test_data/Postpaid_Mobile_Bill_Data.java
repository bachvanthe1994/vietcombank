package vietcombank_test_data;

import java.util.Arrays;
import java.util.List;

public class Postpaid_Mobile_Bill_Data {

	public class VALIDATE {

		public static final String POSTPAID_MOBILE_TITLE = "Cước di động trả sau";
		public static final String VERIFY_INFO_TITLE_HEAD = "Quý khách vui lòng kiểm tra thông tin giao dịch đã khởi tạo";
		public static final String POSTPAID_MOBILE_MESSAGE = "Giao dịch không thành công do hóa đơn không còn nợ cước. Quý khách vui lòng kiểm tra lại.";

	}

	public static class DATA {

		public static final String SOURCE_ACCOUNT = "0011002929257";
		public static final String VIETTEL_SUPPLIER = "Viettel";
		public static final String VINAPHONE_SUPPLIER = "Vinaphone";
		public static final String MOBIFONE_SUPPLIER = "Mobifone";
		public static final List<String> LIST_VIETTEL_MOBILE = Arrays.asList("0989735009", "0989735010", "0989735011",
				"0989735012", "0989735013", "0989735014", "0989735015", "0989735016", "0989735017", "0989735018");
		public static final List<String> LIST_VINAPHONE_MOBILE = Arrays.asList("0835482310", "0836520145", "0836502014",
				"0830123651", "0835412014", "0838540123", "0836523014", "0836013234", "0830120365", "0830123012");
		public static final List<String> LIST_MOBIFONE_MOBILE = Arrays.asList("0904900002", "0904383266", "0904900001",
				"0904329876", "0904333562", "0904567895", "0931265456", "0905235625", "0904900001", "0904900061");

	}
}
