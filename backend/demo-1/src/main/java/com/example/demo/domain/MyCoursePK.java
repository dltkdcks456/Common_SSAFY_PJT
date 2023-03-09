package com.example.demo.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
public class MyCoursePK implements Serializable {

    private Long courseId;

    private Long userId;

//    @Builder
//    public MyCoursePK(Long courseId, Long userId) {
//		this.courseId = courseId;
//		this.userId = userId;
//    }

}