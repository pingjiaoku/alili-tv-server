package com.alili.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class VideoCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String videoPath;

    private Long collectionId;

    private Integer sort;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long checker;

    private Byte status;

}
