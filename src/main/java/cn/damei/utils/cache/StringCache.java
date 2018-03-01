package cn.damei.utils.cache;
public class StringCache {
    private static FIFOCache<String, String> cache = null;
    static {
        cache = new FIFOCache.Builder()
                .threshold(100)
                .builder();
    }
    public static void put(String k, String v) {
        cache.put(k, v);
    }
    public static String get(String k) {
        return cache.get(k);
    }
    public static String remove(String k) {
        return cache.remove(k);
    }
    public static int size() {
        return cache.size();
    }
}
