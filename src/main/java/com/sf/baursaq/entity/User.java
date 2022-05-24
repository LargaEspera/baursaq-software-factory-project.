package com.sf.baursaq.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="usr")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="password2")
    private String password2;
    private String name;
    private String surname;
    @OneToMany (cascade=CascadeType.ALL)
    private List<Recipe> recipes;
}
