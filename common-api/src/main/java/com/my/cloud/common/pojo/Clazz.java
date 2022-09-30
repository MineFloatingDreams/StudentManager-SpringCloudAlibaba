package com.my.cloud.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * 班级信息实体类
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/23 10:17
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_clazz")
public class Clazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id", nullable = false)
    // 班级id
    private Long id;

    @Column(name = "c_name", nullable = false)
    // 班级名称
    private String clazzName;

    @OneToMany(mappedBy = "clazz", orphanRemoval = true)
    // 班级学生
    private List<Student> students = new ArrayList<>();

    @Override
    public String toString() {
        return "Clazz{" +
                "id=" + id +
                ", clazzName='" + clazzName + '\'' +
                '}';
    }
}
