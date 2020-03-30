package com.stubhub.messaging.attachment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.stubhub.messaging.attachment.attachments.Attachment;
import com.stubhub.messaging.attachment.attachments.Attachments;
import com.stubhub.messaging.attachment.dao.UserMapper;
import com.stubhub.messaging.attachment.model.Users;
import com.stubhub.messaging.attachment.util.CommunicationUtils;
import com.stubhub.messaging.attachment.util.GZip;
import com.stubhub.messaging.attachment.v2.V2EmailTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RestController
public class V2Controller {

    @Autowired
    private V2EmailTrigger v2EmailTrigger;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(method = RequestMethod.GET , path = "/v2trigger/send")
    public String v2send(){
        v2EmailTrigger.sendEmail();
        return null;
    }

    @GetMapping("/ibatis")
    public String testIBatis(){
        Users users = userMapper.getUserById("120237809");
        int i = userMapper.mergeUsers(Arrays.asList(users, users));
        System.out.println("merged number: " + i);
        return userMapper.getUserById("120237809").toString();
    }

//    @GetMapping("/f")
//    public String testDec(){
//
//    }

    @GetMapping("/db")
    public String getFromDB(){
        String sql = "SELECT * FROM stub.users WHERE default_email = 'linqiu@ebay.com'";
        List<Object> query = jdbcTemplate.query(sql, new RowMapper<Object>() {
            @Override
            public Users mapRow(ResultSet resultSet, int i) throws SQLException {
                Users user = new Users();
                user.setId(resultSet.getString("ID"));
                user.setGuid(resultSet.getString("USER_COOKIE_GUID"));
                user.setDefaultEmail(resultSet.getString("DEFAULT_EMAIL"));
//                user.setLocale(resultSet.getString("PREFERRED_LOCALE"));
                return user;

            }
        });

        for (Object obj: query){
            Users user = (Users)obj;
            System.out.println(user);
        }
        return query.toString();
    }

//    @GetMapping("/")
//    public String index(){
//        return "static/index.html";
//    }

    @PostMapping("/uploadcsv")
    @ResponseBody
    public String uploadcsv(@RequestParam("csv") MultipartFile[] files){
        if (files == null || files.length == 0) {
            return "upload empty file!";
        }

        List<Users> usersList = new ArrayList<>();
        for (MultipartFile file: files){
            Reader reader = null;
            try {
                reader = new InputStreamReader(file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            CsvToBeanBuilder<Users> usersCsvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            usersList.addAll(usersCsvToBeanBuilder.withType(Users.class).build().parse());
        }
        String output = "Number of input users: " + usersList.size() + "\n";
        if (usersList.isEmpty()){
            return output;
        }
        System.out.println(usersList);
        int i = userMapper.mergeUsers(usersList);
        output += "Number of merged users: " + i + "\n";
        System.out.println("Number of merged users: " + i);
        output += "Begin double check from db...\n";
        //double check
        boolean flag = true;
        List<String> idList = new ArrayList<>();
        for (Users user: usersList){
            idList.add(user.getId());
        }
        List<Users> dbusers = userMapper.getUsersByIdList(idList);
        Map<String, Users> dbUsersMap = new HashMap<>();
        for (Users dbuser: dbusers){
            dbUsersMap.put(dbuser.getId(), dbuser);
        }
        for (Users input: usersList){
            Users dbuser = dbUsersMap.get(input.getId());
            if (dbuser == null){
                output += "Missing User: " + input;
                flag = false;
                continue;
            }
            if (!input.equals(dbuser)){
                output += "Unequal Users:\n";
                output += "  input user:\n";
                output += "  " + input.toString() + "\n";
                output += "  db user:\n";
                output += "  " + dbuser.toString() + "\n";
                flag = false;
            }

        }
        if (flag){
            output += "All correct: " + dbusers.size() + "entries...";
        }

        return output;
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return "upload empty file!";
        }

        Attachments attachments = new Attachments();
        List<Attachment> attachmentList = new ArrayList<>();
        attachments.setAttachments(attachmentList);

        for (MultipartFile file : files){
            String contentType = file.getContentType();
            String name = file.getOriginalFilename();
            String data = null;
            try {
//                System.out.println(multipartFileile.getBytes());
//                System.out.println(multipartFileile.getBytes().toString().getBytes());
//                System.out.println(Base64.getEncoder().encodeToString(multipartFileile.getBytes()));
//                System.out.println(Base64.getEncoder().encodeToString(multipartFileile.getBytes().toString().getBytes()));
//            data = multipartFileile.getBytes().toString();
                data = Base64.getEncoder().encodeToString(file.getBytes());
                byte[] bytes = GZip.gZip(file.getBytes());
//                System.out.println(GZip.gZip(file.getBytes()));
                String s = GZip.gZip2String(file.getBytes());
                System.out.println(s);
                System.out.println("压缩后字符串长度:" + s.length());
                String origin = GZip.bytesToHexString(file.getBytes());
                System.out.println("原始的字符串长度:"+ origin.length());

                byte[] back = GZip.unGZip4String(s);
                System.out.println(back);

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

            attachmentList.add(attachment);
        }



        String str = attachments.toString() + "\n";
//        v2EmailTrigger.sendDemoADHOCEmail(attachments);

//        String jsonStr = v2EmailTrigger.getJsonStr(attachments);
//        str += "attachments json str: " + jsonStr + "\n";
//        List list = (List) CommunicationUtils.unpackJson(jsonStr);
//        str += "list str after unpack: " + list.toString() + "\n";

        return str + "upload successfully！";
    }
}
