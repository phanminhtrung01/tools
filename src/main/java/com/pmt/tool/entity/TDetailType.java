package com.pmt.tool.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "detail_type")
public class TDetailType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detail_type")
    private Long idDetailType;
    @Column(nullable = false)
    private String nameDetailType;
    private String description;

    @OneToOne
    @JoinColumn(name = "id_software", nullable = false)
    private TSoftwareType softwareType;

}
