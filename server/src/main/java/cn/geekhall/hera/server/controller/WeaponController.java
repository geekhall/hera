package cn.geekhall.hera.server.controller;


import cn.geekhall.hera.server.entity.Weapon;
import cn.geekhall.hera.server.mapper.WeaponMapper;
import cn.geekhall.hera.server.service.IWeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yiny
 * @since 2022-03-06
 */
@RestController
@RequestMapping("/server/weapon")
public class WeaponController {

    @Autowired
    private IWeaponService weaponService;

    @GetMapping("/greeting")
    public String greeting(){
        System.out.println("Greetings");
        return "Hello from hera";
    }

    @ResponseBody
    @RequestMapping("/{id}")
    public Weapon getWeaponById(@PathVariable("id") Long id){
        return weaponService.getById(id);
    }
}
