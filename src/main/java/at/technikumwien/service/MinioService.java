package at.technikumwien.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.BucketExistsArgs;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class MinioService {

    private final MinioClient minioClient;
    private final String bucketName;

    public MinioService(MinioClient minioClient, String defaultBucketName) {
        this.minioClient = minioClient;
        this.bucketName = defaultBucketName;
    }

    public void uploadFile(String fileName, String name, InputStream fileInputStream, String contentType) {
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
            throw new RuntimeException("Error uploading file to MinIO", e);
        }
    }

    public InputStream downloadFile(String fileName, String name) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error downloading file from MinIO", e);
        }
    }
}
