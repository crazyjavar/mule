package com.delicate.mule.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 用户角色表
 *
 * @author caiji Mr. Li
 * @date 2020/6/4 10:47
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolePermission {


    @Id
    private Long roleId;
    private Long permissionId;
}
