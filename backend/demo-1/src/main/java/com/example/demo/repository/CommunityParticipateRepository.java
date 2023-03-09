package com.example.demo.repository;

import com.example.demo.domain.CommunityParticipate;
import com.example.demo.domain.Gun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityParticipateRepository extends JpaRepository<CommunityParticipate, Long> {

    //    @Query("SELECT m FROM CommunityParticipate m where m.community_id = :community_id")
//    List<CommunityParticipate> findByName(@Param("community_id") Long community_id);
    List<CommunityParticipate> findByCommunityId(Long community_id);

    List<CommunityParticipate> findByUserId(Long user_id);

    CommunityParticipate findByCommunityIdAndUserId(Long community_id, Long user_id);

    boolean existsByCommunityIdAndUserId(Long community_id, Long user_id);
}
