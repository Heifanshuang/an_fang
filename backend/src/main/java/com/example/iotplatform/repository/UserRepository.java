package com.example.iotplatform.repository;

import com.example.iotplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据账号查询用户
     */
    User findByAccount(String account);

    /**
     * 根据邮箱查询用户
     */
    User findByEmail(String email);

    /**
     * 根据API Key查询用户
     */
    User findByApiKey(String apiKey);

    /**
     * 根据手机号查询用户
     */
    User findByTelephone(String telephone);

}