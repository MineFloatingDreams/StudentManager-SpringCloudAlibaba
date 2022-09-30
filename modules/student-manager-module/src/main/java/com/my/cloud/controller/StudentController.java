package com.my.cloud.controller;

import com.alibaba.nacos.shaded.com.google.gson.JsonArray;
import com.alibaba.nacos.shaded.com.google.gson.JsonObject;
import com.my.cloud.common.code.CommonResultCode;
import com.my.cloud.common.code.StudentResultCode;
import com.my.cloud.common.exception.clazz.ClazzNotFoundException;
import com.my.cloud.common.exception.student.DuplicateStudentId;
import com.my.cloud.common.exception.student.StudentNotFoundException;
import com.my.cloud.common.pojo.CommonResult;
import com.my.cloud.common.pojo.Student;
import com.my.cloud.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: zhangjiachen
 * @Date: 2022/9/28 13:32
 */
@Slf4j
@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;

    /**
     * 展示学生信息
     *
     * @param context     搜索正文内容
     * @param tags        搜索标签内容
     * @param currentPage 当前页
     * @param pageSize    每页显示的记录数
     * @param sort        排序字段
     * @param asc         是否升序
     * @return 学生信息
     */
    @PostMapping("/showStudent")
    public CommonResult<Object> showStudent(@RequestParam(required = false) String context,
                                            @RequestParam(required = false) String tags,
                                            @RequestParam Integer currentPage,
                                            @RequestParam Integer pageSize,
                                            @RequestParam(required = false) String sort,
                                            @RequestParam(required = false, defaultValue = "true") Boolean asc) {
        try {
            // 查询学生信息，返回分页信息
            Page<Student> studentPage = this.studentService.showStudent(context, tags, currentPage, pageSize, sort, asc);
            //创建map，用于存储分页信息
            Map<String, Object> map = new HashMap<>();
            //总条数
            map.put("totalNum", studentPage.getTotalElements() + "");
            //总页数
            map.put("totalPages", studentPage.getTotalPages() + "");
            //详细的学生信息，这里需要注意一点
            /*
                如果直接使用studentPage.getContent()，会导致调用到Student对象中的clazz对象
                这样导致转换student对象到json的时候，会转换到clazz对象，然后转换到clazz对象中的studentList
                陷入死循环最终导致栈溢出
                ------
                因此只可以使用Gson进行json对象的封装，单独做处理后返回到前端
             */
            map.put("student", listStudentToJSON(studentPage.getContent()));
            //返回查询结果以及成功信息
            return new CommonResult<>(CommonResultCode.SUCCESS, "学生信息查询成功", map);
        } catch (Exception e) {
            //异常处理
            e.printStackTrace();
            return new CommonResult<>(CommonResultCode.UNKNOWN_ERROR, "未知错误：" + e.getMessage());
        }
    }

    @PostMapping("/addStudent")
    public CommonResult<Object> insertStudent(@RequestParam String id,
                                              @RequestParam String name,
                                              @RequestParam Integer age,
                                              @RequestParam Character sex,
                                              @RequestParam Long clazzId) {
        try {
            this.studentService.insertStudent(id, name, age, sex, clazzId);
            return new CommonResult<>(CommonResultCode.SUCCESS, "学生信息插入成功");
        } catch (ClazzNotFoundException e) {
            e.printStackTrace();
            return new CommonResult<>(StudentResultCode.CLAZZ_NOT_FOUND, e.getMessage());
        } catch (DuplicateStudentId e) {
            return new CommonResult<>(StudentResultCode.DUPLICATE_STUDENT_ID, e.getMessage());
        } catch (Exception e) {
            //异常处理
            e.printStackTrace();
            return new CommonResult<>(CommonResultCode.UNKNOWN_ERROR, "未知错误：" + e.getMessage());
        }

    }

    @GetMapping("/deleteStudent")
    public CommonResult<Object> deleteStudent(@RequestParam List<String> ids) {
        try {
            this.studentService.deleteStudent(ids);
            return new CommonResult<>(CommonResultCode.SUCCESS, "学生信息删除成功");
        } catch (StudentNotFoundException e) {
            return new CommonResult<>(StudentResultCode.STUDENT_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            //异常处理
            e.printStackTrace();
            return new CommonResult<>(CommonResultCode.UNKNOWN_ERROR, "未知错误：" + e.getMessage());
        }
    }

    @PostMapping("/updateStudent")
    public CommonResult<Object> updateStudent(@RequestParam(value = "id", required = false) String id,
                                              @RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "age", required = false) Integer age,
                                              @RequestParam(value = "sex", required = false) Character sex,
                                              @RequestParam(value = "clazzId", required = false) Long clazzId) {
        try {
            this.studentService.updateStudent(name, age, sex, clazzId, id);
            return new CommonResult<>(CommonResultCode.SUCCESS, "学生信息更新成功");
        } catch (StudentNotFoundException e) {
            return new CommonResult<>(StudentResultCode.STUDENT_NOT_FOUND, e.getMessage());
        } catch (ClazzNotFoundException e) {
            return new CommonResult<>(StudentResultCode.CLAZZ_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            //异常处理
            e.printStackTrace();
            return new CommonResult<>(CommonResultCode.UNKNOWN_ERROR, "未知错误：" + e.getMessage());
        }
    }

    /**
     * 转换Student对象
     *
     * @param studentList 学生列表
     * @return 转换后的json对象
     */
    private String listStudentToJSON(List<Student> studentList) {
        JsonArray jsonArray = new JsonArray();
        studentList.forEach(e -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", e.getId());
            jsonObject.addProperty("name", e.getName());
            jsonObject.addProperty("age", e.getAge());
            jsonObject.addProperty("sex", e.getSex());
            jsonObject.addProperty("classId", e.getClazz().getId());
            jsonObject.addProperty("className", e.getClazz().getClazzName());
            jsonArray.add(jsonObject);
        });
        return jsonArray.toString();
    }

}
