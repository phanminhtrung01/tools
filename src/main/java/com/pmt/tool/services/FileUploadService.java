package com.pmt.tool.services;

import com.pmt.tool.dto.FileUploadDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface FileUploadService {

    Optional<FileUploadDto> addFile(@NotNull FileUploadDto fileUploadDto);

    Optional<FileUploadDto> removeFile(@NotNull FileUploadDto fileUploadDto);

    Optional<FileUploadDto> updateFile(@NotNull FileUploadDto fileUploadDto, Long id);

    Optional<FileUploadDto> storeFile(MultipartFile file);

}
