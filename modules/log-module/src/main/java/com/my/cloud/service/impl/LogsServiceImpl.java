package com.my.cloud.service.impl;

import com.my.cloud.common.pojo.Logs;
import com.my.cloud.common.pojo.User;
import com.my.cloud.common.repository.LogsRepository;
import com.my.cloud.common.repository.UserRepository;
import com.my.cloud.service.LogsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: zhangjiachen
 * @Date: 2022/9/27 16:33
 */
@Service
public class LogsServiceImpl implements LogsService {

    @Resource
    private LogsRepository logsRepository;

    @Resource
    private UserRepository userRepository;

    @Override
    public void addLogs(Integer code, String message, Long userId) {
        User user = new User();
        user.setId(userId);
        Logs logs = new Logs();
        logs.setCode(code);
        logs.setMessage(message);
        logs.setUser(user);
        logs.setDate(new Date());
        System.out.print(logs);
        logsRepository.save(logs);
    }

    @Override
    public void addLogsByUsername(Integer code, String message, String username) {
        User user = new User();
        user.setUsername(username);
        user = this.userRepository.findUserByUsername(username);
        if (user != null) {
            Logs logs = new Logs();
            logs.setCode(code);
            logs.setMessage(message);
            logs.setUser(user);
            logs.setDate(new Date());
            System.out.print(logs);
            logsRepository.save(logs);
        }
    }
}
