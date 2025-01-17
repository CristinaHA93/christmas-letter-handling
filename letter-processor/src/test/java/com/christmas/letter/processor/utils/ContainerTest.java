package com.christmas.letter.processor.utils;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

@Testcontainers
public class ContainerTest {

  @Container
  static LocalStackContainer localStack =
      new LocalStackContainer(DockerImageName.parse("localstack/localstack:3.4.0"))
          .withCopyFileToContainer(MountableFile.forClasspathResource("test-init.sh", 4566), "/etc/localstack/ready.d/test-init.sh")
          .withServices(LocalStackContainer.Service.SQS, LocalStackContainer.Service.SNS, LocalStackContainer.Service.DYNAMODB);

  @BeforeAll
  static void startContainer() {
    if (!localStack.isRunning()) {
      localStack.start();
    }
  }

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.cloud.aws.credentials.access-key", localStack::getAccessKey);
    registry.add("spring.cloud.aws.credentials.secret-key", localStack::getSecretKey);
    registry.add("spring.cloud.aws.sqs.region", localStack::getRegion);
    registry.add("spring.cloud.aws.sqs.endpoint", localStack::getEndpoint);
    registry.add("spring.cloud.aws.sns.region", localStack::getRegion);
    registry.add("spring.cloud.aws.sns.endpoint", localStack::getEndpoint);
    registry.add("spring.cloud.aws.dynamodb.region", localStack::getRegion);
    registry.add("spring.cloud.aws.dynamodb.endpoint", () -> localStack.getEndpointOverride(LocalStackContainer.Service.DYNAMODB).toString());
  }

}
