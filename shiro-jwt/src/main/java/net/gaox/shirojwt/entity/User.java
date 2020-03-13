package net.gaox.shirojwt.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * <p> 用户表 </p>
  User
 * @author gaox·Eric
 * @date 2019/5/2 15:01
 * <p>
 * Entity 声明这个类对应了一个数据库表。必选
 * Table  声明了数据库实体对应的表信息。可选
 * 包括表名称、索引信息等。
 * 如果没有指定，则表名和实体的名称保持一致。
 * </p>
 */
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 32)
    private String name;
    @Column(length = 32)
    private String password;
    @Column(length = 32)
    private String role;
    @Column(length = 255)
    private String permission;
    @Column(length = 32)
    private String spCode;
    private Boolean state;
}