package com.example.demo.repository;

import com.example.demo.domain.CommunityBanUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityBanUserRepository extends JpaRepository<CommunityBanUser, Long> {

    CommunityBanUser findByCommunityIdAndUserId (Long communityId, Long userId);

    List<CommunityBanUser> findByCommunityId (Long communityId);
}
