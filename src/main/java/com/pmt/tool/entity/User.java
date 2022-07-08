package com.pmt.tool.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;
    @Column(nullable = false, unique = true)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean gender;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    @ToString.Exclude
    private List<HandleType> handleTypeSet = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    @ToString.Exclude
    private Set<DetailWork> detailWorkSet = new HashSet<>();

    /*@ManyToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JoinTable(
            name = "detail_work",
            joinColumns = {@JoinColumn(name = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_work")}
    )
    @ToString.Exclude
    private Set<Works> worksSet = new HashSet<>();*/

}
