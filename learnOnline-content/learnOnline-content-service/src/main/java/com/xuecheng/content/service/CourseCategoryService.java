package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 李阳
 * @Date: 2025/08/06/13:42
 * @Description: 课程分类的服务层
 */

public interface CourseCategoryService {
    /**
     * 查询课程分类树
     * @return 返回课程
     */
    List<CourseCategoryTreeDto> queryCourseCategory( String id);
}
