package com.sudhanshu.profileservice.repository;

import com.sudhanshu.profileservice.entity.UserProfiles;
import com.sudhanshu.profileservice.repository.projection.ProfileSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<UserProfiles, UUID> {

    @Query(value ="SELECT p.user_id, p.engagement_score, p.churn_risk_score, \n" +
            "               p.last_seen, p.subscription_tier\n" +
            "        FROM user_profiles p \n" +
            "        WHERE p.engagement_score > :minScore \n" +
            "        ORDER BY p.engagement_score DESC",
    countQuery = "SELECT COUNT(*) FROM user_profiles WHERE engagement_score > :minScore",
    nativeQuery = true)
    Page<ProfileSummaryProjection> findHighEngagementProfiles(
            @Param("minScore") BigDecimal minScore, Pageable pageable
            );
}
