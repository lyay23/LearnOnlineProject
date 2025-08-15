package com.xuecheng.content.model.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 李阳
 * @Date: 2025/08/08/17:59
 * @Description:
 */
@Data
@ToString
public class CourseTeacherDto {
    private Long id;
    private Long courseId;
    private String teacherName;
    private String position;
    private String introduction;
    private String photograry;
    private LocalDateTime createTime;
}
