package com.my.cloud.common.code;

/**
 * 学生错误代码
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/28 14:29
 */
public class StudentResultCode {
    //学生所在班级不存在
    public static final Integer CLAZZ_NOT_FOUND = 3001;
    //找不到学生
    public static final Integer STUDENT_NOT_FOUND = 3002;

    //学生id重复
    public static final Integer DUPLICATE_STUDENT_ID = 3003;
}
