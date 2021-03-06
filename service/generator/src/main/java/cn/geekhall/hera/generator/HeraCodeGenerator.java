package cn.geekhall.hera.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * HeraCodeGenerator
 *
 * @author yiny
 */
public class HeraCodeGenerator {
    public static void main(String[] args) {
        List<String> includes = new ArrayList<String>();
        includes.add("h_teacher");
        includes.add("h_user");
        includes.add("h_role");
        includes.add("h_player");
        includes.add("h_product");
        includes.add("h_weapon");
        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                .entityBuilder()
                .disableSerialVersionUID()
                .enableChainModel()
                .enableRemoveIsPrefix()
                .enableTableFieldAnnotation()
                .enableActiveRecord()
                .versionColumnName("version")
                .versionPropertyName("version")
                .logicDeleteColumnName("deleted")
                .logicDeletePropertyName("deleteFlag")
                .naming(NamingStrategy.no_change)
                .columnNaming(NamingStrategy.underline_to_camel)
                .addSuperEntityColumns("id", "created_by", "created_time", "updated_by", "updated_time")
                .addTableFills(new Column("create_time", FieldFill.INSERT))
                .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                .idType(IdType.AUTO)
                .formatFileName("%sEntity")
                .build();


        FastAutoGenerator.create("jdbc:mysql://localhost:3316/olympians?userUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai", "zeus", "yy123456")
                // ????????????
                .globalConfig(builder -> {
                    builder.author("yiny") // ????????????
                            .enableSwagger() // ?????? swagger ??????
                            .fileOverride() // ?????????????????????
                            .outputDir(System.getProperty("user.dir") + "/service/generator/src/main/java"); // ??????????????????
                })
                // ?????????
                .packageConfig(builder -> {
                    builder.parent("cn.geekhall.hera")  // ???????????????
                            .moduleName("server")       // ?????????????????????
                            .entity("entity")             // Entity??????
                            .service("service")         // Service??????
                            .serviceImpl("service.impl") // ServiceImpl??????
                            .controller("controller")   // Controller??????
                            .mapper("mapper")           // Mapper??????
                            .xml("mapper")              // Mapper XML??????
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                    System.getProperty("user.dir") + "/service/generator/src/main/resources/mapper")); // ??????mapperXml????????????
                })
                // ????????????
                .strategyConfig(builder -> {
                    builder.addInclude(includes) // ???????????????????????????
                            .addTablePrefix("h_") // ?????????????????????
                            .entityBuilder()
                            .enableActiveRecord()
                            .naming(NamingStrategy.underline_to_camel)  // ???????????????????????????????????????????????????????????????
                            .enableLombok()                             // ??????Lombok??????
//                            .logicDeleteColumnName("is_deleted")        // ?????????????????????
                            .controllerBuilder()
                            .formatFileName("%sController")             // ?????????Controller??????
                            .enableRestStyle()                          // ????????????@RestController
                            .mapperBuilder()
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .enableMapperAnnotation()                   // ??????@Mapper??????
                            .formatMapperFileName("%sMapper")           // ?????????Mapper?????????
                            .formatXmlFileName("%sMapper");             // ?????????xml?????????
                })
                .templateEngine(new FreemarkerTemplateEngine()) // ??????Freemarker???????????????????????????Velocity????????????
                .execute();

    }
}
