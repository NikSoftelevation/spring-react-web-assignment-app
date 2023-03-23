package org.coderscampus.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter
@Setter
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(optional = false)
    private User user;
    private String authority;

    public Authority() {
    }

    public Authority(String authority) {
        this.authority = authority;
    }
}