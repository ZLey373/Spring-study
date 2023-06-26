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

​

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
![img](Spring学习笔记.assets/aHR0cHM6Ly9pbWcyMDE4LmNuYmxvZ3MuY29tL2Jsb2cvMTQxODk3NC8yMDE5MDcvMTQxODk3NC0yMDE5MDcyNjExMjg1NTA3NC02Njk5OTg3OTYucG5n.png)

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

![image-20230526211828217](Spring学习笔记.assets/image-20230526211828217.png)



2、如果实体类需使用有参构造函数则有以下解决办法：

1. 方法一

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



​          ![image-20230527205353452](Spring学习笔记.assets/image-20230527205353452.png)

## 6、注入依赖

依赖： beans.xml容器创建对象

注入：通过容器给对象赋值

### 6.1、通过构造器注入

### 6.2、通过Set注入

1. 复杂类型

   ```java
   public class Address {
       private String address;
   
       public String getAddress() {
           return address;
       }
   
       public void setAddress(String address) {
           this.address = address;
       }
   }
   
   ```

2. 真实测试对象

   ```java
   public class Student {
       private String name;
       private Address address;
       private String[] books;
       private List<String> hobbies;
       private Map<String,String> card;
       private Set<String> games;
       private String ptr;
       private Properties info;
   }
   ```

3. beans.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd">
   <bean id="student" class="com.zhaolei.pojo.Student">
       <property name="name" value="赵磊"></property>
   </bean>
   </beans>
   
   ```

4. 测试类

   ```java
   public class MyTest {
       public static void main(String[] args) {
           ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
           Student student = (Student) context.getBean("student");
           System.out.println(student.getName());
       }
   }
   ```

   完善beans.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd">
   <bean id="address" class="com.zhaolei.pojo.Address">
       <property name="address" value="西安电子科技大学"></property>
   </bean>
   <bean id="student" class="com.zhaolei.pojo.Student">
       <property name="name" value="赵磊"></property>
       <!--引用注入-->
       <property name="address" ref="address"></property>
       <!-- 数组注入  -->
       <property name="books">
           <array>
               <value>计算机组成原理</value>
               <value>操作系统</value>
               <value>计算机网络</value>
               <value>数据机构</value>
           </array>
       </property>
       <!--  List -->
       <property name="hobbies">
           <list>
               <value>自驾游</value>
               <value>敲代码</value>
               <value>旅游</value>
           </list>
       </property>
       <!--   map  -->
       <property name="card">
           <map>
               <entry key="学号" value="22031212xxx"></entry>
               <entry key="姓名" value="赵磊"></entry>
               <entry key="专业" value="计算机科学与技术"></entry>
           </map>
       </property>
       <!--   set  -->
       <property name="games">
           <set>
               <value>王者荣耀</value>
               <value>部落冲突</value>
           </set>
       </property>
       <!--  null -->
       <property name="ptr">
           <null></null>
       </property>
        <!-- info -->
       <property name="info">
           <props>
               <prop key="drive">123783210hdks</prop>
               <prop key="url">http://dhsje12.com</prop>
               <prop key="username">root</prop>
               <prop key="password">123</prop>
           </props>
       </property>
   </bean>
   
   </beans>
   
   ```

   完善测试类：

   ```java
   public class MyTest {
       public static void main(String[] args) {
           ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
           Student student = (Student) context.getBean("student");
           System.out.println(student.toString());
       }
   }
   
   
   /*
   * Student{name='赵磊',
   *         address=Address{address='西安电子科技大学'},
   *          books=[计算机组成原理, 操作系统, 计算机网络, 数据机构],
   *          hobbies=[自驾游, 敲代码, 旅游],
   *          card={学号=22031212xxx, 姓名=赵磊, 专业=计算机科学与技术},
   *          games=[王者荣耀, 部落冲突],
   *          ptr='null',
   *          info={password=123, url=http://dhsje12.com, drive=123783210hdks, username=root}
   * }
   * */
   ```

### 6.3、其他方式注入

p命名空间和c命名空间

