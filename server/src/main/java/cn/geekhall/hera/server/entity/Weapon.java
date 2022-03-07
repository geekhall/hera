package cn.geekhall.hera.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yiny
 * @since 2022-03-06
 */
@Data
@TableName("h_weapon")
@ApiModel(value = "Weapon对象", description = "")
@AllArgsConstructor
@NoArgsConstructor
public class Weapon extends Model<Weapon> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("武器名称")
    private String name;

    @ApiModelProperty("武器描述")
    private String description;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
