package com.pmt.tool.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilesDto implements Serializable {
    private final Byte[] data;
    private final String nameFile;
    private final Double sizeFile;
    private final StatusFileDto statusFile;
}
