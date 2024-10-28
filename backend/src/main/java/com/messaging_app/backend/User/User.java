package com.messaging_app.backend.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @UniqueConstraint(columnNames = "username, email, phone_number") })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Column(name = "id")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
  @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 10)
  private Long id;

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

  @Column(name = "top_interests")
  @Size(max = 5, message = "you may select up to 5 top interests")
  @ElementCollection
  private List<String> topInterests;

  @Column(name = "date_joined", updatable = false)
  @CreationTimestamp
  private LocalDate dateJoined;

  public int getMembershipLength() {
    return Period.between(this.dateJoined, LocalDate.now()).getDays();
  }

}