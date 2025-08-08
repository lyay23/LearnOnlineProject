package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.service.TeachplanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 李阳
 * @Date: 2025/08/07/17:39
 * @Description: 查询课程计划的实现类
 */
@Service
@RequiredArgsConstructor
public class TechplanServiceImpl implements TeachplanService {

    private final TeachplanMapper teachplanMapper;
    /**
     * 查询课程计划
     * @param courseId 课程id
     * @return 返回课程结果
     */
    @Override
    public List<TeachplanDto> findTeachplanTree(Long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    /**
     * 新增/修改章节
     * @param teachplan 章节数据
     */
    @Override
    public void saveTeachplan(SaveTeachplanDto teachplan) {

        // 根据id判断是查询还是新增
        Long id = teachplan.getId();

        if(id==null){
            // 新增
            Teachplan teachplan1 = new Teachplan();
            BeanUtils.copyProperties(teachplan,teachplan1);
            teachplanMapper.insert(teachplan1);
            teachplan1.setCreateDate(LocalDateTime.now());
        }else {
            // 修改
            Teachplan teachplan1 = teachplanMapper.selectById(id);
            Long parentid = teachplan.getParentid();
            Long courseId = teachplan.getCourseId();
            LambdaQueryWrapper<Teachplan> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
            objectLambdaQueryWrapper=objectLambdaQueryWrapper.eq(Teachplan::getCourseId,courseId).eq(Teachplan::getParentid,parentid);
            Integer i = teachplanMapper.selectCount(objectLambdaQueryWrapper);
            teachplan1.setOrderby(i+1);
            // 将参数传入
            BeanUtils.copyProperties(teachplan,teachplan1);

            teachplan1.setChangeDate(LocalDateTime.now());
            teachplanMapper.updateById(teachplan1);
        }
    }
}

