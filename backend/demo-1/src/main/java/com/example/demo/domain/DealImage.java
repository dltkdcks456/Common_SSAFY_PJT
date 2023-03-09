package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "deal_image")
public class DealImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deal_image_id")
    private Long dealImageId;

    @JoinColumn(name = "deal_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Deal dealId;

    @Column(length = 255)
    private String name;
    @Column(length = 255)
    private String path;
    @Column(length = 255)
    private String type;

    @Builder
    public DealImage(Long dealImageId, Deal dealId, String name, String path, String type) {
        this.dealImageId = dealImageId;
        this.dealId = dealId;
        this.name = name;
        this.path = path;
        this.type = type;
    }
}
