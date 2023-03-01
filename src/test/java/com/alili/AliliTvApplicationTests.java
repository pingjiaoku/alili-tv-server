package com.alili;

import com.alili.common.enums.IdType;
import com.alili.entity.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class AliliTvApplicationTests {

    @Value("${mybatis.db-config.id-type}")
    IdType value;

    @Test
    void contextLoads() {
        System.out.println(value.equals(IdType.SNOW));
    }

    public static void main(String[] args) {

    }

}
