package com.uwplp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class CloudService {

    private final static String PREFIX = "/home/anastasia/pseudoCloud/";
    private final static Logger log = LoggerFactory.getLogger(CloudService.class);
    public void saveFile(String username, String filename, MultipartFile file) throws IOException {
        //create directory if it doesn't exist !!!
        String filePath = PREFIX + username + "/" + filename;
        file.transferTo(new File(filePath));
    }

    public File getFile(String username, String filename) {
        File file = new File(PREFIX + username + "/" + filename);
        return file;
    }
}
