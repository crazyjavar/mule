package com.delicate.mule.dao;

import com.delicate.mule.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    /**
     * 根据roleId与permissionId 删除记录
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    long deleteByRoleIdAndAndPermissionId(Long roleId, Long permissionId);

    /**
     * 根据permissionId 删除记录
     *
     * @param permissionId
     * @return
     */
    long deleteByPermissionId(Long permissionId);
}
