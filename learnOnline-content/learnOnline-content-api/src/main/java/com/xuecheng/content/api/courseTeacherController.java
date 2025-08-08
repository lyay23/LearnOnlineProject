package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.CourseTeacherDto;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 李阳
 * @Date: 2025/08/08/17:03
 * @Description: 教师接口信息
 */
@RestController
@RequiredArgsConstructor
@Api("教师接口信息")
@RequestMapping
public class courseTeacherController {

    private final CourseTeacherService courseTeacherService;

    /**
     * 查询教师接口信息
     */
    @ApiOperation("查询教师接口信息")
    @GetMapping("courseTeacher/list/{courseId}")
    public List<CourseTeacher> queryCourseTeacher( @PathVariable Long courseId) {

        return courseTeacherService.queryCourseTeacher(courseId);
    }

    /**
     * 新增教师接口信息
     */
    @ApiOperation("新增教师接口信息")
    @PostMapping("/courseTeacher")
    public CourseTeacher addCourseTeacher(@RequestBody CourseTeacherDto courseTeacherDto) {
      Long companyId = 1232141425L;
        return courseTeacherService.addCourseTeacher(companyId,courseTeacherDto);

    }
}
