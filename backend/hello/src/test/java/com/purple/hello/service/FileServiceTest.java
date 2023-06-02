package com.purple.hello.service;

import com.purple.hello.service.impl.FileServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
class FileServiceTest {
    static private FileServiceImpl fileService;
    @BeforeAll
    static void beforeAll() {

    }

    @BeforeEach
    void setUp() {
        fileService = new FileServiceImpl();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void compareExtensions_IsTrue() throws Exception {
        // given

        // when
        boolean whenResult = fileService.compareExtensions("file1",
                new String[]{"file1", "file2"});

        // then
        assertTrue(whenResult);
    }

    @Test
    void compareExtensions_IsFalse() throws Exception {
        // given

        // when
        boolean whenResult = fileService.compareExtensions("file",
                new String[]{"file1", "file2"});

        // then
        assertFalse(whenResult);
    }

    @Test
    void getExtension() {
        // given

        // when
        String whenResult = fileService.getExtension("filename.txt");

        // then
        assertEquals("txt", whenResult);
    }

    @Test
    void getExtension_NoExtension() {
        // given

        // when
        String whenResult = fileService.getExtension("filename");
        // then
        assertEquals(null, whenResult);
    }
}