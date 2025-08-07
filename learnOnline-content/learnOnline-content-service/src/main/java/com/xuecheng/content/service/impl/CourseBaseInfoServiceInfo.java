package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final CourseMarketMapper courseMarketMapper;
    private final CourseCategoryMapper courseCategoryMapper;

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
        courseBaseLambdaQueryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()),CourseBase::getName,queryCourseParamsDto.getCourseName());
        // 设置查询条件-根据课程审核状态查询
        courseBaseLambdaQueryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()),CourseBase::getAuditStatus,queryCourseParamsDto.getAuditStatus());
        // 设置查询条件-根据课程状态查询
        courseBaseLambdaQueryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus()),CourseBase::getStatus,queryCourseParamsDto.getPublishStatus());

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

    /**
     * 新增课程
     *
     * @param companyId    机构ID
     * @param addCourseDto 添加课程的信息
     * @return 返回课程信息
     */
    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto) {

        // 参数合法性校验
        if (StringUtils.isBlank(addCourseDto.getName())) {
            throw new XueChengPlusException("课程名称为空");
        }

        if (StringUtils.isBlank(addCourseDto.getMt())) {
            throw new XueChengPlusException("课程分类为空");
        }

        if (StringUtils.isBlank(addCourseDto.getSt())) {
            throw new XueChengPlusException("课程分类为空");
        }

        if (StringUtils.isBlank(addCourseDto.getGrade())) {
            throw new XueChengPlusException("课程等级为空");
        }

        if (StringUtils.isBlank(addCourseDto.getTeachmode())) {
            throw new XueChengPlusException("教育模式为空");
        }

        if (StringUtils.isBlank(addCourseDto.getUsers())) {
            throw new XueChengPlusException("适应人群为空");
        }

        if (StringUtils.isBlank(addCourseDto.getCharge())) {
            throw new XueChengPlusException("收费规则为空");
        }

        // 向课程基本信息表写入信息 course_base写入数据

        CourseBase courseBase = new CourseBase();
        // 将传入的页面的参数放到对象中
        BeanUtils.copyProperties(addCourseDto,courseBase);
        // 不是表单中的数据要单独定义
        courseBase.setCompanyId(companyId);
        courseBase.setCreateDate(LocalDateTime.now());
        // 审核状态和课程状态默认为未提交
        courseBase.setAuditStatus("202002");
        courseBase.setStatus("203001");

        int insert = courseBaseMapper.insert(courseBase);
        if(insert<=0){
            throw new RuntimeException("添加课程失败");
        }


        // 向课程营销表course_market 写入数据

        CourseMarket courseMarket = new CourseMarket();
         // 将页面输入的信息copy到表里面去
        BeanUtils.copyProperties(addCourseDto,courseMarket);

        Long course= courseBase.getId();
        // 主键与课程id相关联
        courseMarket.setId(course);
        //保存营销信息
        saveCourseMarket(courseMarket);

        // 从数据库查询信息
        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfo(course);



        return courseBaseInfo;

    }

    // 查询课程信息
    public CourseBaseInfoDto getCourseBaseInfo(long caseId){

        //从课程基本信息表查询
        CourseBase courseBase = courseBaseMapper.selectById(caseId);
        if (courseBase==null){
            return null;
        }
        // 从课程营销表查询
        CourseMarket courseMarket = courseMarketMapper.selectById(caseId);

        //组装
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);
        BeanUtils.copyProperties(courseBase,courseBaseInfoDto);

        // 通过courseCategoryMapping 查询出分类的信息，然后将分类信息放到Dto中
        CourseCategory courseCategoryMt = courseCategoryMapper.selectById(courseBase.getMt());
        courseBaseInfoDto.setMt(courseCategoryMt.getName());

        CourseCategory courseCategorySt = courseCategoryMapper.selectById(courseBase.getSt());
        courseBaseInfoDto.setMt(courseCategorySt.getName());



        return courseBaseInfoDto;


    }

    // 单独的保存营销信息表，存在则更新，不存在则添加
    private int saveCourseMarket(CourseMarket courseMarket){

        // 参数合法校验
        String charge = courseMarket.getCharge();
        if(StringUtils.isBlank(charge)){
            throw new XueChengPlusException("收费规则没有选择");
        }
        //收费规则为收费
        if(charge.equals("201001")){
            if(courseMarket.getPrice() == null || courseMarket.getPrice().floatValue()<=0){
                throw new XueChengPlusException("课程为收费价格不能为空且必须大于0");
            }
        }

        // 从数据库查询
        Long id = courseMarket.getId();
        CourseMarket byId = courseMarketMapper.selectById(id);
        if(byId == null){
            // 插入数据库
            int insert = courseMarketMapper.insert(courseMarket);
            return insert;
        }else {

            // 将前端传入的数据copy到我们从数据库查询出来的对象中
            BeanUtils.copyProperties(courseMarket,byId);
            byId.setId(courseMarket.getId());

            //更新
            int i = courseMarketMapper.updateById(byId);
            return i;
        }

    }
}
