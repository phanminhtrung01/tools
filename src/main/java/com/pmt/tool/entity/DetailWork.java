package com.pmt.tool.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "detail_work")
public class DetailWork implements Serializable {

    @Id
    @Column(name = "id_user")
    private Long idUser;
    @Id
    @Column(name = "id_work")
    private Long idWork;
    private String description;

    @OneToOne
    @JoinColumn(name = "id_detail_type", nullable = false)
    private DetailType detailType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_work", nullable = false)
    private Works works;

}
