package com.sudhanshu.profileservice.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;

/**
 * Core Entity for user profiles
 * @author Sudhanshu
 * */
@Entity
@Table(name = "user_profiles")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfiles {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false, unique = true, length = 100)
	private String userId;
	
	@Column(nullable = false, length = 150)
	private String name;
	
	@Column(nullable = false, length = 200)
	private String email;
	
	@Column(length = 100)
	private String country;
	
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "last_seen")
    private LocalDateTime lastSeen;
    
    // Engagement Metrics
    @Column(name = "total_watch_time_min", precision = 10, scale = 2)
    private BigDecimal totalWatchTimeMin = BigDecimal.ZERO;
    
    @Column(name = "total_sessions")
    private Integer totalSessions = 0;
    
    @Column(name = "avg_completion_rate", precision = 5, scale = 2)
    private BigDecimal avgCompletionRate = BigDecimal.ZERO;
    
    @Column(name = "avg_session_duration_min", precision = 5, scale = 2)
    private BigDecimal avgSessionDurationMin = BigDecimal.ZERO;
    
    // Behavioral Scores (0.0-1.0)
    @Column(name = "engagement_score", precision = 5, scale = 3)
    private BigDecimal engagementScore = BigDecimal.ZERO;
    
    @Column(name = "churn_risk_score", precision = 5, scale = 3)
    private BigDecimal churnRiskScore = BigDecimal.valueOf(0.5);
    
    // JSON Fields 
    @Column(name = "preferred_genres", columnDefinition = "json")
    private String preferredGenresJson;
    
    @Column(name = "preferred_devices", columnDefinition = "json")
    private String preferredDevicesJson;
    
    @Column(name = "preferred_quality", columnDefinition = "json")
    private String preferredQualityJson;
    
    @Column(name = "top_content_ids", columnDefinition = "json")
    private String topContentIdsJson;
    
    // Activity Patterns
    @Column(name = "binge_streak_days")
    private Integer bingeStreakDays = 0;
    
    @Column(name = "last_binge_date")
    private LocalDate lastBingeDate;
    
    @Column(name = "peak_activity_hour")
    private Integer peakActivityHour;
    
    @Column(name = "subscription_tier")
    private String subscriptionTier;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Version
    private Integer version;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(LocalDateTime lastSeen) {
		this.lastSeen = lastSeen;
	}

	public BigDecimal getTotalWatchTimeMin() {
		return totalWatchTimeMin;
	}

	public void setTotalWatchTimeMin(BigDecimal totalWatchTimeMin) {
		this.totalWatchTimeMin = totalWatchTimeMin;
	}

	public Integer getTotalSessions() {
		return totalSessions;
	}

	public void setTotalSessions(Integer totalSessions) {
		this.totalSessions = totalSessions;
	}

	public BigDecimal getAvgCompletionRate() {
		return avgCompletionRate;
	}

	public void setAvgCompletionRate(BigDecimal avgCompletionRate) {
		this.avgCompletionRate = avgCompletionRate;
	}

	public BigDecimal getAvgSessionDurationMin() {
		return avgSessionDurationMin;
	}

	public void setAvgSessionDurationMin(BigDecimal avgSessionDurationMin) {
		this.avgSessionDurationMin = avgSessionDurationMin;
	}

	public BigDecimal getEngagementScore() {
		return engagementScore;
	}

	public void setEngagementScore(BigDecimal engagementScore) {
		this.engagementScore = engagementScore;
	}

	public BigDecimal getChurnRiskScore() {
		return churnRiskScore;
	}

	public void setChurnRiskScore(BigDecimal churnRiskScore) {
		this.churnRiskScore = churnRiskScore;
	}

	public String getPreferredGenresJson() {
		return preferredGenresJson;
	}

	public void setPreferredGenresJson(String preferredGenresJson) {
		this.preferredGenresJson = preferredGenresJson;
	}

	public String getPreferredDevicesJson() {
		return preferredDevicesJson;
	}

	public void setPreferredDevicesJson(String preferredDevicesJson) {
		this.preferredDevicesJson = preferredDevicesJson;
	}

	public String getPreferredQualityJson() {
		return preferredQualityJson;
	}

	public void setPreferredQualityJson(String preferredQualityJson) {
		this.preferredQualityJson = preferredQualityJson;
	}

	public String getTopContentIdsJson() {
		return topContentIdsJson;
	}

	public void setTopContentIdsJson(String topContentIdsJson) {
		this.topContentIdsJson = topContentIdsJson;
	}

	public Integer getBingeStreakDays() {
		return bingeStreakDays;
	}

	public void setBingeStreakDays(Integer bingeStreakDays) {
		this.bingeStreakDays = bingeStreakDays;
	}

	public LocalDate getLastBingeDate() {
		return lastBingeDate;
	}

	public void setLastBingeDate(LocalDate lastBingeDate) {
		this.lastBingeDate = lastBingeDate;
	}

	public Integer getPeakActivityHour() {
		return peakActivityHour;
	}

	public void setPeakActivityHour(Integer peakActivityHour) {
		this.peakActivityHour = peakActivityHour;
	}

	public String getSubscriptionTier() {
		return subscriptionTier;
	}

	public void setSubscriptionTier(String subscriptionTier) {
		this.subscriptionTier = subscriptionTier;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}

	public UserProfiles() {
		super();
	}
    
    @Transient
    public List<String> getPreferredGenres() throws JsonMappingException, JsonProcessingException {
        return preferredGenresJson != null ? 
        		OBJECT_MAPPER.readValue(preferredGenresJson, new TypeReference<List<String>>(){}) : 
            Collections.emptyList();
    }
    
    public void setPreferredGenres(List<String> genres) throws JsonProcessingException {
        this.preferredGenresJson = OBJECT_MAPPER.writeValueAsString(genres);
    }
    
}
