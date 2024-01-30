package net.gaox.principle.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * <p> 软引用LRU缓存实现 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-30 14:01
 */
public class SoftLRUCache<K, V> {

    /**
     * 记录 key 值的顺序
     */
    private final LinkedList<K> keyList = new LinkedList<>();

    /**
     * 存放 软引用的数据
     */
    private final Map<K, SoftReference<V>> cache = new HashMap<>();

    /**
     * 最大容量
     */
    private final int capacity;

    /**
     * 加载数据的方式
     */
    private final CacheLoader<K, V> cacheLoader;

    public SoftLRUCache(int capacity, CacheLoader<K, V> cacheLoader) {
        this.capacity = capacity;
        this.cacheLoader = cacheLoader;
    }


    /**
     * 将 Value 缓存至 Cache 中
     * 如果当前 Cache 的容量超过了指定容量的大小，则会将最先保存至 Cache 中的数据丢弃掉
     *
     * @param key   键
     * @param value 值
     */
    public void put(K key, V value) {
        // 当元素数量超过容量时，将最老的数据清除
        if (keyList.size() >= capacity) {
            // eldest data
            K eldestKey = keyList.removeFirst();
            cache.remove(eldestKey);
        }
        // 如果数据已经存在，则从 key 的队列中删除
        if (keyList.contains(key)) {
            keyList.remove(key);
        }

        // 将key存放至队尾
        keyList.addLast(key);
        // 保存 软引用 SoftReference
        cache.put(key, new SoftReference<>(value));
    }

    /**
     * 根据 key 从 Cache 中获取数据
     * 如果数据存在则先从 keyList 中删除，然后再插入到队尾，否则调用 CacheLoader 的 load 方法进行加载
     *
     * @param key 键
     * @return 值
     */
    public V get(K key) {
        V value;
        // 先将 key 从 key list 中删除
        boolean success = keyList.remove(key);
        // 如果删除失败则表明该数据不存在
        if (!success) {
            // 通过 cacheLoader 对数据进行加载
            value = cacheLoader.load(key);
            // 调用 put 方法 cache 数据
            this.put(key, value);
        } else {
            // 如果删除成功，则从 cache 中返回数据
            value = cache.get(key).get();
            // 并且将 key 再次放到队尾
            keyList.addLast(key);
        }
        return value;
    }

    @Override
    public String toString() {
        return keyList.toString();
    }

}
