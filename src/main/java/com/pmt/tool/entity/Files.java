package com.pmt.tool.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "file")
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_file")
    private Long idFile;
    @Column(nullable = false, columnDefinition = "mediumblob")
    private Byte[] data;
    @Column(nullable = false)
    private String nameFile;
    @Column(nullable = false)
    private String pathFile;
    @Column(nullable = false, columnDefinition = "double default 0.0")
    private Double sizeFile;
    @Column(nullable = false)
    private String typeFile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_status", nullable = false)
    private StatusFile statusFile;
}
