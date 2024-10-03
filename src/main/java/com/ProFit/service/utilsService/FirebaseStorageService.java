package com.ProFit.service.utilsService;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseStorageService {

    private final Storage storage;
    private final String bucketName;

    public FirebaseStorageService(Storage storage, @Value("${firebase.bucket.name}") String bucketName) {
        this.storage = storage;
        this.bucketName = bucketName;
    }

    /**
     * 上傳文件到 Firebase Storage 並返回公開 URL
     *
     * @param file 要上傳的 MultipartFile
     * @return 上傳後的公開 URL
     * @throws IOException 如果上傳過程中出現錯誤
     */
    public String uploadFile(MultipartFile file) throws IOException {
        // 生成唯一的文件名
        String originalFileName = file.getOriginalFilename();
        String suffixName = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = uuid + suffixName;

        // 上傳文件到 Firebase Storage
        Bucket bucket = storage.get(bucketName);
        if (bucket == null) {
            throw new IOException("Bucket not found: " + bucketName);
        }
        Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());

        // 設置檔案為公開訪問（根據需求調整權限）
        blob.createAcl(com.google.cloud.storage.Acl.of(
                com.google.cloud.storage.Acl.User.ofAllUsers(),
                com.google.cloud.storage.Acl.Role.READER));

        // 生成公開 URL
        String publicUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, blob.getName());

        return publicUrl;
    }
}