package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class Board{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
    private Long boardId;

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User userId;

    @Column(name = "board_code")
    private int boardCode;

	@Column(name = "count")
	private Long count;

	@Column(length = 255)
	private String title;

	@Column(length = 16000)
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
    public Board(Long boardId, User userId, int boardCode, Long count, String title, String content,
				 Long visited, Long likeCount, Long hateCount, LocalDateTime regTime) {
		this.boardId = boardId;
		this.userId = userId;
        this.boardCode = boardCode;
		this.count = count;
		this.title = title;
		this.content = content;
		this.visited = visited;
		this.likeCount = likeCount;
		this.hateCount = hateCount;
		this.regTime = regTime;
    }

}