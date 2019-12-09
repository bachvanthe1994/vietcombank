package vietcombankUI;

public class DynamicPageUIs {
	public static final String DYNAMIC_ACCEPT_BUTTON_OR_BUTTON="//android.widget.Button[@resource-id=\"%s\"]";
	public static final String DYNAMIC_BUTTON="//android.widget.Button[@text='%s']";
	public static final String DYNAMIC_BUTTON_LINK_LABEL_TEXT="//android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID="//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_INPUT_BOX="//android.widget.EditText[@text='%s']";
	public static final String DYNAMIC_OTP_INPUT="//android.widget.Button[@text='%s']//preceding-sibling::android.widget.FrameLayout/android.widget.TextView";
	public static final String DYNAMIC_INPUT_IN_LOGIN="//android.widget.Button[@text='%s']//preceding-sibling::android.widget.FrameLayout//android.widget.EditText";
	public static final String DYNAMIC_INPUT_TEXT_POPUP="//android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_CONFIRM_INFO = "//android.widget.LinearLayout/android.widget.TextView[@text = '%s']/following-sibling::android.widget.TextView";
	public static final String DYNAMIC_CONFIRM_SECOND_LINE_INFO = "//android.widget.TextView[@text='%s']//parent::android.widget.LinearLayout/following-sibling::android.widget.TextView";
	public static final String DYNAMIC_CLOSE_ICON="//android.widget.TextView[@text='%s']//following-sibling::android.widget.ImageView";
	public static final String DYNAMIC_ICON="//android.widget.TextView[@text='%s']//parent::android.widget.LinearLayout";

	public static final String DYNAMIC_LABEL_AMOUNT="//android.widget.TextView[@text='%s']//following-sibling::android.widget.TextView";
	public static final String DYNAMIC_DROPDOWN_BY_LABEL = "//android.widget.TextView[@text = '%s']/following-sibling::android.widget.LinearLayout/android.widget.TextView";
	public static final String DYNAMIC_TRANSFER_TIME = "//android.widget.TextView[@text = '%s']/following-sibling::android.widget.TextView[@index='%s']";
	public static final String DYNAMIC_TRANSACTION_INFO = "//android.widget.LinearLayout[@index ='0']//android.widget.LinearLayout[@index ='%s']//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_BOTTOM_MENU = "//android.widget.ImageView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_BACK_ICON = "//android.widget.TextView[@text = '%s']//ancestor::android.widget.FrameLayout//android.widget.ImageView";

}


