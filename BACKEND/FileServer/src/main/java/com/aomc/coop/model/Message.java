package com.aomc.coop.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {
    private int message_idx;
    private String content;
    private String nickname;
    private int channel_idx;
    private int user_idx;
    private String send_date;
    private String send_time;
    private String send_db_date;
    private String file_url;
    private String type;
    private FileInfo file;
    private Reply reply;
    private String image;
}
