package com.example.demo.domain;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Data
@Table(name = "course_board_visited")
public class CourseBoardVisited {

    @EmbeddedId
    private CourseBoardPK courseBoardPK;

    @Builder
    public CourseBoardVisited(CourseBoardPK courseBoardPK) {
		this.courseBoardPK = courseBoardPK;
    }

}