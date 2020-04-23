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
		public static final List<String> LIST_VIETTEL_MOBILE = Arrays.asList("0980081901", "0980081902", "0980081903", "0980081904", "0980081905", "0980081906", "0980081907", "0980081908", "0980081909", "0988781901");
		public static final List<String> LIST_VINAPHONE_MOBILE = Arrays.asList("0940000001", "0940000002", "0940000003", "0940000004", "0940000005", "0940000006", "0940000011", "0940000008", "0940000009", "0940000012");
		public static final List<String> LIST_MOBIFONE_MOBILE = Arrays.asList("0901234566", "0901234567", "0901234563", "0901234564", "0901234565", "0902247838", "0901234561", "0901234562", "0933584784", "0904234561");

	}
}
