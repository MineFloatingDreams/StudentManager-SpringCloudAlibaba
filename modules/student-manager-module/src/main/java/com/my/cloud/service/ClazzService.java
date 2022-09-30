package com.my.cloud.service;

import com.my.cloud.common.exception.clazz.ClazzNotFoundException;
import com.my.cloud.common.exception.clazz.DuplicateClazzName;
import com.my.cloud.common.pojo.Clazz;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClazzService {
    /**
     * 添加班级信息
     *
     * @param clazzName 班级名称
     * @throws DuplicateClazzName 重复的班级名称异常
     */
    void addClass(String clazzName) throws DuplicateClazzName;

    /**
     * 更新班级信息
     *
     * @param clazzName 班级名称
     * @param id        需要修改的班级id
     * @throws ClazzNotFoundException 找不到班级异常
     * @throws DuplicateClazzName     重复的班级名称异常
     */
    void updateClass(String clazzName, Long id) throws ClazzNotFoundException, DuplicateClazzName;

    /**
     * 删除班级信息
     *
     * @param ids 需要删除的班级的id
     * @throws ClazzNotFoundException 找不到班级异常
     */
    void deleteClass(List<Long> ids) throws ClazzNotFoundException;

    /**
     * 搜索班级详细信息
     *
     * @param context     搜索内容
     * @param tags        搜索标签，用于搜索学生的姓名、学号、班级等信息
     * @param currentPage 当前页
     * @param pageSize    每页显示的记录数
     * @param sort        排序字段
     * @param asc         是否升序
     * @return 返回查询出来的班级结果
     */
    Page<Clazz> searchClass(String context, String tags, Integer currentPage, Integer pageSize, String sort, Boolean asc);


    List<Clazz> showAll();
}