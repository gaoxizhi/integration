package net.gaox.relation.model.dto;


import lombok.Data;
import net.gaox.relation.model.enums.EnumSex;

/**
 * <p> 用户基础信息 </p>
 *
 * @author gaox·Eric
 * @date 2019/7/10 21:31
 */
@Data
public class UserBaseDTO {
    private Long id;
    private String userName;
    private EnumSex sex;
    private String address;
}
