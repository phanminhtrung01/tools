package com.pmt.tool.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "file")
public class TFile {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id_file")
    private String idFile;
    @Lob
    @Column(nullable = false, columnDefinition = "mediumblob")
    private byte[] data;
    @Column(nullable = false)
    private String nameFile;
    @Column(nullable = false)
    private Long sizeFile;
    @Column(nullable = false)
    private String typeFile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_status", nullable = false)
    private TStatusFile statusFile;
}
