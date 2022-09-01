package com.pmt.tool.controller;

import com.pmt.tool.component.ResponseObject;
import com.pmt.tool.dto.TStatusFileDto;
import com.pmt.tool.services.StatusFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/pmt/home/")
public class HomeController {

    private final StatusFileService statusFileService;

    @Autowired
    public HomeController(StatusFileService statusFileService) {
        this.statusFileService = statusFileService;
    }


    @GetMapping("")
    public String index(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        return ip;
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
