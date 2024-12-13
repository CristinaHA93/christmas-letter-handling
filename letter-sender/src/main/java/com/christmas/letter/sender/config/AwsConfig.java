package com.christmas.letter.sender.config;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
public class AwsConfig {

  @Autowired
  private Environment env;

  @Bean
  public SnsClient configSns(){
    String snsEndpoint = env.getProperty("sns.endpoint");
    return SnsClient.builder()
        .region(Region.US_EAST_1)
        .endpointOverride(URI.create(snsEndpoint))
        .build();
  }

}
