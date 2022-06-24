package com.pmt.tool.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "detail_type")
public class DetailType {
    @Id
    @Column(name = "id_detail")
    private Long idDetail;
    private String code;
    private Date dateCreated;
    private Date dateModified;

    @OneToOne
    @JoinColumn(name = "id_type")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private FileUpload fileUpload;

}
