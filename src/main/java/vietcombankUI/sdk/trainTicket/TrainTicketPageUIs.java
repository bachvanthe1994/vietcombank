package vietcombankUI.sdk.trainTicket;

public class TrainTicketPageUIs {
		public static final String DYNAMIC_HISTORY_ICON = "//android.widget.TextView[@text='%s']/following::android.widget.ImageView";

		public static final String DYNAMIC_COMBOBOX = "//android.widget.TextView[@text='%s']/parent::android.widget.LinearLayout/following-sibling::android.widget.ImageView";
		public static final String DYNAMIC_START_AND_END_TEXT = "//android.widget.TextView[@text='%s']//following::android.widget.ScrollView//android.widget.TextView[@text='%s']";
		public static final String DYNAMIC_CANCEL_ICON = "//android.widget.EditText[@text ='%s']//ancestor::android.widget.LinearLayout/android.widget.ImageView";
		public static final String DYNAMIC_CHANGE_ICON = "//android.widget.TextView[@text ='%s']//ancestor::android.widget.LinearLayout/android.widget.ImageView";

		public static final String DYNAMIC_INPUT_POINT = "//android.widget.LinearLayout[@index ='%s']/android.widget.EditText";
		public static final String DYNAMIC_VIEW_TEXT_START = "//android.widget.LinearLayout/android.widget.TextView[@resource-id='%s']";
		public static final String DYNAMIC_LIST_ITEM = "//android.support.v7.widget.RecyclerView[@index ='%s']";

		public static final String DYNAMIC_CALL_ICON = "//android.widget.TextView[@text='%s']/following::android.widget.FrameLayout/android.widget.ImageView";
	    public static final String DYNAMIC_BUTTON_LINK_LABEL_TEXT = "//android.widget.TextView[@text='%s']";
		public static final String DYNAMIC_DATE = "//android.widget.TextView[@resource-id='%s']";
		
		public static final String DYNAMIC_DATE_SELECTED="//android.view.ViewGroup[@resource-id='com.VCB:id/calendar_grid']//android.widget.TextView[@text='%s']";
		public static final String DYNAMIC_MESSAGE_INVALID="//android.widget.Toast[@index ='%s']";

	}

