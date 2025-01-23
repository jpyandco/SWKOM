package at.technikumwien.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.BucketExistsArgs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class MinioService {

    private static final Logger LOGGER = LogManager.getLogger();
    private final MinioClient minioClient;
    private final String bucketName;

    public MinioService(MinioClient minioClient, String defaultBucketName) {
        this.minioClient = minioClient;
        this.bucketName = defaultBucketName;
    }

    public void uploadFile(String fileName, InputStream fileInputStream, String contentType) {
        try {
            boolean isBucketExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isBucketExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(fileInputStream, fileInputStream.available(), -1)
                            .contentType(contentType)
                            .build()
            );
        } catch (Exception e) {
            LOGGER.error("Error uploading file to MinIO: {}", fileName, e);
            throw new RuntimeException("Error uploading file to MinIO", e);
        }
    }

    public InputStream downloadFile(String fileName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
        } catch (Exception e) {
            LOGGER.error("Error downloading file from MinIO: {}", fileName, e);
            throw new RuntimeException("Error downloading file from MinIO", e);
        }
    }

}
