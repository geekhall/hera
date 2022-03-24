package cn.geekhall.hera.server.service.impl;

import cn.geekhall.hera.server.entity.User;
import cn.geekhall.hera.server.mapper.UserMapper;
import cn.geekhall.hera.server.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yiny
 * @since 2022-03-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}