package vietcombank_test_data;

import java.util.Arrays;
import java.util.List;

public class TransferMoneyInVCB_Data {

	public static class InputDataInVCB {
		public static final String[] OPTION_TRANSFER = { "Chuyển tiền ngày giá trị hiện tại", "Chuyển tiền định kỳ", "Chuyển tiền tương lai trong VCB" };

		public static final String[] PAYMENT_OPTIONS = { "Mật khẩu đăng nhập", "SMS OTP", "VCB - Smart OTP" };

		public static final String DIFFERENT_OWNER_NAME = "NGUY H NH";

		public static final String RECEIVER_ACCOUNT_3 = "0451001458259";
		public static final String[] COST = { "Người chuyển trả", "Người nhận trả" };

		public static final String AMOUNT_OF_EUR_OR_USD_TRANSFER = "2";
		public static final String VND_MONEY = "20000";

		public static final List<String> SUGGESTED_EUR_MONEY = Arrays.asList("20 EUR ~ 479,960 VND", "200 EUR ~ 4,799,600 VND");
		public static final List<String> SUGGESTED_VND_MONEY = Arrays.asList("2,000 VND", "20,000 VND", "200,000 VND");

		public static final String NOTE = "Test";


		public static final String EUR_PAYMENT_BY_PASSWORD_FEE = "0.05";
		public static final String EUR_PAYMENT_BY_OTP_FEE = "0.09";


		public static final String USD_EXCHANGE_RATE = "1 USD ~ 23,087.00 VND";
		public static final String EUR_EXCHANGE_RATE = "1 EUR ~ 23,997.76 VND";
		public static final String EUR_CHANGED_MONEY = "47996";

		public static final String NUMBER_TRANSACTION = "Số lần giao dịch";
		public static final String NUMBER_DAY_FREQUENCY = "5";
		public static final String NUMBER_WEEK_FREQUENCY = "2";
		public static final String NUMBER_MONTH_FREQUENCY = "2";

		public static final String MONEY_OVER_PER_TRANSACTION = "100000001";
		public static final String MONEY_OVER_PER_DAY_OF_GROUP = "8000001";

		public static final String TRANSFER_TYPE_VIETCOM_BANL = "Chuyển tiền trong Vietcombank";
		public static final String TRANSFER_FASTER = "Chuyển tiền nhanh 24/7 qua thẻ";

	}

