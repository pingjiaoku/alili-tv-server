package com.alili.controller;

import com.alili.common.R;
import com.alili.entity.dto.UserDto;
import com.alili.entity.User;
import com.alili.service.UserService;
import com.alili.utils.JWTUtils;
import com.alili.utils.SendCodeUtils;
import com.aliyuncs.exceptions.ClientException;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import jakarta.mail.internet.AddressException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping(value = "/user", produces = "application/json")
@Slf4j
public class UserController {

    @Resource
    private SendCodeUtils sendCodeUtils;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private UserService userService;

    @GetMapping("/{id}")
    public R<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return user != null ? R.success(user) : R.error("uid：" + id + "用户不存在");
    }

    @PostMapping("/sendCode")
    private R<String> sendCode(@RequestBody User user) throws ClientException, UnsupportedEncodingException, AddressException {
        // 生成随机6位数验证码
        Random random = new Random();
        String code = random.nextInt(900000) + 100000 + "";

        if (user.getPhone() != null) {
            Long expire = redisTemplate.getExpire(user.getPhone());
            // 两次请求验证码不能小于1分钟
            if (expire != null && expire > 240) {
                return R.error("验证码请求过快，请稍后重试");
            }

            log.info("发送验证码：phone = {}, code = {}", user.getPhone(), code);

            // 调用阿里云提供的短信服务api完成发送短信
//            sendCodeUtils.sendSMS(user.getPhone(), code);

            // 将验证码存入redis中
            redisTemplate.opsForValue().set(user.getPhone(), code, 5L, TimeUnit.MINUTES);
            return R.success("发送成功");
        } else if (user.getEmail() != null) {
            Long expire = redisTemplate.getExpire(user.getEmail());
            if (expire != null && expire > 240) {
                return R.error("验证码请求过快，请稍后重试");
            }

            log.info("发送验证码：email = {}, code = {}", user.getEmail(), code);
            sendCodeUtils.sendEmail(user.getEmail(), code);

            redisTemplate.opsForValue().set(user.getEmail(), code, 5L, TimeUnit.MINUTES);
            return R.success("发送成功");
        } else {
            return R.error("手机号或邮箱不能为空");
        }
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody UserDto user, HttpServletResponse response) {
        Map<String, Object> map;
        // 判断是验证码登录还是密码登录
        if (user.getVerificationCode() != null) {
            map = userService.loginByCode(user);
        } else if (user.getPassword() != null) {
            map = userService.loginByPwd(user);
        } else {
            return R.error("错误的登录方式");
        }

        // 将token存入Http的header中
        response.setHeader(JWTUtils.USER_LOGIN_TOKEN, (String) map.get("token"));
        return R.success((User) map.get("user"));
    }

    @PostMapping("/logout")
    public R<String> logout() {
        return R.success();
    }

    @GetMapping("/page/{pageIndex}/{pageSize}")
    public PageInfo<User> page(@PathVariable Integer pageIndex, @PathVariable Integer pageSize, UserDto user) {
        PageInfo<User> page = userService.page(pageIndex, pageSize, user);
        System.out.println(page);

        return page;
    }

}
