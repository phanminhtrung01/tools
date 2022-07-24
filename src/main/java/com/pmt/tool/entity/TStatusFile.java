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
@Table(name = "status_file")
public class TStatusFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status_file", nullable = false)
    private Long idStatusFile;
    @Column(nullable = false)
    private String nameStatusFile;
    @Column(nullable = false, columnDefinition = "datetime default now()")
    private Date dateCompleted;
}