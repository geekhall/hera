package cn.geekhall.hera.server.controller;


import cn.geekhall.hera.server.entity.Product;
import cn.geekhall.hera.server.entity.Teacher;
import cn.geekhall.hera.server.service.impl.TeacherServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author yiny
 * @since 2022-03-26
 */
@Api(tags = "讲师模块")
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherServiceImpl teacherService;

    @ApiOperation("获取所有讲师信息")
    @ResponseBody
    @RequestMapping("/all")
    public List<Teacher> all(){
        return teacherService.list();
    }

    @ApiOperation("根据id删除讲师")
    @ApiImplicitParam(name = "id", value = "讲师ID", required = true, paramType = "path", dataType = "Long")
    @DeleteMapping("{id}")
    public boolean removeTeacher(@PathVariable("id") Long id){
        return teacherService.removeById(id);
    }
}
