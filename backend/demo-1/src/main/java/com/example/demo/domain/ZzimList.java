package com.example.demo.domain;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Data
@Table(name = "zzim_list")
public class ZzimList {

    @EmbeddedId
    private DealPK dealPK;

    @Builder
    public ZzimList(DealPK dealPK) {
		this.dealPK = dealPK;
    }

}