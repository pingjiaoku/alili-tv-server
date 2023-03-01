package com.alili.mapper;

import com.alili.entity.dto.UserDto;
import com.alili.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User getById(Long id);

    Integer save(User user);

    List<User> page(@Param("user") UserDto user);

    User getByLoginInfo(@Param("user") UserDto user);
}
