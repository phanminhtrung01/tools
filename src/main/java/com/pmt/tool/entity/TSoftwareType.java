package com.pmt.tool.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "software_type")
public class TSoftwareType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_software")
    private Long idSoftware;
    @Column(nullable = false)
    private String nameSoftware;
}