package vietcombank_test_data;

public class TransferMoneyInVCB_Data {
	
	public static class InputData {
		public static final String[] OPTION_TRANSFER = { "Chuyển tiền ngay","Chuyển tiền ngay","Chuyển tiền ngày tương lai"};
		public static final String SOURCE = "0011000000645";
		public static final String RECEIVE_ACCOUNT = "0011000000779";
		public static final String RECEIVE_NAME = "NGO TRI NAM";
		public static final String[] COST = { "Người chuyển trả","Người nhận trả"};
		public static final String MONEY = "100000";
		public static final String NOTE = "Test";
		public static final String TRANSFER_FEE = "3,300";
		
	}
	public static class Output {
		public static final String TRANSFER_SUCESS_MESSAGE = "CHUYỂN KHOẢN THÀNH CÔNG";
	}
}
