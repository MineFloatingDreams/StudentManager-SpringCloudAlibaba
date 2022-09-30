package com.my.cloud.common.repository;

import com.my.cloud.common.pojo.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 日志持久层
 * @Author: zhangjiachen
 * @Date: 2022/9/27 16:31
 */
@Repository
public interface LogsRepository extends JpaRepository<Logs, Long>, JpaSpecificationExecutor<Logs> {

}
