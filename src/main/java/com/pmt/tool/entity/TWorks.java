package com.pmt.tool.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "works")
public class TWorks implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_works", nullable = false)
    private Long idWorks;
    @Column(name = "date_created", columnDefinition = "datetime default now()")
    private Date startDate;
    @Column(name = "date_finished", nullable = false)
    private Date endDate;

    @OneToOne
    @JoinColumn(name = "id_type_work", nullable = false)
    private TTypeWork typeWork;

    @OneToOne
    @JoinColumn(name = "id_file", nullable = false)
    private TFile files;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "works")
    @ToString.Exclude
    private Set<TUser> users = new HashSet<>();

}
