package net.gaox.principle.cache;

/**
 * <p> 加载数据的方式 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-30 13:28
 */
@FunctionalInterface
public interface CacheLoader<K, V> {

    /**
     * 加载数据
     *
     * @param k key
     * @return value
     */
    V load(K k);

}
