package com.alili.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Likes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long cvId;

    private Byte type;

    private Long createUser;

    private LocalDateTime createTime;

}
