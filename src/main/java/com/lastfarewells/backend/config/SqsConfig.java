package com.lastfarewells.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class SqsConfig {

    private static final Logger logger = LoggerFactory.getLogger(SqsConfig.class);

    @Value("${sqs.accesskey:dummyAccessKey}") // Default to "dummyAccessKey" if not set
    private String accessKey;

    @Value("${sqs.secretkey:dummySecretKey}") // Default to "dummySecretKey" if not set
    private String secretKey;

    @Value("${sqs.region:us-east-1}") // Default to "us-east-1" if not set
    private String region;

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        if ("dummyAccessKey".equals(accessKey) || "dummySecretKey".equals(secretKey)) {
            logger.warn("AWS SQS credentials are missing. SqsAsyncClient will not be fully operational.");
        }

        return SqsAsyncClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }
}
