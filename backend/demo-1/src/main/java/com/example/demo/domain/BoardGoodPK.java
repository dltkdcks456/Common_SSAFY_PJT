package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardGoodPK implements Serializable {

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "user_id")
    private Long userId;

    @Builder
    public BoardGoodPK(Long boardId, Long userId) {
		this.boardId = boardId;
		this.userId = userId;
    }

}