package com.lastfarewells.backend.service;

import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lastfarewells.backend.domain.presigUrlDownloadResult;
import com.lastfarewells.backend.domain.presigUrlUploadResult;
import com.lastfarewells.backend.entity.PresignedUrl;

import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
public class StorjAWSService {

	@Value("${storj.bucketName}")
	private String bucketName;

	@Value("${storj.upload.duration}")
	private String presignedUrlUploadDuration;
	
	@Value("${storj.download.duration}")
	private String presignedUrlDownlodDuration;


	@Autowired
	private S3Presigner presigner;

	@Autowired
	private PresignedUrlService presignedUrlService;

	public presigUrlUploadResult getUploadUrl(PresignedUrl presingedUrl) throws Exception{
		presigUrlUploadResult result = new presigUrlUploadResult();
		String objectKey = presingedUrl.getDirectory() + "/" + presingedUrl.getName() + "_" + UUID.randomUUID()
				+ presingedUrl.getExtension();
		presingedUrl.setKey(objectKey);
		
		presignedUrlService.save(presingedUrl);
		result.setUploadUrl(generateUploadPresignedurl(objectKey,presingedUrl.getFileType()));
		result.setKey(objectKey);
		return result;

	}
	
	public String generateUploadPresignedurl(String objectKey,String contentType) {
		PutObjectRequest objectRequest = PutObjectRequest.builder().bucket(bucketName).key(objectKey)
				.contentType(contentType).build();

		PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
				.signatureDuration(Duration.ofMinutes(Integer.parseInt(presignedUrlUploadDuration)))
				.putObjectRequest(objectRequest).build();

		PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
		return presignedRequest.url().toString();
		
	}

	public presigUrlDownloadResult getDownloadUrl(String key) {
		// TODO Auto-generated method stub
		presigUrlDownloadResult  result = new presigUrlDownloadResult();
		result.setMetadata(presignedUrlService.findByKey(key));
		String url =  generateDownloadPresignedUrl(key);
		result.setUrl(url);
		return result;
	}
	
	public String generateDownloadPresignedUrl(String key) {
		GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(key).build();

		GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder().getObjectRequest(getObjectRequest)
				.signatureDuration(Duration.ofMinutes(Integer.parseInt(presignedUrlDownlodDuration))).build();
		String presignedUrl = presigner.presignGetObject(presignRequest).url().toString();
		System.out.println(presignedUrl);
		return presignedUrl;
	}

}
