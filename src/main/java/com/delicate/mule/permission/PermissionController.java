package com.delicate.mule.permission;

import com.delicate.mule.annotation.Remark;
import com.delicate.mule.common.Ret;
import com.delicate.mule.model.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 权限管理
 */

@Api(tags = "权限管理")
@RequestMapping("/admin/permission")
@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    @ApiOperation("权限管理首页")
    @Remark("权限管理首页")
    @GetMapping("/index")
    public Ret index(@RequestParam(value = "page", defaultValue = "0") Integer page,
                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                     HttpServletRequest request) {
        // 获取Controller中的url
        Page<Permission> permissionPage = permissionService.paginate(page, size);
        boolean hasRemovedPermission = permissionService.markRemovedMapping(permissionPage, request);
        return Ret.ok("permissionPage", permissionPage).set("hasRemovedPermission", hasRemovedPermission);
    }


    @ApiOperation("权限一键同步")
    @Remark("权限一键同步")
    @PostMapping(value = "/sync")
    public Ret permissionList() {
        return permissionService.sync();
    }

    @ApiOperation("查看所有权限")
    @Remark("查看所有权限")
    @GetMapping(value = "/getAllPermission")
    public Ret allPermission() {
        List<Permission> allPermission = permissionService.getAllPermissions();
        return Ret.ok("allPermission", allPermission);
    }

    @ApiOperation("修改备注信息")
    @Remark("修改备注信息")
    @PostMapping("/remark")
    public Ret update(Permission permission) {
        return permissionService.save(permission);
    }


    @ApiOperation("修改权限")
    @Remark("修改权限")
    @GetMapping("/edit")
    public Ret edit(Long id) {
        Permission permission = permissionService.findById(id);
        return Ret.ok("permission", permission);
    }

    @ApiOperation("删除权限")
    @Remark("删除权限")
    @GetMapping("/delete")
    public Ret delete(Long id) {
        Ret ret = permissionService.delete(id);
        return ret;
    }

}
