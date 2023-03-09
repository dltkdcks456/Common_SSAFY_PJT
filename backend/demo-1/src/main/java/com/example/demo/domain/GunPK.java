package com.example.demo.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class GunPK implements Serializable {
    private Integer si_code;
    private Integer gun_code;

}
