package cn.geekhall.hera.server;

import cn.geekhall.hera.server.entity.Weapon;
import cn.geekhall.hera.server.mapper.WeaponMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class ServerApplicationTests {

    @Autowired
    private WeaponMapper weaponMapper;

//    @Test
    void contextLoads() {
        System.out.println("test weapon mapper");
        List<Weapon> weapons = weaponMapper.selectList(null);
        System.out.println("======================");
        weapons.forEach(System.out::println);
        for (Weapon weapon : weapons) {
            System.out.println(weapon);
        }
        System.out.println("======================");
    }

//    @Test
    void weaponTest(){

        //_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // insert
        //_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        System.out.println("weapon insert test");
        Weapon weapon = new Weapon();
//        UUID uuid = UUID.randomUUID();
//        weapon.setId(uuid.getLeastSignificantBits());
        weapon.setId(3L);
        weapon.setName("M4A1");
        weapon.setDescription("M4A1 冲锋枪");
        int result = weaponMapper.insert(weapon);
        System.out.println(result);

        //_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // delete
        //_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        int deleteResult = weaponMapper.deleteById(3L);
        System.out.println(deleteResult);

        //_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // select
        //_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        Weapon weapon4 = weaponMapper.selectById(4L);
        if (weapon4 != null) {
            int deleteResult4 = weaponMapper.deleteById(weapon4);
            System.out.println(deleteResult4);
            System.out.println("weapon4 has bean deleted. ");
        } else {
            System.out.println("weapon4 not exist. ");
        }

        //_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // update
        //_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        Weapon weapon5 = weaponMapper.selectById(2L);
        if (weapon5 != null) {
            System.out.println("Weapon update start.");
            if (weapon5.getName().equals("M4A1")){
                weapon5.setName("AK47");
                weapon5.setDescription("AK47 冲锋枪");
            } else {
                weapon5.setName("M4A1");
                weapon5.setDescription("M4A1 冲锋枪");
            }
            weaponMapper.updateById(weapon5);
            System.out.println("Weapon update success.");
        } else {
            System.out.println("Weapon not exist.");
        }

    }
}
