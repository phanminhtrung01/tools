package com.pmt.tool.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "detail_work")
public class TDetailWork {
    @Id
    @Column(name = "id_user")
    private Long idUser;
    @PrimaryKeyJoinColumn(name = "id_work")
    @Column(name = "id_work")
    private Long idWork;
    private String description;

    @OneToOne
    @JoinColumn(name = "id_detail_type", nullable = false)
    private TDetailType detailType;

}
