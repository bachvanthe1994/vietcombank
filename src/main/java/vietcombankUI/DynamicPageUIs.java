package vietcombankUI;

public class DynamicPageUIs {
	// BUTTON
	public static final String DYNAMIC_ACCEPT_BUTTON_OR_BUTTON = "//android.widget.Button[@resource-id= '%s']";
	public static final String DYNAMIC_BUTTON = "//android.widget.Button[@text='%s']";

	// TEXT VIEW android.widget.TextView
	public static final String DYNAMIC_BUTTON_LINK_LABEL_TEXT = "//android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_BUTTON_LINK_LABEL_TEXT_CONTAINS = "//android.widget.TextView[contains(@text, '%s')]";
	public static final String DYNAMIC_TEXT_BY_ID = "//android.widget.TextView[@resource-id='%s']";
	public static final String DYNAMIC_TEXT_IMAGE = "//android.widget.ImageView[@resource-id='%s']//following-sibling::android.widget.TextView";
	public static final String DYNAMIC_IMAGE_BY_FOLLOWING_IMAGE_ID = "//android.widget.ImageView[@resource-id='%s']//following-sibling::android.widget.ImageView";
	public static final String DYNAMIC_DROPDOWN_BY_HEADER = "//android.widget.TextView[@text='%s']/parent::android.widget.LinearLayout//following-sibling::android.widget.LinearLayout[@index='%s']//android.widget.TextView";
	public static final String DYNAMIC_TEXT_IN_POPUP = "//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_CONFIRM_INFO = "//android.widget.LinearLayout/android.widget.TextView[@text = '%s']/following-sibling::android.widget.TextView";
	public static final String DYNAMIC_TEXT_LINE_2_BY_LINEARLAYOUT = "//android.widget.TextView[@text='%s']/parent::android.widget.LinearLayout/following-sibling::android.widget.LinearLayout//android.widget.TextView[@resource-id = 'com.VCB:id/tvContent']";
	public static final String DYNAMIC_CONFIRM_SECOND_LINE_INFO = "//android.widget.TextView[@text='%s']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView";
	public static final String DYNAMIC_DEFAULT_SOURCE_ACCOUNT = "//android.widget.TextView[@text='%s']/following-sibling::android.widget.LinearLayout/android.widget.TextView";
	public static final String DYNAMIC_IMAGE_VIEW = "//android.widget.ImageView[@resource-id='%s']";
	public static final String DYNAMIC_LABEL_AMOUNT = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.TextView";
	public static final String DYNAMIC_CURRENTCY_IN_MONEY_TEXTBOX = "//android.widget.EditText[@text='%s']//following-sibling::android.widget.TextView";
	public static final String DYNAMIC_DROPDOWN_BY_LABEL = "//android.widget.TextView[@text = '%s']/following-sibling::android.widget.LinearLayout//android.widget.TextView";
	public static final String DYNAMIC_TRANSFER_TIME = "//android.widget.TextView[@text = '%s']/following-sibling::android.widget.TextView[@index='%s']";
	public static final String DYNAMIC_TRANSACTION_INFO_IN_REPORT = "//android.widget.LinearLayout[@index ='0']//android.widget.LinearLayout[@index ='%s']//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_TRANSACTION_INFO_IN_TRANSFER_ORDER_STATUS = "//android.widget.ListView[@resource-id =\"com.VCB:id/nlvHisDetailAcc\"]//android.widget.LinearLayout[@index='0']//android.widget.LinearLayout[@index='%s']//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_IMAGEVIEW_BY_LISTVIEW_ID = "//android.widget.ListView[@resource-id =\"com.VCB:id/nlvHisDetailAcc\"]//android.view.ViewGroup[@index='%s']";
	public static final String DYNAMIC_LABEL_COST = "//android.widget.TextView[@text='%s']//preceding::android.widget.TextView";
	public static final String DYNAMIC_LABEL_MONEY_BY_ACCOUNT = "//android.widget.TextView[@text='%s']/following-sibling::android.widget.TextView";
	public static final String DYNAMIC_INPUT_LABEL_BY_HEADER = "//android.widget.TextView[@text='%s']/parent::android.widget.FrameLayout/following-sibling::android.widget.LinearLayout//android.widget.LinearLayout[@index='%s']//android.widget.TextView[@resource-id='%s']";
	public static final String DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID = "//android.widget.LinearLayout[@resource-id='%s']//android.widget.TextView";
	public static final String DYNAMIC_TEXT = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.TextView";
	public static final String DYNAMIC_QUICK_MENU = "//android.widget.LinearLayout[@resource-id='com.VCB:id/layoutRoot']//android.widget.ImageView[@resource-id='com.VCB:id/icon']";

