package com.christmas.letter.sender.controller;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

@Testcontainers
public class ContainerTest {

  private static final String SNS_TOPIC_ARN = "arn:aws:sns:us-east-1:000000000000:ChristmasLetterSns";

  @Container
  static LocalStackContainer localStack =
      new LocalStackContainer(DockerImageName.parse("localstack/localstack:3.4.0"))
          .withCopyFileToContainer(MountableFile.forClasspathResource("test-setup.sh", 4566), "/etc/localstack/ready.d/test-setup.sh")
          .withServices(LocalStackContainer.Service.SQS, LocalStackContainer.Service.SNS);

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("cloud.aws.sqs.endpoint", localStack::getEndpoint);
    registry.add("spring.cloud.aws.sqs.region", localStack::getRegion);
    registry.add("cloud.aws.sns.endpoint", localStack::getEndpoint);
    registry.add("spring.cloud.aws.sns.region", localStack::getRegion);
    registry.add("cloud.aws.credentials.access-key", localStack::getAccessKey);
    registry.add("cloud.aws.credentials.secret-key", localStack::getSecretKey);
    registry.add("aws.sns.topic.arn", () -> SNS_TOPIC_ARN);
  }

}
