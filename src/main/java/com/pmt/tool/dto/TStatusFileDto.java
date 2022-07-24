package com.pmt.tool.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TStatusFileDto implements Serializable {
    private String nameStatusFile;
    private Date dateCompleted;
}
