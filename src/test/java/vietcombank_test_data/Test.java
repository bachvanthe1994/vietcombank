package vietcombank_test_data;

import java.util.Calendar;

public class Test {

	public static void main(String[] args) {

		Calendar calendar = Calendar.getInstance();
		int lastDate = calendar.getActualMaximum(Calendar.DATE);
		calendar.set(Calendar.DATE, lastDate);
		int lastDay = calendar.get(Calendar.DAY_OF_MONTH);

		System.out.println("Last Day : " + lastDay);
	}
}
