package at.technikumwien.controller;

import at.technikumwien.service.MinioService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final MinioService minioService;
    private final String bucketName;

    public FileController(MinioService minioService, String defaultBucketName) {
        this.minioService = minioService;
        this.bucketName = defaultBucketName;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            minioService.uploadFile(bucketName, fileName, file.getInputStream(), file.getContentType());
            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }

    @GetMapping(value = "/download/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStream> downloadFile(@PathVariable String fileName) {
        try {
            InputStream fileStream = minioService.downloadFile(bucketName, fileName);
            return ResponseEntity.ok(fileStream);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}