	public static final String DYNAMIC_EDITVIEW_BY_LINEARLAYOUT_ID = "//android.widget.LinearLayout[@resource-id=\"%s\"]//android.widget.EditText";
	public static final String DYNAMIC_TEXTVIEW_BY_RELATIVELAYOUT_ID = "//android.widget.RelativeLayout[@resource-id=\"%s\"]//android.widget.TextView";
	public static final String DYNAMIC_TEXTVIEW_BY_LISTVIEW = "//android.widget.ListView[@resource-id='%s']//android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_TEXTVIEW_FOLLOW_IMAGE = "//android.widget.ImageView[@index='%s']//following-sibling::android.widget.TextView[@index='%s']";
	public static final String DYNAMIC_EDIT_INDEX = "//android.widget.TextView[@text = '%s']//following-sibling::android.widget.LinearLayout[@index='%s']//android.widget.TextView";
	public static final String DYNAMIC_TEXT_FOLLOW_LAYOUT = "//android.widget.LinearLayout[@resource-id = '%s']//android.widget.TextView";
	public static final String DYNAMIC_TEXT_FOLLOW_2_LAYOUT = "//android.widget.LinearLayout[@resource-id = '%s']//android.widget.LinearLayout[@resource-id = '%s']//android.widget.TextView";
	public static final String DYNAMIC_WISHES = "//android.widget.LinearLayout[@index ='1']//android.widget.LinearLayout[@index ='%s']//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_TEXT_INDEX = "//android.widget.LinearLayout[@index='%s']//android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_TEXT_INDEX_ID = "//android.widget.LinearLayout[@index='%s']//android.widget.TextView[@resource-id='%s']";
	public static final String DYNAMIC_IMAGE_TEXT = "//android.widget.ImageView//following-sibling::android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_ACCOUNT_OUT_VCB = "//android.widget.TextView[@text ='%s']/parent::android.widget.LinearLayout//following-sibling::android.widget.LinearLayout//android.widget.LinearLayout//descendant::android.widget.LinearLayout[@index='%s']//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_TEXT_BUTTON = "//android.widget.Button[@text='%s']//preceding-sibling::android.widget.TextView";

	public static final String DYNAMIC_TEXT_FOLLOWING_VIEWGROUP = "//android.widget.TextView[@text = '%s']//following-sibling::android.view.ViewGroup[@index ='%s']/android.widget.TextView";
	
	
	// LINEAER LAYOUT
	public static final String DYNAMIC_STARUS = "//android.widget.TextView[@text='%s']/parent::android.widget.FrameLayout/following-sibling::android.widget.LinearLayout//android.widget.LinearLayout[@index='1']";
	public static final String DYNAMIC_LINEAERLAYOUT_BY_ID = "//android.widget.LinearLayout[@resource-id=\"%s\"]";
	public static final String DYNAMIC_LINEAERLAYOUT_BY_INDEX = "//android.widget.LinearLayout[@index=%s]";

