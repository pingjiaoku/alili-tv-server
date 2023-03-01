package com.alili.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Barrage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String content;

    private Long video_id;

    private Long create_user;

    private Integer time;

    private Byte type;

    private String color;

    private LocalDateTime create_time;

}
