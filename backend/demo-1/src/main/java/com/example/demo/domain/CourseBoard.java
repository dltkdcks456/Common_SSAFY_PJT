package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "course_board")
public class CourseBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_board_id")
    private Long courseBoardId;

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User userId;

	@JoinColumn(name = "course_id")
	@OneToOne(fetch = FetchType.LAZY)
	private CustomCourse courseId;

	@Column
	private String title;

	@Column
	private String content;

	private Long visited;

	@Column(name = "`like`")
	private Long likeCount;

	@Column(name = "hate")
	private Long hateCount;

	@Column(name = "time")
	private LocalDateTime regTime;

//	@OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
//	@JsonIgnore
//	private List<Comment> comments = new ArrayList<>();

    @Builder
    public CourseBoard(Long courseBoardId, User userId, CustomCourse courseId, String title, String content,
                       Long visited, Long likeCount, Long hateCount, LocalDateTime regTime) {
		this.courseBoardId = courseBoardId;
		this.userId = userId;
		this.courseId = courseId;
		this.title = title;
		this.content = content;
		this.visited = visited;
		this.likeCount = likeCount;
		this.hateCount = hateCount;
		this.regTime = regTime;
    }

}