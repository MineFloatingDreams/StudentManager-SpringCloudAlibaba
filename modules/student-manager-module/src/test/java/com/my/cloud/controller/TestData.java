package com.my.cloud.controller;

import com.my.cloud.StudentPayment;
import com.my.cloud.common.exception.clazz.ClazzNotFoundException;
import com.my.cloud.common.exception.clazz.DuplicateClazzName;
import com.my.cloud.common.exception.student.DuplicateStudentId;
import com.my.cloud.service.ClazzService;
import com.my.cloud.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Author: zhangjiachen
 * @Date: 2022/9/28 15:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StudentPayment.class)
public class TestData {
    @Resource
    StudentService studentController;

    @Resource
    ClazzService clazzController;

    @Test
    public void addClassData() throws DuplicateClazzName {
        for (int i = 1; i < 10; i++) {
            clazzController.addClass("21软件工程" + i + "班");
        }
    }

    @Test
    public void addStudentData() throws DuplicateStudentId, ClazzNotFoundException {
        String[] name = {"冷若寒", "蓝仲亭", "金灿光", "木听风", "屠横空", "虚游天", "风无痕", "楚翌天", "莫非凡", "云锦"};
        Character[] sex = {'f', 'm'};
        for (int j = 1; j < 10; j++) {
            for (int i = 0; i < 40; i++) {
                this.studentController.insertStudent("218111582" + this.addZero((i * j) + ""), name[(int) (Math.random() * 10)], (int) (Math.random() * 40 + 18), sex[i % 2], (long) j);
            }
        }
    }

    public String addZero(String str) {
        StringBuilder strBuilder = new StringBuilder(str);
        while (strBuilder.length() < 3) {
            strBuilder.append(0);
        }
        return strBuilder.toString();
    }
}
