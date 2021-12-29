package com.example.java_labs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    private String username;
    private String salt;
    private String hash;
    private Role role;

    public User(UUID id, String username, String salt, String hash, Role role) {
        super(id);
        this.username = username;
        this.salt = salt;
        this.hash = hash;
        this.role = role;
    }
}
