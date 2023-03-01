package com.alili.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String describe;

    private Long createUser;

    private Byte channel;

    private String videoPath;

    private String coverPath;

    private Integer totalTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Byte type;

    private Long checker;

    private Byte status;

    private Integer viewCount;

    private Integer barrageCount;

    private Integer likes;

    private Integer collection;

}
