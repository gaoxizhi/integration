package net.gaox.jpa.util;

import net.gaox.jpa.base.BaseEnum;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 * <p> BaseEnum 工具类 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-25 23:11
 */
public class BaseEnumUtils {
    public static <T extends BaseEnum<E>, E> T find(E code, Class<T> enumTpe) {
        AtomicReference<T> reference = new AtomicReference<>();
        Optional.ofNullable(code).ifPresent(
                s -> reference.set(
                        Stream.of(enumTpe.getEnumConstants())
                                .filter(t -> t.getCode().equals(s))
                                .findFirst()
                                .orElse(null)
                ));
        return reference.get();
    }
}
