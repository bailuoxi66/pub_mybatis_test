# 		Mybatis

## 前沿

​		mybatis是一款优秀的持久层框架（软件开发中的一套解决方案，不同框架解决不同的问题，框架封装了很多细节，是开发者可以使用极简的方式实现功能，需要业务层配合使用，共同完成特定功能）

​		三层架构（表现层、业务层、持久层）

​		<img src="/Users/liangzhengtao/Library/Application Support/typora-user-images/image-20201124104922960.png" alt="image-20201124104922960" style="zoom:40%;" />

​		JDBC技术：Connection、PreparedStatement、ResultSet

​		Spring的JdbcTemplate：Spring中对jdbc的简单封装

​		Apache的DButils：它和spring的JdbcTemplate很像，都是简单封装

​		以上都不是框架：JDBC是规范，Spring的JdbcTemplate和Apache的DButils都是工具类，是对规范的一个简单实现

​		mybatis 是一个优秀的基于 java 的持久层框架，它内部封装了 jdbc，使开发者只需要关注 sql 语句本身， 而不需要花费精力去处理加载驱动、创建连接、创建 statement 等繁杂的过程

## 一、mybatis环境搭建

### 1.1 数据库理解及其错误解决

```
[root@host]# mysql -u root -p   
Enter password:******  # 登录后进入终端

mysql> create DATABASE RUNOOB;
mysql> drop database RUNOOB;
```

```
在数据库外面可使用，无需进入数据库内
mysqladmin -u 用户 -p '旧密码' '新密码'
如果是新数据库，未设置密码，可使用
mysqladmin -u root -p password  密码
```

#### 1.1.1 密码忘记：

https://segmentfault.com/a/1190000020679306

1、先找到m y.cnf位置

![image-20201203140328364](/Users/liangzhengtao/Library/Application Support/typora-user-images/image-20201203140328364.png)

![image-20201203140046634](/Users/liangzhengtao/Library/Application Support/typora-user-images/image-20201203140046634.png)

![image-20201203140515555](/Users/liangzhengtao/Library/Application Support/typora-user-images/image-20201203140515555.png)

#### 1.2 重启方式

```mysql
cd /usr/local/Cellar/mysql/8.0.22_1/support-files
mysql.server restart
```

#### 1.3 进入mysql

![image-20201203142122225](/Users/liangzhengtao/Library/Application Support/typora-user-images/image-20201203142122225.png)

框架：配置文件。最好的方式：看官网文档

### 1.2 如何获取

maven：

Github：https://github.com/mybatis/mybatis-3

中文文档：https://mybatis.org/mybatis-3/zh/index.html

### 1.3 持久化

数据持久化

```
持久化就是将程序的数据在持久状态和瞬时状态转化的过程
内存：断电即失
数据库（jdbc），io文件的持久化
生活：冷藏。罐头
```

为什么需要持久化？

```
有一些对象，不能让他丢掉
内存太贵了
```

### 1.4 持久层

Dao层，Service层，Controller层

```
完成持久化工作的代码块
层界限十分明显
```

### 1.5 为什么需要Mybatis？

```
方便
传统的JDBC代码块太复杂了，简化，框架自动化
帮程序员将数据存入到数据库中
不用mybatis也可以，更容易上手。技术没有高低之分。
```

### 1.6 第一个Mybatis程序

思路：搭建环境-》导入mybatis-〉编写代码-》测试

#### 1.6.1 搭建数据库

```mysql
CREATE DATABASE `mybatis`;
USE `mybatis`;
CREATE TABLE `user`(
	`id` INT(20) NOT NULL PRIMARY KEY,
	`name` VARCHAR(30) DEFAULT NULL,
	`pwd` VARCHAR(30) DEFAULT NULL
)ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO `user`(`id`, `name`, `pwd`) VALUES
(1, '狂神', '123456'),
(2, '张三', '123456'),
(3, '李四', '123456')
```

#### 1.6.2 新建项目

1、新建一个普通的maven项目

2、删除src目录 

3、导入maven项目

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <!-- 父工程-->
    <groupId>org.example</groupId>
    <artifactId>mybatis-study</artifactId>
    <version>1.0-SNAPSHOT</version>

    <!-- 导入依赖-->
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.6</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.6</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

