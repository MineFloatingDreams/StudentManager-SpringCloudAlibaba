package com.my.cloud.service.impl;

import com.my.cloud.common.exception.clazz.ClazzNotFoundException;
import com.my.cloud.common.exception.clazz.DuplicateClazzName;
import com.my.cloud.common.pojo.Clazz;
import com.my.cloud.common.repository.ClazzRepository;
import com.my.cloud.service.ClazzService;
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
 * 班级信息服务实现类
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/28 14:48
 */
@Service
public class ClazzServiceImpl implements ClazzService {

    @Resource
    private ClazzRepository clazzRepository;

    /**
     * 添加班级信息
     *
     * @param clazzName 班级名称
     * @throws DuplicateClazzName 重复的班级名称
     */
    @Override
    public void addClass(String clazzName) throws DuplicateClazzName {
        // 首先前往数据库查询班级名称是否重复
        Clazz clazz = this.clazzRepository.getClazzByClazzName(clazzName);
        // 如果有查询结果的话，说明班级名称重复，抛出异常
        if (clazz != null) {
            throw new DuplicateClazzName("班级名重复");
        } else {
            // 如果没有的话，就添加班级信息
            clazz = new Clazz();
            clazz.setClazzName(clazzName);
            this.clazzRepository.save(clazz);
        }
    }

    /**
     * @param clazzName 班级名称
     * @param id        需要修改的班级id
     * @throws ClazzNotFoundException 班级不存在
     * @throws DuplicateClazzName     重复的班级名称
     */
    @Override
    public void updateClass(String clazzName, Long id) throws ClazzNotFoundException, DuplicateClazzName {
        //先查班级名是否重复在查班级是否存在，这样可以在都没事的情况下，节约一点点资源
        Clazz clazz = this.clazzRepository.getClazzByClazzName(clazzName);
        //查询班级名称是否重复，如果重复，抛出异常
        if (clazz != null) {
            throw new DuplicateClazzName("班级名重复");
        } else {
            //如果没有重复，就查询班级是否存在
            clazz = this.clazzRepository.getClazzById(id);
            //如果班级不存在，抛出异常
            if (clazz == null) {
                throw new ClazzNotFoundException("班级不存在");
            } else {
                // 如果班级存在，就修改班级名称
                clazz.setClazzName(clazzName);
                this.clazzRepository.save(clazz);
            }
        }
    }

    /**
     * 删除班级信息
     *
     * @param ids 需要删除的班级的id
     * @throws ClazzNotFoundException 班级不存在
     */
    @Override
    public void deleteClass(List<Long> ids) throws ClazzNotFoundException {
        //首先查询班级是否存在
        List<Clazz> clazz = this.clazzRepository.findByIdIn(ids);
        if (clazz.size() == ids.size()) {
            //如果存在，判断是否有学生，如果有学生，就抛出异常
            for (Clazz clazz1 : clazz) {
                if (clazz1.getStudents().size() > 0) {
                    throw new ClazzNotFoundException("班级中有学生，不能删除");
                }
            }
            this.clazzRepository.deleteAll(clazz);
        } else {
            throw new ClazzNotFoundException("班级不存在");
        }
    }

    @Override
    public Page<Clazz> searchClass(String context, String tags, Integer currentPage, Integer pageSize, String sort, Boolean asc) {
        //分页内容
        Pageable pageable;
        //首先判断排序字段是否为空，如果为空则默认按照学号排序
        if ("".equals(sort) || sort == null) {
            sort = "id";
        }
        Sort.Order order = new Sort.Order((asc ? Sort.Direction.ASC : Sort.Direction.DESC), sort);
        //设置分页 当前页，分页大小，排序方法
        pageable = PageRequest.of(currentPage, pageSize, Sort.by(order));
        Page<Clazz> clazzPage;
        //判断是否有搜索内容，如果有则按照搜索内容进行搜索
        if (context != null && !context.equals("")) {
            System.out.println("搜索内容：" + context);
            clazzPage = this.clazzRepository.findAll(new Specification<Clazz>() {
                @Override
                public Predicate toPredicate(Root<Clazz> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    return cb.like(root.get(tags), "%" + context + "%");
                }
            }, pageable);
        } else {
            //如果没有搜索条件，那么就直接查询
            clazzPage = this.clazzRepository.findAll(pageable);
        }
        return clazzPage;
    }

    @Override
    public List<Clazz> showAll() {
        return this.clazzRepository.findAll();
    }
}
