package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "deal")
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "deal_id")
    private Long dealId;

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User userId;

    @Column
    private String kind;

	@Column
	private String title;

	@Column
	private String content;

	@Column
	private String name;

	@Column
	private Long price;

	@Column(name = "on_sale")
	private boolean onSale;

	@Column
	private Long visited;

	@Column
	private LocalDateTime time;

//	@OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
//	@JsonIgnore
//	private List<Comment> comments = new ArrayList<>();

    @Builder
    public Deal(Long dealId, User userId, String kind, String title, String content,
				String name, Long price, boolean onSale, Long visited,LocalDateTime time) {
		this.dealId = dealId;
		this.userId = userId;
        this.kind = kind;
		this.title = title;
		this.content = content;
		this.name = name;
		this.price = price;
		this.onSale = onSale;
		this.visited = visited;
		this.time = time;
    }

}