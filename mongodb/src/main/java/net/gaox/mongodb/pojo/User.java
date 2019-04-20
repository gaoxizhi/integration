package net.gaox.mongodb.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @Description: <p>  </p>
 * @Author: gaox·Eric
 * @Date: 2019/1/21 22:50
 */
@Data
public class User {
    @Id
    private String id;
    private String name;
    private Integer age;
    private Integer grade;

}