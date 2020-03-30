package com.stubhub.messaging.attachment.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class Users {

    //ID
    @CsvBindByName(column = "USER_ID", required = true)
    private String id;

    //USER_COOKIE_GUID
    @CsvBindByName(column = "USER_COOKIE_GUID", required = true)
    private String guid;

    //DEFAULT_EMAIL
    @CsvBindByName(column = "EMAIL", required = true)
    private String defaultEmail;

    //PREFERRED_LOCALE
//    private String locale;

}
