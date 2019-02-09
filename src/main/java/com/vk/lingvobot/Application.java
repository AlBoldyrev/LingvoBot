package com.vk.lingvobot;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.entities.User;
import com.vk.service.UserService;
import com.vk.service.impl.UserServiceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Application {

    private static final String PROPERTIES_FILE = "config.properties";

    public static void main(String[] args) throws Exception {

        UserService userService = new UserServiceImpl();
        User user = new User("Vasya", "Kaliteevsky");
        System.out.println(user.getName());
        userService.saveUser(user);

        Properties properties = readProperties();
        GroupActor groupActor = createGroupActor(properties);

        HttpTransportClient httpClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(httpClient);

        if (!vk.groups().getLongPollSettings(groupActor).execute().isEnabled()) {
            vk.groups().setLongPollSettings(groupActor).enabled(true).wallPostNew(true).execute();
            vk.groups().setLongPollSettings(groupActor).enabled(true).messageNew(true).execute();
        }

        BotRequestHandler botHandler = new BotRequestHandler(vk, groupActor);
        botHandler.run();
    }

    private static GroupActor createGroupActor(Properties properties) {
        String groupId = properties.getProperty("groupId");
        String accessToken = properties.getProperty("token");
        return new GroupActor(Integer.parseInt(groupId), accessToken);
    }

    private static Properties readProperties() throws IOException {
        InputStream inputStream = Application.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
        if (inputStream == null) {
            throw new FileNotFoundException("property file '" + PROPERTIES_FILE + "' not found in the classpath");
        }
        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Incorrect properties file");
        } finally {
            inputStream.close();
        }
    }


}