	// TEXT BOX OR INPUT BOX
	public static final String DYNAMIC_INPUT_BOX = "//android.widget.EditText[@text='%s']";
	public static final String DYNAMIC_INPUT_BOX_BY_HEADER = "//android.widget.TextView[@text='%s']/parent::android.widget.LinearLayout//following-sibling::android.widget.LinearLayout[@index='%s']//android.widget.EditText";
	public static final String DYNAMIC_COMBOBOX_TEXT = "//android.widget.TextView[@text='%s']/parent::android.widget.LinearLayout//following-sibling::android.widget.LinearLayout[@index='%s']//android.widget.TextView";
	public static final String DYNAMIC_OTP_INPUT = "//android.widget.Button[@text='%s']//preceding-sibling::android.widget.FrameLayout//android.widget.TextView";
	public static final String DYNAMIC_PASSWORD_INPUT = "//android.widget.Button[@text='%s']//preceding-sibling::android.widget.FrameLayout//android.widget.EditText";
	public static final String DYNAMIC_INPUT_IN_LOGIN = "//android.widget.Button[@text='%s']//preceding-sibling::android.widget.FrameLayout//android.widget.EditText";
	public static final String DYNAMIC_TEXT_BOX_WITH_ID = "//android.widget.EditText[@resource-id='%s']";
	public static final String DYNAMIC_LABEL_LIST_ACCEPT = "//android.widget.TextView[@text = '%s']/parent::android.widget.FrameLayout/following-sibling::android.widget.FrameLayout//android.widget.EditText";
	public static final String DYNAMIC_LABEL_SEARCH_BANK = "//android.widget.TextView[@text = '%s']/parent::android.widget.FrameLayout/following-sibling::android.widget.EditText";
	public static final String DYNAMIC_EDIT_FOLLOW_TEXT = "//android.widget.TextView[@text = '%s']//following-sibling::android.widget.LinearLayout[@index='%s']//android.widget.EditText";
	public static final String DYNAMIC_IMAGE_COMBOBOX = "//android.widget.TextView[@text='%s']/parent::android.widget.LinearLayout//following-sibling::android.widget.LinearLayout[@index='%s']//android.widget.ImageView";
	public static final String DYNAMIC_EDI_TEXT = "//android.widget.LinearLayout[@index='%s']//android.widget.EditText";
	public static final String DYNAMIC_EDIT_TEXT_BY_2_LAYOUT = "//android.widget.LinearLayout[@resource-id='%s']//android.widget.LinearLayout[@resource-id='%s']//android.widget.EditText";
	public static final String DYNAMIC_COMBOBOX_TEXT_ID = "//android.widget.TextView[@text='%s']/parent::android.widget.LinearLayout//following-sibling::android.widget.LinearLayout[@index='%s']//android.widget.TextView[@resource-id='%s']";
	public static final String DYNAMIC_TEXT_ID = "//android.widget.TextView[@text='%s']/parent::android.widget.LinearLayout//following-sibling::android.widget.TextView[@resource-id='%s']";
	public static final String DYNAMIC_INPUT_BY_ID = "//android.widget.EditText[@resource-id='%s']";

	// ICON
	public static final String DYNAMIC_CLOSE_ICON = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.ImageView";
	public static final String DYNAMIC_ICON = "//android.widget.TextView[@text='%s']//parent::android.widget.LinearLayout";
	public static final String DYNAMIC_SUCCESS_ICON = "//android.widget.TextView[@text='%s']//preceding-sibling::android.widget.ImageView";
	public static final String DYNAMIC_BOTTOM_MENU_CLOSE_ICON = "//android.widget.ImageView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_BACK_ICON = "//android.widget.TextView[@text = '%s']//ancestor::android.widget.FrameLayout//android.widget.ImageView";
	public static final String DYNAMIC_BOTTOM_MENU = "//android.widget.ImageView[@resource-id='%s']";
	public static final String DYNAMIC_IMAGEVIEW_BY_LINEARLAYOUT_ID = "//android.widget.LinearLayout[@resource-id=\"%s\"]//android.widget.ImageView";
	public static final String DYNAMIC_CONTACT_KEY_MENU = "//android.widget.TextView[@text='%s']/parent::android.widget.LinearLayout//following-sibling::android.widget.ImageView";
	public static final String DYNAMIC_ICON_IN_LIST_VIEW = "//android.widget.ListView[@resource-id='com.VCB:id/nlvHisDetailAcc']/android.widget.LinearLayout[@index='%s']//android.widget.ImageView[@resource-id=\"%s\"]";

