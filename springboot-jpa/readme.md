# JPA 概述

# 注解

## GeneratedValue

<table>
    <thead>
        <td style="color: darkorange">属性</td> 
        <td style="color: darkorange">属性说明</td>
        <td style="color: darkorange">属性值</td> 
        <td style="color: darkorange">属性值说明</td> 
   </thead>
    <tr>
        <td rowspan="4" style="color: red">strategy</td>    
        <td rowspan="4" style="color: indianred">主键生成策略</td>    
        <td >AUTO</td>  
        <td >程序控制（默认）</td>  
    </tr>
    <tr>
        <td >IDENTITY</td>  
        <td >数据库自动生成，即采用数据库ID自增长的方式，Oracle不支持这种方式</td>  
    </tr>
    <tr>
        <td >SEQUENCE</td>  
        <td >数据库的序列产生主键，通过 SequenceGenerator 注解指定序列名，mysql不支持这种方式</td>  
    </tr>
    <tr>
        <td >TABLE</td>  
        <td >通过特定的数据库表产生主键，使用该策略可以使应用更易于数据库移植</td>  
    </tr>
</table>

## org.hibernate.annotations.Table

<table>
    <thead>
        <td style="color: darkorange">属性</td> 
        <td style="color: darkorange">属性说明</td>
        <td style="color: darkorange">属性值</td> 
        <td style="color: darkorange">属性值说明</td> 
   </thead>
    <tr>
        <td style="color: red">appliesTo</td>    
        <td style="color: indianred">表名称</td>    
        <td >user</td>  
        <td >表名称（默认与类名称一致）</td>  
    </tr>
    <tr>
        <td style="color: red">comment</td>    
        <td style="color: indianred">表注释</td>    
        <td >用户表</td>  
        <td >表注释（自定义）</td>  
    </tr>
</table>


## Entity 声明这个类对应了一个数据库表（必须）

## Table  声明了数据库实体对应的表信息（可选）

> 包括表名称、索引信息等。\
> 如果表名称没有指定，则表名和实体的名称保持一致。
