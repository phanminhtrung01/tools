package com.pmt.tool.services;

import com.pmt.tool.component.Converter;
import com.pmt.tool.component.MyRunner;
import com.pmt.tool.dto.TFileDto;
import com.pmt.tool.entity.TFile;
import com.pmt.tool.entity.TSoftware;
import com.pmt.tool.entity.TSoftwareType;
import com.pmt.tool.entity.TStatusFile;
import com.pmt.tool.enums.SearchType;
import com.pmt.tool.repositories.FileRepository;
import com.pmt.tool.repositories.SoftwareRepository;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileService {
    private static final Path ROOT = MyRunner.resourceDirectory;
    private final FileRepository fileRepository;
    private final SoftwareRepository softwareRepository;
    private final Converter<TFile, TFileDto> fileConverter;

    @Autowired
    public FileService(
            FileRepository fileRepository,
            SoftwareRepository softwareRepository,
            Converter<TFile, TFileDto> fileConverter) {
        this.fileRepository = fileRepository;
        this.softwareRepository = softwareRepository;
        this.fileConverter = fileConverter;
    }

    public Path createDirectory(String type) {
        try {
            return Files.createDirectory(ROOT.resolve(type));
        } catch (IOException ignored) {
            return ROOT.resolve(type);
        }
    }

    public List<Path> storedFile(
            @NotNull MultipartFile[] file,
            @NotNull String nameSoftware,
            @NotNull HttpServletRequest request) {

        return Arrays.stream(file).map(file1 -> {
            String fileName = StringUtils
                    .cleanPath(Objects.requireNonNull(file1.getOriginalFilename()));
            String fileExtension = FilenameUtils.getExtension(file1.getOriginalFilename());

            Optional<TSoftware> software = softwareRepository.findByNameSoftware(nameSoftware);

            software.ifPresent((tSoftware) -> {
                List<TSoftwareType> softwareTypeList = tSoftware.getSoftwareTypes();
                List<String> nameSoftwareType = softwareTypeList
                        .stream()
                        .map(TSoftwareType::getExtensionType)
                        .toList();

                if (!nameSoftwareType.contains(fileExtension)) {
                    try {
                        boolean check = false;
                        softwareTypeList = MyRunner.searchSoftwareType(fileExtension, SearchType.MOST);
                        for (TSoftwareType softwareType : softwareTypeList) {
                            if (softwareType.getDescription().contains(nameSoftware)) {
                                tSoftware.getSoftwareTypes().add(softwareType);
                                tSoftware.setSoftwareTypes(tSoftware.getSoftwareTypes());
                                //softwareType.setSoftware(tSoftware);
                                softwareRepository.save(tSoftware);
                                check = true;
                                break;
                            }
                        }
                        if (!check)
                            throw new RuntimeException("File format is not suitable!");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            Path path = createDirectory(fileExtension);
            String randomUUID = UUID.randomUUID().toString();
            String suffixUUID = randomUUID.split("-", 4)[3];
            double generatedTime = DateFormat
                    .getDateTimeInstance()
                    .getCalendar()
                    .getTimeInMillis() / 1000000000D;
            String replaceSuffixUUID = Long
                    .toHexString(Double.doubleToLongBits(generatedTime));
            String prefixFileName = randomUUID
                    .replaceAll(suffixUUID, replaceSuffixUUID);
            String generatedFileName = (prefixFileName + "." + fileExtension)
                    .replaceAll("-", "");
            Path destinationFilePath = path
                    .resolve(generatedFileName);

            if (!destinationFilePath
                    .getParent()
                    .equals(ROOT.resolve(fileExtension))) {
                throw new RuntimeException();
            }

            try (InputStream inputStream = file1.getInputStream()) {
                Files.copy(
                        inputStream,
                        destinationFilePath,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            TFile fileFound = new TFile();
            fileFound.setNameFile(fileName);
            try {
                fileFound.setData(file1.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileFound.setSizeFile(file1.getSize());
            fileFound.setTypeFile(file1.getContentType());

            TStatusFile statusFile = new TStatusFile();
            List<String> listString = Arrays
                    .stream(request.getRequestURI().split("/")).toList();
            statusFile.setNameStatusFile(listString.get(listString.size() - 1));
            statusFile.setDateCompleted(new Date());

            fileFound.setStatusFile(statusFile);
            fileRepository.save(fileFound);

            return destinationFilePath;
        }).collect(Collectors.toList());
    }

    public Optional<TFileDto> getFile(String id) {
        Optional<TFile> file = Optional.empty();

        return file.map(files -> fileConverter.entityToDto(
                fileRepository.findById(id).get(),
                TFileDto.class)
        );
    }

    public List<TFileDto> getAllFiles() {
        List<TFile> files = fileRepository.findAll();

        return fileConverter.entityToDto(files, TFileDto.class);
    }


}
