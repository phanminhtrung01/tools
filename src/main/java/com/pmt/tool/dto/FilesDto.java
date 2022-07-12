package com.pmt.tool.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilesDto implements Serializable {
    private Byte[] data;
    private String nameFile;
    private Double sizeFile;
    private StatusFileDto statusFile;
}