- 首先导入约束

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:p="http://www.springframework.org/schema/p"
         xmlns:c="http://www.springframework.org/schema/c"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd">
     <import resource="beans.xml"></import>
      <bean id="user" class="com.zhaolei.pojo.User" p:name="赵磊" p:age="22"></bean>
      <bean id="user2" class="com.zhaolei.pojo.User" c:name="zl" c:age="22"></bean>
  </beans>
  
  ```

  ```xml
     xmlns:p="http://www.springframework.org/schema/p"
     xmlns:c="http://www.springframework.org/schema/c"
  ```

- 调用

  ```xml
    <bean id="user" class="com.zhaolei.pojo.User" p:name="赵磊" p:age="22"></bean>
    <bean id="user2" class="com.zhaolei.pojo.User" c:name="zl" c:age="22"></bean>
  ```

- 测试

  ```java
  @Test
  public void test(){
      ApplicationContext context = new ClassPathXmlApplicationContext("Applicationcontext.xml");
      User user = context.getBean("user", User.class);
      User user2 = context.getBean("user2", User.class);
      System.out.println(user);
      System.out.println(user2);
  }
  ```

注意：c命名空间的使用必须是有参构造方法!!!

### 6.4、bean的作用域

1. singleton 单例模式（Spring的默认模式）：从容器里getBean的对象都是同一个

   ```xml
    <bean id="user2" class="com.zhaolei.pojo.User" c:name="zl" c:age="22" scope="singleton"></bean>
   ```

2. prototype原型模式：从容器中getBeam的对象都是不同的

   ```java
   <bean id="user2" class="com.zhaolei.pojo.User" c:name="zl" c:age="22" scope="prototype"></bean>
   ```

3. request、session、application都是在web中实用的模式



## 7、bean的自动装配

### 7.1、环境搭建：一个人有两个宠物

### 7.2、测试

1. 实体类

   ```java
   public class People {
           private Cat cat;
           private Dog dog;
           private String name;
   }
   ```

2. xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd">
       <bean id="cat" class="com.zhaolei.pojo.Cat"></bean>
       <bean id="dog" class="com.zhaolei.pojo.Dog"></bean>
   
       <bean id="people" class="com.zhaolei.pojo.People">
           <property name="name" value="赵磊"></property>
           <property name="cat" ref="cat"></property>
           <property name="dog" ref="dog"></property>
       </bean>
   </beans>
   ```

3. 测试

   ```java
   public class MyTest {
       @Test
       public void test(){
           ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
           People people = context.getBean("people", People.class);
           people.getCat().shout();
           people.getDog().shout();
       }
   }
   ```

### 7.3、 byName 自动装配

```xml
<bean id="cat" class="com.zhaolei.pojo.Cat"></bean>
<bean id="dog" class="com.zhaolei.pojo.Dog"></bean>
<bean id="people" class="com.zhaolei.pojo.People" autowire="byName">
    <property name="name" value="赵磊"></property>
</bean>
```

byName: 从容器中选取与类（people）的属性（有三个属性name、cat、dog）相同（cat、dog）的名字；

### 7.4、 byType 自动装配

```xml
<bean id="cat" class="com.zhaolei.pojo.Cat"></bean>
<bean id="dog" class="com.zhaolei.pojo.Dog"></bean>
<!--byType自动装配-->
<bean id="people" class="com.zhaolei.pojo.People" autowire="byType">
    <property name="name" value="赵磊"></property>
</bean>
```

byType:从容器中选取类别一致的，class后面的类别。

### 7.5、注解自动装配

1. 首先导入约束

   ```xml
   xmlns:context="http://www.springframework.org/schema/context"
   ```

   ```xml
   http://www.springframework.org/schema/context
   https://www.springframework.org/schema/context/spring-context.xsd
   ```



2. 开启注解

   ```xml
   <context:annotation-config/>
   ```

3. 使用注解

```java
public class People {
        @Autowired
        private Cat cat;
        @Autowired
        private Dog dog;
        private String name;
}
```



综合：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           https://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context
                           https://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>

</beans>
```

小结：

- @Autowired使用时，类可以不用添加Set方法。

- @Autowired一般会和@Qualifier一起使用，@Qualifier可以设置名字，如cat222.

- @Autowired 先通过byType判断再通过byName判断



@Resource 也可以实现自动装配：先通过byName判断再通过byType判断,且可以使用@Resource(name = "dog22")设置要自动装配的名称，更为方便!





# 8、使用注解开发

1. 导入注解支持的文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:context="http://www.springframework.org/schema/context"
      xsi:schemaLocation="http://www.springframework.org/schema/beans
                          https://www.springframework.org/schema/beans/spring-beans.xsd
                          http://www.springframework.org/schema/context
                          https://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.zhaolei">
    </context:component-scan>   <!-- 扫描包-->
   <context:annotation-config/>   <!-- 开启注解 -->

</beans>
```



2. 使用注解对类进行注入@Component

```java
@Component        //注解   相等于 <bean id="user" class="com.zhaolei.pojo.User"></bean>
public class User {
   public String name;
}
```



3. 使用注解给属性注入@Value

```java
 public class User {
       @Value("zley")
       public String name;
}

```




