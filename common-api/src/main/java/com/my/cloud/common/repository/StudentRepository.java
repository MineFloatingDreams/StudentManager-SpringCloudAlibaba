package com.my.cloud.common.repository;

import com.my.cloud.common.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: zhangjiachen
 * @Date: 2022/9/28 10:39
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String>, JpaSpecificationExecutor<Student> {
    Student findStudentById(String id);

    Long countStudentById(String id);

    List<Student> findByIdIn(List<String> ids);
}
