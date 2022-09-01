package com.pmt.tool.services.serviceIml;

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
import com.pmt.tool.repositories.SoftwareTypeRepository;
import com.pmt.tool.services.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
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

@Service
@RequiredArgsConstructor
public class FileServiceIml implements FileService {
    private static final Path ROOT = MyRunner.resourceDirectory;
    private final FileRepository fileRepository;
    private final SoftwareRepository softwareRepository;
    private final SoftwareTypeRepository softwareTypeRepository;
    private final Converter<TFile, TFileDto> fileConverter;

    public Path createDirectory(String type) {
        try {
            return Files.createDirectory(ROOT.resolve(type));
        } catch (IOException ignored) {
            return ROOT.resolve(type);
        }
    }

    @Override
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
                                softwareType.setSoftware(tSoftware);
                                softwareTypeRepository.save(softwareType);
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
        }).toList();
    }

    @Override
    public Optional<TFileDto> getFile(String id) {
        Optional<TFile> file = Optional.empty();

        return file.map(files -> fileConverter.entityToDto(
                fileRepository.findById(id).get(),
                TFileDto.class)
        );
    }

    @Override
    public List<TFileDto> getAllFiles() {
        List<TFile> files = fileRepository.findAll();

        return fileConverter.entityToDto(files, TFileDto.class);
    }
}
