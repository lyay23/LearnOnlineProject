package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 李阳
 * @Date: 2025/08/06/13:40
 * @Description: 课程分类相关接口
 */
@RestController
@Api("课程分类相关接口")
@RequestMapping("/course-category")
@Slf4j
@RequiredArgsConstructor
public class CourseCategoryController {

    private final CourseCategoryService courseCategoryService;

    /**
     * 查询课程分类
     */
    @ApiOperation("查询课程分类")
    @GetMapping("/tree-nodes")
    public List<CourseCategoryTreeDto> queryCourseCategory() {

        return courseCategoryService.queryCourseCategory("1");

    }



}
