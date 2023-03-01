package com.alili.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class VideoFavorite implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long favoriteId;

    private Long videoId;

    private LocalDateTime createTime;

}
