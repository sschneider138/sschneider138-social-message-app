package com.messaging.app.backend.Post;

import com.messaging.app.backend.Tags.Tag;
import com.messaging.app.backend.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "posts", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Column(name = "id", nullable = false, updatable = false, unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_sequence")
    @SequenceGenerator(name = "post_sequence", sequenceName = "post_sequence", allocationSize = 10)
    private Long id;

    @Column(name = "hash", nullable = false, updatable = false, unique = true)
    @Builder.Default
    private final UUID postUUID = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User author;

    @ManyToMany
    @JoinTable(name = "posts_liked_by_users", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    private List<User> likedByUsers = new ArrayList<>();

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

    @ManyToMany
    @JoinTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @Builder.Default
    private Set<Tag> tags = new HashSet<>();

}
