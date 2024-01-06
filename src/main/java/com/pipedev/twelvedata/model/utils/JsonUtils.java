package com.pipedev.twelvedata.model.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Slf4j
public class JsonUtils {

    public static ObjectMapper getJsonMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        JavaTimeModule javaTimeModule=new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        javaTimeModule.addSerializer(LocalDate.class,  new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        objectMapper.registerModule(javaTimeModule);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    public static <T> T readValue(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        try {
            return getJsonMapper().readValue(json, clazz);
        } catch (Exception e) {
            log.warn("JsonUtils.readValue error", e);
            return null;
        }
    }

    public static <T> T readValue(byte[] json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        try {
            return getJsonMapper().readValue(json, clazz);
        } catch (Exception e) {
            log.warn("JsonUtils.readValue error", e);
            return null;
        }
    }

    public static <T> T readValue(byte[] json, TypeReference<T> typeReference) {
        if (json == null) {
            return null;
        }
        try {
            return getJsonMapper().readValue(json, typeReference);
        } catch (Exception e) {
            log.warn("JsonUtils.readValue error", e);
            return null;
        }
    }

    public static <T> ArrayList<T> readListValue(byte[] json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
            return mapper.readValue(json, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> ArrayList<T> readListValue(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
            return mapper.readValue(json, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toString(Object object) {
        if (object == null) {
            return "null";
        }
        try {
            return getJsonMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "error " + object;
        }
    }


}
