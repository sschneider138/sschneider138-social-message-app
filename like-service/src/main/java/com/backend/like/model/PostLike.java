package com.backend.like.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_post_like", uniqueConstraints = @UniqueConstraint(columnNames = { "post_uuid", "user_uuid" }))
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostLike {

  @Column(name = "id", nullable = false, updatable = false, unique = true)
  @NotNull(message = "id is required")
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "like_sequence")
  @SequenceGenerator(name = "like_sequence", sequenceName = "like_sequence", allocationSize = 10)
  private Long id;

  @Column(name = "post_uuid", nullable = false, updatable = false, unique = true, columnDefinition = "CHAR(36)")
  @NotNull(message = "uuid is required")
  private String postUUID;

  @Column(name = "user_uuid", nullable = false, updatable = false, unique = true, columnDefinition = "CHAR(36)")
  @NotNull(message = "uuid is required")
  private String userUUID;

  @Column(name = "liked_at_timestamp", nullable = false, updatable = false)
  @Builder.Default
  private Instant likedAt = Instant.now();

}
