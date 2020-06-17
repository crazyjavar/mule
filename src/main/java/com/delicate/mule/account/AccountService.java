package com.delicate.mule.account;

import com.delicate.mule.dao.AccountRepository;
import com.delicate.mule.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;


    public Page<Account> paginate(Integer page, Integer size) {
        return accountRepository.findAll(PageRequest.of(page, size, Sort.by("id")));
    }

    public Account findById(Long id) {
        return accountRepository.getOne(id);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public Account findByName(String name) {
        return accountRepository.findByName(name);
    }
}
