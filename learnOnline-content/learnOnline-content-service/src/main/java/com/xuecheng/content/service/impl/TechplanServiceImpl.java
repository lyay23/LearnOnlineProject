package com.xuecheng.content.service.impl;

import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.service.TeachplanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}

