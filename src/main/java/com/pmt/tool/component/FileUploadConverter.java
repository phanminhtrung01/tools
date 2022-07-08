package com.pmt.tool.component;

import com.pmt.tool.dto.FileUploadDto;
import com.pmt.tool.entity.Files;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileUploadConverter {

    //TODO-UPDATE------------------------------->Transient

    public FileUploadDto entityToDto(@NotNull Files files) {
        FileUploadDto fileUploadDto = new FileUploadDto();

        fileUploadDto.setIdFile(files.getIdFile());
        fileUploadDto.setData(files.getData());
        fileUploadDto.setNameFile(files.getNameFile());
        fileUploadDto.setPathFile(files.getPathFile());
        fileUploadDto.setSizeFile(files.getSizeFile());
        fileUploadDto.setTypeFile(files.getTypeFile());

        return fileUploadDto;
    }

    public List<FileUploadDto> entityToDto(@NotNull List<Files> files) {

        return files.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public Files dtoToEntity(@NotNull FileUploadDto fileUploadDto) {

        Files files = new Files();

        files.setIdFile(fileUploadDto.getIdFile());
        files.setData(fileUploadDto.getData());
        files.setNameFile(fileUploadDto.getNameFile());
        files.setPathFile(fileUploadDto.getPathFile());
        files.setSizeFile(fileUploadDto.getSizeFile());
        files.setTypeFile(fileUploadDto.getTypeFile());

        return files;
    }

    public List<Files> dtoToEntity(@NotNull List<FileUploadDto> fileUploadsDto) {
        return fileUploadsDto.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
