package com.aomc.coop.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class Message implements Serializable {
    private int idx;
    private String content;
    private String nickname;
    private int channel_idx;
    private int user_idx;
    private String send_date;
    private String send_time;

    private File file;
    private Reply reply;


}