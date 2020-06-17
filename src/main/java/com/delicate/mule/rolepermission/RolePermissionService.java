package com.delicate.mule.rolepermission;

import com.delicate.mule.common.Ret;
import com.delicate.mule.dao.RolePermissionRepository;
import com.delicate.mule.model.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionService {

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    /**
     * 添加权限
     */
    public Ret addPermission(Long roleId, Long permissionId) {
        if (roleId == 1) {
            return Ret.fail("msg", "超级管理员天然拥有所有权限，无需分配");
        }

        rolePermissionRepository.save(new RolePermission(roleId, permissionId));
        return Ret.ok("msg", "添加权限成功");
    }

    /**
     * 删除权限
     */
    public Ret deletePermission(Long roleId, Long permissionId) {
        if (roleId == 1) {
            return Ret.fail("msg", "超级管理员天然拥有所有权限，不能删除权限");
        }
        rolePermissionRepository.deleteByRoleIdAndAndPermissionId(roleId, permissionId);
        return Ret.ok("msg", "删除权限成功");
    }
}
