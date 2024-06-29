package com.oussema.bsn.file;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class FileUtils {
    public static byte[] readFileFromLocation(String fileUrl) {
        if(StringUtils.isBlank(fileUrl)){
            return null;
        }
        try {
            return Files.readAllBytes(new File(fileUrl).toPath());
        } catch (IOException e) {
           log.warn("Error reading file from location: {}", fileUrl);
        }
        return null;
    }
}
