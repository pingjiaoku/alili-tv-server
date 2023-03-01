package com.alili.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Follow implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long createUser;

    private Long userId;

    private LocalDateTime createTime;

}
