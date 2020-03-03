package vietcombankUI.sdk.hotelBooking;

public class HotelBookingPageUIs {
	//TextView
	public static final String TEXTVIEW_PAYCODE_BY_ID = "//android.widget.TextView[@resource-id='com.VCB:id/tvPaycode']"; 
	public static final String TEXTVIEW_BY_LISTVIEW = "//android.widget.ListView//android.widget.TextView[@resource-id='%s']";
	public static final String TEXTVIEW_HOTEL_NAME_BY_ID = "//android.widget.TextView[@resource-id='com.VCB:id/tvName']";
	public static final String TEXTVIEW_BY_LINEAR_LAYOUT_ID = "//android.widget.LinearLayout[@resource-id='%s']//android.widget.TextView";
	public static final String TEXTVIEW_BY_TEXT = "//android.widget.TextView[@text='%s']";
	public static final String TEXTVIEW_BY_ID = "//android.widget.TextView[@resource-id='%s']";
	public static final String DETAIL_BUTTON_BY_PAYCODE = "//android.widget.TextView[@text = '%s']/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout//android.widget.TextView[@text = 'Chi tiết']";
	public static final String DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP = "//android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_TEXTVIEW_BY_LINEARLAYOUT_ID = "//android.widget.LinearLayout[@resource-id=\"%s\"]//android.widget.TextView";
	public static final String DYNAMIC_CONFIRM_INFO = "//android.widget.LinearLayout/android.widget.TextView[@text = '%s']/following-sibling::android.widget.TextView";
	public static final String DYNAMIC_DROPDOWN_BY_LABEL = "//android.widget.TextView[@text = '%s']/following-sibling::android.widget.LinearLayout/android.widget.TextView";
	public static final String DYNAMIC_OTP_INPUT = "//android.widget.Button[@text='%s']//preceding-sibling::android.widget.FrameLayout//android.widget.TextView";
	public static final String DYNAMIC_DROP_DOWN_DATE_TIME_PICKER_WITH_ID_LIST_OF_MONEY = "//android.widget.TextView[@resource-id=\"%s\"]";

	//Linear layout
	public static final String LINEARLAYOUT_HOTEL_BY_PAYCODE = "//android.widget.ListView[@resource-id='com.VCB:id/lvList']//android.widget.TextView[@text = '%s']/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout";
	public static final String LINEARLAYOUT_HOTEL_BY_HOTEL_NAME = "//android.widget.TextView[@text = '%s']/parent::android.widget.LinearLayout/parent::android.widget.RelativeLayout/parent::android.widget.LinearLayout";
	
	//Edit text
	public static final String INPUTBOX_BY_ID = "//android.widget.EditText[@resource-id='%s']";
	public static final String INPUT_BOX_BY_TEXT = "//android.widget.EditText[@text='%s']";
	public static final String DYNAMIC_INPUT_BOX = "//android.widget.EditText[@text='%s']";
	
	//Button
	public static final String DYNAMIC_BUTTON = "//android.widget.Button[@text='%s']";
	
	//Image view
	public static final String DYNAMIC_BACK_ICON = "//android.widget.TextView[@text = '%s']//ancestor::android.widget.FrameLayout//android.widget.ImageView";
	public static final String VIEW_BY_CONTENT_DESC = "//android.view.View[@content-desc = '%s']//android.view.View";
	public static final String DYNAMIC_BOTTOM_MENU_CLOSE_ICON = "//android.widget.ImageView[@resource-id=\"%s\"]";
	
	
	
}
