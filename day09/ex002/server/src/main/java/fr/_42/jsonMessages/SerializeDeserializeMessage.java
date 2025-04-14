package fr._42.jsonMessages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class SerializeDeserializeMessage {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> String serialize(T object){
        try {
            return objectMapper.writeValueAsString(object);
        }catch (IOException e) {
            return null;
        }
    }

    public static <T> T deserialize(String json, Class<T> clazz){
        try {
            return objectMapper.readValue(json, clazz);
        }catch (IOException e) {
            return null;
        }
    }
}
