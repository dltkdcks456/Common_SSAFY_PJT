package com.example.demo.domain;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity(name = "report")
public class Report {
    @Id
	@GeneratedValue
	private Long report_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id_reported")
	private User userIdReported;

	@Column(length = 255)
	private String type;

	@Column(length = 255)
	private String content;

	private LocalDateTime time;

	@Column(name="check")
	private boolean state;


	@Builder
	public Report(Long report_id, User userId, User userIdReported, String type,
				  String content, LocalDateTime time, boolean state) {
		this.report_id = report_id;
		this.userId = userId;
		this.userIdReported = userIdReported;
		this.type = type;
		this.content = content;
		this.time = time;
		this.state = state;
	}

}