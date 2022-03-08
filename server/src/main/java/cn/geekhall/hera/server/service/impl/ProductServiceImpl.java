package cn.geekhall.hera.server.service.impl;

import cn.geekhall.hera.server.entity.Product;
import cn.geekhall.hera.server.mapper.ProductMapper;
import cn.geekhall.hera.server.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yiny
 * @since 2022-03-06
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    public void initProduct(){
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1L,"MacBookPro","Mac book pro", "Apple", new BigDecimal(15000.00), true));
        products.add(new Product(2L,"MacBookAir","Mac book air", "Apple", new BigDecimal(8000.00), true));
        products.add(new Product(3L,"iPhone13","iphone13 pro max", "Apple", new BigDecimal(9800.00), true));
        products.add(new Product(4L,"iMac","iMac", "Apple", new BigDecimal(12000.00), true));
        products.add(new Product(5L,"iWatch","iWatch", "Apple", new BigDecimal(4000.00), true));
        products.add(new Product(6L,"MacMini","MacMini", "Apple", new BigDecimal(6000.00), true));
        products.add(new Product(7L,"AirPots","Air Pots Pro", "Apple", new BigDecimal(2000.00), true));
        products.add(new Product(8L,"Surface","Surface book", "Microsoft", new BigDecimal(8000.00), true));
        products.add(new Product(9L,"Honor","Honor phone", "Huawei", new BigDecimal(2000.00), true));

        for (Product product: products){
            Product tempProduct = productMapper.selectById(product.getId());
            if (tempProduct != null && tempProduct.equals(product)) {
                continue;
            }
            productMapper.updateById(product);
        }
    }
}
