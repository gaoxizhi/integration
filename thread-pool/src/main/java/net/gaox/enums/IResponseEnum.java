package net.gaox.enums;

/**
 * <p> 异常返回码枚举接口 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-06 12:50
 */
public interface IResponseEnum {
    /**
     * 获取返回码
     *
     * @return 返回码
     */
    int getCode();

    /**
     * 获取返回信息
     *
     * @return 返回信息
     */
    String getMessage();

}
