package com.example.demo.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Data
@Table(name = "board_visited")
public class BoardVisited {

    @EmbeddedId
    private BoardGoodPK boardGoodPK;

    @Builder
    public BoardVisited(BoardGoodPK boardGoodPK) {
		this.boardGoodPK = boardGoodPK;
    }

}