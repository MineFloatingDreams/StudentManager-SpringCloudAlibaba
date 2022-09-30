package com.my.cloud.service.impl;

import com.my.cloud.common.exception.clazz.ClazzNotFoundException;
import com.my.cloud.common.exception.student.DuplicateStudentId;
import com.my.cloud.common.exception.student.StudentNotFoundException;
import com.my.cloud.common.pojo.Clazz;
import com.my.cloud.common.pojo.Student;
import com.my.cloud.common.repository.ClazzRepository;
import com.my.cloud.common.repository.StudentRepository;
import com.my.cloud.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 学生业务类接口实现类
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/28 12:46
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentRepository studentRepository;

    @Resource
    private ClazzRepository clazzRepository;

    /**
     * 学生显示方法
     * 为了考虑接口的可拓展性，先预留好一系列的接口，后续可以根据需要进行拓展
     * 例如搜索功能，如果有需要前端可以传入需要搜索的tags，然后按照需要搜索的内容进行搜索即可
     * 这个值可以为空的字符串，放置到数据库中就是 like %%，也就是搜索所有
     * <p>
     * 预留的排序接口，可以根据前端的字段进行排序，也可以根据前端的排序方式进行排序
     * 前端只需要传入sort以及asc即可，其中asc为布尔类型，为true则为升序，为false则为降序
     * sort则为排序的字段，例如：id、name、age等
     *
     * @param context     搜索内容
     * @param tags        搜索标签，用于搜索学生的姓名、学号、班级等信息
     * @param currentPage 当前页
     * @param pageSize    每页显示的记录数
     * @param sort        排序字段
     * @param asc         是否升序
     * @return 学生分页信息
     */
    @Override
    public Page<Student> showStudent(String context, String tags,
                                     Integer currentPage, Integer pageSize,
                                     String sort, Boolean asc) {
        //分页内容
        Pageable pageable;
        //首先判断排序字段是否为空，如果为空则默认按照学号排序
        if (sort == null || sort.equals("")) {
            sort = "id";
        }
        //设置排序方法以及按照什么进行排序
        Sort.Order order = new Sort.Order((asc ? Sort.Direction.ASC : Sort.Direction.DESC), sort);
        //设置分页 当前页，分页大小，排序方法
        pageable = PageRequest.of(currentPage, pageSize, Sort.by(order));
        Page<Student> studentPage;
        //判断是否有搜索内容，如果有则按照搜索内容进行搜索
        if (context != null && !context.equals("")) {
            if ("age".equals(tags)) {
                studentPage = studentRepository.findAll((Specification<Student>) (root, criteriaQuery, criteriaBuilder) -> {
                    Predicate predicate = criteriaBuilder.conjunction();
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get(tags), Integer.parseInt(context)));
                    return predicate;
                }, pageable);
            } else {
                studentPage = studentRepository.findAll(new Specification<Student>() {
                    @Override
                    public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                        return cb.like(root.get(tags), "%" + context + "%");
                    }
                }, pageable);
            }
        } else {
            //如果没有搜索条件，那么就直接查询
            studentPage = studentRepository.findAll(pageable);
        }
        return studentPage;
    }

    /**
     * 添加学生实现方法
     *
     * @param id      学生id
     * @param name    学生姓名
     * @param age     学生年龄
     * @param sex     学生性别
     * @param clazzId 学生所在班级ID
     */

    @Override
    public void insertStudent(String id, String name, Integer age, Character sex, Long clazzId) throws ClazzNotFoundException, DuplicateStudentId {
        //首先查询班级信息
        Clazz clazz = this.clazzRepository.getClazzById(clazzId);
        //如果找不到对应的班级直接抛出异常
        if (clazz == null) {
            throw new ClazzNotFoundException("找不到班级信息");
        } else {
            Long count = this.studentRepository.countStudentById(id);
            if (count == 0) {
                //如果找到了班级信息，那么就创建一个学生对象
                Student student = new Student();
                //设置学生的信息
                student.setId(id);
                student.setName(name);
                student.setAge(age);
                student.setSex(sex);
                student.setClazz(clazz);
                //插入学生信息
                this.studentRepository.save(student);
            } else {
                throw new DuplicateStudentId("学生学号重复");
            }

        }
    }

    @Override
    public void deleteStudent(List<String> ids) throws StudentNotFoundException {
        List<Student> studentList = studentRepository.findByIdIn(ids);
        if (studentList.size() == ids.size()) {
            this.studentRepository.deleteAll(studentList);
        } else {
            throw new StudentNotFoundException("找不到学生信息");
        }
    }

    @Override
    public void updateStudent(String name, Integer age, Character sex, Long clazzId, String id)
            throws StudentNotFoundException, ClazzNotFoundException {
        Student one = studentRepository.findStudentById(id);
        if (one != null) {
            if (name != null && !"".equals(name)) {
                one.setName(name);
            }
            if (age != null) {
                one.setAge(age);
            }
            if (sex != null) {
                one.setSex(sex);
            }
            if (clazzId != null) {
                Clazz clazzById = this.clazzRepository.getClazzById(clazzId);
                if (clazzById == null) {
                    throw new ClazzNotFoundException("找不到班级信息");
                }
                one.setClazz(clazzById);
            }
            studentRepository.save(one);
        } else {
            throw new StudentNotFoundException("找不到学生信息");
        }
    }
}
