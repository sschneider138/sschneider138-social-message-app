package com.messaging.app.backend.user;

import com.messaging.app.backend.post.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username", "email", "phone_number"})})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    private transient PasswordEncoder passwordEncoder;

    @Column(name = "date_joined", nullable = false, updatable = false)
    @CreationTimestamp
    @Builder.Default
    private final Instant dateJoined = Instant.now();

    @Column(name = "id", nullable = false, updatable = false, unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 10)
    private Long id;

    @Column(name = "hash", nullable = false, updatable = false, unique = true)
    @Builder.Default
    private UUID userUUID = UUID.randomUUID();

    @Column(name = "role", nullable = false, updatable = false)
    @NotNull(message = "role cannot be blank")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

    @NotBlank(message = "password cannot be blank")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters and include at least one uppercase letter, one lowercase letter, one digit, and one special character"
    )
    private String password;


    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "first name is required")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "last name is required")
    private String lastName;

    @Column(name = "username", nullable = false, unique = true)
    @NotBlank(message = "username is required")
    @Size(min = 3, max = 15, message = "username must be between 3 and 15 characters")
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "email is required")
    @Email(message = "please enter a valid email")
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "phone number should be valid and can include country code")
    private String phoneNumber;

    @Column(name = "top_interests", nullable = false)
    @Size(max = 5, message = "you may select up to 5 top interests")
    @ElementCollection
    @Builder.Default
    private List<String> topInterests = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_followers", joinColumns = @JoinColumn(name = "followed_user_id"), inverseJoinColumns = @JoinColumn(name = "following_user_id"))
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

    public long getMembershipLength() {
        return Duration.between(this.dateJoined, Instant.now()).toDays();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}