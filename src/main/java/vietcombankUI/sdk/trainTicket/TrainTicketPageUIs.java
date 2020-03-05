package vietcombankUI.sdk.trainTicket;

public class TrainTicketPageUIs {
	// Text
	public static final String DYNAMIC_START_AND_END_TEXT = "//android.widget.TextView[@text='%s']//following::android.widget.ScrollView//android.widget.TextView[@resource-id='%s']";
	public static final String DYNAMIC_VIEW_TEXT_START = "//android.widget.LinearLayout/android.widget.TextView[@resource-id='%s']";
	public static final String DYNAMIC_BUTTON_LINK_LABEL_TEXT = "//android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_DATE = "//android.widget.TextView[@resource-id='%s']";
	public static final String DYNAMIC_DATE_SELECTED = "//android.widget.TextView[@text='%s']//following-sibling::android.view.ViewGroup//android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_TITLE_SELECT_DATE = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.FrameLayout/android.widget.TextView";
	public static final String DYNAMIC_TITLE_SELECT_WEEK = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.FrameLayout//android.widget.TextView[@resource-id='%s']";
	public static final String DYNAMIC_NUMBER_CUSTOMER = "//android.widget.TextView[@text='%s']/following::android.widget.TextView";
	public static final String DYNAMIC_TEXT_CHANGE_NUMBER = "//android.widget.TextView[@text='%s']//parent::android.widget.LinearLayout//following-sibling::android.widget.LinearLayout//android.widget.TextView[@resource-id='%s']";
	public static final String DYNAMIC_TEXT_INDEX = "//android.widget.TextView[@index ='%s']";
	public static final String DYNAMIC_DATE_SELECT = "//android.widget.LinearLayout[@resource-id='%s']//android.widget.TextView";
	public static final String DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID = "//android.widget.LinearLayout[@resource-id=\"%s\"]//android.widget.TextView";
	public static final String DYNAMIC_LABEL_AMOUNT = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.TextView";
	public static final String DYNAMIC_TEXT_IMAGE = "//android.widget.ImageView[@resource-id='%s']//following-sibling::android.widget.TextView";
	public static final String DYNAMIC_TEXT_IN_POPUP = "//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY = "//android.widget.TextView[@resource-id='%s']";
	public static final String DYNAMIC_TEXT_FOLLOW_BUTTON = "//android.widget.Button[@resource-id='%s']//preceding-sibling::android.widget.LinearLayout//android.widget.TextView";
	public static final String DYNAMIC_TEXT_MESSAGE_SEARCH = "//android.widget.RelativeLayout[@resource-id='%s']//android.widget.TextView";
	public static final String DYNAMIC_LIST_ITEM = "//android.support.v7.widget.RecyclerView[@index ='%s']";

	// Image-icon
	public static final String DYNAMIC_HISTORY_ICON = "//android.widget.TextView[@text='%s']/following::android.widget.ImageView";
	public static final String DYNAMIC_COMBOBOX = "//android.widget.TextView[@text='%s']/parent::android.widget.LinearLayout/following-sibling::android.widget.ImageView";
	public static final String DYNAMIC_CANCEL_ICON = "//android.widget.EditText[@text ='%s']//ancestor::android.widget.LinearLayout/android.widget.ImageView";
	public static final String DYNAMIC_CHANGE_ICON = "//android.widget.TextView[@text ='%s']//ancestor::android.widget.LinearLayout/android.widget.ImageView";
	public static final String DYNAMIC_CALL_ICON = "//android.widget.TextView[@text='%s']/following::android.widget.FrameLayout/android.widget.ImageView";
	public static final String DYNAMIC_BACK_ICON = "//android.widget.TextView[@text = '%s']//ancestor::android.widget.FrameLayout//android.widget.ImageView";
	public static final String DYNAMIC_SUCCESS_ICON = "//android.widget.TextView[@text='%s']//preceding-sibling::android.widget.ImageView";
	public static final String DYNAMIC_ICON_CHANGE_NUMBER = "//android.widget.TextView[@text='%s']//parent::android.widget.LinearLayout//following-sibling::android.widget.LinearLayout//android.widget.ImageView[@resource-id='%s']";
	public static final String DYNAMIC_BOTTOM_MENU_CLOSE_ICON = "//android.widget.ImageView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_ICON_CHECK = "//android.widget.TextView[@text='%s']//parent::android.widget.LinearLayout//following-sibling::android.widget.ImageView[@resource-id='%s']";

	// Input
	public static final String DYNAMIC_INPUT_POINT = "//android.widget.EditText[@resource-id='%s']";

	// button
	public static final String DYNAMIC_ACCEPT_BUTTON_OR_BUTTON = "//android.widget.Button[@resource-id=\"%s\"]";
	public static final String DYNAMIC_BUTTON = "//android.widget.Button[@text='%s']";

	//LinearLayout
	public static final String DYNAMIC_TEXT_NAME_TRAIN = "//android.widget.TextView[@resource-id='%s']//ancestor::android.widget.LinearLayout[@index='%s']";
	public static final String DYNAMIC_STARUS = "//android.widget.TextView[@text='%s']/parent::android.widget.FrameLayout/following-sibling::android.widget.LinearLayout//android.widget.LinearLayout[@index='1']";
}
