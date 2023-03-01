package com.alili.entity.dto;

import com.alili.entity.Video;
import lombok.Data;

import java.io.Serializable;

@Data
public class VideoDto extends Video implements Serializable {

    private static final long serialVersionUID = 1L;

    private String createUserName;
}
