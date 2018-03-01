package cn.damei.shiro;
import com.google.common.collect.Maps;
import cn.damei.shiro.cache.CacheData;
import java.util.Map;
public class MemoryManager {
    private final Map<String, CacheData> cacheMap = Maps.newConcurrentMap();
    public <T> T getValueByKey(final String key) {
        CacheData cached = cacheMap.get(key);
        if (cached == null || (cached.getExpiresTime() > 0 && cached.getExpiresTime() + cached.getCreateTime() < System.currentTimeMillis())) {
            cacheMap.remove(key);
            return null;
        }
        return (T) cached.getData();
    }
    public void deleteByKey(final String key) {
        cacheMap.remove(key);
    }
    public void saveValueByKey(final String key, Object data, long expireTime) {
        CacheData cacheData = new CacheData.Builder().data(data, expireTime).builder();
        cacheMap.put(key, cacheData);
    }
}
