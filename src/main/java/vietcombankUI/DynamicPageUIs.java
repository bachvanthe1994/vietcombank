package vietcombankUI;

public class DynamicPageUIs {
	public static final String DYNAMIC_ACCEPT_BUTTON_OR_BUTTON="//android.widget.Button[@resource-id=\"%s\"]";
	public static final String DYNAMIC_BUTTON="//android.widget.Button[@text='%s']";
	public static final String DYNAMIC_BUTTON_LINK_LABEL_TEXT="//android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_INPUT_BOX="//android.widget.EditText[@text='%s']";
	public static final String DYNAMIC_LOGIN_INPUT_BOX="//android.widget.TextView[@text='Đăng nhập']//preceding::android.widget.LinearLayout[@index='0']/android.widget.EditText[@index='%s']";	
	public static final String DYNAMIC_OTP_INPUT="//android.widget.TextView[@text='%s']/following-sibling::android.widget.TextView";
	public static final String DYNAMIC_CHECKBOX="//android.widget.CheckBox[@text='%s']";
	public static final String DYNAMIC_RADIO_BUTTON="//android.widget.CheckBox[@text='%s']//following-sibling::android.widget.LinearLayout//android.widget.RadioButton[@text='%s']";
	public static final String DYNAMIC_DEFAULT_PERSON_PLACEHOLDER="//android.widget.TextView[@text='%s']//following-sibling::android.widget.LinearLayout/android.widget.TextView[@index='%s']";
	public static final String DYNAMIC_DEFAULT_INCREASE_OR_DECREASE_ICON="//android.widget.TextView[@text='%s']//following-sibling::android.widget.LinearLayout/android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_DATE_SELECTED="//android.widget.TextView[@text='%s']//following-sibling::android.view.ViewGroup//android.widget.TextView[@text='%s']";
	public static final String ALL_DYNAMIC_DROP_DOWN_VALUE="//android.support.v7.widget.RecyclerView/android.widget.LinearLayout";
	public static final String DYNAMIC_BACK_ICON="//android.widget.TextView[@text='%s']/parent::android.widget.LinearLayout/preceding-sibling::android.widget.ImageView";
	public static final String DYNAMIC_CALL_ICON="//android.widget.TextView[@text='%s']/following::android.widget.FrameLayout/android.widget.ImageView";
	public static final String CALL_NUMBER="//android.widget.EditText[@resource-id='com.samsung.android.dialer:id/digits']";
	public static final String LIST_OF_ITEMS="//android.support.v7.widget.RecyclerView/android.widget.LinearLayout/android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_DELETE_ICON="//android.widget.TextView[@text='%s']/preceding::android.widget.LinearLayout//android.widget.ImageView[@index='%s']";
	public static final String DYNAMIC_DEPARTURE_OR_ARRIVE_CODE="//android.widget.TextView[@text='%s']/preceding-sibling::android.widget.TextView[@index='0']";

	
	
}

