package net.gaox.jpa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * <p> 用户类别 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-25 22:55
 */
@Getter
@AllArgsConstructor
public enum MemberEnum {
    SUPER(1, "super", "超级管理员"),
    ADMIN(2, "admin", "管理员"),
    MEMBER(3, "member", "会员"),
    USER(4, "user", "用户"),
    GUEST(5, "guest", "来宾");

    private final Integer code;
    private final String name;
    private final String alias;


    public MemberEnum getByName(String name) {

        Optional<MemberEnum> first = Arrays.stream(MemberEnum.values()).filter(s -> s.getName().equals(name)).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        throw new IllegalArgumentException("No matching constant for [" + name + "]");
    }
}