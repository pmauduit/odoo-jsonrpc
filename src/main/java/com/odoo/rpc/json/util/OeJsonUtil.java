/*
 * Copyright 2016 QFast
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.odoo.rpc.json.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * OeJsonUtil is a utility class for converting json text to some java data types
 *
 * @author Ahmed El-mawaziny
 * @since 1.0
 */
public class OeJsonUtil {

    private static final Logger LOG = Logger.getLogger(OeJsonUtil.class.getName());

    /**
     * static method to convert json string to map
     *
     * @param jsonStr Json object string
     * @return Map key string and value object
     * @see #convertJsonToMap(JsonObject)
     */
    public static Map<String, Object> convertStringToMap(String jsonStr) {
        return convertJsonToMap(new JsonObject().getAsJsonObject(jsonStr));
    }

    /**
     * static method to convert json object to map
     *
     * @param jsonObject json object
     * @return Map key string and value object
     */
    public static Map<String, Object> convertJsonToMap(JsonObject jsonObject) {
        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
        Map<String, Object> convertedMap = new HashMap<String, Object>(entries.size());
        for (Map.Entry<String, JsonElement> entry : entries) {
            convertedMap.put(entry.getKey(), entry.getValue().toString());
        }
        return convertedMap;
    }

    /**
     * static method to convert json array to typed java array
     *
     * @param jsonArray json array
     * @param tArr      class of typed array
     * @param <T>       generic type
     * @return typed array
     */
    public static <T> T[] convertJsonArray(JsonArray jsonArray, Class<T[]> tArr) {
        return new Gson().fromJson(jsonArray, tArr);
    }

    /**
     * static method to convert json array contains json object to map key string and value object array
     *
     * @param jsonArray json array
     * @return array of map string key and value object
     * @see #convertJsonToMap(JsonObject)
     */
    public static Map<String, Object>[] convertJsonArrayToMapArray(JsonArray jsonArray) {
        int length = jsonArray.size();
        @SuppressWarnings("unchecked")
        Map<String, Object>[] mapArr = new HashMap[length];
        for (int i = 0; i < length; i++) {
            mapArr[i] = convertJsonToMap(jsonArray.get(i).getAsJsonObject());
        }
        return mapArr;
    }

    /**
     * static method to parse object to json array
     *
     * @param obj object to parse
     * @return json array
     */
    public static JsonArray parseAsJsonArray(Object obj) {
        return parseAsJsonElement(obj).getAsJsonArray();
    }

    /**
     * static method to parse object to json object
     *
     * @param obj object to parse
     * @return json object
     */
    public static JsonObject parseAsJsonObject(Object obj) {
        return parseAsJsonElement(obj).getAsJsonObject();
    }

    /**
     * static method to parse object to json element
     *
     * @param obj object to parse
     * @return json element
     */
    public static JsonElement parseAsJsonElement(Object obj) {
        try {
            if (!(obj instanceof String)) {
                obj = new Gson().toJson(obj);
            }
            Reader reader = new StringReader(obj.toString());
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.setLenient(true);
            return new JsonParser().parse(jsonReader);
        } catch (JsonParseException e) {
            throw new Error(obj.toString(), e);
        }
    }

    /**
     * static method to merge two json objects in one object
     *
     * @param with merged object
     * @param into merged into it object
     * @return the second param merged with the first param
     */
    public static JsonObject margeJsonObject(JsonObject with, JsonObject into) {
        Set<Map.Entry<String, JsonElement>> entries = with.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            into.add(entry.getKey(), entry.getValue());
        }
        return into;
    }
}
