package com.stubhub.messaging.attachment.config;

import com.stubhub.messaging.attachment.properties.V2ClientProperties;
import com.stubhub.platform.crypto.lib.Algorithm;
import com.stubhub.platform.crypto.lib.DefaultStringEncryptor;
import com.stubhub.platform.crypto.lib.StringEncryptor;
import com.stubhub.platform.crypto.lib.keystore.KeyFactory;
import com.stubhub.platform.crypto.lib.keystore.KeyStoreConfig;
import com.stubhub.platform.crypto.lib.keystore.KeyStoreFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@EnableConfigurationProperties(V2ClientProperties.class)
public class V2ClientConfig {

//    @Bean
//    @ConditionalOnMissingBean
//    public StringEncryptor stubhubEncryptor(@Value("token_encrypt_key") String key) {
//        KeyStoreConfig keystoreConfig = KeyStoreConfig.getInstance();
//        KeyStoreFactory keyStoreFactory = new KeyStoreFactory(keystoreConfig);
//        KeyFactory keyFactory = new KeyFactory(keyStoreFactory.getKeyStore());
//        SecretKey subscriberKey = keyFactory.getSecretKey(key, keystoreConfig.getKeyPassword(key));
//
//        DefaultStringEncryptor encryptor = new DefaultStringEncryptor(subscriberKey);
//        encryptor.setAlgorithm(Algorithm.AES);
//        return encryptor;
//    }
}
