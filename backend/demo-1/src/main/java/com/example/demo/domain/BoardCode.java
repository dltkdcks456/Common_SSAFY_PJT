package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "board_code")
public class BoardCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "code")
    private int code;

	@Column(name = "name")
	private String name;

    @Column(name = "count")
    private Long count;

    @Builder
    public BoardCode(int code, String name, Long count) {
		this.code = code;
		this.name = name;
        this.count = count;
    }

}