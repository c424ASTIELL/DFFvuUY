// 代码生成时间: 2025-10-02 02:36:21
package com.example.integrity;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.io.ResourceLoader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

// FileIntegrityChecker is a utility class for checking the integrity of files.
public class FileIntegrityChecker {

    private MessageDigest digest;

    // Constructor which initializes the MessageDigest instance with SHA-256 algorithm
    public FileIntegrityChecker() {
        try {
            this.digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to initialize SHA-256 algorithm", e);
        }
    }

    // Method to calculate the checksum of a file
    public byte[] calculateChecksum(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            throw new IllegalArgumentException("File does not exist: " + filePath.toString());
        }

        try {
            return calculateChecksum(Files.newInputStream(filePath));
        } catch (IOException e) {
            throw new IOException("Error while reading file: " + filePath.toString(), e);
        }
    }

    // Helper method to calculate checksum from an InputStream
    private byte[] calculateChecksum(java.io.InputStream inputStream) throws IOException {
        byte[] buffer = new byte[8192];
        int read;
        while ((read = inputStream.read(buffer)) != -1) {
            digest.update(buffer, 0, read);
        }
        inputStream.close();
        return digest.digest();
    }

    // Method to compare two checksums for equality
    public boolean areChecksumsEqual(byte[] checksum1, byte[] checksum2) {
        return Arrays.equals(checksum1, checksum2);
    }
}

@Factory
class FileIntegrityCheckerFactory {

    @Bean
    FileIntegrityChecker fileIntegrityChecker() {
        return new FileIntegrityChecker();
    }
}