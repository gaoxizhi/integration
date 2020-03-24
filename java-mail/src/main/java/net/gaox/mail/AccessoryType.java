package net.gaox.mail;

/**
 * <p> 附件类型 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/24 22:27
 */
public enum AccessoryType {
    /**
     * 1 图片
     * 2 文件
     */
    PICTURE("picture"),
    FILE("file");

    private String type;

    AccessoryType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}