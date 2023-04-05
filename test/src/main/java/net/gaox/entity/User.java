package net.gaox.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p> 用户 </p>
 *
 * @author gaox·Eric
 * @date 2019/5/2 15:01
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {

    private Long id;

    private String name;

    private String spCode;

}