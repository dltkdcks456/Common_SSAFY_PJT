package com.example.demo.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(GunPK.class)
public class Gun {

    @Id
    @Column(name = "si_code")
    private Integer si_code;

    @Id
    @Column(name = "code")
    private Integer gun_code;

    @Column(length = 255)
    private String name;
}