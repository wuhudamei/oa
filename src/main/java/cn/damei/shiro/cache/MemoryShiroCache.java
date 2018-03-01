package cn.damei.shiro.cache;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.damei.shiro.MemoryManager;
import java.util.Collection;
import java.util.Set;
public class MemoryShiroCache<K, V> implements Cache<K, V> {
    public static final String MEMORY_SHIRO_CACHE = "shiro-cache:";
    private MemoryManager cacheMemoryManager;
    private String name;
    private Logger logger = LoggerFactory.getLogger(MemoryShiroCache.class);
    public MemoryShiroCache(String name, MemoryManager cacheMemoryManager) {
        this.name = name;
        this.cacheMemoryManager = cacheMemoryManager;
    }
    public String getName() {
        if (name == null)
            return "";
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @SuppressWarnings("unchecked")
    @Override
    public V get(K key) throws CacheException {
        V value = null;
        try {
            value  = cacheMemoryManager.getValueByKey(buildCacheKey(key));
        } catch (Exception e) {
            logger.warn("get cache error", e);
        }
        return value;
    }
    @Override
    public V put(K key, V value) throws CacheException {
        V previos = get(key);
        try {
            String cacheKey = buildCacheKey(key);
            cacheMemoryManager.saveValueByKey(cacheKey,
                    value, -1);
        } catch (Exception e) {
            logger.warn("put cache error", e);
        }
        return previos;
    }
    @Override
    public V remove(K key) throws CacheException {
        V previos = get(key);
        try {
            String cacheKey = buildCacheKey(key);
            cacheMemoryManager.deleteByKey(cacheKey);
        } catch (Exception e) {
            logger.warn("remove cache error", e);
        }
        return previos;
    }
    @Override
    public void clear() throws CacheException {
    }
    @Override
    public int size() {
        if (keys() == null)
            return 0;
        return keys().size();
    }
    @Override
    public Set<K> keys() {
        return null;
    }
    @Override
    public Collection<V> values() {
        return null;
    }
    private String buildCacheKey(Object key) {
        return MEMORY_SHIRO_CACHE + key;
    }
}
