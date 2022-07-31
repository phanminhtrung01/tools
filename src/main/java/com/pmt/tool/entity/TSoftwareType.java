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
    @Column(name = "id_type")
    private Long idType;
    private String extensionType;
    private String description;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_software", nullable = false)
    private TSoftware software;

}
