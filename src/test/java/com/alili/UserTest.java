package com.alili;

import com.alili.entity.User;
import com.alili.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.apache.ibatis.mapping.SqlCommandType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class UserTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testGetById() {
        User user = userMapper.getById(1L);
        System.out.println(user);
    }

    @Test
    public void testEnum() {
        List<SqlCommandType> allowType = Arrays.asList(
                SqlCommandType.UNKNOWN, SqlCommandType.INSERT, SqlCommandType.UPDATE
        );
        System.out.println(allowType.contains(SqlCommandType.INSERT));
    }
}
