package com.vk.service;

import com.vk.entities.Message;
import java.util.List;

public interface MessageService {

    Message findMessage(int id);
    void saveMessage(Message message);
    void deleteMessage(Message message);
    void updateMessage(Message message);
    List<Message> findAllMessages();
}
