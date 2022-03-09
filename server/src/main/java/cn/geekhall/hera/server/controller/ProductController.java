package cn.geekhall.hera.server.controller;


import cn.geekhall.hera.server.entity.Product;
import cn.geekhall.hera.server.mapper.ProductMapper;
import cn.geekhall.hera.server.service.IProductService;
import cn.geekhall.hera.server.service.impl.ProductServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
    @RequestMapping("/random")
    public Product randomLike(){
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("brand", "Apple")
                .between("price", 5000.00, 10000.00);
        List<Product> list = productMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
        int index = (int)(Math.random() * list.size());
        return list.get(index);
    }

    /**
     * http://localhost:8080/server/product/order
     * @return
     */
    @ResponseBody
    @RequestMapping("/order")
    public List<Product> testOrder(){
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("price")
                .orderByAsc("brand");
        List<Product> list = productMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
        return list;
    }

    /**
     * delete product by id.
     */
    @RequestMapping("/del/{id}")
    public void addProduct(@PathVariable("id") Long id){
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        Product product = new Product();
        product.setId(id);
        queryWrapper.setEntity(product);
        int delete = productMapper.delete(queryWrapper);
        System.out.println("product " + id + " has bean deleted ");
//        int result = productMapper.deleteById(id);
//        System.out.println(result);
    }

    /**
     * 将 （品牌为苹果，并且价格大于等于8000）, 或者品牌为华为 的产品 描述修改为打折啦。
     */
    @RequestMapping("/bargain")
    public void bargain(){
        // 将 （品牌为苹果，并且价格大于等于8000）, 或者品牌为华为 的产品 描述修改为打折啦。
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("brand", "Apple")
                .ge("price", 8000)
                .or()
                .like("brand", "Huawei");
        Product product = new Product();
        product.setDescription("打折啦");
    }

    @Autowired
    private ProductServiceImpl productService;

    @RequestMapping("/init")
    public void init(){
        productService.initProduct();
    }

    /**
     * select * from h_product where brand like '%App%' and (price > 5000 or description is null)
     */
    @RequestMapping("/update")
    public void updateProduct(){
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.like("brand", "App")
                .and(i->i.gt("price", 5000).or().isNull("description"));
        Product product = new Product();
        product.setDescription("大甩卖！");
        productMapper.update(product, productQueryWrapper);
    }


    public void updateProductWithOrCondition(){
        UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
    }
}
