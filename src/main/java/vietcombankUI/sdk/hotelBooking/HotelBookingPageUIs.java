package vietcombankUI.sdk.hotelBooking;

public class HotelBookingPageUIs {
	public static final String LINEARLAYOUT_HOTEL_BY_PAYCODE = "//android.widget.ListView[@resource-id='com.VCB:id/lvList']//android.widget.TextView[@text = '%s']/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout";
	public static final String TEXTVIEW_PAYCODE_BY_ID = "//android.widget.TextView[@resource-id='com.VCB:id/tvPaycode']"; 
	public static final String TEXTVIEW_BY_LISTVIEW = "//android.widget.ListView//android.widget.TextView[@resource-id='%s']";
	public static final String LINEARLAYOUT_HOTEL_BY_HOTEL_NAME = "//android.widget.TextView[@text = '%s']/parent::android.widget.LinearLayout/parent::android.widget.RelativeLayout/parent::android.widget.LinearLayout";
	public static final String TEXTVIEW_HOTEL_NAME_BY_ID = "//android.widget.TextView[@resource-id='com.VCB:id/tvName']";
	public static final String VIEW_BY_CONTENT_DESC = "//android.view.View[@content-desc = '%s']//android.view.View";
	public static final String TEXTVIEW_BY_LINEAR_LAYOUT_ID = "//android.widget.LinearLayout[@resource-id='%s']//android.widget.TextView";
	public static final String TEXTVIEW_BY_TEXT = "//android.widget.TextView[@text='%s']";
	public static final String INPUTBOX_BY_ID = "//android.widget.EditText[@resource-id='%s']";
	public static final String DETAIL_BUTTON_BY_PAYCODE = "//android.widget.TextView[@text = '%s']/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout//android.widget.TextView[@text = 'Chi tiết']";
	
}
