package com.alili.service.impl;

import com.alili.entity.dto.UserDto;
import com.alili.entity.User;
import com.alili.common.exception.BusinessException;
import com.alili.common.exception.SystemException;
import com.alili.mapper.UserMapper;
import com.alili.service.UserService;
import com.alili.utils.JWTUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private UserMapper userMapper;

    @Override
    public User getById(Long id) {
        return userMapper.getById(id);
    }

    @Override
    public PageInfo<User> page(Integer pageIndex, Integer pageSize, UserDto user) {
        PageHelper.startPage(pageIndex, pageSize);
        List<User> list = userMapper.page(user);
        return new PageInfo<>(list, 5);
    }

    @Override
    public void save(User user) {
        Integer count = userMapper.save(user);
        System.out.println(count);
    }

    @Override
    public Map<String, Object> loginByPwd(UserDto user) {
        if (user.getPhone() != null || "".equals(user.getPhone())
                || user.getEmail() != null || "".equals(user.getEmail())) {
            // 查询是否存在该用户
            User userInfo = userMapper.getByLoginInfo(user);
            if (userInfo != null) {
                // 将用户id存入Token中
                String token = JWTUtils.createToken(userInfo.getId().toString());
                Map<String, Object> map = new HashMap<>();
                map.put("user", userInfo);
                map.put("token", token);
                return map;
            } else {
                throw new BusinessException("账号或密码错误");
            }
        } else {
            throw new BusinessException("手机号或邮箱为空");
        }
    }

    @Override
    public Map<String, Object> loginByCode(UserDto user) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        // 从redis中获取验证码
        String redisKey;
        if (user.getPhone() != null) {
            redisKey = user.getPhone();
        } else if (user.getEmail() != null) {
            redisKey = user.getEmail();
        } else {
            throw new BusinessException("手机号或邮箱为空");
        }
        String code = ops.get(redisKey);

        // 匹配验证码
        if (user.getVerificationCode() != null && user.getVerificationCode().equals(code)) {
            // 删除验证码
            redisTemplate.delete(redisKey);

            // 查询是否存在该用户，不存在则直接创建
            User userInfo = userMapper.getByLoginInfo(user);
            if (userInfo == null) {
                Integer count = userMapper.save(user);
                // mybatis将生成的id放入了user中
                userInfo = user;
                if (count == 0) throw new SystemException("注册失败");
            }
            //将userId存入token中
            String token = JWTUtils.createToken(userInfo.getId().toString());
            Map<String,Object> map = new HashMap<>();
            map.put("user", userInfo);
            map.put("token", token);
            return map;
        } else {
            throw new BusinessException("验证码无效");
        }
    }
}
