package cn.geekhall.hera.server;

import cn.geekhall.hera.server.entity.User;
import cn.geekhall.hera.server.entity.Weapon;
import cn.geekhall.hera.server.mapper.UserMapper;
import cn.geekhall.hera.server.mapper.WeaponMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class ServerApplicationTests {

    @Autowired
    private WeaponMapper weaponMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 添加用户测试
     */
    @Test
    public void addUser(){
        User user = new User();
        user.setName("King");
        user.setAge(30);
        user.setEmail("eric@geekhall.com");
        int insert = userMapper.insert(user);
        System.out.println("insert : " + insert);
    }

    /**
     * 普通更新测试
     */
    @Test
    public void updateUser(){
        User user = new User();
        user.setId(1506862754944806914L);
        user.setEmail("test11@geekhall.com");
        int insert = userMapper.updateById(user);
        System.out.println("insert : " + insert);
    }

    /**
     * 乐观锁更新测试
     */
    @Test
    public void testOptimisticLocker(){
        // 根据ID查询数据
        User user = userMapper.selectById(1506862754944806914L);
        user.setAge(100);
        userMapper.updateById(user);
    }

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

    /**
     * 分页查询测试
     */
    @Test
    void testPage(){
        // 1. 创建page对象
        // 2. 传入两个参数：当前页和每页显示的记录数
        Page<User> page = new Page<>(1, 3);
        // 调用分页查询的方法
        userMapper.selectPage(page, null);

        System.out.println(page.getPages());    // 总页数
        System.out.println(page.getCurrent());  // 当前页
        System.out.println(page.getRecords());
        System.out.println(page.getSize()); // 每页显示的记录数
        System.out.println(page.getTotal()); // 总记录数
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }

    /**
     * 逻辑删除测试
     */
    @Test
    public void logicDeleteTest(){
        int result = userMapper.deleteById(1L);
        System.out.println(result);
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
