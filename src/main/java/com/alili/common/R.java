package com.alili.common;

import lombok.Data;

/**
 * 响应结果集
 * @param <T>
 */
@Data
public class R<T> {

    private Byte code; //编码：1成功，0和其它数字为失败

    private T data; //数据

    private String massage; //错误信息

    public static R<String> success() {
        R<String> r = new R<>();
        r.code = 1;
        return r;
    }

    public static <T> R<T> success(T data) {
        R<T> r = new R<>();
        r.data = data;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String massage) {
        R<T> r = new R<T>();
        r.massage = massage;
        r.code = 0;
        return r;
    }
}
