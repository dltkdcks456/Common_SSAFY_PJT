package com.example.demo.domain;

import lombok.Data;

import java.io.Serializable;
@Data
public class DongPK implements Serializable {
    private Integer si_code;
    private Integer gun_code;
    private Integer dong_code;
}
