package cn.damei.utils;
import java.text.DecimalFormat;
import java.text.NumberFormat;
public class MoneyUtils {
    public static final String DEFAULT_PATTREN = "#,###.##";
    public static final String COMMA_DOUBLE_DECIMAL = "#,###.##";
    public static final String YUAN_DOUBLE_DECIMAL = "￥,###.##";
    public static final String DOLLAR_DOUBLE_DECIMAL = "$,###.##";
    public static String format(Double momey) {
        return format(momey, DEFAULT_PATTREN);
    }
    public static String format(Double money, String pattern) {
        NumberFormat nf = new DecimalFormat(pattern);
        return nf.format(money);
    }
}
