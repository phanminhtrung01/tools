package com.pmt.tool.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "software")
public class TSoftware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_software")
    private Long idSoftware;
    @Column(nullable = false)
    private String nameSoftware;

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "software", orphanRemoval = true)
    @ToString.Exclude
    private List<TSoftwareType> softwareTypes = new ArrayList<>();

}