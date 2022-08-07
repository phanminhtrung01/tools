package com.pmt.tool.controller;

import com.pmt.tool.dto.TFileDto;
import com.pmt.tool.entity.ResponseObject;
import com.pmt.tool.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*@CrossOrigin("http://localhost:8080")*/
@RestController
@RequestMapping("/api/pmt/file/")
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("upload")
    public ResponseEntity<ResponseObject> uploadFile(
            @RequestPart("file") MultipartFile[] file,
            HttpServletRequest request) {
        try {

            List<Path> pathList = fileService.storedFile(file, request);
            final int[] i = {0};
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseObject(
                            HttpStatus.OK.value(),
                            "Upload file succeed!",

                            Arrays.stream(file).map((file_) -> {
                                TFileDto fileDto = new TFileDto();
                                fileDto.setNameFile(file_.getOriginalFilename());
                                fileDto.setSizeFile((double) file_.getSize());
                                fileDto.setTypeFile(file_.getContentType());
                                fileDto.setPathFile(pathList.get(i[0]));
                                i[0]++;

                                return fileDto;
                            })
                    ));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseObject(
                            HttpStatus.EXPECTATION_FAILED.value(),
                            "Could not upload the file: "
                                    + file[0].getOriginalFilename() + "!\n" + e,
                            null
                    ));
        }
    }

    @ResponseBody
    @GetMapping("files")
    public ResponseEntity<ResponseObject> getListFiles() {
        List<TFileDto> files = fileService.getAllFiles();

        return files.size() > 0 ?
                ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(
                                HttpStatus.OK.value(),
                                "Query user successfully",
                                files)) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseObject(
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                1));
    }

    @GetMapping("files/{id}")
    public ResponseEntity<ResponseObject> getFile(@PathVariable String id) {
        Optional<TFileDto> file = fileService.getFile(id);

        return file
                .map(filesDto -> ResponseEntity.status(HttpStatus.OK)
                        .header(
                                HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\""
                                        + filesDto.getNameFile() + "\"")
                        .body(new ResponseObject(
                                HttpStatus.OK.value(),
                                "Download File Succeed!",
                                filesDto))
                )
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.EXPECTATION_FAILED)
                        .body(new ResponseObject(
                                HttpStatus.EXPECTATION_FAILED.value(),
                                "Download File Failed!",
                                null))
                );
    }
}
