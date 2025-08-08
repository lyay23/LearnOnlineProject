package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.mapper.TeachplanMediaMapper;
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
    private final TeachplanMediaMapper teachplanMediaMapper;
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
            
            // 设置创建时间
            teachplan1.setCreateDate(LocalDateTime.now());
            
            // 设置排序号（新增时排在最后）
            Long parentid = teachplan.getParentid();
            Long courseId = teachplan.getCourseId();
            
            // 查询同级节点中最大的排序号
            LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Teachplan::getCourseId, courseId)
                       .eq(Teachplan::getParentid, parentid);
            queryWrapper.orderByDesc(Teachplan::getOrderby);
            queryWrapper.last("LIMIT 1");
            
            Teachplan maxOrderTeachplan = teachplanMapper.selectOne(queryWrapper);
            int newOrderby = (maxOrderTeachplan != null) ? maxOrderTeachplan.getOrderby() + 1 : 1;
            teachplan1.setOrderby(newOrderby);
            
            teachplanMapper.insert(teachplan1);
        }else {
            // 修改
            Teachplan teachplan1 = teachplanMapper.selectById(id);

            // 将参数传入
            BeanUtils.copyProperties(teachplan,teachplan1);

            teachplan1.setChangeDate(LocalDateTime.now());
            teachplanMapper.updateById(teachplan1);
        }
    }

    @Override
    public void deleteTeachplan(String id) {
        // 1.删除第一级别的大章节时要求大章节下边没有小章节时方可删除
        // 根据传入的id查询章节信息
        // 2 删除第二级别的小章节时要求小章节下边没有视频时方可删除
        // 3 删除第二级别的小章节时需要将teachplan_media表关联的信息也删除。
        Teachplan teachplan = teachplanMapper.selectById(id);
        if (teachplan==null){
            return;
        }
        Long parentid = teachplan.getParentid();
        if(parentid!=0) {
            // 删除小章节
            teachplanMapper.deleteById(id);
            // 删除teachplan_media表关联的信息
            teachplanMediaMapper.deleteById(id);
        } else {
            // 删除大章节
            // 查询大章节下边是否有小章节
            LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Teachplan::getParentid, id);
            List<Teachplan> teachplanList = teachplanMapper.selectList(queryWrapper);
            if (!teachplanList.isEmpty()){
                // 有子节点，不能删除
                XueChengPlusException.cast("该大章节下有子节点，不能删除");
            }
            // 删除大章节

            teachplanMapper.deleteById(id);


        }

    }
}