	public static class Output {
		public static final String SUCESSFULL_CREATED_ORDER = "LẬP LỆNH THÀNH CÔNG";
		public static final String TRANSFER_SUCESS_MESSAGE = "CHUYỂN KHOẢN THÀNH CÔNG";
		public static final String ERRROR_MESSAGE_FOR_ACCOUNT_LESS_THAN_13_CHARACTER = "Tài khoản đích không hợp lệ. Quý khách vui lòng kiểm tra lại.";
		public static final String INVALID_RECEIVE_ACCOUNT = "Tài khoản hưởng không hợp lệ. Quý khách vui lòng kiểm tra lại.";
		public static final String INVALID_RECEIVE_ACCOUNT_EUR = "Tài khoản hưởng không hợp lệ, Quý khách vui lòng kiểm tra lại.";
		public static final String ERRROR_MESSAGE_FOR_ACCOUNT_LESS_THAN_10_CHARACTER = "Tài khoản đích không đủ 10 ký tự, Quý khách vui lòng kiểm tra lại";
		public static final String TRANSACTION_LIMIT_TEXT = "Tài khoản đích không đủ 10 ký tự, Quý khách vui lòng kiểm tra lại";
		public static final String EMPTY_RECEIVE_ACCOUNT_MESSAGE = "Quý khách vui lòng nhập tài khoản nhận";
		public static final String EMPTY_MONEY_MESSAGE = "Quý khách vui lòng nhập số tiền chuyển khoản";
		public static final String EMPTY_TRANSFER_NOTE_MESSAGE = "Quý khách vui lòng nhập nội dung giao dịch";
		public static final String DUPLICATED_ACCOUNT_MESSAGE = "Tài khoản nguồn và tài khoản đích không được giống nhau, Quý khách vui lòng kiểm tra lại";
		public static final String SAME_ACCOUNT_OWNER_MESSAGE = "Tài khoản nguồn không hợp lệ do là tài khoản đồng chủ sở hữu.";
		public static final String INEXISTED_ACCOUNT_OWNER_MESSAGE = "Tài khoản hưởng không hợp lệ, Quý khách vui lòng kiểm tra lại.";
		public static final String INVALID_DESTINATION_ACCOUNT_MESSAGE = "Tài khoản đích không hợp lệ. Quý khách vui lòng kiểm tra lại.";
		public static final String INVALID_DESTINATION_ACCOUNT_MESSAGE_2 = "Tài khoản hưởng không hợp lệ, Quý khách vui lòng kiểm tra lại.";
		public static final String NOT_ENOUGH_MONEY = "Giao dịch không thành công do tài khoản của Quý khách không đủ số dư. Vui lòng kiểm tra lại.";
		public static final String ERROR_MESSAGE_WITH_MIN_LIMIT_TRANSFER_MONEY = "Chuyển tiền không thành công. Số tiền giao dịch nhỏ hơn hạn mức 10,000 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";
		public static final String ERROR_MESSAGE_WITH_MAX_LIMIT_TRANSFER_MONEY = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 1,000,000 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
		public static final String ERROR_MESSAGE_WITH_MIN_LIMIT_FUTURE_TRANSFER_MONEY = "Chuyển tiền không thành công. Số tiền giao dịch nhỏ hơn hạn mức 10,000 VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		public static final String ERROR_MESSAGE_WITH_MAX_LIMIT_FUTURE_TRANSFER_MONEY = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000,000 VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		public static final String ERROR_MESSAGE_FOR_EMPTY_OTP = "Quý khách vui lòng nhập mã OTP";
		public static final String ERROR_MESSAGE_FOR_EMPTY_PASSWORD = "Quý khách vui lòng nhập mật khẩu đăng nhập ứng dụng";
		public static final String ERROR_MESSAGE_FOR_OTP_LESS_THAN_6 = "OTP phải đủ 6 ký tự, Quý khách vui lòng kiểm tra lại";
		public static final String ERROR_MESSAGE_FOR_WRONG_OTP = "OTP không chính xác, Quý khách vui lòng kiểm tra lại.";
		public static final String ERROR_MESSAGE_FOR_WRONG_OTP_N_TIMES = "Nhập sai OTP quá 4 lần. Quý khách vui lòng thực hiện giao dịch mới.";
		public static final String ERROR_MESSAGE_FOR_WRONG_PASSWORD = "Mật khẩu không chính xác. Quý khách lưu ý, dịch vụ VCB-Mobile B@nking sẽ bị tạm khóa và dịch vụ VCBPAY sẽ bị hủy nếu Quý khách nhập sai mật khẩu quá 5 lần.";
		public static final String MONEY_INPUT_OVER_PER_A_TRANSACTION_MESSAGE = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000,000 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
		public static final String MONEY_INPUT_OVER_PER_A_DAY_OF_GROUP_MESSAGE = "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức 8,000,000 VND/1 ngày của nhóm dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		public static final String TRANSACTION_VALIDATION = "Xác thực giao dịch";
		public static final String OTP_NOTIFICATION_SENDED = "Quý khách vui lòng nhập OTP đã được gửi về số điện thoại " + LogIn_Data.Login_Account.PHONE_HIDDEN;
		public static final String PASSWORD_NOTIFICATION = "Vui lòng nhập mật khẩu đăng nhập ứng dụng của Quý khách để xác thực giao dịch";
		public static final String OTP_EMPTY = "Quý khách vui lòng nhập mã OTP";
		public static final String PASSWORD_EMPTY = "Quý khách vui lòng nhập mật khẩu đăng nhập ứng dụng";
		public static final String OTP_LESS_THAN_6_CHARACTER = "OTP phải đủ 6 ký tự, Quý khách vui lòng kiểm tra lại";
		public static final String PASSWORD_LESS_THAN_8_CHARACTER = "Mật khẩu không chính xác. Quý khách lưu ý, dịch vụ VCB-Mobile B@nking sẽ bị tạm khóa và dịch vụ VCBPAY sẽ bị hủy nếu Quý khách nhập sai mật khẩu quá " + LogIn_Data.Login_Account.PASSWORD_INVALID_TIMES + " lần.";
		public static final String OTP_INVALID = "OTP không chính xác, Quý khách vui lòng kiểm tra lại.";
		public static final String PASSWORD_INVALID = "Mật khẩu không chính xác. Quý khách lưu ý, dịch vụ VCB-Mobile B@nking sẽ bị tạm khóa và dịch vụ VCBPAY sẽ bị hủy nếu Quý khách nhập sai mật khẩu quá " + LogIn_Data.Login_Account.PASSWORD_INVALID_TIMES + " lần.";
		public static final String OTP_INVALID_N_TIMES = "Nhập sai OTP quá " + LogIn_Data.Login_Account.OTP_INVALID_TIMES + " lần. Quý khách vui lòng thực hiện giao dịch khác.";
		public static final String PASSWORD_INVALID_N_TIMES = "";
		public static final String OTP_EXPIRE = "Mã xác thực đã hết hiệu lực. Quý khách vui lòng khởi tạo mã xác thực mới.";
		public static final String ORDER_TIME = "Giao dịch sẽ được tự động khởi tạo vào ";
		public static final String INFO_VALIDATION = "Xác nhận thông tin";

