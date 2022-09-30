package com.my.cloud.common.repository;

import com.my.cloud.common.pojo.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: zhangjiachen
 * @Date: 2022/9/28 10:39
 */
@Repository
public interface ClazzRepository extends JpaRepository<Clazz, Long>, JpaSpecificationExecutor<Clazz> {

    /**
     * 根据班级id获取班级信息
     *
     * @param id 班级ID
     * @return 查询结果
     */
    Clazz getClazzById(Long id);

    /**
     * 根据班级名称获取班级信息
     *
     * @param name 班级名称
     * @return 查询结果
     */
    Clazz getClazzByClazzName(String name);

    List<Clazz> findByIdIn(List<Long> lds);

}
