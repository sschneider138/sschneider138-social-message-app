package com.messaging.app.backend.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.messaging.app.backend.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  private final UUID postUUID = UUID.randomUUID();

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User author;

  @ManyToMany
  @JoinTable(name = "posts_liked_by_users", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
  @Builder.Default
  private List<User> likedByUsers = new ArrayList<>();
}
