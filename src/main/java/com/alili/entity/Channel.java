package com.alili.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Channel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Byte id;

    private String name;

    private String icon;

    private Byte type;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
