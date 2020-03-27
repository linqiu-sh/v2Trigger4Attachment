package com.stubhub.messaging.attachment.attachments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attachments {

    @JsonProperty(AttachmentFieldName.ATTACHMENTS)
    private List<Attachment> attachments;
}
