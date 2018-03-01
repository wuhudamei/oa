package cn.damei.utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class JsonUtils {
    public static final String STRING_EMPTY = "";
    public static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
    private JsonUtils() {
    }
    public static String toJson(Object obj) {
        String json;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return STRING_EMPTY;
        }
        return json;
    }
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static <K, V> Map<K, V> fromJsonAsMap(String json, Class<K> keyClass, Class<V> valueClass) {
        JavaType type = mapper.getTypeFactory().
                constructMapType(Map.class, keyClass, valueClass);
        try {
            Map<K, V> map = mapper.readValue(json, type);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
    public static <T> List<T> fromJsonAsList(String json, Class<T> clazz) {
        JavaType type = mapper.getTypeFactory().
                constructCollectionType(List.class, clazz);
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}