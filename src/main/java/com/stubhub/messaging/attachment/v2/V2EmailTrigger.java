package com.stubhub.messaging.attachment.v2;

import com.stubhub.messaging.attachment.attachments.Attachment;
import com.stubhub.messaging.attachment.attachments.Attachments;
import com.stubhub.platform.messagehub.client.MessageHubRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class V2EmailTrigger {
    @Autowired
    private V2Client v2Client;

    public void sendEmail(){
        MessageHubRequest.MessageHubRequestBuilder request = MessageHubRequest.builder()
                .messageName("TW_ADHOC_EMAILS")
                .shStoreId(1L) // Long
                .locale(new Locale("en", "US")) // Java Locale
                .userId(117804089L)
                .userGuid("9EFA3C688246461DE0540010E0581C5E")
                .data("shStoreId","1");

        request.data(MessageHubRequest.USER_EMAIL_DATA_KEY, "linqiu@stubhub.com");

        request.data("emailsubject", "test subject");
        request.data("preheader", "test preheader");
        request.data("emailcontent", "This is the content");
        request.data("attachments", "place holder");
        v2Client.send(request);
    }

    public void sendDemoADHOCEmail(Attachments attachments){
        MessageHubRequest.MessageHubRequestBuilder request = MessageHubRequest.builder()
                .messageName("TW_ADHOC_EMAILS")
                .shStoreId(1L) // Long
                .locale(new Locale("en", "US")) // Java Locale
                .userId(117804089L)
                .userGuid("9EFA3C688246461DE0540010E0581C5E")
                .data("shStoreId","1");

        request.data(MessageHubRequest.USER_EMAIL_DATA_KEY, "linqiu@stubhub.com");

        request.data("emailsubject", "test subject");
        request.data("preheader", "test preheader");
        request.data("emailcontent", "This is the content");
        request.data("attachments", attachments.getAttachments());
        v2Client.send(request);
    }

    public String getJsonStr(Attachments attachments){
        return v2Client.toJson(attachments.getAttachments());
    }
}