		public static final String MESSEGE_ERROR_lOWER_MIN_LIMIT = "Chuyển tiền không thành công. Số tiền giao dịch nhỏ hơn hạn mức ";
		public static final String MESSEGE_ERROR_HIGHER_MAX_LIMIT = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức ";
		public static final String MESSEGE_ERROR_HIGHER_MAX_GROUP_LIMIT = "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức ";
		
		
		public static final String LIMIT_TRANSACTION_MESSAGE = " VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
		public static final String LIMIT_DAY_MESSAGE = " VND/1 ngày, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		
		public static final String DETAIL_A_GROUP_MESSAGE = " VND/1 ngày của nhóm dịch vụ chuyển tiền. Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
		public static final String DETAIL_A_PACKAGE_MESSAGE = " VND/1 ngày của gói dịch vụ chuyển tiền. Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
		
		public static final String MESSEGE_ERROR_HIGHER_LIMIT_DAY = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 400,000,000 VND/1 ngày, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		public static final String MESSEGE_ERROR_HIGHER_LIMIT_GROUP_SERVICES = "Giao dịch không thành công. Số tiền giao dịch lớn hơn hạn mức 99,999,999 VND/1 ngày của nhóm dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";

		public static final String MESSEGE_ERROR_HIGHER_LIMIT_GROUP_SERVICES_NOW = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 99,999,999 VND/1 ngày của nhóm dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";

		public static final String MESSEGE_ERROR_HIGHER_LIMIT_PACKAGE_SERVICES = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 99,999,999 VND/1 ngày của gói dịch vụ, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		public static final String MESSEGE_ERROR_HIGHER_LIMIT_PACKAGE_SERVICES_NOW = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 99,999,999 VND/1 ngày của gói dịch vụ, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";

		public static final String MESSEGE_ERROR_lOWER_MIN_LIMIT_NOW = "Chuyển tiền không thành công. Số tiền giao dịch nhỏ hơn hạn mức 10,000 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";
		public static final String MESSEGE_ERROR_HIGHER_LIMIT_DAY_NOW = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 400,000,000 VND/1 ngày, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.”";

	}

	public static class InputDataInFutureForOTP {
		public static final String TRANSFER_AMOUNT = "20000";
		public static final String AMOUNT_OF_EUR_OR_USD_TRANSFER = "1";
		public static final String USD_SMS_OTP_FEE = "0.1";
		public static final String EUR_SMS_OTP_FEE = "0.09";

