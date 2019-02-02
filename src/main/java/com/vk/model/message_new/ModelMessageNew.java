package com.vk.model.message_new;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ModelMessageNew {

    String type;

    @SerializedName("object")
    Info info;

    int group_id;

    public Info getInfo() {
        return info;
    }

}


