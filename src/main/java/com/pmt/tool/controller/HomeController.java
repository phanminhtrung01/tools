package com.pmt.tool.controller;

import com.pmt.tool.component.ResponseObject;
import com.pmt.tool.dto.TStatusFileDto;
import com.pmt.tool.services.StatusFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pmt/home/")
public class HomeController {

    private final StatusFileService statusFileService;

    @Autowired
    public HomeController(StatusFileService statusFileService) {
        this.statusFileService = statusFileService;
    }

    @PostMapping("i_sf")
    public ResponseEntity<ResponseObject> insertStatusFile(@RequestBody TStatusFileDto status) {
        return statusFileService.insert_sf(status)
                .map(statusFileDto -> ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(
                                HttpStatus.OK.value(),
                                "Query user successfully",
                                statusFileDto)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseObject(
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                null)));
    }

}
