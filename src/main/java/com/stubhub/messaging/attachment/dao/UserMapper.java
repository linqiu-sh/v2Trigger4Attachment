package com.stubhub.messaging.attachment.dao;

import com.stubhub.messaging.attachment.model.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

//    @Select("SELECT * FROM stub.users WHERE ID = #{id}")
//    @Results(id = "user",
//            value = {
//                    @Result(column = "ID",property = "id"),
//                    @Result(column = "USER_COOKIE_GUID",property = "guid"),
//                    @Result(column = "DEFAULT_EMAIL",property = "defaultEmail"),
//                    @Result(column = "PREFERRED_LOCALE",property = "locale")
//            }
//    )
    public Users getUserById(String id);

    public int mergeUsers(@Param("list")List<Users> list);

    public List<Users> getUsersByIdList(@Param("list")List<String> idList);
}
