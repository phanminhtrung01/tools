package com.pmt.tool.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FilesDto implements Serializable {

    //TODO-UPDATE------------------------------->Transient

    private final Long idFile;
    private final Byte[] data;
    private final String nameFile;
    private final String pathFile;
    private final Double sizeFile;
    private final String typeFile;
    private final Long statusFileIdStatusFile;
    private final String statusFileNameStatusFile;
    private final Date statusFileDateCompleted;
}
