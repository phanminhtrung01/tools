package com.pmt.tool.services;

import com.aspose.words.Document;
import com.aspose.words.ImportFormatMode;
import com.pmt.tool.component.Converter;
import com.pmt.tool.dto.TFileDto;
import com.pmt.tool.entity.TFile;
import com.pmt.tool.entity.TStatusFile;
import com.pmt.tool.repositories.FileRepository;
import com.pmt.tool.repositories.StatusFileRepository;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.util.*;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final StatusFileRepository statusFileRepository;
    private final Converter<TFile, TFileDto> fileConverter;

    private final Path root = Paths.get("uploads");

    @Autowired
    public FileService(
            FileRepository fileRepository,
            StatusFileRepository statusFileRepository,
            Converter<TFile, TFileDto> fileConverter) {
        this.fileRepository = fileRepository;
        this.statusFileRepository = statusFileRepository;
        this.fileConverter = fileConverter;

        try {
            Files.createDirectory(root);
        } catch (IOException ignored) {
        }
    }

    public File storedFile(
            MultipartFile @NotNull [] file,
            @NotNull HttpServletRequest request) throws Exception {

        Document output = new Document();
        // Remove all content from the destination document before appending.
        output.removeAllChildren();

        Arrays.stream(file).forEach(file1 -> {
            String fileName = StringUtils
                    .cleanPath(Objects.requireNonNull(file1.getOriginalFilename()));

            String fileExtension = FilenameUtils.getExtension(file1.getOriginalFilename());
            String generatedFileName = Long.toHexString(
                    DateFormat.getDateTimeInstance().getCalendar().getTimeInMillis()
                            + Double.doubleToLongBits(Math.random())) + "." + fileExtension;

            Path destinationFilePath = this.root
                    .resolve(Paths.get(generatedFileName))
                    .normalize()
                    .toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.root.toAbsolutePath())) {
                throw new RuntimeException("");
            }

            Document input;
            try (InputStream inputStream = file1.getInputStream()) {
                Files.copy(
                        inputStream,
                        destinationFilePath,
                        StandardCopyOption.REPLACE_EXISTING);

                input = new Document(destinationFilePath.toString());

                Files.delete(destinationFilePath);
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
            statusFileRepository.save(statusFile);

            fileFound.setStatusFile(statusFile);

            fileRepository.save(fileFound);

            // Append the source document to the end of the destination document.
            output.appendDocument(input, ImportFormatMode.KEEP_SOURCE_FORMATTING);
        });

        String fileOutput = "output.pdf";
        Path destinationFileOutput = this.root
                .resolve(Paths.get(fileOutput))
                .normalize()
                .toAbsolutePath();
        output.save(destinationFileOutput.toString());

        return new File(destinationFileOutput.toUri());
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
