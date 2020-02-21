package vietcombankUI.sdk.airTicketBooking;

public class AirTicketBookingUIs {
	public static final String DATE_IN_CALENDAR = "//android.view.View[@text='%s']";
	public static final String DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP = "//android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_BUTTON = "//android.widget.Button[@text='%s']";
	public static final String DYNAMIC_DATE_SELECTED = "//android.widget.TextView[@text='%s']//following-sibling::android.view.ViewGroup//android.widget.TextView[@text='%s']";
	public static final String LIST_FLIGHT = "//*[@resource-id='com.VCB:id/recycler_view_one']//android.widget.TextView[@resource-id='com.VCB:id/tv_flightNo'and contains(@text,'%s')]";
	public static final String DYNAMIC_TEXT_BOX_BY_LABEL = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.LinearLayout//android.widget.EditText[@resource-id=\"%s\"]";
	public static final String DYNAMIC_TEXT_BOX_BY_ID = "//android.widget.EditText[@resource-id=\"%s\"]";
	public static final String DYNAMIC_TEXT_BY_ID = "//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_ICON_BY_ID = "//android.widget.ImageView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_CHECK_BOX_BY_LABEL = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.LinearLayout//android.widget.CheckBox[@resource-id=\"%s\"]";
	public static final String DYNAMIC_BUTTON_OR_DROPDOWN_BY_LABEL = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.LinearLayout//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_DEFAULT_INCREASE_OR_DECREASE_ICON = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.LinearLayout//android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_SELECTED_PEOPLE = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.LinearLayout/android.widget.TextView[@resource-id='com.VCB:id/tvNumber']";
	public static final String DYNAMIC_PLACE_TEXT = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_PRICE_INFO = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_ICON_BY_LABEL = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_FLIGHT_SHIFT_BY_FLIGHT_CODE = "//*[@resource-id=\"%s\"]//android.widget.TextView[@resource-id='com.VCB:id/tv_flightcode_internal' and contains(@text,'%s')]";
	public static final String DYNAMIC_FLIGHT_SHIFT_INFO_BY_FLIGHT_CODE_2_WAY = "//*[@resource-id=\"%s\"]//android.widget.TextView[@resource-id='com.VCB:id/tv_flightcode_internal' and contains(@text,'%s')]//preceding::android.widget.LinearLayout//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_FLIGHT_SHIFT_INFO_BY_FLIGHT_CODE_1_WAY = "//*[@resource-id=\"%s\"]//android.widget.TextView[@resource-id='com.VCB:id/tv_flightNo' and contains(@text,'%s')]//following::android.widget.LinearLayout//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_TEXT_BOX_AIR_TICKET_INFO_OF_CUSTOMER = "//*[@resource-id=\"%s\"]/android.widget.LinearLayout[@index='%s']//android.widget.EditText[@resource-id=\"%s\"]";
	public static final String DYNAMIC_TEXT_VIEW_AIR_TICKET_INFO_OF_CUSTOMER = "//*[@resource-id=\"%s\"]/android.widget.LinearLayout[@index='%s']//android.widget.LinearLayout[@resource-id=\"%s\"]//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_HEADER_IN_TICKET_INFO = "//*[@resource-id=\"%s\"]/android.widget.LinearLayout[@index='%s']//android.widget.TextView[@resource-id=\"%s\"]";
}
