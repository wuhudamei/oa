package cn.damei.utils.cache;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
public class FIFOCache<K, V> implements Cache<K, V> {
    private FIFOCache(int threshold) {
        this.threshold = threshold;
    }
    private int threshold = Integer.MAX_VALUE;
    private List<K> keys = new LinkedList<>();
    private ReentrantLock lock = new ReentrantLock();
    private Map<K, V> cache = new ConcurrentHashMap<>();
    public static class Builder {
        private int threshold = Integer.MAX_VALUE;
        public Builder threshold(int threshold) {
            this.threshold = threshold;
            return this;
        }
        public <K, V> FIFOCache<K, V> builder() {
            return new FIFOCache<>(threshold);
        }
    }
    @Override
    public void put(K k, V v) {
        checkThresholdAndSetKey(k);
        this.cache.put(k, v);
    }
    private void checkThresholdAndSetKey(K k) {
        if (k != null) {
            final ReentrantLock lock = this.lock;
            lock.lock();
            try {
                if (keys.size() == threshold) {
                    K oldK = this.keys.remove(0);
                    this.remove(oldK);
                }
                keys.add(k);
            } finally {
                lock.unlock();
            }
        }
    }
    @Override
    public V get(K k) {
        if (k == null)
            return null;
        return cache.get(k);
    }
    @Override
    public V remove(K k) {
        return this.cache.remove(k);
    }
    @Override
    public int size() {
        return cache.size();
    }
}
