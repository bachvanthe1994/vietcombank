package vietcombank_test_data;

import java.util.Arrays;
import java.util.List;

public class TransferMoneyInVCB_Data {

	public static class InputDataInVCB {
		public static final String[] OPTION_TRANSFER = { "Chuyển tiền ngày giá trị hiện tại", "Chuyển tiền định kỳ", "Chuyển tiền ngày tương lai" };
		public static final String[] PAYMENT_OPTIONS = { "Mật khẩu đăng nhập", "SMS OTP" };

		public static final String DIFFERENT_OWNER_NAME = "NGUY H NH";

		public static final String RECEIVER_NAME_ACCOUNT_2 = "NGUYEN NGOC TOAN";
		public static final String RECEIVER_NAME_ACCOUNT_1 = "NGUYEN NGOC TOAN-NGUYEN NGOC TOAN-NGUYEN";
		public static final String[] COST = { "Người chuyển trả", "Người nhận trả" };

		public static final String AMOUNT_OF_EUR_OR_USD_TRANSFER = "2";
		public static final String VND_MONEY = "20000";

		public static final List<String> SUGGESTED_EUR_MONEY = Arrays.asList("20 EUR ~ 479,960 VND", "200 EUR ~ 4,799,600 VND");
		public static final List<String> SUGGESTED_VND_MONEY = Arrays.asList("2,000 VND", "20,000 VND", "200,000 VND");

		public static final String NOTE = "Test";
		public static final String TRANSFER_OTP_FEE_TO_OTHER_ACCOUNT_OWNER = "1,100";
		public static final String TRANSFER_PASSWORD_FEE_TO_OTHER_ACCOUNT_OWNER = "2,200";

		public static final String PAYMENT_BY_PASSWORD_FEE = "1,100";
		public static final String PAYMENT_BY_OTP_FEE = "2,200";

		public static final String EUR_PAYMENT_BY_PASSWORD_FEE = "0.05";
		public static final String EUR_PAYMENT_BY_OTP_FEE = "0.09";

		public static final String USD_PAYMENT_BY_PASSWORD_FEE = "0.04";
		public static final String USD_PAYMENT_BY_OTP_FEE = "0.24";

		public static final String TRANSFER_TYPE = "Chuyển tiền trong Vietcombank";
		public static final String USD_EXCHANGE_RATE = "1 USD ~ 23,087.00 VND";
		public static final String EUR_EXCHANGE_RATE = "1 EUR ~ 23,997.76 VND";
		public static final String USD_CHANGED_MONEY = "46174";
		public static final String EUR_CHANGED_MONEY = "47996";

		public static final String NUMBER_TRANSACTION = "Số lần giao dịch";
		public static final String NUMBER_DAY_FREQUENCY = "5";
		public static final String NUMBER_WEEK_FREQUENCY = "2";
		public static final String NUMBER_MONTH_FREQUENCY = "2";

		public static final String MONEY_OVER_PER_TRANSACTION = "100000001";
		public static final String MONEY_OVER_PER_DAY_OF_GROUP = "8000001";

	}

	public static class Output {
		public static final String SUCESSFULL_CREATED_ORDER = "LẬP LỆNH THÀNH CÔNG";
		public static final String TRANSFER_SUCESS_MESSAGE = "CHUYỂN KHOẢN THÀNH CÔNG";
		public static final String CONFIRMATION_MESSAGE = "Quý khách vui lòng kiểm tra thông tin giao dịch đã khởi tạo";
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
		public static final String INVALID_DESTINATION_ACCOUNT_MESSAGE_1 = "Số tài khoản người nhận không đúng. Quý khách vui lòng kiểm tra lại.";
		public static final String INVALID_DESTINATION_ACCOUNT_MESSAGE = "Tài khoản đích không hợp lệ. Quý khách vui lòng kiểm tra lại.";
		public static final String INVALID_DESTINATION_ACCOUNT_MESSAGE_2 ="Tài khoản hưởng không hợp lệ, Quý khách vui lòng kiểm tra lại.";
		public static final String INVALID_DESTINATION_ACCOUNT_MESSAGE_3 = "Tài khoản hưởng không hợp lệ. Quý khách vui lòng kiểm tra lại.";
		public static final String NOT_ENOUGH_MONEY = "Giao dịch không thành công do tài khoản của Quý khách không đủ số dư. Vui lòng kiểm tra lại.";
		public static final String ERROR_MESSAGE_WITH_MIN_LIMIT_TRANSFER_MONEY = "Chuyển tiền không thành công. Số tiền giao dịch nhỏ hơn hạn mức 10,000 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp";
		public static final String ERROR_MESSAGE_WITH_MIN_LIMIT_FUTURE_TRANSFER_MONEY = "Chuyển tiền không thành công. Số tiền giao dịch nhỏ hơn hạn mức 10,000 VND/1 lần, chi tiết xem tại https://www.vietcombank.com.vn hoặc liên hệ Hotline của Vietcombank để được trợ giúp.";
		public static final String ERROR_MESSAGE_WITH_MAX_LIMIT_TRANSFER_MONEY = "Chuyển tiền không thành công. Số tiền giao dịch lớn hơn hạn mức 100,000,000 VND/1 lần, Chi tiết xem tại http://www.vietcombank.com.vn hoặc liên hệ Hotline 24/7: 1900545413 để được trợ giúp.";
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

	}

	public static class InputDataInFutureForOTP {
		public static final String OTP_FEE = "2,200";
		public static final String TRANSFER_AMOUNT = "20000";
		public static final String AMOUNT_OF_EUR_OR_USD_TRANSFER = "1";
		public static final String USD_SMS_OTP_FEE = "0.1";
		public static final String EUR_SMS_OTP_FEE = "0.09";

	}

	public static class InputDataInFutureForPassword {
		public static final String PASSWORD_FEE = "500";
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
}
