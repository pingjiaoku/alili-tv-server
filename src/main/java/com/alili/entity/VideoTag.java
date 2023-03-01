package com.alili.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class VideoTag implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long videoId;

    private Long tagId;
}
