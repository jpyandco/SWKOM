package at.technikumwien.workerservice.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class MinioService {

    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public byte[] downloadFile(String fileName) {
        try (InputStream inputStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket("documents")
                        .object(fileName)
                        .build()
        )) {
            return inputStream.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException("Error downloading file from MinIO: " + fileName, e);
        }
    }
}
