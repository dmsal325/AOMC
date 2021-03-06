package com.aomc.coop.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Data
public class Message implements Serializable {
    private int message_idx;
    private String content;
    private String nickname;
    private int channel_idx;
    private int user_idx;
    private String image;
    private String send_date;
    private String send_time;
    private String send_db_date;
    private String file_url;
    private String type;
    private File file;
    private Reply reply;
}
