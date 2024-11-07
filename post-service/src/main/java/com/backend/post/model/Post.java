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
import java.util.ArrayList;
import java.util.List;
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

    @Column(name = "post_uuid", nullable = false, updatable = false, unique = true)
    @NotNull(message = "uuid is required")
    @Builder.Default
    private UUID postUUID = UUID.randomUUID();

    @Column(name = "author_uuid", nullable = false)
    @NotNull(message = "author uuid is required")
    private UUID authorUUID;

    @ElementCollection
    @CollectionTable(name = "uuid_of_users_who_liked_this_post", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "user_id")
    @Builder.Default
    private List<UUID> uuidsOfUsersWhoLikedThisPost = new ArrayList<>();

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

    @Column(name = "post_like_count", nullable = false)
    @Builder.Default
    private Integer likeCount = uuidsOfUsersWhoLikedThisPost.size();

    @ElementCollection
    @CollectionTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "tag")
    @Builder.Default
    private List<String> tags = new ArrayList<>();

}