package com.ni3.dms.service;

//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ni3.dms.exception.ImageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class DmsS3ServiceImpl implements DmsS3Service {

    @Autowired
    private S3Client client;

    @Value("${app.s3.bucket}")
    private String bucketName;
    @Override
    public String uploadFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new ImageNotFoundException("Image Not Found");
        }

        // Get the original file name and generate a new one using UUID
        String actualFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + actualFileName.substring(actualFileName.lastIndexOf("."));

        try (InputStream inputStream = file.getInputStream()) {

            // Create a PutObjectRequest
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            // Upload the file to S3
            PutObjectResponse response = client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));

            // Return the file name if the upload is successful
            return "file is uploaded SuccessFully"+fileName;

        } catch (IOException e) {
            // Handle the exception in case of failure during file upload
            throw new ImageNotFoundException("Error in uploading file: " + e.getMessage());
        }
    }
//    @Override
//    public String uploadFile(MultipartFile file) {
//
//        if(file == null){
//            throw new ImageNotFoundException("Image Not Found");
//        }
//
//        String actualFileName = file.getOriginalFilename();
//        ObjectMetadata metaData = new ObjectMetadata();
//        metaData.setContentLength(file.getSize());
//        String fileName=  UUID.randomUUID().toString()+actualFileName.substring(actualFileName.lastIndexOf("."));
//        try {
//            client.putObject(new PutObjectRequest(bucketName,fileName,file.getInputStream(),metaData));
//            return fileName;
//        } catch (IOException e) {
//            throw new ImageNotFoundException("error in uploading file"+e.getMessage());
//        }
//
//
//    }

}
