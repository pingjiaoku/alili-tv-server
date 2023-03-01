package com.alili.intercepter;

import com.alili.common.annotation.LoginRequired;
import com.alili.common.exception.BusinessException;
import com.alili.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

public class UserLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 方法注解级拦截器
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        // 有 @LoginRequired 注解，需要认证
        if (methodAnnotation != null) {
            // 这写你拦截需要干的事儿，比如取缓存，SESSION，权限判断等
            //http的header中获得token
            String token = request.getHeader(JWTUtils.USER_LOGIN_TOKEN);
            //token不存在
            if (token == null || "".equals(token))
                throw new BusinessException("请先登录");
            //验证token
            String sub = JWTUtils.validateToken(token);
            if (sub == null || "".equals(sub))
                throw new BusinessException("身份验证失败");
            //更新token有效时间 (如果需要更新其实就是产生一个新的token)
            if (JWTUtils.isNeedUpdate(token)){
                String newToken = JWTUtils.createToken(sub);
                response.setHeader(JWTUtils.USER_LOGIN_TOKEN,newToken);
            }
        }
        return true;
    }
}
