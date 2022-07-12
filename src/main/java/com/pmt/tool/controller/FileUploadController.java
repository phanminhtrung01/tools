/*
package com.pmt.tool.controller;

import com.pmt.tool.entity.ResponseObject;
import com.pmt.tool.services.impl.FileUploadServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/pmt/file/")
public class FileUploadController {

    private FileUploadServiceImpl fileUploadService;

    */
/*@GetMapping("")
    public List<FileUploadDto> findAll() {

        List<FileUpload> file

        return fileUploadConverter.entityToDto(fileUploads);
    }*//*


    @PostMapping("upload")
    public ResponseEntity<ResponseObject> uploadFile(
            @RequestParam("file-upload") MultipartFile file,
            @RequestParam("file") FileUploadDto fileUploadDto) {

        Optional<FileUploadDto> fileUpload = fileUploadService.storeFile(file);

        return fileUpload.map(fileUploadDto1 -> {

            String fileOpenUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/pmt/file/openFile/")
                    .path(fileUploadDto1.getIdFile().toString())
                    .toUriString();

            fileUploadDto1.setPathFile(fileOpenUri);
            fileUploadDto1.setDetailTypeIdDetail(fileUploadDto.getDetailTypeIdDetail());
            fileUploadService.addFile(fileUploadDto1);
            return ResponseEntity.ok(
                    new ResponseObject(200, "Upload succeed file!", fileUploadDto1));

        }).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(300, "Upload failed file!", new FileUploadDto())));

    }

}
*/
