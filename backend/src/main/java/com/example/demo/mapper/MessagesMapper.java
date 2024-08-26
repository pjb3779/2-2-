package com.example.demo.mapper;

import com.example.demo.model.Messages;
import java.util.List;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MessagesMapper {

        @Select("SELECT m.messageID, m.senderID, ua.userName, m.message " +
                        "FROM Messages m " +
                        "INNER JOIN UserAccount ua ON ua.userID = m.senderID " +
                        "WHERE m.userID = #{userId} " +
                        "ORDER BY m.messageID DESC")
        List<Messages> getMessages(int userId);

        @Delete("DELETE FROM Messages WHERE userID = #{userId} and messageID = #{messageId}")
        void deleteMessages(int userId, int messageId);

        @Insert("INSERT INTO Messages (userID, senderID, message) VALUES (#{userId}, #{senderId}, #{message})")
        void sendMessage(Messages message);
}
