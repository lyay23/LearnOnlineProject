package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseTeacherMapper;
import com.xuecheng.content.model.dto.CourseTeacherDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 李阳
 * @Date: 2025/08/08/17:05
 * @Description:
 */
@Service
@RequiredArgsConstructor
public class CourseTeacherServiceImpl implements CourseTeacherService {
    private final CourseTeacherMapper courseTeacherMapper;
    private final CourseBaseMapper courseBaseMapper;

    @Override
    public List<CourseTeacher> queryCourseTeacher(Long courseId) {

        return courseTeacherMapper.selectList(new LambdaQueryWrapper<CourseTeacher>()
                .eq(CourseTeacher::getCourseId, courseId));
    }


    /**
     * 添加讲师信息
     * @param companyId 公司id
     * @param courseTeacherDto 讲师信息
     * @return 返回讲师信息
     */
    @Override
    public CourseTeacher addCourseTeacher(Long companyId, CourseTeacherDto courseTeacherDto) {

        // 健壮性校验
        if(StringUtils.isEmpty(courseTeacherDto.getTeacherName())){
            XueChengPlusException.cast("讲师名称不能为空");
        }

        if(StringUtils.isEmpty(courseTeacherDto.getPosition())){
            XueChengPlusException.cast("讲师职位不能为空");
        }
        CourseBase courseBase = courseBaseMapper.selectById(courseTeacherDto.getCourseId());
        Long companyId1 = courseBase.getCompanyId();
        if (!companyId.equals(companyId1)){
            XueChengPlusException.cast("课程不属于该公司");
        }


        // 添加讲师信息
        // 判断数据库有没有该数据
        CourseTeacher courseTeacher = courseTeacherMapper.selectById(courseTeacherDto.getId());
        if (courseTeacher != null){
            // 有数据，更新
            BeanUtils.copyProperties(courseTeacherDto, courseTeacher);
            courseTeacherMapper.updateById(courseTeacher);
            return courseTeacher;
        }
        courseTeacher = new CourseTeacher();
        BeanUtils.copyProperties(courseTeacherDto, courseTeacher);
        courseTeacher.setCreateDate(LocalDateTime.now());
        courseTeacherMapper.insert(courseTeacher);

        return courseTeacher;
    }


    /**
     * 删除讲师信息
     * @param courseId 课程id
     * @param id 讲师id
     * @param companyId 公司id
     */
    @Override
    public void deleteCourseTeacher(Long courseId, Long id, Long companyId) {
        // 校验讲师信息
        CourseTeacher courseTeacher = courseTeacherMapper.selectById(id);
        if (courseTeacher == null){
            XueChengPlusException.cast("讲师信息不存在");
        }
        // 校验课程信息
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null){
            XueChengPlusException.cast("课程信息不存在");
        }
        // 校验讲师信息是否属于该课程
        if (!courseId.equals(courseTeacher.getCourseId())){
            XueChengPlusException.cast("讲师信息不属于该课程");
        }
        // 校验讲师信息是否属于该公司
        if (!companyId.equals(courseBase.getCompanyId())){
            XueChengPlusException.cast("课程不属于该公司");
        }

        // 删除讲师信息
        courseTeacherMapper.deleteById(id);

    }
}