4、创建一个模块

编写mybatis的核心配置文件(XML 配置文件中包含了对 MyBatis 系统的核心设置，包括获取数据库连接实例的数据源（DataSource）以及决定事务作用域和控制方式的事务管理器（TransactionManager）

路径：src/main/resources/mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<!--configuration核心配置文件-->
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
                <property name="username" value="root"/>
                <property name="password" value="12345"/>
            </dataSource>
        </environment>
    </environments>
</configuration>
```

编写mybatis工具类

路径：src/main/java/com.example.utils/MybatisUtils

```java
package com.example.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
//获取sqlSessionFactory对象构建sqlSession
public class MybatisUtils {

    private static SqlSessionFactory sqlSessionFactory;
    static {
        try {
            //使用mybatis的第一步
            //获取sqlSessionFactory对象
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    //既然有了 SqlSessionFactory，顾名思义，我们可以从中获得 SqlSession 的实例。SqlSession
    //提供了在数据库执行 SQL 命令所需的所有方法。你可以通过 SqlSession 实例来直接执行已映射的 SQL 语句
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}
```

#### 1.6.3 编写代码

实体类

```java
package com.example.pojo;

//实体类
public class User {
    private int id;
    private String name;
    private String pwd;

    public User(){
    }
    public User(int id, String name, String pwd){
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
```

Dao接口

```java
package com.example.dao;

import com.example.pojo.User;

import java.util.List;

public interface UserMapper {
    //查询全部用户
    List<User> getUserList();

    //根据id查询用户
    User getUserById(int id);

    //insert一个用户
    int addUser(User user);

    //update一个用户
    int updateUser(User user);

    //delete一个用户
    int deleteUser(int id);
}
```

接口实现类(由原来的UserDaoImp转化为Mapper配置)

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--namespace绑定一个对应的Dao/Mapper接口-->
<mapper namespace="com.example.dao.UserMapper">
    <!--    select查询语句-->
    <select id="getUserList" resultType="com.example.pojo.User">
        select * from mybatis.user
    </select>

    <select id="getUserById" parameterType="int" resultType="com.example.pojo.User">
        select * from mybatis.user where id = #{id}
    </select>

<!--    对象中的属性，可以直接取出来-->
    <insert id="addUser" parameterType="com.example.pojo.User">
        insert into mybatis.user (id, name, pwd) values(#{id}, #{name}, #{pwd});
    </insert>

    <update id="updateUser" parameterType="com.example.pojo.User">
        update mybatis.user set name=#{name},pwd=#{pwd} where id=#{id}
    </update>

    <delete id="deleteUser" parameterType="int">
        delete from mybatis.user where id=#{id};
    </delete>
</mapper>
```

#### 1.6.4 测试

##### 注意点1：

```java
org.apache.ibatis.binding.BindingException: Type interface com.example.dao.UserDao is not known to the MapperRegistry.
```

记得添加核心配置文件项mappers

```xml
<mappers>
    <mapper resource="com/example/dao/UserMapper.xml"/>
</mappers>
```

##### 注意点2:

```verilog
org.apache.ibatis.exceptions.PersistenceException: 
### Error querying database.  Cause: com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure

Last packet sent to the server was 284 ms ago.
### The error may exist in com/example/dao/UserMapper.xml
### The error may involve com.example.dao.UserDao.getUserList
### The error occurred while executing a query
### Cause: com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure
```

```xml
<dependencies>
    <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>        //请注意保证版本一致。调整为8.0.21即可解决问题
        <version>5.1.6</version>
    </dependency>

    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.2</version>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

##### 注意点3:

```
org.apache.ibatis.builder.IncompleteElementException: Could not find result map 'com.example.pojo.User' referenced from 'com.example.dao.UserDao.getUserList'
```

```xml
<select id="getUserList" resultMap="com.example.pojo.User">     //将resultMap替换成resultType可以解决问题
        select * from mybatis.user
</select>
```

##### 注5(资源导出失败):

```xml
<!--    在build中配置resource，来防止我们资源导出失败的问题-->
    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>
```



##### junit测试

```java
package com.example.dao;

import com.example.pojo.User;
import com.example.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserDaoTest {

    @Test
    public void test(){
        //第一步获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //方式一：getMapper
        UserMapper userDao = sqlSession.getMapper(UserMapper.class);
        List<User> userList =  userDao.getUserList();

        for (User user : userList) {
            System.out.println(user);
        }

        //关闭SqlSession
        sqlSession.close();
    }

    @Test
    public void getUserById(){
        //第一步获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //方式一：getMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.getUserById(1);
        System.out.println(user);

        //关闭SqlSession
        sqlSession.close();
    }

    //增删改需要提交事务
    @Test
    public void addUser(){
        //第一步获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //方式一：getMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int res = mapper.addUser(new User(4, "哈哈", "111223"));
        if(res > 0){
            System.out.println("插入成功!");
        }
        //提交事务
        sqlSession.commit();
        //关闭SqlSession
        sqlSession.close();
    }

    //增删改需要提交事务
    @Test
    public void updateUser(){
        //第一步获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //方式一：getMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int res = mapper.updateUser(new User(4, "呵呵", "111223"));
        if(res > 0){
            System.out.println("update success!");
        }
        //提交事务
        sqlSession.commit();
        //关闭SqlSession
        sqlSession.close();
    }

    //增删改需要提交事务
    @Test
    public void deleteUser(){
        //第一步获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //方式一：getMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int res = mapper.deleteUser(4);
        if(res > 0){
            System.out.println("delete success!");
        }
        //提交事务
        sqlSession.commit();
        //关闭SqlSession
        sqlSession.close();
    }
}
```

## 二、Crud

### 2.1 namespace

namespace中的包名要和Dao/Mapper接口的包名一致

### 2.2 SELECT

选择，查询语句

```java
id：就是对应的namespace中的方法名
resultType：sql语句执行的返回值
```

增删改需要提交事务

```java
//提交事务
sqlSession.commit();
```

### 2.3 分析错误

```
标签不要配错
resource绑定mapper，需要使用路径名
程序配置文件，必须满足规范
NullPointerException,没有注册到资源
输出的xml文件中存在中文乱码
maven资源没有导出文件
```

### 2.4 万能的Map

假设，我们的实体类，或者数据库中的表，字段或者参数过多，我们可以考虑map

```java
    //万能的Map
    int addUer2(Map<String, Object> map);
```

```xml
    <!--    对象中的属性，可以直接取出来-->
    <insert id="addUser2" parameterType="map">
        insert into mybatis.user (id, name, pwd) values(#{userid}, #{username}, #{passwd});
    </insert>
```

```java
    @Test
    public void addUser2(){
        //第一步获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //方式一：getMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userid", 5);
        map.put("username", "你好");
        map.put("passwd", "1223343");
        mapper.addUer2(map);

        //提交事务
        //sqlSession.commit();
        //关闭SqlSession
        sqlSession.close();
    }
```

Map传递参数，直接在sql中取出key即可

对象传递参数，直接在sql中取对象的属性即可

只有一个基本类型参数的情况下，可以直接在sql中取到

多个参数用Map，或者**注解！**

### 2.5 模糊查询怎么写

#### 2.5.1在java中确认书写通配符（'?'）

```java
List<User> userList = mapper.getUserLike("%李%");
```

#### 2.5.2在mysql中确认书写通配符（'?'）

```xml
List<User> userList = mapper.getUserLike("李");

<!--    select查询语句-->
    <select id="getUserLike" resultType="com.example.pojo.User">
        select * from mybatis.user where name like "%"#{value}"%"
    </select>
```



接口：

```java
    //模糊查询
    List<User> getUserLike(String value);
```

Mapper_select:

```java
    <!--    select查询语句-->
    <select id="getUserLike" resultType="com.example.pojo.User">
        select * from mybatis.user where name like "%"#{value}"%"
    </select>
```

Test:

```java
    @Test
    public void getUserLike(){
        //第一步获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //方式一：getMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.getUserLike("李");

        for (User user : userList){
            System.out.println(user);
        }
        //关闭SqlSession
        sqlSession.close();
    }
```

#### 2.5.3 注意点1:

org.apache.ibatis.binding.BindingException: Invalid bound statement (not found)

解决：**https://blog.csdn.net/sundacheng1989/article/details/81630370**

<img src="https://img-blog.csdn.net/20180813151254454?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N1bmRhY2hlbmcxOTg5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70" alt="img" style="zoom:70%;" />

还有一个可能的地方是（这就很神奇了，真的是接口拼错了，哎，认真点吧）：

<img src="/Users/liangzhengtao/Library/Application Support/typora-user-images/image-20201127191357535.png" alt="image-20201127191357535" style="zoom:50%;" />

 ### 2.6 配置解析

#### 2.6.1 核心配置文件

mybatis-config.xml

mybatis的配置文件包含了会深深影响mybatis行为的设置和属性信息

```xml
configuration（配置）
	properties（属性）
	settings（设置）
	typeAliases（类型别名）
	typeHandlers（类型处理器）
	objectFactory（对象工厂）
	plugins（插件）
	environments（环境配置）
	environment（环境变量）
	transactionManager（事务管理器）
	dataSource（数据源）
	databaseIdProvider（数据库厂商标识）
	mappers（映射器）
```

#### 2.6.2 环境配置（environments）

mybatis可以配置成适应多种环境

**不过要记住：尽管可以配置多个环境，但每个 SqlSessionFactory 实例只能选择一种环境。**

学会使用配置多套运行环境！

mybatis默认的事物管理器就是JDBC，连接池：POOLED

#### 2.6.3 属性（properties）

我们可以通过properties属性来实现引用配置文件

这些属性可以在外部进行配置，并可以进行动态替换。你既可以在典型的 Java 属性文件中配置这些属性，也可以在 properties 元素的子元素中设置

<img src="/Users/liangzhengtao/Desktop/截屏2020-11-28 下午4.28.07.png" alt="截屏2020-11-28 下午4.28.07" style="zoom:50%;" />

编写一个配置文件

dp.properties

```properties
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis?useSSL=true;useUnicode=true;characterEncoding=UTF-8
username=root
password=12345
```

在核心配置文件中引入

```xml
<!--    引入一个配置文件，可以不用新增属性，进行自闭和-->
    <properties resource="db.properties"/>
```

可以直接引入外部文件

可以在其中增加一些属性配置

如果两个文件有同一个字段，优先使用外部配置

```xml
<!--    引入一个配置文件，可以不用新增属性，进行自闭和，优先使用外部-->
    <properties resource="db.properties">
        <property name="password" value="111"/>
    </properties>
```

#### 2.6.4 类型别名（typeAliases）

类型别名可为 Java 类型设置一个缩写名字。 它仅用于 XML 配置，意在降低冗余的全限定类名书写

```xml
<!--    可以给实体类起别名-->
    <typeAliases>
        <typeAlias type="com.example.pojo.User" alias="User"/>
    </typeAliases>
```

也可以指定一个包名，MyBatis 会在包名下面搜索需要的 Java Bean

```xml
    <typeAliases>
        <package name="com.example.pojo"/>    
    </typeAliases>
```

```xml
<!--namespace绑定一个对应的Dao/Mapper接口-->
<mapper namespace="com.example.dao.UserMapper">
    <!--    select查询语句-->
    <select id="getUserList" resultType="user">    //user以前为com.example.pojo.User
        select * from mybatis.user
    </select>
```

在实体类比较少的时候，使用第一种方式

如果实体类十分多，建议使用第二种

第一种可以DIY别名，第二种不行，如果非要改，需要在实体上增加注解

```java
//实体类
@Alias("hello")
public class User {
    private int id;
    private String name;
    private String pwd;
```

#### 2.6.5 设置

这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为

<img src="/Users/liangzhengtao/Library/Application Support/typora-user-images/image-20201128201745739.png" alt="image-20201128201745739" style="zoom:50%;" />

<img src="/Users/liangzhengtao/Library/Application Support/typora-user-images/image-20201128201833210.png" alt="image-20201128201833210" style="zoom:50%;" />

#### 2.6.6 映射器

MapperRegistry:注册绑定

第一种方式：没有第二个所产生的问题【推荐使用】

```xml
<!--    每一个Mapper.XML都需要在Mybatis-config.xml核心配置文件里面注册-->
    <mappers>
        <mapper resource="com/example/dao/UserMapper.xml"/>
    </mappers>
```

第二种方式：使用class绑定注册

```xml
    <mappers>
<!--        <mapper resource="com/example/dao/UserMapper.xml"/>-->
        <mapper class="com.example.dao.UserMapper"/>
    </mappers>
```

可能会报错，因为有一些要求

注意点

- 接口和他的Mapper配置文件必须同名！
- 接口和他的Mapper配置文件必须在同一个包下！

第三种方式：使用package绑定注册

```xml
<!--    每一个Mapper.XML都需要在Mybatis-config.xml核心配置文件里面注册-->
    <mappers>
<!--        <mapper resource="com/example/dao/UserMapper.xml"/>-->
<!--        <mapper class="com.example.dao.UserMapper"/>-->
        <package name="com.example.dao"/>
    </mappers>
```

注意点

- 接口和他的Mapper配置文件必须同名！
- 接口和他的Mapper配置文件必须在同一个包下！

<img src="/Users/liangzhengtao/Library/Application Support/typora-user-images/image-20201128205030428.png" alt="image-20201128205030428" style="zoom:50%;" />

倘使真的出现了如上的问题：请检查mapper是否说明绑定具体信息

#### 2.6.7 生命周期和作用域

<img src="/Users/liangzhengtao/Library/Application Support/typora-user-images/image-20201128222939571.png" alt="image-20201128222939571" style="zoom:50%;" />

生命周期和作用域是至关重要的，因为错误的使用会导致非常严重的**并发问题**。

SqlSessionFactorBuilder:

- 一旦创建了SqlSessionFactory，就不需要它了
- 局部变量

SqlSessionFactory：

- 说白了就是可以想象为：数据库连接池
- SqlSessionFactory一旦被创建就应该应用的运行期间一直存在，没有任何理由丢弃它或者重新创建另一个实例
- 因此SqlSessionFactory的最佳作用域就是应用作用域
- 最简单的就是使用单例模式或者静态单例模式

SqlSession：

- 连接到连接池的一个请求！
- 用完之后需要赶紧关闭，否则资源被占用
- SqlSession的实例不是线程安全的，因此是不能被共享的，

<img src="/Users/liangzhengtao/Library/Application Support/typora-user-images/image-20201128223628185.png" alt="image-20201128223628185" style="zoom:50%;" />

这里面的每一个Mapper，就代表一个具体的业务！（可以理解为为了执行一个sql）

## 三、解决属性名和字段名不一致的问题

### 3.1 问题

数据库中的字段

<img src="/Users/liangzhengtao/Library/Application Support/typora-user-images/image-20201129001633715.png" alt="image-20201129001633715" style="zoom:50%;" />

新建一个项目，拷贝之前的，测试实体类字段不一致的情况

```java
public class User {
    private int id;
    private String name;
    private String passwd;
```

测试出现的问题！

```sql
User{id=1, name='狂神', passwd='null'}
User{id=2, name='张三', passwd='null'}
User{id=3, name='李四', passwd='null'}

select * from mybatis.user 
//类型处理器
select id,name,pwd from mybatis.user
```

解决方法：

- 起别名

  ```sql
      <!--    select查询语句-->
      <select id="getUserList" resultType="user">
          select id,name,pwd as passwd from mybatis.user
      </select>
  ```

- 使用resultMap

### 3.2 resultMap

结果集映射

```
id	name	pwd
id	name	passwd
```

```xml
    <!--    结果集映射-->
    <resultMap id="UserMap" type="user">
        <result column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="pwd" property="passwd"/>
    </resultMap>

    <!--    select查询语句-->
    <select id="getUserList" resultMap="UserMap">
        select * from mybatis.user
    </select>
```

- resultMap元素是最重要强大的元素
- resultMap的设计思想，对于简单的语句根本不需要配置显示的映射，而对于复杂的语句，只需要描述它们的关系即可
- resultMap最优秀地方在于，虽然你已经对它相当了解了，但是根本不需要显示用到他们
- 如果世界总是这么简单就好了

## 四、日志

### 4.1 日志工厂

如果一个数据库操作，出现了异常，我们需要排错，日志就是最好的工具

曾经：sout、debug

现在：日志工厂！

mybatis内置了日志工厂

<img src="/Users/liangzhengtao/Library/Application Support/typora-user-images/image-20201129014410697.png" alt="image-20201129014410697" style="zoom:50%;" />

SLF4J | LOG4J【掌握】 | LOG4J2 | JDK_LOGGING | COMMONS_LOGGING | STDOUT_LOGGING【掌握】 | NO_LOGGING

在mybatis中具体使用那个日志实现，设置就好

STDOUT_LOGGING标准日志输出

在mybatis核心配置文件中，配置我们的日志

<img src="/Users/liangzhengtao/Library/Application Support/typora-user-images/image-20201129015411419.png" alt="image-20201129015411419" style="zoom:50%;" />

```xml
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
```

### 4.2 Log4j

Log4j是[Apache](https://baike.baidu.com/item/Apache/8512995)的一个开源项目

- 使用Log4j，我们可以控制日志信息输送的目的地是[控制台](https://baike.baidu.com/item/控制台/2438626)、文件、[GUI](https://baike.baidu.com/item/GUI)组件
- 也可以控制每一条日志的输出格式
- 通过定义每一条日志信息的级别，我们能够更加细致地控制日志的生成过程
- 这些可以通过一个[配置文件](https://baike.baidu.com/item/配置文件/286550)来灵活地进行配置

#### 4.2.1、引入log4j

```xml
<!-- https://mvnrepository.com/artifact/log4j/log4j -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
```

#### 4.2.2 添加log4j.properties文件

```properties
#############
# 输出到控制台
#############

# log4j.rootLogger日志输出类别和级别：只输出不低于该级别的日志信息DEBUG < INFO < WARN < ERROR < FATAL
# DEBUG：日志级别     CONSOLE：输出位置自己定义的一个名字       logfile：输出位置自己定义的一个名字
log4j.rootLogger=DEBUG,CONSOLE,logfile
# 配置CONSOLE输出到控制台
log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender
log4j.appender.CONSOLE.Target = System.out
log4j.appender.CONSOLE.Threshold = DEBUG
# 配置CONSOLE设置为自定义布局模式
log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout
# 配置CONSOLE日志的输出格式
log4j.appender.CONSOLE.layout.ConversionPattern=[%c]-%m%n

################
# 输出到日志文件中
################

# 配置logfile输出到文件中 文件大小到达指定尺寸的时候产生新的日志文件
log4j.appender.logfile=org.apache.log4j.RollingFileAppender
# 保存编码格式
log4j.appender.logfile.Encoding=UTF-8
# 输出文件位置此为项目根目录下的logs文件夹中
log4j.appender.logfile.File=./log/lemon.log
# 后缀可以是KB,MB,GB达到该大小后创建新的日志文件
log4j.appender.logfile.MaxFileSize=10MB
# 设置滚定文件的最大值3 指可以产生root.log.1、root.log.2、root.log.3和root.log四个日志文件
log4j.appender.logfile.MaxBackupIndex=3
# 配置logfile为自定义布局模式
log4j.appender.logfile.layout=org.apache.log4j.PatternLayout
log4j.appender.logfile.layout.ConversionPattern=[%p][%d{yy-MM-dd}][%c]%m%n

#日志输出级别
log4j.logger.org.mybatis=DEBUG
log4j.logger.java.sql=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.ResultSet=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG
```

#### 4.2.3 配置日志LOG4J

```xml
    <settings>
        <setting name="logImpl" value="LOG4J"/>
    </settings>
```

#### 4.2.4 log4j的使用

<img src="/Users/liangzhengtao/Library/Application Support/typora-user-images/image-20201129104815890.png" alt="image-20201129104815890" style="zoom:40%;" />

简单使用

1、在要使用Log4j的类中，导入包import org.apache.log4j.Logger;

2、日志对象，参数为当前类的class

```java
    static Logger logger = Logger.getLogger(UserDaoTest.class);
```

3、测试

```java
    @Test
    public void testLog4j(){
        logger.info("info: 进入了testLog4j");
        logger.debug("debug: 进入了log4j");
        logger.error("error: 进入了testLog4j");
    }
```

### 4.3 分页

思考：为啥什么要分页？

- 减少数据的处理量

##### 1、使用Limit分页

```mysql
1、SELECT * FROM user LIMIT startIndex,pageSize;
2、SELECT * FROM user LIMIT 2, 2
```

使用Mybatis实现分页，核心SQL

1、接口

```java
//分页
List<User> getUserByLimit(Map<String, Integer> map);
```

2、Mapper.XML

```xml
<!--    分页-->
<select id="getUserByLimit" parameterType="map" resultMap="UserMap">
    select * from mybatis.user limit #{startIndex}, #{pageSize }
</select>
```

3、测试

```java
@Test
public void getUserByLimit(){
    //第一步获取sqlSession对象
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    //方式一：getMapper
    UserMapper userDao = sqlSession.getMapper(UserMapper.class);
    HashMap<String, Integer> map = new HashMap<String, Integer>();
    map.put("startIndex", 0);
    map.put("pageSize", 2);

    List<User> userList =  userDao.getUserByLimit(map);

    for (User user : userList) {
        System.out.println(user);
    }

    //关闭SqlSession
    sqlSession.close();
}
```

##### 2、RowBounds分页

接口

```java
//分页RowBounds方式实现
List<User> getUserByRowBounds();
```

Mapper.xml

```xml
<!--    分页2-->
<select id="getUserByRowBounds" resultMap="UserMap">
    select * from mybatis.user
</select>
```

测试

```java
@Test
public void getUserByRowBounds(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();

    //RowBounds实现
    RowBounds rowBounds = new RowBounds(1,2);

    //通过java代码层面实现分页
    List<User> userList = sqlSession.selectList("com.example.dao.UserMapper.getUserByRowBounds", null, rowBounds);
    for (User user : userList){
        System.out.println(user);
    }
}
```

### 4.4 使用注解开发

可以理解为简单实现xml的功能

为什么加个注解就能执行了？加断点看下？注解核心就是反射！

注解就是在接口上面实现

```java
@Select("select * from user")
List<User> getUsers();
```

需要在核心配置文件中绑定接口

```xml
    <mappers>
        <mapper class="com.example.dao.UserMapper"/>
    </mappers>
```

<img src="/Users/liangzhengtao/Library/Application Support/typora-user-images/image-20201129162047748.png" alt="image-20201129162047748" style="zoom:50%;" />

### 4.5 Mybatis详细执行流程

![121](/Users/liangzhengtao/Downloads/121.png)

### 4.6 curd

我们可以在工具类创建的时候自动提交事务

```java
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession(true);
    }
```

```java
package com.example.dao;

import com.example.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select("select * from user")
    List<User> getUser();

    //方法存在多个参数，基本类型的参数必须加上@Param("id")注解
    @Select("select * from user where id = #{id}")
    User getUserByID(@Param("id") int id);

    @Insert("insert into user(id, name, pwd) values (#{id}, #{name}, #{passwd})")
    int addUser(User user);

    @Update("update user set name=#{name}, pwd = #{passwd} where id = #{id}")
    int updateUser(User user);

    @Delete("delete from user where id = #{id}")
    int deleteUser(@Param("id") int id);
}
```

```java
package com.example.dao;

import com.example.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class UserDaoTest {

    @Test
    public void test(){
        //第一步获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //方式一：getMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        //User userByID = mapper.getUserByID(1);
        //mapper.addUser(new User(12, "heelo", "123334"));
        //mapper.updateUser(new User(4, "23sdsd", "11222"));
        mapper.deleteUser(10);
        //关闭SqlSession
        sqlSession.close();
    }
}
```



使用注解的话，务必需要将其绑定在配置文件中

**[注意：]**

- 我们必须将接口注解绑定到我们的核心配置文件中

  ```xml
      <mappers>
  <!--        <mapper resource="com/example/dao/UserMapper.xml"/>-->
          <mapper class="com.example.dao.UserMapper"/>
  <!--        <package name="com.example.dao"/>-->
      </mappers>
  ```

- 关于@Param（）注解

  - 基本类型的参数和String类型，需要加上
  - 引用类型不需要加
  - 如果只有一个基本类型的话，可以忽略，但是建议大家都加上
  - 我们在SQL中引用就是我们这里提到的@Param设定的属性名字

- #{}、${}区别？

  - #{}可以有效的预防mysql注入问题
  - <img src="/Users/liangzhengtao/Library/Application Support/typora-user-images/image-20201203145434888.png" alt="image-20201203145434888" style="zoom:50%;" />

### 4.7 lombok

使用步骤

1、在IDEA中安装lombok插件

2、在项目中导入lombok的jar包

```java
        <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.16</version>
        </dependency>
```

```java
@Getter and @Setter
@FieldNameConstants
@ToString
@EqualsAndHashCode
@AllArgsConstructor, @RequiredArgsConstructor and @NoArgsConstructor
@Log, @Log4j, @Log4j2, @Slf4j, @XSlf4j, @CommonsLog, @JBossLog, @Flogger, @CustomLog
@Data
@Builder
@SuperBuilder
@Singular
@Delegate
@Value
@Accessors
@Wither
@With
@SneakyThrows
@val
@var
experimental @var
@UtilityClass
Lombok config system
```

```java
@Data:生成无参构造、set、get、equals、canEqual、hashCode、toString
@AllArgsConstructor
@NoArgsConstructor
```

```java
@Getter
@Target({ElementType.FIELD, ElementType.TYPE})     //可以放在字段上面，也可以放在类上面
@Retention(RetentionPolicy.SOURCE)
public @interface Getter {
    AccessLevel value() default AccessLevel.PUBLIC;

    Getter.AnyAnnotation[] onMethod() default {};

    boolean lazy() default false;

    /** @deprecated */
    @Deprecated
    @Retention(RetentionPolicy.SOURCE)
    @Target({})
    public @interface AnyAnnotation {
    }
}
```

### 4.8 多对一处理

<img src="/Users/liangzhengtao/Library/Application Support/typora-user-images/image-20201212011008981.png" alt="image-20201212011008981" style="zoom:50%;" />

- 多个学生，对应一个老师
- 对于学生这边而言，**关联** 多个学生，关联一个老师【多对一】
- 对于老师而言，**集合** 一个老师，有很多学生【一对多】

sql:通过物理外键实现多对一

```sql
CREATE TABLE `teacher` (
 `id` INT(10) NOT NULL,
 `name` VARCHAR(30) DEFAULT NULL,
 PRIMARY KEY(`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8

use mybatis;
show tables;
desc teacher

INSERT INTO teacher(`id`, `name`) VALUES(2, '小白');

CREATE TABLE `student` (
 `id` INT(10) NOT NULL,
 `name` VARCHAR(30) DEFAULT NULL,
 `tid` INT(10) DEFAULT NULL,
 PRIMARY KEY(`id`),
 KEY `fktid` (`tid`),
 CONSTRAINT `fktid` FOREIGN KEY (`tid`) REFERENCES `teacher` (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8

INSERT INTO `student` (`id`, `name`, `tid`) VALUES('1', '小明', '1');
INSERT INTO `student` (`id`, `name`, `tid`) VALUES('2', '小红', '1');
INSERT INTO `student` (`id`, `name`, `tid`) VALUES('3', '小张', '1');
INSERT INTO `student` (`id`, `name`, `tid`) VALUES('4', '小李', '1');
INSERT INTO `student` (`id`, `name`, `tid`) VALUES('5', '小王', '1');

use mybatis;
show tables;  //有了三张表了
```

测试环境搭建

1、导入lombok

2、新建实体类Teacher、Student

3、建立Mapper接口

4、建立Mapper.XML文件

5、在核心配置文件中绑定注册我们的Mapper接口或者文件！【方式很多，随心选】

6、测试查询是否能够成功



