# Spring-study
Spring自学记录
## 1、Spring

### 1.1 介绍

定义：Spring是Java EE编程领域的一个轻量级开源框架，该框架由一个叫Rod Johnson的程序员在 2002 年最早提出并随后创建，是为了解决企业级编程开发中的复杂性，实现敏捷开发的应用型框架 。

官网：[Spring Framework Overview :: Spring Framework](https://docs.spring.io/spring-framework/reference/overview.html)

GitHub: [spring-projects/spring-framework: Spring Framework (github.com)](https://github.com/spring-projects/spring-framework)

导入：

```xml
<!-- https://mvnrepository.com/artifact/org.springframework/spring-web -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-web</artifactId>
    <version>6.0.9</version>
</dependency>

```



### 1.2 优点

1. 面向切面编程（AOP）
2. 控制反转（IOC）

### 1.3组成

七大模块

### 1.4 扩展

- Spring Boot
    - 一个快速开发的脚手架
    - 基于Springboot可以快速开发一个微服务
    - 约定大于 配置

- Spring Cloud
    - Spring Cloud是基于SpringBoot开发的


## 2、IOC理论推导

1. UserDao接口
2. UserDaoImpl实现类
3. UserService业务接口
4. UserServiceImpl业务实现类

在我们之前的业务中，用户的需求可能会影响我们原来的代码，我们需要根据用户的需求去修改原代码！

如果程序的代码量十分大，修改一次的成本代价十分高昂!



我们使用一个Set接口实现，大大减少了修改范围，统一化了接口！





- 使用Set前，程序主动创建对象，控制权在程序员手上！
- 使用Set注入后，程序不再具有主动性，而是变成了被动的接收对象！

这种思想从本质上解决了大范围修改原代码的问题，我们程序员不用再去管理对象的创建，系统的耦合性大大降低，可以更加专注的在业务上实现！这是IOC的原型！



**IOC本质：**

控制反转IoC(Inversion of Control)，是一种设计思想，DI(依赖注入)是实现IoC的一种方法，也有人认为DI只是IoC的另一种说法。没有IoC的程序中 , 我们使用面向对象编程 , 对象的创建与对象间的依赖关系完全硬编码在程序中，对象的创建由程序自己控制，控制反转后将对象的创建转移给第三方，个人认为所谓控制反转就是：获得依赖对象的方式反转了。



采用XML方式配置Bean的时候，Bean的定义信息是和实现分离的，而采用注解的方式可以把两者合为一体，Bean的定义信息直接以注解的形式定义在实现类中，从而达到了零配置的目的。

控制反转是一种通过描述（XML或注解）并通过第三方去生产或获取特定对象的方式。在Spring中实现控制反转的是IoC容器，其实现方法是依赖注入（Dependency Injection,DI）。
![img](D:\Code\Java_code\Spring-study\image\aHR0cHM6Ly9pbWcyMDE4LmNuYmxvZ3MuY29tL2Jsb2cvMTQxODk3NC8yMDE5MDcvMTQxODk3NC0yMDE5MDcyNjExMjg1NTA3NC02Njk5OTg3OTYucG5n.png)

## 3、HelloSpring

1. 首先创建一个实体类Hello

   ```java
   package com.zhaolei.hello;
   
   public class Hello {
       private String str;
   
       public String getStr() {
           return str;
       }
   
       public void setStr(String str) {
           this.str = str;
       }
   
       @Override
       public String toString() {
           return "Hello{" +
                   "str='" + str + '\'' +
                   '}';
       }
   }
   
   ```

   必须具有set方法!!!

2. 创建配置文件beans.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd">
   
       <bean id="hello" class="com.zhaolei.hello.Hello">
           <property name="str" value="Spring"></property>
       </bean>
   </beans>
   ```

   bean标签相当于创建对象，通过property标签进行赋值

3. 测试

   ```java
   package com.zhaolei;
   
   import com.zhaolei.hello.Hello;
   import org.springframework.context.ApplicationContext;
   import org.springframework.context.support.ClassPathXmlApplicationContext;
   
   public class Mytest {
       public static void main(String[] args) {
           ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
           Hello hello = (Hello) context.getBean("hello");
           System.out.printf(hello.toString());
       }
   }
   ```

   ```java
   ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
   ```

   该句很重要！！！

## 4、IOC创建对象的方式

1、beans.xml创建对象默认使用无参构造方法！



```xml
<bean id="user" class="com.zhaolei.pojo.User">
        <property name="name" value="赵磊"></property>
</bean>
```

自动调用无参构造方法

实体类：

```java
public class User {
    private String name;

    public User(){
        System.out.println("无参构造对象");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void show(){
        System.out.println("姓名: "+name);
    }
}
```

测试：

```java
public class Mytest {
    public static void main(String[] args) {
        ApplicationContext Context = new ClassPathXmlApplicationContext("beans.xml");
        User user = (User) Context.getBean("user");
        user.show();
    }
}
```

结果：

![image-20230526211828217](D:\Code\Java_code\Spring-study\image\image-20230526211828217.png)



2、如果实体类需使用有参构造函数则有以下解决办法：

1.  方法一

   ```xml
   <bean id="hello" class="com.zhaolei.hello.Hello">
       <property name="str" value="Spring"></property>
   </bean>
   ```

2. 方法二(推荐)

   ```xml
   <bean id="user" class="com.zhaolei.pojo.User">
       <constructor-arg name="name" value="ZL"></constructor-arg>
   </bean>
   ```

3. 方法三

   ```xml
   <bean id="user" class="com.zhaolei.pojo.User">
       <constructor-arg type="java.lang.String" value="Zhaolei"></constructor-arg>
   </bean>
   ```





注意：凡是在beans.xml文件里使用bean标签对应的都会被创建对象，不管有没有使用！！！



## 5、Spring配置说明



### 5.1、alias起别名

```xml
<!--    起别名   -->
<alias name="user" alias="userNew"></alias>
```



### 5.2、bean的配置

```xml
<!--
id: bean的唯一标识，相当于对象名
class: bean对象对应的全限定名，相当于包名+类型
name: 也是别名，可以同时起多个别名
-->
<bean id="user" class="com.zhaolei.pojo.User" name="u1 u2 u3 u4">
    <constructor-arg type="java.lang.String" value="Zhaolei"></constructor-arg>
</bean>
```

### 5.3、import 的配置

适合团队开发，每个成员都有自己对应的bean.xml文件，最后可以通过import导入到最终的ApplicationContext.xml配置文件中，合并其中相同的。

- 赵一  beans.xml
- 赵二  beans2.xml
- 赵三  beans3.xml
- 总的  ApplicationContext.xml

```xml
<import resource="beans.xml"></import>
<import resource="beans2.xml"></import>
<import resource="beans3.xml"></import>
```



​          ![image-20230527205353452](D:\Code\Java_code\Spring-study\image\image-20230527205353452.png)
