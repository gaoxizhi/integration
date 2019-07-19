package net.gaox.shirojwt.util.api;


public class ApiError {
    /**
     * 异常代码
     */
    Integer code;
    /**
     * 异常信息
     */
    private String msg;

    public Integer getCode() {

        return code;
    }

    public void setCode(Integer code) {

        this.code = code;
    }

    public String getMsg() {

        return msg;
    }

    public void setMsg(String msg) {

        this.msg = msg;
    }

    /**
     * 构造器，包含异常码和异常信息
     *
     * @param code
     * @param msg
     */
    public ApiError(Integer code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

}
