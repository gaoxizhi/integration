package net.gaox.livechat.entity;

import lombok.Data;

import javax.persistence.*;


/**
 * @author gaox·Eric
 * <p> 用户表 </p>
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
    /**
     * 声明了实体唯一标识对应的属性
     */
    @Id
    /**
     * GeneratedValue  注解的strategy属性提供四种值：
     *
     * –AUTO： 主键由程序控制，是默认选项，不设置即此项。
     *
     * –IDENTITY：主键由数据库自动生成，即采用数据库ID自增长的方式，Oracle不支持这种方式。
     *
     * –SEQUENCE：通过数据库的序列产生主键，通过@SequenceGenerator 注解指定序列名，mysql不支持这种方式。
     *
     * –TABLE：通过特定的数据库表产生主键，使用该策略可以使应用更易于数据库移植。
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 声明实体属性的表字段的定义。可选
     * 默认字符串长度255
     */
    @Column(length = 32)
    private String name;
    @Column(length = 32)
    private String spCode;
    private Boolean state;
}