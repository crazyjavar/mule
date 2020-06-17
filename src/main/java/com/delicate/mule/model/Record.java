package com.delicate.mule.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 用于返回后台管理员信息，接口为/admin/account/showAdminList
 *
 * @author caiji Mr. Li
 * @date 2020/6/6 16:54
 */


@SqlResultSetMapping(name = "adminLists",
        entities = {
                @EntityResult(entityClass = com.delicate.mule.model.Record.class, fields = {
                        @FieldResult(name = "name", column = "name"),
                        @FieldResult(name = "accountId", column = "account_id"),
                        @FieldResult(name = "roleId", column = "role_id"),
                        @FieldResult(name = "roleName", column = "role_name")
                })}
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Record {

    /**
     * 用户id
     */
    @Id
    private Long accountId;

    /**
     * 用户名
     */
    private String name;


    private Long roleId;

    /**
     * 角色名
     */
    private String roleName;

}
