package cn.geekhall.hera.server.controller;


import cn.geekhall.hera.server.entity.Product;
import cn.geekhall.hera.server.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    public void deleteProduct(@PathVariable("id") Long id) {
//        productMapper.
//        return productMapper.delete();
    }
}
