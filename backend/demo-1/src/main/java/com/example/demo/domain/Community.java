package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class Community {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "community_id")
    private Long communityId;

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User userId;

	@Column
	private String title;

	@Column
	private String content;

	@Column(length = 50)
	private String si;

	@Column(length = 50)
	private String gun;

	@Column(length = 50)
	private String dong;

	@Column(name = "current_person")
	private int currentPerson;

	@Column(name = "max_person")
	private int maxPerson;

	@Column(name = "date")
	private LocalDateTime date;

	@Column(name = "in_progress")
	private boolean inProgress;

	@Column(name = "chatting_room_id")
	private String chattingRoomId;

    @Builder
    public Community(Long communityId, User userId, String title, String content,
					 String si, String gun, String dong, int currentPerson, int maxPerson,
					 LocalDateTime date, boolean inProgress, String chattingRoomId) {
		this.communityId = communityId;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.si = si;
		this.gun = gun;
		this.dong = dong;
		this.currentPerson = currentPerson;
		this.maxPerson = maxPerson;
		this.date = date;
		this.inProgress = inProgress;
		this.chattingRoomId = chattingRoomId;
    }

}