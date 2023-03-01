package com.alili.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReflectUtils {
    /**
     * 获取类的属性，包括其直接父类的属性
     * @param clazz 类对象
     * @return
     */
    public static Field[] getFieldsWithFather(Class clazz) {
        List<Field> list = new ArrayList<>();  // 保存属性对象数组到列表

        Collections.addAll(list, clazz.getDeclaredFields()); // 添加子类属性对象数组

        clazz = clazz.getSuperclass();  // 获得父类的字节码对象

        Collections.addAll(list, clazz.getDeclaredFields()); // 添加父类属性对象数组

        return list.toArray(new Field[0]);
    }

}
