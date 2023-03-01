package com.alili.entity;

import com.alili.common.annotation.AutoGenerate;
import com.alili.common.annotation.TableId;
import com.alili.common.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.ibatis.mapping.SqlCommandType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(fill = IdType.AUTO)
    private Long id;

    private String name;

    private String headshot;

    private String backImg;

    private String password;

    private Byte gender;

    private String phone;

    private String email;

    private String idNumber;

    @AutoGenerate(fill = SqlCommandType.INSERT)
    private LocalDateTime createTime;

    @AutoGenerate
    private LocalDateTime updateTime;

    // 只在管理员修改其状态时才更新
    private Long updateUser;

    private String channelSort;

    private Byte status;

    private Byte type;

    private Integer video;

    private Integer follow;

    private Integer fans;

    private Integer collection;
}
