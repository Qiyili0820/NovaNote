package com.testdemo.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.testdemo.demo.Entities.accountsEntity;

@Repository
public class AccountRepository {
public Optional<Integer> findIdByAccount(String account) {
    String sql = "SELECT id FROM accounts WHERE account = ?";
    List<Integer> result = jdbcTemplate.query(
        sql,
        (rs, rowNum) -> rs.getInt("id"),
        account
    );
    return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
}

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public accountsEntity query() {
        String sql = "SELECT * FROM accounts WHERE id = 1";
        List<accountsEntity> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(accountsEntity.class));
        return result.isEmpty() ? null : result.get(0);
    }

    public accountsEntity query2(Integer id) {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        List<accountsEntity> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(accountsEntity.class), id);
        return result.isEmpty() ? null : result.get(0);
    }

    public Optional<accountsEntity> findByAccount(String account) {
        String sql = "SELECT * FROM accounts WHERE account = ?";
        List<accountsEntity> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(accountsEntity.class), account);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public void save(accountsEntity newAccount) {
        String sql = "INSERT INTO accounts (account, password, name) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, newAccount.getAccount(), newAccount.getPassword(), newAccount.getName());
    }

    public int deleteByAccount(String account) {
        String sql = "DELETE FROM accounts WHERE account = ?";
        return jdbcTemplate.update(sql, account);
    }
}
