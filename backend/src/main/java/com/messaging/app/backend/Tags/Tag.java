package com.messaging.app.backend.Tags;

import com.messaging.app.backend.Post.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_sequence")
    @SequenceGenerator(name = "tag_sequence", sequenceName = "tag_sequence", allocationSize = 10)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Tag name cannot be blank")
    @Size(max = 50, message = "tag name should not exceed 50 characters")
    private String name;

    @ManyToMany(mappedBy = "tags")
    @Builder.Default
    private Set<Post> posts = new HashSet<>();
}
