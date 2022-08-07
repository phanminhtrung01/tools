package com.pmt.tool.dto;

import lombok.Data;

import java.io.Serializable;
import java.nio.file.Path;

@Data
public class TFileDto implements Serializable {
    private String nameFile;
    private Double sizeFile;
    private String typeFile;
    private Path pathFile;
}
