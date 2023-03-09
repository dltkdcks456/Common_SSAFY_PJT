package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class UnofficialPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "unofficial_point_id")
    private Long unofficialPointId;

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User userId;

    @Column(name = "name")
    private String name;

	@Column(name = "image_name")
	private String imageName;

	@Column(name = "content")
	private String content;

	@Column(name = "lat")
	private BigDecimal lat;

	@Column(name = "lon")
	private BigDecimal lon;

    @Builder
    public UnofficialPoint(Long unofficialPointId, User userId, String name, String imageName, String content,
						   BigDecimal lat, BigDecimal lon) {
		this.unofficialPointId = unofficialPointId;
		this.userId = userId;
		this.name = name;
		this.imageName = imageName;
		this.content = content;
		this.lat = lat;
		this.lon = lon;
    }

}