package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "course_board_comment")
public class CourseBoardComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_board_comment_id")
    private Long courseBoardCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_board_id")
	private CourseBoard courseBoardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "content")
    private String content;

    @Column(name = "time")
    private LocalDateTime time;

    @Builder
    public CourseBoardComment(Long courseBoardCommentId, CourseBoard courseBoardId, User userId, String content, LocalDateTime time) {
		this.courseBoardCommentId = courseBoardCommentId;
		this.courseBoardId = courseBoardId;
        this.userId = userId;
        this.content = content;
        this.time = time;
    }

}