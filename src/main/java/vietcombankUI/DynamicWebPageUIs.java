package vietcombankUI;

public class DynamicWebPageUIs {
	public static final String DYNAMIC_INPUT_BY_ID = "//input[@id='%s']";
	public static final String DYNAMIC_BUTTON_BY_ID= "//button[@id='%s']";
	public static final String DYNAMIC_LINK_TEXT_BY_ID= "//li[@id='%s']//a";
	public static final String DYNAMIC_LINK_TEXT_BY_TEXT= "//li//a[text()='%s']";
	public static final String DYNAMIC_LINK_CONFIX_LIMIT= "//td[contains(text(),'TÀI CHÍNH CƯ TRÚ')]//following-sibling::td//a[@title='Edit Package']";

	public static final String DYNAMIC_BUTTON_A_BY_ID = "//a[@id='%s']";
	public static final String DYNAMIC_BUTTON_A_BY_CLASS = "//a[@class='%s']";
	
	public static final String DYNAMIC_MENU_BY_LINK = "//a[@href='%s']";
	public static final String DYNAMIC_MENU_BY_NG_CLICK = "//a[@ng-click ='%s']";
	
	public static final String DYNAMIC_DROPDOWN_BY_CLASS = "//select[contains(@class,'%s')]";
	public static final String DYNAMIC_DROPDOWN_BY_ID = "//select[@id='%s']";
	public static final String DYNAMIC_OPTION_DROPDOWN_BY_TEXT = "//option[contains(text(),'%s')]";
	
	public static final String DYNAMIC_ICON_TITLE_BY_FOLLOW_TEXT = "//td[contains(text(),'%s')]//following-sibling::td//a[@title='%s']";
	public static final String DYNAMIC_ICON_TITLE_BY_FOLLOW_TWO_TEXTS = "//td[contains(text(),'%s')]//following-sibling::td[contains(text(),'%s')]//following-sibling::td//a[@title='%s']";

	public static final String DYNAMIC_TEXT_BY_FOLLOW_TEXT_INDEX = "//td[contains(text(),'%s')]/following-sibling::td[%s]";


	public static final String DYNAMIC_LINK_LI_BY_ID= "//li[@id='%s']";
	public static final String DYNAMIC_GET_LIST_METHOD= "//th[contains(text(),'%s')]//parent::tr//following-sibling::tr//td[@class='ng-binding'][2]";

	public static final String DYNAMIC_LINK_A_BY_ID= "//a[@id='%s']";
	public static final String DYNAMIC_SELECT_BY_CLASS= "//select[@class='%s']";
	public static final String DYNAMIC_LIMIT_SERVICES= "//select[@class='%s']";
	public static final String DYNAMIC_ICON_PENCIL= "//td[contains(text(),'%s')]//following-sibling::td//a[@class='%s']";
	public static final String DYNAMIC_SELECT_MODEL  = "//select[@ng-model='%s']";
	public static final String DYNAMIC_OPTION_VALUE   = "//option[@value='%s']";

	public static final String DYNAMIC_SELECT_ID   = "//select[@id ='%s']";
	public static final String DYNAMIC_OPTION_TEXT    = "//option[text()='%s']";
	
	public static final String DYNAMIC_TR_INDEX    = "//tr[@class='%s']/td[@class='%s'][%s]";
	public static final String DYNAMIC_TD_FOLLOWING_INDEX    = "//tr[@class='%s']//td[contains(text(),'%s')]//following-sibling::td[%s]";


}

