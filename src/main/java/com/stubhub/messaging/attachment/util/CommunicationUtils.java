package com.stubhub.messaging.attachment.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CommunicationUtils {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private CommunicationUtils() { }

    public static Object unpackJson(String value) {
        try {
            if (value.startsWith("[") && value.endsWith("]")) {
                return JSON_MAPPER.readValue(value, List.class);
            }
            if (value.startsWith("{") && value.endsWith("}")) {
                return JSON_MAPPER.readValue(value, Map.class);
            }
        } catch (IOException ex) { /* ignored */ }
        return value;
    }

}
