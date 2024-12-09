package com.ni3.dms.service;

import org.springframework.web.multipart.MultipartFile;

public interface DmsS3Service {

    public String uploadFile(MultipartFile file);

}