		public static final String LOWER_TRANSFER_AMOUNT = "9999";
		public static final String HIGHER_TRANSFER_AMOUNT = "100000001";
		public static final String MAX_TRANFER_AMOUNT = "100000000";
		public static final String MIN_TRANFER_AMOUNT = "10000";
		public static final String TOTAL_LIMIT_AMOUNT = "400000000";
		public static final String TOTAL_GROUP_SERVICE = "99999999";

	}

	public static class InputDataInFutureForPassword {
		public static final String TRANSFER_AMOUNT = "20000";
		public static final String AMOUNT_OF_EUR_OR_USD_TRANSFER = "1";
		public static final String USD_PASSWORD_FEE = "0.02";
		public static final String EUR_PASSWORD_FEE = "0.02";

	}

	public static class InvalidInputData {

		public static final String INVALID_ACCOUNT_15_CHARACTERS = "445555555555555";
		public static final String INVALID_ACCOUNT_LESS_THAN_10_CHARACTERS = "001100000";
		public static final String INVALID_ACCOUNT_LESS_THAN_13_CHARACTERS = "00110000000";
		public static final String INVALID_ACCOUNT_NOT_EXISTED_IN_SYSTEM = "0011111111111";
		public static final String INVALID_ACCOUNT_NOT_EXISTED_IN_VCB = "01825909301";
		public static final String TEXT_AND_SPECIAL_CHARACTERS = "Abc!@#$%%";
		public static final String VIETNAMESE_LANGUAGE = "Chuyển khoản nội bộ";
		public static final String TEXT_WITH_MORE_THAN_140_CHARAC = "Chuyen khoan Chuyen khoan Chuyen khoan Chuyen khoan Chuyen khoan Chuyen khoan Chuyen khoan Chuyen khoan Chuyen khoan Chuyen khoan Chuyen khoan Chuyen khoan ";
		public static final String CHANGED_VIETNAMESE_LANGUAGE = "Chuyen khoan noi bo";
		public static final String TEXT_NUMBER_AND_SPECIAL_CHARACTERS = "Abc!@ _ - \\ . , & * # ! $ + : ; ? / | % ( ) =1234";
		public static final String INVALID_MONEY = "12345678901";
		public static final String INVALID_DECIMAL_MONEY = "1234567890.1234";
		public static final String INVALID_MIN_TRANSFER_AMOUNT = "1";
		public static final String INVALID_MAX_TRANSFER_AMOUNT = "200000000";
		public static final String OTP_WITH_LESS_THAN_6_CHARACTERS = "12345";
		public static final String WRONG_OTP = "111111";
		public static final String MORE_THAN_21_CHARACTERS = "123456789012345678901234";

	}

	public static class InputData_MoneyRecurrent {

		public static final String DAY_TEXT = "Ngày";
		public static final String MONTH_TEXT = "Tháng";
		public static final String VND_AMOUNT = "500000";
		public static final String USD_EUR_AMOUNT = "10";
		public static final String RECEIVED_PAY = "Người nhận trả";
		public static final String TRANSFER_PAY = "Người chuyển trả";
		public static final String CONTENT = "test";

	}

	public static class InputText_MoneyRecurrent {

		public static final String BE_TRANSFER_TEXT = "Chuyển khoản";
		public static final String BE_TRANSFER_RECURRENT_TEXT = "Chuyển khoản định kỳ";
		public static final String TRANSFER_MONEY_STATUS_TEXT = "Trạng thái chuyển tiền";
		public static final String BILLS_PAYMENT_TEXT = "Nạp tiền/ Thanh toán hoá đơn";
		public static final String TRANSFER_FREQUENCY_TEXT = "Tần suất chuyển";
		public static final String TRANSACTION_TIMES_TEXT = "Số lần giao dịch";
		public static final String TRANSFER_PER_TIMES_TEXT = "/ lần";
		public static final String FREQUENCY_TEXT = "Tần suất";
		public static final String FEE_PER_TIMES_TEXT = "Số tiền phí/lần";
		public static final String SAVE_RECEIVED_ACCOUNT_TEXT = "Lưu thụ hưởng";
		public static final String SAVE_CONTACT_SUCCESS_MESSAGE = "Lưu thành công";
	}

