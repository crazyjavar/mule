package com.delicate.mule.login;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.delicate.mule.common.Ret;
import com.delicate.mule.dao.AccountRepository;
import com.delicate.mule.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class LoginService {

    @Autowired
    private AccountRepository repository;

    public Ret login(String name, String password) {
        name = name.toLowerCase().trim();
        password = password.trim();
        Account loginAccount = repository.findByName(name);
        if (loginAccount == null) {
            return Ret.fail("msg", "用户名或密码不正确");
        }
        // 省略判断账号是否锁定，激活等
        String salt = loginAccount.getSalt();
        String hashedPass = SecureUtil.sha256(salt + password);
        if (!loginAccount.getPassword().equals(hashedPass)) {
            return Ret.fail("msg", "用户名或密码不正确");
        }

        return Ret.ok("msg", "登录成功");
    }

    public Ret reg(String name, String password) {
        name = name.toLowerCase().trim();
        password = password.trim();

        if (isNameExists(name)) {
            return Ret.fail("msg", "名称已被注册，请换一个名称");
        }
       	// 密码加盐 hash
        String salt = IdUtil.simpleUUID();
        password = SecureUtil.sha256(salt + password);
        Account account = new Account();
        account.setName(name);
        account.setPassword(password);
        account.setSalt(salt);
        repository.save(account);
        return Ret.ok("msg", "注册成功");
    }

    public boolean isNameExists(String name) {
        return repository.findByName(name.toLowerCase().trim()) != null;
    }
}
