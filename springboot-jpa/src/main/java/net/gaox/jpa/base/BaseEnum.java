package net.gaox.jpa.base;

/**
 * <p> 枚举接口 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-25 22:59
 */
public interface BaseEnum<T> {
    /**
     * 通过参数获取 枚举
     *
     * @return enum
     */
    T getCode();

}
