package com.example.demo.domain;

import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@IdClass(CommunityParticipatePK.class)
public class CommunityParticipate {
    @Id
    @Column(name = "community_id")
    private Long communityId;

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Builder
    public CommunityParticipate(Long communityId, Long userId) {
        this.communityId = communityId;
        this.userId = userId;
    }

}
