package vehicalTicketBookingUI;

public class CommonPageUIs {
    public static final String DYNAMIC_INPUT_TEXT = "//android.widget.EditText[@text='%s']";
    public static final String DYNAMIC_TITlE = "//android.widget.LinearLayout[@resource-id=\"%s\"]";
    public static final String DYNAMIC_BUTTON = "//android.widget.Button[@text='%s']";
    public static final String DYNAMIC_BUTTON_ID = "//android.widget.Button[@resource-id=\"%s\"]";
    public static final String DYNAMIC_TEXT = "//android.widget.TextView[@text='%s']";
    public static final String DYNAMIC_ICON_BACK = "//android.widget.TextView[@text='%s']/preceding::android.widget.ImageView";
    public static final String DYNAMIC_ICON_CHANGE_PLACE_AND_BACK = "//android.widget.ImageView[@resource-id=\"%s\"]";
    public static final String DYNAMIC_TEXT_BY_ID = "//android.widget.TextView[@resource-id=\"%s\"]";
    public static final String DYNAMIC_EDIT_TEXT = "//android.widget.EditText[@resource-id=\"%s\"]";
    public static final String DYNAMIC_NULL_DATA = "//android.widget.RelativeLayout[@resource-id=\"%s\"]//android.widget.LinearLayout//android.widget.TextView";
    public static final String DYNAMIC_FROMT_INPUT_BY_CLOSE = "//android.widget.ImageView/following-sibling::android.widget.LinearLayout//android.widget.LinearLayout//android.widget.LinearLayout[@resource-id=\"%s\"]//android.widget.EditText";
    public static final String DYNAMIC_CALENDA = "//android.widget.ListView[@resource-id=\"%s\"]//android.widget.LinearLayout//android.widget.TextView";
    public static final String DYNAMIC_SUGGEST_TRIP = "//android.widget.LinearLayout[@resource-id=\"%s\"]//android.widget.TextView";
    public static final String DYNAMIC_DATE_SELECTED = "//android.widget.TextView[@text='%s']//following-sibling::android.view.ViewGroup//android.widget.TextView[@text='%s']";
    public static final String DYNAMIC_FILTER_TRIPS = "//android.widget.LinearLayout[@index='%s']//android.widget.LinearLayout//android.widget.TextView";
    public static final String DYNAMIC_CHECK_MANUFACTURER = "//android.widget.LinearLayout[@index='%s']//android.widget.LinearLayout//android.widget.ImageView";
    public static final String DYNAMIC_BOOKING_CHAIR = "//android.widget.LinearLayout[@resource-id='com.VCB:id/lnSeat']//ancestor::android.widget.LinearLayout[@index='%s']//android.widget.ImageView[@index='%s']";
}
