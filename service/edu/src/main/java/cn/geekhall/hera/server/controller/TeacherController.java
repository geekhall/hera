package cn.geekhall.hera.server.controller;


import cn.geekhall.hera.server.entity.Teacher;
import cn.geekhall.hera.server.entity.vo.TeacherQuery;
import cn.geekhall.hera.server.service.impl.TeacherServiceImpl;
import cn.geekhall.hera.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
//import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Result all(){
        return Result.ok().data("items",teacherService.list() );
    }

    /**
     * 根据id删除讲师
     * @param id 讲师id
     * @return 执行结果
     */
    @ApiOperation("根据id删除讲师")
    @ApiImplicitParam(name = "id", value = "讲师ID", required = true, paramType = "path", dataType = "Long")
    @DeleteMapping("{id}")
    public Result removeTeacher(@PathVariable("id") Long id){

        boolean result = teacherService.removeById(id);
        if (result) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }


    /**
     * 分页查询讲师信息
     * @param current 当前页
     * @param limit   每页记录数
     * @return 执行结果
     */
    @ApiOperation("分页查询讲师信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = true, paramType = "path", dataType = "long"),
            @ApiImplicitParam(name = "limit", value = "每页记录数", required = true, paramType = "path", dataType = "long")
    })
    @GetMapping("/page/{current}/{limit}")
    public Result pageingQuery(
            @PathVariable("current") long current,
            @PathVariable("limit") long limit){
        Page<Teacher> pageTeacher= new Page<>(current, limit);
        teacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();
        List<Teacher> records = pageTeacher.getRecords();
//        Map<String, Object> result = new HashMap<>();
//        result.put("total", total);
//        result.put("records", records);
//        return Result.ok().data(result);
        return Result.ok().data("total", total).data("records", records);
    }

    /**
     * 支持查询条件的讲师信息分页查询
     * @param current 当前页
     * @param limit   每页记录数
     * @return 执行结果
     */
    @ApiOperation("支持查询条件的讲师信息分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = true, paramType = "path", dataType = "long"),
            @ApiImplicitParam(name = "limit", value = "每页记录数", required = true, paramType = "path", dataType = "long"),
            @ApiImplicitParam(name = "teacherQuery", value = "查询条件", required = false)
    })
    @PostMapping("/conditional-page/{current}/{limit}")
    public Result conditionalPagingQuery(
            @PathVariable("current") long current,
            @PathVariable("limit") long limit,
            @RequestBody(required = false) TeacherQuery teacherQuery){

        // 创建Page对象
        Page<Teacher> pageTeacher = new Page<>(current, limit);
        // 构建查询条件
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();


        // 下面的写法不好，数据库字段名称直接出现在了Controller层，难以维护。
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (StringUtils.hasText(name)) {
            wrapper.like("name", name);
        }
        if (level != null && level>=1 && level <= 4){
            wrapper.eq("level", level);
        }
        if (StringUtils.hasText(begin)){
            wrapper.ge("create_time", begin);
        }
        if (StringUtils.hasText(end)){
            wrapper.le("create_time", end);
        }

        teacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal();
        List<Teacher> records = pageTeacher.getRecords();
        return Result.ok().data("total", total).data("records", records);
    }
}
