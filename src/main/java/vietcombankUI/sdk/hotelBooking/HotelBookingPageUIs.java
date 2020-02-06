package vietcombankUI.sdk.hotelBooking;

public class HotelBookingPageUIs {
	public static final String LINEARLAYOUT_HOTEL_BY_PAYCODE = "//android.widget.ListView[@resource-id='com.VCB:id/lvList']//android.widget.TextView[@text = '%s']/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout";
	public static final String TEXTVIEW_PAYCODE_BY_ID = "//android.widget.TextView[@resource-id='com.VCB:id/tvPaycode']"; 
	public static final String TEXTVIEW_BY_LISTVIEW = "//android.widget.ListView//android.widget.TextView[@resource-id='%s']";
	public static final String LINEARLAYOUT_HOTEL_BY_HOTEL_NAME = "//android.widget.TextView[@text = '%s']/parent::android.widget.LinearLayout/parent::android.widget.RelativeLayout/parent::android.widget.LinearLayout";
	public static final String TEXTVIEW_HOTEL_NAME_BY_ID = "//android.widget.TextView[@resource-id='com.VCB:id/tvName']";
	
}
