package cn.damei.utils.cache;
public interface Cache<K, V> {
    void put(K k, V v);
    V get(K k);
    V remove(K k);
    int size();
}
