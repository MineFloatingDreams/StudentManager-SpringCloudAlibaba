package com.my.cloud.common.repository;

import com.my.cloud.common.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 用户持久层
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/23 10:34
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    User findUserByUsername(String username);

    /**
     * 根据用户名密码查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户
     */
    User findUserByUsernameAndPassword(String username, String password);

    /**
     * 根据用户名查询用户
     *
     * @param email 邮箱
     * @return 用户
     */
    User findUserByEmail(String email);

    /**
     * 根据邮箱统计用户数量
     *
     * @param email 邮箱
     * @return 用户数量
     */
    Integer countUserByEmail(String email);

    /**
     * 根据用户名统计用户数量
     *
     * @param username 用户名
     * @return 用户数量
     */
    Integer countUserByUsername(String username);
}
