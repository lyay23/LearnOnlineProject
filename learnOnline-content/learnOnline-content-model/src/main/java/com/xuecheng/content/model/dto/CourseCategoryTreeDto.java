package com.xuecheng.content.model.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 李阳
 * @Date: 2025/08/06/13:18
 * @Description: 课程分类树形结构
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("course_category")
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {

    // 子节点
    List<CourseCategoryTreeDto> childrenTreeNodes;

}
