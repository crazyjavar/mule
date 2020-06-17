package com.delicate.mule.accountrole;

import com.delicate.mule.common.Ret;
import com.delicate.mule.dao.AccountRoleRepository;
import com.delicate.mule.model.AccountRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountRoleService {

    @Autowired
    private AccountRoleRepository repository;

    /**
     * 是否为超级管理员，role.id 值为 1 的为超级管理员
     */
    public boolean isSuperAdmin(Long id) {
        return repository.isSuperAdmin(id) != null;
    }

    public Ret addRole(Long accountId, Long roleId) {
        AccountRole accountRole =new AccountRole(accountId,roleId);
        repository.save(accountRole);
        return Ret.ok("msg", "添加角色成功");
    }

    public Ret deleteRole(Long accountId, Long roleId) {
        AccountRole accountRole =new AccountRole(accountId,roleId);
        repository.delete(accountRole);
        return Ret.ok("msg", "删除角色成功");
    }
}
