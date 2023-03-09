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
@Table(name = "course_board_like")
public class CourseBoardLike {

    @EmbeddedId
    private CourseBoardPK courseBoardPK;

    @Column
    private boolean selected;

    @Builder
    public CourseBoardLike(CourseBoardPK courseBoardPK, boolean selected) {
		this.courseBoardPK = courseBoardPK;
		this.selected = selected;
    }

}