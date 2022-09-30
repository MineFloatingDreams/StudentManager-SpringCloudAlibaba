package com.my.cloud.controller;

import com.alibaba.nacos.shaded.com.google.gson.JsonArray;
import com.alibaba.nacos.shaded.com.google.gson.JsonObject;
import com.my.cloud.common.code.ClazzResultCode;
import com.my.cloud.common.code.CommonResultCode;
import com.my.cloud.common.exception.clazz.ClazzNotFoundException;
import com.my.cloud.common.exception.clazz.DuplicateClazzName;
import com.my.cloud.common.pojo.Clazz;
import com.my.cloud.common.pojo.CommonResult;
import com.my.cloud.service.ClazzService;
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
 * @Date: 2022/9/28 15:27
 */
@Slf4j
@RestController
@RequestMapping("/clazz")
public class ClazzController {
    @Resource
    private ClazzService clazzService;

    @PostMapping("/showClazz")
    public CommonResult<Object> showClazz(@RequestParam(required = false) String context,
                                          @RequestParam(required = false) String tags,
                                          @RequestParam Integer currentPage,
                                          @RequestParam(required = false) Integer pageSize,
                                          @RequestParam(required = false) String sort,
                                          @RequestParam(required = false, defaultValue = "true") Boolean asc) {
        try {
            // 查询学生信息，返回分页信息
            Page<Clazz> clazzPage = this.clazzService.searchClass(context, tags, currentPage, pageSize, sort, asc);
            //创建map，用于存储分页信息
            Map<String, Object> map = new HashMap<>();
            //总条数
            map.put("totalNum", clazzPage.getTotalElements());
            //总页数
            map.put("totalPages", clazzPage.getTotalPages());
            //详细的学生信息，这里需要注意一点
            /*
                如果直接使用clazzPage.getContent()，会导致调用到Clazz对象中的student对象
                这样导致转换student对象到json的时候，会转换到clazz对象，然后转换到clazz对象中的studentList
                陷入死循环最终导致栈溢出
                ------
                因此只可以使用Gson进行json对象的封装，单独做处理后返回到前端
             */
            map.put("clazz", listClazzToJSON(clazzPage.getContent()));
            //返回查询结果以及成功信息
            return new CommonResult<>(CommonResultCode.SUCCESS, "班级信息查询成功", map);
        } catch (Exception e) {
            //异常处理
            e.printStackTrace();
            return new CommonResult<>(CommonResultCode.UNKNOWN_ERROR, "未知错误：" + e.getMessage());
        }
    }

    @PostMapping("/addClazz")
    public CommonResult<Object> addClazz(@RequestParam String clazzName) {
        try {
            this.clazzService.addClass(clazzName);
            return new CommonResult<>(CommonResultCode.SUCCESS, "班级信息插入成功");
        } catch (DuplicateClazzName e) {
            return new CommonResult<>(ClazzResultCode.DUPLICATE_CLASSNAME, e.getMessage());
        } catch (Exception e) {
            //异常处理
            e.printStackTrace();
            return new CommonResult<>(CommonResultCode.UNKNOWN_ERROR, "未知错误：" + e.getMessage());
        }
    }

    @PostMapping("/deleteClazz")
    public CommonResult<Object> deleteClazz(@RequestParam List<Long> ids) {
        try {
            this.clazzService.deleteClass(ids);
            return new CommonResult<>(CommonResultCode.SUCCESS, "班级删除成功");
        } catch (ClazzNotFoundException e) {
            return new CommonResult<>(ClazzResultCode.CLAZZ_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            //异常处理
            e.printStackTrace();
            return new CommonResult<>(CommonResultCode.UNKNOWN_ERROR, "未知错误：" + e.getMessage());
        }

    }

    @PostMapping("/updateClazz")
    public CommonResult<Object> updateClazz(@RequestParam String clazzName,
                                            @RequestParam Long clazzId) {
        try {
            this.clazzService.updateClass(clazzName, clazzId);
            return new CommonResult<>(CommonResultCode.SUCCESS, "班级信息更新成功");
        } catch (ClazzNotFoundException e) {
            return new CommonResult<>(ClazzResultCode.CLAZZ_NOT_FOUND, e.getMessage());
        } catch (DuplicateClazzName e) {
            return new CommonResult<>(ClazzResultCode.DUPLICATE_CLASSNAME, e.getMessage());
        } catch (Exception e) {
            //异常处理
            e.printStackTrace();
            return new CommonResult<>(CommonResultCode.UNKNOWN_ERROR, "未知错误：" + e.getMessage());
        }
    }

    @GetMapping("/getClazz")
    public CommonResult<String> getClazz() {
        try {
            return new CommonResult<>(CommonResultCode.SUCCESS, "班级信息查询成功", listClazzToJSON(this.clazzService.showAll()));
        } catch (Exception e) {
            //异常处理
            e.printStackTrace();
            return new CommonResult<>(CommonResultCode.UNKNOWN_ERROR, "未知错误：" + e.getMessage());
        }
    }

    private String listClazzToJSON(List<Clazz> clazzList) {
        JsonArray jsonArray = new JsonArray();
        clazzList.forEach(e -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", e.getId());
            jsonObject.addProperty("clazzName", e.getClazzName());
            jsonArray.add(jsonObject);
        });
        return jsonArray.toString();
    }
}
