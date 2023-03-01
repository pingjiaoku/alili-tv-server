package com.alili.intercepter;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.alili.common.annotation.AutoGenerate;
import com.alili.common.BaseContext;
import com.alili.common.annotation.TableId;
import com.alili.common.enums.IdType;
import com.alili.common.exception.SystemException;
import com.alili.utils.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
@Component
@Slf4j
public class MybatisInterceptor implements Interceptor {

    @Value("${mybatis.db-config.auto-generate.id-type}")
    private IdType idType;

    @Value("${mybatis.db-config.auto-generate.id-name}")
    private String idName;

    // sql的类型
    private boolean isInsert = false;
    private boolean isUpdate = false;

    @Override
    public Object intercept(Invocation invocation) throws IllegalAccessException, InvocationTargetException {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        // 获取sql类型invocation = {Invocation@7519}
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        isInsert = SqlCommandType.INSERT.equals(sqlCommandType);
        isUpdate = SqlCommandType.UPDATE.equals(sqlCommandType);
        // 获取参数
        Object parameter = invocation.getArgs()[1];
        // 获取类对象
        Class<?> aClass = parameter.getClass();

        // 获取成员变量
        Field[] fields;

        // 获取类名，判断是不是Dto
        String className = aClass.getSimpleName();
        if (className.endsWith("Dto")) {
            // 为Dto时需要获取父类的属性，反射默认只会获取自身的属性
            fields = ReflectUtils.getFieldsWithFather(aClass);
        } else {
            fields = aClass.getDeclaredFields();
        }

        System.out.println(className + ", " + className.endsWith("Dto"));
        for (Field field : fields) {
            // 只在插入时处理TableId注解
            if (isInsert) {
                tableId(field, parameter);
            }

            // 处理AutoGenerate注解
            autoGenerate(field, parameter);
        }
        return invocation.proceed();
    }

    private void tableId(Field field, Object parameter) throws IllegalAccessException {

        TableId annotation = field.getAnnotation(TableId.class);
        // 拥有注解TableId优先注解，没有时用配置文件中全局id策略
        if (annotation != null) {
            idType = annotation.fill();
        } else if (field.getName().equals(idName)) {
            log.info(field.getName());
        } else {
            return;
        }
        if (idType == null) return;

        field.setAccessible(true);
        switch (idType) {
            case SNOW:
                Snowflake snowflake = IdUtil.getSnowflake();
                field.set(parameter, snowflake.nextId());
                break;
            case UUID:
                field.set(parameter, IdUtil.simpleUUID());
                break;
            case AUTO:
                field.set(parameter, null);
                break;
            case INPUT:
                break;
            default:
                throw new SystemException("@TableId has unexpected attribute value, only [SNOW, UUID, AUTO, INPUT] are allowed");
        }
    }

    private void autoGenerate(Field field, Object parameter) throws IllegalAccessException {
        // 属性上是否有AutoGenerate注解
        AutoGenerate annotation = field.getAnnotation(AutoGenerate.class);
        if (annotation != null) {

            // 查看使用注解的属性何时使用自动注入
            switch (annotation.fill()) {
                case UNKNOWN:
                    log.info("field[{}] is autoGenerate on insert or update", field.getName());
                    if (isInsert || isUpdate) generateData(field, parameter);
                    break;
                case INSERT:
                    log.info("field[{}] is autoGenerate on insert", field.getName());
                    if (isInsert) generateData(field, parameter);
                    break;
                case UPDATE:
                    log.info("field[{}] is autoGenerate on update", field.getName());
                    if (isUpdate) generateData(field, parameter);
                    break;
                default:
                    throw new SystemException("@AutoGenerate has unexpected attribute value, only [UNKNOWN, INSERT, UPDATE] are allowed");
            }
        }
    }

    /**
     * 根据属性类型自动注入值
     *
     * @param field     待自动生成的字段
     * @param parameter
     * @throws IllegalAccessException
     */
    private void generateData(Field field, Object parameter) throws IllegalAccessException {
        field.setAccessible(true);
        if (LocalDateTime.class == field.getType()) {
            field.set(parameter, LocalDateTime.now());
        } else if (Long.class == field.getType()) {
            field.set(parameter, BaseContext.getCurrentId());
        } else {
            throw new SystemException("@AutoGenerate can only be used in [LocalDateTime, Long]");
        }
    }
}
