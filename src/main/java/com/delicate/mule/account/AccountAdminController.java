package com.delicate.mule.account;

import com.delicate.mule.accountrole.AccountRoleService;
import com.delicate.mule.annotation.Remark;
import com.delicate.mule.auth.AdminAuthService;
import com.delicate.mule.common.Ret;
import com.delicate.mule.model.Account;
import com.delicate.mule.model.Record;
import com.delicate.mule.model.Role;
import com.delicate.mule.role.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 账号管理控制器
 */

@Api(tags = "角色管理控制器")
@RequestMapping("/admin/account")
@RestController
public class AccountAdminController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AccountRoleService accountRoleService;

    @Autowired
    private AdminAuthService adminAuthService;

    @Remark("账户管理首页")
    @ApiOperation("账户管理首页")
    @GetMapping("/index")
    public Ret index(@RequestParam(value = "page", defaultValue = "0") Integer page,
                     @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Page<Account> accountPage = accountService.paginate(page, size);
        return Ret.ok("accountPage", accountPage);
    }

    @Remark("账户管理首页")
    @ApiOperation("编辑账户")
    @PostMapping("/edit")
    public Ret edit(Long id) {
        Account account = accountService.findById(id);
        return Ret.ok("account", account);
    }

    /**
     * 提交修改
     */
    @Remark("账户管理首页")
    @ApiOperation("提交修改")
    @PostMapping("/update")
    public Ret update(Account account) {
        accountService.save(account);
        return Ret.ok("msg", "修改成功");
    }


    /**
     * 分配角色,就是把用户信息与所有角色信息查出来
     */
    @Remark("分配角色")
    @ApiOperation("分配角色")
    @PostMapping("assignRoles")
    public Ret assignRoles(Long id) {
        Account account = accountService.findById(id);
        List<Role> roleList = roleService.getAllRoles();
        return Ret.ok().set("account", account).set("roleList", roleList);
    }

    @Remark("添加角色")
    @ApiOperation("添加角色")
    @PostMapping("/addRole")
    public Ret addRole(Long accountId, Long roleId) {
        return accountRoleService.addRole(accountId, roleId);
    }

    @Remark("删除角色")
    @ApiOperation("删除角色")
    @PostMapping("/deleteRole")
    public Ret deleteRole(Long accountId, Long roleId) {
        return accountRoleService.deleteRole(accountId, roleId);
    }

    /**
     * 显示 "后台账户/管理员" 列表，在 account_role 表中存在的账户(被分配过角色的账户)
     * 被定义为 "后台账户/管理员"
     * <p>
     * 该功能便于查看后台都有哪些账户被分配了角色，在对账户误操作分配了角色时，也便于取消角色分配
     */
    @Remark("后台管理员列表")
    @ApiOperation("后台管理员列表")
    @PostMapping("/showAdminList")
    public Ret showAdminList() {
        List<Record> adminList = adminAuthService.getAdminList();
        return Ret.ok().set("adminList", adminList);
    }

}
