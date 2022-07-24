package com.pmt.tool.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TFileDto implements Serializable {
    private String idFile;
    private String nameFile;
    private String pathFile;
    private Double sizeFile;
    private String typeFile;
}
