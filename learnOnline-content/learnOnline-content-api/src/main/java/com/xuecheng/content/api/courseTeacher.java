package com.xuecheng.content.api;

import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 李阳
 * @Date: 2025/08/08/17:03
 * @Description: 教师接口信息
 */
@RestController
@RequestMapping("/courseTeacher")
@RequiredArgsConstructor
@Api("教师接口信息")
public class courseTeacher {

    private final CourseTeacherService courseTeacherService;

    /**
     * 查询教师接口信息
     */
    @ApiOperation("查询教师接口信息")
    @GetMapping("/list/{courseId}")
    public List<CourseTeacher> queryCourseTeacher(@Validated Long courseId) {

        return courseTeacherService.queryCourseTeacher(courseId);
    }
}
