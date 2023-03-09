package com.example.demo.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DealPK implements Serializable {

    @Column(name = "deal_id")
    private Long dealId;

    @Column(name = "user_id")
    private Long userId;

    @Builder
    public DealPK(Long dealId, Long userId) {
		this.dealId = dealId;
		this.userId = userId;
    }

}