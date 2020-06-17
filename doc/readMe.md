# 项目
# 项目介绍
## 1.simple-security框架：
一个简单的权限框架，最近看过MVC源码后，有了一点思路。
将所有的Controller与@RequestMapping中的value值（在这里我称这个为mapping映射）收集起来，当成权限表中的资源。然后通过角色权限表(role_permission)记录角色表与权限表的关系，使得分配的角色拥有不同的权限，然后用户角色表(account_role)又记录用户表(account)与角色表(role)的关系，使得用户与角色表关联起来，所以分配权限的问题，也就变成了给用户分配角色的问题。
SpringBoot 2.2.8 ,JPA 2.2.8 ,JJWT 0.9.1 ,文档knife4j 2.0.1 ,工具包hutool 5.3.6,权限部分没有使用其他的框架，自己手写完成。主要思想就是收集项目中所有的url，当成权限表中的资源，然后根据角色表与权限表进行关联，使得每个角色可以分配不同的权限

## 思路
权限控制，以前没做过，花时间研究了一下RBAC模型，然后自己动手整了一个。

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


小细节：
通过一个自定义注解`@Remark`，来标记每一个mapping，从而可以向 permission 表的 remark 字段中添加 @Remark 注解的内容。也就是项目中的一键同步权限。
项目中添加新方法后，会使得权限表(permission)表中的mapping成为旧值，所以写了一个一键同步，这样子就不用手动的去维护权限表。

联系人： 李博
手机：17666542054
email: creazycoder@sina.com