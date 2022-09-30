package com.my.cloud.service;

import com.my.cloud.common.exception.clazz.ClazzNotFoundException;
import com.my.cloud.common.exception.student.DuplicateStudentId;
import com.my.cloud.common.exception.student.StudentNotFoundException;
import com.my.cloud.common.pojo.Student;
import org.springframework.data.domain.Page;

import java.util.List;


/**
 * 学生业务类接口
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/28 12:31
 */
public interface StudentService {
    /**
     * 查询所有学生
     *
     * @param context     搜索内容
     * @param tags        搜索标签，用于搜索学生的姓名、学号、班级等信息
     * @param currentPage 当前页
     * @param pageSize    每页显示的记录数
     * @param sort        排序字段
     * @param asc         是否升序
     * @return 学生列表分页信息
     */
    Page<Student> showStudent(String context, String tags,
                              Integer currentPage, Integer pageSize,
                              String sort, Boolean asc);

    /**
     * 添加学生
     *
     * @param id      学生id
     * @param name    学生姓名
     * @param age     学生年龄
     * @param sex     学生性别
     * @param clazzId 学生所在班级ID
     * @throws ClazzNotFoundException 找不到班级异常
     */
    void insertStudent(String id, String name, Integer age, Character sex, Long clazzId) throws ClazzNotFoundException, DuplicateStudentId;

    /**
     * 删除学生信息
     *
     * @param ids 需要删除的学生的id
     * @throws StudentNotFoundException 找不到学生异常
     */
    void deleteStudent(List<String> ids) throws StudentNotFoundException;

    /**
     * 更新学生信息
     *
     * @param name    学生姓名
     * @param age     学生年龄
     * @param sex     学生性别
     * @param clazzId 学生所在班级id
     * @param id      需要修改的学生id
     * @throws StudentNotFoundException 找不到学生异常
     * @throws ClazzNotFoundException   找不到班级异常
     */
    void updateStudent(String name, Integer age, Character sex, Long clazzId, String id) throws StudentNotFoundException, ClazzNotFoundException;
}
