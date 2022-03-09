package cn.geekhall.hera.server.controller;


import cn.geekhall.hera.server.entity.Product;
import cn.geekhall.hera.server.mapper.ProductMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>ß
 *  前端控制器
 * </p>
 *
 * @author yiny
 * @since 2022-03-06
 */
@RestController
@RequestMapping("/server/product")
public class ProductController {

    @Autowired
    private ProductMapper productMapper;

    @GetMapping("/greeting")
    public String greeting(){
        System.out.println("Greetings");
        return "Hello from hera product";
    }


    @ResponseBody
    @RequestMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long id) {
        System.out.println("getProduct called , id = " + id);
        return productMapper.selectById(id);
    }

    @ResponseBody
    @RequestMapping("/")
    public List<Object> products() {
        System.out.println("getProduct called");
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("brand");
        return productMapper.selectObjs(queryWrapper);
    }

    public void deleteProduct(@PathVariable("id") Long id) {
//        productMapper.
//        return productMapper.delete();
    }
}
