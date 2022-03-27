package cn.geekhall.hera.server.controller;


import cn.geekhall.hera.server.service.impl.TeacherServiceImpl;
import cn.geekhall.hera.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @ApiOperation("分页查询讲师信息")
    @GetMapping("/page/{current}/{limit}")
    public Result page(
            @PathVariable("current") long current,
            @PathVariable("limit") long limit){
        return Result.ok();
    }
}
