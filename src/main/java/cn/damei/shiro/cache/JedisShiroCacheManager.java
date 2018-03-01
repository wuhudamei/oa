package cn.damei.shiro.cache;
import org.apache.shiro.cache.Cache;
import cn.damei.shiro.JedisManager;
public class JedisShiroCacheManager implements ShiroCacheManager {
    private JedisManager jedisManager;
    @Override
    public <K, V> Cache<K, V> getCache(String name) {
        return new JedisShiroCache<K, V>(name, getJedisManager());
    }
    @Override
    public void destroy() {
    }
    public JedisManager getJedisManager() {
        return jedisManager;
    }
    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }
}
