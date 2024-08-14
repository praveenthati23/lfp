package com.lastfarewells.backend.service;


import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.lastfarewells.backend.domain.presigUrlDownloadResult;
import com.lastfarewells.backend.domain.presigUrlUploadResult;
import com.lastfarewells.backend.entity.PresignedUrl;
import com.lastfarewells.backend.entity.Users;
import com.lastfarewells.backend.repository.UsersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class StorjAWSService {

	@Value("${storj.bucketName}")
	private String bucketName;

	@Value("${storj.upload.duration}")
	private String presignedUrlUploadDuration;
	
	@Value("${storj.download.duration}")
	private String presignedUrlDownlodDuration;


	private final UsersRepository usersRepo;
	private final S3Presigner presigner;

	@Autowired
	private PresignedUrlService presignedUrlService;

	public presigUrlUploadResult getUploadUrl(PresignedUrl presingedUrl) throws Exception{
		log.info("call to getUploadUrl");
		presigUrlUploadResult result = new presigUrlUploadResult();
		
		JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext()
				.getAuthentication();
		String userEmail = authentication.getTokenAttributes().get("email").toString();
		Users user = usersRepo.findByEmail(userEmail).get();
		Date date = new Date();
		String fileName ="";
		fileName = user.getId() + "-"+date.getTime() +"-"+UUID.randomUUID()+"."+presingedUrl.getExtension();
		
		log.info("getUploadUrl: fileName: "+fileName);
		String objectKey = presingedUrl.getDirectory()  +"/" + fileName;
		presingedUrl.setKey("/"+objectKey);
		presingedUrl.setCreatedAt(Instant.now());
		presingedUrl.setModifiedAt(Instant.now());
		
		presignedUrlService.save(presingedUrl);
		result.setUploadUrl(generateUploadPresignedurl(objectKey,presingedUrl.getFileType()));
		result.setKey(fileName);
		return result;

	}
	
	public String generateUploadPresignedurl(String objectKey,String contentType) {
		log.info("generateUploadPresignedurl: objectKey: "+objectKey);
		PutObjectRequest objectRequest = PutObjectRequest.builder().bucket(bucketName).key(objectKey)
				.contentType(contentType).build();

		PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
				.signatureDuration(Duration.ofMinutes(Integer.parseInt(presignedUrlUploadDuration)))
				.putObjectRequest(objectRequest).build();

		PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
		return presignedRequest.url().toString();
		
	}

	public presigUrlDownloadResult getDownloadUrl(String key) {
		log.info("getDownloadUrl: key: "+key);
		presigUrlDownloadResult  result = new presigUrlDownloadResult();
		result.setMetadata(presignedUrlService.findByKey(key));
		String url =  generateDownloadPresignedUrl(key);
		result.setUrl(url);
		return result;
	}
	
	public String generateDownloadPresignedUrl(String key) {
		log.info("call to generateDownloadPresignedUrl: "+key);
		GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(key).build();
		GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder().getObjectRequest(getObjectRequest)
				.signatureDuration(Duration.ofMinutes(Integer.parseInt(presignedUrlDownlodDuration))).build();
		String presignedUrl = presigner.presignGetObject(presignRequest).url().toString();
		log.info("presignedUrl: "+presignedUrl);
		return presignedUrl;
	}

}
