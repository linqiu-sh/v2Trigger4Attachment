package com.stubhub.messaging.attachment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix="client")
public class V2ClientProperties {
    private String domainName;
}
