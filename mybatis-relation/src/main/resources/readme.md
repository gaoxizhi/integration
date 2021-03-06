# resultType：
### 作用：
将查询结果按照sql列名pojo属性名一致性映射到pojo中。

### 场合：
常见一些明细记录的展示，比如用户购买商品明细，将关联查询信息全部展示在页面时，此时可直接使用resultType将每一条记录映射到pojo中，在前端页面遍历list（list中是pojo）即可。

# resultMap：
使用association和collection完成一对一和一对多高级映射（对结果有特殊的映射要求）。
## association：
### 作用：
将关联查询信息映射到一个pojo对象中。
### 场合：
为了方便查询关联信息可以使用association将关联订单信息映射为用户对象的pojo属性中，比如：查询订单及关联用户信息。
使用resultType无法将查询结果映射到pojo对象的pojo属性中，根据对结果集查询遍历的需要选择使用resultType还是resultMap。

## collection：
### 作用：
将关联查询信息映射到一个list集合中。
### 场合：
为了方便查询遍历关联信息可以使用collection将关联信息映射到list集合中，比如：查询用户权限范围模块及模块下的菜单，可使用collection将模块映射到模块list中，将菜单列表映射到模块对象的菜单list属性中，这样的作的目的也是方便对查询结果集进行遍历查询。

>*如果使用resultType无法将查询结果映射到list集合中。*

>参考 https://www.cnblogs.com/selene/p/4627446.html#_label0