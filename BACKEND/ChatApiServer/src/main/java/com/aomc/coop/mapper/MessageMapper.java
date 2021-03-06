package com.aomc.coop.mapper;

import com.aomc.coop.model.Channel;
import com.aomc.coop.model.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageMapper {

    //messages 테이블
    @Insert("INSERT INTO messages(message_idx, content, channel_idx, user_idx) VALUES(#{message.message_idx}, #{message.content}, #{channelIdx}, #{userIdx})")
    int createMessage(final Message message, final int channelIdx, final int userIdx);

    //채널의 메세지조회
    @Select("SELECT m.message_idx, m.content, channel_idx, u.nickname, u.idx as user_idx, u.image, DATE_FORMAT(send_date, '%W, %M %D') as send_date, DATE_FORMAT(send_date, '%l:%i %p') as send_time, f.content as file_url FROM messages m LEFT JOIN users u ON m.user_idx=u.idx LEFT JOIN file f ON f.idx = m.file_idx WHERE channel_idx = #{channelIdx} ORDER BY m.message_idx desc LIMIT #{start}, 10")
    List<Message> getChannelMessage(int channelIdx, int start);
}
