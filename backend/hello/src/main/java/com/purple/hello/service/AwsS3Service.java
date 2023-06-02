package com.purple.hello.service;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.purple.hello.dto.tool.AwsS3DTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface AwsS3Service {
    AwsS3DTO upload(MultipartFile multipartFile, String dirname) throws IOException;
    boolean removeDirectory(String directoryName) throws Exception;
}
