package com.messaging.app.backend.User;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.messaging.app.backend.Post.Post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "username", "email", "phone_number" }) })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
  @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 10)
  private Long id;

  @Column(name = "first_name", nullable = false, updatable = true, unique = false)
  @NotBlank(message = "first name is required")
  private String firstName;

  @Column(name = "last_name", nullable = false, updatable = true, unique = false)
  @NotBlank(message = "last name is required")
  private String lastName;

  @Column(name = "username", nullable = false, updatable = true, unique = true)
  @NotBlank(message = "username is required")
  @Size(min = 3, max = 15, message = "username must be between 3 and 15 characters")
  private String username;

  @Column(name = "email", nullable = false, updatable = true, unique = true)
  @NotBlank(message = "email is required")
  @Email(message = "please enter a valid email")
  private String email;

  @Column(name = "phone_number", nullable = false, updatable = true, unique = true)
  @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "phone number should be valid and can include country code")
  private String phoneNumber;

  @Column(name = "top_interests", nullable = false, updatable = true, unique = false)
  @Size(max = 5, message = "you may select up to 5 top interests")
  @ElementCollection
  @Builder.Default
  private List<String> topInterests = new ArrayList<>();

  @ManyToMany
  @JoinTable(name = "user_followers", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "follower_id"))
  @Builder.Default
  private List<User> followers = new ArrayList<>();

  @ManyToMany(mappedBy = "followers")
  @Builder.Default
  private List<User> following = new ArrayList<>();

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<Post> postsByUser = new ArrayList<>();

  @ManyToMany
  @JoinTable(name = "posts_liked_by_users", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "post_id"))
  @Builder.Default
  private List<Post> likedPosts = new ArrayList<>();

  @Column(name = "date_joined", nullable = false, updatable = false, unique = false)
  @CreationTimestamp
  @Builder.Default
  private final Instant dateJoined = Instant.now();

  public long getMembershipLength() {
    return Duration.between(this.dateJoined, Instant.now()).toDays();
  }

}