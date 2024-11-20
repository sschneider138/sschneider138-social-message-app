package com.backend.post.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "t_posts", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id", "postUUID"})
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

  @Column(name = "id", nullable = false, updatable = false, unique = true)
  @NotNull(message = "id is required")
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "post_sequence")
  @SequenceGenerator(name = "post_sequence", sequenceName = "post_sequence", allocationSize = 10)
  private Long id;

  @Column(name = "post_uuid", nullable = false, updatable = false, unique = true, columnDefinition = "CHAR(36)")
  @NotNull(message = "uuid is required")
  @Builder.Default
  private String postUUID = UUID.randomUUID().toString();

  @Column(name = "author_uuid", nullable = false)
  @NotNull(message = "author uuid is required")
  private String authorUUID;

  @Column(name = "author_username", nullable = false)
  @NotNull(message = "author username is required")
  private String authorUsername;

  @Column(name = "post_content", nullable = false, updatable = false)
  @NotBlank(message = "your post cannot be blank")
  @Size(max = 280)
  private String postContent;

  @Column(name = "date_posted", nullable = false, updatable = false)
  @CreationTimestamp
  @Builder.Default
  private final Instant datePosted = Instant.now();

  @Column(name = "share_count")
  @Builder.Default
  private Integer shareCount = 0;

  @Column(name = "like_count")
  @Builder.Default
  private Integer likeCount = 0;

  @ElementCollection
  @CollectionTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"))
  @Column(name = "tag")
  @Builder.Default
  private Set<String> tags = new HashSet<>();

}