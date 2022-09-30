package com.my.cloud.service.impl;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import com.my.cloud.common.exception.code.CodeMismatchException;
import com.my.cloud.common.exception.code.NullCodeException;
import com.my.cloud.common.exception.user.DuplicateEmail;
import com.my.cloud.common.exception.user.DuplicateUsername;
import com.my.cloud.common.pojo.User;
import com.my.cloud.common.repository.UserRepository;
import com.my.cloud.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * UserService，主要进行一系列的用户操作
 * 包括：登录、注册
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/23 15:25
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    /**
     * 用户注册方法，调用repository进行注册
     *
     * @param password 用户注册的密码
     * @param username 用户注册的用户名
     * @param email    用户注册的邮箱账号
     * @param code     用户注册用于验证的验证码
     *                 用户注册的ID为自动生成，不需要注入
     * @throws DuplicateUsername 如果有重复的用户名则会抛出此异常
     */
    @Override
    public User register(String username, String password, String email, String code) throws DuplicateUsername, DuplicateEmail, CodeMismatchException, NullCodeException {
        //从Redis中取出对应的验证码，存储
        Integer redisCode = redisTemplate.opsForValue().get(email);
        //判空处理
        if (redisCode == null) {
            throw new NullCodeException("不存在验证码");
            //匹配处理
        } else if (!redisCode.equals(Integer.valueOf(code))) {
            throw new CodeMismatchException("验证码不匹配");
        } else {
            //首先判断用户名和邮箱号是否重复，如果重复的话就抛出异常
            if (this.userRepository.countUserByUsername(username) != 0) {
                throw new DuplicateUsername("重复的用户用户名");
            }
            if (this.userRepository.countUserByEmail(email) != 0) {
                throw new DuplicateEmail("重复的邮箱号");
            }

            //如果没有重复的话就开始创建用户
            User user = new User();
            user.setUsername(username);
            //密码加密保存，使用HuTool工具包的十六进制计算进行加密
            user.setPassword(HexUtil.encodeHexStr(password, CharsetUtil.CHARSET_UTF_8));
            user.setEmail(email);
            //repository保存
            userRepository.save(user);
            return user;
        }
    }

    /**
     * 用户登录方法 - 用户名密码登录
     * 以用户名和密码前往数据库中查找对应的用户
     * 这里不需要进行判断是否存在，如果不存在查询结果自然为null
     *
     * @param username 用户名
     * @param password 密码
     * @return 数据库中存在的用户
     */
    @Override
    public User loginByPassword(String username, String password) {
        return this.userRepository.findUserByUsernameAndPassword(username,
                HexUtil.encodeHexStr(password, CharsetUtil.CHARSET_UTF_8));
    }

    /**
     * 与Redis中的验证码进行匹配，如果不同或者不存在则抛出异常
     *
     * @param email 用户注册使用的邮箱
     * @param code  用户填写的验证码
     * @return 返回查询到的用户信息，如果没有查询到则为空值
     * @throws NullCodeException     Redis中不存在此邮箱的验证码
     * @throws CodeMismatchException Redis中存储的验证码和用户输入的不一致
     */
    @Override
    public User loginByEmail(String email, String code) throws NullCodeException, CodeMismatchException {
        //从Redis中取出对应的验证码，存储
        Integer redisCode = redisTemplate.opsForValue().get(email);
        //判空处理
        if (redisCode == null) {
            throw new NullCodeException("不存在验证码");
            //匹配处理
        } else if (!redisCode.equals(Integer.valueOf(code))) {
            throw new CodeMismatchException("验证码不匹配");
        } else {
            //dao层查询
            User user = this.userRepository.findUserByEmail(email);
            if (user != null) {
                redisTemplate.delete(email);
            }
            return user;
        }
    }
}
