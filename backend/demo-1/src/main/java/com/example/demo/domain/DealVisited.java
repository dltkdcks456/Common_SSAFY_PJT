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
@Table(name = "deal_visited")
public class DealVisited {

    @EmbeddedId
    private DealPK dealPK;

    @Builder
    public DealVisited(DealPK dealPK) {
		this.dealPK = dealPK;
    }

}