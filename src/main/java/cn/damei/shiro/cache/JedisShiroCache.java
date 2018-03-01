package cn.damei.shiro.cache;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.damei.shiro.JedisManager;
import cn.damei.utils.SerializeUtil;
import java.util.Collection;
import java.util.Set;
public class JedisShiroCache<K, V> implements Cache<K, V> {
    private static final String REDIS_SHIRO_CACHE = "shiro-cache:";
    private JedisManager jedisManager;
    private String name;
    private Logger logger = LoggerFactory.getLogger(JedisShiroCache.class);
    public JedisShiroCache(String name, JedisManager jedisManager) {
        this.name = name;
        this.jedisManager = jedisManager;
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
        byte[] byteKey = SerializeUtil.serialize(buildCacheKey(key));
        byte[] byteValue = new byte[0];
        try {
            byteValue = jedisManager.getValueByKey(byteKey);
        } catch (Exception e) {
            logger.warn("get cache error", e);
        }
        return (V) SerializeUtil.deserialize(byteValue);
    }
    @Override
    public V put(K key, V value) throws CacheException {
        V previos = get(key);
        try {
            jedisManager.saveValueByKey(SerializeUtil.serialize(buildCacheKey(key)),
                    SerializeUtil.serialize(value), -1);
        } catch (Exception e) {
            logger.warn("put cache error", e);
        }
        return previos;
    }
    @Override
    public V remove(K key) throws CacheException {
        V previos = get(key);
        try {
            jedisManager.deleteByKey(SerializeUtil.serialize(buildCacheKey(key)));
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
        return REDIS_SHIRO_CACHE + getName() + ":" + key;
    }
}
