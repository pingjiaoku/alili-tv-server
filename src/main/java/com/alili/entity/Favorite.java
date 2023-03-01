package com.alili.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Favorite implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Long createUser;

    private Integer sort;

    private Long createTime;

    private Integer videoCount;

}