4. 衍生的注解

   ```java
   service     (@Service)
   controller   (@Controller)
   dao    (@Repository)
   ```

​      ==这三个注解和@Component注解的功能相同，只是服务的对象不同==

5. 自动装配

```java
 public class People {
           @Autowired
           private Cat cat;
           @Autowired
           private Dog dog;
           private String name;
   }
```




6. 作用域@Scope("singleton")  单例模式

```java
@Component        //注解   相等于 <bean id="user" class="com.zhaolei.pojo.User"></bean>
@Scope("singleton")
public class User {
   @Value("zley")
   public String name;
}
```




7. 小结


xml与注解：

​    \- xml更加万能，适合任何场合，维护简单！
​    \- 注解不是自己的类是用不了，维护比较复杂!

xml与注解的最佳实践：

​    \- xml用来管理bean；

​    \- 注解只负责完成属性的注入；

​    \- 在我们使用的过程中，要注意一个问题：要让注解生效，必需要开启注解的支持!

```xml
<context:component-scan base-package="com.zhaolei"></context:component-scan>
<context:annotation-config/>
```



# 9、使用Java注解开发

1. 首先创建配置类

```java
import com.zhaolei.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZLConfig {
   @Bean
   public User user(){
       return new User();
   }
}
```

@Bean: 注释用于指示方法实例化、配置和初始化要由Spring IoC容器管理的新对象。

@Configuration表明其主要目的是作为bean定义的来源,

@Configuration类允许通过调用同一类中的其他@bean方法来定义bean间的依赖关系

2. 创建实体类

```java
package com.zhaolei.pojo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class User {
   @Value("赵磊")
   private String name;

   public String getName() {
       return name;
   }
   public void setName(String name) {
       this.name = name;
   }
   @Override
   public String toString() {
       return "User{" +
               "name='" + name + '\'' +
               '}';
   }

}
```

使用@Component进行注入类，@Value注入属性值

3. 测试

```java
public class AppTest {
   @Test
   public void test(){
       ApplicationContext context = new AnnotationConfigApplicationContext(ZLConfig.class);
       User user = context.getBean("user", User.class);
       System.out.println(user.getName());
   }
}
```




4. 常用注解：


     @Import(ConfigA.class)
     @Scope("prototype")
     @ComponentScan(basePackages = "com.acme")
     @Autowired

#  10、代理模式

代理模式是SpringAOC的底层

代理模式：

\- 静态代理
\- 动态代理

## 10.1、静态代理

角色分析：

\- 抽象角色：一般会使用接口或者抽象类  【租房这件事】
\- 代理角色：代理真实角色，一般会有一些附属操作 【中介】
\- 真实角色：被代理的角色  【房东】
\- 客户端：访问代理对象的人  【租客】

代码步骤：

1. 接口

```java
public interface Rent {
   public void rent();

```

2. 真实类

```java
public class Host implements Rent{
   @Override
   public void rent() {
       System.out.println("房东出租房子");
   }
}
```

3. 代理类

```java
public class Proxy implements Rent{
   private Host host;

   public Proxy(Host host) {
       this.host = host;
   }

   public Proxy() {
   }

   @Override
   public void rent() {
       host.rent();
       seeHouse();
       Contract();
       fare();
   }
   public void seeHouse(){
       System.out.println("中介带着看房子");
   }
   public void fare(){
       System.out.println("中介收取中介费用");
   }
   public void Contract(){
       System.out.println("签租赁合同");
   }

}
```

4. 客户

```java
public class Client {
   public static void main(String[] args) {
       Host host = new Host();
       Proxy proxy = new Proxy(host);
       proxy.rent();
   }
}
```



代理模式的优点：

\- 可以使得真实角色的操作更加纯粹！不用去关注一些公共的业务
\- 公共的业务就交给代理角色！实现了业务的分工
\- 公共业务发生拓展时，方便集中管理

缺点：

\- 一个真实角色会产生一个代理对象，倒是开发的效率变低

##  10.2 、动态代理

\- 动态代理和静态代理角色一样
\- 动态代理和代理类是动态生成的，不是我们直接写好
\- 动态代理分为两大类：基于接口的动态代理，基于类的动态代理

1. 基于接口--JDK动态代理
2. 基于类：cglib

```java
package com.zhaolei.demo03;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyInvocationHandler implements InvocationHandler {
    // 被代理的接口
    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    // 生成的到代理类
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log(method.getName());
        Object result = method.invoke(target,args);
        return result;
    }
    public void log(String msg){
        System.out.println("执行了"+msg+"方法");
    }
}
```



测试：

