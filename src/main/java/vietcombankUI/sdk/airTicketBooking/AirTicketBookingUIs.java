package vietcombankUI.sdk.airTicketBooking;

public class AirTicketBookingUIs {

	// text box
	public static final String DYNAMIC_TEXT_BOX_BY_LABEL = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.LinearLayout//android.widget.EditText[@resource-id=\"%s\"]";
	public static final String DYNAMIC_TEXT_BOX_BY_ID = "//android.widget.EditText[@resource-id=\"%s\"]";
	public static final String DYNAMIC_TEXT_BOX_AIR_TICKET_INFO_OF_CUSTOMER = "//*[@resource-id=\"%s\"]/android.widget.LinearLayout[@index='%s']//android.widget.EditText[@resource-id=\"%s\"]";

	// text view
	public static final String DATE_IN_CALENDAR = "//android.view.View[@text='%s']";
	public static final String DYNAMIC_BUTTON_LINK_LABEL_TEXT_POP_UP = "//android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_DATE_SELECTED = "//android.widget.TextView[@text='%s']//following-sibling::android.view.ViewGroup//android.widget.TextView[@text='%s']";
	public static final String LIST_FLIGHT = "//*[@resource-id='com.VCB:id/recycler_view_one']//android.widget.TextView[@resource-id='com.VCB:id/tv_flightNo'and contains(@text,'%s')]";
	public static final String DYNAMIC_1_WAY_FLIGHT = "//*[@resource-id='com.VCB:id/recycler_view_one']//android.widget.LinearLayout[@index='%s']//android.widget.LinearLayout[@resource-id='%s']";
	public static final String DYNAMIC_TEXT_BY_ID = "//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_BUTTON_OR_DROPDOWN_BY_LABEL = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.LinearLayout//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_SELECTED_PEOPLE = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.LinearLayout/android.widget.TextView[@resource-id='com.VCB:id/tvNumber']";
	public static final String DYNAMIC_PLACE_TEXT = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_PRICE_INFO = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_FLIGHT_SHIFT_BY_FLIGHT_CODE = "//*[@resource-id=\"%s\"]//android.widget.TextView[@resource-id='com.VCB:id/tv_flightcode_internal' and contains(@text,'%s')]";
	public static final String DYNAMIC_FLIGHT_SHIFT_INFO_BY_FLIGHT_CODE_2_WAY = "//*[@resource-id=\"%s\"]//android.widget.TextView[@resource-id='com.VCB:id/tv_flightcode_internal' and contains(@text,'%s')]//parent::android.widget.LinearLayout//parent::android.widget.LinearLayout//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_FLIGHT_SHIFT_INFO_BY_FLIGHT_CODE_1_WAY = "//*[@resource-id=\"%s\"]//android.widget.TextView[@resource-id='com.VCB:id/tv_flightNo' and contains(@text,'%s')]//following::android.widget.LinearLayout//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_FLIGHT_SHIFT_DEPARTURE_RETURN_FLIGHT_CODE_2_WAY = "//*[@resource-id=\"%s\"]/android.widget.LinearLayout[@index='%s']";
	public static final String DYNAMIC_FLIGHT_SHIFT_DATA = "//*[@resource-id=\"%s\"]/android.widget.LinearLayout[@index='%s']//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_TEXT_VIEW_AIR_TICKET_INFO_OF_CUSTOMER = "//*[@resource-id=\"%s\"]/android.widget.LinearLayout[@index='%s']//android.widget.LinearLayout[@resource-id=\"%s\"]//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_HEADER_IN_TICKET_INFO = "//*[@resource-id=\"%s\"]/android.widget.LinearLayout[@index='%s']//android.widget.TextView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_DEFAULT_INCREASE_OR_DECREASE_ICON = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.LinearLayout//android.widget.TextView[@text='%s']";
	public static final String DYNAMIC_PAYEMENT_INFO_BY_CUSTOMER_NAME = "//android.widget.TextView[@text='%s']//ancestor::android.widget.LinearLayout[@resource-id='com.VCB:id/linContent']//android.widget.TextView[@text='%s']//following-sibling::android.widget.TextView";
	public static final String DYNAMIC_DEPARTURE_ARRIVAL_DATA = "//android.widget.LinearLayout[@resource-id='%s']//android.widget.TextView[@resource-id='%s']";
	public static final String DYNAMIC_CONFIRM_INFO = "//*[@resource-id='com.VCB:id/recycler_view']//android.widget.LinearLayout[@index='%s']//android.widget.TextView[@text=\"%s\"]/following-sibling::android.widget.TextView";
//Icon
	public static final String DYNAMIC_ICON_BY_ID = "//android.widget.ImageView[@resource-id=\"%s\"]";
	public static final String DYNAMIC_ICON_BY_LABEL = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.ImageView[@resource-id=\"%s\"]";

//Button
	public static final String DYNAMIC_BUTTON = "//android.widget.Button[@text='%s']";

//radio button
	public static final String DYNAMIC_CHECK_RADIO_BUTTON_BY_CHECKBOX = "//android.widget.CheckBox[@text='%s']//following-sibling::android.widget.LinearLayout//android.widget.RadioButton[@text=\"%s\"]";

//Check box
	public static final String DYNAMIC_CHECK_BOX_BY_LABEL = "//android.widget.TextView[@text='%s']//following-sibling::android.widget.LinearLayout//android.widget.CheckBox[@resource-id=\"%s\"]";
	public static final String DYNAMIC_CHECK_BOX_BY_TEXT = "//android.widget.CheckBox[@text=\"%s\"]";
	public static final String DYNAMIC_CHECK_BOX_BY_ID = "//android.widget.CheckBox[@resource-id=\"%s\"]";

}
