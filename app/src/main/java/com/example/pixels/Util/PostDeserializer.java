package com.example.pixels.Util;

import com.example.pixels.models.ArtType;
import com.example.pixels.models.Image;
import com.example.pixels.models.Post;
import com.example.pixels.models.WritingType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class PostDeserializer implements JsonDeserializer<Post> {
    @Override
    public Post deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        String tag = object.get("tags").getAsString();
        String title = object.get("title").getAsString();
        int type = object.get("type").getAsInt();
        Gson gson = new Gson();

        if (type == Const.ART) {
            ArtType artType = gson.fromJson(object.get("postType").getAsString(),ArtType.class);
            Post post = new Post();
            post.setTitle(title);
            post.setTags(tag);
            post.setType(type);
            post.setContent(artType);
            return post;
        } else {
            WritingType artType = serializeWrite(object.get("postType").getAsJsonObject());
            Post post = new Post();
            post.setTitle(title);
            post.setTags(tag);
            post.setType(type);
            post.setContent(artType);
            return post;
        }
    }

    private WritingType serializeWrite(JsonObject object) {
        WritingType type = new WritingType();
        return type;
    }
}
