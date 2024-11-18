package com.backend.email.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "t_emails", schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

  @Column(name = "id", nullable = false, updatable = false, unique = true)
  @NotNull(message = "id is required")
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "email_sequence")
  @SequenceGenerator(name = "email_sequence", sequenceName = "email_sequence", allocationSize = 10)
  private Long id;

  @Column(name = "email_uuid", nullable = false, updatable = false, unique = true, columnDefinition = "CHAR(36)")
  @NotNull(message = "uuid is required")
  @Builder.Default
  private final String emailUUID = UUID.randomUUID().toString();

  @Column(name = "email_recipient", nullable = false)
  @NotBlank(message = "recipient is required")
  @Email
  private String recipient;

  @Column(name = "email_subject", nullable = false)
  @NotBlank(message = "subject is required")
  private String subject;

  @Column(name = "email_body", nullable = false)
  @NotBlank(message = "body is required")
  private String body;

  @Column(name = "date_sent", nullable = false, updatable = false)
  @NotNull(message = "date sent is required")
  @CreationTimestamp
  @Builder.Default
  private final Instant dateSent = Instant.now();

}
