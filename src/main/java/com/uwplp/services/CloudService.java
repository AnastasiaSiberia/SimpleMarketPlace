package com.uwplp.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class CloudService {

    private final static String PREFIX = "/home/anastasia/pseudoCloud/";
    public void saveFile(String username, String filename, MultipartFile file) throws IOException {
        String dirPath = PREFIX + username;
        File dir = new File(dirPath);
        if(!dir.exists()) {
            boolean state = dir.mkdir();
            if(!state) {
                throw new RuntimeException("Не удалось создать папку для пользователя " + username);
            }
        }
        String filePath = dirPath + "/" + filename;
        file.transferTo(new File(filePath));
    }

    public File getFile(String username, String filename) {
        return new File(PREFIX + username + "/" + filename);
    }
}
