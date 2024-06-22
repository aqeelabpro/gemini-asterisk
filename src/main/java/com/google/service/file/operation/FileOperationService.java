package com.aws.service.file.operation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class FileOperationService {

    public void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.delete()) {
                throw new IOException("Failed to delete file: " + filePath);
            }
            log.info("File deleted successfully: {}", filePath);
        } catch (IOException e) {
            log.error("Error deleting file: {}", filePath, e);
        }
    }

    public void moveFile(String sourceFilePath, String destinationFilePath) {
        try {
            Path sourcePath = new File(sourceFilePath).toPath();
            Path destinationPath = new File(destinationFilePath).toPath();
            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            log.info("File moved successfully from {} to {}", sourceFilePath, destinationFilePath);
        } catch (IOException e) {
            log.error("Error moving file from {} to {}: {}", sourceFilePath, destinationFilePath, e.getMessage());
        }
    }
}