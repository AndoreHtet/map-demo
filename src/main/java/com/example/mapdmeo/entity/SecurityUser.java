package com.example.mapdmeo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString
public class SecurityUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String name;
    private String password;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    private Set<Role> roles = new HashSet<>();


    public void addRole(Role role){
        role.getSecurityUsers().add(this);
        this.roles.add(role);
    }

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Guest guest;

    public SecurityUser(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
