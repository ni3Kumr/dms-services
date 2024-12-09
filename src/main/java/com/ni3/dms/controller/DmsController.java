package com.ni3.dms.controller;

import com.ni3.dms.service.DmsS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/dms")
public class DmsController {
    @Autowired
    private DmsS3Service dmsS3Service;
    @PostMapping
    ResponseEntity<String> uploadFile(@RequestParam (value ="file")MultipartFile file){
        return new ResponseEntity<>(dmsS3Service.uploadFile(file), HttpStatus.OK);

    }

}
