package net.gaox.base.format;

/**
 * <p> equals和== </p>
 *
 * @author gaox·Eric
 * @date 2020/10/15 16:02
 */
public class EqualsTest {
    public static void main(String[] args) {
        sameHashCode("精彩", "笔记");
        // hashCode 相同
        sameHashCode("重地", "通话");
    }

    public static void sameHashCode(String str1, String str2) {
        System.out.println(String.format("%s：%d | %s：%d", str1, str1.hashCode(), str2, str2.hashCode()));
        System.out.println(String.format("%s.equals(%s)：%s", str1, str2, str1.equals(str2)));
    }
}
