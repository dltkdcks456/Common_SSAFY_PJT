package com.example.demo.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseBoardPK implements Serializable {

    @Column(name = "course_board_id")
    private Long courseBoardId;

    @Column(name = "user_id")
    private Long userId;

    @Builder
    public CourseBoardPK(Long courseBoardId, Long userId) {
		this.courseBoardId = courseBoardId;
		this.userId = userId;
    }

}