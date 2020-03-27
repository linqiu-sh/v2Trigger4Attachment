package com.stubhub.messaging.attachment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stubhub.messaging.attachment.attachments.Attachment;
import com.stubhub.messaging.attachment.attachments.Attachments;
import com.stubhub.messaging.attachment.util.CommunicationUtils;
import com.stubhub.messaging.attachment.v2.V2EmailTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
public class V2Controller {

    @Autowired
    private V2EmailTrigger v2EmailTrigger;

    @RequestMapping(method = RequestMethod.GET , path = "/v2trigger/send")
    public String v2send(){
        v2EmailTrigger.sendEmail();
        return null;
    }

//    @GetMapping("/")
//    public String index(){
//        return "static/index.html";
//    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile multipartFileile) {
        if (multipartFileile.isEmpty()) {
            return "upload empty file!";
        }

        String contentType = multipartFileile.getContentType();
        String name = multipartFileile.getName();
        String data = null;
        try {
            System.out.println(multipartFileile.getBytes());
            System.out.println(multipartFileile.getBytes().toString().getBytes());
            System.out.println(Base64.getEncoder().encodeToString(multipartFileile.getBytes()));
            System.out.println(Base64.getEncoder().encodeToString(multipartFileile.getBytes().toString().getBytes()));
//            data = multipartFileile.getBytes().toString();
            data = Base64.getEncoder().encodeToString(multipartFileile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (data == null || data.isEmpty()){
            return "data empty!!";
        }

        Attachment attachment = new Attachment();
        attachment.setName(name);
        attachment.setType(contentType);
        attachment.setData(data);

        Attachments attachments = new Attachments();
        attachments.setAttachments(Arrays.asList(attachment));

        String str = attachments.toString() + "\n";
        v2EmailTrigger.sendDemoADHOCEmail(attachments);

//        String jsonStr = v2EmailTrigger.getJsonStr(attachments);
//        str += "attachments json str: " + jsonStr + "\n";
//        List list = (List) CommunicationUtils.unpackJson(jsonStr);
//        str += "list str after unpack: " + list.toString() + "\n";

        return str + "upload successfully！";
    }
}
