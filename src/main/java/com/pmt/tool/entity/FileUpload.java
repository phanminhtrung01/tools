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
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_file")
    private Long idFile;
    private byte[] data;
    private String typeFile;
    private Double sizeFile;
    private String nameFile;
    private String pathFile;

    @OneToOne
    @JoinColumn(name = "id_detail")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private DetailType detailType;
}
