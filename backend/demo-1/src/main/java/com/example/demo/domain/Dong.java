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
@IdClass(DongPK.class)
public class Dong {

    @Id
    @Column(name = "si_code")
    private Integer si_code;

    @Id
    @Column(name = "gun_code")
    private Integer gun_code;

    @Id
    @Column(name = "code")
    private Integer dong_code;

    @Column(length = 255)
    private String name;
}