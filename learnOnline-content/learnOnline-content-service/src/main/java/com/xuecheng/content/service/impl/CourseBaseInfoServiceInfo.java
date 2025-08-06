package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 李阳
 * @Date: 2025/08/06/7:26
 * @Description:
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CourseBaseInfoServiceInfo implements CourseBaseInfoService {

    private final CourseBaseMapper courseBaseMapper;

    /**
     * 分页查询课程基础信息
     * @param pageParams 查询参数
     * @param queryCourseParamsDto 查询条件
     * @return 分页结果
     */
    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {

        // 构建查询对象
        LambdaQueryWrapper<CourseBase> courseBaseLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 设置查询条件-根据课程名称模糊查询
        courseBaseLambdaQueryWrapper.like(CourseBase::getName, queryCourseParamsDto.getCourseName());
        // 设置查询条件-根据课程审核状态查询
        courseBaseLambdaQueryWrapper.eq(queryCourseParamsDto.getAuditStatus() != null, CourseBase::getAuditStatus, queryCourseParamsDto.getAuditStatus());
        // 设置查询条件-根据课程状态查询
        courseBaseLambdaQueryWrapper.eq(queryCourseParamsDto.getPublishStatus() != null, CourseBase::getStatus, queryCourseParamsDto.getPublishStatus());

        // 设置分页对象
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        // 查询数据内容获得结果
        Page<CourseBase> courseBasePage = courseBaseMapper.selectPage(page, courseBaseLambdaQueryWrapper);
        // 获取数据列表
        List<CourseBase> records = courseBasePage.getRecords();
        // 获取总记录数
        long total = courseBasePage.getTotal();

        // 返回结果
        return new PageResult<CourseBase>(records, total, pageParams.getPageNo(), pageParams.getPageSize());

    }
}
