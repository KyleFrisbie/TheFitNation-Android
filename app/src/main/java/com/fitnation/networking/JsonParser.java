package com.fitnation.networking;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.Arrays;
import java.util.List;

/**
 * Parses JSON
 */
public class JsonParser {
    /**
     * Converts a json string to a list
     *
     * @param s     - json string
     * @param clazz - the [] class of the model object you are wanting to create. ex. Book[].class
     * @return - a list of the objects you are passing in
     */
    public static <T> List<T> convertJsonStringToList(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);

        return Arrays.asList(arr);
    }

    /**
     * Converts a json string to a single object
     *
     * @param json   - json payload
     * @param tClass - the class of the model object you are wanting to create. ex. Book.class
     * @return - a new model object(ex. Book) loaded with values out of the json
     */
    public static <T> T convertJsonStringToPojo(String json, Class<T> tClass) {
        Gson gson = new Gson();

        return tClass.cast(gson.fromJson(json, tClass));
    }

    /**
     * Converts an object to a json string
     *
     * @param obj - the object to be converted
     * @return - a json string
     */
    public static String convertPojoToJsonString(Object obj) {
        Gson gson = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                return expose != null && !expose.serialize();
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        })
                .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                        final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                        return expose != null && !expose.deserialize();
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> aClass) {
                        return false;
                    }
                }).create();

        return gson.toJson(obj);
    }
}
