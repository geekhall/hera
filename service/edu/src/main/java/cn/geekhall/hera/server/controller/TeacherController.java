package cn.geekhall.hera.server.controller;


import cn.geekhall.hera.server.entity.Product;
import cn.geekhall.hera.server.entity.Teacher;
import cn.geekhall.hera.server.service.impl.TeacherServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherServiceImpl teacherService;

    @ResponseBody
    @RequestMapping("/all")
    public List<Teacher> all(){
        return teacherService.list();
    }

    @DeleteMapping("{id}")
    public boolean removeTeacher(@PathVariable("id") Long id){
        return teacherService.removeById(id);
    }
}
