package com.vk.lingvobot;

import com.google.gson.JsonObject;
import com.vk.api.sdk.callback.longpoll.responses.GetLongPollEventsResponse;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.exceptions.LongPollServerKeyExpiredException;
import com.vk.api.sdk.objects.groups.responses.GetLongPollServerResponse;
import com.vk.strategy.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;


public class BotRequestHandler {

    private static final Logger LOG = LoggerFactory.getLogger(BotRequestHandler.class);
    private static final int DEFAULT_WAIT = 10;

    private final VkApiClient apiClient;

    private final GroupActor groupActor;
    private final Random random = new Random();
    private UserActor userActor;
    private final Integer groupId;
    private final Integer waitTime;


    BotRequestHandler(VkApiClient apiClient, GroupActor groupActor) {
        this.apiClient = apiClient;
        this.groupActor = groupActor;
        this.groupId = groupActor.getGroupId();
        this.waitTime = DEFAULT_WAIT;
    }

    void handle(int userId) {
        try {
            apiClient.messages().send(groupActor).message("Hello my friend!").userId(userId).randomId(random.nextInt()).execute();
        } catch (ApiException e) {
            LOG.error("INVALID REQUEST", e);
        } catch (ClientException e) {
            LOG.error("NETWORK ERROR", e);
        }
    }
    void run() throws Exception {

        GetLongPollServerResponse longPollServer = getLongPollServer();
        int lastTimeStamp = longPollServer.getTs();
        while (true) {
            try {
                GetLongPollEventsResponse eventsResponse = apiClient.longPoll().getEvents(longPollServer.getServer(), longPollServer.getKey(), lastTimeStamp).waitTime(waitTime).execute();
                for (JsonObject jsonObject: eventsResponse.getUpdates()) {
                    String type = jsonObject.get("type").getAsString();
                    System.out.println("jsonType: " + type + "  " + jsonObject);

                    Action.valueOf(type).execute(jsonObject, apiClient, groupActor);

                }
                lastTimeStamp = eventsResponse.getTs();
            } catch (LongPollServerKeyExpiredException e) {
                longPollServer = getLongPollServer();
                LOG.info(longPollServer.toString());
            }
        }
    }

    private GetLongPollServerResponse getLongPollServer() throws ClientException, ApiException {
        if (groupActor != null) {
            return apiClient.groups().getLongPollServer(groupActor).execute();
        }

        return apiClient.groups().getLongPollServer(userActor, groupActor.getGroupId()).execute();
    }
}
