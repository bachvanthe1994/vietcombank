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
		public static final List<String> LIST_VIETTEL_MOBILE = Arrays.asList("0988781905", "0989735001", "0980080904",
				"0860080906", "0980080905", "0860080907", "0980080906", "0860080908", "0980080999", "0860080905");
		public static final List<String> LIST_VINAPHONE_MOBILE = Arrays.asList("0835482310", "0836520145", "0836502014",
				"0830123651", "0835412014", "0838540123", "0836523014", "0836013234", "0830120365", "0830123012");
		public static final List<String> LIST_MOBIFONE_MOBILE = Arrays.asList("0908893194", "0901234566", "0901234567",
				"0901234563", "0901234564", "0901234565", "0902247838", "0904383266", "0904900001", "0904900002");

	}
}
