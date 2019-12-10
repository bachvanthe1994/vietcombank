package vnpay.vietcombank.transfer_money_in_vcb;

public class test {

	public static void main(String[] args) {
		String money = "4,317,842,003 VND";
		 long m = convertMoneytoInt(money);
		String a = String.format("%.2f", Double.parseDouble("0.4")) + " EURO";
		System.out.println(m);
		System.out.println(a);

	}
public static long convertMoneytoInt(String money) {
	  money = money.replaceAll(" VND", "");
	  money = money.replaceAll(",", "");
	  long m = Long.parseLong(money);
	  return m;
}
}