package com.alili.service;

import com.alili.entity.dto.UserDto;
import com.alili.entity.User;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

public interface UserService {
    User getById(Long id);

    PageInfo<User> page(Integer pageIndex, Integer pageSize, UserDto user);

    void save(User user);

    Map<String, Object> loginByPwd(UserDto user);

    Map<String, Object> loginByCode(UserDto user);
}
