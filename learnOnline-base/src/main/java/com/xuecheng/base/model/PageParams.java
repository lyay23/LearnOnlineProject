package com.xuecheng.base.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 李阳
 * @Date: 2025/08/05/13:57
 * @Description: 分页查询的通用参数
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PageParams {
    // 当前页码
    @ApiModelProperty("当前页码")
    private Long pageNo = 1L;

    // 每页记录数
    @ApiModelProperty("每页记录数")
    private Long pageSize = 10L;



}
