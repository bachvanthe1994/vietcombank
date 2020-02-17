package vietcombank_test_data;

import java.util.Arrays;
import java.util.List;

public class StatusTransfer_Data {

    public static class Message {
	public static final String HOME_MESSAGE = "Lưu ý: Tra cứu trạng thái lệnh giao dịch chuyển tiền định kỳ hoặc ngày tương lai";
	public static final List<String> STATUS_TRANSFER = Arrays.asList("Chuyển tiền định kỳ", "Chuyển tiền ngày tương lai");
	public static final String FIND_CONFIRM = "Quý khách không có lệnh giao dịch nào trong khoảng thời gian này. Vui lòng kiểm tra lại.";
    }

    public static class TextVaidate {
	public static final String TITLE = "Trạng thái lệnh chuyển tiền";
	public static final String TITLE_COMBO = "Hình thức chuyển";
    }
}
