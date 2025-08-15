package com.xuecheng.content.service.impl;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 李阳
 * @Date: 2025/08/06/13:43
 * @Description: 课程分类服务层实现类
 */
@Service
@RequiredArgsConstructor
public class CourseCategoryServiceImpl implements CourseCategoryService {

    private final  CourseCategoryMapper courseCategoryMapper;
    /**
     * 查询课程分类树
     * @return 返回课程
     */
    @Override
    public List<CourseCategoryTreeDto> queryCourseCategory(String id) {

        // 1.调用Mapper查询出信息
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNode(id);



        // 2.2 按父节点ID分组，建立父ID到子节点列表的映射
        Map<String, List<CourseCategoryTreeDto>> parentMap = courseCategoryTreeDtos.stream()
                .filter(item -> !id.equals(item.getId()))
                .collect(Collectors.groupingBy(CourseCategoryTreeDto::getParentid));

        // 3. 为每个节点设置子节点（包含根节点）
        courseCategoryTreeDtos.stream()
                .filter(item -> !id.equals(item.getId()))
                .forEach(item -> item.setChildrenTreeNodes(parentMap.get(item.getId())));

         return courseCategoryTreeDtos.stream()
                .filter(node -> id.equals(node.getParentid()))
                .collect(Collectors.toList());
    }
}