```java
package com.zhaolei.demo03;

import com.zhaolei.demo02.UserService;
import com.zhaolei.demo02.UserServiceImpl;

public class Client {

    public static void main(String[] args) {
        // 真实角色
        UserServiceImpl userService = new UserServiceImpl();
        // 代理角色，不存在
        ProxyInvocationHandler pih = new ProxyInvocationHandler();
        // 设置要代理的对象
        pih.setTarget(userService);

        // 动态生成代理类
        UserService proxy = (UserService) pih.getProxy();
        proxy.query();

    }
}
```



动态代理的好处：

\- 可以使真实角色的操作更加纯粹！不用去关注一些公共的业务
\- 公共也就就交给代理角色！实现了业务的分工！
\- 公共业务发生扩展的时候，方便集中管理！
\- 一个动态代理类代理的是一个接口，一般就是对应的一类业务





# 11、AOP

##  11.1、什么是AOP

在软件业，AOP为Aspect Oriented Programming的缩写，意为：面向切面编程，通过预编译方式和运行期间动态代理实现程序功能的统一维护的一种技术。AOP是[OOP](https://baike.baidu.com/item/OOP/1152915)的延续，是[软件开发](https://baike.baidu.com/item/软件开发/3448966)中的一个热点，也是[Spring](https://baike.baidu.com/item/Spring)框架中的一个重要内容，是[函数式编程](https://baike.baidu.com/item/函数式编程/4035031)的一种衍生范型。==利用AOP可以对[业务逻辑](https://baike.baidu.com/item/业务逻辑/3159866)的各个部分进行隔离，从而使得业务逻辑各部分之间的[耦合度](https://baike.baidu.com/item/耦合度/2603938)降低，提高程序的[可重用性](https://baike.baidu.com/item/可重用性/53650612)，同时提高了开发的效率。==

## 11.2、AOP在Spring中的作用

提供声明式事务；允许用户自定义切面

\- 横切关注点:跨越应用程序多个模块的方法或功能.既是,与我们业务逻辑无关,但是我们需要关注的部分,就是横切关注点.如日志,安全,缓存,事务等…


\- 切面（ASPECT）：横切关注点 被模块化 的特殊对象。即，它是一个类。

\- 通知（Advice）：切面必须要完成的工作。即，它是类中的一个方法。

\- 目标（Target）：被通知对象。

\- 代理（Proxy）：向目标对象应用通知之后创建的对象。

\- 切入点（PointCut）：切面通知 执行的 “地点”的定义。

\- 连接点（JointPoint）：与切入点匹配的执行点。

Spring AOP includes the following types of advice:

\- Before advice: 在连接点之前运行的建议，但不能阻止执行流继续进行到连接点（除非抛出异常）。
\- After returning advice: 在连接点正常完成后运行的建议（例如，如果一个方法返回而没有抛出异常）。
\- After throwing advice: 如果方法通过抛出异常退出，则要运行的建议。
\- After (finally) advice: 无论连接点以何种方式退出都要运行的建议（正常或异常返回）。
\- Around advice: 可以在方法调用前后执行自定义行为。它还负责选择是继续到连接点，还是通过返回自己的返回值或抛出异常来缩短建议的方法执行。



代理机制：

If the target object to be proxied implements at least one interface, a JDK dynamic proxy is used. All of the interfaces implemented by the target type are proxied. If the target object does not implement any interfaces, a CGLIB proxy is created.



## 11.3、AOP在Spring中的使用

1. 导入约束依赖：

```xml
<dependency>
    <groupId> org.aspectj</groupId >
    <artifactId> aspectjweaver</artifactId >
    <version> 1.9.4</version >
</dependency>
```




2. 配置 [applicationContext.xml](..\..\..\Code\Java_code\Spring-study\Spring-09-aop\src\main\resources\applicationContext.xml) 文件：


方法一：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           https://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context
                           https://www.springframework.org/schema/context/spring-context.xsd
                           http://www.springframework.org/schema/aop
                           https://www.springframework.org/schema/aop/spring-aop.xsd">

    <context:component-scan base-package="com.zhaolei"></context:component-scan>
    <context:annotation-config/>
    
<!--    注册bean-->
    <bean id="UserService" class="com.zhaolei.service.UserServiceImpl"></bean>
    <bean id="prelog" class="com.zhaolei.log.preLog"></bean>
    <bean id="afterlog" class="com.zhaolei.log.afterLog"></bean>

<!--    配置aop：需要导入aop的约束   -->
    <aop:config>
<!--     切入点：expression: 表达式：execution(需执行的位置！* * * * *)    -->
        <aop:pointcut id="pointcut" expression="execution(* com.zhaolei.service.UserServiceImpl.*(..))"/>

<!--    执行环绕增加   -->
        <aop:advisor advice-ref="prelog" pointcut-ref="pointcut"></aop:advisor>
        <aop:advisor advice-ref="afterlog" pointcut-ref="pointcut"></aop:advisor>

    </aop:config>
</beans>
```




方法二：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           https://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context
                           https://www.springframework.org/schema/context/spring-context.xsd
                           http://www.springframework.org/schema/aop
                           https://www.springframework.org/schema/aop/spring-aop.xsd">

    <context:component-scan base-package="com.zhaolei"></context:component-scan>
    <context:annotation-config/>
    
<!--    注册bean-->
    <bean id="UserService" class="com.zhaolei.service.UserServiceImpl"></bean>
    <bean id="prelog" class="com.zhaolei.log.preLog"></bean>
    <bean id="afterlog" class="com.zhaolei.log.afterLog"></bean>
<!--    方法二，自定义类   -->
    <bean id="diy" class="com.zhaolei.diy.DiyPointCut"></bean>
    <aop:config>
        <!--自定义切面，ref表示要引用的类-->
        <aop:aspect ref="diy">
            <!--切入点-->
            <aop:pointcut id="pointcut" expression="execution(* com.zhaolei.service.UserServiceImpl.*(..))"/>
              <!-- 通知-->
            <aop:before method="before" pointcut-ref="pointcut"></aop:before>
           <aop:after method="after" pointcut-ref="pointcut"></aop:after>
        </aop:aspect>

    </aop:config>

</beans>
```

3. afterLog:

```java
public class afterLog implements AfterReturningAdvice {
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("执行了"+method.getName()+"方法，返回结果为："+returnValue);
    }
}
```

4. preLog:

```java
public class preLog implements MethodBeforeAdvice {
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println(target.getClass().getName()+"的"+method.getName()+"被执行了");
    }
}
```

5. 测试：

```java
public class AppTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService) context.getBean("UserService");
        userService.add();
    }
}
```



方法二：

1. 导入依赖，同上

2. 新建自定义类DiyPointCut

```java
package com.zhaolei.diy;

   public class DiyPointCut {
       public void before(){
           System.out.println("---------执行前---------");
       }
       public void after(){
           System.out.println("---------执行后---------");
       }
   }
```

3. 配置xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:context="http://www.springframework.org/schema/context"
      xmlns:aop="http://www.springframework.org/schema/aop"
      xsi:schemaLocation="http://www.springframework.org/schema/beans
                          https://www.springframework.org/schema/beans/spring-beans.xsd
                          http://www.springframework.org/schema/context
                          https://www.springframework.org/schema/context/spring-context.xsd
                          http://www.springframework.org/schema/aop
                          https://www.springframework.org/schema/aop/spring-aop.xsd">

   <context:component-scan base-package="com.zhaolei"></context:component-scan>
   <context:annotation-config/>

<!--    注册bean-->
   <bean id="UserService" class="com.zhaolei.service.UserServiceImpl"></bean>
   <bean id="prelog" class="com.zhaolei.log.preLog"></bean>
   <bean id="afterlog" class="com.zhaolei.log.afterLog"></bean>
<!--    方法二，自定义类   -->
   <bean id="diy" class="com.zhaolei.diy.DiyPointCut"></bean>
   <aop:config>
       <!--自定义切面，ref表示要引用的类-->
       <aop:aspect ref="diy">
           <!--切入点-->
           <aop:pointcut id="pointcut" expression="execution(* com.zhaolei.service.UserServiceImpl.*(..))"/>
             <!-- 通知-->
           <aop:before method="before" pointcut-ref="pointcut"></aop:before>
           <aop:after method="after" pointcut-ref="pointcut"></aop:after>
       </aop:aspect>

   </aop:config>



</beans>
```




4. 测试同上


![image-20230624164444497](C:\typora工作区\Java\Spring\assets\image-20230624164444497.png)



方法三：注解的方式

1. 导入依赖，同上

2. 新建自定义类

```java
package com.zhaolei.diy;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect     // 注册切面
public class InnoPointCut {
   @Before("execution(* com.zhaolei.service.UserServiceImpl.*(..))")  // 绑定切点
   public void before(){
       System.out.println("---------执行前---------");
   }
   @After("execution(* com.zhaolei.service.UserServiceImpl.*(..))")
   public void after(){
       System.out.println("---------执行后---------");
   }

   @Around("execution(* com.zhaolei.service.UserServiceImpl.*(..))")
   public void around(ProceedingJoinPoint jp) throws Throwable {
       System.out.println("-----环绕前----");
       Object proceed = jp.proceed();
       System.out.println("-----环绕后----");
   }

}
```



3. 配置xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:context="http://www.springframework.org/schema/context"
      xmlns:aop="http://www.springframework.org/schema/aop"
      xsi:schemaLocation="http://www.springframework.org/schema/beans
                          https://www.springframework.org/schema/beans/spring-beans.xsd
                          http://www.springframework.org/schema/context
                          https://www.springframework.org/schema/context/spring-context.xsd
                          http://www.springframework.org/schema/aop
                          https://www.springframework.org/schema/aop/spring-aop.xsd">

   <context:component-scan base-package="com.zhaolei"></context:component-scan>
   <context:annotation-config/>

<!--    注册bean-->
   <bean id="UserService" class="com.zhaolei.service.UserServiceImpl"></bean>
   <bean id="prelog" class="com.zhaolei.log.preLog"></bean>
   <bean id="afterlog" class="com.zhaolei.log.afterLog"></bean>
<!--    方式三:注解   -->
<!--    注册自定义类   -->
   <bean id="inno" class="com.zhaolei.diy.InnoPointCut"></bean>
   <!--开启注解支持-->
   <aop:aspectj-autoproxy></aop:aspectj-autoproxy>


</beans>
```




4. 测试



![image-20230624171255841](C:\typora工作区\Java\Spring\assets\image-20230624171255841.png)



# 12、整合Mybatis

步骤：

1. 导入相关的jar包
   - junit
   - mybatis
   - mysql数据库
   - spring相关包
   - apo植入
   - mybatis-spring
2. 编写测试文件
3. 测试

## 12.1、回忆mybatis



1. 导入依赖

   ```xml
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
       <parent>
           <groupId>com.zhaolei</groupId>
           <artifactId>Spring-study</artifactId>
           <version>1.0-SNAPSHOT</version>
       </parent>
   
       <artifactId>Spring-10-Mybatis</artifactId>
       <packaging>jar</packaging>
   
       <name>Spring-10-Mybatis</name>
       <url>http://maven.apache.org</url>
   
       <properties>
           <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
       </properties>
   
       <dependencies>
           <!-- https://mvnrepository.com/artifact/junit/junit -->
           <dependency>
               <groupId>junit</groupId>
               <artifactId>junit</artifactId>
               <version>4.13.2</version>
               <scope>test</scope>
           </dependency>
   
           <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
           <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
               <version>8.0.28</version>
           </dependency>
   
   
           <dependency>
               <groupId>org.mybatis</groupId>
               <artifactId>mybatis</artifactId>
               <version>3.5.6</version>
           </dependency>
           <!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-webmvc</artifactId>
               <version>6.0.9</version>
           </dependency>
           <!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-jdbc</artifactId>
               <version>6.0.9</version>
           </dependency>
           <!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
           <dependency>
               <groupId>org.aspectj</groupId>
               <artifactId>aspectjweaver</artifactId>
               <version>1.9.19</version>
               <scope>runtime</scope>
           </dependency>
           <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
           <dependency>
               <groupId>org.mybatis</groupId>
               <artifactId>mybatis-spring</artifactId>
               <version>2.0.6</version>
           </dependency>
            <!--https://mvnrepository.com/artifact/org.projectlombok/lombok -->
           <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok</artifactId>
               <version>1.18.24</version>
               <scope>provided</scope>
           </dependency>
           <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
           <dependency>
               <groupId>org.slf4j</groupId>
               <artifactId>slf4j-api</artifactId>
               <version>1.7.30</version>
           </dependency>
           <!--         https://mvnrepository.com/artifact/org.slf4j/slf4j-simple-->
           <dependency>
               <groupId>org.slf4j</groupId>
               <artifactId>slf4j-simple</artifactId>
               <version>1.7.30</version>
               <!-- 注意，若无type为jar则报错-->
               <type>jar</type>
           </dependency>
   
   
       </dependencies>
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
                   <filtering>false</filtering>
               </resource>
           </resources>
       </build>
   </project>
   
   ```



2. 编写实体类

   ```java
   package com.zhaolei.pojo;
   public class User {
       private int id;
       private String name;
       private String pwd;
   
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



3. 编写核心配置文件

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "https://mybatis.org/dtd/mybatis-3-config.dtd">
   <!--核心配置-->
   <configuration>
       <environments default="development">
           <environment id="development">
               <transactionManager type="JDBC"/>
               <dataSource type="POOLED">
                   <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                   <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;usseUnicode=true&amp;characterEncoding=UTF-8"/>
                   <property name="username" value="root"/>
                   <property name="password" value="123456"/>
               </dataSource>
           </environment>
       </environments>
       <mappers>
           <mapper class="com.zhaolei.mapper.UserMapper"></mapper>
       </mappers>
   </configuration>
   ```

4. 编写接口

   ```java
   package com.zhaolei.mapper;
   
   import com.zhaolei.pojo.User;
   
   import java.util.List;
   
   public interface UserMapper {
       public List<User> selectUser();
   }
   
   ```



5. 编写Mapper.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <!--核心配置-->
   <mapper namespace="com.zhaolei.mapper.UserMapper">
       <select id="selectUser" resultType="com.zhaolei.pojo.User">
           select * from mybatis.user;
       </select>
   
   </mapper>
   ```



6. 测试

   ```java
   package com.zhaolei;
   
   import com.zhaolei.mapper.UserMapper;
   import com.zhaolei.pojo.User;
   import org.apache.ibatis.io.Resources;
   import org.apache.ibatis.session.SqlSession;
   import org.apache.ibatis.session.SqlSessionFactory;
   import org.apache.ibatis.session.SqlSessionFactoryBuilder;
   import org.junit.Test;
   
   import java.io.IOException;
   import java.io.InputStream;
   import java.util.List;
   
   /**
    * Unit test
    */
   public class AppTest{
       @Test
       public void test() throws IOException {
           String resources = "mybatis-config.xml";
           InputStream input = Resources.getResourceAsStream(resources);
           SqlSessionFactory sessionFactory =new SqlSessionFactoryBuilder().build(input);
           SqlSession sqlSession = sessionFactory.openSession(true);
   
           UserMapper mapper = sqlSession.getMapper(UserMapper.class);
           List<User> userList = mapper.selectUser();
           for (User user : userList) {
               System.out.println(user);
           }
       }
   }
   
   ```

## 12.2、整合

1. 编写数据源配置

2. 编写sqlSessionFactory连接数据库

3. 编写sqlSessionTemplate生成连接对象实例sqlSession

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
                              https://www.springframework.org/schema/beans/spring-beans.xsd
                              http://www.springframework.org/schema/context
                              https://www.springframework.org/schema/context/spring-context.xsd
                              http://www.springframework.org/schema/aop
                              https://www.springframework.org/schema/aop/spring-aop.xsd">
   
          <!--配置数据库，这里使用Spring的数据源代替Mybatis的数据源-->
          <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
                 <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
                 <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;usseUnicode=true&amp;characterEncoding=UTF-8"/>
                 <property name="username" value="root"/>
                 <property name="password" value="123456"/>
          </bean>
          <!--sqlSessionFactory连接数据库-->
          <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
                 <property name="dataSource" ref="dataSource"/>
                 <!--绑定配置-->
                 <property name="configLocation" value="classpath:mybatis-config.xml"/>
                 <property name="mapperLocations" value="classpath:com/zhaolei/mapper/*.xml"/>
          </bean>
   
          <!--实例化连接对象-->
          <bean id="sqlsession" class="org.mybatis.spring.SqlSessionTemplate">
                 <!--只能使用构造器进行注入，因为SqlSessionTemplate没有set方法-->
                 <constructor-arg index="0" ref="sqlSessionFactory"/>
          </bean>
   
   
   </beans>
   ```

4. 编写接口类的实现类

   ```java
   package com.zhaolei.mapper;
   
   import com.zhaolei.pojo.User;
   import org.mybatis.spring.SqlSessionTemplate;
   
   import java.util.List;
   
   public class UserMapperImpl implements UserMapper{
       private SqlSessionTemplate sqlSession;
   
       public void setSqlSession(SqlSessionTemplate sqlSession) {
           this.sqlSession = sqlSession;
       }
   
       public List<User> selectUser() {
           UserMapper mapper = sqlSession.getMapper(UserMapper.class);
           return mapper.selectUser();
       }
   }
   
   ```



5. 通过实现类把实例sqlSession注入，调用实例sqlSession的方法

6. 在spring中注册实现类

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
                              https://www.springframework.org/schema/beans/spring-beans.xsd
                              http://www.springframework.org/schema/context
                              https://www.springframework.org/schema/context/spring-context.xsd
                              http://www.springframework.org/schema/aop
                              https://www.springframework.org/schema/aop/spring-aop.xsd">
   
       <import resource="spring-dao.xml"/>
   
       <!--通过类注入sqlsession，调用sqlsession的方法-->
       <bean id="usermapper" class="com.zhaolei.mapper.UserMapperImpl">
           <property name="sqlSession" ref="sqlsession"/>
       </bean>
   
   </beans>
   ```



7. 测试

   ```java
   package com.zhaolei;
   
   import com.zhaolei.mapper.UserMapper;
   import com.zhaolei.pojo.User;
   import org.apache.ibatis.io.Resources;
   import org.apache.ibatis.session.SqlSession;
   import org.apache.ibatis.session.SqlSessionFactory;
   import org.apache.ibatis.session.SqlSessionFactoryBuilder;
   import org.junit.Test;
   import org.springframework.context.ApplicationContext;
   import org.springframework.context.support.ClassPathXmlApplicationContext;
   
   import java.io.IOException;
   import java.io.InputStream;
   import java.util.List;
   
   /**
    * Unit test
    */
   public class AppTest{
       @Test
       public void test() throws IOException {
           ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
           UserMapper usermapper = context.getBean("usermapper", UserMapper.class);
           for (User user : usermapper.selectUser()) {
               System.out.println(user);
           }
   
       }
   }
   
   ```





# 13、声明事务

## 13.1、回顾事务

- 把一组业务当成一个业务来做；要么都成功，要么都失败；
- 事务在项目开发中十分重要，涉及到数据的一致性问题，不能马虎！
- 确保完整性和一致性；



事物的ACID原则：

- 原子性
- 一致性
- 隔离性
   - 多个业务可能操作同一个资源，防止数据损坏
- 持久性
   - 事务一旦提交，无论系统发生什么问题，结果都不会再被影响，被持久化的写到存储器中！



## 13.2、spring中的事务管理

- 声明式事务：AOP
- 编程式事务：需要在代码中进行事务的管理，如异常捕获，回滚等操作



思考：

为什么需要事务：

- 如果不配置事务，可能存在数据提交不一致的问题！



使用方法：

```xml
       <!--配置事务声明-->
       <bean id="transcationManage" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
              <property name="dataSource" ref="dataSource"/>
       </bean>

       <!--结合AOP实现事务的织入-->
       <!--声明事务-->
       <tx:advice id="txAdvice" transaction-manager="transcationManage">
              <!--给方法配置事务-->
              <tx:attributes>
                     <tx:method name="add" propagation="REQUIRED"/>
                     <tx:method name="delete" propagation="REQUIRED"/>
                     <tx:method name="query" read-only="true"/>
                     <tx:method name="*" propagation="REQUIRED"/>
              </tx:attributes>
       </tx:advice>

       <!--配置事务切入-->
       <aop:config>
              <aop:pointcut id="txPointCut" expression="execution(* com.zhaolei.mapper.*.*(..))"/>
              <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>
       </aop:config>

```

AOP是横切的，只需要在spring-dao.xml文件中增加配置即可!

spring-dao.xml:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           https://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context
                           https://www.springframework.org/schema/context/spring-context.xsd
                           http://www.springframework.org/schema/aop
                           https://www.springframework.org/schema/aop/spring-aop.xsd
                           http://www.springframework.org/schema/tx
                           https://www.springframework.org/schema/tx/spring-tx.xsd">

       <!--配置数据库，这里使用Spring的数据源代替Mybatis的数据源-->
       <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
              <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
              <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;usseUnicode=true&amp;characterEncoding=UTF-8"/>
              <property name="username" value="root"/>
              <property name="password" value="123456"/>
       </bean>
       <!--sqlSessionFactory连接数据库-->
       <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
              <property name="dataSource" ref="dataSource"/>
              <!--绑定配置-->
              <property name="configLocation" value="classpath:mybatis-config.xml"/>
              <property name="mapperLocations" value="classpath:com/zhaolei/mapper/*.xml"/>
       </bean>

       <!--实例化连接对象-->
       <bean id="sqlsession" class="org.mybatis.spring.SqlSessionTemplate">
              <!--只能使用构造器进行注入，因为SqlSessionTemplate没有set方法-->
              <constructor-arg index="0" ref="sqlSessionFactory"/>
       </bean>
       <!--配置事务声明-->
       <bean id="transcationManage" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
              <property name="dataSource" ref="dataSource"/>
       </bean>

       <!--结合AOP实现事务的织入-->
       <!--声明事务-->
       <tx:advice id="txAdvice" transaction-manager="transcationManage">
              <!--给方法配置事务-->
              <tx:attributes>
                     <tx:method name="add" propagation="REQUIRED"/>
                     <tx:method name="delete" propagation="REQUIRED"/>
                     <tx:method name="query" read-only="true"/>
                     <tx:method name="*" propagation="REQUIRED"/>
              </tx:attributes>
       </tx:advice>

       <!--配置事务切入-->
       <aop:config>
              <aop:pointcut id="txPointCut" expression="execution(* com.zhaolei.mapper.*.*(..))"/>
              <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>
       </aop:config>


</beans>
```

