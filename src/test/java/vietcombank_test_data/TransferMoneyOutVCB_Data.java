package vietcombank_test_data;


public class TransferMoneyOutVCB_Data {

	public static class InputDataAmountOutVCB {
		public static final String LOWER_MIN_TRANSFER = "9999";
		public static final String HIGHER_MAX_TRANSFER = "100000001";
		public static final String MIN_TRANSFER = "10000";
		public static final String MAX_TRANSFER = "100000000";
		public static final String MAX_TRANSFER_OF_DAY = "100000001";

		
	}
	public static class MESSEGE_ERROR {
		public static final String LOWER_MIN_AMOUNT_A_TRAN = "Chuyển tiền không thành công. Số tiền giao dịch nhỏ hơn hạn mức 10,000 VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		public static final String HIGHER_MAX_AMOUNT_A_TRAN = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000,000 VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		public static final String HIGHER_MAX_AMOUNT_A_TRAN_A_DAY = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000,001 VND/1 ngày, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		public static final String HIGHER_MAX_AMOUNT_A_TRAN_GROUP = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000,001 VND/1 ngày của nhóm dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		public static final String HIGHER_MAX_AMOUNT_A_TRAN_PACKAGE = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000,001 VND/1 ngày của gói dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";

		
	}
	public static class MESSEGE_SUCCESS {
		public static final String SUCCESS_TRANSFER_MONEY = "CHUYỂN KHOẢN THÀNH CÔNG";
		
		
	}

	
}
