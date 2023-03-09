package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@Getter 
@Setter
@Entity 
public class CertInfo {

    @Id
    private String email;

    @Column(length = 55)
    private String code;

    @Builder
    public CertInfo(String email, String code) {
        this.email = email;
        this.code = code;
    }

}