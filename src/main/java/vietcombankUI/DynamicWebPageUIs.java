package vietcombankUI;

public class DynamicWebPageUIs {
	public static final String DYNAMIC_INPUT_BY_ID = "//input[@id='%s']";
	public static final String DYNAMIC_BUTTON_BY_ID= "//button[@id='%s']";
	public static final String DYNAMIC_LINK_TEXT_BY_ID= "//li[@id='%s']//a";
	public static final String DYNAMIC_LINK_TEXT_BY_TEXT= "//li//a[text()='%s']";
	public static final String DYNAMIC_LINK_CONFIX_LIMIT= "//td[contains(text(),'TÀI CHÍNH CƯ TRÚ')]//following-sibling::td//a[@title='Edit Package']";

	public static final String DYNAMIC_BUTTON_A_BY_ID = "//a[@id='%s']";
	
	public static final String DYNAMIC_MENU_BY_LINK = "//a[@href='%s']";
	
	public static final String DYNAMIC_DROPDOWN_BY_CLASS = "//select[contains(@class,'%s')]";
	public static final String DYNAMIC_OPTION_DROPDOWN_BY_TEXT = "//option[contains(text(),'%s')]";
	
	public static final String DYNAMIC_ICON_TITLE_BY_FOLLOW_TEXT = "//td[contains(text(),'%s')]//following-sibling::td//a[@title='%s']";
	public static final String DYNAMIC_ICON_TITLE_BY_FOLLOW_TWO_TEXTS = "//td[contains(text(),'%s')]//following-sibling::td[contains(text(),'%s')]//following-sibling::td//a[@title='%s']";
	

	public static final String DYNAMIC_LINK_LI_BY_ID= "//li[@id='%s']";

}
