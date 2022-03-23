package cn.geekhall.hera.boot;

//import cn.geekhall.hera.server.mapper.WeaponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

/**
 * BootTest
 *
 * @author yiny
 */
@SpringBootApplication
public class BootTest {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(BootTest.class, args);
        ConfigurableEnvironment environment = run.getEnvironment();


//        Map<String, Object> systemEnvironment = environment.getSystemEnvironment();
//        Map<String, Object> systemProperties = environment.getSystemProperties();
//        System.out.println(systemEnvironment);
//        System.out.println(systemProperties);

    }
}
