package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "custom_course")
public class CustomCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
    private Long courseId;

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User userId;

	@Column(name = "title")
	private String title;

	@Column
	private String name;

	@Column
	private String path;

	@Column(length = 50)
	private String type;

    @Builder
    public CustomCourse(Long courseId, User userId, String title, String name,
						String path, String type) {
		this.courseId = courseId;
		this.userId = userId;
		this.title = title;
		this.name = name;
		this.path = path;
		this.type = type;
    }

}