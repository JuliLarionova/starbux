package com.bstlr.starbux.web.dto;

import org.springframework.util.ResourceUtils;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class BaseDtoTest {

    protected String testReadFile(String resource) throws IOException {
        String output = readFile("classpath:cases/"+resource);
        return  StringUtils.normalizeSpace(output.replaceAll("[\\s]",""));
    }

    protected String readFile(String path) throws IOException {
        File file = ResourceUtils.getFile(path);
        return new String(Files.readAllBytes(file.toPath()));
    }
}
