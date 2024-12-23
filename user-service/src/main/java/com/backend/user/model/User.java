package com.backend.user.model;

import com.backend.user.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "t_users", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id", "userUUID", "username", "email", "phone_number"})})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Column(name = "id", nullable = false, updatable = false, unique = true)
    @NotNull(message = "id is required")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 10)
    private Long id;

    @Column(name = "user_uuid", nullable = false, updatable = false, unique = true, columnDefinition = "CHAR(36)")
    @NotNull(message = "uuid is required")
    @Builder.Default
    private final String userUUID = UUID.randomUUID().toString();

    @Column(name = "role", nullable = false, updatable = false)
    @NotNull(message = "role cannot be blank")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

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
    @NotBlank(message = "phone number is required")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "phone number should be valid and can include country code")
    private String phoneNumber;

    @NotBlank(message = "password cannot be blank")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Password must be at least 8 characters and include at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;

    @Column(name = "top_interests", nullable = false)
    @Size(max = 5, message = "you may select up to 5 top interests")
    @ElementCollection
    @Builder.Default
    private List<String> topInterests = new ArrayList<>();

    @ElementCollection
    @Column(name = "followers", nullable = false)
    private List<User> followers;

    @ElementCollection
    @Column(name = "following", nullable = false)
    private List<User> following;

    @ElementCollection
    @Builder.Default
    private List<UUID> likedPostIds = new ArrayList<>();

    @ElementCollection
    @Builder.Default
    private List<UUID> likedByUserIds = new ArrayList<>();

    @Column(name = "date_joined", nullable = false, updatable = false)
    @NotNull(message = "date joined is required")
    @CreationTimestamp
    @Builder.Default
    private final Instant dateJoined = Instant.now();

    public long getMembershipLength() {
        return Duration.between(this.dateJoined, Instant.now()).toDays();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

}
