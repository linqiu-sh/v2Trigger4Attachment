package com.stubhub.messaging.attachment.attachments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attachment {
    /**
     * Valid email address
     * The MIME type of the file. The value will apply "as-is" to the "Content-Type" header of the generated MIME part for the file.
     * sample = {"image/jpeg"}
     */
    @JsonProperty(AttachmentFieldName.TYPE)
    private String type;

    /**
     * User-friendly name for the email address
     * The name of the file.
     * sample = {"rainbow.jpg"}
     */

    @JsonProperty(AttachmentFieldName.NAME)
    private String name;

    /**
     * Email address to display in the "To" header instead of address.email (for BCC)
     * he content of the file as a Base64 encoded string. The string should not contain \r\n line breaks. The SparkPost systems will add line breaks as necessary to ensure the Base64 encoded lines contain no more than 76 characters each.
     */
    @JsonProperty(AttachmentFieldName.DATA)
    private String data;
}
