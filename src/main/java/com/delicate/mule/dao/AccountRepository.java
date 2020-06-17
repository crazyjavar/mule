package com.delicate.mule.dao;

import com.delicate.mule.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * 通过name查询用户
     *
     * @param name 名字
     * @return
     */
    Account findByName(String name);


//    <T> Collection<T> findByName(String name, Class<T> tClass);

}
