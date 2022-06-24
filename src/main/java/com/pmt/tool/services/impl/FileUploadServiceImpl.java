package com.pmt.tool.services.impl;

import com.pmt.tool.component.FileUploadConverter;
import com.pmt.tool.dto.FileUploadDto;
import com.pmt.tool.entity.DetailType;
import com.pmt.tool.entity.FileUpload;
import com.pmt.tool.repositories.DetailTypeRepository;
import com.pmt.tool.repositories.FileUploadRepository;
import com.pmt.tool.services.FileUploadService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private DetailTypeRepository detailTypeRepository;
    private FileUploadRepository fileUploadRepository;
    private FileUploadConverter fileUploadConverter;

    @Override
    public Optional<FileUploadDto> addFile(@NotNull FileUploadDto fileUploadDto) {
        Optional<FileUpload> fileUpload = fileUploadRepository.findById(fileUploadDto.getIdFile());

        if (fileUpload.isPresent()) {
            return Optional.empty();
        }

        fileUploadRepository.save(fileUploadConverter.dtoToEntity(fileUploadDto));

        return Optional.of(fileUploadDto);
    }

    @Override
    public Optional<FileUploadDto> removeFile(@NotNull FileUploadDto fileUploadDto) {
        Optional<FileUpload> fileUpload = fileUploadRepository.findById(fileUploadDto.getIdFile());

        return Optional.of((fileUpload.map(file -> {
            fileUploadRepository.delete(file);
            return fileUploadConverter.entityToDto(file);
        }).orElse(new FileUploadDto())));

    }

    @Override
    public Optional<FileUploadDto> updateFile(@NotNull FileUploadDto fileUploadDto, Long id) {
        Optional<FileUpload> fileUpload = fileUploadRepository.findById(fileUploadDto.getIdFile());

        return Optional.of((fileUpload.map(file -> {

            file.setSizeFile(fileUploadDto.getSizeFile());
            file.setData(fileUploadDto.getData());
            file.setNameFile(fileUploadDto.getNameFile());
            file.setPathFile(fileUploadDto.getPathFile());
            file.setTypeFile(fileUploadDto.getTypeFile());
            DetailType detailType = detailTypeRepository
                    .findById(fileUploadDto.getDetailTypeIdDetail()).orElse(new DetailType());
            file.setDetailType(detailType);

            fileUploadRepository.save(file);

            return fileUploadConverter.entityToDto(file);
        }).orElse(fileUploadDto)));

    }

    private boolean isFile(@NotNull MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());

        assert fileExtension != null;
        return Arrays.asList(new String[]{"docx", "xlsx", "doc", "xlx"}).contains(fileExtension.trim().toLowerCase());
    }

    public Optional<FileUploadDto> storeFile(MultipartFile file) {

        try {
            if (!isFile(file))
                throw new RuntimeException("Failed to store empty file!");

            float fileSize = file.getSize() / 100_000_000.0f;

            if (fileSize > 5.0f) {
                throw new RuntimeException("Failed to store empty file!");
            }

            FileUpload fileUpload = new FileUpload(
                    null,
                    file.getBytes(),
                    file.getContentType(),
                    (double) file.getSize(),
                    file.getOriginalFilename(),
                    "",
                    null
            );

            return Optional.ofNullable(fileUploadConverter.entityToDto(fileUpload));

        } catch (Exception e) {
            throw new RuntimeException("Failed to store empty file!");
        }

    }

    /*public byte[] readFileContent(String fileName) {
        try {
            Path file = st
        } catch (Exception e) {

        }
    }*/
}
