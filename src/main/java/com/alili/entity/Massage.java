package com.alili.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Massage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long receiver;

    private Long sender;

    private String content;

    private Byte status;

}
