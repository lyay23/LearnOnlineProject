package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CourseTeacherDto;
import com.xuecheng.content.model.po.CourseTeacher;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 李阳
 * @Date: 2025/08/08/17:05
 * @Description:
 */
public interface CourseTeacherService {

    /**
     * 分页查询讲师信息
     * @param courseId 教师id
     * @return 返回教师信息
     */
    List<CourseTeacher> queryCourseTeacher(Long courseId);


    /**
     * 添加讲师信息
     * @param companyId 公司id
     * @param courseTeacherDto 讲师信息
     * @return 返回讲师信息
     */
    CourseTeacher addCourseTeacher(Long companyId, CourseTeacherDto courseTeacherDto);
}
