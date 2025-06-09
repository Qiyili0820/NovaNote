package com.testdemo.demo.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testdemo.demo.Entities.accountsEntity;
import com.testdemo.demo.repository.AccountRepository;

@Service
public class accountService {

    @Autowired
    private AccountRepository accountRepository;

    public accountsEntity getID1() {
        return accountRepository.query();
    }

    public accountsEntity getByID(Integer id) {
        return accountRepository.query2(id);
    }

    public String register(accountsEntity newAccount) {
        Optional<accountsEntity> existing = accountRepository.findByAccount(newAccount.getAccount());
        if (existing.isPresent()) {
            return "帳號已存在！";
        } else {
            accountRepository.save(newAccount);
            return "註冊成功！";
        }
    }

    public Optional<accountsEntity> login(String account, String password) {
        Optional<accountsEntity> existing = accountRepository.findByAccount(account);
        return existing.filter(a -> a.getPassword().equals(password));
    }

public String deleteAccount(String account) {
    Optional<accountsEntity> existing = accountRepository.findByAccount(account);
    if (existing.isPresent()) {
        accountRepository.deleteByAccount(account);
        return "刪除成功";
    } else {
        return "帳號不存在";
    }
}
// 查詢帳號資料
public accountsEntity getAccount(String account) {
    return accountRepository.findByAccount(account).orElse(null);
}

// 修改帳號資料（只改 name / password，不改 account）
public String updateAccount(String account, accountsEntity updateData) {
    Optional<accountsEntity> existing = accountRepository.findByAccount(account);
    if (existing.isPresent()) {
        accountsEntity acc = existing.get();
        acc.setName(updateData.getName());
        acc.setPassword(updateData.getPassword());
        acc.setEmail(updateData.getEmail());
        accountRepository.save(acc);
        return "修改成功";
    } else {
        return "帳號不存在";
    }
}


}

