package com.alili.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Byte channelId;

    private Integer count;

    private LocalDateTime createTime;

    private Long createUser;

}
