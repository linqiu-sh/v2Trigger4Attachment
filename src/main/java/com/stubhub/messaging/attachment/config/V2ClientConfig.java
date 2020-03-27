package com.stubhub.messaging.attachment.config;

import com.stubhub.messaging.attachment.properties.V2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(V2ClientProperties.class)
public class V2ClientConfig {
}
