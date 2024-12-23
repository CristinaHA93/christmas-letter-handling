package com.christmas.letter.processor.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import java.net.URI;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
@EnableDynamoDBRepositories(basePackages = {"com.christmas.letter.processor.repository"})
public class Configs {

  @Value("${spring.cloud.aws.dynamodb.region}")
  private String region;

  @Value("${spring.cloud.aws.dynamodb.endpoint}")
  private String endpoint;

  @Value("${spring.cloud.aws.credentials.access-key}")
  private String accessKey;

  @Value("${spring.cloud.aws.credentials.secret-key}")
  private String secretKey;

  @Value("${spring.cloud.aws.sns.endpoint}")
  private String snsEndpoint;

  @Value("${spring.cloud.aws.sqs.endpoint}")
  private String sqsEndpoint;

  @Bean
  public AmazonDynamoDB amazonDynamoDB() {
    return AmazonDynamoDBClientBuilder.standard()
        .withEndpointConfiguration(getEndpointConfig())
        .withCredentials(getCredentialsProvider())
        .build();
  }

  @Bean
  public SnsClient snsClient(){
    return SnsClient.builder()
        .region(Region.of(region))
        .endpointOverride(URI.create(snsEndpoint))
        .credentialsProvider(StaticCredentialsProvider.create(
            AwsBasicCredentials.create(accessKey, secretKey)))
        .build();
  }

  @Bean
  public SqsClient sqsClient(){
    return SqsClient.builder()
        .region(Region.of(region))
        .endpointOverride(URI.create(sqsEndpoint))
        .credentialsProvider(StaticCredentialsProvider.create(
            AwsBasicCredentials.create(accessKey, secretKey)))
        .build();
  }

  private AWSStaticCredentialsProvider getCredentialsProvider() {
    return new AWSStaticCredentialsProvider(getBasicAWSCredentials());
  }

  private AwsClientBuilder.EndpointConfiguration getEndpointConfig() {
    return  new AwsClientBuilder.EndpointConfiguration(endpoint,region);
  }

  private BasicAWSCredentials getBasicAWSCredentials() {
    return new BasicAWSCredentials(accessKey, secretKey);
  }

}
