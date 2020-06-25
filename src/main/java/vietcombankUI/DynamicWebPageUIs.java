package vietcombankUI;

public class DynamicWebPageUIs {
	public static final String DYNAMIC_INPUT_BY_ID = "//input[@id='%s']";
	public static final String DYNAMIC_BUTTON_BY_ID= "//button[@id='%s']";
	public static final String DYNAMIC_LINK_TEXT_BY_ID= "//li[@id='%s']//a";
	public static final String DYNAMIC_LINK_TEXT_BY_TEXT= "//li//a[text()='%s']";
	public static final String DYNAMIC_LINK_CONFIX_LIMIT= "//td[contains(text(),'TÀI CHÍNH CƯ TRÚ')]//following-sibling::td//a[@title='Edit Package']";
}