	// VIEW
	public static final String DYNAMIC_DATE_IN_DATE_TIME_PICKER_AND_TEXT = "//android.view.View[@text='%s']";
	public static final String DYNAMIC_VIEW_BY_ID = "//android.view.View[@resource-id = '%s']/android.view.View";
	public static final String DYNAMIC_VIEW_VIEW_BY_INDEX = "//android.view.View[@resource-id='%s']//following-sibling::android.view.View[@index='%s']/android.view.View/android.view.View";
	public static final String DYNAMIC_VIEWGROUP_TEXT = "//android.widget.TextView[@text='%s']//parent::android.view.ViewGroup//following-sibling::android.widget.HorizontalScrollView//android.view.ViewGroup//android.view.ViewGroup";

	public static final String DYNAMIC_SCROLL_TEXT = "//android.widget.TextView[@text='%s']//parent::android.view.ViewGroup//following-sibling::android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup//android.view.ViewGroup[@index='%s']//android.widget.TextView[@index='%s']";
	public static final String DYNAMIC_VIEWGROUP_TEXTVIEW = "//android.widget.TextView[@text='%s']//ancestor::android.view.ViewGroup/following-sibling::android.view.ViewGroup//android.view.ViewGroup[@index='%s']//android.widget.TextView[@index='%s']";
	public static final String DYNAMIC_TEXT_PRECEDING = "//android.widget.TextView[@text='%s']//parent::android.view.ViewGroup//preceding-sibling::android.view.ViewGroup[@index='%s']//android.widget.TextView";
	public static final String DYNAMIC_TEXT_VIEW = "//android.widget.TextView[@resource-id='%s']";
	public static final String DYNAMIC_EDIT_TEXT_VIEW_BY_TEXT = "//android.view.View[@text='%s']//following::android.view.View//child::android.widget.EditText";
	public static final String DYNAMIC_IMAGE_VIEW_ID = "//android.widget.ImageView[@resource-id='%s']";
	

	
	public static final String DYNAMIC_BACK = "//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[@index='%s']//android.widget.TextView[@index='%s']";
	public static final String DYNAMIC_IMAGE_TEXTVEW = "//android.widget.TextView[@text='%s']/preceding-sibling::android.view.ViewGroup/android.widget.ImageView";
	public static final String DYNAMIC_TEXT_PRECEDING_TEXT = "//android.widget.TextView[@text = '%s']//preceding-sibling::android.widget.TextView[@index ='%s']";
	public static final String DYNAMIC_TEXT_FOLLOWING_TEXT = "//android.widget.TextView[@text = '%s']//following-sibling::android.widget.TextView[@index ='%s']";
	public static final String DYNAMIC_BACK_TEXT = "//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[@index='%s']//android.view.ViewGroup[@index='%s']/android.widget.TextView";
	public static final String DYNAMIC_IMAGE_EDITTEXT  = "//android.widget.EditText[@text='%s']//ancestor::android.view.ViewGroup/preceding-sibling::android.view.ViewGroup/android.widget.ImageView";


	// IMAGE BUTTON
	public static final String DYNAMIC_IMAGE_BUTTON = "//android.widget.ImageButton[@resource-id='%s']";
	public static final String DYNAMIC_IMAGE_EDIT = "//android.widget.EditText[@text='%s']/following-sibling::android.view.ViewGroup/android.widget.ImageView";

	// CHECK BOX
	public static final String DYNAMIC_CHECK_BOX = "//android.widget.CheckBox[@resource-id=\"%s\"]";

//    Bắt expath với class bị mã hóa chỉ có id
	public static final String DYNAMIC_ESPECIALLY = "//*[@resource-id=\"%s\"]";
	public static final String DYNAMIC_ESPECIALLY_TEXTVIEW = "//*[@resource-id=\"%s\"]//android.widget.LinearLayout[@index='%s']//android.widget.TextView[@resource-id=\"%s\"]";
//DATE PICKER
	public static final String DYNAMIC_DATE_PICKER_BY_ID = "//android.widget.DatePicker[@resource-id=\"%s\"]";

	// WAIT
	public static final String WAIT_BAR = "//android.widget.ProgressBar[@resource-id='com.VCB:id/progressBar']";
}
