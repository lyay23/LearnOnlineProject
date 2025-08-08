package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 李阳
 * @Date: 2025/08/07/17:37
 * @Description: 查询课程计划的Service
 */
public interface TeachplanService {

    /**
     * 查询课程计划
     * @param courseId 课程id
     * @return 返回课程结果
     */
    List<TeachplanDto> findTeachplanTree(Long courseId);

    /**
     * 新增章节
     * @param teachplan 章节数据
     */
    void saveTeachplan(SaveTeachplanDto teachplan);
}