	public static class TittleData {
		public static final String SOURCE_ACCOUNT = "Tài khoản nguồn";
		public static final String SURPLUS = "Số dư khả dụng";
		public static final String INPUT_ACCOUNT_BENEFICI = "Nhập/ chọn tài khoản nhận VND";
		public static final String TRANSFER_INFO = "Thông tin giao dịch";
		public static final String AMOUNT = "Số tiền";
		public static final String CONTINUE_BTN = "Tiếp tục";
		public static final String FORM_TRANSFER = "Hình thức chuyển tiền";
		public static final String SOURCE_ACCOUNT_VND = "Tài khoản đích/ VND";
		public static final String EFFECTIVE_DATE = "Ngày hiệu lực";
		public static final String CONTENT = "Nội dung";
		public static final String METHOD_VALIDATE = "Chọn phương thức xác thực";
		public static final String SMS_OTP = "SMS OTP";
		public static final String SMART_OTP = "VCB - Smart OTP";
		public static final String PASSWORD = "Mật khẩu đăng nhập";
		public static final String FEE_AMOUNT = "Số tiền phí";
		public static final String CODE_TRANSFER = "Mã giao dịch";
		public static final String NAME_BENEFICI = "Tên người thụ hưởng";
		public static final String ACCOUNT_BENEFICI = "Tài khoản thụ hưởng";
		public static final String NEW_TRANSFER = "Thực hiện giao dịch mới";
		public static final String CREAT_DATE = "Ngày lập lệnh";
		public static final String NAME_RECEIPTER = "Tên người nhận";
		public static final String NOTE = "Ghi chú";
		public static final String STATUS = "Trạng thái";
		public static final String DETAIL_TRANSFER = "Chi tiết lệnh chuyển tiền";
		public static final String SUCCESS = "Thành công";
		public static final String FEE_TRANSFER_SOURCE_ACCOUNT_PAY = "Phí giao dịch người chuyển trả";
		public static final String BENEFICI_PAY = "Người nhận trả";
		public static final String CLOSE = "Đóng";
		public static final String ACCEPT = "Đồng ý";
		public static final String SEARCH = "Tìm kiếm";
		public static final String TRANSFER_IN_VCBANK = "Chuyển tiền trong Vietcombank";
		public static final String FEE_TRANSFER = "Phí giao dịch";
		public static final String NAME_OF_BENEFICI = "Tên người hưởng";
		public static final String AMOUNT_TRANSFER = "Số tiền giao dịch";
		public static final String AMOUNT_SURPLUS = "Tài khoản ghi có";
		public static final String NUMBER_TRANSFER = "Số lệnh giao dịch";
		public static final String TIME_TRANSFER = "Thời gian giao dịch";
		public static final String ALL_TYPE_TRANSFER = "Tất cả các loại giao dịch";
		public static final String REPORT_TRANSFER = "Báo cáo giao dịch";
		public static final String DETAIL_TRANSACTION = "Chi tiết giao dịch";
		public static final String CONTENT_TRANSACTION = "Nội dung giao dịch";
		public static final String TYPE_TRANSACTION = "Loại giao dịch";
		public static final String STATUS_TRANSACTION = "Trạng thái chuyển tiền";
		public static final String AMOUNT_CHANGE = "Số tiền quy đổi";
		public static final String BILL_PAYMENT = "Hóa đơn thanh toán";
		public static final String DELETE_CONTACT = "Xóa danh bạ thành công";
		public static final String CONTACT_BENFICAL = "Danh bạ người hưởng";
		public static final String SETTING = "Cài đặt";
		public static final String REFERENCE_RATE = "Tỷ giá quy đổi tham khảo";
		public static final String TRANSFER_IN_BANK_SAME_OWNER = "Chuyển khoản nội bộ cùng chủ tài khoản";
		public static final String TRANSFER_IN_BANK_OTHER_OWNER = "Chuyển khoản nội bộ khác chủ tài khoản";
		public static final String TRANSFER_SAME_OWNER = "Chuyển khoản cùng chủ";
		public static final String PACKAGE_NAME = "TESTBUG";
		public static final String TITTLE_METHOD = "Method Otp";
		
		

	}
}
