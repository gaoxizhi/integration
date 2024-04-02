package net.gaox.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> 枚举工具 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-01 13:00
 */
public class EnumUtils {

    public static <E extends Enum<E>> boolean isValueOf(E enumValue, Class<E> enumClass) {
        return enumValue != null && Arrays.stream(enumClass.getEnumConstants()).anyMatch(e -> e.equals(enumValue));
    }

    public static <E extends Enum<E>> List<String> getAllValuesAsString(Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
