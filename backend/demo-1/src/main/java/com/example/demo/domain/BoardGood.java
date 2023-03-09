package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Data
@Table(name = "board_good")
public class BoardGood {

    @EmbeddedId
    private BoardGoodPK boardGoodPK;

    @Column(name = "selected")
    private boolean selected;

    @Builder
    public BoardGood(BoardGoodPK boardGoodPK, boolean selected) {
		this.boardGoodPK = boardGoodPK;
		this.selected = selected;
    }

}