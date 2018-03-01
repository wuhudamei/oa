package cn.damei.utils.excel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelTitle {
	String title();
	int order() default 9999;
}