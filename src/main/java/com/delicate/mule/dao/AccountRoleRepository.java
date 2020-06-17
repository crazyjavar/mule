package com.delicate.mule.dao;

import com.delicate.mule.model.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {

    /**
     * 查询用户是否是超级管理员，也就是判断用户的role_id是否等于1
     *
     * @param accountId
     * @return
     */
    @Query("select u from AccountRole u where u.accountId=?1 and u.roleId=1")
    AccountRole isSuperAdmin(Long accountId);

    /**
     * 根据roleId删除
     *
     * @param roleId
     * @return
     */
    long deleteByRoleId(Long roleId);
}
