package com.pmt.tool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileUploadDto implements Serializable {
    private Long idFile;
    private byte[] data;
    private String typeFile;
    private Double sizeFile;
    private String pathFile;
    private String nameFile;
    private Long detailTypeIdDetail;
}