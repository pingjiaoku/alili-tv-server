package com.alili.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String content;

    private Long createUser;

    private Long videoId;

    private LocalDateTime createTime;

    private Integer likes;

    private Long commentId;

    private Long userId;

}
