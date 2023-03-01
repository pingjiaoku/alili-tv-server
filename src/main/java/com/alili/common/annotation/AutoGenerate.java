package com.alili.common.annotation;

import org.apache.ibatis.mapping.SqlCommandType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动生成值，只用于LocalDateTime和Long类型上
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoGenerate {
    /**
     * UNKNOWN：默认值，insert和update均会自动装配
     * INSERT：只在insert时自动装配
     * UPDATE：只在update时自动装配
     */
    SqlCommandType fill() default SqlCommandType.UNKNOWN;
}

