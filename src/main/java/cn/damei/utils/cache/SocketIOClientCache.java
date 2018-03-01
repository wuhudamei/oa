package cn.damei.utils.cache;
import com.corundumstudio.socketio.SocketIOClient;
public class SocketIOClientCache {
    private static FIFOCache<String, SocketIOClient> cache = null;
    static {
        cache = new FIFOCache.Builder()
                .threshold(100)
                .builder();
    }
    public static void put(String k, SocketIOClient v) {
        cache.put(k, v);
    }
    public static SocketIOClient get(String k) {
        return cache.get(k);
    }
    public static SocketIOClient remove(String k) {
        return cache.remove(k);
    }
    public static int size() {
        return cache.size();
    }
}
