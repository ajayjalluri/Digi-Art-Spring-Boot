package com.example.digiart.utils;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
@Component
public class Gcp {
    String projectId = "us-gcp-ame-con-116-npd-1";
    String bucketName = "hu-22-digi-art";

    public void uploading(MultipartFile multipartFile,String objectName)throws IOException
    {
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        byte[] content = multipartFile.getBytes();

        storage.createFrom(blobInfo, new ByteArrayInputStream(content));

    }

    public String downloading(String objectName){
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        byte[] content = storage.readAllBytes(bucketName, objectName);
        String encoded = Base64Utils.encodeToString(content);
        return encoded;
    }

}
