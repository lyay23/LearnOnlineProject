package com.xuecheng.content.api;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 李阳
 * @Date: 2025/08/05/14:28
 * @Description: 查询课程的基础信息
 */
@RestController
@Api(value = "课程信息编辑接口",tags = "课程信息编辑接口")
@RequiredArgsConstructor
public class CourseBaseInfoController {

    private final CourseBaseInfoService courseBaseInfoService;

    /**
     * 查询课程
     */
    @ApiOperation("查询课程")
    @PostMapping("/course/list")
    public PageResult<CourseBase> queryCourseBaseInfo(PageParams pageParams, @RequestBody (required=false) QueryCourseParamsDto queryCourseParamsDto) {

        return courseBaseInfoService.queryCourseBaseList(pageParams, queryCourseParamsDto);
    }

    /**
     * 新增课程
     */
    @ApiOperation("新增课程")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody AddCourseDto addCourseDto){

        //机构id，由于认证系统没有上线暂时硬编码
        Long companyId = 1232141425L;
        return courseBaseInfoService.createCourseBase(companyId,addCourseDto);

    }
}
