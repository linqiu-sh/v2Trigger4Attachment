package com.stubhub.messaging.attachment.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stubhub.messaging.attachment.properties.V2ClientProperties;
import com.stubhub.platform.messagehub.client.*;
import com.stubhub.platform.messagehub.client.exception.MessageHubException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class V2Client {
    private MessageHubApiClient v2client;

    private static final Logger logger = LoggerFactory.getLogger(V2Client.class);

    @Autowired
    private V2ClientProperties v2ClientProperties;

    @PostConstruct
    public void init() {
//        DyeProvider dyeProvider = new DyeProvider() {
//            @Override
//            public String getDye() {
//                return SHDyeContext.get().getDye();
//            }
//        };

        String baseUrl = "https://api-int." + v2ClientProperties.getDomainName() + ".com" ;
//        String baseUrl = "https://api-shape-internal." + v2ClientProperties.getDomainName() + ".com" ;
        MessageHubApiContext context = MessageHubApiContext.builder()
                .baseUrl(baseUrl)
                .authorization("Bearer JYf0azPrf1RAvhUhpGZudVU9bBEa")
                .role("R2")
                .operatorId("TRIAL")
                .build();
        v2client = MessageHubApiHttpClient.create(context);
    }

    public void send(MessageHubRequest.MessageHubRequestBuilder request) {
        logger.info("message=Sending Email using MessageHub API");
        try {
            MessageHubRequest req = request.build();
            String body = toJson(req);
            logger.info("message=Sending Email using attachment-trigger - {}, userId={}, requestBody={}", req.getMessageName(), req.getUserId(), body);
            MessageHubResponse response = v2client.send(req);
            logger.info("message=Sent Email using attachment-trigger - {}, userId={}, response={}", req.getMessageName(), req.getUserId(), response.toString());
        } catch (MessageHubException e) {
            logger.error("message=Error sending email using attachment-trigger, exception={}", e.toString());
        }
    }


    public <T> String toJson(T object) {
        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = null;
        try {
            //  mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,true);
            if (object != null) {
                jsonInString = mapper.writeValueAsString(object);
            }
        } catch (Exception e) {
            logger.error("toJson Error! Exception={}", e.toString());
        }
        return jsonInString;
    }

}
