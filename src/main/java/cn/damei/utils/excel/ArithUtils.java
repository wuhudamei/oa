package cn.damei.utils.excel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
public final class ArithUtils {
	private static final int  DEF_DIV_SCALE  = 10;
   public static double add(double v1,double v2){
       BigDecimal b1 = new BigDecimal(Double.toString(v1));
       BigDecimal b2 = new BigDecimal(Double.toString(v2));
       return b1.add(b2).doubleValue();
   }
   public static double sub(double v1,double v2){
       BigDecimal b1 = new BigDecimal(Double.toString(v1));
       BigDecimal b2 = new BigDecimal(Double.toString(v2));
       return b1.subtract(b2).doubleValue();
   } 
   public static double mutiply(double v1,double v2){
       BigDecimal b1 = new BigDecimal(Double.toString(v1));
       BigDecimal b2 = new BigDecimal(Double.toString(v2));
       return  b1.multiply(b2).doubleValue();
   } 
   public static double div(double v1,double v2) {
	   return div(v1,v2,DEF_DIV_SCALE);
   }
   public static double div(double v1,double v2,int scale) {
	   if(scale<0){
           throw new IllegalArgumentException(
               "The scale must be a positive integer or zero");
       }
	   BigDecimal d1 = new BigDecimal(Double.toString(v1));
	   BigDecimal d2 = new BigDecimal(Double.toString(v2));
	  return d1.divide(d2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
   }
   public static double round(double v,int scale){
       if(scale<0){
           throw new IllegalArgumentException(
               "The scale must be a positive integer or zero");
       }
       BigDecimal b = new BigDecimal(Double.toString(v));
       BigDecimal one = new BigDecimal("1");
       return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
   }
   public static int roundDown(double v) {
	   BigDecimal b = new BigDecimal(Double.toString(v));
	   BigDecimal one = new BigDecimal("1");
	   return b.divide(one, 0, BigDecimal.ROUND_DOWN).intValue();
   }
   public static int roundUp(double v) {
	   BigDecimal b = new BigDecimal(Double.toString(v));
	   BigDecimal one = new BigDecimal("1");
	   return b.divide(one, 0, BigDecimal.ROUND_UP).intValue();
   }
   public static int roundHalfUp(double v){
	  return  new BigDecimal(v).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
   }
   public  static String getCurrency(double money){   
       NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.CHINA);   
       return formatter.format(money);   
   }
    public static double percentToDouble(String percent) {
        String floatNum = percent.substring(0, percent.length() - 1);
        double n = NumberUtils.toDouble(floatNum);
        return n = n / 100;
    }
    public static String formatMoney(double amount) {
        String money = StringUtils.EMPTY;
        int intPart = (int) amount;
        if (amount != intPart) {
            NumberFormat fmt = NumberFormat.getInstance();
            fmt.setMaximumFractionDigits(4);
            money = fmt.format(amount);
            money = money.replace(",", StringUtils.EMPTY);
        } else {
            money = String.valueOf(intPart);
        }
        return money;
    }
}