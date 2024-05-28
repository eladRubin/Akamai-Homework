package com.akamai.homework.dao.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
@Table(name="USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column
    private String userName;

    @NonNull
    @Column
    private String hashedPassword;

    @OneToMany(mappedBy="user")
    private List<Post> posts;

    public User() {
    }
}
