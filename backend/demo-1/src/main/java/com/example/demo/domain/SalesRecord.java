package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Data
@Table(name = "sales_record")
public class SalesRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_record_id")
    private Long salesRecordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deal_id")
    private Deal dealId;

    @Column(name = "clearly_deal")
    private boolean clearlyDeal;

    @Builder
    public SalesRecord(Long salesRecordId, User userId, Deal dealId, boolean clearlyDeal ) {
		this.salesRecordId = salesRecordId;
        this.userId = userId;
        this.dealId = dealId;
        this.clearlyDeal = clearlyDeal;
    }

}