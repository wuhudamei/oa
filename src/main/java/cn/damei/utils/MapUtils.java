package cn.damei.utils;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.Map;
public class MapUtils {
    private MapUtils() {
    }
    public static void putNotNull(Map<String, Object> map, String key, Object value) {
        if (map == null || value == null)
            return;
        if (value instanceof String) {
            if (StringUtils.isNotBlank((String) value))
                map.put(key, ((String) value).trim());
        } else {
            map.put(key, value);
        }
    }
    public static void putOrElse(Map<String, Object> map, String key, Object value, Object defaultVal) {
        if (map == null || StringUtils.isBlank(key))
            return;
        if (value == null) {
            map.put(key, defaultVal);
            return;
        }
        if (value instanceof String) {
            if (StringUtils.isNotBlank((String) value))
                map.put(key, ((String) value).trim());
            else
                map.put(key, defaultVal);
        } else {
            map.put(key, value);
        }
    }
    public static <K, V> Map<K, V> of(K k, V v) {
        Map<K, V> map = new HashMap<>();
        checkNotNullAndPut(map, k, v);
        return map;
    }
    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2) {
        Map<K, V> map = new HashMap<>();
        checkNotNullAndPut(map, k1, v1);
        checkNotNullAndPut(map, k2, v2);
        return map;
    }
    private static <K, V> void checkNotNullAndPut(Map<K, V> map, K k, V v) {
        if (k != null && v != null) {
            map.put(k, v);
        }
    }
}
