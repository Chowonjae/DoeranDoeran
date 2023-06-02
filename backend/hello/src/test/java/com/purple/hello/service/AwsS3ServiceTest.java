package com.purple.hello.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.purple.hello.dto.tool.AwsS3DTO;
import com.purple.hello.service.impl.AwsS3ServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class AwsS3ServiceTest {
    static private AwsS3ServiceImpl awsS3Service;
    static private AmazonS3 amazonS3;
    static private AwsS3ServiceImpl mockAwsS3Service;
    @BeforeAll
    static void beforeAll(){

    }
    @BeforeEach
    void setUp() {
        amazonS3 = Mockito.mock(AmazonS3.class);
        mockAwsS3Service = Mockito.mock(AwsS3ServiceImpl.class);

        awsS3Service = new AwsS3ServiceImpl(amazonS3);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void upload() throws IOException {
        // init
        String initURL = "https://www.example.com";
        String initFileName = "test.txt";
        String initDirName = "test_dirName";

        ReflectionTestUtils.setField(awsS3Service, "bucket", "new-bucket");
        // given
        MockMultipartFile file= new MockMultipartFile("file", initFileName, "text/plain",
                "Hello, World!".getBytes());

        Mockito.when(amazonS3.getUrl(any(String.class), any(String.class)))
                .thenReturn(new URL(initURL));

        // when
        AwsS3DTO whenResult = awsS3Service.upload(file, initDirName);

        // then
        assertEquals(whenResult.getPath(), initURL);
        assertEquals(whenResult.getKey().split("/")[0], initDirName);
        assertEquals(whenResult.getKey().substring(whenResult.getKey().length() - initFileName.length()),
                initFileName);
    }

    @Test
    void removeDirectory() throws Exception {

    }
}