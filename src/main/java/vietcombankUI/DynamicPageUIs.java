package vietcombankUI;

public class DynamicPageUIs {
	public static final String DYNAMIC_ACCEPT_BUTTON_OR_BUTTON="//android.widget.Button[@resource-id=\"%s\"]";
	public static final String DYNAMIC_BUTTON="//android.widget.Button[@text='%s']";
	public static final String DYNAMIC_BUTTON_LINK_LABEL_TEXT="//android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_DROP_DOWN="//android.widget.TextView[@text='%s']//ancestor::android.widget.LinearLayout//following-siling[@index='%s']";
	public static final String DYNAMIC_INPUT_BOX="//android.widget.EditText[@text='%s']";
	public static final String DYNAMIC_OTP_INPUT="//android.widget.Button[@text='%s']//preceding-sibling::android.widget.FrameLayout/android.widget.TextView";
	public static final String DYNAMIC_INPUT_IN_LOGIN="//android.widget.Button[@text='%s']//preceding-sibling::android.widget.FrameLayout//android.widget.EditText";
	public static final String DYNAMIC_INPUT_TEXT_POPUP="//android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_CONFIRM_INFO = "//android.widget.LinearLayout/android.widget.TextView[@text = '%s']/following-sibling::android.widget.TextView";
	public static final String DYNAMIC_CLOSE_ICON="//android.widget.TextView[@text='%s']//following-sibling::android.widget.ImageView";
	public static final String DYNAMIC_ICON="//android.widget.TextView[@text='%s']//parent::android.widget.LinearLayout";
	public static final String SOURCE_ACCOUNT = "//android.widget.TextView[@text = 'Tài khoản nguồn']/following-sibling::android.widget.LinearLayout/android.widget.TextView";
	public static final String DYNAMIC_TRANSFER_TIME = "//android.widget.TextView[@text = '%s']/following-sibling::android.widget.TextView[@index='%s']";
	public static final String DYNAMIC_TRANSACTION = "//android.widget.TextView[@text ='%s']//parent::android.widget.LinearLayout//following-sibling::android.widget.ListView[@index='%s']/android.widget.TextView[@index='%s']";

}

