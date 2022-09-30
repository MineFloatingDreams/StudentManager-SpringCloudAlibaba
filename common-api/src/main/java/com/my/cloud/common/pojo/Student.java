package com.my.cloud.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 学生信息实体类
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/23 10:05
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_student")
public class Student {
    @Id
    @Column(name = "s_id", nullable = false)
    // 学生id
    private String id;

    @Column(name = "s_name", nullable = false)
    // 学生姓名
    private String name;

    @Column(name = "s_age", nullable = false)
    // 学生年龄
    private Integer age;

    @Column(name = "s_sex", nullable = false)
    // 学生性别
    private Character sex;

    @ManyToOne
    @JoinColumn(name = "clazz_id")
    // 班级信息
    private Clazz clazz;

}
