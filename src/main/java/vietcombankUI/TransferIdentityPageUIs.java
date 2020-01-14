package vietcombankUI;

public class TransferIdentityPageUIs {

    public static final String DYNAMIC_IDENTIFITION = "//android.widget.LinearLayout[@index = '%s']/android.widget.TextView[@resource-id=\"%s\"]";
    public static final String DYNAMIC_TEXT_HEADER = "//android.widget.LinearLayout//android.widget.TextView";
    public static final String DYNAMIC_PASSWORD = "//android.widget.LinearLayout//android.widget.EditText";
    public static final String DYNAMIC_HOME_ICON = "//android.widget.TextView[@text = '%s']//following::android.widget.FrameLayout//android.widget.ImageView";
    public static final String DYNAMIC_TITLE_CONFIRM_TRANSFER = "//android.widget.LinearLayout[@index='%s']//android.widget.LinearLayout//android.widget.TextView";
    public static final String DYNAMIC_MONEY_USD = "//android.widget.FrameLayout[@index='%s']//android.widget.LinearLayout//android.widget.LinearLayout//android.widget.TextView";
    public static final String DYNAMIC_TEXT_CONFIM = "//android.widget.TextView[contains(text(),'Quý khách vui lòng nhập OTP đã được gửi về số điện thoại')]";
    public static final String DYNAMIC_ACCOUNT_CONFIRM = "//android.widget.LinearLayout[@index='%s']//preceding-sibling::android.widget.LinearLayout//android.widget.LinearLayout//android.widget.TextView";
}
