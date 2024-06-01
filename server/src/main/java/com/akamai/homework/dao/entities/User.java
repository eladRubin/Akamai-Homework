package com.akamai.homework.dao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
@Table(name="USERS", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(max = 20)
    @Column
    private String userName;

    @NonNull
    @Size(max = 50)
    @Column
    private String email;

    @NonNull
    @Size(max = 120)
    @Column
    private String hashedPassword;

    @OneToMany(mappedBy="user")
    private List<Post> posts;

    public User() {
    }
}
