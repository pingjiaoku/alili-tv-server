package com.alili.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Emp implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String account;

    private String password;

    private String phone;

    private Byte gender;

    private String idNumber;

    private Byte status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;

}
