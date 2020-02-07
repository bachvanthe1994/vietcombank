package vnpay.vietcombank.vehicleTicketData;

public class VehicalData {
    public static class Login {
	public static final String LOCKED_PHONE = "0335059863";
	public static final String LOCKED_PASS = "1e9978d3";
	public static final String PHONE = "0335059863";
	public static final String PHONE_INVALID = "0335059863";
	public static final String PHONE_HIDDEN = PHONE.substring(0, 4) + "***" + PHONE.substring(PHONE.length() - 3);
	public static final int OTP_INVALID_TIMES = 3;
	public static final int PASSWORD_INVALID_TIMES = 5;
	public static final String WRONG_PASSWORD = "aaaa1111";
	public static final String NEW_PASSWORD = "aaaa1111";
	public static final String OTP = "123456";
	public static final String INVALID_PASSWORD = "123456a  @ABC";
	public static final String NULL = "";
	public static final String PASSWORD_GREATER_THAN_20 = "1ABC1111@11111111111111111";
	public static final String OTP_NUMBER_INVALID = "111111";
	public static final String PASSWORD_LABEL = "Mật khẩu đăng nhập";
    }

    public static class Data {
	public static final String FROMT = "Chọn điểm đi";
	public static final String TO = "Chọn điểm đến";
	public static final String DEPARTURE = "Hà Nội";
	public static final String DESTINATION = "Bắc Ninh";
	public static final String FROMT_INPUT = "Điểm đi";
	public static final String TO_INPUT = "Điểm đến";
    }
}
