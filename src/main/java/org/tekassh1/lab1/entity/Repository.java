package org.tekassh1.lab1.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
@Entity
@Table(
        name = "repository",
        uniqueConstraints = {
                @UniqueConstraint(name = "repo_file_name", columnNames = {"name", "file_name"})
        }
)
public class Repository {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "source_code", columnDefinition = "TEXT")
    private String sourceCode;
}
