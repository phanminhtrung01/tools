package com.pmt.tool.services;

import com.pmt.tool.dto.TFileDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface FileService {


    List<Path> storedFile(
            @NotNull MultipartFile[] file,
            @NotNull String nameSoftware,
            @NotNull HttpServletRequest request);

    Optional<TFileDto> getFile(String id);

    List<TFileDto> getAllFiles();
}
