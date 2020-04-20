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
		public static final List<String> LIST_VIETTEL_MOBILE = Arrays.asList("0982816517", "0983584784", "01656029647", "01666907017", "0989734999", "0989069072", "0986218998", "0978008205", "0977768955", "0979409732");
		public static final List<String> LIST_VINAPHONE_MOBILE = Arrays.asList("0940000001", "0940000002", "0940000003", "0940000004", "0940000005", "0940000006", "0940000011", "0940000008", "0940000009", "0940000012");
		public static final List<String> LIST_MOBIFONE_MOBILE = Arrays.asList("0934372168", "0939734997", "0939734999", "0908809805", "0900080991", "0900080992", "0900080993", "0900080901", "0901495868", "0904329876");

	}
}
