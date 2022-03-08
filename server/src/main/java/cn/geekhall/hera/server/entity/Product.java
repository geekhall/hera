package cn.geekhall.hera.server.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author yiny
 * @since 2022-03-06
 */
@Data
@TableName("h_product")
@ApiModel(value = "Product对象", description = "")
@AllArgsConstructor
@NoArgsConstructor
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品描述")
    private String description;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @TableLogic  // 标识逻辑删除字段
    @ApiModelProperty("删除标志")
    private Boolean isDeleted;

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
