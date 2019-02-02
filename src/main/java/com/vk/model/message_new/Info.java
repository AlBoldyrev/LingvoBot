package com.vk.model.message_new;

import lombok.Data;

import java.util.List;

@Data
public class Info {

    int date;
    int from_id;
    int id;
    int out;
    int peer_id;
    int text;
    int conversation_message_id;
    List<Message> messages;
    boolean important;
    int random_id;
    List<Attachment> attachments;
    boolean is_hidden;

    public int getFromId() {
        return from_id;
    }

}

