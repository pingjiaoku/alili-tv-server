package com.alili.entity.dto;

import com.alili.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String verificationCode;

    private Integer orderByFansAsc;

    private Integer orderByDateAsc;
}
