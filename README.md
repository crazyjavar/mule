# mule


一个简单的权限框架，通过将所有的Controller与@RequestMapping中的value值（在这里我称这个为mapping映射）收集起来，当成权限表中的资源。
然后通过角色权限表(role_permission)记录角色表与权限表的关系，使得分配的角色拥有不同的权限，然后用户角色表(account_role)又记录用户表(account)与角色表(role)的关系，使得用户与角色表关联起来，所以分配权限的问题，也就变成了给用户分配角色的问题。
## 项目文档
项目中集成了knife4j框架，启动项目输入http://localhost:8080/doc.html，即可看到。
## 理解RBAC模型
理解RBAC模型：
 总共5张表，分别是用户表(account),用户角色表(account_role),权限表(permission),角色权限表(role_permission),角色表(role)
1. 用户表，记录所有用户。(account)
2. 用户角色表，记录每个用户被授予的角色。(account_role)
3. 权限表，也称资源表，记录所有的资源URL。(permission)
4. 角色权限表，记录每个角色都能访问哪些权限。(role_permission)
5. 角色表，记录所有的角色。(role)

### 表结构如下：
1. 用户表(account)

| id  | name | password | salt |      createAt       |
| --- | ---- | -------- | ---- | ------------------- |
| 1   | 阿飞 | xxx      | xxxx | 1999-09-01 11:11:11 |

2. 用户角色表(account_role)

| account_id | role_id |
| ---------- | ------- |
| 1          | 1       |

3. 权限表(permission)

| id  | mapping |                 contoller                  | remark |
| --- | ------- | ------------------------------------------ | ------ |
| 1   | /hello  | com.devops.minisystem.role.HelloController | 哈喽   |

4. 角色权限表(role_permission)

| role_id | permission_id |
| ------- | ------------- |
| 1       | 1             |

5. 角色表(role)

| id  |   name    |      createAt       |
| --- | --------- | ------------------- |
| 1   | 超级管理员 | 1999-09-01 11:11:11 |

## 自定义注解
一个自定义注解`@Remark`，来标记每一个mapping，从而可以向 permission 表的 remark 字段中添加 @Remark 注解的内容。也就是项目中的一键同步权限。
项目中添加新方法后，会使得权限表(permission)表中的mapping成为旧值，所以写了一个一键同步的功能，这样子就不用手动的去维护权限表。

## 技术
 
|       技术       |     说明     |                                           官网                                           |
| ---------------- | ----------- | ---------------------------------------------------------------------------------------- |
| SpringBoot 2.2.8 | 容器+MVC框架 | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)         |
| JPA 2.2.8        | ORM框架      | [https://spring.io/projects/spring-data-jpa](https://spring.io/projects/spring-data-jpa) |
| knife4j 2.0.1    | 文档框架     | [https://doc.xiaominfo.com/](https://doc.xiaominfo.com/)                                 |
| hutool 5.3.6     | 工具包       | [https://hutool.cn/docs/#/](https://hutool.cn/docs/#/)                                   |

[博客地址](https://juejin.im/post/5edbb27cf265da76b67bfb35)
email: creazycoder@sina.com
