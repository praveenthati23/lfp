package com.lastfarewells.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;



@Configuration
public class SqsConfig {

	@Value("${sqs.accesskey}")
	private String accessKey;


	@Value("${sqs.secretkey}")

	private String secretKey;

	@Value("${sqs.region}")
	private String region;


	@Bean
	public SqsAsyncClient sqsAsyncClient() {
		return SqsAsyncClient.builder().region(Region.of(region))
				.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
				.build();
	}

}
