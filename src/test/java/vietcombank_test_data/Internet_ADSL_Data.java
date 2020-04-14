package vietcombank_test_data;

import java.util.Arrays;
import java.util.List;

public class Internet_ADSL_Data {

	public static class Valid_Account {
		public static final String ACCOUNT1 = "0019961175";
		public static final String NETWORK[] = { "Viettel", "FPT Telecom","Viettel ADSL" };
		public static final List<String> CODEFPT = Arrays.asList("adsl_ftp7", "adsl_ftp8", "adsl_ftp9", "adsl_ftp10", "bidvadsl_ftp12", "bidvadsl_ftp13", "bidvadsl_ftp14", "bidvadsl_ftp15", "bidvadsl_ftp16", "bidvadsl_ftp17");
		public static final List<String> CODEVIETTEL = Arrays.asList("adslvtgdb79", "adslvtgdb83", "adslvtgdb84", "adslvtgdb85", "adslvtgdb86", "adslvtgdb87", "adslvtgdb88", "adslvtgdb89", "adslvtgdb6", "adslvtgdb6", "vpbadsl7", "vpbadsl8", "vpbadsl9", "bvbadsl1", "bvbadsl2", "bvbadsl3", "bvbadsl4", "bvbadsl5", "adslvtgdb63", "adslvtgdb64", "adslvtgdb65", "adslvtgdb66", "adslvtgdb67",
				"adslvtgdb68", "adslvtgdb69", "adslvtgdb73", "adslvtgdb74", "adslvtgdb75", "adslvtgdb76", "adslvtgdb77", "adslvtgdb78");

	}

	public class Invalid_Account {

	}

	public static class Limit_Internet_ADSL_Data {
		public static final String MESSEGER_ERROR_LOWER_LIMIT_MIN_A_TRAN = "Thanh toán hóa đơn không thành công. Số tiền giao dịch nhỏ hơn hạn mức 100,000 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";
		public static final String MESSEGER_ERROR_HIHGER_LIMIT_MAX_A_TRAN = "Thanh toán hóa đơn không thành công. Số tiền giao dịch lớn hơn hạn mức 300,000 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";
		public static final String MESSEGER_ERROR_HIHGER_LIMIT_MAX_A_DAY = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 320,000 VND/1 ngày, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";
		public static final String MESSEGER_ERROR_HIHGER_LIMIT_MAX_GROUP = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 90,000 VND/1 ngày của nhóm dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";
		public static final String MESSEGER_ERROR_HIHGER_LIMIT_MAX_PACKAGE = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 90,000 VND/1 ngày của gói dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";

		public static final String CODE_CUSTOMER[] = { "adslvtgdb83", "adslvtgdb84", "adslvtgdb85", "adslvtgdb86", "adslvtgdb87", "adslvtgdb88", "adslvtgdb89", "adslvtgdb6", "adslvtgdb6", "vpbadsl7", "vpbadsl8", "vpbadsl9", "bvbadsl1", "bvbadsl2", "bvbadsl3", "bvbadsl4", "bvbadsl5", "adslvtgdb63", "adslvtgdb64", "adslvtgdb65", "adslvtgdb66", "adslvtgdb67", "adslvtgdb68",
				"adslvtgdb69", "adslvtgdb73", "adslvtgdb74", "adslvtgdb75", "adslvtgdb76", "adslvtgdb77", "adslvtgdb78" };

	}
}