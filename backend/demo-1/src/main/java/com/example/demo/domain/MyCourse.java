package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "my_course")
@IdClass(MyCoursePK.class)
public class MyCourse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_course_id")
    private Long myCourseId;

//    @EmbeddedId
//    private MyCoursePK myCoursePK;

    @Id
    private Long courseId;
    @Id
    private Long userId;

    @Builder
    public MyCourse(Long myCourseId, Long courseId, Long userId) {
		this.myCourseId = myCourseId;
        this.courseId = courseId;
        this.userId = userId;
    }

}