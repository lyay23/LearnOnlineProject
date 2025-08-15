package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.BindTeachplanMediaDto;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "课程计划编辑接口",tags = "课程计划编辑接口")
@RestController
@RequiredArgsConstructor
public class TeachplanController {

    private final TeachplanService teachplanService;

    @ApiOperation("查询课程计划树形结构")
    @ApiImplicitParam(value = "courseId",name = "课程Id",required = true,dataType = "Long",paramType = "path")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId){

        return teachplanService.findTeachplanTree(courseId);
    }


    /**
     * 新增/修改课程
     * @param teachplan 前端传入课程的数据
     */
    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan(@RequestBody SaveTeachplanDto teachplan){

        teachplanService.saveTeachplan(teachplan);

    }

    /**
     * 删除课程章节
     */
    @ApiOperation("删除课程计划")
    @DeleteMapping("/teachplan/{id}")
    public void deleteTeachplan(@PathVariable String id){

        teachplanService.deleteTeachplan(id);
    }

    /**
     * 上移下移课程章节
     */
    @ApiOperation("上移下移课程计划")
    @PostMapping("/teachplan/{moveType}/{teachplanid}")
    public void moveTeachplan(@PathVariable String moveType,@PathVariable String teachplanid){

        teachplanService.moveTeachplan(moveType,teachplanid);

    }

    @ApiOperation(value = "课程计划和媒资信息绑定")
    @PostMapping("/teachplan/association/media")
    public void associationMedia(@RequestBody BindTeachplanMediaDto bindTeachplanMediaDto){
        teachplanService.associationMedia(bindTeachplanMediaDto);
    }


}