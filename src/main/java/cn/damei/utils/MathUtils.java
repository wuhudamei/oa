package cn.damei.utils;
public class MathUtils {
    public static boolean eq(Double n1, Double n2) {
        if (n1 == null || n2 == null) {
            return false;
        }
        return n1.equals(n2);
    }
    public static Double add(Double... numbers) {
        double sum = 0d;
        if (numbers != null && numbers.length > 0) {
            for (Double number : numbers) {
                if (number != null) {
                    sum += number;
                }
            }
        }
        return new Double(sum);
    }
}
