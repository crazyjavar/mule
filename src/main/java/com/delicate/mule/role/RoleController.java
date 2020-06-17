package com.delicate.mule.role;

import com.delicate.mule.annotation.Remark;
import com.delicate.mule.common.Ret;
import com.delicate.mule.model.Permission;
import com.delicate.mule.model.Role;
import com.delicate.mule.permission.PermissionService;
import com.delicate.mule.rolepermission.RolePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 角色管理控制器
 */

@Api(tags = "角色管理控制器")
@RequestMapping("/admin/role")
@RestController
public class RoleController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolePermissionService rolePermissionService;


    @Remark("角色管理首页")
    @ApiOperation("角色管理首页")
    @GetMapping("/index")
    public Ret index(@RequestParam(value = "page", defaultValue = "0") Integer page,
                     @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Page<Role> rolePage = roleService.paginate(page, size);
        return Ret.ok("rolePage", rolePage);
    }

    @Remark("角色管理首页")
    @ApiOperation("编辑角色")
    @PostMapping("/edit")
    public Ret edit(Long id) {
        Role role = roleService.findById(id);
        return Ret.ok("role", role);
    }

    @Remark("角色管理首页")
    @ApiOperation("创建或更新角色")
    @PostMapping("/saveOrUpdate")
    public Ret saveOrUpdate(Role role) {
        return roleService.saveOrUpdate(role);
    }

    @Remark("删除角色")
    @ApiOperation("删除角色")
    @PostMapping("/delete")
    public Ret delete(Long id) {
        return roleService.deleteById(id);
    }

    @Remark("分配权限")
    @ApiOperation("分配权限")
    @PostMapping("/assignPermissions")
    public Ret assignPermissions(Long id) {
        Role role = roleService.findById(id);
        List<Permission> permissionList = permissionService.getAllPermissions();
        LinkedHashMap<String, List<Permission>> permissionMap = roleService.groupByController(permissionList);
        return Ret.ok("role", role).set("permissionMap", permissionMap);
    }

    @Remark("添加权限")
    @ApiOperation("添加权限")
    @PostMapping("/addPermission")
    public Ret addPermission(Long roleId, Long permissionId) {
        return rolePermissionService.addPermission(roleId, permissionId);
    }

    @Remark("删除权限")
    @ApiOperation("删除权限")
    @PostMapping("/deletePermission")
    public Ret deletePermission(Long roleId, Long permissionId) {
        return rolePermissionService.deletePermission(roleId, permissionId);
    }
}
