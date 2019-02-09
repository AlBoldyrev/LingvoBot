package com.vk.service.impl;

import com.vk.dao.MessageDTO;
import com.vk.entities.Message;
import com.vk.entities.User;
import com.vk.service.MessageService;

import java.util.List;

public class MessageServiceImpl implements MessageService {

    private MessageDTO messageDTO = new MessageDTO();

    public MessageServiceImpl() {
    }

    public Message findMessage(int id) {
        return messageDTO.findById(id);
    }

    public void saveMessage(Message message) {
        messageDTO.save(message);
    }

    public void deleteMessage(Message message) {
        messageDTO.delete(message);
    }

    public void updateMessage(Message message) {
        messageDTO.update(message);
    }

    public List<Message> findAllMessages() {
        return messageDTO.findAll();
    }
}
