package com.pmt.tool.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "handle_type")
public class HandleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_handle", nullable = false)
    private Long idHandle;
    @Column(nullable = false)
    private String nameHandle;
    private String descriptionHandle;

    @OneToOne
    @JoinColumn(name = "id_detail_type", nullable = false)
    private DetailType idDetailType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creator_id", nullable = false)
    private User user;

}
